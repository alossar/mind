package co.mind.management.maestro.client.evaluados;

import java.util.List;

import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
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

public class PanelEvaluadoEspecifico extends HLayout {

	private VLayout panelInformacionCliente;
	private VLayout panelListados;
	// private ListGrid listGridTemasDeClientes;
	private ListGrid listGridProcesos;

	private TextItem nombreItem;
	private TextItem apellidosItem;
	private TextItem cedulaItem;
	private TextItem mailItem;

	private PanelEncabezadoDialogo panelEncabezadoCliente;

	public PanelEvaluadoEspecifico() {
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

		formProceso.setFields(nombreItem, apellidosItem, cedulaItem, mailItem);

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("80%");
		v2.setBackgroundColor("white");
		v2.addChild(formProceso);

		panelEncabezadoCliente = new PanelEncabezadoDialogo("Evaluado",
				"Informaci\u00F3n del evaluado.", "img/admin/bot3.png");
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

				}
				// else if (t.getID().equalsIgnoreCase("pruebas")) {
				// fechaFinal.setVisible(false);
				// fechaInicio.setVisible(false);
				// botonGenerarReporte.setVisible(false);
				// botonAgregarUsos.setVisible(false);
				//
				// }
			}
		});

		Tab tabUsos = new Tab("Participaciones");
		tabUsos.setID("usos");

		listGridProcesos = new ListGrid();
		listGridProcesos.setWidth100();
		listGridProcesos.setHeight100();
		listGridProcesos.setShowAllRecords(true);
		listGridProcesos
				.setEmptyMessage("No hay participaciones en procesos del evaluado.");

		ListGridField nombreField = new ListGridField("procesoNombre",
				"Nombre del Prceso");
		ListGridField apellidoField = new ListGridField("procesoDesc",
				"Descripción del Proceso");
		ListGridField codigoField = new ListGridField("codigo",
				"C\u00F3digo Acceso");
		ListGridField estadoField = new ListGridField("estado",
				"Estado de Participación");
		listGridProcesos.setFields(nombreField, apellidoField, codigoField,
				estadoField);
		listGridProcesos.setCanResizeFields(true);

		tabUsos.setPane(listGridProcesos);

		topTabSet.addTab(tabUsos);

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addSeparator();

		panelListados.addMember(topTabSet);
		panelListados.addMember(menuBarUsuarioBasico);
		addMember(panelInformacionCliente);
		addMember(panelListados);

	}

	public void actualizarParticipacionesEvaluado(
			List<ParticipacionEnProcesoBO> result) {
		listGridProcesos.setData(ParticipacionEnProcesoListGridRecord
				.getRecords(result));
	}

	public void actualizarDatosEvaluado(EvaluadoBO bo) {
		nombreItem.setValue(bo.getNombres());
		apellidosItem.setValue(bo.getApellidos());
		cedulaItem.setValue(bo.getIdentificador());
		mailItem.setValue(bo.getCorreoElectronico());
	}
}
