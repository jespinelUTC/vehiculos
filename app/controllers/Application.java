package controllers;

import models.Usuario;
import play.data.validation.Required;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Application extends Controller {

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
	
    public static void index() {
        render();
    }
    
    public static void enterDemo(@Required String user, @Required String demo) {        
        if(validation.hasErrors()) {
            flash.error("Por favor indique su nombre de usuario");
            index();
        }        
        // Dispatch to the demonstration        
        if(demo.equals("refresh")) {
            Refresh.index(user);
        }    
    }           
    
}