package co.mind.management.login.client;

import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.LinkItem;
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
	private Img imagenCargando;
	private IButton validateButton;

	public PanelLogin() {
		setSize("300px", "250px");
		setAlign(VerticalAlignment.CENTER);
		setDefaultLayoutAlign(VerticalAlignment.CENTER);
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

		LinkItem linkItem = new LinkItem("linkPass");  
        linkItem.setTitle(" "); 
		linkItem.setLinkTitle("¿Olvidó su contraseña?");
		linkItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				Login.mostrarNuevaContrasena();
			}
		});

		formLogin = new DynamicForm();
		formLogin.setSize("250px", "150px");

		// form.setBorder("1px solid blue");
		formLogin.setFields(textCorreo, textPassword, linkItem);

		validateButton = new IButton();
		validateButton.setTitle("Ingresar");
		validateButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				validarLogin();
			}
		});

		imagenCargando = new Img("img/loading.gif", 220, 19);
		imagenCargando.setVisible(false);

		addMember(formLogin);
		addMember(validateButton);
		addMember(imagenCargando);

	}

	private void validarLogin() {
		if (formLogin.validate()) {
			String usuario = textCorreo.getValueAsString();
			String pass = textPassword.getValueAsString();
			validateButton.setDisabled(true);
			imagenCargando.setVisible(true);
			Login.validarLogin(usuario, pass);
		}
	}

	public void limpiarCampos() {
		textPassword.clearValue();
		validateButton.setDisabled(false);
		imagenCargando.setVisible(false);
	}

}
