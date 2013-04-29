package co.mind.management.maestro.client.programadores;

import java.util.List;

import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.ProcesoRecord;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class PanelProgramadorEspecifico extends HLayout {

	private VLayout panelInformacionCliente;
	private VLayout panelListados;
	private ListGrid listGridProcesos;

	private TextItem nombreItem;
	private TextItem apellidosItem;
	private TextItem cedulaItem;
	private TextItem mailItem;
	private TextItem telefono;

	private PanelEncabezadoDialogo panelEncabezadoCliente;
	private UsuarioBO usuarioSeleccionado;

	public PanelProgramadorEspecifico() {
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

		apellidosItem = new TextItem();
		apellidosItem.setTitle("Apellidos");

		cedulaItem = new TextItem();
		cedulaItem.setTitle("Cédula");

		mailItem = new TextItem();
		mailItem.setTitle("Correo Electrónico");

		telefono = new TextItem();
		telefono.setTitle("Teléfono");

		formProceso.setFields(nombreItem, apellidosItem, cedulaItem, mailItem,
				telefono);

		VLayout v2 = new VLayout();
		v2.setWidth100();
		v2.setHeight("80%");
		v2.setBackgroundColor("white");
		v2.addChild(formProceso);

		panelEncabezadoCliente = new PanelEncabezadoDialogo("Programador",
				"Informaci\u00F3n del programador.",
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

			}
		});

		Tab tabUsos = new Tab("Procesos");
		tabUsos.setID("procesos");

		listGridProcesos = new ListGrid();
		listGridProcesos.setWidth100();
		listGridProcesos.setHeight100();
		listGridProcesos.setShowAllRecords(true);
		listGridProcesos.setEmptyMessage("No hay procesos del programador.");

		ListGridField nombreField = new ListGridField("nombreProceso", "Nombre");
		ListGridField descripcionField = new ListGridField(
				"descripcionProceso", "Descripci\u00F3n");
		ListGridField fechaField = new ListGridField("fechaCreacion",
				"Fecha de Creaci\u00F3n");
		fechaField.setCanEdit(false);
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		preguntasField.setCanEdit(false);
		ListGridField tiempoField = new ListGridField("tiempoProceso",
				"Tiempo (Minutos)");
		tiempoField.setCanEdit(false);
		listGridProcesos.setFields(nombreField, descripcionField, fechaField,
				preguntasField, tiempoField);
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

	public void actualizarProcesosProgramador(List<ProcesoUsuarioBO> result) {
		listGridProcesos.setData(ProcesoRecord.getRecords(result));
	}

	public void actualizarDatosProgramador(UsuarioBO bo) {
		usuarioSeleccionado = bo;
		nombreItem.setValue(bo.getNombres());
		apellidosItem.setValue(bo.getApellidos());
		cedulaItem.setValue(bo.getIdentificador());
		mailItem.setValue(bo.getCorreo_Electronico());
		telefono.setValue(bo.getTelefono());
	}
}
