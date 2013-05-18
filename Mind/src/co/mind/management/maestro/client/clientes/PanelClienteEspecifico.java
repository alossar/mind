package co.mind.management.maestro.client.clientes;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsoUsuarioListgridRecord;
import co.mind.management.shared.recursos.Convencion;

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
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelClienteEspecifico extends HLayout {

	private VLayout panelInformacionCliente;
	private VLayout panelListados;
	private ListGrid listGridTemasDeClientes;
	private ListGrid listGridUsos;

	private ListGrid listGridPruebas;

	private TextItem nombreItem;
	private TextItem apellidosItem;
	private TextItem cedulaItem;
	private TextItem mailItem;
	private TextItem ciudadItem;
	private TextItem telefono;
	private TextItem nombreEmpresa;
	private TextItem cargo;

	private ToolStripButton botonGenerarReporte;
	private ToolStripButton botonAgregarUsos;
	private PanelEncabezadoDialogo panelEncabezadoCliente;
	private DateItem fechaInicio;
	private DateItem fechaFinal;
	private UsuarioBO usuarioSeleccionado;
	private DynamicForm formUsos;
	private IntegerItem textUsos;
	private List<PruebaUsuarioBO> listaPruebas;

	public PanelClienteEspecifico() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		panelInformacionCliente = new VLayout();
		panelInformacionCliente.setHeight100();
		panelInformacionCliente.setWidth("250px");

		DynamicForm formProceso = new DynamicForm();
		formProceso.setWidth100();
		formProceso.setHeight100();
		formProceso.setPadding(5);

		nombreItem = new TextItem();
		nombreItem.setTitle("Nombres");
		nombreItem.setCanEdit(false);
		nombreItem.setWidth("100%");
		nombreItem.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

		apellidosItem = new TextItem();
		apellidosItem.setTitle("Apellidos");
		apellidosItem.setCanEdit(false);
		apellidosItem.setWidth("100%");
		apellidosItem.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

		cedulaItem = new TextItem();
		cedulaItem.setTitle("Cédula");
		cedulaItem.setCanEdit(false);
		cedulaItem.setWidth("100%");
		cedulaItem.setLength(Convencion.MAXIMA_LONGITUD_CEDULA);

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator
				.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");

		mailItem = new TextItem();
		mailItem.setTitle("Correo Electrónico");
		mailItem.setValidators(regExpValidator);
		mailItem.setWidth("100%");
		mailItem.setCanEdit(false);

		ciudadItem = new TextItem();
		ciudadItem.setTitle("Ciudad");
		ciudadItem.setCanEdit(false);
		ciudadItem.setWidth("100%");
		ciudadItem.setLength(Convencion.MAXIMA_LONGITUD_CIUDAD);

		telefono = new TextItem();
		telefono.setTitle("Teléfono");
		telefono.setCanEdit(false);
		telefono.setWidth("100%");
		telefono.setLength(10);

		nombreEmpresa = new TextItem();
		nombreEmpresa.setTitle("Nombre Empresa");
		nombreEmpresa.setCanEdit(false);
		nombreEmpresa.setWidth("100%");
		nombreEmpresa.setLength(Convencion.MAXIMA_LONGITUD_EMPRESA);

		cargo = new TextItem();
		cargo.setTitle("Cargo");
		cargo.setCanEdit(false);
		cargo.setWidth("100%");
		cargo.setLength(Convencion.MAXIMA_LONGITUD_CARGO);

		formProceso.setFields(nombreItem, apellidosItem, cedulaItem, mailItem,
				ciudadItem, telefono, nombreEmpresa, cargo);

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("80%");
		v2.setBackgroundColor("white");
		v2.addChild(formProceso);

		panelEncabezadoCliente = new PanelEncabezadoDialogo("Cliente",
				"Informaci\u00F3n del cliente.", "img/admin/bot6.png");
		panelEncabezadoCliente.setSize("100%", "70px");

		panelInformacionCliente.addMember(panelEncabezadoCliente);
		panelInformacionCliente.addMember(v2);

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
				Tab t = topTabSet.getSelectedTab();
				if (t.getID().equalsIgnoreCase("usos")) {
					fechaFinal.setVisible(true);
					fechaInicio.setVisible(true);
					botonGenerarReporte.setVisible(true);
					botonAgregarUsos.setTitle("Agregar Usos");

				} else if (t.getID().equalsIgnoreCase("pruebas")) {
					fechaFinal.setVisible(false);
					fechaInicio.setVisible(false);
					botonGenerarReporte.setVisible(false);
					botonAgregarUsos.setTitle("Agregar Pruebas");
				}
			}
		});

		Tab tabUsos = new Tab("Usos");
		tabUsos.setID("usos");

		listGridUsos = new ListGrid();
		listGridUsos.setWidth100();
		listGridUsos.setHeight100();
		listGridUsos.setShowAllRecords(true);
		listGridUsos.setEmptyMessage("No hay usos del cliente.");

		ListGridField nombreField = new ListGridField("fechaAsignacion",
				"Fecha de Asignación");
		ListGridField fecha = new ListGridField("fechaVencimiento",
				"Fecha de Terminación");
		fecha.setEmptyCellValue("Aún es válido");
		ListGridField apellidoField = new ListGridField("usosAsignados",
				"Usos Asignados");
		ListGridField correoField = new ListGridField("usosRedimidos",
				"Usos Utilizados");
		listGridUsos.setFields(nombreField, fecha, apellidoField, correoField);
		listGridUsos.setCanResizeFields(true);

		tabUsos.setPane(listGridUsos);

		Tab tabPruebas = new Tab("Pruebas");
		tabPruebas.setID("pruebas");

		listGridTemasDeClientes = new ListGrid();
		listGridTemasDeClientes.setWidth100();
		listGridTemasDeClientes.setHeight100();
		listGridTemasDeClientes.setShowAllRecords(true);
		ListGridField nombrePrueba = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		ListGridField tiempoField = new ListGridField("tiempoPrueba",
				"Tiempo (Minutos)");
		listGridTemasDeClientes.setFields(nombrePrueba, apellidosField,
				preguntasField, tiempoField);
		listGridTemasDeClientes.setCanResizeFields(true);
		listGridTemasDeClientes
				.setEmptyMessage("No se encuentran pruebas del cliente.");

		tabPruebas.setPane(listGridTemasDeClientes);

		topTabSet.addTab(tabUsos);
		topTabSet.addTab(tabPruebas);

		fechaInicio = new DateItem("fechaInicio", "Fecha Inicio");
		fechaFinal = new DateItem("fechaFinal", "Fecha Final");
		Date hoy = new Date();
		fechaFinal.setValue(hoy);
		fechaFinal.setEndDate(hoy);
		fechaInicio.setValue(hoy.getTime() - 3600 * 1000 * 24 * 30);
		fechaInicio.setEndDate(hoy);

		DynamicForm formFechaInicio = new DynamicForm();
		formFechaInicio.setWidth("*");
		formFechaInicio.setMinWidth(50);
		formFechaInicio.setNumCols(1);
		formFechaInicio.setItems(fechaInicio);

		DynamicForm formFechaFinal = new DynamicForm();
		formFechaFinal.setWidth("*");
		formFechaFinal.setMinWidth(50);
		formFechaFinal.setNumCols(1);
		formFechaFinal.setItems(fechaFinal);

		botonGenerarReporte = new ToolStripButton("Generar Reporte de Usos",
				"icons/16/document_plain_new.png");

		botonGenerarReporte
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						Date dateInicio = new Date(fechaInicio.getValueAsDate()
								.getTime() - 3600 * 1000 * 12);
						Date dateFinal = new Date(fechaFinal.getValueAsDate()
								.getTime() + 3600 * 1000 * 12);

						if (dateInicio.after(dateFinal)) {
							SC.warn("Las fechas para generar el reporte son incorrectas");
						} else {
							Maestro.generarReporteUsos(usuarioSeleccionado,
									dateInicio, dateFinal);
						}

					}
				});

		botonAgregarUsos = new ToolStripButton("Agregar Usos",
				"icons/16/document_plain_new.png");
		botonAgregarUsos.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				Tab t = topTabSet.getSelectedTab();
				if (t.getID().equalsIgnoreCase("usos")) {
					mostrarDialogoAgregarUso();
				} else if (t.getID().equalsIgnoreCase("pruebas")) {
					mostrarDialogoAgregarPruebas();
				}

			}
		});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addMember(formFechaInicio);
		menuBarUsuarioBasico.addMember(formFechaFinal);
		menuBarUsuarioBasico.addButton(botonGenerarReporte);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addButton(botonAgregarUsos);
		menuBarUsuarioBasico.addSeparator();

		panelListados.addMember(topTabSet);
		panelListados.addMember(menuBarUsuarioBasico);
		addMember(panelInformacionCliente);
		addMember(panelListados);

	}

	private void mostrarDialogoAgregarUso() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(180);
		winModal.setTitle("Agregar Usos");
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

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo("Asignar Usos",
				"Asigne una nueva cantidad de usos a un cliente",
				"img/admin/bot6.png");
		p.setSize("100%", "70px");

		formUsos = new DynamicForm();
		formUsos.setHeight("40%");
		formUsos.setWidth100();
		formUsos.setPadding(5);
		formUsos.setLayoutAlign(VerticalAlignment.BOTTOM);

		textUsos = new IntegerItem();
		textUsos.setRequired(true);
		textUsos.setTitle("Usos");
		textUsos.setAllowExpressions(false);
		textUsos.setLength(10);

		ButtonItem boton = new ButtonItem("Crear");
		boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (formUsos.validate()) {
					int usos = textUsos.getValueAsInteger();
					Maestro.AgregarUsos(usuarioSeleccionado, usos);
					winModal.destroy();
				}
			}
		});

		formUsos.setFields(textUsos, boton);

		winModal.addItem(p);
		winModal.addItem(formUsos);

		winModal.show();
	}

	private void mostrarDialogoAgregarPruebas() {
		final Window winModal = new Window();

		final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Agregar Pruebas", "Agregue pruebas al cliente",
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

		final IButton boton = new IButton("Agregar");
		boton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				Record[] records = listGridPruebas.getSelectedRecords();
				if (records != null) {
					PruebaListGridRecord[] pruebas = new PruebaListGridRecord[records.length];
					for (int i = 0; i < records.length; i++) {
						pruebas[i] = (PruebaListGridRecord) records[i];
					}
					List<PruebaUsuarioBO> pruebasBO = PruebaListGridRecord
							.getBO(pruebas);
					Maestro.agregarPruebasACliente(pruebasBO,
							usuarioSeleccionado);
					winModal.destroy();
				} else {
					SC.warn("Debe seleccionar las pruebas que desea agregar al proceso.");
				}

			}

		});

		winModal.addItem(p);

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
		listGridPruebas.setData(PruebaListGridRecord.getRecords(listaPruebas));

		winModal.addItem(listGridPruebas);
		winModal.addItem(boton);
		winModal.show();

	}

	public void actualizarUsosCliente(List<UsoBO> result) {
		listGridUsos.setData(UsoUsuarioListgridRecord.getRecords(result));
	}

	public void actualizarPruebasCliente(List<PruebaUsuarioBO> result) {
		listGridTemasDeClientes
				.setData(PruebaListGridRecord.getRecords(result));
	}

	public void actualizarDatosCliente(UsuarioBO bo,
			List<PruebaUsuarioBO> listaPruebas2) {
		usuarioSeleccionado = bo;
		listaPruebas = listaPruebas2;
		nombreItem.setValue(bo.getNombres());
		apellidosItem.setValue(bo.getApellidos());
		cedulaItem.setValue(bo.getIdentificador());
		mailItem.setValue(bo.getCorreo_Electronico());
		ciudadItem.setValue(bo.getCiudad());
		telefono.setValue(bo.getTelefono());
		nombreEmpresa.setValue(bo.getEmpresa());
		cargo.setValue(bo.getCargo());
	}
}
