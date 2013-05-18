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
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.ImgButton;
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
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelContenidoProgramadores extends HLayout {

	private ListGrid listGridUsuariosProgramadores;
	private TextItem textNombreUsuarioProgramador;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;
	private IntegerItem textTelefono;
	private ImgButton botonRegresar;
	private ImgButton botonNuevoProgramador;
	private ImgButton botonEliminarProgramador;
	private PanelProgramadorEspecifico panelProgramadorEspecifico;
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
		listGridUsuariosProgramadores
				.setEmptyMessage("No se encuentran usuarios programadores en su cuenta.");

		ListGridField idUsuarioField = new ListGridField("id", "C\u00E9dula");
		ListGridField nombreUsuarioField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosUsuarioField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoUsuarioField = new ListGridField("correo", "Correo");
		listGridUsuariosProgramadores.setFields(idUsuarioField,
				nombreUsuarioField, apellidosUsuarioField, correoUsuarioField);
		listGridUsuariosProgramadores.setCanResizeFields(true);

		listGridUsuariosProgramadores
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						ProgramadorListGridRecord record = (ProgramadorListGridRecord) event
								.getRecord();
						if (record != null) {
							UsuarioBO programador = ProgramadorListGridRecord
									.getBO(record);
							panelProgramadorEspecifico
									.actualizarDatosProgramador(programador);
							Maestro.obtenerProcesosProgramador(programador);
							botonRegresar.setDisabled(false);
							botonNuevoProgramador.setDisabled(true);
							botonEliminarProgramador.setDisabled(true);
							listGridUsuariosProgramadores.setVisible(false);
							panelProgramadorEspecifico.setVisible(true);
						}
					}
				});

		listGridUsuariosProgramadores.setGenerateDoubleClickOnEnter(true);

		panelProgramadorEspecifico = new PanelProgramadorEspecifico();
		panelProgramadorEspecifico.setHeight100();
		panelProgramadorEspecifico.setWidth100();
		panelProgramadorEspecifico.setVisible(false);

		botonNuevoProgramador = new ImgButton();
		botonNuevoProgramador.setWidth(35);
		botonNuevoProgramador.setHeight(35);
		botonNuevoProgramador.setShowRollOver(true);
		botonNuevoProgramador.setShowDown(true);
		botonNuevoProgramador.setSrc("icons/agregar.png");
		botonNuevoProgramador.setDisabled(false);
		botonNuevoProgramador.setTooltip("Nuevo programador");

		botonNuevoProgramador
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioProgramador();
					}

				});

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

		botonEliminarProgramador = new ImgButton();
		botonEliminarProgramador.setWidth(35);
		botonEliminarProgramador.setHeight(35);
		botonEliminarProgramador.setShowRollOver(true);
		botonEliminarProgramador.setShowDown(true);
		botonEliminarProgramador.setSrc("icons/eliminar.png");
		botonEliminarProgramador.setDisabled(false);
		botonEliminarProgramador.setTooltip("Eliminar programador");

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

		HLayout hl1 = new HLayout();
		hl1.setWidth100();
		hl1.setHeight("40px");

		HLayout hlRelleno = new HLayout();
		hlRelleno.setWidth("*");
		hlRelleno.setHeight("1px");

		hl1.addMember(hlRelleno);
		hl1.addMember(botonRegresar);
		hl1.addMember(botonNuevoProgramador);
		hl1.addMember(botonEliminarProgramador);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(hl1);
		vl1.addMember(listGridUsuariosProgramadores);
		vl1.addMember(panelProgramadorEspecifico);

		addMember(vl1);

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
		formUsuarioBasico.setLayoutAlign(VerticalAlignment.CENTER);

		textIdentificadorUsuarioBasico = new IntegerItem();
		textIdentificadorUsuarioBasico.setRequired(true);
		textIdentificadorUsuarioBasico.setTitle("C\u00E9dula");
		textIdentificadorUsuarioBasico.setAllowExpressions(false);
		textIdentificadorUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_CEDULA);

		textNombreUsuarioProgramador = new TextItem();
		textNombreUsuarioProgramador.setTitle("Nombre");
		textNombreUsuarioProgramador.setRequired(true);
		textNombreUsuarioProgramador
				.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

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

		dlgNotificaciones = new DialogoProcesamiento(
				"Creando el Programador...");
		dlgNotificaciones.show();
	}

	public void setEstadoInicial() {
		botonRegresar.setDisabled(true);
		botonNuevoProgramador.setDisabled(false);
		botonEliminarProgramador.setDisabled(false);

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
