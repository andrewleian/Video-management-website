package util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Utils {

    public static int checkPage(HttpServletRequest request, HttpServletResponse response ){
        String page = request.getParameter("page");
        int currentPage;

        if(page == null){
            currentPage = 1;
        }else {
            currentPage = Integer.parseInt(page);
        }
        request.setAttribute("currentPage" , currentPage);
        return currentPage;

    }

    public static void sendMail(String to , String subject , String body){
        Properties props = new Properties();
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "587");

        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");


        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = "yenlhph19202@gmail.com";
                String password = "vrnoahlookxeydkg";

                return  new PasswordAuthentication(username, password);
            }

        });

        session.getProperties().put("mail.smtp.starttls.enable", "true");

        MimeMessage message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("yenlhph19202@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(subject , "utf-8");
            message.setText(body , "utf-8" , "html");
            message.setReplyTo(message.getFrom());
            Transport.send(message);

        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
