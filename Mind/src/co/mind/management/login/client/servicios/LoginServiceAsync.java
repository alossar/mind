package co.mind.management.login.client.servicios;

import co.mind.management.shared.bo.UsuarioBO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void validarLogin(String nombre, String contrasena,
			AsyncCallback<UsuarioBO> callback);

	void loguearseSesionServidor(AsyncCallback<UsuarioBO> callback);

}
