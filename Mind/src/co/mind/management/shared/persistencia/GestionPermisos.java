package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.dto.PermisoBO;
import co.mind.management.shared.entidades.Permiso;
import co.mind.management.shared.recursos.Convencion;

public class GestionPermisos implements IGestionPermisos {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionPermisos() {
		crearEntityManager();
	}

	private void crearEntityManager() {

		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
	}

	@Override
	public int agregarPermiso(PermisoBO permiso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			userTransaction.begin();
			Permiso u = new Permiso();
			u.setCreacion(permiso.getCreacion());
			u.setEdicion(permiso.getEdicion());
			u.setNombre(permiso.getNombre());

			if (!entityManager.contains(u)) {
				entityManager.persist(u);
				entityManager.flush();
				userTransaction.commit();
				entityManager.refresh(u);
				return Convencion.CORRECTO;
			} else {
				return Convencion.INCORRECTO;
			}
		} catch (Exception exception) {
			// Exception has occurred, roll-back the transaction.
			exception.printStackTrace();
			userTransaction.rollback();
			return 1;
		}
	}

	@Override
	public int editarPermiso(PermisoBO permiso) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {

			Permiso u = entityManager.find(Permiso.class,
					permiso.getIdentificador());
			u.setCreacion(permiso.getCreacion());
			u.setEdicion(permiso.getEdicion());
			u.setNombre(permiso.getNombre());
			entityManager.merge(u);
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
	public PermisoBO consultarUsuarioBasico(int permisoID) {
		Permiso usuario = entityManager.find(Permiso.class, permisoID);

		if (usuario == null) {
			return null;
		} else {
			entityManager.refresh(usuario);
			PermisoBO resultado = new PermisoBO();
			resultado.setNombre(usuario.getNombre());
			resultado.setIdentificador(usuario.getIdentificador());
			resultado.setCreacion(usuario.getCreacion());
			resultado.setEdicion(usuario.getEdicion());
			return resultado;
		}
	}

	@Override
	public int eliminarPermiso(int permisoID) {
		EntityTransaction userTransaction = entityManager.getTransaction();
		try {
			Permiso usuario = entityManager.find(Permiso.class, permisoID);
			if (usuario != null) {

				userTransaction.begin();
				entityManager.remove(usuario);
				entityManager.flush();
				userTransaction.commit();
				return Convencion.CORRECTO;
			} else {
				return Convencion.INCORRECTO;
			}
		} catch (Exception e) {
			userTransaction.rollback();
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public List<PermisoBO> listarPermisos() {
		String query = "SELECT DISTINCT(u) FROM Permiso u";
		Query q = entityManager.createQuery(query);
		List<Permiso> usuarios = q.getResultList();
		// Valida que se encuentre un usuario.
		if (usuarios != null) {
			if (usuarios.size() > 0) {
				List<PermisoBO> lista = new ArrayList<PermisoBO>();
				for (int i = 0; i < usuarios.size(); i++) {
					PermisoBO resultado = new PermisoBO();
					Permiso usuario = usuarios.get(i);

					resultado.setNombre(usuario.getNombre());
					resultado.setIdentificador(usuario.getIdentificador());
					resultado.setCreacion(usuario.getCreacion());
					resultado.setEdicion(usuario.getEdicion());
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

}
