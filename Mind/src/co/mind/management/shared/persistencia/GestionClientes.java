package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.dto.ImagenBO;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.entidades.Evaluado;
import co.mind.management.shared.entidades.PreguntaUsuario;
import co.mind.management.shared.entidades.ProcesoUsuario;
import co.mind.management.shared.entidades.PruebaUsuario;
import co.mind.management.shared.entidades.UsoUsuario;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.Generador;
import co.mind.management.shared.recursos.SMTPSender;

public class GestionClientes implements IGestionClientes {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionClientes() {
		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
	}

	@Override
	// Deben pasarle la contrase�a generada.
	public int agregarUsuarioAdministrador(
			UsuarioAdministradorBO usuarioAdministrador) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario u = new Usuario();
			u.setApellidos(usuarioAdministrador.getApellidos());
			u.setCorreo_Electronico(usuarioAdministrador
					.getCorreo_Electronico());
			u.setIdentificador(usuarioAdministrador.getIdentificador());
			u.setNombres(usuarioAdministrador.getNombres());
			u.setCargo(usuarioAdministrador.getCargo());
			u.setCiudad(usuarioAdministrador.getCiudad());
			u.setContrasena(Generador.convertirStringmd5(Generador
					.GenerarCodigo(Generador.CARACTERES, 12)));
			u.setEmpresa(usuarioAdministrador.getEmpresa());
			u.setEstado_Cuenta(Convencion.ESTADO_CUENTA_ACTIVA);
			u.setFecha_Creacion(new Date());
			u.setIdentificador(usuarioAdministrador.getIdentificador());
			u.setNombres(usuarioAdministrador.getNombres());
			u.setPais(usuarioAdministrador.getPais());
			u.setTelefono(usuarioAdministrador.getTelefono());
			u.setTelefono_Celular(usuarioAdministrador.getTelefono_Celular());
			u.setTipo(Convencion.TIPO_USUARIO_ADMINISTRADOR);
			if (!entityManager.contains(u)) {
				entityManager.persist(u);
				entityManager.flush();
				userTransaction.commit();
				return Convencion.CORRECTO;
			} else {
				return Convencion.INCORRECTO;
			}
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	public int agregarUsuarioAdministrador(int usuarioMaestroID,
			UsuarioAdministradorBO usuarioAdministrador, UsoBO usos,
			List<PruebaUsuarioBO> pruebas) {

		Usuario u = entityManager.find(Usuario.class,
				usuarioAdministrador.getIdentificador());
		if (u != null) {
			return Convencion.INCORRECTO_USUARIO_CEDULA_EXISTENTE;
		} else {
			String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.correo_Electronico =:correo";
			Query q = entityManager.createQuery(query);
			q.setParameter("correo",
					usuarioAdministrador.getCorreo_Electronico());
			try {
				u = (Usuario) q.getSingleResult();
				if (u != null) {
					return Convencion.INCORRECTO_USUARIO_CORREO_EXISTENTE;
				}
			} catch (NoResultException e) {
				EntityTransaction userTransaction = entityManager
						.getTransaction();
				try {
					userTransaction.begin();
					String contrasena = Generador.GenerarCodigo(
							Generador.CARACTERES, 12);
					u = new Usuario();
					u.setApellidos(usuarioAdministrador.getApellidos());
					u.setCorreo_Electronico(usuarioAdministrador
							.getCorreo_Electronico());
					u.setIdentificador(usuarioAdministrador.getIdentificador());
					u.setNombres(usuarioAdministrador.getNombres());
					u.setCargo(usuarioAdministrador.getCargo());
					u.setCiudad(usuarioAdministrador.getCiudad());
					u.setContrasena(Generador.convertirStringmd5(contrasena));
					u.setEmpresa(usuarioAdministrador.getEmpresa());
					u.setEstado_Cuenta(Convencion.ESTADO_CUENTA_ACTIVA);
					u.setFecha_Creacion(new Date());
					u.setIdentificador(usuarioAdministrador.getIdentificador());
					u.setNombres(usuarioAdministrador.getNombres());
					u.setPais(usuarioAdministrador.getPais());
					u.setTelefono(usuarioAdministrador.getTelefono());
					u.setTelefono_Celular(usuarioAdministrador
							.getTelefono_Celular());
					u.setTipo(Convencion.TIPO_USUARIO_ADMINISTRADOR);
					if (!entityManager.contains(u)) {
						entityManager.persist(u);
						entityManager.flush();
						userTransaction.commit();

						GestionLaminas gLaminas = new GestionLaminas();
						GestionPreguntas gPreguntas = new GestionPreguntas();
						entityManager.refresh(u);

						List<ImagenUsuarioBO> imagenes = gLaminas
								.listarLaminasUsuarioAdministrador(usuarioMaestroID);
						for (ImagenUsuarioBO imagenUsuarioBO : imagenes) {
							gLaminas.agregarLaminaUsuarioAdministrador(
									u.getIdentificador(), imagenUsuarioBO);
						}
						for (PruebaUsuarioBO pruebaUsuarioBO : pruebas) {
							userTransaction.begin();
							PruebaUsuario c = new PruebaUsuario();
							c.setUsuario(u);
							c.setDescripcion(pruebaUsuarioBO.getDescripcion());
							c.setNombre(pruebaUsuarioBO.getNombre());
							if (!entityManager.contains(c)) {
								entityManager.persist(c);
								entityManager.flush();
								userTransaction.commit();
							}
							entityManager.refresh(c);

							PruebaUsuario pru = entityManager.find(
									PruebaUsuario.class,
									pruebaUsuarioBO.getIdentificador());
							PruebaUsuarioBO pruBO = new PruebaUsuarioBO();
							pruBO.setIdentificador(c.getIdentificador());
							List<PreguntaUsuario> preguntas = pru
									.getPreguntasUsuarios();
							for (PreguntaUsuario im : preguntas) {
								PreguntaUsuarioBO resultado = new PreguntaUsuarioBO();
								resultado.setCaracteresMaximo(im
										.getCaracteresMaximo());
								resultado.setIdentificador(im
										.getIdentificador());
								resultado.setPregunta(im.getPregunta());
								resultado.setTiempoMaximo(im.getTiempoMaximo());
								resultado.setPosicionPreguntaX(im
										.getPosicionPreguntaX());
								resultado.setPosicionPreguntaY(im
										.getPosicionPreguntaY());
								resultado
										.setImagenesUsuarioID(gLaminas
												.consultarLaminaUsuarioAdministradorEnlace(
														u.getIdentificador(),
														im.getImagenesUsuario()
																.getImagene()
																.getImagenURI()));
								gPreguntas.agregarPreguntaUsuarioAdministrador(
										u.getIdentificador(), resultado, pruBO);

							}

						}
						System.out.println("Corregir usos al enviar correo.");
						UsoUsuario uso = new UsoUsuario();
						uso.setFechaAsignacion(new Date());
						uso.setUsosAsignados(usos.getUsosAsignados());
						uso.setUsosRedimidos(0);
						uso.setUsuario(u);
						userTransaction.begin();
						entityManager.persist(uso);
						entityManager.flush();
						userTransaction.commit();

						SMTPSender.enviarCorreoCreacionCuentaCliente(
								u.getNombres(), u.getApellidos(),
								u.getCorreo_Electronico(), contrasena,
								usos.getUsosAsignados());
						return Convencion.CORRECTO;
					} else {
						return Convencion.INCORRECTO;
					}
				} catch (Exception exception) {
					// Exception has occurred, roll-back the transaction.
					exception.printStackTrace();
					userTransaction.rollback();
					return Convencion.INCORRECTO;
				}
			}
		}
		return Convencion.INCORRECTO;

	}

