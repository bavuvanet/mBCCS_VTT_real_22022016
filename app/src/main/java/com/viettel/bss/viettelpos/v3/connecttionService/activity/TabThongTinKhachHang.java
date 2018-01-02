package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.viettel.bss.viettelpos.v4.R;

/**
 * Created by manhtd-outsource on 7/19/16.
 * Hien thi tab thong tin khach hang trong cua so thong tin dau noi
 */
public class TabThongTinKhachHang extends Fragment {
    Activity activity;
    View mView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_thong_tin_khach_hang, container, false);
            unit(mView);
        } else {
            ((ViewGroup) mView.getParent()).removeAllViews();
        }
        return mView;
    }

    public void unit(View v) {
    }
}
