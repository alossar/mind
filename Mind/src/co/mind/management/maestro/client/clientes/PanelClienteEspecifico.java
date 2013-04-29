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

import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
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

	public PanelClienteEspecifico() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		panelInformacionCliente = new VLayout();
		panelInformacionCliente.setHeight100();
		panelInformacionCliente.setWidth("20%");

		DynamicForm formProceso = new DynamicForm();
		formProceso.setWidth100();
		formProceso.setHeight100();
		formProceso.setPadding(5);

		nombreItem = new TextItem();
		nombreItem.setTitle("Nombres");
		nombreItem.setCanEdit(false);

		apellidosItem = new TextItem();
		apellidosItem.setTitle("Apellidos");
		apellidosItem.setCanEdit(false);

		cedulaItem = new TextItem();
		cedulaItem.setTitle("Cédula");
		cedulaItem.setCanEdit(false);

		mailItem = new TextItem();
		mailItem.setTitle("Correo Electrónico");
		mailItem.setCanEdit(false);

		ciudadItem = new TextItem();
		ciudadItem.setTitle("Ciudad");
		ciudadItem.setCanEdit(false);

		telefono = new TextItem();
		telefono.setTitle("Teléfono");
		telefono.setCanEdit(false);

		nombreEmpresa = new TextItem();
		nombreEmpresa.setTitle("Nombre Empresa");
		nombreEmpresa.setCanEdit(false);

		cargo = new TextItem();
		cargo.setTitle("Cargo");
		cargo.setCanEdit(false);

		formProceso.setFields(nombreItem, apellidosItem, cedulaItem, mailItem,
				ciudadItem, telefono, nombreEmpresa, cargo);

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("80%");
		v2.setBackgroundColor("white");
		v2.addChild(formProceso);

		panelEncabezadoCliente = new PanelEncabezadoDialogo("Cliente",
				"Informaci\u00F3n del cliente.",
				"insumos/procesos/logoProcesos.png");
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
					botonAgregarUsos.setVisible(true);

				} else if (t.getID().equalsIgnoreCase("pruebas")) {
					fechaFinal.setVisible(false);
					fechaInicio.setVisible(false);
					botonGenerarReporte.setVisible(false);
					botonAgregarUsos.setVisible(false);

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
				"Usos Redimidos");
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
				mostrarDialogoAgregarUso();

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
		winModal.setHeight(150);
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
				"img/admin/bot4.png");
		p.setSize("100%", "70px");

		formUsos = new DynamicForm();
		formUsos.setHeight("40%");
		formUsos.setWidth100();
		formUsos.setPadding(5);
		formUsos.setLayoutAlign(VerticalAlignment.BOTTOM);

		textUsos = new IntegerItem();
		textUsos.setRequired(true);
		textUsos.setTitle("C\u00E9dula");
		textUsos.setAllowExpressions(false);

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

	public void actualizarUsosCliente(List<UsoBO> result) {
		listGridUsos.setData(UsoUsuarioListgridRecord.getRecords(result));
	}

	public void actualizarPruebasCliente(List<PruebaUsuarioBO> result) {
		listGridTemasDeClientes
				.setData(PruebaListGridRecord.getRecords(result));
	}

	public void actualizarDatosCliente(UsuarioBO bo) {
		usuarioSeleccionado = bo;
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
