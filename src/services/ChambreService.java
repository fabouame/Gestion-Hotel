/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import connexion.Connexion;
import dao.IDao;
import entities.Chambre;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChambreService implements IDao<Chambre> {

    @Override
    public boolean create(Chambre o) {
        String sql = "INSERT INTO chambre (numero, type, prixParNuit) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, o.getNumero());
            ps.setString(2, o.getType());
            ps.setDouble(3, o.getPrixParNuit());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean update(Chambre o) {
        String sql = "UPDATE chambre SET type = ?, prixParNuit = ? WHERE numero = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setString(1, o.getType());
            ps.setDouble(2, o.getPrixParNuit());
            ps.setInt(3, o.getNumero());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean delete(Chambre o) {
        String sql = "DELETE FROM chambre WHERE numero = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, o.getNumero());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public Chambre findById(int id) {
        String sql = "SELECT * FROM chambre WHERE numero = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Chambre(
                        rs.getInt("numero"),
                        rs.getString("type"),
                        rs.getDouble("prixParNuit")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<Chambre> findAll() {
        List<Chambre> list = new ArrayList<>();
        String sql = "SELECT * FROM chambre";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Chambre(
                        rs.getInt("numero"),
                        rs.getString("type"),
                        rs.getDouble("prixParNuit")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ChambreService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
