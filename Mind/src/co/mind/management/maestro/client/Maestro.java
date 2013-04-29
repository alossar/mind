package co.mind.management.maestro.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.mind.management.maestro.client.clientes.PanelContenidoClientes;
import co.mind.management.maestro.client.cuenta.PanelContenidoCuenta;
import co.mind.management.maestro.client.evaluados.PanelContenidoEvaluados;
import co.mind.management.maestro.client.imagenes.PanelContenidoImagenes;
import co.mind.management.maestro.client.procesos.PanelContenidoProcesos;
import co.mind.management.maestro.client.programadores.PanelContenidoProgramadores;
import co.mind.management.maestro.client.pruebas.PanelContenidoPruebas;
import co.mind.management.maestro.client.servicios.UsuarioMaestroService;
import co.mind.management.maestro.client.servicios.UsuarioMaestroServiceAsync;
import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioHasPruebaUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsoBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.dto.UsuarioProgramadorBO;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.UsuarioAdministradorListGridRecord;
import co.mind.management.shared.recursos.Convencion;
import co.mind.management.shared.recursos.Generador;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuButton;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Maestro implements EntryPoint {
	private static String urlLogin = "/Mind/login.html";
	private static UsuarioMaestroServiceAsync usuarioMaestroService = GWT
			.create(UsuarioMaestroService.class);
	private static UsuarioBO usuarioMaestro;
	// private static PanelEdicionPruebas panelEdicionPruebas;

	private static PreguntaUsuarioBO preguntaTemp;
	private static PruebaUsuarioBO pruebaTemp;
	private static ProcesoUsuarioBO procesoTemp;
	private static Timer timerRefrescar;

	private static PanelContenidoProcesos panelVerProcesos;
	private static PanelContenidoPruebas panelPruebas;
	private static PanelContenidoEvaluados panelEvaluados;
	private static PanelContenidoProgramadores panelProgramadores;
	private static PanelContenidoClientes panelClientes;
	private static PanelContenidoImagenes panelImagenes;
	private static PanelContenidoCuenta panelCuenta;
	private static UsuarioBO clienteTemp;

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
							usuarioMaestro = result;
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
		if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_MAESTRO)
				|| usuarioMaestro.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {

			panelVerProcesos = new PanelContenidoProcesos(usuarioMaestro);
			panelPruebas = new PanelContenidoPruebas();
			panelEvaluados = new PanelContenidoEvaluados();
			panelProgramadores = new PanelContenidoProgramadores();
			panelClientes = new PanelContenidoClientes();
			panelImagenes = new PanelContenidoImagenes();
			panelCuenta = new PanelContenidoCuenta(usuarioMaestro);

			RootPanel.get("programarProcesoMInfo").add(panelVerProcesos);
			RootPanel.get("pruebaMInfo").add(panelPruebas);
			RootPanel.get("cuentaMInfo").add(panelCuenta);
			RootPanel.get("laminasMInfo").add(panelImagenes);
			RootPanel.get("evaluadosMInfo").add(panelEvaluados);
			RootPanel.get("clientesMInfo").add(panelClientes);
			RootPanel.get("programadoresMInfo").add(panelProgramadores);

			Img imagenBarraProceso = new Img();
			imagenBarraProceso.setSrc("img/admin/botProceso.png");
			imagenBarraProceso.setWidth(32);
			imagenBarraProceso.setHeight(32);
			imagenBarraProceso
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
							setListaProcesos();
						}
					});

			RootPanel.get("programarProcesoP").add(imagenBarraProceso);

			Img imagenBarraPrueba = new Img();
			imagenBarraPrueba.setSrc("img/admin/botPrueba.png");
			imagenBarraPrueba.setWidth(32);
			imagenBarraPrueba.setHeight(32);
			imagenBarraPrueba
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
							setListaPruebas();
						}
					});

			RootPanel.get("gestionP").add(imagenBarraPrueba);

			Img imagenBarraEvaluados = new Img();
			imagenBarraEvaluados.setSrc("img/admin/botEvaluados.png");
			imagenBarraEvaluados.setWidth(32);
			imagenBarraEvaluados.setHeight(32);
			imagenBarraEvaluados
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("evaluadosP").add(imagenBarraEvaluados);

			Img imagenBarraProgramadores = new Img();
			imagenBarraProgramadores.setSrc("img/admin/botProgramadores.png");
			imagenBarraProgramadores.setWidth(32);
			imagenBarraProgramadores.setHeight(32);
			imagenBarraProgramadores
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("programadorP").add(imagenBarraProgramadores);

			Img imagenBarraLaminas = new Img();
			imagenBarraLaminas.setSrc("img/admin/botLaminas.png");
			imagenBarraLaminas.setWidth(32);
			imagenBarraLaminas.setHeight(32);
			imagenBarraLaminas
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("laminasP").add(imagenBarraLaminas);

			Img imagenBarraClientes = new Img();
			imagenBarraClientes.setSrc("img/admin/botClientes.png");
			imagenBarraClientes.setWidth(32);
			imagenBarraClientes.setHeight(32);
			imagenBarraClientes
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("clientesP").add(imagenBarraClientes);

			Img imagenBarraHome = new Img();
			imagenBarraHome.setSrc("img/admin/botHome.png");
			imagenBarraHome.setWidth(32);
			imagenBarraHome.setHeight(32);
			imagenBarraHome
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("botHomeNav").add(imagenBarraHome);

			Img imagenBarraInfo = new Img();
			imagenBarraInfo.setSrc("img/admin/botPregunta.png");
			imagenBarraInfo.setWidth(32);
			imagenBarraInfo.setHeight(32);
			imagenBarraInfo
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("botInfoNav").add(imagenBarraInfo);

			Menu menuUsuario = new Menu();

			MenuItem menuItemCerrarSesion = new MenuItem("Cerrar Sesi\u00f3n");
			menuItemCerrarSesion
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							cerrarSesion();
						}
					});
			menuUsuario.addItem(menuItemCerrarSesion);

			MenuButton menubutton = new MenuButton(usuarioMaestro.getNombres()
					+ " " + usuarioMaestro.getApellidos(), menuUsuario);

			RootPanel.get("listaFuncionesUser").add(menubutton);

			setListaProcesos();
			setListaPruebas();
			setListaUsuariosBasicos();
			setListaImagenesUsuario();
			setListaClientes();
			setListaProgramadores();
			refrescar();
		} else if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_ADMINISTRADOR)) {

			panelVerProcesos = new PanelContenidoProcesos(usuarioMaestro);
			panelPruebas = new PanelContenidoPruebas();
			panelEvaluados = new PanelContenidoEvaluados();
			panelProgramadores = new PanelContenidoProgramadores();
			panelCuenta = new PanelContenidoCuenta(usuarioMaestro);

			RootPanel.get("programarProcesoMInfo").add(panelVerProcesos);
			RootPanel.get("pruebaMInfo").add(panelPruebas);
			RootPanel.get("cuentaMInfo").add(panelCuenta);
			RootPanel.get("evaluadosMInfo").add(panelEvaluados);
			RootPanel.get("programadoresMInfo").add(panelProgramadores);

			Img imagenBarraProceso = new Img();
			imagenBarraProceso.setSrc("img/admin/botProceso.png");
			imagenBarraProceso.setWidth(32);
			imagenBarraProceso.setHeight(32);
			imagenBarraProceso
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
							setListaProcesos();
						}
					});

			RootPanel.get("programarProcesoP").add(imagenBarraProceso);

			Img imagenBarraPrueba = new Img();
			imagenBarraPrueba.setSrc("img/admin/botPrueba.png");
			imagenBarraPrueba.setWidth(32);
			imagenBarraPrueba.setHeight(32);
			imagenBarraPrueba
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
							setListaPruebas();
						}
					});

			RootPanel.get("gestionP").add(imagenBarraPrueba);

			Img imagenBarraEvaluados = new Img();
			imagenBarraEvaluados.setSrc("img/admin/botEvaluados.png");
			imagenBarraEvaluados.setWidth(32);
			imagenBarraEvaluados.setHeight(32);
			imagenBarraEvaluados
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("evaluadosP").add(imagenBarraEvaluados);

			Img imagenBarraProgramadores = new Img();
			imagenBarraProgramadores.setSrc("img/admin/botProgramadores.png");
			imagenBarraProgramadores.setWidth(32);
			imagenBarraProgramadores.setHeight(32);
			imagenBarraProgramadores
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("programadorP").add(imagenBarraProgramadores);

			RootPanel.get("clientesP").setVisible(false);

			Img imagenBarraHome = new Img();
			imagenBarraHome.setSrc("img/admin/botHome.png");
			imagenBarraHome.setWidth(32);
			imagenBarraHome.setHeight(32);
			imagenBarraHome
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("botHomeNav").add(imagenBarraHome);

			Img imagenBarraInfo = new Img();
			imagenBarraInfo.setSrc("img/admin/botPregunta.png");
			imagenBarraInfo.setWidth(32);
			imagenBarraInfo.setHeight(32);
			imagenBarraInfo
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("botInfoNav").add(imagenBarraInfo);

			Menu menuUsuario = new Menu();

			MenuItem menuItemCerrarSesion = new MenuItem("Cerrar Sesi\u00f3n");
			menuItemCerrarSesion
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							cerrarSesion();
						}
					});
			menuUsuario.addItem(menuItemCerrarSesion);

			MenuButton menubutton = new MenuButton(usuarioMaestro.getNombres()
					+ " " + usuarioMaestro.getApellidos(), menuUsuario);

			RootPanel.get("listaFuncionesUser").add(menubutton);

			setListaProcesos();
			setListaPruebas();
			setListaUsuariosBasicos();
			setListaImagenesUsuario();
			refrescar();
			obtenerUsosCliente(usuarioMaestro);

		} else if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_PROGRAMADOR)) {

			panelVerProcesos = new PanelContenidoProcesos(usuarioMaestro);
			panelEvaluados = new PanelContenidoEvaluados();
			panelCuenta = new PanelContenidoCuenta(usuarioMaestro);

			RootPanel.get("programarProcesoMInfo").add(panelVerProcesos);
			RootPanel.get("pruebaMInfo").setVisible(false);
			RootPanel.get("cuentaMInfo").add(panelCuenta);
			RootPanel.get("laminasMInfo").setVisible(false);
			RootPanel.get("evaluadosMInfo").add(panelEvaluados);
			RootPanel.get("clientesMInfo").setVisible(false);
			RootPanel.get("programadoresMInfo").setVisible(false);

			Img imagenBarraProceso = new Img();
			imagenBarraProceso.setSrc("img/admin/botProceso.png");
			imagenBarraProceso.setWidth(32);
			imagenBarraProceso.setHeight(32);
			imagenBarraProceso
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
							setListaProcesos();
						}
					});

			RootPanel.get("programarProcesoP").add(imagenBarraProceso);

			RootPanel.get("gestionP").setVisible(false);

			Img imagenBarraEvaluados = new Img();
			imagenBarraEvaluados.setSrc("img/admin/botEvaluados.png");
			imagenBarraEvaluados.setWidth(32);
			imagenBarraEvaluados.setHeight(32);
			imagenBarraEvaluados
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("evaluadosP").add(imagenBarraEvaluados);

			RootPanel.get("programadorP").setVisible(false);
			RootPanel.get("laminasP").setVisible(false);
			RootPanel.get("clientesP").setVisible(false);
			RootPanel.get("evaluadosPD").setVisible(false);
			RootPanel.get("pruebaPD").setVisible(false);

			Img imagenBarraHome = new Img();
			imagenBarraHome.setSrc("img/admin/botHome.png");
			imagenBarraHome.setWidth(32);
			imagenBarraHome.setHeight(32);
			imagenBarraHome
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("botHomeNav").add(imagenBarraHome);

			Img imagenBarraInfo = new Img();
			imagenBarraInfo.setSrc("img/admin/botPregunta.png");
			imagenBarraInfo.setWidth(32);
			imagenBarraInfo.setHeight(32);
			imagenBarraInfo
					.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							estadoInicial();
						}
					});

			RootPanel.get("botInfoNav").add(imagenBarraInfo);

			Menu menuUsuario = new Menu();

			MenuItem menuItemCerrarSesion = new MenuItem("Cerrar Sesi\u00f3n");
			menuItemCerrarSesion
					.addClickHandler(new com.smartgwt.client.widgets.menu.events.ClickHandler() {

						@Override
						public void onClick(MenuItemClickEvent event) {
							cerrarSesion();
						}
					});
			menuUsuario.addItem(menuItemCerrarSesion);

			MenuButton menubutton = new MenuButton(usuarioMaestro.getNombres()
					+ " " + usuarioMaestro.getApellidos(), menuUsuario);

			RootPanel.get("listaFuncionesUser").add(menubutton);

			setListaProcesos();
			setListaPruebas();
			setListaUsuariosBasicos();
			setListaImagenesUsuario();
			refrescar();
		}
		RootPanel.get("superFondoBlanco").setVisible(false);

	}

	private void estadoInicial() {
		panelPruebas.setEstadoInicial();
		panelVerProcesos.setEstadoInicial();
		panelProgramadores.setEstadoInicial();
		panelClientes.setEstadoInicial();
	}

	private void actualizarCookie() {
		String sessionID = usuarioMaestro.getSesionID();
		// 60 minutos * 60 segundos
		final long DURATION = 1000 * 60 * 60;
		Date expires = new Date(System.currentTimeMillis() + DURATION);
		Cookies.setCookie("sid", sessionID, expires, null, "/", false);
	}

	public static void setListaImagenesUsuario() {

		int identificador = usuarioMaestro.getIdentificador();
		if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_PROGRAMADOR)) {
			identificador = ((UsuarioProgramadorBO) usuarioMaestro)
					.getUsuarioAdministradorID();
		}
		usuarioMaestroService.consultarImagenesUsuario(identificador,
				new AsyncCallback<List<ImagenUsuarioBO>>() {

					@Override
					public void onSuccess(List<ImagenUsuarioBO> result) {

						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
							panelImagenes.actualizarImagenesUsuario(result);
							panelPruebas.actualizarImagenesUsuario(result);
						}
						if(usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)){
							panelPruebas.actualizarImagenesUsuario(result);							
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error cargando las imagenes");
					}
				});
	}

	public static void setListaClientes() {
		usuarioMaestroService
				.consultarUsuariosAdministradores(new AsyncCallback<List<UsuarioBO>>() {

					@Override
					public void onSuccess(List<UsuarioBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
							panelClientes
									.actualizarUsuariosAdministradores(UsuarioAdministradorListGridRecord
											.getRecords(result));
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}
				});
	}

	public static void setListaPruebas() {
		int identificador = usuarioMaestro.getIdentificador();
		if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_PROGRAMADOR)) {
			identificador = ((UsuarioProgramadorBO) usuarioMaestro)
					.getUsuarioAdministradorID();
		}
		usuarioMaestroService.consultarPruebasUsuarioAdministrador(
				identificador, new AsyncCallback<List<PruebaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PruebaUsuarioBO> result) {
						panelVerProcesos.actualizarListaPruebas(result);
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
								|| usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelPruebas.actualizarPruebas(result);
						}

						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
							panelClientes.actualizarPruebas(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void setListaProgramadores() {
		usuarioMaestroService.consultarProgramadores(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<UsuarioProgramadorBO>>() {

					@Override
					public void onSuccess(List<UsuarioProgramadorBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
								|| usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelProgramadores
									.actualizarUsuariosProgramadores(result);
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void setListaProcesos() {
		usuarioMaestroService.consultarProcesosUsuarioAdministrador(
				usuarioMaestro.getIdentificador(),
				new AsyncCallback<List<ProcesoUsuarioBO>>() {

					@Override
					public void onSuccess(List<ProcesoUsuarioBO> result) {
						panelVerProcesos.actualizarProcesos(ProcesoRecord
								.getRecords(result));
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error cargando los procesos");
					}
				});
	}

	public static void setListaProcesosDeRevision() {
		usuarioMaestroService
				.consultarProcesosParaRevisar(new AsyncCallback<List<ProcesoUsuarioBO>>() {

					@Override
					public void onSuccess(List<ProcesoUsuarioBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
							panelCuenta.actualizarListaProcesosRevisar(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error cargando los procesos");
					}
				});
	}

	public static void setListaUsuariosBasicos() {
		int identificador = usuarioMaestro.getIdentificador();
		if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_PROGRAMADOR)) {
			identificador = ((UsuarioProgramadorBO) usuarioMaestro)
					.getUsuarioAdministradorID();
		}
		usuarioMaestroService.consultarUsuariosBasicosUsuarioAdministrador(
				identificador, new AsyncCallback<List<EvaluadoBO>>() {

					@Override
					public void onSuccess(List<EvaluadoBO> result) {
						panelVerProcesos.actualizarListaUsuariosBasicos(result);
						panelEvaluados.actualizarUsuariosBasicos(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}
				});
	}

	public static void obtenerPruebasProceso(ProcesoUsuarioBO proceso) {
		usuarioMaestroService.consultarProceso((UsuarioBO) usuarioMaestro,
				proceso, new AsyncCallback<ProcesoUsuarioBO>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(ProcesoUsuarioBO result) {

						List<PruebaUsuarioBO> pruebas = new ArrayList<PruebaUsuarioBO>();
						for (ProcesoUsuarioHasPruebaUsuarioBO pHas : result
								.getProcesoUsuarioHasPruebaUsuario()) {
							pruebas.add(pHas.getPruebasUsuario());
						}
						panelVerProcesos.actualizarPruebasProceso(pruebas);
					}

				});
	}

	public static void obtenerPreguntasPorPrueba(int categoriaID) {
		usuarioMaestroService.consultarPreguntasPorCategoria(
				usuarioMaestro.getIdentificador(), categoriaID,
				new AsyncCallback<List<PreguntaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PreguntaUsuarioBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
								|| usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelPruebas.actualizarPreguntasCategoria(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out
								.println("Error cargando las preguntas de la categoria");
					}
				});
	}

	public static void agregarUsuarioBasico(int idUsuario,
			String nombreUsuario, String apellidosUsuario, String correoUsuario) {
		EvaluadoBO usuarioBasico = new EvaluadoBO();
		usuarioBasico.setApellidos(apellidosUsuario);
		usuarioBasico.setCorreoElectronico(correoUsuario);
		if (usuarioMaestro.getTipo().equalsIgnoreCase(
				Convencion.TIPO_USUARIO_PROGRAMADOR)) {
			usuarioBasico
					.setIdentificadorUsuarioAdministrador(((UsuarioProgramadorBO) usuarioMaestro)
							.getUsuarioAdministradorID());
		} else {
			usuarioBasico.setIdentificadorUsuarioAdministrador(usuarioMaestro
					.getIdentificador());
		}
		usuarioBasico.setIdentificadorUsuarioAdministrador(usuarioMaestro
				.getIdentificador());
		usuarioBasico.setNombres(nombreUsuario);
		usuarioBasico.setCedula(idUsuario);

		usuarioMaestroService.agregarUsuarioBasico(usuarioMaestro,
				usuarioBasico, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaUsuariosBasicos();
						} else {
							SC.warn("El evaluado no pudo ser agregado.");
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						SC.warn("Error conectando la base de datos.");

					}
				});

	}

	public static void eliminarUsuariosBasicos(List<EvaluadoBO> bo) {
		usuarioMaestroService.eliminarUsuariosBasicos(usuarioMaestro, bo,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaUsuariosBasicos();
							setListaProcesos();
						} else {
							SC.warn("El evaluado no pudo ser eliminado.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}
				});

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
						if (result == Convencion.CORRECTO) {
							setListaPruebas();
						} else {
							SC.warn("La prueba no pudo ser agregada.");
						}

					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error cargando las pruebas");

					}
				});

	}

	public static void eliminarPrueba(PruebaUsuarioBO prueba) {
		pruebaTemp = prueba;

		SC.ask("Precaución",
				"Esta prueba puede ser usada en algun proceso. Eliminar la prueba hará que no se encuentre disponible para el proceso. ¿Desea continuar?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value == true) {
							usuarioMaestroService.eliminarPrueba(
									usuarioMaestro, pruebaTemp,
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {

											caught.printStackTrace();
											System.out
													.println("Error eliminando la prueba");

										}

										@Override
										public void onSuccess(Integer result) {
											setListaPruebas();
											setListaProcesos();
										}
									});
						}

					}
				});

	}

	public static void agregarPreguntaUsuario(PreguntaUsuarioBO preguntaBO,
			PruebaUsuarioBO prueba) {
		pruebaTemp = prueba;
		usuarioMaestroService.agregarPreguntaAPrueba(usuarioMaestro, prueba,
				preguntaBO, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							obtenerPreguntasPorPrueba(pruebaTemp
									.getIdentificador());
						} else {
							SC.warn("La pregunta no pudo ser agregada a al prueba.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error agregando la pregunta");

					}
				});

	}

	public static void agregarParticipacionesAProceso(
			List<ParticipacionEnProcesoBO> evaluados, ProcesoUsuarioBO p) {
		procesoTemp = p;
		usuarioMaestroService.agregarParticipacionDeUsuarioBasicoAProceso(
				usuarioMaestro, p, evaluados, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaUsuariosBasicos();
							obtenerParticipantesProceso(procesoTemp);
						} else {
							SC.warn("No dispone de los usos suficientes para la cantidad de evaluados");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void agregarPruebasAProceso(List<PruebaUsuarioBO> pruebas,
			ProcesoUsuarioBO p) {
		procesoTemp = p;
		usuarioMaestroService.agregarPruebasAProceso(usuarioMaestro, p,
				pruebas, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							obtenerPruebasProceso(procesoTemp);
							setListaPruebas();
						} else {
							SC.warn("Las pruebas no pudieron ser agregadas al proceso.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out
								.println("Error agregando los temas al proceso");
					}
				});
	}

	public static void eliminarProceso(ProcesoUsuarioBO proceso) {
		procesoTemp = proceso;

		SC.ask("Precaución",
				"Este proceso puede estar siendo usado. ¿Desea continuar?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value == true) {
							usuarioMaestroService.eliminarProceso(
									usuarioMaestro, procesoTemp,
									new AsyncCallback<Integer>() {

										@Override
										public void onFailure(Throwable caught) {

											caught.printStackTrace();
											System.out
													.println("Error eliminado el proceso");
										}

										@Override
										public void onSuccess(Integer result) {
											if (result == Convencion.CORRECTO) {
												setListaProcesos();
											} else {
												SC.warn("El proceso no pudo ser eliminado.");
											}
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
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
								|| usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelImagenes.actualizarImagenesUsuario(result);
							panelPruebas.actualizarImagenesUsuario(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error cargando las imagenes");

					}
				});

	}

	public static void eliminarPreguntaCategoria(PreguntaUsuarioBO pregunta,
			PruebaUsuarioBO pruebaSeleccionada) {
		preguntaTemp = pregunta;
		pruebaTemp = pruebaSeleccionada;

		SC.ask("Precaución",
				"Esta pregunta es usada en algun proceso. Eliminar la pregunta hará que no se encuentre disponible para la prueba. ¿Desea continuar?",
				new BooleanCallback() {

					@Override
					public void execute(Boolean value) {
						if (value == true) {
							usuarioMaestroService.eliminarPregunta(
									usuarioMaestro, preguntaTemp,
									new AsyncCallback<Integer>() {

										@Override
										public void onSuccess(Integer result) {
											if (result == Convencion.CORRECTO) {
												obtenerPreguntasPorPrueba(pruebaTemp
														.getIdentificador());
											} else {
												SC.warn("La pregunta no pudo ser eliminada de la prueba.");
											}

										}

										@Override
										public void onFailure(Throwable caught) {

											caught.printStackTrace();
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
						panelVerProcesos.actualizarParticipaciones(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out
								.println("Error cargando las participaciones");

					}
				});
		usuarioMaestroService.consultarResultadosProceso(usuarioMaestro,
				proceso, new AsyncCallback<List<ParticipacionEnProcesoBO>>() {

					@Override
					public void onSuccess(List<ParticipacionEnProcesoBO> result) {
						panelVerProcesos.actualizarResultados(result);
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
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
						caught.printStackTrace();

					}

					@Override
					public void onSuccess(Void result) {
						Window.open("/Mind/PDFReportServlet", "_blank", "");

					}
				});

	}

	public static void generarReporteExcel(
			List<ParticipacionEnProcesoBO> participacionesBO) {

		usuarioMaestroService.generarReporte(participacionesBO,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}

					@Override
					public void onSuccess(Void result) {
						Window.open("/Mind/ExcelReportServlet", "_blank", "");

					}
				});

	}

	public static void generarReporteUsos(UsuarioBO usuarioSeleccionado,
			Date dateInicio, Date dateFinal) {
		usuarioMaestroService.generarReporteUsos(usuarioSeleccionado,
				dateInicio, dateFinal, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}

					@Override
					public void onSuccess(Void result) {
						Window.open("/Mind/UsosReportServlet", "_blank", "");

					}
				});
	}

	public static void editarPreguntaUsuario(PreguntaUsuarioBO bo,
			PruebaUsuarioBO prueba) {
		pruebaTemp = prueba;
		usuarioMaestroService.editarPregunta((UsuarioBO) usuarioMaestro, bo,
				prueba, new AsyncCallback<Integer>() {

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							obtenerPreguntasPorPrueba(pruebaTemp
									.getIdentificador());
						} else {
							SC.warn("La pregunta no pudo ser editada.");
						}
					}
				});

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
				arg0.printStackTrace();
				Window.Location.replace(urlLogin);
			}
		});
	}

	public static void agregarPrueba(PruebaUsuarioBO prueba) {
		usuarioMaestroService.agregarPrueba(usuarioMaestro, prueba,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaPruebas();
							setListaProcesos();
						} else {
							SC.warn("La prueba no pudo ser agregada.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}
				});

	}

	public static void agregarProceso(ProcesoUsuarioBO proceso) {
		usuarioMaestroService.agregarProceso(usuarioMaestro, proceso,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaProcesos();
							setListaUsuariosBasicos();
							setListaPruebas();
						} else {
							SC.warn("El proceso no pudo ser agregado.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
						System.out.println("Error agregando el proceso");

					}
				});
	}

	public static void eliminarParticipacionesDeProceso(
			ProcesoUsuarioBO procesoActual, List<ParticipacionEnProcesoBO> bo) {
		procesoTemp = procesoActual;
		usuarioMaestroService.eliminarParticipacionesDeProceso(
				(UsuarioBO) usuarioMaestro, procesoActual, bo,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaProcesos();
							obtenerParticipantesProceso(procesoTemp);
						} else {
							SC.warn("La participacion no pudo ser eliminada del proceso.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void eliminarPruebasDeProceso(ProcesoUsuarioBO procesoActual,
			List<PruebaUsuarioBO> bo) {
		procesoTemp = procesoActual;
		final int cantidad = bo.size();
		usuarioMaestroService.eliminarPruebasDeProceso(
				(UsuarioBO) usuarioMaestro, procesoActual, bo,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaProcesos();
							obtenerParticipantesProceso(procesoTemp);
						} else {
							if (cantidad > 1) {
								SC.warn("Las pruebas no pudieron ser eliminadas del proceso.");
							} else {
								SC.warn("La prueba no pudo ser eliminada del proceso.");
							}
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void eliminarImagen(ImagenUsuarioBO imagenSeleccionada) {
		// TODO Auto-generated method stub
	}

	public static void enviarNotificacionesCorreo(
			List<ParticipacionEnProcesoBO> p) {
		usuarioMaestroService.enviarNotificacionesParticipacionesProceso(
				usuarioMaestro, p, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						panelVerProcesos.desactivarDialogoNotificaciones();
						if (result == Convencion.CORRECTO) {
							setListaProcesos();
						} else {
							SC.warn("Las notificaciones no pudieron ser enviadas.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void agregarCliente(UsuarioBO usuario, UsoBO usos,
			List<PruebaUsuarioBO> pruebas) {

		usuarioMaestroService.agregarCuenta(usuarioMaestro, usuario, usos,
				pruebas, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						panelClientes.desactivarDialogoCreacionClientes();
						if (result == Convencion.CORRECTO) {
							setListaClientes();
						} else {
							SC.warn("El cliente no pudo ser creado");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void duplicarProceso(ProcesoUsuarioBO proceso) {
		usuarioMaestroService.duplicarProceso((UsuarioBO) usuarioMaestro,
				proceso, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaProcesos();
						} else {
							SC.warn("El proceso no pudo ser duplicado.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void duplicarPrueba(PruebaUsuarioBO prueba) {
		usuarioMaestroService.duplicarPrueba((UsuarioBO) usuarioMaestro,
				prueba, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {

							setListaPruebas();
						} else {
							SC.warn("La prueba no pudo ser duplicada.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void editarProceso(ProcesoUsuarioBO proceso) {
		procesoTemp = proceso;
		usuarioMaestroService.editarProceso((UsuarioBO) usuarioMaestro,
				proceso, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {

							setListaProcesos();
							obtenerParticipantesProceso(procesoTemp);
							obtenerPruebasProceso(procesoTemp);
						} else {
							SC.warn("El proceso no pudo ser editado.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void editarPrueba(PruebaUsuarioBO prueba) {
		usuarioMaestroService.editarPrueba((UsuarioBO) usuarioMaestro, prueba,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaPruebas();
							setListaProcesos();
						} else {
							SC.warn("La prueba no pudo ser editada.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void editarEvaluado(EvaluadoBO proceso) {
		usuarioMaestroService.editarEvaluado(usuarioMaestro, proceso,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {

							setListaUsuariosBasicos();
							setListaProcesos();
						} else {
							SC.warn("El evaluado no pudo ser editado.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	private static void refrescar() {
		timerRefrescar = new Timer() {
			@Override
			public void run() {
				setListaProcesos();
				if (procesoTemp != null) {
					obtenerParticipantesProceso(procesoTemp);
				}
				if (usuarioMaestro.getTipo().equalsIgnoreCase(
						Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
					obtenerUsosCliente(usuarioMaestro);
				}
			}
		};
		timerRefrescar.scheduleRepeating(120000);

	}

	public static void obtenerUsosCliente(UsuarioBO cliente) {
		usuarioMaestroService.consultarUsos(cliente,
				new AsyncCallback<List<UsoBO>>() {

					@Override
					public void onSuccess(List<UsoBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
							panelClientes.actualizarUsosCliente(result);
						} else if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelCuenta.actualizarListaUsos(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void obtenerPruebasCliente(UsuarioBO cliente) {
		usuarioMaestroService.consultarPruebasUsuarioAdministrador(
				cliente.getIdentificador(),
				new AsyncCallback<List<PruebaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PruebaUsuarioBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
							panelClientes.actualizarPruebasCliente(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void consultarProcesosClave(String keyword) {

		usuarioMaestroService.consultarProcesosUsuarioAdministradorNombre(
				usuarioMaestro.getIdentificador(), keyword,
				new AsyncCallback<List<ProcesoUsuarioBO>>() {

					@Override
					public void onSuccess(List<ProcesoUsuarioBO> result) {
						panelVerProcesos.actualizarProcesos(ProcesoRecord
								.getRecords(result));
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void consultarPruebasClave(String keyword) {
		usuarioMaestroService.consultarPruebasUsuarioAdministradorNombre(
				usuarioMaestro.getIdentificador(), keyword,
				new AsyncCallback<List<PruebaUsuarioBO>>() {

					@Override
					public void onSuccess(List<PruebaUsuarioBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
								|| usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelPruebas.actualizarPruebas(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void consultarEvaluadosClave(String keyword) {
		try {
			int valor = Integer.parseInt(keyword);
			usuarioMaestroService.consultarEvaluadoPorCedula(
					usuarioMaestro.getIdentificador(), valor,
					new AsyncCallback<List<EvaluadoBO>>() {

						@Override
						public void onSuccess(List<EvaluadoBO> result) {
							if (usuarioMaestro.getTipo().equalsIgnoreCase(
									Convencion.TIPO_USUARIO_MAESTRO)
									|| usuarioMaestro
											.getTipo()
											.equalsIgnoreCase(
													Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
									|| usuarioMaestro
											.getTipo()
											.equalsIgnoreCase(
													Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
								panelEvaluados
										.actualizarUsuariosBasicos(result);
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}
					});
		} catch (NumberFormatException e) {
			usuarioMaestroService.consultarEvaluadoPorCorreo(
					usuarioMaestro.getIdentificador(), keyword,
					new AsyncCallback<List<EvaluadoBO>>() {

						@Override
						public void onSuccess(List<EvaluadoBO> result) {
							if (usuarioMaestro.getTipo().equalsIgnoreCase(
									Convencion.TIPO_USUARIO_MAESTRO)
									|| usuarioMaestro
											.getTipo()
											.equalsIgnoreCase(
													Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
									|| usuarioMaestro
											.getTipo()
											.equalsIgnoreCase(
													Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
								panelEvaluados
										.actualizarUsuariosBasicos(result);
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}
					});
		}

	}

	public static void consultarClientesClave(String keyword) {
		try {
			int valor = Integer.parseInt(keyword);
			usuarioMaestroService.consultarClientePorCedula(
					usuarioMaestro.getIdentificador(), valor,
					new AsyncCallback<List<UsuarioBO>>() {

						@Override
						public void onSuccess(List<UsuarioBO> result) {
							if (usuarioMaestro.getTipo().equalsIgnoreCase(
									Convencion.TIPO_USUARIO_MAESTRO)
									|| usuarioMaestro
											.getTipo()
											.equalsIgnoreCase(
													Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
								panelClientes
										.actualizarUsuariosAdministradores(UsuarioAdministradorListGridRecord
												.getRecords(result));
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}
					});
		} catch (NumberFormatException e) {
			usuarioMaestroService.consultarClientePorEmpresa(
					usuarioMaestro.getIdentificador(), keyword,
					new AsyncCallback<List<UsuarioBO>>() {

						@Override
						public void onSuccess(List<UsuarioBO> result) {
							if (usuarioMaestro.getTipo().equalsIgnoreCase(
									Convencion.TIPO_USUARIO_MAESTRO)
									|| usuarioMaestro
											.getTipo()
											.equalsIgnoreCase(
													Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)) {
								panelClientes
										.actualizarUsuariosAdministradores(UsuarioAdministradorListGridRecord
												.getRecords(result));
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();
						}
					});
		}

	}

	public static void agregarProgramador(UsuarioProgramadorBO programador) {
		programador.setTipo(Convencion.TIPO_USUARIO_PROGRAMADOR);
		programador
				.setUsuarioAdministradorID(usuarioMaestro.getIdentificador());
		programador.setEstado_Cuenta(Convencion.ESTADO_CUENTA_ACTIVA);
		programador.setEmpresa(usuarioMaestro.getEmpresa());

		usuarioMaestroService.agregarProgramador(usuarioMaestro, programador,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						panelProgramadores.desactivarDialogoCreacionClientes();
						if (result == Convencion.CORRECTO) {
							setListaProgramadores();
						} else {
							SC.warn("El programador no pudo ser agregado.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void obtenerProcesosProgramador(UsuarioBO programador) {
		usuarioMaestroService.consultarProcesosUsuarioAdministrador(
				programador.getIdentificador(),
				new AsyncCallback<List<ProcesoUsuarioBO>>() {

					@Override
					public void onSuccess(List<ProcesoUsuarioBO> result) {
						if (usuarioMaestro.getTipo().equalsIgnoreCase(
								Convencion.TIPO_USUARIO_MAESTRO)
								|| usuarioMaestro
										.getTipo()
										.equalsIgnoreCase(
												Convencion.TIPO_USUARIO_MAESTRO_PRINCIPAL)
								|| usuarioMaestro.getTipo().equalsIgnoreCase(
										Convencion.TIPO_USUARIO_ADMINISTRADOR)) {
							panelProgramadores
									.actualizarProcesosProgramador(result);
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});

	}

	public static void AgregarUsos(UsuarioBO usuarioSeleccionado, int usos) {
		clienteTemp = usuarioSeleccionado;
		usuarioMaestroService.agregarUsos(usuarioSeleccionado, usos,
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {

							obtenerUsosCliente(clienteTemp);
						} else {
							SC.warn("Los usos no pudieron ser asignados al cliente.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void cambiarContrasena(String pass) {
		usuarioMaestroService.cambiarContrasena(usuarioMaestro,
				Generador.convertirStringmd5(pass),
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						obtenerUsosCliente(clienteTemp);
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();
					}
				});
	}

	public static void eliminarCuentaCliente(UsuarioBO cliente) {
		usuarioMaestroService.eliminarCuenta(cliente.getIdentificador(),
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {

							setListaClientes();
						} else {
							SC.warn("La cuenta del cliente no pudo ser eliminada.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}
				});
	}

	public static void cambiarEstadoCuentaCliente(UsuarioBO cliente,
			boolean activo) {
		if (!activo) {
			usuarioMaestroService.desactivarCuenta(cliente.getIdentificador(),
					new AsyncCallback<Integer>() {
						@Override
						public void onSuccess(Integer result) {
							if (result == Convencion.CORRECTO) {

								setListaClientes();
							} else {
								SC.warn("La cuenta del cliente no pudo ser desactivada.");
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();

						}
					});
		} else {
			usuarioMaestroService.activarCuenta(cliente.getIdentificador(),
					new AsyncCallback<Integer>() {
						@Override
						public void onSuccess(Integer result) {
							if (result == Convencion.CORRECTO) {

								setListaClientes();
							} else {
								SC.warn("La cuenta del cliente no pudo ser activada.");
							}
						}

						@Override
						public void onFailure(Throwable caught) {
							caught.printStackTrace();

						}
					});
		}
	}

	public static void eliminarProgramador(UsuarioBO programador) {
		usuarioMaestroService.eliminarCuenta(programador.getIdentificador(),
				new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {
						if (result == Convencion.CORRECTO) {
							setListaProgramadores();
						} else {
							SC.warn("La cuenta de programador no pudo ser eliminada.");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						caught.printStackTrace();

					}
				});
	}
}
