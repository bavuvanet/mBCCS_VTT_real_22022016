package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.UI;
import com.viettel.bss.viettelpos.v4.customer.object.FormBean;
import com.viettel.bss.viettelpos.v4.customer.object.ProfileUpload;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.ProfileAdapter;
import com.viettel.bss.viettelpos.v3.connecttionService.adapter.ProfileAdapter.OnChangeImage;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GetLisRecordPrepaidTask extends AsyncTask<Void, Void, SaleOutput> {

    private Activity context;

    private ListView lvUploadImage;
    private View prb;
    private SubscriberDTO mainSub;
    private SubscriberDTO sub;
    OnChangeImage onChangeImage;

    public GetLisRecordPrepaidTask(SubscriberDTO mainSub, SubscriberDTO sub,
                                   OnChangeImage onChangeImage, ListView lvUploadImage, View prb,
                                   Activity context) {
        this.onChangeImage = onChangeImage;
        this.mainSub = mainSub;
        this.lvUploadImage = lvUploadImage;
        this.prb = prb;
        this.sub = sub;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {
        prb.setVisibility(View.VISIBLE);
        super.onPreExecute();
    }

    @Override
    protected SaleOutput doInBackground(Void... params) {

        return getListRecordPrepaid();
    }

    @Override
    protected void onPostExecute(SaleOutput result) {
        prb.setVisibility(View.GONE);
        try {
            if (result.getErrorCode().equals("0")) {
                List<FormBean> lst = result.getLstProfileRecord();

                if (lst == null || lst.isEmpty()) {
                    sub.setLstProfile(null);
                } else {

                    Map<String, List<FormBean>> map = new HashMap<String, List<FormBean>>();

                    for (FormBean formBean : lst) {
                        List<FormBean> lstTmp = map.get(formBean.getId());
                        if (CommonActivity.isNullOrEmpty(lstTmp)) {
                            map.put(formBean.getId(), new ArrayList<FormBean>());
                        }
//						if (mainSub != sub) {
//							for (ProfileUpload profile : mainSub
//									.getLstProfile()) {
//								if (formBean.getCode().equals(
//										profile.getSelectProfile().getCode())) {
//									formBean.setGroupParentId(profile.getId());
//								}
//							}
//						}
                        map.get(formBean.getId()).add(formBean);
                    }
                    List<ProfileUpload> lstProfile = new ArrayList<ProfileUpload>();
                    for (Entry<String, List<FormBean>> entry : map.entrySet()) {
                        ProfileUpload profile = new ProfileUpload();
                        profile.setId(entry.getKey());
                        profile.setLstProfile(entry.getValue());
                        profile.setParentId(entry.getValue().get(0)
                                .getGroupParentId());
                        lstProfile.add(profile);
                    }
                    sub.setLstProfile(lstProfile);

                }
                List<ProfileUpload> tmp = new ArrayList<ProfileUpload>();
                for (ProfileUpload profileUpload : sub.getLstProfile()) {
//					if (CommonActivity.isNullOrEmpty(profileUpload
//							.getParentId())) {
                    tmp.add(profileUpload);
//					}

                }
                ProfileAdapter adapter = new ProfileAdapter(context, tmp,
                        onChangeImage);
                lvUploadImage.setVisibility(View.VISIBLE);
                lvUploadImage.setAdapter(adapter);
                UI.setListViewHeightBasedOnChildren(lvUploadImage);
            } else {
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity.BackFromLogin(context,
                            result.getDescription());
                } else {
                    if (result.getDescription() == null
                            || result.getDescription().isEmpty()) {
                        result.setDescription(context
                                .getString(R.string.checkdes));
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context,
                            result.getDescription(),
                            context.getString(R.string.app_name));
                    dialog.show();
                }
            }
        } catch (Exception e) {
        }

        super.onPostExecute(result);
    }

    private SaleOutput getListRecordPrepaid() {
        SaleOutput result;
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getListRecordPrepaid");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getListRecordPrepaid>");
            rawData.append("<cmInput>");
            rawData.append("<isConnect>");
            rawData.append(true);
            rawData.append("</isConnect>");
            rawData.append("<isPospaid>" + true + "</isPospaid>");

            rawData.append("<serviceType>" + sub.getServiceType()
                    + "</serviceType>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<productCode>" + sub.getProductCode()
                    + "</productCode>");
            rawData.append("<reasonId>" + sub.getHthm().getReasonId()
                    + "</reasonId>");
            rawData.append("</cmInput>");
            rawData.append("</ws:getListRecordPrepaid>");
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    context, "mbccs_getListRecordPrepaid");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            String original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);
            // parser
            Serializer serializer = new Persister();
            result = serializer.read(SaleOutput.class, original);
            if (result == null) {
                result = new SaleOutput();
                result.setDescription(context
                        .getString(R.string.no_return_from_system));
                result.setErrorCode(Constant.ERROR_CODE);
            }
        } catch (Exception e) {
            Log.e("getListIP", e.toString() + "description error", e);
            result = new SaleOutput();
            result.setDescription(context.getString(R.string.exception) + " - "
                    + e);
            result.setErrorCode(Constant.ERROR_CODE);
        }
        return result;
    }
}
