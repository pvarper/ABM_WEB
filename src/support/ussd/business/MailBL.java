package support.ussd.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import micrium.aes.AlgoritmoAES;
import support.user.business.ParametroBL;
import support.user.mail.Mail;
import support.user.mail.MailManager;
import support.user.model.MuUsuario;
import support.ussd.model.Adjunto;
import support.ussd.model.TAbm;
import support.ussd.model.TServicio;
import support.util.Code;
import support.util.Result;

import org.apache.log4j.Logger;

@Named
public class MailBL implements Serializable {

	private static final long serialVersionUID = 1L;
	public static Logger log = Logger.getLogger(MailBL.class);

	@Inject
	ParametroBL controlParametro;

	public Result sendEmail(List<MuUsuario> correo, List<Adjunto> listaAdj,TAbm form,List<TServicio> tsaux,String obs) {
		log.info("Se va enviar mail de abm nro:" + form.getAbm());
		String mensaje = "";
		Result result;
		AlgoritmoAES aes = new AlgoritmoAES();

		// hash=DigestUtil.generarHash(pathDetalle);
		mensaje = aes.desencriptar(controlParametro.getTemplateEnvio());// SysParameter.getProperty(SysParameter.TEMPLATE_ENVIO);
		// mensaje="<b><h3>Favor revisar el siguiente ODECO:</h3></b><b>Codigo Reclamacion:</b> {CODIGO}<br/><b>Link Acceso al Sistema Web:</b> {LINK}<br/><b>Adjuntos:</b> {ADJUNTO}<br/>  <b><h3>Saludos. TIGO</h3></b> ";
		mensaje = mensaje.replace("{ABM}",String.valueOf(form.getAbm()));
		mensaje = mensaje.replace("{SOLICITANTE}",form.getSolicitante());
		mensaje = mensaje.replace("{USUARIO}",form.getUsuario());
		mensaje = mensaje.replace("{EHUMANO}",form.getEhumano());
		mensaje = mensaje.replace("{CARGO}",form.getCargo());
		mensaje = mensaje.replace("{AREASUCURSAL}",form.getAreasucursal());
		mensaje = mensaje.replace("{FECHAFIN}",form.getFechafin());
		mensaje=mensaje.replace("{LINKSISTEMA}",aes.desencriptar(controlParametro.getLinkSistema()));
		mensaje = mensaje.replace("{OBSERVACION}",form.getObservacionAbm());
		mensaje = mensaje.replace("{OBSERVACIONES}",obs);
		// mensaje=mensaje.replace("{ADJUNTO}","bb");
		List<String> listaCorreos= new ArrayList<String>();
		for (MuUsuario a : correo) {
			listaCorreos.add(a.getCorreo());
		}
		String html="";
		for (TServicio a : tsaux) {
			html=html+" <tr > <td > <p><a  > "+a.getDescripcion() + "</p></td>	<td >    <p><a  >"+((a.getTipoUsuario()==null)?"":a.getTipoUsuario())+" </p></td></tr>";
		}
		mensaje = mensaje.replace("{serviciosxml}",html);
		
		Mail mail = MailManager.getMail(listaAdj,aes.desencriptar(controlParametro.getAsunto()) + " "+ form.getAbm(), mensaje, listaCorreos);

		result = MailManager.sendEmailDetalle(mail, aes
				.desencriptar(controlParametro.getMailSmtpHost()), aes
				.desencriptar(controlParametro.getMailTituloFrom()), Integer
				.valueOf(aes.desencriptar(controlParametro.getMailSmtpPort())),
				Boolean.valueOf(aes.desencriptar(controlParametro
						.getMailSmtpAuth())), Boolean.valueOf(aes
						.desencriptar(controlParametro
								.getMailSmtpStartTlsEnable())), Boolean
						.valueOf(aes.desencriptar(controlParametro
								.getMailSmtpSslEnable())), aes
						.desencriptar(controlParametro.getMailSmtpUser()), aes
						.desencriptar(controlParametro.getMailSmtpPw()), aes
						.desencriptar(controlParametro.getMailSmtpFrom()));

		if (!result.getCode().equalsIgnoreCase(Code.OK)) {
			log.info("No se envio al correo " + correo);
			result.error("No se envio al correo " + correo);
			return result;
		}
		log.debug("se envio el correo " + correo);
		result.ok("se envio el correo " + correo);

		return result;

	}
	
