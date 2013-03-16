package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.entidades.ProcesoUsuario;
import co.mind.management.shared.entidades.PruebaUsuario;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.NotificadorCorreo;

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
			List<PruebaUsuarioBO> pruebasUsuario = proceso
					.getPruebasUsuarioID();
			List<PruebaUsuario> pruebas = new ArrayList<>();
			for (PruebaUsuarioBO pruebaUsuarioBO : pruebasUsuario) {
				pruebas.add(entityManager.find(PruebaUsuario.class,
						pruebaUsuarioBO.getIdentificador()));
			}
			ProcesoUsuario proc = new ProcesoUsuario();
			proc.setDescripcion(proceso.getDescripcion());
			proc.setEstadoValoracion(proceso.getEstadoValoracion());
			proc.setFechaCreacion(proceso.getFechaCreacion());
			proc.setFechaFinalizacion(proceso.getFechaFinalizacion());
			proc.setFechaInicio(proceso.getFechaInicio());
			proc.setNombre(proceso.getNombre());
			proc.setPruebasUsuarios(pruebas);
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
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public int agregarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			ProcesoUsuarioBO proceso,
			List<ParticipacionEnProcesoBO> participaciones) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			List<PruebaUsuarioBO> pruebasUsuario = proceso
					.getPruebasUsuarioID();
			List<PruebaUsuario> pruebas = new ArrayList<>();
			for (PruebaUsuarioBO pruebaUsuarioBO : pruebasUsuario) {
				pruebas.add(entityManager.find(PruebaUsuario.class,
						pruebaUsuarioBO.getIdentificador()));
			}
			ProcesoUsuario proc = new ProcesoUsuario();
			proc.setDescripcion(proceso.getDescripcion());
			proc.setEstadoValoracion(proceso.getEstadoValoracion());
			proc.setFechaCreacion(proceso.getFechaCreacion());
			proc.setFechaFinalizacion(proceso.getFechaFinalizacion());
			proc.setFechaInicio(proceso.getFechaInicio());
			proc.setNombre(proceso.getNombre());
			proc.setPruebasUsuarios(pruebas);
			proc.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_NO_REALIZADA);
			if (!entityManager.contains(proc)) {
				entityManager.persist(proc);
				entityManager.flush();
				userTransaction.commit();
				entityManager.refresh(proc);
				for (int i = 0; i < participaciones.size(); i++) {
					gestionEvaluacion
							.agregarParticipacionEnProceso(
									usuarioAdministradorID, participaciones
											.get(i).getUsuarioBasicoID(), proc
											.getIdentificador(),
									participaciones.get(i));
					NotificadorCorreo
							.enviarCorreoParticipacionAProceso(participaciones
									.get(i));
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
	public int editarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			ProcesoUsuarioBO proceso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			ProcesoUsuario proc = entityManager.find(ProcesoUsuario.class,
					proceso.getIdentificador());
			List<PruebaUsuarioBO> pruebasUsuario = proceso
					.getPruebasUsuarioID();
			List<PruebaUsuario> pruebas = new ArrayList<>();
			for (PruebaUsuarioBO pruebaUsuarioBO : pruebasUsuario) {
				pruebas.add(entityManager.find(PruebaUsuario.class,
						pruebaUsuarioBO.getIdentificador()));
			}
			proc.setDescripcion(proceso.getDescripcion());
			proc.setEstadoValoracion(proceso.getEstadoValoracion());
			proc.setFechaCreacion(proceso.getFechaCreacion());
			proc.setFechaFinalizacion(proceso.getFechaFinalizacion());
			proc.setFechaInicio(proceso.getFechaInicio());
			proc.setNombre(proceso.getNombre());
			proc.setPruebasUsuarios(pruebas);
			proc.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_NO_REALIZADA);
			entityManager.merge(proc);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public ProcesoUsuarioBO consultarProcesoUsuarioAdministrador(
			int usuarioAdministradorID, int procesoID) {
		ProcesoUsuario im = entityManager.find(ProcesoUsuario.class, procesoID);
		if (im == null) {
			return null;
		} else {
			ProcesoUsuarioBO resultado = new ProcesoUsuarioBO();
			resultado.setDescripcion(im.getDescripcion());
			resultado.setEstadoValoracion(im.getEstadoValoracion());
			resultado.setFechaCreacion(im.getFechaCreacion());
			resultado.setFechaFinalizacion(im.getFechaFinalizacion());
			resultado.setFechaInicio(im.getFechaInicio());
			resultado.setIdentificador(im.getIdentificador());
			resultado.setNombre(im.getNombre());
			List<PruebaUsuario> pruebas = im.getPruebasUsuarios();
			List<PruebaUsuarioBO> pruebasUsuarioBOs = new ArrayList<>();
			for (PruebaUsuario pruebaUsuario : pruebas) {

				PruebaUsuarioBO prueba = new PruebaUsuarioBO();
				prueba.setDescripcion(pruebaUsuario.getDescripcion());
				prueba.setIdentificador(pruebaUsuario.getIdentificador());
				prueba.setNombre(pruebaUsuario.getNombre());
				prueba.setUsuarioAdministradorID(pruebaUsuario.getUsuario()
						.getIdentificador());
				pruebasUsuarioBOs.add(prueba);
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
			userTransaction.begin();
			entityManager.remove(im);
			entityManager.flush();
			userTransaction.commit();
			return Convencion.CORRECTO;
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
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
			String query = "SELECT DISTINCT (u) FROM ProcesoUsuario u, PruebaUsuario i WHERE i.usuario =:user AND u.pruebasUsuario = i";
			Query q = entityManager.createQuery(query);
			q.setParameter("user", user);
			List<ProcesoUsuario> usuarios = q.getResultList();
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
						List<PruebaUsuario> pruebas = im.getPruebasUsuarios();
						List<PruebaUsuarioBO> pruebasUsuarioBOs = new ArrayList<>();
						for (PruebaUsuario pruebaUsuario : pruebas) {

							PruebaUsuarioBO prueba = new PruebaUsuarioBO();
							prueba.setDescripcion(pruebaUsuario
									.getDescripcion());
							prueba.setIdentificador(pruebaUsuario
									.getIdentificador());
							prueba.setNombre(pruebaUsuario.getNombre());
							prueba.setUsuarioAdministradorID(pruebaUsuario
									.getUsuario().getIdentificador());
							pruebasUsuarioBOs.add(prueba);
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

}
