package co.mind.management.shared.recursos;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FIleUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		File uploadedFile;

		System.out.print("on server");
		try {

			Connection conn = getConnection();

			PrintWriter out = res.getWriter();

			// out.println("<br>Content type is :: " +contentType);
			// to get the content type information from JSP Request Header
			String contentType = req.getContentType();
			int flag = 0;
			FileInputStream fis = null;
			FileOutputStream fileOut = null;
			// here we are checking the content type is not equal to Null and as
			// well as the passed data from mulitpart/form-data is greater than
			// or equal to 0
			if ((contentType != null)
					&& (contentType.indexOf("multipart/form-data") >= 0)) {
				DataInputStream in = new DataInputStream(req.getInputStream());
				// we are taking the length of Content type data
				int formDataLength = req.getContentLength();
				byte dataBytes[] = new byte[formDataLength];
				int byteRead = 0;
				int totalBytesRead = 0;

				// this loop converting the uploaded file into byte code
				while (totalBytesRead < formDataLength) {
					byteRead = in.read(dataBytes, totalBytesRead,
							formDataLength);
					totalBytesRead += byteRead;
				}

				res.setContentType("application/octet-stream");
				String file = new String(dataBytes);
				// for saving the file name
				String saveFile = file
						.substring(file.indexOf("filename=\"") + 10);
				saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
				out.println("savefiledddd" + saveFile);
				int extension_save = saveFile.lastIndexOf("\"");
				String extension_saveName = saveFile.substring(extension_save);

				// Here we are invoking the absolute path out of the encrypted
				// data

				saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1,
						saveFile.indexOf("\""));
				int lastIndex = contentType.lastIndexOf("=");
				String boundary = contentType.substring(lastIndex + 1,
						contentType.length());
				int pos;

				// extracting the index of file
				pos = file.indexOf("filename=\"");
				pos = file.indexOf("\n", pos) + 1;
				pos = file.indexOf("\n", pos) + 1;
				pos = file.indexOf("\n", pos) + 1;
				int boundaryLocation = file.indexOf(boundary, pos) - 4;
				int startPos = ((file.substring(0, pos)).getBytes()).length;
				int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

				out.println("savefile" + saveFile);

				int file_No = 22;

				uploadedFile = new File("./war/img");

				uploadedFile.mkdir();

				String kk = uploadedFile.getAbsolutePath();

				String pathname_dir = kk + "/" + saveFile;
				// String
				// pathname_dir="C:\\Program Files\\Apache Software Foundation\\Tomcat 6.0\\jk\\"+saveFile;
				File filepath = new File(pathname_dir);
				out.println("filepath_  " + filepath);
				fileOut = new FileOutputStream(filepath);
				fileOut.write(dataBytes, startPos, (endPos - startPos));
				fileOut.flush();
				out.println("<h1> your files are saved</h1></body></html>");
				out.close();

				File database_filename = new File(pathname_dir);
				fis = new FileInputStream(database_filename);

				int len = (int) database_filename.length();
				PreparedStatement ps = conn
						.prepareStatement("insert into new (image) values (?)");
				ps.setBinaryStream(1, fis, len);
				ps.executeUpdate();
				ps.close();
				flag = 1;

			}

			if (flag == 1) {
				fileOut.close();
				fis.close();
			}
		} catch (Exception e) {
			System.out.println("Exception Due to" + e);
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection jdbcConnection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			jdbcConnection = DriverManager.getConnection(
					Convencion.CONEXION_URL, Convencion.CONEXION_USER,
					Convencion.CONEXION_PASS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jdbcConnection;
	}
}