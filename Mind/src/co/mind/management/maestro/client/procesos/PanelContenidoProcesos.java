package co.mind.management.maestro.client.procesos;

import java.util.Date;
import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.maestro.client.temas.contenedores.PanelAgregarLaminas;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.event.shared.UmbrellaException;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.DateTimeItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelContenidoProcesos extends HLayout {

	private ListGrid listGridProcesos;
	private ToolStripButton botonRegresar;
	private ToolStripButton botonNuevoBasico;
	private ToolStripButton botonEliminarBasico;
	private PanelProcesoEspecifico panelProcesoEspecifico;
	private ProcesoUsuarioBO procesoSeleccionado;
	private DynamicForm formNuevoProceso;
	private TextItem textNombreProcesoNuevo;
	private TextAreaItem textAreaDescripcionProcesoNuevo;
	private DateItem dateTimeItemFechaInicioNuevo;
	private DateItem dateTimeItemFechaFinalizacionNuevo;
	private CheckboxItem checkBoxHabilitarFechaFinalizacion;

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

		dateTimeItemFechaInicioNuevo = new DateTimeItem();
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
						proceso.setFechaFinalizacion(dateTimeItemFechaFinalizacionNuevo
								.getValueAsDate());
					}
					proceso.setFechaInicio(dateTimeItemFechaInicioNuevo
							.getValueAsDate());
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
		panelProcesoEspecifico.actualizarListaPruebas(pruebas);
	}

	public void actualizarListaUsuariosBasicos(List<EvaluadoBO> usuarios) {
		panelProcesoEspecifico.actualizarEvaluados(usuarios);
	}

	public void actualizarParticipaciones(
			List<ParticipacionEnProcesoBO> result) {
		panelProcesoEspecifico
				.actualizarParticipaciones(result);
	}

	public void actualizarResultados(
			List<ParticipacionEnProcesoBO> result) {
		panelProcesoEspecifico
				.actualizarResultados(result);
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
	}

}
