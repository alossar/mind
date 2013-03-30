package co.mind.management.maestro.client.programadores;

import co.mind.management.shared.bo.UsuarioBO;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelProgramadores extends VLayout {

	private PanelContenidoProgramadores panelContenidoProgramadores;
	private Canvas canvasEncabezado;
	private HTMLFlow titulo;
	private HTMLFlow subtitulo;
	private Img imagenLogo;

	public PanelProgramadores() {
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		setPadding(15);

		canvasEncabezado = new Canvas();
		canvasEncabezado.setSize("100%", "70px");
		canvasEncabezado.setBackgroundColor("white");
		panelContenidoProgramadores = new PanelContenidoProgramadores();

		inicializarEncabezado();

		titulo.setContents("<h1>" + "Programadores" + "</h1>");
		subtitulo.setContents("Personal de los procesos");

		addMember(canvasEncabezado);
		addMember(panelContenidoProgramadores);

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
