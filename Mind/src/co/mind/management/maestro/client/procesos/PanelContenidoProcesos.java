package co.mind.management.maestro.client.procesos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.smartgwt.client.widgets.ImgButton;
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
import com.smartgwt.client.widgets.grid.events.EditCompleteEvent;
import com.smartgwt.client.widgets.grid.events.EditCompleteHandler;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordDoubleClickHandler;
import com.smartgwt.client.widgets.grid.events.RowContextClickEvent;
import com.smartgwt.client.widgets.grid.events.RowContextClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PanelContenidoProcesos extends HLayout {

	private ListGrid listGridProcesos;
	private ImgButton botonRegresar;
	private ImgButton botonNuevoBasico;
	private ImgButton botonEliminarBasico;
	private ImgButton botonDuplicarBasico;
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
				.addRecordDoubleClickHandler(new RecordDoubleClickHandler() {

					@Override
					public void onRecordDoubleClick(RecordDoubleClickEvent event) {
						ProcesoRecord p = (ProcesoRecord) event.getRecord();
						if (p != null) {
							navegarAProcesoEspecifico(ProcesoRecord.getBO(p));
							botonRegresar.setDisabled(false);
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

		listGridProcesos.setGenerateDoubleClickOnEnter(true);

		panelProcesoEspecifico = new PanelProcesoEspecifico(usuario);
		panelProcesoEspecifico.setHeight100();
		panelProcesoEspecifico.setWidth100();
		panelProcesoEspecifico.setVisible(false);

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
		formBusqueda.setWidth("250px");
		formBusqueda.setHeight("33px");
		formBusqueda.setPadding(5);
		formBusqueda.setLayoutAlign(VerticalAlignment.CENTER);
		formBusqueda.setFields(searchItem);

		botonRegresar = new ImgButton();
		botonRegresar.setWidth(35);
		botonRegresar.setHeight(35);
		botonRegresar.setShowRollOver(true);
		botonRegresar.setShowDown(true);
		botonRegresar.setSrc("icons/atras.png");
		botonRegresar.setDisabled(true);

		botonRegresar
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						setEstadoInicial();
					}

				});

		botonNuevoBasico = new ImgButton();
		botonNuevoBasico.setWidth(35);
		botonNuevoBasico.setHeight(35);
		botonNuevoBasico.setShowRollOver(true);
		botonNuevoBasico.setShowDown(true);
		botonNuevoBasico.setSrc("icons/agregar.png");
		botonNuevoBasico.setDisabled(false);
		botonNuevoBasico.setTooltip("Nuevo proceso");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {
					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						mostrarDialogoCrearProceso();
					}

				});

		botonEliminarBasico = new ImgButton();
		botonEliminarBasico.setWidth(35);
		botonEliminarBasico.setHeight(35);
		botonEliminarBasico.setShowRollOver(true);
		botonEliminarBasico.setShowDown(true);
		botonEliminarBasico.setSrc("icons/eliminar.png");
		botonEliminarBasico.setDisabled(false);
		botonEliminarBasico.setTooltip("Eliminar proceso");

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

		botonDuplicarBasico = new ImgButton();
		botonDuplicarBasico.setWidth(35);
		botonDuplicarBasico.setHeight(35);
		botonDuplicarBasico.setShowRollOver(true);
		botonDuplicarBasico.setShowDown(true);
		botonDuplicarBasico.setSrc("icons/duplicar.png");
		botonDuplicarBasico.setDisabled(false);
		botonDuplicarBasico.setTooltip("Duplicar proceso");

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

		HLayout hl1 = new HLayout();
		hl1.setWidth100();
		hl1.setHeight("40px");

		HLayout hlRelleno = new HLayout();
		hlRelleno.setWidth("*");
		hlRelleno.setHeight("1px");

		hl1.addMember(hlRelleno);
		hl1.addMember(formBusqueda);
		hl1.addMember(botonRegresar);
		hl1.addMember(botonNuevoBasico);
		hl1.addMember(botonEliminarBasico);
		hl1.addMember(botonDuplicarBasico);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(hl1);
		vl1.addMember(listGridProcesos);
		vl1.addMember(panelProcesoEspecifico);

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
		textNombreProcesoNuevo.setLength(Convencion.MAXIMA_LONGITUD_NOMBRE);

		textAreaDescripcionProcesoNuevo = new TextAreaItem();
		textAreaDescripcionProcesoNuevo.setTitle("Descripci\u00F3n");
		textAreaDescripcionProcesoNuevo.setRequired(true);
		textAreaDescripcionProcesoNuevo
				.setLength(Convencion.MAXIMA_LONGITUD_DESCRIPCION_PROCESO);

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

	public void navegarAProcesoEspecifico(ProcesoUsuarioBO proceso) {
		procesoSeleccionado = proceso;
		Maestro.obtenerParticipantesProceso(procesoSeleccionado);
		Maestro.obtenerPruebasProceso(procesoSeleccionado);
		panelProcesoEspecifico.deseleccionarTodo();
		panelProcesoEspecifico.actualizarDatosProceso(procesoSeleccionado);
		listGridProcesos.setVisible(false);
		panelProcesoEspecifico.setVisible(true);
		botonNuevoBasico.setDisabled(true);
		botonEliminarBasico.setDisabled(true);
		botonDuplicarBasico.setDisabled(true);
		formBusqueda.setVisible(false);
	}

	public void setEstadoInicial() {
		Maestro.setListaProcesos();
		botonRegresar.setDisabled(true);
		listGridProcesos.setVisible(true);
		panelProcesoEspecifico.setVisible(false);
		botonNuevoBasico.setDisabled(false);
		botonEliminarBasico.setDisabled(false);
		botonDuplicarBasico.setDisabled(false);
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

	public void actualizarProcesoSeleccionado(ProcesoUsuarioBO proceso) {
		panelProcesoEspecifico.actualizarDatosProceso(proceso);
	}

}
