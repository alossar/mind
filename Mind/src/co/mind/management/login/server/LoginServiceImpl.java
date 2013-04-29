package co.mind.management.login.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.login.client.servicios.LoginService;
import co.mind.management.shared.persistencia.GestionAccesos;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.SMTPSender;

public class LoginServiceImpl extends RemoteServiceServlet implements
		LoginService {

	private static final long serialVersionUID = 1L;
	private GestionAccesos gestionAccesos;

	public LoginServiceImpl() {
		gestionAccesos = new GestionAccesos();
	}

	@Override
	public UsuarioBO validarLogin(String nombre, String contrasena) {
		Object user = gestionAccesos.verificarTipoUsuario(nombre, contrasena);
		if (user != null) {
			((UsuarioBO) user).setSesionID(this.getThreadLocalRequest()
					.getSession().getId());
			guardarUsuarioEnSesion((UsuarioBO) user);
		}
		return (UsuarioBO) user;
	}

	private void guardarUsuarioEnSesion(UsuarioBO user) {
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession(true);
		session.setAttribute(Convencion.CLAVE_USUARIO, user);
	}

	@Override
	public UsuarioBO loguearseSesionServidor() {
		UsuarioBO user = null;
		HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
		HttpSession session = httpServletRequest.getSession();
		Object userObj = session.getAttribute(Convencion.CLAVE_USUARIO);

		if (userObj != null && userObj instanceof UsuarioBO) {
			user = (UsuarioBO) userObj;
			return user;
		}
		return user;
	}

	@Override
	public int enviarNuevaContrasena(String correo) {
		String pass = gestionAccesos.cambiarContrasenaCorreo(correo);
		if (pass != null) {
			return SMTPSender.enviarContrasenaAlCorreo(correo, pass);
		}else{
			return Convencion.INCORRECTO;
		}
	}
}
