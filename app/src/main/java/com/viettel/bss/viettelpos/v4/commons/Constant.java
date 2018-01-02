package com.viettel.bss.viettelpos.v4.commons;

import android.os.Environment;

import com.viettel.bss.viettelpos.v4.R;

import java.io.File;

public class Constant {
    // omni
    public static final String ID_SEARCH_ORDER = "ID_SEARCH_ORDER";
    public static final String ID_CLAIM_ORDER = "ID_CLAIM_ORDER";
    public static final String ORD_TYPE_CONNECT_PREPAID = "CONNECT_PREPAID"; //2
    public static final String ORD_TYPE_CONNECT_POSPAID = "CONNECT_POSPAID"; //1
    public static final String ORD_TYPE_REGISTER_PREPAID = "REGISTER_PREPAID";
    public static final String ORD_TYPE_CHANGE_TO_POSPAID = "CHANGE_TO_POSPAID";
    public static final String ORD_TYPE_CHANGE_PREPAID_FEE = "CHANGE_PREPAID_FEE";
    public static final String HSDT = "HSDT";
    public static final String CHANGE_TO_PREPAID = "CHANGE_TO_PREPAID";
    public static final String CHANGE_SIM = "CHANGE_SIM";
    public static final String CONNECT_FIX_LINE = "CONNECT_FIX_LINE";

    // SharePreferences file name for check NV omni
    public static final String SHARE_PREFERENCES_FILE = "mbccs_share_preferences";
    public static final String OMNI_STAFF_NAME_SAVE = "omni_staff"; // -1 - nil, 0 - NV, 1 - CTV
    public static final String OMNI_STAFF_INVALID = "-1";
    public static final String OMNI_STAFF_NV = "0";
    public static final String OMNI_STAFF_CTV = "1";

    public static final String SIGNATURE_STAFF_SAVE = "signature_staff";
    public static final String SIGNATURE_STAFF_EXISTS = "signature_exits";

    // image ext
    public static final String IMG_EXT_PNG = "png";
    public static final String IMG_EXT_JPG = "jpg";
    public static final String ZIP = "zip";


    // public final static long TIME_OUT = 60000 * 13;// 60000*13
    // Dev Test
    public static String IP = "10.58.71.240";
    private static int REAL_VERSION = 1;
    private static int TEST_VERSION = 2;
    private static int TEST_MERGE = 4;
    private static int BEFORE_UPCODE_VERSION = 3;
    public static int PORT = 8989;
    public static final String SURVEY_DOMAIN = "mbccs";
    public static final int STAFF_VT_ID = 14;

    //	public static String IP = "192.168.176.168";
//	public static int PORT = 8115;
//	public static String URL_SERVICE = "http://10.58.71.177:8989/";
//	public static String URL_SERVICE_DOWNLOAD = "http://10.58.71.240:8888/";
    public static String URL_SERVICE = "http://192.168.176.168:8115/";
    //	 public static String URL_SERVICE = "http://10.60.7.126:8115/";
    public static String URL_SERVICE_DOWNLOAD = "http://10.60.7.126:8086/";
    public static String SURVEY_GET_DATA_URL = "http://10.60.106.229:8909/chat/" + SURVEY_DOMAIN + "/";
    public static String SURVEY_SUBMIT_DATA_URL = "http://10.60.106.229:8909/survey/";
    public static String WS_ASIGN_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BocWS?wsdl";
    public static String WS_PAKAGE_DATA = "http://10.58.71.240:8432/" + "mBCCSService/ProductServiceWS?wsdl";
    public static String WS_SUB_DATA = "http://10.58.71.240:8888/" + "mBCCSService/BCCS2FixServiceWS?wsdl";
    public static String PATH_UPDATE_FIX_ERROR_VERSION = "http://10.60.7.126:8086/mBCCSService/webresources/syncDatabase/getDebugApk/";
    public static String PATH_UPDATE_MBCCS_VERSION = "http://10.60.7.126:8086/mBCCSService/webresources/syncDatabase/upgradeNormal/";
    public static String DOWNLOAD_DIRECTORY_STRING =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    public static String IMAGES_DIRECTORY_STRING =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    public static final String DIR_SAVE_PROFILE_PATH =
            IMAGES_DIRECTORY_STRING + File.separator + "ProfileRecords";
    public static final String DIR_SAVE_PROFILE_SALE_PATH =
            IMAGES_DIRECTORY_STRING + File.separator + "ProfilesSale";
    public static final String DIR_SAVE_SIG_PATH =
            IMAGES_DIRECTORY_STRING + File.separator + "Signatures";

    // public static String SURVEY_GET_DATA_URL =
    // "http://10.55.53.158:8090/chat/"
    // + SURVEY_DOMAIN + "/";
    // public static String SURVEY_SUBMIT_DATA_URL =
    // "http://10.55.53.158:8090/survey/";
    public static String WS_PAKAGE_DATA_BCCS = "http://10.60.7.126:8086/"
            + "mBCCSService/ProductServiceWS?wsdl";
//    public static String BCCS_GW_URL_TEST_BCCS2 = URL_SERVICE
//            + "BCCSGatewayWS/BCCSGatewayWS?wsdl";


    // http://10.60.7.126:8086/
    public static String PATH_BARCODE = URL_SERVICE_DOWNLOAD
            + "mBCCSService/webresources/syncDatabase/barcodeApk/";
    public static final Boolean addInfo = true;

