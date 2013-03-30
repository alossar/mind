package co.mind.management.maestro.client.temas;

import java.util.List;

import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.records.ImagenRecord;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.PruebaListGridRecord;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelTemas extends VLayout {

	private PanelContenidoTemas panelContenidoTemas;
	private Canvas canvasEncabezado;
	private static HTMLFlow titulo;
	private static HTMLFlow subtitulo;
	private static Img imagenLogo;

	public PanelTemas() {
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		setPadding(15);

		canvasEncabezado = new Canvas();
		canvasEncabezado.setSize("100%", "70px");
		canvasEncabezado.setBackgroundColor("white");
		panelContenidoTemas = new PanelContenidoTemas();

		inicializarEncabezado();

		titulo.setContents("<h1>" + "Temas" + "</h1>");
		subtitulo.setContents("Agrupe el contenido de sus evaluaciones");
		imagenLogo.setSrc("img/check.png");

		addMember(canvasEncabezado);
		addMember(panelContenidoTemas);

	}

	private void inicializarEncabezado() {
		titulo = new HTMLFlow();
		titulo.setTop(25);
		titulo.setLeft(70);
		titulo.setWidth("90%");

		subtitulo = new HTMLFlow();
		subtitulo.setTop(60);
		subtitulo.setLeft(70);

		imagenLogo = new Img();
		imagenLogo.setTop(25);
		imagenLogo.setLeft(25);
		imagenLogo.setWidth(43);
		imagenLogo.setHeight(43);

		canvasEncabezado.addChild(imagenLogo);
		canvasEncabezado.addChild(titulo);
		canvasEncabezado.addChild(subtitulo);
	}

	public static void setEncabezado(String t, String s, String urlImagen) {
		titulo.setContents("<h1>" + t + "</h1>");
		subtitulo.setContents(s);
		imagenLogo.setSrc("img/check.png");

	}

	public void actualizarPruebas(List<PruebaUsuarioBO> result) {
		panelContenidoTemas.actualizarPruebas(result);
	}

	public void actualizarPreguntasCategoria(
			List<PreguntaUsuarioBO> result) {
		panelContenidoTemas.actualizarPreguntasCategoria(result);
	}

	public void actualizarImagenesUsuario(List<ImagenUsuarioBO> result) {
		panelContenidoTemas.actualizarImagenesUsuario(result);
	}

	public void agregarPreguntaPrueba(PreguntaUsuarioBO bo) {
		panelContenidoTemas.agregarPreguntaPrueba(bo);
	}

	public void editarPreguntaCategoria(PreguntaUsuarioBO bo) {
		panelContenidoTemas.editarPreguntaCategoria(bo);
	}

	public void setEstadoInicial() {
		panelContenidoTemas.setEstadoInicial();
	}

	public static void setEncabezadoPredeterminado() {
		titulo.setContents("<h1>" + "Temas" + "</h1>");
		subtitulo.setContents("Agrupe el contenido de sus evaluaciones");
		imagenLogo.setSrc("img/check.png");
	}

}
