package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.bo.ImagenBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ResultadoBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.entidades.ParticipacionEnProceso;
import co.mind.management.shared.entidades.PreguntaUsuario;
import co.mind.management.shared.entidades.ProcesoUsuario;
import co.mind.management.shared.entidades.Resultado;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.entidades.Evaluado;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.Generador;

public class GestionEvaluacion implements IGestionEvaluacion {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionEvaluacion() {
		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
	}

	@Override
	public int agregarParticipacionEnProceso(int usuarioAdministradorBO,
			int usuarioBasicoID, int procesoID,
			ParticipacionEnProcesoBO participacion) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			Evaluado evaluado = entityManager.find(Evaluado.class,
					usuarioBasicoID);
			if (evaluado == null) {
				userTransaction.begin();
				evaluado = new Evaluado();
				Usuario usuario = entityManager.find(Usuario.class,
						usuarioAdministradorBO);
				evaluado.setApellidos(participacion.getUsuarioBasico()
						.getApellidos());
				evaluado.setCorreoElectronico(participacion.getUsuarioBasico()
						.getCorreoElectronico());
				evaluado.setIdentificador(participacion.getUsuarioBasico()
						.getIdentificador());
				evaluado.setNombres(participacion.getUsuarioBasico()
						.getNombres());
				evaluado.setUsuario(usuario);
				entityManager.persist(evaluado);
				entityManager.flush();
				userTransaction.commit();
				entityManager.refresh(evaluado);
			}
			userTransaction.begin();
			ProcesoUsuario proceso = entityManager.find(ProcesoUsuario.class,
					procesoID);
			ParticipacionEnProceso par = new ParticipacionEnProceso();
			par.setCodigo_Acceso(generarCodigoAcceso());
			par.setEstado(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_ESPERA);
			par.setFechaFinalizacion(participacion.getFechaFinalizacion());
			par.setFechaInicio(participacion.getFechaInicio());
			par.setProcesosUsuario(proceso);
			par.setEvaluado(evaluado);
			if (!entityManager.contains(par)) {
				entityManager.persist(par);
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
	public int editarParticipacionEnProceso(int usuarioAdministradorBO,
			int usuarioBasicoID, int procesoID,
			ParticipacionEnProcesoBO participacion) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ParticipacionEnProceso par = entityManager.find(
					ParticipacionEnProceso.class,
					participacion.getIdentificador());
			par.setEstado(participacion.getEstado());
			if (participacion.getFechaFinalizacion() != null) {
				par.setFechaFinalizacion(participacion.getFechaFinalizacion());
			}
			if (participacion.getFechaInicio() != null) {
				par.setFechaInicio(participacion.getFechaInicio());
			}
			entityManager.merge(par);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception e) {
			e.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public int eliminarParticipacionEnProceso(int usuarioAdministradorBO,
			int usuarioBasicoID, int procesoID, int participacionID) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			ParticipacionEnProceso par = entityManager.find(
					ParticipacionEnProceso.class, participacionID);
			entityManager.remove(par);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception e) {
			e.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public ParticipacionEnProcesoBO consultarParticipacionAProceso(
			int usuarioAdministradorBO, int usuarioBasicoID, int procesoID,
			int participacionID) {
		ParticipacionEnProceso par = entityManager.find(
				ParticipacionEnProceso.class, participacionID);
		if (par == null) {
			return null;
		} else {
			ParticipacionEnProcesoBO resultado = new ParticipacionEnProcesoBO();
			resultado.setCodigo_Acceso(par.getCodigo_Acceso());
			resultado.setEstado(par.getEstado());
			resultado.setFechaFinalizacion(par.getFechaFinalizacion());
			resultado.setFechaInicio(par.getFechaInicio());
			resultado.setIdentificador(par.getIdentificador());
			resultado.setProcesoID(par.getProcesosUsuario().getIdentificador());
			EvaluadoBO user = new EvaluadoBO();
			user.setApellidos(par.getEvaluado().getApellidos());
			user.setCorreoElectronico(par.getEvaluado().getCorreoElectronico());
			user.setEdad(par.getEvaluado().getEdad());
			user.setIdentificador(par.getEvaluado().getIdentificador());
			user.setIdentificadorUsuarioAdministrador(usuarioAdministradorBO);
			user.setNombres(par.getEvaluado().getNombres());
			resultado.setUsuarioBasico(user);
			return resultado;
		}
	}

	@Override
	public List<ParticipacionEnProcesoBO> listarParticipacionesEnProceso(
			int usuarioAdministradorBO, int procesoID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorBO);
		if (user != null) {
			ProcesoUsuario proceso = entityManager.find(ProcesoUsuario.class,
					procesoID);
			String query = "SELECT DISTINCT(u) FROM ParticipacionEnProceso u WHERE u.procesosUsuario =:proc";
			Query q = entityManager.createQuery(query);
			q.setParameter("proc", proceso);
			List<ParticipacionEnProceso> usuarios = q.getResultList();
			// Valida que se encuentre un usuario.
			if (usuarios != null) {
				if (usuarios.size() > 0) {
					List<ParticipacionEnProcesoBO> lista = new ArrayList<ParticipacionEnProcesoBO>();
					for (int i = 0; i < usuarios.size(); i++) {
						ParticipacionEnProceso par = usuarios.get(i);
						ParticipacionEnProcesoBO resultado = new ParticipacionEnProcesoBO();
						resultado.setCodigo_Acceso(par.getCodigo_Acceso());
						resultado.setEstado(par.getEstado());
						resultado.setFechaFinalizacion(par
								.getFechaFinalizacion());
						resultado.setFechaInicio(par.getFechaInicio());
						resultado.setIdentificador(par.getIdentificador());
						resultado.setProcesoID(par.getProcesosUsuario()
								.getIdentificador());
						EvaluadoBO userb = new EvaluadoBO();
						userb.setApellidos(par.getEvaluado().getApellidos());
						userb.setCorreoElectronico(par.getEvaluado()
								.getCorreoElectronico());
						userb.setEdad(par.getEvaluado().getEdad());
						userb.setIdentificador(par.getEvaluado()
								.getIdentificador());
						userb.setIdentificadorUsuarioAdministrador(usuarioAdministradorBO);
						userb.setNombres(par.getEvaluado().getNombres());
						resultado.setUsuarioBasico(userb);
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

	private String generarCodigoAcceso() {
		return Generador.GenerarCodigo(Generador.CARACTERES, 8);
	}

	@Override
	public int eliminarCodigoAcceso(int participacionID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarResultadoParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID, ResultadoBO resultado) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ParticipacionEnProceso par = entityManager.find(
					ParticipacionEnProceso.class, participacionID);
			PreguntaUsuario pre = entityManager.find(PreguntaUsuario.class,
					resultado.getPreguntasUsuario().getIdentificador());
			Resultado res = new Resultado();
			res.setRespuesta(resultado.getRespuesta());
			res.setPreguntasUsuario(pre);
			res.setParticipacionEnProceso(par);
			if (!entityManager.contains(res)) {
				entityManager.persist(res);
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
	public int eliminarResultadoParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID, int resultadoID) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			Resultado par = entityManager.find(Resultado.class, resultadoID);
			entityManager.remove(par);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception e) {
			e.printStackTrace();
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public ResultadoBO consultarResultadoParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID, int resultadoID) {
		Resultado res = entityManager.find(Resultado.class, resultadoID);
		if (res == null) {
			return null;
		} else {
			ResultadoBO resultado = new ResultadoBO();
			resultado.setIdentificador(res.getIdentificador());
			resultado.setRespuesta(res.getRespuesta());
			ParticipacionEnProcesoBO par = new ParticipacionEnProcesoBO();
			par.setCodigo_Acceso(res.getParticipacionEnProceso()
					.getCodigo_Acceso());
			par.setEstado(res.getParticipacionEnProceso().getEstado());
			par.setFechaFinalizacion(res.getParticipacionEnProceso()
					.getFechaFinalizacion());
			par.setFechaInicio(res.getParticipacionEnProceso().getFechaInicio());
			par.setIdentificador(res.getParticipacionEnProceso()
					.getIdentificador());
			par.setProcesoID(res.getParticipacionEnProceso()
					.getProcesosUsuario().getIdentificador());
			resultado.setParticipacionEnProceso(par);

			PreguntaUsuarioBO pregunta = new PreguntaUsuarioBO();
			pregunta.setCaracteresMaximo(res.getPreguntasUsuario()
					.getCaracteresMaximo());
			pregunta.setIdentificador(res.getPreguntasUsuario()
					.getIdentificador());
			ImagenUsuarioBO imagen = new ImagenUsuarioBO();
			imagen.setIdentificador(res.getPreguntasUsuario()
					.getImagenesUsuario().getIdentificador());
			ImagenBO img = new ImagenBO();
			img.setIdentificador(res.getPreguntasUsuario().getImagenesUsuario()
					.getImagene().getIdentificador());
			img.setImagenURI(res.getPreguntasUsuario().getImagenesUsuario()
					.getImagene().getImagenURI());
			imagen.setImagene(img);
			imagen.setUsuario(usuarioAdministradorID);
			pregunta.setImagenesUsuarioID(imagen);
			pregunta.setPregunta(res.getPreguntasUsuario().getPregunta());
			pregunta.setTiempoMaximo(res.getPreguntasUsuario()
					.getTiempoMaximo());
			resultado.setPreguntasUsuario(pregunta);
			return resultado;
		}
	}

	@Override
	public List<ResultadoBO> listarResultadosParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {
			ParticipacionEnProceso parti = entityManager.find(
					ParticipacionEnProceso.class, participacionID);
			String query = "SELECT DISTINCT(u) FROM Resultado u WHERE u.participacionEnProceso =: participacion";
			Query q = entityManager.createQuery(query);
			q.setParameter("participacion", parti);
			List<Resultado> usuarios = q.getResultList();
			// Valida que se encuentre un usuario.
			if (usuarios != null) {
				if (usuarios.size() > 0) {
					List<ResultadoBO> lista = new ArrayList<ResultadoBO>();
					for (int i = 0; i < usuarios.size(); i++) {
						Resultado res = usuarios.get(i);
						ResultadoBO resultado = new ResultadoBO();
						resultado.setIdentificador(res.getIdentificador());
						resultado.setRespuesta(res.getRespuesta());
						ParticipacionEnProcesoBO par = new ParticipacionEnProcesoBO();
						par.setCodigo_Acceso(res.getParticipacionEnProceso()
								.getCodigo_Acceso());
						par.setEstado(res.getParticipacionEnProceso()
								.getEstado());
						par.setFechaFinalizacion(res
								.getParticipacionEnProceso()
								.getFechaFinalizacion());
						par.setFechaInicio(res.getParticipacionEnProceso()
								.getFechaInicio());
						par.setIdentificador(res.getParticipacionEnProceso()
								.getIdentificador());
						par.setProcesoID(res.getParticipacionEnProceso()
								.getProcesosUsuario().getIdentificador());
						resultado.setParticipacionEnProceso(par);

						PreguntaUsuarioBO pregunta = new PreguntaUsuarioBO();
						pregunta.setCaracteresMaximo(res.getPreguntasUsuario()
								.getCaracteresMaximo());
						pregunta.setIdentificador(res.getPreguntasUsuario()
								.getIdentificador());
						ImagenUsuarioBO imagen = new ImagenUsuarioBO();
						imagen.setIdentificador(res.getPreguntasUsuario()
								.getImagenesUsuario().getIdentificador());
						ImagenBO img = new ImagenBO();
						img.setIdentificador(res.getPreguntasUsuario()
								.getImagenesUsuario().getImagene()
								.getIdentificador());
						img.setImagenURI(res.getPreguntasUsuario()
								.getImagenesUsuario().getImagene()
								.getImagenURI());
						imagen.setImagene(img);
						imagen.setUsuario(usuarioAdministradorID);
						pregunta.setImagenesUsuarioID(imagen);
						pregunta.setPregunta(res.getPreguntasUsuario()
								.getPregunta());
						pregunta.setTiempoMaximo(res.getPreguntasUsuario()
								.getTiempoMaximo());
						resultado.setPreguntasUsuario(pregunta);
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

	@Override
	public List<ResultadoBO> listarResultadosProceso(
			int usuarioAdministradorID, int procesoID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {
			ProcesoUsuario parti = entityManager.find(ProcesoUsuario.class,
					procesoID);
			String query = "SELECT DISTINCT(u) FROM Resultado u, ParticipacionEnProceso p WHERE p.procesosUsuario =: proceso u.participacionEnProceso = p";
			Query q = entityManager.createQuery(query);
			q.setParameter("proceso", parti);
			List<Resultado> usuarios = q.getResultList();
			// Valida que se encuentre un usuario.
			if (usuarios != null) {
				if (usuarios.size() > 0) {
					List<ResultadoBO> lista = new ArrayList<ResultadoBO>();
					for (int i = 0; i < usuarios.size(); i++) {
						Resultado res = usuarios.get(i);
						ResultadoBO resultado = new ResultadoBO();
						resultado.setIdentificador(res.getIdentificador());
						resultado.setRespuesta(res.getRespuesta());
						ParticipacionEnProcesoBO par = new ParticipacionEnProcesoBO();
						par.setCodigo_Acceso(res.getParticipacionEnProceso()
								.getCodigo_Acceso());
						par.setEstado(res.getParticipacionEnProceso()
								.getEstado());
						par.setFechaFinalizacion(res
								.getParticipacionEnProceso()
								.getFechaFinalizacion());
						par.setFechaInicio(res.getParticipacionEnProceso()
								.getFechaInicio());
						par.setIdentificador(res.getParticipacionEnProceso()
								.getIdentificador());
						par.setProcesoID(res.getParticipacionEnProceso()
								.getProcesosUsuario().getIdentificador());
						resultado.setParticipacionEnProceso(par);

						PreguntaUsuarioBO pregunta = new PreguntaUsuarioBO();
						pregunta.setCaracteresMaximo(res.getPreguntasUsuario()
								.getCaracteresMaximo());
						pregunta.setIdentificador(res.getPreguntasUsuario()
								.getIdentificador());
						ImagenUsuarioBO imagen = new ImagenUsuarioBO();
						imagen.setIdentificador(res.getPreguntasUsuario()
								.getImagenesUsuario().getIdentificador());
						ImagenBO img = new ImagenBO();
						img.setIdentificador(res.getPreguntasUsuario()
								.getImagenesUsuario().getImagene()
								.getIdentificador());
						img.setImagenURI(res.getPreguntasUsuario()
								.getImagenesUsuario().getImagene()
								.getImagenURI());
						imagen.setImagene(img);
						imagen.setUsuario(usuarioAdministradorID);
						pregunta.setImagenesUsuarioID(imagen);
						pregunta.setPregunta(res.getPreguntasUsuario()
								.getPregunta());
						pregunta.setTiempoMaximo(res.getPreguntasUsuario()
								.getTiempoMaximo());
						resultado.setPreguntasUsuario(pregunta);
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

	@Override
	public ParticipacionEnProcesoBO consultarParticipacionAProceso(
			int usuarioBasicoID, String correo, String codigoAcceso) {
		String query = "SELECT DISTINCT(p) FROM Evaluado u, ParticipacionEnProceso p WHERE p.evaluado = u AND u.correoElectronico =:correo AND p.codigo_Acceso =:codigo AND u.identificador =:id";
		Query qs = entityManager.createQuery(query);
		qs.setParameter("correo", correo);
		qs.setParameter("codigo", codigoAcceso);
		qs.setParameter("id", usuarioBasicoID);
		List<ParticipacionEnProceso> participaciones = qs.getResultList();
		if (participaciones.size() > 0) {
			ParticipacionEnProceso par = participaciones.get(0);
			ParticipacionEnProcesoBO resultado = new ParticipacionEnProcesoBO();
			resultado.setCodigo_Acceso(par.getCodigo_Acceso());
			resultado.setEstado(par.getEstado());
			resultado.setFechaFinalizacion(par.getFechaFinalizacion());
			resultado.setFechaInicio(par.getFechaInicio());
			resultado.setIdentificador(par.getIdentificador());
			resultado.setProcesoID(par.getProcesosUsuario().getIdentificador());
			EvaluadoBO user = new EvaluadoBO();
			user.setApellidos(par.getEvaluado().getApellidos());
			user.setCorreoElectronico(par.getEvaluado().getCorreoElectronico());
			user.setEdad(par.getEvaluado().getEdad());
			user.setIdentificador(par.getEvaluado().getIdentificador());
			user.setIdentificadorUsuarioAdministrador(par.getEvaluado()
					.getUsuario().getIdentificador());
			user.setNombres(par.getEvaluado().getNombres());
			resultado.setUsuarioBasico(user);

			return resultado;

		} else {
			return null;
		}

	}

	@Override
	public List<ParticipacionEnProcesoBO> listarParticipacionesEnProcesoCompletas(
			int usuarioAdministradorID, int procesoID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {
			ProcesoUsuario proceso = entityManager.find(ProcesoUsuario.class,
					procesoID);
			String query = "SELECT DISTINCT(u) FROM ParticipacionEnProceso u WHERE u.procesosUsuario =:proc AND u.estado =:est";
			Query q = entityManager.createQuery(query);
			q.setParameter("proc", proceso);
			q.setParameter("est",
					Convencion.ESTADO_PARTICIPACION_EN_PROCESO_INACTIVA);
			List<ParticipacionEnProceso> usuarios = q.getResultList();
			// Valida que se encuentre un usuario.
			if (usuarios != null) {
				if (usuarios.size() > 0) {
					List<ParticipacionEnProcesoBO> lista = new ArrayList<ParticipacionEnProcesoBO>();
					for (int i = 0; i < usuarios.size(); i++) {
						ParticipacionEnProceso par = usuarios.get(i);
						ParticipacionEnProcesoBO resultado = new ParticipacionEnProcesoBO();
						resultado.setCodigo_Acceso(par.getCodigo_Acceso());
						resultado.setEstado(par.getEstado());
						resultado.setFechaFinalizacion(par
								.getFechaFinalizacion());
						resultado.setFechaInicio(par.getFechaInicio());
						resultado.setIdentificador(par.getIdentificador());
						resultado.setProcesoID(par.getProcesosUsuario()
								.getIdentificador());
						EvaluadoBO userb = new EvaluadoBO();
						userb.setApellidos(par.getEvaluado().getApellidos());
						userb.setCorreoElectronico(par.getEvaluado()
								.getCorreoElectronico());
						userb.setEdad(par.getEvaluado().getEdad());
						userb.setIdentificador(par.getEvaluado()
								.getIdentificador());
						userb.setIdentificadorUsuarioAdministrador(usuarioAdministradorID);
						userb.setNombres(par.getEvaluado().getNombres());
						resultado.setUsuarioBasico(userb);
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

}
