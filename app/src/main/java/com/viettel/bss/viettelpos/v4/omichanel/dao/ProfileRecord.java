package com.viettel.bss.viettelpos.v4.omichanel.dao;

import android.graphics.Bitmap;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuLA4 on 8/30/2017.
 */
@Root(name = "return", strict = false)
public class ProfileRecord implements Serializable {

    @Element(name = "code", required = false)
    private String code;
    @Element(name = "url", required = false)
    private String url;
    @Element(name = "server", required = false)
    private String server;
    @Element(name = "symbolicLink", required = false)
    private String symbolicLink;
    @Element(name = "content", required = false)
    private String content;
    @Element(name = "isDownload", required = false)
    private boolean isDownload = false;
    @Element(name = "fileExtension", required = false)
    private String fileExtension = Constant.IMG_EXT_JPG;

    // type chinh la sourceId cua profile
    @Element(name = "type", required = false)
    private String type;

    private List<String> unZipPath;
    private Bitmap bitmap;
    private String path;
    private ArrayList<OrderInfo> arrOrderInfo = new ArrayList<>();

    public ArrayList<OrderInfo> getArrOrderInfo() {
        return arrOrderInfo;
    }

    public void setArrOrderInfo(ArrayList<OrderInfo> arrOrderInfo) {
        this.arrOrderInfo = arrOrderInfo;
    }

    public ProfileRecord() {
        // do nothing
    }

    public ProfileRecord(String code, String content, String fileExtension) {
        this.code = code;
        this.content = content;
        this.fileExtension = fileExtension;
    }

    public ProfileRecord(String code, String url) {
        this.code = code;
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getFileExtension() {
        return CommonActivity.getFileExtension(url).toLowerCase();
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getSymbolicLink() {
        return symbolicLink;
    }

    public void setSymbolicLink(String symbolicLink) {
        this.symbolicLink = symbolicLink;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }


    public  String toXml(){
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("<code>").append(code);
        resultStr.append("</code>");
        if(!CommonActivity.isNullOrEmpty(type)){
            resultStr.append("<type>").append(type);
            resultStr.append("</type>");
        }
        resultStr.append("<content>").append(content);
        resultStr.append("</content>");
        resultStr.append("<fileExtension>").append(fileExtension);
        resultStr.append("</fileExtension>");
        return resultStr.toString();
    }

    public  String toXmlOmni(){
        StringBuilder resultStr = new StringBuilder();
        resultStr.append("<fileCode>").append(code);
        resultStr.append("</fileCode>");
        resultStr.append("<contents>").append("");
        resultStr.append("</contents>");
        resultStr.append("<electronicSign>").append(1);
        resultStr.append("</electronicSign>");
        return resultStr.toString();
    }


    public List<String> getUnZipPath() {
        return unZipPath;
    }

    public void setUnZipPath(List<String> unZipPath) {
        this.unZipPath = unZipPath;
    }
}
