package controllers;

import models.SolicitudSalidaVehiculo;
import models.Usuario;
import models.Vehiculo;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
@Check("DESPACHADOR")
public class DespachoVehiculos extends Controller {

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
	
	
    public static void salida(Long idSolicitud) {
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);    
        render(solicitud,vehiculo);
    }
    
    public static void retorno(Long idSolicitud) {
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);    
        render(solicitud,vehiculo);
    }
    
    public static void registrarSalida(Long idSolicitud, String horaSalida, int kilometrajeSalida, String observaciones){
    	
    	Usuario despachador=Usuario.find("byUsername",Security.connected()).first();       	
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);   
    	vehiculo.kilometraje=kilometrajeSalida;//actualizando kilometraje del vehiculo
    	vehiculo.save();
    	solicitud.despachoVehiculo.registrarSalida(horaSalida, kilometrajeSalida, observaciones, despachador.nombre+" "+despachador.apellido,despachador.id); 
    	solicitud.despachoVehiculo.estado=1;
    	solicitud.despachoVehiculo.save();        	
    	solicitud.save();    	
    	flash.put("salida","Salida registrada exitosamente");
    	Solicitudes.aprobadas();

    }
                
    public static void registrarRetorno(Long idSolicitud, String horaRetorno, int kilometrajeRetorno, String combustibleRetorno, String observaciones){
    	SolicitudSalidaVehiculo solicitud=SolicitudSalidaVehiculo.findById(idSolicitud);
    	Vehiculo vehiculo=Vehiculo.findById(solicitud.ordenMovilizacion.idVehiculo);    	
    	//Actualizando datos del vehiculo
    	vehiculo.kilometraje=kilometrajeRetorno;
    	vehiculo.combustible=combustibleRetorno;
    	vehiculo.save();    	    	
    	solicitud.despachoVehiculo.registrarRetorno(horaRetorno, kilometrajeRetorno, combustibleRetorno, observaciones);    	
    	solicitud.despachoVehiculo.save();
    	solicitud.estado=1;
    	solicitud.save();
    	flash.put("finalizada","Retorno registrado exitosamente. La solicitud ha sido finalizada");
    	flash.put("idSolicitud",solicitud.id);
    	Solicitudes.finalizadas();	
    }       
        
	
	public static boolean validarKilometraje(Long idVehiculo, int kilometraje){		
		Vehiculo vehiculo=Vehiculo.findById(idVehiculo);		
		return kilometraje>=vehiculo.kilometraje;
	}

}
