package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import co.mind.management.shared.bo.ImagenBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.ProcesoUsuarioHasPruebaUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.entidades.ImagenUsuario;
import co.mind.management.shared.entidades.PreguntaUsuario;
import co.mind.management.shared.entidades.ProcesoUsuario;
import co.mind.management.shared.entidades.ProcesoUsuarioHasPruebaUsuario;
import co.mind.management.shared.entidades.PruebaUsuario;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.recursos.Convencion;

public class GestionPruebas implements IGestionPruebas {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionPruebas() {
		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
	}

	@Override
	public int agregarPruebaUsuarioAdministrador(int usuarioAdministradorID,
			PruebaUsuarioBO prueba) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario usuario = entityManager.find(Usuario.class,
					usuarioAdministradorID);
			PruebaUsuario c = new PruebaUsuario();
			c.setUsuario(usuario);
			c.setDescripcion(prueba.getDescripcion());
			c.setNombre(prueba.getNombre());
			if (!entityManager.contains(c)) {
				entityManager.persist(c);
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
	public int agregarPruebaUsuarioAdministrador(int usuarioAdministradorID,
			PruebaUsuarioBO prueba, List<PreguntaUsuarioBO> preguntas) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario usuario = entityManager.find(Usuario.class,
					usuarioAdministradorID);
			PruebaUsuario c = new PruebaUsuario();
			c.setUsuario(usuario);
			c.setDescripcion(prueba.getDescripcion());
			c.setNombre(prueba.getNombre());
			if (!entityManager.contains(c)) {
				entityManager.persist(c);
				entityManager.flush();
				userTransaction.commit();
				entityManager.refresh(c);
				for (PreguntaUsuarioBO preguntaBO : preguntas) {
					PreguntaUsuario pregunta = new PreguntaUsuario();
					pregunta.setCaracteresMaximo(preguntaBO
							.getCaracteresMaximo());
					ImagenUsuario imagen = entityManager.find(
							ImagenUsuario.class, preguntaBO
									.getImagenesUsuario().getIdentificador());
					pregunta.setImagenesUsuario(imagen);
					pregunta.setPosicionPreguntaX(preguntaBO
							.getPosicionPreguntaX());
					pregunta.setPosicionPreguntaY(preguntaBO
							.getPosicionPreguntaY());
					pregunta.setPregunta(preguntaBO.getPregunta());
					pregunta.setPruebasUsuario(c);
					pregunta.setTiempoMaximo(preguntaBO.getTiempoMaximo());
					if (!entityManager.contains(pregunta)) {
						entityManager.persist(pregunta);
						entityManager.flush();
						userTransaction.commit();
					}
				}
				return Convencion.CORRECTO;
			} else {
				return Convencion.INCORRECTO;
			}
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public int editarPruebaUsuarioAdministrador(int usuarioAdministradorID,
			PruebaUsuarioBO prueba) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Usuario usuario = entityManager.find(Usuario.class,
					usuarioAdministradorID);
			PruebaUsuario c = entityManager.find(PruebaUsuario.class,
					prueba.getIdentificador());
			c.setUsuario(usuario);
			c.setDescripcion(prueba.getDescripcion());
			c.setNombre(prueba.getNombre());
			entityManager.merge(c);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception e) {
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public PruebaUsuarioBO consultarPruebaUsuarioAdministrador(
			int usuarioAdministradorID, int pruebaID) {
		PruebaUsuario prueba = entityManager
				.find(PruebaUsuario.class, pruebaID);
		if (prueba == null) {
			return null;
		} else {
			PruebaUsuarioBO resultado = new PruebaUsuarioBO();
			resultado.setDescripcion(prueba.getDescripcion());
			resultado.setIdentificador(prueba.getIdentificador());
			resultado.setNombre(prueba.getNombre());
			resultado.setUsuarioAdministradorID(usuarioAdministradorID);
			List<PreguntaUsuario> preguntas = prueba.getPreguntasUsuarios();
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
			resultado.setPreguntas(preguntasBO);
			return resultado;
		}
	}

	@Override
	public int eliminarPruebaUsuarioAdministrador(int usuarioAdministradorID,
			int pruebaID) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			PruebaUsuario cat = entityManager.find(PruebaUsuario.class,
					pruebaID);
			entityManager.remove(cat);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception e) {
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public List<PruebaUsuarioBO> listarPruebasUsuarioAdministrador(
			int usuarioAdministradorID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {
			entityManager.refresh(user);
			List<PruebaUsuarioBO> lista = new ArrayList<PruebaUsuarioBO>();
			for (int i = 0; i < user.getPruebasUsuarios().size(); i++) {
				PruebaUsuario prueba = user.getPruebasUsuarios().get(i);
				PruebaUsuarioBO resultado = new PruebaUsuarioBO();
				resultado.setDescripcion(prueba.getDescripcion());
				resultado.setIdentificador(prueba.getIdentificador());
				resultado.setNombre(prueba.getNombre());
				resultado.setUsuarioAdministradorID(usuarioAdministradorID);
				List<PreguntaUsuario> preguntas = prueba.getPreguntasUsuarios();
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
				resultado.setPreguntas(preguntasBO);
				lista.add(resultado);
			}
			return lista;
		} else {
			return null;
		}
	}

	@Override
	public int eliminarPreguntaDePruebaUsuarioAdministrador(
			int usuarioAdministradorID, int preguntaCategoriaEnPruebaID) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			PreguntaUsuario p = entityManager.find(PreguntaUsuario.class,
					preguntaCategoriaEnPruebaID);
			entityManager.remove(p);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception e) {
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public List<PreguntaUsuarioBO> listarPreguntasPorProceso(
			int usuarioAdministradorID, int procesoID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {
			ProcesoUsuario proceso = entityManager.find(ProcesoUsuario.class,
					procesoID);
			List<ProcesoUsuarioHasPruebaUsuario> procesoUsuarioHasPruebaUsuario = proceso
					.getProcesosUsuariosHasPruebasUsuarios();
			List<PruebaUsuario> pruebasProceso = new ArrayList<PruebaUsuario>();
			for (ProcesoUsuarioHasPruebaUsuario procesoUsuarioHasPruebaUsuario2 : procesoUsuarioHasPruebaUsuario) {
				pruebasProceso.add(procesoUsuarioHasPruebaUsuario2
						.getPruebasUsuario());
			}
			List<PreguntaUsuario> preguntas = new ArrayList<>();
			for (PruebaUsuario pruebaUsuario : pruebasProceso) {
				preguntas.addAll(pruebaUsuario.getPreguntasUsuarios());
			}
			// Valida que se encuentre un usuario.
			if (preguntas != null) {
				if (preguntas.size() > 0) {
					List<PreguntaUsuarioBO> lista = new ArrayList<PreguntaUsuarioBO>();
					for (int i = 0; i < preguntas.size(); i++) {
						PreguntaUsuario pregunta = preguntas.get(i);

						PruebaUsuarioBO prueba = new PruebaUsuarioBO();
						prueba.setDescripcion(pregunta.getPruebasUsuario()
								.getDescripcion());
						prueba.setIdentificador(pregunta.getPruebasUsuario()
								.getIdentificador());
						prueba.setNombre(pregunta.getPruebasUsuario()
								.getNombre());
						prueba.setUsuarioAdministradorID(pregunta
								.getPruebasUsuario().getUsuario()
								.getIdentificador());

						PreguntaUsuarioBO preguntaUsuario = new PreguntaUsuarioBO();
						preguntaUsuario.setCaracteresMaximo(pregunta
								.getCaracteresMaximo());
						preguntaUsuario.setIdentificador(pregunta
								.getIdentificador());
						preguntaUsuario.setPosicionPreguntaX(pregunta
								.getPosicionPreguntaX());
						preguntaUsuario.setPosicionPreguntaY(pregunta
								.getPosicionPreguntaY());
						preguntaUsuario.setPregunta(pregunta.getPregunta());
						preguntaUsuario.setTiempoMaximo(pregunta
								.getTiempoMaximo());
						ImagenUsuarioBO imagen = new ImagenUsuarioBO();
						imagen.setIdentificador(pregunta.getImagenesUsuario()
								.getIdentificador());
						ImagenBO img = new ImagenBO();
						img.setIdentificador(pregunta.getImagenesUsuario()
								.getImagene().getIdentificador());
						img.setImagenURI(pregunta.getImagenesUsuario()
								.getImagene().getImagenURI());
						imagen.setImagene(img);
						imagen.setUsuario(usuarioAdministradorID);
						preguntaUsuario.setImagenesUsuarioID(imagen);
						preguntaUsuario.setPruebaUsuario(prueba);
						lista.add(preguntaUsuario);
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

	@Override
	public List<PreguntaUsuarioBO> listarPreguntasRestantesPrueba(
			int usuarioAdministradorID, int procesoID, int usuarioBasicoID) {
		return null;
	}

	@Override
	public List<PreguntaUsuarioBO> listarPreguntasPrueba(
			int usuarioAdministradorID, int pruebaID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		entityManager.refresh(user);
		if (user != null) {
			PruebaUsuario prueba = entityManager.find(PruebaUsuario.class,
					pruebaID);
			entityManager.refresh(prueba);
			List<PreguntaUsuario> preguntas = prueba.getPreguntasUsuarios();
			// Valida que se encuentre un usuario.
			if (preguntas != null) {
				if (preguntas.size() > 0) {
					List<PreguntaUsuarioBO> lista = new ArrayList<PreguntaUsuarioBO>();
					for (int i = 0; i < preguntas.size(); i++) {
						PreguntaUsuario pregunta = preguntas.get(i);

						PruebaUsuarioBO p = new PruebaUsuarioBO();
						p.setDescripcion(pregunta.getPruebasUsuario()
								.getDescripcion());
						p.setIdentificador(pregunta.getPruebasUsuario()
								.getIdentificador());
						p.setNombre(pregunta.getPruebasUsuario().getNombre());
						p.setUsuarioAdministradorID(pregunta
								.getPruebasUsuario().getUsuario()
								.getIdentificador());

						PreguntaUsuarioBO preguntaUsuario = new PreguntaUsuarioBO();
						preguntaUsuario.setCaracteresMaximo(pregunta
								.getCaracteresMaximo());
						preguntaUsuario.setIdentificador(pregunta
								.getIdentificador());
						preguntaUsuario.setPosicionPreguntaX(pregunta
								.getPosicionPreguntaX());
						preguntaUsuario.setPosicionPreguntaY(pregunta
								.getPosicionPreguntaY());
						preguntaUsuario.setPregunta(pregunta.getPregunta());
						preguntaUsuario.setTiempoMaximo(pregunta
								.getTiempoMaximo());
						ImagenUsuarioBO imagen = new ImagenUsuarioBO();
						imagen.setIdentificador(pregunta.getImagenesUsuario()
								.getIdentificador());
						ImagenBO img = new ImagenBO();
						img.setIdentificador(pregunta.getImagenesUsuario()
								.getImagene().getIdentificador());
						img.setImagenURI(pregunta.getImagenesUsuario()
								.getImagene().getImagenURI());
						imagen.setImagene(img);
						imagen.setUsuario(usuarioAdministradorID);
						preguntaUsuario.setImagenesUsuarioID(imagen);
						preguntaUsuario.setPruebaUsuario(p);
						lista.add(preguntaUsuario);
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

	@Override
	public int agregarPruebaAProceso(int usuarioAdministradorID,
			PruebaUsuarioBO prueba, ProcesoUsuarioBO proceso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			PruebaUsuario c = entityManager.find(PruebaUsuario.class,
					prueba.getIdentificador());
			ProcesoUsuario proc = entityManager.find(ProcesoUsuario.class,
					proceso.getIdentificador());
			if (c == null) {
				c = new PruebaUsuario();
				Usuario usuario = entityManager.find(Usuario.class,
						usuarioAdministradorID);
				c.setUsuario(usuario);
				c.setDescripcion(prueba.getDescripcion());
				c.setNombre(prueba.getNombre());
				if (!entityManager.contains(c)) {
					entityManager.persist(c);
					entityManager.flush();
					userTransaction.commit();
				}
			}
			entityManager.refresh(c);
			return agregarAsociacionProcesoPrueba(c, proc);
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	private int agregarAsociacionProcesoPrueba(PruebaUsuario prueba,
			ProcesoUsuario proceso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ProcesoUsuarioHasPruebaUsuario proHas = new ProcesoUsuarioHasPruebaUsuario();

			proHas.setProcesosUsuario(proceso);
			proHas.setPruebasUsuario(prueba);
			if (!entityManager.contains(proHas)) {
				entityManager.persist(proHas);
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

}
