package com.viettel.gem.asynctask;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.AsyncTaskCommonSupper;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductSpecCharDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharUseDTO;
import com.viettel.bss.viettelpos.v4.work.object.ProductSpecCharValueDTOList;
import com.viettel.gem.model.ProductSpecificationModel;
import com.viettel.gem.utils.FileUtils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;

/**
 * Created by BaVV on 11/23/2017.
 */

public class CustomerInfoCollectAsyncTask extends AsyncTaskCommonSupper<ProductSpecificationModel, Void, String> {

    public CustomerInfoCollectAsyncTask(
            Activity context,
            OnPostExecuteListener<String> listener,
            View.OnClickListener moveLogInAct) {

        super(context, listener, moveLogInAct);
    }

    @Override
    protected String doInBackground(ProductSpecificationModel... params) {
        return getCusInfoCollectV2(params[0]);
    }

    private String getCusInfoCollectV2(ProductSpecificationModel productSpecificationModel) {

        String original = "";
        ParseOuput out = null;
        String func = "cusInfoCollectV2";

        try {
            BCCSGateway input = new BCCSGateway();
            input.addValidateGateway("username", Constant.BCCSGW_USER);
            input.addValidateGateway("password", Constant.BCCSGW_PASS);
            input.addValidateGateway("wscode", "mbccs_" + func);

            StringBuilder rawData = new StringBuilder();
            rawData.append("<ws:" + func + ">");
            rawData.append("<input>");
            rawData.append("<token>" + Session.getToken() + "</token>");

            if (!CommonActivity.isNullOrEmpty(productSpecificationModel)) {
                List<ProductSpecificationDTO> lstProductSpecificationDTOs = productSpecificationModel.getLstProductSpecificationDTOs();
                if (!CommonActivity.isNullOrEmpty(lstProductSpecificationDTOs)) {
                    for (ProductSpecificationDTO productSpecificationDTO : lstProductSpecificationDTOs) {
                        productSpecificationDTO.setName("");
                        List<ProductSpecCharUseDTO> lstProductSpecCharUseDTO = productSpecificationDTO.getLstProductSpecCharUseDTO();
                        if (!CommonActivity.isNullOrEmpty(lstProductSpecCharUseDTO)) {
                            for (ProductSpecCharUseDTO specCharUseDTO : lstProductSpecCharUseDTO) {
                                ProductSpecCharDTO productSpecCharDTO = specCharUseDTO.getListProductSpecCharDTOs();
                                if (!CommonActivity.isNullOrEmpty(productSpecCharDTO)) {
                                    productSpecCharDTO.setName("");
                                    List<ProductSpecCharValueDTOList> productSpecCharValueDTOList = productSpecCharDTO.getProductSpecCharValueDTOList();
                                    if (!CommonActivity.isNullOrEmpty(productSpecCharValueDTOList)) {
                                        for(ProductSpecCharValueDTOList productSpecCharValueDTOList1 : productSpecCharValueDTOList) {
                                            productSpecCharValueDTOList1.setName("");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Serializer serializer = new Persister();

            String tempPath = Environment.getExternalStorageDirectory() + "/txt_temp.txt";

            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(tempPath), "UTF-8"));

            serializer.write(productSpecificationModel, writer);

            String model = FileUtils.getStringFromFile(tempPath);
            model = model.replaceAll("<return>", "");
            model = model.replaceAll("</return>", "");
            model = model.replaceAll("\\*", "");

            if (!CommonActivity.isNullOrEmpty(model))
                rawData.append(model);

            rawData.append("</input>");
            rawData.append("</ws:" + func + ">");
            Log.i("RowData", rawData.toString());
            String envelope = input.buildInputGatewayWithRawData(rawData.toString());
            Log.d("Send evelop", envelope);
            Log.i("LOG", Constant.BCCS_GW_URL);
            String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
                    mActivity, "mbccs_" + func);
            Log.i("Responseeeeeeeeee", response);
            CommonOutput output = input.parseGWResponse(response);
            original = output.getOriginal();
            Log.i("Original", response);

            serializer = new Persister();
            out = serializer.read(ParseOuput.class, original);
        } catch (Exception e) {
            Log.e(Constant.TAG, "blockSubForTerminate", e);
            description = e.getMessage();
            errorCode = Constant.ERROR_CODE;
        }

        if (CommonActivity.isNullOrEmpty(productSpecificationModel)) {
            description = mActivity.getString(R.string.no_return_from_system);
            errorCode = Constant.ERROR_CODE;
            return null;
        } else {
            if (null != out) {
                description = out.getDescription();
                errorCode = out.getErrorCode();
            }

            if (description != null && description.contains("java.lang.String.length()")) {
                description = mActivity.getString(R.string.server_time_out);
            }
        }

        return description;
    }
}