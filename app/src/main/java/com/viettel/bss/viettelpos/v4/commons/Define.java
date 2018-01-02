/*
 * Copyright (C) 2014 TinhVan Outsourcing.
 */
package com.viettel.bss.viettelpos.v4.commons;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;

/**
 * 
 * @author Chinh Nguyen Quang
 * 
 */
public interface Define {

	// public static final String DATABASE_CREATE =
	// "create table tasks (_id integer primary key autoincrement, "
	// +
	// "name text not null, content text not null, start_time not null, end_time not null);";
	// public static final String DROP_TABLE = "DROP TABLE IF EXISTS tasks";
    String TABLE_NAME_STAFF = "staff";
	String TABLE_NAME_TASK_ROAD = "TASK_ROAD";
	String KEY_OJ_URI = "key_uri";
	String KEY_VER_CUSTOMER = "key_ver_cus";
	String KEY_STAFF = "key_staff";
	String ARG_KEY = "key";
	String KEY_TASK = "key_TASK";
	String KEY_CRITERIA = "key_criteria";
	String KEY_CRITERIA_CHILD = "KEY_CRITERIA_CHILD";
	String KEY_AREA = "KEY_AREA";
	String KEY_JOB = "KEY_JOB";
	String KEY_COLLECTED_INFO = "KEY_COLLECTED_INFO";
	String GET_VALUE = "GET_VALUE";
	String KEY_SALE_TRANS_DETAIL = "key_staff_sale_trans_detail";
	String KEY_LIST_STAFF = "key_staff_list";
	String DB_NAME = "smartphone";
	String PATH_DATABASE = "//data//data//com.viettel.bss.viettelpos.v4//databases//";
	// public static final String PATH_DATABASE = Constant.MBCCS_TEMP_FOLDER;
    int COUNTLOAD = 10;
	String X_KEY = "MBCCS_X_LOCATION";
	String Y_KEY = "MBCCS_Y_LOCATION";
	String X_KEY_AGPS = "MBCCS_X_LOCATION_AGPS";
	String Y_KEY_AGPS = "MBCCS_Y_LOCATION_AGPS";
	// public static final String PATH_OUTPUT_INSTALL = Environment
	// .getExternalStorageDirectory() + "/download/";
    String PATH_OUTPUT_INSTALL = "//data//data//com.viettel.bss.viettelpos.v4//version//";
	String TABLE_NAME_MAX_ORA_ROWSCN = "max_ora_rowscn";
	String KEY_WORKOBJECT = "KEY_WORKOBJECT";
	String arrStatus[] = new String[] {
			LoginActivity.getInstance().getString(R.string.bthuong),
			LoginActivity.getInstance().getString(R.string.tamngung) };

	class MAXORAROWSCN {
		public static final String Field_max_ora_rowscn = "max_ora_rowscn";
		public static final String FIELD_TABLE_NAME = "table_name";
		public static final String FIELD_STATUS = "sync_status";

	}

	String collected_object_info = "collected_object_info";
	String object_detail_group = "object_detail_group";

	class CollectedObjectInfoF {
		public static final String ID = "id";
		public static final String PARENT_ID = "parent_id";
		public static final String GROUP_ID = "group_id";
		public static final String CREATE_TIME = "create_time";
		public static final String UPDATE_TIME = "update_time";
		public static final String CREATE_USER = "create_user";
		public static final String VALUE = "value";
		public static final String REPORT_CIRCLE = "report_circle";
		public static final String AREA_CODE = "area_code";
		public static final String STATUS = "status";
		public static final String YEAR_CIRCLE = "year_circle";
	}

	class STAFF {
		public static final String KEY_ID = "staff_id";
		public static final String KEY_STAFF_OWNER_ID = "staff_owner_id";
		public static final String KEY_CHANNEL_TYPE_ID = "channel_type_id";
		public static final String KEY_NAME = "name";
		public static final String KEY_ADDRESS = "address";
		public static final String KEY_ID_USER_NO = "id_no";
		public static final String KEY_ID_USER_NO_DATE = "id_issue_date";
		public static final String KEY_ID_USER_BIRTHDAY = "birthday";
		public static final String KEY_CODE = "staff_code";
		public static final String KEY_LOCATION_X = "x";
		public static final String KEY_LOCATION_Y = "y";

		public static final String OWNER_TYPE = "";
		public static final String TEL = "tel";
		public static final String ISDN_AGENT = "isdn_agent";
		// Thuytv3_start
		public static final String BUSINESS_METHOD = "business_method";
		public static final String POINT_OF_SALE_TYPE = "point_of_sale_type";
		public static final String CONTRACT_METHOD = "contract_method";
		public static final String PRECINCT = "precinct";
		public static final String DISTRICT = "district";
		public static final String PROVINCE = "province";
		public static final String STREET = "street";
		public static final String STREETBLOCK = "street_block";
		public static final String HOME = "home";
		public static final String ID_ISSUE_PLACE = "id_issue_place";		
		// Thuytv3_end
	}

	class ObjectDetailGroupDF {
		public static final String CODE = "code";
		public static final String NAME = "name";
		public static final String ISKEY = "is_key";
		public static final String PARENTID = "parent_id";
		public static final String HAVECHILDREND = "have_children";
		public static final String ID = "id";
		public static final String VALUETYPE = "value_type";
		public static final String HAVE_O_INFO = "have_collected_object_info";
		public static final String METHOD_TYPE = "method_type";
		public static final String PATH = "path";
		public static final String TREE_LEVEL = "tree_level";
		public static final String STATUS = "status";
		public static final String ITEM_ORDER = "item_order";
	}

	class CHANNEL_TYPE {
		public static final String KEY_ID = "channel_type_id";
		public static final String KEY_NAME = "name";
	}

