package co.mind.management.shared.persistencia;

import java.util.List;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ResultadoBO;

public interface IGestionEvaluacion {

	public int agregarParticipacionEnProceso(int usuarioAdministradorBO,
			int usuarioBasicoID, int procesoID,
			ParticipacionEnProcesoBO participacion);

	public int editarParticipacionEnProceso(int usuarioAdministradorBO,
			int usuarioBasicoID, int procesoID,
			ParticipacionEnProcesoBO participacion);

	public int eliminarParticipacionEnProceso(int usuarioAdministradorBO,
			int usuarioBasicoID, int procesoID, int participacionID);

	public ParticipacionEnProcesoBO consultarParticipacionAProceso(
			int usuarioAdministradorBO, int usuarioBasicoID, int procesoID,
			int participacionID);

	public ParticipacionEnProcesoBO consultarParticipacionAProceso(
			int usuarioBasicoID, String correo, String codigoAcceso);

	public List<ParticipacionEnProcesoBO> listarParticipacionesEnProceso(
			int usuarioAdministradorBO, int procesoID);

	public int eliminarCodigoAcceso(int participacionID);

	public int agregarResultadoParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID, ResultadoBO resultado);

	public int eliminarResultadoParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID, int resultadoID);

	public ResultadoBO consultarResultadoParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID, int resultadoID);

	public List<ResultadoBO> listarResultadosParticipacionEnProceso(
			int usuarioAdministradorID, int usuarioBasicoID, int procesoID,
			int participacionID);

	public List<ResultadoBO> listarResultadosProceso(
			int usuarioAdministradorID, int procesoID);

	public List<ParticipacionEnProcesoBO> listarParticipacionesEnProcesoCompletas(int usuarioAdministradorID,
			int procesoID);

}
