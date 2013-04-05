package co.mind.management.maestro.client.procesos;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelProcesoEspecifico extends HLayout {

	private VLayout panelInformacionProceso;
	private VLayout panelListados;
	private ListGrid listGridTemasDeProceso;
	private ListGrid listGridParticipaciones;
	private ListGrid listGridResultados;
	private TextAreaItem textAreaDescripcionProceso;
	private DateTimeItem dateTimeItemFechaInicio;
	private DateTimeItem dateTimeItemFechaFinalizacion;
	private DateTimeItem dateTimeItemFechaCreacion;
	private ToolStripButton botonGenerarReporte;
	private ToolStripButton botonSeleccionarTodo;
	private ToolStripButton botonAgregar;
	private ToolStripButton botonEliminar;
	private ToolStripButton botonEnviarParticipaciones;
	private SelectItem selectTipoReporte;
	private PanelEncabezadoDialogo panelEncabezadoProceso;
	private DynamicForm formNuevaParticipacion;
	private IntegerItem textIdentificadorUsuarioBasico;
	private TextItem textNombreUsuarioBasico;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private ProcesoUsuarioBO procesoActual;
	private DynamicForm formTemas;
	private ListGrid listGridPruebas;
	private List<PruebaUsuarioBO> listaPruebasRestantes;
	private List<EvaluadoBO> listaEvaluadosRestantes;
	private ListGrid listGridUsuariosBasicos;
	private TextItem textNombreCategoriaNueva;
	private TextAreaItem textAreaDescripcionCategoriaNueva;
	private boolean nuevaParticipacion;
	private boolean nuevoTema;

	public PanelProcesoEspecifico() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		panelInformacionProceso = new VLayout();
		panelInformacionProceso.setHeight100();
		panelInformacionProceso.setWidth("20%");

		DynamicForm formProceso = new DynamicForm();
		formProceso.setWidth100();
		formProceso.setHeight100();
		formProceso.setPadding(5);

		textAreaDescripcionProceso = new TextAreaItem();
		textAreaDescripcionProceso.setTitle("Descripci\u00F3n");
		textAreaDescripcionProceso.setCanEdit(false);

		dateTimeItemFechaInicio = new DateTimeItem();
		dateTimeItemFechaInicio.setTitle("Fecha Inicio");
		dateTimeItemFechaInicio.setCanEdit(false);

		dateTimeItemFechaFinalizacion = new DateTimeItem();
		dateTimeItemFechaFinalizacion.setTitle("Fecha Finalizaci\u00F3n");
		dateTimeItemFechaFinalizacion.setCanEdit(false);

		dateTimeItemFechaCreacion = new DateTimeItem();
		dateTimeItemFechaCreacion.setTitle("Fecha Creaci\u00F3n");
		dateTimeItemFechaCreacion.setCanEdit(false);

		formProceso.setFields(textAreaDescripcionProceso,
				dateTimeItemFechaInicio, dateTimeItemFechaFinalizacion,
				dateTimeItemFechaCreacion);

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("80%");
		v2.setBackgroundColor("white");
		v2.addChild(formProceso);

		panelEncabezadoProceso = new PanelEncabezadoDialogo("Proceso",
				"Informaci\u00F3n del proceso.", "img/check.png");
		panelEncabezadoProceso.setSize("100%", "70px");

		panelInformacionProceso.addMember(panelEncabezadoProceso);
		panelInformacionProceso.addMember(v2);

		panelListados = new VLayout();
		panelListados.setHeight100();
		panelListados.setWidth("70%");

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {
				Tab t = event.getTab();
				if (t.getID().equalsIgnoreCase("participaciones")) {
					selectTipoReporte.setVisible(false);
					botonGenerarReporte.setVisible(false);
					botonAgregar.setTitle("Agregar Participacion");
					botonAgregar.setVisible(true);
					botonEliminar.setVisible(false);
					botonEnviarParticipaciones.setVisible(true);
				} else if (t.getID().equalsIgnoreCase("resultados")) {
					selectTipoReporte.setVisible(true);
					botonGenerarReporte.setVisible(true);
					botonAgregar.setVisible(false);
					botonEliminar.setVisible(false);
					botonEnviarParticipaciones.setVisible(false);
				} else if (t.getID().equalsIgnoreCase("temas")) {
					selectTipoReporte.setVisible(false);
					botonGenerarReporte.setVisible(false);
					botonAgregar.setTitle("Agregar Tema");
					botonAgregar.setVisible(true);
					botonEliminar.setVisible(true);
					botonEnviarParticipaciones.setVisible(false);
				}
			}
		});

		Tab tabParticipaciones = new Tab("Participaciones",
				"pieces/16/pawn_blue.png");
		tabParticipaciones.setID("participaciones");

		listGridParticipaciones = new ListGrid();
		listGridParticipaciones.setWidth100();
		listGridParticipaciones.setHeight100();
		listGridParticipaciones.setShowAllRecords(true);
		listGridParticipaciones
				.setEmptyMessage("No hay evaluados en este proceso.");

		ListGridField idField = new ListGridField("idEval", "C\u00E9dula");
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidoField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoField = new ListGridField("correo", "Correo");
		ListGridField codigoField = new ListGridField("codigo",
				"C\u00F3digo Acceso");
		ListGridField estadoField = new ListGridField("estado", "Estado");
		listGridParticipaciones.setFields(idField, nombreField, apellidoField,
				correoField, codigoField, estadoField);
		listGridParticipaciones.setCanResizeFields(true);
		listGridParticipaciones.setAutoFetchData(true);
		listGridParticipaciones.setShowFilterEditor(true);
		listGridParticipaciones
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		tabParticipaciones.setPane(listGridParticipaciones);

		Tab tabResultados = new Tab("Resultados", "pieces/16/pawn_green.png");
		tabResultados.setID("resultados");

		listGridResultados = new ListGrid();
		listGridResultados.setWidth100();
		listGridResultados.setHeight100();
		listGridResultados.setShowAllRecords(true);
		listGridResultados.setFields(idField, nombreField, apellidoField,
				correoField, codigoField, estadoField);
		listGridResultados.setCanResizeFields(true);
		listGridResultados
				.setEmptyMessage("No hay resultados en este proceso.");

		listGridResultados.setAutoFetchData(true);
		listGridResultados.setShowFilterEditor(true);
		listGridResultados.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		tabResultados.setPane(listGridResultados);

		Tab tabTemas = new Tab("Temas", "pieces/16/pawn_green.png");
		tabTemas.setID("temas");

		listGridTemasDeProceso = new ListGrid();
		listGridTemasDeProceso.setWidth100();
		listGridTemasDeProceso.setHeight100();
		listGridTemasDeProceso.setShowAllRecords(true);
		listGridTemasDeProceso.setSelectionType(SelectionStyle.SINGLE);
		listGridTemasDeProceso.setEmptyMessage("No hay temas en este proceso.");

		ListGridField nombrePruebaField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		ListGridField tiempoField = new ListGridField("tiempoPrueba",
				"Tiempo (Segundos)");
		listGridTemasDeProceso.setFields(nombrePruebaField, apellidosField,
				preguntasField, tiempoField);
		listGridTemasDeProceso.setCanResizeFields(true);
		listGridTemasDeProceso.setAutoFetchData(true);
		listGridTemasDeProceso.setShowFilterEditor(true);
		listGridTemasDeProceso
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		tabTemas.setPane(listGridTemasDeProceso);

		topTabSet.addTab(tabTemas);
		topTabSet.addTab(tabParticipaciones);
		topTabSet.addTab(tabResultados);

		botonSeleccionarTodo = new ToolStripButton("Seleccionar Todos",
				"icons/16/document_plain_new.png");

		botonSeleccionarTodo
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						Tab t = topTabSet.getSelectedTab();
						if (t.getID().equalsIgnoreCase("temas")) {
							listGridTemasDeProceso.selectAllRecords();
						} else if (t.getID()
								.equalsIgnoreCase("participaciones")) {
							listGridParticipaciones.selectAllRecords();
						} else if (t.getID().equalsIgnoreCase("resultados")) {
							listGridResultados.selectAllRecords();
						}
					}
				});

		botonAgregar = new ToolStripButton("Agregar Tema",
				"icons/16/document_plain_new.png");

		botonAgregar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Tab t = topTabSet.getSelectedTab();
				if (t.getID().equalsIgnoreCase("temas")) {
					mostrarDialogoAgregarTema();
				} else if (t.getID().equalsIgnoreCase("participaciones")) {
					mostrarDialogoAgregarParticipacion();
				}
			}
		});

		botonEliminar = new ToolStripButton("Eliminar Tema",
				"icons/16/document_plain_new.png");

		botonEliminar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Tab t = topTabSet.getSelectedTab();
				if (t.getID().equalsIgnoreCase("temas")) {
					Record[] records = listGridTemasDeProceso
							.getSelectedRecords();
					if (records == null) {
						SC.warn("Debe seleccionar los temas que desea eliminar.");
					} else {
						PruebaListGridRecord[] participaciones = new PruebaListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							participaciones[i] = (PruebaListGridRecord) records[i];
						}
						Maestro.eliminarPrubasDeProceso(PruebaListGridRecord
								.getBO(participaciones));
					}
				} else if (t.getID().equalsIgnoreCase("participaciones")) {
					Record[] records = listGridParticipaciones
							.getSelectedRecords();
					if (records == null) {
						SC.warn("Debe seleccionar los temas que desea eliminar.");
					} else {
						ParticipacionEnProcesoListGridRecord[] participaciones = new ParticipacionEnProcesoListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							participaciones[i] = (ParticipacionEnProcesoListGridRecord) records[i];
						}
						Maestro.eliminarParticipacionesDeProceso(ParticipacionEnProcesoListGridRecord
								.getBO(participaciones));
					}
				}
			}
		});

		botonEnviarParticipaciones = new ToolStripButton(
				"Enviar Participaciones", "icons/16/document_plain_new.png");

		botonEnviarParticipaciones.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord[] records = listGridParticipaciones
						.getSelectedRecords();
				if (records != null) {
					ParticipacionEnProcesoListGridRecord[] participacionRecords = new ParticipacionEnProcesoListGridRecord[records.length];
					for (int i = 0; i < records.length; i++) {
						participacionRecords[i] = (ParticipacionEnProcesoListGridRecord) records[i];
					}
					List<ParticipacionEnProcesoBO> participacionesBO = ParticipacionEnProcesoListGridRecord
							.getBO(participacionRecords);
					Maestro.enviarNotificacionesCorreo(participacionesBO);
				} else {
					SC.warn("Seleccione a los evaluados que desea que reciban la notificacion de correo");
				}
			}
		});

		DynamicForm fontForm = new DynamicForm();
		fontForm.setShowResizeBar(true);
		fontForm.setWidth("*");
		fontForm.setMinWidth(50);
		fontForm.setNumCols(1);

		selectTipoReporte = new SelectItem();
		selectTipoReporte.setShowTitle(false);
		selectTipoReporte.setWidth("*");

		LinkedHashMap<String, String> valueMap = new LinkedHashMap<String, String>();
		valueMap.put("excel", "<span style='font-family:verdana'>Excel</span>");
		valueMap.put("pdf", "<span style='font-family:verdana'>PDF</span>");
		selectTipoReporte.setValueMap(valueMap);
		fontForm.setItems(selectTipoReporte);
		selectTipoReporte.setDefaultValue("pdf");

		botonGenerarReporte = new ToolStripButton("Generar Reporte",
				"icons/16/document_plain_new.png");

		botonGenerarReporte
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						ListGridRecord[] records = listGridResultados
								.getSelectedRecords();
						if (records.length > 0) {
							ParticipacionEnProcesoListGridRecord[] participacionRecords = new ParticipacionEnProcesoListGridRecord[records.length];
							for (int i = 0; i < records.length; i++) {
								participacionRecords[i] = (ParticipacionEnProcesoListGridRecord) records[i];
							}
							List<ParticipacionEnProcesoBO> participacionesBO = ParticipacionEnProcesoListGridRecord
									.getBO(participacionRecords);
							String reporte = selectTipoReporte
									.getValueAsString();
							if (reporte.equalsIgnoreCase("pdf")) {
								Maestro.generarReportePDF(participacionesBO);
							} else if (reporte.equalsIgnoreCase("excel")) {
								Maestro.generarReporteExcel(participacionesBO);
							}

						} else {
							SC.warn("Debe seleccionar los resultados que desea exportar");
						}
					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonAgregar);
		menuBarUsuarioBasico.addButton(botonEliminar);
		menuBarUsuarioBasico.addButton(botonSeleccionarTodo);
		menuBarUsuarioBasico.addButton(botonEnviarParticipaciones);
		menuBarUsuarioBasico.addMember(fontForm);
		menuBarUsuarioBasico.addButton(botonGenerarReporte);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addSeparator();

		panelListados.addMember(topTabSet);
		panelListados.addMember(menuBarUsuarioBasico);
		addMember(panelInformacionProceso);
		addMember(panelListados);

	}

	private void mostrarDialogoAgregarParticipacion() {
		final Window winModal = new Window();

		final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Agregar Evaluados", "Agregue evaluados al proceso",
				"img/check.png");
		p.setSize("100%", "70px");

		winModal.setWidth(400);
		winModal.setHeight(300);
		winModal.setTitle("Agregar Evaluados");
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

		formNuevaParticipacion = new DynamicForm();
		formNuevaParticipacion.setHeight("40%");
		formNuevaParticipacion.setWidth100();
		formNuevaParticipacion.setPadding(5);
		formNuevaParticipacion.setLayoutAlign(VerticalAlignment.BOTTOM);

		textIdentificadorUsuarioBasico = new IntegerItem();
		textIdentificadorUsuarioBasico.setRequired(true);
		textIdentificadorUsuarioBasico.setTitle("C\u00E9dula");
		textIdentificadorUsuarioBasico.setAllowExpressions(false);

		textNombreUsuarioBasico = new TextItem();
		textNombreUsuarioBasico.setTitle("Nombre");
		textNombreUsuarioBasico.setRequired(true);

		textApellidosUsuarioBasico = new TextItem();
		textApellidosUsuarioBasico.setRequired(true);
		textApellidosUsuarioBasico.setTitle("Apellidos");

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator
				.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");

		textCorreoUsuarioBasico = new TextItem();
		textCorreoUsuarioBasico.setRequired(true);
		textCorreoUsuarioBasico.setTitle("Correo");
		textCorreoUsuarioBasico.setValidators(regExpValidator);

		final IButton boton = new IButton("Crear");
		boton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {

				if (nuevaParticipacion) {
					if (formNuevaParticipacion.validate()) {
						agregarNuevaParticipacionAProceso();
						winModal.destroy();
					}
				} else {
					Record[] records = listGridUsuariosBasicos
							.getSelectedRecords();
					if (records != null) {
						UsuarioBasicoListGridRecord[] pruebas = new UsuarioBasicoListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							pruebas[i] = (UsuarioBasicoListGridRecord) records[i];
						}
						List<EvaluadoBO> pruebasBO = UsuarioBasicoListGridRecord
								.getBO(pruebas);
						List<ParticipacionEnProcesoBO> participaciones = obtenerParticipacionesDeEvaluados(pruebasBO);
						Maestro.agregarParticipacionesAProceso(participaciones,
								procesoActual);
						winModal.destroy();
					} else {
						SC.warn("Debe seleccionar los evaluados que desea agregar al proceso.");
					}
				}
				winModal.destroy();

			}

		});

		formNuevaParticipacion.setFields(textIdentificadorUsuarioBasico,
				textNombreUsuarioBasico, textApellidosUsuarioBasico,
				textCorreoUsuarioBasico);

		winModal.addItem(p);
		winModal.addItem(formNuevaParticipacion);

		final Canvas canvasMenuOpciones = new Canvas();
		canvasMenuOpciones.setWidth100();
		canvasMenuOpciones.setHeight(230);

		Img imagenOpcionNuevaParticipacion = new Img("img/check.png");
		imagenOpcionNuevaParticipacion.setSize("105px", "105px");
		imagenOpcionNuevaParticipacion.setTop(25);
		imagenOpcionNuevaParticipacion.setLeft(63);
		imagenOpcionNuevaParticipacion.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				formNuevaParticipacion.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				nuevaParticipacion = true;
			}
		});

		Img imagenOpcionAgregarParticipaciones = new Img("img/check.png");
		imagenOpcionAgregarParticipaciones.setSize("105px", "105px");
		imagenOpcionAgregarParticipaciones.setTop(25);
		imagenOpcionAgregarParticipaciones.setLeft(231);
		imagenOpcionAgregarParticipaciones.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				listGridUsuariosBasicos.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				boton.setTitle("Agregar Evaluados");
				nuevaParticipacion = false;
			}
		});

		HTMLFlow tituloNuevaParticipacion = new HTMLFlow("<h2>Crear Nuevo</h2>");
		tituloNuevaParticipacion.setTop(140);
		tituloNuevaParticipacion.setLeft(63);
		tituloNuevaParticipacion.setWidth(105);

		HTMLFlow tituloAgregarParticipaciones = new HTMLFlow("<h2>Agregar</h2>");
		tituloAgregarParticipaciones.setTop(140);
		tituloAgregarParticipaciones.setLeft(231);
		tituloAgregarParticipaciones.setWidth(105);

		canvasMenuOpciones.addChild(imagenOpcionNuevaParticipacion);
		canvasMenuOpciones.addChild(imagenOpcionAgregarParticipaciones);
		canvasMenuOpciones.addChild(tituloNuevaParticipacion);
		canvasMenuOpciones.addChild(tituloAgregarParticipaciones);

		winModal.addItem(canvasMenuOpciones);

		listGridUsuariosBasicos = new ListGrid();
		listGridUsuariosBasicos.setWidth100();
		listGridUsuariosBasicos.setHeight100();
		listGridUsuariosBasicos.setShowAllRecords(true);
		listGridUsuariosBasicos.setSelectionType(SelectionStyle.SIMPLE);
		listGridUsuariosBasicos
				.setEmptyMessage("No hay evaluados en su cuenta.");

		ListGridField idField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoField = new ListGridField("correo", "Correo");
		listGridUsuariosBasicos.setFields(idField, nombreField, apellidosField,
				correoField);
		listGridUsuariosBasicos.setData(UsuarioBasicoListGridRecord
				.getRecords(listaEvaluadosRestantes));
		listGridUsuariosBasicos.setAutoFetchData(true);
		listGridUsuariosBasicos.setShowFilterEditor(true);
		listGridUsuariosBasicos
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		winModal.addItem(listGridUsuariosBasicos);
		winModal.addItem(boton);

		listGridUsuariosBasicos.setVisible(false);
		formNuevaParticipacion.setVisible(false);
		boton.setVisible(false);
		p.setVisible(false);

		winModal.show();
	}

	private void mostrarDialogoAgregarTema() {
		final Window winModal = new Window();

		final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Agregar Temas", "Agregue temas al proceso", "img/check.png");
		p.setSize("100%", "70px");

		winModal.setWidth(400);
		winModal.setHeight(300);
		winModal.setTitle("Agregar Temas");
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

		formTemas = new DynamicForm();
		formTemas.setHeight("40%");
		formTemas.setWidth100();
		formTemas.setPadding(5);
		formTemas.setLayoutAlign(VerticalAlignment.BOTTOM);

		textNombreCategoriaNueva = new TextItem();
		textNombreCategoriaNueva.setTitle("Nombre");
		textNombreCategoriaNueva.setRequired(true);
		textNombreCategoriaNueva.setWidth("100%");

		textAreaDescripcionCategoriaNueva = new TextAreaItem();
		textAreaDescripcionCategoriaNueva.setTitle("Descripci\u00F3n");
		textAreaDescripcionCategoriaNueva.setRequired(true);
		textAreaDescripcionCategoriaNueva.setWidth("100%");

		formTemas.setFields(textNombreCategoriaNueva,
				textAreaDescripcionCategoriaNueva);

		final IButton boton = new IButton("Crear");
		boton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {

				if (nuevoTema) {
					if (formTemas.validate()) {
						agregarNuevaPruebaAProceso();
						winModal.destroy();
					}
				} else {
					Record[] records = listGridPruebas.getSelectedRecords();
					if (records != null) {
						PruebaListGridRecord[] pruebas = new PruebaListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							pruebas[i] = (PruebaListGridRecord) records[i];
						}
						List<PruebaUsuarioBO> pruebasBO = PruebaListGridRecord
								.getBO(pruebas);
						Maestro.agregarTemasAProceso(pruebasBO, procesoActual);
						winModal.destroy();
					} else {
						SC.warn("Debe seleccionar los temas que desea agregar al proceso.");
					}
				}
			}
		});

		winModal.addItem(p);
		winModal.addItem(formTemas);

		final Canvas canvasMenuOpciones = new Canvas();
		canvasMenuOpciones.setWidth100();
		canvasMenuOpciones.setHeight(230);

		Img imagenOpcionNuevaParticipacion = new Img("img/check.png");
		imagenOpcionNuevaParticipacion.setSize("105px", "105px");
		imagenOpcionNuevaParticipacion.setTop(25);
		imagenOpcionNuevaParticipacion.setLeft(63);
		imagenOpcionNuevaParticipacion.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				formTemas.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				nuevoTema = true;
			}
		});

		Img imagenOpcionAgregarParticipaciones = new Img("img/check.png");
		imagenOpcionAgregarParticipaciones.setSize("105px", "105px");
		imagenOpcionAgregarParticipaciones.setTop(25);
		imagenOpcionAgregarParticipaciones.setLeft(231);
		imagenOpcionAgregarParticipaciones.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				listGridPruebas.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				boton.setTitle("Agregar Temas");
				nuevoTema = false;
			}
		});

		HTMLFlow tituloNuevaParticipacion = new HTMLFlow("<h2>Crear Nuevo</h2>");
		tituloNuevaParticipacion.setTop(140);
		tituloNuevaParticipacion.setLeft(63);
		tituloNuevaParticipacion.setWidth(105);

		HTMLFlow tituloAgregarParticipaciones = new HTMLFlow("<h2>Agregar</h2>");
		tituloAgregarParticipaciones.setTop(140);
		tituloAgregarParticipaciones.setLeft(231);
		tituloAgregarParticipaciones.setWidth(105);

		canvasMenuOpciones.addChild(imagenOpcionNuevaParticipacion);
		canvasMenuOpciones.addChild(imagenOpcionAgregarParticipaciones);
		canvasMenuOpciones.addChild(tituloNuevaParticipacion);
		canvasMenuOpciones.addChild(tituloAgregarParticipaciones);

		winModal.addItem(canvasMenuOpciones);

		listGridPruebas = new ListGrid();
		listGridPruebas.setWidth100();
		listGridPruebas.setHeight100();
		listGridPruebas.setShowAllRecords(true);
		listGridPruebas.setSelectionType(SelectionStyle.SINGLE);
		listGridPruebas.setEmptyMessage("No hay temas en su cuenta.");

		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");

		listGridPruebas.setFields(nombreField, apellidosField);
		listGridPruebas.setCanResizeFields(false);
		listGridPruebas.setAutoFetchData(true);
		listGridPruebas.setShowFilterEditor(true);
		listGridPruebas.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		listGridPruebas.setData(PruebaListGridRecord
				.getRecords(listaPruebasRestantes));

		winModal.addItem(listGridPruebas);
		winModal.addItem(boton);

		listGridPruebas.setVisible(false);
		formTemas.setVisible(false);
		boton.setVisible(false);
		p.setVisible(false);

		winModal.show();
	}

	public void actualizarDatosProceso(ProcesoUsuarioBO proceso) {
		this.procesoActual = proceso;
		textAreaDescripcionProceso.setValue(proceso.getDescripcion());
		dateTimeItemFechaInicio.setValue(proceso.getFechaInicio());
		dateTimeItemFechaFinalizacion.setValue(proceso.getFechaFinalizacion());
		dateTimeItemFechaCreacion.setValue(proceso.getFechaCreacion());
		panelEncabezadoProceso.actualizarEncabezado(proceso.getNombre(),
				"Informaci\u00F3n general del proceso", "img/check.png");
	}

	public void actualizarListaPruebas(List<PruebaUsuarioBO> pruebas) {
		listaPruebasRestantes = pruebas;
	}

	public void actualizarEvaluados(List<EvaluadoBO> usuarios) {
		listaEvaluadosRestantes = usuarios;
	}

	public void actualizarParticipaciones(List<ParticipacionEnProcesoBO> result) {
		listGridParticipaciones.setData(ParticipacionEnProcesoListGridRecord
				.getRecords(result));
	}

	public void actualizarResultados(List<ParticipacionEnProcesoBO> result) {
		listGridResultados.setData(ParticipacionEnProcesoListGridRecord
				.getRecords(result));
	}

	public void actualizarTemasProceso(List<PruebaUsuarioBO> result) {
		listGridTemasDeProceso.setData(PruebaListGridRecord.getRecords(result));
	}

	private void agregarNuevaParticipacionAProceso() {
		String nombreUsuario = textNombreUsuarioBasico.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();
		EvaluadoBO eval = new EvaluadoBO();
		eval.setApellidos(apellidosUsuario);
		eval.setCorreoElectronico(correoUsuario);
		eval.setIdentificador(idUsuario);
		eval.setNombres(nombreUsuario);
		List<EvaluadoBO> evaluados = new ArrayList<EvaluadoBO>();
		evaluados.add(eval);
		List<ParticipacionEnProcesoBO> participaciones = obtenerParticipacionesDeEvaluados(evaluados);
		Maestro.agregarParticipacionesAProceso(participaciones, procesoActual);
	}

	private void agregarNuevaPruebaAProceso() {
		PruebaUsuarioBO prueba = new PruebaUsuarioBO();
		prueba.setNombre(textNombreCategoriaNueva.getValueAsString());
		prueba.setDescripcion(textAreaDescripcionCategoriaNueva
				.getValueAsString());
		List<PruebaUsuarioBO> pruebas = new ArrayList<PruebaUsuarioBO>();
		pruebas.add(prueba);
		Maestro.agregarTemasAProceso(pruebas, procesoActual);
	}

	private List<ParticipacionEnProcesoBO> obtenerParticipacionesDeEvaluados(
			List<EvaluadoBO> pruebasBO) {
		List<ParticipacionEnProcesoBO> participaciones = new ArrayList<ParticipacionEnProcesoBO>();
		for (EvaluadoBO evaluadoBO : pruebasBO) {
			ParticipacionEnProcesoBO p = new ParticipacionEnProcesoBO();
			p.setUsuarioBasico(evaluadoBO);
			p.setFechaFinalizacion(procesoActual.getFechaFinalizacion());
			p.setFechaInicio(procesoActual.getFechaInicio());
			p.setProcesoID(procesoActual.getIdentificador());
			participaciones.add(p);
		}
		return participaciones;
	}

	public void deseleccionarTodo() {
		listGridParticipaciones.deselectAllRecords();
		listGridTemasDeProceso.deselectAllRecords();
		listGridResultados.deselectAllRecords();

	}
}
