package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.VasInfoAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProductInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VasInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 9/5/2017.
 */

public class EditCardFragment extends FragmentCommon {

    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.tvBundleName)
    TextView tvBundleName;
    @BindView(R.id.tvBundlePrice)
    TextView tvBundlePrice;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.tvStart)
    TextView tvStart;
    @BindView(R.id.tvEnd)
    TextView tvEnd;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.editSelectCard)
    EditText editSelectCard;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    Boolean editFromAndroid = false;

    Activity activity;
    private ProductInfo productInfo;
    private ArrayList<VasInfo> vasInfos;

    private final long ONE_MILLION = 1000000L;
    private final long MAX_CARD = 2000000L;
    private final long MINE_CARD = 10000L;
    private Long delta = 0l;
    private Long minPrice = 0l;
    private Long totalPrice = 0l;
    private long card = 0;

    private String description = "";

    public EditCardFragment(ProductInfo productInfo, Long curentAmount) {
        super();
        this.productInfo = productInfo;

        if (!CommonActivity.isNullOrEmpty(productInfo.getVasInfos())) {
            this.vasInfos = new ArrayList<>(productInfo.getVasInfos());
        } else {
            this.vasInfos = new ArrayList<>();
            productInfo.setVasInfos(new ArrayList<VasInfo>());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        idLayout = R.layout.omni_edit_card_fragment;
        ButterKnife.bind(getActivity());
        activity = getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(activity.getString(R.string.info_charge_card));
    }

    @Override
    protected void unit(View v) {
        ArrayList<String> listName = new ArrayList<>();
        for (VasInfo vasInfo : productInfo.getVasInfos()) {
            totalPrice += (vasInfo.getPrice() == null ? 0l : vasInfo.getPrice());
            if (!CommonActivity.isNullOrEmpty(vasInfo.getVasCode()))
                listName.add(vasInfo.getVasCode());
        }
        if (!CommonActivity.isNullOrEmpty(productInfo.getPrice())) ;
        totalPrice += productInfo.getPrice();

        tvBundleName.setText(productInfo.getBundleName());
        tvBundlePrice.setText(StringUtils.formatMoney(productInfo.getPrice().toString()));

        String total = StringUtils.formatMoney(totalPrice.toString()) + " VNĐ ";
        tvTotalPrice.setText(total);

        minPrice = (totalPrice / 10000 + (totalPrice % 10000 == 0 ? 0 : 1)) * 10000;
        editSelectCard.setText(StringUtils.formatMoney(minPrice.toString()));
        editSelectCard.addTextChangedListener(new AddListenerOnTextChange(editSelectCard));

        String start = StringUtils.formatMoney(minPrice + "") + " VNĐ ";
        String max = StringUtils.formatMoney(ONE_MILLION + "") + " VNĐ ";
        tvStart.setText(start);
        tvEnd.setText(max);

        delta = (ONE_MILLION - minPrice) / 10000;
        seekBar.setMax(delta.intValue());

        String lstVas = "";
        if (!CommonActivity.isNullOrEmpty(productInfo.getVasInfos()))
            lstVas = "( " + TextUtils.join(" ,", listName) + " )";
        description = getString(R.string.omni_confirm_check_charge_card,
                StringUtils.formatMoney(totalPrice.toString())) + lstVas;
        tvDescription.setText(description);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (!CommonActivity.isNullOrEmpty(productInfo.getVasInfos())) {
            VasInfoAdapter vasInfoAdapter = new VasInfoAdapter(productInfo.getVasInfos());
            recyclerView.setAdapter(vasInfoAdapter);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Intent intent = new Intent(getContext(), EditCardFragment.class);
                    Bundle bundle = new Bundle();
                    Long cardValue = new Long(card);
                    bundle.putSerializable("card", cardValue);
                    intent.putExtras(bundle);
                    getTargetFragment().onActivityResult(getTargetRequestCode(),
                            DetailOrderOmniFragment.RESULT_OK, intent);
                    getFragmentManager().popBackStack();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    Long select = progress * 10000 + minPrice;
                    card = select.longValue();
                    editFromAndroid = true;
                    editSelectCard.setText(StringUtils.formatMoney(select + ""));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void setPermission() {

    }

    private boolean validate() {
        String cardType = editSelectCard.getText().toString();

        if (!CommonActivity.isNullOrEmpty(cardType)) {
            long select = Long.valueOf(cardType.replaceAll("\\.", ""));
            editSelectCard.setText(StringUtils.formatMoney(select + ""));
            card = select;

            if (select % 10000 > 0 || select < totalPrice) {
                CommonActivity.showConfirmValidate(getActivity(), R.string.omni_confirm_check_charge_card_div);
                return false;
            }

            if (select < minPrice) {
                card = minPrice;
            }
//            else {
//                if (select > ONE_MILLION) {
//                    card = ONE_MILLION;
//                }
//            }

            return true;
        } else {
            card = 0;
            return false;
        }
    }

    class AddListenerOnTextChange implements TextWatcher {
        private EditText mEdt;
        private String textFormat;

        public AddListenerOnTextChange(EditText mEdt) {
            super();
            this.mEdt = mEdt;
        }

        @Override
        public void afterTextChanged(Editable s) {
            String textInput = mEdt.getText().toString().replaceAll("\\.", "");
            if (!editFromAndroid & !CommonActivity.isNullOrEmpty(textInput)) {

                Long nCard = Long.valueOf(textInput);
                nCard = (nCard / MINE_CARD + (nCard % MINE_CARD == 0 ? 0 : 1)) * MINE_CARD;
                Long max;
                Long min = minPrice;

                if (nCard < ONE_MILLION) {
                    max = ONE_MILLION;
                } else if (nCard <= MAX_CARD) {
                    max = nCard;
                } else {
                    max = MAX_CARD;
                }

                tvStart.setText(StringUtils.formatMoney(min + "") + " VNĐ");
                tvEnd.setText(StringUtils.formatMoney(max + "") + " VNĐ");

                Long progress = (nCard - min) / 10000;
                Long maxPro = (max - min) / 10000;

                seekBar.setMax(maxPro.intValue());
                seekBar.setProgress(progress.intValue());
                card = nCard;

                textFormat = StringUtils.formatMoney(textInput );
                mEdt.removeTextChangedListener(this);
                mEdt.setText(textFormat);
                mEdt.setSelection(textFormat.length());
                mEdt.addTextChangedListener(new AddListenerOnTextChange(mEdt));
            } else {
                editFromAndroid = false;
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }
}
