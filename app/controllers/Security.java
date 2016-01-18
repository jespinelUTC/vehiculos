package controllers;

import models.Usuario;
import play.mvc.Before;

public class Security extends Secure.Security {
	
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
	
		
	public static boolean authenticate(String username,String password){
		Usuario user = Usuario.find("byUsername", username).first();	
		if(user!=null){
			if(user.username.equals(username) && user.password.equals(password)){
				flash.put("bienvenido","Bienvenido al sistema" );
				return true;
			}else{
				flash.put("errorPassword","El usuario o contraseña ingresados son incorrectos." );
				return false;
			}
		}else{
			flash.put("errorUsername","El usuario ingresado no existe." );
			return false;
		}
	}
	

 	 public static boolean check(String profile) {
 	       Usuario user = Usuario.find("byUsername", connected()).first();
 	       
 	       if(user == null){
 	    	   errorPermisos();
 	           return false;
 	       }
 	       else {

 	           if (user.perfil.descripcion.equals(profile)) {
 		           return true;
 		       }else{
	 	           errorPermisos();
	 	           return false;
 		       }
 	       }
 	   }  
 	 
 	 public static void errorPermisos(){
 		 render();
 	 }
 	 
 	 
	public static void configurarCuenta(){
	    	render();
	}
 	  
    //Metodo validador para configuraciones de la cuenta
    public static boolean verificarCedulaEditar(String cedula, Long id){
    	Usuario usuario=Usuario.find("byCedula",cedula).first();   	
    	Usuario existente=Usuario.findById(id);    	
    	return usuario==null || existente==usuario;    	
    }
    
    
  //Metodo validador para configuraciones de la cuenta
    public static boolean verificarUsername(String username, Long id){
    	Usuario usuario=Usuario.find("byUsername",username).first();   	
    	Usuario existente=Usuario.findById(id);    	
    	return usuario==null || existente==usuario;    	
    }
    
  
    
    //Metodo validador para configuraciones de la cuenta
    public static boolean verificarPassword(String password,Long idUsuario){    	
    	Usuario usuario=Usuario.findById(idUsuario);    	
    	if(usuario.password.equals(password)){
    		return true;
    	}else{
    		return false;
    	}    	
    }
    
    public static void actualizarCuenta(String cedula, String apellido, String nombre, String username,String passwordNuevo){
    	
    	boolean opcion;
    	
    	
    	Usuario user = Usuario.find("byUsername", Security.connected()).first();
    	
    	opcion=username.equals(user.username);
    	
    	user.actualizarCuenta(cedula, apellido, nombre, username);

    	if(!passwordNuevo.equals("")){
    		user.actalizarPassword(passwordNuevo);
    	}    
    	user.save();   
    	
    	if(opcion){
    		flash.put("actualizacionCuenta","Los datos de su cuenta han sido actualizados exitosamente.");
    		Application.index();
    	}else{
    		try {
				Secure.logout();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	  	
    }
    

}
