package co.mind.management.maestro.client.temas;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.bo.CategoriaUsuarioBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.records.CategoriaListGridRecord;
import co.mind.management.shared.records.ImagenRecord;
import co.mind.management.shared.records.PreguntaCategoriaListGridRecord;
import co.mind.management.shared.records.PreguntaPruebaListGridRecord;
import co.mind.management.shared.records.PruebaListGridRecord;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.TileRecord;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelLaminasCategorias extends HLayout {

	private VLayout layoutIzquierda;
	private ListGrid treeGridPruebas;
	private DynamicForm formInformacionPrueba;
	private TextItem textNombre;
	private TextAreaItem textAreaDescripcion;
	private StaticTextItem textTiempoTotal;
	private StaticTextItem textCantidadPreguntas;
	private VLayout layoutCentral;
	private PanelLaminaEdicionPrueba panelPregunta;
	private VLayout layoutDerecha;
	private TileGrid tileGridImagenesUsuario;
	private ListGrid listGridPreguntas;
	private DynamicForm formInformacionLamina;
	private TextAreaItem textAreaPregunta;
	private IntegerItem integerItemTiempoMaximo;
	private IntegerItem integerItemRespuestaMaxima;
	private List<PreguntaUsuarioBO> listaPreguntasPorCategoria;
	private List<CategoriaUsuarioBO> listaCategorias;
	private ListGrid listGridCategorias;
	private ListGrid listGridCategoriasLamina;
	private DynamicForm formPruebaNueva;
	private TextItem textNombrePruebaNueva;
	private TextAreaItem textAreaDescripcionPruebaNueva;
	private ListGrid listGridPreguntasPrueba;
	private TabSet tabsetRepositorio;
	private ToolStripButton botonNuevaPregunta;
	private ToolStripButton botonCancelarNuevaPregunta;
	private ToolStripButton botonGuardarPregunta;
	private boolean nuevaPregunta = false;

	public PanelLaminasCategorias() {
		setWidth("80%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		layoutIzquierda = new VLayout();
		layoutIzquierda.setWidth("20%");
		layoutIzquierda.setHeight100();

		inicializarPanelIzquierda();

		layoutCentral = new VLayout();
		layoutCentral.setWidth("60%");
		layoutCentral.setHeight100();
		layoutCentral.setAlign(Alignment.CENTER);

		InicializarPanelCentral();

		layoutDerecha = new VLayout();
		layoutDerecha.setWidth("20%");
		layoutDerecha.setHeight100();

		inicializarPanelDerecha();

		addMember(layoutIzquierda);
		addMember(layoutCentral);
		addMember(layoutDerecha);
	}

	private void inicializarPanelDerecha() {

		VLayout v1 = new VLayout();
		v1.setWidth100();
		v1.setHeight("50%");

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("25%");

		VLayout v3 = new VLayout();
		v3.setWidth100();
		v3.setHeight("25%");

		tabsetRepositorio = new TabSet();
		tabsetRepositorio.setTabBarPosition(Side.TOP);
		tabsetRepositorio.setTitle("Repositorio");
		tabsetRepositorio.setWidth100();
		tabsetRepositorio.setHeight100();

		Tab tTab1 = new Tab("Im\u00E1genes", "pieces/16/pawn_blue.png");
		tTab1.setName("imagenes");

		tileGridImagenesUsuario = new TileGrid();
		tileGridImagenesUsuario.setTileWidth(210);
		tileGridImagenesUsuario.setTileHeight(140);
		tileGridImagenesUsuario.setHeight100();
		tileGridImagenesUsuario.setWidth100();
		tileGridImagenesUsuario.setCanReorderTiles(false);
		tileGridImagenesUsuario.setShowAllRecords(true);
		tileGridImagenesUsuario.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				if (nuevaPregunta) {
					panelPregunta.cambiarImagen(((ImagenRecord) event
							.getRecord()).getPicture());
				}

			}
		});
		DetailViewerField pictureField = new DetailViewerField("picture");
		pictureField.setType("image");
		pictureField.setImageWidth(210);
		pictureField.setImageHeight(140);

		DetailViewerField nameField = new DetailViewerField("idImagenUsuario");

		tileGridImagenesUsuario.setFields(pictureField, nameField);

		tTab1.setPane(tileGridImagenesUsuario);

		Tab tTab2 = new Tab("Preguntas", "pieces/16/pawn_green.png");
		tTab2.setName("preguntas");

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
				actualizarComponentesPregunta(listGridPreguntas
						.getSelectedRecord());

			}
		});

		tTab2.setPane(listGridPreguntas);

		tabsetRepositorio.addTab(tTab1);
		tabsetRepositorio.addTab(tTab2);

		formInformacionLamina = new DynamicForm();
		formInformacionLamina.setIsGroup(true);
		formInformacionLamina.setWidth100();
		formInformacionLamina.setHeight100();
		formInformacionLamina
				.setGroupTitle("Informaci\u00F3n de la L\u00E1mina");

		textAreaPregunta = new TextAreaItem();
		textAreaPregunta.setTitle("Pregunta");
		textAreaPregunta.setCanEdit(false);
		textAreaPregunta.setRequired(true);
		textAreaPregunta.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (textAreaPregunta.getValueAsString() != null) {
					panelPregunta.cambiarTextoPregunta(textAreaPregunta
							.getValueAsString());
				} else {
					panelPregunta.cambiarTextoPregunta("Pregunta");
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

		formInformacionLamina.setFields(textAreaPregunta,
				integerItemTiempoMaximo, integerItemRespuestaMaxima);

		listGridCategoriasLamina = new ListGrid();
		listGridCategoriasLamina.setWidth100();
		listGridCategoriasLamina.setHeight(120);
		listGridCategoriasLamina.setShowAllRecords(true);

		ListGridField name = new ListGridField("nombre", "Categoría");
		listGridCategoriasLamina.setFields(name);
		listGridCategoriasLamina.setCanResizeFields(false);
		listGridCategoriasLamina.setData(CategoriaListGridRecord
				.getRecords(listaCategorias));
		listGridCategoriasLamina.setSelectionType(SelectionStyle.SINGLE);
		listGridCategoriasLamina.setDisabled(true);

		v1.addChild(tabsetRepositorio);
		v2.addChild(formInformacionLamina);
		v3.addChild(listGridCategoriasLamina);

		layoutDerecha.addMember(v1);
		layoutDerecha.addMember(v2);
		layoutDerecha.addMember(v3);
		botonNuevaPregunta = new ToolStripButton("Nueva Pregunta",
				"icons/16/document_plain_new.png");
		botonNuevaPregunta
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						habilitarNuevaPregunta();
						botonNuevaPregunta.setDisabled(true);
						botonCancelarNuevaPregunta.setDisabled(false);
						botonGuardarPregunta.setDisabled(false);
					}
				});

		botonCancelarNuevaPregunta = new ToolStripButton("Cancelar",
				"icons/16/document_plain_new.png");
		botonCancelarNuevaPregunta.setDisabled(true);
		botonCancelarNuevaPregunta
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						desHabilitarNuevaPregunta();
						botonNuevaPregunta.setDisabled(false);
						botonCancelarNuevaPregunta.setDisabled(true);
						botonGuardarPregunta.setDisabled(true);
					}
				});

		botonGuardarPregunta = new ToolStripButton("Guardar",
				"icons/16/document_plain_new.png");
		botonGuardarPregunta.setDisabled(true);
		botonGuardarPregunta
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						agregarPregunta();
						botonNuevaPregunta.setDisabled(false);
						botonCancelarNuevaPregunta.setDisabled(true);
						botonGuardarPregunta.setDisabled(true);

					}

				});

		ToolStrip menuBar = new ToolStrip();
		menuBar.setWidth100();
		menuBar.addButton(botonNuevaPregunta);
		menuBar.addButton(botonCancelarNuevaPregunta);
		menuBar.addButton(botonGuardarPregunta);
		menuBar.addFill();
		menuBar.addSeparator();
		menuBar.addSeparator();

		layoutDerecha.addMember(menuBar);

	}

	private void InicializarPanelCentral() {

		panelPregunta = new PanelLaminaEdicionPrueba();
		layoutCentral.addMember(panelPregunta);

	}

	private void inicializarPanelIzquierda() {
		VLayout v1 = new VLayout();
		v1.setWidth100();
		v1.setHeight("30%");

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("30%");

		VLayout v3 = new VLayout();
		v3.setWidth100();
		v3.setHeight("40%");

		treeGridPruebas = new ListGrid();
		treeGridPruebas.setWidth100();
		treeGridPruebas.setHeight100();
		treeGridPruebas.setShowAllRecords(true);

		ListGridField prueba = new ListGridField("nombre", "Prueba");
		treeGridPruebas.setFields(prueba);
		treeGridPruebas.setCanResizeFields(false);
		treeGridPruebas.setSelectionType(SelectionStyle.SINGLE);
		treeGridPruebas.setData(PreguntaCategoriaListGridRecord
				.getRecords(null));
		treeGridPruebas.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				try {
					Record r = treeGridPruebas.getSelectedRecord();
					String res = r.getAttributeAsString("idPreguntaEnPrueba");
					if (res == null) {
						textNombre.setValue(r.getAttribute("nombre"));
						textAreaDescripcion.setValue(r
								.getAttribute("descripcion"));
						Maestro.obtenerPreguntasCategoriaEnPrueba(r
								.getAttributeAsInt("pruebaID"));
					} else {
						actualizarComponentesPregunta(r);
					}
				} catch (NullPointerException e) {

				}
			}
		});

		listGridPreguntasPrueba = new ListGrid();
		listGridPreguntasPrueba.setWidth100();
		listGridPreguntasPrueba.setHeight100();
		listGridPreguntasPrueba.setShowAllRecords(true);

		ListGridField pregunta = new ListGridField("pregunta", "Pregunta");
		listGridPreguntasPrueba.setFields(pregunta);
		listGridPreguntasPrueba.setCanResizeFields(false);
		listGridPreguntasPrueba.setSelectionType(SelectionStyle.SINGLE);
		listGridPreguntasPrueba.setData(PreguntaCategoriaListGridRecord
				.getRecords(null));
		listGridPreguntasPrueba.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				actualizarComponentesPregunta(listGridPreguntasPrueba
						.getSelectedRecord());
			}
		});

		formInformacionPrueba = new DynamicForm();
		formInformacionPrueba.setIsGroup(true);
		formInformacionPrueba.setWidth100();
		formInformacionPrueba.setHeight100();
		formInformacionPrueba.setGroupTitle("Informaci\u00F3n de la Prueba");
		formInformacionPrueba.setAutoFocus(false);

		textNombre = new TextItem();
		textNombre.setTitle("Nombre");

		textAreaDescripcion = new TextAreaItem();
		textAreaDescripcion.setTitle("Descripci\u00F3n");

		textTiempoTotal = new StaticTextItem();
		textTiempoTotal.setTitle("Tiempo Total");

		textCantidadPreguntas = new StaticTextItem();
		textCantidadPreguntas.setTitle("Cantidad Preguntas");

		formInformacionPrueba.setFields(textNombre, textAreaDescripcion,
				textTiempoTotal, textCantidadPreguntas);

		v1.addChild(treeGridPruebas);
		v2.addChild(formInformacionPrueba);
		v3.addChild(listGridPreguntasPrueba);
		layoutIzquierda.addMember(v1);
		ToolStripButton botonNuevaPrueba = new ToolStripButton("Nueva Prueba",
				"icons/16/document_plain_new.png");

		botonNuevaPrueba
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						final Window winModal = new Window();
						winModal.setWidth(350);
						winModal.setHeight(380);
						winModal.setTitle("Crear una prueba");
						winModal.setShowMinimizeButton(false);
						winModal.setIsModal(true);
						winModal.setShowModalMask(true);
						winModal.centerInPage();
						winModal.addCloseClickHandler(new CloseClickHandler() {
							@Override
							public void onCloseClick(CloseClickEvent event) {
								winModal.destroy();

							}
						});

						listGridCategorias = new ListGrid();
						listGridCategorias.setWidth100();
						listGridCategorias.setHeight(120);
						listGridCategorias.setShowAllRecords(true);

						ListGridField nameField = new ListGridField("nombre",
								"Categoría");
						listGridCategorias.setFields(nameField);
						listGridCategorias.setCanResizeFields(false);
						listGridCategorias.setData(CategoriaListGridRecord
								.getRecords(listaCategorias));
						listGridCategorias
								.setSelectionType(SelectionStyle.SIMPLE);

						formPruebaNueva = new DynamicForm();
						formPruebaNueva.setHeight100();
						formPruebaNueva.setWidth100();
						formPruebaNueva.setPadding(5);
						formPruebaNueva
								.setLayoutAlign(VerticalAlignment.BOTTOM);

						textNombrePruebaNueva = new TextItem();
						textNombrePruebaNueva.setTitle("Nombre");
						textNombrePruebaNueva.setRequired(true);

						textAreaDescripcionPruebaNueva = new TextAreaItem();
						textAreaDescripcionPruebaNueva
								.setTitle("Descripci\u00F3n");
						textAreaDescripcionPruebaNueva.setRequired(true);

						formPruebaNueva.setFields(textNombrePruebaNueva,
								textAreaDescripcionPruebaNueva);

						winModal.addItem(formPruebaNueva);
						winModal.addItem(listGridCategorias);

						DynamicForm formOtro = new DynamicForm();
						formOtro.setHeight100();
						formOtro.setWidth100();
						formOtro.setPadding(5);
						ButtonItem boton = new ButtonItem("Crear");
						boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								if (formPruebaNueva.validate()) {
									ListGridRecord[] records = listGridCategorias
											.getSelectedRecords();
									if (records.length > 0) {
										CategoriaListGridRecord[] categoriaRecords = new CategoriaListGridRecord[records.length];
										for (int i = 0; i < records.length; i++) {
											categoriaRecords[i] = (CategoriaListGridRecord) records[i];
										}
										List<CategoriaUsuarioBO> categorias = CategoriaListGridRecord
												.getBO(categoriaRecords);
										agregarPrueba(categorias);
										winModal.destroy();
									}

								}
							}

						});
						formOtro.setFields(boton);
						winModal.addItem(formOtro);

						winModal.show();
					}
				});

		ToolStrip menuBar = new ToolStrip();
		menuBar.setWidth100();
		menuBar.addButton(botonNuevaPrueba);
		menuBar.addFill();
		menuBar.addSeparator();
		menuBar.addSeparator();

		layoutIzquierda.addMember(menuBar);
		layoutIzquierda.addMember(v3);
		layoutIzquierda.addMember(v2);

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

	private void actualizarComponentesPregunta(Record r) {
		textAreaPregunta.setValue(r.getAttribute("pregunta"));
		panelPregunta.actualizarPosicionPregunta(
				r.getAttributeAsInt("posicionX"),
				r.getAttributeAsInt("posicionY"), r.getAttributeAsInt("ancho"),
				r.getAttributeAsInt("alto"));
		integerItemRespuestaMaxima.setValue(r.getAttributeAsInt("caracteres"));
		integerItemTiempoMaximo.setValue(r.getAttributeAsInt("tiempo"));	
		panelPregunta.actualizarTiempo(integerItemTiempoMaximo
				.getValueAsInteger());
		if (textAreaPregunta.getValueAsString() != null) {
			panelPregunta.cambiarTextoPregunta(textAreaPregunta
					.getValueAsString());
		} else {
			panelPregunta.cambiarTextoPregunta("Pregunta");
		}

		panelPregunta.cambiarImagen(r.getAttribute("imagen"));
	}

	public void actualizarListaCategorias(List<CategoriaUsuarioBO> categorias) {
		listaCategorias = categorias;
		listGridCategoriasLamina.setData(CategoriaListGridRecord
				.getRecords(listaCategorias));
	}

	private void agregarPrueba(List<CategoriaUsuarioBO> categorias) {
		String nombrePrueba = textNombrePruebaNueva.getValueAsString();
		String descripcionPrueba = textAreaDescripcionPruebaNueva
				.getValueAsString();
		Maestro.agregarPrueba(nombrePrueba, descripcionPrueba, categorias);

	}

	private void habilitarNuevaPregunta() {
		nuevaPregunta = true;
		limpiarCamposPreguntas();
		textAreaPregunta.setCanEdit(true);
		integerItemTiempoMaximo.setCanEdit(true);
		integerItemRespuestaMaxima.setCanEdit(true);
		listGridCategoriasLamina.setDisabled(false);
		tileGridImagenesUsuario.setDisabled(false);
		listGridPreguntas.setDisabled(true);
		panelPregunta.habilitarEdicion();
		layoutIzquierda.setDisabled(true);
	}

	private void limpiarCamposPreguntas() {
		textAreaPregunta.setValue("");
		integerItemRespuestaMaxima.setValue("");
		integerItemTiempoMaximo.setValue("");
		panelPregunta.cambiarTextoPregunta("Pregunta");
	}

	private void desHabilitarNuevaPregunta() {
		nuevaPregunta = false;
		limpiarCamposPreguntas();
		textAreaPregunta.setCanEdit(false);
		integerItemTiempoMaximo.setCanEdit(false);
		integerItemRespuestaMaxima.setCanEdit(false);
		listGridCategoriasLamina.setDisabled(true);
		tileGridImagenesUsuario.setDisabled(true);
		listGridPreguntas.setDisabled(false);
		panelPregunta.deshabilitarEdicion();
		layoutIzquierda.setDisabled(false);
	}

	private void agregarPregunta() {
		if (formInformacionLamina.validate()) {
			String pregunta = textAreaPregunta.getValueAsString();
			int tiempo = integerItemTiempoMaximo.getValueAsInteger();
			int caracteres = integerItemRespuestaMaxima.getValueAsInteger();
			int posicionX = panelPregunta.obtenerPosicionX();
			int posicionY = panelPregunta.obtenerPosicionY();
			ListGridRecord record = listGridCategoriasLamina
					.getSelectedRecord();
			TileRecord imagen = tileGridImagenesUsuario.getSelectedRecord();
			if (record != null) {
				if (imagen != null) {
					PreguntaUsuarioBO preguntaBO = new PreguntaUsuarioBO();
					preguntaBO.setCaracteresMaximo(caracteres);
					preguntaBO.setPosicionPreguntaX(posicionX);
					preguntaBO.setPosicionPreguntaY(posicionY);
					preguntaBO.setPregunta(pregunta);
					preguntaBO.setTiempoMaximo(tiempo);
					ImagenUsuarioBO imagenBO = ImagenRecord
							.getBO((ImagenRecord) imagen);
					preguntaBO.setImagenesUsuarioID(imagenBO);
					CategoriaUsuarioBO categoria = CategoriaListGridRecord
							.getBO((CategoriaListGridRecord) record);
					Maestro.agregarPreguntaUsuario(preguntaBO, categoria);
					desHabilitarNuevaPregunta();

				} else {
					SC.warn("Debe selecionar la imagen de la pregunta");
				}
			} else {
				SC.warn("Debe selecionar la categoria de la pregunta");
			}
		} else {
			SC.warn("Debe escribir la pregunta, tiempo y caracteres maximo de respuesta.");
		}

	}
}
