package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;

/**
 * Created by manhtd-outsource on 7/15/16.
 * Fragment thông tin kết cuối
 */
public class FragmentConnectionInfo extends Fragment {
    public interface KetCuoiPassListener {
        void selectedKetCuoi();
    }

    private View mView;
    private Button btnChon, btnBoQua;
    private TextView txtCongNghe, txtMaKetCuoi, txtDiaChiKetCuoi, txtTaiNguyenKetCuoi, txtTaiNguyenThietBi,
            txtTaiNguyenCapGoc, txtDonViQuanLyKetCuoi, txtPortRF, txtMaTram, txtHoTenNV, txtDTNV, txtChucVu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.connection_layout_subscriber_info, container, false);
        unit(mView);
        return mView;
    }

    @Override
    public void onResume() {
        addActionBar();
        super.onResume();
    }

    private void addActionBar() {
        ActionBar mActionBar = getActivity().getActionBar();
        assert mActionBar != null;
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.layout_actionbar_channel);
        LinearLayout relaBackHome = (LinearLayout) mActionBar.getCustomView().findViewById(R.id.relaBackHome);
        relaBackHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                getActivity().onBackPressed();
            }
        });
        Button btnHome = (Button) mActionBar.getCustomView().findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CommonActivity.callphone(getActivity(), Constant.phoneNumber);
            }
        });
        TextView txtNameActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtNameActionbar);
        TextView txtAddressActionBar = (TextView) mActionBar.getCustomView().findViewById(R.id.txtAddressActionbar);
        txtAddressActionBar.setVisibility(View.GONE);
        String title = getArguments().getString("title", "Back");
        txtNameActionBar.setText(title);
    }

    private void unit(View v) {
        btnChon = (Button) v.findViewById(R.id.btnChon);
        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity curActivity = getActivity();
                curActivity.onBackPressed();
                Fragment activeFragment = ReplaceFragment.getActiveFragment(curActivity);
                if (activeFragment instanceof FragmentConnectionInfo.KetCuoiPassListener) {
                    FragmentConnectionInfo.KetCuoiPassListener mCallback = (FragmentConnectionInfo.KetCuoiPassListener) activeFragment;
                    mCallback.selectedKetCuoi();
                }
            }
        });

        btnBoQua = (Button) v.findViewById(R.id.btnBoQua);
        btnBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        Bundle bundle = getArguments();
        txtCongNghe = (TextView) v.findViewById(R.id.txtCongNghe);
        txtCongNghe.setText(bundle.getString("CN", ""));

        txtTaiNguyenKetCuoi = (TextView) v.findViewById(R.id.txtTaiNguyenKetCuoi);
        txtTaiNguyenKetCuoi.setText(bundle.getString("TNKC", ""));

        txtTaiNguyenThietBi = (TextView) v.findViewById(R.id.txtTaiNguyenThietBi);
        txtTaiNguyenThietBi.setText(bundle.getString("TNTB", ""));

        txtTaiNguyenCapGoc = (TextView) v.findViewById(R.id.txtTaiNguyenCapGoc);
        txtTaiNguyenCapGoc.setText(bundle.getString("TNCG", ""));

        txtDonViQuanLyKetCuoi = (TextView) v.findViewById(R.id.txtDonViQuanLyKetCuoi);
        txtDonViQuanLyKetCuoi.setText(bundle.getString("DVQLKC", ""));

        txtPortRF = (TextView) v.findViewById(R.id.txtPortRF);
        txtPortRF.setText(bundle.getString("PRF", ""));

        txtMaTram = (TextView) v.findViewById(R.id.txtMaTram);
        txtMaTram.setText(bundle.getString("MT", ""));

        txtHoTenNV = (TextView) v.findViewById(R.id.txtHoTenNV);
        txtHoTenNV.setText(bundle.getString("HTNV", ""));

        txtDTNV = (TextView) v.findViewById(R.id.txtDTNV);
        txtDTNV.setText(bundle.getString("DTNV", ""));

        txtChucVu = (TextView) v.findViewById(R.id.txtChucVu);
        txtChucVu.setText(bundle.getString("CV", ""));

        txtMaKetCuoi = (TextView) v.findViewById(R.id.txtMaKetCuoi);
        txtMaKetCuoi.setText(bundle.getString("MKC", ""));

        txtDiaChiKetCuoi = (TextView) v.findViewById(R.id.txtDiaChiKetCuoi);
        txtDiaChiKetCuoi.setText(bundle.getString("DCKC", ""));
    }
}
