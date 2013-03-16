package co.mind.management.maestro.client.temas;

import java.util.List;

import co.mind.management.shared.bo.ImagenBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.records.ImagenRecord;
import co.mind.management.shared.records.PreguntaCategoriaListGridRecord;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.PreguntaPruebaListGridRecord;
import co.mind.management.shared.records.PruebaListGridRecord;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.RichTextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelEditarLaminas extends HLayout {

	private ListGrid treeGridPruebas;
	private VLayout layoutCentral;
	private PanelLaminaEdicionPrueba panelPregunta;
	private TileGrid tileGridImagenesUsuario;
	private ListGrid listGridPreguntas;
	private DynamicForm formInformacionLamina;
	private IntegerItem integerItemTiempoMaximo;
	private IntegerItem integerItemRespuestaMaxima;
	private List<PreguntaUsuarioBO> listaPreguntasPorCategoria;
	private ListGrid listGridPreguntasPrueba;
	private TabSet tabsetRepositorio;
	private ToolStripButton botonNuevaPregunta;
	private boolean nuevaPregunta = false;
	private RichTextItem richTextEditorPregunta;
	private ImagenRecord imagenSeleccionada;
	private PanelCategorias panelCategorias;
	private PreguntaCategoriaTileRecord pregunta;

	public PanelEditarLaminas(PanelCategorias panelCategorias,
			PreguntaCategoriaTileRecord pregunta) {
		this.panelCategorias = panelCategorias;
		this.pregunta = pregunta;
		this.panelCategorias = panelCategorias;
		setWidth100();
		setHeight100();
		setBackgroundColor("white");
		setPadding(15);
		setOverflow(Overflow.AUTO);

		layoutCentral = new VLayout();
		layoutCentral.setWidth("100%");
		layoutCentral.setHeight("100%");
		layoutCentral.setAlign(Alignment.CENTER);

		botonNuevaPregunta = new ToolStripButton("Guardar L\u00E1mina",
				"icons/16/document_plain_new.png");
		botonNuevaPregunta
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						editarPregunta();
					}
				});

		ToolStrip menuBar = new ToolStrip();
		menuBar.setWidth(600);
		menuBar.addButton(botonNuevaPregunta);
		menuBar.addFill();
		menuBar.addSeparator();
		menuBar.addSeparator();

		InicializarPanelCentral();

		formInformacionLamina = new DynamicForm();
		formInformacionLamina.setIsGroup(true);
		formInformacionLamina.setWidth(600);
		formInformacionLamina.setHeight100();
		formInformacionLamina
				.setGroupTitle("Informaci\u00F3n de la L\u00E1mina");
		formInformacionLamina.setNumCols(6);
		formInformacionLamina.setID("formInformacionLamina");

		richTextEditorPregunta = new RichTextItem();
		richTextEditorPregunta.setWidth(600);
		richTextEditorPregunta.setRequired(true);
		richTextEditorPregunta.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (richTextEditorPregunta.getValue() != null) {
					panelPregunta
							.cambiarTextoPregunta((String) richTextEditorPregunta
									.getValue());
				}
			}
		});

		integerItemTiempoMaximo = new IntegerItem();
		integerItemTiempoMaximo.setTitle("Tiempo");
		integerItemTiempoMaximo.setCanEdit(false);
		integerItemTiempoMaximo.setRequired(true);
		integerItemTiempoMaximo.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (integerItemTiempoMaximo.validate()) {
					panelPregunta.actualizarTiempo(integerItemTiempoMaximo
							.getValueAsInteger());
				}
			}
		});
		integerItemRespuestaMaxima = new IntegerItem();
		integerItemRespuestaMaxima.setTitle("Longitud de Respuesta");
		integerItemRespuestaMaxima.setCanEdit(false);
		integerItemRespuestaMaxima.setRequired(true);

		formInformacionLamina.setFields(richTextEditorPregunta,
				integerItemTiempoMaximo, integerItemRespuestaMaxima);

		layoutCentral.addMember(formInformacionLamina);
		layoutCentral.addMember(menuBar);
		addMember(layoutCentral);
		addMember(tileGridImagenesUsuario);
		mostrarPregunta();
		habilitarNuevaPregunta();
	}

	private void InicializarPanelCentral() {
		VLayout v1 = new VLayout();
		v1.setWidth100();
		v1.setHeight("100%");

		VLayout v2 = new VLayout();
		v2.setWidth(600);
		v2.setHeight("100%");

		panelPregunta = new PanelLaminaEdicionPrueba();
		v2.addMember(panelPregunta);

		tileGridImagenesUsuario = new TileGrid();
		tileGridImagenesUsuario.setTileWidth(150);
		tileGridImagenesUsuario.setTileHeight(94);
		tileGridImagenesUsuario.setHeight100();
		tileGridImagenesUsuario.setWidth100();
		tileGridImagenesUsuario.setCanReorderTiles(false);
		tileGridImagenesUsuario.setShowAllRecords(false);
		tileGridImagenesUsuario.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (nuevaPregunta) {
					imagenSeleccionada = ((ImagenRecord) event.getRecord());
					panelPregunta.cambiarImagen(((ImagenRecord) event
							.getRecord()).getPicture());
				}

			}
		});
		DetailViewerField pictureField = new DetailViewerField("picture");
		pictureField.setType("image");
		pictureField.setImageWidth(150);
		pictureField.setImageHeight(94);

		DetailViewerField nameField = new DetailViewerField("idImagenUsuario");

		tileGridImagenesUsuario.setFields(pictureField, nameField);

		listGridPreguntas = new ListGrid();
		listGridPreguntas.setWidth100();
		listGridPreguntas.setHeight100();
		listGridPreguntas.setShowAllRecords(true);

		ListGridField pregunta = new ListGridField("pregunta", "Pregunta");
		listGridPreguntas.setFields(pregunta);
		listGridPreguntas.setCanResizeFields(false);
		listGridPreguntas.setSelectionType(SelectionStyle.SINGLE);
		listGridPreguntas.setData(PreguntaCategoriaListGridRecord
				.getRecords(null));
		listGridPreguntas.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {

			}
		});
		layoutCentral.addMember(v2);

	}

	public void actualizarImagenesUsuario(ImagenRecord[] record) {
		tileGridImagenesUsuario.setData(record);
	}

	public void actualizarPruebas(PruebaListGridRecord[] records) {
		treeGridPruebas.setData(records);
	}

	public void actualizarPreguntas(List<PreguntaUsuarioBO> result) {
		listaPreguntasPorCategoria = result;
		listGridPreguntas.setData(PreguntaCategoriaListGridRecord
				.getRecords(listaPreguntasPorCategoria));
	}

	public void actualizarPreguntasPrueba(PreguntaPruebaListGridRecord[] records) {
		listGridPreguntasPrueba.setData(records);
	}

	private void habilitarNuevaPregunta() {
		nuevaPregunta = true;
		limpiarCamposPreguntas();
		integerItemTiempoMaximo.setCanEdit(true);
		integerItemRespuestaMaxima.setCanEdit(true);
		tileGridImagenesUsuario.setDisabled(false);
		listGridPreguntas.setDisabled(true);
		panelPregunta.habilitarEdicion();
	}

	private void limpiarCamposPreguntas() {
		integerItemRespuestaMaxima.setValue("");
		integerItemTiempoMaximo.setValue("");
		panelPregunta.cambiarTextoPregunta("Pregunta");
	}

	private void mostrarPregunta() {
		PreguntaUsuarioBO p = PreguntaCategoriaTileRecord.getBO(pregunta);
		integerItemRespuestaMaxima.setValue(p.getCaracteresMaximo());
		integerItemTiempoMaximo.setValue(p.getTiempoMaximo());
		panelPregunta.cambiarTextoPregunta(p.getPregunta());
		richTextEditorPregunta.setValue(p.getPregunta());
		panelPregunta.cambiarImagen(p.getImagenesUsuario().getImagen()
				.getImagenURI());
		panelPregunta.setPosicionPregunta(p.getPosicionPreguntaX(),
				p.getPosicionPreguntaY());

	}

	private void editarPregunta() {
		if (imagenSeleccionada != null && formInformacionLamina.validate()) {
			int posicionX = panelPregunta.obtenerPosicionX();
			int posicionY = panelPregunta.obtenerPosicionY();
			int tiempo = Integer.parseInt(integerItemTiempoMaximo
					.getValueAsString());
			int caracteres = Integer.parseInt(integerItemRespuestaMaxima
					.getValueAsString());
			String pregunta = (String) richTextEditorPregunta.getValue();
			ImagenUsuarioBO imagen = ImagenRecord.getBO(imagenSeleccionada);

			PreguntaUsuarioBO bo = new PreguntaUsuarioBO();
			bo.setCaracteresMaximo(caracteres);
			bo.setPosicionPreguntaX(posicionX);
			bo.setPosicionPreguntaY(posicionY);
			bo.setPregunta(pregunta);
			bo.setTiempoMaximo(tiempo);
			bo.setImagenesUsuarioID(imagen);
			bo.setIdentificador(this.pregunta.getAttributeAsInt("idPregunta"));
			panelCategorias.editarPreguntaCategoria(bo);
		} else {
			SC.warn("La informaci\u00F3n de la l\u00E1mina se encuentra incompleta.");
		}
	}
}
