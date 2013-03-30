package co.mind.management.shared.persistencia;

import java.util.List;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;

public interface IGestionProcesos {
	public int agregarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			ProcesoUsuarioBO proceso);
	
	public int editarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			ProcesoUsuarioBO proceso);

	public ProcesoUsuarioBO consultarProcesoUsuarioAdministrador(
			int usuarioAdministradorID, int procesoID);

	public int eliminarProcesoUsuarioAdministrador(int usuarioAdministradorID,
			int procesoID);

	public List<ProcesoUsuarioBO> listarProcesoUsuarioAdministrador(
			int usuarioAdministradorID);

}