package co.mind.management.maestro.client.procesos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.HorizontalAlignment;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.EvaluadoBO;
import co.mind.management.shared.dto.ParticipacionEnProcesoBO;
import co.mind.management.shared.dto.ProcesoUsuarioBO;
import co.mind.management.shared.dto.PruebaUsuarioBO;
import co.mind.management.shared.dto.UsuarioBO;
import co.mind.management.shared.records.ProcesoRecord;
import co.mind.management.shared.recursos.Convencion;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.FormItemIfFunction;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.PickerIcon;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.IconClickHandler;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.CellDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class PanelContenidoProcesos extends HLayout {

	private ListGrid listGridProcesos;
	private ToolStripButton botonRegresar;
	private ToolStripButton botonNuevoBasico;
	private ToolStripButton botonEliminarBasico;
	private ToolStripButton botonDuplicarBasico;
	private PanelProcesoEspecifico panelProcesoEspecifico;
	private ProcesoUsuarioBO procesoSeleccionado;
	private DynamicForm formNuevoProceso;
	private TextItem textNombreProcesoNuevo;
	private TextAreaItem textAreaDescripcionProcesoNuevo;
	private DateItem dateTimeItemFechaInicioNuevo;
	private DateItem dateTimeItemFechaFinalizacionNuevo;
	private CheckboxItem checkBoxHabilitarFechaFinalizacion;
	private List<PruebaUsuarioBO> listaPruebas;
	private List<EvaluadoBO> listaEvaluados;
	private TextItem searchItem;
	private DynamicForm formBusqueda;
	private UsuarioBO usuario;

	public PanelContenidoProcesos(UsuarioBO usuarioS) {
		this.usuario = usuarioS;
		setWidth("90%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		listGridProcesos = new ListGrid();
		listGridProcesos.setWidth100();
		listGridProcesos.setHeight100();
		listGridProcesos.setShowAllRecords(true);
		listGridProcesos.setWrapCells(true);  
		listGridProcesos.setFixedRecordHeights(false); 
		listGridProcesos.setSelectionType(SelectionStyle.SINGLE);
		listGridProcesos
				.setEmptyMessage("No se encuentran procesos en su cuenta.");

		ListGridField nombreField = new ListGridField("nombreProceso", "Nombre");
		ListGridField descripcionField = new ListGridField(
				"descripcionProceso", "Descripci\u00F3n");
		ListGridField fechaField = new ListGridField("fechaCreacion",
				"Fecha de Creaci\u00F3n");
		fechaField.setCanEdit(false);
		ListGridField preguntasField = new ListGridField("cantidadPreguntas",
				"Preguntas");
		preguntasField.setCanEdit(false);
		ListGridField tiempoField = new ListGridField("tiempoProceso",
				"Tiempo (Minutos)");
		tiempoField.setCanEdit(false);

		ListGridField estadoProceso = new ListGridField("estadoProceso",
				"Estado del Proceso");
		estadoProceso.setCanEdit(false);
		listGridProcesos.setFields(nombreField, descripcionField,
				estadoProceso, fechaField, preguntasField, tiempoField);
		listGridProcesos.setCanEdit(true);
		listGridProcesos.setEditEvent(ListGridEditEvent.NONE);
		listGridProcesos.setCanResizeFields(true);

		listGridProcesos
				.addCellDoubleClickHandler(new CellDoubleClickHandler() {

					@Override
					public void onCellDoubleClick(CellDoubleClickEvent event) {
						botonRegresar.setVisible(true);
						ProcesoRecord p = (ProcesoRecord) event.getRecord();
						if (p != null) {
							procesoSeleccionado = ProcesoRecord.getBO(p);
							Maestro.obtenerParticipantesProceso(procesoSeleccionado);
							Maestro.obtenerPruebasProceso(procesoSeleccionado);
							panelProcesoEspecifico.deseleccionarTodo();
							panelProcesoEspecifico
									.actualizarDatosProceso(procesoSeleccionado);
							listGridProcesos.setVisible(false);
							panelProcesoEspecifico.setVisible(true);
							botonNuevoBasico.setVisible(false);
							botonEliminarBasico.setVisible(false);
							botonDuplicarBasico.setVisible(false);
							formBusqueda.setVisible(false);
						}
					}
				});

		listGridProcesos
				.addRowContextClickHandler(new RowContextClickHandler() {
					public void onRowContextClick(RowContextClickEvent event) {
						if (listGridProcesos.startEditing(event.getRowNum(),
								event.getColNum(), false)) {
						}
						event.cancel();
					}
				});

		listGridProcesos.addEditCompleteHandler(new EditCompleteHandler() {

			@Override
			public void onEditComplete(EditCompleteEvent event) {
				ListGridRecord[] r = listGridProcesos.getRecords();
				ProcesoRecord procesoRecord = (ProcesoRecord) r[event
						.getRowNum()];
				ProcesoUsuarioBO proceso = ProcesoRecord.getBO(procesoRecord);
				Maestro.editarProceso(proceso);
			}
		});

		panelProcesoEspecifico = new PanelProcesoEspecifico(usuario);
		panelProcesoEspecifico.setHeight100();
		panelProcesoEspecifico.setWidth100();
		panelProcesoEspecifico.setVisible(false);

		botonRegresar = new ToolStripButton("Volver",
				"icons/16/document_plain_new.png");

		botonRegresar
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						setEstadoInicial();
					}

				});

		botonNuevoBasico = new ToolStripButton("Nuevo Proceso",
				"icons/16/document_plain_new.png");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearProceso();
					}

				});

		botonEliminarBasico = new ToolStripButton("Eliminar Proceso",
				"icons/16/document_plain_new.png");

		botonEliminarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						ProcesoRecord record = (ProcesoRecord) listGridProcesos
								.getSelectedRecord();
						if (record != null) {
							ProcesoUsuarioBO categoria = ProcesoRecord
									.getBO(record);
							Maestro.eliminarProceso(categoria);
						} else {
							SC.warn("Debe seleccionar el proceso que desea eliminar.");
						}

					}
				});

		botonDuplicarBasico = new ToolStripButton("Duplicar Proceso",
				"icons/16/document_plain_new.png");

		botonDuplicarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						final ProcesoRecord record = (ProcesoRecord) listGridProcesos
								.getSelectedRecord();
						if (record != null) {

							final Window winModal = new Window();

							PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
									"Duplicar Proceso",
									"Duplique un proceso con sus pruebas sin los evaluados",
									"img/admin/bot1.png");
							p.setSize("100%", "70px");

							winModal.setWidth(350);
							winModal.setHeight(320);
							winModal.setTitle("Duplicar el Proceso");
							winModal.setShowMinimizeButton(false);
							winModal.setIsModal(true);
							winModal.setShowModalMask(true);
							winModal.centerInPage();
							winModal.addCloseClickHandler(new CloseClickHandler() {
								@Override
								public void onCloseClick(CloseClickEvent event) {
									winModal.destroy();
								}
							});

							formNuevoProceso = new DynamicForm();
							formNuevoProceso.setHeight("40%");
							formNuevoProceso.setWidth100();
							formNuevoProceso.setPadding(5);
							formNuevoProceso
									.setLayoutAlign(VerticalAlignment.BOTTOM);

							textNombreProcesoNuevo = new TextItem();
							textNombreProcesoNuevo.setTitle("Nombre");
							textNombreProcesoNuevo.setRequired(true);

							textAreaDescripcionProcesoNuevo = new TextAreaItem();
							textAreaDescripcionProcesoNuevo
									.setTitle("Descripci\u00F3n");

							ButtonItem boton = new ButtonItem("Crear");
							boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									if (formNuevoProceso.validate()) {
										String nombre = textNombreProcesoNuevo
												.getValueAsString();
										String descripcion = textAreaDescripcionProcesoNuevo
												.getValueAsString();
										ProcesoUsuarioBO proceso = ProcesoRecord
												.getBO(record);
										proceso.setNombre(nombre);
										if (descripcion != null) {
											proceso.setDescripcion(descripcion);
										}
										Maestro.duplicarProceso(proceso);
										winModal.destroy();

									}
								}
							});

							formNuevoProceso.setFields(textNombreProcesoNuevo,
									textAreaDescripcionProcesoNuevo, boton);

							winModal.addItem(p);
							winModal.addItem(formNuevoProceso);
							winModal.show();
						} else {
							SC.warn("Debe seleccionar el proceso que desea eliminar.");
						}
					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonRegresar);
		menuBarUsuarioBasico.addButton(botonNuevoBasico);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addButton(botonDuplicarBasico);
		menuBarUsuarioBasico.addButton(botonEliminarBasico);
		menuBarUsuarioBasico.addSeparator();
		botonRegresar.setVisible(false);

		searchItem = new TextItem("description", "Buscar Proceso");
		searchItem.addKeyPressHandler(new KeyPressHandler() {
			public void onKeyPress(KeyPressEvent event) {
				if ("enter".equalsIgnoreCase(event.getKeyName())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarProcesosClave(keyword);
					} else {
						Maestro.setListaProcesos();
					}
				}
			}
		});
		final PickerIcon findIcon = new PickerIcon(PickerIcon.SEARCH);
		final PickerIcon cancelIcon = new PickerIcon(PickerIcon.CLEAR);
		searchItem.setIcons(findIcon, cancelIcon);

		searchItem.addIconClickHandler(new IconClickHandler() {
			public void onIconClick(IconClickEvent event) {
				FormItemIcon icon = event.getIcon();
				if (icon.getSrc().equals(cancelIcon.getSrc())) {
					searchItem.setValue("");
					Maestro.setListaProcesos();
				} else if (icon.getSrc().equals(findIcon.getSrc())) {
					String keyword = searchItem.getValueAsString();
					if (keyword != "" && keyword != null) {
						Maestro.consultarProcesosClave(keyword);
					} else {
						Maestro.setListaProcesos();
					}
				}
			}
		});

		searchItem.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				String valor = searchItem.getValueAsString();
				if (valor == "" || valor == null) {
					Maestro.setListaProcesos();
				}
			}
		});

		formBusqueda = new DynamicForm();
		formBusqueda.setWidth100();
		formBusqueda.setPadding(5);
		formBusqueda.setLayoutAlign(Alignment.RIGHT);
		formBusqueda.setAlign(Alignment.RIGHT);
		formBusqueda.setFields(searchItem);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(formBusqueda);
		vl1.addMember(listGridProcesos);
		vl1.addMember(panelProcesoEspecifico);
		vl1.addMember(menuBarUsuarioBasico);

		addMember(vl1);

	}

	public void actualizarProcesos(ProcesoRecord[] records) {
		listGridProcesos.setData(records);
	}

	private void mostrarDialogoCrearProceso() {
		final Window winModal = new Window();

		PanelEncabezadoDialogo p = new PanelEncabezadoDialogo("Crear Proceso",
				"Cree un proceso y organizelo como desee", "img/admin/bot1.png");
		p.setSize("100%", "70px");

		winModal.setWidth(350);
		winModal.setHeight(330);
		winModal.setTitle("Crear un Proceso");
		winModal.setShowMinimizeButton(false);
		winModal.setIsModal(true);
		winModal.setShowModalMask(true);
		winModal.centerInPage();
		winModal.addCloseClickHandler(new CloseClickHandler() {
			@Override
			public void onCloseClick(CloseClickEvent event) {
				winModal.destroy();

			}
		});

		formNuevoProceso = new DynamicForm();
		formNuevoProceso.setHeight("40%");
		formNuevoProceso.setWidth100();
		formNuevoProceso.setPadding(5);
		formNuevoProceso.setLayoutAlign(VerticalAlignment.BOTTOM);

		textNombreProcesoNuevo = new TextItem();
		textNombreProcesoNuevo.setTitle("Nombre");
		textNombreProcesoNuevo.setRequired(true);

		textAreaDescripcionProcesoNuevo = new TextAreaItem();
		textAreaDescripcionProcesoNuevo.setTitle("Descripci\u00F3n");
		textAreaDescripcionProcesoNuevo.setRequired(true);

		dateTimeItemFechaInicioNuevo = new DateItem();
		dateTimeItemFechaInicioNuevo.setUseTextField(false);
		dateTimeItemFechaInicioNuevo.setTitle("Fecha Inicio");
		dateTimeItemFechaInicioNuevo.setRequired(true);
		dateTimeItemFechaInicioNuevo.setStartDate(new Date());
		dateTimeItemFechaInicioNuevo.addChangedHandler(new ChangedHandler() {
			@Override
			public void onChanged(ChangedEvent event) {
				dateTimeItemFechaFinalizacionNuevo
						.setStartDate(dateTimeItemFechaInicioNuevo
								.getValueAsDate());
			}
		});

		dateTimeItemFechaFinalizacionNuevo = new DateItem();
		dateTimeItemFechaFinalizacionNuevo.setUseTextField(false);
		dateTimeItemFechaFinalizacionNuevo.setTitle("Fecha Finalizaci\u00F3n");
		dateTimeItemFechaFinalizacionNuevo.setStartDate(new Date());

		checkBoxHabilitarFechaFinalizacion = new CheckboxItem();
		checkBoxHabilitarFechaFinalizacion.setName("onOrder");
		checkBoxHabilitarFechaFinalizacion
				.setTitle("Habilitar fecha de finalización");
		checkBoxHabilitarFechaFinalizacion.setRedrawOnChange(true);
		checkBoxHabilitarFechaFinalizacion.setValue(false);

		dateTimeItemFechaFinalizacionNuevo
				.setShowIfCondition(new FormItemIfFunction() {
					@Override
					public boolean execute(FormItem item, Object value,
							DynamicForm form) {
						return (Boolean) form.getValue("onOrder");
					}
				});

		ButtonItem boton = new ButtonItem("Crear");
		boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (formNuevoProceso.validate()) {

					ProcesoUsuarioBO proceso = new ProcesoUsuarioBO();
					proceso.setDescripcion(textAreaDescripcionProcesoNuevo
							.getValueAsString());
					proceso.setEstadoValoracion(Convencion.ESTADO_SOLICITUD_NO_REALIZADA);
					proceso.setFechaCreacion(new Date());
					if (checkBoxHabilitarFechaFinalizacion.getValueAsBoolean()) {
						proceso.setFechaFinalizacion(new Date(
								dateTimeItemFechaFinalizacionNuevo
										.getValueAsDate().getTime() + 3600 * 1000 * 12));
					}
					Date fechaInicio = new Date(dateTimeItemFechaInicioNuevo
							.getValueAsDate().getTime() - 3600 * 1000 * 12);
					proceso.setFechaInicio(fechaInicio);
					proceso.setNombre(textNombreProcesoNuevo.getValueAsString());
					Maestro.agregarProceso(proceso);

					winModal.destroy();

				}
			}
		});

		formNuevoProceso.setFields(textNombreProcesoNuevo,
				textAreaDescripcionProcesoNuevo, dateTimeItemFechaInicioNuevo,
				checkBoxHabilitarFechaFinalizacion,
				dateTimeItemFechaFinalizacionNuevo, boton);

		winModal.addItem(p);
		winModal.addItem(formNuevoProceso);
		winModal.show();
	}

	public void actualizarListaPruebas(List<PruebaUsuarioBO> pruebas) {
		listaPruebas = pruebas;
	}

	public void actualizarListaUsuariosBasicos(List<EvaluadoBO> usuarios) {
		listaEvaluados = usuarios;
	}

	public void actualizarParticipaciones(List<ParticipacionEnProcesoBO> result) {
		panelProcesoEspecifico.actualizarParticipaciones(result);
		panelProcesoEspecifico.actualizarEvaluados(obtenerEvaluadosNoEnProceso(
				listaEvaluados, obtenerEvaluadosDeParticipacion(result)));
	}

	public void actualizarResultados(List<ParticipacionEnProcesoBO> result) {
		panelProcesoEspecifico.actualizarResultados(result);
	}

	public void setEstadoInicial() {
		Maestro.setListaProcesos();
		botonRegresar.setVisible(false);
		listGridProcesos.setVisible(true);
		panelProcesoEspecifico.setVisible(false);
		botonNuevoBasico.setVisible(true);
		botonEliminarBasico.setVisible(true);
		botonDuplicarBasico.setVisible(true);
		formBusqueda.setVisible(true);
	}

	public void actualizarPruebasProceso(List<PruebaUsuarioBO> result) {
		panelProcesoEspecifico.actualizarPruebasProceso(result);
		panelProcesoEspecifico
				.actualizarListaPruebas(obtenerPruebasNoEnProceso(listaPruebas,
						result));
	}

	private List<EvaluadoBO> obtenerEvaluadosDeParticipacion(
			List<ParticipacionEnProcesoBO> result) {
		List<EvaluadoBO> resultado = new ArrayList<EvaluadoBO>();
		if (result != null) {
			for (ParticipacionEnProcesoBO participacionEnProcesoBO : result) {
				resultado.add(participacionEnProcesoBO.getUsuarioBasico());
			}
		}
		return resultado;
	}

	private List<PruebaUsuarioBO> obtenerPruebasNoEnProceso(
			List<PruebaUsuarioBO> pruebas,
			List<PruebaUsuarioBO> listaPruebasDeProceso) {
		List<PruebaUsuarioBO> resultado = new ArrayList<PruebaUsuarioBO>();
		boolean continuar = true;
		if (pruebas != null) {
			for (int i = 0; i < pruebas.size(); i++) {
				for (int j = 0; j < listaPruebasDeProceso.size() && continuar; j++) {
					if (listaPruebasDeProceso.get(j).getIdentificador() == pruebas
							.get(i).getIdentificador()) {
						continuar = false;
					}
				}
				if (continuar) {
					resultado.add(pruebas.get(i));
				}
				continuar = true;
			}
		}
		return resultado;
	}

	private List<EvaluadoBO> obtenerEvaluadosNoEnProceso(
			List<EvaluadoBO> usuarios, List<EvaluadoBO> listaEvaluadosDeProceso) {
		List<EvaluadoBO> resultado = new ArrayList<EvaluadoBO>();
		boolean continuar = true;
		if (usuarios != null) {
			for (int i = 0; i < usuarios.size(); i++) {
				for (int j = 0; j < listaEvaluadosDeProceso.size() && continuar; j++) {
					if (listaEvaluadosDeProceso.get(j).getIdentificador() == usuarios
							.get(i).getIdentificador()) {
						continuar = false;
					}
				}
				if (continuar) {
					resultado.add(usuarios.get(i));
				}
				continuar = true;
			}
		}
		return resultado;
	}

	public void desactivarDialogoNotificaciones() {
		panelProcesoEspecifico.desactivarDialogoNotificaciones();
	}

}
