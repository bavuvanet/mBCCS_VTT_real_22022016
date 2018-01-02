package com.viettel.bss.viettelpos.v4.report.object;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

/**
 * Created by Toancx on 1/7/2017.
 */

@Root(name = "LogMethodBean", strict = false)
public class LogMethodBean implements Serializable{
    @Element(name = "className", required = false)
    private String className;
    @Element(name = "startTime", required = false)
    private String startTime;
    @Element(name = "methodName", required = false)
    private String methodName;
    @Element(name = "duration", required = false)
    private String duration;
    @Element(name = "userCall", required = false)
    private String userCall;
    @Element(name = "inputValue", required = false)
    private String inputValue;
    @Element(name = "resultValue", required = false)
    private String resultValue;
    @Element(name = "resultCode", required = false)
    private String resultCode;
    @Element(name = "serverId", required = false)
    private String serverId;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUserCall() {
        return userCall;
    }

    public void setUserCall(String userCall) {
        this.userCall = userCall;
    }

    public String getInputValue() {
        return inputValue;
    }

    public void setInputValue(String inputValue) {
        this.inputValue = inputValue;
    }

    public String getResultValue() {
        return resultValue;
    }

    public void setResultValue(String resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultCode() {
        if("0".equals(resultCode)){
            return MainActivity.getInstance().getString(R.string.logMethodSuccess);
        } else if ("1".equals(resultCode)){
            return MainActivity.getInstance().getString(R.string.logMethodFail);
        } else {
            return resultCode;
        }
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
}
