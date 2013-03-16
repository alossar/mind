package co.mind.management.maestro.client.procesos;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridFieldType;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class PanelProcesos extends HLayout {

	private VLayout panelInformacionProceso;
	private VLayout panelListados;
	private ListGrid listGridParticipaciones;
	private ListGrid listGridResultados;
	private TextItem textNombrePrueba;
	private TextAreaItem textAreaDescripcionProceso;
	private DateTimeItem dateTimeItemFechaInicio;
	private DateTimeItem dateTimeItemFechaFinalizacion;
	private DateTimeItem dateTimeItemFechaCreacion;
	private DateTimeItem dateTimeItemFechaInicioNuevo;
	private DateTimeItem dateTimeItemFechaFinalizacionNuevo;
	private ListGrid listGridProcesos;
	private ListGrid listGridPruebas;
	private List<EvaluadoBO> listaUsuariosBasicos;
	private List<PruebaUsuarioBO> listaPruebas;
	private ListGrid listGridUsuariosBasicos;
	private TextAreaItem textAreaDescripcionProcesoNuevo;
	private TextItem textNombreProcesoNuevo;
	private DynamicForm formNuevoProceso;
	private ToolStripButton botonGenerarReporte;
	private ToolStripButton botonSeleccionarTodo;
	private SelectItem selectTipoReporte;

	public PanelProcesos() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		panelInformacionProceso = new VLayout();
		panelInformacionProceso.setHeight100();
		panelInformacionProceso.setWidth("20%");

		listGridProcesos = new ListGrid();
		listGridProcesos.setWidth100();
		listGridProcesos.setHeight100();
		listGridProcesos.setShowAllRecords(true);
		listGridProcesos.setCanResizeFields(false);

		ListGridField nameField = new ListGridField("nombreProceso", "Proceso");
		listGridProcesos.setFields(nameField);
		listGridProcesos.setCanResizeFields(false);
		listGridProcesos.setSelectionType(SelectionStyle.SINGLE);
		listGridProcesos
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						ProcesoRecord record = (ProcesoRecord) listGridProcesos
								.getSelectedRecord();
						ProcesoUsuarioBO proceso = ProcesoRecord.getBO(record);
						actualizarDatosProceso(proceso);
						Maestro.obtenerParticipantesProceso(proceso);
					}
				});

		DynamicForm formProceso = new DynamicForm();
		formProceso.setWidth100();
		formProceso.setHeight100();
		formProceso.setGroupTitle("Informaci\u00F3n del Proceso");
		formProceso.setIsGroup(true);
		formProceso.setPadding(5);

		textAreaDescripcionProceso = new TextAreaItem();
		textAreaDescripcionProceso.setTitle("Descripci\u00F3n");
		textAreaDescripcionProceso.setCanEdit(false);

		textNombrePrueba = new TextItem();
		textNombrePrueba.setTitle("Prueba");
		textNombrePrueba.setCanEdit(false);

		dateTimeItemFechaInicio = new DateTimeItem();
		dateTimeItemFechaInicio.setTitle("Fecha Inicio");
		dateTimeItemFechaInicio.setCanEdit(false);

		// timeHoraInicio = new TimeItem("timeItem", "Hora Inicio");
		// timeHoraInicio.setUseMask(true);
		// timeHoraInicio.setCanEdit(false);

		dateTimeItemFechaFinalizacion = new DateTimeItem();
		dateTimeItemFechaFinalizacion.setTitle("Fecha Finalizaci\u00F3n");
		dateTimeItemFechaFinalizacion.setCanEdit(false);

		dateTimeItemFechaCreacion = new DateTimeItem();
		dateTimeItemFechaCreacion.setTitle("Fecha Creaci\u00F3n");
		dateTimeItemFechaCreacion.setCanEdit(false);

		// timeHoraFinalizacion = new TimeItem("timeItem",
		// "Hora Finalizaci\u00F3n");
		// timeHoraFinalizacion.setUseMask(true);
		// timeHoraFinalizacion.setCanEdit(false);

		formProceso.setFields(textAreaDescripcionProceso, textNombrePrueba,
				dateTimeItemFechaInicio, dateTimeItemFechaFinalizacion,
				dateTimeItemFechaCreacion);

		VLayout v1 = new VLayout();
		v1.setWidth100();
		v1.setHeight("50%");

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("50%");
		v2.setBackgroundColor("white");

		v1.addChild(listGridProcesos);
		v2.addChild(formProceso);
		panelInformacionProceso.addMember(v1);
		inicializarBarraMenu();
		panelInformacionProceso.addMember(v2);

		panelListados = new VLayout();
		panelListados.setHeight100();
		panelListados.setWidth("70%");

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setTitle("Repositorio");
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		Tab tTab1 = new Tab("Participaciones", "pieces/16/pawn_blue.png");

		listGridParticipaciones = new ListGrid();
		listGridParticipaciones.setWidth100();
		listGridParticipaciones.setHeight100();
		listGridParticipaciones.setShowAllRecords(true);

		ListGridField idField = new ListGridField("idEval", "Cédula");
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidoField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField codigoField = new ListGridField("codigo", "Código Acceso");
		ListGridField estadoField = new ListGridField("estado", "Estado");
		listGridParticipaciones.setFields(idField, nombreField, apellidoField,
				codigoField, estadoField);
		listGridParticipaciones.setCanResizeFields(true);
		tTab1.setPane(listGridParticipaciones);

		Tab tTab2 = new Tab("Resultados", "pieces/16/pawn_green.png");

		listGridResultados = new ListGrid();
		listGridResultados.setWidth100();
		listGridResultados.setHeight100();
		listGridResultados.setShowAllRecords(true);
		listGridResultados.setFields(idField, nombreField, apellidoField,
				codigoField, estadoField);
		listGridResultados.setCanResizeFields(true);
		tTab2.setPane(listGridResultados);

		topTabSet.addTab(tTab1);
		topTabSet.addTab(tTab2);

		botonSeleccionarTodo = new ToolStripButton("Seleccionar Todos",
				"icons/16/document_plain_new.png");

		botonSeleccionarTodo
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						listGridResultados.selectAllRecords();
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
		menuBarUsuarioBasico.addButton(botonSeleccionarTodo);
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

	private void inicializarBarraMenu() {

		ToolStripButton botonNuevaPrueba = new ToolStripButton("Nuevo Proceso",
				"icons/16/document_plain_new.png");

		botonNuevaPrueba
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearPoceso();
					}
				});

		ToolStrip menuBar = new ToolStrip();
		menuBar.setWidth100();
		menuBar.addButton(botonNuevaPrueba);
		menuBar.addFill();
		menuBar.addSeparator();
		menuBar.addSeparator();

		panelInformacionProceso.addMember(menuBar);
	}

	private void mostrarDialogoCrearPoceso() {
		final Window winModal = new Window();
		winModal.setWidth(400);
		winModal.setHeight(600);
		winModal.setTitle("Crear un Proceso");
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

		formNuevoProceso = new DynamicForm();
		formNuevoProceso.setHeight("40%");
		formNuevoProceso.setWidth100();
		formNuevoProceso.setPadding(5);
		formNuevoProceso.setLayoutAlign(VerticalAlignment.BOTTOM);

		textNombreProcesoNuevo = new TextItem();
		textNombreProcesoNuevo.setTitle("Nombre");
		textNombreProcesoNuevo.setRequired(true);

		textAreaDescripcionProcesoNuevo = new TextAreaItem();
		textAreaDescripcionProcesoNuevo.setTitle("Descripci\u00F3n");
		textAreaDescripcionProcesoNuevo.setRequired(true);

		dateTimeItemFechaInicioNuevo = new DateTimeItem();
		dateTimeItemFechaInicioNuevo.setUseTextField(false);
		dateTimeItemFechaInicioNuevo.setTitle("Fecha Inicio");
		dateTimeItemFechaInicioNuevo.setRequired(true);
		dateTimeItemFechaInicioNuevo.setStartDate(new Date());

		// timeHoraInicioNuevo = new TimeItem("timeItem", "Hora Inicio");
		// timeHoraInicioNuevo.setUseMask(true);
		// timeHoraInicioNuevo.setRequired(true);
		// timeHoraInicioNuevo
		// .setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);

		dateTimeItemFechaFinalizacionNuevo = new DateTimeItem();
		dateTimeItemFechaFinalizacionNuevo.setUseTextField(false);
		dateTimeItemFechaFinalizacionNuevo.setTitle("Fecha Finalizaci\u00F3n");

		// timeHoraFinalizacionNuevo = new TimeItem("timeItem",
		// "Hora Finalizaci\u00F3n");
		// timeHoraFinalizacionNuevo.setUseMask(true);
		// timeHoraFinalizacionNuevo.setRequired(false);

		ButtonItem boton = new ButtonItem("Crear");
		boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (formNuevoProceso.validate()) {
					ListGridRecord[] recordsPruebas = listGridPruebas
							.getSelectedRecords();
					if (recordsPruebas != null) {
						PruebaListGridRecord[] pruebasR = new PruebaListGridRecord[recordsPruebas.length];
						for (int i = 0; i < pruebasR.length; i++) {
							pruebasR[i] = (PruebaListGridRecord) recordsPruebas[i];
						}
						List<PruebaUsuarioBO> pruebas = PruebaListGridRecord
								.getBO(pruebasR);
						ListGridRecord[] recordsUsuarios = listGridUsuariosBasicos
								.getRecords();
						UsuarioBasicoListGridRecord[] basicos = new UsuarioBasicoListGridRecord[recordsUsuarios.length];
						for (int i = 0; i < recordsUsuarios.length; i++) {
							basicos[i] = (UsuarioBasicoListGridRecord) recordsUsuarios[i];
						}
						List<EvaluadoBO> listaUsuarios = UsuarioBasicoListGridRecord
								.getBO(basicos);

						ProcesoUsuarioBO proceso = new ProcesoUsuarioBO();
						proceso.setDescripcion(textAreaDescripcionProcesoNuevo
								.getValueAsString());
						proceso.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_NO_REALIZADA);
						proceso.setFechaCreacion(new Date());
						proceso.setFechaFinalizacion(dateTimeItemFechaFinalizacionNuevo
								.getValueAsDate());
						proceso.setFechaInicio(dateTimeItemFechaInicioNuevo
								.getValueAsDate());
						proceso.setPruebasUsuarioID(pruebas);
						proceso.setNombre(textNombreProcesoNuevo
								.getValueAsString());
						Maestro.agregarProceso(listaUsuarios, proceso);

						winModal.destroy();

					}
				}
			}
		});

		formNuevoProceso.setFields(textNombreProcesoNuevo,
				textAreaDescripcionProcesoNuevo, dateTimeItemFechaInicioNuevo,
				dateTimeItemFechaFinalizacionNuevo, boton);

		winModal.addItem(formNuevoProceso);

		listGridPruebas = new ListGrid();
		listGridPruebas.setWidth100();
		listGridPruebas.setHeight("30%");
		listGridPruebas.setShowAllRecords(true);

		ListGridField nameField = new ListGridField("nombre", "Prueba");
		listGridPruebas.setFields(nameField);
		listGridPruebas.setCanResizeFields(false);
		listGridPruebas.setData(PruebaListGridRecord.getRecords(listaPruebas));
		listGridPruebas.setSelectionType(SelectionStyle.SINGLE);

		listGridUsuariosBasicos = new ListGrid();
		listGridUsuariosBasicos.setWidth100();
		listGridUsuariosBasicos.setHeight("30%");
		listGridUsuariosBasicos.setShowAllRecords(true);

		ListGridField idFieldUs = new ListGridField("id", "Cédula");
		ListGridField nameFieldUs = new ListGridField("nombre", "Nombre");
		ListGridField lastnameFieldUs = new ListGridField("apellidos",
				"Apellidos");
		ListGridField emailFieldUs = new ListGridField("correo", "Correo");
		listGridUsuariosBasicos.setFields(idFieldUs, nameFieldUs,
				lastnameFieldUs, emailFieldUs);
		listGridUsuariosBasicos.setCanResizeFields(false);
		listGridUsuariosBasicos.setData(UsuarioBasicoListGridRecord
				.getRecords(listaUsuariosBasicos));
		listGridUsuariosBasicos.setSelectionType(SelectionStyle.SIMPLE);

		VLayout vl1 = new VLayout();
		vl1.setHeight100();
		vl1.setWidth100();

		VLayout vl2 = new VLayout();
		vl2.setHeight100();
		vl2.setWidth100();

		VLayout vl3 = new VLayout();
		vl3.setHeight100();
		vl3.setWidth100();

		VLayout vl4 = new VLayout();
		vl4.setHeight100();
		vl4.setWidth100();

		winModal.addItem(listGridPruebas);
		winModal.addItem(listGridUsuariosBasicos);

		winModal.show();
	}

	private void actualizarDatosProceso(ProcesoUsuarioBO proceso) {
		textAreaDescripcionProceso.setValue(proceso.getDescripcion());
		dateTimeItemFechaInicio.setValue(proceso.getFechaInicio());
		dateTimeItemFechaFinalizacion.setValue(proceso.getFechaFinalizacion());
		dateTimeItemFechaCreacion.setValue(proceso.getFechaCreacion());
	}

	public void actualizarProcesos(ProcesoRecord[] procesos) {
		listGridProcesos.setData(procesos);
	}

	public void actualizarListaPruebas(List<PruebaUsuarioBO> pruebas) {
		listaPruebas = pruebas;
	}

	public void actualizarListaUsuariosBasicos(List<EvaluadoBO> usuarios) {
		listaUsuariosBasicos = usuarios;
	}

	public void actualizarParticipaciones(
			ParticipacionEnProcesoListGridRecord[] participacionEnProcesoListGridRecords) {
		listGridParticipaciones.setData(participacionEnProcesoListGridRecords);
	}

	public void actualizarResultados(
			ParticipacionEnProcesoListGridRecord[] participacionEnProcesoListGridRecords) {
		listGridResultados.setData(participacionEnProcesoListGridRecords);
	}

}
