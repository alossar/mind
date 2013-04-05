package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.bo.PruebaUsuarioBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PruebaListGridRecord extends ListGridRecord {

	public PruebaListGridRecord(int pruebaID, int usuarioID, String nombre,
			String descripcion, int tiempoPrueba, int cantidadPreguntas) {
		setAttribute("pruebaID", pruebaID);
		setAttribute("usuarioID", usuarioID);
		setAttribute("nombre", nombre);
		setAttribute("descripcion", descripcion);
		setAttribute("tiempoPrueba", tiempoPrueba);
		setAttribute("cantidadPreguntas", cantidadPreguntas);
	}

	public static PruebaListGridRecord[] getRecords(
			List<PruebaUsuarioBO> pruebas) {
		if (pruebas != null) {
			List<PruebaListGridRecord> resultado = new ArrayList<PruebaListGridRecord>();
			for (PruebaUsuarioBO pruebaUsuario : pruebas) {
				int tiempo = pruebaUsuario.duracionPrueba() / 60;
				PruebaListGridRecord imagen = new PruebaListGridRecord(
						pruebaUsuario.getIdentificador(),
						pruebaUsuario.getUsuarioAdministradorID(),
						pruebaUsuario.getNombre(),
						pruebaUsuario.getDescripcion(), tiempo,
						pruebaUsuario.cantidadDePreguntas());
				resultado.add(imagen);
			}
			PruebaListGridRecord[] records = new PruebaListGridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		}
		return new PruebaListGridRecord[0];
	}

	public static PruebaUsuarioBO getBO(PruebaListGridRecord prueba) {
		if (prueba != null) {
			PruebaUsuarioBO resultado = new PruebaUsuarioBO();
			resultado.setDescripcion(prueba.getAttribute("descripcion"));
			resultado.setIdentificador(prueba.getAttributeAsInt("pruebaID"));
			resultado.setNombre(prueba.getAttribute("nombre"));
			resultado.setUsuarioAdministradorID(prueba
					.getAttributeAsInt("usuarioID"));
			return resultado;
		}
		return null;
	}

	public static List<PruebaUsuarioBO> getBO(PruebaListGridRecord[] pruebasR) {
		if (pruebasR != null) {
			List<PruebaUsuarioBO> listaResultado = new ArrayList<PruebaUsuarioBO>();
			for (PruebaListGridRecord pruebaListGridRecord : pruebasR) {
				PruebaUsuarioBO resultado = new PruebaUsuarioBO();
				resultado.setDescripcion(pruebaListGridRecord
						.getAttribute("descripcion"));
				resultado.setIdentificador(pruebaListGridRecord
						.getAttributeAsInt("pruebaID"));
				resultado
						.setNombre(pruebaListGridRecord.getAttribute("nombre"));
				resultado.setUsuarioAdministradorID(pruebaListGridRecord
						.getAttributeAsInt("usuarioID"));
				listaResultado.add(resultado);
			}
			return listaResultado;
		}
		return null;
	}
}
