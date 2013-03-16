package co.mind.management.evaluacion.client;

import java.util.List;

import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ResultadoBO;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.user.client.Timer;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.drawing.DrawOval;
import com.smartgwt.client.widgets.drawing.DrawPane;
import com.smartgwt.client.widgets.drawing.DrawSector;
import com.smartgwt.client.widgets.drawing.Point;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;

public class PanelPregunta extends Canvas {

	private DrawPane panelReloj;
	private Timer tiempoReloj;
	private DrawOval marco;
	private String colorTiempoRestante = "#FFFFFF";
	private TextAreaItem textAreaRespuesta;
	private StaticTextItem textContador;
	private int radioReloj = 30;
	private int tiempoLamina = 120;
	private int tiempoTotal;
	private float anguloCrecimiento;
	private float anguloInicial = -90;
	private double color;
	private Canvas labelPregunta;
	private HTMLFlow textTiempo;
	private List<PreguntaUsuarioBO> listaPregunta;
	private PreguntaUsuarioBO preguntaActual;
	private int indicePregunta;
	private DynamicForm formRespuesta;
	private IButton botonSiguientePregunta;

	public PanelPregunta() {
		setSize("900px", "600px");
		setLeft(0);
		setTop(0);

		anguloCrecimiento = (float) (360 / tiempoLamina);
		color = 1 / tiempoLamina;
		tiempoTotal = tiempoLamina;
		inicializarRespuesta();
		inicializarReloj();

		botonSiguientePregunta = new IButton("Continuar");
		botonSiguientePregunta.setTop(470);
		botonSiguientePregunta.setLeft(770);
		botonSiguientePregunta.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (textAreaRespuesta.validate()) {
					guardarResultado();
					mostrarPregunta();
				}
			}
		});

		addChild(botonSiguientePregunta);
		textTiempo = new HTMLFlow();
		textTiempo.setLeft(435);
		textTiempo.setTop(55);
		textTiempo.setStyleName("temporizador");
		addChild(textTiempo);

	}

	private void inicializarRespuesta() {
		formRespuesta = new DynamicForm();
		formRespuesta.setGroupTitle("Respuesta");
		formRespuesta.setIsGroup(true);
		formRespuesta.setWidth(700);
		formRespuesta.setHeight(120);
		formRespuesta.setNumCols(2);
		formRespuesta.setColWidths(60, "*");
		// formRespuesta.setBorder("1px solid blue");
		formRespuesta.setPadding(5);
		formRespuesta.setLeft(50);
		formRespuesta.setTop(450);

		textAreaRespuesta = new TextAreaItem();
		textAreaRespuesta.setWidth(690);
		textAreaRespuesta.setHeight(90);
		textAreaRespuesta.setShowTitle(false);
		textAreaRespuesta.setLength(1000);
		textAreaRespuesta.setColSpan(2);
		textAreaRespuesta.setWidth("*");
		textAreaRespuesta.setHeight("*");

		textContador = new StaticTextItem();
		textContador.setTitle("Caracteres");
		formRespuesta.setFields(textAreaRespuesta, textContador);
		textContador.setValue(0 + "/" + textAreaRespuesta.getLength());
		addChild(formRespuesta);

		labelPregunta = new Canvas("Pregunta");
		labelPregunta.setWidth(250);
		labelPregunta.setMaxWidth(250);
		labelPregunta.setHeight(170);
		labelPregunta.setMaxHeight(170);
		labelPregunta.setPadding(8);
		labelPregunta.setBackgroundColor("white");
		labelPregunta.setShowEdges(true);
		labelPregunta.setShadowOffset(10);
		labelPregunta.setShadowSoftness(10);
		labelPregunta.setTop(50);
		labelPregunta.setLeft(50);
		labelPregunta.setKeepInParentRect(true);
		labelPregunta.setCanDragResize(false);
		labelPregunta.setOverflow(Overflow.AUTO);
		addChild(labelPregunta);

		textAreaRespuesta.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (textAreaRespuesta.getValueAsString() != null) {
					textContador.setValue(textAreaRespuesta.getValueAsString()
							.length() + "/" + textAreaRespuesta.getLength());
				} else {
					textContador.setValue(0 + "/"
							+ textAreaRespuesta.getLength());
				}
			}
		});
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

		marco = new DrawOval();
		marco.setDrawPane(panelReloj);
		marco.setCenterPoint(new Point(53, 53));
		marco.setRadius(radioReloj);
		marco.setFillColor(colorTiempoRestante);
		marco.setLineWidth(10);
		marco.draw();

		iniciarReloj();
		addChild(panelReloj);
	}

	private void iniciarReloj() {
		if (tiempoReloj != null) {
			tiempoReloj.cancel();
			tiempoLamina = tiempoTotal;

			panelReloj.erase();

			marco = new DrawOval();
			marco.setDrawPane(panelReloj);
			marco.setCenterPoint(new Point(53, 53));
			marco.setRadius(radioReloj);
			marco.setLineColor(Convencion.rgbToString(tiempoTotal
					- tiempoLamina, tiempoTotal));
			marco.setFillOpacity(0.0f);
			marco.setLineWidth(5);
			marco.draw();

		}
		tiempoReloj = new Timer() {
			@Override
			public void run() {
				if (tiempoLamina == 0) {
					tiempoReloj.cancel();
					guardarResultado();
					mostrarPregunta();
				} else {
					tiempoLamina--;
					panelReloj.erase();

					marco = new DrawOval();
					marco.setDrawPane(panelReloj);
					marco.setCenterPoint(new Point(53, 53));
					marco.setRadius(radioReloj);
					marco.setLineColor(Convencion.rgbToString(tiempoTotal
							- tiempoLamina, tiempoTotal));
					marco.setFillOpacity(0.0f);
					marco.setLineWidth(5);
					marco.draw();

					textTiempo.setContents("<p style='color:"
							+ Convencion.rgbToString(
									tiempoTotal - tiempoLamina, tiempoTotal)
							+ ";font-size:15px;'>"
							+ Convencion
									.obtenerTiempoMinutoSegundos(tiempoLamina)
							+ "</p>");
				}

			}
		};
		tiempoReloj.scheduleRepeating(1000);
	}

	public void iniciar() {
		mostrarPregunta();
	}

	private void mostrarPregunta() {
		try {
			preguntaActual = listaPregunta.remove(0);
			labelPregunta.setContents(preguntaActual.getPregunta());
			labelPregunta.setTop(preguntaActual.getPosicionPreguntaY());
			labelPregunta.setLeft(preguntaActual.getPosicionPreguntaX());
			textAreaRespuesta.setLength(preguntaActual.getCaracteresMaximo());
			tiempoLamina = preguntaActual.getTiempoMaximo();
			tiempoTotal = preguntaActual.getTiempoMaximo();
			textAreaRespuesta.setValue("");
			setBackgroundImage(preguntaActual.getImagenesUsuario().getImagen()
					.getImagenURI());
			textContador.setValue(0 + "/" + textAreaRespuesta.getLength());
			anguloCrecimiento = (float) (360 / tiempoLamina);
			indicePregunta++;
			// labelIndicePregunta.setContents(((Integer)
			// indicePregunta).toString());

			iniciarReloj();

		} catch (IndexOutOfBoundsException e) {
			// Termino las preguntas
			Evaluacion.terminarPrueba();
		}
	}

	public void actualizarNombreEmpresa(String nombre) {

	}

	public void actualizarPreguntas(List<PreguntaUsuarioBO> result) {
		listaPregunta = result;
	}

	private void guardarResultado() {
		ResultadoBO resultado = new ResultadoBO();
		resultado.setRespuesta(textAreaRespuesta.getValueAsString());
		resultado.setPreguntasUsuario(preguntaActual);
		Evaluacion.guardarResultado(resultado);
	}

}
