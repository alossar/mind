package co.mind.management.maestro.client.procesos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelContenidoProcesos extends HLayout {

	private ListGrid listGridProcesos;
	private ToolStripButton botonRegresar;
	private ToolStripButton botonNuevoBasico;
	private ToolStripButton botonEliminarBasico;
	private ToolStripButton botonEditarBasico;
	private ToolStripButton botonDuplicarBasico;
	private PanelProcesoEspecifico panelProcesoEspecifico;
	private ProcesoUsuarioBO procesoSeleccionado;
	private DynamicForm formNuevoProceso;
	private TextItem textNombreProcesoNuevo;
	private TextAreaItem textAreaDescripcionProcesoNuevo;
	private DateItem dateTimeItemFechaInicioNuevo;
	private DateItem dateTimeItemFechaFinalizacionNuevo;
	private CheckboxItem checkBoxHabilitarFechaFinalizacion;
	private List<PruebaUsuarioBO> listaPruebas;
	private List<EvaluadoBO> listaEvaluados;

	public PanelContenidoProcesos() {
		setWidth("100%");
		setHeight("80%%");
		setBackgroundColor("white");
		setPadding(15);

		listGridProcesos = new ListGrid();
		listGridProcesos.setWidth100();
		listGridProcesos.setHeight100();
		listGridProcesos.setShowAllRecords(true);
		listGridProcesos.setSelectionType(SelectionStyle.SINGLE);
		listGridProcesos
				.setEmptyMessage("No se encuentran procesos en su cuenta.");

		ListGridField nombreField = new ListGridField("nombreProceso", "Nombre");
		ListGridField descripcionField = new ListGridField(
				"descripcionProceso", "Descripci\u00F3n");
		ListGridField fechaField = new ListGridField("fechaCreacion",
				"Fecha de Creaci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		ListGridField tiempoField = new ListGridField("tiempoProceso",
				"Tiempo (Segundos)");
		listGridProcesos.setFields(nombreField, descripcionField, fechaField,
				preguntasField, tiempoField);
		listGridProcesos.setCanEdit(true);
		listGridProcesos.setCanResizeFields(true);
		listGridProcesos.setAutoFetchData(true);
		listGridProcesos.setShowFilterEditor(true);

		listGridProcesos.addDoubleClickHandler(new DoubleClickHandler() {

			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				botonRegresar.enable();
				ProcesoRecord p = (ProcesoRecord) listGridProcesos
						.getSelectedRecord();
				if (p != null) {
					procesoSeleccionado = ProcesoRecord.getBO(p);
					Maestro.obtenerParticipantesProceso(procesoSeleccionado);
					Maestro.obtenerTemasProceso(procesoSeleccionado);
					panelProcesoEspecifico.deseleccionarTodo();
					panelProcesoEspecifico
							.actualizarDatosProceso(procesoSeleccionado);
					listGridProcesos.setVisible(false);
					panelProcesoEspecifico.setVisible(true);
					botonNuevoBasico.disable();
					botonEliminarBasico.disable();
				}
			}
		});

		panelProcesoEspecifico = new PanelProcesoEspecifico();
		panelProcesoEspecifico.setHeight100();
		panelProcesoEspecifico.setWidth100();
		panelProcesoEspecifico.setVisible(false);

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

		botonNuevoBasico = new ToolStripButton("Nuevo Proceso",
				"icons/16/document_plain_new.png");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearProceso();
					}

				});

		botonEliminarBasico = new ToolStripButton("Eliminar Proceso",
				"icons/16/document_plain_new.png");

		botonEliminarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						ProcesoRecord record = (ProcesoRecord) listGridProcesos
								.getSelectedRecord();
						if (record != null) {
							ProcesoUsuarioBO categoria = ProcesoRecord
									.getBO(record);
							Maestro.eliminarProceso(categoria);
						} else {
							SC.warn("Debe seleccionar el proceso que desea eliminar.");
						}

					}
				});

		botonDuplicarBasico = new ToolStripButton("Duplicar Proceso",
				"icons/16/document_plain_new.png");

		botonDuplicarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

					}
				});

		botonEditarBasico = new ToolStripButton("Duplicar Proceso",
				"icons/16/document_plain_new.png");

		botonEditarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonRegresar);
		menuBarUsuarioBasico.addButton(botonNuevoBasico);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addButton(botonEditarBasico);
		menuBarUsuarioBasico.addButton(botonDuplicarBasico);
		menuBarUsuarioBasico.addButton(botonEliminarBasico);
		menuBarUsuarioBasico.addSeparator();
		botonRegresar.disable();

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(listGridProcesos);
		vl1.addMember(panelProcesoEspecifico);
		vl1.addMember(menuBarUsuarioBasico);

		addMember(vl1);

	}

	public void actualizarProcesos(ProcesoRecord[] records) {
		listGridProcesos.setData(records);
	}

	private void mostrarDialogoCrearProceso() {
		final Window winModal = new Window();

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo("Crear Proceso",
				"Cree un proceso y organizelo como desee", "img/check.png");
		p.setSize("100%", "70px");

		winModal.setWidth(400);
		winModal.setHeight(400);
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
		textNombreProcesoNuevo.setWidth("*");

		textAreaDescripcionProcesoNuevo = new TextAreaItem();
		textAreaDescripcionProcesoNuevo.setTitle("Descripci\u00F3n");
		textAreaDescripcionProcesoNuevo.setRequired(true);
		textAreaDescripcionProcesoNuevo.setWidth("*");

		dateTimeItemFechaInicioNuevo = new DateItem();
		dateTimeItemFechaInicioNuevo.setUseTextField(false);
		dateTimeItemFechaInicioNuevo.setTitle("Fecha Inicio");
		dateTimeItemFechaInicioNuevo.setRequired(true);
		dateTimeItemFechaInicioNuevo.setStartDate(new Date());
		dateTimeItemFechaInicioNuevo.setWidth("*");

		// timeHoraInicioNuevo = new TimeItem("timeItem", "Hora Inicio");
		// timeHoraInicioNuevo.setUseMask(true);
		// timeHoraInicioNuevo.setRequired(true);
		// timeHoraInicioNuevo
		// .setTimeFormatter(TimeDisplayFormat.TOSHORT24HOURTIME);

		dateTimeItemFechaFinalizacionNuevo = new DateItem();
		dateTimeItemFechaFinalizacionNuevo.setUseTextField(false);
		dateTimeItemFechaFinalizacionNuevo.setTitle("Fecha Finalizaci\u00F3n");
		dateTimeItemFechaFinalizacionNuevo.setWidth("*");

		checkBoxHabilitarFechaFinalizacion = new CheckboxItem();
		checkBoxHabilitarFechaFinalizacion.setName("onOrder");
		checkBoxHabilitarFechaFinalizacion
				.setTitle("Habilitar fecha de finalización");
		checkBoxHabilitarFechaFinalizacion.setRedrawOnChange(true);
		checkBoxHabilitarFechaFinalizacion.setWidth(50);
		checkBoxHabilitarFechaFinalizacion.setValue(false);

		dateTimeItemFechaFinalizacionNuevo
				.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value,
							DynamicForm form) {
						return (Boolean) form.getValue("onOrder");
					}
				});

		// timeHoraFinalizacionNuevo = new TimeItem("timeItem",
		// "Hora Finalizaci\u00F3n");
		// timeHoraFinalizacionNuevo.setUseMask(true);
		// timeHoraFinalizacionNuevo.setRequired(false);

		ButtonItem boton = new ButtonItem("Crear");
		boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (formNuevoProceso.validate()) {

					ProcesoUsuarioBO proceso = new ProcesoUsuarioBO();
					proceso.setDescripcion(textAreaDescripcionProcesoNuevo
							.getValueAsString());
					proceso.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_NO_REALIZADA);
					proceso.setFechaCreacion(new Date());
					if (checkBoxHabilitarFechaFinalizacion.getValueAsBoolean()) {
						proceso.setFechaFinalizacion(new Date(
								dateTimeItemFechaFinalizacionNuevo
										.getValueAsDate().getTime() + 3600 * 1000 * 12));
					}
					Date fechaInicio = new Date(dateTimeItemFechaInicioNuevo
							.getValueAsDate().getTime() - 3600 * 1000 * 12);
					proceso.setFechaInicio(fechaInicio);
					proceso.setNombre(textNombreProcesoNuevo.getValueAsString());
					Maestro.agregarProceso(proceso);

					winModal.destroy();

				}
			}
		});

		formNuevoProceso.setFields(textNombreProcesoNuevo,
				textAreaDescripcionProcesoNuevo, dateTimeItemFechaInicioNuevo,
				checkBoxHabilitarFechaFinalizacion,
				dateTimeItemFechaFinalizacionNuevo, boton);

		winModal.addItem(p);
		winModal.addItem(formNuevoProceso);
		winModal.show();
	}

	public void actualizarListaPruebas(List<PruebaUsuarioBO> pruebas) {
		listaPruebas = pruebas;
	}

	public void actualizarListaUsuariosBasicos(List<EvaluadoBO> usuarios) {
		listaEvaluados = usuarios;
	}

	public void actualizarParticipaciones(List<ParticipacionEnProcesoBO> result) {
		panelProcesoEspecifico.actualizarParticipaciones(result);
		panelProcesoEspecifico.actualizarEvaluados(obtenerEvaluadosNoEnProceso(
				listaEvaluados, obtenerEvaluadosDeParticipacion(result)));
	}

	public void actualizarResultados(List<ParticipacionEnProcesoBO> result) {
		panelProcesoEspecifico.actualizarResultados(result);
	}

	public void setEstadoInicial() {
		Maestro.setListaProcesos();
		botonRegresar.disable();
		listGridProcesos.setVisible(true);
		panelProcesoEspecifico.setVisible(false);
		botonNuevoBasico.enable();
		botonEliminarBasico.enable();
	}

	public void actualizarTemasProceso(List<PruebaUsuarioBO> result) {
		panelProcesoEspecifico.actualizarTemasProceso(result);
		panelProcesoEspecifico
				.actualizarListaPruebas(obtenerPruebasNoEnProceso(listaPruebas,
						result));
	}

	private List<EvaluadoBO> obtenerEvaluadosDeParticipacion(
			List<ParticipacionEnProcesoBO> result) {
		List<EvaluadoBO> resultado = new ArrayList<EvaluadoBO>();
		for (ParticipacionEnProcesoBO participacionEnProcesoBO : result) {
			resultado.add(participacionEnProcesoBO.getUsuarioBasico());
		}
		return resultado;
	}

	private List<PruebaUsuarioBO> obtenerPruebasNoEnProceso(
			List<PruebaUsuarioBO> pruebas,
			List<PruebaUsuarioBO> listaPruebasDeProceso) {
		List<PruebaUsuarioBO> resultado = new ArrayList<PruebaUsuarioBO>();
		boolean continuar = true;

		for (int i = 0; i < pruebas.size(); i++) {
			for (int j = 0; j < listaPruebasDeProceso.size() && continuar; j++) {
				if (listaPruebasDeProceso.get(j).getIdentificador() == pruebas
						.get(i).getIdentificador()) {
					continuar = false;
				}
			}
			if (continuar) {
				resultado.add(pruebas.get(i));
			}
			continuar = true;
		}
		return resultado;
	}

	private List<EvaluadoBO> obtenerEvaluadosNoEnProceso(
			List<EvaluadoBO> usuarios, List<EvaluadoBO> listaEvaluadosDeProceso) {
		List<EvaluadoBO> resultado = new ArrayList<EvaluadoBO>();
		boolean continuar = true;

		for (int i = 0; i < usuarios.size(); i++) {
			for (int j = 0; j < listaEvaluadosDeProceso.size() && continuar; j++) {
				if (listaEvaluadosDeProceso.get(j).getIdentificador() == usuarios
						.get(i).getIdentificador()) {
					continuar = false;
				}
			}
			if (continuar) {
				resultado.add(usuarios.get(i));
			}
			continuar = true;
		}
		return resultado;
	}

}
