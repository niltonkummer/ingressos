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
    /**
     * 
     * @param hour
     * @param retorno
     * @return se a data foi formatada para o objeto passado como referência
     */
    public static boolean stringToHour(String hour, Date retorno){
       if(hour == null){
			return false;
		}
        
        return(isValidDate(hour,"HH:mm", retorno));
    }
    /**
     * 
     * @param format
     * @param data
     * @return formata uma data texto para um objeto Date
     */
    public static String dateToString(String format, Date data){
        return(new SimpleDateFormat(format).format(data));
    }
    
    /**
     * 
     * @param data
     * @param retorno
     * @return se o texto é uma data válida configura a referência
     */
    public static boolean stringToDate(String data, Date retorno) 
    {
        return(isValidDate(data,"dd/MM/yyyy", retorno));
    }
    
    /**
     * 
     * @param dateParam
     * @param dateFormat
     * @param date
     * @return 
     */
    public static boolean isValidDate(String dateParam, String dateFormat, Date date){
        SimpleDateFormat simpDateFormat = new SimpleDateFormat(dateFormat);
		simpDateFormat.setLenient(false);
        if(dateParam == null){
			return false;
		}
        
		try {
 
			date.setTime(simpDateFormat.parse(dateParam).getTime());
			
		} catch (ParseException e) {
            e.printStackTrace();
            return false;
		}
 
		return true;
	}
}
