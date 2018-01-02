package com.viettel.bss.viettelpos.v4.omichanel.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.ListStaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;

/**
 * Created by hantt47 on 9/26/2017.
 */

public class ListStaffDialog extends Dialog implements OnItemClickListener {

    private Context context;
    private Button btnOk, btnCancel;
    private ListView lvStaff;
    private ArrayList<Staff> arrStaff;
    private TextView tvStaff, tvTitle;
    private CheckBox ckbAll;
    private ImageView imgSearch;
    private EditText edtSearch;

    private ListStaffAdapter listStaffAdapter;
    private ArrayList<Staff> listSearch;


    public ListStaffDialog(Context context, ArrayList<Staff> arrStaff, TextView tvStaff) {
        super(context);
        this.context = context;
        this.arrStaff = arrStaff;
        this.tvStaff = tvStaff;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.omni_dialog_staff);
        unit();
    }


    public void unit() {
        this.imgSearch = (ImageView) findViewById(R.id.imgSearch);
        this.edtSearch = (EditText) findViewById(R.id.edtSearch);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.btnOk = (Button) findViewById(R.id.btnOk);
        this.btnCancel = (Button) findViewById(R.id.btnCancel);
        this.lvStaff = (ListView) findViewById(R.id.lvStaff);
        ckbAll = (CheckBox) findViewById(R.id.ckbAll);

        ckbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ArrayList<Staff> listCheckAll ;
                if(!CommonActivity.isNullOrEmpty(listSearch)){
                    listCheckAll = listSearch;
                }else{
                    listCheckAll = arrStaff;
                }
                if (ckbAll.isChecked()) {
                    for (Staff staff : listCheckAll) {
                        staff.setCheck(true);
                    }
                    listStaffAdapter.notifyDataSetChanged();
                } else {
                    for (Staff staff : listCheckAll) {
                        staff.setCheck(false);
                    }
                    listStaffAdapter.notifyDataSetChanged();
                }
            }
        });

        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dismiss();
            }
        });


        listStaffAdapter = new ListStaffAdapter(context, arrStaff);
        lvStaff.setAdapter(listStaffAdapter);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String nameStaff = "";
                boolean chooseStaff = false;
                for (Staff staff : arrStaff) {
                    if (staff.isCheck()) {
                        nameStaff += ", " + staff.getName();
                        chooseStaff = true;
                    }
                }
                nameStaff = nameStaff.replaceFirst(", ", "");
                if (chooseStaff) {
                    tvStaff.setText(nameStaff);
                } else {
                    tvStaff.setText(context.getResources().getString(R.string.choose_staff));
                }
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvTitle.getVisibility() == View.VISIBLE) tvTitle.setVisibility(View.GONE);
                else tvTitle.setVisibility(View.VISIBLE);
                if (edtSearch.getVisibility() == View.VISIBLE) {
                    edtSearch.setVisibility(View.GONE);
                    edtSearch.setText("");
                    CommonActivity.hideKeyboard(edtSearch, context);
                } else {
                    edtSearch.setVisibility(View.VISIBLE);
                    CommonActivity.showKeyBoard(edtSearch, context);
                }

            }
        });
        edtSearch.addTextChangedListener(new AddListenerOnTextChange(edtSearch));
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    class AddListenerOnTextChange implements TextWatcher {
        private EditText edtText;

        public AddListenerOnTextChange(EditText edtText) {
            super();
            this.edtText = edtText;
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String textInput = edtText.getText().toString().trim().toLowerCase();
            listSearch = new ArrayList();
            for (Staff staff : arrStaff) {
                if (staff.getName().trim().toLowerCase().contains(textInput)
                        || staff.getStaffCode().trim().toLowerCase().contains(textInput)) {
                    listSearch.add(staff);
                }
            }

            listStaffAdapter = new ListStaffAdapter(context, listSearch);
            lvStaff.setAdapter(listStaffAdapter);
            listStaffAdapter.notifyDataSetChanged();
        }
    }
}
