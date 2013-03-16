package co.mind.management.shared.bo;

import java.io.Serializable;
import java.util.List;

public class UsuarioAdministradorBO extends UsuarioBO implements Serializable {

	private static final long serialVersionUID = 1L;

	private PermisoBO permiso;

	public PermisoBO getPlanesDeUsuario() {
		return permiso;
	}

	public void setPlanesDeUsuario(PermisoBO permiso) {
		this.permiso = permiso;
	}

}
