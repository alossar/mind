package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ParticipacionEnProcesoListGridRecord extends ListGridRecord {

	public ParticipacionEnProcesoListGridRecord(int idEval, int cedula,
			String nombre, String apellidos, String correo, int idUsuario,
			int edad, int idParticipacion, String codigo, String estado) {
		setAttribute("idParticipacion", idParticipacion);
		setAttribute("codigo", codigo);
		setAttribute("estado", estado);
		setAttribute("idEval", cedula);
		setAttribute("identificadorEval", idEval);
		setAttribute("nombre", nombre);
		setAttribute("apellidos", apellidos);
		setAttribute("correo", correo);
		setAttribute("idUsuario", idUsuario);
		setAttribute("edad", edad);
	}

	public static ParticipacionEnProcesoListGridRecord[] getRecords(
			List<ParticipacionEnProcesoBO> participaciones) {
		List<ParticipacionEnProcesoListGridRecord> resultado = new ArrayList<ParticipacionEnProcesoListGridRecord>();
		if (participaciones != null) {
			for (ParticipacionEnProcesoBO participacion : participaciones) {
				ParticipacionEnProcesoListGridRecord imagen = new ParticipacionEnProcesoListGridRecord(
						participacion.getUsuarioBasico().getIdentificador(),
						participacion.getUsuarioBasico().getCedula(),
						participacion.getUsuarioBasico().getNombres(),
						participacion.getUsuarioBasico().getApellidos(),
						participacion.getUsuarioBasico().getCorreoElectronico(),
						participacion.getUsuarioBasico()
								.getIdentificadorUsuarioAdministrador(),
						participacion.getUsuarioBasico().getEdad(),
						participacion.getIdentificador(), participacion
								.getCodigo_Acceso(),
						estadoParticipacionRecord(participacion.getEstado()));
				resultado.add(imagen);
			}
			ParticipacionEnProcesoListGridRecord[] records = new ParticipacionEnProcesoListGridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		} else {
			return new ParticipacionEnProcesoListGridRecord[0];
		}
	}

	private static String estadoParticipacionRecord(String estado) {
		if (estado
				.equalsIgnoreCase(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_EJECUCION)) {
			return "En Proceso";
		} else if (estado
				.equalsIgnoreCase(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_ESPERA)) {
			return "Programada";
		} else if (estado
				.equalsIgnoreCase(Convencion.ESTADO_PARTICIPACION_EN_PROCESO_INACTIVA)) {
			return "Finalizada";
		}
		return null;
	}

	private static String estadoParticipacionBO(String estado) {
		if (estado.equalsIgnoreCase("En Proceso")) {
			return Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_EJECUCION;
		} else if (estado.equalsIgnoreCase("Programada")) {
			return Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_ESPERA;
		} else if (estado.equalsIgnoreCase("Finalizada")) {
			return Convencion.ESTADO_PARTICIPACION_EN_PROCESO_INACTIVA;
		}
		return null;
	}

	public static List<ParticipacionEnProcesoBO> getBO(
			ParticipacionEnProcesoListGridRecord[] records) {
		List<ParticipacionEnProcesoBO> resultado = new ArrayList<ParticipacionEnProcesoBO>();
		if (records != null) {
			for (ParticipacionEnProcesoListGridRecord usuario : records) {
				EvaluadoBO usuarioBasico = new EvaluadoBO();
				usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
				usuarioBasico.setCorreoElectronico(usuario
						.getAttribute("correo"));
				usuarioBasico.setEdad(usuario.getAttributeAsInt("edad"));
				usuarioBasico.setIdentificador(usuario
						.getAttributeAsInt("identificadorEval"));
				usuarioBasico.setCedula(usuario.getAttributeAsInt("idEval"));
				usuarioBasico.setNombres(usuario.getAttribute("nombre"));
				usuarioBasico.setIdentificadorUsuarioAdministrador(usuario
						.getAttributeAsInt("idUsuario"));
				ParticipacionEnProcesoBO participacion = new ParticipacionEnProcesoBO();
				participacion.setCodigo_Acceso(usuario.getAttribute("codigo"));
				participacion.setIdentificador(usuario
						.getAttributeAsInt("idParticipacion"));
				participacion.setEstado(estadoParticipacionBO(usuario
						.getAttribute("estado")));
				participacion.setUsuarioBasico(usuarioBasico);
				resultado.add(participacion);
			}

			return resultado;
		} else {
			return null;
		}
	}

	public ParticipacionEnProcesoBO getBO(
			ParticipacionEnProcesoListGridRecord usuario) {
		EvaluadoBO usuarioBasico = new EvaluadoBO();
		usuarioBasico.setApellidos(usuario.getAttribute("apellidos"));
		usuarioBasico.setCorreoElectronico(usuario.getAttribute("correo"));
		usuarioBasico.setEdad(usuario.getAttributeAsInt("edad"));
		usuarioBasico.setIdentificador(usuario
				.getAttributeAsInt("identificadorEval"));
		usuarioBasico.setCedula(usuario.getAttributeAsInt("idEval"));
		usuarioBasico.setNombres(usuario.getAttribute("nombre"));
		usuarioBasico.setIdentificadorUsuarioAdministrador(usuario
				.getAttributeAsInt("idUsuario"));
		ParticipacionEnProcesoBO participacion = new ParticipacionEnProcesoBO();
		participacion.setCodigo_Acceso(usuario.getAttribute("codigo"));
		participacion.setIdentificador(usuario
				.getAttributeAsInt("idParticipacion"));
		participacion.setEstado(usuario.getAttribute("estado"));
		participacion.setUsuarioBasico(usuarioBasico);
		return participacion;
	}

}
