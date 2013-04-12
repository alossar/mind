package co.mind.management.maestro.client.imagenes;

import java.util.List;

import co.mind.management.maestro.client.Maestro;
import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.records.ImagenRecord;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.tile.events.RecordClickEvent;
import com.smartgwt.client.widgets.tile.events.RecordClickHandler;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelContenidoImagenes extends HLayout {

	private TileGrid tileGridImagenesUsuario;
	private ImagenUsuarioBO imagenSeleccionada;

	public PanelContenidoImagenes() {
		setWidth("100%");
		setHeight("80%");
		setBackgroundColor("white");
		setPadding(15);

		tileGridImagenesUsuario = new TileGrid();
		tileGridImagenesUsuario.setTileWidth(150);
		tileGridImagenesUsuario.setTileHeight(94);
		tileGridImagenesUsuario.setHeight100();
		tileGridImagenesUsuario.setWidth100();
		tileGridImagenesUsuario.setCanReorderTiles(false);
		tileGridImagenesUsuario.setShowAllRecords(false);
		tileGridImagenesUsuario.addRecordClickHandler(new RecordClickHandler() {

			@Override
			public void onRecordClick(RecordClickEvent event) {
				ImagenRecord record = (ImagenRecord) event.getRecord();
				imagenSeleccionada = ImagenRecord.getBO(record);

			}
		});
		DetailViewerField pictureField = new DetailViewerField("thumbnail");
		pictureField.setType("image");
		pictureField.setImageWidth(150);
		pictureField.setImageHeight(94);

		DetailViewerField nameField = new DetailViewerField("idImagenUsuario");

		tileGridImagenesUsuario.setFields(pictureField, nameField);

		ToolStripButton botonNuevoBasico = new ToolStripButton(
				"Agregar Imagen", "icons/16/document_plain_new.png");

		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					private UploadItem uploadItem;

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						final Window windowCrearLamina = new Window();
						final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
								"Subir una Imagen Temas",
								"Agregue imágenes al sistema", "img/check.png");
						p.setSize("100%", "70px");

						windowCrearLamina.setWidth(400);
						windowCrearLamina.setHeight(250);
						windowCrearLamina.setTitle("Subir una Imagen");
						windowCrearLamina.setShowMinimizeButton(false);
						windowCrearLamina.setIsModal(true);
						windowCrearLamina.setShowModalMask(true);
						windowCrearLamina.centerInPage();
						windowCrearLamina
								.addCloseClickHandler(new CloseClickHandler() {
									@Override
									public void onCloseClick(
											CloseClickEvent event) {
										windowCrearLamina.hide();
									}
								});

						final DynamicForm formUpload = new DynamicForm();
						formUpload.setHeight("40%");
						formUpload.setWidth100();
						formUpload.setPadding(5);
						formUpload.setLayoutAlign(VerticalAlignment.BOTTOM);
						formUpload.setEncoding(Encoding.MULTIPART);

						uploadItem = new UploadItem("Imagen");
						uploadItem.setTitle("Nombre");
						uploadItem.setRequired(true);
						uploadItem.setWidth("100%");

						formUpload.setAction(GWT.getModuleBaseURL() + "upload");

						formUpload.setFields(uploadItem);

						final IButton boton = new IButton("Crear");
						boton.addClickHandler(new ClickHandler() {
							@Override
							public void onClick(
									com.smartgwt.client.widgets.events.ClickEvent event) {
								formUpload.submitForm();
							}
						});

						windowCrearLamina.addItem(p);
						windowCrearLamina.addItem(formUpload);
						windowCrearLamina.addItem(boton);

						windowCrearLamina.show();

					}
				});

		ToolStripButton botonEliminarBasico = new ToolStripButton(
				"Eliminar Imagen", "icons/16/document_plain_new.png");

		botonEliminarBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						if (imagenSeleccionada == null) {
							SC.warn("Debe seleccionar la imagen que desea eliminar");
						} else {
							Maestro.eliminarImagen(imagenSeleccionada);
						}
					}
				});

		ToolStrip menuBarUsuarioBasico = new ToolStrip();
		menuBarUsuarioBasico.setWidth100();
		menuBarUsuarioBasico.addButton(botonNuevoBasico);
		menuBarUsuarioBasico.addButton(botonEliminarBasico);
		menuBarUsuarioBasico.addFill();
		menuBarUsuarioBasico.addSeparator();
		menuBarUsuarioBasico.addSeparator();

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(tileGridImagenesUsuario);
		vl1.addMember(menuBarUsuarioBasico);

		addMember(vl1);

	}

	public void actualizarImagenesUsuario(List<ImagenUsuarioBO> result) {
		tileGridImagenesUsuario.setData(ImagenRecord.getRecord(result));
	}

}
