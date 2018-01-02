package com.viettel.bss.viettelpos.v4.sale.asytask;

import android.app.Activity;
import android.util.Log;
import android.view.View.OnClickListener;

import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.Shop;
import com.viettel.bss.viettelpos.v4.sale.object.SmartphoneOutput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskGetLstShopManagedByStaff extends
        AsyncTaskCommon<Void, Void, List<Shop>> {

    public AsyncTaskGetLstShopManagedByStaff(Activity context,
                                             OnPostExecuteListener<List<Shop>> listener,
                                             OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected List<Shop> doInBackground(Void... arg0) {
        return getLstShopManagedByStaff();
    }

    private List<Shop> getLstShopManagedByStaff() {
        String original = "";
        ArrayList<Spin> lstReasons = new ArrayList<>();
        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_getLstShopManagedByStaff");
            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:getLstShopManagedByStaff>");
            rawData.append("<input>");
            rawData.append("<token>").append(Session.getToken()).append("</token>");
            rawData.append("</input>");
            rawData.append("</ws:getLstShopManagedByStaff>");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData
                    .toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_getLstShopManagedByStaff");
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Responseeeeeeeeee Original", original);

            // parser
            if (!output.getError().equals("0")) {
                return null;
            }

            Serializer serializer = new Persister();
            SmartphoneOutput smartphoneOutput = serializer.read(SmartphoneOutput.class, original);
            errorCode = smartphoneOutput.getErrorCode();
            description = smartphoneOutput.getDescription();
            return smartphoneOutput.getLstShop();

        } catch (Exception e) {
            Log.d("mbccs_getListApproveFinance", e.toString()
                    + "description error", e);
        }

        return null;

    }

}
