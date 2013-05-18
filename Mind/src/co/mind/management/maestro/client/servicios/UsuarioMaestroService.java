package co.mind.management.maestro.client.servicios;

import java.util.Date;
import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ImagenBO;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.SolicitudCambioPlanBO;
import co.mind.management.shared.dto.SolicitudEliminacionCuentaBO;
import co.mind.management.shared.dto.SolicitudIncrementoUsosBO;
import co.mind.management.shared.dto.SolicitudPlanBO;
import co.mind.management.shared.dto.SolicitudValoracionBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.dto.UsuarioProgramadorBO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UsuarioMaestroService")
public interface UsuarioMaestroService extends RemoteService {

	public UsuarioBO validarSesion(String sesion);

	public void cerrarSesion();

	int crearCuenta(UsuarioBO usuarioAdministrador, SolicitudPlanBO solicitud,
			Date fechaFinalizacion, int usosTotal);

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

	public List<ImagenUsuarioBO> consultarImagenesUsuario(int usuariID);

	public int eliminarUsuariosBasicos(UsuarioBO usuarioMaestro,
			List<EvaluadoBO> bo);

	public List<PreguntaUsuarioBO> consultarPreguntasPorCategoriaUsuarioAdministrador(
			int usuarioId);

	int eliminarPregunta(UsuarioBO usuarioMaestro,
			PreguntaUsuarioBO preguntaTemp);

	int eliminarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO pruebaTemp);

	int eliminarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO procesoTemp);

	public void generarReporte(List<ParticipacionEnProcesoBO> participaciones);

	ProcesoUsuarioBO consultarProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO proceso);

	int enviarNotificacionesParticipacionesProceso(UsuarioBO usuarioMaestro,
			List<ParticipacionEnProcesoBO> p);

	int agregarCuenta(UsuarioBO usuarioMaestro, UsuarioBO usuario, UsoBO usos,
			List<PruebaUsuarioBO> pruebas);

	int editarPregunta(UsuarioBO usuarioMaestro, PreguntaUsuarioBO bo,
			PruebaUsuarioBO prueba);

	int duplicarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso);

	int editarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso);

	int editarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO prueba);

	int duplicarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO prueba);

	int editarCliente(UsuarioBO usuarioMaestro, UsuarioAdministradorBO cliente);

	int editarEvaluado(UsuarioBO usuarioMaestro, EvaluadoBO evaluado);

	List<UsoBO> consultarUsos(UsuarioBO cliente);

	List<ProcesoUsuarioBO> consultarProcesosUsuarioAdministradorNombre(
			int identificador, String keyword);

	List<PruebaUsuarioBO> consultarPruebasUsuarioAdministradorNombre(
			int identificador, String keyword);

	List<EvaluadoBO> consultarEvaluadoPorCedula(int identificador, int valor);

	List<EvaluadoBO> consultarEvaluadoPorCorreo(int identificador,
			String keyword);

	List<UsuarioBO> consultarClientePorCedula(int identificador, int valor);

	List<UsuarioBO> consultarClientePorEmpresa(int identificador, String keyword);

	void generarReporteUsos(UsuarioBO usuarioSeleccionado, Date dateInicio,
			Date dateFinal);

	int eliminarParticipacionesDeProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO procesoActual, List<ParticipacionEnProcesoBO> bo);

	int eliminarPruebasDeProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO procesoActual, List<PruebaUsuarioBO> bo);

	int agregarProgramador(UsuarioBO usuarioMaestro,
			UsuarioProgramadorBO programador);

	List<UsuarioProgramadorBO> consultarProgramadores(int identificador);

	int agregarUsos(UsuarioBO usuarioSeleccionado, int usos);

	int cambiarContrasena(UsuarioBO usuarioMaestro, String pass);

	List<ProcesoUsuarioBO> consultarProcesosParaRevisar();

	List<ParticipacionEnProcesoBO> consultarParticipacionesEvaluado(
			UsuarioBO usuarioMaestro, EvaluadoBO bo);

	int agregarPruebas(UsuarioBO usuarioSeleccionado,
			List<PruebaUsuarioBO> pruebasBO);

}
