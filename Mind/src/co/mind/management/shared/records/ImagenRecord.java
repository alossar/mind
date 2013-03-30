package co.mind.management.shared.records;

import java.util.ArrayList;
import java.util.List;

import co.mind.management.shared.bo.ImagenUsuarioBO;

import com.smartgwt.client.widgets.tile.TileRecord;

public class ImagenRecord extends TileRecord {

	public ImagenRecord() {
	}

	public ImagenRecord(int idImagen, int idImagenUsuario, String picture) {
		setIdImagen(idImagen);
		setIdImagenUsuario(idImagenUsuario);
		setPicture(picture);
		setThumbnail("Thumbnails/" + picture);
	}

	/**
	 * Set the name.
	 * 
	 * @param name
	 *            the name
	 */
	public void setIdImagen(int name) {
		setAttribute("idImagen", name);
	}

	/**
	 * Return the name.
	 * 
	 * @return the name
	 */
	public String getIdImagen() {
		return getAttribute("idImagen");
	}

	/**
	 * Set the price.
	 * 
	 * @param price
	 *            the price
	 */
	public void setIdImagenUsuario(int price) {
		setAttribute("idImagenUsuario", price);
	}

	/**
	 * Return the price.
	 * 
	 * @return the price
	 */
	public String getIdImagenUsuario() {
		return getAttribute("idImagenUsuario");
	}

	/**
	 * Set the picture.
	 * 
	 * @param picture
	 *            the picture
	 */
	public void setPicture(String picture) {
		setAttribute("picture", picture);
	}

	/**
	 * Return the picture.
	 * 
	 * @return the picture
	 */
	public String getPicture() {
		return getAttribute("picture");
	}

	/**
	 * Set the picture.
	 * 
	 * @param picture
	 *            the picture
	 */
	public void setThumbnail(String picture) {
		setAttribute("thumbnail", picture);
	}

	/**
	 * Return the picture.
	 * 
	 * @return the picture
	 */
	public String getThumbnail() {
		return getAttribute("thumbnail");
	}

	public static ImagenRecord[] getRecord(List<ImagenUsuarioBO> result) {
		if (result != null) {
			List<ImagenRecord> resultado = new ArrayList<ImagenRecord>();
			for (ImagenUsuarioBO imagenUsuarioBO : result) {
				ImagenRecord imagen = new ImagenRecord(imagenUsuarioBO
						.getImagen().getIdentificador(),
						imagenUsuarioBO.getIdentificador(), imagenUsuarioBO
								.getImagen().getImagenURI());
				resultado.add(imagen);
			}
			ImagenRecord[] records = new ImagenRecord[resultado.size()];
			for (int i = 0; i < resultado.size(); i++) {
				records[i] = resultado.get(i);
			}
			return records;
		}
		return null;

	}

	public static ImagenUsuarioBO getBO(ImagenRecord imagen) {
		ImagenUsuarioBO imagenBO = new ImagenUsuarioBO();
		imagenBO.setIdentificador(imagen.getAttributeAsInt("idImagenUsuario"));
		return imagenBO;
	}

}
