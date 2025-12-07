package services;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author HP
 */
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

    private static final String SENDER_EMAIL = "abouamfati@gmail.com";  
    private static final String APP_PASSWORD = "kmytymmrgemflgpc";
    

    public static boolean sendNewPassword(String toEmail, String newPassword) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(SENDER_EMAIL, APP_PASSWORD);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Réinitialisation de votre mot de passe");
            message.setText("Bonjour,\n\nVotre nouveau mot de passe est : " + newPassword +
                            "\n\nVeuillez vous connecter et le changer immédiatement.");

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
