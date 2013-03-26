/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package co.mind.management.login.client;

import java.util.Date;

import co.mind.management.login.client.servicios.LoginService;
import co.mind.management.login.client.servicios.LoginServiceAsync;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Login implements EntryPoint {
	private static PanelLogin panelLogin;
	private static LoginServiceAsync loginService = GWT
			.create(LoginService.class);
	private static String urlAplicacion = "/Mind/Tarmin.html";
	private static String urlMaster = "/Mind/Maestro.html";

	public void onModuleLoad() {

		String sessionID = Cookies.getCookie("sid");
		if (sessionID != null) {
			verificarSessionCookie(sessionID);
		} else {
			inicializarComponentes();
		}
	}

	private void inicializarComponentes() {
		RootPanel rootPanel = RootPanel.get("registro");
		panelLogin = new PanelLogin();
		rootPanel.add(panelLogin);
		RootPanel.get("superFondoBlanco").setVisible(false);
	}

	public static void validarLogin(String correo, String pass) {
		loginService.validarLogin(correo, pass, new AsyncCallback<UsuarioBO>() {
			public void onFailure(Throwable caught) {

				Window.alert(caught.getMessage());
				panelLogin.limpiarCampos();
			}

			@Override
			public void onSuccess(UsuarioBO result) {
				if (result != null) {
					irALaAplicacion(result);
				} else {
					panelLogin.limpiarCampos();
					SC.say(Convencion.MENSAJE_FALLO_LOGIN);
				}
			}
		});
	}

	private static void irALaAplicacion(UsuarioBO result) {
		String sessionID = result.getSesionID();
		// 60 minutos * 60 segundos
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sid", sessionID, expires, null, "/", false);
		if (result.getTipo().equals(
				Convencion.CLAVE_PERMISOS_USUARIO_ADMINISTRADOR)) {

			if (!GWT.isProdMode()) {
				String url = "?gwt.codesvr=127.0.0.1:9997";
				Window.Location.replace(urlAplicacion + url);
			} else {
				Window.Location.replace(urlAplicacion);
			}
		} else if (result.getTipo().equals(
				Convencion.CLAVE_PERMISOS_USUARIO_MAESTRO)
				|| result.getTipo().equals(
						Convencion.CLAVE_PERMISOS_USUARIO_MAESTRO_PRINCIPAL)) {
			if (!GWT.isProdMode()) {
				String url = "?gwt.codesvr=127.0.0.1:9997";
				Window.Location.replace(urlMaster + url);
			} else {
				Window.Location.replace(urlMaster);
			}
		}
	}

	private void verificarSessionCookie(String sessionID) {
		loginService.loguearseSesionServidor(new AsyncCallback<UsuarioBO>() {

			@Override
			public void onSuccess(UsuarioBO result) {
				if (result != null) {
					irALaAplicacion(result);
				} else {
					inicializarComponentes();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				inicializarComponentes();

			}
		});

	}

}
