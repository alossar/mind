package co.mind.management.maestro.client.evaluados;

import java.util.List;

import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelEvaluados extends VLayout {

	private PanelContenidoEvaluados panelContenidoEvaluados;
	private Canvas canvasEncabezado;
	private HTMLFlow titulo;
	private HTMLFlow subtitulo;
	private Img imagenLogo;

	public PanelEvaluados() {
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		setPadding(15);

		canvasEncabezado = new Canvas();
		canvasEncabezado.setSize("100%", "70px");
		canvasEncabezado.setBackgroundColor("white");
		panelContenidoEvaluados = new PanelContenidoEvaluados();

		inicializarEncabezado();

		titulo.setContents("<h1>" + "Evaluados" + "</h1>");
		subtitulo.setContents("Personas para evaluar");

		addMember(canvasEncabezado);
		addMember(panelContenidoEvaluados);

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

	public void actualizarListaUsuariosBasicos(
			List<EvaluadoBO> result) {
		panelContenidoEvaluados.actualizarUsuariosBasicos(result);

	}

}
