package co.mind.management.maestro.client.cuenta;

import co.mind.management.shared.dto.UsuarioBO;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelContenidoCuenta extends HLayout {

	private VLayout layoutContenedor;
	private TextItem nombreItem;
	private TextItem apellidosItem;
	private TextItem cedulaItem;
	private TextItem mailItem;
	private TextItem ciudadItem;
	private TextItem telefono;
	private TextItem nombreEmpresa;
	private TextItem cargo;

	public PanelContenidoCuenta(UsuarioBO usuario) {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		layoutContenedor = new VLayout();
		layoutContenedor.setSize("50%", "100%");
		layoutContenedor.setBackgroundColor("white");

		inicializarInformacionPersonal();
		inicializarInformacionContacto();
		setInformacionContacto(usuario);
		setHabilitarEdicion(false);

		addMember(layoutContenedor);
	}

	private void inicializarInformacionPersonal() {
		DynamicForm formInformacionPersonal = new DynamicForm();
		formInformacionPersonal.setGroupTitle("Información Personal");
		formInformacionPersonal.setIsGroup(false);
		formInformacionPersonal.setNumCols(2);
		// form.setBorder("1px solid blue");
		formInformacionPersonal.setPadding(5);
		formInformacionPersonal.setCanDragResize(false);

		nombreItem = new TextItem();
		nombreItem.setTitle("Nombres");

		apellidosItem = new TextItem();
		apellidosItem.setTitle("Apellidos");

		cedulaItem = new TextItem();
		cedulaItem.setTitle("Cédula");

		mailItem = new TextItem();
		mailItem.setTitle("Correo Electrónico");

		ciudadItem = new TextItem();
		ciudadItem.setTitle("Ciudad");

		formInformacionPersonal.setFields(nombreItem, apellidosItem,
				cedulaItem, mailItem, ciudadItem);
		layoutContenedor.addMember(formInformacionPersonal);
	}

	private void inicializarInformacionContacto() {
		DynamicForm formInformacionContacto = new DynamicForm();
		formInformacionContacto.setGroupTitle("Información de Contacto");
		formInformacionContacto.setIsGroup(false);
		formInformacionContacto.setNumCols(2);
		// form.setBorder("1px solid blue");
		formInformacionContacto.setPadding(5);
		formInformacionContacto.setCanDragResize(true);

		nombreEmpresa = new TextItem();
		nombreEmpresa.setTitle("Empresa");

		cargo = new TextItem();
		cargo.setTitle("Cargo");

		telefono = new TextItem();
		telefono.setTitle("Teléfono");

		formInformacionContacto.setFields(nombreEmpresa, cargo, telefono);
		layoutContenedor.addMember(formInformacionContacto);
	}

	public void setInformacionContacto(UsuarioBO usuario) {
		nombreItem.setValue(usuario.getNombres());
		apellidosItem.setValue(usuario.getApellidos());
		cedulaItem.setValue(usuario.getIdentificador());
		mailItem.setValue(usuario.getCorreo_Electronico());
		ciudadItem.setValue(usuario.getCiudad());
		nombreEmpresa.setValue(usuario.getEmpresa());
		cargo.setValue(usuario.getCargo());
		telefono.setValue(usuario.getTelefono());
	}

	public void limpiarInformacion() {
		nombreItem.clearValue();
		apellidosItem.clearValue();
		cedulaItem.clearValue();
		mailItem.clearValue();
		ciudadItem.clearValue();
		nombreEmpresa.clearValue();
		cargo.clearValue();
		telefono.clearValue();
	}

	public void setHabilitarEdicion(boolean habilitar) {
		nombreItem.setCanEdit(habilitar);
		apellidosItem.setCanEdit(habilitar);
		cedulaItem.setCanEdit(habilitar);
		mailItem.setCanEdit(habilitar);
		ciudadItem.setCanEdit(habilitar);
		nombreEmpresa.setCanEdit(habilitar);
		cargo.setCanEdit(habilitar);
		telefono.setCanEdit(habilitar);
	}
}
