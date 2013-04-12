package co.mind.management.evaluacion.client;

import java.util.List;

import co.mind.management.shared.dto.ResultadoBO;
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

public class PanelInstruccion extends Canvas {

	private HTMLFlow textTiempo;
	private IButton botonSiguientePregunta;

	public PanelInstruccion() {
		setSize("900px", "600px");
		setLeft(0);
		setTop(0);
		setBackgroundImage("/img/fondo.jpg");

		botonSiguientePregunta = new IButton("Continuar");
		botonSiguientePregunta.setTop(470);
		botonSiguientePregunta.setLeft(770);
		botonSiguientePregunta.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Evaluacion.iniciarCategoria();
			}
		});

		addChild(botonSiguientePregunta);
		textTiempo = new HTMLFlow();
		textTiempo.setLeft(50);
		textTiempo.setTop(450);
		textTiempo.setWidth(700);
		textTiempo.setHeight(120);
		textTiempo.setStyleName("temporizador");
		addChild(textTiempo);

	}

	public void setImagenFondo(String imagen) {
		if (imagen == null) {
			setBackgroundImage("");
		} else {
			setBackgroundImage(imagen);
		}
	}

	public void setInstruccion(String instruccion) {
		textTiempo.setContents(instruccion);
	}

}
