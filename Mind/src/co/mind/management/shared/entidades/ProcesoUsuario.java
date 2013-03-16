package co.mind.management.shared.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the procesos_usuarios database table.
 * 
 */
@Entity
@Table(name="procesos_usuarios")
public class ProcesoUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int identificador;

	private String descripcion;

	@Column(name="estado_valoracion")
	private String estadoValoracion;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fechaCreacion;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fechaFinalizacion;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fechaInicio;

	private String nombre;

	@Column(name="notificacion_enviada")
	private String notificacionEnviada;

	//bi-directional many-to-one association to ParticipacionEnProceso
	@OneToMany(mappedBy="procesosUsuario")
	private List<ParticipacionEnProceso> participacionEnProcesos;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuarios_identificador")
	private Usuario usuario;

	//bi-directional many-to-many association to PruebaUsuario
	@ManyToMany(mappedBy="procesosUsuarios")
	private List<PruebaUsuario> pruebasUsuarios;

    public ProcesoUsuario() {
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

	public String getEstadoValoracion() {
		return this.estadoValoracion;
	}

	public void setEstadoValoracion(String estadoValoracion) {
		this.estadoValoracion = estadoValoracion;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaFinalizacion() {
		return this.fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNotificacionEnviada() {
		return this.notificacionEnviada;
	}

	public void setNotificacionEnviada(String notificacionEnviada) {
		this.notificacionEnviada = notificacionEnviada;
	}

	public List<ParticipacionEnProceso> getParticipacionEnProcesos() {
		return this.participacionEnProcesos;
	}

	public void setParticipacionEnProcesos(List<ParticipacionEnProceso> participacionEnProcesos) {
		this.participacionEnProcesos = participacionEnProcesos;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<PruebaUsuario> getPruebasUsuarios() {
		return this.pruebasUsuarios;
	}

	public void setPruebasUsuarios(List<PruebaUsuario> pruebasUsuarios) {
		this.pruebasUsuarios = pruebasUsuarios;
	}
	
}