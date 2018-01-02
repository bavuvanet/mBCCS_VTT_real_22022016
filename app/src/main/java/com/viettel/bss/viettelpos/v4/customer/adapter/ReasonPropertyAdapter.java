package com.viettel.bss.viettelpos.v4.customer.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.customer.object.ReasonCusCareProperty;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.AdapterProvinceSpinner;
import com.viettel.bss.viettelpos.v4.infrastrucure.dal.InfrastrucureDB;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

public class ReasonPropertyAdapter extends BaseAdapter {
	private ArrayList<ReasonCusCareProperty> lstData = new ArrayList<>();
	private final Context mContext;
	private InfrastrucureDB mInfrastrucureDB;

	public ReasonPropertyAdapter(Context mContext,
								 ArrayList<ReasonCusCareProperty> lstData) {
		this.mContext = mContext;
		this.lstData = lstData;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lstData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup arg2) {
		final ReasonCusCareProperty item = lstData.get(position);
		final ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(R.layout.boc_item_property, arg2,
					false);

			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			holder.edtValue = (EditText) convertView
					.findViewById(R.id.edtValue);
			holder.spnValue = (Spinner) convertView.findViewById(R.id.spnValue);
			holder.lnProvince = (LinearLayout) convertView
					.findViewById(R.id.lnProvince);
			holder.lnEditTextSpiner = (LinearLayout) convertView
					.findViewById(R.id.lnEditTextSpiner);
			holder.spProvince = (Spinner) convertView
					.findViewById(R.id.spProvince);
			holder.spDistrict = (Spinner) convertView
					.findViewById(R.id.spDistrict);
			holder.spPrecint = (Spinner) convertView
					.findViewById(R.id.spPrecint);
			holder.edtDetail = (EditText) convertView
					.findViewById(R.id.edtDetail);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.position = position;
		holder.tvName.setText(item.getName());

		if (item.getOptionType().equals(1)) {
			holder.tvName.setText(Html.fromHtml(item.getName()
					+ "<font color=\"#EE0000\">(*)</font>"));
		}

		if (item.getDataType().equals(1) || item.getDataType().equals(7)) { // truong hop du lieu kieu text 1/kieu date = 7
			holder.edtValue.setVisibility(View.VISIBLE);
			holder.lnEditTextSpiner.setVisibility(View.VISIBLE);
			holder.spnValue.setVisibility(View.GONE);
			holder.lnProvince.setVisibility(View.GONE);
			holder.edtValue.setText(item.getValue());

			if(item.getDataType().equals(7)){
				new DateTimeDialogWrapper(holder.edtValue, (Activity)mContext);
				holder.edtValue.setClickable(true);
				holder.edtValue.setFocusable(false);
				holder.edtValue.setFocusableInTouchMode(false);
			}

			holder.edtValue.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
											  int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable editable) {
					// TODO Auto-generated method stub
					ReasonPropertyAdapter.this.lstData.get(holder.position)
							.setValue(editable.toString());
					ReasonPropertyAdapter.this.lstData.get(holder.position)
							.setNameValue(editable.toString());
				}
			});
		} else if (item.getDataType().equals(2)) { // truong hop du lieu kieu
			// select
			holder.spnValue.setVisibility(View.VISIBLE);
			holder.lnEditTextSpiner.setVisibility(View.VISIBLE);
			holder.edtValue.setVisibility(View.GONE);
			holder.lnProvince.setVisibility(View.GONE);
			String[] lstData = new String[item.getLstDataProperty().size()];

			int selectSpn = 0;
			List<Spin> lstSpins = new ArrayList<>();
			for (int i = 0; i < item.getLstDataProperty().size(); i++) {
				Spin spin = new Spin();
				spin.setValue(item.getLstDataProperty().get(i).getName());
				spin.setCode(item.getLstDataProperty().get(i).getValue());

				lstSpins.add(spin);
				if (spin.getCode().equals(
						ReasonPropertyAdapter.this.lstData.get(holder.position)
								.getValue())) {
					selectSpn = i;
				}
			}

			Utils.setDataSpinner(mContext, lstSpins, holder.spnValue);
			holder.spnValue.setSelection(selectSpn);
			holder.spnValue
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0, View v,
												   int position, long arg3) {
							// TODO Auto-generated method stub
							Log.d(getClass().getSimpleName(),
									"onItemSelected with position = "
											+ holder.position
											+ ", value = "
											+ holder.spnValue.getSelectedItem()
											.toString());
							ReasonPropertyAdapter.this.lstData.get(
									holder.position).setValue(
									((Spin) holder.spnValue.getSelectedItem())
											.getCode());
							ReasonPropertyAdapter.this.lstData.get(
									holder.position).setNameValue(
									((Spin) holder.spnValue.getSelectedItem())
											.getValue());
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
							// TODO Auto-generated method stub

						}
					});
		} else if (item.getDataType().equals(3)) { // truong hop chon tinh
			holder.lnEditTextSpiner.setVisibility(View.GONE);
			holder.lnProvince.setVisibility(View.VISIBLE);

			mInfrastrucureDB = new InfrastrucureDB(mContext);

			if (CommonActivity.isNullOrEmptyArray(holder.lstProvince)) {
				AreaObj areaObj = new AreaObj();
				areaObj.setProvince("-1");
				areaObj.setName(mContext.getResources().getString(
						R.string.select_area));

				holder.lstProvince.add(areaObj);
				holder.lstProvince.addAll(mInfrastrucureDB.getLisProvince());

				holder.lstDistrict.add(areaObj);
				holder.lstPrecint.add(areaObj);
				// initSpinnerDistrict(holder, holder.lstProvince.get(0)
				// .getProvince());
			}

			holder.adapterProvince = new AdapterProvinceSpinner(
					holder.lstProvince, (Activity) mContext);
			holder.spProvince.setAdapter(holder.adapterProvince);

			holder.adapterDistrict = new AdapterProvinceSpinner(
					holder.lstDistrict, (Activity) mContext);
			holder.spDistrict.setAdapter(holder.adapterDistrict);

			holder.adapterPrecint = new AdapterProvinceSpinner(
					holder.lstPrecint, (Activity) mContext);
			holder.spPrecint.setAdapter(holder.adapterPrecint);

			holder.spProvince
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
												   View arg1, int position, long arg3) {

							AreaObj areaObj = (AreaObj) holder.spProvince
									.getSelectedItem();

							if ("-1".equals(areaObj.getProvince())) {
								AreaObj tmp = new AreaObj();
								tmp.setProvince("-1");
								tmp.setName(mContext.getResources().getString(
										R.string.select_area));

								holder.lstDistrict.clear();
								holder.lstDistrict.add(tmp);
								if (holder.adapterDistrict != null) {
									holder.adapterDistrict
											.notifyDataSetChanged();
								}

								holder.lstPrecint.clear();
								holder.lstPrecint.add(tmp);
								if (holder.adapterPrecint != null) {
									holder.adapterPrecint
											.notifyDataSetChanged();
								}

							} else {
								initSpinnerDistrict(holder, holder.lstProvince
										.get(position).getProvince());
							}

							initAddress(holder);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

			holder.spDistrict
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
												   View arg1, int position, long arg3) {

							AreaObj areaObj = (AreaObj) holder.spDistrict
									.getSelectedItem();

							if ("-1".equals(areaObj.getProvince())) {
								AreaObj tmp = new AreaObj();
								tmp.setProvince("-1");
								tmp.setName(mContext.getResources().getString(
										R.string.select_area));

								holder.lstPrecint.clear();
								holder.lstPrecint.add(tmp);
								if (holder.adapterPrecint != null) {
									holder.adapterPrecint
											.notifyDataSetChanged();
								}

							} else {
								Log.d(getClass().getSimpleName(), "initSpinnerPrecint");
								initSpinnerPrecint(holder, holder.lstDistrict
										.get(position).getAreaCode());
							}
							initAddress(holder);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

			holder.spPrecint
					.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
												   View arg1, int arg2, long arg3) {
							initAddress(holder);
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {

						}
					});

			holder.edtDetail.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1,
										  int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1,
											  int arg2, int arg3) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable editable) {
					// TODO Auto-generated method stub
					initAddress(holder);
				}
			});

		}
		return convertView;
	}

	public ArrayList<ReasonCusCareProperty> getLstData() {
		return lstData;
	}

	public void setLstData(ArrayList<ReasonCusCareProperty> lstData) {
		this.lstData = lstData;
	}

	/**
	 * initSpinnerDistrict
	 *
	 * @param parentCodeProvince
	 */
	private void initSpinnerDistrict(ViewHolder holder,
									 String parentCodeProvince) {
		AreaObj tmp = new AreaObj();
		tmp.setProvince("-1");
		tmp.setName(mContext.getResources().getString(R.string.select_area));
		holder.lstDistrict.clear();
		holder.lstDistrict.add(tmp);

		holder.lstDistrict.addAll(mInfrastrucureDB
				.getLisDistrict(parentCodeProvince));

		holder.adapterDistrict = new AdapterProvinceSpinner(holder.lstDistrict,
				(Activity) mContext);
		holder.spDistrict.setAdapter(holder.adapterDistrict);
		holder.spDistrict.setSelection(0);
		holder.spPrecint.setSelection(0);

	}

	/**
	 * initSpinnerPrecint
	 *
	 * @param parentCodeDistrict
	 */
	private void initSpinnerPrecint(ViewHolder holder, String parentCodeDistrict) {
		holder.lstPrecint = mInfrastrucureDB
				.getListPrecinct(parentCodeDistrict);

		holder.adapterPrecint = new AdapterProvinceSpinner(holder.lstPrecint,
				(Activity) mContext);
		holder.spPrecint.setAdapter(holder.adapterPrecint);
		holder.spPrecint.setSelection(0);

	}

	private class ViewHolder {
		TextView tvName;
		Spinner spnValue;
		EditText edtValue;
		LinearLayout lnProvince;
		LinearLayout lnEditTextSpiner;
		Spinner spProvince;
		Spinner spDistrict;
		Spinner spPrecint;
		EditText edtDetail;

		final ArrayList<AreaObj> lstProvince = new ArrayList<>();
		final ArrayList<AreaObj> lstDistrict = new ArrayList<>();
		ArrayList<AreaObj> lstPrecint = new ArrayList<>();

		AdapterProvinceSpinner adapterProvince;
		AdapterProvinceSpinner adapterDistrict;
		AdapterProvinceSpinner adapterPrecint;

		int position;
	}

	private void initAddress(ViewHolder holder) {
		AreaObj areaProvince = (AreaObj) holder.spProvince.getSelectedItem();
		AreaObj areaDistrict = (AreaObj) holder.spDistrict.getSelectedItem();
		AreaObj areaPrecint = (AreaObj) holder.spPrecint.getSelectedItem();

		if (areaProvince == null || areaDistrict == null || areaPrecint == null
				|| "-1".equals(areaProvince.getProvince())
				|| "-1".equals(areaDistrict.getProvince())
				|| "-1".equals(areaPrecint.getProvince())) {
			ReasonPropertyAdapter.this.lstData.get(holder.position)
					.setValue("");
			ReasonPropertyAdapter.this.lstData.get(holder.position)
					.setNameValue("");
			return;
		}

		String code = areaProvince.getProvince() + "-" + areaDistrict.getAreaCode() + "-" + areaPrecint.getAreaCode();
		String addres = areaPrecint.getName() + "-" + areaDistrict.getName()
				+ "-" + areaProvince.getName();
		if (!CommonActivity.isNullOrEmpty(holder.edtDetail)) {
			addres = holder.edtDetail.getText().toString().trim() + "-" + addres;
		}

		ReasonPropertyAdapter.this.lstData.get(holder.position).setValue(code + ";" + addres);
		ReasonPropertyAdapter.this.lstData.get(holder.position).setNameValue(
				addres);

	}

}
