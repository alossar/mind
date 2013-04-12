package co.mind.management.shared.dto;

import java.io.Serializable;
import java.util.Date;

public class ParticipacionEnProcesoBO implements Serializable {

	private static final long serialVersionUID = 1L;
	private int identificador;
	private String codigo_Acceso;
	private String estado;
	private Date fechaFinalizacion;
	private Date fechaInicio;
	private int procesoID;
	private EvaluadoBO usuarioBasico;

	private String sesionID;

	public ParticipacionEnProcesoBO() {
	}

	public int getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getCodigo_Acceso() {
		return this.codigo_Acceso;
	}

	public void setCodigo_Acceso(String codigo_Acceso) {
		this.codigo_Acceso = codigo_Acceso;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public int getProcesoID() {
		return procesoID;
	}

	public void setProcesoID(int procesoID) {
		this.procesoID = procesoID;
	}

	public EvaluadoBO getUsuarioBasico() {
		return usuarioBasico;
	}

	public void setUsuarioBasico(EvaluadoBO usuarioBasico) {
		this.usuarioBasico = usuarioBasico;
	}

	public void setSesionID(String id) {
		this.sesionID = id;

	}

	public String getSesionID() {
		return sesionID;
	}

}
