/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import entity.*;

public class Auth {
    /**
     * 
     * @param acc: Account object
     * @return true if successful, false otherwise
     */
    public static boolean register(Account acc) {
        boolean registerStatus = acc.register("");
        return registerStatus;
    }

    /**
     * 
     * @param acc: Account object
     * @return true if successful, false otherwise
     */
    public static boolean login(Account acc) {
        if (acc == null) {
            return false;
        }
        boolean loginStatus = acc.login("");
        return loginStatus;
    }

    
}
