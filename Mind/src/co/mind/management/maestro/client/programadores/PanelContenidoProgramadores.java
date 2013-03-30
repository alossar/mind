package co.mind.management.maestro.client.programadores;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;

import com.smartgwt.client.types.SelectionAppearance;
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

public class PanelContenidoProgramadores extends HLayout {

	private ListGrid listGridUsuariosProgramadores;
	private TextItem textNombreUsuarioProgramador;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textEdadUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;

	public PanelContenidoProgramadores() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		listGridUsuariosProgramadores = new ListGrid();
		listGridUsuariosProgramadores.setWidth100();
		listGridUsuariosProgramadores.setHeight100();
		listGridUsuariosProgramadores.setShowAllRecords(true);

		ListGridField idUsuarioField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreUsuarioField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosUsuarioField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoUsuarioField = new ListGridField("correo", "Correo");
		listGridUsuariosProgramadores.setFields(idUsuarioField,
				nombreUsuarioField, apellidosUsuarioField, correoUsuarioField);
		listGridUsuariosProgramadores.setCanResizeFields(true);
		listGridUsuariosProgramadores.setAutoFetchData(true);
		listGridUsuariosProgramadores.setShowFilterEditor(true);
		listGridUsuariosProgramadores
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		ToolStripButton botonNuevoProgramador = new ToolStripButton(
				"Nuevo Usuario Programador", "icons/16/document_plain_new.png");

		botonNuevoProgramador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioProgramador();
					}

				});

		ToolStrip menuBarUsuarioProgramador = new ToolStrip();
		menuBarUsuarioProgramador.setWidth100();
		menuBarUsuarioProgramador.addButton(botonNuevoProgramador);
		menuBarUsuarioProgramador.addFill();
		menuBarUsuarioProgramador.addSeparator();
		menuBarUsuarioProgramador.addSeparator();

		VLayout vl2 = new VLayout();
		vl2.setWidth100();
		vl2.setHeight100();

		vl2.addMember(listGridUsuariosProgramadores);
		vl2.addMember(menuBarUsuarioProgramador);

		addMember(vl2);

	}

	public void actualizarUsuariosProgramadores(
			UsuarioAdministradorListGridRecord[] records) {
		listGridUsuariosProgramadores.setData(records);

	}

	private void mostrarDialogoCrearUsuarioProgramador() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(180);
		winModal.setTitle("Crear un Usuario Programador");
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

		textNombreUsuarioProgramador = new TextItem();
		textNombreUsuarioProgramador.setTitle("Nombre");
		textNombreUsuarioProgramador.setRequired(true);

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
					agregarUsuarioProgramador();
					winModal.destroy();
				}
			}
		});

		formUsuarioBasico.setFields(textIdentificadorUsuarioBasico,
				textNombreUsuarioProgramador, textApellidosUsuarioBasico,
				textEdadUsuarioBasico, textCorreoUsuarioBasico, boton);

		winModal.addItem(formUsuarioBasico);

		winModal.show();
	}

	private void agregarUsuarioProgramador() {
		String nombreUsuario = textNombreUsuarioProgramador.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();

		Maestro.agregarUsuarioBasico(idUsuario, nombreUsuario,
				apellidosUsuario, correoUsuario);
	}
}
