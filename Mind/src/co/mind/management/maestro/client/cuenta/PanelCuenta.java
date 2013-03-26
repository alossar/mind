package co.mind.management.maestro.client.cuenta;

import co.mind.management.shared.bo.UsuarioBO;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelCuenta extends VLayout {

	private PanelContenidoCuenta panelContenidoCuenta;
	private Canvas canvasEncabezado;
	private HTMLFlow titulo;
	private HTMLFlow subtitulo;
	private Img imagenLogo;

	public PanelCuenta(UsuarioBO usuario) {
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		setPadding(15);

		canvasEncabezado = new Canvas();
		canvasEncabezado.setSize("100%", "20%");
		canvasEncabezado.setBackgroundColor("white");
		panelContenidoCuenta = new PanelContenidoCuenta();

		inicializarEncabezado();

		titulo.setContents("<h1>" + "Información de la Cuenta" + "</h1>");
		subtitulo.setContents(usuario.getNombres() + " "
				+ usuario.getApellidos());
		setInformacion(usuario);

		addMember(canvasEncabezado);
		addMember(panelContenidoCuenta);

	}

	private void inicializarEncabezado() {
		titulo = new HTMLFlow();
		titulo.setTop(25);
		titulo.setLeft(70);

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

	private void setInformacion(UsuarioBO usuario) {
		panelContenidoCuenta.setInformacionContacto(usuario);
	}

}
