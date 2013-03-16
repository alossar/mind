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
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UsuarioMaestroService")
public interface UsuarioMaestroService extends RemoteService {

	public UsuarioBO validarSesion(String sesion);

	public void cerrarSesion();

	/**
	 * Agrega un Usuario Administradors a la Base de Datos, dada la cuenta de
	 * Usuario Administrador a la que pertenece. Se crean el plan de Usuario
	 * Basico, las laminas, pruebas predeterminadas para el usuario.
	 * <p>
	 * 
	 * @param usuarioAdministrador
	 *            Datos del Usuario Administrador.
	 * @return 0 si el objeto se ingreso correctamente.
	 */
	public int crearCuenta(UsuarioAdministradorBO usuarioAdministrador,
			SolicitudPlanBO solicitud, PermisoBO plan, Date fechaFinalizacion,
			int usosTotal);

	/**
	 * Activa la cuenta de un Usuario Administrador.
	 * <p>
	 * 
	 * @param usuarioAdministradorID
	 *            Identificador del Usuario Administrador.
	 * @return 0 si el objeto se editó correctamente. 1 en caso contrario.
	 */
	public int activarCuenta(int usuarioAdministradorID);

	/**
	 * Desactiva la cuenta de un Usuario Administrador.
	 * <p>
	 * 
	 * @param usuarioAdministradorID
	 *            Identificador del Usuario Administrador.
	 * @return 0 si el objeto se editó correctamente. 1 en caso contrario.
	 */
	public int desactivarCuenta(int usuarioAdministradorID);

	public int eliminarCuenta(int usuarioAdministradorID);

	public int eliminarCuenta(int usuarioAdministradorID,
			SolicitudEliminacionCuentaBO solicitud);

	public int agregarImagenPredeterminada(ImagenBO imagen);

	public int agregarImagenPredeterminadaAUsuarios(
			List<UsuarioAdministradorBO> usuariosAdministradores,
			ImagenBO imagen);

	public int agregarPlan(PermisoBO plan);

	public int agregarParticipacionDeUsuarioBasicoAProceso(
			UsuarioAdministradorBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion);

	public int agregarUsuarioBasico(UsuarioBO usuarioAdministrador,
			EvaluadoBO usuarioBasico);

	int agregarPrueba(UsuarioBO usuarioAdministrador, PruebaUsuarioBO prueba);

	public int agregarPreguntaAPrueba(
			UsuarioAdministradorBO usuarioAdministrador,
			PruebaUsuarioBO prueba, PreguntaUsuarioBO pregunta);

	public int agregarPreguntasAPrueba(
			UsuarioAdministradorBO usuarioAdministrador,
			PruebaUsuarioBO prueba, List<PreguntaUsuarioBO> preguntas);

	public int agregarPregunta(UsuarioAdministradorBO usuarioAdministrador,
			PreguntaUsuarioBO pregunta);

	public int agregarPregunta(UsuarioBO usuarioAdministrador,
			PreguntaUsuarioBO pregunta, PruebaUsuarioBO categoria);

	public int agregarProceso(UsuarioBO usuarioAdministrador,
			List<EvaluadoBO> usuariosBasicos, ProcesoUsuarioBO proceso);

	List<ParticipacionEnProcesoBO> consultarParticipacionEnProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso);

	public List<ParticipacionEnProcesoBO> consultarValoracionesProceso(
			UsuarioAdministradorBO usuarioAdministrador,
			ProcesoUsuarioBO proceso);

	public List<ParticipacionEnProcesoBO> consultarResultadosProceso(
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso);

	public List<SolicitudCambioPlanBO> consultarSolicitudesCambioDePlan();

	public List<SolicitudEliminacionCuentaBO> consultarSolicitudesCambioDeCuenta();

	public List<SolicitudIncrementoUsosBO> consultarSolicitudesIncrementoDeUsos();

	public List<SolicitudValoracionBO> consultarSolicitudesValoracionProceso();

	public List<SolicitudPlanBO> consultarSolicitudesPlan();

	public UsuarioAdministradorBO consultarUsuarioAdministrador(int usuarioID);

	public List<UsuarioAdministradorBO> consultarUsuariosAdministradores();

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
}
