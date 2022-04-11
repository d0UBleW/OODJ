/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author LEGION
 */
public class Vaccine {

    private String ID;
    private String name;
    private int dose;
    public static final String FILENAME = "vaccine.txt";

    public Vaccine() {
    }

    public Vaccine(String ID, String name, int dose) {
        this.ID = ID;
        this.name = name;
        this.dose = dose;
    }

    public Vaccine(String[] arr) {
        this.ID = arr[0];
        this.name = arr[1];
        this.dose = Integer.parseInt(arr[2]);
    }

    public int getDose() {
        return dose;
    }

    public String getName() {
        return name;
    }

    public String getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public String[] toArray() {
        String[] arr = {
            String.valueOf(this.getID()),
            this.getName(),
            String.valueOf(this.getDose()),
        };
        return arr;
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
