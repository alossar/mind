package co.mind.management.main.client.servicios;

import co.mind.management.shared.dto.UsuarioBO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MainServiceAsync {

	void validarSesion(String sesion, AsyncCallback<UsuarioBO> callback);

	void cerrarSesion(AsyncCallback<Void> callback);

}
