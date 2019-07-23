/*-*
 *
 * FILENAME  :
 *    Mailing.java
 *
 * STATUS  :	
 *    2013 23:17:57  
 *
 *    
 * Copyright (c) 2012 SystemSoft Ltda. All rights reserved.
 *
 ****************************************************************/

package support.user.mail;

import java.io.File;
import java.util.List;


import org.apache.commons.mail.EmailAttachment;
import org.apache.log4j.Logger;

import support.util.Result;
public class Mailing {

	private static Logger log = Logger.getLogger(Mailing.class);

	public synchronized Result sendMail(String subject, String mensaje, List<Attachment> attachment, List<String> lstEmailsTo, List<String> lstEmailsCc,
			List<String> lstEmailsCco,String mailSmtlHost,String mailSmtlUserName,
			int mailSmtlPort,boolean mailSmtlAuth,boolean mailSmtlStarttlsEnable,boolean mailSmtlSslEnable,String mailSmtlUser,String mailSmtlPassword,String mailSmtlFrom ) {

		EmailConfig email = null;
		Result result= new Result();
		try {

			email = new EmailConfig( mailSmtlHost, mailSmtlUserName,
					 mailSmtlPort, mailSmtlAuth, mailSmtlStarttlsEnable, mailSmtlSslEnable, mailSmtlUser, mailSmtlPassword, mailSmtlFrom  );
			
			// Create the attachment
			EmailAttachment emailAttachment = new EmailAttachment();
			long tamañoAdjuntos=0;
			
			for (Attachment a : attachment) {
				File fichero = new File(a.getPath());
				tamañoAdjuntos=tamañoAdjuntos+fichero.length();
				//email.attach(emailAttachment);				
			}
			System.out.println("tamañao adjuntos: "+tamañoAdjuntos);
			if(tamañoAdjuntos<10485760){
				for (Attachment a : attachment) {
					emailAttachment.setPath(a.getPath());
					emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
					emailAttachment.setDescription(a.getDescription());
					emailAttachment.setName(a.getName());
					email.attach(emailAttachment);				
				}
			}else{
				Attachment a=attachment.get(attachment.size()-1);
				emailAttachment.setPath(a.getPath());
				emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
				emailAttachment.setDescription(a.getDescription());
				emailAttachment.setName(a.getName());
				email.attach(emailAttachment);	
			}
			
			// Create the email message
			
			email.agregarDestinatarios(lstEmailsTo, lstEmailsCc, lstEmailsCco);
			email.setSubject(subject);
			email.setHtmlMsg(mensaje);
			
			// add the attachment
			

			// send the email
			email.send();
			

			result.ok("Email enviado From:" + email.getFromAddress() + " To:" + email.getToAddresses() + " adjunto:" 
					+ " subject:" + subject + ", mensaje:" + mensaje);
			log.info("Email enviado From:" + email.getFromAddress() + " To:" + email.getToAddresses() + " adjunto:"
					+ " subject:" + subject + ", mensaje:" + mensaje);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Email fallido  To:" + lstEmailsTo + " adjunto:" + " subject:" + subject + ", mensaje:" + mensaje, e);
			result.error("Email fallido  To:" + lstEmailsTo + " adjunto:" + " subject:" + subject + ", mensaje:" + mensaje);
			return result;
		}

		
	}



}