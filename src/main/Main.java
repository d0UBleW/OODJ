/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import gui.*;

/**
 *
 * @author LEGION
 */
public class Main {
    public static void main(String[] args) {
        // Admin secret key: letmein (Demonstration purposes)
        // vaccine.txt is required to exist before execution
        // format: ID;Name;Dose
        new LoginForm().setVisible(true);
    }
}
