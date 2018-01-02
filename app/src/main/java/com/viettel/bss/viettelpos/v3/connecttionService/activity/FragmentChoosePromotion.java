package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.PromotionAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentChoosePromotion extends GPSTracker implements
        OnItemClickListener, View.OnClickListener {

    private EditText edtsearch;
    private ListView lvItem;

    private PromotionAdapter adtPromotion;
    private ArrayList<PromotionTypeBeans> lstData = new ArrayList<PromotionTypeBeans>();
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String productCode = "";

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setContentView(R.layout.connectionmobile_layout_lst_pakage);
        ButterKnife.bind(this);
        EditText edtsearch = (EditText) findViewById(R.id.edtsearch);
        edtsearch.setHint(R.string.nhap_khuyen_mai_tim_kiem);
        TextView tvLabel = (TextView) findViewById(R.id.txtinfo);
        tvLabel.setText(R.string.ds_khuyen_mai);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.chonkhuyenmai));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MainActivity.getInstance().setTitleActionBar(R.string.chonkhuyenmai);
        unit();
        Bundle b = getIntent().getExtras();
        if (b != null) {
//			bundle.putString("custType",custType + "");
//			bundle.putString("province",area.getProvince() + "");
//			bundle.putString("regType", sub.getHthm().getCode());
//			bundle.putString("offerId",sub.getOfferId() + "");
//			bundle.putString("serviceType",sub.getSubType());
//			bundle.putString("subType",sub.getSubType());

            String custType = b.getString("custType", "");
            String province = b.getString("province", "");
            String regType = b.getString("regType", "");
            String offerId = b.getString("offerId", "");
            String serviceType = b.getString("serviceType", "");
            String subType = b.getString("subType", "");
             productCode = b.getString("productCode", "");

            GetPromotionAsytask getPromotionAsytask = new GetPromotionAsytask(FragmentChoosePromotion.this);
            getPromotionAsytask.execute(custType, province, regType, offerId, serviceType, subType);


        }


        super.onCreate(savedInstanceState);
    }

    // =========== webservice danh sach khuyen mai =================

    public void unit() {
        edtsearch = (EditText) findViewById(R.id.edtsearch);
        lvItem = (ListView) findViewById(R.id.lstpakage);
        lvItem.setOnItemClickListener(this);
        lvItem.setTextFilterEnabled(true);
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onSearchData();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void onSearchData() {

        String keySearch = edtsearch.getText().toString().trim();
        if (keySearch.length() == 0) {

             adtPromotion = new PromotionAdapter(this, lstData,"");
            lvItem.setAdapter(adtPromotion);
            adtPromotion.notifyDataSetChanged();

        } else {
            ArrayList<PromotionTypeBeans> items = new ArrayList<PromotionTypeBeans>();
            for (PromotionTypeBeans promotionTypeBeans : lstData) {
                if (promotionTypeBeans.getCodeName().toLowerCase()
                        .contains(keySearch.toLowerCase())) {
                    items.add(promotionTypeBeans);
                }
            }

            adtPromotion = new PromotionAdapter(this, items,"");
            lvItem.setAdapter(adtPromotion);
            adtPromotion.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Intent data = new Intent();
        Bundle mBundle = new Bundle();
        mBundle.putSerializable("selectedPromotionKey",
                adtPromotion.getItem(arg2));
//		mBundle.putSerializable("lstPromotionKey", lstData);
        data.putExtras(mBundle);
        setResult(Activity.RESULT_OK, data);
        finish();
        // getTargetFragment().onActivityResult(getTargetRequestCode(),
        // Activity.RESULT_OK, data);
        // context.onBackPressed();
    }

    public class GetPromotionAsytask extends
            AsyncTask<String, Void, ArrayList<PromotionTypeBeans>> {

        private Activity context = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        private ProgressDialog progress;

        public GetPromotionAsytask(Activity context) {

            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(
                    R.string.getdataing));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected ArrayList<PromotionTypeBeans> doInBackground(String... arg0) {
            return getPromotionTypeBeans(arg0[0], arg0[1], arg0[2], arg0[3], arg0[4], arg0[5]);
        }

        @Override
        protected void onPostExecute(ArrayList<PromotionTypeBeans> result) {
            this.progress.dismiss();
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    lstData = result;
//					PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
//					promotionTypeBeans1.setCodeName(context
//							.getString(R.string.selectMKM));
//					promotionTypeBeans1.setPromProgramCode("-1");
//					lstData.add(0, promotionTypeBeans1);
//					edtPromotion.setText(context.getString(R.string.chonctkm1));

                } else {

                    PromotionTypeBeans promotionTypeBeans1 = new PromotionTypeBeans();
//					promotionTypeBeans1.setCodeName(context
//							.getString(R.string.not_exists_promotion));
//
//					promotionTypeBeans1.setPromProgramCode("");
                    promotionTypeBeans1.setCodeName(context
                            .getString(R.string.not_exists_promotion));
                    promotionTypeBeans1.setPromProgramCode("-1");
                    lstData = new ArrayList<PromotionTypeBeans>();
//					sub.setPromotion(promotionTypeBeans1);
                    lstData.add(0, promotionTypeBeans1);
//					edtPromotion.setText(promotionTypeBeans1.getCodeName());
//					if (tab != null) {
//						tab.getCDT();
//					}
                }
//				sub.setLstPromotion(lstData);


                adtPromotion = new PromotionAdapter(context, lstData, productCode);
                lvItem.setAdapter(adtPromotion);
                adtPromotion.notifyDataSetChanged();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    CommonActivity.BackFromLogin((Activity) context, description);
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            context.getString(R.string.error_get_promotion,
                                    description), context
                                    .getString(R.string.app_name));
                    dialog.show();
                }
            }
        }

        /**
         * @return
         */

