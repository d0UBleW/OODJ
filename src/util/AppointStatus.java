/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author LEGION
 */
public enum AppointStatus {
    WAITING("Waiting"),
    COMPLETED("Completed");

    private String status;

    private static final Map<String, AppointStatus> BY_STATUS = new HashMap<>();

    // Map "Waiting" to AppointStatus.WAITING, etc.
    static {
        for (AppointStatus s: AppointStatus.values()) {
            BY_STATUS.put(s.status, s);
        }
    }
    
    private AppointStatus(String status) {
        this.status = status;
    }

    // Get enum value from given string
    public static AppointStatus valueOfStatus(String status) {
        return BY_STATUS.get(status);
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }

    
    
}
