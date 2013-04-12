package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.mind.management.shared.dto.PermisoBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PermisoRecord extends ListGridRecord {

	public PermisoRecord(int idPermiso, String nombre, String edicion,
			String creacion) {
		setAttribute("idPermiso", idPermiso);
		setAttribute("nombre", nombre);
		setAttribute("edicion", edicion);
		setAttribute("creacion", creacion);
	}

	public static PermisoRecord[] getRecords(List<PermisoBO> permisos) {
		List<PermisoRecord> resultado = new ArrayList<PermisoRecord>();
		if (permisos != null) {
			for (PermisoBO permiso : permisos) {
				PermisoRecord per = new PermisoRecord(
						permiso.getIdentificador(), permiso.getNombre(),
						permiso.getEdicion(), permiso.getCreacion());
				resultado.add(per);
			}
			PermisoRecord[] records = new PermisoRecord[resultado.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		}
		return new PermisoRecord[0];
	}

	public static PermisoBO getBO(PermisoRecord record) {
		PermisoBO permiso = new PermisoBO();
		permiso.setNombre(record.getAttribute("nombre"));
		permiso.setIdentificador(record.getAttributeAsInt("idPermiso"));
		permiso.setCreacion(record.getAttribute("creacion"));
		permiso.setEdicion(record.getAttribute("edicion"));
		return permiso;
	}

}
