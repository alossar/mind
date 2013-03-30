package co.mind.management.maestro.client.temas.contenedores;

import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.DragAppearance;
import com.smartgwt.client.types.ImageStyle;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.DrawSector;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;

public class PanelModoEdicionPregunta extends Canvas {

	private DrawPane panelReloj;
	private DrawSector reloj;
	private DrawSector marco;
	private String colorTiempoRestante = "#FFFFFF";
	private Img imagenLamina;
	private int radioReloj = 30;
	private double tiempoLamina = 120;
	private float anguloInicial = -90;
	private Canvas labelPregunta;
	private StaticTextItem textTiempo;
	private int posicionXDefault = 50;
	private int posicionYDefault = 50;

	private int anchoLamina = 600;
	private int altoLamina = 400;
	private int anchoPregunta = 167;
	private int altoPregunta = 113;

	public PanelModoEdicionPregunta() {
		setWidth(anchoLamina);
		setHeight(altoLamina);
		setLeft(0);
		setTop(0);

		inicializarRespuesta();
		inicializarReloj();

		DynamicForm form = new DynamicForm();
		form.setWidth(200);
		form.setHeight(30);
		form.setLeft(350);
		form.setTop(0);
		textTiempo = new StaticTextItem();
		textTiempo.setTitle("Tiempo");
		form.setFields(textTiempo);
		addChild(form);
		draw();
	}

	private void inicializarRespuesta() {

		imagenLamina = new Img();
		imagenLamina.setWidth(anchoLamina);
		imagenLamina.setHeight(altoLamina);
		imagenLamina.setImageType(ImageStyle.STRETCH);
		addChild(imagenLamina);

		labelPregunta = new Canvas("Pregunta");
		labelPregunta.setWidth(anchoPregunta);
		labelPregunta.setMaxWidth(anchoPregunta);
		labelPregunta.setHeight(altoPregunta);
		labelPregunta.setMaxHeight(altoPregunta);
		labelPregunta.setPadding(8);
		labelPregunta.setBackgroundColor("white");
		labelPregunta.setShowEdges(true);
		labelPregunta.setShadowOffset(10);
		labelPregunta.setShadowSoftness(10);
		labelPregunta.setTop(posicionYDefault);
		labelPregunta.setLeft(posicionXDefault);
		labelPregunta.setKeepInParentRect(true);
		labelPregunta.setCanDragResize(false);
		labelPregunta.setOverflow(Overflow.AUTO);

		Canvas c = new Canvas();
		c.setSize("600px", "300px");
		c.setMaxHeight(300);
		c.setMaxWidth(600);
		c.setTop(0);
		c.setLeft(0);

		c.addChild(labelPregunta);
		addChild(c);

	}

	private void inicializarReloj() {
		panelReloj = new DrawPane();
		panelReloj.setHeight(110);
		panelReloj.setWidth(110);
		panelReloj.setTop(10);
		panelReloj.setLeft(395);
		panelReloj.setEdgeSize(4);
		panelReloj.setBackgroundColor("transparent");
		panelReloj.setCursor(Cursor.AUTO);

		marco = new DrawSector();
		marco.setDrawPane(panelReloj);
		marco.setCenterPoint(new Point(53, 53));
		marco.setStartAngle(anguloInicial);
		marco.setEndAngle(360);
		marco.setRadius(radioReloj);
		marco.setFillColor(colorTiempoRestante);
		marco.draw();

		reloj = new DrawSector();
		reloj.setDrawPane(panelReloj);
		reloj.setCenterPoint(new Point(53, 53));
		reloj.setStartAngle(anguloInicial);
		reloj.setEndAngle(anguloInicial);
		reloj.setRadius(radioReloj);
		reloj.draw();
		addChild(panelReloj);
	}

	public void cambiarImagen(String string) {
		imagenLamina.setSrc(string);
	}

	public void cambiarTextoPregunta(String pregunta) {
		labelPregunta.setContents(pregunta);

	}

	public void actualizarTiempo(int segundos) {
		tiempoLamina = segundos;
		textTiempo.setValue(Convencion
				.obtenerTiempoMinutoSegundos(tiempoLamina));
	}

	public void actualizarPosicionPregunta(int posicionX, int posicionY,
			int ancho, int alto) {
		if (ancho != 0 && alto != 0) {

			labelPregunta.setRect(posicionX, posicionY,
					labelPregunta.getWidth(), labelPregunta.getHeight());

		}
		if (alto != 0) {
			labelPregunta.setHeight(alto);
		}
		if (ancho != 0) {

			labelPregunta.setWidth(ancho);
		}

	}

	public void habilitarEdicion() {
		labelPregunta.setCanDragReposition(true);
		labelPregunta.setDragAppearance(DragAppearance.TARGET);

	}

	public void deshabilitarEdicion() {

		labelPregunta.setCanDragReposition(false);
		labelPregunta.setDragAppearance(DragAppearance.TARGET);

	}

	public int obtenerPosicionX() {
		return labelPregunta.getLeft();

	}

	public int obtenerPosicionY() {
		return labelPregunta.getTop();

	}

	public void setPosicionPregunta(int posicionPreguntaX, int posicionPreguntaY) {
		labelPregunta.setLeft(posicionPreguntaY);
		labelPregunta.setTop(posicionPreguntaY);

	}

	public void limpiarCampos() {
		labelPregunta.setLeft(posicionXDefault);
		labelPregunta.setTop(posicionYDefault);
		labelPregunta.setContents("");
		setBackgroundImage(null);

	}

}
