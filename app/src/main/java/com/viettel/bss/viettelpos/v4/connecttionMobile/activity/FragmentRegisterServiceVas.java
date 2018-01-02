package com.viettel.bss.viettelpos.v4.connecttionMobile.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.GetPakageVasAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.VasAdapter;
import com.viettel.bss.viettelpos.v4.connecttionMobile.adapter.VasAdapter.OnChangeCheckedVas;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.CommonParseOutput;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.PakageVasBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.SubRelPro;
import com.viettel.bss.viettelpos.v4.connecttionMobile.handler.PakageVasHanlder;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentRegisterServiceVas extends AppCompatActivity
		implements OnClickListener, OnItemClickListener, OnChangeCheckedVas {

	// ===== define view ==========
	private EditText editIsdn;
    private GridView gridpakagecharge;
	private ArrayList<PakageVasBean> arrPakageVasBeans = new ArrayList<>();
	private GetPakageVasAdapter adapPakageVasAdapter = null;
	private FrameLayout frameLayout;
	private PakageVasBean pakageVasBean = new PakageVasBean();

	private LinearLayout lnregistermulti;
	private com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView lvVas;
	private Spinner spn_reason, spinner_service;

    private final List<Spin> lstReason = new ArrayList<>();
	private ProgressBar prbreason;

	private VasAdapter vasAdapter = null;
	private List<SubRelPro> lstSubRelPros = new ArrayList<>();

	private String isdn = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			isdn = bundle.getString("isdnKey", "");
		}

		setContentView(R.layout.layout_dktt_vas);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		unitView();

	}

	private void unitView() {
		editIsdn = (EditText) findViewById(R.id.edit_isdnacount);
		if (!CommonActivity.isNullOrEmpty(isdn)) {
			editIsdn.setText(isdn);
		}

        Button btnsearch = (Button) findViewById(R.id.btnsearch);
		btnsearch.setOnClickListener(FragmentRegisterServiceVas.this);
		gridpakagecharge = (GridView) findViewById(R.id.gridPakageVas);
		gridpakagecharge.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                for (PakageVasBean pakaBean : arrPakageVasBeans) {
					
					pakaBean.setCheck(false);
					
				}
				arrPakageVasBeans.get(arg2).setCheck(true);
				adapPakageVasAdapter.notifyDataSetChanged();
				for (PakageVasBean paBean : arrPakageVasBeans) {
					if (paBean.isCheck()) {
						pakageVasBean = paBean;
						break;
					}
				}

			}
		});
        Button btnregister = (Button) findViewById(R.id.btnregister);
		btnregister.setOnClickListener(FragmentRegisterServiceVas.this);
		frameLayout = (FrameLayout) findViewById(R.id.frGridPakage);
		frameLayout.setVisibility(View.GONE);

		lnregistermulti = (LinearLayout) findViewById(R.id.lnregistermulti);
		lnregistermulti.setVisibility(View.GONE);
		lvVas = (com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView) findViewById(
				R.id.lvVas);
		lvVas.setExpanded(true);
		spn_reason = (Spinner) findViewById(R.id.spn_reason);
        EditText edtsearch = (EditText) findViewById(R.id.edtsearch);
		edtsearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				vasAdapter.filter(arg0.toString());
			}
		});
        Button btnregisterorcancel = (Button) findViewById(R.id.btnregisterorcancel);
		btnregisterorcancel.setOnClickListener(FragmentRegisterServiceVas.this);
		spinner_service = (Spinner) findViewById(R.id.spinner_service);
		List<Spin> lstService = new ArrayList<>();
		lstService.add(new Spin("M", FragmentRegisterServiceVas.this.getString(R.string.mobile)));
		lstService.add(new Spin("U", FragmentRegisterServiceVas.this.getString(R.string.multiscreen)));
		Utils.setDataSpinner(FragmentRegisterServiceVas.this, lstService, spinner_service);

		prbreason = (ProgressBar) findViewById(R.id.prbreason);
		spinner_service.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Spin item = (Spin) arg0.getItemAtPosition(arg2);
				lnregistermulti.setVisibility(View.GONE);
				frameLayout.setVisibility(View.GONE);
				if (item != null) {
					if ("M".equals(item.getId())) {
					} else {

						if (CommonActivity.isNetworkConnected(FragmentRegisterServiceVas.this)) {
							AsyntaskGetReasonPos asyntaskGetReasonPos = new AsyntaskGetReasonPos(
									FragmentRegisterServiceVas.this);
							asyntaskGetReasonPos.execute();
						}
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});

		if (!CommonActivity.isNullOrEmpty(isdn)) {
			if (arrPakageVasBeans != null && !arrPakageVasBeans.isEmpty() && adapPakageVasAdapter != null) {
				arrPakageVasBeans.clear();
				adapPakageVasAdapter.notifyDataSetChanged();
			}
			GetPakageVasAsyn getPakageVasAsyn = new GetPakageVasAsyn(FragmentRegisterServiceVas.this);
			getPakageVasAsyn.execute(isdn);

		}
	}

	@Override
	public void onResume() {
		super.onResume();
        getSupportActionBar().setTitle(R.string.connectVas);
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.relaBackHome:
			FragmentRegisterServiceVas.this.onBackPressed();
			break;
		case R.id.btnsearch:
			if (CommonActivity.isNetworkConnected(FragmentRegisterServiceVas.this)) {
				Spin item = (Spin) spinner_service.getSelectedItem();
				if ("M".equals(item.getId())) {
					if (editIsdn.getText() != null && !editIsdn.getText().toString().isEmpty()) {
						if (arrPakageVasBeans != null && !arrPakageVasBeans.isEmpty() && adapPakageVasAdapter != null) {
							arrPakageVasBeans.clear();
							adapPakageVasAdapter.notifyDataSetChanged();
						}
						GetPakageVasAsyn getPakageVasAsyn = new GetPakageVasAsyn(FragmentRegisterServiceVas.this);
						getPakageVasAsyn.execute(CommonActivity.getStardardIsdnBCCS(editIsdn.getText().toString().trim()));
					} else {
						CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,
								getString(R.string.checkSubId), getString(R.string.app_name)).show();
					}
				} else {
					if (editIsdn.getText() != null && !editIsdn.getText().toString().isEmpty()) {
						if (lstSubRelPros != null && !lstSubRelPros.isEmpty() && vasAdapter != null) {
							lstSubRelPros = new ArrayList<>();
							vasAdapter.notifyDataSetChanged();
						}
						GetPrepareChangeVasAsyn getPrepareChangeVasAsyn = new GetPrepareChangeVasAsyn(
								FragmentRegisterServiceVas.this);
						getPrepareChangeVasAsyn.execute(item.getId(), editIsdn.getText().toString().trim());
					} else {
						CommonActivity
								.createAlertDialog(FragmentRegisterServiceVas.this,
										getString(R.string.must_input_isdn_account), getString(R.string.app_name))
								.show();
					}

				}

			} else {
				CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;

		case R.id.btnregister:

			if (CommonActivity.isNetworkConnected(FragmentRegisterServiceVas.this)) {

				if (pakageVasBean != null && pakageVasBean.getRelProductName() != null) {
					// MI0
					// 4. MI10
					// 5. MI30
					// 6. MI50
					// 7. MIMAX
					// 8. MIMIN
					// CAMAU;MIMAXCM
					if ("MI10".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "MI30".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "MI50".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "MI0".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "MIMAX".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "MIMIN".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "DMAX".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "DMAXXX".equalsIgnoreCase(pakageVasBean.getRelProductName())
							|| "Tichluy30K".equalsIgnoreCase(pakageVasBean.getRelProductName())) {
						
						CommonActivity.createDialog(FragmentRegisterServiceVas.this, getString(R.string.checkRegisVas),
								getString(R.string.app_name), getString(R.string.buttonOk),
								getString(R.string.buttonCancel), registerVasClick, null).show();

					} else {
						// MCA ------- IMUZIK
					}
				} else {
					CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,
							getString(R.string.checkIspakageVas), getString(R.string.app_name)).show();
				}

			} else {
				CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;

		case R.id.btnregisterorcancel:
			if (CommonActivity.isNetworkConnected(FragmentRegisterServiceVas.this)) {

				final Spin item = (Spin) spinner_service.getSelectedItem();

				if (CommonActivity.isNullOrEmpty(editIsdn.getText().toString())) {
					CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,
							getString(R.string.must_input_isdn_account), getString(R.string.app_name)).show();
					return;
				}

				if (StringUtils.CheckCharSpecical(editIsdn.getText().toString())) {
					CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,
							getString(R.string.checkaccountspecial), getString(R.string.app_name)).show();
					return;
				}
				final Spin reason = (Spin) spn_reason.getSelectedItem();
				if (reason == null) {
					CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.checkreasonCN),
							getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(reason.getId())) {
					CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.checkreasonCN),
							getString(R.string.app_name)).show();
					return;
				}

				final ArrayList<SubRelPro> arSubRelPros = new ArrayList<>();
				if (lstSubRelPros != null && lstSubRelPros.size() > 0) {
					for (SubRelPro subPro : lstSubRelPros) {
						if (subPro.isChecked()) {
							arSubRelPros.add(subPro);
						}
					}
				}

				// if (arSubRelPros != null && arSubRelPros.size() == 0) {
				// CommonActivity.createAlertDialog(
				// FragmentRegisterServiceVas.this,
				// getString(R.string.checkdkvas),
				// getString(R.string.app_name)).show();
				// return;
				// }

				OnClickListener changeVasClick = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						ChangeVasSubAsyn changeVasSubAsyn = new ChangeVasSubAsyn(FragmentRegisterServiceVas.this,
								arSubRelPros);
						changeVasSubAsyn.execute(editIsdn.getText().toString().trim(), item.getId(), reason.getId());
					}
				};

				CommonActivity.createDialog(FragmentRegisterServiceVas.this, getString(R.string.confirmdkvas),
						getString(R.string.app_name), getString(R.string.buttonOk), getString(R.string.buttonCancel),
						changeVasClick, null).show();

			} else {
				CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.errorNetwork),
						getString(R.string.app_name)).show();
			}
			break;
		default:
			break;
		}

	}

	// onclick register vas
    private final android.view.View.OnClickListener registerVasClick = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (pakageVasBean != null) {
				if("Tichluy30K".equals(pakageVasBean.getRelProductName())){
					new ChangeVasAsyn(FragmentRegisterServiceVas.this).execute(editIsdn.getText().toString(),
							pakageVasBean.getRelProductName(), "1");
				}else{
					new RegisterVasInternetAsyn(FragmentRegisterServiceVas.this).execute(editIsdn.getText().toString(),
							pakageVasBean.getRelProductName(), "1");
				}
			
			}
		}
	};

	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FragmentRegisterServiceVas.this, LoginActivity.class);
			startActivity(intent);
			if (MainActivity.getInstance() != null) {
				MainActivity.getInstance().finish();
			}
		}
	};

	// register vas internet
	private class RegisterVasInternetAsyn extends AsyncTask<String, Void, String> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public RegisterVasInternetAsyn(Context context) {
			this.context = context;
			progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return registerVas(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				// if (description == null || description.isEmpty()) {
				CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.dkvasthanhcong),
						getString(R.string.app_name), onclickRemoveVas).show();
				// } else {
				// CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,
				// description,
				// getString(R.string.app_name),onclickRemoveVas).show();
				// }

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		// register vas internet
		private String registerVas(String msisdn, String pkgName, String requestId) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_registerDataSendSMS");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:registerDataSendSMS>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<msIsdn>").append(CommonActivity.getStardardIsdn(msisdn));
				rawData.append("</msIsdn>");

				rawData.append("<pkgName>").append(pkgName);
				rawData.append("</pkgName>");

				rawData.append("<requestId>").append(requestId);
				rawData.append("</requestId>");

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:registerDataSendSMS>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentRegisterServiceVas.this,
						"mbccs_registerDataSendSMS");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return errorCode;
		}

	}

	// onclick registervas sucess
    private final OnClickListener onclickRemoveVas = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (arrPakageVasBeans != null && arrPakageVasBeans.size() > 0) {
				arrPakageVasBeans.clear();
				if (adapPakageVasAdapter != null) {
					adapPakageVasAdapter.notifyDataSetChanged();
				}
				frameLayout.setVisibility(View.GONE);
			}

		}
	};

	private final OnClickListener onclickReLoadVas = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (lstSubRelPros != null && lstSubRelPros.size() > 0) {
				lstSubRelPros = new ArrayList<>();
				if (vasAdapter != null) {
					vasAdapter.notifyDataSetChanged();
				}
				Spin item = (Spin) spinner_service.getSelectedItem();
				GetPrepareChangeVasAsyn getPrepareChangeVasAsyn = new GetPrepareChangeVasAsyn(
						FragmentRegisterServiceVas.this);
				getPrepareChangeVasAsyn.execute(item.getId(), editIsdn.getText().toString().trim());

				AsyntaskGetReasonPos asyntaskGetReasonPos = new AsyntaskGetReasonPos(FragmentRegisterServiceVas.this);
				asyntaskGetReasonPos.execute();

				// if(lstReason != null && lstReason.size() > 0){
				// for (Spin spin : lstReason) {
				// if(CommonActivity.isNullOrEmpty(item.getId())){
				// spn_reason.setSelection(lstReason.indexOf(spin));
				// break;
				// }
				// }
				// }

			}

		}
	};

	private class GetPakageVasAsyn extends AsyncTask<String, Void, ArrayList<PakageVasBean>> {

		final ProgressDialog progress;
		private Context context = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public GetPakageVasAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<PakageVasBean> doInBackground(String... arg0) {
			return getLstPakageVas(arg0[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<PakageVasBean> result) {
			CommonActivity.hideKeyboard(editIsdn, context);
			progress.dismiss();
			if ("0".equals(errorCode)) {

				if (result != null && result.size() > 0) {
					frameLayout.setVisibility(View.VISIBLE);
					arrPakageVasBeans = result;
					adapPakageVasAdapter = new GetPakageVasAdapter(arrPakageVasBeans, FragmentRegisterServiceVas.this);
					gridpakagecharge.setAdapter(adapPakageVasAdapter);
				} else {
					frameLayout.setVisibility(View.GONE);
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private ArrayList<PakageVasBean> getLstPakageVas(String subId) {
			ArrayList<PakageVasBean> arrPakageVasBeans = new ArrayList<>();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getVasInfo");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getVasInfo>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<isdn>").append(subId);
				rawData.append("</isdn>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getVasInfo>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.e("Send evelop", envelope);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentRegisterServiceVas.this,
						"mbccs_getVasInfo");
				Log.e("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);

				// parse xml

				PakageVasHanlder hanlder = (PakageVasHanlder) CommonActivity.parseXMLHandler(new PakageVasHanlder(),
						original);
				output = hanlder.getItem();
				if (output.getToken() != null && !output.getToken().isEmpty()) {
					Session.setToken(output.getToken());
				}
				if (output.getErrorCode() != null) {
					errorCode = output.getErrorCode();
				}
				if (output.getDescription() != null) {
					description = output.getDescription();
				}
				arrPakageVasBeans = hanlder.getLstData();
			} catch (Exception e) {
				Log.e(FragmentRegisterServiceVas.class.getName(), "Exception", e);
			}
			return arrPakageVasBeans;
		}

	}

	private class AsyntaskGetReasonPos extends AsyncTask<String, Void, ArrayList<Spin>> {
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public AsyntaskGetReasonPos(Context context) {
			this.context = context;

			prbreason.setVisibility(View.VISIBLE);

			if (lstReason != null && lstReason.size() > 0) {
				lstReason.clear();
			}
		}

		@Override
		protected ArrayList<Spin> doInBackground(String... params) {
			return getReasonInfoPos();
		}

		@Override
		protected void onPostExecute(ArrayList<Spin> result) {
			prbreason.setVisibility(View.GONE);
			if ("0".equals(errorCode)) {
				lstReason.add(new Spin("", getString(R.string.txt_select_reason)));
				lstReason.addAll(result);
				Utils.setDataSpinner(FragmentRegisterServiceVas.this, lstReason, spn_reason);

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					lstReason.add(new Spin("-1", getString(R.string.txt_select_reason)));
					Utils.setDataSpinner(FragmentRegisterServiceVas.this, lstReason, spn_reason);
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<Spin> getReasonInfoPos() {

			Spin serviceItem = (Spin) spinner_service.getSelectedItem();
			ArrayList<Spin> lstReason = null;
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListReasonByTelServicePos");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListReasonByTelServicePos>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<actionCode>" + 102 + "</actionCode>");
				rawData.append("<serviceType>").append(serviceItem.getId()).append("</serviceType>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:getListReasonByTelServicePos>");

				Log.i("LOG", "raw data" + rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("LOG", "Send evelop" + envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentRegisterServiceVas.this,
						"mbccs_getListReasonByTelService");
				Log.i("LOG", "Respone:  " + response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("LOG", "Responseeeeeeeeee Original  " + response);

				// parser

				lstReason = parserListGroupPos(original);

			} catch (Exception e) {
				e.printStackTrace();
			}
			return lstReason;
		}

		public ArrayList<Spin> parserListGroupPos(String original) {
			ArrayList<Spin> lstReason = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null;
			for (int i = 0; i < nl.getLength(); i++) {
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstReasonPos");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					Spin spin = new Spin();
					spin.setValue(parse.getValue(e1, "codeName"));
					Log.d("LOG", "value: " + spin.getValue());
					spin.setId(parse.getValue(e1, "reasonId"));
					Log.d("LOG", "Idddd: " + spin.getId());
					lstReason.add(spin);
				}
			}

			return lstReason;
		}

	}

	// }
	private class GetPrepareChangeVasAsyn extends AsyncTask<String, Void, CommonParseOutput> {
		final ProgressDialog progress;
		private Context context = null;
		String errorCode = "";
		String description = "";

		public GetPrepareChangeVasAsyn(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected CommonParseOutput doInBackground(String... arg0) {
			return getLstPakageVasMulti(arg0[0], arg0[1]);
		}

		@Override
		protected void onPostExecute(CommonParseOutput result) {
			CommonActivity.hideKeyboard(editIsdn, context);
			progress.dismiss();
			if ("0".equals(errorCode)) {

				if (result != null && result.getLstSubRelPros().size() > 0) {
					lnregistermulti.setVisibility(View.VISIBLE);
					lstSubRelPros = result.getLstSubRelPros();
					vasAdapter = new VasAdapter(FragmentRegisterServiceVas.this, result.getLstSubRelPros(),
							FragmentRegisterServiceVas.this);
					lvVas.setAdapter(vasAdapter);
				} else {
					lnregistermulti.setVisibility(View.GONE);
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {

					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		private CommonParseOutput getLstPakageVasMulti(String serviceType, String account) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_prepareChangeVas");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:prepareChangeVas>");
				rawData.append("<cmMobileInput>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));
				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<account>").append(account);
				rawData.append("</account>");
				rawData.append("</cmMobileInput>");
				rawData.append("</ws:prepareChangeVas>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.e("Send evelop", envelope);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentRegisterServiceVas.this,
						"mbccs_prepareChangeVas");
				Log.e("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.d("original", original);

				// parse xml
				Serializer serializer = new Persister();
				CommonParseOutput commonParseOutput = serializer.read(CommonParseOutput.class, original);

				errorCode = commonParseOutput.getErrorCode();
				description = commonParseOutput.getDescription();

				return commonParseOutput;
			} catch (Exception e) {
				Log.e(FragmentRegisterServiceVas.class.getName(), "Exception", e);
			}
			return null;
		}

	}

	@Override
	public void onChangeCheckedVas(SubRelPro subRelPro) {
		for (SubRelPro item : lstSubRelPros) {
			if (subRelPro.getRelProductCode().equals(item.getRelProductCode())) {
				item.setChecked(!item.isChecked());
				break;
			}
		}

	}

	private class ChangeVasSubAsyn extends AsyncTask<String, Void, String> {

		private Activity mActivity = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		final ProgressDialog progress;
		private ArrayList<SubRelPro> arrSubRelPros = new ArrayList<>();

		public ChangeVasSubAsyn(Activity context, ArrayList<SubRelPro> lsSubRelPros) {
			this.progress = new ProgressDialog(context);
			// check font

			this.progress.setMessage(context.getResources().getString(R.string.processing));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

			this.arrSubRelPros = lsSubRelPros;

		}

		@Override
		protected String doInBackground(String... arg0) {
			return changeVas(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if ("0".equals(errorCode)) {

				String des = description;
				if (CommonActivity.isNullOrEmpty(des)) {
					des = getString(R.string.dkvasthanhcong);
				}
				CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, des, getString(R.string.app_name),
						onclickReLoadVas).show();
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							FragmentRegisterServiceVas.this.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = FragmentRegisterServiceVas.this.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, description,
							FragmentRegisterServiceVas.this.getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private String changeVas(String account, String serviceType, String reasonId) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changeVasSub");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changeVasSub>");
				rawData.append("<cmMobileInput>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");

				rawData.append("<serviceType>").append(serviceType);
				rawData.append("</serviceType>");
				rawData.append("<account>").append(account);
				rawData.append("</account>");
				rawData.append("<reasonId>").append(reasonId);
				rawData.append("</reasonId>");

				if (arrSubRelPros != null && !arrSubRelPros.isEmpty()) {
					for (SubRelPro subRelPro : arrSubRelPros) {
						rawData.append("<itemServiceScreen>").append(subRelPro.getRelProductCode());
						rawData.append("</itemServiceScreen>");
					}
				}

				rawData.append("</cmMobileInput>");
				rawData.append("</ws:changeVasSub>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentRegisterServiceVas.this,
						"mbccs_changeVasSub");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					description = parse.getValue(e2, "description");
					Log.d("errorCode", errorCode);

				}
			} catch (Exception e) {
				Log.d("mbccs_changeVasSub", e.toString() + "description error", e);
			}

			return errorCode;

		}
	}
	
	
	
	private class ChangeVasAsyn extends AsyncTask<String, Void, String> {
		final ProgressDialog progress;
		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";

		public ChangeVasAsyn(Context context) {
			this.context = context;
			progress = new ProgressDialog(context);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(R.string.waitting));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected String doInBackground(String... arg0) {
			return registerVas(arg0[0], arg0[1], arg0[2]);
		}

		@Override
		protected void onPostExecute(String result) {
			progress.dismiss();
			if ("0".equals(errorCode)) {
				// if (description == null || description.isEmpty()) {
				CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this, getString(R.string.dkvasthanhcong),
						getString(R.string.app_name), onclickRemoveVas).show();
				// } else {
				// CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,
				// description,
				// getString(R.string.app_name),onclickRemoveVas).show();
				// }

			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity.createAlertDialog((Activity) context, description,
							context.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(FragmentRegisterServiceVas.this,context.getString(R.string.dk_faile) + " "+ description,
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}

		}

		// register vas internet
		private String registerVas(String msisdn, String pkgName, String requestId) {
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_changeVas");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:changeVas>");
				rawData.append("<input>");
				HashMap<String, String> paramToken = new HashMap<>();
				paramToken.put("token", Session.getToken());
				rawData.append(input.buildXML(paramToken));

				rawData.append("<isdn>").append(CommonActivity.getStardardIsdn(msisdn));
				rawData.append("</isdn>");

				rawData.append("<lstRegister>").append(pkgName);
				rawData.append("</lstRegister>");
				rawData.append("<reasonId>" + 146);
				rawData.append("</reasonId>");
				rawData.append("<pkgName>").append(pkgName);
				rawData.append("</pkgName>");
				
				rawData.append("<requiredRoleMap>");
				
				SharedPreferences preferences = FragmentRegisterServiceVas.this.getSharedPreferences(
						Define.PRE_NAME, Activity.MODE_PRIVATE);
				String name = preferences.getString(Define.KEY_MENU_NAME, "");
				if(name.contains(";menu.changevas.pre;")){
					rawData.append("<values>" + Constant.BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TT);
					rawData.append("</values>");
				}
				if(name.contains(";menu.changevas.pos;")){
					rawData.append("<values>" + Constant.BCCS2_SALE_SAUBAN_DIDONG_TDVAS_MB_TS);
					rawData.append("</values>");
				}
				rawData.append("</requiredRoleMap>");

				rawData.append("</input>");
				rawData.append("</ws:changeVas>");

				Log.i("RowData", rawData.toString());

				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentRegisterServiceVas.this,
						"mbccs_changeVas");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Document doc = parse.getDomElement(original);
				NodeList nl = doc.getElementsByTagName("return");
				for (int i = 0; i < nl.getLength(); i++) {
					Element e2 = (Element) nl.item(i);
					errorCode = parse.getValue(e2, "errorCode");
					Log.d("errorCode", errorCode);
					description = parse.getValue(e2, "description");
					Log.d("description", description);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return errorCode;
		}

	}
	

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

	}

}
