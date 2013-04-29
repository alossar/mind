package co.mind.management.maestro.client.programadores;

import java.util.Date;
import java.util.List;

import co.mind.management.maestro.client.DialogoProcesamiento;
import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.dto.UsuarioProgramadorBO;
import co.mind.management.shared.records.ProgramadorListGridRecord;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
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
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelContenidoProgramadores extends HLayout {

	private ListGrid listGridUsuariosProgramadores;
	private TextItem textNombreUsuarioProgramador;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;
	private IntegerItem textTelefono;
	private ToolStripButton botonRegresar;
	private PanelProgramadorEspecifico panelProgramadorEspecifico;
	private ToolStripButton botonNuevoProgramador;
	private ToolStripButton botonEliminarProgramador;
	private DialogoProcesamiento dlgNotificaciones;

	public PanelContenidoProgramadores() {
		setWidth("90%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		listGridUsuariosProgramadores = new ListGrid();
		listGridUsuariosProgramadores.setWidth100();
		listGridUsuariosProgramadores.setHeight100();
		listGridUsuariosProgramadores.setShowAllRecords(true);
		listGridUsuariosProgramadores.setEmptyMessage("No se encuentran usuarios programadores en su cuenta.");

		ListGridField idUsuarioField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreUsuarioField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosUsuarioField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoUsuarioField = new ListGridField("correo", "Correo");
		listGridUsuariosProgramadores.setFields(idUsuarioField,
				nombreUsuarioField, apellidosUsuarioField, correoUsuarioField);
		listGridUsuariosProgramadores.setCanResizeFields(true);

		listGridUsuariosProgramadores
				.addCellDoubleClickHandler(new CellDoubleClickHandler() {
					@Override
					public void onCellDoubleClick(CellDoubleClickEvent event) {
						ProgramadorListGridRecord record = (ProgramadorListGridRecord) event
								.getRecord();
						if (record != null) {
							UsuarioBO programador = ProgramadorListGridRecord
									.getBO(record);
							panelProgramadorEspecifico
									.actualizarDatosProgramador(programador);
							Maestro.obtenerProcesosProgramador(programador);
							botonRegresar.setVisible(true);
							botonNuevoProgramador.setVisible(false);
							botonEliminarProgramador.setVisible(false);
							listGridUsuariosProgramadores.setVisible(false);
							panelProgramadorEspecifico.setVisible(true);
						}
					}
				});

		panelProgramadorEspecifico = new PanelProgramadorEspecifico();
		panelProgramadorEspecifico.setHeight100();
		panelProgramadorEspecifico.setWidth100();
		panelProgramadorEspecifico.setVisible(false);

		botonNuevoProgramador = new ToolStripButton("Nuevo Programador",
				"icons/16/document_plain_new.png");

		botonNuevoProgramador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioProgramador();
					}

				});
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

		botonEliminarProgramador = new ToolStripButton("Eliminar Programador",
				"icons/16/document_plain_new.png");

		botonEliminarProgramador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						final ProgramadorListGridRecord record = (ProgramadorListGridRecord) listGridUsuariosProgramadores
								.getSelectedRecord();

						if (record != null) {
							SC.ask("Puede perder procesos importantes. ¿Desea Eliminar el usuario?",
									new BooleanCallback() {

										@Override
										public void execute(Boolean value) {
											if (value) {
												UsuarioBO programador = ProgramadorListGridRecord
														.getBO(record);
												Maestro.eliminarProgramador(programador);
											}
										}
									});

						} else {
							SC.warn("Debe seleccionar el programador que desea eliminar.");
						}
					}

				});

		ToolStrip menuBarUsuarioProgramador = new ToolStrip();
		menuBarUsuarioProgramador.setWidth100();
		menuBarUsuarioProgramador.addButton(botonNuevoProgramador);
		menuBarUsuarioProgramador.addButton(botonRegresar);
		menuBarUsuarioProgramador.addFill();
		menuBarUsuarioProgramador.addSeparator();
		menuBarUsuarioProgramador.addButton(botonEliminarProgramador);
		menuBarUsuarioProgramador.addSeparator();

		VLayout vl2 = new VLayout();
		vl2.setWidth100();
		vl2.setHeight100();

		vl2.addMember(listGridUsuariosProgramadores);
		vl2.addMember(panelProgramadorEspecifico);
		vl2.addMember(menuBarUsuarioProgramador);

		addMember(vl2);

	}

	public void actualizarUsuariosProgramadores(
			List<UsuarioProgramadorBO> records) {
		listGridUsuariosProgramadores.setData(ProgramadorListGridRecord
				.getRecords(records));

	}

	private void mostrarDialogoCrearUsuarioProgramador() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(280);
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

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
				"Crear Programador",
				"Cree un programador que administre procesos",
				"img/admin/bot4.png");
		p.setSize("100%", "70px");

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
		textTelefono.setAllowExpressions(false);
		textTelefono.setLength(10);

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
				textCorreoUsuarioBasico, textTelefono, boton);

		winModal.addItem(p);
		winModal.addItem(formUsuarioBasico);

		winModal.show();
	}

	private void agregarUsuarioProgramador() {
		String nombreUsuario = textNombreUsuarioProgramador.getValueAsString();
		int idUsuario = textIdentificadorUsuarioBasico.getValueAsInteger();
		String apellidosUsuario = textApellidosUsuarioBasico.getValueAsString();
		String correoUsuario = textCorreoUsuarioBasico.getValueAsString();
		String telefono = textTelefono.getValueAsString();
		UsuarioProgramadorBO programador = new UsuarioProgramadorBO();
		programador.setApellidos(apellidosUsuario);
		programador.setCorreo_Electronico(correoUsuario);
		programador.setFecha_Creacion(new Date());
		programador.setIdentificador(idUsuario);
		programador.setNombres(nombreUsuario);
		programador.setTelefono(telefono);
		Maestro.agregarProgramador(programador);

		dlgNotificaciones = new DialogoProcesamiento("Creando el Programador...");
		dlgNotificaciones.show();
	}

	public void setEstadoInicial() {
		botonRegresar.setVisible(false);
		botonNuevoProgramador.setVisible(true);
		botonEliminarProgramador.setVisible(true);

		listGridUsuariosProgramadores.setVisible(true);
		panelProgramadorEspecifico.setVisible(false);

	}

	public void actualizarProcesosProgramador(List<ProcesoUsuarioBO> result) {
		panelProgramadorEspecifico.actualizarProcesosProgramador(result);
	}

	public void desactivarDialogoCreacionClientes() {
		dlgNotificaciones.destroy();
	}
}
