package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class UsuarioBasicoListGridRecord extends ListGridRecord {

	public UsuarioBasicoListGridRecord(int id, int cedula, String nombre,
			String apellidos, String correo, int idUsuario, int edad) {
		setAttribute("id", cedula);
		setAttribute("identificador", id);
		setAttribute("nombre", nombre);
		setAttribute("apellidos", apellidos);
		setAttribute("correo", correo);
		setAttribute("idUsuario", idUsuario);
		setAttribute("edad", edad);
	}

	public static UsuarioBasicoListGridRecord[] getRecords(
			List<EvaluadoBO> usuarios) {
		List<UsuarioBasicoListGridRecord> resultado = new ArrayList<UsuarioBasicoListGridRecord>();
		if (usuarios != null) {
			for (EvaluadoBO usuario : usuarios) {
				UsuarioBasicoListGridRecord imagen = new UsuarioBasicoListGridRecord(
						usuario.getIdentificador(), usuario.getCedula(),
						usuario.getNombres(), usuario.getApellidos(),
						usuario.getCorreoElectronico(),
						usuario.getIdentificadorUsuarioAdministrador(),
						usuario.getEdad());
				resultado.add(imagen);
			}
			UsuarioBasicoListGridRecord[] records = new UsuarioBasicoListGridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		} else {
			return new UsuarioBasicoListGridRecord[0];
		}
	}

	public static List<EvaluadoBO> getBO(UsuarioBasicoListGridRecord[] records) {
		List<EvaluadoBO> resultado = new ArrayList<EvaluadoBO>();
		if (records != null) {
			for (UsuarioBasicoListGridRecord usuario : records) {
				EvaluadoBO usuarioBasico = new EvaluadoBO();
				usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
				usuarioBasico.setCorreoElectronico(usuario
						.getAttribute("correo"));
				usuarioBasico.setEdad(usuario.getAttributeAsInt("edad"));
				usuarioBasico.setIdentificador(usuario
						.getAttributeAsInt("identificador"));
				usuarioBasico.setCedula(usuario.getAttributeAsInt("id"));
				usuarioBasico.setNombres(usuario.getAttribute("nombre"));
				usuarioBasico.setIdentificadorUsuarioAdministrador(usuario
						.getAttributeAsInt("idUsuario"));
				resultado.add(usuarioBasico);
			}

			return resultado;
		} else {
			return null;
		}
	}

	public static EvaluadoBO getBO(UsuarioBasicoListGridRecord usuario) {
		if (usuario != null) {
			EvaluadoBO usuarioBasico = new EvaluadoBO();
			usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
			usuarioBasico.setCorreoElectronico(usuario.getAttribute("correo"));
			usuarioBasico.setEdad(usuario.getAttributeAsInt("edad"));
			usuarioBasico.setIdentificador(usuario
					.getAttributeAsInt("identificador"));
			usuarioBasico.setCedula(usuario.getAttributeAsInt("id"));
			usuarioBasico.setNombres(usuario.getAttribute("nombre"));
			usuarioBasico.setIdentificadorUsuarioAdministrador(usuario
					.getAttributeAsInt("idUsuario"));
			return usuarioBasico;
		}
		return null;
	}
}
