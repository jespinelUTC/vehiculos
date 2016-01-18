package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Perfil extends Model {
    public String descripcion;

	public Perfil(String descripcion) {
		super();
		this.descripcion = descripcion;
	}
    
}
