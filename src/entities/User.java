/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

public class User {
    private String login;
    private String password;
    private String question;
    private String reponse;

    public User(String login, String password, String question, String reponse) {
        this.login = login;
        this.password = password;
        this.question = question;
        this.reponse = reponse;
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getQuestion() { return question; }
    public String getReponse() { return reponse; }

    public void setPassword(String password) { this.password = password; }
}
