package easyserv.aapp.customserv.com.myapplication.Model;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Created by ronnykibet on 1/22/18.
 */

public class SimpleMail {

    /**CHANGE ACCORDINGLY**/
    private static final String SMTP_HOST_NAME = "smtp.gmail.com"; //can be your host server smtp@yourdomain.com
    private static final String SMTP_AUTH_USER = "jameshudsonrandall@gmail.com"; //your login username/email
    private static final String SMTP_AUTH_PWD  = "vitalexeev2606q"; //password/secret

    private static Message message;


    public static void sendEmail(String to, String subject, String msg){
        // Recipient's email ID needs to be mentioned.

        // Sender's email ID needs to be mentioned
        String from = "jameshudsonrandall@gmail.com"; //from

        final String username = SMTP_AUTH_USER;
        final String password = SMTP_AUTH_PWD;

        // Assuming you are sending email through relay.jangosmtp.net
        String host = SMTP_HOST_NAME;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Now set the actual message
            messageBodyPart.setContent(msg, "text/html;charset=utf-8");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

//            // Part two is attachment
//            messageBodyPart = new MimeBodyPart();
//            String filename = Context.;
//            DataSource source = new FileDataSource(filename);
//            messageBodyPart.setDataHandler(new DataHandler(source));
//            messageBodyPart.setFileName(filename);
//            multipart.addBodyPart(messageBodyPart);

            // Send the complete message parts
            message.setContent(multipart);

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try  {

                        // Send message
                        Transport.send(message);
                        System.out.println("Sent message successfully....");
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            });

            thread.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}