package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;




public class FragmentCreateAddress extends Fragment implements OnClickListener {

	
	public static final String PREFS_NAME = "ADDRESS_PRECINT"; 
	private View mView;
	private Spinner spProvincial;
	private Spinner spDistrict;
	private Spinner spPricent;
	private Spinner spGroup;
	private EditText edtFlow;
	private EditText edtHomeNumber;
    private ArrayList<AreaObj> mListTP = new ArrayList<>();
	private ArrayList<AreaObj> mListQuan = new ArrayList<>();
	private ArrayList<AreaObj> mListXa = new ArrayList<>();
	private ArrayList<AreaObj> mListGroup = new ArrayList<>();
	private InfrastrucureDB mInfrastrucureDB;
	private Activity activity;
	private String strProvince;
	private String strDistris;
	private AreaObj areaGroup;
	private AreaObj areaPrecint;
	private String areaStreet;
	private String areaHomeNumber;
	
	
	
	
	private Button btnHome;
    private TextView txtNameActionBar;
	private long type; // 1: update, con lai la them moi

	@Override
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		activity = getActivity();
		mInfrastrucureDB = new InfrastrucureDB(activity);
        Bundle mBundle = getArguments();
		if (mBundle != null) {
			strProvince = mBundle.getString("strProvince");
			strDistris = mBundle.getString("strDistris");
			areaPrecint = (AreaObj) mBundle.getSerializable("areaPrecint");
			areaGroup = (AreaObj) mBundle.getSerializable("areaGroup");
			
			areaStreet = mBundle.getString("areaFlow");
			areaHomeNumber = mBundle.getString("areaHomeNumber"); 
			type = mBundle.getLong("TYPE"); 
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_create_address, container, false);
			spProvincial = (Spinner) mView.findViewById(R.id.spProvincial);
			spDistrict = (Spinner) mView.findViewById(R.id.spDistrict);
			spPricent = (Spinner) mView.findViewById(R.id.spPricent);
			spGroup = (Spinner) mView.findViewById(R.id.sqGroupAddress);
			edtFlow = (EditText) mView.findViewById(R.id.edtFlowAddress);
			edtHomeNumber = (EditText) mView.findViewById(R.id.edtHomeNumber);
            Button btnCancel = (Button) mView.findViewById(R.id.btn_cancel);
            Button btnOk = (Button) mView.findViewById(R.id.btn_ok);

			btnCancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					getActivity().onBackPressed();
				}
			});

			btnOk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					doGetAddress();
				}
			});
		}
		
		if (areaStreet != null && areaStreet.length() > 0) {
			edtFlow.setText(areaStreet);
		}
		
		if (areaHomeNumber != null && areaHomeNumber.length() > 0) {
			edtHomeNumber.setText(areaHomeNumber);
		}
		
		
		addListProvinceSpinner();
		addListDistristSpinner();
		return mView;
	}

	private void addListProvinceSpinner() {
		mListTP = mInfrastrucureDB.getLisProvince();
		AdapterProvinceSpinner adapterTp = new AdapterProvinceSpinner(mListTP, activity);
		spProvincial.setAdapter(adapterTp);
		for (int i = 0; i < mListTP.size(); i++) {
			AreaObj areaObject = mListTP.get(i);
			if (areaObject.getProvince() != null && areaObject.getProvince().equals(strProvince)) {
				spProvincial.setSelection(i);
				spProvincial.setEnabled(false);
			}
		}
	}

	private void addListDistristSpinner() {
		
		Log.d("Log", "Distris code:" + strDistris);
		
		mListQuan = mInfrastrucureDB.getLisDistrict(strProvince);
		AdapterProvinceSpinner adapterDistrist = new AdapterProvinceSpinner(mListQuan, activity);
		spDistrict.setAdapter(adapterDistrist);

		for (int i = 0; i < mListQuan.size(); i++) {
			AreaObj areaObject = mListQuan.get(i);
			if (areaObject.getDistrict() != null && areaObject.getDistrict().equals(strDistris)) {
				spDistrict.setSelection(i);
				spDistrict.setEnabled(false);
				String parentCodePrecinct = strProvince + strDistris;
				addListPrecintSpinner(parentCodePrecinct);
			}
		}
	}

	private void addListPrecintSpinner(String parentCode) {
		mListXa = mInfrastrucureDB.getListPrecinct(parentCode);
		AreaObj areaObject = new AreaObj();
		areaObject.setName(getResources().getString(R.string.tv_select_precint));
		areaObject.setPrecinct("-1111");
		mListXa.add(0, areaObject);
		AdapterProvinceSpinner adapterPricinct = new AdapterProvinceSpinner(mListXa, activity);
		spPricent.setAdapter(adapterPricinct);
		
		if(type == 1){
			if (areaPrecint != null) {
				for (int i = 0; i < mListXa.size(); i++) {
					AreaObj areaPrecintObject = mListXa.get(i);
					if(areaPrecintObject.getPrecinct() != null && areaPrecint.getPrecinct() != null){
						if (areaPrecintObject.getPrecinct().equals(areaPrecint.getPrecinct())) {
							spPricent.setSelection(i); 
						}
					}
				}
			} 
		}else {
			if (areaPrecint != null) {
				for (int i = 0; i < mListXa.size(); i++) {
					AreaObj areaPrecintObject = mListXa.get(i);
					if (areaPrecintObject.getName().equals(areaPrecint.getName())) {
						spPricent.setSelection(i); 
					}
				}
			}  
		}
		
		
		

		spPricent.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) { 
				if (arg2 != 0) {
					Context context = getActivity();  
					AreaObj areaObjectPrecint = mListXa.get(arg2);
					String parentCodeGroup = strProvince + strDistris + areaObjectPrecint.getPrecinct();
					addListGroupSpinner(parentCodeGroup);
				} 
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	private void addListGroupSpinner(String parentCode) {
		GetListGroupAdressAsyncTask getlistGroupAsynctask = new GetListGroupAdressAsyncTask(getActivity());
		SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
		String original = sharedPref.getString(parentCode, null);
		Log.d("addListGroupSpinner", "check original save sharePref: " + original);
		if (original != null && original.length() > 0) {
			Log.d("addListGroupSpinner", "check original get  frome sharePref: ");
			getlistGroupAsynctask.progress.dismiss();
			ArrayList<AreaObj> result = getlistGroupAsynctask.parserListGroup(original);
			getlistGroupAsynctask.updatePrecintSpinner(result);  
		} else {
			Log.d("addListGroupSpinner", "check original get  frome Internet");
			getlistGroupAsynctask.execute(parentCode);
		} 
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.createAddress);
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.relaBackHome:
			getActivity().onBackPressed();
			break;
		default:
			break;
		}
	}

	private void doGetAddress() {
		
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE); 
		View view = activity.getCurrentFocus();
		if (view != null) { 
		 imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		Intent i = new Intent(); 
		int indexProvincial = spProvincial.getSelectedItemPosition();
		int indexDistrict = spDistrict.getSelectedItemPosition();
		int indexPrecint = spPricent.getSelectedItemPosition();
		int indexGroup = spGroup.getSelectedItemPosition();

		AreaObj areaProvicial = null;
		AreaObj areaDistrist = null;
		AreaObj areaPrecint = null;
		AreaObj areaGroup = null;

		if (mListTP.size() > 0) {
			areaProvicial = mListTP.get(indexProvincial);
		}

		if (mListQuan.size() > 0) {
			areaDistrist = mListQuan.get(indexDistrict);
		}

		if (mListXa.size() > 0) {
			areaPrecint = mListXa.get(indexPrecint);
		}

		if (mListGroup.size() > 0) {
			areaGroup = mListGroup.get(indexGroup); 
		}
		
		String areaFlow = edtFlow.getText().toString().trim();
		String areaHomeNumber = edtHomeNumber.getText().toString().trim();
		
		Dialog dialog = null;
		if (areaProvicial == null) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.message_pleass_input_province),getString(R.string.app_name));
		} else if (areaDistrist == null) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.message_pleass_input_distrist),getString(R.string.app_name));
		} else if (areaPrecint == null || areaPrecint.getName().equals(getString(R.string.tv_select_precint))) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.message_pleass_input_precint),getString(R.string.app_name));
		} else if (areaGroup == null) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.message_pleass_input_group),getString(R.string.app_name));
		} else if (areaFlow.length() == 0) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.message_pleass_input_street),getString(R.string.app_name));
		
			edtFlow.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		} else if (StringUtils.CheckCharSpecical(areaFlow)) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.msg_special_street), getString(R.string.app_name));
		
			edtFlow.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
		} else if (areaHomeNumber.length() == 0) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.message_pleass_input_home),getString(R.string.app_name));
			edtHomeNumber.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0); 
		}  else if (StringUtils.CheckCharSpecical(areaHomeNumber)) {
			dialog = CommonActivity.createAlertDialog(activity, getString(R.string.msg_special_home), getString(R.string.app_name));
		
			edtHomeNumber.requestFocus();
			imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0); 
		}
		
		if (dialog != null) {
			dialog.show();
			return;
		}
		

		i.putExtra("areaProvicial", areaProvicial);
		i.putExtra("areaDistrist", areaDistrist);
		i.putExtra("areaPrecint", areaPrecint);
		i.putExtra("areaGroup", areaGroup);
		i.putExtra("areaFlow", areaFlow);
		i.putExtra("areaHomeNumber", areaHomeNumber);
		getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
		getActivity().onBackPressed();
	}

	/// request list group
	
	// move login
    private final OnClickListener moveLogInAct = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				getActivity().finish();
				MainActivity.getInstance().finish();

			}
		};

	@SuppressWarnings("unused")
	private class GetListGroupAdressAsyncTask extends AsyncTask<String, Void, ArrayList<AreaObj>> {

		private Context context = null;
		final XmlDomParse parse = new XmlDomParse();
		String errorCode = ""; 
		String description = "";
		final ProgressDialog progress;

		@SuppressWarnings("unused")
		public GetListGroupAdressAsyncTask(Context context) {
			this.context = context;
			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing)); 
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {
			
			return getListGroupAddress(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {
			
			super.onPostExecute(result);
			progress.dismiss();
			
			if (errorCode.equals("0")) { 
				if (result != null && result.size() > 0) {
					updatePrecintSpinner(result);
				} else { 
					CommonActivity.createAlertDialog(getActivity(),getString(R.string.ko_co_dl_precint),getString(R.string.app_name)).show(); 
				} 
			}  else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									(Activity) context,
									description,
									context.getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if(description == null || description.isEmpty()){
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
			ArrayList<AreaObj> listGroupAdress = null;
			String original = ""; 
			try {
				
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_getListArea");
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:getListArea>");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<parentCode>").append(parentCode).append("</parentCode>");
				rawData.append("</input>");
				rawData.append("</ws:getListArea>");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL); 
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, getActivity(),
						"mbccs_getListArea");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);
				
				SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
				Editor editor = sharedPref.edit();
				editor.putString(parentCode, original);
			    editor.commit();  
			    
				// ==== parse xml list ip
			    listGroupAdress =  parserListGroup(original);
				
			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			} 
			return listGroupAdress;
		}
		
		public ArrayList<AreaObj> parserListGroup (String original) {
			ArrayList<AreaObj> listGroupAdress = new ArrayList<>();
			Document doc = parse.getDomElement(original);
			NodeList nl = doc.getElementsByTagName("return");
			NodeList nodechild = null; 
			for (int i = 0; i < nl.getLength(); i++) { 
				Element e2 = (Element) nl.item(i);
				errorCode = parse.getValue(e2, "errorCode");
				description = parse.getValue(e2, "description");
				Log.d("errorCode", errorCode);
				nodechild = doc.getElementsByTagName("lstArea");
				for (int j = 0; j < nodechild.getLength(); j++) {
					Element e1 = (Element) nodechild.item(j);
					AreaObj areaObject = new AreaObj(); 
					areaObject.setName(parse.getValue(e1, "name"));
					Log.d("name area: ",areaObject.getName());
					areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
					listGroupAdress.add(areaObject);
				} 
			} 
			
			return listGroupAdress;
		}
		
		public void updatePrecintSpinner(ArrayList<AreaObj> result) {
			mListGroup = result; 
			AdapterProvinceSpinner adapterGroup = new AdapterProvinceSpinner(mListGroup, activity);
			spGroup.setAdapter(adapterGroup); 
			
			if(type == 1){
				if (areaGroup != null) {
					for (int i = 0; i < mListGroup.size(); i++) {
						AreaObj areaGroupObject = mListGroup.get(i);
						if (areaGroupObject.getAreaCode().equals(areaGroup.getAreaCode())) {
							spGroup.setSelection(i); 
						}
					}
				} 
			}else{
			
				if (areaGroup != null) {
					for (int i = 0; i < mListGroup.size(); i++) {
						AreaObj areaGroupObject = mListGroup.get(i);
						if (areaGroupObject.getName().equals(areaGroup.getName())) {
							spGroup.setSelection(i); 
						}
					}
				} 
			}
			
			spGroup.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
		}

	}

}
