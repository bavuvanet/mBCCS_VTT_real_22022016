package com.viettel.bss.viettelpos.v4.listener;

import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.omichanel.dao.RecordTypeScanDTO;

/**
 * Created by Toancx on 5/10/2017.
 */
public interface OnImageClickListener {
    public void onClick(RecordPrepaid item, Integer imageId);
    public void onClickNew(RecordTypeScanDTO item, Integer imageId);
}
