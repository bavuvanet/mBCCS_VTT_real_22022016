package com.viettel.bss.viettelpos.v4.sale.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubGoodsInfoAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubGoodsNewInfoAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubGoodsNewInfoAdapter.OnChangeGoodsNewInfo;
import com.viettel.bss.viettelpos.v4.sale.adapter.SubscriberInfoRowAdapter;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskChangeEquipment;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListGoodsByReason;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListReason;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListSubGoods;
import com.viettel.bss.viettelpos.v4.sale.object.SubGoods;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.sale.utils.SubscriberUtils;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class FragmentChangeEquipmentSub extends FragmentCommon {

	private Activity mActivity;

    private ExpandableHeightListView lv_addGoods;
	private Spinner spinner_reason;
	private CheckBox cb_createTaskForTeam;
	private Button btn_changeEquipment;

	private SubscriberDTO sub;
	private List<SubGoods> lstSubGoods;
	private List<Spin> lstChangeEquipmentReason;
	private List<SubGoods> lstSubGoodsNew;

	private SubGoodsInfoAdapter subGoodsInfoAdapter;
	private SubGoodsNewInfoAdapter subGoodsNewInfoAdapter;

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_change_equipment;
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
			sub = (SubscriberDTO) mBundle.getSerializable("subscriberDTO");
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
        ExpandableHeightListView lv_subscriberInfos = (ExpandableHeightListView) mView
                .findViewById(R.id.lv_subscriberInfos);
        ExpandableHeightListView lv_subscriberEquipments = (ExpandableHeightListView) mView
                .findViewById(R.id.lv_subscriberEquipments);
		lv_addGoods = (ExpandableHeightListView) mView
				.findViewById(R.id.lv_addGoods);
		spinner_reason = (Spinner) mView.findViewById(R.id.spinner_reason);
		btn_changeEquipment = (Button) mView
				.findViewById(R.id.btn_changeEquipment);
		cb_createTaskForTeam = (CheckBox) mView
				.findViewById(R.id.cb_createTaskForTeam);

		btn_changeEquipment.setOnClickListener(this);
		cb_createTaskForTeam.setOnClickListener(this);

		List<Spin> lstData = SubscriberUtils.toSpins(sub, mActivity);
		SubscriberInfoRowAdapter subAdapter = new SubscriberInfoRowAdapter(
				mActivity, lstData);
		lv_subscriberInfos.setExpanded(true);
		lv_subscriberInfos.setAdapter(subAdapter);

		lstSubGoods = new ArrayList<>();

		subGoodsInfoAdapter = new SubGoodsInfoAdapter(mActivity, lstSubGoods);
		lv_subscriberEquipments.setExpanded(true);
		lv_subscriberEquipments.setAdapter(subGoodsInfoAdapter);

		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsyncTaskGetListSubGoods asynGetListSubGoods = new AsyncTaskGetListSubGoods(
					mActivity, new OnPostExecuteGetListSubGoods(), moveLogInAct);
			asynGetListSubGoods.execute(sub.getAccount());
		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}

	}

	private void setMoreInfo() {
		if (CommonActivity.isNetworkConnected(getActivity())) {
			AsyncTaskGetListReason asyncGetListReason = new AsyncTaskGetListReason(
					mActivity, new OnPostExecuteGetListReason(), moveLogInAct,
					Constant.ACTION_CODE_CHANGE_EQUIPMENT);
			asyncGetListReason.execute(sub);
		} else {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.errorNetwork),
					getString(R.string.app_name)).show();
		}

		lstSubGoodsNew = new ArrayList<>();

		lstChangeEquipmentReason = new ArrayList<>();
		subGoodsNewInfoAdapter = new SubGoodsNewInfoAdapter(mActivity,
				lstSubGoodsNew, new OnChangeGoodsNew());
		lv_addGoods.setExpanded(true);
		lv_addGoods.setAdapter(subGoodsNewInfoAdapter);
		spinner_reason.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Spin spnType = (Spin) arg0.getItemAtPosition(arg2);
				if ("".equals(spnType.getId())) {
					lstSubGoodsNew.clear();
					subGoodsNewInfoAdapter.notifyDataSetChanged();
				} else if (CommonActivity.isNetworkConnected(getActivity())) {
					if (spnType != null) {
						AsyncTaskGetListGoodsByReason asyntaskGetListReason = new AsyncTaskGetListGoodsByReason(
								getActivity(),
								new OnPostExecuteGetListGoodsByReason(),
								moveLogInAct);
						asyntaskGetListReason.execute(spnType.getId(),
								sub.getTelecomServiceId(), sub.getProductCode());
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
	}

	private class OnPostExecuteGetListSubGoods implements
			OnPostExecuteListener<List<SubGoods>> {

		@Override
		public void onPostExecute(List<SubGoods> result, String errorCode,
				String description) {
			// TODO Auto-generated method stub
			if (result.isEmpty()) {
				btn_changeEquipment.setVisibility(View.GONE);
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.subGoodsListEmpty),
						getString(R.string.app_name)).show();
			} else {
				lstSubGoods.clear();
				// for (SubGoods subGoods : result) {
				// if(subGoods.get)
				// }
				lstSubGoods.addAll(result);
				for (SubGoods subGoods : result) {
					if (!subGoods.isVirtualGoods()) {
						subGoods.setSerialToRetrieve(subGoods.getSerial());
					}
				}
				subGoodsInfoAdapter.notifyDataSetChanged();
				setMoreInfo();
			}
		}

	}

	private class OnPostExecuteGetListReason implements
			OnPostExecuteListener<ArrayList<Spin>> {

		@Override
		public void onPostExecute(ArrayList<Spin> result, String errorCode,
				String description) {
			// TODO Auto-generated method stub
			lstChangeEquipmentReason.clear();
			lstChangeEquipmentReason.add(new Spin("",
					getString(R.string.txt_select_reason)));
			lstChangeEquipmentReason.addAll(result);
			Utils.setDataSpinner(getActivity(), lstChangeEquipmentReason,
					spinner_reason);
		}

	}

	private class OnPostExecuteGetListGoodsByReason implements
			OnPostExecuteListener<List<SubGoods>> {

		@Override
		public void onPostExecute(List<SubGoods> result, String errorCode,
				String description) {
			// TODO Auto-generated method stub
			lstSubGoodsNew.clear();
			lstSubGoodsNew.addAll(result);
			subGoodsNewInfoAdapter.notifyDataSetChanged();

			// createTaskForTeam
			boolean hasVirtualGoods = false;
			for (SubGoods subGoods : lstSubGoodsNew) {
				if (subGoods.isVirtualGoods()) {
					hasVirtualGoods = true;
					break;
				}
			}

			if (hasVirtualGoods) {
				cb_createTaskForTeam.setEnabled(false);
				cb_createTaskForTeam.setChecked(false);
				CommonActivity.createAlertDialog(getActivity(),
						getString(R.string.virtualGoodsAlert),
						getString(R.string.app_name)).show();
			} else {
				cb_createTaskForTeam.setEnabled(true);
			}
		}

	}

	private class OnPostExecuteChangeEquipment implements
			OnPostExecuteListener<String> {

		@Override
		public void onPostExecute(String result, String errorCode,
				String description) {
			// TODO Auto-generated method stub

			String message = getString(R.string.change_device_success,
					sub.getAccount());
			if (cb_createTaskForTeam.isChecked()) {
				message = getString(
						R.string.generate_work_change_device_success,
						sub.getAccount());
			}

			btn_changeEquipment.setEnabled(false);

			CommonActivity.createAlertDialog(getActivity(), message,
					getString(R.string.app_name)).show();
		}

	}

	private class OnChangeGoodsNew implements OnChangeGoodsNewInfo {

		@Override
		public void onChange(SubGoods goodsNew, int position) {
			// TODO Auto-generated method stub
            Spin reason = (Spin) spinner_reason.getSelectedItem();
			String reasonId = reason.getId();
			Bundle mBundle = new Bundle();

			Boolean createTaskForTeam = cb_createTaskForTeam.isChecked();
			mBundle.putSerializable("subGoods", goodsNew);
			mBundle.putSerializable("subscriberDTO", sub);
			mBundle.putSerializable("reasonId", reasonId);
			mBundle.putSerializable("createTaskForTeam", createTaskForTeam);

			FragmentEditNewEquipment fragmentAddGoodsNew = new FragmentEditNewEquipment();
			fragmentAddGoodsNew.setArguments(mBundle);
			fragmentAddGoodsNew.setTargetFragment(
					FragmentChangeEquipmentSub.this, 100);
			ReplaceFragment.replaceFragment(mActivity, fragmentAddGoodsNew,
					true);
		}
	}

	@Override
	public void onClick(View arg0) {
		super.onClick(arg0);
		switch (arg0.getId()) {
		case R.id.btn_changeEquipment:
			doChangeEquipment();
			break;
		case R.id.cb_createTaskForTeam:
			if (!cb_createTaskForTeam.isChecked()) {
				if (!CommonActivity.isNullOrEmpty(lstSubGoodsNew)) {
					for (SubGoods good : lstSubGoodsNew) {
						good.setSerial("");
					}
					subGoodsNewInfoAdapter.notifyDataSetChanged();
				}
			}
			if (!CommonActivity.isNullOrEmpty(lstSubGoods)) {
				if (cb_createTaskForTeam.isChecked()) {
					for (SubGoods good : lstSubGoods) {
						good.setAllowSerial(false);
						good.setSerialToRetrieve("");
					}
				} else {
					for (SubGoods good : lstSubGoods) {
						good.setAllowSerial(true);
					}
				}
				subGoodsInfoAdapter.notifyDataSetChanged();
			}

			break;
		default:
			break;
		}

	}

	private void doChangeEquipment() {
		SubGoods selectedSubGoods = null;
		for (SubGoods subGoods : lstSubGoods) {
			if (subGoods.isRetrieveGoods()) {
				selectedSubGoods = subGoods;
				if (!subGoods.isVirtualGoods() && subGoods.isAllowSerial()) {
					if (CommonActivity.isNullOrEmpty(subGoods
							.getSerialToRetrieve())) {
						CommonActivity.createAlertDialog(
								getActivity(),
								getString(R.string.retrieveSerialEmpty) + " "
										+ subGoods.getStockModelName(),
								getString(R.string.app_name)).show();
						return;
					}
				}
				break;
			}
		}

		if (selectedSubGoods == null) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.retrieveGoodsEmpty),
					getString(R.string.app_name)).show();
			return;
		}

		Spin reason = (Spin) spinner_reason.getSelectedItem();
		final String reasonId = reason.getId();
		if ("".equals(reasonId)) {
			CommonActivity.createAlertDialog(getActivity(),
					getString(R.string.choose_reason_not),
					getString(R.string.app_name)).show();
			return;
		}

		for (SubGoods subGoods : lstSubGoodsNew) {
			if (CommonActivity.isNullOrEmpty(subGoods.getStockModelId())) {
				CommonActivity.createAlertDialog(
						getActivity(),
						getString(R.string.newGoodsEmpty) + " "
								+ subGoods.getSaleServiceModelName(),
						getString(R.string.app_name)).show();
				return;
			}
		}

		final boolean createTaskForTeam = cb_createTaskForTeam.isChecked();
		if (createTaskForTeam) {
			for (SubGoods subGoods : lstSubGoodsNew) {
				if (!CommonActivity.isNullOrEmpty(subGoods.getSerial())) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getString(R.string.createTaskForTeamWithSerial)
									+ " " + subGoods.getStockModelName(),
							getString(R.string.app_name)).show();
					return;
				}
			}
		} else {
			for (SubGoods subGoods : lstSubGoodsNew) {
				if (!subGoods.isVirtualGoods()
						&& CommonActivity.isNullOrEmpty(subGoods.getSerial())) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getString(R.string.serialEmpty) + " "
									+ subGoods.getStockModelName(),
							getString(R.string.app_name)).show();
					return;
				}
			}
		}

		OnClickListener onClickChangeEquipment = new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CommonActivity.isNetworkConnected(getActivity())) {
					AsyncTaskChangeEquipment asyncTaskChangeEquipment = new AsyncTaskChangeEquipment(
							mActivity, new OnPostExecuteChangeEquipment(),
							moveLogInAct, sub.getAccount(), reasonId,
							createTaskForTeam, lstSubGoods, lstSubGoodsNew);
					asyncTaskChangeEquipment.execute();
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getString(R.string.errorNetwork),
							getString(R.string.app_name)).show();
				}

			}
		};

		String message = getString(R.string.changeEquipmentConfirm,
				sub.getAccount(), selectedSubGoods.getStockModelCode(),
				selectedSubGoods.getSerialToRetrieve());
		if (cb_createTaskForTeam.isChecked()) {
			message = getString(R.string.changeEquipmentCreateTaskConfirm,
					sub.getAccount());
		}
		CommonActivity.createDialog(getActivity(), message,
				getActivity().getString(R.string.app_name),
				getActivity().getString(R.string.ok),
				getActivity().getString(R.string.cancel),
				onClickChangeEquipment, null).show();

	}

	@Override
	public void setPermission() {
		// TODO Auto-generated method stub

	}
}