	@Override
	public int editarUsuarioAdministrador(
			UsuarioAdministradorBO usuarioAdministrador) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario u = entityManager.find(Usuario.class,
					usuarioAdministrador.getIdentificador());
			u.setApellidos(usuarioAdministrador.getApellidos());
			u.setCorreo_Electronico(usuarioAdministrador
					.getCorreo_Electronico());
			u.setIdentificador(usuarioAdministrador.getIdentificador());
			u.setNombres(usuarioAdministrador.getNombres());
			u.setCargo(usuarioAdministrador.getCargo());
			u.setCiudad(usuarioAdministrador.getCiudad());
			u.setEmpresa(usuarioAdministrador.getEmpresa());
			u.setEstado_Cuenta(usuarioAdministrador.getEstado_Cuenta());
			u.setFecha_Creacion(usuarioAdministrador.getFecha_Creacion());
			u.setIdentificador(usuarioAdministrador.getIdentificador());
			u.setNombres(usuarioAdministrador.getNombres());
			u.setPais(usuarioAdministrador.getPais());
			u.setTelefono(usuarioAdministrador.getTelefono());
			u.setTelefono_Celular(usuarioAdministrador.getTelefono_Celular());
			u.setTipo(usuarioAdministrador.getTipo());
			entityManager.merge(u);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public UsuarioAdministradorBO consultarUsuarioAdministrador(
			int usuarioAdministradorID) {

		Usuario usuario = entityManager.find(Usuario.class,
				usuarioAdministradorID);
		if (usuario == null) {
			return null;
		} else {
			entityManager.refresh(usuario);
			UsuarioAdministradorBO resultado = new UsuarioAdministradorBO();
			resultado.setApellidos(usuario.getApellidos());
			resultado.setCorreo_Electronico(usuario.getCorreo_Electronico());
			resultado.setIdentificador(usuario.getIdentificador());
			resultado.setNombres(usuario.getNombres());
			resultado.setCargo(usuario.getCargo());
			resultado.setCiudad(usuario.getCiudad());
			resultado.setEmpresa(usuario.getEmpresa());
			resultado.setEstado_Cuenta(usuario.getEstado_Cuenta());
			resultado.setFecha_Creacion(usuario.getFecha_Creacion());
			resultado.setIdentificador(usuario.getIdentificador());
			resultado.setNombres(usuario.getNombres());
			resultado.setPais(usuario.getPais());
			resultado.setTelefono(usuario.getTelefono());
			resultado.setTelefono_Celular(usuario.getTelefono_Celular());
			resultado.setTipo(usuario.getTipo());

			return resultado;
		}
	}

