package co.mind.management.evaluacion.client.servicios;

import java.util.List;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.ResultadoBO;
import co.mind.management.shared.bo.EvaluadoBO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface EvaluacionServiceAsync {

	void comenzarPrueba(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion,
			AsyncCallback<Integer> callback);

	void guardarResultado(EvaluadoBO usuarioBasico, ResultadoBO resultado,
			AsyncCallback<Integer> callback);

	void preguntasPrueba(ProcesoUsuarioBO proceso, EvaluadoBO usuarioBasico,
			AsyncCallback<List<PreguntaUsuarioBO>> callback);

	void terminarPrueba(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion,
			AsyncCallback<Integer> callback);

	void validarUsuarioBasico(String identificador, String correo,
			String codigoAcceso, AsyncCallback<Integer> callback);

	void obtenerParticipacionEnProceso(int identificador, String correo,
			String codigoAcceso,
			AsyncCallback<ParticipacionEnProcesoBO> callback);

	void obtenerNombreDeLaEmpresa(int usuarioAdministradorID,
			AsyncCallback<String> callback);

	void cerrarSesion(AsyncCallback<Void> callback);

	void obtenerParticipacionEnProcesoDesdeServidor(
			AsyncCallback<ParticipacionEnProcesoBO> callback);

	void validarSesion(String sesion,
			AsyncCallback<ParticipacionEnProcesoBO> callback);

}
