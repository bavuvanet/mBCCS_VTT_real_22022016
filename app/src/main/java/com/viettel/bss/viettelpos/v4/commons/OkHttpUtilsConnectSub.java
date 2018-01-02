package com.viettel.bss.viettelpos.v4.commons;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpUtilsConnectSub {
    private static final String TAG = "OkHttpUtils";
    private static final MediaType XML
            = MediaType.parse("text/xml;charset=UTF-8");
    private static OkHttpClient client;

    static {
        try{
            client = new OkHttpClient.Builder()
                    .connectTimeout(600000, TimeUnit.MILLISECONDS)
                    .readTimeout(600000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(false)
                    .build();
        }catch (Exception ex){
            Log.d(TAG, "Error OkHttpClient.Builder: ", ex);
        }
    }

    public static String callViaHttpClient(String url, String envelope) {
        String result = "";
        try {
            Log.d(TAG, "callViaHttpClient url = " + url + ", envelope = " + envelope);
            Long start = System.currentTimeMillis();
//            Log.d(TAG,"starttime send request:=========> " + start);
            RequestBody body = RequestBody.create(XML, envelope);
//            Log.d(TAG,"createxml send request:=========> " + (System.currentTimeMillis() - start));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            result = response.body().string();
//            Log.d(TAG,"endtime send request:=========> " + (System.currentTimeMillis()-start));
        } catch (Exception e) {
            Log.d(TAG, "callViaHttpClient", e);
        }
        return result;
    }

    public static String callViaHttpClientWriteToFile(String url, String envelope, final String fileName) {
        String result = "";
        try {
            RequestBody body = RequestBody.create(XML, envelope);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            result =  writeToFile(response.body().string(), fileName);

//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    writeToFile1(response.body().string(), fileName);
//                }
//            });
        } catch (Exception e) {
            Log.d(TAG, "callViaHttpClient", e);
        }
        return result;
    }

//    private static String writeToFile(String response, String fileName){
//        String result = "";
//        try{
//
//            File path = new File(MBCCS_TEMP_FOLDER);
//            File file = new File(path,fileName);
//            FileOutputStream  out = null;
//            try {
//                path.mkdirs();
//                file.createNewFile();
//                out = new FileOutputStream (
//                        file);
//                out.write(response.getBytes());
//                out.flush();
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (out != null) {
//                    out.close();
//                }
//            }
//            result = Constant.MBCCS_TEMP_FOLDER + fileName;
//            return result;
//        }catch (Exception ex){
//            Log.d(TAG, "writeToFile", ex);
//        }
//        return  result;
//    }

    private static String writeToFile(String data, String filename) {
        String result = "";
        String path =
                Environment.getExternalStorageDirectory() + File.separator  + "parseFolder";
        // Create the folder.
        File folder = new File(path);
        folder.mkdirs();

        // Create the file.
        File file = new File(folder, filename);


        try {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
            result = file.getPath();
            return  result;
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return  result;
    }


    public static OkHttpClient getClient(){
        return client;
    }
}
