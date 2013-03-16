package co.mind.management.shared.persistencia;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.UsuarioAdministradorBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;
import co.mind.management.shared.bo.UsuarioProgramadorBO;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.Generador;

import co.mind.management.shared.entidades.ParticipacionEnProceso;

import co.mind.management.shared.entidades.Usuario;

public class GestionAccesos implements IGestionAccesos {

	public EntityManagerFactory emf = null;
	public EntityManager entityManager = null;
	public static final String JTA_PU_NAME = Convencion.JTA_PU_NAME;

	public GestionAccesos() {
		emf = Persistence.createEntityManagerFactory(JTA_PU_NAME);
		entityManager = emf.createEntityManager();
	}

	@Override
	public int verificarUsuarioBasico(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion) {
		String query = "SELECT DISTINCT(p) FROM UsuarioBasico u, ParticipacionEnProceso p WHERE p.usuariosBasico = u AND u.correoElectronico =:correo AND p.codigo_Acceso =:codigo";
		Query qs = entityManager.createQuery(query);
		qs.setParameter("correo", usuarioBasico.getCorreoElectronico());
		qs.setParameter("codigo", participacion.getCodigo_Acceso());
		List<ParticipacionEnProceso> participaciones = qs.getResultList();
		if (participaciones.size() > 0) {
			ParticipacionEnProceso par = participaciones.get(0);
			entityManager.refresh(par);
			Date fechaInicio = par.getProcesosUsuario().getFechaInicio();
			Date fechaFinal = par.getProcesosUsuario().getFechaFinalizacion();
			Date fechaActual = new Date();
			if (fechaFinal != null) {
				if (fechaActual.compareTo(fechaInicio) <= 0
						|| fechaActual.compareTo(fechaFinal) >= 0) {
					return Convencion.VERIFICACION_USUARIO_BASICO_PARTICIPACION_NO_EXISTE;
				}
			} else if (fechaActual.compareTo(fechaInicio) <= 0) {
				return Convencion.VERIFICACION_USUARIO_BASICO_PARTICIPACION_NO_EXISTE;
			}

			if (par.getEstado().equalsIgnoreCase(
					Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_ESPERA)) {
				return Convencion.VERIFICACION_USUARIO_BASICO_CORRECTA;
			} else if (par.getEstado().equalsIgnoreCase(
					Convencion.ESTADO_PARTICIPACION_EN_PROCESO_INACTIVA)) {
				return Convencion.VERIFICACION_USUARIO_BASICO_CODIGO_ACCESO_NO_VALIDO;
			} else if (par.getEstado().equalsIgnoreCase(
					Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_EJECUCION)) {
				return Convencion.VERIFICACION_USUARIO_BASICO_SIN_TERMINAR_PRUEBA;
			} else {
				return Convencion.VERIFICACION_USUARIO_BASICO_PARTICIPACION_NO_EXISTE;
			}
		} else {
			return Convencion.VERIFICACION_USUARIO_BASICO_PARTICIPACION_NO_EXISTE;
		}

	}

	@Override
	public UsuarioBO verificarTipoUsuario(String correoUsuario,
			String contrasenaUsuario) {

		String query = "SELECT DISTINCT(u) FROM Usuario u WHERE u.correo_Electronico =:correo AND u.contrasena =:pass";
		Query q = entityManager.createQuery(query);
		q.setParameter("correo", correoUsuario);
		q.setParameter("pass", Generador.convertirStringmd5(contrasenaUsuario));
		List<Usuario> usuarios = q.getResultList();
		// Valida que se encuentre un usuario.
		if (usuarios.size() > 0) {
			Usuario u = usuarios.get(0);
			// Valida que la cuenta se encuentre activa
			if (validarCuentaPorEstado(u)) {
				UsuarioBO resultado;
				if (u.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
					resultado = new UsuarioAdministradorBO();
				} else if (u.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_MAESTRO)) {
					resultado = new UsuarioMaestroBO();
				} else if (u.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
					resultado = new UsuarioMaestroBO();
				} else if (u.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_PROGRAMADOR)) {
					resultado = new UsuarioProgramadorBO();
				} else {
					resultado = new UsuarioBO();
				}
				resultado.setIdentificador(u.getIdentificador());
				resultado.setApellidos(u.getApellidos());
				resultado.setCargo(u.getCargo());
				resultado.setCiudad(u.getCiudad());
				resultado.setCorreo_Electronico(u.getCorreo_Electronico());
				resultado.setEmpresa(u.getEmpresa());
				resultado.setEstado_Cuenta(u.getEstado_Cuenta());
				resultado.setNombres(u.getNombres());
				resultado.setPais(u.getPais());
				resultado.setTelefono(u.getTelefono());
				resultado.setTelefono_Celular(u.getTelefono_Celular());
				resultado.setTipo(u.getTipo());

				return resultado;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	private boolean validarCuentaPorEstado(Usuario u) {
		if (u.getEstado_Cuenta().equalsIgnoreCase(
				Convencion.ESTADO_CUENTA_ACTIVA)) {
			return true;
		} else {
			return false;
		}
	}

}
