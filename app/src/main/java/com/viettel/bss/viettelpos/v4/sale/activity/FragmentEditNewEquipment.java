package com.viettel.bss.viettelpos.v4.sale.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyBaseBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyMethodBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SupplyProgramBean;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListSupplyMethod;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListSupplyProgram;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetPriceInService;
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class FragmentEditNewEquipment extends FragmentCommon {

	private Activity mActivity;

    private Spinner spinner_stockModel;
	private Spinner spinner_supplyMethod;
	private Spinner spinner_supplyProgram;
	private Spinner spinner_programMonth;
	private Spinner spinner_payMethod;
	private EditText edt_payAmount;
	private EditText edt_serial;

    private SubscriberDTO currentSubscriber;
	private SubGoods currentSubGoods;
	private String reasonId;
	private Boolean createTaskForTeam;

	private List<SupplyMethodBean> supplyMethodBeans;
	private SupplyMethodBean defaultSupplyMethodBean;
	private List<SupplyProgramBean> supplyProgramBeans;
	private SupplyProgramBean defaultSupplyProgramBean;
	private List<SupplyBaseBean> programMonths;
	private SupplyBaseBean defaultProgramMonth;
	private List<Spin> payMethods;
	private Spin defaultPayMethod;
	private Spin defaultPayMethod0;
	private Spin defaultPayMethod1;

    private String initSupplyMethodCode;
	private String initSupplyProgramCode;
	private String initProgramMonth;
	private String initPayMethod;
    private boolean needInitData;

	private static final String SUPPLY_METHOD_BD = "BD";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_edit_change_equipment;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = getActivity();
		mBundle = getArguments();
		if (mBundle == null) {
			Log.i("tag", "mBundle null");
		} else {
			Log.i("tag", "mBundle not null");
		}
		if (mBundle != null) {
			currentSubGoods = (SubGoods) mBundle.getSerializable("subGoods");
			currentSubscriber = (SubscriberDTO) mBundle
					.getSerializable("subscriberDTO");

			reasonId = (String) mBundle.getSerializable("reasonId");
			createTaskForTeam = (Boolean) mBundle
					.getSerializable("createTaskForTeam");
		}
	}

	@Override
	public void onResume() {
		MainActivity.getInstance().setTitleActionBar(R.string.addEquipmentInfo);
		super.onResume();
	}

	@Override
	public void unit(View v) {
		// TODO Auto-generated method stub
        EditText edt_saleServiceModel = (EditText) mView
                .findViewById(R.id.edt_saleServiceModel);
		spinner_stockModel = (Spinner) mView
				.findViewById(R.id.spinner_stockModel);
		spinner_supplyMethod = (Spinner) mView
				.findViewById(R.id.spinner_supplyMethod);
		spinner_supplyProgram = (Spinner) mView
				.findViewById(R.id.spinner_supplyProgram);
		spinner_programMonth = (Spinner) mView
				.findViewById(R.id.spinner_programMonth);
		spinner_payMethod = (Spinner) mView
				.findViewById(R.id.spinner_payMethod);
		edt_payAmount = (EditText) mView.findViewById(R.id.edt_payAmount);
		edt_serial = (EditText) mView.findViewById(R.id.edt_serial);
        Button btnOk = (Button) mView.findViewById(R.id.btnOk);
        Button btnCancel = (Button) mView.findViewById(R.id.btnCancel);

		edt_saleServiceModel.setEnabled(false);
		edt_payAmount.setEnabled(false);

		edt_saleServiceModel.setText(currentSubGoods.getSaleServiceModelName());

		if (currentSubGoods.isVirtualGoods() || createTaskForTeam) {
			edt_serial.setEnabled(false);
		}
		
		//InitData
        String initStockModelId = currentSubGoods.getStockModelId();
		initSupplyMethodCode = currentSubGoods.getSupplyMethodCode();
		initSupplyProgramCode = currentSubGoods.getSupplyProgramCode();
		initProgramMonth = currentSubGoods.getProgramMonth();
		initPayMethod = currentSubGoods.getPayMethod();
        String initSerial = currentSubGoods.getSerial();
        needInitData = !CommonActivity.isNullOrEmpty(initStockModelId);

		List<Spin> saleServiceDetails = new ArrayList<>();
		saleServiceDetails.add(new Spin("", mActivity
				.getString(R.string.stockModelSpin)));
		saleServiceDetails.addAll(currentSubGoods.getLstSaleServiceDetail());
		Utils.setDataSpinner(getActivity(), saleServiceDetails,
				spinner_stockModel);

		supplyMethodBeans = new ArrayList<>();
		defaultSupplyMethodBean = new SupplyMethodBean("",
				mActivity.getString(R.string.supplyMethodSpin));
		spinner_stockModel
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Spin spnType = (Spin) arg0.getItemAtPosition(arg2);
						supplyMethodBeans.clear();
						supplyMethodBeans.add(defaultSupplyMethodBean);
						if ("".equals(spnType.getId())) {
							// Set default list
							Utils.setDataSpinner(getActivity(),
									supplyMethodBeans, spinner_supplyMethod);
						} else if (CommonActivity
								.isNetworkConnected(getActivity())) {
							if (spnType != null) {
								AsyncTaskGetListSupplyMethod task = new AsyncTaskGetListSupplyMethod(
										getActivity(),
										new OnPostExecuteGetListSupplyMethod(),
										moveLogInAct);
								task.execute(
										reasonId,
										currentSubscriber.getTelecomServiceId(),
										currentSubscriber.getProductCode(),
										spnType.getId());
							}
						} else {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.errorNetwork),
									getString(R.string.app_name)).show();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		supplyProgramBeans = new ArrayList<>();
		defaultSupplyProgramBean = new SupplyProgramBean(
				mActivity.getString(R.string.supplyProgramSpin), "");

		payMethods = new ArrayList<>();
		defaultPayMethod = new Spin("",
				mActivity.getString(R.string.payMethodSpin));
		defaultPayMethod1 = new Spin("1",
				mActivity.getString(R.string.payMethodSpin1));
		defaultPayMethod0 = new Spin("0",
				mActivity.getString(R.string.payMethodSpin0));

		spinner_supplyMethod
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Spin stockModel = (Spin) spinner_stockModel
								.getSelectedItem();
						SupplyMethodBean spnType = (SupplyMethodBean) arg0
								.getItemAtPosition(arg2);
						// Set default list
						supplyProgramBeans.clear();
						supplyProgramBeans.add(defaultSupplyProgramBean);

						payMethods.clear();
						payMethods.add(defaultPayMethod);
						if ("".equals(spnType.getValue())) {
							// Chuong trinh
							Utils.setDataSpinner(getActivity(),
									supplyProgramBeans, spinner_supplyProgram);
							spinner_supplyProgram.setEnabled(true);
							spinner_programMonth.setEnabled(true);

							// Hinh thuc thanh toan
							Utils.setDataSpinner(getActivity(), payMethods,
									spinner_payMethod);
							spinner_payMethod.setEnabled(true);
						} else if (SUPPLY_METHOD_BD.equals(spnType.getValue())) {
							//Neu khoi tao thi da xong
							needInitData = false;
							
							// Chuong trinh
							Utils.setDataSpinner(getActivity(),
									supplyProgramBeans, spinner_supplyProgram);
							spinner_supplyProgram.setEnabled(false);
							spinner_programMonth.setEnabled(false);

							// Hinh thuc thanh toan
							Utils.setDataSpinner(getActivity(), payMethods,
									spinner_payMethod);
							spinner_payMethod.setEnabled(false);

							// So tien
							AsyncTaskGetPriceInService task = new AsyncTaskGetPriceInService(
									getActivity(),
									new OnPostExecuteGetPriceInService(),
									moveLogInAct, reasonId, currentSubscriber
											.getTelecomServiceId(),
									currentSubscriber.getProductCode(),
									stockModel.getId());
							task.execute();
							
						} else if (CommonActivity
								.isNetworkConnected(getActivity())) {
							// Hinh thuc thanh toan
							payMethods.add(defaultPayMethod1);
							payMethods.add(defaultPayMethod0);
							Utils.setDataSpinner(getActivity(), payMethods,
									spinner_payMethod);
							spinner_payMethod.setEnabled(true);
							
							if (needInitData && !CommonActivity.isNullOrEmpty(initPayMethod)) {
								boolean found = false;
								for (int i = 0; i < payMethods.size(); i++) {
									Spin item = payMethods.get(i);
									if (item.getId().equals(initPayMethod)) {
										found = true;
										spinner_payMethod.setSelection(i);
										break;
									}
								}
								if (!found) {
									needInitData = false;
								}
							}
							// Chuong trinh
							AsyncTaskGetListSupplyProgram task = new AsyncTaskGetListSupplyProgram(
									getActivity(),
									new OnPostExecuteGetListSupplyProgram(),
									moveLogInAct);
							task.execute(reasonId,
									currentSubscriber.getTelecomServiceId(),
									currentSubscriber.getProductCode(),
									stockModel.getId(), spnType.getValue());
						} else {
							CommonActivity.createAlertDialog(getActivity(),
									getString(R.string.errorNetwork),
									getString(R.string.app_name)).show();
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		programMonths = new ArrayList<>();
		defaultProgramMonth = new SupplyBaseBean();
		defaultProgramMonth.setLabel(getString(R.string.programMonthSpin));
		defaultProgramMonth.setProgramMonth("");

		spinner_supplyProgram
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						SupplyProgramBean supplyProgram = (SupplyProgramBean) arg0
								.getItemAtPosition(arg2);
						programMonths.clear();
						programMonths.add(defaultProgramMonth);
						if ("".equals(supplyProgram.getProgramCode())) {
							Utils.setDataSpinner(getActivity(), programMonths,
									spinner_programMonth);
						} else {
							programMonths.addAll(supplyProgram
									.getLstSupplyBaseBean());
							Utils.setDataSpinner(getActivity(), programMonths,
									spinner_programMonth);
						}
						if (needInitData && !CommonActivity.isNullOrEmpty(initProgramMonth)) {
							boolean found = false;
							for (int i = 0; i < programMonths.size(); i++) {
								SupplyBaseBean item = programMonths.get(i);
								if (item.getProgramMonth().equals(initProgramMonth)) {
									found = true;
									spinner_programMonth.setSelection(i);
									break;
								}
							}
							if (!found) {
								needInitData = false;
							}
						}
						//Neu khoi tao thi da xong
						needInitData = false;
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});
		spinner_programMonth
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						SupplyBaseBean programMonth = (SupplyBaseBean) arg0
								.getItemAtPosition(arg2);
						if ("".equals(programMonth.getPrice())) {
							edt_payAmount.setText("");
						} else {
							edt_payAmount.setText(programMonth.getPrice());
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		btnOk.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
		if (needInitData) {
			edt_serial.setText(initSerial);
			boolean found = false;
			for (int i = 0; i < saleServiceDetails.size(); i++) {
				Spin spin = saleServiceDetails.get(i);
				if (spin.getId().equals(initStockModelId)) {
					found = true;
					spinner_stockModel.setSelection(i);
					break;
				}
			}
			if (!found) {
				needInitData = false;
			}
		}
	}

	private class OnPostExecuteGetListSupplyMethod implements
			OnPostExecuteListener<List<SupplyMethodBean>> {

		@Override
		public void onPostExecute(List<SupplyMethodBean> result,
				String errorCode, String description) {
			// TODO Auto-generated method stub
			supplyMethodBeans.addAll(result);
			Utils.setDataSpinner(getActivity(), supplyMethodBeans,
					spinner_supplyMethod);
			
			if (needInitData) {
				boolean found = false;
				for (int i = 0; i < supplyMethodBeans.size(); i++) {
					SupplyMethodBean item = supplyMethodBeans.get(i);
					if (item.getValue().equals(initSupplyMethodCode)) {
						found = true;
						spinner_supplyMethod.setSelection(i);
						break;
					}
				}
				if (!found) {
					needInitData = false;
				}
			}
		}

	}

	private class OnPostExecuteGetPriceInService implements
			OnPostExecuteListener<String> {

		@Override
		public void onPostExecute(String result, String errorCode,
				String description) {
			// TODO Auto-generated method stub
			edt_payAmount.setText(result);
		}

	}

	private class OnPostExecuteGetListSupplyProgram implements
			OnPostExecuteListener<List<SupplyProgramBean>> {

		@Override
		public void onPostExecute(List<SupplyProgramBean> result,
				String errorCode, String description) {
			// TODO Auto-generated method stub
			supplyProgramBeans.addAll(result);
			Utils.setDataSpinner(getActivity(), supplyProgramBeans,
					spinner_supplyProgram);
			spinner_supplyProgram.setEnabled(true);
			spinner_programMonth.setEnabled(true);
			if (needInitData && !CommonActivity.isNullOrEmpty(initSupplyProgramCode)) {
				boolean found = false;
				for (int i = 0; i < supplyProgramBeans.size(); i++) {
					SupplyProgramBean item = supplyProgramBeans.get(i);
					if (initSupplyProgramCode.equals(item.getProgramCode())) {
						found = true;
						spinner_supplyProgram.setSelection(i);
						break;
					}
				}
				if (!found) {
					needInitData = false;
				}
			}
		}

	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btnOk:
			onOkClick();
			break;
		case R.id.btnCancel:
			getTargetFragment().onActivityResult(getTargetRequestCode(),
					Activity.RESULT_CANCELED, null);
			mActivity.onBackPressed();
			break;
		default:
			break;
		}

	}

	private void onOkClick() {

		Spin stockModel = (Spin) spinner_stockModel.getSelectedItem();
		if ("".equals(stockModel.getId())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.stockModelEmpty),
					getString(R.string.app_name)).show();
			return;
		}

		SupplyMethodBean supplyMethodBean = (SupplyMethodBean) spinner_supplyMethod
				.getSelectedItem();
		if ("".equals(supplyMethodBean.getValue())) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.supplyMethodEmpty),
					getString(R.string.app_name)).show();
			return;
		}

		SupplyProgramBean supplyProgramBean = null;
		SupplyBaseBean programMonth = null;
		Spin payMethod = null;
		if (!SUPPLY_METHOD_BD.equals(supplyMethodBean.getValue())) {

			supplyProgramBean = (SupplyProgramBean) spinner_supplyProgram
					.getSelectedItem();
			if ("".equals(supplyProgramBean.getProgramCode())) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.supplyProgramEmpty),
						getString(R.string.app_name)).show();
				return;
			}

			programMonth = (SupplyBaseBean) spinner_programMonth
					.getSelectedItem();
			if ("".equals(programMonth.getProgramMonth())) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.programMonthEmpty),
						getString(R.string.app_name)).show();
				return;
			}

			payMethod = (Spin) spinner_payMethod.getSelectedItem();
			if ("".equals(payMethod.getId())) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.payMethodEmpty),
						getString(R.string.app_name)).show();
				return;
			}
		}

		String serial = edt_serial.getText().toString();
		if (!currentSubGoods.isVirtualGoods() && !createTaskForTeam) {
			if (CommonActivity.isNullOrEmpty(serial)) {
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.serialEmpty),
						getString(R.string.app_name)).show();
				return;
			}
		}

		currentSubGoods.setStockModelId(stockModel.getId());
		currentSubGoods.setStockModelName(stockModel.getName());
		currentSubGoods.setSupplyMethodCode(supplyMethodBean.getValue());
		currentSubGoods.setSupplyMethodName(supplyMethodBean.getName());

		if (SUPPLY_METHOD_BD.equals(supplyMethodBean.getValue())) {
			currentSubGoods.setSupplyProgramCode(null);
			currentSubGoods.setSupplyProgramName(null);
			currentSubGoods.setProgramMonth(null);
			currentSubGoods.setPayMethod(null);
			currentSubGoods.setPayAmount(edt_payAmount.getText().toString());
		} else {
			currentSubGoods.setSupplyProgramCode(supplyProgramBean
					.getProgramCode());
			currentSubGoods.setSupplyProgramName(supplyProgramBean
					.getProgramName());
			currentSubGoods.setProgramMonth(programMonth.getProgramMonth());
			currentSubGoods.setPayMethod(payMethod.getId());
			currentSubGoods.setPayMethodName(payMethod.getValue());
			currentSubGoods.setPayAmount(programMonth.getPrice());
		}

		currentSubGoods.setSerial(serial);

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		intent.putExtras(bundle);
		getTargetFragment().onActivityResult(getTargetRequestCode(),
				Activity.RESULT_OK, intent);
		mActivity.onBackPressed();
	}

    @Override
	public void setPermission() {
		// TODO Auto-generated method stub
		
	}
}
