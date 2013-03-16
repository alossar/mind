package co.mind.management.maestro.client;

import java.util.List;

import co.mind.management.maestro.client.procesos.PanelProcesos;
import co.mind.management.maestro.client.temas.PanelCategorias;
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
	private PanelAdministracion panelAdministracion;
	private PanelCategorias panelCategorias;
	private PanelProcesos panelVerProcesos;
	private List<ProcesoUsuarioBO> listaProcesos;
	private List<EvaluadoBO> listaUsuariosBasicos;
	private List<ImagenUsuarioBO> listaImagenes;
	private List<PruebaUsuarioBO> listaPruebas;
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

		Img imagenLogo = new Img("img/Admin/logo.png");
		ToolStrip menuBarUsuarioBasico = new ToolStrip();

		Menu menuUsuario = new Menu();
		MenuItem menuItemUsos = new MenuItem("Informaci\u00f3n de la Cuenta");
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

		ToolStripButton toolStripButtonProcesosEvaluacion = new ToolStripButton(
				"Procesos de Evaluaci\u00f3n");
		toolStripButtonProcesosEvaluacion
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						cargarLayoutProcesos();

					}
				});
		ToolStripButton toolStripButtonEvaluaciones = new ToolStripButton(
				"Evaluaciones");
		toolStripButtonEvaluaciones
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						cargarLayoutEvaluaciones();

					}
				});
		ToolStripButton toolStripButtonTemas = new ToolStripButton("Temas");
		toolStripButtonTemas
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						cargarLayoutTemas();
					}
				});
		ToolStripButton toolStripButtonEvaluados = new ToolStripButton(
				"Evaluados");
		toolStripButtonEvaluados
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						cargarLayoutAdministracion();
					}
				});
		ToolStripButton toolStripButtonHome = new ToolStripButton("Home");
		toolStripButtonHome
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						cargarLayoutHome();
					}
				});

		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addMember(imagenLogo);
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addButton(toolStripButtonProcesosEvaluacion);
		menuBarUsuarioBasico.addButton(toolStripButtonEvaluaciones);
		menuBarUsuarioBasico.addButton(toolStripButtonTemas);
		menuBarUsuarioBasico.addButton(toolStripButtonEvaluados);
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

		panelAdministracion = new PanelAdministracion();
		panelCategorias = new PanelCategorias();
		panelVerProcesos = new PanelProcesos();

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

		layoutContenido.addMember(panelAdministracion);
		layoutContenido.addMember(panelCategorias);
		layoutContenido.addMember(panelVerProcesos);
		h.addMember(imagenDashboard);
		panelDashboard.addMember(h);
		layoutContenido.addMember(panelDashboard);

		panelAdministracion.setVisible(false);
		panelCategorias.setVisible(false);
		panelVerProcesos.setVisible(false);

	}

	private void cargarLayoutAdministracion() {
		panelAdministracion.setVisible(true);
		panelCategorias.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
	}

	private void cargarLayoutProcesos() {
		panelAdministracion.setVisible(false);
		panelCategorias.setVisible(false);
		panelVerProcesos.setVisible(true);
		panelDashboard.setVisible(false);
	}

	private void cargarLayoutEvaluaciones() {
		panelAdministracion.setVisible(false);
		panelCategorias.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
	}

	private void cargarLayoutTemas() {
		panelAdministracion.setVisible(false);
		panelCategorias.setVisible(true);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(false);
	}

	private void cargarLayoutHome() {
		panelAdministracion.setVisible(false);
		panelCategorias.setVisible(false);
		panelVerProcesos.setVisible(false);
		panelDashboard.setVisible(true);
	}

	public void actualizarProcesos(List<ProcesoUsuarioBO> result) {
		listaProcesos = result;
		panelVerProcesos.actualizarProcesos(ProcesoRecord
				.getRecords(listaProcesos));
	}

	public void actualizarUsuariosBasicos(List<EvaluadoBO> result) {
		listaUsuariosBasicos = result;
		panelAdministracion
				.actualizarUsuariosBasicos(UsuarioBasicoListGridRecord
						.getRecords(listaUsuariosBasicos));
		panelVerProcesos.actualizarListaUsuariosBasicos(listaUsuariosBasicos);
	}

	public void actualizarImagenesUsuario(List<ImagenUsuarioBO> result) {
		panelCategorias.actualizarImagenesUsuario(ImagenRecord
				.getRecord(result));
	}

	public void actualizarPreguntasCategoria() {
		panelCategorias.actualizarPreguntasCategoria();
	}

	public void actualizarPreguntasCategoria(List<PreguntaUsuarioBO> result) {
		panelCategorias
				.actualizarPreguntasCategoria(PreguntaCategoriaTileRecord
						.getRecords(result));

	}

	public void actualizarResultados(List<ParticipacionEnProcesoBO> result) {
		if (result == null) {
			System.out.println("resultados nulos");
		}
		panelVerProcesos
				.actualizarResultados(ParticipacionEnProcesoListGridRecord
						.getRecords(result));

	}

	public void actualizarParticipaciones(List<ParticipacionEnProcesoBO> result) {
		panelVerProcesos
				.actualizarParticipaciones(ParticipacionEnProcesoListGridRecord
						.getRecords(result));

	}

}
