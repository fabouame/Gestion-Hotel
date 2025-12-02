package services;

import connexion.Connexion;
import dao.IDao;
import entities.Chambre;
import entities.Client;
import entities.Reservation;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationService implements IDao<Reservation> {

    private ChambreService chambreService;
    private ClientService clientService;

    public ReservationService() {
        chambreService = new ChambreService();
        clientService = new ClientService();
    }

    @Override
    public boolean create(Reservation r) {

        if (!isRoomAvailable(r.getChambre().getNumero(), r.getDateDebut(), r.getDateFin())) {
            return false;
        }

        String sql = "INSERT INTO reservation (chambre, client, dateDebut, dateFin) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, r.getChambre().getNumero());
            ps.setInt(2, r.getClient().getId());
            ps.setDate(3, Date.valueOf(r.getDateDebut()));
            ps.setDate(4, Date.valueOf(r.getDateFin()));
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean update(Reservation r) {

        if (!isRoomAvailable(r.getChambre().getNumero(), r.getDateDebut(), r.getDateFin(), r.getId())) {
            return false;
        }

        String sql = "UPDATE reservation SET chambre = ?, client = ?, dateDebut = ?, dateFin = ? WHERE id = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, r.getChambre().getNumero());
            ps.setInt(2, r.getClient().getId());
            ps.setDate(3, Date.valueOf(r.getDateDebut()));
            ps.setDate(4, Date.valueOf(r.getDateFin()));
            ps.setInt(5, r.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean delete(Reservation r) {
        String sql = "DELETE FROM reservation WHERE id = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, r.getId());
            ps.executeUpdate();
            return true;

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public Reservation findById(int id) {
        String sql = "SELECT * FROM reservation WHERE id = ?";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Chambre ch = chambreService.findById(rs.getInt("chambre"));
                Client cl = clientService.findById(rs.getInt("client"));

                return new Reservation(
                        rs.getInt("id"),
                        ch,
                        cl,
                        rs.getDate("dateDebut").toLocalDate(),
                        rs.getDate("dateFin").toLocalDate()
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public List<Reservation> findAll() {
        List<Reservation> list = new ArrayList<>();
        String sql = "SELECT * FROM reservation";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Chambre ch = chambreService.findById(rs.getInt("chambre"));
                Client cl = clientService.findById(rs.getInt("client"));

                list.add(new Reservation(
                        rs.getInt("id"),
                        ch,
                        cl,
                        rs.getDate("dateDebut").toLocalDate(),
                        rs.getDate("dateFin").toLocalDate()
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public boolean isRoomAvailable(int numeroChambre, LocalDate d1, LocalDate d2) {
        return isRoomAvailable(numeroChambre, d1, d2, -1);
    }
    
    

    public boolean isRoomAvailable(int numeroChambre, LocalDate d1, LocalDate d2, int idToExclude) {

        String sql = "SELECT * FROM reservation WHERE chambre = ? AND id != ? "
                   + "AND (dateDebut <= ? AND dateFin >= ?)";

        try {
            PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
            ps.setInt(1, numeroChambre);
            ps.setInt(2, idToExclude);
            ps.setDate(3, Date.valueOf(d2));
            ps.setDate(4, Date.valueOf(d1));

            ResultSet rs = ps.executeQuery();
            return !rs.next();

        } catch (SQLException ex) {
            Logger.getLogger(ReservationService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public List<Chambre> findChambresDisponibles(LocalDate d1, LocalDate d2) {
    List<Chambre> list = new ArrayList<>();
    String sql =
        "SELECT * FROM chambre WHERE numero NOT IN (" +
        " SELECT chambre FROM reservation " +
        " WHERE NOT (dateFin < ? OR dateDebut > ?) )";

    try {
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setDate(1, Date.valueOf(d1));
        ps.setDate(2, Date.valueOf(d2));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Chambre(
                rs.getInt("numero"),
                rs.getString("type"),
                rs.getDouble("prixParNuit")
            ));
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }
    
    public List<Reservation> findByClient(int clientId) {
    List<Reservation> list = new ArrayList<>();
    String sql = "SELECT * FROM reservation WHERE client = ?";

    try {
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setInt(1, clientId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Chambre ch = chambreService.findById(rs.getInt("chambre"));
            Client cl = clientService.findById(clientId);

            list.add(new Reservation(
                rs.getInt("id"),
                ch,
                cl,
                rs.getDate("dateDebut").toLocalDate(),
                rs.getDate("dateFin").toLocalDate()
            ));
        }

    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return list;
}

    public List<Chambre> findChambresDisponiblesParType(String type, LocalDate d1, LocalDate d2) {
    List<Chambre> list = new ArrayList<>();
    String sql =
        "SELECT * FROM chambre WHERE type = ? AND numero NOT IN (" +
        " SELECT chambre FROM reservation " +
        " WHERE NOT (dateFin < ? OR dateDebut > ?) )";

    try {
        PreparedStatement ps = Connexion.getConnection().prepareStatement(sql);
        ps.setString(1, type);
        ps.setDate(2, Date.valueOf(d1));
        ps.setDate(3, Date.valueOf(d2));

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            list.add(new Chambre(
                rs.getInt("numero"),
                rs.getString("type"),
                rs.getDouble("prixParNuit")
            ));
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }

    return list;
}


    
}
