
package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransAction;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.HashMap;
import java.util.List;

public class FragmentConfirmNote extends FragmentCommon {

	private ListView lvItem;
	private ArrayAdapter<StockTransAction> adapter;
	private List<StockTransAction> lstData;
	private StockTransAction stockTransAction;
	private int currentPosition = 0;

    private static final int REQUEST_CONFIRM_NOTE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		idLayout = R.layout.sale_confirm_note_layout;
	}

	@Override
	protected void unit(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.tvConfirmNoteTitle);
		lvItem = (ListView) view.findViewById(R.id.lvNoteItem);

		new GetLstStockTransAsyn(getActivity()).execute();

	}

	@Override
	protected void setPermission() {

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setTitleActionBar(R.string.note_list);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CONFIRM_NOTE) {
			if (resultCode == Activity.RESULT_OK) {
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					Long stockTransId = bundle.getLong("stockTransId");
					for (int i = 0; i < lstData.size(); i++) {
						StockTransAction item = lstData.get(i);
						if (item.getStockTransId().compareTo(stockTransId) == 0) {
							lstData.remove(i);
							adapter.notifyDataSetChanged();
							return;
						}
					}
				}
			}
		}
	}

	// Ham lay danh sach giao dich xac nhan nhap hang
	private class GetLstStockTransAsyn extends
			AsyncTask<String, Void, ParseOuput> {

		final ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetLstStockTransAsyn(Context context) {
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
		protected ParseOuput doInBackground(String... arg0) {
			return getLstStockTrans();
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {

			progress.dismiss();

			if ("0".equals(errorCode)) {
				if (result != null) {
					if (result.getLstStockTransActions() != null) {
						lstData = result.getLstStockTransActions();

						adapter = new ArrayAdapter<StockTransAction>(
								getActivity(), R.layout.bhld_item,
								R.id.tvStaffCode, lstData) {
							@Override
							public View getView(int position, View convertView,
									ViewGroup parent) {
								LayoutInflater inflater = getActivity()
										.getLayoutInflater();
								View view = inflater.inflate(
										R.layout.bhld_item, parent, false);

								// View view = super.getView(position,
								// convertView, parent);
								TextView text1 = (TextView) view
										.findViewById(R.id.tvStaffCode);
								TextView text2 = (TextView) view
										.findViewById(R.id.tvStaffName);
								text1.setText(lstData.get(position)
										.getExpCode());
//								Date expDate = lstData.get(position)
//										.getExportDate();
								String createDateTime = lstData.get(position).getCreateDatetime();
								if (createDateTime != null) {
									text2.setText(DateTimeUtils
											.convertDateSoap(createDateTime));
								}
								return view;
							}
						};
						
						lvItem.setAdapter(adapter);
						lvItem.setOnItemClickListener(new OnItemClickListener() {
							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int position, long arg3) {
								stockTransAction = lstData.get(position);
								currentPosition = position + 1;
								
								new GetLstStockTransDetailAsyn(getActivity()).execute();
							}
						});
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.noConfirmNote),
								getResources().getString(R.string.app_name));
						dialog.show();
					}
				} else {
					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.no_data),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ParseOuput getLstStockTrans() {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLstStockTrans");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstStockTrans>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				paramToken.put("toOwnerCode", Session.userName);
				

				rawData.append(input.buildXML(paramToken));

				rawData.append("</input>");
				rawData.append("</ws:getLstStockTrans>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getLstStockTrans");

				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
				}

				return parseOuput;
			} catch (Exception e) {
				Log.i("getLstStockTrans", e.toString());
			}
			return null;
		}

	}
	
	
	// Ham lay chi tiet giao dich
	private class GetLstStockTransDetailAsyn extends
			AsyncTask<String, Void, ParseOuput> {

		final ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetLstStockTransDetailAsyn(Context context) {
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
		protected ParseOuput doInBackground(String... arg0) {
			return getLstStockTransDetail();
		}

		@Override
		protected void onPostExecute(final ParseOuput result) {

			progress.dismiss();

			if ("0".equals(errorCode)) {
				if (result != null) {
					if (result.getLstStockTransDetails() != null) {
						Bundle bundle = new Bundle();
						bundle.putSerializable("parseOuput",
								result);
						bundle.putSerializable("stockTransAction",
								stockTransAction);
						bundle.putInt("position", currentPosition);

						FragmentConfirmNoteDetail fragment = new FragmentConfirmNoteDetail();

						fragment.setArguments(bundle);
						fragment.setTargetFragment(FragmentConfirmNote.this,
								REQUEST_CONFIRM_NOTE);
						ReplaceFragment.replaceFragment(getActivity(),
								fragment, false);
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.not_found_detail_transaction),
								getResources().getString(R.string.app_name));
						dialog.show();						
					}
				} else {
					if (description != null && !description.isEmpty()) {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(), description, getResources()
										.getString(R.string.app_name));
						dialog.show();
					} else {
						Dialog dialog = CommonActivity.createAlertDialog(
								getActivity(),
								getResources().getString(R.string.no_data),
								getResources().getString(R.string.app_name));
						dialog.show();
					}

				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)
						&& description != null && !description.isEmpty()) {

					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ParseOuput getLstStockTransDetail() {
			String original = null;
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getLstStockTransDetail");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getLstStockTransDetail>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
//				paramToken.put("toOwnerCode", Session.userName);
				paramToken.put("stockTransId", String.valueOf(stockTransAction.getStockTransId()));

				rawData.append(input.buildXML(paramToken));

				rawData.append("</input>");
				rawData.append("</ws:getLstStockTransDetail>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getLstStockTransDetail");

				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee", original);

				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
				}

				return parseOuput;
			} catch (Exception e) {
				Log.i("getLstStockTransDetail", e.toString());
			}
			return null;
		}

	}
	
	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(getActivity(), LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
			MainActivity.getInstance().finish();

		}
	};

}
