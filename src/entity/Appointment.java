/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.time.LocalDateTime;
import util.AppointStatus;

/**
 *
 * @author LEGION
 */
public class Appointment implements Comparable<Appointment> {
    
    // People and appointment composition
    // Vaccine and appointment aggregation
    // Vaccine itself exists not due to a certain appointment is created

    private String ID;
    private Vaccine vaccine;
    private Centre centre;
    private AppointStatus status;
    private LocalDateTime dateTime;
    public static final String FILENAME = "appointment.txt";

    public Appointment() {
    }

    public Appointment(String ID, Vaccine vaccine, Centre centre,
            LocalDateTime dateTime) {
        this.ID = ID;
        this.vaccine = vaccine;
        this.centre = centre;
        this.dateTime = dateTime;
        if (dateTime.isBefore(LocalDateTime.now())) {
            this.status = AppointStatus.COMPLETED;
        }
        else {
            this.status = AppointStatus.WAITING;
        }
    }

    public AppointStatus getStatus() {
        return status;
    }

    public Centre getCentre() {
        return centre;
    }

    public String getID() {
        return ID;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setVaccine(Vaccine vaccine) {
        this.vaccine = vaccine;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        if (dateTime.isBefore(LocalDateTime.now())) {
            this.status = AppointStatus.COMPLETED;
        }
        else {
            this.status = AppointStatus.WAITING;
        }
    }

    public String[] toArray() {
        String[] arr = {
            this.getID(),
            this.getCentre().toString(),
            this.getVaccine().toString(),
            this.getStatus().toString(),
        };
        return arr;
    }

    @Override
    public String toString() {
        String[] arr = this.toArray();
        return String.join(";", arr);
    }

    @Override
    public int compareTo(Appointment other) {
        return this.getDateTime().compareTo(other.getDateTime());
    }

    
    
}
