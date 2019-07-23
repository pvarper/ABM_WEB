package support.user.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import support.ussd.model.Adjunto;
import support.util.Code;
import support.util.Result;

public class MailManager {

	private static Logger log = Logger.getLogger(MailManager.class);

	public static Result sendEmailDetalle(Mail mail,String mailSmtlHost,String mailSmtlUserName,
			int mailSmtlPort,boolean mailSmtlAuth,boolean mailSmtlStarttlsEnable,boolean mailSmtlSslEnable,String mailSmtlUser,String mailSmtlPassword,String mailSmtlFrom ) {

		log.info("Vamos a enviar el mail " + mail);
		Result result= new Result();
		try {
			//String pathname = mail.getAttachment().getPath();
			Mailing mailing = new Mailing();
			result=mailing.sendMail(mail.getSubject(), mail.getContent(), mail.getAttachment(), mail.getLstEmailsTo(), mail.getLstEmailsCc(),
					mail.getLstEmailsCco(), mailSmtlHost, mailSmtlUserName,
					 mailSmtlPort, mailSmtlAuth, mailSmtlStarttlsEnable, mailSmtlSslEnable, mailSmtlUser, mailSmtlPassword, mailSmtlFrom  );
			if (!result.getCode().equalsIgnoreCase(Code.OK)) {
				log.info("No se envio el mail " + mail);
				result.error("No se envio el mail " + mail);
				return result;
			}	
			
			log.info("Se envio satisfactoriamente el mail " + mail);
			result.ok("Se envio satisfactoriamente el mail " + mail);
			return result;
		} catch (Exception e) {
			// TODO: handle exception
			log.info("No se envio el mail " + mail);
			result.error("No se envio el mail " + mail);
			return result;
		}
		
	}
	

	public static Mail getMail(List<Adjunto> listAdj, String subject, String content, List<String> lstEmailsTo) {

		Mail mail = new Mail();
		List<Attachment> attachment = new ArrayList<Attachment>();
		if(listAdj!=null){
			for (Adjunto a : listAdj) {
				Attachment b= new Attachment();
				b.setPath(a.getRuta());
				String name = FilenameUtils.getName(a.getRuta());
				b.setName(name);
				attachment.add(b);
			}
		}
		
		
		mail.setAttachment(attachment);
		
	
		

		mail.setLstEmailsTo(lstEmailsTo);
		mail.setSubject(subject);
		mail.setContent(content);
		
		
		
		return mail;
	}
	
	

}
