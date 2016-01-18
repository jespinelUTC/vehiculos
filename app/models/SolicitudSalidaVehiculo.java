package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class SolicitudSalidaVehiculo extends Model {
	
	public int numero;
	public int estado;
	
	public String lugar;
	public Date fecha;
	
	@Column(columnDefinition="TEXT")
	public String motivoMovilizacion;
	public String lugarOrigen;
	public String lugarDestino;
	public String horaSalida;
	public String tiempoDuracionComision;
	@Column(columnDefinition="TEXT")
	public String observaciones;		
	public String cargoSolicitante;
	public String direccionSolicitante;		
	public String cedulaSolicitante;
	public String nombreSolicitante;	
	public Long idSolicitante;
	
	@ManyToOne
	public OrdenMovilizacion ordenMovilizacion;
	
	@ManyToOne
	public DespachoVehiculo despachoVehiculo;
	

	public SolicitudSalidaVehiculo(int numero, int estado, String lugar, Date fecha, String motivoMovilizacion,
			String lugarOrigen, String lugarDestino, String horaSalida, String tiempoDuracionComision,
			String observaciones, String cargoSolicitante, String direccionSolicitante, String cedulaSolicitante,
			String nombreSolicitante, Long idSolicitante, OrdenMovilizacion ordenMovilizacion,
			DespachoVehiculo despachoVehiculo) {
		super();
		this.numero = numero;
		this.estado = estado;
		this.lugar = lugar;
		this.fecha = fecha;
		this.motivoMovilizacion = motivoMovilizacion;
		this.lugarOrigen = lugarOrigen;
		this.lugarDestino = lugarDestino;
		this.horaSalida = horaSalida;
		this.tiempoDuracionComision = tiempoDuracionComision;
		this.observaciones = observaciones;
		this.cargoSolicitante = cargoSolicitante;
		this.direccionSolicitante = direccionSolicitante;
		this.cedulaSolicitante = cedulaSolicitante;
		this.nombreSolicitante = nombreSolicitante;
		this.idSolicitante = idSolicitante;
		this.ordenMovilizacion = ordenMovilizacion;
		this.despachoVehiculo = despachoVehiculo;
	}
	
	
	
	
	
	
	
	
	
	
	
    
}
