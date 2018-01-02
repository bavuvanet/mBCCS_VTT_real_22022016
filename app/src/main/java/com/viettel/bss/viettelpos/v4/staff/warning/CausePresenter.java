package com.viettel.bss.viettelpos.v4.staff.warning;

/**
 * Created by ICHI on 6/3/2017.
 */

public interface CausePresenter {
    void requestCauseForStaff();

    void requestUpdateAlarmForStaff(String alarmID, String causeID, String causeName, String content);
}
