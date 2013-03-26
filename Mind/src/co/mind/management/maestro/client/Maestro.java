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
package co.mind.management.maestro.client;

import java.util.Date;
import java.util.List;

import co.mind.management.maestro.client.procesos.PanelProcesos;
import co.mind.management.maestro.client.servicios.UsuarioMaestroService;
import co.mind.management.maestro.client.servicios.UsuarioMaestroServiceAsync;
import co.mind.management.maestro.client.temas.PanelPruebas;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.UsuarioMaestroBO;
import co.mind.management.shared.records.ImagenRecord;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Maestro implements EntryPoint {
	private static String urlLogin = "/Mind/Login.html";
	private static UsuarioMaestroServiceAsync usuarioMaestroService = GWT
			.create(UsuarioMaestroService.class);
	private static UsuarioMaestroBO usuarioMaestro;
	private static MaestroMainLayout mainLayout;
	// private static PanelEdicionPruebas panelEdicionPruebas;

	private static PreguntaUsuarioBO preguntaTemp;
	private static PruebaUsuarioBO pruebaTemp;
	private static ProcesoUsuarioBO procesoTemp;

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
		usuarioMaestroService.validarSesion(sessionID,
				new AsyncCallback<UsuarioBO>() {

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
							usuarioMaestro = (UsuarioMaestroBO) result;
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
		mainLayout = new MaestroMainLayout(usuarioMaestro);
		RootPanel.get().add(mainLayout);
		setListaImagenesUsuario();
		setListaProcesos();
		setListaPruebas();
		setListausuariosBasicos();
	}

	private void actualizarCookie() {
		String sessionID = usuarioMaestro.getSesionID();
		// 60 minutos * 60 segundos
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sid", sessionID, expires, null, "/", false);
	}

	public void setListaImagenesUsuario() {
		usuarioMaestroService.consultarImagenesUsuario(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<ImagenUsuarioBO>>() {

					@Override
					public void onSuccess(List<ImagenUsuarioBO> result) {
						mainLayout.actualizarImagenesUsuario(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error Cargando las imagenes");
					}
				});
	}

	public static void setListaPruebas() {
		usuarioMaestroService.consultarPruebasUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<PruebaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PruebaUsuarioBO> result) {
						mainLayout.actualizarPruebas(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error cargando las pruebas");
					}
				});
	}

	public static void setListaProcesos() {
		usuarioMaestroService.consultarProcesosUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<ProcesoUsuarioBO>>() {

					@Override
					public void onSuccess(List<ProcesoUsuarioBO> result) {
						if (result == null) {
							System.out.print("Resultado procesos nulo");
						}
						mainLayout.actualizarProcesos(result);

					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error cargando los procesos");
					}
				});
	}

	public static void setListausuariosBasicos() {
		usuarioMaestroService.consultarUsuariosBasicosUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<EvaluadoBO>>() {

					@Override
					public void onSuccess(List<EvaluadoBO> result) {
						if (result == null) {
							System.out.print("Resultado procesos nulo");
						}
						mainLayout.actualizarUsuariosBasicos(result);

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
	}

	public static void obtenerPreguntasPorPrueba(int categoriaID) {
		usuarioMaestroService.consultarPreguntasPorCategoria(
				usuarioMaestro.getIdentificador(), categoriaID,
				new AsyncCallback<List<PreguntaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PreguntaUsuarioBO> result) {
						mainLayout.actualizarPreguntasPrueba(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out
								.println("Error cargando las preguntas de la categoria");
					}
				});
	}

	public static void agregarUsuarioBasico(int idUsuario,
			String nombreUsuario, String apellidosUsuario,
			String correoUsuario, int edadUsuario) {
		EvaluadoBO usuarioBasico = new EvaluadoBO();
		usuarioBasico.setApellidos(apellidosUsuario);
		usuarioBasico.setCorreoElectronico(correoUsuario);
		usuarioBasico.setEdad(edadUsuario);
		usuarioBasico.setIdentificadorUsuarioAdministrador(usuarioMaestro
				.getIdentificador());
		usuarioBasico.setNombres(nombreUsuario);
		usuarioBasico.setIdentificador(idUsuario);
		usuarioMaestroService.agregarUsuarioBasico(usuarioMaestro,
				usuarioBasico, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListausuariosBasicos();
						} else {
							SC.warn("Error ingresando el nuevo usuario.");
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						SC.warn("Error conectando la base de datos.");

					}
				});

	}

	public static void eliminarUsuariosBasicos(List<EvaluadoBO> bo) {
		for (EvaluadoBO usuarioBasicoBO : bo) {
			usuarioMaestroService.eliminarUsuariosBasicos(usuarioMaestro, bo,
					new AsyncCallback<Integer>() {

						@Override
						public void onSuccess(Integer result) {
							// TODO Auto-generated method stub

						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub

						}
					});
		}

	}

	public static void agregarPrueba(String nombrePrueba,
			String descripcionPrueba, List<PreguntaUsuarioBO> preguntas) {
		PruebaUsuarioBO prueba = new PruebaUsuarioBO();
		prueba.setDescripcion(descripcionPrueba);
		prueba.setNombre(nombrePrueba);
		prueba.setUsuarioAdministradorID(usuarioMaestro.getIdentificador());
		usuarioMaestroService.agregarPrueba(usuarioMaestro, prueba,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						setListaPruebas();

					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error cargando las pruebas");

					}
				});

	}

	public static void eliminarPrueba(PruebaUsuarioBO prueba) {
		pruebaTemp = prueba;

		SC.ask("Precauci�n",
				"Esta prueba puede ser usada en algun proceso. Eliminar la prueba har� que no se encuentre disponible para el proceso. �Desea continuar?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value == true) {
							usuarioMaestroService.eliminarPrueba(
									usuarioMaestro, pruebaTemp,
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											System.out
													.println("Error eliminando la prueba");

										}

										@Override
										public void onSuccess(Integer result) {

										}
									});
						}

					}
				});

	}

	public static void agregarPreguntaUsuario(PreguntaUsuarioBO preguntaBO,
			PruebaUsuarioBO prueba) {
		usuarioMaestroService.agregarPregunta(usuarioMaestro, preguntaBO,
				prueba, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						mainLayout.actualizarPreguntasPrueba();
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error agregando la pregunta");

					}
				});

	}

	public static void agregarProceso(List<EvaluadoBO> usuariosBasicos,
			ProcesoUsuarioBO p) {
		usuarioMaestroService.agregarProceso(usuarioMaestro, usuariosBasicos,
				p, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						setListaProcesos();
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error agregando el proceso.");

					}
				});

	}

	public static void eliminarProceso(ProcesoUsuarioBO proceso) {
		procesoTemp = proceso;

		SC.ask("Precauci�n",
				"Este proceso puede estar siendo usado. �Desea continuar?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value == true) {
							usuarioMaestroService.eliminarProceso(
									usuarioMaestro, procesoTemp,
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {
											System.out
													.println("Error eliminado el proceso");
										}

										@Override
										public void onSuccess(Integer result) {
											setListaProcesos();
										}
									});
						}

					}
				});
	}

	public static void obtenerImagenesUsuario() {
		usuarioMaestroService.consultarImagenesUsuario(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<ImagenUsuarioBO>>() {

					@Override
					public void onSuccess(List<ImagenUsuarioBO> result) {
						mainLayout.actualizarImagenesUsuario(result);

					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error cargando las imagenes");

					}
				});

	}

	public static void eliminarPreguntaCategoria(PreguntaUsuarioBO pregunta) {
		preguntaTemp = pregunta;

		SC.ask("Precauci�n",
				"Esta l�mina es usada en alguna prueba. Eliminar la categor�a har� que no se encuentre disponible para la prueba. �Desea continuar?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value == true) {
							usuarioMaestroService.eliminarPregunta(
									usuarioMaestro, preguntaTemp,
									new AsyncCallback<Integer>() {

										@Override
										public void onSuccess(Integer result) {
											mainLayout
													.actualizarPreguntasPrueba();

										}

										@Override
										public void onFailure(Throwable caught) {
											// TODO Auto-generated method stub

										}
									});
						}

					}
				});

	}

	public static void obtenerParticipantesProceso(ProcesoUsuarioBO proceso) {
		usuarioMaestroService.consultarParticipacionEnProceso(usuarioMaestro,
				proceso, new AsyncCallback<List<ParticipacionEnProcesoBO>>() {

					@Override
					public void onSuccess(List<ParticipacionEnProcesoBO> result) {
						mainLayout.actualizarParticipaciones(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out
								.println("Error cargando las participaciones");

					}
				});
		usuarioMaestroService.consultarResultadosProceso(usuarioMaestro,
				proceso, new AsyncCallback<List<ParticipacionEnProcesoBO>>() {

					@Override
					public void onSuccess(List<ParticipacionEnProcesoBO> result) {
						mainLayout.actualizarResultados(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out
								.println("Error cargando los resultados del proceso");

					}
				});

	}

	public static void generarReportePDF(
			List<ParticipacionEnProcesoBO> participacionesBO) {

		usuarioMaestroService.generarReporte(participacionesBO,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Window.open("/PDFReportServlet", "_blank", "");

					}
				});

	}

	public static void generarReporteExcel(
			List<ParticipacionEnProcesoBO> participacionesBO) {

		usuarioMaestroService.generarReporte(participacionesBO,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Void result) {
						Window.open("/ExcelReportServlet", "_blank", "");

					}
				});

	}

	public static void editarPreguntaUsuario(PreguntaUsuarioBO bo) {
		// TODO Auto-generated method stub

	}

	public static void cerrarSesion() {
		usuarioMaestroService.cerrarSesion(new AsyncCallback<Void>() {

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
				Window.Location.replace(urlLogin);
			}
		});
	}

	public static void agregarPrueba(PruebaUsuarioBO prueba) {
		// TODO Auto-generated method stub
		
	}

}
