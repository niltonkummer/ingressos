package utils;
 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class DateValidator {
 
	public boolean isValidDate(String dateParam, String dateFormat){
 
		if(dateParam == null){
			return false;
		}
 
		SimpleDateFormat simpDateFormat = new SimpleDateFormat(dateFormat);
		simpDateFormat.setLenient(false);
 
		try {
 
			Date date = simpDateFormat.parse(dateParam);
			
		} catch (ParseException e) {
            e.printStackTrace();
            return false;
		}
 
		return true;
	}
}