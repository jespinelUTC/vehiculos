package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Vehiculo extends Model {
    
	public String placa;
	public String matricula;
	public String marca;
	public String color;
	public String tipo;
	public String descripcion;
	public int kilometraje;
	public String combustible;
	
	@ManyToOne
	public Usuario custodio;

	
	public Vehiculo(String placa, String matricula, String marca, String color, String tipo, String descripcion,
			int kilometraje, Usuario custodio, String combustible) {
		super();
		this.placa = placa;
		this.matricula = matricula;
		this.marca = marca;
		this.color = color;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.kilometraje = kilometraje;
		this.custodio = custodio;
		this.combustible=combustible;
	}
	
	public void actualizarDatos(String placa, String matricula, String marca, String color, String tipo, String descripcion,
				int kilometraje, Usuario custodio, String combustible) {			
			this.placa = placa;
			this.matricula = matricula;
			this.marca = marca;
			this.color = color;
			this.tipo = tipo;
			this.descripcion = descripcion;
			this.kilometraje = kilometraje;
			this.custodio = custodio;
			this.combustible=combustible;
	}

}
