package co.mind.management.shared.recursos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

public class ExcelReportServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {

			/*
			 * Asegurar que existe el archivo jasper
			 */
			ServletContext sc = getServletConfig().getServletContext();
			String vjrxmlPath = "/reports/reporte.jrxml";
			String vjasperPath = "/reports/reporte.jasper";

			InputStream is = sc.getResourceAsStream(vjasperPath);

			if (is == null) {
				String rp = sc.getRealPath(vjrxmlPath);
				JasperCompileManager.compileReportToFile(rp);
				is = sc.getResourceAsStream(vjasperPath);
			}

			HttpSession session = request.getSession();
			List<ParticipacionEnProcesoBO> participaciones = (List<ParticipacionEnProcesoBO>) session
					.getAttribute("participaciones");
			if (participaciones != null) {
				Map<String, Object> parametros = new HashMap<>();
				Integer participacion = participaciones.get(0)
						.getIdentificador();
				String direccionSubreporteCategorias = getServletConfig()
						.getServletContext().getRealPath(
								"/reports/subreporte_categorias.jasper");
				String direccionSubreportePreguntas = getServletConfig()
						.getServletContext().getRealPath(
								"/reports/subreporte_preguntas.jasper");
				parametros.put("participacionID", participacion);
				parametros.put("SUBREPORT_DIR", direccionSubreporteCategorias);
				parametros.put("SUBREPORT_DIR_PRE",
						direccionSubreportePreguntas);

				Connection jdbcConnection = getConnection();
				String jasperPath = sc.getRealPath(vjasperPath);
				JasperPrint jprint = JasperFillManager.fillReport(jasperPath,
						parametros, jdbcConnection);

				/*
				 * Creamos el archivo xls
				 */
				String vxls = "/reports/reporte.xls";
				String xls = sc.getRealPath(vxls);
				JRXlsExporter exporter = new JRXlsExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT, jprint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, xls);
				exporter.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				exporter.setParameter(
						JRXlsExporterParameter.IS_IGNORE_CELL_BORDER,
						Boolean.FALSE);
				exporter.setParameter(
						JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				exporter.exportReport();

				/*
				 * Configuramos el tipo de archivo, la cabecera http
				 */
				File f = new File(xls);
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + "detObjMet.xls" + "\"");
				InputStream in = new FileInputStream(f);
				ServletOutputStream sos = response.getOutputStream();

				int bit = 256;
				while ((bit) >= 0) {
					bit = in.read();
					sos.write(bit);
				}

				sos.flush();
				sos.close();
				in.close();
			} else {
				response.sendRedirect(Convencion.DIRECCION_WEB);
			}
		} catch (JRException e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			response.setContentType("text/plain");
			response.getOutputStream().print(sw.toString());
		} catch (NullPointerException nul) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			nul.printStackTrace(pw);
			response.setContentType("text/plain");
			response.getOutputStream().print(sw.toString());

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