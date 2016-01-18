package controllers;

import java.util.List;

import models.Perfil;
import models.Usuario;
import models.Vehiculo;
import play.modules.paginate.ModelPaginator;
import play.modules.paginate.ValuePaginator;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
@Check("LOGISTICA")
public class Usuarios extends Controller {

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
	
    public static void index(String busqueda) {    	
    	Usuario conectado=Usuario.find("byUsername",Security.connected()).first();
    	List<Usuario> usuariosExcel=Usuario.find("order by apellido asc").fetch();      	
    	List<Usuario> listaUsuarios=null;
    	
    	if(busqueda==null){
    		listaUsuarios=Usuario.find("id <> ? order by apellido asc", conectado.id).fetch();
    	}else{
    		
    		listaUsuarios=Usuario.find("id <> ? and lower(trim(apellido)) || lower(trim(nombre)) || cedula || lower(trim(perfil.descripcion)) like ? order by perfil.descripcion asc", conectado.id,"%"+busqueda.toLowerCase()+"%").fetch();
    	}
    	ValuePaginator usuarios=new ValuePaginator(listaUsuarios);      	    	
    	usuarios.setPageSize(10);
    	
        render(usuarios,usuariosExcel,busqueda);
    }
    
    
    public static void nuevoUsuario() {
    	List<Perfil> perfiles=Perfil.find("order by descripcion").fetch();
        render(perfiles);
    }
    
    public static void guardarUsuario(String cedula,String apellido,String nombre, Long perfil){    	
    	Perfil perfil1=Perfil.findById(perfil);    	
    	Usuario u=new Usuario(cedula, cedula, cedula, apellido, nombre, perfil1);
    	u.save();
    	flash.put("guardado","Usuario guardado exitosamente");
    	flash.put("nuevoUsuario",u.cedula);
    	index(null);
    }
    
    public static void actualizarUsuario(Long idUsuario,String cedula,String apellido,String nombre, Long perfil){    	
    	Perfil perfil1=Perfil.findById(perfil);    	
    	Usuario u=Usuario.findById(idUsuario);
    	u.actualizarDatos(cedula, apellido, nombre, perfil1);    	    
    	u.save();
    	flash.put("actualizado","Los datos del usuario se han actualizado exitosamente.");
    	flash.put("nuevoUsuario",u.cedula);
    	index(null);
    }
    
    public static void eliminarUsuario(Long id){  
    	
    	Usuario usuario=Usuario.findById(id);
    	
    	Vehiculo vehiculo=Vehiculo.find("byCustodio",usuario).first();
    	
    	try{    		    
    		usuario.delete();
    		flash.put("eliminado","Usuario eliminado exitosamente.");
    	}catch(Exception ex){
    		
    		if(usuario.perfil.descripcion.equals("CONDUCTOR") && vehiculo!= null){    			    		
    			flash.put("errorEliminado","No se puede eliminar a este conductor ya que es el custododio del vehículo "+vehiculo.descripcion);
    		}else{
    			flash.put("errorEliminado","No se puede eliminar a este usuario ya que se relaciona con otras tablas.");
    		}
    	}
    	
    	index(null);    	
    }
    
    
    public static boolean verificarCedulaNueva(String cedula){
    	Usuario usuario=Usuario.find("byCedula",cedula).first();   	
    	return usuario==null;    	
    }
    
    //Metodo validador para ediciones de usuarios
    public static boolean verificarCedulaEditar(String cedula, Long id){
    	Usuario usuario=Usuario.find("byCedula",cedula).first();   	
    	Usuario existente=Usuario.findById(id);    	
    	return usuario==null || existente==usuario;    	
    }
    
    public static void editarUsuario(Long id){
    	Usuario usuarioEditar=Usuario.findById(id);
    	List<Perfil> perfiles=Perfil.find("order by descripcion").fetch();
    	render(perfiles,usuarioEditar);
    }
    
    public static void reestablecerPassword(Long idUsuario){
    	
    	Usuario usuario=Usuario.findById(idUsuario);
    	usuario.reestablecerPassword();
    	usuario.save();
    	flash.put("reestablecido","Las credenciales del usuario se han reestablecido exitosamente.");
    	flash.put("nuevoUsuario",usuario.cedula);
    	index(null);
    	
    }
    
    
  
  
    

    
  	 

}
