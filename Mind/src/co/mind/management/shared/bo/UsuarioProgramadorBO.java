package co.mind.management.shared.bo;

import java.io.Serializable;

public class UsuarioProgramadorBO extends UsuarioBO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int usuarioAdministradorID;

	public int getUsuarioAdministradorID() {
		return usuarioAdministradorID;
	}

	public void setUsuarioAdministradorID(int usuarioAdministradorID) {
		this.usuarioAdministradorID = usuarioAdministradorID;
	}


}