    static {
		int version_type = BEFORE_UPCODE_VERSION;
        if (version_type == REAL_VERSION) {
//            BCCS_GW_URL_TEST_BCCS2 = "http://10.60.7.126:8115/"
//                    + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
            IP = "192.168.176.168";
            PORT = 8115;
            URL_SERVICE = "http://192.168.176.168:8115/";
            URL_SERVICE_DOWNLOAD = "http://10.60.7.126:8086/";
//            URL_SERVICE_DOWNLOAD = "http://10.60.7.240:8873/";
            SURVEY_GET_DATA_URL = "http://10.60.106.229:8909/chat/"
                    + SURVEY_DOMAIN + "/";
            SURVEY_GET_DATA_URL = "http://10.60.106.229:8909/chat/"
                    + SURVEY_DOMAIN + "/";
            SURVEY_SUBMIT_DATA_URL = "http://10.60.106.229:8909/survey/";
            WS_ASIGN_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BocWS?wsdl";
            PATH_UPDATE_FIX_ERROR_VERSION = "http://10.60.7.126:8086/mBCCSService/webresources/syncDatabase/getDebugApk/";
            PATH_UPDATE_MBCCS_VERSION= "http://10.60.7.126:8086/mBCCSService/webresources/syncDatabase/upgradeNormal/";
            WS_PAKAGE_DATA_BCCS = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/ProductServiceWS?wsdl";
            WS_PAKAGE_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/ProductServiceWS?wsdl";
            WS_SUB_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BCCS2FixServiceWS?wsdl";
        } else if (version_type == TEST_VERSION) {

            IP = "10.58.71.177";
            PORT = 8289;
            URL_SERVICE = "http://10.58.71.177:8289/";
            //URL_SERVICE = "http://10.58.71.240:8189/";
            URL_SERVICE_DOWNLOAD = "http://10.58.71.240:8887/";
            SURVEY_SUBMIT_DATA_URL = "http://10.60.106.229:8909/survey/";
            SURVEY_GET_DATA_URL = "http://10.55.53.158:8090/chat/"
                    + SURVEY_DOMAIN + "/";
            SURVEY_SUBMIT_DATA_URL = "http://10.55.53.158:8090/survey/";
            WS_ASIGN_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BocWS?wsdl";
            WS_PAKAGE_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/ProductServiceWS?wsdl";
            WS_SUB_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BCCS2FixServiceWS?wsdl";
            PATH_UPDATE_FIX_ERROR_VERSION = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/getDebugApk/";
            PATH_UPDATE_MBCCS_VERSION = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/upgradeNormal/";
            WS_PAKAGE_DATA_BCCS = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/ProductServiceWS?wsdl";
//            BCCS_GW_URL_TEST_BCCS2 = URL_SERVICE
//                    + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
            PATH_BARCODE = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/webresources/syncDatabase/barcodeApk/";
        } else if (version_type == BEFORE_UPCODE_VERSION) {
            IP = "192.168.176.168";
            PORT = 8115;
            URL_SERVICE = "http://10.60.7.126:8115/";

            URL_SERVICE_DOWNLOAD = "http://10.60.7.126:8086/";
//			URL_SERVICE_DOWNLOAD = "http://10.60.7.240:8873/";
            SURVEY_GET_DATA_URL = "http://10.60.106.229:8909/chat/"
                    + SURVEY_DOMAIN + "/";
            SURVEY_GET_DATA_URL = "http://10.60.106.229:8909/chat/"
                    + SURVEY_DOMAIN + "/";
            SURVEY_SUBMIT_DATA_URL = "http://10.60.106.229:8909/survey/";
            WS_ASIGN_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BocWS?wsdl";
            WS_PAKAGE_DATA_BCCS = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/ProductServiceWS?wsdl";
            WS_PAKAGE_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/ProductServiceWS?wsdl";
            WS_SUB_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BCCS2FixServiceWS?wsdl";

//            BCCS_GW_URL_TEST_BCCS2 = "http://10.60.7.126:8115/"
//                    + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
        } else if (version_type == TEST_MERGE) {

            IP = "10.58.71.240";
            PORT = 8689;
            URL_SERVICE = "http://10.58.71.240:8189/";
            URL_SERVICE_DOWNLOAD = "http://10.58.71.240:8887/";
            SURVEY_SUBMIT_DATA_URL = "http://10.60.106.229:8909/survey/";
            SURVEY_GET_DATA_URL = "http://10.55.53.158:8090/chat/"
                    + SURVEY_DOMAIN + "/";
            SURVEY_SUBMIT_DATA_URL = "http://10.55.53.158:8090/survey/";
            WS_ASIGN_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BocWS?wsdl";
            WS_PAKAGE_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/ProductServiceWS?wsdl";
            WS_SUB_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BCCS2FixServiceWS?wsdl";
            PATH_UPDATE_FIX_ERROR_VERSION = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/getDebugApk/";
            PATH_UPDATE_MBCCS_VERSION = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/upgradeNormal/";
            WS_PAKAGE_DATA_BCCS = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/ProductServiceWS?wsdl";
//            BCCS_GW_URL_TEST_BCCS2 = URL_SERVICE
//                    + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
            PATH_BARCODE = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/webresources/syncDatabase/barcodeApk/";
        }

        if (version_type == REAL_VERSION
                || version_type == BEFORE_UPCODE_VERSION) {
            PATH_BARCODE = URL_SERVICE_DOWNLOAD
                    + "mBCCSService/webresources/syncDatabase/barcodeApk/";
        }
    }

    // public static String ;
    // public static int PORT = 8115;

    // public static String URL_SERVICE = "http://192.168.176.168:8115/";
    // public static String URL_SERVICE_DOWNLOAD = "http://10.60.7.126:8086/";
    // public static String URL_SERVICE = "http://10.60.7.126:8115/";
    public static String BCCS_GW_URL = URL_SERVICE
            + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
    // that phan doi sim
    // public static String BCCS_GW_URL_BEFORE =
    // "http://10.60.7.126:8115/BCCSGatewayWS/BCCSGatewayWS?wsdl";
    // test doi sim
    public static String BCCS_GW_URL_BEFORE = URL_SERVICE
            + "BCCSGatewayWS/BCCSGatewayWS?wsdl";
    public static String LINK_WS_UPLOAD_IMAGE = URL_SERVICE_DOWNLOAD
            + "mBCCSService/webresources/syncDatabase/upload/";
    public static String LINK_WS_SYNC = URL_SERVICE_DOWNLOAD
            + "mBCCSService/webresources/syncDatabase/";
    public static String LINK_WS_COM = URL_SERVICE_DOWNLOAD
            + "mBCCSService/webresources/syncDatabase/communication/";
    public static String PATH_DOWNLOAD = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/firstSyncBCCS2/";
    //    public static String PATH_DOWNLOAD = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/firstSync/";
    public static String PATH_SYNC_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/webresources/syncDatabase/syncDataBCCS2/";
    public static String PATH_UPDATE_VERSION = URL_SERVICE_DOWNLOAD
            + "mBCCSService/webresources/syncDatabase/upgrade/";
    public static String WS_SYNCHRONIZE_DATA = URL_SERVICE_DOWNLOAD
            + "mBCCSService/SmartphoneWS?wsdl";
    public static String WS_PAKAGE_DATA_SMARTSIM = URL_SERVICE_DOWNLOAD + "mBCCSService/CMWS?wsdl";
    public static final String PAKAGE_FILE_NAME = "pakageManualFile";
    // lay danh sach thue bao chinh chu
    //	public static String WS_SUB_DATA = "http://10.30.131.80:8022/mBCCSService/BCCS2FixServiceWS?wsdl";
//	public static String WS_ASIGN_DATA = URL_SERVICE_DOWNLOAD + "mBCCSService/BocWS?wsdl"; //real
    // "mBCCSService/BocWS?wsdl"; //real
    // public static String WS_ASIGN_DATA = "http://10.58.71.240:8431/" +
    // "mBCCSService/BocWS?wsdl";
    public static final String ASIGN_FILE_NAME = "asignManualFile";

