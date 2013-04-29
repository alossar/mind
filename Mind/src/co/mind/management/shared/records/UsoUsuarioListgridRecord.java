package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsoBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UsoUsuarioListgridRecord extends ListGridRecord {

	public UsoUsuarioListgridRecord(int id, Date fechaAsignacion,
			Date fechaVencimiento, int usosAsignados, int usosRedimidos) {
		setAttribute("id", id);
		setAttribute("usosAsignados", usosAsignados);
		setAttribute("usosRedimidos", usosRedimidos);
		setAttribute("fechaAsignacion", fechaAsignacion);
		setAttribute("fechaVencimiento", fechaVencimiento);
	}

	public static UsoUsuarioListgridRecord[] getRecords(List<UsoBO> result) {
		List<UsoUsuarioListgridRecord> resultado = new ArrayList<UsoUsuarioListgridRecord>();
		if (result != null) {
			for (UsoBO procesoUsuario : result) {
				UsoUsuarioListgridRecord imagen = new UsoUsuarioListgridRecord(
						procesoUsuario.getIdentificador(),
						procesoUsuario.getFechaAsignacion(),
						procesoUsuario.getFechaVencimiento(),
						procesoUsuario.getUsosAsignados(),
						procesoUsuario.getUsosRedimidos());
				resultado.add(imagen);
			}
			UsoUsuarioListgridRecord[] records = new UsoUsuarioListgridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		}
		return new UsoUsuarioListgridRecord[0];
	}

	public static List<UsoBO> getBO(UsoUsuarioListgridRecord[] records) {
		if (records != null) {
			List<UsoBO> lista = new ArrayList<UsoBO>();
			for (UsoUsuarioListgridRecord record : records) {

				UsoBO resultado = new UsoBO();

				resultado.setIdentificador(record.getAttributeAsInt("id"));
				resultado.setFechaAsignacion(record
						.getAttributeAsDate("fechaAsignacion"));
				resultado.setFechaVencimiento(record
						.getAttributeAsDate("fechaVencimientoF"));
				resultado.setUsosAsignados(record
						.getAttributeAsInt("usosAsignados"));
				resultado.setUsosRedimidos(record
						.getAttributeAsInt("usosRedimidos"));
				lista.add(resultado);
			}
			return lista;
		}
		return null;
	}

	public static UsoBO getBO(UsoUsuarioListgridRecord record) {
		if (record != null) {
			UsoBO resultado = new UsoBO();

			resultado.setIdentificador(record.getAttributeAsInt("id"));
			resultado.setFechaAsignacion(record
					.getAttributeAsDate("fechaAsignacion"));
			resultado.setFechaVencimiento(record
					.getAttributeAsDate("fechaVencimientoF"));
			resultado.setUsosAsignados(record
					.getAttributeAsInt("usosAsignados"));
			resultado.setUsosRedimidos(record
					.getAttributeAsInt("usosRedimidos"));
			return resultado;
		}
		return null;
	}

}
