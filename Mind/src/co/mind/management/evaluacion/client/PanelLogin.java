package co.mind.management.evaluacion.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.IntegerItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelLogin extends VLayout {

	private TextItem textCorreo;
	private TextItem textPassword;
	private IntegerItem textCedula;
	private DynamicForm formLogin;
	private IButton botonIngresar;

	public PanelLogin() {
		setSize("300px", "250px");
		setAlign(Alignment.CENTER);
		setDefaultLayoutAlign(Alignment.CENTER);
		setPadding(10);

		inicializarComponentes();

		draw();
	}

	private void inicializarComponentes() {
		RegExpValidator regExpValidator = new RegExpValidator();
		regExpValidator
				.setExpression("^([a-zA-Z0-9_.\\-+])+@(([a-zA-Z0-9\\-])+\\.)+[a-zA-Z0-9]{2,4}$");

		textCorreo = new TextItem("email");
		textCorreo.setTitle("Correo");
		textCorreo.setValidators(regExpValidator);
		textCorreo.setRequired(true);
		textCorreo.setWidth("*");

		textPassword = new TextItem();
		textPassword.setTitle("C\u00F3digo Acceso");
		textPassword.setRequired(true);
		textPassword.setWidth("*");

		textCedula = new IntegerItem();
		textCedula.setTitle("C\u00E9dula");
		textCedula.setRequired(true);
		textCedula.setWidth("*");

		textCorreo.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter")) {
					validarLogin();
				}
			}
		});
		
		textPassword.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter")) {
					validarLogin();
				}
			}
		});
		
		textCedula.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getKeyName().equalsIgnoreCase("Enter")) {
					validarLogin();
				}
			}
		});

		formLogin = new DynamicForm();
		formLogin.setSize("250px", "150px");
		formLogin.setGroupTitle("Evaluaci\u00F3n");
		formLogin.setIsGroup(true);

		// form.setBorder("1px solid blue");
		formLogin.setFields(textCorreo, textPassword, textCedula);

		botonIngresar = new IButton();
		botonIngresar.setTitle("Ingresar");
		botonIngresar.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				validarLogin();
			}
		});
		addMember(formLogin);
		addMember(botonIngresar);
	}

	private void validarLogin() {
		habilitarCampos(false);
		if (formLogin.validate()) {
			String usuario = textCorreo.getValueAsString();
			String pass = textPassword.getValueAsString();
			int cedula = textCedula.getValueAsInteger();
			Evaluacion.validarLogin(usuario, pass,
					((Integer) cedula).toString());
		} else {
			habilitarCampos(true);
		}
	}

	public void habilitarCampos(boolean b) {
		botonIngresar.setDisabled(!b);
		textCorreo.setDisabled(!b);
		textPassword.setDisabled(!b);
		textCedula.setDisabled(!b);

	}

}