    public static final String EXCHANGE_ADDRESS = "190";
    // public static final String EXCHANGE_ADDRESS = "154";
    public static final String LOGIN_SYNTAX = "0SI";
    public static final long checkmemoryPhone = 30;
    public static final long lowMemory = 256;
    public final static int LOGIN_TIME_OUT_CONECT = 30000;
    public final static int LOGIN_TIME_OUT_RESPONE = 75000;
    public final static int TIME_OUT_PING = 10000;
    public final static int TIME_OUT_CONECT = 60000;
    public final static int TIME_OUT_SETDATE = 500;
    public final static int TIME_OUT_RESPONE = 60000 * 3;
    public static final String SUCCESS_CODE = "SUCCESS";
    public static final String ERROR_CODE = "ERROR";
    public static final String INVALID_TOKEN = "TOKEN_INVALID";
    public static final String INVALID_TOKEN2 = "TOKEN_INVALID";
    public static final String BCCSGW_USER = CommonActivity.getUser(Define.us);
    public static final String BCCSGW_PASS = CommonActivity.getPass(Define.ps);

    public final static String DATABASE_CONFIG = "DATABSE_CONFIG";
    public static final String PARAM_USER_LOGIN = "LAST_USER_LOGIN";
    // Loai gia cho ban dut
    public static final Long PRICE_SALE = 1L;
    // Loai gia cho dat coc
    public static final Long PRICE_SALE_DEPOSIT = 5L;

    // TransType ban dut
    public static final Long TRANS_TYPE_SALE = 2L;
    // TransType dat coc
    public static final Long TRANS_TYPE_SALE_DEPOSIT = 1L;

    public static final int COUNT_SALE_TRANS_PER_ONCE = 100;

    public static final String SEARCH_CONTRACT_FOR_PAYMENT = "mbccs_searchContractForPayment";//

    public static final String TAG = "mBCCS";

    //
    public static class Fillter {
        public static final String defaultFillter = "defaultFillter";
        public static final String spinner_show = "spinner_show";
        public static final String orderByVisitDesc = "orderByVisitDesc";
        public static final String orderByVisitAsc = "orderByVisitAsc";
        public static final String orderBySaleDesc = "orderBySaleDesc";
        public static final String orderBySaleAsc = "orderBySaleAsc";
        public static final String orderByLocation = "orderByLocation";
        public static final String notUpdateLocation = "notUpdateLocation";
        public static final String cusCareByDay = "cusCareByDay";
    }

    public static class VSAMenu {
        // Ban dut
        public static final String SALE_SALING = ";sale.saling;";
        public static final String SALE_SALING_MBCCS2 = ";sale.saling.mbccs2;";
        // Ban le
        public static final String SALE_RETAIL = ";sale.retail;";
        public static final String SALE_RETAIL_MBCCS2 = ";sale.retail.mbccs2;";
        // Lap hoa don
        public static final String SALE_CREATE_INVOICE = ";sale.invoice;";
        public static final String SALE_CREATE_INVOICE_MBCCS2 = ";sale.invoice.mbccs2;";
        // Nhan vien dat hang cap tren
        public static final String SALE_ORDER = ";sale.order;";
        public static final String SALE_ORDER_MBCCS2 = ";sale.order.mbccs2;";
        // Ban hang qua don dat hang thanh toan qua Bankplus
        public static final String SALE_BY_ORDER = ";sale.by.order;";
        public static final String SALE_BY_ORDER_MBCCS2 = ";sale.by.order.mbccs2;";
        // Ban cho chuong trinh BHLD
        public static final String SALE_PROGRAM = ";sale.program;"; // Quan ly
        public static final String SALE_PROGRAM_MBCCS2 = ";sale.program.mbccs2;"; // Quan ly
        // ban hang
        public static final String SALE_MANAGEMENT = ";sale.management;";
        // Ban dat coc
        public static final String SALE_DEPOSIT = ";sale.deposit;";
        public static final String SALE_DEPOSIT_MBCCS2 = ";sale.deposit.mbccs2;";
        // Xac nhan nhap hang tu cap tren

        public static final String SALE_CONFIRM_NOTE = ";sale_confirm_note;";
        public static final String SALE_CONFIRM_NOTE_MBCCS2 = ";sale_confirm_note_mbccs2;";
        // phe duyet don hang
        public static final String MENU_CHANNEL_APPROVEORDER = ";sale_handle_order;";
        public static final String MENU_CHANNEL_APPROVEORDER_MBCCS2 = ";sale_handle_order_mbccs2;";

        // cap nhap ma trang
        public static final String MENU_CREATE_CHANNEL = ";create_channel;";
        public static final String MENU_CREATE_CHANNEL_MBCCS2 = ";create_channel_mbccs2;";

        // kich hoat tai khoan agent
        public static final String MENU_ACTIVE_ACCOUNT_PAYMENT = ";active_account_agent;";
        public static final String MENU_ACTIVE_ACCOUNT_PAYMENT_MBCCS2 = ";active_account_agent_mbccs2;";

