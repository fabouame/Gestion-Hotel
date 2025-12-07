/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import entities.User;
import services.UserService;
import utiles.SecurityUtil;

import javax.swing.*;
import java.awt.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class ForgotPasswordDialog extends JDialog {

    private JTextField txtLogin;
    private JTextField txtAnswer;
    private JTextField txtEmail;
    private JLabel lblQuestion;
    private final UserService userService = new UserService();

    public ForgotPasswordDialog(JFrame parent) {
        super(parent, "Mot de passe oublié", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(6, 1, 5, 5));

        txtLogin = new JTextField();
        txtAnswer = new JTextField();
        txtEmail = new JTextField();
        lblQuestion = new JLabel("Question : ------------");

        JButton btnReset = new JButton("Réinitialiser");

        add(new JLabel("Login :"));
        add(txtLogin);
        add(lblQuestion);
        add(new JLabel("Réponse :"));
        add(txtAnswer);
        add(new JLabel("Email :"));
        add(txtEmail);
        add(btnReset);

        // Quand on tape le login, afficher automatiquement la question
        txtLogin.addCaretListener(e -> updateQuestion());

        btnReset.addActionListener(e -> resetPassword());
    }

    // -----------------------------------------
    // Afficher la question secrète
    // -----------------------------------------
    private void updateQuestion() {
        String login = txtLogin.getText().trim();
        User u = userService.findByLogin(login);
        if (u != null) {
            lblQuestion.setText("Question : " + u.getQuestion());
        } else {
            lblQuestion.setText("Question : ------------");
        }
    }

    // -----------------------------------------
    // Réinitialisation du mot de passe
    // -----------------------------------------
    private void resetPassword() {
        String login = txtLogin.getText().trim();
        String answer = txtAnswer.getText().trim();
        String email = txtEmail.getText().trim();

        if (login.isEmpty() || answer.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier login
        User u = userService.findByLogin(login);
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Utilisateur introuvable.");
            return;
        }

        // Vérifier la réponse secrète
        if (!userService.verifySecurityAnswer(login, answer)) {
            JOptionPane.showMessageDialog(this, "Réponse incorrecte.");
            return;
        }

        // Générer un nouveau mot de passe
        String newPw = generatePassword();
        userService.changePassword(login, newPw);

        // Envoyer email
        sendEmail(email, login, newPw);

        JOptionPane.showMessageDialog(this,
            "Votre mot de passe a été réinitialisé.\nUn email vous a été envoyé.");

        dispose();
    }

    // -----------------------------------------
    // Génération de mot de passe
    // -----------------------------------------
    private String generatePassword() {
        return "PW" + (int)(Math.random() * 9000 + 1000);
    }

    // -----------------------------------------
    // Envoi email JavaMail
    // -----------------------------------------
    private void sendEmail(String to, String login, String pw) {
        final String sender = "tonEmail@gmail.com";
        final String pass = "ton-mot-de-passe-app";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, pass);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(sender));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject("Réinitialisation du mot de passe");
            msg.setText("Bonjour " + login + ",\nVotre nouveau mot de passe : " + pw);

            Transport.send(msg);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur d'envoi email.");
        }
    }
}
