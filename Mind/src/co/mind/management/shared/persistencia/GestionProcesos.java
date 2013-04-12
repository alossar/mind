package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.dto.ImagenBO;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioHasPruebaUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.entidades.ParticipacionEnProceso;
import co.mind.management.shared.entidades.PreguntaUsuario;
import co.mind.management.shared.entidades.ProcesoUsuario;
import co.mind.management.shared.entidades.ProcesoUsuarioHasPruebaUsuario;
import co.mind.management.shared.entidades.PruebaUsuario;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.recursos.Convencion;

public class GestionProcesos implements IGestionProcesos {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;
	private GestionEvaluacion gestionEvaluacion;

	public GestionProcesos() {
		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
		gestionEvaluacion = new GestionEvaluacion();
	}

	@Override
	public int agregarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			ProcesoUsuarioBO proceso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ProcesoUsuario proc = new ProcesoUsuario();
			proc.setDescripcion(proceso.getDescripcion());
			proc.setEstadoValoracion(proceso.getEstadoValoracion());
			proc.setFechaCreacion(proceso.getFechaCreacion());
			proc.setFechaFinalizacion(proceso.getFechaFinalizacion());
			proc.setFechaInicio(proceso.getFechaInicio());
			proc.setNombre(proceso.getNombre());
			Usuario user = entityManager.find(Usuario.class,
					usuarioAdministradorID);
			proc.setUsuario(user);
			System.out.println("Agregando");
			if (!entityManager.contains(proc)) {
				entityManager.persist(proc);
				entityManager.flush();
				userTransaction.commit();
				entityManager.refresh(proc);
				return Convencion.CORRECTO;
			} else {
				return Convencion.INCORRECTO;
			}
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			userTransaction.rollback();
			exception.printStackTrace();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public int editarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			ProcesoUsuarioBO proceso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ProcesoUsuario proc = entityManager.find(ProcesoUsuario.class,
					proceso.getIdentificador());
			proc.setDescripcion(proceso.getDescripcion());
			proc.setEstadoValoracion(proceso.getEstadoValoracion());
			proc.setFechaCreacion(proceso.getFechaCreacion());
			proc.setFechaFinalizacion(proceso.getFechaFinalizacion());
			proc.setFechaInicio(proceso.getFechaInicio());
			proc.setNombre(proceso.getNombre());
			proc.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_NO_REALIZADA);
			entityManager.merge(proc);
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
	public ProcesoUsuarioBO consultarProcesoUsuarioAdministrador(
			int usuarioAdministradorID, int procesoID) {
		ProcesoUsuario proceso = entityManager.find(ProcesoUsuario.class,
				procesoID);
		if (proceso == null) {
			return null;
		} else {
			entityManager.refresh(proceso);
			ProcesoUsuarioBO resultado = new ProcesoUsuarioBO();
			resultado.setDescripcion(proceso.getDescripcion());
			resultado.setEstadoValoracion(proceso.getEstadoValoracion());
			resultado.setFechaCreacion(proceso.getFechaCreacion());
			resultado.setFechaFinalizacion(proceso.getFechaFinalizacion());
			resultado.setFechaInicio(proceso.getFechaInicio());
			resultado.setIdentificador(proceso.getIdentificador());
			resultado.setNombre(proceso.getNombre());
			List<ProcesoUsuarioHasPruebaUsuario> pruebas = proceso
					.getProcesosUsuariosHasPruebasUsuarios();
			List<ProcesoUsuarioHasPruebaUsuarioBO> pruebasUsuarioBOs = new ArrayList<>();
			for (ProcesoUsuarioHasPruebaUsuario pruebaUsuario : pruebas) {
				ProcesoUsuarioHasPruebaUsuarioBO pHas = new ProcesoUsuarioHasPruebaUsuarioBO();
				pHas.setIdentificador(pruebaUsuario.getIdentificador());
				PruebaUsuarioBO prueba = new PruebaUsuarioBO();
				prueba.setDescripcion(pruebaUsuario.getPruebasUsuario()
						.getDescripcion());
				prueba.setIdentificador(pruebaUsuario.getPruebasUsuario()
						.getIdentificador());
				prueba.setNombre(pruebaUsuario.getPruebasUsuario().getNombre());
				prueba.setUsuarioAdministradorID(pruebaUsuario
						.getPruebasUsuario().getUsuario().getIdentificador());
				List<PreguntaUsuario> preguntas = pruebaUsuario
						.getPruebasUsuario().getPreguntasUsuarios();
				List<PreguntaUsuarioBO> preguntasBO = new ArrayList<>();
				for (PreguntaUsuario pre : preguntas) {
					PreguntaUsuarioBO pregunta = new PreguntaUsuarioBO();
					pregunta.setCaracteresMaximo(pre.getCaracteresMaximo());
					pregunta.setIdentificador(pre.getIdentificador());
					pregunta.setPregunta(pre.getPregunta());
					pregunta.setTiempoMaximo(pre.getTiempoMaximo());
					ImagenUsuarioBO imagen = new ImagenUsuarioBO();
					imagen.setIdentificador(pre.getImagenesUsuario()
							.getIdentificador());
					imagen.setUsuario(pre.getImagenesUsuario().getUsuario()
							.getIdentificador());
					ImagenBO ima = new ImagenBO();
					ima.setIdentificador(pre.getImagenesUsuario().getImagene()
							.getIdentificador());
					ima.setImagenURI(pre.getImagenesUsuario().getImagene()
							.getImagenURI());
					imagen.setImagene(ima);
					pregunta.setImagenesUsuarioID(imagen);
					preguntasBO.add(pregunta);
				}
				prueba.setPreguntas(preguntasBO);
				pHas.setPruebasUsuario(prueba);
				pruebasUsuarioBOs.add(pHas);
			}
			resultado.setPruebasUsuarioID(pruebasUsuarioBOs);
			return resultado;
		}
	}

	@Override
	public int eliminarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			int procesoID) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			ProcesoUsuario im = entityManager.find(ProcesoUsuario.class,
					procesoID);
			if (im != null) {
				List<ProcesoUsuarioHasPruebaUsuario> p = im
						.getProcesosUsuariosHasPruebasUsuarios();
				List<ParticipacionEnProceso> participaciones = im
						.getParticipacionEnProcesos();
				if (p != null) {
					for (ProcesoUsuarioHasPruebaUsuario procesoUsuarioHasPruebaUsuario : p) {
						userTransaction.begin();
						entityManager.remove(procesoUsuarioHasPruebaUsuario);
						entityManager.flush();
						userTransaction.commit();
					}
				}
				if (participaciones != null) {
					for (ParticipacionEnProceso participacionEnProceso : participaciones) {
						userTransaction.begin();
						entityManager.remove(participacionEnProceso);
						entityManager.flush();
						userTransaction.commit();
					}
				}
				userTransaction.begin();
				entityManager.remove(im);
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

	@Override
	public List<ProcesoUsuarioBO> listarProcesoUsuarioAdministrador(
			int usuarioAdministradorID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {
			entityManager.refresh(user);
			List<ProcesoUsuario> usuarios = user.getProcesosUsuarios();
			// Valida que se encuentre un usuario.
			if (usuarios != null) {
				if (usuarios.size() > 0) {
					List<ProcesoUsuarioBO> lista = new ArrayList<ProcesoUsuarioBO>();
					for (int i = 0; i < usuarios.size(); i++) {
						ProcesoUsuario im = usuarios.get(i);
						ProcesoUsuarioBO resultado = new ProcesoUsuarioBO();
						resultado.setDescripcion(im.getDescripcion());
						resultado.setEstadoValoracion(im.getEstadoValoracion());
						resultado.setFechaCreacion(im.getFechaCreacion());
						resultado.setFechaFinalizacion(im
								.getFechaFinalizacion());
						resultado.setFechaInicio(im.getFechaInicio());
						resultado.setIdentificador(im.getIdentificador());
						resultado.setNombre(im.getNombre());
						List<ProcesoUsuarioHasPruebaUsuario> pruebas = im
								.getProcesosUsuariosHasPruebasUsuarios();
						List<ProcesoUsuarioHasPruebaUsuarioBO> pruebasUsuarioBOs = new ArrayList<>();
						for (ProcesoUsuarioHasPruebaUsuario pruebaUsuario : pruebas) {
							ProcesoUsuarioHasPruebaUsuarioBO pHas = new ProcesoUsuarioHasPruebaUsuarioBO();
							pHas.setIdentificador(pruebaUsuario
									.getIdentificador());
							PruebaUsuarioBO prueba = new PruebaUsuarioBO();
							prueba.setDescripcion(pruebaUsuario
									.getPruebasUsuario().getDescripcion());
							prueba.setIdentificador(pruebaUsuario
									.getPruebasUsuario().getIdentificador());
							prueba.setNombre(pruebaUsuario.getPruebasUsuario()
									.getNombre());
							prueba.setUsuarioAdministradorID(pruebaUsuario
									.getPruebasUsuario().getUsuario()
									.getIdentificador());
							List<PreguntaUsuario> preguntas = pruebaUsuario
									.getPruebasUsuario().getPreguntasUsuarios();
							List<PreguntaUsuarioBO> preguntasBO = new ArrayList<>();
							for (PreguntaUsuario pre : preguntas) {
								PreguntaUsuarioBO pregunta = new PreguntaUsuarioBO();
								pregunta.setCaracteresMaximo(pre
										.getCaracteresMaximo());
								pregunta.setIdentificador(pre
										.getIdentificador());
								pregunta.setPregunta(pre.getPregunta());
								pregunta.setTiempoMaximo(pre.getTiempoMaximo());
								ImagenUsuarioBO imagen = new ImagenUsuarioBO();
								imagen.setIdentificador(pre
										.getImagenesUsuario()
										.getIdentificador());
								imagen.setUsuario(pre.getImagenesUsuario()
										.getUsuario().getIdentificador());
								ImagenBO ima = new ImagenBO();
								ima.setIdentificador(pre.getImagenesUsuario()
										.getImagene().getIdentificador());
								ima.setImagenURI(pre.getImagenesUsuario()
										.getImagene().getImagenURI());
								imagen.setImagene(ima);
								pregunta.setImagenesUsuarioID(imagen);
								preguntasBO.add(pregunta);

							}
							prueba.setPreguntas(preguntasBO);
							pHas.setPruebasUsuario(prueba);
							pruebasUsuarioBOs.add(pHas);
						}
						resultado.setPruebasUsuarioID(pruebasUsuarioBOs);
						lista.add(resultado);
					}
					return lista;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public int agregarProcesoConPruebas(
			int identificador,
			ProcesoUsuarioBO proceso,
			List<ProcesoUsuarioHasPruebaUsuarioBO> procesoUsuarioHasPruebaUsuario) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ProcesoUsuario proc = new ProcesoUsuario();
			proc.setDescripcion(proceso.getDescripcion());
			proc.setEstadoValoracion(proceso.getEstadoValoracion());
			proc.setFechaCreacion(new Date());
			proc.setFechaFinalizacion(proceso.getFechaFinalizacion());
			proc.setFechaInicio(proceso.getFechaInicio());
			proc.setNombre(proceso.getNombre());
			Usuario user = entityManager.find(Usuario.class, identificador);
			proc.setUsuario(user);
			System.out.println("Agregando");
			if (!entityManager.contains(proc)) {
				entityManager.persist(proc);
				entityManager.flush();
				userTransaction.commit();
				entityManager.refresh(proc);
				proceso.setIdentificador(proc.getIdentificador());
				GestionPruebas gPruebas = new GestionPruebas();
				for (ProcesoUsuarioHasPruebaUsuarioBO procesoUsuarioHasPruebaUsuarioBO : procesoUsuarioHasPruebaUsuario) {
					gPruebas.agregarPruebaAProceso(identificador,
							procesoUsuarioHasPruebaUsuarioBO
									.getPruebasUsuario(), proceso);
				}
				return Convencion.CORRECTO;
			} else {
				return Convencion.INCORRECTO;
			}
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			userTransaction.rollback();
			exception.printStackTrace();
			return Convencion.INCORRECTO;
		}
	}

}
