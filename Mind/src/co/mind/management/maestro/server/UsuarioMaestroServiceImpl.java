package co.mind.management.maestro.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;
import co.mind.management.maestro.client.servicios.UsuarioMaestroService;
import co.mind.management.shared.persistencia.GestionEvaluacion;
import co.mind.management.shared.persistencia.GestionLaminas;
import co.mind.management.shared.persistencia.GestionPreguntas;
import co.mind.management.shared.persistencia.GestionProcesos;
import co.mind.management.shared.persistencia.GestionPruebas;
import co.mind.management.shared.persistencia.GestionUsuariosAdministradores;
import co.mind.management.shared.persistencia.GestionUsuariosBasicos;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.Generador;
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
	public int crearCuenta(UsuarioAdministradorBO usuarioAdministrador,
			SolicitudPlanBO solicitud, PermisoBO plan, Date fechaFinalizacion,
			int usosTotal) {

		return 1;
	}

	@Override
	public int activarCuenta(int usuarioAdministradorID) {
		UsuarioAdministradorBO usuario = gestionUsuariosAdministradores
				.consultarUsuarioAdministrador(usuarioAdministradorID);
		if (usuario != null) {
			if (usuario.getEstado_Cuenta().equalsIgnoreCase(
					Convencion.ESTADO_CUENTA_INACTIVA)) {
				usuario.setEstado_Cuenta(Convencion.ESTADO_CUENTA_ACTIVA);
				int resultado = gestionUsuariosAdministradores
						.editarUsuarioAdministrador(usuario);
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
		UsuarioAdministradorBO usuario = gestionUsuariosAdministradores
				.consultarUsuarioAdministrador(usuarioAdministradorID);
		if (usuario != null) {
			if (usuario.getEstado_Cuenta().equalsIgnoreCase(
					Convencion.ESTADO_CUENTA_ACTIVA)) {
				usuario.setEstado_Cuenta(Convencion.ESTADO_CUENTA_INACTIVA);
				int resultado = gestionUsuariosAdministradores
						.editarUsuarioAdministrador(usuario);
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
			List<UsuarioAdministradorBO> usuariosAdministradores,
			ImagenBO imagen) {
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
	public UsuarioAdministradorBO consultarUsuarioAdministrador(
			int usuarioAdministradorID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PreguntaUsuarioBO> consultarPreguntasUsuarioAdministrador(
			int usuarioAdministradorID) {
		// TODO Auto-generated method stub
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
	public List<UsuarioAdministradorBO> consultarUsuariosAdministradores() {
		Object lista = gestionUsuariosAdministradores
				.listarUsuariosAdministradores();
		if (lista != null) {
			return (List<UsuarioAdministradorBO>) lista;
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
			UsuarioAdministradorBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion) {
		// TODO Auto-generated method stub
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
	public int agregarPreguntaAPrueba(
			UsuarioAdministradorBO usuarioAdministrador,
			PruebaUsuarioBO prueba, PreguntaUsuarioBO pregunta) {
		// TODO Auto-generated method stub
		return gestionPreguntas.agregarPreguntaUsuarioAdministrador(
				usuarioAdministrador.getIdentificador(), pregunta);
	}

	@Override
	public int agregarPreguntasAPrueba(
			UsuarioAdministradorBO usuarioAdministrador,
			PruebaUsuarioBO prueba, List<PreguntaUsuarioBO> preguntas) {

		return 0;
	}

	@Override
	public int agregarPregunta(UsuarioAdministradorBO usuarioAdministrador,
			PreguntaUsuarioBO pregunta) {
		return 0;
	}

	@Override
	public int agregarProceso(UsuarioBO usuarioAdministrador,
			List<EvaluadoBO> usuariosBasicos, ProcesoUsuarioBO proceso) {
		List<ParticipacionEnProcesoBO> participaciones = new ArrayList<ParticipacionEnProcesoBO>();
		for (int i = 0; i < usuariosBasicos.size(); i++) {
			ParticipacionEnProcesoBO part = new ParticipacionEnProcesoBO();
			part.setCodigo_Acceso(Generador.GenerarCodigo(Generador.CARACTERES,
					8));
			part.setEstado(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_ESPERA);
			part.setFechaFinalizacion(proceso.getFechaFinalizacion());
			part.setFechaInicio(proceso.getFechaInicio());
			part.setUsuarioBasicoID(usuariosBasicos.get(i).getIdentificador());
			part.setUsuarioBasico(usuariosBasicos.get(i));
			participaciones.add(part);
		}
		return gestionProcesos.agregarProcesoUsuarioAdministrador(
				usuarioAdministrador.getIdentificador(), proceso,
				participaciones);
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
			UsuarioAdministradorBO usuarioAdministrador,
			ProcesoUsuarioBO proceso) {
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
	public int agregarPregunta(UsuarioBO usuarioAdministrador,
			PreguntaUsuarioBO pregunta, PruebaUsuarioBO categoria) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<PreguntaUsuarioBO> consultarPreguntasPorCategoria(
			int usuarioID, int categoriaID) {
		// TODO Auto-generated method stub
		return null;
	}

}
