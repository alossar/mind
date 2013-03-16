package co.mind.management.shared.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int identificador;

	private String apellidos;

	private String cargo;

	private String ciudad;

	private String contrasena;

	private String correo_Electronico;

	private String empresa;

	private String estado_Cuenta;

    @Temporal( TemporalType.TIMESTAMP)
	private Date fecha_Creacion;

	private String nombres;

	private String pais;

	private String telefono;

	private String telefono_Celular;

	private String tipo;

	//bi-directional many-to-one association to Evaluado
	@OneToMany(mappedBy="usuario")
	private List<Evaluado> evaluados;

	//bi-directional many-to-one association to ImagenUsuario
	@OneToMany(mappedBy="usuario")
	private List<ImagenUsuario> imagenesUsuarios;

	//bi-directional many-to-one association to ProcesoUsuario
	@OneToMany(mappedBy="usuario")
	private List<ProcesoUsuario> procesosUsuarios;

	//bi-directional many-to-one association to PruebaUsuario
	@OneToMany(mappedBy="usuario")
	private List<PruebaUsuario> pruebasUsuarios;

	//bi-directional many-to-one association to Permiso
    @ManyToOne
	@JoinColumn(name="permisos_identificador")
	private Permiso permiso;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="usuarios_identificador")
	private Usuario usuario;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="usuario")
	private List<Usuario> usuarios;

    public Usuario() {
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

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getContrasena() {
		return this.contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo_Electronico() {
		return this.correo_Electronico;
	}

	public void setCorreo_Electronico(String correo_Electronico) {
		this.correo_Electronico = correo_Electronico;
	}

	public String getEmpresa() {
		return this.empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getEstado_Cuenta() {
		return this.estado_Cuenta;
	}

	public void setEstado_Cuenta(String estado_Cuenta) {
		this.estado_Cuenta = estado_Cuenta;
	}

	public Date getFecha_Creacion() {
		return this.fecha_Creacion;
	}

	public void setFecha_Creacion(Date fecha_Creacion) {
		this.fecha_Creacion = fecha_Creacion;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getTelefono_Celular() {
		return this.telefono_Celular;
	}

	public void setTelefono_Celular(String telefono_Celular) {
		this.telefono_Celular = telefono_Celular;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Evaluado> getEvaluados() {
		return this.evaluados;
	}

	public void setEvaluados(List<Evaluado> evaluados) {
		this.evaluados = evaluados;
	}
	
	public List<ImagenUsuario> getImagenesUsuarios() {
		return this.imagenesUsuarios;
	}

	public void setImagenesUsuarios(List<ImagenUsuario> imagenesUsuarios) {
		this.imagenesUsuarios = imagenesUsuarios;
	}
	
	public List<ProcesoUsuario> getProcesosUsuarios() {
		return this.procesosUsuarios;
	}

	public void setProcesosUsuarios(List<ProcesoUsuario> procesosUsuarios) {
		this.procesosUsuarios = procesosUsuarios;
	}
	
	public List<PruebaUsuario> getPruebasUsuarios() {
		return this.pruebasUsuarios;
	}

	public void setPruebasUsuarios(List<PruebaUsuario> pruebasUsuarios) {
		this.pruebasUsuarios = pruebasUsuarios;
	}
	
	public Permiso getPermiso() {
		return this.permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}