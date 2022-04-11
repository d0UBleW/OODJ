/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import gui.PopupDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import util.Checker;

/**
 *
 * @author LEGION
 */
public abstract class Account implements java.io.Serializable {

    private String ID;
    private String password;

    public Account() {
    }

    public Account(String ID, String password) {
        this.ID = ID;
        this.password = password;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getID() {
        return ID;
    }

    public String getPassword() {
        return password;
    }

    public abstract String[] toArray();
    
    @Override
    public abstract String toString();

    public boolean login(String fileName) {
        File fh = new File(fileName);
        String[] credentials;
        boolean matchID;
        boolean matchPasswd;
        try {
            Scanner sc = new Scanner(fh);
            while (sc.hasNextLine()) {
                credentials = sc.nextLine().split(";");
                matchID = this.ID.equals(credentials[0]);
                matchPasswd = this.password.equals(credentials[1]);
                if (matchID && matchPasswd) {
                    return true;
                }
            }
        }
        catch (FileNotFoundException e) {
            return false;
        }

        return false;
    }

    public boolean hasDuplicate(String fileName) {
        File fh = new File(fileName);
        List<String> buf;
        try {
            Scanner sc = new Scanner(fh);
            while (sc.hasNextLine()) {
                buf = Arrays.asList(sc.nextLine().split(";"));
                if (this.getID().equals(buf.get(0))) {
                    return true;
                }
            }
        }
        catch (FileNotFoundException e) {
            return false;
        }
        return false;
    }

    public boolean register(String fileName) {
        if (Checker.hasDuplicate(this.getID(), fileName)) {
            return false;
        }
        String[] details = this.toArray();

        try {
            // Open file in append mode (2nd parameter true)
            FileWriter fw = new FileWriter(fileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(String.join(";", details));
            pw.close();
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            PopupDialog.error();
            System.exit(0);
        }
        return true;
    }


}
