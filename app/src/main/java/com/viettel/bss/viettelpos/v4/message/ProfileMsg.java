package com.viettel.bss.viettelpos.v4.message;

import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toancx on 12/27/2016.
 */

public class ProfileMsg {
    public ActionProfileBean actionProfileBean;
    public List<RecordBean> lstRecordBeans = new ArrayList<>();

    public ProfileMsg(){}

    public ProfileMsg(ActionProfileBean actionProfileBean, List<RecordBean> lstRecordBeans){
        this.actionProfileBean = actionProfileBean;
        this.lstRecordBeans = lstRecordBeans;
    }
}
