package com.viettel.bss.viettelpos.v4.staff.work;

/**
 * Created by NHAT on 01/06/2017.
 */

public interface WorkListPresenter {
    void requestWorkList(int rowStart, int status, long fromDate, long toDate, String woCode);
}
