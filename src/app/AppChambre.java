/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entities.Chambre;
import services.ChambreService;

public class AppChambre {
    public static void main(String[] args) {

        ChambreService cs = new ChambreService();

        cs.create(new Chambre(101, "Single", 50));
        cs.create(new Chambre(102, "Double", 80));
        cs.create(new Chambre(103, "Suite", 150));

        for (Chambre c : cs.findAll()) {
            System.out.println(c);
        }
    }
}
