package co.mind.management.maestro.client.procesos;

import java.util.List;

import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.PruebaListGridRecord;

import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.Layout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelProcesos extends VLayout {

	private PanelContenidoProcesos panelContenidoProcesos;
	private Canvas canvasEncabezado;
	private HTMLFlow titulo;
	private HTMLFlow subtitulo;
	private Img imagenLogo;

	public PanelProcesos() {
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		setPadding(15);

		canvasEncabezado = new Canvas();
		canvasEncabezado.setSize("100%", "70px");
		canvasEncabezado.setBackgroundColor("white");
		panelContenidoProcesos = new PanelContenidoProcesos();

		inicializarEncabezado();

		titulo.setContents("<h1>" + "Procesos de Evaluación" + "</h1>");
		subtitulo.setContents("Programe sus pruebas a su criterio");

		addMember(canvasEncabezado);
		addMember(panelContenidoProcesos);

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

	public void actualizarProcesos(ProcesoRecord[] procesos) {
		panelContenidoProcesos.actualizarProcesos(procesos);
	}

	public void actualizarListaPruebas(List<PruebaUsuarioBO> pruebas) {
		panelContenidoProcesos.actualizarListaPruebas(pruebas);
	}

	public void actualizarListaUsuariosBasicos(List<EvaluadoBO> usuarios) {
		panelContenidoProcesos.actualizarListaUsuariosBasicos(usuarios);
	}

	public void actualizarParticipaciones(
List<ParticipacionEnProcesoBO> result) {
		panelContenidoProcesos
				.actualizarParticipaciones(result);
	}

	public void actualizarResultados(
			List<ParticipacionEnProcesoBO> result) {
		panelContenidoProcesos
				.actualizarResultados(result);
	}

	public void setEstadoInicial() {
		panelContenidoProcesos.setEstadoInicial();
	}

	public void actualizarTemasProceso(List<PruebaUsuarioBO> result) {
		panelContenidoProcesos.actualizarTemasProceso(result);
		
	}

}
