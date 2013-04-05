package co.mind.management.login.client;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.KeyDownEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyDownHandler;
import com.smartgwt.client.widgets.form.validator.RegExpValidator;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelLogin extends VLayout {

	private TextItem textCorreo;
	private PasswordItem textPassword;
	private DynamicForm formLogin;

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

		textPassword = new PasswordItem();
		textPassword.setTitle("Contrase\u00F1a");
		textPassword.setRequired(true);
		textPassword.setWidth("*");

		textCorreo.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				try {
					if (event.getKeyName().equalsIgnoreCase("Enter")) {
						validarLogin();
					}
				} catch (NullPointerException e) {

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

		formLogin = new DynamicForm();
		formLogin.setSize("250px", "150px");

		// form.setBorder("1px solid blue");
		formLogin.setFields(textCorreo, textPassword);

		IButton validateButton = new IButton();
		validateButton.setTitle("Ingresar");
		validateButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				validarLogin();
			}
		});
		addMember(formLogin);
		addMember(validateButton);
	}

	private void validarLogin() {
		if (formLogin.validate()) {
			String usuario = textCorreo.getValueAsString();
			String pass = textPassword.getValueAsString();
			Login.validarLogin(usuario, pass);
		}
	}

	public void limpiarCampos() {
		textPassword.clearValue();
	}

}
