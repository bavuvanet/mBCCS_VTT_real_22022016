package com.viettel.bss.viettelpos.v4.bankplus.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.bankplus.adapter.RecyclerViewTransactionAdapter;
import com.viettel.bss.viettelpos.v4.bankplus.object.TransactionDTO;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by hantt47 on 8/3/2017.
 */

public class ManagerWorkFragment extends FragmentCommon {
    private EditText editIdTrans;
    private Spinner spinStatus;
    private RecyclerView lvTransaction;
    private Button btnSearch;
    private ArrayList<TransactionDTO> transactionDTOs;
    private List<String> listStatus = new ArrayList<>(Arrays.asList("Chọn tất cả", "Chờ giao việc"
            , "Đã giao việc, chưa xác nhận", "Đã từ chối", "Không tìm thấy KH"));
    private List<String> listCTV = new ArrayList<>(Arrays.asList("Chọn cộng tác viên", "CTV 1", "CTV 2"
            , "CTV 3", "CTV 4", "CTV 5"));
    Dialog dialogSelectTransaction;

    @Override
    public void onResume() {
        super.onResume();
        setTitleActionBar(R.string.bankplus_menu_work_manager);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.bankplus_work_manager_fragment;
    }

    @Override
    protected void unit(View v) {
        editIdTrans = (EditText) v.findViewById(R.id.editTrans);
        spinStatus = (Spinner) v.findViewById(R.id.spinStatus);
        lvTransaction = (RecyclerView) v.findViewById(R.id.lvTrans);
        btnSearch = (Button) v.findViewById(R.id.btnSearch);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item);
        for (String status : listStatus) {
            adapter.add(status);
        }
        spinStatus.setAdapter(adapter);
        lvTransaction.setHasFixedSize(true);
        lvTransaction.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionDTOs = new ArrayList<TransactionDTO>();
                for (int i = 0; i < 10; i++) {
                    transactionDTOs.add(new TransactionDTO());
                }
                Comparator<TransactionDTO> s = new Comparator<TransactionDTO>() {
                    @Override
                    public int compare(TransactionDTO o1, TransactionDTO o2) {
                        return o1.getTimeExpire() - o2.getTimeExpire();
                    }
                };
                Collections.sort(transactionDTOs, s);
                RecyclerViewTransactionAdapter adapterTrans = new RecyclerViewTransactionAdapter(getContext(), transactionDTOs);
                lvTransaction.setAdapter(adapterTrans);
            }
        });
        lvTransaction.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), lvTransaction, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                TransactionDTO transactionDTO = transactionDTOs.get(position);
                showDiaLogSelectReason(transactionDTO);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }

    @Override
    protected void setPermission() {

    }

    private void showDiaLogSelectReason(TransactionDTO transactionDTO) {
        dialogSelectTransaction = new Dialog(getActivity());
        dialogSelectTransaction.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialogSelectTransaction
                .setContentView(R.layout.dialog_layout_transaction_assign);

        TextView txtIdTrans = (TextView) dialogSelectTransaction.findViewById(R.id.txtIdTrans);
        TextView txtMoney = (TextView) dialogSelectTransaction.findViewById(R.id.txtMoney);
        TextView txtPhone = (TextView) dialogSelectTransaction.findViewById(R.id.txtPhone);
        TextView txtAddress = (TextView) dialogSelectTransaction.findViewById(R.id.txtAddress);
        Spinner spinCTV = (Spinner) dialogSelectTransaction.findViewById(R.id.spinCTV);
        Button btnAssign = (Button) dialogSelectTransaction.findViewById(R.id.btnAssign);

        txtIdTrans.setText(transactionDTO.getIdTransaction());
        txtMoney.setText(transactionDTO.getNumberMoney());
        txtPhone.setText(transactionDTO.getPhoneNumberCus());
        txtAddress.setText(transactionDTO.getAddressCus());


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(), android.R.layout.simple_spinner_dropdown_item);
        for (String status : listCTV) {
            adapter.add(status);
        }
        spinCTV.setAdapter(adapter);
        btnAssign.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                dialogSelectReason.dismiss();
            }
        });

        dialogSelectTransaction.show();
    }
}
