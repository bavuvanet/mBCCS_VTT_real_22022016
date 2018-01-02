package com.viettel.bss.viettelpos.v4.commons;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.viettel.bss.viettelpos.v4.R;

public class DateTimeUtils {

    public static String convertDateTimeToString(Date date, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        try {
            if (date == null) {
                return null;
            }
            return dateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String convertDateTimeToString(Date date) {
        return convertDateTimeToString(date, "dd/MM/yyyy HH:mm:ss");
    }

    public static int getMonthFromString(String value) {
        if (CommonActivity.isNullOrEmpty(value)) {
            return 0;
        }
        return Integer.parseInt(value.substring(value.length() - 2));
    }

    public static String getDateFromSerialString(String value) {
        if(CommonActivity.isNullOrEmpty(value)){
            return "";
        }
        String year = value.substring(0, 4);
        String month = value.substring(4, 6);
        String day = value.substring(6, 8);

        return day + "/" + month + "/" + year;
    }

    public static String getDateFromFullString(String value) {
        try {
            if(CommonActivity.isNullOrEmpty(value)){
                return "";
            }

            value = value.substring(0, 10);
            return DateTimeUtils.convertDateTimeToString(
                    convertStringToTime(value, "yyyy-MM-dd"), "dd/MM/yyyy");
        } catch (Exception e) {
            Log.e("exception", "exception", e);
            return "";
        }
    }

    /**
     * Tra ve thoi gian trong ngay theo dinh dang yyyyMMdd HHmmss gio, phu, giay
     * bang 0
     *
     * @return
     */
    public static String getSysDate() {
        Calendar calStartDate = Calendar.getInstance();
        calStartDate.set(Calendar.HOUR_OF_DAY, 0);
        calStartDate.set(Calendar.MINUTE, 0);
        calStartDate.set(Calendar.SECOND, 0);
        return DateTimeUtils.convertDateTimeToString(calStartDate.getTime(),
                "yyyyMMdd HHmmss");
    }

    /**
     * Tra ve thoi gian ngay + 1 theo dinh dang yyyyMMdd HHmmss gio, phu, giay
     * bang 0
     *
     * @return
     */
    public static String getSysDateAddOne() {
        Calendar calStartDate = Calendar.getInstance();
        calStartDate.add(Calendar.DAY_OF_MONTH, 1);
        calStartDate.set(Calendar.HOUR_OF_DAY, 0);
        calStartDate.set(Calendar.MINUTE, 0);
        calStartDate.set(Calendar.SECOND, 0);
        return DateTimeUtils.convertDateTimeToString(calStartDate.getTime(),
                "yyyyMMdd HHmmss");
    }

    public static Date convertStringToTime(String date, String pattern) {
        try {
            if (date == null || pattern == null) {
                return null;
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(date);
        } catch (Exception ignored) {
            Log.e("convertStringToTime", ignored.getMessage());
        }
        return null;
    }

    public static Date convertStringToTime(String date) {
        return convertStringToTime(date, "dd/MM/yyyy");
    }

    /**
     * @param originalDate
     * @return
     */
    public static String convertDate(String originalDate) {
        if (CommonActivity.isNullOrEmpty(originalDate)) {
            return "";
        }
        try {
            if (originalDate.length() >= 10) {
                String[] dateToken = originalDate.substring(0, 10).split("-");
                if (dateToken.length >= 3)
                    return dateToken[2] + "/" + dateToken[1] + "/"
                            + dateToken[0];
            }
        } catch (Exception ignored) {

        }

        return "";
    }

    public static String formatDateInsert(String originalDate) {
        try {
            if (originalDate.length() >= 10) {
                String[] dateToken = originalDate.substring(0, 10).split("/");
                if (dateToken.length >= 3)
                    return dateToken[2] + "-" + dateToken[1] + "-"
                            + dateToken[0];
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    /**
     * Tinh tuoi da qua quy dinh hay chua
     *
     * @return
     */
    public static Boolean compareAge(Date birthDay, int age) {
        try {
            if (birthDay == null) {
                return false;
            }
            Calendar currentCal = Calendar.getInstance();

            Calendar birthCal = Calendar.getInstance();

            birthCal.setTime(birthDay);
            birthCal.add(Calendar.YEAR, age);
            return birthCal.before(currentCal);

        } catch (Exception e) {
            return false;
        }
    }

    public static String getFirstDateOfMonth() {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            return DateTimeUtils.convertDateTimeToString(cal.getTime(),
                    "dd/MM/yyyy");
        } catch (Exception ex) {
            return "";
        }
    }

    public static String getFirstDateOfMonth(String partern) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.DAY_OF_MONTH, 1);
            return DateTimeUtils.convertDateTimeToString(cal.getTime(), partern);
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * convertDateSoap
     *
     * @param date
     * @return
     */
    public static String convertDateSoap(String date) {
        try {
            date = date.substring(0, 10);
            return DateTimeUtils.convertDateTimeToString(
                    convertStringToTime(date, "yyyy-MM-dd"), "dd-MM-yyyy");
        } catch (Exception e) {
            Log.e("exception", "exception", e);
            return "";
        }
    }

    public static String convertDateSoapToString(String value){
        if(CommonActivity.isNullOrEmpty(value)){
            return "";
        }

        if(value.contains("+")){
            value = value.substring(0, value.lastIndexOf("+"));
            Date date = convertStringToTime(value, "yyyy-MM-dd'T'hh:mm:ss");
            return convertDateTimeToString(date, "dd/MM/yyy HH:mm:ss");
        } else {
            return convertDateSoap(value);
        }
    }
    public static String convertDateSoapToNewFormat(String value,String newFormart){
        if(CommonActivity.isNullOrEmpty(value)){
            return "";
        }

        if(value.contains("+")){
            value = value.substring(0, value.lastIndexOf("+"));
        }

        Date date = convertStringToTime(value, "yyyy-MM-dd'T'hh:mm:ss");
        return convertDateTimeToString(date, newFormart);
    }

    public static String convertDateSoapToFormat(String value,String format){
        if(CommonActivity.isNullOrEmpty(value)){
            return "";
        }

        if(value.contains("+")){
            value = value.substring(0, value.lastIndexOf("+"));
            Date date = convertStringToTime(value, "yyyy-MM-dd'T'hh:mm:ss");
            return convertDateTimeToString(date, format);
        } else {
            return convertDateSoap(value);
        }
    }

    public static String convertDateSendOverSoap(Date date) {
        try {
            return convertDateTimeToString(date, "yyyy-MM-dd'T'hh:mm:ss");

        } catch (Exception e) {
            Log.e("exception", "exception", e);
            return "";
        }

    }

    public static int calculateDays2Date(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);

        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);

        cal2.set(Calendar.HOUR_OF_DAY, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        Long days = (cal1.getTimeInMillis() - cal2.getTimeInMillis())
                / (1000 * 60 * 60 * 24);
        int result = days.intValue();
		if(result <0){
            return -result;
        }
        return result;
    }

    public static Date addDays(Date date, int day) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        return cal.getTime();
    }

    public static Date addMonths(Date date, int month) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();
    }

    public static Date convertDateFromSoap(String date) {
        try {
            return convertStringToTime(date, "yyyy-MM-dd'T'HH:mm:ss");
        } catch (Exception e) {
            Log.e("Error Date Soap", e.toString(), e);

            return null;
        }
    }

    public static boolean validateData(Context mContext, EditText edtFromDate, EditText edtToDate, Integer DURATION_MAX) {
        java.util.Calendar calFromDate = java.util.Calendar.getInstance();
        calFromDate.setTime(DateTimeUtils.convertStringToTime(
                edtFromDate.getText().toString(), "dd/MM/yyyy"));

        java.util.Calendar calToDate = java.util.Calendar.getInstance();
        calToDate.setTime(DateTimeUtils.convertStringToTime(edtToDate
                .getText().toString(), "dd/MM/yyyy"));

        if(calToDate.after(new Date())){
            CommonActivity.createAlertDialog(mContext,
                    mContext.getString(R.string.txt_todate_after_currentdate),
                    mContext.getString(R.string.app_name)).show();
            return false;
        }

        if (calFromDate.getTime().after(calToDate.getTime())) {
            CommonActivity.createAlertDialog(mContext,
                    mContext.getString(R.string.checktimeupdatejob),
                    mContext.getString(R.string.app_name)).show();
            return false;
        }

        if(DURATION_MAX != null) {
            if (DateTimeUtils.calculateDays2Date(calFromDate.getTime(),
                    calToDate.getTime()) > DURATION_MAX.intValue()) {
                CommonActivity.createAlertDialog(mContext,
                        mContext.getString(R.string.duration_over_load, DURATION_MAX),
                        mContext.getString(R.string.app_name)).show();
                return false;
            }
        }

        return true;
    }

    public static String getCurrMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return month + "/" + year;
    }

    public static Date getFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getCurrDate() {
        Calendar calStartDate = Calendar.getInstance();
        calStartDate.set(Calendar.HOUR_OF_DAY, 0);
        calStartDate.set(Calendar.MINUTE, 0);
        calStartDate.set(Calendar.SECOND, 0);
        return calStartDate.getTime();
    }
	public static Date addDay(Date nowDate, int period) {
		if (nowDate == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		calendar.add(Calendar.DAY_OF_MONTH, period);
		return calendar.getTime();
	}

	public static Date truncDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
				cal.get(Calendar.DATE), 0, 0, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

    public static int daysBetween(Date fromDate, Date toDate) {
        return (int) ((getHourZero(toDate.getTime()) - getHourZero(fromDate.getTime())) / (1000 * 60 * 60 * 24));
    }



    private static long getHourZero(long timeStamp) {
        return timeStamp - ((timeStamp + 25200000) % 86400000);
    }


    public static int yearsBetween(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static int monthsBetween(Date s1, Date s2) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(s1);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(s2);
        int diff = (d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR)) * 12 + d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
        return diff;
    }
}
