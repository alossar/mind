package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProcesoRecord extends ListGridRecord {

	public ProcesoRecord(int idProceso, String nombreProceso,
			String descripcionProceso, Date fechaCreacion, Date fechaInicio,
			Date fechaFinalizacion, String estadoValoracion,
			String solicitudValoracion) {
		setAttribute("idProceso", idProceso);
		setAttribute("nombreProceso", nombreProceso);
		setAttribute("descripcionProceso", descripcionProceso);
		setAttribute("fechaCreacion", fechaCreacion);
		setAttribute("fechaInicio", fechaInicio);
		setAttribute("fechaFinalizacion", fechaFinalizacion);
		setAttribute("estadoValoracion", estadoValoracion);
		setAttribute("solicitudValoracion", solicitudValoracion);
	}

	public static ProcesoRecord[] getRecords(List<ProcesoUsuarioBO> procesos) {
		List<ProcesoRecord> resultado = new ArrayList<ProcesoRecord>();
		if (procesos != null) {
			for (ProcesoUsuarioBO procesoUsuario : procesos) {
				ProcesoRecord imagen = new ProcesoRecord(
						procesoUsuario.getIdentificador(),
						procesoUsuario.getNombre(),
						procesoUsuario.getDescripcion(),
						procesoUsuario.getFechaCreacion(),
						procesoUsuario.getFechaInicio(),
						procesoUsuario.getFechaFinalizacion(),
						procesoUsuario.getEstadoValoracion(),
						procesoUsuario.getSolicitudValoracion());
				resultado.add(imagen);
			}
			ProcesoRecord[] records = new ProcesoRecord[resultado.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		}
		return new ProcesoRecord[0];
	}

	public static ProcesoUsuarioBO getBO(ProcesoRecord record) {
		ProcesoUsuarioBO proceso = new ProcesoUsuarioBO();
		proceso.setDescripcion(record.getAttribute("descripcionProceso"));
		proceso.setEstadoValoracion(record.getAttribute("estadoValoracion"));
		proceso.setFechaCreacion(record.getAttributeAsDate("fechaCreacion"));
		proceso.setFechaFinalizacion(record
				.getAttributeAsDate("fechaFinalizacion"));
		proceso.setFechaInicio(record.getAttributeAsDate("fechaInicio"));
		proceso.setIdentificador(record.getAttributeAsInt("idProceso"));
		proceso.setNombre(record.getAttribute("nombreProceso"));
		proceso.setSolicitudValoracion(record
				.getAttribute("solicitudValoracion"));
		return proceso;
	}
}
