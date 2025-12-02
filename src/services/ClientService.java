/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import connexion.Connexion;
import dao.IDao;
import entities.Client;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientService implements IDao<Client> {

    @Override
    public boolean create(Client o) {
        String sql = "INSERT INTO client (nom, ville, telephone) VALUES (?, ?, ?)";
        
        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getVille());
            ps.setString(3, o.getTelephone());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean update(Client o) {
        String sql = "UPDATE client SET nom = ?, ville = ?, telephone = ? WHERE id = ?";
        
        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getVille());
            ps.setString(3, o.getTelephone());
            ps.setInt(4, o.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Client o) {
        String sql = "DELETE FROM client WHERE id = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, o.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Client findById(int id) {
        String sql = "SELECT * FROM client WHERE id = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("ville"),
                    rs.getString("telephone")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<Client> findAll() {
        List<Client> list = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Client(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("ville"),
                    rs.getString("telephone")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
