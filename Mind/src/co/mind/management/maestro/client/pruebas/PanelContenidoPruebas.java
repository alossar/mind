package co.mind.management.maestro.client.pruebas;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.maestro.client.pruebas.contenedores.PanelAgregarPregunta;
import co.mind.management.maestro.client.pruebas.contenedores.PanelEditarPregunta;
import co.mind.management.shared.datasources.ProcesosDataSource;
import co.mind.management.shared.datasources.PruebasDataSource;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.PruebaListGridRecord;

import com.google.gwt.event.shared.UmbrellaException;
import com.smartgwt.client.types.ListGridEditEvent;
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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.EditorEnterEvent;
import com.smartgwt.client.widgets.grid.events.EditorEnterHandler;
import com.smartgwt.client.widgets.grid.events.EditorExitEvent;
import com.smartgwt.client.widgets.grid.events.EditorExitHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelContenidoPruebas extends HLayout {

	private ListGrid listGridPruebas;
	private DynamicForm formNuevaCategoria;
	private TextItem textNombreCategoriaNueva;
	private TextAreaItem textAreaDescripcionCategoriaNueva;
	private ToolStripButton botonRegresar;
	private ToolStripButton botonNuevoBasico;
	private ToolStripButton botonEliminarBasico;
	private ToolStripButton botonDuplicarBasico;
	private TileGrid tileGridImagenesUsuario;
	private DynamicForm formularioLamina;
	private StaticTextItem textAreaPregunta;
	private IntegerItem integerTiempoPregunta;
	private IntegerItem integerCaracteresPregunta;
	private boolean enCategoria = true;
	private PanelAgregarPregunta panelAgregarLaminas;
	private PruebaUsuarioBO pruebaSeleccionada;
	private Window windowCrearLamina;
	private List<ImagenUsuarioBO> listaImagenesUsuario;
	private PanelEditarPregunta panelEditarPregunta;
	private PruebasDataSource pruebasDataSource;

	public PanelContenidoPruebas() {
		setWidth("100%");
		setHeight("80%%");
		setBackgroundColor("white");
		setPadding(15);

		listGridPruebas = new ListGrid();
		listGridPruebas.setWidth100();
		listGridPruebas.setHeight100();
		pruebasDataSource = new PruebasDataSource("pruebas");
		listGridPruebas.setDataSource(pruebasDataSource);
		listGridPruebas.setShowAllRecords(true);
		listGridPruebas.setSelectionType(SelectionStyle.SINGLE);
		listGridPruebas.setEmptyMessage("No hay pruebas en su cuenta.");

		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		preguntasField.setCanEdit(false);
		ListGridField tiempoField = new ListGridField("tiempoPrueba",
				"Tiempo (min)");
		tiempoField.setCanEdit(false);
		listGridPruebas.setFields(nombreField, apellidosField, preguntasField,
				tiempoField);
		listGridPruebas.setCanEdit(true);
		listGridPruebas.setEditEvent(ListGridEditEvent.NONE);
		listGridPruebas.setCanResizeFields(true);
		listGridPruebas.setShowFilterEditor(true);
		listGridPruebas.setAutoFetchData(true);

		listGridPruebas.addCellDoubleClickHandler(new CellDoubleClickHandler() {

			@Override
			public void onCellDoubleClick(CellDoubleClickEvent event) {
				PruebaListGridRecord p = (PruebaListGridRecord) listGridPruebas
						.getSelectedRecord();
				pruebaSeleccionada = PruebaListGridRecord.getBO(p);
				if (pruebaSeleccionada != null) {
					Maestro.obtenerPreguntasPorPrueba(pruebaSeleccionada
							.getIdentificador());
					limpiarFormularioLamina();
					PanelPruebas.setEncabezado(
							"Prueba " + pruebaSeleccionada.getNombre(),
							pruebaSeleccionada.getDescripcion(),
							"img/check.png");
					botonRegresar.setVisible(true);
					listGridPruebas.setVisible(false);
					tileGridImagenesUsuario.setVisible(true);
					formularioLamina.setVisible(true);
					enCategoria = false;
					// limpiarFormularioLamina();
					botonNuevoBasico.setTitle("Nueva Pregunta");
					botonEliminarBasico.setTitle("Eliminar Pregunta");
					botonDuplicarBasico.setVisible(false);

				}
			}
		});

		listGridPruebas.addRowContextClickHandler(new RowContextClickHandler() {
			public void onRowContextClick(RowContextClickEvent event) {
				if (listGridPruebas.startEditing(event.getRowNum(),
						event.getColNum(), false)) {
				}
				event.cancel();
			}
		});

		listGridPruebas.addEditCompleteHandler(new EditCompleteHandler() {

			@Override
			public void onEditComplete(EditCompleteEvent event) {
				String nombre = (String) event.getNewValues().get("nombre");
				String descripcion = (String) event.getNewValues().get(
						"descripcion");

				ListGridRecord[] r = listGridPruebas.getRecords();
				PruebaListGridRecord procesoRecord = (PruebaListGridRecord) r[event
						.getRowNum()];
				PruebaUsuarioBO prueba = PruebaListGridRecord
						.getBO(procesoRecord);
				if (nombre != null && descripcion != "") {
					prueba.setNombre(nombre);
				}
				if (descripcion != null && descripcion != "") {
					prueba.setDescripcion(descripcion);
				}
				Maestro.editarPrueba(prueba);
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

		tileGridImagenesUsuario.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				PreguntaCategoriaTileRecord record = (PreguntaCategoriaTileRecord) tileGridImagenesUsuario
						.getSelectedRecord();
				mostrarDialogoCrearLamina(record);

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
		formularioLamina.setGroupTitle("Informaci\u00F3n de la Pregunta");
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

						setEstadoInicial();
					}

				});

		botonNuevoBasico = new ToolStripButton("Nueva Prueba",
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

		botonEliminarBasico = new ToolStripButton("Eliminar Prueba",
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
								PruebaUsuarioBO categoria = PruebaListGridRecord
										.getBO(record);
								Maestro.eliminarPrueba(categoria);
							} else {
								SC.warn("Debe seleccionar la prueba que desea eliminar.");
							}
						} else {
							PreguntaCategoriaTileRecord record = (PreguntaCategoriaTileRecord) tileGridImagenesUsuario
									.getSelectedRecord();
							if (record != null) {
								PreguntaUsuarioBO pregunta = PreguntaCategoriaTileRecord
										.getBO(record);
								Maestro.eliminarPreguntaCategoria(pregunta,
										pruebaSeleccionada);
							} else {
								SC.warn("Debe seleccionar la Pregunta que desea eliminar");
							}
						}
					}
				});

		botonDuplicarBasico = new ToolStripButton("Duplicar Prueba",
				"icons/16/document_plain_new.png");

		botonDuplicarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						PruebaListGridRecord record = (PruebaListGridRecord) listGridPruebas
								.getSelectedRecord();
						if (record != null) {
							PruebaUsuarioBO categoria = PruebaListGridRecord
									.getBO(record);
							Maestro.duplicarPrueba(categoria);
						} else {
							SC.warn("Debe seleccionar el proceso que desea eliminar.");
						}
					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonRegresar);
		menuBarUsuarioBasico.addButton(botonNuevoBasico);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addButton(botonDuplicarBasico);
		menuBarUsuarioBasico.addButton(botonEliminarBasico);
		menuBarUsuarioBasico.addSeparator();
		botonRegresar.setVisible(false);

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

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo("Crear Prueba",
				"Cree una prueba con sus preguntas respectivas",
				"img/check.png");
		p.setSize("100%", "70px");

		winModal.setWidth(400);
		winModal.setHeight(300);
		winModal.setTitle("Crear una Prueba");
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

		winModal.addItem(p);
		winModal.addItem(formNuevaCategoria);
		winModal.addItem(boton);
		winModal.show();
	}

	private void mostrarDialogoCrearLamina(
			PreguntaCategoriaTileRecord preguntaCategoriaTileRecord) {

		windowCrearLamina = new Window();

		windowCrearLamina.setWidth("100%");
		windowCrearLamina.setHeight("100%");
		windowCrearLamina.setTitle("Crear una Pregunta");
		windowCrearLamina.setShowMinimizeButton(false);
		windowCrearLamina.setIsModal(true);
		windowCrearLamina.setShowModalMask(true);
		windowCrearLamina.centerInPage();
		windowCrearLamina.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				windowCrearLamina.destroy();
			}
		});

		if (preguntaCategoriaTileRecord == null) {
			panelAgregarLaminas = new PanelAgregarPregunta(this);
			panelAgregarLaminas.actualizarImagenesUsuario(listaImagenesUsuario);
			windowCrearLamina.addItem(panelAgregarLaminas);
		} else {
			panelEditarPregunta = new PanelEditarPregunta(this,
					PreguntaCategoriaTileRecord
							.getBO(preguntaCategoriaTileRecord));
			panelEditarPregunta.actualizarImagenesUsuario(listaImagenesUsuario);
			windowCrearLamina.addItem(panelEditarPregunta);
		}
		windowCrearLamina.show();
	}

	public void actualizarPruebas(List<PruebaUsuarioBO> result) {
		pruebasDataSource.setTestData(PruebaListGridRecord.getRecords(result));
		listGridPruebas.setData(PruebaListGridRecord.getRecords(result));
	}

	public void actualizarPreguntasCategoria(List<PreguntaUsuarioBO> result) {
		tileGridImagenesUsuario.setData(PreguntaCategoriaTileRecord
				.getRecords(result));
	}

	public void actualizarImagenesUsuario(List<ImagenUsuarioBO> result) {
		listaImagenesUsuario = result;
	}

	public void agregarPreguntaPrueba(PreguntaUsuarioBO bo) {
		Maestro.agregarPreguntaUsuario(bo, pruebaSeleccionada);
		windowCrearLamina.destroy();
	}

	public void editarPreguntaCategoria(PreguntaUsuarioBO bo) {
		Maestro.editarPreguntaUsuario(bo, pruebaSeleccionada);
		windowCrearLamina.destroy();
	}

	private void limpiarFormularioLamina() {
		textAreaPregunta.setValue("");
		integerCaracteresPregunta.setValue("");
		integerTiempoPregunta.setValue("");
	}

	public void setEstadoInicial() {
		botonRegresar.setVisible(false);
		listGridPruebas.setVisible(true);
		tileGridImagenesUsuario.setVisible(false);
		formularioLamina.setVisible(false);
		enCategoria = true;
		botonNuevoBasico.setTitle("Nueva Prueba");
		botonEliminarBasico.setTitle("Eliminar Prueba");
		PanelPruebas.setEncabezadoPredeterminado();
		Maestro.setListaPruebas();
		botonDuplicarBasico.setVisible(true);

	}
}