        // phe duyet thanh toan
        public static final String MENU_REVIEW_PAYMENT_STAFF = ";inspect_sale_trans;";
        public static final String MENU_REVIEW_PAYMENT_STAFF_MBCCS2 = ";inspect_sale_trans_mbccs2;";

        // tra hang cap tren
        public static final String MENU_RETURN_GOOD = ";return_good;";
        public static final String MENU_RETURN_GOOD_MBCCS2 = ";return_good_mbccs2;";

        // tra hang cap tren
        public static final String GPS_ONLY = ";gps_only;";

        // tra hang cap tren
        public static final String CHARGE_DEL_CTV_PERMISSION = ";charge_del_ctv_permission;";

        // dat lich hen thay
        public static final String VSA_BOOK_SMS = ";book_sms;";

        // doi thiet bi
        public static final String CHANGE_EQUIPMENT = ";change_equipment;";

        // tra cuu no cuoc
        public static final String LOOKUP_DEBIT_INFO = ";lookup_debit_info;";
        public static final String LOOKUP_DEBIT_INFO_MBCCS2 = ";lookup_debit_info_mbccs2;";
        public static final String BCCS2IM_QLSO_TRACUUSO_GIUSO = ";BCCS2IM.QLSO.TRACUUSO.GIUSO;";
        public static final String UPDATE_ISDN_EMAIL = ";update_isdn_email;";
        public static final String UPDATE_ISDN_EMAIL_MBCCS2 = ";update_isdn_email_mbccs2;";
        // thu thap thong tin khach hang
        public static final String LOOKUP_PROFILE_TRANSACTION = ";lookup_profile_transaction;";
        public static final String LOOKUP_PROFILE_TRANSACTION_MBCCS2 = ";lookup_profile_transaction_mbccs2;";
        public static final String COLLECT_CUS_INFO = ";collect_cus_info;";
        public static final String COLLECT_CUS_INFO_MBCCS2 = ";collect_cus_info_mbccs2;";
        public static final String DO_WARRANTY = ";do_warranty;";
        public static final String DO_WARRANTY_MBCCS2 = ";do_warranty_mbccs2;";

        public static final String COMPLAIN_RECEIVER = ";complain_receiver;";

        //		public static final String PERMISSION_EDIT_CUSTOMER_ALL = ";menu.tdttkh;"; //quyen sua all thong tin khach hang
        public static final String PERMISSION_EDIT_CUSTOMER_OWNER = ";update.cus.owner;"; //quyen chi cho phep sua thong tin khach hang do user tao ra
        public static final String EDIT_CUSTOMER = ";menu.edit.customer.mbccs2;";
        public static final String TRANSFER_CUS = ";menu.transfer.cus.mbccs2;";
        public static final String REPORT_REGISTER = ";menu_report_register;"; //bao cao chi tiet dang ky thong tin
        public static final String REPORT_REGISTER_MBCCS2 = ";menu_report_register_mbccs2;"; //bao cao chi tiet dang ky thong tin
        public static final String PERMISSION_ADVISORY_CUSTOMER = "act.advisory.customer"; //quyen tu van khach hang
        public static final String PERMISSION_REGISTER_CUSTOMER = "act.register.customer"; //quyen tu van khach hang
    }

    public static final String TABLE_STAFF = "staff";
    // Khoang cach cho phep cap nhat
    public static final Long DISTANCE_VALID = 100L;
    public static final String DISTANCE_NOT_VALID = "DISTANCE_NOT_VALID";
    public static final String STAFF_HAVE_NOT_LOCATION = "STAFF_HAVE_NOT_LOCATION";

    public static final String INVALID_DISTANCE = "INVALID_DISTANCE";
    public static final String BY_SALE = "BY_SALE";
    public static final String BY_VISIT = "BY_VISIT";

    public static final Long CHANNEL_TYPE_COLLABORATOR = 10L;
    public static final Long CHANNEL_TYPE_POS = 80043L;
    public static final float RATE_RESIZE_IMG = 0.5F;
    public static final int MAX_SIZE_IMG = 100000;
    public static final int MAX_SIZE_CROP_IMG = 180;
    public static final int TIMEOUT_SMS = 60000 * 5;
    public static final int NUM_OF_CHARACTER_SMS = 110;
    public static final int SMS_TRANSACTION_LENGTH = 5;
    public static final int SMS_NUM_OF_SMS_LENGTH = 2;

    public static final String REGISTER_RECEIVER = "com.viettel.viettepos.register.sms.receiver";
    public static final String REGISTER_SENT = "com.viettel.viettepos.register.sms.sent";
    public static final String REGISTER_DELIVERED = "com.viettel.viettepos.register.sms.delivered";
    public static final String KEY_SMS_COUNT = "KEY_SMS_COUNT";
    public static final String nameSdFolderApps = "com.google.apps";
    public static final String ARR_EXT[] = new String[]{".jpg", ".mp3"};

    public static class JobMenu {
        // Giao viec
        public static final String ASIGN_JOB = ";work_assign_task;";
        // cap nhat cong viec
        public static final String WORK_UPDATE = ";work_update;";
        // thu thap thong tin thi truong
        public static final String WORK_COLLECT_INFO = ";work_collect_info;";
        public static final String WORK_COLLECT_INFO_MBCCS2 = ";work_collect_info_mbccs2;";
        // truyen thong chinh sach
        public static final String WORK_COMMUNICATION = ";work_communication;";
        public static final String WORK_COMMUNICATION_MBCCS2 = ";work_communication_mbccs2;";
        // xem lo trinh
        public static final String WORK_TASK_ROAD = ";work_task_road;";
        public static final String WORK_TASK_ROAD_MBCCS2 = ";work_task_road_mbccs2;";

        public static final String WORK_TASK_ASSIGN_MANAGER = ";work_task_assign_manager;";
        public static final String WORK_TASK_ASSIGN_STAFF = ";work_task_assign_staff;";

        public static final String WORK_TASK_UPDATE_AREA = ";work_task_update_area;";

        public static final String WORK_TASK_UPDATE_FAMILY = ";work_task_update_family;";

