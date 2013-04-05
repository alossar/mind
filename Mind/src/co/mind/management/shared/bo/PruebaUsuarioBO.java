package co.mind.management.shared.bo;

import java.io.Serializable;
import java.util.List;

public class PruebaUsuarioBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int identificador;
	private String descripcion;
	private String nombre;
	private int usuarioAdministradorID;
	private List<PreguntaUsuarioBO> preguntas;

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

	public List<PreguntaUsuarioBO> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaUsuarioBO> preguntas) {
		this.preguntas = preguntas;
	}

	public int cantidadDePreguntas() {
		return preguntas.size();
	}

	public int duracionPrueba() {
		int duracion = 0;
		for (int i = 0; i < preguntas.size(); i++) {
			duracion += preguntas.get(i).getTiempoMaximo();
		}
		return duracion;
	}

	@Override
	public boolean equals(Object o) {
		return this.getIdentificador() == ((PruebaUsuarioBO) o)
				.getIdentificador();
	}

	@Override
	public int hashCode() {
		return this.getIdentificador();
	}
}
