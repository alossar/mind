package co.mind.management.maestro.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import co.mind.management.maestro.client.servicios.UsuarioMaestroService;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.ImagenBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.PermisoBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.bo.SolicitudCambioPlanBO;
import co.mind.management.shared.bo.SolicitudEliminacionCuentaBO;
import co.mind.management.shared.bo.SolicitudIncrementoUsosBO;
import co.mind.management.shared.bo.SolicitudPlanBO;
import co.mind.management.shared.bo.SolicitudValoracionBO;
import co.mind.management.shared.bo.UsuarioAdministradorBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;
import co.mind.management.shared.persistencia.GestionEvaluacion;
import co.mind.management.shared.persistencia.GestionLaminas;
import co.mind.management.shared.persistencia.GestionPreguntas;
import co.mind.management.shared.persistencia.GestionProcesos;
import co.mind.management.shared.persistencia.GestionPruebas;
import co.mind.management.shared.persistencia.GestionUsuariosAdministradores;
import co.mind.management.shared.persistencia.GestionUsuariosBasicos;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.NotificadorCorreo;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UsuarioMaestroServiceImpl extends RemoteServiceServlet implements
		UsuarioMaestroService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GestionUsuariosAdministradores gestionUsuariosAdministradores = new GestionUsuariosAdministradores();
	private GestionLaminas gestionLaminas = new GestionLaminas();
	private GestionPreguntas gestionPreguntas = new GestionPreguntas();
	private GestionPruebas gestionPruebas = new GestionPruebas();
	private GestionUsuariosBasicos gestionUsuariosBasicos;
	private GestionProcesos gestionProcesos;
	private GestionEvaluacion gestionEvaluacion;

	public UsuarioMaestroServiceImpl() {
		gestionUsuariosAdministradores = new GestionUsuariosAdministradores();
		gestionLaminas = new GestionLaminas();
		gestionPreguntas = new GestionPreguntas();
		gestionPruebas = new GestionPruebas();
		gestionUsuariosBasicos = new GestionUsuariosBasicos();
		gestionProcesos = new GestionProcesos();
		gestionEvaluacion = new GestionEvaluacion();
	}

	@Override
	public UsuarioBO validarSesion(String sesion) {
		Object user = obtenerUsuarioDeSesion();
		if (user != null) {
			return (UsuarioBO) user;
		} else {
			return null;
		}
	}

	private UsuarioBO obtenerUsuarioDeSesion() {
		UsuarioBO user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute(Convencion.CLAVE_USUARIO);
		if (userObj != null && userObj instanceof UsuarioBO) {
			user = (UsuarioBO) userObj;
		}
		return user;
	}

	@Override
	public void cerrarSesion() {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute(Convencion.CLAVE_USUARIO);
	}

	@Override
	public int crearCuenta(UsuarioBO usuarioAdministrador,
			SolicitudPlanBO solicitud, PermisoBO plan, Date fechaFinalizacion,
			int usosTotal) {

		return 1;
	}

	@Override
	public int activarCuenta(int usuarioAdministradorID) {
		UsuarioBO usuario = gestionUsuariosAdministradores
				.consultarUsuarioAdministrador(usuarioAdministradorID);
		if (usuario != null) {
			if (usuario.getEstado_Cuenta().equalsIgnoreCase(
					Convencion.ESTADO_CUENTA_INACTIVA)) {
				usuario.setEstado_Cuenta(Convencion.ESTADO_CUENTA_ACTIVA);
				int resultado = gestionUsuariosAdministradores
						.editarUsuarioAdministrador((UsuarioAdministradorBO) usuario);
				return resultado;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}

	@Override
	public int desactivarCuenta(int usuarioAdministradorID) {
		UsuarioBO usuario = gestionUsuariosAdministradores
				.consultarUsuarioAdministrador(usuarioAdministradorID);
		if (usuario != null) {
			if (usuario.getEstado_Cuenta().equalsIgnoreCase(
					Convencion.ESTADO_CUENTA_ACTIVA)) {
				usuario.setEstado_Cuenta(Convencion.ESTADO_CUENTA_INACTIVA);
				int resultado = gestionUsuariosAdministradores
						.editarUsuarioAdministrador((UsuarioAdministradorBO) usuario);
				return resultado;
			} else {
				return 1;
			}
		} else {
			return 1;
		}
	}

	@Override
	public int eliminarCuenta(int usuarioAdministradorID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int eliminarCuenta(int usuarioAdministradorID,
			SolicitudEliminacionCuentaBO solicitud) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarImagenPredeterminada(ImagenBO imagen) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarImagenPredeterminadaAUsuarios(
			List<UsuarioBO> usuariosAdministradores, ImagenBO imagen) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int agregarPlan(PermisoBO plan) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SolicitudCambioPlanBO> consultarSolicitudesCambioDePlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SolicitudEliminacionCuentaBO> consultarSolicitudesCambioDeCuenta() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SolicitudIncrementoUsosBO> consultarSolicitudesIncrementoDeUsos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SolicitudValoracionBO> consultarSolicitudesValoracionProceso() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SolicitudPlanBO> consultarSolicitudesPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UsuarioBO consultarUsuarioAdministrador(int usuarioAdministradorID) {
		Object result = gestionUsuariosAdministradores
				.consultarUsuarioAdministrador(usuarioAdministradorID);
		if (result != null) {
			return (UsuarioBO) result;
		}
		return null;
	}

	@Override
	public List<PreguntaUsuarioBO> consultarPreguntasUsuarioAdministrador(
			int usuarioAdministradorID) {
		Object result = gestionPreguntas
				.listarPreguntasUsuarioAdministrador(usuarioAdministradorID);
		if (result != null) {
			return (List<PreguntaUsuarioBO>) result;
		}
		return null;
	}

	@Override
	public List<PruebaUsuarioBO> consultarPruebasUsuarioAdministrador(
			int usuarioAdministradorID) {
		Object result = gestionPruebas
				.listarPruebasUsuarioAdministrador(usuarioAdministradorID);
		if (result != null) {
			return (List<PruebaUsuarioBO>) result;
		}
		return null;
	}

	@Override
	public List<ProcesoUsuarioBO> consultarProcesosUsuarioAdministrador(
			int usuarioAdministradorID) {
		Object result = gestionProcesos
				.listarProcesoUsuarioAdministrador(usuarioAdministradorID);
		if (result != null) {
			return (List<ProcesoUsuarioBO>) result;
		}
		return null;
	}

	@Override
	public List<EvaluadoBO> consultarUsuariosBasicosUsuarioAdministrador(
			int usuarioAdministradorID) {
		Object result = gestionUsuariosBasicos
				.listarUsuariosBasicos(usuarioAdministradorID);
		if (result != null) {

			return (List<EvaluadoBO>) result;
		}
		return null;
	}

	@Override
	public List<PermisoBO> consultarPlanesUsuarioAdministrador(
			int usuarioAdministradorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuarioBO> consultarUsuariosAdministradores() {
		Object lista = gestionUsuariosAdministradores
				.listarUsuariosAdministradores();
		if (lista != null) {
			return (List<UsuarioBO>) lista;
		} else {
			return null;
		}
	}

	@Override
	public List<ImagenUsuarioBO> consultarImagenesUsuario(int usuariID) {
		Object lista = gestionLaminas
				.listarLaminasUsuarioAdministrador(usuariID);
		if (lista != null) {
			return (List<ImagenUsuarioBO>) lista;
		} else {
			return null;
		}
	}

	@Override
	public int agregarParticipacionDeUsuarioBasicoAProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso,
			List<ParticipacionEnProcesoBO> evaluados) {
		for (ParticipacionEnProcesoBO participacionEnProcesoBO : evaluados) {
			gestionEvaluacion.agregarParticipacionEnProceso(
					usuarioAdministrador.getIdentificador(),
					participacionEnProcesoBO.getUsuarioBasico()
							.getIdentificador(), proceso.getIdentificador(),
					participacionEnProcesoBO);
		}
		return 0;
	}

	@Override
	public int agregarPruebasAProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, List<PruebaUsuarioBO> pruebas) {
		for (PruebaUsuarioBO pruebaUsuarioBO : pruebas) {
			gestionPruebas.agregarPruebaAProceso(
					usuarioAdministrador.getIdentificador(), pruebaUsuarioBO,
					proceso);
		}
		return 0;
	}

	@Override
	public int agregarUsuarioBasico(UsuarioBO usuarioAdministrador,
			EvaluadoBO usuarioBasico) {
		return (Integer) gestionUsuariosBasicos.agregarUsuarioBasico(
				usuarioAdministrador.getIdentificador(), usuarioBasico);

	}

	@Override
	public int agregarPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba) {
		return gestionPruebas.agregarPruebaUsuarioAdministrador(
				usuarioAdministrador.getIdentificador(), prueba);

	}

	@Override
	public int agregarPreguntaAPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba, PreguntaUsuarioBO pregunta) {
		return gestionPreguntas.agregarPreguntaUsuarioAdministrador(
				usuarioAdministrador.getIdentificador(), pregunta, prueba);
	}

	@Override
	public int agregarPreguntasAPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba, List<PreguntaUsuarioBO> preguntas) {

		return 0;
	}

	@Override
	public List<ParticipacionEnProcesoBO> consultarParticipacionEnProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso) {
		Object result = gestionEvaluacion.listarParticipacionesEnProceso(
				usuarioAdministrador.getIdentificador(),
				proceso.getIdentificador());
		if (result != null) {
			return (List<ParticipacionEnProcesoBO>) result;
		}
		return null;
	}

	@Override
	public List<ParticipacionEnProcesoBO> consultarValoracionesProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso) {
		Object result = gestionEvaluacion.listarParticipacionesEnProceso(
				usuarioAdministrador.getIdentificador(),
				proceso.getIdentificador());
		if (result != null) {
			return (List<ParticipacionEnProcesoBO>) result;
		}
		return null;
	}

	@Override
	public List<ParticipacionEnProcesoBO> consultarResultadosProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso) {
		Object result = gestionEvaluacion
				.listarParticipacionesEnProcesoCompletas(
						usuarioAdministrador.getIdentificador(),
						proceso.getIdentificador());
		if (result != null) {
			return (List<ParticipacionEnProcesoBO>) result;
		}
		return null;
	}

	@Override
	public int eliminarUsuariosBasicos(UsuarioBO usuarioMaestro,
			List<EvaluadoBO> bo) {
		try {
			for (EvaluadoBO usuarioBasicoBO : bo) {
				gestionUsuariosBasicos.eliminarUsuarioBasico(
						usuarioMaestro.getIdentificador(),
						usuarioBasicoBO.getIdentificador());
			}
			return Convencion.CORRECTO;
		} catch (Exception e) {
			return Convencion.INCORRECTO;
		}
	}

	@Override
	public List<PreguntaUsuarioBO> consultarPreguntasPorCategoriaUsuarioAdministrador(
			int usuarioId) {
		Object result = gestionPreguntas
				.listarPreguntasUsuarioAdministrador(usuarioId);
		if (result != null) {
			return (List<PreguntaUsuarioBO>) result;
		}
		return null;
	}

	@Override
	public int eliminarPregunta(UsuarioMaestroBO usuarioMaestro,
			PreguntaUsuarioBO preguntaTemp) {
		return gestionPreguntas.eliminarPreguntaUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				preguntaTemp.getIdentificador());
	}

	@Override
	public int eliminarPrueba(UsuarioMaestroBO usuarioMaestro,
			PruebaUsuarioBO pruebaTemp) {
		return gestionPruebas.eliminarPruebaUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				pruebaTemp.getIdentificador());
	}

	@Override
	public int eliminarProceso(UsuarioMaestroBO usuarioMaestro,
			ProcesoUsuarioBO procesoTemp) {
		return gestionProcesos.eliminarProcesoUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				procesoTemp.getIdentificador());
	}

	@Override
	public void generarReporte(List<ParticipacionEnProcesoBO> participaciones) {

		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		session.setAttribute("participaciones", participaciones);

	}

	@Override
	public List<PreguntaUsuarioBO> consultarPreguntasPorCategoria(
			int usuarioID, int categoriaID) {
		Object result = gestionPruebas.listarPreguntasPrueba(usuarioID,
				categoriaID);
		if (result != null) {
			return (List<PreguntaUsuarioBO>) result;
		}
		return null;

	}

	@Override
	public int agregarProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso) {
		return gestionProcesos.agregarProcesoUsuarioAdministrador(
				usuarioAdministrador.getIdentificador(), proceso);
	}

	@Override
	public ProcesoUsuarioBO consultarProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO proceso) {
		return gestionProcesos.consultarProcesoUsuarioAdministrador(
				usuarioMaestro.getIdentificador(), proceso.getIdentificador());
	}

	@Override
	public int enviarNotificacionesParticipacionesProceso(
			UsuarioMaestroBO usuarioMaestro, List<ParticipacionEnProcesoBO> p) {
		for (ParticipacionEnProcesoBO participacionEnProcesoBO : p) {
			NotificadorCorreo
					.enviarCorreoParticipacionAProceso(participacionEnProcesoBO);
		}
		return 0;
	}

}
