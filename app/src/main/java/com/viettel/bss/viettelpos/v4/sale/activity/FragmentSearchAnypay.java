package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.captcha.Captcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha;
import com.viettel.bss.viettelpos.v4.captcha.MathCaptcha.MathOptions;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v4.sale.adapter.CardInfoAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.CardInfoProAdapter;
import com.viettel.bss.viettelpos.v4.sale.asytask.GetAccountBookBankPlusInfo;
import com.viettel.bss.viettelpos.v4.sale.asytask.GetBankPlusTransType;
import com.viettel.bss.viettelpos.v4.sale.dialog.ChooseTransTypeDialog;
import com.viettel.bss.viettelpos.v4.sale.object.BCCS2Output;
import com.viettel.bss.viettelpos.v4.sale.object.CardInfoProvisioningDTO;
import com.viettel.bss.viettelpos.v4.sale.object.IMOutput;
import com.viettel.bss.viettelpos.v4.sale.object.StockCardFullDTO;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FragmentSearchAnypay extends Fragment implements OnClickListener {
    private final String SEARCH_TRANS_APPROVE = "SEARCH_TRANS_APPROVE";
	private final String SEARCH_DATA_CELL = "SEARCH_DATA_CELL";
    private final int DURATION_MAX = 30;
    private Spinner spinTypeSearch, spinmethodnapthe;
    private LinearLayout lnPUKandExpireKIT, lnnapthe, lnSerial, lnLookupCell;
    private EditText edtisdn, edtserial, edtserialnapthe, edt_capcha, edtLookupCode, edtLookupDate;
	private RadioButton rbCell, rbTram, rbDistrict;
	private CheckBox cbLookupLK;
    private ImageView imgCapcha;
    private Button btn_search;

    private LinearLayout lninfotongdai, lninfocard;
    private ListView lvinfopro, lvinfocard;

    private CardInfoAdapter cardInfoAdapter = null;
    private CardInfoProAdapter cardInfoProAdapter = null;

    private ArrayList<StockCardFullDTO> listCardFullDTOs = new ArrayList<StockCardFullDTO>();
    private ArrayList<CardInfoProvisioningDTO> listCardInfoProvisioningDTOs = new ArrayList<CardInfoProvisioningDTO>();
    // tra cuu giao dich tich duyet
    private LinearLayout lnTrans;
    private TextView tvFromDate;
    private TextView tvToDate;
    private TextView tvTransSelected;
    private List<FormBean> lstBankTransType = new ArrayList<FormBean>();
    // end tra cuu giao dich tich duyet

    private String keySearch = "";
    private Captcha cap = new MathCaptcha(100, 100,
            MathOptions.PLUS_MINUS_MULTIPLY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (this.getArguments() != null) {
            keySearch = this.getArguments().getString("ketSearch", "");
        }
        View mView = inflater.inflate(R.layout.layout_search, container, false);
        unitView(mView);

        return mView;
    }

    private void unitView(View mView) {
        imgCapcha = (ImageView) mView.findViewById(R.id.imgCapcha);
        imgCapcha.setOnClickListener(this);
        lninfotongdai = (LinearLayout) mView.findViewById(R.id.lninfotongdai);
        lninfocard = (LinearLayout) mView.findViewById(R.id.lninfocard);
        lnTrans = (LinearLayout) mView.findViewById(R.id.lnTrans);
        lvinfopro = (ListView) mView.findViewById(R.id.lvinfopro);
        lvinfocard = (ListView) mView.findViewById(R.id.lvinfocard);
        lnSerial = (LinearLayout) mView.findViewById(R.id.lnSerial);
		lnLookupCell = (LinearLayout) mView.findViewById(R.id.lnLookupCell);
		edtLookupCode = (EditText) mView.findViewById(R.id.edtLookupCode);
		rbCell = (RadioButton) mView.findViewById(R.id.rbCell);
		rbTram = (RadioButton) mView.findViewById(R.id.rbTram);
		rbDistrict = (RadioButton) mView.findViewById(R.id.rbDistrict);
		cbLookupLK = (CheckBox) mView.findViewById(R.id.cbLookupLK);
		edtLookupDate = (EditText) mView.findViewById(R.id.edtLookupDate);
		edtLookupDate.setText(DateTimeUtils.convertDateTimeToString(new Date(), "dd/MM/yyyy"));
		
		new DateTimeDialogWrapper(edtLookupDate, getActivity());
		
		rbCell.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(rbCell.isChecked()){
					edtLookupCode.setText("");
					edtLookupCode.setHint(R.string.txt_enter_cell_code);
				}
			}
		});
		
		rbTram.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						if(rbTram.isChecked()){
							edtLookupCode.setText("");
							edtLookupCode.setHint(R.string.txt_enter_bastation_code);
						}
					}
				});
		
		rbDistrict.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(rbDistrict.isChecked()){
					edtLookupCode.setText("");
					edtLookupCode.setHint(R.string.txt_enter_district_code);
				}
			}
		});

        spinTypeSearch = (Spinner) mView.findViewById(R.id.spinTypeSearch);
        tvTransSelected = (TextView) mView.findViewById(R.id.tvTransSelected);
        mView.findViewById(R.id.btnNextDate).setOnClickListener(this);
        mView.findViewById(R.id.btnPreDate).setOnClickListener(this);
        mView.findViewById(R.id.lnChooseTransType).setOnClickListener(this);
        spinTypeSearch.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spin item = (Spin) arg0.getItemAtPosition(arg2);
                if (item != null) {
                    if (!CommonActivity.isNullOrEmpty(item.getId())) {
                        // tra cuu PUK
                        if ("1".equals(item.getId())
                                || "3".equals(item.getId())
                                || "7".equals(item.getId())) {
                            resetView();
                            lnPUKandExpireKIT.setVisibility(View.VISIBLE);
                            if ("7".equals(item.getId())) {
                                lnSerial.setVisibility(View.GONE);
                            }

                        } else if ("2".equals(item.getId())) {
                            // tra cuu ttin the cao
                            resetView();
                            lnnapthe.setVisibility(View.VISIBLE);

                        } else if ("4".equals(item.getId())
                                || "5".equals(item.getId())
                                || "6".equals(item.getId())) {
                            // tra cuu so du tk anypay
                            resetView();
                        } else if (SEARCH_TRANS_APPROVE.equals(item.getId())) {
                            // tra cuu giao dich tich duyet
                            resetView();
                            lnTrans.setVisibility(View.VISIBLE);
                            if (CommonActivity.isNullOrEmpty(lstBankTransType)) {
                                GetBankPlusTransType asy = new GetBankPlusTransType(
                                        getActivity(), lstBankTransType,
                                        tvTransSelected);
                                asy.execute();
                            }
						} else if(SEARCH_DATA_CELL.equals(item.getId())){
							resetView();
							lnLookupCell.setVisibility(View.VISIBLE);
						}	
                    } else {
                        resetView();
                    }
                } else {
                    resetView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        spinmethodnapthe = (Spinner) mView.findViewById(R.id.spinmethodnapthe);
        lnPUKandExpireKIT = (LinearLayout) mView
                .findViewById(R.id.lnPUKandExpireKIT);
        lnnapthe = (LinearLayout) mView.findViewById(R.id.lnnapthe);
        edtisdn = (EditText) mView.findViewById(R.id.edtisdn);
        edtserial = (EditText) mView.findViewById(R.id.edtserial);
        edtserialnapthe = (EditText) mView.findViewById(R.id.edtserialnapthe);
        edt_capcha = (EditText) mView.findViewById(R.id.edt_capcha);
        btn_search = (Button) mView.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        tvFromDate = (TextView) mView.findViewById(R.id.tvFromDate);
        tvToDate = (TextView) mView.findViewById(R.id.tvToDate);
        tvToDate.setText(DateTimeUtils.convertDateTimeToString(new Date(),
                "dd/MM/yyyy"));
        // Date fromDate = DateTimeUtils.addMonths(new Date(), -1);
        Date fromDate = DateTimeUtils.addDays(new Date(), -DURATION_MAX);

        tvFromDate.setText(DateTimeUtils.convertDateTimeToString(fromDate,
                "dd/MM/yyyy"));

        tvFromDate.setOnClickListener(this);
        tvToDate.setOnClickListener(this);
        initTypeSearch();
        initHTTT();
        resetView();
    }

    private void resetView() {
        lnPUKandExpireKIT.setVisibility(View.GONE);
        lnnapthe.setVisibility(View.GONE);
        lninfotongdai.setVisibility(View.GONE);
        lninfocard.setVisibility(View.GONE);
        lnTrans.setVisibility(View.GONE);
        lnSerial.setVisibility(View.VISIBLE);
		lnLookupCell.setVisibility(View.GONE);
        initCap();
        edt_capcha.setText("");
        edtisdn.setText("");
        edtserial.setText("");
        edtserialnapthe.setText("");
        imgCapcha.refreshDrawableState();

    }

    private void showDialogPass(final String type) {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pass_dialog);
        final EditText edtpass = (EditText) dialog.findViewById(R.id.edtpass);
        final Button btnOk = (Button) dialog.findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if (CommonActivity.isNullOrEmpty(edtpass.getText().toString()
                        .trim())) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.passwordRequired),
                            getActivity().getString(R.string.app_name)).show();
                    return;
                }
                if ("4".equals(type)) {
                    // tra cuu so du tk anypay
                    resetView();
                    SearchAnyPay searchAnyPay = new SearchAnyPay(getActivity(),
                            "getBalance");
                    searchAnyPay.execute(edtpass.getText().toString().trim());

                } else if ("5".equals(type)) {
                    // tra cuu kq bh trong ngay
                    resetView();
                    SearchAnyPay searchAnyPay = new SearchAnyPay(getActivity(),
                            "reportSaleAnypayInDay");
                    searchAnyPay.execute(edtpass.getText().toString().trim());

                } else if ("6".equals(type)) {
                    // tra cuu lich su 5 ngay gan nhat
                    resetView();
                    SearchAnyPay searchAnyPay = new SearchAnyPay(getActivity(),
                            "lookupTransaction");
                    searchAnyPay.execute(edtpass.getText().toString().trim());
                }

                dialog.dismiss();
            }
        });

        Button btncancel = (Button) dialog.findViewById(R.id.btncancel);
        btncancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // khoi tao loai tra cuu
    private void initTypeSearch() {

        // o Tra cÃ¡Â»Â©u PUK search.puk
        // o Tra cÃ¡Â»Â©u tÃƒÂ¬nh trÃ¡ÂºÂ¡ng thÃ¡ÂºÂ» cÃƒÂ o search.info.card
        // o ThÃ¡Â»ï¿½i hÃ¡ÂºÂ¡n sÃ¡Â»Â­ dÃ¡Â»Â¥ng KIT search.expire.kit
        // o Tra cÃ¡Â»Â©u sÃ¡Â»â€˜ dÃ†Â° tÃƒÂ i khoÃ¡ÂºÂ£n Anypay search.balance.anypay
        // o Tra cÃ¡Â»Â©u kÃ¡ÂºÂ¿t quÃ¡ÂºÂ£ bÃƒÂ¡n hÃƒÂ ng trong ngÃƒÂ y search.sale.day
        // o Tra cÃ¡Â»Â©u 5 giao dÃ¡Â»â€¹ch gÃ¡ÂºÂ§n nhÃ¡ÂºÂ¥t search.transaction.anypay

        SharedPreferences preferences = getActivity().getSharedPreferences(
                Define.PRE_NAME, Activity.MODE_PRIVATE);

        String name = preferences.getString(Define.KEY_MENU_NAME, "");

        List<Spin> arrTypeSearch = new ArrayList<Spin>();
        arrTypeSearch.add(new Spin("", getActivity().getString(
                R.string.loaitracuu)));
        if (name.contains(";search.puk;")) {
            arrTypeSearch.add(new Spin("1", getActivity().getString(
                    R.string.tcpuk)));
        }
        if (name.contains(";search.info.card;")) {
            arrTypeSearch.add(new Spin("2", getActivity().getString(
                    R.string.tctttheocao)));
        }
        if (name.contains(";search.expire.kit;")) {
            arrTypeSearch.add(new Spin("3", getActivity().getString(
                    R.string.ttsudungkit)));
        }
        if (name.contains(";search.balance.anypay;")) {
            arrTypeSearch.add(new Spin("4", getActivity().getString(
                    R.string.tcsodutkanypay)));
        }
        if (name.contains(";search.sale.day;")) {
            arrTypeSearch.add(new Spin("5", getActivity().getString(
                    R.string.tckquabhtrongngay)));
        }
        if (name.contains(";search.transaction.anypay;")) {
            arrTypeSearch.add(new Spin("6", getActivity().getString(
                    R.string.tc5tran)));
        }

        if (name.contains(";search.subscriber.update.info;")) {
            arrTypeSearch.add(new Spin("7", getActivity().getString(
                    R.string.searchSubscriber)));
        }

        if (name.contains(";search.trans.approval;")) {

            arrTypeSearch.add(new Spin("SEARCH_TRANS_APPROVE", getActivity()
                    .getString(R.string.searchTransactionApproval)));
        }
		
		if(name.contains(";search.data.cell;")){
			arrTypeSearch.add(new Spin(SEARCH_DATA_CELL, getActivity()
					.getString(R.string.search_data_cell)));
		}

        Utils.setDataSpinner(getActivity(), arrTypeSearch, spinTypeSearch);

        if (!CommonActivity.isNullOrEmpty(keySearch)
                && !CommonActivity.isNullOrEmptyArray(arrTypeSearch)) {

            for (Spin spin : arrTypeSearch) {
                if ("8".equals(spin.getId())) {
                    spinTypeSearch.setSelection(arrTypeSearch.indexOf(spin));
                    break;
                }
            }

        }

    }

    private void initHTTT() {
        List<Spin> arrHTTT = new ArrayList<Spin>();
        arrHTTT.add(new Spin("0", getActivity().getString(R.string.naptuhtkhac)));
        // arrHTTT.add(new Spin("1", getActivity().getString(
        // R.string.naphethongocs)));
        Utils.setDataSpinner(getActivity(), arrHTTT, spinmethodnapthe);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.tctienich);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tvFromDate:
                CommonActivity.showDatePickerDialog(getActivity(), DateTimeUtils
                        .convertStringToTime(tvFromDate.getText().toString(),
                                "dd/MM/yyyy"), fromDateCallBack);
                break;
            case R.id.tvToDate:
                CommonActivity.showDatePickerDialog(getActivity(), DateTimeUtils
                        .convertStringToTime(tvToDate.getText().toString(),
                                "dd/MM/yyyy"), toDateCallBack);
                break;
            case R.id.btnNextDate:
                Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
                        .getText().toString(), "dd/MM/yyyy");
                Date toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
                        .toString(), "dd/MM/yyyy");
