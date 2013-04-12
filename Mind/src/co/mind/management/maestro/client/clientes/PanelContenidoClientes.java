package co.mind.management.maestro.client.clientes;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.dto.PermisoBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;

import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelContenidoClientes extends HLayout {

	private ListGrid listGridUsuariosAdministradores;
	private Window windowCrearLamina;
	private PanelAgregarCliente panelAgregarCliente;
	private List<PermisoBO> listaPermisos;
	private List<PruebaUsuarioBO> listaPruebas;

	public PanelContenidoClientes() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		listGridUsuariosAdministradores = new ListGrid();
		listGridUsuariosAdministradores.setWidth100();
		listGridUsuariosAdministradores.setHeight100();
		listGridUsuariosAdministradores.setShowAllRecords(true);

		ListGridField idUsuarioField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreUsuarioField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosUsuarioField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoUsuarioField = new ListGridField("correo", "Correo");
		listGridUsuariosAdministradores.setFields(idUsuarioField,
				nombreUsuarioField, apellidosUsuarioField, correoUsuarioField);
		listGridUsuariosAdministradores.setCanResizeFields(true);
		listGridUsuariosAdministradores.setAutoFetchData(true);
		listGridUsuariosAdministradores.setShowFilterEditor(true);

		ToolStripButton botonNuevoAdministrador = new ToolStripButton(
				"Nuevo Cliente", "icons/16/document_plain_new.png");

		botonNuevoAdministrador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioAdministrador();
					}

				});

		ToolStrip menuBarUsuarioAdministrador = new ToolStrip();
		menuBarUsuarioAdministrador.setWidth100();
		menuBarUsuarioAdministrador.addButton(botonNuevoAdministrador);
		menuBarUsuarioAdministrador.addFill();
		menuBarUsuarioAdministrador.addSeparator();
		menuBarUsuarioAdministrador.addSeparator();

		VLayout vl2 = new VLayout();
		vl2.setWidth100();
		vl2.setHeight100();

		vl2.addMember(listGridUsuariosAdministradores);
		vl2.addMember(menuBarUsuarioAdministrador);

		addMember(vl2);

	}

	public void actualizarUsuariosAdministradores(
			UsuarioAdministradorListGridRecord[] records) {
		listGridUsuariosAdministradores.setData(records);

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
		panelAgregarCliente = new PanelAgregarCliente(this, listaPruebas,
				listaPermisos);
		windowCrearLamina.addItem(panelAgregarCliente);

		windowCrearLamina.show();

	}

	public void agregarUsuarioAdministrador(UsuarioBO usuario,
			List<PruebaUsuarioBO> pruebas) {
		Maestro.agregarCliente(usuario, pruebas);
		windowCrearLamina.destroy();
	}

	public void actualizarPermisos(List<PermisoBO> permisos) {
		listaPermisos = permisos;
	}

	public void actualizarPruebas(List<PruebaUsuarioBO> result) {
		listaPruebas = result;
	}
}
