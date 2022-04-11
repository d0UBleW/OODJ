/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gui.PopupDialog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

/**
 *
 * @author LEGION
 */
public class Checker {
    /**
     * @@Create file if not found
     * @param fileName: File to be counted
     * @return number of line
     */
    public static long countLine(String fileName) {
        File fh = new File(fileName);
        long count = 0;
        try (Scanner fr = new Scanner(fh)) {
            while (fr.hasNextLine()) {
                fr.nextLine();
                count++;
            }
            fr.close();
        }
        catch (FileNotFoundException e) {
            try {
                FileWriter fw = new FileWriter(fh);
            }
            catch (IOException ioE) {
                PopupDialog.error(ioE.toString());
                System.exit(0);
            }
        }
        return count;
    }

    /**
     * @@Create file if not found
     * @param fileName: File to be counted
     * @return number of field/column
     */
    public static int countColumn(String fileName) {
        File fh = new File(fileName);
        int count = 0;
        try (Scanner fr = new Scanner(fh)) {
            while (fr.hasNextLine()) {
                count = fr.nextLine().split(";").length;
                break;
            }
            fr.close();
        }
        catch (FileNotFoundException e) {
            try {
                FileWriter fw = new FileWriter(fh);
            }
            catch (IOException ioE) {
                PopupDialog.error(ioE.toString());
                System.exit(0);
            }
        }
        return count;
    }
    
    public static boolean isICPNum(String ICPNum, String role) {
        String regex;
        if (role.equals("Citizen"))
            regex = "^IC(?!0{6})\\d{6}$";
        else
            regex = "^P(?!0{6})\\d{6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ICPNum);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile(
                "^\\w+([._-]?\\w+)*@\\w+([-.]?\\w+)*\\.(com|org|cc|my)$"
        );
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isPhone(String phone) {
        Pattern pattern = Pattern.compile("^\\+(?!0{11,13})\\d{11,13}$");
        Matcher matcher = pattern.matcher(phone);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isPassword(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$");
        Matcher matcher = pattern.matcher(password);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean isCentreID(String CID) {
        Pattern pattern = Pattern.compile("^C(?!000)\\d{3}$");
        Matcher matcher = pattern.matcher(CID);
        boolean matchFound = matcher.find();
        if (matchFound) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean hasNoBadChar(String str, char[] charArr) {
        String temp = String.join("", String.valueOf(charArr));
        temp = Convert.escapeSpecialChar(temp);
        temp = String.format("[%s]", temp);
        Pattern pattern = Pattern.compile(temp);
        Matcher matcher = pattern.matcher(str);
        if (!matcher.find()) {
            return true;
        }
        else {
            return false;
        }
    }

    public static boolean hasDuplicate(String ID, String fileName) {
        File fh = new File(fileName);
        List<String> buf;
        try {
            Scanner sc = new Scanner(fh);
            while (sc.hasNextLine()) {
                buf = Arrays.asList(sc.nextLine().split(";"));
                if (ID.equals(buf.get(0))) {
                    return true;
                }
            }
        }
        catch (FileNotFoundException e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        String a = "Wi;";
        char[] bad = {
            ';',
        };
        System.out.println(hasNoBadChar(a, bad));
    }
}
