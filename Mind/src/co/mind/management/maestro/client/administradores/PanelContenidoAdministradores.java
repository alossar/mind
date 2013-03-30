package co.mind.management.maestro.client.administradores;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelContenidoAdministradores extends HLayout {

	private ListGrid listGridUsuariosAdministradores;
	private TextItem textNombreUsuarioAdministrador;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textEdadUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;
	private IntegerItem textTelefono;

	public PanelContenidoAdministradores() {
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
				"Nuevo Usuario Administrador",
				"icons/16/document_plain_new.png");

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
		final Window winModal = new Window();
		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Crear Una Cuenta", "Cree una cuenta de Usuario",
				"img/check.png");
		p.setSize("100%", "70px");
		winModal.setWidth(350);
		winModal.setHeight(180);
		winModal.setTitle("Crear una Cuenta");
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

		formUsuarioBasico = new DynamicForm();
		formUsuarioBasico.setHeight("40%");
		formUsuarioBasico.setWidth100();
		formUsuarioBasico.setPadding(5);
		formUsuarioBasico.setLayoutAlign(VerticalAlignment.BOTTOM);

		textIdentificadorUsuarioBasico = new IntegerItem();
		textIdentificadorUsuarioBasico.setRequired(true);
		textIdentificadorUsuarioBasico.setTitle("C\u00E9dula");
		textIdentificadorUsuarioBasico.setAllowExpressions(false);

		textNombreUsuarioAdministrador = new TextItem();
		textNombreUsuarioAdministrador.setTitle("Nombre");
		textNombreUsuarioAdministrador.setRequired(true);

		textApellidosUsuarioBasico = new TextItem();
		textApellidosUsuarioBasico.setRequired(true);
		textApellidosUsuarioBasico.setTitle("Apellidos");

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator
				.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");

		textCorreoUsuarioBasico = new TextItem();
		textCorreoUsuarioBasico.setRequired(true);
		textCorreoUsuarioBasico.setTitle("Correo");
		textCorreoUsuarioBasico.setValidators(regExpValidator);

		textTelefono = new IntegerItem();
		textTelefono.setRequired(true);
		textTelefono.setTitle("Teléfono");
		textTelefono.setLength(10);
		textTelefono.setAllowExpressions(false);

		ButtonItem boton = new ButtonItem("Crear");
		boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (formUsuarioBasico.validate()) {
					agregarUsuarioAdministrador();
					winModal.destroy();
				}
			}
		});

		formUsuarioBasico.setFields(textIdentificadorUsuarioBasico,
				textNombreUsuarioAdministrador, textApellidosUsuarioBasico,
				textEdadUsuarioBasico, textCorreoUsuarioBasico, boton);

		winModal.addItem(formUsuarioBasico);

		winModal.show();
	}

	private void agregarUsuarioAdministrador() {
		String nombreUsuario = textNombreUsuarioAdministrador
				.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();
		int telefono = textTelefono.getValueAsInteger();
	}
}
