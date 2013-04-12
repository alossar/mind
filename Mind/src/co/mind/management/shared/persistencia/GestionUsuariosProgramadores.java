package co.mind.management.shared.persistencia;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.shared.dto.UsuarioProgramadorBO;
import co.mind.management.shared.entidades.Usuario;
import co.mind.management.shared.entidades.Evaluado;
import co.mind.management.shared.recursos.Convencion;

public class GestionUsuariosProgramadores implements
		IGestionUsuariosProgramadores {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionUsuariosProgramadores() {
		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
	}

	@Override
	public int agregarUsuarioProgramador(int usuarioAdministradorID,
			UsuarioProgramadorBO usuarioProgramador) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int editarUsuarioProgramador(int usuarioAdministradorID,
			UsuarioProgramadorBO usuarioProgramador) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UsuarioProgramadorBO consultarUsuarioProgramador(
			int usuarioAdministradorID, int usuarioProgramadorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int eliminarUsuarioProgramador(int usuarioAdministradorID,
			int usuarioProgramadorID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UsuarioProgramadorBO> listarUsuariosProgramadores(
			int usuarioAdministradorID) {
		Usuario user = entityManager
				.find(Usuario.class, usuarioAdministradorID);
		if (user != null) {

			String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.tipo =:tipoU AND u.usuario =:user";
			Query q = entityManager.createQuery(query);
			q.setParameter("tipoU", Convencion.TIPO_USUARIO_PROGRAMADOR);
			q.setParameter("user", user);
			List<Evaluado> usuarios = q.getResultList();
			// Valida que se encuentre un usuario.
			if (usuarios != null) {
				if (usuarios.size() > 0) {
					List<UsuarioProgramadorBO> lista = new ArrayList<UsuarioProgramadorBO>();
					for (int i = 0; i < usuarios.size(); i++) {
						UsuarioProgramadorBO resultado = new UsuarioProgramadorBO();
						resultado.setApellidos(lista.get(i).getApellidos());
						resultado.setCorreo_Electronico(lista.get(i)
								.getCorreo_Electronico());
						resultado.setIdentificador(lista.get(i)
								.getIdentificador());
						resultado.setNombres(lista.get(i).getNombres());
						resultado.setCargo(lista.get(i).getCargo());
						resultado.setCiudad(lista.get(i).getCiudad());
						resultado.setDireccion_Empresa(lista.get(i)
								.getDireccion_Empresa());
						resultado.setDireccion_Residencia(lista.get(i)
								.getDireccion_Residencia());
						resultado.setEmpresa(lista.get(i).getEmpresa());
						resultado.setEstado_Cuenta(lista.get(i)
								.getEstado_Cuenta());
						resultado.setFecha_Creacion(lista.get(i)
								.getFecha_Creacion());
						resultado.setIdentificador(lista.get(i)
								.getIdentificador());
						resultado.setNombres(lista.get(i).getNombres());
						resultado.setPais(lista.get(i).getPais());
						resultado.setTelefono(lista.get(i).getTelefono());
						resultado.setTelefono_Celular(lista.get(i)
								.getTelefono_Celular());
						resultado.setTipo(lista.get(i).getTipo());
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
