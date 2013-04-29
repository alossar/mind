package co.mind.management.shared.recursos;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import co.mind.management.shared.persistencia.GestionLaminas;

public class FileUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher(Convencion.DIRECCION_WEB).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		if (ServletFileUpload.isMultipartContent(req)) {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(req);
				for (FileItem item : items) {
					// se procesa solo la carga del
					// archivo – Se descartan
					// otros tipos de items de
					// formulario
					if (item.isFormField())
						continue;

					String fileName = item.getName();
					// Se obtiene sólo el nombre del
					// archivo no la ruta entera
					if (fileName != null) {
						fileName = FilenameUtils.getName(fileName);
					}
					File uploadedFile = new File(getServletConfig()
							.getServletContext().getRealPath("")
							+ "/images/Laminas", fileName);
					if (uploadedFile.createNewFile()) {
						GestionLaminas gl = new GestionLaminas();
						item.write(uploadedFile);
						resp.setStatus(HttpServletResponse.SC_CREATED);
						resp.getWriter().print(
								"El archivo se subió exitosamente");
						resp.flushBuffer();
					} else
						// Aquí se genera un error si el archivo subido ya
						// existe
						// en el repositorio. Usted puede utilizar algoritmos
						// alternativos aquí como añadir un único número en el
						// nombre de los archivos duplicados.
						throw new IOException(
								"El archivo ya existe en el directorio");
				}
			} catch (Exception e) {
				resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
						"Ocurrió un error mientras se subía el documento : "
								+ e.getMessage());
				e.printStackTrace();
			}

		} else {
			resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE,
					"El tipo de contenido solicitado no es compatible con el servlet");
		}
	}
}