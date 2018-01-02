package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.advisory.data.VasDTOData;
import com.viettel.bss.viettelpos.v4.advisory.data.VtFreeActivedDTOData;
import com.viettel.bss.viettelpos.v4.cc.object.CCOutput;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetSubscriberInfoTVBH;

import java.util.ArrayList;

/**
 * Created by Admin_pmvt on 7/1/2017.
 */

public class SubcriberInfoFragment extends FragmentCommon {

    private TextView tvPackDataValue;
    private TextView tvDateActiveValue;
    private TextView tvRootAccValue;            // TK goc
    private TextView tvDateDeadlineValue;
    private TextView tvPromotionValue;          // TK KM
    private TextView tvSimValue;
    private TextView tvDeviceValue;
    private TextView tvMyVTInstall;     // Danh sách sim
    private TextView tvVasNoData;
    private TextView tvFreeNoData;
    private LinearLayout subPlusLayout;

    private TableLayout tbVas;
    private TableLayout tbFree;

    private TableRow tbrWarning;
    private TableRow tbrSubAbleView;
    private TextView tvSubOutNet;
    private ImageButton btInfoWarning;
    private WarningSubInfoDialog dialogWarning;

    private CCOutput ccOutput;

    public SubcriberInfoFragment(CCOutput ccOutput) {
        super();
        this.ccOutput = ccOutput;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_number_info_table_fragment, container, false);

        this.tvPackDataValue = (TextView) view.findViewById(R.id.tvPackDataValue);
        this.tvDateActiveValue = (TextView) view.findViewById(R.id.tvDateActiveValue);
        this.tvDateDeadlineValue = (TextView) view.findViewById(R.id.tvDateDeadlineValue);
        this.tvRootAccValue = (TextView) view.findViewById(R.id.tvRootAccValue);
        this.tvPromotionValue = (TextView) view.findViewById(R.id.tvPromotionValue);
        this.tvSimValue = (TextView) view.findViewById(R.id.tvSimValue);
        this.tvDeviceValue = (TextView) view.findViewById(R.id.tvDeviceValue);
        this.tvMyVTInstall = (TextView) view.findViewById(R.id.tvMyVTInstall);
        this.tvVasNoData = (TextView) view.findViewById(R.id.tvVasNoData);
        this.tvFreeNoData = (TextView) view.findViewById(R.id.tvFreeNoData);
        this.subPlusLayout = (LinearLayout) view.findViewById(R.id.subPlusLayout);

        this.tvVasNoData.setVisibility(View.GONE);
        this.tvFreeNoData.setVisibility(View.GONE);
        this.tbVas = (TableLayout) view.findViewById(R.id.tbVas);
        this.tbFree = (TableLayout) view.findViewById(R.id.tbFree);

        this.tbrWarning = (TableRow) view.findViewById(R.id.tbrWarning);
        this.tbrSubAbleView = (TableRow) view.findViewById(R.id.tbrSubAbleView);
        this.tvSubOutNet = (TextView) view.findViewById(R.id.tvSubOutNet);
        this.btInfoWarning = (ImageButton) view.findViewById(R.id.btInfoWarning);

        ArrayList<String> strings = new ArrayList<>(
                ccOutput.getSubscriberInfoData().getLstWarningConsum());
        if (strings.size() > 0) {
            tbrWarning.setVisibility(View.VISIBLE);
        } else {
            tbrWarning.setVisibility(View.GONE);
        }

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        this.tvSubOutNet.startAnimation(anim);

        dialogWarning = new WarningSubInfoDialog(getActivity(), strings);

