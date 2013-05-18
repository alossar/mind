package co.mind.management.maestro.client.pruebas;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.maestro.client.pruebas.contenedores.PanelAgregarPregunta;
import co.mind.management.maestro.client.pruebas.contenedores.PanelEditarPregunta;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.event.shared.UmbrellaException;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelContenidoPruebas extends HLayout {

	private ListGrid listGridPruebas;
	private DynamicForm formNuevaCategoria;
	private TextItem textNombreCategoriaNueva;
	private TextAreaItem textAreaDescripcionCategoriaNueva;
	private ImgButton botonRegresar;
	private ImgButton botonNuevoBasico;
	private ImgButton botonEliminarBasico;
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
	private TextItem searchItem;
	private DynamicForm formBusqueda;
	private boolean deProceso = false;
	private HTMLFlow tituloPrueba;

	public PanelContenidoPruebas() {
		setWidth("90%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		listGridPruebas = new ListGrid();
		listGridPruebas.setWidth100();
		listGridPruebas.setHeight100();
		listGridPruebas.setShowAllRecords(true);
		listGridPruebas.setWrapCells(true);
		listGridPruebas.setFixedRecordHeights(false);
		listGridPruebas.setSelectionType(SelectionStyle.SINGLE);
		listGridPruebas.setEmptyMessage("No se encuentran pruebas.");

		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		preguntasField.setCanEdit(false);
		ListGridField tiempoField = new ListGridField("tiempoPrueba",
				"Tiempo (Minutos)");
		tiempoField.setCanEdit(false);
		listGridPruebas.setFields(nombreField, apellidosField, preguntasField,
				tiempoField);
		listGridPruebas.setCanEdit(true);
		listGridPruebas.setEditEvent(ListGridEditEvent.NONE);
		listGridPruebas.setCanResizeFields(true);

		listGridPruebas
				.addRecordDoubleClickHandler(new com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(
							com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent event) {
						PruebaListGridRecord p = (PruebaListGridRecord) listGridPruebas
								.getSelectedRecord();
						pruebaSeleccionada = PruebaListGridRecord.getBO(p);
						if (pruebaSeleccionada != null) {
							deProceso = false;
							Maestro.obtenerPreguntasPorPrueba(pruebaSeleccionada
									.getIdentificador());
							limpiarFormularioLamina();
							// PanelPruebas.setEncabezado(
							// "Prueba " + pruebaSeleccionada.getNombre(),
							// pruebaSeleccionada.getDescripcion(),
							// "insumos/pruebas/logoTemas.png");
							botonRegresar.setDisabled(false);
							listGridPruebas.setVisible(false);
							tileGridImagenesUsuario.setVisible(true);
							formularioLamina.setVisible(true);
							enCategoria = false;
							// limpiarFormularioLamina();
							botonNuevoBasico.setTooltip("Nueva Pregunta");
							botonEliminarBasico.setTooltip("Eliminar Pregunta");
							formBusqueda.setVisible(false);
							tituloPrueba.setContents("<h2>"
									+ pruebaSeleccionada.getNombre() + "</h2>");
							tituloPrueba.setVisible(true);
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

		listGridPruebas.setGenerateDoubleClickOnEnter(true);

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
				if (record != null) {
					textAreaPregunta.setValue(record.getAttribute("pregunta"));
					integerCaracteresPregunta.setValue(record
							.getAttributeAsInt("caracteres"));
					integerTiempoPregunta.setValue(record
							.getAttributeAsInt("tiempo"));
				}
			}
		});

		tileGridImagenesUsuario
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {
					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						PreguntaCategoriaTileRecord record = (PreguntaCategoriaTileRecord) tileGridImagenesUsuario
								.getSelectedRecord();
						if (record != null) {
							mostrarDialogoCrearLamina(record);
						}
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

		botonRegresar = new ImgButton();
		botonRegresar.setWidth(35);
		botonRegresar.setHeight(35);
		botonRegresar.setShowRollOver(true);
		botonRegresar.setShowDown(true);
		botonRegresar.setSrc("icons/atras.png");
		botonRegresar.setDisabled(true);

		botonRegresar
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						if (deProceso) {
							volverAProceso();
						} else {
							setEstadoInicial();
						}
					}

				});

		botonNuevoBasico = new ImgButton();
		botonNuevoBasico.setWidth(35);
		botonNuevoBasico.setHeight(35);
		botonNuevoBasico.setShowRollOver(true);
		botonNuevoBasico.setShowDown(true);
		botonNuevoBasico.setSrc("icons/agregar.png");
		botonNuevoBasico.setDisabled(false);
		botonNuevoBasico.setTooltip("Nueva prueba");

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

		botonEliminarBasico = new ImgButton();
		botonEliminarBasico.setWidth(35);
		botonEliminarBasico.setHeight(35);
		botonEliminarBasico.setShowRollOver(true);
		botonEliminarBasico.setShowDown(true);
		botonEliminarBasico.setSrc("icons/eliminar.png");
		botonEliminarBasico.setDisabled(false);
		botonEliminarBasico.setTooltip("Eliminar prueba");

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

		searchItem = new TextItem("description", "Buscar Prueba");
		searchItem.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if ("enter".equalsIgnoreCase(event.getKeyName())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarPruebasClave(keyword);
					} else {
						Maestro.setListaPruebas();
					}
				}
			}
		});
		final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
		final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
		searchItem.setIcons(findIcon, cancelIcon);

		searchItem.addIconClickHandler(new IconClickHandler() {
			public void onIconClick(IconClickEvent event) {
				FormItemIcon icon = event.getIcon();
				if (icon.getSrc().equals(cancelIcon.getSrc())) {
					searchItem.setValue("");
					Maestro.setListaPruebas();
				} else if (icon.getSrc().equals(findIcon.getSrc())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarPruebasClave(keyword);
					} else {
						Maestro.setListaPruebas();
					}
				}
			}
		});

		searchItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String valor = searchItem.getValueAsString();
				if (valor == "" || valor == null) {
					Maestro.setListaPruebas();
				}
			}
		});

		formBusqueda = new DynamicForm();
		formBusqueda.setWidth("250px");
		formBusqueda.setHeight("33px");
		formBusqueda.setPadding(5);
		formBusqueda.setLayoutAlign(VerticalAlignment.CENTER);
		formBusqueda.setFields(searchItem);

		tituloPrueba = new HTMLFlow();
		tituloPrueba.setTop(60);
		tituloPrueba.setLeft(0);
		tituloPrueba.setWidth("200");
		tituloPrueba.setVisible(false);

		HLayout hl1 = new HLayout();
		hl1.setWidth100();
		hl1.setHeight("40px");

		HLayout hlRelleno = new HLayout();
		hlRelleno.setWidth("*");
		hlRelleno.setHeight("1px");

		hl1.addMember(tituloPrueba);
		hl1.addMember(hlRelleno);
		hl1.addMember(formBusqueda);
		hl1.addMember(botonRegresar);
		hl1.addMember(botonNuevoBasico);
		hl1.addMember(botonEliminarBasico);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(hl1);
		vl1.addMember(listGridPruebas);
		vl1.addMember(tileGridImagenesUsuario);
		vl1.addMember(formularioLamina);

		addMember(vl1);

	}

	private void mostrarDialogoCrearCategoria() {
		final Window winModal = new Window();

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo("Crear Prueba",
				"Cree una prueba con sus preguntas respectivas",
				"img/admin/bot2.png");
		p.setSize("100%", "70px");

		winModal.setWidth(350);
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
		textNombreCategoriaNueva.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE);

		textAreaDescripcionCategoriaNueva = new TextAreaItem();
		textAreaDescripcionCategoriaNueva.setTitle("Descripci\u00F3n");
		textAreaDescripcionCategoriaNueva.setRequired(true);
		textAreaDescripcionCategoriaNueva
				.setLength(Convencion.MAXIMA_LONGITUD_DESCRIPCION_PRUEBA);

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

		windowCrearLamina.setWidth("900px");
		windowCrearLamina.setHeight("100%");
		windowCrearLamina.setTitle("Crear una Pregunta");
		windowCrearLamina.setShowMinimizeButton(false);
		windowCrearLamina.setIsModal(true);
		windowCrearLamina.setShowModalMask(true);
		windowCrearLamina.centerInPage();
		windowCrearLamina.setCanDragReposition(false);
		windowCrearLamina.setCanDrag(false);
		windowCrearLamina.setCanDragResize(false);
		windowCrearLamina.setCanDragScroll(false);
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
		botonRegresar.setDisabled(true);
		listGridPruebas.setVisible(true);
		tileGridImagenesUsuario.setVisible(false);
		formularioLamina.setVisible(false);
		enCategoria = true;
		botonNuevoBasico.setTooltip("Nueva Prueba");
		botonEliminarBasico.setTooltip("Eliminar Prueba");
		// PanelPruebas.setEncabezadoPredeterminado();
		Maestro.setListaPruebas();
		formBusqueda.setVisible(true);
		tituloPrueba.setVisible(false);
	}

	public void setPreguntasPrueba(PruebaUsuarioBO prueba) {
		pruebaSeleccionada = prueba;
		deProceso = true;
		botonRegresar.setDisabled(false);
		botonRegresar.setTooltip("Volver a Proceso");
		listGridPruebas.setVisible(false);
		tileGridImagenesUsuario.setVisible(true);
		formularioLamina.setVisible(true);
		enCategoria = false;
		botonNuevoBasico.setTooltip("Nueva Pregunta");
		botonEliminarBasico.setTooltip("Eliminar Pregunta");
		// PanelPruebas.setEncabezadoPredeterminado();
		formBusqueda.setVisible(false);
		tituloPrueba.setContents("<h2>" + pruebaSeleccionada.getNombre()
				+ "</h2>");
		tituloPrueba.setVisible(true);
		limpiarFormularioLamina();
	}

	private void volverAProceso() {
		Maestro.obtenerPruebasProceso(null);
		Maestro.irAProcesoDesdePrueba();
		// setEstadoInicial();
	}
}
