/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.*;
import gui.PopupDialog;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author LEGION
 */
public class Transfer {
    /**
     * 
     * @param obj Object array where data will be imported
     * @param fileName file where data will be imported from
     */
    public static void importObject(Object[] obj, String fileName) {
        int line = 0;
        String[] details;
        File fh = new File(fileName);
        try {
            Scanner sc = new Scanner(fh);
            while (sc.hasNextLine()) {
                details = sc.nextLine().split(";");
                if (obj instanceof Citizen[]) {
                    obj[line] = new Citizen(details);
                    line++;
                }
                else if (obj instanceof NonCitizen[]) {
                    obj[line] = new NonCitizen(details);
                    line++;
                }
                else if (obj instanceof Vaccine[]) {
                    obj[line] = new Vaccine(details);
                    line++;
                }
                else if (obj instanceof Centre[]) {
                    obj[line] = new Centre(details);
                    line++;
                }
            }
        }
        catch (FileNotFoundException e) {
            PopupDialog.error("File not found: " + fileName);
            System.exit(0);
        }
    }

    /**
     * 
     * @param obj Object array as the data source to be exported
     * @param fileName file to which the data will be exported to
     */
    public static void exportObject(Object[] obj, String fileName) {
        try {
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (Object data: obj) {
                pw.println(data.toString());
            }
            pw.close();
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            PopupDialog.error("File not found: " + fileName);
            System.exit(0);
        }

    }

    /**
     * 
     * @param czDict citizen dictionary to which the appointment will be imported to
     * @param nczDict noncitizen dictionary to which the appointment will be imported to
     */
    public static void readAppoint(Map<String, Citizen> czDict, Map<String, NonCitizen> nczDict) {
        Map<String, Vaccine> vacDictName = InitDictionary.vacData(true);
        Map<String, Centre> centreDict = InitDictionary.centreData();
        File fh = new File(Appointment.FILENAME);
        int counter = 0;
        String[][] arr = null;
        int appCount = (int) Checker.countLine(Appointment.FILENAME);
        if (appCount > 0) {
            int appCol = (int) Checker.countColumn(Appointment.FILENAME);
            arr = new String[appCount][appCol];
        }
        try {
            Scanner sc = new Scanner(fh);
            while (sc.hasNextLine()) {
                arr[counter] = sc.nextLine().split(";");
                counter++;
            }
        }
        catch (FileNotFoundException e) {
            PopupDialog.error("File not found: " + Appointment.FILENAME);
            System.exit(0);
        }
        if (arr != null) {
            for (String[] app: arr) {
                if (app[0].substring(0, 1).equals("P")) {
                    nczDict.get(app[0])
                            .getAppoint()
                            .add(new Appointment(
                                    app[1],
                                    vacDictName.get(app[2]),
                                    centreDict.get(app[3]),
                                    Convert.StringToLocalDateTime(app[4], "dd/MM/yyyy HH:mm")
                            ));
                }
                else {
                    czDict.get(app[0])
                            .getAppoint()
                            .add(new Appointment(
                                    app[1],
                                    vacDictName.get(app[2]),
                                    centreDict.get(app[3]),
                                    Convert.StringToLocalDateTime(app[4], "dd/MM/yyyy HH:mm")
                            ));
                }
            }
        }
    }

    /**
     * 
     * @param czArr citizen array which contains the appointment data to be written to text file
     * @param nczArr noncitizen array which contains the appointment data to be written to text file
     */
    public static void writeAppoint(Citizen[] czArr, NonCitizen[] nczArr) {
        try {
            FileWriter fw = new FileWriter(Appointment.FILENAME);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            for (Citizen cz: czArr) {
                for (Appointment app: cz.getAppoint()) {
                    String[] temp = {
                        cz.getID(),
                        app.getID(),
                        app.getVaccine().getName(),
                        app.getCentre().getCentreID(),
                        Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy HH:mm"),
                    };
                    pw.println(String.join(";", temp));
                }
            }
            for (NonCitizen ncz: nczArr) {
                for (Appointment app: ncz.getAppoint()) {
                    String[] temp = {
                        ncz.getID(),
                        app.getID(),
                        app.getVaccine().getName(),
                        app.getCentre().getCentreID(),
                        Convert.LocalDateTimeToString(app.getDateTime(), "dd/MM/yyyy HH:mm"),
                    };
                    pw.println(String.join(";", temp));
                }
            }
            pw.close();
            bw.close();
            fw.close();
        }
        catch (IOException e) {
            PopupDialog.error("Unexpected error occurred: "+ e.getMessage());
            System.exit(0);
        }
    }

}
