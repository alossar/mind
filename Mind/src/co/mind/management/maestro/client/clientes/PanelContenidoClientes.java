package co.mind.management.maestro.client.clientes;

import java.util.List;

import co.mind.management.maestro.client.DialogoProcesamiento;
import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowMouseDownEvent;
import com.smartgwt.client.widgets.grid.events.RowMouseDownHandler;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedEvent;
import com.smartgwt.client.widgets.grid.events.SelectionUpdatedHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelContenidoClientes extends HLayout {

	private ListGrid listGridUsuariosAdministradores;
	private PanelClienteEspecifico panelClienteEspecifico;
	private Window windowCrearLamina;
	private PanelAgregarCliente panelAgregarCliente;
	private List<PruebaUsuarioBO> listaPruebas;
	private ToolStripButton botonRegresar;
	private ToolStripButton botonNuevoAdministrador;
	private TextItem searchItem;
	private DynamicForm formBusqueda;
	private ToolStripButton botonEliminarCuenta;
	private ToolStripButton botonDesactivarCuenta;
	private DialogoProcesamiento dlgNotificaciones;

	public PanelContenidoClientes() {
		setWidth("90%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		listGridUsuariosAdministradores = new ListGrid();
		listGridUsuariosAdministradores.setWidth100();
		listGridUsuariosAdministradores.setHeight100();
		listGridUsuariosAdministradores.setShowAllRecords(true);

		ListGridField idUsuarioField = new ListGridField("id", "C\u00E9dula");
		idUsuarioField.setCanEdit(false);
		ListGridField nombreUsuarioField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosUsuarioField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoUsuarioField = new ListGridField("correo", "Correo");
		ListGridField empresaUsuarioField = new ListGridField("empresa",
				"Empresa");
		ListGridField telefonoUsuarioField = new ListGridField("telefono",
				"Telefono");
		ListGridField estadoField = new ListGridField("estado",
				"Estado de la Cuenta");
		ListGridField vencimientoField = new ListGridField("vencimiento",
				"Vencimiento de la Cuenta");
		vencimientoField.setEmptyCellValue("No definido");
		estadoField.setCanEdit(false);

		listGridUsuariosAdministradores.setFields(idUsuarioField,
				nombreUsuarioField, apellidosUsuarioField, correoUsuarioField,
				empresaUsuarioField, telefonoUsuarioField, estadoField,
				vencimientoField);
		listGridUsuariosAdministradores.setCanResizeFields(true);
		listGridUsuariosAdministradores
				.setEmptyMessage("No se encuentran clientes.");

		listGridUsuariosAdministradores
				.addCellDoubleClickHandler(new CellDoubleClickHandler() {

					@Override
					public void onCellDoubleClick(CellDoubleClickEvent event) {
						UsuarioAdministradorListGridRecord record = (UsuarioAdministradorListGridRecord) event
								.getRecord();
						if (record != null) {
							UsuarioBO cliente = UsuarioAdministradorListGridRecord
									.getBO(record);
							panelClienteEspecifico
									.actualizarDatosCliente(cliente);
							Maestro.obtenerUsosCliente(cliente);
							Maestro.obtenerPruebasCliente(cliente);

							listGridUsuariosAdministradores.setVisible(false);
							panelClienteEspecifico.setVisible(true);
							formBusqueda.setVisible(false);

							botonNuevoAdministrador.setVisible(false);
							botonRegresar.setVisible(true);
						}
					}
				});

		listGridUsuariosAdministradores
				.addRowMouseDownHandler(new RowMouseDownHandler() {

					@Override
					public void onRowMouseDown(RowMouseDownEvent event) {
						UsuarioAdministradorListGridRecord record = (UsuarioAdministradorListGridRecord) event
								.getRecord();
						if (record != null) {
							UsuarioBO user = UsuarioAdministradorListGridRecord
									.getBO(record);
							if (user.getEstado_Cuenta().equalsIgnoreCase(
									Convencion.ESTADO_CUENTA_ACTIVA)) {
								botonDesactivarCuenta
										.setTitle("Desactivar Cuenta");
								botonDesactivarCuenta.setVisible(true);

							} else if (user.getEstado_Cuenta()
									.equalsIgnoreCase(
											Convencion.ESTADO_CUENTA_INACTIVA)) {
								botonDesactivarCuenta
										.setTitle("Activar Cuenta");
								botonDesactivarCuenta.setVisible(true);

							} else {
								botonDesactivarCuenta.setVisible(false);
							}

						}
					}
				});

		panelClienteEspecifico = new PanelClienteEspecifico();
		panelClienteEspecifico.setHeight100();
		panelClienteEspecifico.setWidth100();
		panelClienteEspecifico.setVisible(false);

		botonRegresar = new ToolStripButton("Volver",
				"icons/16/document_plain_new.png");
		botonRegresar.setVisible(false);

		botonRegresar
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						setEstadoInicial();
					}

				});

		botonNuevoAdministrador = new ToolStripButton("Nuevo Cliente",
				"icons/16/document_plain_new.png");

		botonNuevoAdministrador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioAdministrador();
					}

				});

		botonEliminarCuenta = new ToolStripButton("Eliminar Cuenta",
				"icons/16/document_plain_new.png");

		botonEliminarCuenta
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						UsuarioAdministradorListGridRecord record = (UsuarioAdministradorListGridRecord) listGridUsuariosAdministradores
								.getSelectedRecord();
						if (record != null) {
							UsuarioBO cliente = UsuarioAdministradorListGridRecord
									.getBO(record);
							Maestro.eliminarCuentaCliente(cliente);
						} else {
							SC.warn("Debe seleccionar la cuenta que desea eliminar.");
						}
					}

				});

		botonDesactivarCuenta = new ToolStripButton("Desactivar Cuenta");
		botonDesactivarCuenta.setVisible(false);
		botonDesactivarCuenta
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						UsuarioAdministradorListGridRecord record = (UsuarioAdministradorListGridRecord) listGridUsuariosAdministradores
								.getSelectedRecord();
						if (record != null) {
							UsuarioBO cliente = UsuarioAdministradorListGridRecord
									.getBO(record);
							if (botonDesactivarCuenta.getTitle()
									.equalsIgnoreCase("Desactivar Cuenta")) {
								Maestro.cambiarEstadoCuentaCliente(cliente,
										false);

							} else if (botonDesactivarCuenta.getTitle()
									.equalsIgnoreCase("Activar Cuenta")) {
								Maestro.cambiarEstadoCuentaCliente(cliente,
										true);
							}
						} else {
							SC.warn("Debe seleccionar la cuenta que desea desactivar.");
						}
					}

				});

		ToolStrip menuBarUsuarioAdministrador = new ToolStrip();
		menuBarUsuarioAdministrador.setWidth100();
		menuBarUsuarioAdministrador.addButton(botonNuevoAdministrador);
		menuBarUsuarioAdministrador.addButton(botonRegresar);
		menuBarUsuarioAdministrador.addFill();
		menuBarUsuarioAdministrador.addSeparator();
		menuBarUsuarioAdministrador.addButton(botonEliminarCuenta);
		menuBarUsuarioAdministrador.addButton(botonDesactivarCuenta);
		menuBarUsuarioAdministrador.addSeparator();

		searchItem = new TextItem("description", "Buscar Cliente");
		searchItem.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if ("enter".equalsIgnoreCase(event.getKeyName())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarClientesClave(keyword);
					} else {
						Maestro.setListaClientes();
					}
				}
			}
		});
		final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
		final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
		searchItem.setIcons(findIcon, cancelIcon);

		searchItem.addIconClickHandler(new IconClickHandler() {
			public void onIconClick(IconClickEvent event) {
				FormItemIcon icon = event.getIcon();
				if (icon.getSrc().equals(cancelIcon.getSrc())) {
					searchItem.setValue("");
					Maestro.setListaClientes();
				} else if (icon.getSrc().equals(findIcon.getSrc())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarClientesClave(keyword);
					} else {
						Maestro.setListaClientes();
					}
				}
			}
		});

		searchItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String valor = searchItem.getValueAsString();
				if (valor == "" || valor == null) {
					Maestro.setListaClientes();
				}
			}
		});

		formBusqueda = new DynamicForm();
		formBusqueda.setWidth100();
		formBusqueda.setPadding(5);
		formBusqueda.setLayoutAlign(VerticalAlignment.BOTTOM);
		formBusqueda.setFields(searchItem);

		VLayout vl2 = new VLayout();
		vl2.setWidth100();
		vl2.setHeight100();

		vl2.addMember(formBusqueda);
		vl2.addMember(listGridUsuariosAdministradores);
		vl2.addMember(panelClienteEspecifico);
		vl2.addMember(menuBarUsuarioAdministrador);

		addMember(vl2);

	}

	public void actualizarUsuariosAdministradores(
			UsuarioAdministradorListGridRecord[] records) {
		listGridUsuariosAdministradores.setData(records);
		botonDesactivarCuenta.setVisible(false);

	}

	private void mostrarDialogoCrearUsuarioAdministrador() {

		windowCrearLamina = new Window();

		windowCrearLamina.setWidth("80%");
		windowCrearLamina.setHeight("80%");
		windowCrearLamina.setTitle("Crear un Cliente");
		windowCrearLamina.setShowMinimizeButton(false);
		windowCrearLamina.setIsModal(true);
		windowCrearLamina.setShowModalMask(true);
		windowCrearLamina.centerInPage();
		windowCrearLamina.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				windowCrearLamina.destroy();
			}
		});
		panelAgregarCliente = new PanelAgregarCliente(this, listaPruebas);
		windowCrearLamina.addItem(panelAgregarCliente);

		windowCrearLamina.show();

	}

	public void agregarUsuarioAdministrador(UsuarioBO usuario, UsoBO usos,
			List<PruebaUsuarioBO> pruebas) {
		Maestro.agregarCliente(usuario, usos, pruebas);
		windowCrearLamina.destroy();
		dlgNotificaciones = new DialogoProcesamiento("Creando el Cliente...");
		dlgNotificaciones.show();
	}

	public void actualizarPruebas(List<PruebaUsuarioBO> result) {
		listaPruebas = result;
	}

	public void setEstadoInicial() {
		listGridUsuariosAdministradores.setVisible(true);
		panelClienteEspecifico.setVisible(false);
		formBusqueda.setVisible(true);
		botonNuevoAdministrador.setVisible(true);
		botonRegresar.setVisible(false);
	}

	public void actualizarUsosCliente(List<UsoBO> result) {
		panelClienteEspecifico.actualizarUsosCliente(result);

	}

	public void actualizarPruebasCliente(List<PruebaUsuarioBO> result) {
		panelClienteEspecifico.actualizarPruebasCliente(result);

	}

	public void desactivarDialogoCreacionClientes() {
		dlgNotificaciones.destroy();
	}
}
