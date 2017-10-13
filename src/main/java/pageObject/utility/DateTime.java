package pageObject.utility;



import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.TimeZone;


/**
 * Created by NGorodenchuk on 10/5/2017.
 */
public class DateTime {

    public String timeFormatAmPm = "h:mm a";
    public String dateFormatFormatForDb = "YYYY-MM-dd HH:mm:ss";
    public String timeZone = "America/Los_Angeles";


    public String getTimeInLA(){
        String timeInLA;
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(timeFormatAmPm);
        df.setTimeZone(TimeZone.getTimeZone(timeZone));
        timeInLA = df.format(date).toLowerCase();

        return timeInLA;
    }

    public String generateDateForValidMedical(){
        String timeInLA;
        Date date = DateUtils.addMonths(new Date(), 1);
        DateFormat df = new SimpleDateFormat(dateFormatFormatForDb);
        df.setTimeZone(TimeZone.getTimeZone(timeZone));
        timeInLA = df.format(date).toLowerCase();

        return timeInLA;
    }

    public static void main (String args[]) {
        DateTime dateTime = new DateTime();
        System.out.println(dateTime.generateDateForValidMedical());
        System.out.println(dateTime.getTimeInLA());
    }


}
