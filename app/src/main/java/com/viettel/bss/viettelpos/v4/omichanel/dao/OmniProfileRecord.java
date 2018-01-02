package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by TuLA4 on 8/30/2017.
 */
public class OmniProfileRecord implements Serializable {
    private String code;
    private String url;
    private String server;
    private String originalUrl;
    private String warnLinkDie;

    private String type;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getWarnLinkDie() {
        return warnLinkDie;
    }

    public void setWarnLinkDie(String warnLinkDie) {
        this.warnLinkDie = warnLinkDie;
    }
}
