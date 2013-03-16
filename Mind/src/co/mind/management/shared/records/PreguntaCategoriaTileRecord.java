package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import com.smartgwt.client.widgets.tile.TileRecord;

import co.mind.management.shared.bo.ImagenBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;

public class PreguntaCategoriaTileRecord extends TileRecord {

	public PreguntaCategoriaTileRecord(int idPrueba, int idPregunta,
			int idImagenUsuario, String pregunta, String imagen,
			int posicionPreguntaX, int posicionPreguntaY, int caracteresMaximo,
			int tiempoMaximo) {
		setAttribute("idPrueba", idPrueba);
		setAttribute("idPregunta", idPregunta);
		setAttribute("idImagenUsuario", idImagenUsuario);
		setAttribute("pregunta", pregunta);
		setAttribute("imagen", imagen);
		setAttribute("posicionX", posicionPreguntaX);
		setAttribute("posicionY", posicionPreguntaY);
		setAttribute("caracteres", caracteresMaximo);
		setAttribute("tiempo", tiempoMaximo);

	}

	public static PreguntaCategoriaTileRecord[] getRecords(
			List<PreguntaUsuarioBO> preguntas) {
		List<PreguntaCategoriaTileRecord> resultado = new ArrayList<PreguntaCategoriaTileRecord>();
		if (preguntas != null) {
			for (PreguntaUsuarioBO pregunta : preguntas) {
				PreguntaCategoriaTileRecord imagen = new PreguntaCategoriaTileRecord(
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
			PreguntaCategoriaTileRecord[] records = new PreguntaCategoriaTileRecord[resultado
					.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		} else {
			return new PreguntaCategoriaTileRecord[0];
		}
	}

	public static List<PreguntaUsuarioBO> getBO(
			PreguntaCategoriaTileRecord[] preguntas) {
		if (preguntas.length > 0) {
			List<PreguntaUsuarioBO> resultado = new ArrayList<PreguntaUsuarioBO>();
			for (PreguntaCategoriaTileRecord pregunta : preguntas) {
				PreguntaUsuarioBO p = new PreguntaUsuarioBO();
				PruebaUsuarioBO cat = new PruebaUsuarioBO();
				cat.setIdentificador(pregunta.getAttributeAsInt("idCategoria"));
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
				ImagenBO img = new ImagenBO();
				img.setImagenURI(pregunta.getAttribute("imagen"));
				imagen.setImagene(img);
				p.setImagenesUsuarioID(imagen);
				resultado.add(p);
			}
			return resultado;
		}
		return null;
	}

	public static PreguntaUsuarioBO getBO(PreguntaCategoriaTileRecord pregunta) {
		if (pregunta != null) {

			PreguntaUsuarioBO p = new PreguntaUsuarioBO();
			PruebaUsuarioBO cat = new PruebaUsuarioBO();
			cat.setIdentificador(pregunta.getAttributeAsInt("idPrueba"));
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
			ImagenBO img = new ImagenBO();
			img.setImagenURI(pregunta.getAttribute("imagen"));
			imagen.setImagene(img);
			p.setImagenesUsuarioID(imagen);
			return p;
		}
		return null;
	}
}
