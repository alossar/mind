package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.bo.UsuarioAdministradorBO;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.Generador;

public class GestionUsuariosAdministradores implements
		IGestionUsuariosAdministradores {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionUsuariosAdministradores() {
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
			u.setContrasena(Generador.convertirStringmd5(usuarioAdministrador
					.getContrasena()));
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
			u.setContrasena(Generador.convertirStringmd5(usuarioAdministrador
					.getContrasena()));
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

	@Override
	public List<UsuarioAdministradorBO> listarUsuariosAdministradores() {
		String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.tipo =: tipoU";
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
}
