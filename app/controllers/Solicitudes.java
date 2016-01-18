package controllers;

import static play.modules.pdf.PDF.renderPDF;

import java.util.Date;
import java.util.List;

import models.DespachoVehiculo;
import models.OrdenMovilizacion;
import models.SolicitudSalidaVehiculo;
import models.Usuario;
import models.Vehiculo;
import play.modules.paginate.ModelPaginator;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
public class Solicitudes extends Controller {
	
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
	
	@Check("DIRECCION")
    public static void formulario() {
        render();
    }
    
	@Check("DIRECCION")
    public static void guardarSolicitud(String lugar, Date fecha, String cargo, String direccion, String motivo,
    		String lugarOrigen,String lugarDestino, String horaSalida,
    		String duracion, String observaciones){
		
		System.out.println("Número de mes "+fecha.getMonth());
    	
    	int numero;
    	List<SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("order by id asc").fetch();
    	Usuario user = Usuario.find("byUsername", Security.connected()).first();
    	
    	if(solicitudes.isEmpty()){    		
    		numero=1;//Valor inicial de las solicitudes
    	}else{
    		SolicitudSalidaVehiculo ultimaSolicitud=solicitudes.get(solicitudes.size()-1);
    		numero=ultimaSolicitud.numero+1;
    	}
    	
    	OrdenMovilizacion ordenMovilizacion=new OrdenMovilizacion();
    	ordenMovilizacion.save();
    	DespachoVehiculo despachoVehiculo=new DespachoVehiculo();
    	despachoVehiculo.save();
    	
    	    	
    	SolicitudSalidaVehiculo nuevaSolicitud=new SolicitudSalidaVehiculo(numero,-1,lugar, fecha, motivo, 
    			lugarOrigen, lugarDestino, horaSalida, duracion, observaciones, cargo, direccion, 
    			user.cedula, user.nombre+" "+user.apellido,user.id, ordenMovilizacion, despachoVehiculo);
    	
    	nuevaSolicitud.save();
    	historialSolicitudes();
    }
    
    public static void historialSolicitudes(){
    	
    	Usuario usuario=Usuario.find("byUsername",Security.connected()).first();    	
    	List<SolicitudSalidaVehiculo> solicitudes=null;
    	if(usuario.perfil.descripcion.equals("DIRECCION")){
    		solicitudes=SolicitudSalidaVehiculo.find("idsolicitante = ? order by id desc",usuario.id).fetch();
    	}
    	    	
    	if(usuario.perfil.descripcion.equals("LOGISTICA")){
    		solicitudes=SolicitudSalidaVehiculo.find("estado = ? order by id asc",-1).fetch();
    	}
    	render(solicitudes);
    }
    
 
  public static void aprobadas(){    	    
    	List<SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("estado = ? order by id asc",0).fetch();  
    	render(solicitudes);
    }
    
    
    @Check("LOGISTICA")
    public static void editarOrdenMovilizacion(Long idSolicitud){        	
    	Usuario user=Usuario.find("byUsername",Security.connected()).first();
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);    
    	if(user.perfil.descripcion.equals("LOGISTICA") || solicitud.idSolicitante==user.id ){    	
    		List<Vehiculo> vehiculos=Vehiculo.findAll();
    		List<Usuario> conductores=Usuario.find("byPerfil.descripcion","CONDUCTOR").fetch();
    		render(solicitud,vehiculos,conductores);    	
    	}else{
    		Security.errorPermisos();
    	}
    }

    @Check("LOGISTICA")
    public static void guardarOrdenMovilizacion(Long idSolicitud, Long idVehiculo, Long idConductor, String servidor, String cedulaServidor, String funcionario){
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(idVehiculo);
    	Usuario conductor=Usuario.findById(idConductor);
    	
    	solicitud.ordenMovilizacion.asignarDatos(vehiculo, conductor, servidor, cedulaServidor, funcionario);
    	solicitud.ordenMovilizacion.save();
    	solicitud.estado=0;
    	solicitud.save();
    	OrdenesCombustible.confirmacion(idSolicitud);
    	   	
    }
    
    @Check("LOGISTICA")
    public static void editarSolicitud(Long idSolicitud){
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	List<Vehiculo> vehiculos=Vehiculo.findAll();
		List<Usuario> conductores=Usuario.find("byPerfil.descripcion","CONDUCTOR").fetch();
    	render(solicitud,vehiculos,conductores);
    }
    
    public static void finalizadas(){
    	
    	
    	
    	ModelPaginator solicitudes=new ModelPaginator(SolicitudSalidaVehiculo.class,"estado = ?",1).orderBy("ID ASC");
    	//List<SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("estado = ? order by id asc",1).fetch();
    	solicitudes.setPageSize(10);
    	render(solicitudes);
    	
    }
    
    
    public static void solicitudPDF(Long idSolicitud){
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);    
    	String fecha=formatearFecha(solicitud.fecha);
    	renderPDF(solicitud,fecha);
    }
    
    
    public static int contarPendientes(){
    	List<SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("estado = -1").fetch();       	
    	return solicitudes.size();    	
    }
    
    
    public static int contarAprobadas(){
    	List<SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("estado = 0 and despachoVehiculo.estado=0").fetch();    	
    	return solicitudes.size();
    }
    
    public static int contarAprobadasSalida(){
    	List<SolicitudSalidaVehiculo> solicitudes=SolicitudSalidaVehiculo.find("estado = 0 and despachoVehiculo.estado=1").fetch();    	
    	return solicitudes.size();    	
    }
    

    
    public static String formatearFecha(Date fecha){

        	String[] partesFecha=String.valueOf(fecha).split("-");
        	String[] dia = partesFecha[2].split(" ");
        
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
    		

            String fechaLista=dia[0]+" de "+mes+" del "+partesFecha[0];
        	return (fechaLista);
        	
        }

}


