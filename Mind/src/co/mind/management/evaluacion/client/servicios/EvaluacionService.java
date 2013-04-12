package co.mind.management.evaluacion.client.servicios;

import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.ResultadoBO;
import co.mind.management.shared.dto.UsuarioBO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("EvaluacionService")
public interface EvaluacionService extends RemoteService {

	public int validarUsuarioBasico(String identificador, String correo,
			String codigoAcceso);

	public int comenzarPrueba(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion);

	public int guardarResultado(EvaluadoBO usuarioBasico, ResultadoBO resultado);

	public List<PreguntaUsuarioBO> preguntasPrueba(ProcesoUsuarioBO proceso,
			EvaluadoBO usuarioBasico);

	public int terminarPrueba(EvaluadoBO usuarioBasico,
			ParticipacionEnProcesoBO participacion);

	public ParticipacionEnProcesoBO obtenerParticipacionEnProceso(
			int identificador, String correo, String codigoAcceso);

	public ParticipacionEnProcesoBO obtenerParticipacionEnProcesoDesdeServidor();

	public ParticipacionEnProcesoBO validarSesion(String sesion);

	public void cerrarSesion();

	public String obtenerNombreDeLaEmpresa(int usuarioAdministradorID);
}
