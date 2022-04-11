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
public class VaccineStock extends Vaccine{

    // Vaccine and VaccineStock composition
    private long quantity;

    public VaccineStock() {
    }

    public VaccineStock(long quantity, String[] arr) {
        super(arr);
        this.quantity = quantity;
    }

    public VaccineStock(long quantity, String ID, String name, int dose) {
        super(ID, name, dose);
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return this.getID() + ":" + this.getQuantity();
    }

}
