package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class ParticipacionEnProcesoListGridRecord extends ListGridRecord {

	public ParticipacionEnProcesoListGridRecord(int idEval, int cedula,
			String nombre, String apellidos, String correo, int idUsuario,
			int edad, int idParticipacion, String codigo, String estado,
			String procesoNombre, String procesoDesc, String notificado) {
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
		setAttribute("estaNotificado", notificado);
		setAttribute("procesoNombre", procesoNombre);
		setAttribute("procesoDesc", procesoDesc);
	}

	public static ParticipacionEnProcesoListGridRecord[] getRecords(
			List<ParticipacionEnProcesoBO> participaciones) {
		List<ParticipacionEnProcesoListGridRecord> resultado = new ArrayList<ParticipacionEnProcesoListGridRecord>();
		if (participaciones != null) {
			for (ParticipacionEnProcesoBO participacion : participaciones) {

				ProcesoUsuarioBO proceso = participacion.getProceso();
				String nombreProceso = null;
				String descProceso = null;
				if (proceso != null) {
					nombreProceso = proceso.getNombre();
					descProceso = proceso.getDescripcion();
				}
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
						estadoParticipacionRecord(participacion.getEstado()),
						nombreProceso, descProceso,
						estadoNotificacionCorreoRecord(participacion
								.getEstaNotificado()));
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
		if (estado != null) {
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
		}
		return null;
	}

	private static String estadoNotificacionCorreoRecord(String estado) {
		if (estado != null) {
			if (estado.equalsIgnoreCase(Convencion.ESTADO_NOTIFICACION_ENVIADA)) {
				return "Notificación enviada";
			} else if (estado
					.equalsIgnoreCase(Convencion.ESTADO_NOTIFICACION_NO_ENVIADA)) {
				return "Notificación aún no enviada";
			}
		}
		return null;
	}

	private static String estadoParticipacionBO(String estado) {
		if (estado != null) {
			if (estado.equalsIgnoreCase("En Proceso")) {
				return Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_EJECUCION;
			} else if (estado.equalsIgnoreCase("Programada")) {
				return Convencion.ESTADO_PARTICIPACION_EN_PROCESO_EN_ESPERA;
			} else if (estado.equalsIgnoreCase("Finalizada")) {
				return Convencion.ESTADO_PARTICIPACION_EN_PROCESO_INACTIVA;
			}
		}
		return null;
	}

	private static String estadoNotificacionCorreoBO(String estado) {
		if (estado != null) {
			if (estado.equalsIgnoreCase("Notificación enviada")) {
				return Convencion.ESTADO_NOTIFICACION_ENVIADA;
			} else if (estado.equalsIgnoreCase("Notificación aún no enviada")) {
				return Convencion.ESTADO_NOTIFICACION_NO_ENVIADA;
			}
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
				participacion
						.setEstaNotificado(estadoNotificacionCorreoBO(usuario
								.getAttribute("estaNotificado")));
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
		participacion.setEstaNotificado(estadoNotificacionCorreoBO(usuario
				.getAttribute("estaNotificado")));
		return participacion;
	}

}
