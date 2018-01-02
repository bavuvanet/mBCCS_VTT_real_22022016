package com.viettel.bss.viettelpos.v4.omichanel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.adapter.SubWarningInfoAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;

import java.util.ArrayList;

/**
 * Created by toancx on 9/19/2017.
 */

public class VasPlusDeatailDialog extends Dialog {

    private TextView tvVasCode;
    private TextView tvVasName;
    private TextView tvVasDetail;
    private Button btnOk;

    private Context context;
    private PoCatalogOutsideDTO poCatalogOutsideDTO;

    public VasPlusDeatailDialog(Context context, PoCatalogOutsideDTO poCatalogOutsideDTO) {
        super(context);
        this.context = context;
        this.poCatalogOutsideDTO = poCatalogOutsideDTO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.omni_vas_plus_detail_dialog);

        this.tvVasCode = (TextView) findViewById(R.id.tvVasCode);
        this.tvVasName = (TextView) findViewById(R.id.tvVasName);
        this.tvVasDetail = (TextView) findViewById(R.id.tvVasDetail);
        this.btnOk = (Button) findViewById(R.id.btnOk);


        this.tvVasCode.setText(poCatalogOutsideDTO.getCode());
        this.tvVasName.setText(poCatalogOutsideDTO.getVasName());
        this.tvVasDetail.setText(poCatalogOutsideDTO.getDetail());

        this.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
    }
}