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
import co.mind.management.shared.dto.UsuarioMaestroBO;
import co.mind.management.shared.dto.UsuarioProgramadorBO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UsuarioMaestroServiceAsync {

	void crearCuenta(UsuarioBO usuarioAdministrador, SolicitudPlanBO solicitud,
			Date fechaFinalizacion, int usosTotal,
			AsyncCallback<Integer> callback);

	void activarCuenta(int usuarioAdministradorID,
			AsyncCallback<Integer> callback);

	
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
			UsuarioBO usuarioAdministrador, ProcesoUsuarioBO proceso,
			List<ParticipacionEnProcesoBO> evaluados,
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

	void consultarValoracionesProceso(UsuarioBO usuarioAdministrador,
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

	void eliminarPregunta(UsuarioBO usuarioMaestro,
			PreguntaUsuarioBO preguntaTemp, AsyncCallback<Integer> callback);

	void eliminarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO pruebaTemp,
			AsyncCallback<Integer> asyncCallback);

	void eliminarProceso(UsuarioBO usuarioMaestro,
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

	void enviarNotificacionesParticipacionesProceso(UsuarioBO usuarioMaestro,
			List<ParticipacionEnProcesoBO> p,
			AsyncCallback<Integer> asyncCallback);

	void agregarCuenta(UsuarioBO usuarioMaestro, UsuarioBO usuario, UsoBO usos,
			List<PruebaUsuarioBO> pruebas, AsyncCallback<Integer> asyncCallback);

	void editarPregunta(UsuarioBO usuarioMaestro, PreguntaUsuarioBO bo,
			PruebaUsuarioBO prueba, AsyncCallback<Integer> asyncCallback);

	void duplicarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso,
			AsyncCallback<Integer> asyncCallback);

	void duplicarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO prueba,
			AsyncCallback<Integer> asyncCallback);

	void editarProceso(UsuarioBO usuarioMaestro, ProcesoUsuarioBO proceso,
			AsyncCallback<Integer> asyncCallback);

	void editarPrueba(UsuarioBO usuarioMaestro, PruebaUsuarioBO prueba,
			AsyncCallback<Integer> asyncCallback);

	void editarEvaluado(UsuarioBO usuarioMaestro, EvaluadoBO evaluado,
			AsyncCallback<Integer> asyncCallback);

	void editarCliente(UsuarioBO usuarioMaestro,
			UsuarioAdministradorBO cliente, AsyncCallback<Integer> asyncCallback);

	void consultarUsos(UsuarioBO cliente,
			AsyncCallback<List<UsoBO>> asyncCallback);

	void consultarProcesosUsuarioAdministradorNombre(int identificador,
			String keyword, AsyncCallback<List<ProcesoUsuarioBO>> asyncCallback);

	void consultarPruebasUsuarioAdministradorNombre(int identificador,
			String keyword, AsyncCallback<List<PruebaUsuarioBO>> asyncCallback);

	void consultarEvaluadoPorCedula(int identificador, int valor,
			AsyncCallback<List<EvaluadoBO>> asyncCallback);

	void consultarEvaluadoPorCorreo(int identificador, String keyword,
			AsyncCallback<List<EvaluadoBO>> asyncCallback);

	void consultarClientePorCedula(int identificador, int valor,
			AsyncCallback<List<UsuarioBO>> asyncCallback);

	void consultarClientePorEmpresa(int identificador, String keyword,
			AsyncCallback<List<UsuarioBO>> asyncCallback);

	void generarReporteUsos(UsuarioBO usuarioSeleccionado, Date dateInicio,
			Date dateFinal, AsyncCallback<Void> asyncCallback);

	void eliminarParticipacionesDeProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO procesoActual, List<ParticipacionEnProcesoBO> bo,
			AsyncCallback<Integer> asyncCallback);

	void eliminarPruebasDeProceso(UsuarioBO usuarioMaestro,
			ProcesoUsuarioBO procesoActual, List<PruebaUsuarioBO> bo,
			AsyncCallback<Integer> asyncCallback);

	void agregarProgramador(UsuarioBO usuarioMaestro,
			UsuarioProgramadorBO programador,
			AsyncCallback<Integer> asyncCallback);

	void consultarProgramadores(int identificador,
			AsyncCallback<List<UsuarioProgramadorBO>> asyncCallback);

	void agregarUsos(UsuarioBO usuarioSeleccionado, int usos,
			AsyncCallback<Integer> asyncCallback);

	void cambiarContrasena(UsuarioBO usuarioMaestro, String pass,
			AsyncCallback<Integer> asyncCallback);

	void consultarProcesosParaRevisar(
			AsyncCallback<List<ProcesoUsuarioBO>> asyncCallback);

}
