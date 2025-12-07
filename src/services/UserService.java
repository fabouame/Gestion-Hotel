/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import connexion.Connexion;
import static connexion.Connexion.getConnection;
import dao.IDao;
import entities.User;
import utiles.SecurityUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IDao<User> {

    @Override
    public boolean create(User u) {
        String sql = "INSERT INTO user(login, password, question, reponse) VALUES (?, SHA1(?), ?, ?)";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, u.getLogin());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getQuestion());
            ps.setString(4, SecurityUtil.hashSHA1(u.getReponse()));
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Create User Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(User u) {
        String sql = "UPDATE user SET password = SHA1(?) WHERE login = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, u.getPassword());
            ps.setString(2, u.getLogin());
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(User u) {
        return false; // not needed
    }

    @Override
    public User findById(int id) {
        return null; // not needed
    }

    @Override
    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new User(
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("question"),
                    rs.getString("reponse")
                ));
            }

        } catch (SQLException e) {
            System.out.println("FindAll Error: " + e.getMessage());
        }
        return list;
    }


    // ---------------------------- CUSTOM METHODS ------------------------------

    public User findByLogin(String login) {
    String sql = "SELECT * FROM user WHERE login = ?";

    try {
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setString(1, login);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                rs.getString("login"),
                rs.getString("password"),
                rs.getString("question"),
                rs.getString("reponse")
            );
        }
    } catch (SQLException e) {
        System.out.println("FindByLogin Error: " + e.getMessage());
    }
    return null;
}


    public boolean verifySecurityAnswer(String login, String answer) {
        String sql = "SELECT reponse FROM user WHERE login = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String correctHash = rs.getString("reponse");
                String userHash = SecurityUtil.hashSHA1(answer);
                return correctHash.equals(userHash);
            }

        } catch (SQLException e) {
            System.out.println("Verify answer error: " + e.getMessage());
        }
        return false;
    }

    public boolean changePassword(String login, String newPw) {
        String sql = "UPDATE user SET password = SHA1(?) WHERE login = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, newPw);
            ps.setString(2, login);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            System.out.println("Change password error: " + e.getMessage());
        }
        return false;
    }
   
    public boolean isConnectionValid() {
    Connection conn = getConnection();
    if (conn == null) {
        System.out.println("Connection is NULL");
        return false;
    }
    try {
        return !conn.isClosed();
    } catch (SQLException e) {
        System.out.println("Connection check error: " + e.getMessage());
        return false;
    }
}

public boolean authenticate(String login, String password) {
    Connection conn = getConnection();
    if (conn == null) {
        System.out.println("Cannot authenticate - no database connection");
        return false;
    }
    
    String sql = "SELECT * FROM user WHERE login = ? AND password = SHA1(?)";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    } catch (SQLException e) {
        System.out.println("Auth error: " + e.getMessage());
    }
    return false;
}
public boolean resetPasswordAndSendEmail(String login, String email, String newPassword) {
    boolean updated = changePassword(login, newPassword);

    if (updated) {
        return EmailSender.sendNewPassword(email, newPassword);
    }
    return false;
}

}
