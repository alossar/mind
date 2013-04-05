package co.mind.management.maestro.client;

import java.util.List;

import co.mind.management.maestro.client.administradores.PanelClientes;
import co.mind.management.maestro.client.cuenta.PanelCuenta;
import co.mind.management.maestro.client.evaluados.PanelEvaluados;
import co.mind.management.maestro.client.imagenes.PanelImagenes;
import co.mind.management.maestro.client.procesos.PanelProcesos;
import co.mind.management.maestro.client.programadores.PanelProgramadores;
import co.mind.management.maestro.client.pruebas.PanelPruebas;
import co.mind.management.shared.bo.EvaluadoBO;
import co.mind.management.shared.bo.ImagenUsuarioBO;
import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.PreguntaUsuarioBO;
import co.mind.management.shared.bo.ProcesoUsuarioBO;
import co.mind.management.shared.bo.PruebaUsuarioBO;
import co.mind.management.shared.bo.UsuarioBO;
import co.mind.management.shared.records.ImagenRecord;
import co.mind.management.shared.records.ParticipacionEnProcesoListGridRecord;
import co.mind.management.shared.records.PreguntaCategoriaTileRecord;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.records.PruebaListGridRecord;
import co.mind.management.shared.records.UsuarioBasicoListGridRecord;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.toolbar.ToolStripMenuButton;

public class MaestroMainLayout extends VLayout {

	private UsuarioBO usuario;
	private VLayout layoutContenido;
	private PanelProcesos panelVerProcesos;
	private PanelPruebas panelPruebas;
	private PanelEvaluados panelEvaluados;
	private PanelProgramadores panelProgramadores;
	private PanelClientes panelClientes;
	private PanelImagenes panelImagenes;
	private PanelCuenta panelCuenta;
	private VLayout panelDashboard;

	public MaestroMainLayout(UsuarioBO usuario) {
		this.usuario = usuario;
		setAlign(Alignment.CENTER);
		setAlign(VerticalAlignment.CENTER);
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		inicializarMenuBar();
	}

