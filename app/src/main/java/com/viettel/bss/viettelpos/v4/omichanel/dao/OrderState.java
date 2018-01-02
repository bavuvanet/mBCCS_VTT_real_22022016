package com.viettel.bss.viettelpos.v4.omichanel.dao;

/**
 * Created by toancx on 9/25/2017.
 */

public class OrderState {

    public static final int ORD_CHECKIN_ACT = 1; // xac nhan
    public static final int ORD_CLAIM_ACT = 2; // nhan viec
    public static final int ORD_CONNECT_ACT = 3; // dau noi
    public static final int ORD_CLAIM_RECEIP_ACT = 4; // nhan viec
    public static final int ORD_SAVE_ACT = 5; // save
    public static final int ORD_RE_CLAIM_ACT = 6; // create order

    public static final int ORD_STATE_OTHER_ACT = 7; // only view
    public static final int ORD_STAFF_OTHER_ACT = 8; // staff other
    public static final int ORD_TRANSFER_ACT = 9; // staff other
    public static final int ORD_HSDT_ACT = 10; // staff other
}
