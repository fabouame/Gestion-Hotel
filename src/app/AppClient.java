/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entities.Client;
import services.ClientService;

public class AppClient {
    public static void main(String[] args) {

        ClientService cs = new ClientService();

        cs.create(new Client(0, "Fati", "Marrakech", "0666000000"));
        cs.create(new Client(0, "amal", "Rabat", "0611223344"));
        cs.create(new Client(0, "rim", "Casablanca", "0622334455"));

        for (Client c : cs.findAll()) {
            System.out.println(c);
        }
    }
}
