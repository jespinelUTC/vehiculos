package controllers;

import java.util.List;

import models.Usuario;
import models.Vehiculo;
import play.modules.paginate.ModelPaginator;
import play.modules.paginate.ValuePaginator;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.With;


@With(Secure.class)
@Check("LOGISTICA")
public class Vehiculos extends Controller {
	
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
  	
    	List<Vehiculo> listaVehiculos=null;
    	
    	if(busqueda==null){
    		listaVehiculos=Vehiculo.find("order by id asc ").fetch();
    	}else{
    		
    		listaVehiculos=Vehiculo.find("lower(trim(placa)) || lower(trim(marca)) || lower(trim(tipo))  || lower(trim(color)) || lower(trim(descripcion)) || lower(trim(custodio.nombre)) || lower(trim(custodio.apellido)) like ? order by id asc","%"+busqueda.toLowerCase()+"%").fetch();
    	}
    	ValuePaginator vehiculos=new ValuePaginator(listaVehiculos);      	    	
    	vehiculos.setPageSize(10);    	    
    	List<Vehiculo> vehiculosExcel=Vehiculo.find("order by id asc").fetch();    	
        render(vehiculos,vehiculosExcel,busqueda);
    }
    
    public static void nuevoVehiculo() {
    	List<Usuario> conductores=Usuario.find("byPerfil.descripcion","CONDUCTOR").fetch();
        render(conductores);
    }
    
    public static void guardarVehiculo(String placa, String matricula, String marca, String color, String tipo, String descripcion, int kilometraje, Long idCustodio, String combustible){    	
    	Usuario custodio=Usuario.findById(idCustodio);
    	Vehiculo vehiculo=new Vehiculo(placa.toUpperCase(), matricula, marca, color, tipo, descripcion, kilometraje, custodio,combustible);
    	vehiculo.save();
    	flash.put("nuevoVehiculo",vehiculo.placa);
    	flash.put("guardado", "Vehículo guardado exitosamente");
    	index(null);
    }
    
    
    public static void eliminarVehiculo(Long idVehiculo){       	
    	Vehiculo vehiculo=Vehiculo.findById(idVehiculo);
    	vehiculo.delete();
    	flash.put("eliminado", "Vehículo eliminado exitosamente");
    	index(null);    	
    }
    
    public static void editarVehiculo(Long idVehiculo){
    	List<Usuario> conductores=Usuario.find("byPerfil.descripcion","CONDUCTOR").fetch();
    	Vehiculo vehiculo=Vehiculo.findById(idVehiculo);
    	render(vehiculo,conductores);
    	
    }
    
    
    public static void actualizarVehiculo(Long idVehiculo,String placa, String matricula, String marca, String color, String tipo, String descripcion, int kilometraje, Long idCustodio, String combustible){
    	Usuario custodio=Usuario.findById(idCustodio);
    	Vehiculo vehiculo=Vehiculo.findById(idVehiculo);
    	vehiculo.actualizarDatos(placa.toUpperCase(), matricula, marca, color, tipo, descripcion, kilometraje, custodio,combustible);
    	vehiculo.save();
    	flash.put("nuevoVehiculo",vehiculo.placa);
    	flash.put("actualizado", "Vehículo actualizado exitosamente");
    	index(null);
    }

    public static boolean verificarPlacaNueva(String placa){
    	Vehiculo vehiculo=Vehiculo.find("byPlaca",placa.toUpperCase()).first();   	
    	return vehiculo==null;    	
    }
    
    //Metodo validador para ediciones de usuarios
    public static boolean verificarPlacaEditar(String placa, Long id){
    	Vehiculo vehiculo=Vehiculo.find("byPlaca",placa.toUpperCase()).first(); 
    	Vehiculo existente=Vehiculo.findById(id);   	
    	return vehiculo==null || existente==vehiculo;    	
    }

}
