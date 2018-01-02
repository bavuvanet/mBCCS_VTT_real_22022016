package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.io.Serializable;


/**
 * Created by Thinhhq1 on 7/28/2017.
 */
@Root(name = "return",strict=false)
public class ImageBO implements Serializable {
    @Element(name = "imageContent", required = false)
    private String imageContent;
    @Element(name = "imageLink", required = false)
    private String imageLink;
    @Element(name = "imageType", required = false)
    private String imageType;

    private boolean isDownload = false;

    public boolean isDownload() {
        return isDownload;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public String getImageLink() {
        if(!CommonActivity.isNullOrEmpty(imageLink)){
            return "http://" + imageLink;
        }
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}
