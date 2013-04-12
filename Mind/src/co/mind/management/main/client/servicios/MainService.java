package co.mind.management.main.client.servicios;

import co.mind.management.shared.dto.UsuarioBO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("UsuarioMaestroService")
public interface MainService extends RemoteService {

	public UsuarioBO validarSesion(String sesion);

	public void cerrarSesion();	
}