        // cap nhat vi tri cong viec
        // public static final String WORK_UPDATE_LOCATION =
        // ";work_update_location;";
        // public static final String WORK_UPDATE_LOCATION_MOBILE_SALING =
        // ";work_update_location_mobile_saling;";
        public static final String PERMISSION_WORK_UPDATE_TASK_LOCATION = "work_update_task_location";
        public static final String PERMISSION_WORK_UPDATE_BHLD_LOCATION = "work_update_BHLD_location";

        public static final String REQUEST_HOTLINE_WORK = "work_request_hotline";
        public static final String REQUEST_ASIGN_WORK_HOTLINE = "work_asign_hotline";

        public static final String REQUEST_RECEIPT_CHANGE_TECHNOLOGY = "request_change_tech_hotline";

        public static final String COLLECT_CUS_INFO = "COLLECT_CUS_INFO";

        public static final String ROLL_UP = "ROLL_UP";

        public static final String UPDATE_JOB_BOC2 = "UPDATE_JOB_BOC2";

    }

    private static class ReportMenu {
        public static String PERMISSION_REPORT_ASSIGN_CONTRACT = "report_assign_contract";
    }

    /*
     *
     * channel.care channel.demo channel.info channel.management channel_image
     * channel_location
     */
    public static class ChannelMenu {

        // THONG TIN KENH
        public static final long DUR_CHANNEL_CARE = 1;// min
        public static final int xDur = 60000;
        public static final String CHANNEL_INFO = ";channel.info;";
        public static final String CHANNEL_INFO_MBCCS2 = ";channel.info.mbccs2;";
        // CAP NHAT TOA DO
        public static final String CHANNEL_LOCATION = ";channel_location;";
        public static final String CHANNEL_LOCATION_MBCCS2 = ";channel_location_mbccs2;";
        // CAP NHAT HINH ANH
        public static final String CHANNEL_IMAGE = ";channel_image;";
        // Vat pham trung bay
        public static final String CHANNEL_DEMO = ";channel.demo;";
        // cham soc kenh
        public static final String CHANNEL_CARE = ";channel.care;";
        // xua ban dut cho kenh
        public static final String SALE_SALING = ";sale.saling;";
        // Ban dat coc
        public static final String SALE_DEPOSIT = ";sale.deposit;";
        // Truyen thong chinh sach
        public static final String WORK_COMMUNICATION = ";work_communication;";
        public static final String WORK_COMMUNICATION_MBCCS2 = ";work_communication_mbccs2;";
    }

    public static final String extSdcard = ".jpg";
    public static final String extServer = ".png";
    // public static final String nameSdFolderApps = "com.google.apps";
    public static final int POLICY_NOT_DONE = 1;
    public static final int POLICY_DONE = 2;
    public static final int POLICY_REFUSED = 3;
    public static final int POLICY_TASK_TYPE = 10;

    public static final int PROGRESS_LATE = 2;
    public static final int PROGRESS_COMING = 1;
    public static final int PROGRESS_COMING_FAR = 0;
    public static final int PROGRESS_DONE_OK = 3;
    public static final int PROGRESS_DONE_LATE = 4;

    public static final int ARR_PROGRESS_RESID[] = new int[]{
            R.string.chua_thong, R.string.policy_sche_coming,
            R.string.policy_sche_late, R.string.da_thong, R.string.da_thong_tre};
    public static final int DATE_REMAIN = -1;
    public static final String UPDATE_STAFF = "UPDATE_STAFF";
    public static final String SYNCHRONIZE_AUTO_FILE_NAME = "syncAutoFile";
    public static final String SYNCHRONIZE_MANUAL_FILE_NAME = "syncManualFile";
    static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    public static final String MBCCS_TEMP_FOLDER = Environment
            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .getAbsolutePath()
            + "/" + "MBCCS_TMP" + "/";
    // public static final String MBCCS_TEMP_FOLDER_IMG_TMP = Environment
    // .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
    // + File.separator + "UPLOAD_IMAGE";

    public static final String MBCCS_TEMP_FOLDER_IMG_PROFILE = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "MBCCS_HOSO_CAM_XOA";
    public static final String MBCCS_TEMP_FOLDER_IMG_PROFILE_EXTRACT = Environment
            .getExternalStorageDirectory().getAbsolutePath()
            + File.separator
            + "MBCCS_EXTRACT_HOSO_CAM_XOA";

    public static final Long POINT_OF_SALE_TYPE = 1L;
    public static final Long POINT_OF_SALE_COLLABORATOR = 2L;
    public static final Long TIME_SYNC_CHANGE_RECORD = 3 * 60L * 1000;
    // Thoi gian dong bo du lieu
    // public static final Long TIME_SYNC_CHANGE_RECORD = 1 * 20L * 1000;
    public static final Long TIME_UPLOAD_IMAGE = 60000L * 2;
    public static final int SIZE_IMAGE_SCALE = 840;

    // public static final int MAX_DISTANCE = 10;
    public static final int[] arrResIdWarnDate = new int[]{
            R.string.report_warn_fromdate_now, R.string.report_warn_todate_now,
            R.string.report_warn_todate_fromdate};
    // Thoi gian lay toa do GPS lan truoc do, de 30 phut
    // public static final Long TIME_VALID_LOCATION = 24*60 * 60 * 60 * 1000L;
    public static final Long TIME_VALID_LOCATION = 24 * 60 * 60 * 1000L;

    public static final Long TIME_VALID_LOCATION_GPS = 10 * 60 * 1000L;

    // AGPS GPS
    public static final String LOCATION_TYPE_GPS = "GPS";
    public static final String LOCATION_TYPE_AGPS = "AGPS";

    public static final String ACTION_CODE_CHANGE_EQUIPMENT = "712";

    // KPI TEST
    // public static final String KPI_SERVER =
    // "http://10.58.71.216:8208/kpiserver/GetMobileRequest";

    // kpi that
    public static final String KPI_SERVER = "http://10.60.7.126:8208/kpiserver/GetMobileRequest";
    public static final String KPI_PARTY_CODE = "MBCCS";
    public static final String USER_KPI = "WlC88aZUBgPkSxy7pYTWWmpb3CzRR1tapgcRLTNCoaA=";
    public static final String PASS_KPI = "X0nKDJK6oY5iLpA7yTGHwZvTJDC0UbiF8KsP2qGQxO8=";

