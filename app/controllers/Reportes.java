package controllers;

import static play.modules.pdf.PDF.renderPDF;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

import models.OrdenCombustible;
import models.OrdenMovilizacion;
import models.SolicitudSalidaVehiculo;
import models.Usuario;
import models.Vehiculo;
import play.modules.pdf.PDF.Options;
import play.mvc.Before;
import play.mvc.Controller;

public class Reportes extends Controller {
	
	@Before
	public static void mostrarUsuario(){
		try{
			Usuario user = Usuario.find("byUsername", Security.connected()).first();
			//obteniendo datos del usuario que ha iniciado sesion
			if(Security.isConnected()) {
		        renderArgs.put("conectado",user); 
		    }
		}catch(Exception ex){
			
		}	
	}

    public static void reporteDiarioVehicular() {
    	List<Vehiculo> vehiculos=Vehiculo.find("order by id asc").fetch();    	
        render(vehiculos);
    }
    
    public static void reporteDiarioVehicularPDF(String fecha) {
    	List<Vehiculo> vehiculos=Vehiculo.find("order by id asc").fetch();    	
        renderPDF(vehiculos,fecha);
    }
    
    
    public static void controlIngresoKilometraje(Date fecha) throws ParseException{
    	String f;
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");  
    	List <SolicitudSalidaVehiculo> solicitudes=null;  
    	if(fecha==null){    
    		Date now = new Date();
    		f=sdfDate.format(now);
    		solicitudes=SolicitudSalidaVehiculo.find("fecha = ? and estado = ? order by id",now, 1).fetch();
    	}else{
    		solicitudes=SolicitudSalidaVehiculo.find("fecha = ? and estado = ? order by id",fecha, 1).fetch();
    		  		    
    		f= sdfDate.format(fecha);
    	}    	

    	render(solicitudes,f);
    }
    
    public static void controlIngresoKilometrajePDF(Date fecha1, String fechaConsulta){
    	//fecha1 -> 2015-11-18 -- fechaConsulta Miercoles, 18 de Noviembre del 2015
    	Options options = new Options();
    	options.filename = "Control_Ingreso_Kilometraje_"+fechaConsulta+".pdf";
    	options.pageSize = IHtmlToPdfTransformer.A4L; //  A4 landscape size
    	List <SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("fecha = ? and estado = ? order by id",fecha1, 1).fetch();
    	renderPDF(options,fecha1, fechaConsulta,solicitudes);
    }
    
    public static String fechaHoy(){
    	Date hoy=new Date();
    	SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
    	return dt1.format(hoy); 
    }
    
    public static void reporteVehiculoFecha(Date fechaInicio, Date fechaFin, Long idVehiculo){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");  
    	String f1="";
    	String f2="";
    	List<SolicitudSalidaVehiculo> solicitudes=null;
    	//System.out.println(rangoFechas);
    	if(fechaInicio!=null && fechaFin!=null && idVehiculo!=null){
    		solicitudes=SolicitudSalidaVehiculo.find("ordenMovilizacion.idVehiculo = ? and estado=1 and fecha between ? and ?",idVehiculo,fechaInicio,fechaFin).fetch();
    		f1=sdfDate.format(fechaInicio);
        	f2=sdfDate.format(fechaFin);
    	}
    	
    	List<Vehiculo> vehiculos=Vehiculo.find("order by id asc").fetch(); 
    	render(vehiculos,f1,f2,idVehiculo,solicitudes);
    }
    
    
    public static void consumoCombustible(Date fechaInicio, Date fechaFin){
    	
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");  
    	String f1="";
    	String f2="";
    	List<OrdenCombustible> ordenes=null;
    	//System.out.println(rangoFechas);
    	if(fechaInicio!=null && fechaFin!=null){
    		ordenes=OrdenCombustible.find("fecha between ? and ? order by id asc",fechaInicio,fechaFin).fetch();
    		f1=sdfDate.format(fechaInicio);
        	f2=sdfDate.format(fechaFin);
    	}
    	
    	render(f1,f2,ordenes);
    	render();
    }
    
     
    
 public static String formatearFecha(Date fecha){
    	 
    	
    	SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
    	String fechaCompleta=formateador.format(fecha);
    	
    	String[] partesFecha=fechaCompleta.split("-");
    
    	String mes="Desconocido";
        
       if(partesFecha[1].equals("01")) mes="enero";       
       if(partesFecha[1].equals("02")) mes="febrero";
       if(partesFecha[1].equals("03")) mes="marzo";
       if(partesFecha[1].equals("04")) mes="abril";
       if(partesFecha[1].equals("05")) mes="mayo";
       if(partesFecha[1].equals("06")) mes="junio";
       if(partesFecha[1].equals("07")) mes="julio";
       if(partesFecha[1].equals("08")) mes="agosto";
       if(partesFecha[1].equals("09")) mes="septiembre";
       if(partesFecha[1].equals("10")) mes="octubre";
       if(partesFecha[1].equals("11")) mes="noviembre";
       if(partesFecha[1].equals("12")) mes="diciembre";
		

        String fechaLista=partesFecha[0]+" de "+mes+" del "+partesFecha[2];
    	return (fechaLista);
    	
    }
 
 
 public static void calendario(Long idVehiculo){
	 List<SolicitudSalidaVehiculo> solicitudes =null;
	 Vehiculo vehiculo=null;
	 if(idVehiculo != null){
		 solicitudes =SolicitudSalidaVehiculo.find("ordenMovilizacion.idVehiculo=? order by id asc",idVehiculo).fetch();
		 vehiculo=Vehiculo.findById(idVehiculo);
	 }
	 
	 List<Vehiculo> vehiculos=Vehiculo.find("order by id asc").fetch(); 
	 render(vehiculos,solicitudes,idVehiculo,vehiculo);
 }

}
