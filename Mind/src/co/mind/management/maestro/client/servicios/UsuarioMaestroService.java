package co.mind.management.maestro.client.servicios;

import java.util.Date;
import java.util.List;

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
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UsuarioMaestroService")
public interface UsuarioMaestroService extends RemoteService {

	public UsuarioBO validarSesion(String sesion);

	public void cerrarSesion();

	int crearCuenta(UsuarioBO usuarioAdministrador, SolicitudPlanBO solicitud,
			PermisoBO plan, Date fechaFinalizacion, int usosTotal);

	/**
	 * Activa la cuenta de un Usuario Administrador.
	 * <p>
	 * 
	 * @param usuarioAdministradorID
	 *            Identificador del Usuario Administrador.
	 * @return 0 si el objeto se edit� correctamente. 1 en caso contrario.
	 */
	public int activarCuenta(int usuarioAdministradorID);

	/**
	 * Desactiva la cuenta de un Usuario Administrador.
	 * <p>
	 * 
	 * @param usuarioAdministradorID
	 *            Identificador del Usuario Administrador.
	 * @return 0 si el objeto se edit� correctamente. 1 en caso contrario.
	 */
	public int desactivarCuenta(int usuarioAdministradorID);

	public int eliminarCuenta(int usuarioAdministradorID);

	public int eliminarCuenta(int usuarioAdministradorID,
			SolicitudEliminacionCuentaBO solicitud);

	public int agregarImagenPredeterminada(ImagenBO imagen);

	public int agregarImagenPredeterminadaAUsuarios(
			List<UsuarioBO> usuariosAdministradores, ImagenBO imagen);

	public int agregarPlan(PermisoBO plan);

	int agregarParticipacionDeUsuarioBasicoAProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso,
			List<ParticipacionEnProcesoBO> evaluados);

	int agregarPruebasAProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, List<PruebaUsuarioBO> pruebas);

	public int agregarUsuarioBasico(UsuarioBO usuarioAdministrador,
			EvaluadoBO usuarioBasico);

	int agregarPrueba(UsuarioBO usuarioAdministrador, PruebaUsuarioBO prueba);

	int agregarPreguntaAPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba, PreguntaUsuarioBO pregunta);

	int agregarPreguntasAPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba, List<PreguntaUsuarioBO> preguntas);

	public int agregarProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso);

	List<ParticipacionEnProcesoBO> consultarParticipacionEnProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso);

	List<ParticipacionEnProcesoBO> consultarValoracionesProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso);

	public List<ParticipacionEnProcesoBO> consultarResultadosProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso);

	public List<SolicitudCambioPlanBO> consultarSolicitudesCambioDePlan();

	public List<SolicitudEliminacionCuentaBO> consultarSolicitudesCambioDeCuenta();

	public List<SolicitudIncrementoUsosBO> consultarSolicitudesIncrementoDeUsos();

	public List<SolicitudValoracionBO> consultarSolicitudesValoracionProceso();

	public List<SolicitudPlanBO> consultarSolicitudesPlan();

	public UsuarioBO consultarUsuarioAdministrador(int usuarioID);

	public List<UsuarioBO> consultarUsuariosAdministradores();

	public List<PreguntaUsuarioBO> consultarPreguntasUsuarioAdministrador(
			int usuarioID);

	public List<PreguntaUsuarioBO> consultarPreguntasPorCategoria(
			int usuarioID, int categoriaID);

	public List<PruebaUsuarioBO> consultarPruebasUsuarioAdministrador(
			int usuarioID);

	public List<ProcesoUsuarioBO> consultarProcesosUsuarioAdministrador(
			int usuarioID);

	public List<EvaluadoBO> consultarUsuariosBasicosUsuarioAdministrador(
			int usuarioID);

	public List<PermisoBO> consultarPlanesUsuarioAdministrador(int usuarioID);

	public List<ImagenUsuarioBO> consultarImagenesUsuario(int usuariID);

	public int eliminarUsuariosBasicos(UsuarioBO usuarioMaestro,
			List<EvaluadoBO> bo);

	public List<PreguntaUsuarioBO> consultarPreguntasPorCategoriaUsuarioAdministrador(
			int usuarioId);

	public int eliminarPregunta(UsuarioMaestroBO usuarioMaestro,
			PreguntaUsuarioBO preguntaTemp);

	public int eliminarPrueba(UsuarioMaestroBO usuarioMaestro,
			PruebaUsuarioBO pruebaTemp);

	int eliminarProceso(UsuarioMaestroBO usuarioMaestro,
			ProcesoUsuarioBO procesoTemp);

	public void generarReporte(List<ParticipacionEnProcesoBO> participaciones);

	ProcesoUsuarioBO consultarProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO proceso);

	int enviarNotificacionesParticipacionesProceso(
			UsuarioMaestroBO usuarioMaestro, List<ParticipacionEnProcesoBO> p);

}
