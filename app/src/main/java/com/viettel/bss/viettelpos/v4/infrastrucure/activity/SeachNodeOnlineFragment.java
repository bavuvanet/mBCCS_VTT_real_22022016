package com.viettel.bss.viettelpos.v4.infrastrucure.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.connecttionService.adapter.GetPstnAdapter;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.GetConectortAdapter;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.GetSpliterAdapter;
import com.viettel.bss.viettelpos.v4.infrastrucure.adapter.NodeChildAdapter;
import com.viettel.bss.viettelpos.v4.infrastrucure.asyntask.FindNodeByStaffIdAndNodeCodeAsyn;
import com.viettel.bss.viettelpos.v4.infrastrucure.asyntask.GetBccsInfoAsyn;
import com.viettel.bss.viettelpos.v4.infrastrucure.asyntask.GetSplitterBySubNodeAsyn;
import com.viettel.bss.viettelpos.v4.infrastrucure.asyntask.SurveyOnlineAsyn;
import com.viettel.bss.viettelpos.v4.infrastrucure.object.NodeCodeDetail;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by thinhhq1 on 9/29/2017.
 */

public class SeachNodeOnlineFragment extends Fragment{
    private View mView = null;
    private Activity activity;
    @BindView(R.id.edtSearchNode)
    AppCompatEditText edtSearchNode;
    @BindView(R.id.lvNode)
    ListView lvNode;
    GetConectortAdapter  getConectortAdapter = null;

