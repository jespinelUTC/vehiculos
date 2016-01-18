package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class OrdenCombustible extends Model {
    public int numero;
    public String concepto;
    public float cantidad;
    public float valorUnitario;
    public String unidadAsignada;
    public Date fecha;
    @Column(columnDefinition="TEXT")
    public String observaciones;
    
    //datos vehiculo
    public String marca;
    public String tipo;
    public String placa;
    public String custodio;//Nombre y apellido
    public int kilometraje;
    @Column(columnDefinition="TEXT")
    public String actividad;
    public String nombreConductor;
    public String nombreAutorizadoMunicipio;
    public String estacionServicio;
    public String suma;
    public float total;
    
	public void asignarDatos(int numero, String concepto, float cantidad, float valorUnitario, String unidadAsignada,
			Date fecha, String observaciones, String marca, String tipo, String placa, String custodio,
			int kilometraje, String actividad, String nombreConductor, String nombreAutorizadoMunicipio,
			String estacionServicio, String suma,float total) {		
		this.numero = numero;
		this.concepto = concepto;
		this.cantidad = cantidad;
		this.valorUnitario = valorUnitario;
		this.unidadAsignada = unidadAsignada;
		this.fecha = fecha;
		this.observaciones = observaciones;
		this.marca = marca;
		this.tipo = tipo;
		this.placa = placa;
		this.custodio = custodio;
		this.kilometraje = kilometraje;
		this.actividad = actividad;
		this.nombreConductor = nombreConductor;
		this.nombreAutorizadoMunicipio = nombreAutorizadoMunicipio;
		this.estacionServicio = estacionServicio;
		this.suma=suma;
		this.total=total;
	}
    

    
}
