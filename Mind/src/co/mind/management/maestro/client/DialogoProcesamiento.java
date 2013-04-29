package co.mind.management.maestro.client;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;

public class DialogoProcesamiento extends Window {

	public DialogoProcesamiento(String mensaje) {
		this.setShowCloseButton(false);
		this.setShowMinimizeButton(false);
		this.setShowBody(true);
		this.setShowHeader(true);
		this.setShowMaximizeButton(false);
		this.setTitle("");
		this.setShowTitle(false);
		this.setAutoCenter(true);
		this.setAlign(VerticalAlignment.CENTER);
		this.setLayoutAlign(VerticalAlignment.CENTER);
		this.setOverflow(Overflow.HIDDEN);
		this.setCanDragReposition(false);
		this.setCanDragResize(false);
		this.setWidth(300);
		this.setHeight(100);
		this.setShowModalMask(true);
		this.setIsModal(true);
		this.addItem(obtenerCanvasContenido(mensaje));
	}

	private Canvas obtenerCanvasContenido(String mensaje) {
		Canvas canvas = new Canvas();
		canvas.setWidth100();
		canvas.setHeight100();
		canvas.setLayoutAlign(VerticalAlignment.CENTER);
		canvas.setBackgroundColor("white");

		Label label = new Label();
		label.setHeight(30);
		label.setWidth("100%");
		label.setTop(0);
		label.setLeft(0);
		label.setContents("<center>" + mensaje + "</center>");

		Img imagenCargando = new Img("img/loading.gif", 220, 19);
		imagenCargando.setTop("20px");
		imagenCargando.setLeft("40px");

		canvas.addChild(label);
		canvas.addChild(imagenCargando);

		return canvas;
	}

}
