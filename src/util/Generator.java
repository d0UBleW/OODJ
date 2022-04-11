/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.Appointment;
import gui.PopupDialog;
import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author LEGION
 */
public class Generator {
    
    public static String generateAppID() {
        String finalID = null;
        // Create random object with time seeded value
        Random rand = new Random(System.currentTimeMillis());
        List<Integer> ids = new ArrayList<>();
        File fh = new File(Appointment.FILENAME);
        try {
            Scanner sc = new Scanner(fh);
            // Get used id
            while (sc.hasNextLine()) {
                String id = sc.nextLine().split(";")[1];
                id = id.substring(1, id.length());
                ids.add(Integer.parseInt(id));
            }

            // Generate new unique id
            int rnd = rand.nextInt(999999);
            while (rnd == 0) {
                rnd = rand.nextInt(999999);
                for (int a: ids) {
                    if (a == rnd) {
                        rnd = 0;
                    }
                }
            }
            finalID = "A" + String.format("%06d", rnd);
        }
        catch (FileNotFoundException e) {
            PopupDialog.error("File not found: " + Appointment.FILENAME);
            System.exit(0);
        }
        return finalID;
    }

    public static void main(String[] args) {
        System.out.println(generateAppID());
    }
    
}