	class CONTENT_CARE {
		public static final String KEY_ID = "care_content_id";
		public static final String KEY_CONTENT = "content";
		public static final String KEY_DATE = "create_date";
	}

	class VISIT {
		public static final String KEY_ID = "visit_id";
		public static final String KEY_CONTENT = "content";
		public static final String DATE = "arrival_time";
	}

	class SALE_TRANS {
		public static final String KEY_ID = "sale_trans_id";
		public static final String KEY_DATE = "sale_trans_date";
		public static final String KEY_AMOUT_TAX = "amount_tax";
	}

	class SALE_TRANS_DETAIL {
		public static final String KEY_ID = "sale_trans_detail_id";
		public static final String KEY_DATE = "sale_trans_date";
		public static final String KEY_AMOUT = "amount";
		public static final String KEY_QUANTITY = "quantity";
		public static final String KEY_STOCK_MODEL_NAME = "stock_model_name";
		public static final String KEY_SALE_ID = "sale_trans_id";

	}

	class OBJ_CAT {
		public static final String KEY_OBJ_CATE_ID = "id";
		public static final String KEY_OBJ_CATE_CODE = "code";
		public static final String KEY_OBJ_TYPE = "type";
		public static final String KEY_OBJ_NAME = "name";
		public static final String KEY_OBJ_VALUE = "value";
		public static final String KEY_OBJ_NOTE = "note";

	}

	class ASSET_DETAIL_HISTORY {
		public static final String ASEST_SHOP_KEY_ID = "shop_id";
		public static final String ASSET_ID = "asset_id";
		public static final String ASSET_CODE = "asset_code";
		public static final String ASSET_QTY = "qty";
		public static final String ASSET_STATUS = "status";
		public static final String ASSET_NOTE = "note";
		public static final String ASSET_ISSUE_DATE = "issue_date";
		public static final String ASSET_CHANNEL_TYPE_ID = "channel_type_id";

	}

	String MENU_SALE_ORDER_INFO = "menu.sale.order.info";
	String MENU_SALE_ASSIGNMENT = "menu.sale.assignment";

	String MENU_SALE_POLICY_INFO = "menu.sale.policy.info";
	String MENU_SALE_JOB_INFO = "menu.sale.job.info";
	String MENU_SALE_JOB_INFO_MANAGER = "menu.sale.job.infomanager";
	String MENU_SALE_SALING = "menu.sale.saling";
	String MENU_SALE_DESPOSIT = "menu.sale.desposit";
	String MENU_SALE_CREATE_INVOICE = "menu.sale.create.invoice";
	String MENU_SALE_ORDER = "menu.sale.order";
	String MENU_SALE_PROGRAM = "menu.sale.program";
	String MENU_SALE_MANAGEMENT = "menu.sale.management";
	String MENU_SALE_RETAIL = "menu.sale.retail";
	String PRE_NAME = "smartphone_preferences";
	String KEY_MENU_NAME = "savemenu";
	String KEY_TRACKING = "mBCCS_tracking";
	String MENU_CHANNEL_CUSTOMER = "menu.channel.customer";
	String MENU_CHANNEL_SALE = "menu.channel.sale";
	String MENU_CHANNEL_CHARGEABLE = "menu.channel.chargeable";
	String MENU_CHANNEL_MANAGEMENT = "menu.channel.management";
	String MENU_CHANNEL_INFRASTRUCTURE = "menu.channel.infrastructure";
	String MENU_CHANNEL_JOB = "menu.channel.job";
	String MENU_CHANNEL_SYSTEM = "menu.channel.system";
	String MENU_CHANNEL_APPROVEORDER = "menu.channel.approveOrder";
	String MENU_CHANNEL_REPORT = "menu.channel.report";
	String MENU_CONTRACT_MANAGER = "menu.contract.manager";
	String TABLE_NAME_TASK = "TASK";
	String TABLE_NAME_SERVICE = "telecom_service";
	String KEY_LOGIN_NAME = "login_name";
	String ASSIGN_JOB = "ASSIGN_JOB";
	String VIEW_ROUTER = "VIEW_ROUTER";
	String KEY_TEMP = "temp";

	class CHANNEL_UPDATE_IMAGE {
		public static final int PICK_FROM_CAMERA = 1;
		public static final int PICK_FROM_GALLERY = 2;
		public static final int PICK_IMAGE = 15345;
		public static final int CROP_PIC = 4;
		public static final int PICK_IMAGE_KH = 15262;
	}

	String KEY_LAST_VERSION = "KEY_LAST_VERSION";
	String KEY_SURVEY_NAME = "KEY_SURVEY_NAME";
	String KEY_SURVEY_ID = "KEY_SURVEY_NAME";
	String KEY_SURVEY_DESCRIPTION = "KEY_SURVEY_NAME";
	String KEY_SURVEY_REQUIRED = "KEY_SURVEY_NAME";
	String KEY_SURVEY_DATE = "KEY_SURVEY_DATE";
	String KEY_INVALID_TOKEN = "KEY_INVALID_TOKEN";
	String KEY_NOTICE_SERVICE = "savenotice";
	String NOTIFY = "NOTIFY";
	String MARK_SELLING_CONTACT_HIS_ID = "MARK_SELLING_CONTACT_HIS_ID";

	public static final String us = "d75c507cf9d4a80d5be1924a3d0c790fbd5b9aa53d8c5f5066d0d8f632bcb26814fc22840211ab1ed617be90f58390ef";
	public static final String ps = "7e406d34b24a4a7c8439c5b3105b014e839c7c5f183bbc1a79a9f2e5b27a400114fc22840211ab1ed617be90f58390ef";

	String PLAY_VIDEO = "play.video";
}
