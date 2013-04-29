package co.mind.management.evaluacion.server;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.ResultadoBO;
import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.evaluacion.client.servicios.EvaluacionService;
import co.mind.management.shared.persistencia.GestionAccesos;
import co.mind.management.shared.persistencia.GestionEvaluacion;
import co.mind.management.shared.persistencia.GestionPruebas;
import co.mind.management.shared.persistencia.GestionClientes;
import co.mind.management.shared.persistencia.GestionUsos;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class EvaluacionServiceImpl extends RemoteServiceServlet implements
		EvaluacionService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GestionAccesos g = new GestionAccesos();
	private GestionEvaluacion gestionEvaluacion = new GestionEvaluacion();
	private GestionPruebas gestionPruebas = new GestionPruebas();
	private GestionClientes gestionUsuariosAdministradores = new GestionClientes();
	private GestionUsos gestionUsos = new GestionUsos();

	@Override
	public int comenzarPrueba(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion) {

		int resultado = g.verificarUsuarioBasico(usuarioBasico, participacion);
		if (resultado == Convencion.VERIFICACION_USUARIO_BASICO_CORRECTA) {
			participacion
					.setEstado(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_EJECUCION);
			gestionEvaluacion.editarParticipacionEnProceso(
					usuarioBasico.getIdentificadorUsuarioAdministrador(),
					usuarioBasico.getIdentificador(),
					participacion.getProcesoID(), participacion);
			gestionEvaluacion.decrementarCantidadDeUsosUsuarios(
					participacion.getIdentificador(),
					participacion.getProcesoID());

			return resultado;

		} else {
			return resultado;
		}
	}

	@Override
	public int guardarResultado(EvaluadoBO usuarioBasico, ResultadoBO resultado) {
		int r = gestionEvaluacion.agregarResultadoParticipacionEnProceso(
				usuarioBasico.getIdentificadorUsuarioAdministrador(),
				usuarioBasico.getIdentificador(), resultado
						.getParticipacionEnProceso().getProcesoID(), resultado
						.getParticipacionEnProceso().getIdentificador(),
				resultado);
		return r;
	}

	@Override
	public List<PreguntaUsuarioBO> preguntasPrueba(ProcesoUsuarioBO proceso,
			EvaluadoBO usuarioBasico) {
		return gestionPruebas.listarPreguntasPorProceso(
				usuarioBasico.getIdentificadorUsuarioAdministrador(),
				proceso.getIdentificador());

	}

	@Override
	public int terminarPrueba(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion) {

		participacion
				.setEstado(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_INACTIVA);
		return gestionEvaluacion.editarParticipacionEnProceso(
				usuarioBasico.getIdentificadorUsuarioAdministrador(),
				usuarioBasico.getIdentificador(), participacion.getProcesoID(),
				participacion);

	}

	@Override
	public int validarUsuarioBasico(String cedula, String correo,
			String codigoAcceso) {
		EvaluadoBO u = new EvaluadoBO();
		u.setCorreoElectronico(correo);
		u.setCedula(Integer.parseInt(cedula));
		ParticipacionEnProcesoBO p = new ParticipacionEnProcesoBO();
		p.setCodigo_Acceso(codigoAcceso);
		return (Integer) g.verificarUsuarioBasico(u, p);
	}

	@Override
	public ParticipacionEnProcesoBO obtenerParticipacionEnProceso(
			int identificador, String correo, String codigoAcceso) {
		Object res = gestionEvaluacion.consultarParticipacionAProceso(
				identificador, correo, codigoAcceso);
		if (res != null) {
			((ParticipacionEnProcesoBO) res).setSesionID(this
					.getThreadLocalRequest().getSession().getId());
			guardarParticipacionEnSesion((ParticipacionEnProcesoBO) res);
		}
		return (ParticipacionEnProcesoBO) res;
	}

	@Override
	public String obtenerNombreDeLaEmpresa(int usuarioAdministradorID) {
		Object objeto = gestionUsuariosAdministradores
				.consultarUsuarioAdministrador(usuarioAdministradorID);
		if (objeto != null) {
			return (String) ((UsuarioAdministradorBO) objeto).getEmpresa();
		}
		return null;
	}

	@Override
	public ParticipacionEnProcesoBO validarSesion(String sesion) {
		// TODO Auto-generated method stub
		Object user = obtenerParticipacionDeSesion();
		if (user != null) {
			return (ParticipacionEnProcesoBO) user;
		} else {
			return null;
		}
	}

	private ParticipacionEnProcesoBO obtenerParticipacionDeSesion() {
		ParticipacionEnProcesoBO user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute(Convencion.CLAVE_EVALUACION);
		if (userObj != null && userObj instanceof ParticipacionEnProcesoBO) {
			user = (ParticipacionEnProcesoBO) userObj;
		}
		return user;
	}

	@Override
	public void cerrarSesion() {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute(Convencion.CLAVE_EVALUACION);
	}

	private void guardarParticipacionEnSesion(ParticipacionEnProcesoBO user) {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute(Convencion.CLAVE_EVALUACION, user);
	}

	@Override
	public ParticipacionEnProcesoBO obtenerParticipacionEnProcesoDesdeServidor() {
		ParticipacionEnProcesoBO user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute(Convencion.CLAVE_EVALUACION);

		if (userObj != null && userObj instanceof ParticipacionEnProcesoBO) {
			user = (ParticipacionEnProcesoBO) userObj;
			return user;
		}
		return user;
	}
}