	public Result sendEmailObsFin(List<MuUsuario> correo,TAbm form,String servicio,String obs, String admin) {
		log.info("Se va enviar mail de abm nro:" + form.getAbm());
		String mensaje = "";
		Result result;
		AlgoritmoAES aes = new AlgoritmoAES();

		// hash=DigestUtil.generarHash(pathDetalle);
		mensaje = aes.desencriptar(controlParametro.getTemplateEnvioObservado());// SysParameter.getProperty(SysParameter.TEMPLATE_ENVIO);
		// mensaje="<b><h3>Favor revisar el siguiente ODECO:</h3></b><b>Codigo Reclamacion:</b> {CODIGO}<br/><b>Link Acceso al Sistema Web:</b> {LINK}<br/><b>Adjuntos:</b> {ADJUNTO}<br/>  <b><h3>Saludos. TIGO</h3></b> ";
		mensaje = mensaje.replace("{ABM}",String.valueOf(form.getAbm()));
		mensaje = mensaje.replace("{SERVICIO}",servicio);
		mensaje = mensaje.replace("{OBSERVACION}",obs);
		mensaje = mensaje.replace("{ADMINISTRADOR}",admin);
		// mensaje=mensaje.replace("{ADJUNTO}","bb");
		List<String> listaCorreos= new ArrayList<String>();
		for (MuUsuario a : correo) {
			listaCorreos.add(a.getCorreo());
		}
		
		
		Mail mail = MailManager.getMail(null,"OBSERVACION ABM" + " "+ form.getAbm(), mensaje, listaCorreos);

		result = MailManager.sendEmailDetalle(mail, aes
				.desencriptar(controlParametro.getMailSmtpHost()), aes
				.desencriptar(controlParametro.getMailTituloFrom()), Integer
				.valueOf(aes.desencriptar(controlParametro.getMailSmtpPort())),
				Boolean.valueOf(aes.desencriptar(controlParametro
						.getMailSmtpAuth())), Boolean.valueOf(aes
						.desencriptar(controlParametro
								.getMailSmtpStartTlsEnable())), Boolean
						.valueOf(aes.desencriptar(controlParametro
								.getMailSmtpSslEnable())), aes
						.desencriptar(controlParametro.getMailSmtpUser()), aes
						.desencriptar(controlParametro.getMailSmtpPw()), aes
						.desencriptar(controlParametro.getMailSmtpFrom()));

		if (!result.getCode().equalsIgnoreCase(Code.OK)) {
			log.info("No se envio al correo " + correo);
			result.error("No se envio al correo " + correo);
			return result;
		}
		log.debug("se envio el correo " + correo);
		result.ok("se envio el correo " + correo);

		return result;

	}
	
	public Result sendEmailFin(List<MuUsuario> correo,TAbm form) {
		log.info("Se va enviar mail de abm nro:" + form.getAbm());
		String mensaje = "";
		Result result;
		AlgoritmoAES aes = new AlgoritmoAES();

		// hash=DigestUtil.generarHash(pathDetalle);
		mensaje ="El ABM "+form.getAbm()+" ha finalizado, por favor cerrar el flujo";
		// mensaje=mensaje.replace("{ADJUNTO}","bb");
		List<String> listaCorreos= new ArrayList<String>();
		for (MuUsuario a : correo) {
			listaCorreos.add(a.getCorreo());
		}
		
		
		Mail mail = MailManager.getMail(null,"FINALIZADO ABM " + " "+ form.getAbm(), mensaje, listaCorreos);

		result = MailManager.sendEmailDetalle(mail, aes
				.desencriptar(controlParametro.getMailSmtpHost()), aes
				.desencriptar(controlParametro.getMailTituloFrom()), Integer
				.valueOf(aes.desencriptar(controlParametro.getMailSmtpPort())),
				Boolean.valueOf(aes.desencriptar(controlParametro
						.getMailSmtpAuth())), Boolean.valueOf(aes
						.desencriptar(controlParametro
								.getMailSmtpStartTlsEnable())), Boolean
						.valueOf(aes.desencriptar(controlParametro
								.getMailSmtpSslEnable())), aes
						.desencriptar(controlParametro.getMailSmtpUser()), aes
						.desencriptar(controlParametro.getMailSmtpPw()), aes
						.desencriptar(controlParametro.getMailSmtpFrom()));

		if (!result.getCode().equalsIgnoreCase(Code.OK)) {
			log.info("No se envio al correo " + correo);
			result.error("No se envio al correo " + correo);
			return result;
		}
		log.debug("se envio el correo " + correo);
		result.ok("se envio el correo " + correo);

		return result;

	}

}
