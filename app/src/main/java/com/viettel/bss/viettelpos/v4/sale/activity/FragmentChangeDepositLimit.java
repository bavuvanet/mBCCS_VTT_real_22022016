package com.viettel.bss.viettelpos.v4.sale.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.sale.asytask.AsyncTaskGetListReason;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

public class FragmentChangeDepositLimit extends FragmentCommon {

	private SubscriberDTO sub;
	private Spinner spnReason;
	private TextView tvAccount, tvCallLimit, tvSmsLimit, tvDataLimit;
	private EditText edtCallLimit, edtSmsLimit, edtDataLimit;
	private Button btnChangeDepositLimit;
	private View prbReason;
	private List<Spin> lstReason = new ArrayList<Spin>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		idLayout = R.layout.layout_change_deposit_limit;
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		act = getActivity();
		mBundle = getArguments();
		sub = (SubscriberDTO) mBundle.getSerializable("subscriberDTO");
	}

	@Override
	public void onResume() {
		setTitleActionBar(R.string.change_limit);
		super.onResume();
	}

	@Override
	public void unit(View v) {

		spnReason = (Spinner) v.findViewById(R.id.spnReason);
		tvAccount = (TextView) v.findViewById(R.id.tvAccount);
		tvCallLimit = (Button) v.findViewById(R.id.tvCallLimit);
		tvDataLimit = (Button) v.findViewById(R.id.tvDataLimit);
		tvSmsLimit = (Button) v.findViewById(R.id.tvSmsLimit);
		edtCallLimit = (EditText) v.findViewById(R.id.edtCallLimit);
		edtSmsLimit = (EditText) v.findViewById(R.id.edtSmsLimit);
		edtDataLimit = (EditText) v.findViewById(R.id.edtDataLimit);
		btnChangeDepositLimit = (Button) v
				.findViewById(R.id.btnChangeDepositLimit);
		prbReason = v.findViewById(R.id.prbReason);
		tvAccount.setText(sub.getAccount());

		// Lay ly do thay doi dat coc - han muc
		AsyncTaskGetListReason asy = new AsyncTaskGetListReason(act,
				onPostGetReason, moveLogInAct, "18");
		asy.execute(sub);
	}

	@Override
	public void setPermission() {
		permission = ";m.change.deposit.limit;";
	}

	OnPostExecuteListener<ArrayList<Spin>> onPostGetReason = new OnPostExecuteListener<ArrayList<Spin>>() {

		@Override
		public void onPostExecute(ArrayList<Spin> result, String errorCode,
				String description) {
			lstReason = result;
			ArrayAdapter<Spin> adapter = new ArrayAdapter<Spin>(getActivity(),
					R.layout.spinner_item, lstReason);
			spnReason.setAdapter(adapter);
		}
	};
}
