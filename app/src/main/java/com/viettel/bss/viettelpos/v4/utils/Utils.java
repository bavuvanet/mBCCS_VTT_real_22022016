package com.viettel.bss.viettelpos.v4.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Toancx on 2/13/2017.
 */

public class Utils {

    /**
     *
     * @param date
     * @return
     */
    public static String convertDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

        return sdf.format(date);
    }



    public static String formatStringDateToOtherFormat(String dateInString){
        String dateout = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date date = sdf.parse(dateInString);
            dateout = sdf1.format(date);

        } catch (ParseException e){

            e.printStackTrace();
        }

        return dateout;

    }

    public static boolean validateAgeAbove18(String inputDateString){
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(inputDateString);
            Calendar calendar = GregorianCalendar.getInstance();
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);
            if (calendar.getTime().after(date)){
                return false;
            } else {
                return true;
            }
        } catch (ParseException e) {
            return false;
        }
    }


    public static String revertDate(String inputDateString){
        String dateReturn =inputDateString;
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(inputDateString);
            Date date2 = new Date(date1.getTime());
             dateReturn = new SimpleDateFormat("dd/MM/yyyy").format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    return dateReturn;


}
}