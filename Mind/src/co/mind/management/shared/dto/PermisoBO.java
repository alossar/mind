package co.mind.management.shared.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the permisos database table.
 * 
 */
public class PermisoBO implements Serializable {

	private int identificador;

	private String creacion;

	private String edicion;

	private String nombre;	

    public PermisoBO() {
    }

	public int getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getCreacion() {
		return this.creacion;
	}

	public void setCreacion(String creacion) {
		this.creacion = creacion;
	}

	public String getEdicion() {
		return this.edicion;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
		
}