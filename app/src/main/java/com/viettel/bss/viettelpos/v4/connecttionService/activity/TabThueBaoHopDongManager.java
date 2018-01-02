package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.activity.TabThongTinHopDong;
import com.viettel.bss.viettelpos.v3.connecttionService.activity.TabThongTinThueBao;
import com.viettel.bss.viettelpos.v3.connecttionService.model.GroupsDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.commons.auto.template.ActivityTemplate;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailClone;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.DummyTabContent;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TabThueBaoHopDongManager extends GPSTrackerFragment implements
        OnClickListener {

    public static TabHost tHost;

    public static TabThueBaoHopDongManager instance = null;

    public CustIdentityDTO custIdentityDTO;
    public ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2FormInit;
    public ArrayList<ProductCatalogDTO> lstProductCatalog;
    public String accountGline;
    public ArrayList<CustTypeDTO> arrCustTypeDTOs;
    public AreaObj area;
    public ArrayList<CustomerOrderDetailClone> lstCustomerOrderDetailId;
    public static String contactName;
    public static String telMobile;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    public static GroupsDTO groupsDTO;
    private String CHECK_CHANGE_TECH = "";
    private String oldSubId = "";


    // bo sung luong hotline  requestExtId;
    public String requestExtId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create_new_request1);
