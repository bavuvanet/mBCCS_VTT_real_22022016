package com.viettel.bss.viettelpos.v4.report.dialog;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ListView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.report.object.AttributeObjectBO;
import com.viettel.bss.viettelpos.v4.report.object.AttributeObjectBOArray;
import com.viettel.bss.viettelpos.v4.report.object.BonusOutput;
import com.viettel.bss.viettelpos.v4.sale.adapter.AdapterListServiceDetail;

public class DialogBonusSubDetail extends Dialog {
	private final Context context;
	private final String billCycle;
	private final String reportType;
	private final String key;
	private AdapterListServiceDetail adapter;
	private final List<AttributeObjectBOArray> lstData = new ArrayList<>();
	private final List<AttributeObjectBOArray> lstView = new ArrayList<>();
	private View footer;
	private boolean loadmore = true;
	private ListView lv;
	private EditText edtSearch;

	public DialogBonusSubDetail(Context context, String billCycle,
			String reportType, String key) {
		super(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
		this.context = context;
		this.billCycle = billCycle;
		this.reportType = reportType;
		this.key = key;
	}

	@SuppressLint("InflateParams")
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.layout_list_info_report_money_having);
		lv = (ListView) findViewById(R.id.lvInfoReportMoney);
		adapter = new AdapterListServiceDetail(context, lstView);
		lv.setAdapter(adapter);

		LayoutInflater inflater = MainActivity.getInstance()
				.getLayoutInflater();
		footer = inflater.inflate(R.layout.footer_layout, null, false);

		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				int lastitemScreen = firstVisibleItem + visibleItemCount;
				if ((lastitemScreen == totalItemCount) && (!loadmore)
						&& totalItemCount > 0) {
					loadmore = true;
					GetListCommRpDetail asy = new GetListCommRpDetail(context);
					asy.execute();
				}
			}
		});
		findViewById(R.id.btnClose).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						dismiss();

					}
				});

		edtSearch = (EditText) findViewById(R.id.edtSearch);
		edtSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				String text = arg0.toString().trim();
				lstView.clear();
				if (CommonActivity.isNullOrEmpty(text)) {
					lstView.addAll(lstData);

				} else {
					for (AttributeObjectBOArray item : lstData) {
						for (AttributeObjectBO bo : item.getItem()) {
							if (bo.getColumnDescription().toLowerCase()
									.contains(text.toLowerCase())
									|| bo.getColumnValue().toLowerCase()
											.contains(text.toLowerCase())) {
								lstView.add(item);
								break;
							}
						}
					}
				}
				adapter.notifyDataSetChanged();

			}
		});

	}

	public void getListData() {
		GetListCommRpDetail asy = new GetListCommRpDetail(context);
		asy.execute();
	}

	class GetListCommRpDetail extends AsyncTask<String, Void, BonusOutput> {

		private final Context context;
		private final ProgressDialog progress;

		public GetListCommRpDetail(Context context) {
			super();
			this.progress = new ProgressDialog(context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

			this.context = context;
		}

		@Override
		protected BonusOutput doInBackground(String... params) {
			return getListCommRpDetail();
		}

		@Override
		protected void onPostExecute(final BonusOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			lv.removeFooterView(footer);
			if (result.getErrorCode().equals("0")) {
				try {

					List<AttributeObjectBOArray> lstTmp = result
							.getLstAttributeObjectBO();
					if (!CommonActivity.isNullOrEmpty(lstTmp)) {

						for (AttributeObjectBOArray item : lstTmp) {
							for (int i = 0; i < item.getItem().size(); i++) {
								AttributeObjectBO bo = item.getItem().get(i);
								if (bo.getColumnName().equalsIgnoreCase("KEY")) {
									item.getItem().remove(i);
									item.setKey(bo);
									break;
								}
							}
						}
						String text = edtSearch.getText().toString().trim();
						if (CommonActivity.isNullOrEmpty(text)) {
							lstView.addAll(lstTmp);
						} else {
							for (AttributeObjectBOArray item : lstTmp) {
								for (AttributeObjectBO bo : item.getItem()) {
									if (bo.getColumnDescription().toLowerCase()
											.contains(text.toLowerCase())
											|| bo.getColumnValue()
													.toLowerCase()
													.contains(
															text.toLowerCase())) {
										lstView.add(item);
										break;
									}
								}
							}
						}

						lstData.addAll(lstTmp);

						adapter.notifyDataSetChanged();

						loadmore = false;
					} else {
						loadmore = true;
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					CommonActivity.BackFromLogin((Activity) context,
							result.getDescription(), ";report_revenue;");
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(context,
							result.getDescription(),
							context.getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private BonusOutput getListCommRpDetail() {

			String original = "";
			BonusOutput bonusOutput;
			String methodName = "getListCommRpDetail";
			try {

				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
//				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<billCycle>").append(billCycle).append("</billCycle>");
				rawData.append("<reportType>").append(reportType).append("</reportType>");
				rawData.append("<key>").append(key).append("</key>");
				rawData.append("<index>").append(lstData.size()).append("</index>");
				rawData.append("<size>" + 50 + "</size>");

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, context, "mbccs_" + methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Serializer serializer = new Persister();
				bonusOutput = serializer.read(BonusOutput.class, original);
				if (bonusOutput == null) {
					bonusOutput = new BonusOutput();
					bonusOutput.setDescription(context
							.getString(R.string.no_return_from_system));
					bonusOutput.setErrorCode(Constant.ERROR_CODE);
					return bonusOutput;
				} else {
					return bonusOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, methodName, e);
				bonusOutput = new BonusOutput();
				bonusOutput.setDescription(e.getMessage());
				bonusOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bonusOutput;
		}
	}
}
