package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.handler.VSAMenuHandler;
import com.viettel.bss.viettelpos.v4.sale.adapter.ResolveListOrderAdapter;
import com.viettel.bss.viettelpos.v4.sale.adapter.ResolveListOrderAdapter.OnChangeSoLuong;
import com.viettel.bss.viettelpos.v4.sale.adapter.ResolveListOrderAdapter.OncacelObjectMerge;
import com.viettel.bss.viettelpos.v4.sale.business.CacheData;
import com.viettel.bss.viettelpos.v4.sale.dal.ViewInfoAndOrderDal;
import com.viettel.bss.viettelpos.v4.sale.object.GetOrderObjectMerge;
import com.viettel.bss.viettelpos.v4.sale.object.ViewInfoOject;
import com.viettel.bss.viettelpos.v4.synchronizationdata.GetMaxOraRowScnDal;
import com.viettel.bss.viettelpos.v4.synchronizationdata.XmlDomParse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentResolveOrder extends Fragment implements OnClickListener,
        OncacelObjectMerge, OnChangeSoLuong {
    Button btnHome;
    TextView textOrderId, nameOrder, texttimeorder;
    ListView lvcontactorder;
    Button btnbrowerOrder, btncancelOrder;
    ArrayList<GetOrderObjectMerge> lismObjectMerges = new ArrayList<GetOrderObjectMerge>();
    ResolveListOrderAdapter resolveListOrderAdapter = null;
    Long quantity_UP = 0L;
    String staffId = "";

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.resolve_order_layout2,
                container, false);
        unit(mView);

        return mView;
    }

    public void unit(View view) {
        // ===init view==========
        textOrderId = (TextView) view.findViewById(R.id.txtorderid);
        nameOrder = (TextView) view.findViewById(R.id.txtnameorderid);
        texttimeorder = (TextView) view.findViewById(R.id.txttimeorder);
        lvcontactorder = (ListView) view.findViewById(R.id.listcontactorder);
        btnbrowerOrder = (Button) view.findViewById(R.id.btnduyetdonhang);
        btncancelOrder = (Button) view.findViewById(R.id.btnhuydonhang);
        btnbrowerOrder.setOnClickListener(this);
        btncancelOrder.setOnClickListener(this);
        if (CacheData.getInstanse().getOrderObject().getStockOrderCode() != null
                && CacheData.getInstanse().getOrderObject() != null) {
            staffId = CacheData.getInstanse().getOrderObject().getStaffId();
            Log.d("staffId", staffId);
            textOrderId.setText("  "
                    + CacheData.getInstanse().getOrderObject()
                    .getStockOrderCode());
            texttimeorder.setText("  "
                    + DateTimeUtils.convertDate(CacheData.getInstanse()
                    .getOrderObject().getCreateDate()));
            nameOrder.setText("  "
                    + CacheData.getInstanse().getOrderObject().getStaffName());
        }

        if (CacheData.getInstanse().getLisGetOrderObjectDetails() != null
                && CacheData.getInstanse().getLisGetOrderObjectDetails().size() > 0) {
            ArrayList<ViewInfoOject> lisInFoStockTH = new ArrayList<ViewInfoOject>();
            ViewInfoAndOrderDal dal = new ViewInfoAndOrderDal(getActivity());
            try {
                dal.open();
                lisInFoStockTH = dal.getListAllStockModelTH1();
                dal.close();
                for (int i = 0; i < lisInFoStockTH.size(); i++) {
                    Log.d("stock_model_id", lisInFoStockTH.get(i)
                            .get_stock_model_id().toString());
                    for (int j = 0; j < CacheData.getInstanse()
                            .getLisGetOrderObjectDetails().size(); j++) {
                        // ==== compare stock model
                        if (lisInFoStockTH
                                .get(i)
                                .get_stock_model_id()
                                .equals(CacheData.getInstanse()
                                        .getLisGetOrderObjectDetails().get(j)
                                        .getStockModelId())) {
                            GetOrderObjectMerge getOrderObjectMerge = new GetOrderObjectMerge();
                            getOrderObjectMerge.setNameorder(lisInFoStockTH
                                    .get(i).get_name());
                            getOrderObjectMerge.setQuantityOrder(CacheData
                                    .getInstanse()
                                    .getLisGetOrderObjectDetails().get(j)
                                    .getQuantityOrder());
                            getOrderObjectMerge.setQuantityReal(CacheData
                                    .getInstanse()
                                    .getLisGetOrderObjectDetails().get(j)
                                    .getQuantityOrder());
                            getOrderObjectMerge
                                    .setQuantityOrderTH(lisInFoStockTH.get(i)
                                            .get_quantity_issue());
                            getOrderObjectMerge.setStockOrderDetailId(CacheData
                                    .getInstanse()
                                    .getLisGetOrderObjectDetails().get(j)
                                    .getStockOrderDetailId());
                            getOrderObjectMerge.setStockModelId(lisInFoStockTH
                                    .get(i).get_stock_model_id());
                            getOrderObjectMerge.setStockOrderId(CacheData
                                    .getInstanse()
                                    .getLisGetOrderObjectDetails().get(j)
                                    .getStockOrderId());
                            lismObjectMerges.add(getOrderObjectMerge);
                            break;
                        }

                    }
                }
                resolveListOrderAdapter = new ResolveListOrderAdapter(
                        getActivity(), lismObjectMerges,
                        FragmentResolveOrder.this, FragmentResolveOrder.this);
                lvcontactorder.setAdapter(resolveListOrderAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnduyetdonhang:
                ArrayList<GetOrderObjectMerge> lischeckObjectMerges = new ArrayList<GetOrderObjectMerge>();
                for (GetOrderObjectMerge itemchecObjectMerge : lismObjectMerges) {
                    if (Long.parseLong(itemchecObjectMerge.getQuantityOrder()) == 0) {
                        lischeckObjectMerges.add(itemchecObjectMerge);
                    }
                }
                // ====check if list don hang rong
                if (lismObjectMerges.size() == 0) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.checkapproveOrder),
                            Toast.LENGTH_LONG).show();
                } else if (lischeckObjectMerges.size() == lismObjectMerges.size()) {
                    Toast.makeText(getActivity(),
                            getResources().getString(R.string.checksoluong),
                            Toast.LENGTH_LONG).show();
                }

                else {
                    if (CommonActivity.isNetworkConnected(getActivity()) == true) {
                        Dialog dialog = CommonActivity.createDialog(getActivity(),
                                getResources().getString(R.string.isapproveOrder),
                                getResources().getString(R.string.approveOrder),
                                getResources().getString(R.string.ok),
                                getResources().getString(R.string.cancel),
                                resovleOrder, null);
                        dialog.show();

                    } else {
                        Dialog dialog = CommonActivity.createAlertDialog(
                                getActivity(),
                                getResources().getString(R.string.errorNetwork),
                                getResources().getString(R.string.app_name));
                        dialog.show();
                    }

                }

                break;
            case R.id.btnhuydonhang:

                if (CommonActivity.isNetworkConnected(getActivity()) == true) {
                    Dialog dialog = CommonActivity.createDialog(getActivity(),
                            getResources().getString(R.string.checkrefuseOrder),
                            getResources().getString(R.string.refuseOrder),
                            getResources().getString(R.string.ok), getResources()
                                    .getString(R.string.cancel), RefuseOrderSyn,
                            null);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getResources().getString(R.string.errorNetwork),
                            getResources().getString(R.string.app_name));
                    dialog.show();
                }
                break;
            case R.id.btnHome:
                CommonActivity.callphone(getActivity(), Constant.phoneNumber);
                break;
            case R.id.relaBackHome:
                CacheData.getInstanse().setLisGetOrderObjectDetails(null);
                CacheData.getInstanse().setOrderObject(null);
                getActivity().onBackPressed();
                break;
            default:
                break;
        }
    }

    // move login
    OnClickListener moveLogInAct = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();

        }
    };
    // == method huy don hang========
    OnClickListener RefuseOrderSyn = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // call Asyntask========
            if (lismObjectMerges != null && lismObjectMerges.size() > 0) {
                // ==========huy don hang============
                CancelResolveAsyn cancelResolveAsyn = new CancelResolveAsyn(
                        getActivity());
                cancelResolveAsyn.execute();
            }
        }
    };
    // ========phe duyet don hang ==============
    OnClickListener resovleOrder = new OnClickListener() {

        @Override
        public void onClick(View v) {
            // call Asyntask========
            if (lismObjectMerges != null && lismObjectMerges.size() > 0) {
                // ==========phe duyet don hang============
                ResolveOrderAsyn reOrderAsyn = new ResolveOrderAsyn(
                        getActivity());
                reOrderAsyn.execute();
            }
        }
    };

    @Override
    public void onChangeSoluongListener(GetOrderObjectMerge viOjectMerge) {
        for (final GetOrderObjectMerge item : lismObjectMerges) {

            Log.d("stock_id_item", item.getStockModelId());
            Log.d("stock_id_object", viOjectMerge.getStockModelId());
            Long stock_id_item = Long.parseLong(item.getStockModelId());
            Long stock_id_object = Long.parseLong(viOjectMerge
                    .getStockModelId());
            if (stock_id_item.compareTo(stock_id_object) == 0) {
                final Long soluongBD = Long.parseLong(viOjectMerge
                        .getQuantityReal());
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.sale_input_quantity_dialog_order3);
                // dialog.setTitle(getResources().getString(
                // R.string.approveOrder));
                final TextView texteror = (TextView) dialog
                        .findViewById(R.id.texterror);

                final EditText edtsoluong = (EditText) dialog
                        .findViewById(R.id.edtQuantity);
                if (Long.parseLong(item.getQuantityOrder()) >= 0) {
                    edtsoluong.setText("" + item.getQuantityOrder());
                }

                dialog.findViewById(R.id.btnOk).setOnClickListener(
                        new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                // WHEN CLICK OK
                                String soluong = edtsoluong.getText()
                                        .toString().trim();
                                quantity_UP = Long.parseLong(soluong);
                                // ===== SO LUONG NHAP VAO = 0
                                if (quantity_UP < 0) {
                                    texteror.setVisibility(View.VISIBLE);
                                    texteror.setText(getResources().getString(
                                            R.string.checksluongam));
                                    edtsoluong.setText("");
                                    return;
                                }
                                Log.d("soluong", soluong);
                                // ======= neu so luong khac rong =====
                                if (!soluong.isEmpty()) {
                                    quantity_UP = Long.parseLong(soluong);
                                    Log.d("quantity_UP", quantity_UP + "");
                                } else {
                                    quantity_UP = 0L;
                                }
                                // check neu so luong bang rong
                                if (soluong.isEmpty() && quantity_UP == 0l) {
                                    texteror.setVisibility(View.VISIBLE);
                                    texteror.setText(getResources().getString(
                                            R.string.isorderthan));
                                    edtsoluong.setText("0");
                                    return;
                                }
                                // ========= check so luong nhap vao lon hon so
                                // luong ban dau
                                if (quantity_UP > soluongBD) {
                                    texteror.setVisibility(View.VISIBLE);
                                    texteror.setText(getResources().getString(
                                            R.string.sluongnhapvaolonhon));
                                    edtsoluong.setText("");
                                    return;
                                }
                                // ===========check sluong nhap vao lon hon so
                                // luong kho cap tren
                                if (quantity_UP > Long.parseLong(item
                                        .getQuantityOrderTH())) {
                                    texteror.setVisibility(View.VISIBLE);
                                    texteror.setText(getResources().getString(
                                            R.string.sluonglonhonkho));
                                    edtsoluong.setText("");
                                    return;
                                } else {
                                    // =======thoa man cac dieu kien
                                    dialog.dismiss();
                                    item.setQuantityOrder("" + quantity_UP);
                                    // /======add list object ==========
                                    if (lismObjectMerges.size() > 0) {
                                        Boolean isExists = false;
                                        for (int i = 0; i < lismObjectMerges
                                                .size(); i++) {
                                            if (item.getStockModelId().equals(
                                                    lismObjectMerges.get(i)
                                                            .getStockModelId())) {
                                                lismObjectMerges
                                                        .get(i)
                                                        .setQuantityOrder(
                                                                item.getQuantityOrder());
                                                isExists = true;
                                                break;
                                            }
                                        }
                                        if (!isExists) {
                                            lismObjectMerges.add(item);
                                        }
                                    } else {
                                        lismObjectMerges.add(item);
                                    }
                                    // ==== update list object
                                    resolveListOrderAdapter
                                            .notifyDataSetChanged();

                                }
                            }
                        });
                // cancel order item
                dialog.findViewById(R.id.btnViewSaleTrans).setOnClickListener(
                        new OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                // CLICK CANCEL
                                edtsoluong.setText("");
                                dialog.dismiss();
                            }
                        });
                edtsoluong.requestFocus();
                dialog.getWindow().setSoftInputMode(
                        LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                dialog.show();
            }
        }
    }

    @Override
    public void onCancelOjectMergeListenner(GetOrderObjectMerge objeOjectMerge) {
        for (GetOrderObjectMerge item : lismObjectMerges) {
            Long stock_id_item = Long.parseLong(item.getStockModelId());
            Long stock_id_object = Long.parseLong(objeOjectMerge
                    .getStockModelId());
            if (stock_id_item.compareTo(stock_id_object) == 0) {
                item.setQuantityOrder("" + 0l);
                resolveListOrderAdapter.notifyDataSetChanged();
                break;
            }

        }
    }

    // ===================asyntask phe duyet don hang ===================
    public class ResolveOrderAsyn extends AsyncTask<Void, Void, String> {
        private Context context = null;
        ProgressDialog progress;
        String errorCode = "";
        String description = "";
        XmlDomParse parse = new XmlDomParse();

        private ResolveOrderAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            // String syndata = waitingsyn
            this.progress.setMessage(this.context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            return resolveOrder(lismObjectMerges);

        }

        @Override
        protected void onPostExecute(String result) {

            // ===========if sucess - > FragmentListOrder and remove item in
            // listoder
            progress.dismiss();
            if (result.equalsIgnoreCase("0")) {
                // =======show pop up and romove lisorder

                Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                        getResources().getString(R.string.approveOrderSucess),
                        getResources().getString(R.string.approveOrder),
                        approveclick);
                dialog.show();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)
                        && description != null && !description.isEmpty()) {

                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.approveOrder),
                            moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result,
                            getResources().getString(R.string.approveOrder));
                    dialog.show();
                }
            }
        }

        // === event remove list order item

        OnClickListener approveclick = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // call Asyntask========
                // =======update stattus max_ora_rowcn stock_total = 0
                GetMaxOraRowScnDal dal = new GetMaxOraRowScnDal(context);
                try {
                    dal.open();
                    dal.runQueryDataFromServer("update max_ora_rowscn set sync_status = '0' where table_name='stock_total'");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (dal != null) {
                        dal.close();
                    }
                }

                FragmentListOrder.instance.arraGetOrderObjects.remove(CacheData
                        .getInstanse().getOrderObject());
                FragmentListOrder.instance.adapListOrderAdapter
                        .notifyDataSetChanged();
                CacheData.getInstanse().setOrderObject(null);
                CacheData.getInstanse().setLisGetOrderObjectDetails(null);
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentListOrder, false);
            }
        };

        public String resolveOrder(
                ArrayList<GetOrderObjectMerge> lisOrderObjectMerges) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_approveOrderBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:approveOrder>");
                rawData.append("<input>");
                HashMap<String, String> param = new HashMap<String, String>();
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                if (staffId != null || !staffId.isEmpty()) {
                    rawData.append("<staffId>" + staffId);
                    rawData.append("</staffId>");
                }
                if (lisOrderObjectMerges.size() > 0) {
                    for (GetOrderObjectMerge item : lisOrderObjectMerges) {
                        if (Long.parseLong(item.getQuantityOrder()) >= 0) {
                            HashMap<String, String> rawDataItem = new HashMap<String, String>();
                            rawDataItem.put("quantityOrder",
                                    item.getQuantityOrder());
                            rawDataItem.put("quantityReal",
                                    item.getQuantityOrder());
                            rawDataItem.put("stockModelId",
                                    item.getStockModelId());
                            rawDataItem.put("prodOfferId",
                                    item.getStockModelId());
                            rawDataItem.put("stockOrderDetailId",
                                    item.getStockOrderDetailId());
                            rawDataItem.put("stockOrderId",
                                    item.getStockOrderId());

                            param.put("lstStockOrderDetailDTO",
                                    input.buildXML(rawDataItem));
                            rawData.append(input.buildXML(param));
                        }
                    }
                }
                rawData.append("<stockOrderCode>"
                        + CacheData.getInstanse().getOrderObject()
                        .getStockOrderCode());
                rawData.append("</stockOrderCode>");
                rawData.append("</input>");
                rawData.append("</ws:approveOrder>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL,getActivity(),"mbccs_approveOrderBccs2");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("originalllllllll", original);
                // ===========parser xml ===================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("erorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                }
                VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
                        .parseXMLHandler(new VSAMenuHandler(), original);
                output = handler.getItem();
                if (output.getToken() != null && !output.getToken().isEmpty()) {
                    Session.setToken(output.getToken());
                }
                if (!output.getErrorCode().equals("0")) {
                    original = output.getDescription();
                } else {
                    original = output.getErrorCode();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return original;
        }
    }

    // ===================asyntask cancel duyet order ======================
    public class CancelResolveAsyn extends AsyncTask<Void, Void, String> {

        private Context context = null;
        ProgressDialog progress;
        String errorCode = "";
        String description = "";
        XmlDomParse parse = new XmlDomParse();

        private CancelResolveAsyn(Context context) {
            this.context = context;
            this.progress = new ProgressDialog(this.context);
            // check font
            // String syndata = waitingsyn
            this.progress.setMessage(this.context.getResources().getString(
                    R.string.waitting));
            if (!this.progress.isShowing()) {
                this.progress.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            return CancelResolve(lismObjectMerges);
        }

        @Override
        protected void onPostExecute(String result) {
            // ===========if sucess - > FragmentListOrder and remove item in
            // listoder
            progress.dismiss();
            if ("0".equalsIgnoreCase(errorCode)) {
                // =======show pop up and romove lisorder
                Dialog dialog = CommonActivity.createAlertDialog(
                        (Activity) context,
                        getResources().getString(R.string.refuseOrderSucess),
                        getResources().getString(R.string.refuseOrder),
                        refuseClick);
                dialog.show();
            } else {
                if (errorCode.equals(Constant.INVALID_TOKEN2)) {

                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), description, getResources()
                                    .getString(R.string.refuseOrder),
                            moveLogInAct);
                    dialog.show();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(
                            getActivity(), result,
                            getResources().getString(R.string.refuseOrder));
                    dialog.show();
                }

            }
        }

        OnClickListener refuseClick = new OnClickListener() {

            @Override
            public void onClick(View v) {
                // call Asyntask========
                FragmentListOrder.instance.arraGetOrderObjects.remove(CacheData
                        .getInstanse().getOrderObject());
                FragmentListOrder.instance.adapListOrderAdapter
                        .notifyDataSetChanged();
                CacheData.getInstanse().setOrderObject(null);
                CacheData.getInstanse().setLisGetOrderObjectDetails(null);
                CacheData.getInstanse().setLisStockOrderDetails(null);
                CacheData.getInstanse().setStockOrderCode(null);
                FragmentListOrder fragmentListOrder = new FragmentListOrder();
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentListOrder, false);
            }
        };

        public String CancelResolve(
                ArrayList<GetOrderObjectMerge> lisOrderObjectMerges) {
            String original = null;
            try {
                BCCSGateway input = new BCCSGateway();
                input.addValidateGateway("username", Constant.BCCSGW_USER);
                input.addValidateGateway("password", Constant.BCCSGW_PASS);
                input.addValidateGateway("wscode", "mbccs_refuseOrderBccs2");
                StringBuilder rawData = new StringBuilder();
                rawData.append("<ws:refuseOrder>");
                rawData.append("<input>");
                HashMap<String, String> param = new HashMap<String, String>();
                HashMap<String, String> paramToken = new HashMap<String, String>();
                paramToken.put("token", Session.getToken());
                rawData.append(input.buildXML(paramToken));
                if (staffId != null || !staffId.isEmpty()) {
                    rawData.append("<staffId>" + staffId);
                    rawData.append("</staffId>");
                }
                if (lisOrderObjectMerges.size() > 0) {
                    for (GetOrderObjectMerge item : lisOrderObjectMerges) {
                        if (Long.parseLong(item.getQuantityOrder()) >= 0) {
                            HashMap<String, String> rawDataItem = new HashMap<String, String>();
                            rawDataItem.put("quantityOrder",
                                    item.getQuantityOrder());
                            rawDataItem.put("quantityReal",
                                    item.getQuantityOrder());
                            rawDataItem.put("stockModelId",
                                    item.getStockModelId());
                            rawDataItem.put("stockOrderDetailId",
                                    item.getStockOrderDetailId());
                            rawDataItem.put("stockOrderId",
                                    item.getStockOrderId());
                            param.put("lstStockOrderDetail",
                                    input.buildXML(rawDataItem));
                            rawData.append(input.buildXML(param));
                        }
                    }
                }
                rawData.append("<stockOrderCode>"
                        + CacheData.getInstanse().getOrderObject()
                        .getStockOrderCode());
                rawData.append("</stockOrderCode>");
                rawData.append("</input>");
                rawData.append("</ws:refuseOrder>");
                Log.i("RowData", rawData.toString());
                String envelope = input.buildInputGatewayWithRawData(rawData
                        .toString());
                Log.d("Send evelop", envelope);
                Log.i("LOG", Constant.BCCS_GW_URL);
                String response = input.sendRequest(envelope,
                        Constant.BCCS_GW_URL,getActivity(),"mbccs_refuseOrderBccs2");
                Log.i("Responseeeeeeeeee", response);
                CommonOutput output = input.parseGWResponse(response);
                original = output.getOriginal();
                Log.d("originalllllllll", original);
                // parse xml
                // ===========parser xml ===================
                Document doc = parse.getDomElement(original);
                NodeList nl = doc.getElementsByTagName("return");
                for (int i = 0; i < nl.getLength(); i++) {
                    Element e2 = (Element) nl.item(i);
                    errorCode = parse.getValue(e2, "errorCode");
                    Log.d("erorCode", errorCode);
                    description = parse.getValue(e2, "description");
                    Log.d("description", description);
                }
                VSAMenuHandler handler = (VSAMenuHandler) CommonActivity
                        .parseXMLHandler(new VSAMenuHandler(), original);
                output = handler.getItem();
                if (output.getToken() != null && !output.getToken().isEmpty()) {
                    Session.setToken(output.getToken());
                }
                if (!output.getErrorCode().equals("0")) {
                    original = output.getDescription();
                } else {
                    original = output.getErrorCode();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return original;
        }
    }

}
