package co.mind.management.shared.bo;

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
	private String solicitudValoracion;
	private String notificacionEnviada;
	private List<PruebaUsuarioBO> pruebasUsuario;

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

	public String getSolicitudValoracion() {
		return this.solicitudValoracion;
	}

	public void setSolicitudValoracion(String solicitudValoracion) {
		this.solicitudValoracion = solicitudValoracion;
	}

	public List<PruebaUsuarioBO> getPruebasUsuarioID() {
		return this.pruebasUsuario;
	}

	public void setPruebasUsuarioID(List<PruebaUsuarioBO> pruebasUsuario) {
		this.pruebasUsuario = pruebasUsuario;
	}

	public String getNotificacionEnviada() {
		return notificacionEnviada;
	}

	public void setNotificacionEnviada(String notificacionEnviada) {
		this.notificacionEnviada = notificacionEnviada;
	}

}
