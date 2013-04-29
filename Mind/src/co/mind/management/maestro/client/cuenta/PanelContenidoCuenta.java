package co.mind.management.maestro.client.cuenta;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.UsoUsuarioListgridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.MatchesFieldValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class PanelContenidoCuenta extends HLayout {

	private VLayout layoutContenedor;
	private VLayout panelListados;
	private TextItem nombreItem;
	private TextItem apellidosItem;
	private TextItem cedulaItem;
	private TextItem mailItem;
	private TextItem ciudadItem;
	private TextItem telefono;
	private TextItem nombreEmpresa;
	private TextItem cargo;
	private ButtonItem botonCambiarContrasena;
	private ButtonItem botonEditarCuenta;
	private ButtonItem botonGuardarCambios;
	private boolean enEditar = false;
	private DynamicForm formNuevaCategoria;
	private PasswordItem textPassActual;
	private PasswordItem textPassNuevaRepetir;
	private PasswordItem textPassNueva;
	private PanelEncabezadoDialogo panelEncabezadoCuenta;
	private ListGrid listGridProcesos;
	private ListGrid listGridUsos;

	public PanelContenidoCuenta(UsuarioBO usuario) {
		setWidth("90%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		layoutContenedor = new VLayout();
		layoutContenedor.setHeight100();
		layoutContenedor.setWidth("20%");
		layoutContenedor.setBackgroundColor("white");

		panelListados = new VLayout();
		panelListados.setHeight100();
		panelListados.setWidth("70%");

		panelEncabezadoCuenta = new PanelEncabezadoDialogo("Cuenta",
				"Informaci\u00F3n de la Cuenta.", "img/admin/bot2.png");
		panelEncabezadoCuenta.setSize("100%", "70px");

		layoutContenedor.addMember(panelEncabezadoCuenta);

		inicializarInformacionPersonal();
		inicializarInformacionContacto();
		inicializarFormBotones();
		if (usuario.getTipo().equalsIgnoreCase(Convencion.TIPO_USUARIO_MAESTRO)
				|| usuario.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
			inicializarPanelProcesosParaRevisar();
		} else if (usuario.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
			inicializarPanelUsos();
		}
		setInformacionContacto(usuario);
		setHabilitarEdicion(false);

		addMember(layoutContenedor);
		addMember(panelListados);
	}

	private void inicializarInformacionPersonal() {
		DynamicForm formInformacionPersonal = new DynamicForm();
		formInformacionPersonal.setGroupTitle("Información Personal");
		formInformacionPersonal.setIsGroup(false);
		formInformacionPersonal.setNumCols(2);
		// form.setBorder("1px solid blue");
		formInformacionPersonal.setPadding(5);
		formInformacionPersonal.setCanDragResize(false);

		nombreItem = new TextItem();
		nombreItem.setTitle("Nombres");

		apellidosItem = new TextItem();
		apellidosItem.setTitle("Apellidos");

		cedulaItem = new TextItem();
		cedulaItem.setTitle("Cédula");

		mailItem = new TextItem();
		mailItem.setTitle("Correo Electrónico");

		ciudadItem = new TextItem();
		ciudadItem.setTitle("Ciudad");

		formInformacionPersonal.setFields(nombreItem, apellidosItem,
				cedulaItem, mailItem, ciudadItem);

		layoutContenedor.addMember(formInformacionPersonal);
	}

	private void inicializarInformacionContacto() {
		DynamicForm formInformacionContacto = new DynamicForm();
		formInformacionContacto.setGroupTitle("Información de Contacto");
		formInformacionContacto.setIsGroup(false);
		formInformacionContacto.setNumCols(2);
		// form.setBorder("1px solid blue");
		formInformacionContacto.setPadding(5);
		formInformacionContacto.setCanDragResize(true);

		nombreEmpresa = new TextItem();
		nombreEmpresa.setTitle("Empresa");

		cargo = new TextItem();
		cargo.setTitle("Cargo");

		telefono = new TextItem();
		telefono.setTitle("Teléfono");

		formInformacionContacto.setFields(nombreEmpresa, cargo, telefono);
		layoutContenedor.addMember(formInformacionContacto);
	}

	private void inicializarFormBotones() {

		DynamicForm formBotones = new DynamicForm();
		formBotones.setIsGroup(false);
		formBotones.setNumCols(2);
		// form.setBorder("1px solid blue");
		formBotones.setPadding(5);
		formBotones.setCanDragResize(false);

		botonEditarCuenta = new ButtonItem();
		botonEditarCuenta.setTitle("Editar Cuenta");
		botonEditarCuenta.setAutoFit(true);
		botonEditarCuenta.setStartRow(false);
		botonEditarCuenta.setEndRow(false);
		botonEditarCuenta
				.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						if (!enEditar) {
							enEditar = true;
							botonGuardarCambios.setVisible(true);
							setHabilitarEdicion(true);
							botonEditarCuenta.setTitle("Cancelar");
						} else {
							botonGuardarCambios.setVisible(false);
							enEditar = false;
							setHabilitarEdicion(false);
							botonEditarCuenta.setTitle("Editar");

						}
					}
				});

		botonGuardarCambios = new ButtonItem();
		botonGuardarCambios.setTitle("Guardar Cambios");
		botonGuardarCambios.setVisible(false);
		botonGuardarCambios.setAutoFit(true);
		botonGuardarCambios.setStartRow(false);
		botonGuardarCambios.setEndRow(false);
		botonGuardarCambios
				.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {

					}
				});

		botonCambiarContrasena = new ButtonItem();
		botonCambiarContrasena.setTitle("Cambiar Contraseña");
		botonCambiarContrasena.setAutoFit(true);
		botonCambiarContrasena.setStartRow(false);
		botonCambiarContrasena.setEndRow(false);
		botonCambiarContrasena
				.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
						mostrarDialogoCambiarContrasena();
					}
				});

		formBotones.setFields(botonEditarCuenta, botonGuardarCambios,
				botonCambiarContrasena);
		layoutContenedor.addMember(formBotones);
	}

	private void inicializarPanelProcesosParaRevisar() {
		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

			}
		});

		Tab tabUsos = new Tab("Procesos de Revisión");
		tabUsos.setID("procesos");

		listGridProcesos = new ListGrid();
		listGridProcesos.setWidth100();
		listGridProcesos.setHeight100();
		listGridProcesos.setShowAllRecords(true);
		listGridProcesos
				.setEmptyMessage("No hay procesos pendientes de revisión.");

		ListGridField nombreUsuarioField = new ListGridField("nombreUsuario",
				"Nombre del Cliente");
		ListGridField idUsuario = new ListGridField("idUsuario",
				"Cédula del Cliente");
		ListGridField empresaCliente = new ListGridField("empresa",
				"Empresa del Cliente");
		ListGridField nombreField = new ListGridField("nombreProceso",
				"Nombre del Proceso");
		ListGridField descripcionField = new ListGridField(
				"descripcionProceso", "Descripci\u00F3n");
		listGridProcesos.setFields(idUsuario, nombreUsuarioField,
				empresaCliente, nombreField, descripcionField);
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
	}

	private void inicializarPanelUsos() {
		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setWidth100();
		topTabSet.setHeight100();
		topTabSet.addTabSelectedHandler(new TabSelectedHandler() {

			@Override
			public void onTabSelected(TabSelectedEvent event) {

			}
		});

		Tab tabUsos = new Tab("Usos", "pieces/16/pawn_blue.png");
		tabUsos.setID("usos");

		listGridUsos = new ListGrid();
		listGridUsos.setWidth100();
		listGridUsos.setHeight100();
		listGridUsos.setShowAllRecords(true);
		listGridUsos.setEmptyMessage("No hay usos en la cuenta.");

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

		topTabSet.addTab(tabUsos);

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addSeparator();

		panelListados.addMember(topTabSet);
		panelListados.addMember(menuBarUsuarioBasico);
	}

	public void setInformacionContacto(UsuarioBO usuario) {
		nombreItem.setValue(usuario.getNombres());
		apellidosItem.setValue(usuario.getApellidos());
		cedulaItem.setValue(usuario.getIdentificador());
		mailItem.setValue(usuario.getCorreo_Electronico());
		ciudadItem.setValue(usuario.getCiudad());
		nombreEmpresa.setValue(usuario.getEmpresa());
		cargo.setValue(usuario.getCargo());
		telefono.setValue(usuario.getTelefono());
	}

	public void limpiarInformacion() {
		nombreItem.clearValue();
		apellidosItem.clearValue();
		cedulaItem.clearValue();
		mailItem.clearValue();
		ciudadItem.clearValue();
		nombreEmpresa.clearValue();
		cargo.clearValue();
		telefono.clearValue();
	}

	public void setHabilitarEdicion(boolean habilitar) {
		nombreItem.setCanEdit(habilitar);
		apellidosItem.setCanEdit(habilitar);
		cedulaItem.setCanEdit(false);
		mailItem.setCanEdit(false);
		ciudadItem.setCanEdit(habilitar);
		nombreEmpresa.setCanEdit(habilitar);
		cargo.setCanEdit(habilitar);
		telefono.setCanEdit(habilitar);
	}

	private void mostrarDialogoCambiarContrasena() {
		final Window winModal = new Window();

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Cambiar Contraseña",
				"Cambie la contraseña que tiene en el momento",
				"img/admin/bot2.png");
		p.setSize("100%", "70px");

		winModal.setWidth(350);
		winModal.setHeight(250);
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

		MatchesFieldValidator validator = new MatchesFieldValidator();
		validator.setOtherField("repetir");
		validator.setErrorMessage("Passwords do not match");

		textPassActual = new PasswordItem();
		textPassActual.setTitle("Contraseña Actual");
		textPassActual.setRequired(true);

		textPassNueva = new PasswordItem();
		textPassNueva.setTitle("Contraseña Nueva");
		textPassNueva.setRequired(true);
		textPassNueva.setValidators(validator);

		textPassNuevaRepetir = new PasswordItem();
		textPassNuevaRepetir.setTitle("Repetir Contraseña Nueva");
		textPassNuevaRepetir.setRequired(true);
		textPassNuevaRepetir.setName("repetir");

		formNuevaCategoria.setFields(textPassActual, textPassNueva,
				textPassNuevaRepetir);

		IButton boton = new IButton("Crear");
		boton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.events.ClickEvent event) {
				if (formNuevaCategoria.validate()) {
					if (textPassNueva.getValueAsString().equals(
							textPassNuevaRepetir.getValueAsString())) {
						Maestro.cambiarContrasena(textPassNueva
								.getValueAsString());
						winModal.destroy();
					} else {
						textPassActual.setValue("");
						textPassNueva.setValue("");
						textPassNuevaRepetir.setValue("");
					}
				}

			}
		});

		winModal.addItem(p);
		winModal.addItem(formNuevaCategoria);
		winModal.addItem(boton);
		winModal.show();
	}

	public void actualizarListaProcesosRevisar(List<ProcesoUsuarioBO> result) {
		listGridProcesos.setData(ProcesoRecord.getRecords(result));
	}

	public void actualizarListaUsos(List<UsoBO> result) {
		listGridUsos.setData(UsoUsuarioListgridRecord.getRecords(result));
	}
}
