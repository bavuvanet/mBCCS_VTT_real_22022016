package com.viettel.bss.viettelpos.v4.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.login.activity.FragmentLoginNotData;
import com.viettel.bss.viettelpos.v4.object.SurveyAnswer;

public class SurveyAnwserAdapter extends BaseAdapter {

	private final String logTag = SurveyAnwserAdapter.class.getName();
	private final Context context;
	private List<SurveyAnswer> lstData = new ArrayList<>();
	private String anwserType = "";

	public SurveyAnwserAdapter(Context context, List<SurveyAnswer> lstData,
			String anwserType) {
		this.context = context;
		this.lstData = lstData;
		this.anwserType = anwserType;
	}

	@Override
	public int getCount() {
		if (lstData == null) {
			return 0;
		} else {
			return lstData.size();
		}
	}

	@Override
	public Object getItem(int arg0) {
		return lstData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	static class ViewHoder {
		TextView tvAnwser;
		// TextView tvStockModelName;
		CheckBox checkBox;
		RadioButton radioButton;
	}

	@Override
	public View getView(int position, View convertview, ViewGroup arg2) {
		View mView = convertview;
		final ViewHoder hoder;
		if (mView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			mView = inflater
					.inflate(R.layout.layout_survey_anwser, arg2, false);
			hoder = new ViewHoder();
			hoder.tvAnwser = (TextView) mView.findViewById(R.id.tvAnwser);
			hoder.checkBox = (CheckBox) mView.findViewById(R.id.checkBox);
			hoder.radioButton = (RadioButton) mView
					.findViewById(R.id.radioButton);
			if (FragmentLoginNotData.TYPE_CHECKBOX.equals(anwserType)) {
				hoder.checkBox.setVisibility(View.VISIBLE);
				hoder.radioButton.setVisibility(View.GONE);
			} else if (FragmentLoginNotData.TYPE_RADIO_BUTTON
					.equals(anwserType)
					|| FragmentLoginNotData.TYPE_COMBOBOX.equals(anwserType)) {
				hoder.checkBox.setVisibility(View.GONE);
				hoder.radioButton.setVisibility(View.VISIBLE);
			} else {
				hoder.checkBox.setVisibility(View.GONE);
				hoder.radioButton.setVisibility(View.GONE);
			}
			mView.setTag(hoder);
		} else {
			hoder = (ViewHoder) mView.getTag();
		}
		final SurveyAnswer item = lstData.get(position);

		if (item != null) {
			hoder.tvAnwser.setText(item.getContent());
			hoder.checkBox.setChecked(item.getIsChoose());
			hoder.radioButton.setChecked(item.getIsChoose());
		}
		return mView;
	}

}