    public static final String SYSTEM_NAME = "MBCCS";
    public static final String CODE_TSV = "NEW_STU_15";
    public static final String OFFERID_TSV = "1013";

    public static final String OFFERID_TSV_SPEC = "5395;6679;6680";

    // update BHLD
    public static final String REQUEST_TYPE_NEW = "6";
    public static final String REQUEST_TYPE_UPDATE = "7";
    //thay nhanh cham soc KH 1789 = sdt 0462532287
    public static final String phoneNumber = "1789";
    public static final int REQUEST_MAX_SIZE_IMAGE = 200;
    public static final String LST_SERVICE_TYPE = "A;P;N;E;I;F;U;T;2";
    public static final String SMART_SIM = "SMART";

    public static final String METHOD_NAME_ZIP_FILE = "zipImageFile";
    public static final String METHOD_NAME_ENCODE_FILE = "encodeImageFile";
    public final static int NUMBER_UPLOAD_FAIL = 20;
    public static final int PAGE_INDEX = 0;
    public static final int PAGE_SIZE = 50;
    public static final int TEEN = 18;
    public static final int CONNECT_TIMEOUT_SURVEY = 5000;
    public static final int RESPONSE_TIMEOUT_SURVEY = 5000;

    // dia chi map moi
    public static final String IP_MAP_NEW = "10.60.106.250";
    // dia chi map cu
    public static final String IP_MAP_OLD = "10.60.32.182";

    public interface PRODUCT_OFFER_TYPE {
        Long HANDSET = 7L;
        Long CARD = 6L;
    }

    public static final int PORT_MAP = 80;
    public static final int DEFAULT_JPEG_QUALITY = 70;

    public interface CUS_CARE {
        String NOI_MANG = "1";
        String NOI_MANG_VANG_LAI = "2";
        String NGOAI_MANG = "3";
        String NGOAI_MANG_VANG_LAI = "4";
        String DOI_4G = "6";
    }

    // khai bao quyen (BCCS2_SALE_SAUBAN_DIDONG_TDVAS_HP_TS,
    //BCCS2_SALE_SAUBAN_DIDONG_TDVAS_HP_TT,
    // BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TT,
    //BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TT, BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TS);

    // menu.changevas.pre
    // menu.changevas.pos

    public static final String BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TT = "BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TT";
    public static final String BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TS = "BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TS";

    // chuyen doi infratype qua cong nghe
    public static final String IN_FRATYPE_CCN = "CCN";// cong nghe cap dong =>1
    public static final String IN_FRATYPE_FCN = "FCN";// cong nghe cap quang =>3
    public static final String IN_FRATYPE_CATV = "CATV";// cong nghe cap dong truc, truyen hinh cap =>2
    // truc, truyen hinh cap
    // =>2
    public static final String IN_FRATYPE_GPON = "GPON";// cong nghe bang rong co dinh gpon =>4
    // co dinh gpon =>4
    public static final String IN_FRATYPE_CCN_CM = "1";//
    public static final String IN_FRATYPE_FCN_CM = "3";//
    public static final String IN_FRATYPE_CATV_CM = "2";//
    public static final String IN_FRATYPE_GPON_CM = "4";//

    public interface MENU_FUNCTIONS {
        String UPDATE_ISDN_EMAIL = "UPDATE_ISDN_EMAIL";
        String LOOKUP_DEBIT_INFO = "LOOKUP_DEBIT_INFO";
        String LOOKUP_PROFILE_TRANSACTION = "LOOKUP_PROFILE_TRANSACTION"; //Tra cuu ho so
        String DO_WARRANTY = "DO_WARRANTY"; //Bao hanh

        String SALE_SALING = "SALE_SALING";
        String SALE_DEPOSIT = "SALE_DEPOSIT";
        String SALE_BHLD = "SALE_BHLD";
        String SALE_RETAIL = "SALE_RETAIL";
        String CONFIRM_NOTE = "CONFIRM_NOTE";
        String CREATE_INVOICE = "CREATE_INVOICE";
        String VIEW_INFO_ORDER_ITEM = "VIEW_INFO_ORDER_ITEM";
        String SALE_BY_ORDER = "SALE_BY_ORDER";
        String APPROVE_ORDER = "APPROVE_ORDER";
        String SALE_CREATE_CHANEL = "SALE_CREATE_CHANEL";
        String SALE_ACTIVE_ACCOUNT_PAYMENT = "SALE_ACTIVE_ACCOUNT_PAYMENT";
        String SALE_INFO_SEARCH = "SALE_INFO_SEARCH";
        String RETURN_THE_GOOD = "RETURN_THE_GOOD";
        String SALE_TO_CUSTOMER = "SALE_TO_CUSTOMER";
        String SALE_SEARCH_ISDN = "SALE_SEARCH_ISDN";
        String SALE_2G_TO_3G = "SALE_2G_TO_3G";
        String RECHARGE_TO_BANK = "RECHARGE_TO_BANK";
        String APPROVE_MONEY = "APPROVE_MONEY";

        String CHANGE_PASS_ANYPAY = "CHANGE_PASS_ANYPAY";
        String SEARCH_ANYPAY = "SEARCH_ANYPAY";
        String SALE_SEARCH_SERIAL_CARD = "SALE_SEARCH_SERIAL_CARD";
        String SALE_SEARCH_KIT = "SALE_SEARCH_KIT";
        String SALE_ANYPAY_ISDN = "SALE_ANYPAY_ISDN";
        String SALE_ANYPAY_EXCHANGE = "SALE_ANYPAY_EXCHANGE";
        String VIEW_STOCK = "VIEW_STOCK";
        String CHANNEL_ORDER = "CHANNEL_ORDER";
        String STAFF_HANDLE_ORDER = "STAFF_HANDLE_ORDER";
        String SALE_PROMOTION = "SALE_PROMOTION";
        String CHANNEL_CARE = "CHANNEL_CARE";
        String UPDATE_GIFT = "UPDATE_GIFT";
        String RECEIVE_COMPLAIN_CUSTOMNER = "RECEIVE_COMPLAIN_CUSTOMNER";
        String CHANGE_PROMOTION_BCCS = "CHANGE_PROMOTION_BCCS";
        String CHANGE_PRODUCT_BCCS = "CHANGE_PRODUCT_BCCS";
        String CARE_LOST_SUB = "CARE_LOST_SUB";
        String COLLECT_BUSSINESS = "COLLECT_BUSSINESS";
        String TICK_APPOINT = "TICK_APPOINT";
        String REGISTER_VAS_SAFENET_BCCS = "REGISTER_VAS_SAFENET_BCCS";
        String REVENUE_STATION = "REVENUE_STATION";
        String CUSTOMER_MANAGER = "CUSTOMER_MANAGER";
        String REPORT_REGISTER = "REPORT_REGISTER";
    }

