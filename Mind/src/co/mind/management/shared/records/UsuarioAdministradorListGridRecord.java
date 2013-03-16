package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.bo.EvaluadoBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UsuarioAdministradorListGridRecord extends ListGridRecord {

	public UsuarioAdministradorListGridRecord(int id, String nombre, String apellidos,
			String correo, int idUsuario) {
		setAttribute("id", id);
		setAttribute("nombre", nombre);
		setAttribute("apellidos", apellidos);
		setAttribute("correo", correo);
		setAttribute("idUsuario", idUsuario);
	}

	public static UsuarioAdministradorListGridRecord[] getRecords(
			List<EvaluadoBO> usuarios) {
		List<UsuarioAdministradorListGridRecord> resultado = new ArrayList<UsuarioAdministradorListGridRecord>();
		if (usuarios != null) {
			for (EvaluadoBO usuario : usuarios) {
				UsuarioAdministradorListGridRecord imagen = new UsuarioAdministradorListGridRecord(
						usuario.getIdentificador(), usuario.getNombres(),
						usuario.getApellidos(), usuario.getCorreoElectronico(),
						usuario.getIdentificadorUsuarioAdministrador());
				resultado.add(imagen);
			}
			UsuarioAdministradorListGridRecord[] records = new UsuarioAdministradorListGridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		} else {
			return new UsuarioAdministradorListGridRecord[0];
		}
	}

}
