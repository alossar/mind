package co.mind.management.shared.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the permisos database table.
 * 
 */
@Entity
@Table(name="permisos")
public class Permiso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int identificador;

	private String creacion;

	private String edicion;

	private String nombre;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="permiso")
	private List<Usuario> usuarios;

    public Permiso() {
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

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}