	@Override
	public int eliminarUsuarioAdministrador(int usuarioAdministradorID) {
		Usuario usuario = entityManager.find(Usuario.class,
				usuarioAdministradorID);
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			List<UsoUsuario> usos = usuario.getUsosUsuarios();
			GestionUsos g = new GestionUsos();
			for (UsoUsuario usoUsuario : usos) {
				g.eliminarPermiso(usoUsuario.getIdentificador());
			}
			List<Usuario> usuarios = usuario.getUsuarios();
			GestionUsuariosProgramadores gp = new GestionUsuariosProgramadores();
			for (Usuario user : usuarios) {
				gp.eliminarUsuarioProgramador(usuario.getIdentificador(),
						user.getIdentificador());
			}
			List<Evaluado> evaluados = usuario.getEvaluados();
			GestionEvaluados geval = new GestionEvaluados();
			for (Evaluado evaluado : evaluados) {
				geval.eliminarUsuarioBasico(usuario.getIdentificador(),
						evaluado.getIdentificador());
			}
			List<ProcesoUsuario> procesos = usuario.getProcesosUsuarios();
			GestionProcesos gproc = new GestionProcesos();
			for (ProcesoUsuario procesoUsuario : procesos) {
				gproc.eliminarProcesoUsuarioAdministrador(
						usuario.getIdentificador(),
						procesoUsuario.getIdentificador());
			}
			List<PruebaUsuario> pruebas = usuario.getPruebasUsuarios();
			GestionPruebas gpru = new GestionPruebas();
			for (PruebaUsuario pruebaUsuario : pruebas) {
				gpru.eliminarPruebaUsuarioAdministrador(
						usuario.getIdentificador(),
						pruebaUsuario.getIdentificador());
			}
			entityManager.remove(usuario);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	public List<UsuarioAdministradorBO> listarClientesPorEmpresaParcial(
			String empresa) {
		String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.tipo =:tipoU AND u.empresa LIKE :empresa";
		Query q = entityManager.createQuery(query);
		q.setParameter("tipoU", Convencion.TIPO_USUARIO_ADMINISTRADOR);
		q.setParameter("empresa", "%" + empresa + "%");
		List<Usuario> usuarios = q.getResultList();
		// Valida que se encuentre un usuario.
		if (usuarios != null) {
			if (usuarios.size() > 0) {
				List<UsuarioAdministradorBO> lista = new ArrayList<UsuarioAdministradorBO>();
				for (int i = 0; i < usuarios.size(); i++) {
					UsuarioAdministradorBO resultado = new UsuarioAdministradorBO();
					Usuario usuario = usuarios.get(i);

					resultado.setApellidos(usuario.getApellidos());
					resultado.setCorreo_Electronico(usuario
							.getCorreo_Electronico());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setNombres(usuario.getNombres());
					resultado.setCargo(usuario.getCargo());
					resultado.setCiudad(usuario.getCiudad());
					resultado.setEmpresa(usuario.getEmpresa());
					resultado.setEstado_Cuenta(usuario.getEstado_Cuenta());
					resultado.setFecha_Creacion(usuario.getFecha_Creacion());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setNombres(usuario.getNombres());
					resultado.setPais(usuario.getPais());
					resultado.setTelefono(usuario.getTelefono());
					resultado
							.setTelefono_Celular(usuario.getTelefono_Celular());
					resultado.setTipo(usuario.getTipo());

					lista.add(resultado);
				}
				return lista;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public List<UsuarioAdministradorBO> listarClientesPorCedulaParcial(int id) {
		String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.tipo =:tipoU AND u.identificador =:empresa";
		Query q = entityManager.createQuery(query);
		q.setParameter("tipoU", Convencion.TIPO_USUARIO_ADMINISTRADOR);
		q.setParameter("empresa", id);
		List<Usuario> usuarios = q.getResultList();
		// Valida que se encuentre un usuario.
		if (usuarios != null) {
			if (usuarios.size() > 0) {
				List<UsuarioAdministradorBO> lista = new ArrayList<UsuarioAdministradorBO>();
				for (int i = 0; i < usuarios.size(); i++) {
					UsuarioAdministradorBO resultado = new UsuarioAdministradorBO();
					Usuario usuario = usuarios.get(i);

					resultado.setApellidos(usuario.getApellidos());
					resultado.setCorreo_Electronico(usuario
							.getCorreo_Electronico());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setNombres(usuario.getNombres());
					resultado.setCargo(usuario.getCargo());
					resultado.setCiudad(usuario.getCiudad());
					resultado.setEmpresa(usuario.getEmpresa());
					resultado.setEstado_Cuenta(usuario.getEstado_Cuenta());
					resultado.setFecha_Creacion(usuario.getFecha_Creacion());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setNombres(usuario.getNombres());
					resultado.setPais(usuario.getPais());
					resultado.setTelefono(usuario.getTelefono());
					resultado
							.setTelefono_Celular(usuario.getTelefono_Celular());
					resultado.setTipo(usuario.getTipo());

					lista.add(resultado);
				}
				return lista;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public List<UsuarioAdministradorBO> listarUsuariosAdministradores() {
		String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.tipo =:tipoU";
		Query q = entityManager.createQuery(query);
		q.setParameter("tipoU", Convencion.TIPO_USUARIO_ADMINISTRADOR);
		List<Usuario> usuarios = q.getResultList();
		// Valida que se encuentre un usuario.
		if (usuarios != null) {
			if (usuarios.size() > 0) {
				List<UsuarioAdministradorBO> lista = new ArrayList<UsuarioAdministradorBO>();
				for (int i = 0; i < usuarios.size(); i++) {
					UsuarioAdministradorBO resultado = new UsuarioAdministradorBO();
					Usuario usuario = usuarios.get(i);

					resultado.setApellidos(usuario.getApellidos());
					resultado.setCorreo_Electronico(usuario
							.getCorreo_Electronico());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setNombres(usuario.getNombres());
					resultado.setCargo(usuario.getCargo());
					resultado.setCiudad(usuario.getCiudad());
					resultado.setEmpresa(usuario.getEmpresa());
					resultado.setEstado_Cuenta(usuario.getEstado_Cuenta());
					resultado.setFecha_Creacion(usuario.getFecha_Creacion());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setNombres(usuario.getNombres());
					resultado.setPais(usuario.getPais());
					resultado.setTelefono(usuario.getTelefono());
					resultado
							.setTelefono_Celular(usuario.getTelefono_Celular());
					resultado.setTipo(usuario.getTipo());

					lista.add(resultado);
				}
				return lista;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public int cambiarContrasena(UsuarioBO usuarioMaestro, String pass) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario u = entityManager.find(Usuario.class,
					usuarioMaestro.getIdentificador());
			u.setContrasena(pass);
			entityManager.merge(u);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	public int cambiarEstadoCUenta(UsuarioAdministradorBO usuario,
			String estadoCuentaInactiva) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario u = entityManager.find(Usuario.class,
					usuario.getIdentificador());
			u.setEstado_Cuenta(estadoCuentaInactiva);
			entityManager.merge(u);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}
}
