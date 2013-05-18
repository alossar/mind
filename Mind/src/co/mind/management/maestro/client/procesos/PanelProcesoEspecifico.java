package co.mind.management.maestro.client.procesos;

import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import co.mind.management.maestro.client.DialogoProcesamiento;
import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.TabBarControls;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
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
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
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
	private ListGrid listGridPruebasDeProceso;
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
	private ButtonItem botonEditarProceso;
	private SelectItem selectTipoReporte;
	private PanelEncabezadoDialogo panelEncabezadoProceso;
	private DynamicForm formNuevaParticipacion;
	private IntegerItem textIdentificadorUsuarioBasico;
	private TextItem textNombreUsuarioBasico;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private ProcesoUsuarioBO procesoActual;
	private DynamicForm formPruebas;
	private ListGrid listGridPruebas;
	private List<PruebaUsuarioBO> listaPruebasRestantes;
	private List<EvaluadoBO> listaEvaluadosRestantes;
	private ListGrid listGridUsuariosBasicos;
	private TextItem textNombreCategoriaNueva;
	private TextAreaItem textAreaDescripcionCategoriaNueva;
	private boolean nuevaParticipacion;
	private boolean nuevoPrueba;
	private ButtonItem botonCancelar;
	private boolean enEdicion = false;
	private ButtonItem botonSolicitarRevision;
	private Window dlgNotificaciones;
	private CheckboxItem checkBoxHabilitarFechaFinalizacion;

	public PanelProcesoEspecifico(UsuarioBO usuario) {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		panelInformacionProceso = new VLayout();
		panelInformacionProceso.setHeight100();
		panelInformacionProceso.setWidth("250px");

		DynamicForm formProceso = new DynamicForm();
		formProceso.setWidth100();
		formProceso.setHeight100();
		formProceso.setPadding(5);

		textAreaDescripcionProceso = new TextAreaItem();
		textAreaDescripcionProceso.setTitle("Descripci\u00F3n");
		textAreaDescripcionProceso.setCanEdit(false);
		textAreaDescripcionProceso
				.setLength(Convencion.MAXIMA_LONGITUD_DESCRIPCION_PROCESO);

		dateTimeItemFechaInicio = new DateTimeItem();
		dateTimeItemFechaInicio.setTitle("Fecha Inicio");
		dateTimeItemFechaInicio.setCanEdit(false);

		checkBoxHabilitarFechaFinalizacion = new CheckboxItem();
		checkBoxHabilitarFechaFinalizacion.setName("onOrder");
		checkBoxHabilitarFechaFinalizacion.setTitle("Cerrar Proceso");
		checkBoxHabilitarFechaFinalizacion.setRedrawOnChange(true);
		checkBoxHabilitarFechaFinalizacion.setValue(false);
		checkBoxHabilitarFechaFinalizacion.setCanEdit(false);
		checkBoxHabilitarFechaFinalizacion
				.addChangedHandler(new ChangedHandler() {
					@Override
					public void onChanged(ChangedEvent event) {
						if (true) {
							Boolean valor = (Boolean) event.getValue();
							if (valor) {
								checkBoxHabilitarFechaFinalizacion
										.setTitle("Proceso Cerrado");
							} else {
								checkBoxHabilitarFechaFinalizacion
										.setTitle("Cerrar Proceso");
							}
						}

					}
				});

		dateTimeItemFechaFinalizacion = new DateTimeItem();
		dateTimeItemFechaFinalizacion.setTitle("Fecha Finalizaci\u00F3n");
		dateTimeItemFechaFinalizacion.setCanEdit(false);
		dateTimeItemFechaFinalizacion
				.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value,
							DynamicForm form) {
						return (Boolean) form.getValue("onOrder");
					}
				});

		dateTimeItemFechaCreacion = new DateTimeItem();
		dateTimeItemFechaCreacion.setTitle("Fecha Creaci\u00F3n");
		dateTimeItemFechaCreacion.setCanEdit(false);

		botonEditarProceso = new ButtonItem();
		botonEditarProceso.setTitle("Editar Proceso");
		botonEditarProceso.setAutoFit(true);
		botonEditarProceso.setStartRow(false);
		botonEditarProceso.setEndRow(false);
		botonEditarProceso
				.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						if (enEdicion == false) {
							botonEditarProceso.setTitle("Guardar Cambios");
							botonCancelar.setVisible(true);
							textAreaDescripcionProceso.setCanEdit(true);
							dateTimeItemFechaInicio.setCanEdit(true);
							dateTimeItemFechaFinalizacion.setCanEdit(true);
							checkBoxHabilitarFechaFinalizacion.setCanEdit(true);
							enEdicion = true;
						} else {
							ProcesoUsuarioBO proceso = new ProcesoUsuarioBO();
							Date fechaFinalizacion = dateTimeItemFechaFinalizacion
									.getValueAsDate();
							Date fechaInicio = dateTimeItemFechaInicio
									.getValueAsDate();
							String desc = textAreaDescripcionProceso
									.getValueAsString();
							if (checkBoxHabilitarFechaFinalizacion
									.getValueAsBoolean()) {
								if (fechaFinalizacion != null) {
									if (fechaInicio != null) {
										if (fechaFinalizacion
												.before(fechaInicio)) {
											SC.warn("La fecha de finalización no puede suceder antes de la fecha de inicio.");
										} else {
											proceso.setFechaFinalizacion(fechaFinalizacion);
											proceso.setFechaInicio(fechaInicio);
										}
									} else {
										proceso.setFechaInicio(procesoActual
												.getFechaInicio());
									}
								} else {
									proceso.setFechaFinalizacion(procesoActual
											.getFechaFinalizacion());
									if (fechaInicio != null) {
										proceso.setFechaInicio(fechaInicio);
									} else {

										proceso.setFechaInicio(procesoActual
												.getFechaInicio());
									}
								}
							} else {
								proceso.setFechaFinalizacion(null);
								if (fechaInicio != null) {
									proceso.setFechaInicio(fechaInicio);
								} else {

									proceso.setFechaInicio(procesoActual
											.getFechaInicio());
								}
							}
							if (desc != null) {
								proceso.setDescripcion(desc);
							} else {
								proceso.setDescripcion(procesoActual
										.getDescripcion());
							}
							proceso.setIdentificador(procesoActual
									.getIdentificador());
							proceso.setEstadoValoracion(procesoActual
									.getEstadoValoracion());
							proceso.setFechaCreacion(procesoActual
									.getFechaCreacion());
							proceso.setNombre(procesoActual.getNombre());
							proceso.setNotificacionEnviada(procesoActual
									.getNotificacionEnviada());

							botonEditarProceso.setTitle("Editar Proceso");
							textAreaDescripcionProceso.setCanEdit(false);
							dateTimeItemFechaInicio.setCanEdit(false);
							dateTimeItemFechaFinalizacion.setCanEdit(false);
							checkBoxHabilitarFechaFinalizacion
									.setCanEdit(false);
							botonCancelar.setVisible(false);
							enEdicion = false;
							Maestro.editarProceso(proceso);
							actualizarDatosProceso(proceso);
						}
					}
				});

		botonCancelar = new ButtonItem();
		botonCancelar.setTitle("Cancelar");
		botonCancelar.setVisible(false);
		botonCancelar.setAutoFit(true);
		botonCancelar.setStartRow(false);
		botonCancelar.setEndRow(false);
		botonCancelar
				.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						botonEditarProceso.setTitle("Editar Proceso");
						textAreaDescripcionProceso.setCanEdit(false);
						dateTimeItemFechaInicio.setCanEdit(false);
						dateTimeItemFechaFinalizacion.setCanEdit(false);
						botonCancelar.setVisible(false);
						checkBoxHabilitarFechaFinalizacion.setCanEdit(false);
						enEdicion = false;
					}
				});

		formProceso.setFields(textAreaDescripcionProceso,
				dateTimeItemFechaInicio, checkBoxHabilitarFechaFinalizacion,
				dateTimeItemFechaFinalizacion, dateTimeItemFechaCreacion,
				botonEditarProceso, botonCancelar);

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("80%");
		v2.setBackgroundColor("white");
		v2.addChild(formProceso);

		panelEncabezadoProceso = new PanelEncabezadoDialogo("Proceso",
				"Informaci\u00F3n del proceso.", "img/admin/bot1.png");
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
					botonAgregar.setTitle("Agregar Evaluado");
					botonAgregar.setVisible(true);
					botonEliminar.setTitle("Descartar Evaluado");
					botonEliminar.setVisible(true);
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
					botonAgregar.setTitle("Agregar Prueba");
					botonAgregar.setVisible(true);
					botonEliminar.setTitle("Eliminar Prueba");
					botonEliminar.setVisible(true);
					botonEnviarParticipaciones.setVisible(false);
				}
			}
		});

		Tab tabParticipaciones = new Tab("Participaciones");
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
		ListGridField notificacionField = new ListGridField("estaNotificado",
				"Notificación de Correo");
		listGridParticipaciones.setFields(idField, nombreField, apellidoField,
				correoField, codigoField, estadoField, notificacionField);
		listGridParticipaciones.setCanResizeFields(true);
		listGridParticipaciones
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		listGridParticipaciones.setSelectionType(SelectionStyle.SIMPLE);

		tabParticipaciones.setPane(listGridParticipaciones);

		Tab tabResultados = new Tab("Resultados");
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

		listGridResultados.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		listGridResultados.setSelectionType(SelectionStyle.SIMPLE);

		tabResultados.setPane(listGridResultados);

		Tab tabPruebas = new Tab("Pruebas");
		tabPruebas.setID("temas");

		listGridPruebasDeProceso = new ListGrid();
		listGridPruebasDeProceso.setWidth100();
		listGridPruebasDeProceso.setHeight100();
		listGridPruebasDeProceso.setWrapCells(true);
		listGridPruebasDeProceso.setFixedRecordHeights(false);
		listGridPruebasDeProceso.setShowAllRecords(true);
		listGridPruebasDeProceso.setSelectionType(SelectionStyle.SINGLE);
		listGridPruebasDeProceso
				.setEmptyMessage("No hay pruebas en este proceso.");

		ListGridField nombrePruebaField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		ListGridField tiempoField = new ListGridField("tiempoPrueba",
				"Tiempo (Minutos)");
		listGridPruebasDeProceso.setFields(nombrePruebaField, apellidosField,
				preguntasField, tiempoField);
		listGridPruebasDeProceso.setCanResizeFields(true);
		listGridPruebasDeProceso
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		listGridPruebasDeProceso
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {

						PruebaListGridRecord record = (PruebaListGridRecord) event
								.getRecord();
						if (record != null) {
							PruebaUsuarioBO prueba = PruebaListGridRecord
									.getBO(record);
							Maestro.irAPruebaDesdeProceso();
							Maestro.obtenerPreguntasPorPrueba(prueba
									.getIdentificador());
							Maestro.irAPruebaSeleccionada(prueba);
						}
					}
				});
		listGridPruebasDeProceso.setGenerateDoubleClickOnEnter(true);

		tabPruebas.setPane(listGridPruebasDeProceso);

		topTabSet.addTab(tabPruebas);
		topTabSet.addTab(tabParticipaciones);
		topTabSet.addTab(tabResultados);

		botonSeleccionarTodo = new ToolStripButton("Seleccionar Todos");

		botonSeleccionarTodo
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						Tab t = topTabSet.getSelectedTab();
						if (t.getID().equalsIgnoreCase("temas")) {
							listGridPruebasDeProceso.selectAllRecords();
						} else if (t.getID()
								.equalsIgnoreCase("participaciones")) {
							listGridParticipaciones.selectAllRecords();
						} else if (t.getID().equalsIgnoreCase("resultados")) {
							listGridResultados.selectAllRecords();
						}
					}
				});

		botonAgregar = new ToolStripButton("Agregar Prueba");

		botonAgregar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Tab t = topTabSet.getSelectedTab();
				if (t.getID().equalsIgnoreCase("temas")) {
					mostrarDialogoAgregarPrueba();
				} else if (t.getID().equalsIgnoreCase("participaciones")) {
					if (procesoActual.getFechaFinalizacion() == null) {
						if (listGridParticipaciones.getRecords().length <= Convencion.MAXIMO_EVALUADOS_PROCESO) {
							mostrarDialogoAgregarParticipacion();
						} else {
							SC.say("La cantidad máxima de evaluados en el proceso es de 100.");
						}
					} else {
						if (procesoActual.getFechaFinalizacion().before(
								new Date())) {
							SC.say("El proceso se encuentra finalizado. No se pueden agregar evaluados al proceso.");
						} else {
							if (listGridParticipaciones.getRecords().length <= Convencion.MAXIMO_EVALUADOS_PROCESO) {
								mostrarDialogoAgregarParticipacion();
							} else {
								SC.say("La cantidad máxima de evaluados en el proceso es de 100.");
							}
						}
					}
				}
			}
		});

		botonEliminar = new ToolStripButton("Eliminar Prueba");

		botonEliminar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Tab t = topTabSet.getSelectedTab();
				if (t.getID().equalsIgnoreCase("temas")) {
					Record[] records = listGridPruebasDeProceso
							.getSelectedRecords();
					if (records == null) {
						SC.warn("Debe seleccionar las pruebas que desea eliminar.");
					} else {
						final PruebaListGridRecord[] participaciones = new PruebaListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							participaciones[i] = (PruebaListGridRecord) records[i];
						}

						SC.ask("¿Desea eliminar las pruebas seleccionadas?",
								new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											Maestro.eliminarPruebasDeProceso(
													procesoActual,
													PruebaListGridRecord
															.getBO(participaciones));
										}
									}
								});

					}
				} else if (t.getID().equalsIgnoreCase("participaciones")) {
					Record[] records = listGridParticipaciones
							.getSelectedRecords();
					if (records == null) {
						SC.warn("Debe seleccionar los evaluados que desea descartar.");
					} else {
						final ParticipacionEnProcesoListGridRecord[] participaciones = new ParticipacionEnProcesoListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							participaciones[i] = (ParticipacionEnProcesoListGridRecord) records[i];
						}

						SC.ask("¿Desea descartar los evaluados seleccionados?",
								new BooleanCallback() {

									@Override
									public void execute(Boolean value) {
										if (value) {
											Maestro.eliminarParticipacionesDeProceso(
													procesoActual,
													ParticipacionEnProcesoListGridRecord
															.getBO(participaciones));
										}
									}
								});
					}
				}
			}
		});

		botonEnviarParticipaciones = new ToolStripButton("Enviar Notificación");

		botonEnviarParticipaciones.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if (procesoActual.getFechaFinalizacion() == null) {
					enviarCorreos();
				} else {
					Date hoy = new java.util.Date();
					if (hoy.before(procesoActual.getFechaFinalizacion())) {
						enviarCorreos();
					} else {
						SC.warn("El proceso se encuentra cerrado. Actualice la fecha de finalización si desea reabrir el proceso.");
					}
				}
			}

			private void enviarCorreos() {
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

					dlgNotificaciones = new DialogoProcesamiento(
							"Enviando Notificaciones...");
					dlgNotificaciones.show();

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
		valueMap.put("excel", "Excel");
		valueMap.put("pdf", "PDF");
		selectTipoReporte.setValueMap(valueMap);
		fontForm.setItems(selectTipoReporte);
		selectTipoReporte.setDefaultValue("pdf");

		botonGenerarReporte = new ToolStripButton("Generar Reporte");

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
		menuBarUsuarioBasico.addButton(botonEnviarParticipaciones);
		menuBarUsuarioBasico.addMember(fontForm);
		menuBarUsuarioBasico.addButton(botonGenerarReporte);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addButton(botonEliminar);
		menuBarUsuarioBasico.addButton(botonSeleccionarTodo);
		menuBarUsuarioBasico.addSeparator();
		if (!(usuario.getTipo()
				.equalsIgnoreCase(Convencion.TIPO_USUARIO_MAESTRO))) {
			botonSolicitarRevision = new ButtonItem();
			botonSolicitarRevision.setTitle("Solicitar Evaluación de Experto");

			botonSolicitarRevision
					.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

						@Override
						public void onClick(
								com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
							procesoActual
									.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_PENDIENTE);
							Maestro.editarProceso(procesoActual);
							botonSolicitarRevision.setDisabled(true);
							botonSolicitarRevision.setVisible(true);
							botonSolicitarRevision
									.setTitle("Proceso pendiente de revisión");
						}
					});
			DynamicForm form = new DynamicForm();
			// form.setHeight(1);
			form.setPadding(0);
			form.setMargin(0);
			form.setCellPadding(1);
			form.setNumCols(1);
			form.setFields(botonSolicitarRevision);

			topTabSet.setTabBarControls(TabBarControls.TAB_SCROLLER,
					TabBarControls.TAB_PICKER, form);

		}

		panelListados.addMember(topTabSet);
		panelListados.addMember(menuBarUsuarioBasico);
		addMember(panelInformacionProceso);
		addMember(panelListados);

	}

	private void mostrarDialogoAgregarParticipacion() {
		final Window winModal = new Window();

		final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Agregar Evaluados", "Agregue evaluados al proceso",
				"insumos/evaluados/logoEvaluados.png");
		p.setSize("100%", "70px");

		winModal.setWidth(350);
		winModal.setHeight(225);
		winModal.setTitle("Agregar Evaluados");
		winModal.setShowMinimizeButton(false);
		winModal.setIsModal(true);
		winModal.setShowModalMask(true);
		winModal.centerInPage();
		// winModal.setBackgroundColor("#CECECE");
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
		formNuevaParticipacion.setBackgroundColor("#CECECE");
		formNuevaParticipacion.setLayoutAlign(VerticalAlignment.BOTTOM);

		textIdentificadorUsuarioBasico = new IntegerItem();
		textIdentificadorUsuarioBasico.setRequired(true);
		textIdentificadorUsuarioBasico.setTitle("C\u00E9dula");
		textIdentificadorUsuarioBasico.setAllowExpressions(false);
		textIdentificadorUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_CEDULA);

		textNombreUsuarioBasico = new TextItem();
		textNombreUsuarioBasico.setTitle("Nombre");
		textNombreUsuarioBasico.setRequired(true);
		textNombreUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

		textApellidosUsuarioBasico = new TextItem();
		textApellidosUsuarioBasico.setRequired(true);
		textApellidosUsuarioBasico.setTitle("Apellidos");
		textApellidosUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

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
			public void onClick(ClickEvent event) {
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
		canvasMenuOpciones.setHeight(150);

		Img imagenOpcionNuevaParticipacion = new Img(
				"insumos/procesos/botCrear.png");
		imagenOpcionNuevaParticipacion.setSize("105px", "105px");
		imagenOpcionNuevaParticipacion.setTop(25);
		imagenOpcionNuevaParticipacion.setLeft(46);
		imagenOpcionNuevaParticipacion.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				formNuevaParticipacion.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				nuevaParticipacion = true;
				winModal.setSize("350", "300");
				winModal.centerInPage();
			}
		});

		Img imagenOpcionAgregarParticipaciones = new Img(
				"insumos/procesos/botAgregar.png");
		imagenOpcionAgregarParticipaciones.setSize("105px", "105px");
		imagenOpcionAgregarParticipaciones.setTop(25);
		imagenOpcionAgregarParticipaciones.setLeft(197);
		imagenOpcionAgregarParticipaciones.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				listGridUsuariosBasicos.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				boton.setTitle("Agregar Evaluados");
				nuevaParticipacion = false;
				winModal.setSize("350", "300");
				winModal.centerInPage();
			}
		});

		HTMLFlow tituloNuevaParticipacion = new HTMLFlow("<h2>Crear Nuevo</h2>");
		tituloNuevaParticipacion.setTop(140);
		tituloNuevaParticipacion.setLeft(46);
		tituloNuevaParticipacion.setWidth(105);

		HTMLFlow tituloAgregarParticipaciones = new HTMLFlow("<h2>Agregar</h2>");
		tituloAgregarParticipaciones.setTop(140);
		tituloAgregarParticipaciones.setLeft(200);
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
		listGridUsuariosBasicos.setEmptyMessage("No se encuentran evaluados.");

		ListGridField idField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoField = new ListGridField("correo", "Correo");
		listGridUsuariosBasicos.setFields(idField, nombreField, apellidosField,
				correoField);
		listGridUsuariosBasicos.setData(UsuarioBasicoListGridRecord
				.getRecords(listaEvaluadosRestantes));
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

	private void mostrarDialogoAgregarPrueba() {
		final Window winModal = new Window();

		final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Agregar Pruebas", "Agregue pruebas al proceso",
				"img/admin/bot1.png");
		p.setSize("100%", "70px");

		winModal.setWidth(350);
		winModal.setHeight(225);
		winModal.setTitle("Agregar Pruebas");
		winModal.setShowMinimizeButton(false);
		winModal.setIsModal(true);
		winModal.setShowModalMask(true);
		winModal.centerInPage();
		// winModal.setBackgroundColor("#CECECE");
		winModal.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				winModal.destroy();
			}
		});

		formPruebas = new DynamicForm();
		formPruebas.setHeight("40%");
		formPruebas.setWidth100();
		formPruebas.setPadding(5);
		formPruebas.setBackgroundColor("#CECECE");
		formPruebas.setLayoutAlign(VerticalAlignment.BOTTOM);

		textNombreCategoriaNueva = new TextItem();
		textNombreCategoriaNueva.setTitle("Nombre");
		textNombreCategoriaNueva.setRequired(true);
		textNombreCategoriaNueva.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE);

		textAreaDescripcionCategoriaNueva = new TextAreaItem();
		textAreaDescripcionCategoriaNueva.setTitle("Descripci\u00F3n");
		textAreaDescripcionCategoriaNueva.setRequired(true);
		textAreaDescripcionCategoriaNueva
				.setLength(Convencion.MAXIMA_LONGITUD_DESCRIPCION_PRUEBA);

		final IButton boton = new IButton("Crear");
		boton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				if (nuevoPrueba) {
					if (formPruebas.validate()) {
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
						Maestro.agregarPruebasAProceso(pruebasBO, procesoActual);
						winModal.destroy();
					} else {
						SC.warn("Debe seleccionar las pruebas que desea agregar al proceso.");
					}
				}
			}
		});

		formPruebas.setFields(textNombreCategoriaNueva,
				textAreaDescripcionCategoriaNueva);

		winModal.addItem(p);
		winModal.addItem(formPruebas);

		final Canvas canvasMenuOpciones = new Canvas();
		canvasMenuOpciones.setWidth100();
		canvasMenuOpciones.setHeight(150);

		Img imagenOpcionNuevaParticipacion = new Img(
				"insumos/procesos/botCrear.png");
		imagenOpcionNuevaParticipacion.setSize("105px", "105px");
		imagenOpcionNuevaParticipacion.setTop(25);
		imagenOpcionNuevaParticipacion.setLeft(46);
		imagenOpcionNuevaParticipacion.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				formPruebas.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				nuevoPrueba = true;
				winModal.setSize("350", "300");
				winModal.centerInPage();
			}
		});

		Img imagenOpcionAgregarParticipaciones = new Img(
				"insumos/procesos/botAgregar.png");
		imagenOpcionAgregarParticipaciones.setSize("105px", "105px");
		imagenOpcionAgregarParticipaciones.setTop(25);
		imagenOpcionAgregarParticipaciones.setLeft(197);
		imagenOpcionAgregarParticipaciones.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				p.setVisible(true);
				listGridPruebas.setVisible(true);
				canvasMenuOpciones.setVisible(false);
				boton.setVisible(true);
				boton.setTitle("Agregar Pruebas");
				nuevoPrueba = false;
				winModal.setSize("350", "300");
				winModal.centerInPage();
			}
		});

		HTMLFlow tituloNuevaParticipacion = new HTMLFlow("<h2>Crear Nuevo</h2>");
		tituloNuevaParticipacion.setTop(140);
		tituloNuevaParticipacion.setLeft(46);
		tituloNuevaParticipacion.setWidth(105);

		HTMLFlow tituloAgregarParticipaciones = new HTMLFlow("<h2>Agregar</h2>");
		tituloAgregarParticipaciones.setTop(140);
		tituloAgregarParticipaciones.setLeft(200);
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
		listGridPruebas.setWrapCells(true);
		listGridPruebas.setFixedRecordHeights(false);
		listGridPruebas.setSelectionType(SelectionStyle.SINGLE);
		listGridPruebas.setEmptyMessage("No se encuentran pruebas.");

		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");

		listGridPruebas.setFields(nombreField, apellidosField);
		listGridPruebas.setCanResizeFields(false);
		listGridPruebas.setSelectionType(SelectionStyle.SIMPLE);
		listGridPruebas.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		listGridPruebas.setData(PruebaListGridRecord
				.getRecords(listaPruebasRestantes));

		winModal.addItem(listGridPruebas);
		winModal.addItem(boton);

		listGridPruebas.setVisible(false);
		formPruebas.setVisible(false);
		boton.setVisible(false);
		p.setVisible(false);

		winModal.show();
	}

	public void actualizarDatosProceso(ProcesoUsuarioBO proceso) {

		this.procesoActual = proceso;

		textAreaDescripcionProceso.setValue(proceso.getDescripcion());
		dateTimeItemFechaInicio.setValue(proceso.getFechaInicio());
		if (proceso.getFechaFinalizacion() != null) {
			checkBoxHabilitarFechaFinalizacion.setValue(true);
			dateTimeItemFechaFinalizacion.setValue(proceso
					.getFechaFinalizacion());
		} else {
			checkBoxHabilitarFechaFinalizacion.setValue(false);
		}
		dateTimeItemFechaCreacion.setValue(proceso.getFechaCreacion());
		panelEncabezadoProceso.actualizarEncabezado(proceso.getNombre(),
				"Informaci\u00F3n General del Proceso", "img/admin/bot1.png");
		if (botonSolicitarRevision != null) {
			if (proceso.getEstadoValoracion().equalsIgnoreCase(
					Convencion.ESTADO_SOLICITUD_PENDIENTE)) {
				botonSolicitarRevision.setDisabled(true);
				botonSolicitarRevision.setVisible(true);
				botonSolicitarRevision
						.setTitle("Proceso pendiente de revisión");
			} else if (proceso.getEstadoValoracion().equalsIgnoreCase(
					Convencion.ESTADO_SOLICITUD_ACEPTADA)) {
				botonSolicitarRevision.setDisabled(true);
				botonSolicitarRevision.setVisible(true);
				botonSolicitarRevision
						.setTitle("Solicitud de Revisión Aceptada.");
			} else if (proceso.getEstadoValoracion().equalsIgnoreCase(
					Convencion.ESTADO_SOLICITUD_RECHAZADA)) {
				botonSolicitarRevision.setDisabled(true);
				botonSolicitarRevision.setVisible(true);
				botonSolicitarRevision
						.setTitle("Solicitud de Revisión Rechazada.");
			} else if (proceso.getEstadoValoracion().equalsIgnoreCase(
					Convencion.ESTADO_SOLICITUD_REALIZADA)) {
				botonSolicitarRevision.setVisible(false);
			} else {
				botonSolicitarRevision
						.setTitle("Solicitar Revisión de Experto");
				botonSolicitarRevision.setVisible(true);
				botonSolicitarRevision.setDisabled(false);
			}
		}
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

	public void actualizarPruebasProceso(List<PruebaUsuarioBO> result) {
		listGridPruebasDeProceso.setData(PruebaListGridRecord
				.getRecords(result));
	}

	private void agregarNuevaParticipacionAProceso() {
		String nombreUsuario = textNombreUsuarioBasico.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();
		EvaluadoBO eval = new EvaluadoBO();
		eval.setApellidos(apellidosUsuario);
		eval.setCorreoElectronico(correoUsuario);
		eval.setCedula(idUsuario);
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
		Maestro.agregarPruebasAProceso(pruebas, procesoActual);
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
			p.setEstaNotificado(Convencion.ESTADO_NOTIFICACION_NO_ENVIADA);
			participaciones.add(p);
		}
		return participaciones;
	}

	public void deseleccionarTodo() {
		listGridParticipaciones.deselectAllRecords();
		listGridPruebasDeProceso.deselectAllRecords();
		listGridResultados.deselectAllRecords();

	}

	public void desactivarDialogoNotificaciones() {
		dlgNotificaciones.destroy();
	}
}
