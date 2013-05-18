package co.mind.management.maestro.client.imagenes;

import java.util.List;

import co.mind.management.maestro.client.PanelEncabezadoDialogo;
import co.mind.management.shared.dto.ImagenUsuarioBO;
import co.mind.management.shared.records.ImagenRecord;

import com.google.gwt.user.client.ui.NamedFrame;
import com.smartgwt.client.types.Encoding;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.types.Visibility;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.CloseClickEvent;
import com.smartgwt.client.widgets.events.CloseClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ButtonItem;
import com.smartgwt.client.widgets.form.fields.UploadItem;
import com.smartgwt.client.widgets.form.fields.events.ClickEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tile.TileGrid;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import com.smartgwt.client.widgets.viewer.DetailViewerField;

public class PanelContenidoImagenes extends HLayout {

	private TileGrid tileGridImagenesUsuario;

	public PanelContenidoImagenes() {
		setWidth("90%");
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
		tileGridImagenesUsuario.setSelectionType(SelectionStyle.SIMPLE);

		DetailViewerField pictureField = new DetailViewerField("thumbnail");
		pictureField.setType("image");
		pictureField.setImageWidth(150);
		pictureField.setImageHeight(94);

		DetailViewerField nameField = new DetailViewerField("idImagenUsuario");

		tileGridImagenesUsuario.setFields(pictureField, nameField);

		ImgButton botonNuevoBasico = new ImgButton();
		botonNuevoBasico.setWidth(35);
		botonNuevoBasico.setHeight(35);
		botonNuevoBasico.setShowRollOver(true);
		botonNuevoBasico.setShowDown(true);
		botonNuevoBasico.setSrc("icons/agregar.png");
		botonNuevoBasico.setDisabled(false);
		botonNuevoBasico.setTooltip("Nueva prueba");
		botonNuevoBasico
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					private UploadItem uploadItem;

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {

						final Window windowCrearLamina = new Window();
						final PanelEncabezadoDialogo p = new PanelEncabezadoDialogo(
								"Subir una Imagen",
								"Agregue imágenes al sistema.",
								"img/admin/bot3.png");
						p.setSize("100%", "70px");

						windowCrearLamina.setWidth(400);
						windowCrearLamina.setHeight(200);
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
										windowCrearLamina.destroy();
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

						formUpload.setAction("/Mind/FileUploadServlet");
						formUpload.setTarget("fileUploadFrame");

						final ButtonItem boton = new ButtonItem("Agregar");
						boton.addClickHandler(new com.smartgwt.client.widgets.form.fields.events.ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								formUpload.submitForm();
								windowCrearLamina.destroy();
							}
						});
						formUpload.setFields(uploadItem, boton);

						windowCrearLamina.addItem(p);
						windowCrearLamina.addItem(formUpload);

						windowCrearLamina.show();

					}
				});
		//
		// ToolStripButton botonEliminarBasico = new ToolStripButton(
		// "Eliminar Imagen", "icons/16/document_plain_new.png");
		//
		// botonEliminarBasico
		// .addClickHandler(new
		// com.smartgwt.client.widgets.events.ClickHandler() {
		//
		// @Override
		// public void onClick(
		// com.smartgwt.client.widgets.events.ClickEvent event) {
		// if (imagenSeleccionada == null) {
		// SC.warn("Debe seleccionar la imagen que desea eliminar");
		// } else {
		// Maestro.eliminarImagen(imagenSeleccionada);
		// }
		// }
		// });

		ImgButton botonAgregarImagenes = new ImgButton();
		botonAgregarImagenes.setWidth(35);
		botonAgregarImagenes.setHeight(35);
		botonAgregarImagenes.setShowRollOver(true);
		botonAgregarImagenes.setShowDown(true);
		botonAgregarImagenes.setSrc("icons/agregar.png");
		botonAgregarImagenes.setDisabled(false);
		botonAgregarImagenes.setTooltip("Agregar imagenes a cliente");
		botonAgregarImagenes
				.addClickHandler(new com.smartgwt.client.widgets.events.ClickHandler() {

					@Override
					public void onClick(
							com.smartgwt.client.widgets.events.ClickEvent event) {
						
					}
				});

		HLayout hl1 = new HLayout();
		hl1.setWidth100();
		hl1.setHeight("40px");

		HLayout hlRelleno = new HLayout();
		hlRelleno.setWidth("*");
		hlRelleno.setHeight("1px");

		hl1.addMember(hlRelleno);
		hl1.addMember(botonNuevoBasico);
		hl1.addMember(botonAgregarImagenes);

		VLayout vl1 = new VLayout();
		vl1.setWidth100();
		vl1.setHeight100();

		vl1.addMember(hl1);
		vl1.addMember(tileGridImagenesUsuario);

		addMember(vl1);

	}

	public void actualizarImagenesUsuario(List<ImagenUsuarioBO> result) {
		tileGridImagenesUsuario.setData(ImagenRecord.getRecord(result));
	}

}
