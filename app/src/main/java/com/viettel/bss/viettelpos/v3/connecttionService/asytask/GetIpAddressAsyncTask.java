package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class GetIpAddressAsyncTask extends AsyncTask<Void, Void, ParseOuput> {
    private Activity mActivity = null;
    private Spinner spinner;
    private Map<String, List<String>> mapIpAddress;
    private SubscriberDTO sub;
    private AreaObj area;
    private View prb;
    private View ln;

    public GetIpAddressAsyncTask(Activity mActivity, Spinner spinner,
                                 SubscriberDTO sub, Map<String, List<String>> mapIpAddress,
                                 AreaObj area, View prb, View ln) {
        this.mActivity = mActivity;
        this.sub = sub;
        this.area = area;
        this.mapIpAddress = mapIpAddress;
        this.spinner = spinner;
        this.prb = prb;
        this.ln = ln;
        ln.setVisibility(View.VISIBLE);
        prb.setVisibility(View.VISIBLE);
    }

    @Override
    protected ParseOuput doInBackground(Void... params) {
        return getListIpAddress();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected void onPostExecute(ParseOuput result) {
        super.onPostExecute(result);
        prb.setVisibility(View.GONE);
        try {
            if (result.getErrorCode().equals("0")) {
                List<String> lst = result.getListValue();

                if (lst == null || lst.isEmpty()) {
                    lst = new ArrayList<String>();
                    lst.add(mActivity.getString(R.string.not_support_static_ip));
                    ln.setVisibility(View.GONE);
                } else {
                    ln.setVisibility(View.VISIBLE);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
                        R.layout.spinner_item, R.id.spinner_value, lst);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                spinner.setAdapter(adapter);

                mapIpAddress.put(area.getProvince() + "_" + sub.getOfferId(), lst);

            } else {
                ln.setVisibility(View.GONE);
                if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
                    CommonActivity
                            .BackFromLogin(mActivity, result.getDescription());
                } else {
                    if (result.getDescription() == null
                            || result.getDescription().isEmpty()) {
                        result.setDescription(mActivity
                                .getString(R.string.checkdes));
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(mActivity,
                            result.getDescription(), mActivity.getResources()
                                    .getString(R.string.app_name));
                    dialog.show();
                }
            }
        }catch (Exception e){

        }
    }

    private ParseOuput getListIpAddress() {

        List<String> lst = mapIpAddress.get(area.getProvince() + "_"
                + sub.getOfferId());
        if (!CommonActivity.isNullOrEmpty(lst)) {
            ParseOuput out = new ParseOuput();
            out.setErrorCode("0");
            out.setListValue((ArrayList<String>) lst);
            return out;
        }
        ParseOuput result;
        String original = "";
        try {
            String methodName = "getListStaticIPProvince";
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + methodName);
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + methodName + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");
            rawData.append("<offerId>" + sub.getOfferId() + "</offerId>");
            rawData.append("<province>" + area.getProvince() + "</province>");
            rawData.append("<telecomServiceId>" + sub.getTelecomServiceId()
                    + "</telecomServiceId>");
            rawData.append("<productCode>" + sub.getProductCode()
                    + "</productCode>");
            rawData.append("<serviceType>" + sub.getServiceType()
                    + "</serviceType>");

            rawData.append("</input>");
            rawData.append("</ws:" + methodName + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_getReasonMobile");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);
            // parser
            Serializer serializer = new Persister();
            result = serializer.read(ParseOuput.class, original);
            if (result == null) {
                result = new ParseOuput();
                result.setDescription(mActivity
                        .getString(R.string.no_return_from_system));
                result.setErrorCode(Constant.ERROR_CODE);
            }
        } catch (Exception e) {
            Log.e("getListIP", e.toString() + "description error", e);
            result = new ParseOuput();
            result.setDescription(mActivity.getString(R.string.exception)
                    + " - " + e);
            result.setErrorCode(Constant.ERROR_CODE);
        }

        return result;

    }
}
