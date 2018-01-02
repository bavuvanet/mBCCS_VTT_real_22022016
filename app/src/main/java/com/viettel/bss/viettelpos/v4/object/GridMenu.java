package com.viettel.bss.viettelpos.v4.object;

import com.viettel.bss.viettelpos.v4.login.object.Manager;

import java.util.List;

/**
 * Created by Toancx on 1/19/2017.
 */

public class GridMenu {
    private String title;
    private List<Manager> lstData;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Manager> getLstData() {
        return lstData;
    }

    public void setLstData(List<Manager> lstData) {
        this.lstData = lstData;
    }
}
