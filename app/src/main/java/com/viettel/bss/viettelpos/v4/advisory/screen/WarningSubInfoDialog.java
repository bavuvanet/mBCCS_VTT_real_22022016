package com.viettel.bss.viettelpos.v4.advisory.screen;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.advisory.adapter.SubWarningInfoAdapter;

import java.util.ArrayList;

/**
 * Created by hantt47 on 7/13/2017.
 */

public class WarningSubInfoDialog extends Dialog {
    private ArrayList<String> mesStrings;
    private Context context;
    private ListView lvMessage;
    private SubWarningInfoAdapter subWarningInfoAdapter;

    public WarningSubInfoDialog(Context context, ArrayList<String> mesStrings) {
        super(context);
        this.context = context;
        this.mesStrings = mesStrings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.warning_sub_info_dialog_view);

        this.lvMessage = (ListView) findViewById(R.id.lvMessage);
        this.subWarningInfoAdapter = new SubWarningInfoAdapter(mesStrings, context);
        this.lvMessage.setAdapter(subWarningInfoAdapter);

        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });
    }
}