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
package co.mind.management.evaluacion.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import co.mind.management.evaluacion.client.servicios.EvaluacionService;
import co.mind.management.evaluacion.client.servicios.EvaluacionServiceAsync;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.ResultadoBO;
import co.mind.management.shared.recursos.Convencion;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ClosingEvent;
import com.google.gwt.user.client.Window.ClosingHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.layout.VLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Evaluacion implements EntryPoint {

	private static PanelPregunta panelPregunta;
	private static PanelLogin panelLogin;
	private static EvaluacionServiceAsync evaluacionService = GWT
			.create(EvaluacionService.class);
	private static ParticipacionEnProcesoBO participacionEnProceso;
	private static int identificador;
	private static String correo;
	private static String codigoAcceso;
	private static boolean termina = false;
	// private static String finUrl ="/end.html";
	private static String finUrl = "/Mind/end.html";

	private static PanelInstruccion panelInstruccion;
	private static HashMap<PruebaUsuarioBO, List<PreguntaUsuarioBO>> preguntasPorPrueba;
	private static Entry<PruebaUsuarioBO, List<PreguntaUsuarioBO>> categoriaActual;
	private static Iterator<Entry<PruebaUsuarioBO, List<PreguntaUsuarioBO>>> iterador;

	public void onModuleLoad() {
		inicializarPanelesEvaluacion();
		mostrarLogin();

		RootPanel.get("superFondoBlanco").setVisible(false);
	}

	private void mostrarLogin() {
		RootPanel rootPanel = RootPanel.get("registro");
		panelLogin = new PanelLogin();
		RootPanel.get("registro").setVisible(true);
		RootPanel.get("evaluacion").setVisible(false);
		rootPanel.add(panelLogin);
	}

	private static void inicializarPanelesEvaluacion() {
		RootPanel rootPanel = RootPanel.get("evaluacion");
		panelInstruccion = new PanelInstruccion();
		panelPregunta = new PanelPregunta();
		VLayout v1 = new VLayout();
		v1.setSize("900px", "600px");
		v1.addMember(panelInstruccion);
		v1.addMember(panelPregunta);
		rootPanel.add(v1);
		panelInstruccion.setVisible(false);
		panelPregunta.setVisible(false);

	}

	private static void mostrarPanelPregunta() {
		panelPregunta.setVisible(true);
		panelInstruccion.setVisible(false);
		RootPanel.get("evaluacion").setVisible(true);
		RootPanel.get("registro").setVisible(false);
	}

	private static void mostrarPanelInstruccion() {
		panelInstruccion
				.setInstruccion("<div style='color:#40B3DF;letter-spacing:12px;font-size:15px;position:relative;left:25px;top:10px;'>Prueba: "
						+ categoriaActual.getKey().getNombre()
						+ "</div><p></p><div style='letter-spacing:6px;font-size:12px;position:relative;left:25px;top:20px;'>"
						+ categoriaActual.getKey().getDescripcion() + "</div>");
		RootPanel.get("evaluacion").setVisible(true);
		RootPanel.get("registro").setVisible(false);
		panelPregunta.setVisible(false);
		panelInstruccion.setVisible(true);
	}

	public static void iniciarCategoria() {
		mostrarPanelPregunta();
		panelPregunta.actualizarPreguntas(categoriaActual.getValue());
		panelPregunta.iniciar();
	}

	public static void validarLogin(String usuario, String pass, String cedula) {
		identificador = Integer.parseInt(cedula);
		correo = usuario;
		codigoAcceso = pass;
		evaluacionService.validarUsuarioBasico(cedula, usuario, pass,
				new AsyncCallback<Integer>() {
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
						caught.printStackTrace();
						SC.warn("Error de conexi�n.");
					}

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.VERIFICACION_USUARIO_BASICO_CORRECTA) {
							obtenerParticipacionEnProceso();
						} else {
							panelLogin.habilitarCampos(true);
							if (result == Convencion.VERIFICACION_USUARIO_BASICO_CODIGO_ACCESO_NO_VALIDO) {
								SC.warn("El c\u00F3digo de Acceso no es válido");
							} else if (result == Convencion.VERIFICACION_USUARIO_BASICO_SIN_TERMINAR_PRUEBA) {
								SC.warn("El usuario comenz\u00F3 una prueba pero no la termin\u00F3.");
							} else if (result == Convencion.VERIFICACION_USUARIO_BASICO_PARTICIPACION_NO_EXISTE) {

								SC.warn("Identificaci\u00F3n o c\u00F3digo no v�lido.");
							} else if (result == Convencion.VERIFICACION_USUARIO_BASICO_NO_EXISTE) {
								SC.warn("Identificaci\u00F3n o c\u00F3digo no válido.");
							} else {
								SC.warn("Correo o c\u00F3digo no válido.");
							}
						}
					}
				});
	}

	private static void obtenerParticipacionEnProceso() {
		evaluacionService.obtenerParticipacionEnProceso(identificador, correo,
				codigoAcceso, new AsyncCallback<ParticipacionEnProcesoBO>() {

					@Override
					public void onSuccess(ParticipacionEnProcesoBO result) {
						participacionEnProceso = result;
						// obtenerNombreEmpresa();
						Window.addWindowClosingHandler(new ClosingHandler() {

							@Override
							public void onWindowClosing(ClosingEvent event) {
								if (!termina) {
									event.setMessage("Si cierra puede perder los resultados de la prueba. ¿Desea continuar?");
								}
							}
						});
						comenzarPrueba();
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error obteniendo la participacion");

					}
				});
	}

	private static void comenzarPrueba() {

		evaluacionService.comenzarPrueba(
				participacionEnProceso.getUsuarioBasico(),
				participacionEnProceso, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result.equals(Convencion.CORRECTO)) {
						}
						obtenerPreguntasPrueba();
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error comenzando la prueba");

					}
				});
	}

	private static void obtenerNombreEmpresa() {
		evaluacionService.obtenerNombreDeLaEmpresa(participacionEnProceso
				.getUsuarioBasico().getIdentificadorUsuarioAdministrador(),
				new AsyncCallback<String>() {

					@Override
					public void onSuccess(String result) {
						if (result != null) {
							panelPregunta.actualizarNombreEmpresa(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {

					}
				});
	}

	public static void guardarResultado(ResultadoBO resultado) {
		resultado.setParticipacionEnProceso(participacionEnProceso);
		evaluacionService.guardarResultado(
				participacionEnProceso.getUsuarioBasico(), resultado,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result.equals(Convencion.CORRECTO)) {
							System.out.println("Se guarda el resultado");
						} else {
							System.out.println("Error guardando el resultado");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error guardando el resultado");
					}
				});

	}

	private static void obtenerPreguntasPrueba() {
		ProcesoUsuarioBO proceso = new ProcesoUsuarioBO();
		proceso.setIdentificador(participacionEnProceso.getProcesoID());
		evaluacionService.preguntasPrueba(proceso,
				participacionEnProceso.getUsuarioBasico(),
				new AsyncCallback<List<PreguntaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PreguntaUsuarioBO> result) {
						obtenerListaPreguntasPorCategoria(result);
						mostrarPanelInstruccion();

					}

					@Override
					public void onFailure(Throwable caught) {
						System.out.println("Error cargando las preguntas");

					}
				});
	}

	private static void obtenerListaPreguntasPorCategoria(
			List<PreguntaUsuarioBO> listaPreguntas) {
		preguntasPorPrueba = new HashMap<PruebaUsuarioBO, List<PreguntaUsuarioBO>>();
		List<PreguntaUsuarioBO> preguntasTemporales = new ArrayList<PreguntaUsuarioBO>();
		for (PreguntaUsuarioBO pregunta : listaPreguntas) {
			if (preguntasPorPrueba.containsKey(pregunta.getPruebaUsuario())) {
				preguntasPorPrueba.get(pregunta.getPruebaUsuario()).add(
						pregunta);
			} else {
				preguntasTemporales = new ArrayList<PreguntaUsuarioBO>();
				preguntasTemporales.add(pregunta);
				preguntasPorPrueba.put(pregunta.getPruebaUsuario(),
						preguntasTemporales);
			}

		}
		iterador = preguntasPorPrueba.entrySet().iterator();
		categoriaActual = iterador.next();
	}

	public static void terminarPrueba() {
		try {
			categoriaActual = iterador.next();
			mostrarPanelInstruccion();

		} catch (NoSuchElementException exc) {
			evaluacionService.terminarPrueba(
					participacionEnProceso.getUsuarioBasico(),
					participacionEnProceso, new AsyncCallback<Integer>() {

						@Override
						public void onSuccess(Integer result) {
							cerrarSesion();
						}

						@Override
						public void onFailure(Throwable caught) {
							System.out.println("Error terminando la prueba");
						}
					});
		}
	}

	private static void cerrarSesion() {
		evaluacionService.cerrarSesion(new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				termina = true;
				Window.Location.replace(finUrl);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.Location.replace(finUrl);
			}
		});
	}
}
