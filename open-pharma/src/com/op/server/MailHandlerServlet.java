package com.op.server;

import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties; 
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session; 
import javax.mail.internet.MimeMessage; 


@SuppressWarnings("serial")
public class MailHandlerServlet extends BaseServlet {
	private static final Logger log = Logger.getLogger(MailHandlerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, final HttpServletResponse resp) throws IOException {		
		Properties props = new Properties(); 
		Session session = Session.getDefaultInstance(props, null);
			
	    try {
			MimeMessage message = new MimeMessage(session, req.getInputStream());			

			Multipart m=(Multipart) message.getContent();
			
			String s="";
			for(int i=0;i<m.getCount();i++){
				Part part=m.getBodyPart(i);
				s+=m.getBodyPart(i).getContent().toString();
				 String disposition = part.getDisposition();
				 if (disposition != null && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE))) {					 
					 
				 }
			}			
		} catch (MessagingException e) {
			e.printStackTrace();
			log.warning("Exception : "+e.getMessage());
		}

	    resp.setContentType("text/plain");
	    resp.setStatus(HttpServletResponse.SC_OK); //Reponse positive pour le serveur
	}
	
}
