package com.viettel.bss.viettelpos.v4.commons;

public class BPConstant {
	public static final String COMMAND_TRANSFER_AT_HOME = "CMDTAH";
    public static final String COMMAND_TRANSFER_AT_SHOP = "CMDTAS";
    public static final String COMMAND_DELIVER = "DELIVER";
    public static final String COMMAND_PAYMENT = "PAYMENT";
    public static final String COMMAND_TOPUP_VIETTEL = "TOPUP_VT";
    public static final String COMMAND_TOPUP_VINA = "TOPUP_VN";
    public static final String COMMAND_TOPUP_VIMO = "TOPUP_VM";
    public static final String COMMAND_TOPUP_MOBI = "TOPUP_MB";
    public static final String COMMAND_GET_FEE = "GET_FEE_TRANSFER";
    public static final String COMMAND_GET_MERCHANT = "GET_MERCHANT";
    public static final String COMMAND_SHOP_RECEIVER_MONEY = "CMDGSRM";
    public static final String COMMAND_BANK_KPP = "GET_BANK_KPP";
    public static final String COMMAND_CHECK_DEBIT_INFOR = "KPP302";
    public static final String COMMAND_PAYMENT_WATER = "CMDPWATER";
    public static final String COMMAND_PAYMENT_FINANCE = "CMDPFCE";
    public static final String COMMAND_BUY_CARD_GAME = "BUYCG";
    public static final String COMMAND_PAYMENT_TELEVISON = "CMDPTH";
    public static final String COMMAND_PAYMENT_ELECTRIC = "CMDPEC";
    public static final String COMMAND_LOOKUP_TRANS_HISTORY = "CMDLTH";

    public static final String COMMAND_RESET_PIN_CODE = "CMDRSPIN";
    public static final String COMMAND_CHANGE_PIN_CODE = "CMDCHNPIN";
    public static final String COMMAND_GET_LST_PIN_CODE_TRANS = "CMDGLPCT";
    public static final String COMMAND_SEND_SMS_PIN_CODE_TRANS = "CMDSSMSPCT";
    public static final String COMMAND_CREATE_OTP_DESTROY_TRANS = "CMDCODT";
    public static final String COMMAND_DESTROY_TRANS = "CMDT";
    public static final String COMMAND_QUERY_DELIVER_TRANS = "CMDQDT";

    public static interface SERVICE_INDICATOR {
    	public static final String RECEIVER_AT_HOME = "645100";
    	public static final String RECEIVER_AT_SHOP = "645200";
    }
    
    public static interface MERCHANT{
    	public static final String NUOC = "NUOC";
    	public static final String DIEN = "EVN";
    	public static final String TAICHINH = "TAICHINH";
    	public static final String BAOHIEM = "BAOHIEM";
    	public static final String CVT = "CVT";
    	public static final String GAME = "GAME";
    	public static final String THS = "THS";
    	public static final String UNGDUNG = "UNGDUNG";
    	public static final String PINCODEOUT = "PINCODEOUT";
    }
}
