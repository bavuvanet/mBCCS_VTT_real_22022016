package com.viettel.bss.viettelpos.v4.connecttionService.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.GPSTracker;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetDistrictAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetPrecinctAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetProvinceAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetStreetBlockAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentSearchLocation extends GPSTracker implements
		OnClickListener, OnItemClickListener {

	// == unitview
	private EditText edtsearch;
	private ListView lvpakage;
	private GetProvinceAdapter getProvinceAdapter;
	private GetDistrictAdapter getDistrictAdapter;
	private GetPrecinctAdapter getPrecinctAdapter;
	private GetStreetBlockAdapter getStreetBlockAdapter;

	private ArrayList<AreaBean> arrProvinces = new ArrayList<>();
	private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
	private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
	private List<AreaObj> arrStreetBlock = new ArrayList<>();

	private String province = "";
	private String district = "";
	private String precinct = "";

	private String check = "";
	private Button btnRefreshStreetBlock;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.connectionmobile_layout_lst_pakage);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		UnitView();
		Bundle mBundle = this.getIntent().getExtras();
		if (mBundle != null) {
			arrProvinces = (ArrayList<AreaBean>) mBundle
					.getSerializable("arrProvincesKey");
			arrDistrict = (ArrayList<AreaBean>) mBundle
					.getSerializable("arrDistrictKey");
			arrPrecinct = (ArrayList<AreaBean>) mBundle
					.getSerializable("arrPrecinctKey");

			province = mBundle.getString("province", "");
			district = mBundle.getString("district", "");
			precinct = mBundle.getString("precinct", "");

			check = mBundle.getString("checkKey", "");
			switch (Integer.parseInt(check)) {
			case 1:
				if (arrProvinces != null && !arrProvinces.isEmpty()) {
					getProvinceAdapter = new GetProvinceAdapter(arrProvinces,
							FragmentSearchLocation.this);
					lvpakage.setAdapter(getProvinceAdapter);
					getProvinceAdapter.notifyDataSetChanged();
				}
				break;
			case 2:
				if (arrDistrict != null && !arrDistrict.isEmpty()) {
					getDistrictAdapter = new GetDistrictAdapter(arrDistrict,
							FragmentSearchLocation.this);
					lvpakage.setAdapter(getDistrictAdapter);
					getDistrictAdapter.notifyDataSetChanged();
				}
				break;
			case 3:
				if (arrPrecinct != null && !arrPrecinct.isEmpty()) {
					getPrecinctAdapter = new GetPrecinctAdapter(arrPrecinct,
							FragmentSearchLocation.this);
					lvpakage.setAdapter(getPrecinctAdapter);
					getPrecinctAdapter.notifyDataSetChanged();
				}
				break;
			case 4:
				if (arrStreetBlock != null && !arrStreetBlock.isEmpty()) {
					getStreetBlockAdapter = new GetStreetBlockAdapter(
							arrStreetBlock, FragmentSearchLocation.this);
					lvpakage.setAdapter(getStreetBlockAdapter);
					getStreetBlockAdapter.notifyDataSetChanged();
				} else {
					GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
							FragmentSearchLocation.this, null);

					async.execute(province + district + precinct);
				}
				btnRefreshStreetBlock.setVisibility(View.VISIBLE);
				btnRefreshStreetBlock
						.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								new CacheDatabaseManager(MainActivity
										.getInstance()).insertCacheStreetBlock(
										null, province + district + precinct);
								GetListGroupAdressAsyncTask async = new GetListGroupAdressAsyncTask(
										FragmentSearchLocation.this, null);

								async.execute(province + district + precinct);
							}
						});
				break;
			default:
				break;
			}
		}
	}

	private void UnitView() {
		edtsearch = (EditText) findViewById(R.id.edtsearch);
		lvpakage = (ListView) findViewById(R.id.lstpakage);
		lvpakage.setOnItemClickListener(this);
		lvpakage.setTextFilterEnabled(true);
		TextView txtinfo = (TextView) findViewById(R.id.txtinfo);
		txtinfo.setText(getString(R.string.createAddress));
		edtsearch.setHint(R.string.createAddress);
		edtsearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String input = edtsearch.getText().toString()
						.toLowerCase(Locale.getDefault());

				switch (Integer.parseInt(check)) {
				case 1:
					if (getProvinceAdapter != null) {
						arrProvinces = getProvinceAdapter.SearchInput(input);
						lvpakage.setAdapter(getProvinceAdapter);
						getProvinceAdapter.notifyDataSetChanged();
					}
					break;
				case 2:
					if (getDistrictAdapter != null) {
						arrDistrict = getDistrictAdapter.SearchInput(input);
						lvpakage.setAdapter(getDistrictAdapter);
						getDistrictAdapter.notifyDataSetChanged();
					}
					break;
				case 3:
					if (getPrecinctAdapter != null) {
						arrPrecinct = getPrecinctAdapter.SearchInput(input);
						lvpakage.setAdapter(getPrecinctAdapter);
						getPrecinctAdapter.notifyDataSetChanged();
					}
					break;
				case 4:
					if (getStreetBlockAdapter != null) {
						getStreetBlockAdapter.getFilter().filter(input);
						// getStreetBlockAdapter.SearchInput(input);
						lvpakage.setAdapter(getStreetBlockAdapter);
						getStreetBlockAdapter.notifyDataSetChanged();
					}
					break;
				default:
					break;
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		btnRefreshStreetBlock = (Button) findViewById(R.id.btnRefreshStreetBlock);
		btnRefreshStreetBlock.setVisibility(View.GONE);
	}

	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(getResources().getString(R.string.search));
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {

		switch (Integer.parseInt(check)) {
		case 1:
			Intent data = new Intent();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("provinceKey", arrProvinces.get(position));
			data.putExtras(mBundle);
			setResult(RESULT_OK, data);
			finish();
			break;
		case 2:
			Intent data1 = new Intent();
			Bundle mBundle1 = new Bundle();
			mBundle1.putSerializable("districtKey", arrDistrict.get(position));
			data1.putExtras(mBundle1);
			setResult(RESULT_OK, data1);
			finish();
			break;
		case 3:
			Intent data2 = new Intent();
			Bundle mBundle2 = new Bundle();
			mBundle2.putSerializable("precinctKey", arrPrecinct.get(position));
			data2.putExtras(mBundle2);
			setResult(RESULT_OK, data2);
			finish();
			break;

		case 4:
			Intent data3 = new Intent();
			Bundle mBundle3 = new Bundle();
			AreaObj streetBlockKey = (AreaObj) adapterView.getAdapter()
					.getItem(position);
			mBundle3.putSerializable("streetBlockKey", streetBlockKey);
			data3.putExtras(mBundle3);
			setResult(RESULT_OK, data3);
			finish();
			break;
		default:
			break;
		}
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

	// move login
	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FragmentSearchLocation.this,
					LoginActivity.class);
			startActivity(intent);
			finish();
			MainActivity.getInstance().finish();
		}
	};

	private class GetListGroupAdressAsyncTask extends
			AsyncTask<String, Void, ArrayList<AreaObj>> {
		private Context context = null;
		private final XmlDomParse parse = new XmlDomParse();
		private String errorCode = "";
		private String description = "";
		private ProgressDialog progress;
		private ProgressBar prb_bar = null;

		public GetListGroupAdressAsyncTask(Context context,
				ProgressBar progessBar) {
			this.context = context;
			this.prb_bar = progessBar;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			this.progress = new ProgressDialog(this.context);
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.getdataing));
			this.progress.show();

			if (prb_bar != null) {
				prb_bar.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected ArrayList<AreaObj> doInBackground(String... params) {
			return getListGroupAddress(params[0]);
		}

		@Override
		protected void onPostExecute(ArrayList<AreaObj> result) {
			super.onPostExecute(result);

			if (prb_bar != null) {
				prb_bar.setVisibility(View.GONE);
			}
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
			}
			if (errorCode.equals("0")) {
				if (result != null && result.size() > 0) {
					arrStreetBlock = result;
					getStreetBlockAdapter = new GetStreetBlockAdapter(
							arrStreetBlock, FragmentSearchLocation.this);
					lvpakage.setAdapter(getStreetBlockAdapter);
					getStreetBlockAdapter.notifyDataSetChanged();
				} else {
					arrStreetBlock = new ArrayList<>();
					CommonActivity.createAlertDialog(
							FragmentSearchLocation.this,
							getString(R.string.notStreetBlock),
							getString(R.string.app_name)).show();
				}
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(FragmentSearchLocation.this,
									description, context.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = context.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							FragmentSearchLocation.this, description,
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}
		}

		private ArrayList<AreaObj> getListGroupAddress(String parentCode) {
			if (parentCode == null || parentCode.isEmpty()) {
				return new ArrayList<>();
			}
			ArrayList<AreaObj> listGroupAdress = null;
			listGroupAdress = new CacheDatabaseManager(
					MainActivity.getInstance())
					.getListCacheStreetBlock(parentCode);

			if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
				errorCode = "0";
				return listGroupAdress;
			}
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
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, FragmentSearchLocation.this,
						"mbccs_getListArea");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original group", response);

				// ==== parse xml list ip
				listGroupAdress = parserListGroup(original);
				if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
					new CacheDatabaseManager(MainActivity.getInstance())
							.insertCacheStreetBlock(listGroupAdress, parentCode);
				}

			} catch (Exception e) {
				Log.d("getListIP", e.toString());
			}
			return listGroupAdress;
		}

		public ArrayList<AreaObj> parserListGroup(String original) {
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
					Log.d("name area: ", areaObject.getName());
					areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
					listGroupAdress.add(areaObject);
				}
			}
			return listGroupAdress;
		}
	}

}
