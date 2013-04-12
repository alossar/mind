package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.shared.dto.UsuarioBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UsuarioAdministradorListGridRecord extends ListGridRecord {

	public UsuarioAdministradorListGridRecord(int id, String nombre,
			String apellidos, String correo, String empresa, String cargo,
			String ciudad, String telefono, String celular, int idPermiso,
			String permisos, int usos) {
		setAttribute("id", id);
		setAttribute("nombre", nombre);
		setAttribute("apellidos", apellidos);
		setAttribute("correo", correo);
		setAttribute("empresa", empresa);
		setAttribute("cargo", cargo);
		setAttribute("ciudad", ciudad);
		setAttribute("telefono", telefono);
		setAttribute("celular", celular);
		setAttribute("idPermiso", idPermiso);
		setAttribute("permisos", permisos);
		setAttribute("usos", usos);
	}

	public static UsuarioAdministradorListGridRecord[] getRecords(
			List<UsuarioBO> usuarios) {
		List<UsuarioAdministradorListGridRecord> resultado = new ArrayList<UsuarioAdministradorListGridRecord>();
		if (usuarios != null) {
			for (UsuarioBO u : usuarios) {
				UsuarioAdministradorBO usuario = (UsuarioAdministradorBO) u;
				UsuarioAdministradorListGridRecord imagen = new UsuarioAdministradorListGridRecord(
						usuario.getIdentificador(), usuario.getNombres(),
						usuario.getApellidos(),
						usuario.getCorreo_Electronico(), usuario.getEmpresa(),
						usuario.getCargo(), usuario.getCiudad(),
						usuario.getTelefono(), usuario.getTelefono_Celular(),
						usuario.getPlanesDeUsuario().getIdentificador(),
						usuario.getPlanesDeUsuario().getNombre(),
						usuario.getUsos());
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

	public static List<UsuarioBO> getBO(
			UsuarioAdministradorListGridRecord[] records) {
		List<UsuarioBO> resultado = new ArrayList<UsuarioBO>();
		if (records != null) {
			for (UsuarioAdministradorListGridRecord usuario : records) {
				UsuarioBO usuarioBasico = new UsuarioBO();
				usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
				usuarioBasico.setCorreo_Electronico(usuario
						.getAttribute("correo"));
				usuarioBasico.setIdentificador(usuario.getAttributeAsInt("id"));
				usuarioBasico.setNombres(usuario.getAttribute("nombre"));
				resultado.add(usuarioBasico);
			}

			return resultado;
		} else {
			return null;
		}
	}

}
