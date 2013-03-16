package co.mind.management.shared.bo;

import java.io.Serializable;

public class PruebaUsuarioBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int identificador;
	private String descripcion;
	private String nombre;
	private int usuarioAdministradorID;

	public int getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getUsuarioAdministradorID() {
		return this.usuarioAdministradorID;
	}

	public void setUsuarioAdministradorID(int usuarioAdministradorID) {
		this.usuarioAdministradorID = usuarioAdministradorID;
	}

}
