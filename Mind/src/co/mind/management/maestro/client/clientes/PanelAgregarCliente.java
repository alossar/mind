package co.mind.management.maestro.client.clientes;

import java.util.Date;
import java.util.List;

import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioAdministradorBO;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VisibilityMode;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.SectionStack;
import com.smartgwt.client.widgets.layout.SectionStackSection;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelAgregarCliente extends HLayout {

	private VLayout panelInformacionProceso;
	private HLayout panelListados;
	private ListGrid listGridPruebasDeProceso;
	private IButton botonAgregar;
	private PanelEncabezadoDialogo panelEncabezadoProceso;
	private IntegerItem textIdentificadorUsuarioBasico;
	private TextItem textNombreUsuarioBasico;
	private TextItem textApellidosUsuarioBasico;
	private TextItem textCorreoUsuarioBasico;
	private TextItem textCiudad;
	private IntegerItem textTelefono;
	private IntegerItem textCelular;
	private TextItem textEmpresa;
	private TextItem textCargo;
	private PanelContenidoClientes panelClientes;
	private IntegerItem textUsos;
	private CheckboxItem checkBoxHabilitarFechaFinalizacion;
	private DateItem dateTimeItemFechaFinalizacionNuevo;

	public PanelAgregarCliente(PanelContenidoClientes panel,
			List<PruebaUsuarioBO> listaPruebas) {
		this.panelClientes = panel;
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("transparent");
		setPadding(15);

		panelInformacionProceso = new VLayout();
		panelInformacionProceso.setHeight100();
		panelInformacionProceso.setWidth(200);

		final DynamicForm formInformacion = new DynamicForm();
		formInformacion.setWidth100();
		formInformacion.setPadding(5);
		formInformacion.setGroupTitle("Información Personal");
		formInformacion.setIsGroup(true);

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

		textCiudad = new TextItem();
		textCiudad.setRequired(false);
		textCiudad.setTitle("Ciudad");
		textCiudad.setLength(Convencion.MAXIMA_LONGITUD_CIUDAD);

		textEmpresa = new TextItem();
		textEmpresa.setTitle("Empresa");
		textEmpresa.setRequired(false);
		textEmpresa.setLength(Convencion.MAXIMA_LONGITUD_EMPRESA);

		textCargo = new TextItem();
		textCargo.setTitle("Cargo");
		textCargo.setRequired(false);
		textCargo.setLength(Convencion.MAXIMA_LONGITUD_CARGO);

		textTelefono = new IntegerItem();
		textTelefono.setRequired(true);
		textTelefono.setTitle("Teléfono");
		textTelefono.setAllowExpressions(false);
		textTelefono.setLength(10);

		textCelular = new IntegerItem();
		textCelular.setRequired(false);
		textCelular.setTitle("Celular");
		textCelular.setAllowExpressions(false);
		textCelular.setLength(10);

		textUsos = new IntegerItem();
		textUsos.setRequired(true);
		textUsos.setTitle("Usos");
		textUsos.setAllowExpressions(false);
		textUsos.setLength(10);

		dateTimeItemFechaFinalizacionNuevo = new DateItem();
		dateTimeItemFechaFinalizacionNuevo.setUseTextField(false);
		dateTimeItemFechaFinalizacionNuevo.setTitle("Fecha Vencimiento");
		dateTimeItemFechaFinalizacionNuevo.setStartDate(new Date());

		checkBoxHabilitarFechaFinalizacion = new CheckboxItem();
		checkBoxHabilitarFechaFinalizacion.setName("onOrder");
		checkBoxHabilitarFechaFinalizacion
				.setTitle("Habilitar fecha de Vencimiento");
		checkBoxHabilitarFechaFinalizacion.setRedrawOnChange(true);
		checkBoxHabilitarFechaFinalizacion.setValue(false);

		dateTimeItemFechaFinalizacionNuevo
				.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value,
							DynamicForm form) {
						return (Boolean) form.getValue("onOrder");
					}
				});

		formInformacion.setFields(textIdentificadorUsuarioBasico,
				textNombreUsuarioBasico, textApellidosUsuarioBasico,
				textCorreoUsuarioBasico, textCiudad, textEmpresa, textCargo,
				textTelefono, textCelular, textUsos,
				checkBoxHabilitarFechaFinalizacion,
				dateTimeItemFechaFinalizacionNuevo);

		VLayout v2 = new VLayout();
		v2.setWidth(200);
		v2.setHeight100();
		v2.setBackgroundColor("white");
		v2.addChild(formInformacion);

		panelEncabezadoProceso = new PanelEncabezadoDialogo("Agregar Cliente",
				"Informaci\u00F3n del Cliente.", "img/admin/bot6.png");
		panelEncabezadoProceso.setSize("100%", "70px");

		panelInformacionProceso.addMember(panelEncabezadoProceso);
		panelInformacionProceso.addMember(v2);

		panelListados = new HLayout();
		panelListados.setHeight100();
		panelListados.setWidth("70%");

		listGridPruebasDeProceso = new ListGrid();
		listGridPruebasDeProceso.setWidth100();
		listGridPruebasDeProceso.setHeight100();
		listGridPruebasDeProceso.setShowAllRecords(true);
		listGridPruebasDeProceso.setSelectionType(SelectionStyle.SIMPLE);
		listGridPruebasDeProceso.setEmptyMessage("No se encuentran pruebas.");

		ListGridField nombrePruebaField = new ListGridField("nombre", "Nombre");
		ListGridField apellidosField = new ListGridField("descripcion",
				"Descripci\u00F3n");
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		ListGridField tiempoField = new ListGridField("tiempoPrueba",
				"Tiempo (Segundos)");
		listGridPruebasDeProceso.setFields(nombrePruebaField, apellidosField,
				preguntasField, tiempoField);
		listGridPruebasDeProceso.setCanResizeFields(true);
		listGridPruebasDeProceso
				.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		listGridPruebasDeProceso.setData(PruebaListGridRecord
				.getRecords(listaPruebas));

		botonAgregar = new IButton("Agregar Cliente");

		botonAgregar.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				ListGridRecord[] records = listGridPruebasDeProceso
						.getSelectedRecords();

				if (formInformacion.validate()) {

					UsuarioAdministradorBO usuario = new UsuarioAdministradorBO();
					usuario.setApellidos(textApellidosUsuarioBasico
							.getValueAsString());
					usuario.setCargo(textCargo.getValueAsString());
					usuario.setCiudad(textCiudad.getValueAsString());
					usuario.setCorreo_Electronico(textCorreoUsuarioBasico
							.getValueAsString());
					usuario.setEmpresa(textEmpresa.getValueAsString());
					usuario.setEstado_Cuenta(Convencion.ESTADO_CUENTA_ACTIVA);
					usuario.setFecha_Creacion(new Date());
					usuario.setIdentificador(textIdentificadorUsuarioBasico
							.getValueAsInteger());
					usuario.setNombres(textNombreUsuarioBasico
							.getValueAsString());
					usuario.setTelefono(textTelefono.getValueAsString());
					usuario.setTelefono_Celular(textCelular.getValueAsString());
					PruebaListGridRecord[] recordsusuarios = null;
					if (records.length > 0) {
						recordsusuarios = new PruebaListGridRecord[records.length];
						for (int i = 0; i < records.length; i++) {
							recordsusuarios[i] = (PruebaListGridRecord) records[i];
						}
					}
					UsoBO usos = new UsoBO();
					usos.setUsosAsignados(textUsos.getValueAsInteger());
					usos.setFechaAsignacion(new Date());
					if (checkBoxHabilitarFechaFinalizacion.getValueAsBoolean()) {
						usuario.setFechaVencimiento(new Date(
								dateTimeItemFechaFinalizacionNuevo
										.getValueAsDate().getTime() + 3600 * 1000 * 12));
					}
					panelClientes.agregarUsuarioAdministrador(usuario, usos,
							PruebaListGridRecord.getBO(recordsusuarios));
				} else {
					SC.warn("Debe completar los campos requeridos.");
				}
			}
		});

		panelInformacionProceso.addMember(botonAgregar);

		SectionStack sectionStack = new SectionStack();
		sectionStack.setWidth100();
		sectionStack.setVertical(true);
		sectionStack.setHeight100();
		sectionStack.setVisibilityMode(VisibilityMode.MULTIPLE);
		sectionStack.setAnimateSections(true);
		sectionStack.setOverflow(Overflow.HIDDEN);

		SectionStackSection summarySection = new SectionStackSection();
		summarySection.setTitle("Pruebas");
		summarySection.setExpanded(true);
		summarySection.setItems(listGridPruebasDeProceso);

		sectionStack.setSections(summarySection);

		panelListados.addMember(sectionStack);
		addMember(panelInformacionProceso);
		addMember(panelListados);

	}
}
