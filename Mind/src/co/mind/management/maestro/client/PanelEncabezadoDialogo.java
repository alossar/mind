package co.mind.management.maestro.client;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;

public class PanelEncabezadoDialogo extends Canvas {

	private HTMLFlow titulo;
	private HTMLFlow subtitulo;
	private Img imagenLogo;

	public PanelEncabezadoDialogo(String titulo, String subtitulo,
			String urlImagen) {
		setSize("100%", "90px");
		setBackgroundColor("white");

		inicializarEncabezado(titulo, subtitulo, urlImagen);

	}

	private void inicializarEncabezado(String titulo, String subtitulo,
			String urlImagen) {
		this.titulo = new HTMLFlow();
		this.titulo.setTop(60);
		this.titulo.setLeft(0);
		this.titulo.setWidth100();

		this.subtitulo = new HTMLFlow();
		this.subtitulo.setTop(85);
		this.subtitulo.setLeft(0);
		this.subtitulo.setWidth100();

		imagenLogo = new Img();
		imagenLogo.setTop(10);
		imagenLogo.setLeft("45%");
		imagenLogo.setWidth(43);
		imagenLogo.setHeight(43);

		addChild(imagenLogo);
		addChild(this.titulo);
		addChild(this.subtitulo);

		this.titulo.setContents("<h1><center>" + titulo + "</center></h1>");
		this.subtitulo.setContents("<center>" + subtitulo + "</center>");
		this.imagenLogo.setSrc(urlImagen);
	}

	public void actualizarEncabezado(String titulo, String subtitulo,
			String urlImagen) {
		this.titulo.setContents("<h1><center>" + titulo + "</center></h1>");
		this.subtitulo.setContents("<center>" + subtitulo + "</center>");
		this.imagenLogo.setSrc(urlImagen);
	}

}
