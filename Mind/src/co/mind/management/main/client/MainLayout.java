package co.mind.management.main.client;

import java.util.List;

import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.PreguntaUsuarioBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.ProcesoRecord;

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

public class MainLayout extends VLayout {

	private UsuarioBO usuario;
	private VLayout layoutContenido;
	private VLayout panelDashboard;

	public MainLayout(UsuarioBO usuario) {
		this.usuario = usuario;
		setAlign(Alignment.CENTER);
		setAlign(VerticalAlignment.CENTER);
		setSize("100%", "100%");
		setBackgroundColor("transparent");
		inicializarMenuBar();
	}

	private void inicializarMenuBar() {

		Img imagenLogo = new Img("insumos/navegador/logo.png", 62, 19);
		ToolStrip menuBarUsuarioBasico = new ToolStrip();

		Menu menuUsuario = new Menu();
		MenuItem menuItemUsos = new MenuItem("Informaci\u00f3n de la Cuenta");
		menuItemUsos.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
			}
		});
		MenuItem menuItemCerrarSesion = new MenuItem("Cerrar Sesi\u00f3n");
		menuItemCerrarSesion.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(MenuItemClickEvent event) {
				Main.cerrarSesion();
			}
		});
		menuUsuario.addItem(menuItemUsos);
		menuUsuario.addItem(menuItemCerrarSesion);

		ToolStripMenuButton toolStripMenuUsuario = new ToolStripMenuButton(
				usuario.getNombres() + " " + usuario.getApellidos(),
				menuUsuario);

		ToolStripButton toolStripButtonProcesos = new ToolStripButton();
		toolStripButtonProcesos.setIcon("insumos/navegador/procesos.png");
		toolStripButtonProcesos.setTooltip("Procesos");
		toolStripButtonProcesos
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
					}
				});

		ToolStripButton toolStripButtonTemas = new ToolStripButton();
		toolStripButtonTemas.setIcon("insumos/navegador/temas.png");
		toolStripButtonTemas.setTooltip("Pruebas");
		toolStripButtonTemas
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
					}
				});
		ToolStripButton toolStripButtonEvaluados = new ToolStripButton(
				"Evaluados");
		toolStripButtonEvaluados
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
					}
				});

		ToolStripButton toolStripButtonProgramadores = new ToolStripButton(
				"Programadores");
		toolStripButtonProgramadores
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
					}
				});

		ToolStripButton toolStripButtonHome = new ToolStripButton("Home");
		toolStripButtonHome
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
					}
				});

		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addMember(imagenLogo);
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addButton(toolStripButtonProcesos);
		menuBarUsuarioBasico.addButton(toolStripButtonTemas);
		menuBarUsuarioBasico.addButton(toolStripButtonEvaluados);
		menuBarUsuarioBasico.addButton(toolStripButtonProgramadores);
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

		panelDashboard = new VLayout();
		panelDashboard.setSize("100%", "80%");
		panelDashboard.setAlign(Alignment.CENTER);
		panelDashboard.setAlign(VerticalAlignment.CENTER);

		HLayout h = new HLayout();
		h.setSize("100%", "100%");
		h.setAlign(Alignment.CENTER);
		h.setAlign(VerticalAlignment.CENTER);

		Img imagenDashboard = new Img("insumos/dashboard-fondo.png", 960, 641);
		imagenDashboard.setAlign(Alignment.CENTER);

		h.addMember(imagenDashboard);
		panelDashboard.addMember(h);

		layoutContenido.addMember(panelDashboard);

	}

}
