package co.mind.management.shared.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the pruebas_usuarios database table.
 * 
 */
@Entity
@Table(name="pruebas_usuarios")
public class PruebaUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int identificador;

	private String descripcion;

	private String nombre;

	//bi-directional many-to-one association to PreguntaUsuario
	@OneToMany(mappedBy="pruebasUsuario")
	private List<PreguntaUsuario> preguntasUsuarios;

	//bi-directional many-to-many association to ProcesoUsuario
    @ManyToMany
	@JoinTable(
		name="procesos_usuarios_has_pruebas_usuarios"
		, joinColumns={
			@JoinColumn(name="pruebas_usuarios_identificador")
			}
		, inverseJoinColumns={
			@JoinColumn(name="procesos_usuarios_identificador")
			}
		)
	private List<ProcesoUsuario> procesosUsuarios;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuarios_identificador")
	private Usuario usuario;

    public PruebaUsuario() {
    }

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

	public List<PreguntaUsuario> getPreguntasUsuarios() {
		return this.preguntasUsuarios;
	}

	public void setPreguntasUsuarios(List<PreguntaUsuario> preguntasUsuarios) {
		this.preguntasUsuarios = preguntasUsuarios;
	}
	
	public List<ProcesoUsuario> getProcesosUsuarios() {
		return this.procesosUsuarios;
	}

	public void setProcesosUsuarios(List<ProcesoUsuario> procesosUsuarios) {
		this.procesosUsuarios = procesosUsuarios;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}