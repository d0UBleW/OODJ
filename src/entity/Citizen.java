/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.time.LocalDate;

/**
 *
 * @author LEGION
 */
public class Citizen extends People {
    public static final String FILENAME = "citizen.txt";
    
    public Citizen() {
    }

    public Citizen(String ID, String password) {
        super(ID, password);
    }

    public Citizen(String ICNumber, String name, LocalDate DOB,
            String phoneNumber, String email, String password) {
        super(name, DOB, phoneNumber, email, ICNumber, password);
    }

    public Citizen(String[] arr) {
        super(arr);
    }

    @Override
    public String[] toArray() {
        String[] arr = super.toArray();
        return arr;
    }

    @Override
    public boolean register(String fileName) {
        return super.register(Citizen.FILENAME);
    }

    @Override
    public boolean login(String fileName) {
        return super.login(Citizen.FILENAME);
    }

    
}
