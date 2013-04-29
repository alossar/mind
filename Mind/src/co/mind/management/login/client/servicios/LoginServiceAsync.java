package co.mind.management.login.client.servicios;

import co.mind.management.shared.dto.UsuarioBO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void validarLogin(String nombre, String contrasena,
			AsyncCallback<UsuarioBO> callback);

	void loguearseSesionServidor(AsyncCallback<UsuarioBO> callback);

	void enviarNuevaContrasena(String correo,
			AsyncCallback<Integer> asyncCallback);

}
