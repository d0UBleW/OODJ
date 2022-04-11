/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import gui.PopupDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LEGION
 */
public class Personnel extends Account {

    public static final String FILENAME = "admin.txt";

    public Personnel(String ID, String password) {
        super(ID, password);
    }

    @Override
    public String[] toArray() {
        String[] details = {
            this.getID(),
            this.getPassword(),
        };
        return details;
    }
    
    @Override
    public boolean login(String fileName) {
        List<Personnel> admins = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(file);
            admins = (List<Personnel>) in.readObject();
            in.close();
            file.close();
        } catch (IOException ex) { // Empty file
            return false;
        } catch (ClassNotFoundException CNFEx) {
            PopupDialog.error("Unexpected error occurred: " + CNFEx.getMessage());
            System.exit(0);
        }

        for (Personnel admin: admins) {
            if (admin.getID().equals(this.getID()) &&
                    admin.getPassword().equals(this.getPassword())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(String fileName) {
        File checkFile = new File(FILENAME);
        if (!checkFile.exists()) {
            try {
                checkFile.createNewFile();
            } catch (IOException iex) {
                PopupDialog.error("Unable to create file: " + FILENAME);
                System.exit(0);
            }
        }

        List<Personnel> admins = new ArrayList<>();
        try {
            FileInputStream file = new FileInputStream(FILENAME);
            ObjectInputStream in = new ObjectInputStream(file);
            admins = (List<Personnel>) in.readObject();
            in.close();
            file.close();
        } catch (ClassNotFoundException ex1) {
            PopupDialog.error("Unexpected error occurred: " + ex1.getMessage());
            System.exit(0);
        } catch (IOException ex2) { // Empty file
        }

        // Check duplicate ID
        for (Personnel admin: admins) {
            if (admin.getID().equals(this.getID())) {
                return false;
            }
        }

        admins.add(this);

        try {
            FileOutputStream file = new FileOutputStream(FILENAME);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(admins);
            out.close();
            file.close();
        } catch (IOException ex) {
            PopupDialog.error("Unexpected error occurred: " + ex.getMessage());
            System.exit(0);
        }
        return true;
    }


    @Override
    public String toString() {
        String[] arr = this.toArray();
        return String.join(";", arr);
    }

    

}
