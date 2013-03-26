package co.mind.management.maestro.client;

import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;

import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
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
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelProgramadores extends HLayout {

	private ListGrid listGridUsuariosBasicos;
	private ListGrid listGridUsuariosAdministradores;
	private TextItem textNombreUsuarioAdministrador;
	private TextItem textNombreUsuarioBasico;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textEdadUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;

	public PanelProgramadores() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setTitle("Repositorio");
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		Tab tabUsuariosBasicos = new Tab("Usuarios B\u00E1sicos",
				"pieces/16/pawn_blue.png");

		listGridUsuariosBasicos = new ListGrid();
		listGridUsuariosBasicos.setWidth100();
		listGridUsuariosBasicos.setHeight100();
		listGridUsuariosBasicos.setShowAllRecords(true);
		listGridUsuariosBasicos.setSelectionType(SelectionStyle.SIMPLE);

		ListGridField idField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoField = new ListGridField("correo", "Correo");
		listGridUsuariosBasicos.setFields(idField, nombreField, apellidosField,
				correoField);
		listGridUsuariosBasicos.setCanResizeFields(true);

		ToolStripButton botonNuevoBasico = new ToolStripButton(
				"Nuevo Usuario B\u00E1sico", "icons/16/document_plain_new.png");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioBasico();
					}
				});

		ToolStripButton botonEliminarBasico = new ToolStripButton(
				"Eliminar Usuarios", "icons/16/document_plain_new.png");

		botonEliminarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						ListGridRecord[] records = listGridUsuariosBasicos
								.getSelectedRecords();
						if (records.length > 0) {
							UsuarioBasicoListGridRecord[] recordsusuarios = new UsuarioBasicoListGridRecord[records.length];
							for (int i = 0; i < records.length; i++) {
								recordsusuarios[i] = (UsuarioBasicoListGridRecord) records[i];
							}
							Maestro.eliminarUsuariosBasicos(UsuarioBasicoListGridRecord
									.getBO(recordsusuarios));
						} else {
							SC.say("Seleccione los usuarios a eliminar.");
						}
					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonNuevoBasico);
		menuBarUsuarioBasico.addButton(botonEliminarBasico);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addSeparator();

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(listGridUsuariosBasicos);
		vl1.addMember(menuBarUsuarioBasico);

		tabUsuariosBasicos.setPane(vl1);

		Tab tabUsuariosAdministradores = new Tab("Usuarios Administradores",
				"pieces/16/pawn_green.png");
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

		tabUsuariosAdministradores.setPane(vl2);
		topTabSet.addTab(tabUsuariosBasicos);
		topTabSet.addTab(tabUsuariosAdministradores);

		addMember(topTabSet);

	}

	private void InicializarTabUsuariosBasicos() {

	}

	private void inicilizarTabUsuariosAdministradores() {

	}

	public void actualizarUsuariosBasicos(UsuarioBasicoListGridRecord[] records) {
		listGridUsuariosBasicos.setData(records);

	}

	public void actualizarUsuariosAdministradores(
			UsuarioAdministradorListGridRecord[] records) {
		listGridUsuariosAdministradores.setData(records);

	}

	private void mostrarDialogoCrearUsuarioBasico() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(180);
		winModal.setTitle("Crear un Usuario B\u00E1sico");
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

		textNombreUsuarioBasico = new TextItem();
		textNombreUsuarioBasico.setTitle("Nombre");
		textNombreUsuarioBasico.setRequired(true);

		textApellidosUsuarioBasico = new TextItem();
		textApellidosUsuarioBasico.setRequired(true);
		textApellidosUsuarioBasico.setTitle("Apellidos");

		textEdadUsuarioBasico = new IntegerItem();
		textEdadUsuarioBasico.setRequired(true);
		textEdadUsuarioBasico.setTitle("Edad");

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator
				.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");

		textCorreoUsuarioBasico = new TextItem();
		textCorreoUsuarioBasico.setRequired(true);
		textCorreoUsuarioBasico.setTitle("Correo");
		textCorreoUsuarioBasico.setValidators(regExpValidator);

		ButtonItem boton = new ButtonItem("Crear");
		boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {

				if (formUsuarioBasico.validate()) {
					agregarUsuarioBasico();
					winModal.destroy();
				}
			}
		});

		formUsuarioBasico.setFields(textIdentificadorUsuarioBasico,
				textNombreUsuarioBasico, textApellidosUsuarioBasico,
				textEdadUsuarioBasico, textCorreoUsuarioBasico, boton);

		winModal.addItem(formUsuarioBasico);

		winModal.show();
	}

	private void mostrarDialogoCrearUsuarioAdministrador() {
		// TODO Auto-generated method stub

	}

	private void agregarUsuarioBasico() {
		String nombreUsuario = textNombreUsuarioBasico.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		int edadUsuario = textEdadUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();

		Maestro.agregarUsuarioBasico(idUsuario, nombreUsuario,
				apellidosUsuario, correoUsuario, edadUsuario);
	}
}
