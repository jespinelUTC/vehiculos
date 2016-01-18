package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Usuario extends Model {
	public String username;	
	public String password;
	
	
	public String cedula;
	public String apellido;
	public String nombre;
	
	
	@ManyToOne
	public Perfil perfil;
	
	
	//Constructor para crear el usuario administrador por defecto
	public Usuario(String username, String password, Perfil perfil) {
		super();
		this.username = username;
		this.password = password;
		this.perfil = perfil;
	}
	

	//Constructor para crear usuarios con la interfaz de administracion
	public Usuario(String username, String password, String cedula, String apellido, String nombre, Perfil perfil) {
		super();
		this.username = username;
		this.password = password;
		this.cedula = cedula;
		this.apellido = apellido;
		this.nombre = nombre;
		this.perfil = perfil;
	}
	
	
	
	public void actualizarDatos(String cedula, String apellido, String nombre, Perfil perfil){
		this.cedula = cedula;
		this.apellido = apellido;
		this.nombre = nombre;
		this.perfil = perfil;
	}
	
	
	public void reestablecerPassword(){
		this.password=this.cedula;
		this.username=this.cedula;
	}
	
	
	public void actualizarCuenta(String cedula, String apellido, String nombre, String username){
		this.cedula = cedula;
		this.apellido = apellido;
		this.nombre = nombre;
		this.username=username;
	}
	
	public void actalizarPassword(String password){
		this.password=password;
		
	}

	
	
	
	
	
    
}
