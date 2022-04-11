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
public class NonCitizen extends People {
    
    // Attributes
    public static final String FILENAME = "noncitizen.txt";
    
    public NonCitizen() {
    }

    public NonCitizen(String ID, String password) {
        super(ID, password);
    }

    public NonCitizen(String PassportNumber, String name, LocalDate DOB,
            String phoneNumber, String email, String password) {
        super(name, DOB, phoneNumber, email, PassportNumber, password);
    }

    public NonCitizen(String[] arr) {
        super(arr);
    }

    @Override
    public String[] toArray() {
        String[] arr = super.toArray();
        return arr;
    }

    @Override
    public boolean register(String fileName) {
        return super.register(NonCitizen.FILENAME);
    }

    @Override
    public boolean login(String fileName) {
        return super.login(NonCitizen.FILENAME);
    }
    
}
