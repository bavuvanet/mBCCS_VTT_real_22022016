package com.viettel.bss.viettelpos.v4.listener;

import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.object.ProfileBO;

/**
 * interface cap nhat khi chon ho so cu
 * Created by toancx on 5/25/2017.
 */
public interface OnSelectOPFListener {
    public void OnClick(RecordPrepaid input, boolean isSelect);
}