//		addActionBarTitle();
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CHECK_CHANGE_TECH = getIntent().getExtras().getString("CHECK_CHANGE_TECH", "");
        oldSubId = getIntent().getExtras().getString("oldSubId", "");
        unitView();
        instance = this;
        custIdentityDTO = (CustIdentityDTO) getIntent().getExtras()
                .getSerializable("custIdentityDTO");
        resultSurveyOnlineForBccs2FormInit = (ResultSurveyOnlineForBccs2Form) getIntent()
                .getExtras().getSerializable(
                        "resultSurveyOnlineForBccs2FormInit");
        lstProductCatalog = (ArrayList<ProductCatalogDTO>) getIntent()
                .getExtras().getSerializable("lstProductCatalog");
		accountGline = getIntent().getExtras().getString(
                "accountGline", "");
        arrCustTypeDTOs = (ArrayList<CustTypeDTO>) getIntent().getExtras()
                .getSerializable("arrCustTypeDTOs");
        lstCustomerOrderDetailId = (ArrayList<CustomerOrderDetailClone>) getIntent().getExtras()
                .getSerializable("customerOrderDetailId");
        contactName = getIntent().getExtras().getString("contactName", contactName);
        telMobile = getIntent().getExtras().getString("telMobile", telMobile);
        groupsDTO = (GroupsDTO) getIntent().getExtras().getSerializable("groupsDTO");
        AreaBean bean = (AreaBean) getIntent().getExtras().getSerializable(
                "areaBeanKey");
        area = new AreaObj();
        area.setProvince(bean.getProvince());
        area.setDistrict(bean.getDistrict());
        area.setPrecinct(bean.getPrecinct());
        area.setStreet(bean.getStreet());
        area.setStreetBlock(bean.getStreetBlock());
        area.setHome(bean.getHomeNumber());
        area.setFullNamel(bean.getFullAddress());

        String areaCode = "";
        if (!CommonActivity.isNullOrEmpty(bean.getStreetBlock())) {
            areaCode = bean.getProvince() + bean.getDistrict()
                    + bean.getPrecinct() + bean.getStreetBlock();
        } else {
            areaCode = bean.getProvince() + bean.getDistrict()
                    + bean.getPrecinct();
        }

        area.setAreaCode(areaCode);

        requestExtId = (String) getIntent().getExtras().getString(
                "requestExtId", "");

		findViewById(R.id.btn_loadtemplate).setVisibility(View.VISIBLE);
    }
	   @OnClick(R.id.btn_loadtemplate)
    protected void onLoadTemplate(View view) {
        if (toolbar.findViewById(R.id.btn_loadtemplate).getVisibility() == View.VISIBLE) {
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            TabThongTinThueBao tabThueBao = (TabThongTinThueBao) fm.findFragmentByTag(getResources().getString(R.string.conecmobile));

            Intent intent = new Intent(this, ActivityTemplate.class);
            intent.putExtra(AutoConst.FROM_SCREEN, AutoConst.PREF_DAU_NOI_CO_DINH_MOI_SCREEN);
            intent.putExtra(AutoConst.SERVICE, tabThueBao.getServiceName());
            
            startActivity(intent);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(getString(R.string.create_new_request_title));
        if (!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
            getSupportActionBar().setTitle(getString(R.string.button_change_tech));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

//		if(requestCode == 1212){
//			custIdentityDTO = (CustIdentityDTO) data.getSerializableExtra("custIdentityDTO");
//			resultSurveyOnlineForBccs2FormInit = (ResultSurveyOnlineForBccs2Form) data
//					.getExtras().getSerializable(
//							"resultSurveyOnlineForBccs2FormInit");
//			lstProductCatalog = (ArrayList<ProductCatalogDTO>) data
//					.getSerializableExtra("lstProductCatalog");
//			accountGline = (String) data.get("accountGline", "");
//			arrCustTypeDTOs = (ArrayList<CustTypeDTO>) data
//					.getSerializableExtra("arrCustTypeDTOs");
//			lstCustomerOrderDetailId = (ArrayList<CustomerOrderDetailClone>) data
//					.getSerializableExtra("customerOrderDetailId");
//			contactName = data.getExtras().getString("contactName",contactName);
//			AreaBean bean = (AreaBean) data.getSerializableExtra(
//					"areaBeanKey");
//			area = new AreaObj();
//			area.setProvince(bean.getProvince());
//			area.setDistrict(bean.getDistrict());
//			area.setPrecinct(bean.getPrecinct());
//			area.setStreet(bean.getStreet());
//			area.setStreetBlock(bean.getStreetBlock());
//			area.setHome(bean.getHomeNumber());
//			area.setFullNamel(bean.getFullAddress());
//
//			String areaCode = "";
//			if(!CommonActivity.isNullOrEmpty(bean.getStreetBlock())){
//				areaCode = 	bean.getProvince() + bean.getDistrict()
//						+ bean.getPrecinct() + bean.getStreetBlock();
//			}else{
//				areaCode = 	bean.getProvince() + bean.getDistrict()
//						+ bean.getPrecinct();
//			}
//
//			area.setAreaCode(areaCode);
//		}


        Fragment fragment = ReplaceFragment
                .getActiveFragmentActivity(TabThueBaoHopDongManager.this);

        if (fragment != null && fragment instanceof TabThongTinThueBao) {
            fragment.onActivityResult(requestCode,
                    resultCode, data);
        } else {
            // Log.d("TAG 9",
            // "ActivityCreateNewRequestMobile onActivityResult Fragment : " +
            // fragment.getClass().getName());
        }

    }

    // create unitView
    public void unitView() {
        tHost = (TabHost) findViewById(android.R.id.tabhost);
        tHost.setup();

        /**
         * Defining Tab Change Listener event. This is invoked when tab is
         * changed
         */
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {
                android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
                TabThongTinThueBao tabThueBao = (TabThongTinThueBao) fm
                        .findFragmentByTag(getResources().getString(
                                R.string.conecmobile));
                TabThongTinHopDong tabHopDong = (TabThongTinHopDong) fm
                        .findFragmentByTag(getResources().getString(
                                R.string.contractInfo));
                android.support.v4.app.FragmentTransaction ft = fm
                        .beginTransaction();

                if (!CommonActivity.isNullOrEmpty(CHECK_CHANGE_TECH)) {
                    TabThongTinThueBao.CHECK_CHANGE_TECH = CHECK_CHANGE_TECH;
                    TabThongTinThueBao.oldSubId = oldSubId;
                }

                /** Detaches the infoFragmentInfoCustomer if exists */
                if (tabThueBao != null)
                    ft.detach(tabThueBao);

                /** Detaches the infoFragmentInfoExamine if exists */
                if (tabHopDong != null)
                    ft.detach(tabHopDong);

                /** If current tab is android */
                if (tabId.equalsIgnoreCase(getResources().getString(
                        R.string.conecmobile))) {
                    toolbar.findViewById(R.id.btn_loadtemplate).setVisibility(View.VISIBLE);
                    if (tabThueBao == null) {
                        /**
                         * Create infoFragmentInfoCustomer and adding to
                         * fragmenttransaction
                         */
                        ft.add(R.id.realtabcontent, new TabThongTinThueBao(),
                                getResources().getString(R.string.conecmobile));
                    } else {
                        /**
                         * Bring to the front, if already exists in the
                         * fragmenttransaction
                         */
                        ft.attach(tabThueBao);
                    }

                } else {
                    toolbar.findViewById(R.id.btn_loadtemplate).setVisibility(View.GONE);
                    if (tabHopDong == null) {
                        /**
                         * Create infoFragmentInfoExamine and adding to
                         * fragmenttransaction
                         */
                        ft.add(R.id.realtabcontent, new TabThongTinHopDong(),
                                getResources().getString(R.string.contractInfo));
                    } else {
                        /**
                         * Bring to the front, if already exists in the
                         * fragmenttransaction
                         */
                        ft.attach(tabHopDong);
                    }
                }
                ft.commit();
            }
        };

        /** Setting tabchangelistener for the tab */
        tHost.setOnTabChangedListener(tabChangeListener);

        TabHost.TabSpec tSpecInfoCus = tHost.newTabSpec(getResources()
                .getString(R.string.conecmobile));
        tSpecInfoCus.setIndicator(getResources()
                        .getString(R.string.conecmobile),
                getResources().getDrawable(R.drawable.sale_channel));
        tSpecInfoCus.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecInfoCus);

        setTab();

    }

    private void setTab() {
        TabHost.TabSpec tSpecInfoExamine = tHost.newTabSpec(getResources()
                .getString(R.string.contractInfo));
        tSpecInfoExamine.setIndicator(
                getResources().getString(R.string.contractInfo), getResources()
                        .getDrawable(R.drawable.sale_deposit));
        tSpecInfoExamine.setContent(new DummyTabContent(getBaseContext()));
        tHost.addTab(tSpecInfoExamine);
    }

    // add actionbar title
    private void addActionBarTitle() {

        ActionBar mActionBar = TabThueBaoHopDongManager.this.getActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.layout_actionbar_channel);
        LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
                .findViewById(R.id.relaBackHome);
        relaBackHome.setOnClickListener(this);
        Button btnHome = (Button) mActionBar.getCustomView().findViewById(
                R.id.btnHome);
        btnHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.callphone(TabThueBaoHopDongManager.this,
                        Constant.phoneNumber);
            }
        });
        TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
                .findViewById(R.id.txtNameActionbar);
        TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
                .findViewById(R.id.txtAddressActionbar);
        txtAddressActionBar.setVisibility(View.GONE);
        txtNameActionBar.setText(getResources().getString(
                R.string.create_new_request_title));
    }
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case android.R.id.home:
				finish();
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaBackHome:
                onBackPressed();

                break;
            default:
                break;
        }

    }

}
