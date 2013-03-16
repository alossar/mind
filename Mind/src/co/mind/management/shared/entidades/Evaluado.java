package co.mind.management.shared.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the evaluados database table.
 * 
 */
@Entity
@Table(name="evaluados")
public class Evaluado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int identificador;

	private String apellidos;

	private String correoElectronico;

	private int edad;

	private String nombres;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="cuentaUsuarioAdministrador_identificador")
	private Usuario usuario;

	//bi-directional many-to-one association to ParticipacionEnProceso
	@OneToMany(mappedBy="evaluado")
	private List<ParticipacionEnProceso> participacionEnProcesos;

    public Evaluado() {
    }

	public int getIdentificador() {
		return this.identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCorreoElectronico() {
		return this.correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public int getEdad() {
		return this.edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<ParticipacionEnProceso> getParticipacionEnProcesos() {
		return this.participacionEnProcesos;
	}

	public void setParticipacionEnProcesos(List<ParticipacionEnProceso> participacionEnProcesos) {
		this.participacionEnProcesos = participacionEnProcesos;
	}
	
}