package co.mind.management.main.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import co.mind.management.maestro.client.servicios.UsuarioMaestroService;
import co.mind.management.main.client.servicios.MainService;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.persistencia.GestionClientes;
import co.mind.management.shared.persistencia.GestionEvaluacion;
import co.mind.management.shared.persistencia.GestionEvaluados;
import co.mind.management.shared.persistencia.GestionLaminas;
import co.mind.management.shared.persistencia.GestionPermisos;
import co.mind.management.shared.persistencia.GestionPreguntas;
import co.mind.management.shared.persistencia.GestionProcesos;
import co.mind.management.shared.persistencia.GestionPruebas;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MainServiceImpl extends RemoteServiceServlet implements
		MainService {

	/**
* 
*/
	private static final long serialVersionUID = 1L;

	public MainServiceImpl() {
	}

	@Override
	public UsuarioBO validarSesion(String sesion) {
		Object user = obtenerUsuarioDeSesion();
		if (user != null) {
			return (UsuarioBO) user;
		} else {
			return null;
		}
	}

	private UsuarioBO obtenerUsuarioDeSesion() {
		UsuarioBO user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute(Convencion.CLAVE_USUARIO);
		if (userObj != null && userObj instanceof UsuarioBO) {
			user = (UsuarioBO) userObj;
		}
		return user;
	}

	@Override
	public void cerrarSesion() {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		session.removeAttribute(Convencion.CLAVE_USUARIO);
	}

}
