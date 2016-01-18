package controllers;

import static play.modules.pdf.PDF.renderPDF;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.OrdenCombustible;
import models.SolicitudSalidaVehiculo;
import models.Usuario;
import models.Vehiculo;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
@Check("LOGISTICA")
public class OrdenesCombustible extends Controller {
	
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

    public static void nuevaOrden(Long idSolicitud) {
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud); 
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);    
        render(solicitud,vehiculo);
    }
    
    public static void confirmacion(Long idSolicitud){
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);    	
    	render(solicitud,vehiculo);
    }
    
    public static void guardarOrden(Long idSolicitud,String suma, float total, String concepto, float cantidad, float valor, String estacion, String observaciones){
    	
    	Usuario u=Usuario.find("byUsername",Security.connected()).first();
    	OrdenCombustible orden= new OrdenCombustible();
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);
    	
    	if(solicitud.ordenMovilizacion.ordenCombustible==null){
	    	int numero;
	    	
	    	List<OrdenCombustible> ordenes=OrdenCombustible.findAll();
	    	if(ordenes.isEmpty()){    		
	    		numero=1;//Valor inicial de las solicitudes
	    	}else{
	    		OrdenCombustible ultimaOrden=ordenes.get(ordenes.size()-1);
	    		numero=ultimaOrden.numero+1;
	    	}
	
	       	
	    	orden.asignarDatos(numero, concepto, cantidad, valor,solicitud.direccionSolicitante ,new Date(), observaciones, solicitud.ordenMovilizacion.marca,vehiculo.tipo, solicitud.ordenMovilizacion.placa,solicitud.ordenMovilizacion.custodio, vehiculo.kilometraje, 
	    			solicitud.motivoMovilizacion,solicitud.ordenMovilizacion.conductor, (u.nombre+" "+u.apellido), estacion,suma,total);
	    	
	    	solicitud.ordenMovilizacion.ordenCombustible=orden;
	    	solicitud.ordenMovilizacion.ordenCombustible.save();
	    	solicitud.ordenMovilizacion.save();
	    	solicitud.save();	
	    	flash.put("confirmacionOrden","Orden de combustible generada exitosamente.");	    		
	    	flash.put("nuevosolicitud",solicitud.numero);
	    	flash.put("idSolicitud",solicitud.id);
	    }else{
	    	flash.put("errorOrden","Error al guardar, debido a que esta solicitud de salida ya tiene asociada la Orden de Combustible No."+solicitud.ordenMovilizacion.ordenCombustible.numero);
	    }
    	
    	Solicitudes.aprobadas();
    }
    
    public static void ordenPDF(Long idSolicitud){
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);    	
    	String fecha=formatearFecha(solicitud.ordenMovilizacion.ordenCombustible.fecha);
    	renderPDF(solicitud,fecha);
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
    
}










