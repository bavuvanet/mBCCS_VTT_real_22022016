package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocationCD;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAddressCommon extends GPSTracker implements OnClickListener {

    public static final String PREFS_NAME = "ADDRESS_PRECINT";
	// private Spinner spProvincial;
	// private Spinner spDistrict;
	// private Spinner spPricent;
    private EditText edtprovince, edtdistrict, edtprecinct;

    private Spinner spGroup;
    private EditText edtFlow;
    private EditText edtHomeNumber;
    private ArrayList<AreaObj> mListTP = new ArrayList<>();
	private ArrayList<AreaObj> mListQuan = new ArrayList<>();
	private ArrayList<AreaObj> mListXa = new ArrayList<>();
	private ArrayList<AreaObj> mListGroup = new ArrayList<>();
    private InfrastrucureDB mInfrastrucureDB;
    private String strProvince;
    private String strDistris;
    private AreaObj areaGroup;
    private String areaStreet;
    private String areaHomeNumber;
    private String strStreetBlock = "";

    private AreaObj areaPrecint;
    private AreaObj areaProvicial;
    private AreaObj areaDistrist;

    private Button btnHome;
    private TextView txtNameActionBar;
    private long type; // 1: update, con lai la them moi
    private String key = "";

    private String province = "";
    private String district = "";
    private String precinct = "";
    private ProgressBar prbStreetBlock;
    private Button btnRefreshStreetBlock;
    private String strPrecinct;
    private String checkPCProduct = "";
    private Boolean isCheckStreetBlock = false;
    private Boolean isCheckStreet = true;

    private LinearLayout lnghichu;
    private EditText edtghichu;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private  boolean isCheckDes = false;
    private String description = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_codinh);

        lnghichu = (LinearLayout) findViewById(R.id.lnghichu);
        edtghichu = (EditText) findViewById(R.id.edtghichu);

        mInfrastrucureDB = new InfrastrucureDB(CreateAddressCommon.this);
        Intent intent = getIntent();
        Bundle mBundle = intent.getExtras();
        TextView lblto = (TextView) findViewById(R.id.lblTo);
        if (mBundle != null) {
            strProvince = mBundle.getString("strProvince", "");
            strDistris = mBundle.getString("strDistris", "");
            strPrecinct = mBundle.getString("strPrecint", "");
            strStreetBlock = mBundle.getString("strStreetBlock","");
            areaStreet = mBundle.getString("areaFlow");
            areaHomeNumber = mBundle.getString("areaHomeNumber", "");

            areaPrecint = (AreaObj) mBundle.getSerializable("areaPrecint");
            areaGroup = (AreaObj) mBundle.getSerializable("areaGroup");
            isCheckStreetBlock = mBundle.getBoolean("isCheckStreetBlock", false);
            if (isCheckStreetBlock) {
                findViewById(R.id.lnStreetBlock).setVisibility(View.VISIBLE);
                lblto.setText(Html.fromHtml(this.getString(R.string.tv_to_thon_red)));
            } else {
                lblto.setText(this.getString(R.string.choose_street_block));
            }
            isCheckStreet = mBundle.getBoolean("isCheckStreet", true);
            type = mBundle.getLong("TYPE");
            checkPCProduct = mBundle.getString("checkPCProduct", "");
            isCheckDes = mBundle.getBoolean("isCheckDes",false);
            if(isCheckDes){
                lnghichu.setVisibility(View.VISIBLE);
            }else{
                lnghichu.setVisibility(View.GONE);
            }
        }

        if (CommonActivity.isNullOrEmpty(strProvince)) {
            strProvince = Session.province;
        }
        if (CommonActivity.isNullOrEmpty(strDistris)) {
            strDistris = Session.district;
        }

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addListProvinceSpinner();

        prbStreetBlock = (ProgressBar) findViewById(R.id.prbStreetBlock);
        btnRefreshStreetBlock = (Button) findViewById(R.id.btnRefreshStreetBlock);
        prbStreetBlock.setVisibility(View.GONE);
        btnRefreshStreetBlock.setVisibility(View.GONE);
        edtprovince = (EditText) findViewById(R.id.edtprovince);
        if (!CommonActivity.isNullOrEmpty(strProvince)) {
            addListDistristSpinner(strProvince);
            try {
                GetAreaDal dal = new GetAreaDal(CreateAddressCommon.this);
                dal.open();
                String namePrv = dal.getNameProvince(strProvince);
                edtprovince.setText(namePrv);
                province = strProvince;
                areaProvicial = new AreaObj();
                areaProvicial.setName(namePrv);
                areaProvicial.setProvince(province);
                dal.close();
			} catch (Exception ignored) {
            }
        }
        edtprovince.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), FragmentSearchLocationCD.class);
                intent.putExtra("arrProvincesKey", mListTP);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "1");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 106);

            }
        });

        edtdistrict = (EditText) findViewById(R.id.edtdistrict);
        edtprecinct = (EditText) findViewById(R.id.edtprecinct);
        if (!CommonActivity.isNullOrEmpty(strProvince) && !CommonActivity.isNullOrEmpty(strDistris)) {
            addListPrecintSpinner(strProvince + strDistris);
            try {
                GetAreaDal dal = new GetAreaDal(CreateAddressCommon.this);
                dal.open();
                String distictName = dal.getNameDistrict(strProvince, strDistris);
                edtdistrict.setText(distictName);
                district = strDistris;
                areaDistrist = new AreaObj();
                areaDistrist.setDistrict(district);
                areaDistrist.setName(distictName);
                dal.close();
            } catch (Exception e) {
            }
        }
        if (!CommonActivity.isNullOrEmpty(strProvince) && !CommonActivity.isNullOrEmpty(strDistris) && !CommonActivity.isNullOrEmpty(strPrecinct)) {
            addListGroupSpinner(strProvince + strDistris + strPrecinct);
            try {
                GetAreaDal dal = new GetAreaDal(CreateAddressCommon.this);
                dal.open();
                String precinctName = dal.getNamePrecint(strProvince,strDistris,strPrecinct);
                edtprecinct.setText(precinctName);
                precinct = strPrecinct;
                areaPrecint = new AreaObj();
                areaPrecint.setPrecinct(precinct);
                areaPrecint.setName(precinctName);
                dal.close();
			} catch (Exception ignored) {
            }
        }

        edtdistrict.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), FragmentSearchLocationCD.class);
                intent.putExtra("arrDistrictKey", mListQuan);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "2");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 107);

            }
        });

        edtprecinct.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(getApplicationContext(), FragmentSearchLocationCD.class);
                intent.putExtra("arrPrecinctKey", mListXa);
                Bundle mBundle = new Bundle();
                mBundle.putString("checkKey", "3");
                intent.putExtras(mBundle);
                startActivityForResult(intent, 108);

            }
        });
        spGroup = (Spinner) findViewById(R.id.sqGroupAddress);
        edtFlow = (EditText) findViewById(R.id.edtFlowAddress);
        edtHomeNumber = (EditText) findViewById(R.id.edtHomeNumber);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        Button btnOk = (Button) findViewById(R.id.btn_ok);

        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                doGetAddress();
            }
        });

        if (areaStreet != null && areaStreet.length() > 0) {
            edtFlow.setText(areaStreet);
        }

        if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
            edtHomeNumber.setText(areaHomeNumber);
        }
        btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                new CacheDatabaseManager(MainActivity.getInstance()).insertCacheStreetBlock(null,
                        province + district + precinct);
                addListGroupSpinner(province + district + precinct);

            }
        });

    }

    private void addListProvinceSpinner() {
        mListTP = mInfrastrucureDB.getLisProvince();
		// AdapterProvinceSpinner adapterTp = new
		// AdapterProvinceSpinner(mListTP, CreateAddressCommon.this);
		// spProvincial.setAdapter(adapterTp);
		// if(!CommonActivity.isNullOrEmpty(strProvince)){
		// for (int i = 0; i < mListTP.size(); i++) {
		// AreaObj areaObject = mListTP.get(i);
		// if (areaObject.getProvince() != null &&
		// areaObject.getProvince().equals(strProvince)) {
		// spProvincial.setSelection(i);
		// }
		// }
		// }
		// spProvincial.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1,
		// int arg2, long arg3) {
		// strProvince = mListTP.get(arg2).getProvince();
		// addListDistristSpinner(strProvince);
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });
    }

    private void addListDistristSpinner(final String province) {

        Log.d("Log", "Distris code:" + province);

        mListQuan = mInfrastrucureDB.getLisDistrict(province);
		// AdapterProvinceSpinner adapterDistrist = new
		// AdapterProvinceSpinner(mListQuan, CreateAddressCommon.this);
		// spDistrict.setAdapter(adapterDistrist);
		// if(!CommonActivity.isNullOrEmpty(strDistris)){
		// for (int i = 0; i < mListQuan.size(); i++) {
		// AreaObj areaObject = mListQuan.get(i);
		// if (areaObject.getDistrict() != null &&
		// areaObject.getDistrict().equals(strDistris)) {
		// spDistrict.setSelection(i);
		// spDistrict.setEnabled(false);
		// String parentCodePrecinct = province + strDistris;
		// addListPrecintSpinner(parentCodePrecinct);
		// }
		// }
    }

	// spDistrict.setOnItemSelectedListener(new OnItemSelectedListener() {
	//
	// @Override
	// public void onItemSelected(AdapterView<?> arg0, View arg1,
	// int arg2, long arg3) {
	// strDistris = mListQuan.get(arg2).getDistrict();
	// String parentCodePrecinct = province + strDistris;
	// addListPrecintSpinner(parentCodePrecinct);
	//
	// }
	// @Override
	// public void onNothingSelected(AdapterView<?> arg0) {
	//
	// }
	// });
	// }
    private void addListPrecintSpinner(String parentCode) {
        mListXa = mInfrastrucureDB.getListPrecinct(parentCode);
		// AreaObj areaObject = new AreaObj();
		// areaObject.setName(getResources().getString(R.string.tv_select_precint));
		// areaObject.setPrecinct("-1111");
		// mListXa.add(0, areaObject);
		// AdapterProvinceSpinner adapterPricinct = new
		// AdapterProvinceSpinner(mListXa, CreateAddressCommon.this);
		// spPricent.setAdapter(adapterPricinct);
		//
		// if(type == 1){
		// if (areaPrecint != null) {
		// for (int i = 0; i < mListXa.size(); i++) {
		// AreaObj areaPrecintObject = mListXa.get(i);
		// if(areaPrecintObject.getPrecinct() != null &&
		// areaPrecint.getPrecinct() != null){
		// if
		// (areaPrecintObject.getPrecinct().equals(areaPrecint.getPrecinct())) {
		// spPricent.setSelection(i);
		// }
		// }
		// }
		// }
		// }else {
		// if (areaPrecint != null) {
		// for (int i = 0; i < mListXa.size(); i++) {
		// AreaObj areaPrecintObject = mListXa.get(i);
		// if (areaPrecintObject.getName().equals(areaPrecint.getName())) {
		// spPricent.setSelection(i);
		// }
		// }
		// }
		// }
		// spPricent.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// if (arg2 != 0) {
		// AreaObj areaObjectPrecint = mListXa.get(arg2);
		// String parentCodeGroup = strProvince + strDistris +
		// areaObjectPrecint.getPrecinct();
		// addListGroupSpinner(parentCodeGroup);
		// }
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> arg0) {
		//
		// }
		// });
    }

    private void addListGroupSpinner(String parentCode) {
        GetListGroupAdressAsyncTask getlistGroupAsynctask = new GetListGroupAdressAsyncTask(CreateAddressCommon.this);
        SharedPreferences sharedPref = CreateAddressCommon.this.getPreferences(Context.MODE_PRIVATE);
        String original = sharedPref.getString(parentCode, null);
        Log.d("addListGroupSpinner", "check original save sharePref: " + original);
        if (original != null && original.length() > 0) {
            Log.d("addListGroupSpinner", "check original get  frome sharePref: ");

            ArrayList<AreaObj> result = getlistGroupAsynctask.parserListGroup(original);
            getlistGroupAsynctask.updatePrecintSpinner(result);
        } else {
            Log.d("addListGroupSpinner", "check original get  frome Internet");
            getlistGroupAsynctask.execute(parentCode);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(getResources().getString(R.string.createAddress));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 106:
                if (data != null) {
                    areaProvicial = (AreaObj) data.getExtras().getSerializable("provinceKey");

                    province = areaProvicial.getProvince();
                    addListDistristSpinner(province);
                    edtprovince.setText(areaProvicial.getName());
                    edtdistrict.setText("");
                    edtprecinct.setText("");
                    district = "";
                    precinct = "";
                    areaDistrist = null;
                    areaPrecint = null;
                }
                break;
            case 107:
                if (data != null) {
                    areaDistrist = (AreaObj) data.getExtras().getSerializable("districtKey");
                    if (areaDistrist != null) {
                        district = areaDistrist.getDistrict();
                        addListPrecintSpinner(province + district);
                        edtdistrict.setText(areaDistrist.getName());
                    }

                    edtprecinct.setText("");
                    precinct = "";
                    areaPrecint = null;
                }
                break;

            case 108:
                if (data != null) {
                    areaPrecint = (AreaObj) data.getExtras().getSerializable("precinctKey");
                    if (areaPrecint != null) {
                        precinct = areaPrecint.getPrecinct();

                        edtprecinct.setText(areaPrecint.getName());
                    }
//                    if (isCheckStreetBlock) {
			        	mListGroup = new ArrayList<>();
                        if (!CommonActivity.isNullOrEmpty(province) && !CommonActivity.isNullOrEmpty(district)
                                && !CommonActivity.isNullOrEmpty(precinct)) {
                            addListGroupSpinner(province + district + precinct);
                        }
//                    }
                    // else {
				// addListGroupSpinner("");
				// }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaBackHome:
                CreateAddressCommon.this.onBackPressed();
                break;
            default:
                break;
        }
    }

    private void doGetAddress() {
		// int indexProvincial = spProvincial.getSelectedItemPosition();
		// int indexDistrict = spDistrict.getSelectedItemPosition();
		// int indexPrecint = spPricent.getSelectedItemPosition();
        int indexGroup = spGroup.getSelectedItemPosition();
		// AreaObj areaProvicial = null;
		// AreaObj areaDistrist = null;
		// AreaObj areaPrecint = null;
		
		// if (mListTP.size() > 0) {
		// areaProvicial = mListTP.get(indexProvincial);
		// }
		// if (mListQuan.size() > 0) {
		// areaDistrist = mListQuan.get(indexDistrict);
		// }
		// if (mListXa.size() > 0) {
		// areaPrecint = mListXa.get(indexPrecint);
		// }
        if (mListGroup != null && mListGroup.size() > 0) {
            if (indexGroup > 0) {
                areaGroup = mListGroup.get(indexGroup);
            } else {
                if(areaGroup != null){
                    areaGroup.setName("");
                }
            }

        }

        String areaFlow = edtFlow.getText().toString().trim();
        String areaHomeNumber = edtHomeNumber.getText().toString().trim();
        if (validateAdd()) {
            InputMethodManager imm = (InputMethodManager) CreateAddressCommon.this
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View view = CreateAddressCommon.this.getCurrentFocus();
            if (view != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            Intent i = new Intent();

            i.putExtra("areaProvicial", areaProvicial);
            i.putExtra("areaDistrist", areaDistrist);
            i.putExtra("areaPrecint", areaPrecint);
            i.putExtra("areaGroup", areaGroup);
            i.putExtra("areaFlow", areaFlow);
            i.putExtra("areaHomeNumber", areaHomeNumber);
            if(isCheckDes){
                i.putExtra("description", edtghichu.getText().toString().trim());
            }
            setResult(Activity.RESULT_OK, i);
            finish();
        }

    }

    private boolean validateAdd() {

        Dialog dialog = null;
        if (CommonActivity.isNullOrEmpty(province)) {
            dialog = CommonActivity.createAlertDialog(CreateAddressCommon.this,
                    getString(R.string.message_pleass_input_province), getString(R.string.app_name));
            dialog.show();
            return false;

        }
        if (CommonActivity.isNullOrEmpty(district)) {
            dialog = CommonActivity.createAlertDialog(CreateAddressCommon.this,
                    getString(R.string.message_pleass_input_distrist), getString(R.string.app_name));
            dialog.show();
            return false;
        }
        if (!CommonActivity.isNullOrEmpty(checkPCProduct) && "true".equals(checkPCProduct)) {

            return true;

        } else {
            if (CommonActivity.isNullOrEmpty(precinct)) {
                dialog = CommonActivity.createAlertDialog(CreateAddressCommon.this,
                        getString(R.string.message_pleass_input_precint), getString(R.string.app_name));
                dialog.show();
                return false;
            }

            //bo chon to thon
            if (areaGroup == null || CommonActivity.isNullOrEmpty(areaGroup.getAreaCode())) {
                if (isCheckStreetBlock) {
                    dialog = CommonActivity.createAlertDialog(CreateAddressCommon.this,
                            getString(R.string.message_pleass_input_group), getString(R.string.app_name));
                    dialog.show();
                    return false;
                }

            }
        }

        return true;
    }
    // / request list group

    // move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CreateAddressCommon.this, LoginActivity.class);
            startActivity(intent);
            CreateAddressCommon.this.finish();
            MainActivity.getInstance().finish();

        }
    };

    @SuppressWarnings("unused")
    private class GetListGroupAdressAsyncTask extends AsyncTask<String, Void, ArrayList<AreaObj>> {

        private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";

        @SuppressWarnings("unused")
        public GetListGroupAdressAsyncTask(Context context) {
            this.context = context;
            // this.progress = new ProgressDialog(this.context);
            // this.progress.setCancelable(false);
            // this.progress.setMessage(context.getResources().getString(
            // R.string.getdataing));
            // if (!this.progress.isShowing()) {
            // this.progress.show();
            // }

            prbStreetBlock.setVisibility(View.VISIBLE);
            btnRefreshStreetBlock.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<AreaObj> doInBackground(String... params) {

            return getListGroupAddress(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<AreaObj> result) {
            try{
                super.onPostExecute(result);
                // progress.dismiss();

                if (errorCode.equals("0")) {
                    if (result != null && result.size() > 0) {
                        btnRefreshStreetBlock.setVisibility(View.VISIBLE);
                        prbStreetBlock.setVisibility(View.GONE);
                        updatePrecintSpinner(result);
                    } else {
                        updatePrecintSpinner(result);
                        CommonActivity.createAlertDialog(CreateAddressCommon.this, getString(R.string.notStreetBlock),
                                getString(R.string.app_name)).show();
                    }
                } else {
                    if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                        Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
                                context.getResources().getString(R.string.app_name), moveLogInAct);
                        dialog.show();
                    } else {
                        if (description == null || description.isEmpty()) {
                            description = context.getString(R.string.checkdes);
                        }
                        Dialog dialog = CommonActivity.createAlertDialog(CreateAddressCommon.this, description,
                                getResources().getString(R.string.app_name));
                        dialog.show();

                    }
                }
            }catch (Exception e){
                Log.d("exxxxx" ,e.toString());
            }

        }

        private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
			ArrayList<AreaObj> listGroupAdress = new ArrayList<>();
            if (parentCode.isEmpty()) {
                AreaObj first = new AreaObj();
                first.setAreaCode("");
                first.setName(getString(R.string.choose_street_block));
                listGroupAdress.add(first);
                return listGroupAdress;
            }
            listGroupAdress = new CacheDatabaseManager(MainActivity.getInstance()).getListCacheStreetBlock(parentCode);
            if (!listGroupAdress.isEmpty()) {
                AreaObj first = new AreaObj();
                first.setAreaCode("");
                first.setName(getString(R.string.choose_street_block));
                listGroupAdress.add(0, first);
                errorCode = "0";
                return listGroupAdress;
            }
            String original = "";
            try {

                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListArea");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListArea>");
                rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<parentCode>").append(parentCode).append("</parentCode>");
                rawData.append("</input>");
                rawData.append("</ws:getListArea>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData.toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, CreateAddressCommon.this,
                        "mbccs_getListArea");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee ", response);

                // SharedPreferences sharedPref = CreateAddressCommon.this
                // .getPreferences(Context.MODE_PRIVATE);
                // Editor editor = sharedPref.edit();
                // editor.putString(parentCode, original);
                // editor.commit();

                // ==== parse xml list ip
                listGroupAdress = parserListGroup(original);
                AreaObj first = new AreaObj();
                first.setAreaCode("");
                first.setName(getString(R.string.choose_street_block));
                if (listGroupAdress == null) {
					listGroupAdress = new ArrayList<>();
                    listGroupAdress.add(first);
                } else if (listGroupAdress.isEmpty()) {
                    listGroupAdress.add(0, first);
                } else {
                    new CacheDatabaseManager(MainActivity.getInstance()).insertCacheStreetBlock(listGroupAdress,
                            parentCode);
                    listGroupAdress.add(0, first);
                }

            } catch (Exception e) {
                Log.d("getListIP", e.toString());
            }
            return listGroupAdress;
        }

        public ArrayList<AreaObj> parserListGroup(String original) {
			ArrayList<AreaObj> listGroupAdress = new ArrayList<>();
            try {
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);
                    nodechild = doc.getElementsByTagName("lstArea");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        AreaObj areaObject = new AreaObj();
                        areaObject.setName(parse.getValue(e1, "name"));
                        Log.d("name area: ", areaObject.getName());
                        areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
                        listGroupAdress.add(areaObject);
                    }
                }
			} catch (Exception ignored) {

            }

            return listGroupAdress;
        }

        public void updatePrecintSpinner(ArrayList<AreaObj> result) {
            mListGroup = result;
            AdapterProvinceSpinner adapterGroup = new AdapterProvinceSpinner(mListGroup, CreateAddressCommon.this);
            spGroup.setAdapter(adapterGroup);

            if (type == 1) {
                if (areaGroup != null) {
                    for (int i = 0; i < mListGroup.size(); i++) {
                        AreaObj areaGroupObject = mListGroup.get(i);
                        if (areaGroupObject.getAreaCode().equals(areaGroup.getAreaCode())) {
                            spGroup.setSelection(i);
                        }
                    }
                }
                if(!CommonActivity.isNullOrEmpty(strStreetBlock)){
                    for (int i = 0; i < mListGroup.size(); i++) {
                        AreaObj areaGroupObject = mListGroup.get(i);
                        if (areaGroupObject.getAreaCode().equals(strStreetBlock)) {
                            spGroup.setSelection(i);
                        }
                    }
                }

            } else {

                if (areaGroup != null) {
                    for (int i = 0; i < mListGroup.size(); i++) {
                        AreaObj areaGroupObject = mListGroup.get(i);
                        if (areaGroupObject.getName().equals(areaGroup.getName())) {
                            spGroup.setSelection(i);
                        }
                    }
                }
                if(!CommonActivity.isNullOrEmpty(strStreetBlock)){
                    for (int i = 0; i < mListGroup.size(); i++) {
                        AreaObj areaGroupObject = mListGroup.get(i);
                        if (areaGroupObject.getAreaCode().equals(strStreetBlock)) {
                            spGroup.setSelection(i);
                        }
                    }
                }
            }
        }

    }

}
