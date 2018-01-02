package com.viettel.bss.viettelpos.v3.connecttionService.asytask;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.TabThueBaoHopDongManager;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.AsyncTask;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetListStockTypeAsynTask extends
		AsyncTask<String, Void, ArrayList<ProductOfferTypeDTO>> {

	// ProgressDialog progress;
	private Context context = null;
	private String errorCode = "";
	private String description = "";
	private SubscriberDTO sub;
	private TextView edtThietBi;
	private View prbThietBi;

	public GetListStockTypeAsynTask(Context context, SubscriberDTO sub,
			TextView edtThietBi, View prbThietBi) {
		this.context = context;
		this.sub = sub;
		this.edtThietBi = edtThietBi;
		this.prbThietBi = prbThietBi;
		this.prbThietBi.setVisibility(View.VISIBLE);
	}

	@Override
	protected ArrayList<ProductOfferTypeDTO> doInBackground(String... arg0) {
		return getListProduct();
	}

	@Override
	protected void onPostExecute(ArrayList<ProductOfferTypeDTO> result) {
		this.prbThietBi.setVisibility(View.GONE);
		if (errorCode.equals("0")) {
			if (CommonActivity.isNullOrEmpty(result)) {
//				if (result.size() == 1) {
				edtThietBi.setVisibility(View.GONE);
				edtThietBi.setText(context
							.getString(R.string.not_exists_stock_type));
//				}
			}else{
				edtThietBi.setVisibility(View.VISIBLE);
			}
			sub.setLstProductType(result);

		} else {
			if (errorCode.equals(Constant.INVALID_TOKEN2)) {
				CommonActivity.BackFromLogin((Activity) context, description);
			} else {
				if (description == null || description.isEmpty()) {
					description = context.getString(R.string.checkdes);
				}
				Dialog dialog = CommonActivity.createAlertDialog(context,
						description, context.getString(R.string.app_name));
				dialog.show();
			}
		}
	}

	private ArrayList<ProductOfferTypeDTO> getListProduct() {
		ArrayList<ProductOfferTypeDTO> arrayListThongTinHH = new ArrayList<ProductOfferTypeDTO>();
		String original = "";
		ArrayList<ProductOfferTypeDTO> lstReturn = new ArrayList<ProductOfferTypeDTO>();
		try {
			BCCSGateway input = new BCCSGateway();
			input.addValidateGateway("username", Constant.BCCSGW_USER);
			input.addValidateGateway("password", Constant.BCCSGW_PASS);
			input.addValidateGateway("wscode", "mbccs_getListStockTypeWSSP");
			StringBuilder rawData = new StringBuilder();
			rawData.append("<ws:getListStockTypeWSSP>");
			rawData.append("<input>");
			HashMap<String, String> paramToken = new HashMap<String, String>();
			paramToken.put("token", Session.getToken());
			rawData.append(input.buildXML(paramToken));



			rawData.append("<serviceType>" + sub.getServiceType());
			rawData.append("</serviceType>");

			rawData.append("<regType>" + sub.getHthm().getCode());
			rawData.append("</regType>");
			AreaObj area = TabThueBaoHopDongManager.instance.area;
			rawData.append("<areaCode>" + area.getProvince()
					+ area.getDistrict());

			rawData.append("</areaCode>");
			rawData.append("<productCode>" + sub.getProductCode());
			rawData.append("</productCode>");
//			if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm())){
//				if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode())){
//
//					rawData.append("<vendor>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getResultGetVendorByConnectorForm().getVendorCode());
//					rawData.append("</vendor>");
//
//				}
//			}
//			if(!CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance) && !CommonActivity.isNullOrEmpty(TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getInfraType())){
//				rawData.append("<technology>" + TabThueBaoHopDongManager.instance.resultSurveyOnlineForBccs2FormInit.getInfraType());
//				rawData.append("</technology>");
//			}else{
//				rawData.append("<technology>" + "");
//				rawData.append("</technology>");
//			}
			rawData.append("</input>");
			rawData.append("</ws:getListStockTypeWSSP>");

			Log.i("RowData", rawData.toString());

			String envelope = input.buildInputGatewayWithRawData(rawData
					.toString());
			Log.d("Send evelop", envelope);
			Log.i("LOG", Constant.BCCS_GW_URL);
			String response = input.sendRequest(envelope, Constant.BCCS_GW_URL,
					context, "mbccs_getListStockTypeWSSP");
			Log.i("Responseeeeeeeeee", response);
			CommonOutput output = input.parseGWResponse(response);

			original = output.getOriginal();
			Log.i("original 69696", "original :" + original);

			// ============parse xml in android=========

			Serializer serializer = new Persister();
			ParseOuput parseOuput = serializer.read(ParseOuput.class, original);
			if (parseOuput != null) {
				errorCode = parseOuput.getErrorCode();
				description = parseOuput.getDescription();
				arrayListThongTinHH = parseOuput.getLstProductOfferTypeDTO();
				if (arrayListThongTinHH != null
						&& arrayListThongTinHH.size() > 0) {
					for (ProductOfferTypeDTO productOfferTypeDTO : arrayListThongTinHH) {
						if ("1".equals(productOfferTypeDTO
								.getProductOfferTypeId())
								|| "2".equals(productOfferTypeDTO
										.getProductOfferTypeId())
								|| "3".equals(productOfferTypeDTO
										.getProductOfferTypeId())
								|| "4".equals(productOfferTypeDTO
										.getProductOfferTypeId())) {
							continue;
						} else {
							lstReturn.add(productOfferTypeDTO);
						}
					}
				}

				// clone truong hop trung mat hang
				if (!CommonActivity.isNullOrEmpty(lstReturn)) {
					for (int i = 0; i < lstReturn.size(); i++) {
						ArrayList<com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO> lstProductOfferingDTOs = lstReturn.get(i).getProductOfferings();
						if (!CommonActivity.isNullOrEmpty(lstProductOfferingDTOs)) {
							// check theo cung nhom hay khong
							if (isProductOfferingDTO(lstProductOfferingDTOs)) {
								// tao hastmap phan nhom cac vung gia tri
								Map<String, ArrayList<com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO>> hashMap = new HashMap<>();
								for (com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO productOfferingDTO : lstProductOfferingDTOs) {
									String key = productOfferingDTO.getProdPackTypeId().toString();
									Log.d("key ========= ", key);
									if (!hashMap.containsKey(key)) {
										ArrayList<com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO> list = new ArrayList<>();
										list.add(productOfferingDTO);
										hashMap.put(key, list);
									} else {
										hashMap.get(key).add(productOfferingDTO);
									}
								}
								if (!CommonActivity.isNullOrEmpty(hashMap) && hashMap.size() > 1) {
									for (ArrayList<com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO> arrayList: hashMap.values()) {
										ProductOfferTypeDTO productOfferTypeDTO = new ProductOfferTypeDTO();
										productOfferTypeDTO.setName(lstReturn.get(i).getName());
										productOfferTypeDTO.setSaleServiceCode(lstReturn.get(i).getSaleServiceCode());
										productOfferTypeDTO.setProductOfferTypeId(lstReturn.get(i).getProductOfferTypeId());
										productOfferTypeDTO.setProductOfferings(arrayList);
										lstReturn.add(productOfferTypeDTO);
									}
									lstReturn.remove(i);
								}
							}
						}
					}
				}


			}
		} catch (Exception e) {
			Log.d("getListProduct", e.toString());
		}

		return lstReturn;
	}

	private boolean isProductOfferingDTO(ArrayList<com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO> arrayList){
		boolean ischeck = false;
		Long prodPackTypeId = arrayList.get(0).getProdPackTypeId();
		for (int i = 1; i < arrayList.size() ; i++){
			if(!prodPackTypeId.equals(arrayList.get(i).getProdPackTypeId())){
				ischeck = true;
			}
		}
		return ischeck;

	}

}