    public static final String FUNCTION = "FUNCTION";

    public interface PAR_NAME {
        String TASK_CSKHLN = "TASK_CSKHLN"; // cong viec
        // cham soc
        // khach hang
        // lau nam
        String STATUS_CSKHLN = "STATUS_CSKHLN"; // trang
        // thai cham
        // soc khach
        // hang lau
        // nam
        String UPDATE_ACCOUNT_INFO = "UPDATE_ACCOUNT_INFO";
        String COLLECT_CUS_INFO = "COLLECT_CUS_INFO"; //loai khach hang khao sat
        // khach
        // hang
        // khao
        // sat
    }

    public static final String STATUS_HISTORY_SCHEDULE = "9";
    public static final String CNCCNEW = "CNCCNEW";
    public static final String CNCCDC = "CNCCDC";
    public static final String CNCCDCHT = "CNCCDCHT";
    public static final String INSERTNEWCC = "INSERTNEWCC";

    public static final String CHANGE_PRE_TO_POS = "CHANGE_PRE_TO_POS";
    public static final String CHANGE_POS_TO_PRE = "CHANGE_POS_TO_PRE";
    public static final String BLOCK_OPEN_SUB_MOBILE = "BLOCK_OPEN_SUB_MOBILE"; //chan mo thue bao di dong
    public static final String BLOCK_OPEN_SUB_HOMEPHONE = "BLOCK_OPEN_SUB_HOMEPHONE"; //chan mo thue bao co dinh
    public static final String LIQUIDATION_RECOVERY = "LIQUIDATION_RECOVERY"; //chan mo thue bao co dinh

    public static final String KEY_USER = "KEYYYYYYY";
    public static final String KEY_FINGER_USER = "KEYYYYYYY";
    public static final String KEY_PASS = "VALUEEEEEEEE";
    public static final String KEY_IV = "DESCRIPTIONNNNNNNNNN";

    public interface WARRANTY_TYPE {
        int WARRANTY_RECEIVE_OR_REPAIR = 1;
        int WARRANTY_RETURN = 2;
        int WARRANTY_LOOKUP = 3;
    }

    public interface ACTION_TYPE {
        String SEARCH_COLLECT_CUS_INFO = "0";
        String ADD_COLLECT_CUS_INFO = "1";
        String EDIT_COLLECT_CUS_INFO = "2";
        String DELETE_COLLECT_CUS_INFO = "3";
    }

    public interface WARRANTY_SATUS {
        String WARRANTY = "1";
        String REPAIR = "9";
    }

    public static final String INSERT_CORP = "INSERT_CORP";
    public static final String UPDATE_CORP = "UPDATE_CORP";
    public static final String ACCEPT_CORP = "ACCEPT_CORP";
    public static final String REFUSE_CORP = "REFUSE_CORP";

    public static final String PATH_BUSSNESS = "/SERVICE_INFOR/";

    public static String map_new = "viettelmaps.vn";
    public static String map_old = "viettelmap.vn";


    public static final String SUB_FILE_NAME = "subManualFile";
    public static final String NEW_CONTRACT = "NEW_CONTRACT";
    public static boolean LOG = true;
    public static String DB_NAME = "SUGGESTION";

    public static final String VAS_SAFE_NET = "VAS_SAFE_NET";
    public static final String SECEREC_REQUEST = "MBCCS1";

    // cau hinh so luong cho dich vu


    // cau hinh so luong cho dich vu
    public static final String LST_SERVICE_TYPE_QUANTITY = "A;P;E;F";

    // khai bao parse promotion
    public static String WS_PROMOTION_DATA = URL_SERVICE_DOWNLOAD
            + "mBCCSService/BCCS2ProductWS?wsdl";
    public static final String PROMOTION_FILE_NAME = "promotionManualFile";
    public static final int TIMEOUT_SYNC_DATA_CONNECT = 60000;
    public static final int TIMEOUT_SYNC_DATA_RESPONSE = 5 * 60000;

    // khai bao trang thai tim kiem yeu cau
    public static final String STATUS_NEW_SERVEY_ONLINE_OK = "12";  // Tao yeu cau - Bo qua khao sat
    public static final String STATUS_CONNECT_TASK = "33";          // Dau noi - Trien khai
    public static final String STATUS_CONNECT_TASK_OK = "36";       // Dau noi - Dang trien khai
    public static final String STATUS_ACTIVE = "48";                // Hoan thanh cong viec
    public static final String HYC_BQKS = "02";                // Huy yeu cau - Bo qua khao sat

    public static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    public static interface BANK_PLUS_CMD {
        public static final String TRANSFER_MONEY = "TRANSFER_MONEY";
        public static final String DELIVER_MONEY = "DELIVER_MONEY";
    }

    public static interface BANKPLUS_FUNCTION {
        public static final String BANKPLUS_TRANSFER_MONEY = "BANKPLUS_TRANSFER_MONEY";
        public static final String BANKPLUS_DELIVER_MONEY = "BANKPLUS_DELIVER_MONEY";
        public static final String BANKPLUS_PAYMENT_WATER = "BANKPLUS_PAYMENT_WATER";
        public static final String BANKPLUS_PAYMENT_ELECTRIC = "BANKPLUS_PAYMENT_ELECTRIC";
        public static final String BANKPLUS_PAYMENT_TELEVISION = "BANKPLUS_PAYMENT_TELEVISION";
        public static final String BANKPLUS_PAYMENT_K_PLUS = "BANKPLUS_PAYMENT_K_PLUS";
        public static final String BANKPLUS_PAYMENT_FINANCE = "BANKPLUS_PAYMENT_FINANCE";
        public static final String BANKPLUS_TRANSACTION_HISTORY = "BANKPLUS_TRANSACTION_HISTORY";
        public static final String BANKPLUS_DEPOSIT_TO_BANK = "BANKPLUS_DEPOSIT_TO_BANK";
        public static final String BANKPLUS_WORK_MANAGER = "BANKPLUS_WORK_MANAGER";

