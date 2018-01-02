package com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.GetVasResultAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.VasSafeNetRigistedAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.VasSafeNetRigistedAdapter.OnChangeCheckedSubRelRegisted;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.VasSafeNetRigistedAdapter.OnSelectPromotion;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.VasSafeNetUnRigistedAdapter;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.adapter.VasSafeNetUnRigistedAdapter.OnChangeCheckedSubRelUnRegisted;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.FindByVasAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetListVasAllAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.asyntask.GetReasonFullPMAsyn;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.MBCCSVasResultBO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionUnitVas;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubRelProductBO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.VasResponseBO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;
import com.viettel.bss.viettelpos.v4.work.object.ParseOuput;

public class FragmentRegisterVasSafeNet extends FragmentCommon implements
		OnChangeCheckedSubRelRegisted, OnChangeCheckedSubRelUnRegisted,
		OnSelectPromotion {
	private GridView gridRegisteredUse, gridRegister;
	// private Button btnregisterorcancel;
	private EditText edtselectreason;
	private VasSafeNetRigistedAdapter vasSafeNetRigistedAdapter;
	private VasSafeNetUnRigistedAdapter vasSafeNetUnRigistedAdapter;
	private ArrayList<SubRelProductBO> arrSubRelProductBORegisted = new ArrayList<SubRelProductBO>();
	private ArrayList<SubRelProductBO> arrSubRelProductBORegistedSafeNet = new ArrayList<SubRelProductBO>();
	private ArrayList<SubRelProductBO> arrSubRelProductBOUnRegisted = new ArrayList<SubRelProductBO>();
	private SubscriberDTO subscriberDTO = null;
	private ArrayList<ReasonDTO> arrReasonDTO = new ArrayList<ReasonDTO>();
	private ReasonDTO reasonDTO = null;
	private LinearLayout lnVasUsed;

	private SubRelProductBO subRelProductBOSelect;

	private EditText edtselectpromotion;

	private PromotionUnitVas promotionUnitVas;
	private PromotionUnitVas promotionUnitVasNomal;
	// private LinearLayout lnreasoncancel;
	private LinearLayout lnreasonregister;
	private EditText edtcancelvas;
	private LinearLayout lnRegiterVas;
	private ArrayList<ReasonDTO> arrReasonDTOCancel = new ArrayList<ReasonDTO>();
	private ReasonDTO reasonDTOCancel = null;

	private Spinner spnVassafenet;
	private LinearLayout lnVassafenet;

	private ArrayList<ReasonDTO> arrReasonDTOSafenet = new ArrayList<ReasonDTO>();
	private ReasonDTO reasonDTOSafeNet = null;
	private EditText edtselectreasonvas;

	private EditText edtselectpromotionNormal;
	private Dialog dialogVas;
	private Dialog dialogCancel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle mBundle = this.getArguments();
		if (mBundle != null) {
			subscriberDTO = (SubscriberDTO) mBundle
					.getSerializable("subscriberDTO");
		}

		idLayout = R.layout.activity_change_vas;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
        setTitleActionBar(R.string.registervassafe);
		if (reasonDTO != null
				&& !CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
			edtselectreason.setText(reasonDTO.toString());
		} else {
			edtselectreason.setText("");
			edtselectreason.setHint(getActivity().getString(R.string.chonlydo));
		}
	}

	@Override
	public void unit(View v) {
		spnVassafenet = (Spinner) v.findViewById(R.id.spnVassafenet);
		spnVassafenet.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				if (!CommonActivity.isNullOrEmpty(subscriberDTO) && arg2 > 0) {
					subRelProductBOSelect = arrSubRelProductBORegistedSafeNet
							.get(arg2);
					FindByVasAsyn findByVasAsyn = new FindByVasAsyn(
							getActivity(), new OnPosFindByVAS(), moveLogInAct);
					findByVasAsyn.execute(arrSubRelProductBORegistedSafeNet
							.get(arg2).getVasCode());
				} else {
					subRelProductBOSelect = new SubRelProductBO();
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		lnVassafenet = (LinearLayout) v.findViewById(R.id.lnVassafenet);
		lnVassafenet.setVisibility(View.GONE);
		lnRegiterVas = (LinearLayout) v.findViewById(R.id.lnRegiterVas);
		lnreasonregister = (LinearLayout) v.findViewById(R.id.lnreasonregister);
		lnreasonregister.setVisibility(View.GONE);
		gridRegisteredUse = (GridView) v.findViewById(R.id.gridRegisteredUse);
		gridRegister = (GridView) v.findViewById(R.id.gridRegister);
		lnVasUsed = (LinearLayout) v.findViewById(R.id.lnVasUsed);
		edtselectreason = (EditText) v.findViewById(R.id.edtselectreason);
		edtselectreason.setOnClickListener(this);
		// btnregisterorcancel = (Button)
		// v.findViewById(R.id.btnregisterorcancel);
		// btnregisterorcancel.setVisibility(View.GONE);
		// btnregisterorcancel.setOnClickListener(this);
		if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {
			GetListVasAllAsyn getListVasAllAsyn = new GetListVasAllAsyn(
					getActivity(), new OnPosGetListVasAll(), moveLogInAct);
			getListVasAllAsyn.execute(subscriberDTO.getProductCode(),
					subscriberDTO.getSubId() + "");

			// lay ly do huy/ dang ky vas

		}

	}

	@Override
	public void setPermission() {
		permission = "menu.register.vas2";

	}

	private class OnPosGetListVasAll implements
			OnPostExecuteListener<VasResponseBO> {

		@Override
		public void onPostExecute(VasResponseBO result, String errorCode,
				String description) {
			arrSubRelProductBORegisted = new ArrayList<SubRelProductBO>();
			arrSubRelProductBOUnRegisted = new ArrayList<SubRelProductBO>();
			arrSubRelProductBORegistedSafeNet = new ArrayList<SubRelProductBO>();
			edtselectreason.setText("");
			edtselectreason.setHint(getActivity().getString(R.string.chonlydo));
			reasonDTO = new ReasonDTO();
			reasonDTOCancel = new ReasonDTO();
			reasonDTOSafeNet = new ReasonDTO();
			lnreasonregister.setVisibility(View.GONE);
			promotionUnitVas = new PromotionUnitVas();
			reasonDTOSafeNet = new ReasonDTO();
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmpty(result)
						&& (!CommonActivity.isNullOrEmptyArray(result
								.getLstVasUsed()) || !CommonActivity
								.isNullOrEmptyArray(result.getLstVasChange()))) {
					if (!CommonActivity.isNullOrEmptyArray(result
							.getLstVasUsed())) {
						for (SubRelProductBO item : result.getLstVasUsed()) {

							if (!CommonActivity
									.isNullOrEmpty(item.getAtrrVas())
									&& Constant.VAS_SAFE_NET.equals(item
											.getAtrrVas())) {
								arrSubRelProductBOUnRegisted.add(item);
							}
						}
					}

					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)) {
						// lnreasoncancel.setVisibility(View.VISIBLE);
						spnVassafenet.setEnabled(false);
						lnVasUsed.setVisibility(View.VISIBLE);
					} else {
						spnVassafenet.setEnabled(true);
						lnVasUsed.setVisibility(View.GONE);
						// lnreasoncancel.setVisibility(View.GONE);
					}

					if (!CommonActivity.isNullOrEmptyArray(result
							.getLstVasChange())) {
						for (SubRelProductBO item : result.getLstVasChange()) {
							if (!CommonActivity
									.isNullOrEmpty(item.getAtrrVas())
									&& Constant.VAS_SAFE_NET.equals(item
											.getAtrrVas())) {
								arrSubRelProductBORegistedSafeNet.add(item);
							}
						}
					}
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBORegistedSafeNet)) {

						SubRelProductBO subRelProductBO = new SubRelProductBO();
						subRelProductBO.setVasCode("");
						subRelProductBO.setVasName(getActivity().getString(
								R.string.txt_select));
						arrSubRelProductBORegistedSafeNet.add(0,
								subRelProductBO);
						lnVassafenet.setVisibility(View.VISIBLE);
						Utils.setDataSpinner(getActivity(),
								arrSubRelProductBORegistedSafeNet,
								spnVassafenet);
					} else {
						lnVassafenet.setVisibility(View.GONE);
					}

					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBORegisted)) {
						GetReasonFullPMAsyn getReasonFullPMAsyn = new GetReasonFullPMAsyn(
								getActivity(), new OnPostGetReasonFull(),
								moveLogInAct);
						getReasonFullPMAsyn.execute(subscriberDTO.getOfferId(),
								"102", subscriberDTO.getTelecomServiceAlias(),
								subscriberDTO.getCustType(),
								subscriberDTO.getSubType(), "", "");
						// btnregisterorcancel.setVisibility(View.GONE);
					} else {
						// btnregisterorcancel.setVisibility(View.VISIBLE);
					}

					vasSafeNetRigistedAdapter = new VasSafeNetRigistedAdapter(
							arrSubRelProductBORegisted, getActivity(),
							FragmentRegisterVasSafeNet.this,
							FragmentRegisterVasSafeNet.this);
					gridRegister.setAdapter(vasSafeNetRigistedAdapter);
					vasSafeNetRigistedAdapter.notifyDataSetChanged();
					vasSafeNetUnRigistedAdapter = new VasSafeNetUnRigistedAdapter(
							arrSubRelProductBOUnRegisted, getActivity(),
							FragmentRegisterVasSafeNet.this);
					gridRegisteredUse.setAdapter(vasSafeNetUnRigistedAdapter);
					vasSafeNetUnRigistedAdapter.notifyDataSetChanged();
				}
			} else {
				arrSubRelProductBORegisted = new ArrayList<SubRelProductBO>();
				arrSubRelProductBOUnRegisted = new ArrayList<SubRelProductBO>();
				arrSubRelProductBORegistedSafeNet = new ArrayList<SubRelProductBO>();
				vasSafeNetRigistedAdapter.notifyDataSetChanged();
				vasSafeNetUnRigistedAdapter.notifyDataSetChanged();

				Utils.setDataSpinner(getActivity(),
						arrSubRelProductBORegistedSafeNet, spnVassafenet);

			}
		}
	}

	private class OnPostGetReasonFull implements
			OnPostExecuteListener<List<ReasonDTO>> {
		@Override
		public void onPostExecute(List<ReasonDTO> result, String errorCode,
				String description) {
			arrReasonDTO = new ArrayList<ReasonDTO>();
			if (!CommonActivity.isNullOrEmptyArray(result)) {
				arrReasonDTO.addAll(result);
			} else {
				arrReasonDTO = new ArrayList<ReasonDTO>();
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.notdatareason),
						getActivity().getString(R.string.app_name)).show();
			}
			ReasonDTO reasonDTO = new ReasonDTO();
			reasonDTO.setName(getActivity().getString(R.string.chonlydo));
			reasonDTO.setReasonId("");
			arrReasonDTO.add(0, reasonDTO);

		}
	}

	private class OnPostGetReasonFullSafenet implements
			OnPostExecuteListener<List<ReasonDTO>> {
		@Override
		public void onPostExecute(List<ReasonDTO> result, String errorCode,
				String description) {
			arrReasonDTOSafenet = new ArrayList<ReasonDTO>();
			if (!CommonActivity.isNullOrEmptyArray(result)) {
				arrReasonDTOSafenet.addAll(result);
			} else {
				arrReasonDTOSafenet = new ArrayList<ReasonDTO>();
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.notdatareason),
						getActivity().getString(R.string.app_name)).show();
			}
			ReasonDTO reasonDTO = new ReasonDTO();
			reasonDTO.setName(getActivity().getString(R.string.chonlydo));
			reasonDTO.setReasonId("");
			arrReasonDTO.add(0, reasonDTO);

		}
	}

	private class OnPostGetReasonFullCancel implements
			OnPostExecuteListener<List<ReasonDTO>> {
		@Override
		public void onPostExecute(List<ReasonDTO> result, String errorCode,
				String description) {
			arrReasonDTOCancel = new ArrayList<ReasonDTO>();
			if (!CommonActivity.isNullOrEmptyArray(result)) {
				arrReasonDTOCancel.addAll(result);
				ReasonDTO reasonDTO = new ReasonDTO();
				reasonDTO.setName(getActivity().getString(R.string.chonlydo));
				reasonDTO.setReasonId("");
				arrReasonDTOCancel.add(0, reasonDTO);
				showDialogCancelVas(subRelProductBOSelect, arrReasonDTOCancel);
			} else {
				arrReasonDTOCancel = new ArrayList<ReasonDTO>();
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.notdatareason),
						getActivity().getString(R.string.app_name)).show();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case 1234:
				reasonDTO = (ReasonDTO) data.getExtras().getSerializable(
						"reasonDTO");
				if (reasonDTO != null) {
					edtselectreason.setText(reasonDTO.toString());

				} else {
					edtselectreason.setText("");
					edtselectreason.setHint(getActivity().getString(
							R.string.chonlydo));
				}
				break;
			case 1235:
				reasonDTOCancel = (ReasonDTO) data.getExtras().getSerializable(
						"reasonDTO");
				if (reasonDTOCancel != null) {
					edtcancelvas.setText(reasonDTOCancel.toString());
				} else {
					edtcancelvas.setText("");
					edtcancelvas.setHint(getActivity().getString(
							R.string.chonlydo));
				}
				break;
			case 102:
				promotionUnitVas = (PromotionUnitVas) data.getExtras()
						.getSerializable("PromotionTypeBeans");

				if (!CommonActivity.isNullOrEmpty(promotionUnitVas)) {
					edtselectpromotion.setText(promotionUnitVas
							.getPromotionCodeUnitName());
				}
				break;
			case 103:
				promotionUnitVasNomal = (PromotionUnitVas) data.getExtras()
						.getSerializable("PromotionTypeBeans");

				if (!CommonActivity.isNullOrEmpty(promotionUnitVasNomal)) {
					edtselectpromotionNormal.setText(promotionUnitVasNomal
							.getPromotionCodeUnitName());
				}
				break;
			case 1236:
				reasonDTOSafeNet = (ReasonDTO) data.getExtras()
						.getSerializable("reasonDTO");
				if (reasonDTOSafeNet != null) {
					edtselectreasonvas.setText(reasonDTOSafeNet.toString());
				} else {
					edtselectreasonvas.setText("");
					edtselectreasonvas.setHint(getActivity().getString(
							R.string.chonlydo));
				}
			default:
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btnregisterorcancel:

			if (vaidateRegister()) {
				// CommonActivity.createDialog(getActivity(),
				// getActivity().getString(R.string.confirmregister),
				// getActivity().getString(R.string.app_name),
				// getActivity().getString(R.string.ok),
				// getActivity().getString(R.string.cancel), onclickRegis,
				// null).show();

			}

			break;
		case R.id.edtselectreason:
			if (!CommonActivity.isNullOrEmptyArray(arrReasonDTO)) {
				Intent intent = new Intent(getActivity(),
						FragmentChooseReason.class);
				intent.putExtra("arrReasonDTO", arrReasonDTO);
				startActivityForResult(intent, 1234);
				// FragmentChooseReason fragmentChooseReason = new
				// FragmentChooseReason();
				// Bundle mBundle = new Bundle();
				// mBundle.putSerializable("arrReasonDTO", arrReasonDTO);
				// fragmentChooseReason.setArguments(mBundle);
				// fragmentChooseReason.setTargetFragment(FragmentRegisterVasSafeNet.this,
				// 1234);
				// ReplaceFragment.replaceFragment(getActivity(),
				// fragmentChooseReason, false);
			} else {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.notdatareason),
						getActivity().getString(R.string.app_name)).show();
			}
			break;
		// case R.id.edtselectreasoncancel:
		// if (!CommonActivity.isNullOrEmptyArray(arrReasonDTOCancel)) {
		// FragmentChooseReason fragmentChooseReason = new
		// FragmentChooseReason();
		// Bundle mBundle = new Bundle();
		// mBundle.putSerializable("arrReasonDTO", arrReasonDTOCancel);
		// fragmentChooseReason.setArguments(mBundle);
		// fragmentChooseReason.setTargetFragment(FragmentRegisterVasSafeNet.this,
		// 1235);
		// ReplaceFragment.replaceFragment(getActivity(), fragmentChooseReason,
		// false);
		// } else {
		// CommonActivity.createAlertDialog(getActivity(),
		// getActivity().getString(R.string.notdatareason),
		// getActivity().getString(R.string.app_name)).show();
		// }
		// break;

		default:
			break;
		}
	}

	// OnClickListener onclickRegis = new OnClickListener() {
	//
	// @Override
	// public void onClick(View arg0) {
	// RegisterOrRemoveVASAsyn registerOrRemoveVASAsyn = new
	// RegisterOrRemoveVASAsyn(getActivity());
	// registerOrRemoveVASAsyn.execute();
	//
	// }
	// };

	private boolean vaidateRegister() {
		int demRegister = 0;
		int demCancel = 0;
		boolean ischeckPromotion = false;
		boolean isCheckVas = false;
		if (CommonActivity.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)
				&& CommonActivity
						.isNullOrEmptyArray(arrSubRelProductBORegisted)) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.notdatavas),
					getActivity().getString(R.string.app_name)).show();

			return false;
		}

		if (!CommonActivity.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)) {
			for (SubRelProductBO item : arrSubRelProductBOUnRegisted) {
				if (!item.isRegisted()) {
					demCancel++;
				}
			}
		}
		if (!CommonActivity.isNullOrEmptyArray(arrSubRelProductBORegisted)) {
			for (SubRelProductBO item : arrSubRelProductBORegisted) {
				if (item.isRegisted()) {
					demRegister++;
				}
			}
		}
		if (demCancel == 0 && demRegister == 0) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.validateregister),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		if (!CommonActivity.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)) {
			for (SubRelProductBO item : arrSubRelProductBOUnRegisted) {
				if (item.isRegisted()
						&& Constant.VAS_SAFE_NET.equals(item.getAtrrVas())) {
					isCheckVas = true;
					break;
				}
			}
		}

		int demRegisterVas = 0;
		if (!CommonActivity.isNullOrEmptyArray(arrSubRelProductBORegisted)) {
			for (SubRelProductBO item : arrSubRelProductBORegisted) {
				if (item.isRegisted()) {
					if (Constant.VAS_SAFE_NET.equals(item.getAtrrVas())) {
						demRegisterVas++;
					}
				}
			}
		}

		if (isCheckVas && demRegisterVas > 0) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.uncheckvas),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		if (demRegisterVas > 1) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.registervasin),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		if (!CommonActivity.isNullOrEmptyArray(arrSubRelProductBORegisted)) {
			for (SubRelProductBO item : arrSubRelProductBORegisted) {
				if (item.isRegisted()) {
					if (Constant.VAS_SAFE_NET.equals(item.getAtrrVas())) {
						if (CommonActivity.isNullOrEmpty(item
								.getPromotionCode())) {
							ischeckPromotion = true;
							break;
						}
					}
				}
			}
		}

		if (ischeckPromotion) {
			CommonActivity.createAlertDialog(getActivity(),
					getActivity().getString(R.string.validateselectpromotion),
					getActivity().getString(R.string.app_name)).show();
			return false;
		}

		if (demRegister != 0) {
			if (CommonActivity.isNullOrEmpty(reasonDTO)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.validateReason),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (CommonActivity.isNullOrEmpty(reasonDTO.getReasonId())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.validateReason),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
		}
		if (demCancel != 0) {

			if (CommonActivity.isNullOrEmpty(reasonDTOCancel)) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.validateReasonCancel),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
			if (CommonActivity.isNullOrEmpty(reasonDTOCancel.getReasonId())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.validateReasonCancel),
						getActivity().getString(R.string.app_name)).show();
				return false;
			}
		}
		return true;
	}

	public OnClickListener moveLogInAct = new OnClickListener() {
		@Override
		public void onClick(View v) {
			LoginDialog dialog = new LoginDialog(getActivity(), permission);
			dialog.show();
		}
	};

	@Override
	public void onSelectPromotion(SubRelProductBO subRelProductBO) {
		subRelProductBOSelect = subRelProductBO;
		if (!CommonActivity.isNullOrEmpty(subscriberDTO)) {
			FindByVasAsyn findByVasAsyn = new FindByVasAsyn(getActivity(),
					new OnPosFindByVASNormal(), moveLogInAct);
			findByVasAsyn.execute(subRelProductBOSelect.getVasCode());
		}
	}

	@Override
	public void onChangeCheckedSubRel(SubRelProductBO subRelProductBO) {

	}

	@Override
	public void onChangeCheckedSubRelUnRigisted(SubRelProductBO subRelProductBO) {
		subRelProductBOSelect = subRelProductBO;
		if (!CommonActivity.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)) {
			for (SubRelProductBO item : arrSubRelProductBOUnRegisted) {
				if (subRelProductBO.getVasCode().equals(item.getVasCode())) {
					item.setRegisted(true);
					break;
				}
			}
		}
		if (vasSafeNetUnRigistedAdapter != null) {
			vasSafeNetUnRigistedAdapter.notifyDataSetChanged();
		}

		GetReasonFullPMAsyn getReasonFullPMAsynCancel = new GetReasonFullPMAsyn(
				getActivity(), new OnPostGetReasonFullCancel(), moveLogInAct);
		if (!CommonActivity.isNullOrEmpty(subRelProductBO.getAtrrVas())
				&& Constant.VAS_SAFE_NET.equals(subRelProductBO.getAtrrVas())) {
			getReasonFullPMAsynCancel.execute(subscriberDTO.getOfferId(),
					"414", subscriberDTO.getTelecomServiceAlias(),
					subscriberDTO.getCustType(), subscriberDTO.getSubType(),
					"", "");
		} else {
			getReasonFullPMAsynCancel.execute(subscriberDTO.getOfferId(),
					"102", subscriberDTO.getTelecomServiceAlias(),
					subscriberDTO.getCustType(), subscriberDTO.getSubType(),
					"", "");
		}

	}

	private class OnPosFindByVAS implements
			OnPostExecuteListener<ArrayList<PromotionUnitVas>> {

		@Override
		public void onPostExecute(ArrayList<PromotionUnitVas> result,
				String errorCode, String description) {
			if ("0".equals(errorCode)) {
				GetReasonFullPMAsyn getReasonFullPMAsynVasafe = new GetReasonFullPMAsyn(
						getActivity(), new OnPostGetReasonFullSafenet(),
						moveLogInAct);
				getReasonFullPMAsynVasafe.execute(subscriberDTO.getOfferId(),
						"413", subscriberDTO.getTelecomServiceAlias(),
						subscriberDTO.getCustType(),
						subscriberDTO.getSubType(), "", "");
				if (!CommonActivity.isNullOrEmptyArray(result)) {
					showRegiterVasafenet(subRelProductBOSelect, result);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.notdatakm),
							getActivity().getString(R.string.app_name)).show();
				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getActivity().getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity()
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}
	}

	private class OnPosFindByVASNormal implements
			OnPostExecuteListener<ArrayList<PromotionUnitVas>> {

		@Override
		public void onPostExecute(ArrayList<PromotionUnitVas> result,
				String errorCode, String description) {
			if ("0".equals(errorCode)) {
				if (!CommonActivity.isNullOrEmptyArray(result)) {
					showDialogSavePromotion(subRelProductBOSelect, result);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.notdatakm),
							getActivity().getString(R.string.app_name)).show();
				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)) {
					Dialog dialog = CommonActivity
							.createAlertDialog(
									getActivity(),
									description,
									getActivity().getResources().getString(
											R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = getActivity()
								.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getActivity()
									.getResources()
									.getString(R.string.app_name));
					dialog.show();
				}
			}

		}
	}

	private void showDialogCancelVas(final SubRelProductBO subRelProductBO,
			final ArrayList<ReasonDTO> arReasonDTOs) {
		reasonDTOCancel = new ReasonDTO();
		dialogCancel = new Dialog(getActivity());
		dialogCancel.setContentView(R.layout.dialog_select_reason_cancelvas);
		dialogCancel.setCancelable(false);
		dialogCancel.setTitle(getActivity().getString(R.string.cancelvas));
		EditText edtvas = (EditText) dialogCancel.findViewById(R.id.edtvas);
		edtvas.setText(subRelProductBO.getVasCode());
		edtcancelvas = (EditText) dialogCancel
				.findViewById(R.id.edtreasoncancelvas);
		Button btncancelvas = (Button) dialogCancel
				.findViewById(R.id.btncancelvas);
		btncancelvas.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (CommonActivity.isNullOrEmpty(reasonDTOCancel)) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getString(
									R.string.validateReasonCancel),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(reasonDTOCancel.getReasonId())) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getString(
									R.string.validateReasonCancel),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				OnClickListener onclickCancel = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						RegisterOrRemoveVASAsyn registerOrRemoveVASAsyn = new RegisterOrRemoveVASAsyn(
								getActivity(), subRelProductBO, 0);
						registerOrRemoveVASAsyn.execute();
					}
				};

				CommonActivity.createDialog(getActivity(),
						getActivity().getString(R.string.confirmcancelvas),
						getActivity().getString(R.string.app_name),
						getActivity().getString(R.string.cancel),
						getActivity().getString(R.string.ok ),
						null,onclickCancel ).show();

			}
		});

		edtcancelvas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(),
						FragmentChooseReason.class);
				intent.putExtra("arrReasonDTO", arReasonDTOs);
				startActivityForResult(intent, 1235);
				// FragmentChooseReason fragmentChooseReason = new
				// FragmentChooseReason();
				// Bundle mBundle = new Bundle();
				// mBundle.putSerializable("arrReasonDTO", arReasonDTOs);
				// fragmentChooseReason.setArguments(mBundle);
				// fragmentChooseReason.setTargetFragment(FragmentRegisterVasSafeNet.this,
				// 1235);
				// ReplaceFragment.replaceFragment(getActivity(),
				// fragmentChooseReason, false);
			}
		});

		Button btncancel = (Button) dialogCancel.findViewById(R.id.btncancel);
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogCancel.dismiss();
			}
		});
		dialogCancel.show();

	}

	private void showDialogSavePromotion(final SubRelProductBO subRelProductBO,
			final ArrayList<PromotionUnitVas> arrPromotionUnitVas) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_select_promotion_vasnomal);
		dialog.setCancelable(false);
		dialog.setTitle(getActivity().getString(R.string.chonctkm1));
		EditText edtvas = (EditText) dialog.findViewById(R.id.edtvas);
		edtvas.setText(subRelProductBO.getVasCode());

		edtselectpromotionNormal = (EditText) dialog
				.findViewById(R.id.edtselectpromotion);
		edtselectpromotionNormal.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (!CommonActivity.isNullOrEmptyArray(arrPromotionUnitVas)) {
					Intent intent = new Intent(getActivity(),
							SearchCodePromotionUnitActivity.class);
					intent.putExtra("arrPromotionTypeBeans",
							arrPromotionUnitVas);
					startActivityForResult(intent, 103);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.notdatakm),
							getActivity().getString(R.string.app_name)).show();
				}
			}
		});

		Button btnsave = (Button) dialog.findViewById(R.id.btnsave);
		btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CommonActivity.isNullOrEmpty(promotionUnitVasNomal)) {
					subRelProductBO.setPromotionCode(promotionUnitVasNomal
							.getPromotionCodeUnit());
					subRelProductBO.setPromotionName(promotionUnitVasNomal
							.getPromotionCodeUnitName());
					if (vasSafeNetRigistedAdapter != null) {
						vasSafeNetRigistedAdapter.notifyDataSetChanged();
					}
					dialog.cancel();

				} else {
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getString(
									R.string.validateselectpromotion),
							getActivity().getString(R.string.app_name)).show();
				}
			}
		});
		Button btncancel = (Button) dialog.findViewById(R.id.btncancel);
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.cancel();
			}
		});

		dialog.show();

	}

	private void showRegiterVasafenet(final SubRelProductBO subRelProductBO,
			final ArrayList<PromotionUnitVas> arrPromotionUnitVas) {
		promotionUnitVas = new PromotionUnitVas();
		reasonDTOSafeNet = new ReasonDTO();
		dialogVas = new Dialog(getActivity());
		dialogVas.setContentView(R.layout.dialog_select_promotion);
		dialogVas.setCancelable(false);
		dialogVas.setTitle(getActivity().getString(R.string.registervassafe));
		EditText edtvas = (EditText) dialogVas.findViewById(R.id.edtvas);
		edtvas.setText(subRelProductBO.getVasCode());
		edtselectpromotion = (EditText) dialogVas
				.findViewById(R.id.edtselectpromotion);
		edtselectreasonvas = (EditText) dialogVas
				.findViewById(R.id.edtselectreasonvas);

		edtselectreasonvas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!CommonActivity.isNullOrEmptyArray(arrReasonDTOSafenet)) {
					Intent intent = new Intent(getActivity(),
							FragmentChooseReason.class);
					intent.putExtra("arrReasonDTO", arrReasonDTOSafenet);
					startActivityForResult(intent, 1236);

				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.notdatareason),
							getActivity().getString(R.string.app_name)).show();
				}

			}
		});

		if (!CommonActivity.isNullOrEmpty(subRelProductBO.getPromotionCode())
				&& !CommonActivity.isNullOrEmptyArray(arrPromotionUnitVas)) {
			for (PromotionUnitVas promotionUnitVas : arrPromotionUnitVas) {
				if (subRelProductBO.getPromotionCode().equals(
						promotionUnitVas.getPromotionCodeUnit())) {
					edtselectpromotion.setText(promotionUnitVas
							.getPromotionCodeUnitName());
					break;
				}
			}

		}
		edtselectpromotion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!CommonActivity.isNullOrEmptyArray(arrPromotionUnitVas)) {
					Intent intent = new Intent(getActivity(),
							SearchCodePromotionUnitActivity.class);
					intent.putExtra("arrPromotionTypeBeans",
							arrPromotionUnitVas);
					startActivityForResult(intent, 102);
				} else {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.notdatakm),
							getActivity().getString(R.string.app_name)).show();
				}
			}
		});
		Button btnsave = (Button) dialogVas.findViewById(R.id.btnsave);
		btnsave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				if (CommonActivity.isNullOrEmpty(promotionUnitVas)) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getString(
									R.string.isEmptyPro),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(promotionUnitVas
						.getPromotionCodeUnit())) {
					CommonActivity.createAlertDialog(
							getActivity(),
							getActivity().getString(
									R.string.isEmptyPro),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(reasonDTOSafeNet)) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.validateReason),
							getActivity().getString(R.string.app_name)).show();
					return;
				}
				if (CommonActivity.isNullOrEmpty(reasonDTOSafeNet.getReasonId())) {
					CommonActivity.createAlertDialog(getActivity(),
							getActivity().getString(R.string.validateReason),
							getActivity().getString(R.string.app_name)).show();
					return;
				}

				OnClickListener onclickRegis = new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						RegisterOrRemoveVASAsyn registerOrRemoveVASAsyn = new RegisterOrRemoveVASAsyn(
								getActivity(), subRelProductBO, 2);
						registerOrRemoveVASAsyn.execute();
					}
				};

				CommonActivity.createDialog(getActivity(),
						getActivity().getString(R.string.confirmregister),
						getActivity().getString(R.string.app_name),
						getActivity().getString(R.string.ok),
						getActivity().getString(R.string.cancel), onclickRegis,
						null).show();

			}
		});

		Button btncancel = (Button) dialogVas.findViewById(R.id.btncancel);
		btncancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (!CommonActivity
						.isNullOrEmptyArray(arrSubRelProductBORegistedSafeNet)) {

					for (SubRelProductBO item : arrSubRelProductBORegistedSafeNet) {
						if (CommonActivity.isNullOrEmpty(item.getVasCode())) {
							spnVassafenet
									.setSelection(arrSubRelProductBORegistedSafeNet
											.indexOf(item));
							break;
						}
					}

				}

				dialogVas.dismiss();
			}
		});

		dialogVas.show();
	}

	private boolean validateRegisterVasSafe() {

		return false;
	}

	private void showDialogResultVas(
			ArrayList<MBCCSVasResultBO> arrProductPakage) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.layout_vas_result);
		dialog.setCancelable(false);
		ListView listView = (ListView) dialog.findViewById(R.id.listView);
		GetVasResultAdapter getFeeBCCSAdapter = new GetVasResultAdapter(
				arrProductPakage, getActivity());
		listView.setAdapter(getFeeBCCSAdapter);
		Button btnok = (Button) dialog.findViewById(R.id.btnok);
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog.cancel();
				GetListVasAllAsyn getListVasAllAsyn = new GetListVasAllAsyn(
						getActivity(), new OnPosGetListVasAll(), moveLogInAct);
				getListVasAllAsyn.execute(subscriberDTO.getProductCode(),
						subscriberDTO.getSubId() + "");
			}
		});

		dialog.show();
	}

	private class RegisterOrRemoveVASAsyn extends
			AsyncTask<String, Void, ArrayList<MBCCSVasResultBO>> {
		private Context mContext;
		private String errorCode;
		private String description;
		private ProgressDialog progress;
		private SubRelProductBO subRelProductBO;
		private int mtype = 0;

		public RegisterOrRemoveVASAsyn(Context context,
				SubRelProductBO subRelProductBO, int type) {
			this.mContext = context;
			this.progress = new ProgressDialog(this.mContext);
			// check font
			this.progress.setCancelable(false);
			this.progress.setMessage(context.getResources().getString(
					R.string.processing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}
			this.subRelProductBO = subRelProductBO;
			this.mtype = type;
		}

		@Override
		protected ArrayList<MBCCSVasResultBO> doInBackground(String... arg0) {
			return registerOrRemoveVAS();
		}

		@Override
		protected void onPostExecute(ArrayList<MBCCSVasResultBO> result) {
			super.onPostExecute(result);
			this.progress.dismiss();
			if ("0".equals(errorCode)) {
				if (dialogVas != null) {
					dialogVas.dismiss();
				}
				if (dialogCancel != null) {
					dialogCancel.dismiss();
				}
				if (!CommonActivity.isNullOrEmptyArray(result)) {
					showDialogResultVas(result);
				} else {

					OnClickListener onclick = new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							GetListVasAllAsyn getListVasAllAsyn = new GetListVasAllAsyn(
									getActivity(), new OnPosGetListVasAll(),
									moveLogInAct);
							getListVasAllAsyn.execute(
									subscriberDTO.getProductCode(),
									subscriberDTO.getSubId() + "");
						}
					};

					CommonActivity
							.createAlertDialog(getActivity(), description,
									getActivity().getString(R.string.app_name),
									onclick).show();

				}
			} else {
				if (Constant.INVALID_TOKEN2.equals(errorCode)
						&& description != null && !description.isEmpty()) {
					Dialog dialog = CommonActivity
							.createAlertDialog((Activity) mContext,
									description, mContext.getResources()
											.getString(R.string.app_name),
									moveLogInAct);
					dialog.show();
				} else {
					if (description == null || description.isEmpty()) {
						description = mContext.getString(R.string.checkdes);
					}
					Dialog dialog = CommonActivity.createAlertDialog(
							getActivity(), description, getResources()
									.getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private ArrayList<MBCCSVasResultBO> registerOrRemoveVAS() {
			String original = "";
			ArrayList<MBCCSVasResultBO> lstMBCCSVas = new ArrayList<MBCCSVasResultBO>();
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_registerOrRemoveVAS");
				StringBuilder rawData = new StringBuilder();

				rawData.append("<ws:registerOrRemoveVAS>");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");
				rawData.append("<telecomServiceAlias>"
						+ subscriberDTO.getTelecomServiceAlias()
						+ "</telecomServiceAlias>");
				rawData.append("<isdn>" + subscriberDTO.getAccount()
						+ "</isdn>");

				switch (mtype) {
				case 0:
					// truong hop huy
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)
							&& !CommonActivity.isNullOrEmpty(reasonDTOCancel)
							&& !CommonActivity.isNullOrEmpty(reasonDTOCancel
									.getReasonId())) {
						rawData.append("<reasonIdTemp>"
								+ reasonDTOCancel.getReasonId()
								+ "</reasonIdTemp>");
					}
					break;
				case 1:
					// truong hop dang ky thuong
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBORegisted)
							&& !CommonActivity.isNullOrEmpty(reasonDTO)
							&& !CommonActivity.isNullOrEmpty(reasonDTO
									.getReasonId())) {
						rawData.append("<reasonId>" + reasonDTO.getReasonId()
								+ "</reasonId>");
					}
					break;
				case 2:
					// truogn hop dang ky vas safenet
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBORegistedSafeNet)
							&& !CommonActivity.isNullOrEmpty(reasonDTOSafeNet)
							&& !CommonActivity.isNullOrEmpty(reasonDTOSafeNet
									.getReasonId())) {
						rawData.append("<reasonId>"
								+ reasonDTOSafeNet.getReasonId()
								+ "</reasonId>");
					}
					break;
				default:
					break;
				}
				rawData.append("<vasResponseBO>");
				switch (mtype) {
				case 0:
					// truong hop huy
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBOUnRegisted)
							&& !CommonActivity.isNullOrEmpty(subRelProductBO)) {
						for (SubRelProductBO item : arrSubRelProductBOUnRegisted) {
							if (item.getVasCode().equals(
									subRelProductBO.getVasCode())) {
								rawData.append("<lstVasChange>");
								rawData.append("<vasCode>" + item.getVasCode()
										+ "</vasCode>");
								rawData.append("<registed>" + false
										+ "</registed>");
								rawData.append("<atrrVas>" + item.getAtrrVas()
										+ "</atrrVas>");
								rawData.append("</lstVasChange>");
								break;
							}
						}
					}
					break;
				case 1:
					// truong hop dang ky thuong
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBORegisted)) {
						for (SubRelProductBO item : arrSubRelProductBORegisted) {
							if (item.isRegisted()) {
								rawData.append("<lstVasChange>");
								rawData.append("<vasCode>" + item.getVasCode()
										+ "</vasCode>");
								rawData.append("<registed>" + item.isRegisted()
										+ "</registed>");
								rawData.append("<atrrVas>" + item.getAtrrVas()
										+ "</atrrVas>");
								rawData.append("<promotionCode >"
										+ item.getPromotionCode()
										+ "</promotionCode>");
								rawData.append("</lstVasChange>");
							}
						}
					}
					break;
				case 2:
					// truong hop dang ky vas safenet
					if (!CommonActivity
							.isNullOrEmptyArray(arrSubRelProductBORegistedSafeNet)
							&& !CommonActivity.isNullOrEmpty(subRelProductBO)) {
						for (SubRelProductBO item : arrSubRelProductBORegistedSafeNet) {
							if (item.getVasCode().equals(
									subRelProductBO.getVasCode())) {
								rawData.append("<lstVasChange>");
								rawData.append("<vasCode>" + item.getVasCode()
										+ "</vasCode>");
								rawData.append("<registed>" + true
										+ "</registed>");
								rawData.append("<atrrVas>" + item.getAtrrVas()
										+ "</atrrVas>");
								if (!CommonActivity
										.isNullOrEmpty(promotionUnitVas)) {
									rawData.append("<promotionCode >"
											+ promotionUnitVas
													.getPromotionCodeUnit()
											+ "</promotionCode>");
								}
								rawData.append("</lstVasChange>");
								break;
							}
						}
					}
					break;
				default:
					break;
				}
				rawData.append("</vasResponseBO>");
				rawData.append("</input>");
				rawData.append("</ws:registerOrRemoveVAS>");
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(),
						"mbccs_registerOrRemoveVAS");
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);

				original = output.getOriginal();
				Log.d("originalllllllll", original);
				Serializer serializer = new Persister();
				ParseOuput parseOuput = serializer.read(ParseOuput.class,
						original);
				if (parseOuput != null) {
					errorCode = parseOuput.getErrorCode();
					description = parseOuput.getDescription();
					lstMBCCSVas = parseOuput.getLstMbccsVasResultBO();
				} else {
					errorCode = Constant.ERROR_CODE;
					description = getActivity().getString(
							R.string.no_data_return);
				}
			} catch (Exception e) {
				errorCode = Constant.ERROR_CODE;
				description = getActivity().getString(R.string.no_data_return);
			}
			return lstMBCCSVas;
		}

	}

}
