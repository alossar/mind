package co.mind.management.shared.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProcesoUsuarioBO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int identificador;
	private String descripcion;
	private String estadoValoracion;
	private Date fechaCreacion;
	private Date fechaFinalizacion;
	private Date fechaInicio;
	private String nombre;
	private String notificacionEnviada;
	private List<ProcesoUsuarioHasPruebaUsuarioBO> procesoUsuarioHasPruebaUsuario;
	private UsuarioBO usuario;

	public ProcesoUsuarioBO() {
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

	public List<ProcesoUsuarioHasPruebaUsuarioBO> getProcesoUsuarioHasPruebaUsuario() {
		return this.procesoUsuarioHasPruebaUsuario;
	}

	public void setPruebasUsuarioID(
			List<ProcesoUsuarioHasPruebaUsuarioBO> pruebasUsuario) {
		this.procesoUsuarioHasPruebaUsuario = pruebasUsuario;
	}

	public String getNotificacionEnviada() {
		return notificacionEnviada;
	}

	public void setNotificacionEnviada(String notificacionEnviada) {
		this.notificacionEnviada = notificacionEnviada;
	}

	public int cantidadDePreguntas() {
		int cantidad = 0;
		for (int i = 0; i < procesoUsuarioHasPruebaUsuario.size(); i++) {
			cantidad += procesoUsuarioHasPruebaUsuario.get(i)
					.getPruebasUsuario().cantidadDePreguntas();
		}
		return cantidad;
	}

	public int duracionProceso() {
		int duracion = 0;
		for (int i = 0; i < procesoUsuarioHasPruebaUsuario.size(); i++) {
			duracion += procesoUsuarioHasPruebaUsuario.get(i)
					.getPruebasUsuario().duracionPrueba();
		}
		return duracion;
	}

	public UsuarioBO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioBO usuario) {
		this.usuario = usuario;
	}

}
