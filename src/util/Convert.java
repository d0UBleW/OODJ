/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import gui.PopupDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 *
 * @author LEGION
 */
public class Convert {

    /**
     *
     * @param ld
     * @param format
     * @return
     */
    public static LocalDate StringToLocalDate(String ld, String format) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return LocalDate.parse(ld, fmt);
    }

    public static String LocalDateToString(LocalDate ld, String format) {
        return ld.format(DateTimeFormatter.ofPattern(format));
    }

    public static String LocalDateTimeToString(LocalDateTime ldt, String format) {
        return ldt.format(DateTimeFormatter.ofPattern(format));
    }

    public static LocalDateTime StringToLocalDateTime(String ldt, String format) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(ldt, fmt);
    }

    public static LocalTime StringToLocalTime(String lt, String format) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(lt, fmt);
    }

    public static String LocalTimeToString(LocalTime lt, String format) {
        return lt.format(DateTimeFormatter.ofPattern(format));
    }

    public static Date StringToDate(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        }
        catch (ParseException e) {
            PopupDialog.error();
            System.exit(0);
        }
        return null;
    }

    public static String DateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date LocalDateToDate(LocalDate ld) {
        Instant temp = ld.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return Date.from(temp);
    }

    public static LocalDate DateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String escapeSpecialChar(String str) {
        // \\\\\\ will be interpreted as \\\, then \\
        String escaped = str.replaceAll(
                "[\\<\\(\\[\\{\\\\\\^\\-\\=\\$\\!\\|\\]\\}\\)\\?\\*\\+\\.\\>]",
                "\\\\$0"
        );
        return escaped;
    }
    
}
