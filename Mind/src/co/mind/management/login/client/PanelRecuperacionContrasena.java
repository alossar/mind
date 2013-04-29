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

public class PanelRecuperacionContrasena extends VLayout {

	private TextItem textCorreo;
	private DynamicForm formLogin;
	private IButton validateButton;

	public PanelRecuperacionContrasena() {
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

		textCorreo.addKeyDownHandler(new KeyDownHandler() {

			@Override
			public void onKeyDown(KeyDownEvent event) {
				try {
					if (event.getKeyName().equalsIgnoreCase("Enter")) {
						if (formLogin.validate()) {
							Login.recuperarContrasena(textCorreo
									.getValueAsString());
						}
					}
				} catch (NullPointerException e) {

				}
			}
		});

		LinkItem linkItem = new LinkItem("linkPass");  
        linkItem.setTitle(" "); 
		linkItem.setLinkTitle("Volver");
		linkItem.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(
					com.smartgwt.client.widgets.form.fields.events.ClickEvent event) {
				Login.mostrarLogin();
			}
		});

		formLogin = new DynamicForm();
		formLogin.setSize("250px", "120px");

		// form.setBorder("1px solid blue");
		formLogin.setFields(textCorreo, linkItem);

		validateButton = new IButton();
		validateButton.setTitle("Recuperar");
		validateButton.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				if (formLogin.validate()) {
					Login.recuperarContrasena(textCorreo.getValueAsString());
				}
			}
		});

		addMember(formLogin);
		addMember(validateButton);

	}

	public void limpiarCampos() {
		validateButton.setDisabled(false);
	}

}
