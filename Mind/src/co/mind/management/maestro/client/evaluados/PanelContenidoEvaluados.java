package co.mind.management.maestro.client.evaluados;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;

import com.smartgwt.client.types.SelectionAppearance;
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

public class PanelContenidoEvaluados extends HLayout {

	private ListGrid listGridUsuariosBasicos;
	private TextItem textNombreUsuarioBasico;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;

	public PanelContenidoEvaluados() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("white");
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
		listGridUsuariosBasicos
				.setEmptyMessage("No hay evaluados en su cuenta.");

		ListGridField idField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoField = new ListGridField("correo", "Correo");
		listGridUsuariosBasicos.setFields(idField, nombreField, apellidosField,
				correoField);
		listGridUsuariosBasicos.setCanResizeFields(true);
		listGridUsuariosBasicos.setAutoFetchData(true);
		listGridUsuariosBasicos.setShowFilterEditor(true);
		listGridUsuariosBasicos
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);

		ToolStripButton botonNuevoBasico = new ToolStripButton(
				"Nuevo Evaluado", "icons/16/document_plain_new.png");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioBasico();
					}
				});

		ToolStripButton botonEliminarBasico = new ToolStripButton(
				"Eliminar Evaluados", "icons/16/document_plain_new.png");

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
							SC.warn("Seleccione los evaluados a eliminar.");
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

		addMember(vl1);

	}

	public void actualizarUsuariosBasicos(List<EvaluadoBO> result) {
		listGridUsuariosBasicos.setData(UsuarioBasicoListGridRecord
				.getRecords(result));
	}

	private void mostrarDialogoCrearUsuarioBasico() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(180);
		winModal.setTitle("Crear un Evaluado");
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
				textCorreoUsuarioBasico, boton);

		winModal.addItem(formUsuarioBasico);

		winModal.show();
	}

	private void agregarUsuarioBasico() {
		String nombreUsuario = textNombreUsuarioBasico.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();

		Maestro.agregarUsuarioBasico(idUsuario, nombreUsuario,
				apellidosUsuario, correoUsuario);
	}
}