//		bundle.putString("custType",custType + "");
//		bundle.putString("province",area.getProvince() + "");
//		bundle.putString("regType", sub.getHthm().getCode());
//		bundle.putString("offerId",sub.getOfferId() + "");
//		bundle.putString("serviceType",sub.getSubType());
        private ArrayList<PromotionTypeBeans> getPromotionTypeBeans(String custType, String province, String regType, String offerId, String serviceType, String subType) {
            ArrayList<PromotionTypeBeans> lisPromotionTypeBeans = new ArrayList<PromotionTypeBeans>();
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);

                input.addValidateGateway("wscode", "mbccs_getPromotions");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getPromotions>");
                rawData.append("<input>");
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                rawData.append("<reasonId>" + regType + "</reasonId>");
                rawData.append("<offerId>" + offerId + "</offerId>");
                rawData.append("<serviceType>" + serviceType + "</serviceType>");
                rawData.append("<custType>" + custType + "</custType>");
                rawData.append("<payType>" + 1 + "</payType>");
                rawData.append("<province>" + province + "</province>");

                rawData.append("<actionCode>" + "00" + "</actionCode>");

                if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit)) {

                    if (!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode())) {
                        rawData.append("<nodeCode>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getConnectorCode());
                        rawData.append("</nodeCode>");
                    }


                }

                rawData.append("</input>");
                rawData.append("</ws:getPromotions>");

//			input.addValidateGateway("wscode", "mbccs_getListPromotionByMapSP");
//			StringBuilder rawData = new StringBuilder();
//			rawData.append("<ws:getListPromotionByMapSP>");
//			rawData.append("<input>");
//			HashMap<String, String> paramToken = new HashMap<String, String>();
//			paramToken.put("token", Session.getToken());
//			rawData.append(input.buildXML(paramToken));
//			rawData.append("<regType>" + sub.getHthm().getCode() + "</regType>");
//			rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
//			rawData.append("<serviceType>" + sub.getServiceType()
//					+ "</serviceType>");
//			rawData.append("<custType>" + custType + "</custType>");
//			rawData.append("<payType>" + 1 + "</payType>");
//			rawData.append("<subType>" + sub.getSubType() + "</subType>");
//			rawData.append("<province>" + province + "</province>");
//			rawData.append("<district>" + "-1" + "</district>");
//			rawData.append("<precinct>" + "-1" + "</precinct>");
//			// rawData.append("<actionCode>" + "00" + "</actionCode>");
//			rawData.append("</input>");
//			rawData.append("</ws:getListPromotionByMapSP>");
                Log.i("RowData", rawData.toString());

                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                        context, "mbccs_getPromotions");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();

                // ============parse xml in android=========
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                NodeList nodechild = null;
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("errorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                    nodechild = doc.getElementsByTagName("lstPromotionsBO");
                    for (int j = 0; j < nodechild.getLength(); j++) {
                        Element e1 = (Element) nodechild.item(j);
                        PromotionTypeBeans promotionTypeBeans = new PromotionTypeBeans();
                        String codeName = parse.getValue(e1, "codeName");
                        Log.d("codeName", codeName);
                        promotionTypeBeans.setCodeName(codeName);

                        String name = parse.getValue(e1, "name");
                        Log.d("name", name);
                        promotionTypeBeans.setName(name);

                        String promProgramCode = parse.getValue(e1, "promProgramCode");
                        Log.d("code", promProgramCode);
                        promotionTypeBeans.setPromProgramCode(promProgramCode);

                        String descrip = parse.getValue(e1, "description");
                        Log.d("descriponnnnn", descrip);
                        if (CommonActivity.isNullOrEmpty(promotionTypeBeans.getDescription())) {
                            promotionTypeBeans.setDescription(descrip);
                        }
                        lisPromotionTypeBeans.add(promotionTypeBeans);
                         }
                }
            } catch (Exception ex) {
                Log.d("getPromotionTypeBeans", ex.toString());
            }

            return lisPromotionTypeBeans;
        }

    }
}