//			int days = DateTimeUtils.calculateDays2Date(toDate, fromDate);
			if (toDate.before(fromDate)) {
                    return;
                }
//			toDate = DateTimeUtils.addDays(toDate, days);
			if (toDate.after(new Date())) {
                    toDate = new Date();
                }
			int days = DateTimeUtils.calculateDays2Date(toDate, fromDate);
                fromDate = DateTimeUtils.addDays(toDate, -days);
                tvFromDate.setText(DateTimeUtils.convertDateTimeToString(fromDate,
                        "dd/MM/yyyy"));
                tvToDate.setText(DateTimeUtils.convertDateTimeToString(toDate,
                        "dd/MM/yyyy"));
                resetSearchTransBankplusHistory();
                break;
            case R.id.btnPreDate:
                fromDate = DateTimeUtils.convertStringToTime(tvFromDate.getText()
                        .toString(), "dd/MM/yyyy");
                toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
                        .toString(), "dd/MM/yyyy");
                days = DateTimeUtils.calculateDays2Date(toDate, fromDate);
			if (toDate.before(fromDate)) {
                    return;
                }

                fromDate = DateTimeUtils.addDays(fromDate, -days);
                toDate = DateTimeUtils.addDays(toDate, -days);
                tvFromDate.setText(DateTimeUtils.convertDateTimeToString(fromDate,
                        "dd/MM/yyyy"));
                tvToDate.setText(DateTimeUtils.convertDateTimeToString(toDate,
                        "dd/MM/yyyy"));
                resetSearchTransBankplusHistory();
                break;
            case R.id.lnChooseTransType:
                if (!CommonActivity.isNullOrEmpty(lstBankTransType)) {
                    ChooseTransTypeDialog dialog = new ChooseTransTypeDialog(
                            getActivity(), lstBankTransType, tvTransSelected);
                    dialog.show();
                }

                break;
            case R.id.btn_search:
                Spin item = (Spin) spinTypeSearch.getSelectedItem();
                String codeCapcha = edt_capcha.getText().toString().trim();

                if (item == null) {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.selecttypeseach),
                            getActivity().getString(R.string.app_name)).show();

                    // resetView();
                    return;
                }
			if (CommonActivity.isNullOrEmpty(item.getId())) {
				CommonActivity.createAlertDialog(getActivity(),
						getActivity().getString(R.string.selecttypeseach),
						getActivity().getString(R.string.app_name)).show();

				// resetView();
				return;
			}

			if (SEARCH_TRANS_APPROVE.equals(item.getId())) {

				fromDate = DateTimeUtils.convertStringToTime(tvFromDate
						.getText().toString(), "dd/MM/yyyy");
				toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
						.toString(), "dd/MM/yyyy");
				if (DateTimeUtils.calculateDays2Date(toDate, fromDate) > DURATION_MAX) {
					CommonActivity
							.createAlertDialog(
									getActivity(),
									getString(R.string.duration_over_load,
											DURATION_MAX),
									getString(R.string.app_name)).show();
					return;
				}
			}
			
			if(SEARCH_DATA_CELL.equals(item.getId())){
				if(CommonActivity.isNullOrEmpty(edtLookupCode)){
					CommonActivity.toast(getActivity(), R.string.txt_enter_lookup_code_required);
					edtLookupCode.requestFocus();
					return;
				}
			}

                if (codeCapcha.isEmpty()) {
                    CommonActivity.createAlertDialog(
                            getActivity(),
                            getActivity().getResources().getString(
                                    R.string.please_input_capcha),
                            getActivity().getString(R.string.app_name)).show();
                    return;
                }
                if (!cap.checkAnswer(codeCapcha)) {
                    CommonActivity.createAlertDialog(
                            getActivity(),
                            getActivity().getResources().getString(
                                    R.string.error_input_capcha),
                            getActivity().getString(R.string.app_name)).show();
                    edt_capcha.requestFocus();
                    return;
                }
                initCap();
                if ("1".equals(item.getId())) {
                    // tra cuu PUK
                    if (CommonActivity.isNullOrEmpty(edtisdn.getText().toString())
                            && CommonActivity.isNullOrEmpty(edtserial.getText()
                            .toString())) {
                        CommonActivity.createAlertDialog(
                                getActivity(),
                                getActivity().getString(
                                        R.string.isdnemptyserialempty),
                                getActivity().getString(R.string.app_name)).show();
                        return;
                    }
                    AsynGetSimInfor asynGetSimInfor = new AsynGetSimInfor(
                            getActivity());
                    asynGetSimInfor.execute(edtisdn.getText().toString().trim(),
                            edtserial.getText().toString().trim());

                } else if ("2".equals(item.getId())) {
                    // tra cuu ttin the cao
                    if (CommonActivity.isNullOrEmpty(edtserialnapthe.getText()
                            .toString().trim())) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.serialnotempty),
                                getActivity().getString(R.string.app_name)).show();
                        return;
                    }
                    AsynGetInforCardNumber asynGetInforCardNumber = new AsynGetInforCardNumber(
                            getActivity());
                    asynGetInforCardNumber.execute(edtserialnapthe.getText()
                            .toString().trim());

                } else if ("3".equals(item.getId())) {
                    // tra cuu sd kit
                    if (CommonActivity.isNullOrEmpty(edtisdn.getText().toString())
                            && CommonActivity.isNullOrEmpty(edtserial.getText()
                            .toString())) {
                        CommonActivity.createAlertDialog(
                                getActivity(),
                                getActivity().getString(
                                        R.string.isdnemptyserialempty),
                                getActivity().getString(R.string.app_name)).show();
                        return;
                    }

                    AsynGetExpireDateKit asynGetExpireDateKit = new AsynGetExpireDateKit(
                            getActivity());
                    asynGetExpireDateKit.execute(edtisdn.getText().toString()
                            .trim(), edtserial.getText().toString().trim());

                } else if ("4".equals(item.getId()) || "5".equals(item.getId())
                        || "6".equals(item.getId())) {
                    // tra cuu so du tk anypay
                    showDialogPass(item.getId());
                } else if (SEARCH_TRANS_APPROVE.equals(item.getId())) {
                    GetAccountBookBankPlusInfo asy = new GetAccountBookBankPlusInfo(
                            getActivity(), lstBankTransType, false,
                            DateTimeUtils.convertStringToTime(tvFromDate.getText()
                                    .toString(), "dd/MM/yyyy"),
                            DateTimeUtils.convertStringToTime(tvToDate.getText()
                                    .toString(), "dd/MM/yyyy"), null);
                    asy.execute();

                } else if ("7".equals(item.getId())) {
                    if (CommonActivity.isNullOrEmpty(edtisdn.getText().toString())) {
                        CommonActivity.createAlertDialog(getActivity(),
                                getActivity().getString(R.string.must_input_isdn),
                                getActivity().getString(R.string.app_name)).show();
                        return;
                    }

                    if (!StringUtils.isViettelMobile(edtisdn.getText().toString())) {
                        CommonActivity.createAlertDialog(
                                getActivity(),
                                getActivity().getString(
                                        R.string.isdn_not_viettel_mobile),
                                getActivity().getString(R.string.app_name)).show();
                        return;
                    }

                    new AsySearchSubscriber(getActivity()).execute(edtisdn
                            .getText().toString().trim());
			} else if(SEARCH_DATA_CELL.equals(item.getId())){
				new AsySearchDataCell(getActivity()).execute();
			}

                break;
            case R.id.imgCapcha:
                initCap();
                break;
            case R.id.relaBackHome:
                getActivity().onBackPressed();
                break;

            default:
                break;
        }

    }

    private class SearchAnyPay extends AsyncTask<String, Void, String> {

        private Activity mActivity = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        ProgressDialog progress;
        String wsCode;

        public SearchAnyPay(Activity context, String wsCode) {
            this.wsCode = wsCode;
            this.progress = new ProgressDialog(context);
            // check font
            this.mActivity = context;
            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected String doInBackground(String... arg0) {
            return searchAnypay(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (errorCode.equals("0")) {
                CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getString(R.string.app_name)).show();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

        private String searchAnypay(String passWord) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + wsCode);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + wsCode + ">");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<password>" + passWord + "</password>");

                rawData.append("</input>");
                rawData.append("</ws:" + wsCode + ">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_" + wsCode);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);

                }
            } catch (Exception e) {
                Log.d("mbccs_" + wsCode, e.toString() + "description error", e);
            }

            return errorCode;

        }
    }

    // lay thong tin the cao
    private class AsynGetInforCardNumber extends
            AsyncTask<String, Void, BCCS2Output> {

        private Activity mActivity = null;
        String errorCode = "";
        String description = "";
        ProgressDialog progress;

        public AsynGetInforCardNumber(Activity context) {
            this.progress = new ProgressDialog(context);
            // check font
            this.mActivity = context;
            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected BCCS2Output doInBackground(String... arg0) {
            return getInfoCard(arg0[0]);
        }

        @Override
        protected void onPostExecute(BCCS2Output result) {
            super.onPostExecute(result);
            progress.dismiss();
            listCardFullDTOs = new ArrayList<StockCardFullDTO>();
            listCardInfoProvisioningDTOs = new ArrayList<CardInfoProvisioningDTO>();
            if ("0".equals(errorCode)) {
                if (result.getHistoryInforCardNumberDTO() != null) {
                    lninfocard.setVisibility(View.VISIBLE);
                    lninfotongdai.setVisibility(View.VISIBLE);
                    if (result.getHistoryInforCardNumberDTO()
                            .getStockCardNumber() != null) {
                        listCardFullDTOs.add(result
                                .getHistoryInforCardNumberDTO()
                                .getStockCardNumber());
                        cardInfoAdapter = new CardInfoAdapter(listCardFullDTOs,
                                getActivity());
                        lvinfocard.setAdapter(cardInfoAdapter);
                    }
                    if (result.getHistoryInforCardNumberDTO()
                            .getCardInfoProvisioningDTO() != null) {
                        listCardInfoProvisioningDTOs.add(result
                                .getHistoryInforCardNumberDTO()
                                .getCardInfoProvisioningDTO());
                        cardInfoProAdapter = new CardInfoProAdapter(
                                listCardInfoProvisioningDTOs, getActivity());
                        lvinfopro.setAdapter(cardInfoProAdapter);
                    }
                } else {

                    lninfocard.setVisibility(View.GONE);
                    lninfotongdai.setVisibility(View.GONE);

                    cardInfoAdapter = new CardInfoAdapter(listCardFullDTOs,
                            getActivity());
                    lvinfocard.setAdapter(cardInfoAdapter);
                    cardInfoAdapter.notifyDataSetChanged();
                    cardInfoProAdapter = new CardInfoProAdapter(
                            listCardInfoProvisioningDTOs, getActivity());
                    lvinfopro.setAdapter(cardInfoProAdapter);
                    cardInfoProAdapter.notifyDataSetChanged();
                }
            } else {
                lninfocard.setVisibility(View.GONE);
                lninfotongdai.setVisibility(View.GONE);
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

        private BCCS2Output getInfoCard(String serial) {
            String original = "";
            BCCS2Output bccsOutput = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getInforCardNumber");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getInforCardNumber>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<serial>" + serial + "</serial>");

                Spin spinReg = (Spin) spinmethodnapthe.getSelectedItem();

                if (spinReg != null) {
                    rawData.append("<regType>" + spinReg.getId() + "</regType>");
                }

                rawData.append("</input>");
                rawData.append("</ws:getInforCardNumber>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getInforCardNumber");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                if (original != null && !original.isEmpty()) {
                    Serializer serializer = new Persister();
                    bccsOutput = serializer.read(BCCS2Output.class, original);
                    if (bccsOutput != null) {
                        errorCode = bccsOutput.getErrorCode();
                        description = bccsOutput.getDescription();
                    }

                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("mbccs_" + "getInforCardNumber", e.toString()
                        + "description error", e);
            }

            return bccsOutput;

        }
    }

    // tra cuu PUK
    private class AsynGetSimInfor extends AsyncTask<String, Void, IMOutput> {

        private Activity mActivity = null;
        String errorCode = "";
        String description = "";
        ProgressDialog progress;

        public AsynGetSimInfor(Activity context) {
            this.progress = new ProgressDialog(context);
            // check font
            this.mActivity = context;
            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected IMOutput doInBackground(String... arg0) {
            return getSimInfo(arg0[0], arg0[1]);
        }

        @Override
        protected void onPostExecute(IMOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {
                if (result != null) {
                    if (result.getStockSim() != null) {

                        // String des =
                        // getActivity().getString(R.string.isdnPUK)
                        // + result.getStockSim().getIsdn() + "\n\n"
                        // + getActivity().getString(R.string.mapuk)
                        // + result.getStockSim().getPuk() + "\n\n"
                        // + getActivity().getString(R.string.serialPUK)
                        // + result.getStockSim().getSerial() + "\n\n";

                        String des = "";

                        if (!CommonActivity.isNullOrEmpty(result.getStockSim()
                                .getIsdn())) {
                            des += getActivity().getString(R.string.isdnPUK) +" "
                                    + result.getStockSim().getIsdn() + "\n\n";
                        }

                        if (!CommonActivity.isNullOrEmpty(result.getStockSim()
                                .getSerial())) {
                            des += getActivity().getString(R.string.serialPUK)+" "
                                    + result.getStockSim().getSerial() + "\n\n";
                        }

                        if (!CommonActivity.isNullOrEmpty(result.getStockSim()
                                .getPuk())) {
                            des += getActivity().getString(R.string.mapuk1)+" "
                                    + result.getStockSim().getPuk() + "\n\n";
                        }
                        if (!CommonActivity.isNullOrEmpty(result.getStockSim()
                                .getPuk2())) {
                            des += getActivity().getString(R.string.mapuk2)+" "
                                    + result.getStockSim().getPuk2() + "\n\n";
                        }
                        if (!CommonActivity.isNullOrEmpty(result.getStockSim()
                                .getPin())) {
                            des += getActivity().getString(R.string.mapin1)+" "
                                    + result.getStockSim().getPin() + "\n\n";
                        }
                        if (!CommonActivity.isNullOrEmpty(result.getStockSim()
                                .getPin2())) {
                            des += getActivity().getString(R.string.mapin2)+" "
                                    + result.getStockSim().getPin2() + "\n\n";
                        }

                        CommonActivity.createAlertDialog(getActivity(), des,
                                getActivity().getString(R.string.app_name))
                                .show();

                    } else {
                        CommonActivity.createAlertDialog(
                                getActivity(),

                                        getString(R.string.no_data_return),
                                getActivity().getString(R.string.app_name))
                                .show();
                    }
                } else {
                    CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.no_data_return),
                            getActivity().getString(R.string.app_name)).show();
                }
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

        private IMOutput getSimInfo(String isdn, String serial) {
            String original = "";
            IMOutput imOutput = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getSimInfor");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getSimInfor>");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<serial>" + serial + "</serial>");
                rawData.append("<isdn>" + isdn + "</isdn>");
                rawData.append("</input>");
                rawData.append("</ws:getSimInfor>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input
                        .sendRequest(envelope, Constant.BCCS_GW_URL,
                                getActivity(), "mbccs_getSimInfor");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                if (original != null && !original.isEmpty()) {
                    Serializer serializer = new Persister();
                    imOutput = serializer.read(IMOutput.class, original);
                    if (imOutput != null) {
                        errorCode = imOutput.getErrorCode();
                        description = imOutput.getDescription();
                    }

                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("mbccs_" + "getSimInfor", e.toString()
                        + "description error", e);
            }

            return imOutput;

        }
    }

    private class AsynGetExpireDateKit extends
            AsyncTask<String, Void, IMOutput> {

        private Activity mActivity = null;
        String errorCode = "";
        String description = "";
        ProgressDialog progress;

        public AsynGetExpireDateKit(Activity context) {
            this.progress = new ProgressDialog(context);
            // check font
            this.mActivity = context;
            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected IMOutput doInBackground(String... arg0) {
            return getExpireDateKit(arg0[0], arg0[1]);
        }

        @Override
        protected void onPostExecute(IMOutput result) {
            super.onPostExecute(result);
            progress.dismiss();
            if ("0".equals(errorCode)) {

                // String des = getActivity().getString(R.string.dateexpired)
                // + DateTimeUtils.convertDate(result.getExpiredate())
                // + "\n";

                String des = "";

                if (!CommonActivity.isNullOrEmpty(result.getIsdn())) {
                    des += getActivity().getString(R.string.isdnPUK)+" "
                            + result.getIsdn() + "\n\n";
                }

                if (!CommonActivity.isNullOrEmpty(result.getSerial())) {
                    des += getActivity().getString(R.string.serialPUK) +" "
                            + result.getSerial() + "\n\n";
                }

                if (!CommonActivity.isNullOrEmpty(result.getExpiredate())) {
                    des += getActivity().getString(R.string.dateexpired)+" "
                            + DateTimeUtils.convertDate(result.getExpiredate())
                            + "\n\n";
                }

                if (!CommonActivity.isNullOrEmpty(result.getDescription())) {
                    des += getString(R.string.ngayXuatban)+" "
                            + result.getDescription()
                            + "\n\n";
                }else{
                    des += getString(R.string.chua_xuat_ban)+"\n\n";
                }

                CommonActivity.createAlertDialog(getActivity(), des,
                        getActivity().getString(R.string.app_name)).show();

            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

        private IMOutput getExpireDateKit(String isdn, String serial) {
            String original = "";
            IMOutput imOutput = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getExpireDateKit");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getExpireDateKit>");
                rawData.append("<cmMobileInput>");
                rawData.append("<token>" + Session.getToken() + "</token>");
                rawData.append("<serial>" + serial + "</serial>");
                rawData.append("<isdn>" + isdn + "</isdn>");
                rawData.append("</cmMobileInput>");
                rawData.append("</ws:getExpireDateKit>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(),
                        "mbccs_getExpireDateKit");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                if (original != null && !original.isEmpty()) {
                    Serializer serializer = new Persister();
                    imOutput = serializer.read(IMOutput.class, original);
                    if (imOutput != null) {
                        errorCode = imOutput.getErrorCode();
                        description = imOutput.getDescription();
                    }

                } else {
                    errorCode = Constant.ERROR_CODE;
                    description = getString(R.string.no_data_return);
                }

            } catch (Exception e) {
                Log.d("mbccs_" + "getExpireDateKit", e.toString()
                        + "description error", e);
            }

            return imOutput;

        }
    }

    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            MainActivity.getInstance().finish();

        }
    };

    private OnDateSetListener fromDateCallBack = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (!view.isShown()) {
                return;
            }
            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);

            Date fromDate = DateTimeUtils.convertStringToTime(DateTimeUtils.convertDateTimeToString(cal.getTime(), "dd/MM/yyyy"), "dd/MM/yyyy");
            Date toDate = DateTimeUtils.convertStringToTime(tvToDate.getText()
                    .toString(), "dd/MM/yyyy");
			if (fromDate.after(toDate)) {
                CommonActivity
                        .createAlertDialog(getActivity(),
                                R.string.report_warn_todate_fromdate,
                                R.string.app_name).show();
                return;
                // cal.setTime(toDate);
            }
            if (DateTimeUtils.calculateDays2Date(toDate, fromDate) > DURATION_MAX) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.duration_over_load, DURATION_MAX),
                        getString(R.string.app_name)).show();
                return;
            }
            tvFromDate.setText(DateTimeUtils.convertDateTimeToString(
                    cal.getTime(), "dd/MM/yyyy"));
            resetSearchTransBankplusHistory();
        }
    };
    private OnDateSetListener toDateCallBack = new OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            if (!view.isShown()) {
                return;
            }
            Calendar cal = Calendar.getInstance();
            cal.set(year, monthOfYear, dayOfMonth);
            Date toDate = cal.getTime();
            Date fromDate = DateTimeUtils.convertStringToTime(tvFromDate
                    .getText().toString(), "dd/MM/yyyy");
			if (fromDate.after(toDate)) {
                CommonActivity
                        .createAlertDialog(getActivity(),
                                R.string.report_warn_todate_fromdate,
                                R.string.app_name).show();
                return;
            }

            if (DateTimeUtils.calculateDays2Date(toDate, fromDate) > DURATION_MAX) {
                CommonActivity.createAlertDialog(getActivity(),
                        getString(R.string.duration_over_load, DURATION_MAX),
                        getString(R.string.app_name)).show();
                return;
            }

            if (toDate.after(new Date())) {
                cal = Calendar.getInstance();

            }
            tvToDate.setText(DateTimeUtils.convertDateTimeToString(
                    cal.getTime(), "dd/MM/yyyy"));
            resetSearchTransBankplusHistory();
        }
    };

    private void resetSearchTransBankplusHistory() {
    }

    private class AsySearchSubscriber extends AsyncTask<String, Void, String> {

        private Activity mActivity = null;
        XmlDomParse parse = new XmlDomParse();
        String errorCode = "";
        String description = "";
        ProgressDialog progress;
        String wsCode = "checkSubIncorrectInfor";

        public AsySearchSubscriber(Activity context) {
            this.progress = new ProgressDialog(context);
            // check font
            this.mActivity = context;
            this.progress.setMessage(context.getResources().getString(
                    R.string.processing));
            this.progress.setCancelable(false);
            if (!this.progress.isShowing()) {
                this.progress.show();
            }

        }

        @Override
        protected String doInBackground(String... arg0) {
            return searchSubscriber(arg0[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progress.dismiss();
            if (errorCode.equals("0")) {
                CommonActivity.createAlertDialog(getActivity(), description,
                        getActivity().getString(R.string.app_name)).show();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

        private String searchSubscriber(String isdn) {
            String original = "";
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_" + wsCode);
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:" + wsCode + ">");
                rawData.append("<input>");
                rawData.append("<token>" + Session.getToken() + "</token>");

                rawData.append("<isdn>" + StringUtils.formatIsdn(isdn)
                        + "</isdn>");

                rawData.append("</input>");
                rawData.append("</ws:" + wsCode + ">");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL, getActivity(), "mbccs_" + wsCode);
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Responseeeeeeeeee Original", original);

                // parser
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    description = parse.getValue(e2, "description");
                    Log.d("errorCode", errorCode);

                }
            } catch (Exception e) {
                Log.d("mbccs_" + wsCode, e.toString() + "description error", e);
            }

            return errorCode;

        }
    }

    private void initCap() {
        edt_capcha.setText("");
        cap = new MathCaptcha(300, 100, MathOptions.PLUS_MINUS);
        imgCapcha.setImageBitmap(cap.getImage());
        // imgCapcha.setLayoutParams(new LinearLayout.LayoutParams(cap.width *
        // 2,
        // cap.height * 2));
    }
	private class AsySearchDataCell extends AsyncTask<String, Void, BCCS2Output> {

		private Activity mActivity = null;
		XmlDomParse parse = new XmlDomParse();
		String errorCode = "";
		String description = "";
		ProgressDialog progress;
		String wsCode = "searchDataCell";

		public AsySearchDataCell(Activity context) {
			this.progress = new ProgressDialog(context);
			// check font
			this.mActivity = context;
			this.progress.setMessage(context.getResources().getString(
					R.string.processing));
			this.progress.setCancelable(false);
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected BCCS2Output doInBackground(String... arg0) {
			return searchDataCell();
		}

		@Override
		protected void onPostExecute(BCCS2Output result) {
			super.onPostExecute(result);
			progress.dismiss();
			if (errorCode.equals("0")) {
                if(CommonActivity.isNullOrEmpty(result.getDataCell())){
                    CommonActivity.createAlertDialog(getActivity(), getString(R.string.no_data),
						getActivity().getString(R.string.app_name)).show();
                } else {
                    showDialogInfo(result);
                }
			} else {
				if (errorCode.equals(Constant.INVALID_TOKEN2)) {
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

		private BCCS2Output searchDataCell() {
			BCCS2Output bccsOutput = new BCCS2Output();
			String original = "";
			try {
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs" + wsCode);
				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:" + wsCode + ">");
				rawData.append("<input>");
				rawData.append("<token>" + Session.getToken() + "</token>");

				rawData.append("<syntaxCode>" + getSyntaxLookupCode()
						+ "</syntaxCode>");
				
				rawData.append("<lookupCode>" + edtLookupCode.getText().toString().trim().toUpperCase()
						+ "</lookupCode>");
				
				rawData.append("<currentTimeMillis>" + DateTimeUtils.convertStringToTime(edtLookupDate.getText().toString(), "dd/MM/yyyy").getTime()
						+ "</currentTimeMillis>");

				rawData.append("</input>");
				rawData.append("</ws:" + wsCode + ">");
				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData
						.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope,
						Constant.BCCS_GW_URL, getActivity(), "mbccs" + wsCode);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", original);

				if (original != null && !original.isEmpty()) {
					Serializer serializer = new Persister();
					bccsOutput = serializer.read(BCCS2Output.class, original);
					if (bccsOutput != null) {
						errorCode = bccsOutput.getErrorCode();
						description = bccsOutput.getDescription();
					}

				} else {
					errorCode = Constant.ERROR_CODE;
					description = getString(R.string.no_data_return);
				}
			} catch (Exception e) {
				Log.d("mbccs" + wsCode, e.toString() + "description error", e);
			}

			return bccsOutput;

		}
		
		private String getSyntaxLookupCode(){
			String lookupCode = "";
			if(rbCell.isChecked()){
				lookupCode = "TCC";
			} else if(rbTram.isChecked()){
				lookupCode = "TCT";
			} else if(rbDistrict.isChecked()){
				lookupCode = "TCH";
			}
			if(cbLookupLK.isChecked()){
				lookupCode += "LK";
			}
			return lookupCode;
		}
	}
	
	private void showDialogInfo(BCCS2Output result) {
		final Dialog dialog = new Dialog(getActivity());
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.item_data_cell);
		dialog.setCancelable(false);

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		dialog.getWindow().setAttributes(lp);
		
		TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
		tvTitle.setText(getTitle());
		
		TextView tvTbRegister = (TextView) dialog.findViewById(R.id.tvTbRegister);
		tvTbRegister.setText(getString(R.string.txt_tb_register, StringUtils.formatMoney(result.getDataCell().getTbRegister())));
		
		TextView tvTbPsll = (TextView) dialog.findViewById(R.id.tvTbPsll);
		tvTbPsll.setText(getString(R.string.txt_tb_psll, StringUtils.formatMoney(result.getDataCell().getTbPSLL())));
		
		TextView tvLLErlang = (TextView) dialog.findViewById(R.id.tvLLErlang);
		tvLLErlang.setText(getString(R.string.txt_ll_erlang, StringUtils.formatMoney(result.getDataCell().getLlErlang())));
		
		TextView tvLLData = (TextView) dialog.findViewById(R.id.tvLLData);
		tvLLData.setText(getString(R.string.txt_ll_data, StringUtils.formatMoney(result.getDataCell().getLlData())));
		
		TextView tvTuPeak = (TextView) dialog.findViewById(R.id.tvTuPeak);
		tvTuPeak.setText(getString(R.string.txt_tu_peak, StringUtils.formatMoney(result.getDataCell().getTuPeak())));
		
		TextView tvTuTB = (TextView) dialog.findViewById(R.id.tvTuTB);
		tvTuTB.setText(getString(R.string.txt_tu_trungbinh, StringUtils.formatMoney(result.getDataCell().getTuTb())));
		
		TextView tvDtThucThoai = (TextView) dialog.findViewById(R.id.tvDtThucThoai);
		tvDtThucThoai.setText(getString(R.string.txt_dt_thucthoai_data, StringUtils.formatMoney(result.getDataCell().getDtTT())));
		
		TextView tvLstCell = (TextView) dialog.findViewById(R.id.tvLstCell);
		tvLstCell.setText(getString(R.string.txt_lst_cell, StringUtils.formatMoney(result.getDataCell().getListCell())));
		
		Button btnOke = (Button) dialog.findViewById(R.id.btnOke);
		btnOke.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		dialog.show();
	}
	
	private String getTitle(){
		String title = "";
		if(cbLookupLK.isChecked()){
			if(rbCell.isChecked()){
				title = getString(R.string.txt_title_lookup_cell_lk, edtLookupCode.getText().toString(), edtLookupDate.getText().toString());
			} else if (rbDistrict.isChecked()){
				title = getString(R.string.txt_title_lookup_huyen_lk, edtLookupCode.getText().toString(), edtLookupDate.getText().toString());
			} else if (rbTram.isChecked()){
				title = getString(R.string.txt_title_lookup_tram_lk, edtLookupCode.getText().toString(), edtLookupDate.getText().toString());
			}
		} else {
			if(rbCell.isChecked()){
				title = getString(R.string.txt_title_lookup_cell, edtLookupCode.getText().toString(), edtLookupDate.getText().toString());
			} else if (rbDistrict.isChecked()){
				title = getString(R.string.txt_title_lookup_huyen, edtLookupCode.getText().toString(), edtLookupDate.getText().toString());
			} else if (rbTram.isChecked()){
				title = getString(R.string.txt_title_lookup_tram, edtLookupCode.getText().toString(), edtLookupDate.getText().toString());
			}
		}
		return title;
	}
}
