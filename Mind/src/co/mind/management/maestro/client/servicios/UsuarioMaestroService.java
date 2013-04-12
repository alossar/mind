package co.mind.management.maestro.client.servicios;

import java.util.Date;
import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ImagenBO;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PermisoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.SolicitudCambioPlanBO;
import co.mind.management.shared.dto.SolicitudEliminacionCuentaBO;
import co.mind.management.shared.dto.SolicitudIncrementoUsosBO;
import co.mind.management.shared.dto.SolicitudPlanBO;
import co.mind.management.shared.dto.SolicitudValoracionBO;
import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.dto.UsuarioMaestroBO;

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

	List<PermisoBO> consultarPermisos();

	int agregarCuenta(UsuarioBO usuarioMaestro, UsuarioBO usuario,
			List<PruebaUsuarioBO> pruebas);

	int editarPregunta(UsuarioBO usuarioMaestro, PreguntaUsuarioBO bo,
			PruebaUsuarioBO prueba);

	int duplicarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso);

	int editarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso);

	int editarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO prueba);

	int duplicarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO prueba);

	int editarCliente(UsuarioBO usuarioMaestro, UsuarioAdministradorBO cliente);

	int editarEvaluado(UsuarioBO usuarioMaestro, EvaluadoBO evaluado);

}
