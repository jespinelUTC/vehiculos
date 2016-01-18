package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class DespachoVehiculo extends Model {
	
	public String horaSalida;
	public int kilometrajeSalida;	
	public String horaRetorno;
	public int kilometrajeRetorno;
	public String combustibleRetorno;
	@Column(columnDefinition="TEXT")
	public String observaciones;
	
	public String despachador; //nombres y apellidos
	
	public Long idDespachador;
	
	public int estado;

	
	
	public DespachoVehiculo(){
		this.estado=0;
	}
	

	public void registrarSalida(String horaSalida, int kilometrajeSalida, String observaciones, String despachador, Long idDespachador) {		
		this.horaSalida = horaSalida;
		this.kilometrajeSalida = kilometrajeSalida;
		this.observaciones = observaciones;
		this.despachador = despachador;
		this.idDespachador = idDespachador;
	}
	
	public void registrarRetorno(String horaRetorno,int kilometrajeRetorno, String combustibleRetorno, String observaciones ){
		this.horaRetorno=horaRetorno;
		this.kilometrajeRetorno=kilometrajeRetorno;
		this.combustibleRetorno=combustibleRetorno;
		this.observaciones=observaciones;
	}
    
}
