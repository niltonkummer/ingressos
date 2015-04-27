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
    public static boolean stringToHour(String hour, Date retorno){
       if(hour == null){
			return false;
		}
        
        return(isValidDate(hour,"HH:mm", retorno));
    }
    
    public static String dateToString(String format, Date data){
        return(new SimpleDateFormat(format).format(data));
    }
    
    public static boolean stringToDate(String data, Date retorno) 
    {
        if(data == null){
			return false;
		}
        
        return(isValidDate(data,"dd/MM/yyyy", retorno));
    }
    
    private static boolean isValidDate(String dateParam, String dateFormat, Date date){
        SimpleDateFormat simpDateFormat = new SimpleDateFormat(dateFormat);
		simpDateFormat.setLenient(false);
 
		try {
 
			date.setTime(simpDateFormat.parse(dateParam).getTime());
			
		} catch (ParseException e) {
            e.printStackTrace();
            return false;
		}
 
		return true;
	}
}
