/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author LEGION
 */
public class PopupDialog {

    public static void error() {
        JOptionPane.showMessageDialog(null, "Unexpected error occured",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void error(String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void notif(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String msg) {
        int dialogResult = JOptionPane.showConfirmDialog(null, msg, "Confirmation", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            return true;
        }
        return false;
    }

    public static boolean prompt(String msg, boolean isPasswd, String target) {
        if (isPasswd) {
            JPanel panel = new JPanel();
            JLabel label = new JLabel(msg);
            JPasswordField passwd = new JPasswordField(20);
            panel.add(label);
            panel.add(passwd);
            String[] options = {"OK", "Cancel"};
            int option = JOptionPane.showOptionDialog(
                    null,
                    panel,
                    "Secret",
                    JOptionPane.NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (option == 0) {
                String pw = String.valueOf(passwd.getPassword());
                if (pw == null || !pw.equals(target)) {
                    return false;
                }
                return true;
            }
            return false;
        }
        else {
            String dialogResult = JOptionPane.showInputDialog(null, msg, "Input");
            if (dialogResult == null || !dialogResult.equals(target)) {
                return false;
            }
            else {
                return true;
            }
        }
    }
    
}
