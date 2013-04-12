package co.mind.management.main.client;

import java.util.Date;

import co.mind.management.main.client.servicios.MainService;
import co.mind.management.main.client.servicios.MainServiceAsync;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.dto.UsuarioMaestroBO;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	private static String urlLogin = "/Mind/Login.html";
	private static MainServiceAsync mainService = GWT.create(MainService.class);
	private static UsuarioBO usuarioMain;
	private static MainLayout mainLayout;
	// private static PanelEdicionPruebas panelEdicionPruebas;

	public void onModuleLoad() {

		String sessionID = Cookies.getCookie("sid");
		if (sessionID == null) {
			if (!GWT.isProdMode()) {
				String url = "?gwt.codesvr=127.0.0.1:9997";
				Window.Location.replace(urlLogin + url);
			} else {
				Window.Location.replace(urlLogin);
			}
		} else {
			verificarSesion(sessionID);
		}

	}

	private void verificarSesion(String sessionID) {
		mainService.validarSesion(sessionID, new AsyncCallback<UsuarioBO>() {

			@Override
			public void onFailure(Throwable caught) {
				if (!GWT.isProdMode()) {
					String url = "?gwt.codesvr=127.0.0.1:9997";
					Window.Location.replace(urlLogin + url);
				} else {
					Window.Location.replace(urlLogin);
				}
			}

			@Override
			public void onSuccess(UsuarioBO result) {
				if (result != null) {
					usuarioMain = (UsuarioMaestroBO) result;
					inicializarComponentes();
					actualizarCookie();
				} else {
					if (!GWT.isProdMode()) {
						String url = "?gwt.codesvr=127.0.0.1:9997";
						Window.Location.replace(urlLogin + url);
					} else {
						Window.Location.replace(urlLogin);
					}
				}
			}

		});
	}

	private void inicializarComponentes() {
		mainLayout = new MainLayout(usuarioMain);
		RootPanel.get().add(mainLayout);
	}

	private void actualizarCookie() {
		String sessionID = usuarioMain.getSesionID();
		// 60 minutos * 60 segundos
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sid", sessionID, expires, null, "/", false);
	}

	public static void cerrarSesion() {
		mainService.cerrarSesion(new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void arg0) {
				Cookies.removeCookie("sid");
				if (!GWT.isProdMode()) {
					String url = "?gwt.codesvr=127.0.0.1:9997";
					Window.Location.replace(urlLogin + url);
				} else {
					Window.Location.replace(urlLogin);
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				arg0.printStackTrace();
				Window.Location.replace(urlLogin);
			}
		});
	}

}
