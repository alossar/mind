package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.dto.UsuarioProgramadorBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ProgramadorListGridRecord extends ListGridRecord {

	public ProgramadorListGridRecord(int id, String nombre, String apellidos,
			String correo, String empresa, String cargo, String ciudad,
			String telefono, String celular, String estado) {
		setAttribute("id", id);
		setAttribute("nombre", nombre);
		setAttribute("apellidos", apellidos);
		setAttribute("correo", correo);
		setAttribute("empresa", empresa);
		setAttribute("cargo", cargo);
		setAttribute("ciudad", ciudad);
		setAttribute("telefono", telefono);
		setAttribute("celular", celular);
		setAttribute("estado", estado);
	}

	public static ProgramadorListGridRecord[] getRecords(
			List<UsuarioProgramadorBO> records2) {
		List<ProgramadorListGridRecord> resultado = new ArrayList<ProgramadorListGridRecord>();
		if (records2 != null) {
			for (UsuarioBO u : records2) {
				UsuarioProgramadorBO usuario = (UsuarioProgramadorBO) u;
				ProgramadorListGridRecord imagen = new ProgramadorListGridRecord(
						usuario.getIdentificador(), usuario.getNombres(),
						usuario.getApellidos(),
						usuario.getCorreo_Electronico(), usuario.getEmpresa(),
						usuario.getCargo(), usuario.getCiudad(),
						usuario.getTelefono(), usuario.getTelefono_Celular(),
						usuario.getEstado_Cuenta());
				resultado.add(imagen);
			}
			ProgramadorListGridRecord[] records = new ProgramadorListGridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		} else {
			return new ProgramadorListGridRecord[0];
		}
	}

	public static List<UsuarioBO> getBO(ProgramadorListGridRecord[] records) {
		List<UsuarioBO> resultado = new ArrayList<UsuarioBO>();
		if (records != null) {
			for (ProgramadorListGridRecord usuario : records) {
				UsuarioBO usuarioBasico = new UsuarioBO();
				usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
				usuarioBasico.setCorreo_Electronico(usuario
						.getAttribute("correo"));
				usuarioBasico.setIdentificador(usuario.getAttributeAsInt("id"));
				usuarioBasico.setNombres(usuario.getAttribute("nombre"));
				usuarioBasico.setCargo(usuario.getAttribute("cargo"));
				usuarioBasico.setTelefono(usuario.getAttribute("telefono"));
				usuarioBasico.setTelefono_Celular(usuario
						.getAttribute("celular"));
				usuarioBasico.setEmpresa(usuario.getAttribute("empresa"));
				usuarioBasico.setCiudad(usuario.getAttribute("ciudad"));
				usuarioBasico.setEstado_Cuenta(usuario.getAttribute("estado"));
				resultado.add(usuarioBasico);
			}

			return resultado;
		} else {
			return null;
		}
	}

	public static UsuarioBO getBO(ProgramadorListGridRecord usuario) {
		if (usuario != null) {
			UsuarioBO usuarioBasico = new UsuarioBO();
			usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
			usuarioBasico.setCorreo_Electronico(usuario.getAttribute("correo"));
			usuarioBasico.setIdentificador(usuario.getAttributeAsInt("id"));
			usuarioBasico.setNombres(usuario.getAttribute("nombre"));
			usuarioBasico.setCargo(usuario.getAttribute("cargo"));
			usuarioBasico.setTelefono(usuario.getAttribute("telefono"));
			usuarioBasico.setTelefono_Celular(usuario.getAttribute("celular"));
			usuarioBasico.setEmpresa(usuario.getAttribute("empresa"));
			usuarioBasico.setCiudad(usuario.getAttribute("ciudad"));
			usuarioBasico.setEstado_Cuenta(usuario.getAttribute("estado"));
			return usuarioBasico;
		} else {
			return new UsuarioBO();
		}
	}

}
