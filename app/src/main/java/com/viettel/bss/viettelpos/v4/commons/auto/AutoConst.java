package com.viettel.bss.viettelpos.v4.commons.auto;

/**
 * Created by diepdc-pc on 7/4/2017.
 */

public interface AutoConst {
    int MAX_TD = 10; // template thường dùng
    int MAX_LS = 20; // template bình thường
    int MAX_TEXT_SUGGESTION = 5; // đối với Editext, hiển thị tối da 5 suggestion
    int MAX_SAVE_TIME = 30; // thời gian lưu 30 ngày. sau 30 ngày clear sạch 1 lần
    int RESULT_TEM_CODE = 19888;
    String FROM_SCREEN = "FROM_SCREEN";
    String ITEM_CALLBACK = "ItemClickCallback";

    String EXPIRATION_DATE = "expiration_date";

    String AUTO_SPLIT_KEY = "、"; // jp char
    String AUTO_CONTENT_KEY = "："; // jp char

    String AUTO_CHOOSE_REASON = "AUTO_CHOOSE_REASON";
    String AUTO_REG_NEW_REASON = "AUTO_REG_NEW_REASON";
    String AUTO_CHANGE_SIM = "AUTO_CHANGE_SIM";
    String AUTO_CATEGORY_TB = "AUTO_CATEGORY_TB";
    String AUTO_CDT_SEARCH = "AUTO_CDT_SEARCH";
    String AUTO_CODE_PROMOTION_SEARCH = "AUTO_CODE_PROMOTION_SEARCH";
    String AUTO_PAKAGE_SEARCH = "AUTO_PAKAGE_SEARCH";
    String AUTO_STOCK_SEARCH = "AUTO_STOCK_SEARCH";
    String AUTO_CODE_HTHM_SEARCH = "AUTO_CODE_HTHM_SEARCH";
    String AUTO_NUMBER_PAPER = "AUTO_NUMBER_PAPER"; // số giấy tờ
    String AUTO_NUMBER_SUB = "AUTO_NUMBER_SUB"; // số thuê bao
    String AUTO_ISDN = "AUTO_ISDN";
    String AUTO_PHONE_NUMBER = "AUTO_PHONE_NUMBER";

    //screen
    String PREF_MOBILE_NEW_SCREEN = "PREF_MOBILE_NEW_SCREEN";
    String PREF_DAU_NOI_CO_DINH_MOI_SCREEN = "PREF_DAU_NOI_CO_DINH_MOI_SCREEN";

    String MAP_KEY = "MAP_KEY";
    String TEMPLATE_ID = "TEMPLATE_ID";
    String LOGIN_USER = "LOGIN_USER";
    String TYPE = "TMP_TYPE";
    int EVERYDAY = 0;
    int NORMAL = 1;
    String TIME_STAMP = "TIME_STAMP";

    // connection mobile new screen
    String SERVICE = "service";
    String PACKAGE = "packages";
    String LOAITB = "tbcategory";
    String HTHM = "hthm";
    String KM = "km";
    String CDT = "cdt";
}
