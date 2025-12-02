/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

public class LoginWindow extends javax.swing.JFrame {

    public LoginWindow() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 300);
        setResizable(false);
        setLocationRelativeTo(null);

        LoginForm lf = new LoginForm();
        this.setContentPane(lf.getContentPane());
        lf.setVisible(true);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new LoginWindow().setVisible(true);
        });
    }
}
