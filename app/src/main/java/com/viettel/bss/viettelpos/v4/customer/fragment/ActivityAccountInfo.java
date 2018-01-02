package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelCTV;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.adapter.AccountInfoAdapter;
import com.viettel.bss.viettelpos.v4.customer.adapter.SubBeanAdapter;
import com.viettel.bss.viettelpos.v4.customer.object.AccountBO;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.customer.object.SubBeanBO;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityAccountInfo extends AppCompatActivity {

	private ListView lvAccount;
	private ArrayList<AccountBO> lstAccountBOs = new ArrayList<>();
	private AccountBO accountBO;
	private AccountInfoAdapter adapter;

	@BindView(R.id.toolbar)
    Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lstAccountBOs = (ArrayList<AccountBO>) this.getIntent().getExtras().getSerializable("LST_ACCOUNT_INFO");
		setContentView(R.layout.layout_account_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
		unit();
	}
	

	
	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setTitle(getString(R.string.contractInfo));
	}
//
//	public void addActionBar() {
//		ActionBar mActionBar = getSupportActionBar();
//		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//		mActionBar.setCustomView(R.layout.layout_actionbar_channel);
//		LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView()
//				.findViewById(R.id.relaBackHome);
//		relaBackHome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				finish();
//			}
//		});
//		Button btnHome = (Button) mActionBar.getCustomView().findViewById(
//				R.id.btnHome);
//		btnHome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				CommonActivity.callphone(ActivityAccountInfo.this,
//						Constant.phoneNumber);
//			}
//		});
//		TextView txtNameActionBar = (TextView) mActionBar.getCustomView()
//				.findViewById(R.id.txtNameActionbar);
//		TextView txtAddressActionBar = (TextView) mActionBar.getCustomView()
//				.findViewById(R.id.txtAddressActionbar);
//		txtAddressActionBar.setVisibility(View.GONE);
//		txtNameActionBar.setText(ActivityAccountInfo.this.getString(R.string.contractInfo));
//	}

	private void unit() {
		// TODO Auto-generated method stub
		adapter = new AccountInfoAdapter(ActivityAccountInfo.this, lstAccountBOs);
		lvAccount = (ListView) findViewById(R.id.lvAccount);
		
		lvAccount.setAdapter(adapter);
		lvAccount.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				accountBO = (AccountBO) adapter.getItem(position);
				
				new AsyViewDetailDebitInfo(ActivityAccountInfo.this).execute();

			}
		});
		
		ArrayList<SubBeanBO> lstSubBeanBOs = this.getIntent().getExtras().getParcelableArrayList("LST_SUB_BEAN");
		if(!CommonActivity.isNullOrEmpty(lstSubBeanBOs)){
			showDetailDebitInfo(lstSubBeanBOs);
		}
	}
	
	private class AsyViewDetailDebitInfo extends AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyViewDetailDebitInfo(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return viewDetailDebitInfo();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();
			
			if (result.getErrorCode().equals("0")) {
				if(CommonActivity.isNullOrEmpty(result.getLstSubBeanBOs())){
					Dialog dialog = CommonActivity.createAlertDialog(ActivityAccountInfo.this, mActivity.getResources().getString(R.string.txt_search_invalid_info),
							getResources().getString(R.string.app_name));
					dialog.show();
					return;
				}
				
				showDetailDebitInfo(result.getLstSubBeanBOs());
				//hien thi thong tin khach hang
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (result.getDescription() == null || result.getDescription().isEmpty()) {
						result.setDescription(mActivity.getResources().getString(R.string.checkdes));
					}

					Dialog dialog = CommonActivity.createAlertDialog(ActivityAccountInfo.this, result.getDescription(),
							getResources().getString(R.string.app_name));
					dialog.show();
				}
			}

		}

		private BOCOutput viewDetailDebitInfo() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "getDebitInfoList";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				
				input.addValidateGateway("wscode", "mbccs_" + methodName);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				
				rawData.append("<accountId>").append(accountBO.getAccountId()).append("</accountId>");

				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");
				
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());

				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, ActivityAccountInfo.this,
						"mbccs_" + methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null) {
					bocOutput = new BOCOutput();
					bocOutput.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					return bocOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "viewDetailDebitInfo", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(e.getMessage());
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}

			return bocOutput;
		}

	}
	private final OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(ActivityAccountInfo.this, LoginActivity.class);
			startActivity(intent);
			ActivityAccountInfo.this.finish();
			MainActivity.getInstance().finish();

		}
	};

	
	private SubBeanAdapter subAdapter;
	private void showDetailDebitInfo(ArrayList<SubBeanBO> lstSubBeanBOs){
		final Dialog dialog = new Dialog(ActivityAccountInfo.this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_debit_info);
		dialog.setCancelable(true);
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);
		
		TextView tvHeader = (TextView) dialog.findViewById(R.id.tvHeader);
		tvHeader.setText(getResources().getString(R.string.txt_chi_tiet_cong_no, accountBO.getAccountNo()));

		ListView lvDebit = (ListView) dialog.findViewById(R.id.lvDebit);
		subAdapter = new SubBeanAdapter(ActivityAccountInfo.this, lstSubBeanBOs);
		lvDebit.setAdapter(subAdapter);
		
		LinearLayout lnClose = (LinearLayout) dialog.findViewById(R.id.lnClose);
		lnClose.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		lvDebit.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				SubBeanBO subBeanBO = (SubBeanBO) subAdapter.getItem(position);
				Intent intent = new Intent(ActivityAccountInfo.this,FragmentChargeDelCTV.class);
				intent.putExtra("SUB_BEAN_ISDN", subBeanBO.getIsdn());
				startActivity(intent);
				finish();
				dialog.dismiss();
			}
		});

		dialog.show();
	}
}
