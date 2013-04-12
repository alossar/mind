package co.mind.management.shared.persistencia;

import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.PermisoBO;

public interface IGestionPermisos {

	public int agregarPermiso(PermisoBO permiso);

	public int editarPermiso(PermisoBO permiso);

	public PermisoBO consultarUsuarioBasico(int permisoID);

	public int eliminarPermiso(int permisoID);

	public List<PermisoBO> listarPermisos();
}
