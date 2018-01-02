package com.viettel.bss.viettelpos.v4.omichanel.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.StaffClaimAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ClaimAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.SearchOrderToClaimFragment;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;


import java.util.ArrayList;

/**
 * Created by hantt47 on 9/26/2017.
 */

public class ClaimDialog extends Dialog implements OnItemClickListener {

    private Context context;
    private ListView lvStaff;
    private EditText edtSearch;
    private ImageView imgSearch;
    private TextView tvTitle;
    private ArrayList<Staff> arrStaff, arrStaffSearch = new ArrayList<>();
    private String taskId, processInstanceId ;
    private boolean forStaffOwner;
    private android.support.v4.app.FragmentManager getFragmentManager;
    private StaffClaimAdapter arrStaffAdapter=null;
    private ConnectionOrder connectionOrder;

    public ClaimDialog(Context context, ArrayList<Staff> arrStaff, boolean forStaffOwner, android.support.v4.app.FragmentManager getFragmentManager, ConnectionOrder connectionOrder) {
        super(context);
        this.context = context;
        this.arrStaff = arrStaff;
        this.forStaffOwner = forStaffOwner;
        this.getFragmentManager = getFragmentManager;
        this.connectionOrder = connectionOrder;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.omni_dialog_hand_over);
        unit();
    }


    public void unit() {
        arrStaffSearch.addAll(arrStaff);
        //remove staff of Order
        for(Staff staff: arrStaff){
            if(staff.getStaffId().equals(connectionOrder.getStaffId())){
                arrStaffSearch.remove(staff);
                break;
            }
        }
        taskId = connectionOrder.getTaskId();
        processInstanceId = connectionOrder.getProcessInstanceId();
        this.lvStaff = (ListView) findViewById(R.id.lvStaff);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.imgSearch = (ImageView) findViewById(R.id.imgSearch);
        this.edtSearch = (EditText) findViewById(R.id.edtSearch);


        arrStaffAdapter = new StaffClaimAdapter(context, this.arrStaff);
        lvStaff.setAdapter(arrStaffAdapter);
        arrStaffAdapter.notifyDataSetChanged();

        lvStaff.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameStaffSelect = arrStaffAdapter.getItem(position).getName();
                String staffIdOfOrder = connectionOrder.getStaffId()+"";
                String nameStaffOfOrder = "...";
                for(Staff staff: arrStaff){
                    if(staffIdOfOrder.equals(staff.getStaffId()+"")){
                        nameStaffOfOrder = staff.getName();
                        break;
                    }
                }

                final String staffIdSelect = arrStaffAdapter.getItem(position).getStaffId() + "";
                CommonActivity.createDialog((Activity) context,
                        context.getResources().getString(R.string.confirm_hand_over,processInstanceId, nameStaffOfOrder, nameStaffSelect),
                        context.getResources().getString(R.string.app_name),
                        context.getResources().getString(R.string.cancel),
                        context.getResources().getString(R.string.buttonOk),
                        null, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                doClaim(taskId, staffIdSelect, forStaffOwner);
                            }
                        }).show();
            }
        });

        imgSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(tvTitle.getVisibility() == View.VISIBLE) tvTitle.setVisibility(View.GONE); else tvTitle.setVisibility(View.VISIBLE);
                if(edtSearch.getVisibility()== View.VISIBLE) {
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

    public void doClaim(String taskId, String staffId, boolean forStaffOwner) {
        ClaimAsyncTask claimAsyncTask = new ClaimAsyncTask((Activity) context, new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                dismiss();
                if("0".equals(errorCode)) {
                    CommonActivity.createAlertDialog(context, context.getString(R.string.claim_success, processInstanceId), context.getString(R.string.app_name)).show();
                    getFragmentManager.popBackStack();
                }else{
                    CommonActivity.createAlertDialog(context, description, context.getString(R.string.app_name)).show();
                }

            }
        }, null);
        claimAsyncTask.execute(taskId, staffId, forStaffOwner + "");
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
            ArrayList<Staff> listSearch = new ArrayList();
            for(Staff staff: arrStaff){
                if(staff.getName().trim().toLowerCase().contains(textInput)
                        || staff.getStaffCode().trim().toLowerCase().contains(textInput)){
                    listSearch.add(staff);
                }
            }

            arrStaffAdapter = new StaffClaimAdapter(context, listSearch);
            lvStaff.setAdapter(arrStaffAdapter);
            arrStaffAdapter.notifyDataSetChanged();
        }
    }
}
