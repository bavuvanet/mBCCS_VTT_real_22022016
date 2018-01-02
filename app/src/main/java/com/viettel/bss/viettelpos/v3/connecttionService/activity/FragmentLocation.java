package com.viettel.bss.viettelpos.v3.connecttionService.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.dal.CacheDatabaseManager;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;
import com.viettel.bss.viettelpos.v4.ui.image.utils.DebugLog;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by manhtd - outsoucing on 7/14/16.
 * Fragment cho phÃ©p ngÆ°á»�i dÃ¹ng chá»�n Ä‘á»‹a chá»‰
 */
public class FragmentLocation extends Fragment {
    public interface LocationPassListener {
        void passLocation(AreaBean area);
    }

    private View mView;
    private Button btnFinish, btnCancel, btnRefreshStreetBlock;
    private AutoCompleteTextView txtTinh, txtQuan, txtPhuong, txtTo;
    private EditText txtDuong, txtSoNha;
    private ArrayList<AreaBean> arrProvince = new ArrayList<AreaBean>();
    private ArrayList<AreaBean> arrDistrict = new ArrayList<AreaBean>();
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<AreaBean>();
    private List<AreaObj> arrStreetBlock = new ArrayList<AreaObj>();
    private ArrayAdapter<String> adapterProvince;
    private ArrayAdapter<String> adapterDistrict;
    private ArrayAdapter<String> adapterPrecinct;
    private ArrayAdapter<String> adapterStreetBlock;
    private AreaBean currentArea = new AreaBean();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.layout_location, container, false);

        // get list province
        try {
            GetAreaDal dal = new GetAreaDal(getActivity());
            dal.open();
            arrProvince = dal.getLstProvince();
            ArrayList<String> lstProvince = new ArrayList<String>();
            if(arrProvince != null && arrProvince.size() > 0){
            	 for (AreaBean area: arrProvince) {
                     lstProvince.add(area.getNameProvince());
                 }
                 adapterProvince = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, lstProvince);
            }
           
            adapterDistrict = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);
            adapterPrecinct = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);
            adapterStreetBlock = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line);

            dal.close();
        } catch (Exception ex) {
            DebugLog.e(ex.toString());
        }

        unit(mView);
        return mView;
    }

    // unit view
    private void unit(View v) {
        btnFinish = (Button) v.findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity curActivity = getActivity();
                if (txtTinh.getText().toString().trim().length() == 0) {
                    CommonActivity.createAlertDialog(curActivity,
                            getActivity().getString(R.string.provinceempty), getString(R.string.app_name)).show();
                    txtTinh.requestFocus();
                } else if (txtQuan.getText().toString().trim().length() == 0) {
                    CommonActivity.createAlertDialog(curActivity,
                    		getActivity().getString(R.string.districtempty), getString(R.string.app_name)).show();
                    txtQuan.requestFocus();
                } else if (txtPhuong.getText().toString().trim().length() == 0) {
                    CommonActivity.createAlertDialog(curActivity,
                    		getActivity().getString(R.string.precinctempty), getString(R.string.app_name)).show();
                    txtPhuong.requestFocus();
                } else if (txtTo.getText().toString().trim().length() == 0) {
                    CommonActivity.createAlertDialog(curActivity,
                    		getActivity().getString(R.string.groupempty), getString(R.string.app_name)).show();
                    txtTo.requestFocus();
                } else {
                    currentArea.setStreet(txtDuong.getText().toString().trim());
                    currentArea.setHomeNumber(txtSoNha.getText().toString().trim());
                    String fullAddr = currentArea.getHomeNumber() + " "
                            + currentArea.getStreet() + " " + currentArea.getNameStreetBlock() + " ";
                    GetAreaDal dal = new GetAreaDal(getActivity());
                    try {
                        dal.open();
                        if(fullAddr.trim().length() == 0) {
                            fullAddr = "";
                        }
                        fullAddr = fullAddr + dal.getfulladddress(currentArea.getProvince()
                                + currentArea.getDistrict() + currentArea.getPrecinct());
                    } catch (Exception e) {
                        DebugLog.e(e.getMessage());
                    } finally {
                        dal.close();
                    }
                    currentArea.setFullAddress(fullAddr);
                    curActivity.onBackPressed();
                    Fragment activeFragment = ReplaceFragment.getActiveFragment(curActivity);
                    if (activeFragment instanceof LocationPassListener) {
                        LocationPassListener mCallback = (LocationPassListener) activeFragment;
                        mCallback.passLocation(currentArea);
                    }
                }
            }
        });

        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        btnRefreshStreetBlock = (Button) v.findViewById(R.id.btnRefreshStreetBlock);
        btnRefreshStreetBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPhuong.getText().length() > 0) {
                    txtTo.setText("");
                    adapterStreetBlock.clear();
                    GetListStreetBlockAsyncTask async = new GetListStreetBlockAsyncTask(getActivity(), null);
                    async.execute(currentArea.getProvince() + currentArea.getDistrict() + currentArea.getPrecinct());
                }
            }
        });

        txtTinh = (AutoCompleteTextView) v.findViewById(R.id.edtprovince);
        txtTinh.setAdapter(adapterProvince);
        txtTinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String code = getAreaCode(txtTinh.getText().toString(), AreaType.PROVINCE);
                if(null != code) {
                    currentArea.setProvince(code);
                    selectedArea(currentArea, AreaType.PROVINCE);
                }
            }
        });
        txtTinh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    int i = adapterProvince.getPosition(txtTinh.getText().toString());
                    if(i < 0) {
                        txtTinh.setText("");

                        txtQuan.setText("");
                        adapterDistrict.clear();
                        adapterDistrict.notifyDataSetChanged();
                        txtQuan.setFocusable(false);
                        txtQuan.setFocusableInTouchMode(false);

                        txtPhuong.setText("");
                        adapterPrecinct.clear();
                        adapterPrecinct.notifyDataSetChanged();
                        txtPhuong.setFocusable(false);
                        txtPhuong.setFocusableInTouchMode(false);

                        txtTo.setText("");
                        adapterStreetBlock.clear();
                        adapterStreetBlock.notifyDataSetChanged();
                        txtTo.setFocusable(false);
                        txtTo.setFocusableInTouchMode(false);
                    }
                }
            }
        });

        txtQuan = (AutoCompleteTextView) v.findViewById(R.id.edtdistrict);
        txtQuan.setAdapter(adapterDistrict);
        txtQuan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String code = getAreaCode(txtQuan.getText().toString(), AreaType.DISTRICT);
                if(null != code) {
                    currentArea.setDistrict(code);
                    selectedArea(currentArea, AreaType.DISTRICT);
                }
            }
        });
        txtQuan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    int i = adapterDistrict.getPosition(txtQuan.getText().toString());
                    if(i < 0) {
                        txtQuan.setText("");

                        txtPhuong.setText("");
                        adapterPrecinct.clear();
                        adapterPrecinct.notifyDataSetChanged();
                        txtPhuong.setFocusable(false);
                        txtPhuong.setFocusableInTouchMode(false);

                        txtTo.setText("");
                        adapterStreetBlock.clear();
                        adapterStreetBlock.notifyDataSetChanged();
                        txtTo.setFocusable(false);
                        txtTo.setFocusableInTouchMode(false);
                    }
                }
            }
        });

        txtPhuong = (AutoCompleteTextView) v.findViewById(R.id.edtprecinct);
        txtPhuong.setAdapter(adapterPrecinct);
        txtPhuong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String code = getAreaCode(txtPhuong.getText().toString(), AreaType.PRECINCT);
                if(null != code) {
                    currentArea.setPrecinct(code);
                    selectedArea(currentArea, AreaType.PRECINCT);
                }
            }
        });
        txtPhuong.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    int i = adapterPrecinct.getPosition(txtPhuong.getText().toString());
                    if(i < 0) {
                        txtPhuong.setText("");
                        txtTo.setText("");
                        adapterStreetBlock.clear();
                        adapterStreetBlock.notifyDataSetChanged();
                        txtTo.setFocusable(false);
                        txtTo.setFocusableInTouchMode(false);
                    }
                }
            }
        });

        txtTo = (AutoCompleteTextView) v.findViewById(R.id.edtStreetBlock);
        txtTo.setAdapter(adapterStreetBlock);
        txtTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                String code = getAreaCode(editable.toString(), AreaType.STREETBLOCK);
                if(null != code) {
                    currentArea.setStreetBlock(code);
                    currentArea.setNameStreetBlock(editable.toString());
                }
            }
        });
        txtTo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    int i = adapterStreetBlock.getPosition(txtTo.getText().toString());
                    if(i < 0) {
                        txtTo.setText("");
                        txtTo.setFocusable(false);
                        txtTo.setFocusableInTouchMode(false);
                    }
                }
            }
        });

        txtDuong = (EditText) v.findViewById(R.id.edt_streetName);
        txtSoNha = (EditText) v.findViewById(R.id.edtHomeNumber);
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

    /**
     * Lay danh sach khu vuc con khi lua chon khu vuc cha, vi du lay danh sach cac quan sau khi chon thanh pho Hanoi
     * @param selectedArea code khu vuc vua chon
     * @param areaType loai khu vuc vua chon trong AreaType
     */
    private void selectedArea(AreaBean selectedArea, AreaType areaType) {
        try {
            GetAreaDal dal = new GetAreaDal(getActivity());
            dal.open();
            ArrayList<String> lst = new ArrayList<String>();
            switch (areaType) {
                case PROVINCE:
                    arrDistrict = dal.getLstDistrict(selectedArea.getProvince());
                    for (AreaBean area: arrDistrict) {
                        lst.add(area.getNameDistrict());
                    }
                    adapterDistrict.clear();
                    adapterDistrict.addAll(lst);
                    adapterDistrict.notifyDataSetChanged();
                    txtQuan.setText("");
                    adapterPrecinct.clear();
                    adapterPrecinct.notifyDataSetChanged();
                    txtPhuong.setText("");
                    adapterStreetBlock.clear();
                    adapterStreetBlock.notifyDataSetChanged();
                    txtTo.setText("");
                    txtQuan.setFocusable(true);
                    txtQuan.setFocusableInTouchMode(true);
                    break;
                case DISTRICT:
                    arrPrecinct = dal.getLstPrecinct(selectedArea.getProvince(), selectedArea.getDistrict());
                    for (AreaBean area: arrPrecinct) {
                        lst.add(area.getNamePrecint());
                    }
                    adapterPrecinct.clear();
                    adapterPrecinct.addAll(lst);
                    adapterPrecinct.notifyDataSetChanged();
                    txtPhuong.setText("");
                    adapterStreetBlock.clear();
                    adapterStreetBlock.notifyDataSetChanged();
                    txtTo.setText("");
                    txtPhuong.setFocusable(true);
                    txtPhuong.setFocusableInTouchMode(true);
                    break;
                case PRECINCT:
                    txtTo.setText("");
                    adapterStreetBlock.clear();
                    GetListStreetBlockAsyncTask async = new GetListStreetBlockAsyncTask(getActivity(), null);
                    async.execute(currentArea.getProvince() + currentArea.getDistrict() + currentArea.getPrecinct());
                    break;
            }
            dal.close();
        } catch (Exception ex) {
            DebugLog.e(ex.getMessage());
        }
    }

    private enum AreaType {
        PROVINCE,
        DISTRICT,
        PRECINCT,
        STREETBLOCK
    }

    private String getAreaCode(String value, AreaType type) {
        switch (type) {
            case PROVINCE:
                for (AreaBean area : arrProvince) {
                    if(area.getNameProvince().equals(value)) {
                        return area.getProvince();
                    }
                }
                break;
            case DISTRICT:
                for (AreaBean area : arrDistrict) {
                    if(area.getNameDistrict().equals(value)) {
                        return area.getDistrict();
                    }
                }
                break;
            case PRECINCT:
                for (AreaBean area : arrPrecinct) {
                    if(area.getNamePrecint().equals(value)) {
                        return area.getPrecinct();
                    }
                }
                break;
            case STREETBLOCK:
                for (AreaObj area : arrStreetBlock) {
                    if(area.getName().equals(value)) {
                        return area.getAreaCode();
                    }
                }
                break;
        }

        return null;
    }

    // move login
    private View.OnClickListener moveLogInAct = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
            MainActivity.getInstance().finish();
        }
    };

    private class GetListStreetBlockAsyncTask extends AsyncTask<String, Void, ArrayList<AreaObj>> {
        private Context context = null;
        private XmlDomParse parse = new XmlDomParse();
        private String errorCode = "";
        private String description = "";
        private ProgressDialog progress;
        private ProgressBar prb_bar = null;

        GetListStreetBlockAsyncTask(Context context, ProgressBar progessBar) {
            this.context = context;
            this.prb_bar = progessBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            this.progress = new ProgressDialog(context);
            this.progress.setCancelable(false);
            this.progress.setMessage(context.getResources().getString(R.string.getdataing));
            this.progress.show();

            if (prb_bar != null) {
                prb_bar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected ArrayList<AreaObj> doInBackground(String... params) {
            return getListStreetBlock(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<AreaObj> result) {
            super.onPostExecute(result);

            if (prb_bar != null) {
                prb_bar.setVisibility(View.GONE);
            }
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }
            if (errorCode.equals("0")) {
                if (result != null && result.size() > 0) {
                    arrStreetBlock = result;
                    ArrayList<String> lst = new ArrayList<String>();
                    for (AreaObj area: arrStreetBlock) {
                        lst.add(area.getName());
                    }
                    adapterStreetBlock.addAll(lst);
                    txtTo.setFocusable(true);
                    txtTo.setFocusableInTouchMode(true);
                } else {
                    txtTo.setFocusable(false);
                    txtTo.setFocusableInTouchMode(false);
                    Dialog dialog = CommonActivity.createAlertDialog(context, getString(R.string.notStreetBlock),
                            getString(R.string.app_name));
                    assert dialog != null;
                    dialog.show();
                }
                adapterStreetBlock.notifyDataSetChanged();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(), description,
                                    context.getResources().getString(R.string.app_name), moveLogInAct);
                    assert dialog != null;
                    dialog.show();
                } else {
                    if (description == null || description.isEmpty()) {
                        description = context.getString(R.string.checkdes);
                    }
                    Dialog dialog = CommonActivity.createAlertDialog(context, description,
                            context.getResources().getString(R.string.app_name));
                    assert dialog != null;
                    dialog.show();
                    txtTo.setFocusable(false);
                    txtTo.setFocusableInTouchMode(false);
                }
            }
        }

        private ArrayList<AreaObj> getListStreetBlock(String parentCode) {
            if (parentCode == null || parentCode.isEmpty()) {
                return new ArrayList<AreaObj>();
            }
            ArrayList<AreaObj> listGroupAdress;
            listGroupAdress = new CacheDatabaseManager(MainActivity.getInstance()).getListCacheStreetBlock(parentCode);

            if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
                errorCode = "0";
                return listGroupAdress;
            }
            String original;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_getListArea");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:getListArea>");
                rawData.append("<input>");
                rawData.append("<token>").append(Session.getToken()).append("</token>");
                rawData.append("<parentCode>").append(parentCode).append("</parentCode>");
                rawData.append("</input>");
                rawData.append("</ws:getListArea>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, context, "mbccs_getListArea");
                Log.i("Response", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.i("Response Original group", response);

                // ==== parse xml list ip
                listGroupAdress = parserListGroup(original);
                if (listGroupAdress != null && !listGroupAdress.isEmpty()) {
                    new CacheDatabaseManager(MainActivity.getInstance())
                            .insertCacheStreetBlock(listGroupAdress, parentCode);
                }

            } catch (Exception e) {
                Log.d("getListIP", e.toString());
            }
            return listGroupAdress;
        }

        public ArrayList<AreaObj> parserListGroup(String original) {
            ArrayList<AreaObj> listGroupAdress = new ArrayList<AreaObj>();
            Document doc = parse.getDomElement(original);
            NodeList nl = doc.getElementsByTagName("return");
            NodeList nodechild;
            for (int i = 0; i < nl.getLength(); i++) {
                Element e2 = (Element) nl.item(i);
                errorCode = parse.getValue(e2, "errorCode");
                description = parse.getValue(e2, "description");
                Log.d("errorCode", errorCode);
                nodechild = doc.getElementsByTagName("lstArea");
                for (int j = 0; j < nodechild.getLength(); j++) {
                    Element e1 = (Element) nodechild.item(j);
                    AreaObj areaObject = new AreaObj();
                    areaObject.setName(parse.getValue(e1, "name"));
                    Log.d("name area: ", areaObject.getName());
                    areaObject.setAreaCode(parse.getValue(e1, "streetBlock"));
                    listGroupAdress.add(areaObject);
                }
            }
            return listGroupAdress;
        }
    }
}