        public static final String BANKPLUS_MENU_PAYMENT_INVOICE = "BANKPLUS_MENU_PAYMENT_INVOICE";
        public static final String BANKPLUS_MENU_TRANSACTION_MONEY = "BANKPLUS_MENU_TRANSACTION_MONEY";
        public static final String BANKPLUS_MENU_BUY_GAME_CARD = "BANKPLUS_MENU_BUY_GAME_CARD";
        public static final String BANKPLUS_MENU_BUY_KASPERSKY_CARD = "BANKPLUS_MENU_BUY_KASPERSKY_CARD";
        public static final String BANKPLUS_MENU_UTILITIES = "BANKPLUS_MENU_UTILITIES";

        public static final String BANKPLUS_RESET_PIN = "BANKPLUS_RESET_PIN";
        public static final String BANKPLUS_CHANGE_PIN = "BANKPLUS_CHANGE_PIN";
        public static final String BANKPLUS_RESEND_ID_CARD = "BANKPLUS_RESEND_ID_CARD";
        public static final String BANKPLUS_TELECOMMUNICATION = "BANKPLUS_TELECOMMUNICATION";
        public static final String BANKPLUS_VIEW_HIS_TRANS = "BANKPLUS_VIEW_HIS_TRANS";
    }

    public static final String STANDARD_CONNECT_CHAR = "|";
    public static final String SHARED_PREF_KEYPAIR = "keyPairPref";
    public static final int PRIVATE = 0;
    public static final String SHARED_PRIVATEKEY = "privateKey";
    public static final String SHARED_PUBLICKEY = "publicKey";
    public static final String INVALID_STRING = null;
    public static final String MBCCS_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC6w26T3AlVPHcx5un27337fX4zyjgywVGrq8DKKv298hhm6ByoFqZzJEF+ELBnKI6d29CSlpN4N7WAUVxjB6iUKx7M37QAxvXXyacYL97bXa9a/VdxPw7M/55ePc/C5nf3n6HeQ03OaKhNIbV+ISOXJjQwWDg6dGP6NKBDcvwQwIDAQAB";

    public static interface ACTION_CODE {
        public static final String REGISTER_INFORMATION = "04"; //dang ky thong tin
        public static final String UPDATE_CUSTOMER = "152"; //cap nhat thong tin khach hang
        public static final String CONNECT_MOBILE = "00"; //tac dong dau noi
    }

    public static interface PROFILE_STATUS {
        public static final String HS_GIA_MAO = "7";
        public static final String HS_SCAN_SAI = "8";
        public static final String HS_SAI_THONG_TIN = "9";
        public static final String HS_SCAN_TIEPNHAN_CHUAKT = "1";
        public static final String HS_CHUA_SCAN = "2";
        public static final String HS_SCAN_THIEU = "3";
        public static final String HS_CHO_KIEMDUYET = "11";
    }

    public static interface GROUP_TYPE {
        public static final String KH_DN = "2";
    }

    public static interface ACTION_CODE_BLOCK_OPEN_SUB {
        public static final String BLOCK_ONE_WAY = "06";
        public static final String BLOCK_TWO_WAY = "07";
        public static final String OPEN_ONE_WAY = "08";
        public static final String OPEN_TWO_WAY = "09";
    }

    public static interface BLOCK_MODE_BOSUB {
        public static final String BLOCK_ONE_WAY = "1";
        public static final String BLOCK_TWO_WAY = "2";
        public static final String OPEN = "0";
    }

    public static final String POSTPAID = "1";
    public static final String PREPAID = "2";


    public static interface RESPONSE_CODE {
        public static final String SUCCESS = "0";
        public static final String ERROR = "1";
    }

    public static final int LOADMORE_MAX_ROW = 10;
    public static final int LOAD_KPI_ID = 10;
    public static final int LOAD_ALARM_ID = 11;
    public static final int LOAD_UPLOAD_ALARM_ID = 12;
    public static final int LOAD_CAUSE_ID = 13;
    public static final int LOAD_WORK_LIST_ID = 14;
    public static final int LOAD_WORK_UPDATE_ID = 15;
    public static final String PRODUCT_LYDOCVT = "PRODUCT_LYDOCVT";
    public static final String PRODUCT_LYDOSAITT = "REPAIR_SUB_INFO";

    public static interface PROFILE {
        public static final String PYCTT = "PYCTT";
        public static final String CMTNDMS = "CMNDMS";
        public static final String CMTNDMT = "CMNDMT";
        public static final String HDMBTT = "HDMBTT";
        public static final String PYCTS = "PYCTS";
        public static final String HD = "HD";
        public static final String PLHD = "PLHD";
        public static final String PYCTDDVVT = "PYCTDDVVT";
        public static final String PLBCK = "PLBCK";
        public static final String CHUKY = "CHUKY";
        public static final String CHUKY2 = "CHUKY2";
    }

    public static interface LOCATION_REQUEST_CODE {
        public static final int PROVINCE_CODE = 115;
        public static final int DISTRICT_CODE = 116;
        public static final int PRECINCT_CODE = 117;

    }

    public static interface LOCATION_CHECK_KEY {
        public static final String PROVINCE_KEY = "1";
        public static final String DISTRICT_KEY = "2";
        public static final String PRECINCT_KEY = "3";
    }

    public static interface BOC2 {
        public static final int STATUS_CARING = 0; //Trang thai cham soc kenh
        public static final int STATUS_CARE_NOT_SALE = 1; //Cham soc kenh khong ban hang
        public static final int STATUS_CARE_AND_SALE = 2; //Cham soc kenh va ban hang
        public static final int STATUS_POS_DOOR_CLOSE = 3; //Diem ban dong cua
    }
}
