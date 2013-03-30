package co.mind.management.maestro.client.administradores;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelAdministradores extends VLayout {

	private PanelContenidoAdministradores panelContenidoImagenes;
	private Canvas canvasEncabezado;
	private HTMLFlow titulo;
	private HTMLFlow subtitulo;
	private Img imagenLogo;

	public PanelAdministradores() {
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		setPadding(15);

		canvasEncabezado = new Canvas();
		canvasEncabezado.setSize("100%", "10%");
		canvasEncabezado.setBackgroundColor("white");
		panelContenidoImagenes = new PanelContenidoAdministradores();

		inicializarEncabezado();

		titulo.setContents("<h1>" + "Repositorio de Imágenes" + "</h1>");
		subtitulo.setContents("Administre las imagenes del sistema");

		addMember(canvasEncabezado);
		addMember(panelContenidoImagenes);

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

}