    GetSpliterAdapter getSpliterAdapter = null;
    private  Spin spinConecttorMain ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.layout_search_node, container,
                    false);
            activity = getActivity();
            ButterKnife.bind(this, mView);
        }
        processView();
        return mView;
    }

    private void processView(){
        edtSearchNode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = edtSearchNode.getText().toString()
                        .toLowerCase(Locale.getDefault());
                if (getConectortAdapter != null) {
                    getConectortAdapter.SearchInput(input);
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        lvNode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 spinConecttorMain = (Spin) adapterView.getItemAtPosition(i);

                // TODO xu ly cho nay
                if(spinConecttorMain != null && !CommonActivity.isNullOrEmpty(spinConecttorMain.getNodeCode())){
                    GetSplitterBySubNodeAsyn getSplitterBySubNodeAsyn = new GetSplitterBySubNodeAsyn(getActivity(),new OnPostGetSplit(),moveLogInAct);
                    getSplitterBySubNodeAsyn.execute(spinConecttorMain.getNodeCode());
                }


//                if(spinConecttorMain != null && !CommonActivity.isNullOrEmpty(spinConecttorMain.getNodeCode())){
//                    SurveyOnlineAsyn surveyOnlineAsyn = new SurveyOnlineAsyn(getActivity(),new OnPostGetSurveyOnline(),moveLogInAct);
//                    surveyOnlineAsyn.execute(spinConecttorMain.getNodeCode());
//                }
            }
        });
        FindNodeByStaffIdAndNodeCodeAsyn findNodeByStaffIdAndNodeCodeAsyn = new FindNodeByStaffIdAndNodeCodeAsyn(getActivity(),new OnPostFindNodeByStaffIdAndNodeCodeAsyn(),moveLogInAct);
        findNodeByStaffIdAndNodeCodeAsyn.execute();
    }
    private class OnPostGetSurveyOnline implements OnPostExecuteListener<ResultSurveyOnlineForBccs2Form> {
        @Override
        public void onPostExecute(ResultSurveyOnlineForBccs2Form result, String errorCode, String description) {
            if(result != null && !CommonActivity.isNullOrEmpty(result.getConnectorCode())){
                showDialogInfratructer(result);
            }else{
                CommonActivity.createAlertDialog(getActivity(),getActivity().getString(R.string.notdataconector),getActivity().getString(R.string.app_name)).show();
            }
        }
    }
    private class OnPostGetBccsInfo implements OnPostExecuteListener<ArrayList<NodeCodeDetail>> {
        @Override
        public void onPostExecute(ArrayList<NodeCodeDetail> result, String errorCode, String description) {
            if(!CommonActivity.isNullOrEmptyArray(result)){
                showDialogNode(result);
            }else{
                CommonActivity.createAlertDialog(getActivity(),getActivity().getString(R.string.notdataconector),getActivity().getString(R.string.app_name)).show();
            }
        }
    }
    private class OnPostFindNodeByStaffIdAndNodeCodeAsyn implements OnPostExecuteListener<ArrayList<Spin>> {
        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if(result != null && !CommonActivity.isNullOrEmpty(result)){

                getConectortAdapter = new GetConectortAdapter(result,getActivity());
                lvNode.setAdapter(getConectortAdapter);
            }else{
                // fake du lieu test
                ArrayList<Spin> lstSpin = new ArrayList<>();
                getConectortAdapter = new GetConectortAdapter(lstSpin,getActivity());
                lvNode.setAdapter(getConectortAdapter);
            }
        }
    }
    private class OnPostGetSplit implements OnPostExecuteListener<ArrayList<Spin>> {
        @Override
        public void onPostExecute(ArrayList<Spin> result, String errorCode, String description) {
            if(result != null && !CommonActivity.isNullOrEmpty(result)){
                // neu co danh sach spliter thi show man hinh len
                showDialogSpliter(result);
            }else{
                // neu khong co thi goi luon man hinh lay
                if(spinConecttorMain != null && !CommonActivity.isNullOrEmpty(spinConecttorMain.getNodeCode())){
                    SurveyOnlineAsyn surveyOnlineAsyn = new SurveyOnlineAsyn(getActivity(),new OnPostGetSurveyOnline(),moveLogInAct);
                    surveyOnlineAsyn.execute(spinConecttorMain.getNodeCode());
                }

            }
        }
    }


    private void showDialogInfratructer(final ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.connection_examine_online);
        LinearLayout lnportUsed = (LinearLayout) dialog.findViewById(R.id.lnportUsed);
        lnportUsed.setVisibility(View.VISIBLE);
        TextView txtusedPort = (TextView) dialog.findViewById(R.id.txtusedPort);
        TextView tvconnectorCode = (TextView) dialog.findViewById(R.id.tvconnectorCode);
        tvconnectorCode.setText(resultSurveyOnlineForBccs2Form.getConnectorCode());
        TextView txttnketcuoi = (TextView) dialog.findViewById(R.id.txttnketcuoi);
        TextView txtTaiNguyenThietBi = (TextView) dialog.findViewById(R.id.txtTaiNguyenThietBi);
        TextView txtsoluongportrong = (TextView) dialog.findViewById(R.id.txtsoluongportrong);
        TextView txtsluongportdangtkmoi = (TextView) dialog.findViewById(R.id.txtsluongportdangtkmoi);
        TextView txtCongNghe = (TextView) dialog.findViewById(R.id.txtCongNghe);
        if(!CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form.getInfraType())){
            switch (Integer.parseInt(resultSurveyOnlineForBccs2Form.getInfraType())) {
                case 1:
                    // cap dong
                    txtCongNghe.setText(this.getString(R.string.capdong));
                    break;
                case 2:
                    txtCongNghe.setText(this.getString(R.string.capdongtruc));
                    break;
                case 3:
                    txtCongNghe.setText(this.getString(R.string.capquang));
                    break;
                case 4:
                    txtCongNghe.setText(this.getString(R.string.quanggpon));
                    break;
                default:
                    break;
            }
        }
        TextView txtDiaChiKetCuoi = (TextView) dialog.findViewById(R.id.txtDiaChiKetCuoi);
        if (resultSurveyOnlineForBccs2Form != null
                && !CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form.getAddress())) {
            txtDiaChiKetCuoi.setText(resultSurveyOnlineForBccs2Form.getAddress());
        }
        LinearLayout lncapquang = (LinearLayout) dialog.findViewById(R.id.lnQuang);
        LinearLayout lnquangGPON = (LinearLayout) dialog.findViewById(R.id.lnGPON);
        if ("4".equals(resultSurveyOnlineForBccs2Form.getInfraType())) {
            lnquangGPON.setVisibility(View.VISIBLE);
            lncapquang.setVisibility(View.GONE);
            txtsoluongportrong.setText(resultSurveyOnlineForBccs2Form.getGponConnectorFreePort() + "");
            txtsluongportdangtkmoi.setText(resultSurveyOnlineForBccs2Form.getGponConnectorLockPort() + "");
            txtusedPort.setText(resultSurveyOnlineForBccs2Form.getGponConnectorUsedPort() + "");
        } else {
            lnquangGPON.setVisibility(View.GONE);
            lncapquang.setVisibility(View.VISIBLE);
            txttnketcuoi.setText(resultSurveyOnlineForBccs2Form.getResourceConnector());

        }
        txtTaiNguyenThietBi.setText(resultSurveyOnlineForBccs2Form.getResourceDevice());
        ExpandableHeightListView lvAccount = (ExpandableHeightListView) dialog.findViewById(R.id.lvAccount);
        lvAccount.setExpanded(true);
        if(!CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form) && !CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form.getLstAccountGline())){
            GetPstnAdapter getPstnAdapter = new GetPstnAdapter(resultSurveyOnlineForBccs2Form.getLstAccountGline(),getActivity());
            lvAccount.setAdapter(getPstnAdapter);
        }
        lvAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!CommonActivity.isNullOrEmpty(resultSurveyOnlineForBccs2Form.getLstAccountGline())){
                    String account = resultSurveyOnlineForBccs2Form.getLstAccountGline().get(i);
                    if(!CommonActivity.isNullOrEmpty(account)){
                        GetBccsInfoAsyn getBccsInfoAsyn = new GetBccsInfoAsyn(getActivity(),new OnPostGetBccsInfo(),moveLogInAct);
                        getBccsInfoAsyn.execute(account);
                    }
                }
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    private void showDialogNode(ArrayList<NodeCodeDetail> arrayList) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.connection_examine_online_detail);
        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
        ExpandableHeightListView lvAccount = (ExpandableHeightListView) dialog.findViewById(R.id.lvAccount);
        lvAccount.setExpanded(true);
        if(!CommonActivity.isNullOrEmpty(arrayList)){
            NodeChildAdapter nodeChildAdapter = new NodeChildAdapter(getActivity(),arrayList);
            lvAccount.setAdapter(nodeChildAdapter);
        }
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    private void showDialogSpliter(ArrayList<Spin> arrayList) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.connection_examine_online_detail);
        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
        txtTitle.setText(getActivity().getString(R.string.spliterdes));
        ExpandableHeightListView lvAccount = (ExpandableHeightListView) dialog.findViewById(R.id.lvAccount);
        lvAccount.setExpanded(true);
        if(!CommonActivity.isNullOrEmpty(arrayList)){
            GetSpliterAdapter nodeChildAdapter = new GetSpliterAdapter(arrayList,getActivity());
            lvAccount.setAdapter(nodeChildAdapter);
        }
        lvAccount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Spin spinConecttor = (Spin) adapterView.getItemAtPosition(i);
                if(spinConecttor != null && !CommonActivity.isNullOrEmpty(spinConecttor.getSplitterCode())){
                    SurveyOnlineAsyn surveyOnlineAsyn = new SurveyOnlineAsyn(getActivity(),new OnPostGetSurveyOnline(),moveLogInAct);
                    surveyOnlineAsyn.execute(spinConecttor.getSplitterCode());
                }
            }
        });
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }



    public View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), "infractrue_survey_online_mbccs2");
            dialog.show();
        }
    };
}