	private void inicializarMenuBar() {

		cargarLayouts();

		Img imagenLogo = new Img("insumos/navegador/logo.png");
		ToolStrip menuBarUsuarioBasico = new ToolStrip();

		Menu menuUsuario = new Menu();
		MenuItem menuItemUsos = new MenuItem("Informaci\u00f3n de la Cuenta");
		menuItemUsos.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				estadoInicial();
				cargarLayoutCuenta();
			}
		});
		MenuItem menuItemCerrarSesion = new MenuItem("Cerrar Sesi\u00f3n");
		menuItemCerrarSesion.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Maestro.cerrarSesion();
			}
		});
		menuUsuario.addItem(menuItemUsos);
		menuUsuario.addItem(menuItemCerrarSesion);

		ToolStripMenuButton toolStripMenuUsuario = new ToolStripMenuButton(
				usuario.getNombres() + " " + usuario.getApellidos(),
				menuUsuario);

		ToolStripButton toolStripButtonProcesosEvaluacion = new ToolStripButton();
		toolStripButtonProcesosEvaluacion
				.setSrc("insumos/navegador/procesos.png");
		toolStripButtonProcesosEvaluacion
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutProcesos();
					}
				});

		ToolStripButton toolStripButtonTemas = new ToolStripButton("Pruebas");
		toolStripButtonTemas
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutTemas();
					}
				});
		ToolStripButton toolStripButtonEvaluados = new ToolStripButton(
				"Evaluados");
		toolStripButtonEvaluados
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutEvaluados();
					}
				});
		ToolStripButton toolStripButtonClientes = new ToolStripButton(
				"Clientes");
		toolStripButtonClientes
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutClientes();
					}
				});
		ToolStripButton toolStripButtonProgramadores = new ToolStripButton(
				"Programadores");
		toolStripButtonProgramadores
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutProgramadores();
					}
				});
		ToolStripButton toolStripButtonLaminas = new ToolStripButton("Láminas");
		toolStripButtonLaminas
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutImagenes();
					}
				});
		ToolStripButton toolStripButtonHome = new ToolStripButton("Home");
		toolStripButtonHome
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						estadoInicial();
						cargarLayoutHome();
					}
				});

		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addMember(imagenLogo);
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addButton(toolStripButtonProcesosEvaluacion);
		menuBarUsuarioBasico.addButton(toolStripButtonTemas);
		menuBarUsuarioBasico.addButton(toolStripButtonEvaluados);
		menuBarUsuarioBasico.addButton(toolStripButtonProgramadores);
		menuBarUsuarioBasico.addButton(toolStripButtonClientes);
		menuBarUsuarioBasico.addButton(toolStripButtonLaminas);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addMember(toolStripButtonHome);
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addMember(toolStripMenuUsuario);

		addMember(menuBarUsuarioBasico);
		addMember(layoutContenido);

	}

	private void cargarLayouts() {

		layoutContenido = new VLayout();
		layoutContenido.setSize("100%", "100%");

		panelVerProcesos = new PanelProcesos();
		panelPruebas = new PanelPruebas();
		panelEvaluados = new PanelEvaluados();
		panelProgramadores = new PanelProgramadores();
		panelClientes = new PanelClientes();
		panelImagenes = new PanelImagenes();
		panelCuenta = new PanelCuenta(usuario);

		panelDashboard = new VLayout();
		panelDashboard.setSize("100%", "80%");
		panelDashboard.setAlign(Alignment.CENTER);
		panelDashboard.setAlign(VerticalAlignment.CENTER);

		HLayout h = new HLayout();
		h.setSize("100%", "100%");
		h.setAlign(Alignment.CENTER);
		h.setAlign(VerticalAlignment.CENTER);

		Img imagenDashboard = new Img("img/dashboard.png", 1024, 600);
		imagenDashboard.setAlign(Alignment.CENTER);

		h.addMember(imagenDashboard);
		panelDashboard.addMember(h);

		layoutContenido.addMember(panelPruebas);
		layoutContenido.addMember(panelVerProcesos);
		layoutContenido.addMember(panelCuenta);
		layoutContenido.addMember(panelImagenes);
		layoutContenido.addMember(panelEvaluados);
		layoutContenido.addMember(panelClientes);
		layoutContenido.addMember(panelProgramadores);
		layoutContenido.addMember(panelDashboard);

		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelCuenta.setVisible(false);
		panelEvaluados.setVisible(false);
		panelClientes.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);

	}

	private void cargarLayoutClientes() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(true);
	}

	private void cargarLayoutCuenta() {
		panelCuenta.setVisible(true);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(false);
	}

	private void cargarLayoutProcesos() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(true);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(false);
	}

	private void cargarLayoutTemas() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(true);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(false);
	}

	private void cargarLayoutHome() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(true);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(false);
		estadoInicial();
	}

	private void cargarLayoutEvaluados() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(true);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(false);
	}

	private void cargarLayoutProgramadores() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(true);
		panelImagenes.setVisible(false);
		panelClientes.setVisible(false);
	}

	private void cargarLayoutImagenes() {
		panelCuenta.setVisible(false);
		panelPruebas.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
		panelEvaluados.setVisible(false);
		panelProgramadores.setVisible(false);
		panelImagenes.setVisible(true);
		panelClientes.setVisible(false);
	}

	private void estadoInicial() {
		panelPruebas.setEstadoInicial();
		panelVerProcesos.setEstadoInicial();
	}

	public void actualizarProcesos(List<ProcesoUsuarioBO> result) {
		panelVerProcesos.actualizarProcesos(ProcesoRecord.getRecords(result));
	}

	public void actualizarUsuariosBasicos(List<EvaluadoBO> result) {
		panelVerProcesos.actualizarListaUsuariosBasicos(result);
		panelEvaluados.actualizarListaUsuariosBasicos(result);
	}

	public void actualizarImagenesUsuario(List<ImagenUsuarioBO> result) {
		panelImagenes.actualizarImagenesUsuario(result);
		panelPruebas.actualizarImagenesUsuario(result);
	}

	public void actualizarPreguntasPrueba(List<PreguntaUsuarioBO> result) {
		panelPruebas.actualizarPreguntasCategoria(result);
	}

	public void actualizarResultados(List<ParticipacionEnProcesoBO> result) {
		panelVerProcesos.actualizarResultados(result);
	}

	public void actualizarParticipaciones(List<ParticipacionEnProcesoBO> result) {
		panelVerProcesos.actualizarParticipaciones(result);
	}

	public void actualizarPruebas(List<PruebaUsuarioBO> result) {
		panelPruebas.actualizarPruebas(result);
		panelVerProcesos.actualizarListaPruebas(result);
	}

	public void actualizarTemasProceso(List<PruebaUsuarioBO> result) {
		panelVerProcesos.actualizarTemasProceso(result);
	}

}
