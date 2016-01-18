package controllers;

import java.util.List;

import models.Perfil;
import models.Usuario;
import play.mvc.Controller;

public class Configuracion extends Controller {

  
	public static void crearPerfiles(){
	List<Perfil> perfiles=Perfil.findAll();
    	
    	if(perfiles.isEmpty()){
    		    		   	
    		Perfil perfil2=new Perfil("CONDUCTOR");
    		perfil2.save();
    		
    		Perfil perfil3=new Perfil("DESPACHADOR");
    		perfil3.save();
    		
    		Perfil perfil4=new Perfil("DIRECCION");
    		perfil4.save();
    		
    		Perfil perfil5=new Perfil("LOGISTICA");
    		perfil5.save();    		
    		
    		System.out.println("Perfiles de Usuario Creados");
    	} 
	}
	
	public static void crearLogistica(){
		
		Perfil perfil=Perfil.find("byDescripcion","LOGISTICA").first();
		List<Usuario> usuarios=Usuario.findAll();
				
		if(usuarios.isEmpty() && perfil != null){
			Usuario administrador=new Usuario("admin","admin",perfil);
			administrador.save();
			System.out.println("Usuario administrador creado");
		}else{
			System.out.println("Ejecure primero configuracion/crearPerfiles");
		}		
	}
	


}