        this.btInfoWarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogWarning.show();
            }
        });

        this.tvPackDataValue.setText(this.ccOutput.getSubscriberInfoData().getProductCode());
        this.tvDateActiveValue.setText(DateTimeUtils.getDateFromSerialString(
                this.ccOutput.getSubscriberInfoData().getStaDatetime()));
        this.tvDateDeadlineValue.setText(DateTimeUtils.getDateFromSerialString(
                this.ccOutput.getSubscriberInfoData().getExpiredDateTime()));
        this.tvRootAccValue.setText(StringUtils.formatMoney(
                this.ccOutput.getSubscriberInfoData().getAccountBalance()));
        this.tvPromotionValue.setText(StringUtils.formatMoney(
                this.ccOutput.getSubscriberInfoData().getPromotionBalance()));
        this.tvSimValue.setText(this.ccOutput.getSubscriberInfoData().getSimType());
        this.tvDeviceValue.setText(this.ccOutput.getSubscriberInfoData().getDevice());
        if (this.ccOutput.getSubscriberInfoData().getMyViettelInstalled().equals("1")) {
            this.tvMyVTInstall.setText(getString(R.string.advisory_installed_text));
        } else {
            this.tvMyVTInstall.setText(getString(R.string.advisory_not_install_text));
        }

        String valueString = this.ccOutput.getSubscriberInfoData().getMultiSim().trim();
        if (CommonActivity.isNullOrEmpty(valueString)) {
            tbrSubAbleView.setVisibility(View.GONE);
        } else {
            tbrSubAbleView.setVisibility(View.VISIBLE);
            valueString = valueString.replace(";", ", ");
            valueString = valueString.replace(",", ", ");

            String[] subPlusArray = valueString.split(", ");

            for (int i = 0; i < subPlusArray.length; i++) {
                final TextView textView = new TextView(getActivity());
                textView.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                String numberString = subPlusArray[i];
                SpannableString content = new SpannableString(numberString);
                content.setSpan(new UnderlineSpan(), 0, numberString.length(), 0);
                textView.setPadding(0, 3, 3, 3);
                textView.setText(content);
                textView.setTextSize(13);
                textView.setTextColor(Color.BLUE);
                textView.setTypeface(textView.getTypeface(), Typeface.ITALIC);
                textView.setId(Integer.parseInt(subPlusArray[i]));
                subPlusLayout.addView(textView);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        numberActionClick(textView.getText().toString());
                    }
                });
            }
        }

        // table vas
        ArrayList<VasDTOData> vasDTODatas =
                new ArrayList<>(ccOutput.getLstVasDTOs());
        if (vasDTODatas.size() > 0) {
            this.tvVasNoData.setVisibility(View.GONE);
            this.tbVas.setVisibility(View.VISIBLE);
        } else {
            this.tvVasNoData.setVisibility(View.VISIBLE);
            this.tbVas.setVisibility(View.GONE);
        }

        VasDTOData vasDTOData;
        for (int index = 0; index < vasDTODatas.size(); index++) {
            final TableRow tableRow = (TableRow) getActivity().getLayoutInflater()
                    .inflate(R.layout.advisory_tablerow_vas_item_view, null);

            vasDTOData = vasDTODatas.get(index);
            TextView tv1;
            TextView tv2;
            tv1 = (TextView) tableRow.findViewById(R.id.tv1);
            tv1.setText("" + vasDTOData.getVasName());
            tv2 = (TextView) tableRow.findViewById(R.id.tv2);
            tv2.setText("" + DateTimeUtils.getDateFromFullString(
                    vasDTOData.getStaDatetime()));

            if (index % 2 == 0) {
                tv1.setBackgroundResource(R.color.gray_7);
                tv2.setBackgroundResource(R.color.gray_7);
            } else {
                tv1.setBackgroundResource(R.color.gray_8);
                tv2.setBackgroundResource(R.color.gray_8);
            }

            tbVas.addView(tableRow);
        }

        // table free
        ArrayList<VtFreeActivedDTOData> vtFreeActivedDTODatas =
                new ArrayList<>(ccOutput.getLstVtFreeActivedDTOs());
        if (vtFreeActivedDTODatas.size() > 0) {
            this.tvFreeNoData.setVisibility(View.GONE);
            this.tbFree.setVisibility(View.VISIBLE);
        } else {
            this.tvFreeNoData.setVisibility(View.VISIBLE);
            this.tbFree.setVisibility(View.GONE);
        }

        VtFreeActivedDTOData freeActivedDTOData;
        for (int index = 0; index < vtFreeActivedDTODatas.size(); index++) {
            freeActivedDTOData = vtFreeActivedDTODatas.get(index);
            final TableRow tableRow = (TableRow) getActivity().getLayoutInflater()
                    .inflate(R.layout.advisory_tablerow_vas_item_view, null);

            TextView tv1;
            TextView tv2;
            tv1 = (TextView) tableRow.findViewById(R.id.tv1);
            tv1.setText("" + freeActivedDTOData.getProductCode());
            tv2 = (TextView) tableRow.findViewById(R.id.tv2);
            tv2.setText("" + freeActivedDTOData.getProductName());

            if (index % 2 == 0) {
                tv1.setBackgroundResource(R.color.gray_7);
                tv2.setBackgroundResource(R.color.gray_7);
            } else {
                tv1.setBackgroundResource(R.color.gray_8);
                tv2.setBackgroundResource(R.color.gray_8);
            }

            tbFree.addView(tableRow);
        }

        return view;
    }

    @Override
    protected void unit(View v) {

    }

    @Override
    protected void setPermission() {

    }

    private void numberActionClick(final String number) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Xác nhận");
        alertDialogBuilder
                .setMessage("Bạn muốn tìm kiếm thông tin thuê bao " + number + "?")
                .setCancelable(false)
                .setPositiveButton("Có",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        new AsynTaskGetSubscriberInfoTVBH(getActivity(), new OnPostExecuteListener<CCOutput>() {
                            @Override
                            public void onPostExecute(CCOutput result, String errorCode, String description) {
                                if(!CommonActivity.isNullOrEmpty(result)){
                                    ReplaceFragment.replaceFragment(getActivity(), new MainAdvisoryFragment(result), false);
                                } else {
                                    CommonActivity.toast(getActivity(), R.string.no_data);
                                }
                            }
                        }, moveLogInAct).execute(StringUtils.formatIsdn(number));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Không",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}