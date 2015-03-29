/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author niltonkummer
 */
public class DateUtil {
    public static Date stringToHour(String hour) throws ParseException {
        return(new SimpleDateFormat("HH:mm").parse(hour));
    }
    
    public static String dateToString(String format, Date data){
        return(new SimpleDateFormat(format).format(data));
    }
    
    public static Date stringToDate(String data) throws ParseException
    {
        return(new SimpleDateFormat("dd/MM/yyyy").parse(data));
    }
}
