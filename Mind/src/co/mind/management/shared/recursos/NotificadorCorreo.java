package co.mind.management.shared.recursos;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import co.mind.management.shared.bo.ParticipacionEnProcesoBO;
import co.mind.management.shared.bo.UsuarioAdministradorBO;

public class NotificadorCorreo {

	public static void enviarCorreoCreacionCuentaUsuarioAdministrador(
			co.mind.management.shared.bo.UsuarioAdministradorBO usuarioAdministrador) {

		final String username = Convencion.CORREO_NOTIFICACION;
		final String password = Convencion.CONTRASENA_NOTIFICACION;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Convencion.CORREO_NOTIFICACION));
			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(usuarioAdministrador.getCorreo_Electronico()));
			message.setSubject("Bienvenido a Tarmin");
			message.setText(Convencion.MENSAJE_CREACION_CUENTA_USUARIO_ADMINISTRADOR
					+ " Usuario:"
					+ usuarioAdministrador.getCorreo_Electronico()
					+ ". Contraseña:" + usuarioAdministrador.getContrasena());

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void enviarCorreoParticipacionAProceso(
			ParticipacionEnProcesoBO participacion) {

		final String username = Convencion.CORREO_NOTIFICACION;
		final String password = Convencion.CONTRASENA_NOTIFICACION;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Convencion.CORREO_NOTIFICACION));
			message.setRecipients(Message.RecipientType.TO, InternetAddress
					.parse(participacion.getUsuarioBasico()
							.getCorreoElectronico()));
			message.setSubject("Tiene una evaluación en Tarmin");

			String i = "<!DOCTYPE HTML><html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'><style>#nombre p{	float:left;	margin-left: 10px;} #mail{ clear:both;	}#mail p{ float:left;	margin-left: 10px;	}#mailLink p{	padding-top: 30px;}	</style></head>	<body style='margin:0px; padding:0px;'>	<div id='contenedorTotalMail' style='position: relative; width: 960px margin: 0px auto;'>		<div id='cuerpoMail' style='width: 408px; height: 643px; margin:auto;'>		<div id='mailContenedorDatos' style='	width:408px; height:380px; background-color: white; box-shadow: 0px 0px 10px rgba(0,0,0,0.3);'>		<div id='MailTitle' style='width:137px; height:62px; margin:25px auto; padding-top:20px;'><img src='https://dl.dropbox.com/u/32952272/logo2M.png'></div>	<div id='mailTitleGracias' style='color:rgb(91,91,91); font-family:Arial; font-size:14px; text-align:center; margin-bottom:50px;'><h1>Gracias Por Registrarse</h1></div> <div class='registrateInfo' style='width:337px;font-family:Arial; font-size:12px; margin-top:10px; margin-left:35px;'>	<div id='nombre'><p>Nombre:</p>	<p style=' color:rgb(17,170,209); font-style:italic;' >"
					+ participacion.getUsuarioBasico().getCorreoElectronico()
					+ "</p></div>	<div id='mail'>	<p>Contraseña: </p><p id='pass' style=' color:rgb(17,170,209); font-style:italic;'>"
					+ participacion.getCodigo_Acceso()
					+ "</p>	</div></div><a style='color:rgb(17,170,209);' href='index.html'><div id='mailLink'><p style='font-family:Arial; font-size:14px; clear:both; text-align:center; color:rgb(17,170,209); cursor:pointer;'>Click Aqui para Ingresar</p></div></a><div id='mailFooter'><p style='height:37px; width:100%; margin-top:25px;  padding-top:15px; color:grey; text-align:center;  font-family:Arial; font-size:12px;'>www.mindmanagement.co</p></div></div></div><!--<div id='botonEntrarServicios'><p>Registrate</p></div>--></div><!-- Cierro Cont Total---></body></html> ";

			message.setContent(i, "text/html");
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
