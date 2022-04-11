/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LEGION
 */
public class InitDictionary {
    /**
     * 
     * @param nameAsKey 
     * @return HashMap with name as key if nameAsKey is true, else use id as the key
     */
    public static Map<String, Vaccine> vacData(boolean nameAsKey) {
        Map<String, Vaccine> vaccines = new HashMap<>();
        int vacCount = (int) Checker.countLine(Vaccine.FILENAME);
        if (vacCount > 0) {
            Vaccine[] vacArr = new Vaccine[vacCount];
            Transfer.importObject(vacArr, Vaccine.FILENAME);
            for (Vaccine vac: vacArr) {
                String vacID = String.valueOf(vac.getID());
                String vacName = vac.getName();
                if (nameAsKey) {
                    vaccines.put(vacName, vac);
                }
                else {
                    vaccines.put(vacID, vac);
                }
            }
        }
        return vaccines;
    }

    /**
     * 
     * @return HashMap which map centre ID to the object
     */
    public static Map<String, Centre> centreData() {
        Map<String, Centre> centres = new HashMap<>();
        int centreCount = (int) Checker.countLine(Centre.FILENAME);
        if (centreCount > 0) {
            Centre[] centreArr = new Centre[centreCount];
            Transfer.importObject(centreArr, Centre.FILENAME);
            for (Centre centre: centreArr) {
                String centreID = centre.getCentreID();
                centres.put(centreID, centre);
            }
        }
        return centres;
    }

    /**
     * 
     * @return HashMap which map citizen ID to the object
     */
    public static Map<String, Citizen> citizenDict() {
        Map<String, Citizen> citizens = new HashMap<>();
        int czCount = (int) Checker.countLine(Citizen.FILENAME);
        if (czCount > 0) {
            Citizen[] czArr = new Citizen[czCount];
            Transfer.importObject(czArr, Citizen.FILENAME);
            for (Citizen cz: czArr) {
                citizens.put(cz.getID(), cz);
            }
        }
        return citizens;
    }

    /**
     * 
     * @return HashMap which map noncitizen id to the object
     */
    public static Map<String, NonCitizen> noncitizenDict() {
        Map<String, NonCitizen> noncitizens = new HashMap<>();
        int nczCount = (int) Checker.countLine(NonCitizen.FILENAME);
        if (nczCount > 0) {
            NonCitizen[] nczArr = new NonCitizen[nczCount];
            Transfer.importObject(nczArr, NonCitizen.FILENAME);
            for (NonCitizen ncz: nczArr) {
                noncitizens.put(ncz.getID(), ncz);
            }
        }
        return noncitizens;
    }
}
