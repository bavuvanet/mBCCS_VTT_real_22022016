package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.viettel.bccs2.viettelpos.v2.rsa.Crypto;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.savelog.SaveLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class BCCSGateway {

    private static final String TAG = "BCCSGateway";
    private List<Optional> listParams;
    private List<Optional> listValidateGateways;

    public void addValidateGateway(String field, String value) {
        if (listValidateGateways == null)
            listValidateGateways = new ArrayList<>();
        listValidateGateways.add(new Optional(field, value));
    }

    public void addParam(String field, String value) {
        if (listParams == null)
            listParams = new ArrayList<>();
        listParams.add(new Optional(field, value));
    }

    public String buildInputGateway() {
        String envelope = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\">"
                + "<soapenv:Header/>"
                + "<soapenv:Body>"
                + "<web:gwOperation>"
                + "<Input>" + "<!--Validate BCCSGateway:-->";
        if (listValidateGateways != null) {
            for (Optional option : listValidateGateways) {
                envelope += "<" + option.getField() + ">" + option.getValue()
                        + "</" + option.getField() + ">";
            }
        }
        envelope += "<!--Zero or more repetitions:-->";
        if (listParams != null) {
            for (Optional option : listParams) {
                envelope += "<param name=\"" + option.getField()
                        + "\" value=\"" + option.getValue() + "\"/>";
            }
        }
        envelope += "</Input>" + "</web:gwOperation>" + "</soapenv:Body>"
                + "</soapenv:Envelope>";
        return envelope;
    }

    public String crateXml(
            ArrayList<com.viettel.bss.viettelpos.v4.login.object.Optional> arrayList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (com.viettel.bss.viettelpos.v4.login.object.Optional mOptional : arrayList) {
            stringBuilder.append("<").append(mOptional.getField()).append(">").append(mOptional.getValue()).append("</").append(mOptional.getField()).append(">");
        }
        return stringBuilder.toString();
    }

    public String buildInputGatewayWithRawData2(String rawDataContent,
                                                String nameWSCode) {
        int ordinal = 1;

        String addForKpiLog = StringUtils.addForKpiLog(nameWSCode);
        StringBuilder rawData = new StringBuilder(rawDataContent);
        int offset = StringUtils.ordinalIndexOf(rawData.toString(), '>',
                ordinal);
        rawData.insert(offset + 1, addForKpiLog);
        Log.d(this.getClass().getSimpleName(), rawData.toString());

        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\" >" + "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<web:gwOperation>" +
                "<Input>" + "<!--Validate BCCSGateway:-->" +
                "<wscode>" + nameWSCode + "</wscode>" +
                "<username>" + Constant.BCCSGW_USER + "</username>" +
                "<password>" + Constant.BCCSGW_PASS + "</password>" +
                "<!--Zero or more repetitions:-->" +
                "<rawData><![CDATA[" +
                rawData.toString() +
                "]]></rawData>" +
                "</Input>" + "</web:gwOperation>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    public String getXmlLockBox(String rawDataContent, String nameWSCode) {

        // envelope.append("<surveyOnlineForm>");
        // envelope.append("</surveyOnlineForm>");
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\" >" + "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<web:gwOperation>" +
                "<Input>" + "<!--Validate BCCSGateway:-->" +
                "<wscode>" + nameWSCode + "</wscode>" +
                "<username>" + Constant.BCCSGW_USER + "</username>" +
                "<password>" + Constant.BCCSGW_PASS + "</password>" +
                "<!--Zero or more repetitions:-->" +
                "<rawData><![CDATA[" +
                rawDataContent +
                "]]></rawData>" +
                "</Input>" + "</web:gwOperation>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    public String getXmlCustomer(String rawDataContent, String nameWSCode) {

        // envelope.append("<surveyOnlineForm>");
        // envelope.append("</surveyOnlineForm>");
        return "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\" >" + "<soapenv:Header/>" +
                "<soapenv:Body>" +
                "<web:gwOperation>" +
                "<Input>" + "<!--Validate BCCSGateway:-->" +
                "<wscode>" + nameWSCode + "</wscode>" +
                "<username>" + Constant.BCCSGW_USER + "</username>" +
                "<password>" + Constant.BCCSGW_PASS + "</password>" +
                "<!--Zero or more repetitions:-->" +
                "<rawData><![CDATA[" +
                rawDataContent +
                "]]></rawData>" +
                "</Input>" + "</web:gwOperation>" +
                "</soapenv:Body>" +
                "</soapenv:Envelope>";
    }

    private String buildInputGatewayWithRawData(String rawDataContent,
                                                String nameWSCode) {
        StringBuilder envelope = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\" xmlns:ws=\"http://ws.smartphonev2.bss.viettel.com/\">");
        envelope.append("<soapenv:Header/>");
        envelope.append("<soapenv:Body>");
        envelope.append("<web:gwOperation>");
        envelope.append("<Input>").append("<!--Validate BCCSGateway:-->");
        if (listValidateGateways != null) {
            for (Optional option : listValidateGateways) {
                envelope.append("<").append(option.getField()).append(">")
                        .append(option.getValue());
                envelope.append("</").append(option.getField()).append(">");
            }
        }
        envelope.append("<wscode>").append(nameWSCode).append("</wscode>");

        envelope.append("<username>" + Constant.BCCSGW_USER + "</username>");
        envelope.append("<password>" + Constant.BCCSGW_PASS + "</password>");

        envelope.append("<!--Zero or more repetitions:-->");
        envelope.append("<rawData><![CDATA[");
        envelope.append("<surveyOnlineForm>");
        envelope.append(rawDataContent);
        envelope.append("</surveyOnlineForm>");
        envelope.append("]]></rawData>");
        envelope.append("</Input>").append("</web:gwOperation>")
                .append("</soapenv:Body>");
        envelope.append("</soapenv:Envelope>");
        return envelope.toString();
    }

    public String buildInputGatewayWithRawData(String rawDataContent,
                                               int ordinal) {
        String wscode = "";
        for (Optional optional : listValidateGateways) {
            if ("wscode".equalsIgnoreCase(optional.getField())) {
                wscode = optional.getValue();
                break;
            }
        }
        String addForKpiLog = StringUtils.addForKpiLog(wscode);
        StringBuilder rawData = new StringBuilder(rawDataContent);
        int offset = StringUtils.ordinalIndexOf(rawData.toString(), '>',
                ordinal);
        rawData.insert(offset + 1, addForKpiLog);
        Log.d(this.getClass().getSimpleName(), rawData.toString());

        StringBuilder envelope = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\">");
        envelope.append("<soapenv:Header/>");
        envelope.append("<soapenv:Body>");
        envelope.append("<web:gwOperation>");
        envelope.append("<Input>").append("<!--Validate BCCSGateway:-->");
        if (listValidateGateways != null) {
            for (Optional option : listValidateGateways) {
                envelope.append("<").append(option.getField()).append(">")
                        .append(option.getValue());
                envelope.append("</").append(option.getField()).append(">");
            }
        }
        envelope.append("<!--Zero or more repetitions:-->");
        envelope.append("<rawData><![CDATA[");
        envelope.append(rawData.toString());
        envelope.append("]]></rawData>");
        envelope.append("</Input>").append("</web:gwOperation>")
                .append("</soapenv:Body>");
        envelope.append("</soapenv:Envelope>");
        return envelope.toString();
    }

    public String buildInputGatewayWithRawData(String rawDataContent,
                                               int ordinal, boolean genClientKey) {
        String wscode = "";
        for (Optional optional : listValidateGateways) {
            if ("wscode".equalsIgnoreCase(optional.getField())) {
                wscode = optional.getValue();
                break;
            }
        }
        String addForKpiLog = StringUtils.addForKpiLog(wscode, genClientKey);
        StringBuilder rawData = new StringBuilder(rawDataContent);
        int offset = StringUtils.ordinalIndexOf(rawData.toString(), '>',
                ordinal);
        rawData.insert(offset + 1, addForKpiLog);
        Log.d(this.getClass().getSimpleName(), rawData.toString());
        StringBuilder envelope = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\">");
        envelope.append("<soapenv:Header/>");
        envelope.append("<soapenv:Body>");
        envelope.append("<web:gwOperation>");
        envelope.append("<Input>").append("<!--Validate BCCSGateway:-->");
        if (listValidateGateways != null) {
            for (Optional option : listValidateGateways) {
                envelope.append("<").append(option.getField()).append(">")
                        .append(option.getValue());
                envelope.append("</").append(option.getField()).append(">");
            }
        }
        envelope.append("<!--Zero or more repetitions:-->");
        envelope.append("<rawData><![CDATA[");
        envelope.append(rawData.toString());
        envelope.append("]]></rawData>");
        envelope.append("</Input>").append("</web:gwOperation>")
                .append("</soapenv:Body>");
        envelope.append("</soapenv:Envelope>");
        return envelope.toString();
    }

    public String buildInputGatewayWithRawData(String rawDataContent) {
        int ordinal = 1;

        String wscode = "";
        for (Optional optional : listValidateGateways) {
            if ("wscode".equalsIgnoreCase(optional.getField())) {
                wscode = optional.getValue();
                break;
            }
        }
        String addForKpiLog = StringUtils.addForKpiLog(wscode);
        StringBuilder rawData = new StringBuilder(rawDataContent);
        int offset = StringUtils.ordinalIndexOf(rawData.toString(), '>',
                ordinal);
        rawData.insert(offset + 1, addForKpiLog);
        Log.d(this.getClass().getSimpleName(), rawData.toString());

        StringBuilder envelope = new StringBuilder(
                "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.bccsgw.viettel.com/\">");
        envelope.append("<soapenv:Header/>");
        envelope.append("<soapenv:Body>");
        envelope.append("<web:gwOperation>");
        envelope.append("<Input>").append("<!--Validate BCCSGateway:-->");
        if (listValidateGateways != null) {
            for (Optional option : listValidateGateways) {
                envelope.append("<").append(option.getField()).append(">")
                        .append(option.getValue());
                envelope.append("</").append(option.getField()).append(">");
            }
        }
        envelope.append("<!--Zero or more repetitions:-->");
        envelope.append("<rawData><![CDATA[");
        envelope.append(rawData.toString());
        envelope.append("]]></rawData>");
        envelope.append("</Input>").append("</web:gwOperation>")
                .append("</soapenv:Body>");
        envelope.append("</soapenv:Envelope>");
        return envelope.toString();
    }

    /**
     * HuyPQ15: Ham gui request len server
     *
     * @param envelope
     * @param url
     * @return
     * @throws Exception
     */

    public String sendRequest(String envelope, String url, Context context,
                              String wsCode) throws Exception {
        return sendRequest(envelope, url, context, wsCode,
                Constant.TIME_OUT_CONECT, Constant.TIME_OUT_RESPONE);
    }

    public String sendRequest(String envelope, String url, Context context,
                              String wsCode, int connectTimeOut, int responseTimeout) {

        envelope = envelope.replaceAll("&nbsp;", "&#160;");

        try {
            StringBuilder tmp = new StringBuilder(envelope);
            int wsIndex = tmp.indexOf("<ws:");

            int firstLessIndex = tmp.indexOf(">", wsIndex);
            String requestSecret = "<mbccsRequestCode>"
                    + Constant.SECEREC_REQUEST + "</mbccsRequestCode>";
            tmp.insert(firstLessIndex + 1, requestSecret);
            envelope = tmp.toString();
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }
        String responseString = "";
        Date timeStart = null;
        Date timeEnd = null;
        timeStart = new Date();
        String errCode = "";
        String userName = "";
        String requestId = "";
        SharedPreferences preferences = context.getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        if (preferences != null) {
            userName = preferences.getString(Define.KEY_LOGIN_NAME, "");
        }

        try {
            responseString = OkHttpUtils.callViaHttpClient(url, envelope);
            try {


                int begin = envelope.indexOf("<requestId>");
                int end = envelope.indexOf("</requestId>");

                requestId = userName + "_"
                        + CommonActivity.getDeviceId(context) + "_"
                        + System.currentTimeMillis();

                if (begin >= 0 && end >= 0 && end - begin > 11) {
                    requestId = envelope.substring(begin + 11, end);
                }
                if (requestId != null && requestId.length() >= 60) {
                    requestId = requestId.substring(0, 57);
                }

                // Goi cac ham do kiem
                if (responseString.equals("200")) {
                    errCode = "200";
                } else {
//						CommonOutput output = parseGWResponse(responseString);
                    Log.d(TAG, "Begin parseGWOutputResponse");
                    Date startTime = new Date();
                    output = parseGWOutputResponseWithDom(responseString);
                    Log.d(TAG, "End parseGWOutputResponse");
                    errCode = output.getError();

                    long duration = (System.currentTimeMillis() - startTime.getTime()) / 1000;
                    if (duration > 10) {
                        if (Session.KPI_REQUEST) {
                            SaveLog saveLog = new SaveLog(context,
                                    Constant.SYSTEM_NAME, userName, wsCode,
                                    CommonActivity.findMyLocation(context).getX(),
                                    CommonActivity.findMyLocation(context).getY());
                            timeEnd = new Date();
                            saveLog.saveLogRequest("TRANS_SLOW", startTime, new Date(),
                                    requestId);
                        }
                    }
                }

                if (Session.KPI_REQUEST) {
                    SaveLog saveLog = new SaveLog(context,
                            Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(errCode, timeStart, timeEnd,
                            requestId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            try {
                if (Session.KPI_REQUEST) {
                    // Gọi các hàm đo kiểm KPI Request
                    if (requestId == null || requestId.isEmpty()) {
                        requestId = Session.userName + "_"
                                + CommonActivity.getDeviceId(context) + "_"
                                + System.currentTimeMillis();
                    }
                    SaveLog saveLog = new SaveLog(context,
                            Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(e + "", timeStart, timeEnd,
                            requestId);
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }

            throw e;
        }

        return responseString;
    }

    public String sendRequestConnectSub(String envelope, String url, Context context,
                                        String wsCode) {

        envelope = envelope.replaceAll("&nbsp;", "&#160;");

        try {
            StringBuilder tmp = new StringBuilder(envelope);
            int wsIndex = tmp.indexOf("<ws:");

            int firstLessIndex = tmp.indexOf(">", wsIndex);
            String requestSecret = "<mbccsRequestCode>"
                    + Constant.SECEREC_REQUEST + "</mbccsRequestCode>";
            tmp.insert(firstLessIndex + 1, requestSecret);
            envelope = tmp.toString();
        } catch (Exception e) {
            Log.e("Exception", "Exception", e);
        }
        String responseString = "";
        Date timeStart = null;
        Date timeEnd = null;
        timeStart = new Date();
        String errCode = "";
        String userName = "";
        String requestId = "";
        SharedPreferences preferences = context.getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        if (preferences != null) {
            userName = preferences.getString(Define.KEY_LOGIN_NAME, "");
        }

        try {
            responseString = OkHttpUtilsConnectSub.callViaHttpClient(url, envelope);
            try {


                int begin = envelope.indexOf("<requestId>");
                int end = envelope.indexOf("</requestId>");

                requestId = userName + "_"
                        + CommonActivity.getDeviceId(context) + "_"
                        + System.currentTimeMillis();

                if (begin >= 0 && end >= 0 && end - begin > 11) {
                    requestId = envelope.substring(begin + 11, end);
                }
                if (requestId != null && requestId.length() >= 60) {
                    requestId = requestId.substring(0, 57);
                }

                // Goi cac ham do kiem
                if (responseString.equals("200")) {
                    errCode = "200";
                } else {
//						CommonOutput output = parseGWResponse(responseString);
                    Log.d(TAG, "Begin parseGWOutputResponse");
                    Date startTime = new Date();
                    output = parseGWOutputResponseWithDom(responseString);
                    Log.d(TAG, "End parseGWOutputResponse");
                    errCode = output.getError();

                    long duration = (System.currentTimeMillis() - startTime.getTime()) / 1000;
                    if (duration > 10) {
                        if (Session.KPI_REQUEST) {
                            SaveLog saveLog = new SaveLog(context,
                                    Constant.SYSTEM_NAME, userName, wsCode,
                                    CommonActivity.findMyLocation(context).getX(),
                                    CommonActivity.findMyLocation(context).getY());
                            timeEnd = new Date();
                            saveLog.saveLogRequest("TRANS_SLOW", startTime, new Date(),
                                    requestId);
                        }
                    }
                }

                if (Session.KPI_REQUEST) {
                    SaveLog saveLog = new SaveLog(context,
                            Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(errCode, timeStart, timeEnd,
                            requestId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            try {
                if (Session.KPI_REQUEST) {
                    // Gọi các hàm đo kiểm KPI Request
                    if (requestId == null || requestId.isEmpty()) {
                        requestId = Session.userName + "_"
                                + CommonActivity.getDeviceId(context) + "_"
                                + System.currentTimeMillis();
                    }
                    SaveLog saveLog = new SaveLog(context,
                            Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(e + "", timeStart, timeEnd,
                            requestId);
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }

            throw e;
        }

        return responseString;
    }

    private CommonOutput output = new CommonOutput();

    public CommonOutput parseGWResponse(String response) {
//		CommonOutput output;
//		SAXParserFactory spf = SAXParserFactory.newInstance();
//		SAXParser sp = spf.newSAXParser();
//		XMLReader xr = sp.getXMLReader();
//		CommonOutputHandler handler = new CommonOutputHandler();
//		xr.setContentHandler(handler);
//		InputSource inStream = new InputSource();
//		inStream.setCharacterStream(new StringReader(response));
//		// parse xml
//		xr.parse(inStream);
//		output = handler.getItem();
        return output;
    }


    private CommonOutput parseGWOutputResponse(String response) throws Exception {
//        String original = response.replaceAll("(.*?<original>)(.*)(</original>.*)", "$2");
//        if (!original.startsWith("<![CDATA[")) {
//            original = StringUtils.unescapeXML(original);
//        }
//
//        if (!CommonActivity.isNullOrEmpty(original)) {
//            original = original.replaceAll("(.*?)(<return>.*</return>)(.*)", "$2");
//            output.setOriginal(original);
//            String error = response.toString().replaceAll("(.*?<error>)(.*?)(</error>.*)", "$2");
//            output.setError(error);
//            if (!"0".equals(error)) {
//                String description = response.toString().replaceAll("(.*?<description>)(.*?)(</description>.*)", "$2");
//                output.setDescription(description);
//            }
//        } else {
//        Log.d(TAG,"starttime parse request:=========> " + System.currentTimeMillis());
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SAXParser sp = spf.newSAXParser();
        XMLReader xr = sp.getXMLReader();
        CommonOutputHandler handler = new CommonOutputHandler();
        xr.setContentHandler(handler);
        InputSource inStream = new InputSource();
        inStream.setCharacterStream(new StringReader(response));
        // parse xml
        xr.parse(inStream);
        output = handler.getItem();
//        Log.d(TAG,"entime parse request:=========> " + System.currentTimeMillis());
//        }
        return output;
    }

    private class Optional {
        private final String field;
        private final String value;

        public Optional(String field, String value) {
            // TODO Auto-generated constructor stub
            this.field = field;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public String getField() {
            return field;
        }

    }

    /**
     * HuyPQ15: Build ban tin trong RawData
     *
     * @param methodName
     * @param param
     * @return
     * @throws Exception
     */
    public String buildRawDataRequest(String methodName,
                                      HashMap<String, String> param) {
        StringBuilder result = new StringBuilder();
        result.append("<").append(methodName).append(">");
        Set<String> keySet = param.keySet();
        if (keySet != null && !keySet.isEmpty()) {
            for (String key : keySet) {
                result.append("<").append(key).append(">");
                result.append(param.get(key));
                result.append("</").append(key).append(">");
            }
        }
        result.append("</").append(methodName).append(">");
        return result.toString();
    }

    public String buildXML(HashMap<String, String> param) {
        StringBuilder result = new StringBuilder();

        Set<String> keySet = param.keySet();
        if (keySet != null && !keySet.isEmpty()) {
            for (String key : keySet) {
                String value = param.get(key);
                if (value != null && !value.trim().isEmpty()) {
                    result.append("<").append(key).append(">");
                    result.append(value);
                    result.append("</").append(key).append(">");
                } else {
                    result.append("<").append(key).append("/>");
                }

            }
        }
        return result.toString();
    }

    public String buildBankPlusXml(Context context, String token, String data)
            throws Exception {
        StringBuilder result = new StringBuilder();
        Log.d("buildBankPlusXml", data);
        result.append("<token>")
                .append(Crypto.Encrypt(token, Constant.MBCCS_PUBLIC_KEY, false))
                .append("</token>");
        result.append("<encryptMsg>")
                .append(Crypto.Encrypt(
                        Crypto.Encrypt(data, Session.getPublicKey(), false),
                        Constant.MBCCS_PUBLIC_KEY, false))
                .append("</encryptMsg>");
        result.append("<signatureMsg>")
                .append(Crypto.Encrypt(
                        Crypto.Sign(data, Session.getPrivateKey(), false),
                        Constant.MBCCS_PUBLIC_KEY, false))
                .append("</signatureMsg>");
        return result.toString();
    }

    public static String buildXMLFromHashmap(HashMap<String, Object> param) {
        StringBuilder result = new StringBuilder();

        Set<String> keySet = param.keySet();
        if (keySet != null && !keySet.isEmpty()) {
            for (String key : keySet) {
                Object value = param.get(key);
                if (value != null && value instanceof ArrayList<?>) {
                    ArrayList<String> arrayValue = (ArrayList<String>) value;
                    for (Object object : arrayValue) {
                        result.append("<").append(key).append(">");
                        result.append(object);
                        result.append("</").append(key).append(">");
                    }
                } else if (value == null || value.toString().trim().isEmpty()) {
                    result.append("<").append(key).append("/>");

                } else {
                    result.append("<").append(key).append(">");
                    result.append(param.get(key));
                    result.append("</").append(key).append(">");
                }

            }
        }

        return result.toString();
    }

    public String fomatXML(String reponse) {
        return reponse.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&quot;", "\"");
    }

    /**
     * HuyPQ15: Ham gui request len server
     *
     * @param envelope
     * @param url
     * @return
     * @throws Exception
     */
    public String sendRequestWriteToFile(String envelope, String url, final String fileName) {
        return OkHttpUtils.callViaHttpClientWriteToFile(url, envelope, fileName);
    }

    public String getDataFromURL(String url, Context context, String wsCode,
                                 int connectTimeOut, int responseTimeout) throws Exception {
        String responseString = "";
        Date timeStart = null;
        Date timeEnd = null;
        timeStart = new Date();
        String errCode = "0";
        String userName = "";
        String requestId = "";
        SharedPreferences preferences = context.getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);
        BufferedInputStream inputStream = null;
        BufferedReader bReader = null;
        HttpURLConnection urlConnection = null;
        if (preferences != null) {
            userName = preferences.getString(Define.KEY_LOGIN_NAME, "");
        }
        try {
            URL url1 = new URL(url);
            urlConnection = (HttpURLConnection) url1.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(connectTimeOut);
            urlConnection.setReadTimeout(responseTimeout);

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = new BufferedInputStream(
                        urlConnection.getInputStream());

                try {
                    bReader = new BufferedReader(new InputStreamReader(
                            inputStream, "utf-8"), 8);
                    StringBuilder sBuilder = new StringBuilder();

                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sBuilder.append(line).append("\n");
                    }

                    inputStream.close();
                    bReader.close();
                    urlConnection.disconnect();
                    try {
                        if (Session.KPI_REQUEST) {
                            requestId = userName + "_"
                                    + CommonActivity.getDeviceId(context) + "_"
                                    + System.currentTimeMillis();

                            SaveLog saveLog = new SaveLog(context,
                                    Constant.SYSTEM_NAME, userName, wsCode,
                                    CommonActivity.findMyLocation(context)
                                            .getX(), CommonActivity
                                    .findMyLocation(context).getY());
                            timeEnd = new Date();
                            saveLog.saveLogRequest(errCode, timeStart, timeEnd,
                                    requestId);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return sBuilder.toString();

                } catch (Exception e) {
                    Log.e("Error", "getDataFromURL", e);
                }
            }

        } catch (Exception e) {
            try {
                if (Session.KPI_REQUEST) {
                    // Gọi các hàm đo kiểm KPI Request
                    if (requestId == null || requestId.isEmpty()) {
                        requestId = Session.userName + "_"
                                + CommonActivity.getDeviceId(context) + "_"
                                + System.currentTimeMillis();
                    }
                    SaveLog saveLog = new SaveLog(context,
                            Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(e + "", timeStart, timeEnd,
                            requestId);
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }

            throw e;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }
            try {
                if (bReader != null) {
                    bReader.close();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }
            try {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }

        }

        return responseString;
    }

    public String sendRequestJson(String envelope, String url, Context context, String wsCode) throws Exception {
        return sendRequestJson(envelope, url, context, wsCode, Constant.TIME_OUT_CONECT, Constant.TIME_OUT_RESPONE);
    }

    public String sendRequestJson(String envelope, String url, Context context, String wsCode, int connectTimeOut,
                                  int responseTimeout) throws Exception {

        envelope = envelope.replaceAll("&nbsp;", "&#160;");

        String responseString = "";
        Date timeStart = null;
        Date timeEnd = null;
        timeStart = new Date();
        String errCode = "";
        String userName = "";
        String requestId = "";
        CommonOutput output = null;
        SharedPreferences preferences = context.getSharedPreferences(Define.PRE_NAME, Activity.MODE_PRIVATE);
        if (preferences != null) {
            userName = preferences.getString(Define.KEY_LOGIN_NAME, "");
        }
        try {
            responseString = OkHttpUtils.callViaHttpClient(url, envelope);

            try {
                if (Session.KPI_REQUEST) {

                    int begin = envelope.indexOf("<requestId>");
                    int end = envelope.indexOf("</requestId>");

                    requestId = userName + "_" + CommonActivity.getDeviceId(context) + "_" + System.currentTimeMillis();

                    if (begin >= 0 && end >= 0 && end - begin > 11) {
                        requestId = envelope.substring(begin + 11, end);
                    }
                    if (requestId != null && requestId.length() >= 60) {
                        requestId = requestId.substring(0, 57);
                    }

                    // Goi cac ham do kiem
                    if (responseString.equals("200")) {
                        errCode = "200";
                    } else {
                        Log.d("reponseeeeeeeJson", responseString);
                        Long start = System.currentTimeMillis();
                        output = parseGWOutputResponseWithDom(responseString);

                        Log.d("enddddddddddddddddd", (System.currentTimeMillis() - start) + "");
                        errCode = output.getError();
                        if (output.getOriginal() != null && !output.getOriginal().isEmpty()) {

                            responseString = output.getOriginal();
                            responseString = responseString.replaceAll("<return>", "").replace("</return>", "")
                                    .replace("&quot;", "\"");
                            responseString = responseString.replaceAll("<jsonRet>", "").replace("</jsonRet>", "");
//							VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
//									.parseXMLHandler(new VSAMenuHandler(), output.getOriginal());
//							output = handler.getItem();
                            errCode = output.getErrorCode();

                        }

                    }

                    SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(errCode, timeStart, timeEnd, requestId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            try {
                if (Session.KPI_REQUEST) {
                    // Gọi các hàm đo kiểm KPI Request
                    if (requestId == null || requestId.isEmpty()) {
                        requestId = Session.userName + "_" + CommonActivity.getDeviceId(context) + "_"
                                + System.currentTimeMillis();
                    }
                    SaveLog saveLog = new SaveLog(context, Constant.SYSTEM_NAME, userName, wsCode,
                            CommonActivity.findMyLocation(context).getX(),
                            CommonActivity.findMyLocation(context).getY());
                    timeEnd = new Date();
                    saveLog.saveLogRequest(e + "", timeStart, timeEnd, requestId);
                }
            } catch (Exception e2) {
                // TODO: handle exception
            }

            throw e;
        }

        return responseString;
    }
//    if (localName.equalsIgnoreCase("error")) {
//        item.setError(currentValue);
//        item.setErrorCode(currentValue);
//    } else if (localName.equalsIgnoreCase("errorCode")) {
//        item.setErrorCode(currentValue);
//        item.setError(currentValue);
//    } else if (localName.equalsIgnoreCase("description")) {
//        item.setDescription(currentValue);
//    } else if (localName.equalsIgnoreCase("original")) {
//        currentValue = currentValue.substring(
//                currentValue.indexOf("<return>" ),
//                currentValue.indexOf("</return>")+ 9);
//        item.setOriginal(currentValue);
//    } else if (localName.equalsIgnoreCase("return")) {
//        item.setDescription(currentValue);
//    } else if (localName.equalsIgnoreCase("result")) {
//        // itemsList.add(item);
//    } else if (localName.equalsIgnoreCase("token")) {
//        item.setToken(currentValue);
//    }

    private CommonOutput parseGWOutputResponseWithDom(String response) throws Exception {

        XmlDomParse parse = new XmlDomParse();
        Document doc = parse.getDomElement(response);
        NodeList nl = doc.getElementsByTagName("Result");
        Element e2 = (Element) nl.item(0);
        output.setError(parse.getValue(e2, "error"));
        output.setDescription(parse.getValue(e2, "description"));
        String original = parse.getValue(e2, "original");
        output.setOriginal(original.substring(original.indexOf("<return>"), original.indexOf("</return>") + 9));
        output.setErrorCode(parse.getValue(e2, "errorCode"));
        output.setToken(parse.getValue(e2, "token"));
//        String original = response.replaceAll("(.*?<original>)(.*)(</original>.*)", "$2");
//        if (!original.startsWith("<![CDATA[")) {
//            original = StringUtils.unescapeXML(original);
//        }
//
//        if (!CommonActivity.isNullOrEmpty(original)) {
//            original = original.replaceAll("(.*?)(<return>.*</return>)(.*)", "$2");
//            output.setOriginal(original);
//            String error = response.toString().replaceAll("(.*?<error>)(.*?)(</error>.*)", "$2");
//            output.setError(error);
//            if (!"0".equals(error)) {
//                String description = response.toString().replaceAll("(.*?<description>)(.*?)(</description>.*)", "$2");
//                output.setDescription(description);
//            }
//        } else {
//        Log.d(TAG,"starttime parse request:=========> " + System.currentTimeMillis());
//        SAXParserFactory spf = SAXParserFactory.newInstance();
//        SAXParser sp = spf.newSAXParser();
//        XMLReader xr = sp.getXMLReader();
//        CommonOutputHandler handler = new CommonOutputHandler();
//        xr.setContentHandler(handler);
//        InputSource inStream = new InputSource();
//        inStream.setCharacterStream(new StringReader(response));
//        // parse xml
//        xr.parse(inStream);
//        output = handler.getItem();
//        Log.d(TAG,"entime parse request:=========> " + System.currentTimeMillis());
//        }
        return output;
    }
}
