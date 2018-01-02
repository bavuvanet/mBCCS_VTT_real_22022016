package com.viettel.bss.viettelpos.v4.staff.kpi;

/**
 * Created by NHAT on 01/06/2017.
 */

public interface KPIPresenter {
    public void requestKpiForStaff(String staffCode, String month, String kpiCode, int rowStart);
}
