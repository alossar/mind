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
import com.smartgwt.client.widgets.ImgButton;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowMouseDownEvent;
import com.smartgwt.client.widgets.grid.events.RowMouseDownHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelContenidoClientes extends HLayout {

	private ListGrid listGridUsuariosAdministradores;
	private PanelClienteEspecifico panelClienteEspecifico;
	private Window windowCrearLamina;
	private PanelAgregarCliente panelAgregarCliente;
	private List<PruebaUsuarioBO> listaPruebas;
	private TextItem searchItem;
	private DynamicForm formBusqueda;
	private ImgButton botonRegresar;
	private ImgButton botonNuevoAdministrador;
	private ImgButton botonEliminarCuenta;
	private ImgButton botonDesactivarCuenta;
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
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						UsuarioAdministradorListGridRecord record = (UsuarioAdministradorListGridRecord) event
								.getRecord();
						if (record != null) {
							UsuarioBO cliente = UsuarioAdministradorListGridRecord
									.getBO(record);
							panelClienteEspecifico.actualizarDatosCliente(
									cliente, listaPruebas);
							Maestro.obtenerUsosCliente(cliente);
							Maestro.obtenerPruebasCliente(cliente);

							listGridUsuariosAdministradores.setVisible(false);
							panelClienteEspecifico.setVisible(true);
							formBusqueda.setVisible(false);

							botonNuevoAdministrador.setVisible(false);
							botonRegresar.setDisabled(false);
							botonDesactivarCuenta.setVisible(false);
							botonEliminarCuenta.setVisible(false);
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

		listGridUsuariosAdministradores.setGenerateDoubleClickOnEnter(true);

		panelClienteEspecifico = new PanelClienteEspecifico();
		panelClienteEspecifico.setHeight100();
		panelClienteEspecifico.setWidth100();
		panelClienteEspecifico.setVisible(false);

		botonRegresar = new ImgButton();
		botonRegresar.setWidth(35);
		botonRegresar.setHeight(35);
		botonRegresar.setShowRollOver(true);
		botonRegresar.setShowDown(true);
		botonRegresar.setSrc("icons/atras.png");
		botonRegresar.setDisabled(true);

		botonRegresar
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						setEstadoInicial();
					}

				});

		botonNuevoAdministrador = new ImgButton();
		botonNuevoAdministrador.setWidth(35);
		botonNuevoAdministrador.setHeight(35);
		botonNuevoAdministrador.setShowRollOver(true);
		botonNuevoAdministrador.setShowDown(true);
		botonNuevoAdministrador.setSrc("icons/agregar.png");
		botonNuevoAdministrador.setDisabled(false);
		botonNuevoAdministrador.setTooltip("Nuevo cliente");

		botonNuevoAdministrador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioAdministrador();
					}

				});

		botonEliminarCuenta = new ImgButton();
		botonEliminarCuenta.setWidth(35);
		botonEliminarCuenta.setHeight(35);
		botonEliminarCuenta.setShowRollOver(true);
		botonEliminarCuenta.setShowDown(true);
		botonEliminarCuenta.setSrc("icons/eliminar.png");
		botonEliminarCuenta.setDisabled(false);
		botonEliminarCuenta.setTooltip("Eliminar cuenta");

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

		botonDesactivarCuenta = new ImgButton();
		botonDesactivarCuenta.setWidth(35);
		botonDesactivarCuenta.setHeight(35);
		botonDesactivarCuenta.setShowRollOver(true);
		botonDesactivarCuenta.setShowDown(true);
		botonDesactivarCuenta.setSrc("icons/eliminar.png");
		botonDesactivarCuenta.setDisabled(false);
		botonDesactivarCuenta.setTooltip("Desactivar cuenta");
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
		formBusqueda.setWidth("250px");
		formBusqueda.setHeight("33px");
		formBusqueda.setPadding(5);
		formBusqueda.setLayoutAlign(VerticalAlignment.CENTER);
		formBusqueda.setFields(searchItem);

		HLayout hl1 = new HLayout();
		hl1.setWidth100();
		hl1.setHeight("40px");

		HLayout hlRelleno = new HLayout();
		hlRelleno.setWidth("*");
		hlRelleno.setHeight("1px");

		hl1.addMember(hlRelleno);
		hl1.addMember(formBusqueda);
		hl1.addMember(botonRegresar);
		hl1.addMember(botonNuevoAdministrador);
		hl1.addMember(botonDesactivarCuenta);
		hl1.addMember(botonEliminarCuenta);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(hl1);
		vl1.addMember(listGridUsuariosAdministradores);
		vl1.addMember(panelClienteEspecifico);

		addMember(vl1);

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
		dlgNotificaciones = new DialogoProcesamiento("Creando el cliente...");
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
		botonDesactivarCuenta.setVisible(true);
		botonEliminarCuenta.setVisible(true);
		botonRegresar.setDisabled(true);
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
