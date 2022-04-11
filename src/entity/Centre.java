/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import util.InitDictionary;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LEGION
 */
public class Centre {
    // Centre and VaccineStock composition
    // Centre and Adddress aggregation
    private String CentreID;
    private String name;
    private Address addr;
    private List<VaccineStock> vaccineStocks;
    public static final String FILENAME = "centre.txt";

    public Centre() {
//        addr = new Address();
        vaccineStocks = new ArrayList<>();
    }

    public Centre(String CentreID, String name, Address addr) {
        this.CentreID = CentreID;
        this.name = name;
        this.addr = addr;
        vaccineStocks = new ArrayList<>();
    }

    public Centre(String[] arr) {
        CentreID = arr[0];
        name = arr[1];
        addr = new Address(arr[2]);
        vaccineStocks = new ArrayList<>();
        Map<String, Vaccine> vacData = InitDictionary.vacData(false);
        String[] vacArr = arr[3].split("\\|");
        for (String vac : vacArr) {
            String[] temp = vac.split(":");
            long qty = Long.parseLong(temp[1]);
            String vacName = vacData.get(temp[0]).getName();
            int vacDose = vacData.get(temp[0]).getDose();
            VaccineStock stock = new VaccineStock(qty, temp[0], vacName, vacDose);
            this.vaccineStocks.add(stock);
        }
    }

    public void setAddr(Address addr) {
        this.addr = addr;
    }

    public void setCentreID(String CentreID) {
        this.CentreID = CentreID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddr() {
        return addr;
    }

    public String getCentreID() {
        return CentreID;
    }

    public String getName() {
        return name;
    }

    public List<VaccineStock> getStockVaccines() {
        return vaccineStocks;
    }

    public String toStringStockVaccines() {
        String[] arr = new String[vaccineStocks.size()];
        int i = 0;
        for (VaccineStock stock: vaccineStocks) {
            arr[i] = stock.toString();
            i++;
        }
        return String.join("|", arr);
    }

    public String[] toArray() {
        String[] arr = {
            this.getCentreID(),
            this.getName(),
            this.getAddr().toString(),
            this.toStringStockVaccines(),
        };
        return arr;
    }


    @Override
    public String toString() {
        String[] arr = this.toArray();
        return String.join(";", arr);
    }

    public void initStock() {
        Map<String, Vaccine> vacData = InitDictionary.vacData(false);
        List<VaccineStock> vStock = this.getStockVaccines();
        for (Vaccine vac: vacData.values()) {
            VaccineStock temp = new VaccineStock();
            temp.setID(vac.getID());
            temp.setName(vac.getName());
            temp.setDose(vac.getDose());
            temp.setQuantity(0);
            vStock.add(temp);
        }
    }
    
}
