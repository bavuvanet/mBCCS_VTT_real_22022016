package com.viettel.bss.viettelpos.v4.work.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.charge.dal.CacheDataCharge;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterRegisterRollUpBO;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterRegisterRollUpBO.OnCancelRegister;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncCancelRegisterRollUp;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncViewHistoryRegisterRollUp;
import com.viettel.bss.viettelpos.v4.work.object.RegisterRollUpBO;
import com.viettel.bss.viettelpos.v4.work.object.RollUpBO;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;
import com.viettel.bss.viettelpos.v4.work.object.SaleProgramDTO;

public class FragmentRollUpProgramDetail extends DialogFragment implements
		OnClickListener {
	private View view;
	private SaleProgramDTO program;
	private TextView tvProName, tvDuration, tvRollUp, tvToday, tvRegisterInfo;
	private View btnRollUp, imgRegister;
	private ListView lvRegister;
	private Activity act;
	private OnPostExecute<RollUpOutput> onPostRollUp;
	private DialogFragment dialogRollUp;
	private DialogFragment dialogRegisterRollUp;
	private List<RegisterRollUpBO> lstRollUp = new ArrayList<RegisterRollUpBO>();
	private RegisterRollUpBO cancelBo;

	public FragmentRollUpProgramDetail(SaleProgramDTO program,
			OnPostExecute<RollUpOutput> onPostRollUp) {
		this.program = program;
		this.onPostRollUp = onPostRollUp;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		act = getActivity();
		if (view == null) {
			view = inflater.inflate(R.layout.layout_roll_up_program_detail,
					container, false);
		}
		tvProName = (TextView) view.findViewById(R.id.tvProName);
		tvDuration = (TextView) view.findViewById(R.id.tvDuration);
		tvRollUp = (TextView) view.findViewById(R.id.tvRollUp);
		tvToday = (TextView) view.findViewById(R.id.tvToday);
		tvRegisterInfo = (TextView) view.findViewById(R.id.tvRegisterInfo);
		tvProName.setText(program.getName());
		getDialog().setTitle(program.getName());
		tvDuration.setText(program.getDuration());
		if (program.getNumOfRollUp() > 0) {
			tvRollUp.setText(getString(R.string.roll_up_num,
					program.getNumOfRollUp()));
		} else {
			tvRollUp.setText(getString(R.string.roll_up_empty));
		}
		btnRollUp = view.findViewById(R.id.btnRollUp);
		btnRollUp.setOnClickListener(this);

		Date fromDate = DateTimeUtils.convertDateFromSoap(program
				.getEffectDatetime());
		if (fromDate != null) {
			fromDate = DateTimeUtils.truncDate(fromDate);
			Date now = new Date();
			if (now.before(fromDate)) {
				btnRollUp.setVisibility(View.GONE);
				tvRollUp.setText(getString(R.string.roll_up_cannot_roll_up));
			}
		}
		if (program.getNumOfRollUp() >= 2) {
			btnRollUp.setVisibility(View.GONE);
			tvRollUp.setText(getString(R.string.roll_up_num,
					program.getNumOfRollUp())
					+ "." + getString(R.string.cannot_roll_up));
		}
		imgRegister = view.findViewById(R.id.imgRegister);
		imgRegister.setOnClickListener(this);
		lvRegister = (ListView) view.findViewById(R.id.lvRegister);
		tvToday.setText(getString(R.string.text_today,
				DateTimeUtils.convertDateTimeToString(new Date(), "dd/MM/yyyy")));
		AsyncViewHistoryRegisterRollUp asy = new AsyncViewHistoryRegisterRollUp(
				program.getSaleProgramId(), null, null,
				onPostSearchRegisterHis, getActivity());
		asy.execute();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		Dialog dialog = getDialog();
		if (dialog != null) {
			int width = ViewGroup.LayoutParams.MATCH_PARENT;
			int height = ViewGroup.LayoutParams.MATCH_PARENT;
			dialog.getWindow().setLayout(width, height);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(getActivity(), Constant.phoneNumber);
			break;

		case R.id.relaBackHome:
			if (getActivity() == null) {
				onClickListenerBack.onClick(arg0);
				return;
			}
			getActivity().onBackPressed();
			CacheDataCharge.getInstance().setLisArrayListRe(null);
			CacheDataCharge.getInstance().setLisArrayList(null);
			break;
		case R.id.btnRollUp:
			RollUpBO bo = new RollUpBO();
			bo.setX(CommonActivity.findMyLocation(act).getX());
			bo.setY(CommonActivity.findMyLocation(act).getY());
			bo.setProgramId(program.getSaleProgramId());
			bo.setProgramName(program.getName());
			dialogRollUp = new FragmentDialogRollUp(bo, onPostRollUp);
			dialogRollUp.show(getFragmentManager(), "Dialog RollUp");
			break;
		case R.id.imgRegister:
			RegisterRollUpBO registerBO = new RegisterRollUpBO();
			registerBO.setProgramId(program.getSaleProgramId());
			registerBO.setProgramName(program.getName());
			dialogRegisterRollUp = new FragmentDialogRegisterRollUp(registerBO,
					program, onPostRegister);
			dialogRegisterRollUp.show(getFragmentManager(), "Dialog register");
			break;
		default:
			break;
		}

	}

	public DialogFragment getDialogRollUp() {
		return dialogRollUp;
	}

	public void setDialogRollUp(DialogFragment dialogRollUp) {
		this.dialogRollUp = dialogRollUp;
	}

	public OnClickListener onClickListenerBack = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (act != null) {
				act.onBackPressed();
			} else {
				LoginActivity.getInstance().onBackPressed();
			}
		}
	};
	public OnPostExecute<RollUpOutput> onPostSearchRegisterHis = new OnPostExecute<RollUpOutput>() {

		@Override
		public void onPostExecute(RollUpOutput result) {
			lstRollUp = result.getLstRegisterRollUpBo();

			if (CommonActivity.isNullOrEmpty(lstRollUp)) {
				tvRegisterInfo.setText(R.string.register_roll_up_empty);
			} else {
				tvRegisterInfo.setText(R.string.register_roll_up_info);
			}

			AdapterRegisterRollUpBO adapter = new AdapterRegisterRollUpBO(
					getActivity(), lstRollUp, onCancel);
			lvRegister.setAdapter(adapter);
		}
	};

	public OnPostExecute<RollUpOutput> onPostRegister = new OnPostExecute<RollUpOutput>() {

		@Override
		public void onPostExecute(RollUpOutput result) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.register_roll_up_success, R.string.app_name)
					.show();
			AsyncViewHistoryRegisterRollUp asy = new AsyncViewHistoryRegisterRollUp(
					program.getSaleProgramId(), null, null,
					onPostSearchRegisterHis, getActivity());
			asy.execute();
			dialogRegisterRollUp.dismiss();
		}
	};

	OnCancelRegister onCancel = new OnCancelRegister() {

		@Override
		public void onCancelRegister(RegisterRollUpBO bo) {
			cancelBo = bo;
			CommonActivity.createDialog(
					getActivity(),
					getString(R.string.confirm_cancel_register_roll_up,
							bo.getDuration()), getString(R.string.app_name),
					getString(R.string.cancel), getString(R.string.ok), null,
					confirm).show();
		}
	};

	public OnPostExecute<RollUpOutput> onPostCancel = new OnPostExecute<RollUpOutput>() {
		@Override
		public void onPostExecute(RollUpOutput result) {
			CommonActivity
					.createAlertDialog(getActivity(),
							R.string.cancel_register_roll_up_success,
							R.string.app_name).show();
			for (int i = 0; i < lstRollUp.size(); i++) {
				if (lstRollUp.get(i).getId().equals(cancelBo.getId())) {
					lstRollUp.remove(i);
					AdapterRegisterRollUpBO adapter = new AdapterRegisterRollUpBO(
							getActivity(), lstRollUp, onCancel);
					lvRegister.setAdapter(adapter);
					break;
				}
			}
		}
	};

	private OnClickListener confirm = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			AsyncCancelRegisterRollUp asy = new AsyncCancelRegisterRollUp(
					cancelBo.getId(), onPostCancel, getActivity());
			asy.execute();
		}
	};

}
