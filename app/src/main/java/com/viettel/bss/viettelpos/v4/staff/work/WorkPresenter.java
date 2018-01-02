package com.viettel.bss.viettelpos.v4.staff.work;

/**
 * Created by ICHI on 6/3/2017.
 */

public interface WorkPresenter {
    void requestUpdateWork(String woId, String status, String reason, String solution, String note);
}
