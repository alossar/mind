package co.mind.management.maestro.client.evaluados;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.TabSet;

public class PanelContenidoEvaluados extends HLayout {

	private ListGrid listGridUsuariosBasicos;
	private PanelEvaluadoEspecifico panelEvaluado;
	private TextItem textNombreUsuarioBasico;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private IntegerItem textIdentificadorUsuarioBasico;
	private DynamicForm formUsuarioBasico;
	private TextItem searchItem;
	private ImgButton botonRegresar;
	private ImgButton botonNuevoBasico;
	private DynamicForm formBusqueda;

	public PanelContenidoEvaluados() {
		setWidth("90%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		final TabSet topTabSet = new TabSet();
		topTabSet.setTabBarPosition(Side.TOP);
		topTabSet.setTitle("Repositorio");
		topTabSet.setWidth100();
		topTabSet.setHeight100();

		listGridUsuariosBasicos = new ListGrid();
		listGridUsuariosBasicos.setWidth100();
		listGridUsuariosBasicos.setHeight100();
		listGridUsuariosBasicos.setShowAllRecords(true);
		listGridUsuariosBasicos
				.setEmptyMessage("No hay evaluados en su cuenta.");

		ListGridField idField = new ListGridField("id", "Cédula");
		idField.setCanEdit(false);
		ListGridField nombreField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("apellidos",
				"Apellidos");
		ListGridField correoField = new ListGridField("correo", "Correo");
		listGridUsuariosBasicos.setFields(idField, nombreField, apellidosField,
				correoField);
		listGridUsuariosBasicos.setCanResizeFields(true);
		listGridUsuariosBasicos.setCanEdit(true);

		listGridUsuariosBasicos
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						UsuarioBasicoListGridRecord usuario = (UsuarioBasicoListGridRecord) event
								.getRecord();
						if (usuario != null) {
							EvaluadoBO bo = UsuarioBasicoListGridRecord
									.getBO(usuario);
							panelEvaluado.actualizarDatosEvaluado(bo);

							Maestro.obtenerProcesosEvaluado(bo);

							listGridUsuariosBasicos.setVisible(false);
							panelEvaluado.setVisible(true);
							botonNuevoBasico.setDisabled(true);
							formBusqueda.setVisible(false);

							botonRegresar.setDisabled(false);
						}
					}
				});

		listGridUsuariosBasicos
				.addRowContextClickHandler(new RowContextClickHandler() {
					public void onRowContextClick(RowContextClickEvent event) {
						if (listGridUsuariosBasicos.startEditing(
								event.getRowNum(), event.getColNum(), false)) {
						}
						event.cancel();
					}
				});

		listGridUsuariosBasicos
				.addEditCompleteHandler(new EditCompleteHandler() {

					@Override
					public void onEditComplete(EditCompleteEvent event) {
						String nombre = (String) event.getNewValues().get(
								"nombre");
						String apellidos = (String) event.getNewValues().get(
								"apellidos");

						ListGridRecord[] r = listGridUsuariosBasicos
								.getRecords();
						UsuarioBasicoListGridRecord procesoRecord = (UsuarioBasicoListGridRecord) r[event
								.getRowNum()];
						EvaluadoBO proceso = UsuarioBasicoListGridRecord
								.getBO(procesoRecord);
						if (nombre != null) {
							proceso.setNombres(nombre);
						}
						if (apellidos != null) {
							proceso.setApellidos(apellidos);
						}
						Maestro.editarEvaluado(proceso);
					}
				});

		listGridUsuariosBasicos.setGenerateDoubleClickOnEnter(true);

		panelEvaluado = new PanelEvaluadoEspecifico();
		panelEvaluado.setHeight100();
		panelEvaluado.setWidth100();
		panelEvaluado.setVisible(false);

		botonNuevoBasico = new ImgButton();
		botonNuevoBasico.setWidth(35);
		botonNuevoBasico.setHeight(35);
		botonNuevoBasico.setShowRollOver(true);
		botonNuevoBasico.setShowDown(true);
		botonNuevoBasico.setSrc("icons/agregar.png");
		botonNuevoBasico.setDisabled(false);
		botonNuevoBasico.setTooltip("Nuevo evaluado");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearUsuarioBasico();
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

		searchItem = new TextItem("description", "Buscar Evaluado");
		searchItem.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if ("enter".equalsIgnoreCase(event.getKeyName())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarEvaluadosClave(keyword);
					} else {
						Maestro.setListaUsuariosBasicos();
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
					Maestro.setListaUsuariosBasicos();
				} else if (icon.getSrc().equals(findIcon.getSrc())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarEvaluadosClave(keyword);
					} else {
						Maestro.setListaUsuariosBasicos();
					}
				}
			}
		});

		searchItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String valor = searchItem.getValueAsString();
				if (valor == "" || valor == null) {
					Maestro.setListaUsuariosBasicos();
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
		hl1.addMember(botonNuevoBasico);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(hl1);
		vl1.addMember(listGridUsuariosBasicos);
		vl1.addMember(panelEvaluado);

		addMember(vl1);

	}

	public void actualizarUsuariosBasicos(List<EvaluadoBO> result) {
		listGridUsuariosBasicos.setData(UsuarioBasicoListGridRecord
				.getRecords(result));
	}

	private void mostrarDialogoCrearUsuarioBasico() {
		final Window winModal = new Window();
		winModal.setWidth(350);
		winModal.setHeight(250);
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

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo("Crear Evaluado",
				"Cree un evaluado en su cuenta", "img/admin/bot3.png");
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
		textIdentificadorUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_CEDULA);

		textNombreUsuarioBasico = new TextItem();
		textNombreUsuarioBasico.setTitle("Nombre");
		textNombreUsuarioBasico.setRequired(true);
		textNombreUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

		textApellidosUsuarioBasico = new TextItem();
		textApellidosUsuarioBasico.setRequired(true);
		textApellidosUsuarioBasico.setTitle("Apellidos");
		textApellidosUsuarioBasico
				.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE_USUARIO);

		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator
				.setExpression("^([a-z0-9_.\\-+])+@(([a-z0-9\\-])+\\.)+[a-z0-9]{2,4}$");

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

		winModal.addItem(p);
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

	public void setEstadoInicial() {
		listGridUsuariosBasicos.setVisible(true);
		panelEvaluado.setVisible(false);
		botonNuevoBasico.setDisabled(false);
		botonRegresar.setDisabled(true);
		formBusqueda.setVisible(true);
	}

	public void actualizarParticipacionesEvaluado(
			List<ParticipacionEnProcesoBO> result) {
		panelEvaluado.actualizarParticipacionesEvaluado(result);

	}
}
