package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;

import com.smartgwt.client.widgets.grid.ListGridRecord;

public class PreguntaCategoriaListGridRecord extends ListGridRecord {

	public PreguntaCategoriaListGridRecord(int idCategoria, int idPregunta,
			int idImagenUsuario, String pregunta, String imagen,
			int posicionPreguntaX, int posicionPreguntaY, int caracteresMaximo,
			int tiempoMaximo) {
		setAttribute("idPrueba", idCategoria);
		setAttribute("idPregunta", idPregunta);
		setAttribute("idImagenUsuario", idImagenUsuario);
		setAttribute("pregunta", pregunta);
		setAttribute("imagen", imagen);
		setAttribute("posicionX", posicionPreguntaX);
		setAttribute("posicionY", posicionPreguntaY);
		setAttribute("caracteres", caracteresMaximo);
		setAttribute("tiempo", tiempoMaximo);

	}

	public static PreguntaCategoriaListGridRecord[] getRecords(
			List<PreguntaUsuarioBO> preguntas) {
		List<PreguntaCategoriaListGridRecord> resultado = new ArrayList<PreguntaCategoriaListGridRecord>();
		if (preguntas != null) {
			for (PreguntaUsuarioBO pregunta : preguntas) {
				PreguntaCategoriaListGridRecord imagen = new PreguntaCategoriaListGridRecord(
						pregunta.getPruebaUsuario().getIdentificador(),
						pregunta.getIdentificador(), pregunta
								.getImagenesUsuario().getIdentificador(),
						pregunta.getPregunta(), pregunta.getImagenesUsuario()
								.getImagen().getImagenURI(),
						pregunta.getPosicionPreguntaX(),
						pregunta.getPosicionPreguntaY(),
						pregunta.getCaracteresMaximo(),
						pregunta.getTiempoMaximo());
				resultado.add(imagen);
			}
			PreguntaCategoriaListGridRecord[] records = new PreguntaCategoriaListGridRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		} else {
			return new PreguntaCategoriaListGridRecord[0];
		}
	}

	public static List<PreguntaUsuarioBO> getBO(
			PreguntaCategoriaListGridRecord[] preguntas) {
		if (preguntas.length > 0) {
			List<PreguntaUsuarioBO> resultado = new ArrayList<PreguntaUsuarioBO>();
			for (PreguntaCategoriaListGridRecord pregunta : preguntas) {

				PreguntaUsuarioBO p = new PreguntaUsuarioBO();
				p.setIdentificador(pregunta
						.getAttributeAsInt("idPreguntaCategoria"));
				PruebaUsuarioBO cat = new PruebaUsuarioBO();
				cat.setIdentificador(pregunta.getAttributeAsInt("idPrueba"));
				cat.setUsuarioAdministradorID(pregunta
						.getAttributeAsInt("idUsuario"));
				cat.setNombre(pregunta.getAttribute("nombreCategoria"));
				p.setPruebaUsuario(cat);
				p.setCaracteresMaximo(pregunta.getAttributeAsInt("caracteres"));
				p.setIdentificador(pregunta.getAttributeAsInt("idPregunta"));
				p.setPosicionPreguntaX(pregunta.getAttributeAsInt("posicionX"));
				p.setPosicionPreguntaY(pregunta.getAttributeAsInt("posicionY"));
				p.setPregunta(pregunta.getAttribute("pregunta"));
				p.setTiempoMaximo(pregunta.getAttributeAsInt("tiempo"));
				ImagenUsuarioBO imagen = new ImagenUsuarioBO();
				imagen.setIdentificador(pregunta
						.getAttributeAsInt("idImagenUsuario"));
				p.setImagenesUsuarioID(imagen);
				resultado.add(p);
			}
			return resultado;
		}
		return null;
	}
}
