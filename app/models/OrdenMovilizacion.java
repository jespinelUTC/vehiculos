package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class OrdenMovilizacion extends Model {
    
	public String funcionarioAutorizado;//nombre y apellido
	//Datos de Vehiculo
	public Long idVehiculo;
	public String vehiculoAsignado;
	public String custodio;//nombre y apellido
	public String marca;
	public String placa;
	public String matricula;
	public String color;
	public String conductor;//nombre y apellido
	public String cedulaConductor;
	public String servidorAcargo;//servidor a cargo del vehiculo
	public String cedulaServidorAcargo;	
	public String tipoVehiculo;
	
	@ManyToOne
	public OrdenCombustible ordenCombustible;
	
	
	public void asignarDatos(Vehiculo vehiculo, Usuario conductor, String servidor,
			String cedulaServidor, String funcionario){
		this.idVehiculo=vehiculo.id;
		this.vehiculoAsignado=vehiculo.descripcion; //OJO
		this.custodio=vehiculo.custodio.nombre+" "+vehiculo.custodio.apellido;
		this.marca=vehiculo.marca;
		this.placa=vehiculo.placa;
		this.matricula=vehiculo.matricula;
		this.color=vehiculo.color;
		
		this.conductor=conductor.nombre+" "+conductor.apellido;
		this.cedulaConductor=conductor.cedula;
		this.servidorAcargo=servidor;
		this.cedulaServidorAcargo=cedulaServidor;
		this.funcionarioAutorizado=funcionario;		
		this.tipoVehiculo=vehiculo.tipo;
		
		
	}

	
	
}
