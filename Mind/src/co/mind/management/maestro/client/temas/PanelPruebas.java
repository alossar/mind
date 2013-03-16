package co.mind.management.maestro.client.temas;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.records.ImagenRecord;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.PruebaListGridRecord;

import com.google.gwt.event.shared.UmbrellaException;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelPruebas extends HLayout {

	private ListGrid listGridPruebas;
	private DynamicForm formNuevaCategoria;
	private TextItem textNombreCategoriaNueva;
	private TextAreaItem textAreaDescripcionCategoriaNueva;
	private ToolStripButton botonRegresar;
	private ToolStripButton botonNuevoBasico;
	private ToolStripButton botonEliminarBasico;
	private TileGrid tileGridImagenesUsuario;
	private DynamicForm formularioLamina;
	private StaticTextItem textAreaPregunta;
	private IntegerItem integerTiempoPregunta;
	private IntegerItem integerCaracteresPregunta;
	private boolean enCategoria = true;
	private PanelAgregarLaminas panelAgregarLaminas;
	private PruebaListGridRecord pruebaSeleccionada;

	public PanelPruebas() {
		setWidth("100%");
		setHeight("80%%");
		setBackgroundColor("white");
		setPadding(15);

		listGridPruebas = new ListGrid();
		listGridPruebas.setWidth100();
		listGridPruebas.setHeight100();
		listGridPruebas.setShowAllRecords(true);
		listGridPruebas.setSelectionType(SelectionStyle.SINGLE);
		listGridPruebas.setTitle("Categor\u00EDas");

		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("preguntas",
				"Preguntas");
		ListGridField tiempoField = new ListGridField("tiempo",
				"Tiempo (Segundos)");
		listGridPruebas.setFields(nombreField, apellidosField, preguntasField,
				tiempoField);
		listGridPruebas.setCanResizeFields(true);
		listGridPruebas.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				botonRegresar.enable();
				pruebaSeleccionada = (PruebaListGridRecord) listGridPruebas
						.getSelectedRecord();
				listGridPruebas.setVisible(false);
				tileGridImagenesUsuario.setVisible(true);
				formularioLamina.setVisible(true);
				enCategoria = false;
				// limpiarFormularioLamina();
				botonNuevoBasico.setTitle("Nueva L\u00E1mina");
				botonEliminarBasico.setTitle("Eliminar L\u00E1mina");
				Maestro.obtenerPreguntasPorPrueba(pruebaSeleccionada
						.getAttributeAsInt("id"));
			}
		});

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
				PreguntaCategoriaTileRecord record = (PreguntaCategoriaTileRecord) event
						.getRecord();
				textAreaPregunta.setValue(record.getAttribute("pregunta"));
				integerCaracteresPregunta.setValue(record
						.getAttributeAsInt("caracteres"));
				integerTiempoPregunta.setValue(record
						.getAttributeAsInt("tiempo"));
			}
		});
		tileGridImagenesUsuario
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						mostrarDialogoCrearLamina((PreguntaCategoriaTileRecord) event
								.getRecord());

					}
				});

		DetailViewerField pictureField = new DetailViewerField("imagen");
		pictureField.setType("image");
		pictureField.setImageWidth(210);
		pictureField.setImageHeight(140);

		DetailViewerField nameField = new DetailViewerField("idImagenUsuario");

		tileGridImagenesUsuario.setFields(pictureField, nameField);
		tileGridImagenesUsuario.setVisible(false);

		formularioLamina = new DynamicForm();
		formularioLamina.setIsGroup(true);
		formularioLamina.setGroupTitle("Informaci\u00F3n de la L\u00E1mina");
		formularioLamina.setAutoFocus(false);
		formularioLamina.setID("formularioLamina");
		formularioLamina.setNumCols(6);
		formularioLamina.setPadding(10);

		textAreaPregunta = new StaticTextItem();
		textAreaPregunta.setTitle("Pregunta");
		textAreaPregunta.setWidth(320);
		textAreaPregunta.setCanEdit(false);

		integerTiempoPregunta = new IntegerItem();
		integerTiempoPregunta.setTitle("Tiempo");
		integerTiempoPregunta.setCanEdit(false);

		integerCaracteresPregunta = new IntegerItem();
		integerCaracteresPregunta.setTitle("Caracteres");
		integerCaracteresPregunta.setCanEdit(false);

		formularioLamina.setFields(textAreaPregunta, integerTiempoPregunta,
				integerCaracteresPregunta);
		formularioLamina.setVisible(false);

		botonRegresar = new ToolStripButton("Volver",
				"icons/16/document_plain_new.png");

		botonRegresar
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						botonRegresar.disable();
						listGridPruebas.setVisible(true);
						tileGridImagenesUsuario.setVisible(false);
						formularioLamina.setVisible(false);
						enCategoria = true;
						botonNuevoBasico.setTitle("Nueva Categor\u00EDa");
						botonEliminarBasico.setTitle("Eliminar Categor\u00EDa");
						Maestro.setListaPruebas();
					}

				});

		botonNuevoBasico = new ToolStripButton("Nueva Categor\u00EDa",
				"icons/16/document_plain_new.png");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						if (enCategoria) {
							mostrarDialogoCrearCategoria();
						} else {
							mostrarDialogoCrearLamina(null);
						}
					}

				});

		botonEliminarBasico = new ToolStripButton("Eliminar Categor\u00EDa",
				"icons/16/document_plain_new.png");

		botonEliminarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						if (enCategoria) {
							PruebaListGridRecord record = (PruebaListGridRecord) listGridPruebas
									.getSelectedRecord();
							if (record != null) {
								PruebaUsuarioBO categoria = new PruebaUsuarioBO();
								categoria.setDescripcion(record
										.getAttribute("descripcion"));
								categoria.setIdentificador(record
										.getAttributeAsInt("id"));
								categoria.setNombre(record
										.getAttribute("nombre"));
								categoria.setUsuarioAdministradorID(record
										.getAttributeAsInt("usuarioID"));
								Maestro.eliminarPrueba(categoria);
							} else {
								SC.warn("Debe seleccionar la categor\u00EDa que desea eliminar");
							}
						} else {
							PreguntaCategoriaTileRecord record = (PreguntaCategoriaTileRecord) tileGridImagenesUsuario
									.getSelectedRecord();
							if (record != null) {
								PreguntaUsuarioBO pregunta = PreguntaCategoriaTileRecord
										.getBO(record);
								Maestro.eliminarPreguntaCategoria(pregunta);
							} else {
								SC.warn("Debe seleccionar la l\u00E1mina que desea eliminar");
							}
						}
					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonRegresar);
		menuBarUsuarioBasico.addButton(botonNuevoBasico);
		menuBarUsuarioBasico.addButton(botonEliminarBasico);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addSeparator();
		botonRegresar.disable();

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(listGridPruebas);
		vl1.addMember(tileGridImagenesUsuario);
		vl1.addMember(formularioLamina);
		vl1.addMember(menuBarUsuarioBasico);

		addMember(vl1);

	}

	private void mostrarDialogoCrearCategoria() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(200);
		winModal.setTitle("Crear una Categor\u00EDa");
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

		formNuevaCategoria = new DynamicForm();
		formNuevaCategoria.setHeight("40%");
		formNuevaCategoria.setWidth100();
		formNuevaCategoria.setPadding(5);
		formNuevaCategoria.setLayoutAlign(VerticalAlignment.BOTTOM);

		textNombreCategoriaNueva = new TextItem();
		textNombreCategoriaNueva.setTitle("Nombre");
		textNombreCategoriaNueva.setRequired(true);
		textNombreCategoriaNueva.setWidth("100%");

		textAreaDescripcionCategoriaNueva = new TextAreaItem();
		textAreaDescripcionCategoriaNueva.setTitle("Descripci\u00F3n");
		textAreaDescripcionCategoriaNueva.setRequired(true);
		textAreaDescripcionCategoriaNueva.setWidth("100%");

		formNuevaCategoria.setFields(textNombreCategoriaNueva,
				textAreaDescripcionCategoriaNueva);

		IButton boton = new IButton("Crear");
		boton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				boolean t = false;
				try {
					t = formNuevaCategoria.validate();
				} catch (UmbrellaException e) {
					e.printStackTrace();
				}
				if (t) {
					PruebaUsuarioBO prueba = new PruebaUsuarioBO();
					prueba.setNombre(textNombreCategoriaNueva
							.getValueAsString());
					prueba.setDescripcion(textAreaDescripcionCategoriaNueva
							.getValueAsString());
					Maestro.agregarPrueba(prueba);
					winModal.destroy();
				}

			}
		});

		winModal.addItem(formNuevaCategoria);
		winModal.addItem(boton);
		winModal.show();
	}

	private void mostrarDialogoCrearLamina(
			PreguntaCategoriaTileRecord preguntaCategoriaTileRecord) {
		final Window winModal = new Window();
		winModal.setWidth("100%");
		winModal.setHeight("100%");
		winModal.setTitle("Crear una L\u00E1mina");
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

		if (preguntaCategoriaTileRecord == null) {
			panelAgregarLaminas = new PanelAgregarLaminas(this);
			Maestro.obtenerImagenesUsuario();
			winModal.addItem(panelAgregarLaminas);
		}

		winModal.show();
	}

	public void actualizarPruebas(PruebaListGridRecord[] records) {
		listGridPruebas.setData(records);
	}

	public void actualizarPreguntasCategoria(
			PreguntaCategoriaTileRecord[] record) {
		tileGridImagenesUsuario.setData(record);
	}

	private void limpiarFormularioLamina() {
		textAreaPregunta.setValue("");
		integerCaracteresPregunta.setValue("");
		integerTiempoPregunta.setValue("");
	}

	public void actualizarImagenesUsuario(ImagenRecord[] record) {
		if (record == null) {
			System.out.println("imagenes nulas");
		}
		try {
			panelAgregarLaminas.actualizarImagenesUsuario(record);
		} catch (NullPointerException e) {
		}
	}

	public void agregarPreguntaPrueba(PreguntaUsuarioBO bo) {
		PruebaUsuarioBO categoria = PruebaListGridRecord
				.getBO(pruebaSeleccionada);
		Maestro.agregarPreguntaUsuario(bo, categoria);
	}

	public void actualizarPreguntasCategoria() {
		panelAgregarLaminas.limpiarCamposPreguntas();
		Maestro.obtenerPreguntasPorPrueba(pruebaSeleccionada
				.getAttributeAsInt("id"));
	}

	public void editarPreguntaCategoria(PreguntaUsuarioBO bo) {
		Maestro.editarPreguntaUsuario(bo);

	}
}
