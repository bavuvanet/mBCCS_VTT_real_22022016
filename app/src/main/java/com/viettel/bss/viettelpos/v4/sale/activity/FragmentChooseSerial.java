package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentCusCareByDay;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentMenuHome;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.SaleCommons;
import com.viettel.bss.viettelpos.v4.sale.adapter.AvailableSerialAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SelectedSerialAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.SelectedSerialAdapter.OnCancelSerialListener;
import com.viettel.bss.viettelpos.v4.sale.business.LookUpStockBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

import java.util.ArrayList;

public class FragmentChooseSerial extends Fragment implements OnClickListener,
        OnCancelSerialListener, OnItemClickListener {
    private static final String TAG = FragmentChooseSerial.class.getSimpleName();
    private String logTag = FragmentChooseSerial.class.getName();
    private StockModel stockModel;
    private TextView tvQuantitySaling;
    private TextView tvStockModelCodeName;
    private ListView lvSelectedSerial;
    private SelectedSerialAdapter selectedSerialAdapter;
    private AvailableSerialAdapter avaiLableSerialAdapter;
    private ArrayList<String> lstSelectedSerial = new ArrayList<String>();
    private TextView tvQuantityIssue;
    // private ImageView imgAddSerial;
    private Button btnAccept;

    private EditText edtSearchSerial;
    private Button btnSelectSerial;
    private Button btnCancelSelectSerial;
    private Dialog selectSerialDialog;
    private TextView tvMissingSerial;
    private TextView tvMissingSerialOnSelectDialog;
    private TextView tvMissingSerialOnAvailableDialog;

    private View view;
    private TextView tvMessage;
    private Button btnHome;
    private TextView txtNameActionBar;
    private Boolean isMissingSerial = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        if (view == null) {
            Log.d(TAG, "view is null");
            view = inflater.inflate(R.layout.sale_choose_serial, container,
                    false);
            Bundle bundle = getArguments();
            if (bundle != null) {
                stockModel = (StockModel) bundle.getSerializable("stockModel");
            }


            tvStockModelCodeName = (TextView) view
                    .findViewById(R.id.tvStockModelCodeName);
            lvSelectedSerial = (ListView) view
                    .findViewById(R.id.lvSelectedSerial);
            tvQuantityIssue = (TextView) view
                    .findViewById(R.id.tvQuantityIssue);
            tvQuantitySaling = (TextView) view
                    .findViewById(R.id.tvQuantitySaling);
            tvQuantitySaling.setText(stockModel.getQuantitySaling() + "");
            selectedSerialAdapter = new SelectedSerialAdapter(getActivity(),
                    lstSelectedSerial, this, stockModel.isStockHandset());
            selectedSerialAdapter.setLstData(SaleCommons.getRangeSerial(
                    SaleCommons.sortSerial(lstSelectedSerial, stockModel.isStockHandset()),
                    stockModel.isStockHandset()));
            lvSelectedSerial.setAdapter(selectedSerialAdapter);
            tvMissingSerial = (TextView) view
                    .findViewById(R.id.tvMissingSerial);
            btnAccept = (Button) view.findViewById(R.id.btnOk);
            if (stockModel.getSelectedSerial().size() > 0) {
                lstSelectedSerial.addAll(stockModel.getSelectedSerial());
                selectedSerialAdapter.setLstData(SaleCommons.getRangeSerial(
                        lstSelectedSerial, stockModel.isStockHandset()));
                selectedSerialAdapter.notifyDataSetChanged();
            } else {
                stockModel.setLstSerial(LookUpStockBusiness.getListSerial(
                        getActivity(), stockModel));
                fillSerial();
            }
            tvQuantityIssue.setText(stockModel.getQuantityIssue() + "");
            tvStockModelCodeName.setText(Html.fromHtml("<b>"
                    + stockModel.getStockModelCode() + "<b>")
                    + " " + stockModel.getStockModelName());
            btnAccept.setVisibility(View.VISIBLE);
            btnAccept.setOnClickListener(this);

            if (getActivity() instanceof FragmentCusCareByDay) {
                Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
                getActivity().findViewById(R.id.llContainer).setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        if (v == btnAccept) {
            if (isMissingSerial) {
                selectSerialDialog = new Dialog(getActivity());
                selectSerialDialog
                        .requestWindowFeature(Window.FEATURE_NO_TITLE);
                selectSerialDialog
                        .setContentView(R.layout.sale_availabel_serial_layout);

                avaiLableSerialAdapter = new AvailableSerialAdapter(
                        getActivity(), SaleCommons.getRangeSerial(
                        stockModel.getLstSerial(), stockModel.isStockHandset()),
                        stockModel.isStockCard(), stockModel.isStockHandset());
                ListView lvAvailable = (ListView) selectSerialDialog
                        .findViewById(R.id.lvAvailableSerial);
                lvAvailable.setAdapter(avaiLableSerialAdapter);
                edtSearchSerial = (EditText) selectSerialDialog
                        .findViewById(R.id.edtSearchSerial);
                if (stockModel.isStockHandset()) {
                    edtSearchSerial.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                edtSearchSerial.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                    }

                    @Override
                    public void beforeTextChanged(CharSequence arg0, int arg1,
                                                  int arg2, int arg3) {
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        avaiLableSerialAdapter.filter(editable.toString()
                                .trim(), stockModel.isStockHandset());
                    }
                });
                lvAvailable.setOnItemClickListener(this);
                tvMissingSerialOnAvailableDialog = (TextView) selectSerialDialog
                        .findViewById(R.id.tvMissingSerial);
                tvMissingSerialOnAvailableDialog.setText(getResources()
                        .getString(R.string.missing_serial)
                        + ": "
                        + (stockModel.getQuantitySaling() - lstSelectedSerial
                        .size()));
                selectSerialDialog.show();
            } else {
                stockModel.setSelectedSerial(lstSelectedSerial);
                Intent i = new Intent();
                i.putExtra("stockModel", stockModel);
                getTargetFragment().onActivityResult(getTargetRequestCode(),
                        Activity.RESULT_OK, i);
                if (getActivity() instanceof FragmentCusCareByDay) {
                    Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
                    getActivity().findViewById(R.id.llViewPager).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.llContainer).setVisibility(View.GONE);
                } else {
                    Log.d(TAG, "activity dont instanceof FragmentCusCareByDay");
                    getActivity().onBackPressed();
                }
            }
        } else {
            switch (v.getId()) {
                case R.id.btnHome:
                    FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
                    ReplaceFragment.replaceFragment(getActivity(),
                            fragmentMenuHome, true);
                    break;
                case R.id.relaBackHome:
                    if (getActivity() instanceof FragmentCusCareByDay) {
                        Log.d(TAG, "activity is instanceof FragmentCusCareByDay");
                        getActivity().findViewById(R.id.llViewPager).setVisibility(View.VISIBLE);
                        getActivity().findViewById(R.id.llContainer).setVisibility(View.GONE);
                    } else {
                        Log.d(TAG, "activity dont instanceof FragmentCusCareByDay");
                        getActivity().onBackPressed();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (getActivity() instanceof FragmentCusCareByDay) {
            Log.d(this.getClass().getSimpleName(), "getActivity() is instanceof FragmentCusCareByDay");
        } else {
            MainActivity.getInstance().setTitleActionBar(R.string.choose_serial);
        }
    }

    /**
     * Thuc hien lay dai serial da co fill vao danh sach
     */
    private void fillSerial() {
        try {
            // Long salingQuantity = Long.parseLong(edtQuantitySaling.getText()
            // .toString().trim());
            stockModel.setLstSerial(SaleCommons.sortSerial(
                    stockModel.getLstSerial(), stockModel.isStockHandset()));
            lstSelectedSerial = new ArrayList<String>();
            if (stockModel.getQuantitySaling() > stockModel.getLstSerial()
                    .size()) {
                lstSelectedSerial.addAll(stockModel.getLstSerial());
                stockModel.setLstSerial(new ArrayList<String>());
            } else {
                lstSelectedSerial.addAll(stockModel.getLstSerial().subList(0,
                        stockModel.getQuantitySaling().intValue()));
                ArrayList<String> tmp = new ArrayList<String>();
                tmp.addAll(stockModel.getLstSerial().subList(
                        stockModel.getQuantitySaling().intValue(),
                        stockModel.getLstSerial().size()));
                stockModel.setLstSerial(tmp);
            }
            selectedSerialAdapter.setLstData(SaleCommons.getRangeSerial(
                    lstSelectedSerial, stockModel.isStockHandset()));
            selectedSerialAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e(logTag, "Exception fillSerial", e);
        }
    }

    @Override
    public void onCancelSerial(Serial serial) {

        for (int i = 0; i < lstSelectedSerial.size(); i++) {
            String tmp = lstSelectedSerial.get(i);
            if (tmp.equals(serial.getToSerial())) {
                lstSelectedSerial.remove(i);
                stockModel.getLstSerial().add(tmp);
                break;
            }
            if (SaleCommons.checkInRange(serial, tmp,
                    stockModel.isStockHandset())) {
                lstSelectedSerial.remove(i);
                i--;
                stockModel.getLstSerial().add(tmp);
            }
        }
        stockModel.setLstSerial(SaleCommons.sortSerial(
                stockModel.getLstSerial(), stockModel.isStockHandset()));
        selectedSerialAdapter.setLstData(SaleCommons.getRangeSerial(
                lstSelectedSerial, stockModel.isStockHandset()));
        selectedSerialAdapter.notifyDataSetChanged();
        tvMissingSerial.setText(getResources().getString(
                R.string.missing_serial)
                + ": "
                + (stockModel.getQuantitySaling() - lstSelectedSerial.size()));
        isMissingSerial = true;
        btnAccept.setText(R.string.addSerial);
        // imgAddSerial.setVisibility(View.VISIBLE);
        // imgAccept.setVisibility(View.GONE);
    }

    // Chon serial de ban;
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                            long arg3) {

        final Serial serial = (Serial) avaiLableSerialAdapter.getLstData().get(
                position);
        if (serial.getFromSerial().equals(serial.getToSerial())) {
            lstSelectedSerial.add(serial.getFromSerial());
            if (lstSelectedSerial.size() == stockModel.getQuantitySaling()
                    .intValue()) {
                selectSerialDialog.dismiss();
                tvMissingSerial.setText("");
                btnAccept.setText(R.string.ok);
                isMissingSerial = false;
                // imgAccept.setVisibility(View.VISIBLE);
                // imgAddSerial.setVisibility(View.GONE);
            } else {
                // imgAccept.setVisibility(View.GONE);
                // imgAddSerial.setVisibility(View.VISIBLE);
                isMissingSerial = true;
                btnAccept.setText(R.string.addSerial);
                tvMissingSerial.setText(getResources().getString(
                        R.string.missing_serial)
                        + ": "
                        + (stockModel.getQuantitySaling() - lstSelectedSerial
                        .size()));
            }
            selectedSerialAdapter.setLstData(SaleCommons.getRangeSerial(
                    lstSelectedSerial, stockModel.isStockHandset()));
            selectedSerialAdapter.notifyDataSetChanged();

            tvMissingSerialOnAvailableDialog.setText(getResources().getString(
                    R.string.missing_serial)
                    + ": "
                    + (stockModel.getQuantitySaling() - lstSelectedSerial
                    .size()));
            for (int i = 0; i < stockModel.getLstSerial().size(); i++) {
                if (stockModel.getLstSerial().get(i)
                        .equals(serial.getFromSerial())) {
                    stockModel.getLstSerial().remove(i);
                    break;
                }
            }
            avaiLableSerialAdapter.setLstData(SaleCommons.getRangeSerial(
                    SaleCommons.sortSerial(stockModel.getLstSerial(),
                            stockModel.isStockHandset()), stockModel.isStockHandset()));
            avaiLableSerialAdapter.notifyDataSetChanged();
        } else {
            final Long missingSerial = stockModel.getQuantitySaling()
                    - lstSelectedSerial.size();
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.sale_choose_serial_dialog);
            btnSelectSerial = (Button) dialog.findViewById(R.id.btnOk);
            btnCancelSelectSerial = (Button) dialog
                    .findViewById(R.id.btnViewSaleTrans);
            tvMissingSerialOnSelectDialog = (TextView) dialog
                    .findViewById(R.id.tvMissingSerial);
            tvMissingSerialOnSelectDialog.setText(getResources().getString(
                    R.string.missing_serial)
                    + ": "
                    + (stockModel.getQuantitySaling() - lstSelectedSerial
                    .size()));
            TextView tvParentSerial = (TextView) dialog
                    .findViewById(R.id.tvParentSerial);
            tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
            tvMessage.setText("");
            tvParentSerial.setText(serial.getFromSerial() + " - "
                    + serial.getToSerial());
            final EditText edtStartSerial = (EditText) dialog
                    .findViewById(R.id.edtStartSerial);
            edtStartSerial.setText(serial.getFromSerial());
            final EditText edtEndSerial = (EditText) dialog
                    .findViewById(R.id.edtEndSerial);

            edtStartSerial.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {

                }

                @Override
                public void afterTextChanged(Editable edt) {
                    tvMessage.setText("");
                    if (!SaleCommons.checkInRange(serial, edt.toString(),
                            stockModel.isStockHandset())
                            || !SaleCommons.checkInRange(serial, edtEndSerial
                            .getText().toString(), stockModel
                            .isStockHandset())) {
                        tvMessage.setText(R.string.serial_out_of_range);
                        // Toast.makeText(
                        // getActivity(),
                        // getResources().getString(
                        // R.string.serial_out_of_range),
                        // Toast.LENGTH_SHORT).show();
                        btnSelectSerial.setVisibility(View.GONE);
                    } else if (Long
                            .parseLong(edtEndSerial.getText().toString())
                            - Long.parseLong(edt.toString()) + 1 > missingSerial) {
                        tvMessage
                                .setText(R.string.serial_quantity_greate_than_missing);
                        // Toast.makeText(
                        // getActivity(),
                        // getResources()
                        // .getString(
                        // R.string.serial_quantity_greate_than_missing),
                        // Toast.LENGTH_SHORT).show();
                        btnSelectSerial.setVisibility(View.GONE);
                    } else if (Long
                            .parseLong(edtEndSerial.getText().toString()) < Long
                            .parseLong(edt.toString())) {
                        tvMessage
                                .setText(R.string.from_serial_less_than_to_serial);
                        // Toast.makeText(
                        // getActivity(),
                        // getResources()
                        // .getString(
                        // R.string.from_serial_less_than_to_serial),
                        // Toast.LENGTH_SHORT).show();
                        btnSelectSerial.setVisibility(View.GONE);
                    } else {
                        btnSelectSerial.setVisibility(View.VISIBLE);
                    }
                }
            });

            edtEndSerial.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {
                }

                @Override
                public void afterTextChanged(Editable edt) {
                    tvMessage.setText("");
                    if (!SaleCommons.checkInRange(serial, edt.toString(),
                            stockModel.isStockHandset())
                            || !SaleCommons.checkInRange(serial, edtStartSerial
                            .getText().toString(), stockModel
                            .isStockHandset())) {
                        tvMessage.setText(R.string.serial_out_of_range);
                        // Toast.makeText(
                        // getActivity(),
                        // getResources().getString(
                        // R.string.serial_out_of_range),
                        // Toast.LENGTH_SHORT).show();
                        btnSelectSerial.setVisibility(View.GONE);
                    } else if (Long.parseLong(edt.toString())
                            - Long.parseLong(edtStartSerial.getText()
                            .toString()) + 1 > missingSerial) {
                        tvMessage
                                .setText(R.string.serial_quantity_greate_than_missing);
                        // Toast.makeText(
                        // getActivity(),
                        // getResources()
                        // .getString(
                        // R.string.serial_quantity_greate_than_missing),
                        // Toast.LENGTH_SHORT).show();
                        btnSelectSerial.setVisibility(View.GONE);
                    } else if (Long.parseLong(edtStartSerial.getText()
                            .toString()) > Long.parseLong(edt.toString())) {
                        tvMessage
                                .setText(R.string.from_serial_less_than_to_serial);
                        // Toast.makeText(
                        // getActivity(),
                        // getResources()
                        // .getString(
                        // R.string.from_serial_less_than_to_serial),
                        // Toast.LENGTH_SHORT).show();
                        btnSelectSerial.setVisibility(View.GONE);
                    } else {
                        btnSelectSerial.setVisibility(View.VISIBLE);
                    }
                }
            });


            if (Long.parseLong(serial.getToSerial())
                    - Long.parseLong(serial.getFromSerial()) + 1 <= missingSerial) {
                edtEndSerial.setText(serial.getToSerial());
            } else {
                Long endSerial = Long.parseLong(serial.getFromSerial())
                        + missingSerial - 1;

                if (stockModel.isStockCard()) {
                    edtEndSerial.setText(SaleCommons.normalSerial(endSerial
                            .toString()));
                } else {
                    edtEndSerial.setText(endSerial.toString());
                }
            }

            dialog.setTitle(R.string.input_serial_to_saling);
            btnSelectSerial.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Serial selected = new Serial();

                    if (stockModel.isStockCard()) {
                        selected.setFromSerial(SaleCommons
                                .normalSerial(edtStartSerial.getText()
                                        .toString()));
                        selected.setToSerial(SaleCommons
                                .normalSerial(edtEndSerial.getText().toString()));
                    } else {
                        selected.setFromSerial(edtStartSerial.getText()
                                .toString());
                        selected.setToSerial(edtEndSerial.getText().toString());
                    }
                    final Long quantity = Long.parseLong(selected.getToSerial())
                            - Long.parseLong(selected.getFromSerial()) + 1;
                    ArrayList<String> tmp = SaleCommons.splitSerial(selected,
                            stockModel.isStockCard());
                    lstSelectedSerial.addAll(tmp);
                    int count = 0;
                    for (int i = 0; i < stockModel.getLstSerial().size(); i++) {
                        if (SaleCommons.checkInRange(selected, stockModel
                                .getLstSerial().get(i), stockModel
                                .isStockHandset())) {
                            count++;
                            stockModel.getLstSerial().remove(i);
                            i--;
                        }
                        if (count == quantity.intValue()) {
                            break;
                        }
                    }
                    dialog.dismiss();
                    if (quantity == missingSerial.intValue()) {
                        selectSerialDialog.dismiss();
                        tvMissingSerial.setText("");
                        isMissingSerial = false;
                        btnAccept.setText(R.string.ok);
                        // imgAccept.setVisibility(View.VISIBLE);
                        // imgAddSerial.setVisibility(View.GONE);
                    } else {
                        isMissingSerial = true;
                        btnAccept.setText(R.string.addSerial);
                        // imgAccept.setVisibility(View.GONE);
                        // imgAddSerial.setVisibility(View.VISIBLE);
                    }
                    selectedSerialAdapter.setLstData(SaleCommons
                            .getRangeSerial(lstSelectedSerial, stockModel.isStockHandset()));
                    selectedSerialAdapter.notifyDataSetChanged();
                    avaiLableSerialAdapter.setLstData(SaleCommons
                            .getRangeSerial(SaleCommons.sortSerial(
                                    stockModel.getLstSerial(), stockModel.isStockHandset()),
                                    stockModel.isStockHandset()));
                    avaiLableSerialAdapter.notifyDataSetChanged();
                    tvMissingSerialOnAvailableDialog.setText(getResources()
                            .getString(R.string.missing_serial)
                            + ": "
                            + (stockModel.getQuantitySaling() - lstSelectedSerial
                            .size()));
                    if (stockModel.getQuantitySaling().intValue() == lstSelectedSerial
                            .size()) {
                        tvMissingSerial.setText("");
                    } else {
                        tvMissingSerial.setText(getResources().getString(
                                R.string.missing_serial)
                                + ": "
                                + (stockModel.getQuantitySaling() - lstSelectedSerial
                                .size()));
                    }
                }
            });
            btnCancelSelectSerial.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
