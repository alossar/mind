package co.mind.management.login.client.servicios;

import co.mind.management.shared.dto.UsuarioBO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService{
	
	public UsuarioBO validarLogin(String nombre, String contrasena);

    public UsuarioBO loguearseSesionServidor();

}
