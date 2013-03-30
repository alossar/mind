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
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UsuarioMaestroServiceAsync {

	void crearCuenta(UsuarioBO usuarioAdministrador,
			SolicitudPlanBO solicitud, PermisoBO plan, Date fechaFinalizacion,
			int usosTotal, AsyncCallback<Integer> callback);

	void activarCuenta(int usuarioAdministradorID,
			AsyncCallback<Integer> callback);

	void agregarImagenPredeterminada(ImagenBO imagen,
			AsyncCallback<Integer> callback);

	void agregarImagenPredeterminadaAUsuarios(
			List<UsuarioBO> usuariosAdministradores,
			ImagenBO imagen, AsyncCallback<Integer> callback);

	void agregarPlan(PermisoBO plan, AsyncCallback<Integer> callback);

	void consultarPlanesUsuarioAdministrador(int usuarioAdministradorID,
			AsyncCallback<List<PermisoBO>> callback);

	void consultarPreguntasUsuarioAdministrador(int usuarioAdministradorID,
			AsyncCallback<List<PreguntaUsuarioBO>> callback);

	void consultarProcesosUsuarioAdministrador(int usuarioAdministradorID,
			AsyncCallback<List<ProcesoUsuarioBO>> callback);

	void consultarPruebasUsuarioAdministrador(int usuarioAdministradorID,
			AsyncCallback<List<PruebaUsuarioBO>> callback);

	void consultarSolicitudesCambioDeCuenta(
			AsyncCallback<List<SolicitudEliminacionCuentaBO>> callback);

	void consultarSolicitudesCambioDePlan(
			AsyncCallback<List<SolicitudCambioPlanBO>> callback);

	void consultarSolicitudesIncrementoDeUsos(
			AsyncCallback<List<SolicitudIncrementoUsosBO>> callback);

	void consultarSolicitudesPlan(AsyncCallback<List<SolicitudPlanBO>> callback);

	void consultarSolicitudesValoracionProceso(
			AsyncCallback<List<SolicitudValoracionBO>> callback);

	void consultarUsuarioAdministrador(int usuarioAdministradorID,
			AsyncCallback<UsuarioBO> callback);

	void consultarUsuariosBasicosUsuarioAdministrador(
			int usuarioAdministradorID, AsyncCallback<List<EvaluadoBO>> callback);

	void desactivarCuenta(int usuarioAdministradorID,
			AsyncCallback<Integer> callback);

	void eliminarCuenta(int usuarioAdministradorID,
			AsyncCallback<Integer> callback);

	void eliminarCuenta(int usuarioAdministradorID,
			SolicitudEliminacionCuentaBO solicitud,
			AsyncCallback<Integer> callback);

	void validarSesion(String sesion, AsyncCallback<UsuarioBO> callback);

	void cerrarSesion(AsyncCallback<Void> callback);

	void consultarUsuariosAdministradores(
			AsyncCallback<List<UsuarioBO>> callback);

	void consultarImagenesUsuario(int usuariID,
			AsyncCallback<List<ImagenUsuarioBO>> callback);

	void agregarParticipacionDeUsuarioBasicoAProceso(
			UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, List<ParticipacionEnProcesoBO> evaluados,
			AsyncCallback<Integer> callback);

	void agregarPreguntaAPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba, PreguntaUsuarioBO pregunta,
			AsyncCallback<Integer> callback);

	void agregarPreguntasAPrueba(UsuarioBO usuarioAdministrador,
			PruebaUsuarioBO prueba, List<PreguntaUsuarioBO> preguntas,
			AsyncCallback<Integer> callback);

	void agregarPrueba(UsuarioBO usuarioAdministrador, PruebaUsuarioBO prueba,
			AsyncCallback<Integer> callback);

	void agregarUsuarioBasico(UsuarioBO usuarioAdministrador,
			EvaluadoBO usuarioBasico, AsyncCallback<Integer> callback);

	void consultarParticipacionEnProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso,
			AsyncCallback<List<ParticipacionEnProcesoBO>> callback);

	void consultarValoracionesProceso(
			UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso,
			AsyncCallback<List<ParticipacionEnProcesoBO>> callback);

	void consultarResultadosProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso,
			AsyncCallback<List<ParticipacionEnProcesoBO>> callback);

	void eliminarUsuariosBasicos(UsuarioBO usuarioMaestro, List<EvaluadoBO> bo,
			AsyncCallback<Integer> callback);

	void consultarPreguntasPorCategoriaUsuarioAdministrador(int usuarioId,
			AsyncCallback<List<PreguntaUsuarioBO>> callback);

	void consultarPreguntasPorCategoria(int usuarioID, int categoriaID,
			AsyncCallback<List<PreguntaUsuarioBO>> callback);

	void eliminarPregunta(UsuarioMaestroBO usuarioMaestro,
			PreguntaUsuarioBO preguntaTemp, AsyncCallback<Integer> callback);

	void eliminarPrueba(UsuarioMaestroBO usuarioMaestro,
			PruebaUsuarioBO pruebaTemp, AsyncCallback<Integer> asyncCallback);

	void eliminarProceso(UsuarioMaestroBO usuarioMaestro,
			ProcesoUsuarioBO procesoTemp, AsyncCallback<Integer> asyncCallback);

	void generarReporte(List<ParticipacionEnProcesoBO> participaciones,
			AsyncCallback<Void> callback);

	void agregarProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, AsyncCallback<Integer> callback);

	void agregarPruebasAProceso(UsuarioBO usuarioAdministrador,
			ProcesoUsuarioBO proceso, List<PruebaUsuarioBO> pruebas,
			AsyncCallback<Integer> callback);

	void consultarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso,
			AsyncCallback<ProcesoUsuarioBO> asyncCallback);

	void enviarNotificacionesParticipacionesProceso(
			UsuarioMaestroBO usuarioMaestro, List<ParticipacionEnProcesoBO> p,
			AsyncCallback<Integer> asyncCallback);

}
