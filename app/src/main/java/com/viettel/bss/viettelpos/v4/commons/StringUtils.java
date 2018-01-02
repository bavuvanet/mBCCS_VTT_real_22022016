package com.viettel.bss.viettelpos.v4.commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentChangePromotionBCCS;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.business.ApParamBusiness;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class StringUtils {

    private static final String EMAIL_REG = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static Map<String, String> characterMap = new HashMap<String, String>();


    static {
        characterMap
                .put("[ \\t\\xA0\\u1680\\u180e\\u2000-\\u200a\\u202f\\u205f\\u3000]",
                        " ");
        characterMap.put("\\s+", " ");
        characterMap.put("[\\u01F9\\u0144\\u00F1\\u1E47]", "n");
        characterMap.put("[\\u0300\\u0301\\u0309\\u0303\\u0323\\u001c\\u001d]", "");
    }

    public static String formatMoney(String input) {
        if (CommonActivity.isNullOrEmpty(input)) {
            return "";
        }

        try {
            Double value = Double.parseDouble(input);
            DecimalFormat formatter = new DecimalFormat("#,###,###,##0");
            String result = formatter.format(value);
            return result.replaceAll(",", ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    public static String formatMoneyFromObject(Object input) {
        try {

            Double value = Double.parseDouble(input.toString());
            DecimalFormat formatter = new DecimalFormat("#,###,###,##0");
            String result = formatter.format(value);
            return result.replaceAll(",", ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    public static String formatAbsMoney(String input) {
        try {
            Double value = Double.parseDouble(input);
            if (value < 0) {
                value = -value;
            }
            DecimalFormat formatter = new DecimalFormat("#,###,###,##0");
            String result = formatter.format(value);
            return result.replaceAll(",", ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    public static String unFormatMoney(String input) {
        try {
            Log.d("unFormatMoney", " input " + input);

            input = input.replaceAll("\\.", "");

            Log.d("unFormatMoney", " output " + input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    private static byte[] compress(byte[] bytesToCompress) {
        Deflater deflater = new Deflater();
        deflater.setInput(bytesToCompress);
        deflater.finish();
        byte[] bytesCompressed = new byte[Short.MAX_VALUE];
        int numberOfBytesAfterCompression = deflater.deflate(bytesCompressed);
        byte[] returnValues = new byte[numberOfBytesAfterCompression];
        System.arraycopy(bytesCompressed, 0, returnValues, 0,
                numberOfBytesAfterCompression);
        return returnValues;
    }

    public static byte[] compress(String stringToCompress) {
        byte[] returnValues = null;

        try {

            returnValues = compress(stringToCompress.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return returnValues;
    }

    public static boolean isNumberic(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String standardizeNumber(String number, int length) {
        try {
            while (number.length() < length) {
                number = "0" + number;
            }
        } catch (Exception ignored) {
        }
        return number;
    }

    public static boolean CheckCharSpecical(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        String specialChars = "@*!@#$%^&<>'\"";
        for (int i = 0; i < s.length(); i++) {
            if (specialChars.contains(String.valueOf(s.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public static boolean CheckCharSpecical(String s, String specialChars) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
//        String specialChars = "@*!@#$%^&<>'\"";
        for (int i = 0; i < s.length(); i++) {
            if (specialChars.contains(String.valueOf(s.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public static boolean CheckCharSpecical_1(String s) {
        if (s == null || s.trim().isEmpty()) {
            return false;
        }
        String specialChars = "<>#$%^&*!'\"";
        for (int i = 0; i < s.length(); i++) {
            if (specialChars.contains(String.valueOf(s.charAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public static String checkEscapeText(String t) {
        String ret = null;
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            switch (c) {
                case '<':
                    ret = "<";
                    break;
                case '>':
                    ret = "<";
                    break;
            }
        }
        return ret;
    }

    public static String xmlEscapeText(String t) {
        if (CommonActivity.isNullOrEmpty(t)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        // t = t.replace(">", "");
        // t = t.replace("<", "");
        for (int i = 0; i < t.length(); i++) {
            char c = t.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                default:
                    if (c > 0x7e) {
                        sb.append("&#").append((int) c).append(";");
                    } else
                        sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String getDateFromDateTime(String originalDate) {
        String[] dateToken = null;
        if (originalDate == null || originalDate.length() < 9) {
            return "";
        } else if (originalDate.length() >= 10) {
            dateToken = originalDate.substring(0, 10).split("-");
            return dateToken[2] + "/" + dateToken[1] + "/" + dateToken[0];
        }
        return "";
    }

    public static String convertDate(String originalDate) {

        try {

            if (originalDate.length() >= 10) {

                String[] dateToken = originalDate.substring(0, 10).split("-");

                if (dateToken.length >= 3)

                    return dateToken[2] + "/" + dateToken[1] + "/"
                            + dateToken[0];

            }

        } catch (Exception ignored) {

        }

        return null;

    }

    public static String convertDateToString(String originalDate) {

        String[] dateToken = null;
        try {

            if (originalDate.length() >= 9) {
                dateToken = originalDate.substring(0, 9).split("/");
            }
            if (originalDate.length() >= 10) {
                dateToken = originalDate.substring(0, 10).split("/");
            }

            if (dateToken != null) {
                if (dateToken.length >= 3) {

                    return dateToken[2] + "-" + dateToken[1] + "-"
                            + dateToken[0];
                }
            }

        } catch (Exception ignored) {

        }

        return null;

    }

    public static String convertDateTimeToTimeDate(String input) {
        try {
            String valueArray[] = input.split(" ");
            if (valueArray.length > 1) {
                return valueArray[1] + " " + valueArray[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return input;
    }

    public static int ordinalIndexOf(String str, char c, int n) {
        int pos = str.indexOf(c, 0);
        while (n-- > 0 && pos != -1)
            pos = str.indexOf(c, pos + 1);
        return pos;
    }

    private static String buildRequestId(String wscode) {
        Random r = new Random();
        int i = r.nextInt(900000000) + 100000000;

        return wscode +
                ";" +
                System.currentTimeMillis() +
                ";" +
                i;
    }

    public static String addForKpiLog(String wscode) {
        StringBuilder addForLog = new StringBuilder();
        addForLog.append("<requestId>");
        addForLog.append(buildRequestId(wscode));
        addForLog.append("</requestId>");
        addForLog.append("<userName>");
        try {
            addForLog.append(new SecurityUtil().encrypt(Session.userName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addForLog.append("</userName>");

        return addForLog.toString();
    }

    public static String addForKpiLog(String wscode, boolean genClientKey) {
        StringBuilder addForLog = new StringBuilder();
        addForLog.append("<requestId>");
        String requestId = buildRequestId(wscode);
        addForLog.append(genClientKey ? requestId + "genClientKey" : requestId);
        addForLog.append("</requestId>");
        addForLog.append("<userName>");
        try {
            addForLog.append(new SecurityUtil().encrypt(Session.userName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addForLog.append("</userName>");

        return addForLog.toString();
    }

    private static String getPakageInfo(Context context) {
        String pakage = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context
                    .getApplicationContext().getPackageName(), 0);
            pakage = info.packageName + "_" + info.versionCode + "_"
                    + info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pakage;
    }

    public static String encryptIt(String value, Context context) {
        try {
            String cryptoPass = "mBCCS123456a@";
            if (context != null) {
                cryptoPass += getPakageInfo(context);
            }

            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] clearText = value.getBytes("UTF8");
            // Cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            // Log.d(Constant.TAG, "Encrypted: " + value + " -> " +
            // encrypedValue);
            return Base64.encodeToString(cipher.doFinal(clearText), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String decryptIt(String value, Context context) {
        try {
            String cryptoPass = "mBCCS123456a@";
            if (context != null) {
                cryptoPass += getPakageInfo(context);
            }

            DESKeySpec keySpec = new DESKeySpec(cryptoPass.getBytes("UTF8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey key = keyFactory.generateSecret(keySpec);

            byte[] encrypedPwdBytes = Base64.decode(value, Base64.DEFAULT);
            // cipher is not thread safe
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypedValueBytes = (cipher.doFinal(encrypedPwdBytes));

            // Log.d(Constant.TAG, "Decrypted: " + value + " -> " +
            // decrypedValue);
            return new String(decrypedValueBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }

    @SuppressLint("NewApi")
    public static String escapeHtml(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        input = replaceSpecialCharacter(input);
        return Html.escapeHtml(input);
    }

    public static Boolean validateEmail(String input) {
        return input != null && input.matches(EMAIL_REG);

    }

    public static String formatMsisdn(String mobile) {
        if (CommonActivity.isNullOrEmpty(mobile)) {
            return "";
        }

        if (mobile.startsWith("0")) {
            mobile = "84" + mobile.substring(1);
        } else if (!mobile.startsWith("84")) {
            mobile = "84" + mobile;
        }
        return mobile;
    }

//    public static String unescapeXMLChars(String s) {
//        return s.replaceAll("&amp;",  "&")
//                .replaceAll("&apos;", "'")
//                .replaceAll("&quot;", "\"")
//                .replaceAll("&lt;",   "<")
//                .replaceAll("&gt;",   ">");
//    }
//
//    public static String escapeXMLChars(String s) {
//        return s.replaceAll("&",  "&amp;")
//                .replaceAll("'",  "&apos;")
//                .replaceAll("\"", "&quot;")
//                .replaceAll("<",  "&lt;")
//                .replaceAll(">",  "&gt;");
//    }

    public static String formatIsdn(String mobile) {
        if (CommonActivity.isNullOrEmpty(mobile)) {
            return "";
        }

        if (mobile.startsWith("0")) {
            return mobile.substring(1);
        } else if (mobile.startsWith("84")) {
            return mobile.substring(2);
        } else {
            return mobile;
        }
    }

    public static String replaceSpecialCharacter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        input = Normalizer.normalize(input.trim(), Normalizer.Form.NFC);
        for (Entry<String, String> entry : characterMap.entrySet()) {
            input = input.replaceAll(entry.getKey(), entry.getValue());
        }
        return input;
    }

    public static boolean isViettelMobile(String mobile) {
        mobile = formatMsisdn(mobile);

        String REGEX_VIETTEL_MOBILE = "849[6-8]\\d{7}|8486\\d{7}|8416[2-9]\\d{7}";
        Pattern pattern = Pattern.compile(REGEX_VIETTEL_MOBILE);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isMobileNumber(Context context, String mobile, String nhaMang) {
        String regex = ApParamBusiness.getValue(context, "PREFIX_ISDN", nhaMang);
        if (CommonActivity.isNullOrEmpty(regex)) {
            return false;
        }


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static String removeTonal(String input) {
        String nfdNormalizedString = Normalizer.normalize(input,
                Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    public static String unescapeXML(final String xml) {
        Pattern xmlEntityRegex = Pattern.compile("&(#?)([^;]+);");
        // Unfortunately, Matcher requires a StringBuffer instead of a
        // StringBuilder
        StringBuffer unescapedOutput = new StringBuffer(xml.length());

        Matcher m = xmlEntityRegex.matcher(xml);
        Map<String, String> builtinEntities = null;
        String entity;
        String hashmark;
        String ent;
        int code;
        while (m.find()) {
            ent = m.group(2);
            hashmark = m.group(1);
            if ((hashmark != null) && (hashmark.length() > 0)) {
                code = Integer.parseInt(ent);
                entity = Character.toString((char) code);
            } else {
                // must be a non-numerical entity
                if (builtinEntities == null) {
                    builtinEntities = buildBuiltinXMLEntityMap();
                }
                entity = builtinEntities.get(ent);
                if (entity == null) {
                    // not a known entity - ignore it
                    entity = "&" + ent + ';';
                }
            }
            m.appendReplacement(unescapedOutput, entity);
        }
        m.appendTail(unescapedOutput);

        return unescapedOutput.toString();
    }

    private static Map<String, String> buildBuiltinXMLEntityMap() {
        Map<String, String> entities = new HashMap<String, String>(10);
        entities.put("lt", "<");
        entities.put("gt", ">");
        entities.put("amp", "&");
        entities.put("apos", "'");
        entities.put("quot", "\"");
        return entities;
    }

    /**
     * validateString
     *
     * @param context
     * @param data
     * @param msgEmpty
     * @param msgSpecial
     * @return
     */
    public static boolean validateString(Context context, String data, String msgEmpty, String msgSpecial) {
        if (!CommonActivity.isNullOrEmpty(msgEmpty)) {
            if (CommonActivity.isNullOrEmpty(data)) {
                CommonActivity
                        .createAlertDialog(
                                context,
                                msgEmpty,
                                context.getString(R.string.app_name))
                        .show();
                return false;
            }
        }

        if (!CommonActivity.isNullOrEmpty(msgSpecial)) {
            if (CheckCharSpecical(data)) {
                CommonActivity
                        .createAlertDialog(
                                context,
                                msgSpecial,
                                context.getString(R.string.app_name))
                        .show();
                return false;
            }
        }

        return true;
    }

    /**
     * validateString
     *
     * @param context
     * @param data
     * @param msgEmpty
     * @param msgSpecial
     * @return
     */
    public static boolean validateString(Context context, String data, Integer msgEmpty, Integer msgSpecial) {
        if (msgEmpty != null) {
            if (CommonActivity.isNullOrEmpty(data)) {
                CommonActivity
                        .createAlertDialog(
                                context,
                                context.getString(msgEmpty.intValue()),
                                context.getString(R.string.app_name))
                        .show();
                return false;
            }
        }

        if (msgSpecial != null) {
            if (CheckCharSpecical(data)) {
                CommonActivity
                        .createAlertDialog(
                                context,
                                context.getString(msgSpecial.intValue()),
                                context.getString(R.string.app_name))
                        .show();
                return false;
            }
        }

        return true;
    }

    public static String getTextDefault(EditText edt) {
        String text = edt.getText().toString();
        if (text == null || text.equals("")) {
            return "";
        } else {
            String replaceable = String.format("[%s,.\\s]", NumberFormat
                    .getCurrencyInstance().getCurrency().getSymbol());
            String textDefault = text.replaceAll(replaceable, "");
            textDefault = textDefault.replaceAll("-", "");
            return textDefault;
        }

    }

    public static Boolean isDigit(String strInput) {
        if (CommonActivity.isNullOrEmpty(strInput)) {
            return false;
        }
        String regex = "[0-9]+";
        return strInput.matches(regex);
    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").replaceAll("đ", "d");
        return s;
    }

    public static ArrayList<String> initNgayHieuLuc() {
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> lstDate = new ArrayList<>();
        lstDate.add(FragmentChangePromotionBCCS.REQUIRE_DATE_AFFECT);
        lstDate.add(FragmentChangePromotionBCCS.TODAY);
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        while (calendar.get(Calendar.MONTH) < 11) {
            calendar.add(Calendar.MONTH, 1);
            lstDate.add(sdfDate.format(calendar.getTime()));
        }

        return lstDate;
    }

    private byte[] decompress(byte[] bytesToDecompress) {
        byte[] returnValues = null;
        Inflater inflater = new Inflater();

        int numberOfBytesToDecompress = bytesToDecompress.length;

        inflater.setInput(bytesToDecompress, 0, numberOfBytesToDecompress);

        List<Byte> bytesDecompressedSoFar = new ArrayList<>();

        try {
            while (!inflater.needsInput()) {
                byte[] bytesDecompressedBuffer = new byte[numberOfBytesToDecompress];

                int numberOfBytesDecompressedThisTime = inflater
                        .inflate(bytesDecompressedBuffer);

                for (int b = 0; b < numberOfBytesDecompressedThisTime; b++) {
                    bytesDecompressedSoFar.add(bytesDecompressedBuffer[b]);
                }
            }

            returnValues = new byte[bytesDecompressedSoFar.size()];
            for (int b = 0; b < returnValues.length; b++) {
                returnValues[b] = (bytesDecompressedSoFar.get(b));
            }

        } catch (DataFormatException dfe) {
            dfe.printStackTrace();
        }

        inflater.end();

        return returnValues;
    }

    public String decompressToString(byte[] bytesToDecompress) {
        byte[] bytesDecompressed = this.decompress(bytesToDecompress);

        String returnValue = null;

        try {
            returnValue = new String(bytesDecompressed, 0,
                    bytesDecompressed.length, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        return returnValue;
    }

    public static String getActionByOrderType(String orderType) {
        String action = "";
        switch (orderType) {
            case Constant.ORD_TYPE_CHANGE_PREPAID_FEE : action ="537" ; break;
            case Constant.ORD_TYPE_CHANGE_TO_POSPAID : action = "220"; break;
            case Constant.ORD_TYPE_CONNECT_POSPAID : action = "00"; break;
            case Constant.ORD_TYPE_CONNECT_PREPAID : action = "00"; break;
            case Constant.CONNECT_FIX_LINE : action = "00"; break;
            case Constant.ORD_TYPE_REGISTER_PREPAID : action = "04"; break;
            case Constant.CHANGE_TO_PREPAID : action = "221"; break;
            case Constant.CHANGE_SIM : action = "11"; break;
        }
        return action;
    }
}
