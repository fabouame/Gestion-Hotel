/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */package app;

import entities.Chambre;
import entities.Client;
import entities.Reservation;
import java.time.LocalDate;
import services.ChambreService;
import services.ClientService;
import services.ReservationService;

public class AppReservation {
    public static void main(String[] args) {

        ChambreService chambreService = new ChambreService();
        ClientService clientService = new ClientService();
        ReservationService reservationService = new ReservationService();

        Chambre ch = chambreService.findById(101);
        Client cl = clientService.findById(1);

        Reservation r = new Reservation(
                0,
                ch,
                cl,
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 12)
        );

        reservationService.create(r);

        for (Reservation res : reservationService.findAll()) {
            System.out.println(res);
        }
    }
}
