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
public class Address {
    private String street;
    private String city;

    public Address() {
    }

    public Address(String street, String city) {
        this.street = street;
        this.city = city;
    }

    public Address(String street_city) {
        String[] arr = street_city.split("_");
        this.street = arr[0];
        this.city = arr[1];
    }
    
    public Address(String[] arr) {
        this.street = arr[0];
        this.city = arr[1];
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return street + "_" + city;
    }

    
}
