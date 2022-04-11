/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import util.Convert;

/**
 *
 * @author LEGION
 */
public abstract class People extends Account {
    // People and appointment composition
    // appointment is solely created because of a person action

    // Attributes
    private String name;
    private LocalDate DOB;
    private String phoneNumber;
    private String email;
    private List<Appointment> appoint;


    public People() {
    }

    public People(String ID, String password) {
        super(ID, password);
        this.appoint = new ArrayList<>();
    }

    public People(String name, LocalDate DOB, String phoneNumber, String email,
            String ID, String password) {
        super(ID, password);
        this.name = name;
        this.DOB = DOB;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.appoint = new ArrayList<>();
    }

    public People(String[] arr) {
        super(arr[0], arr[1]);
        this.name = arr[2];
        this.DOB = Convert.StringToLocalDate(arr[3], "dd/MM/yyyy");
        this.email = arr[4];
        this.phoneNumber = arr[5];
        this.appoint = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setDOB(LocalDate DOB) {
        this.DOB = DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = Convert.StringToLocalDate(DOB, "dd/MM/yyyy");
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Appointment> getAppoint() {
        return appoint;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String[] toArray() {
        int appointSize = this.getAppoint().size();
        String[] arr = null;
        if (appointSize > 0) {
            // Helper to write appointment data to text file
            arr = new String[appointSize];
            for (int i = 0; i < appointSize; i++) {
                arr[i] = this.getID()+";"+this.getAppoint().get(i).toString();
            }
        }
        else {
            arr = new String[6];
            arr[0] = this.getID();
            arr[1] = this.getPassword();
            arr[2] = this.getName();
            arr[3] = this.getDOB().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            arr[4] = this.getEmail();
            arr[5] = this.getPhoneNumber();
        }
        return arr;
    }

    @Override
    public String toString() {
        String[] arr = this.toArray();
        if (this.getAppoint().size() > 0) {
            // Helper to write appointment data to text file
            return String.join("\n", arr);
        }
        return String.join(";", arr);
    }

    @Override
    public boolean register(String fileName) {
        boolean status = super.register(fileName);
        return status;
    }

}
