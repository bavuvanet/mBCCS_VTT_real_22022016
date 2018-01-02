package com.viettel.bss.viettelpos.v4.work.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.viettel.bss.smartphonev2.commons.OnPostExecute;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommonV4;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.adapter.AdapterSaleProgram;
import com.viettel.bss.viettelpos.v4.work.asynctask.AsyncFindActiveByProvinceCode;
import com.viettel.bss.viettelpos.v4.work.object.RollUpOutput;
import com.viettel.bss.viettelpos.v4.work.object.SaleProgramDTO;

public class FragmentRollUp extends FragmentCommonV4 {

	private Activity activity;
	private ListView lvItem;
	private List<SaleProgramDTO> lstProgram;
	private FragmentRollUpProgramDetail dialog;
	private SaleProgramDTO selectedItem;

	@Override
	public void onResume() {
		super.onResume();
		txtNameActionBar.setText(activity.getString(R.string.lst_program));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		idLayout = R.layout.layout_roll_up_list_program;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = getActivity();

	}

	@Override
	public void unit(View v) {
		lvItem = (ListView) v.findViewById(R.id.lvProgram);
		lvItem.setOnItemClickListener(this);
		AsyncFindActiveByProvinceCode asy = new AsyncFindActiveByProvinceCode(
				onPost, getActivity());
		asy.execute();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
		selectedItem = lstProgram.get(arg2);
		dialog = new FragmentRollUpProgramDetail(selectedItem, onPostRollUp);
		dialog.show(getFragmentManager(), "Dialog program detail");

	}

	OnPostExecute<RollUpOutput> onPost = new OnPostExecute<RollUpOutput>() {

		@Override
		public void onPostExecute(RollUpOutput result) {
			lstProgram = result.getLstSaleProgram();
			AdapterSaleProgram adapter = new AdapterSaleProgram(act,
					result.getLstSaleProgram());
			lvItem.setAdapter(adapter);
		}
	};

	public void setPermission() {
		permission = ";m.roll.up;";
	}

	private OnPostExecute<RollUpOutput> onPostRollUp = new OnPostExecute<RollUpOutput>() {

		@Override
		public void onPostExecute(RollUpOutput result) {
			CommonActivity.createAlertDialog(getActivity(),
					R.string.roll_up_success, R.string.app_name).show();
			selectedItem.setNumOfRollUp(selectedItem.getNumOfRollUp() + 1);
			AdapterSaleProgram adapter = new AdapterSaleProgram(act, lstProgram);
			lvItem.setAdapter(adapter);

			dialog.getDialogRollUp().dismiss();
			dialog.dismiss();

		}
	};
}
