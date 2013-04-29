package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProcesoRecord extends ListGridRecord {

	public ProcesoRecord(int idProceso, String nombreProceso,
			String descripcionProceso, Date fechaCreacion, Date fechaInicio,
			Date fechaFinalizacion, String estadoValoracion, int tiempoProceso,
			int cantidadPreguntas, int idUsuario, String nombreUsuario,
			String empresa, String estadoProceso) {
		setAttribute("idProceso", idProceso);
		setAttribute("nombreProceso", nombreProceso);
		setAttribute("descripcionProceso", descripcionProceso);
		setAttribute("fechaCreacion", fechaCreacion);
		setAttribute("fechaInicio", fechaInicio);
		setAttribute("fechaFinalizacion", fechaFinalizacion);
		setAttribute("estadoProceso", estadoProceso);
		setAttribute("estadoValoracion", estadoValoracion);
		setAttribute("tiempoProceso", tiempoProceso);
		setAttribute("cantidadPreguntas", cantidadPreguntas);

		setAttribute("idUsuario", idUsuario);
		setAttribute("nombreUsuario", nombreUsuario);
		setAttribute("empresa", empresa);
	}

	public static ProcesoRecord[] getRecords(List<ProcesoUsuarioBO> procesos) {
		List<ProcesoRecord> resultado = new ArrayList<ProcesoRecord>();
		if (procesos != null) {
			for (ProcesoUsuarioBO procesoUsuario : procesos) {
				int tiempo = procesoUsuario.duracionProceso() / 60;
				ProcesoRecord imagen = new ProcesoRecord(
						procesoUsuario.getIdentificador(),
						procesoUsuario.getNombre(),
						procesoUsuario.getDescripcion(),
						procesoUsuario.getFechaCreacion(),
						procesoUsuario.getFechaInicio(),
						procesoUsuario.getFechaFinalizacion(),
						procesoUsuario.getEstadoValoracion(), tiempo,
						procesoUsuario.cantidadDePreguntas(), procesoUsuario
								.getUsuario().getIdentificador(),
						procesoUsuario.getNombre() + " "
								+ procesoUsuario.getUsuario().getApellidos(),
						procesoUsuario.getUsuario().getEmpresa(),
						estadoDelProceso(procesoUsuario.getFechaFinalizacion()));
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

	private static String estadoDelProceso(Date fechaFinalizacion) {
		if (fechaFinalizacion == null) {
			return "Proceso Abierto";
		} else {
			if (fechaFinalizacion.before(new Date())) {
				return "Proceso Cerrado";
			} else {
				return "Proceso Abierto";
			}
		}
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

		return proceso;
	}
}
