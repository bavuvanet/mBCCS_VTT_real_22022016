package com.viettel.bss.viettelpos.v4.customer.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.squareup.picasso.Picasso;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.AttackFileAdapter;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DataUtils;
import com.viettel.bss.viettelpos.v4.commons.DateTimeDialogWrapper;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentSearchLocation;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.AreaBean;
import com.viettel.bss.viettelpos.v4.connecttionService.dal.GetAreaDal;
import com.viettel.bss.viettelpos.v4.connecttionService.listener.ExpandableHeightListView;
import com.viettel.bss.viettelpos.v4.customer.asynctask.AsynTaskGetSubscriberFullByIsdn;
import com.viettel.bss.viettelpos.v4.customer.asynctask.GetTimeProcessAsyncTask;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberFullDTO;
import com.viettel.bss.viettelpos.v4.customview.CustomAutoCompleteTextView;
import com.viettel.bss.viettelpos.v4.customview.obj.FileObj;
import com.viettel.bss.viettelpos.v4.dialog.LoginDialog;
import com.viettel.bss.viettelpos.v4.object.ComplainDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.task.AsynTaskCreateProblemForMBCCS;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetListProblemGroupInProcess;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetProblemPriority;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetProblemTypeByParrent;
import com.viettel.bss.viettelpos.v4.task.AsynTaskGetProblemTypeInProcess;
import com.viettel.bss.viettelpos.v4.task.AsynTaskSearchSubscriber;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePickerActivity;
import com.viettel.bss.viettelpos.v4.ui.image.picker.ImagePreviewActivity;
import com.viettel.bss.viettelpos.v4.ui.image.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;

import static com.viettel.bss.viettelpos.v4.commons.CommonActivity.createAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ComplainReceiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplainReceiveFragment extends FragmentCommon {

    @BindView(R.id.edtIdNo)
    EditText edtIdNo;
    @BindView(R.id.edtIsdnComplain)
    EditText edtIsdnComplain;
    @BindView(R.id.lnComplain)
    LinearLayout lnComplain;
    @BindView(R.id.spnPriorityLevel)
    Spinner spnPriorityLevel;
    @BindView(R.id.spnGroupComplaint)
    Spinner spnGroupComplaint;
    @BindView(R.id.spnTheLoai)
    Spinner spnTheLoai;
    @BindView(R.id.spnType)
    Spinner spnType;
    @BindView(R.id.edtProvince)
    EditText edtProvince;
    @BindView(R.id.edtDistrict)
    EditText edtDistrict;
    @BindView(R.id.edtPrecint)
    EditText edtPrecint;
    @BindView(R.id.edtDateAppoint)
    EditText edtDateAppoint;
    @BindView(R.id.lvFileAttack)
    ExpandableHeightListView lvFileAttack;
    @BindView(R.id.lnAddFileAttack)
    LinearLayout lnAddFileAttack;
    @BindView(R.id.btnComplain)
    Button btnComplain;
    @BindView(R.id.edtProblemContent)
    EditText edtProblemContent;
    @BindView(R.id.edtAddress)
    EditText edtAddress;
    @BindView(R.id.edtIsdnContact)
    EditText edtIsdnContact;
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtNote)
    EditText edtNote;
    @BindView(R.id.edtCustComplain)
    EditText edtCustComplain;
    //    @BindView(R.id.spnMethodReceive)
//    Spinner spnMethodReceive;
    @BindView(R.id.expanInfoCustComplain)
    ExpandableLinearLayout expanInfoCustComplain;
    @BindView(R.id.expanInfoComplain)
    ExpandableLinearLayout expanInfoComplain;
    @BindView(R.id.expanInfoProcess)
    ExpandableLinearLayout expanInfoProcess;
    @BindView(R.id.expanInfoAddition)
    ExpandableLinearLayout expanInfoAddition;

    @BindView(R.id.lnInfoCustComplain)
    LinearLayout lnInfoCustComplain;
    @BindView(R.id.acImgViewInfoCustComplain)
    AppCompatImageView acImgViewInfoCustComplain;

    @BindView(R.id.lnInfoComplain)
    LinearLayout lnInfoComplain;
    @BindView(R.id.acImgViewInfoComplain)
    AppCompatImageView acImgViewInfoComplain;

    @BindView(R.id.lnInfoProcess)
    LinearLayout lnInfoProcess;
    @BindView(R.id.acImgViewInfoProcess)
    AppCompatImageView acImgViewInfoProcess;

    @BindView(R.id.lnInfoAddition)
    LinearLayout lnInfoAddition;
    @BindView(R.id.acImgViewInfoAddition)
    AppCompatImageView acImgViewInfoAddition;

    @BindView(R.id.actvGroupComplaint)
    CustomAutoCompleteTextView actvGroupComplaint;

    @OnClick(R.id.lnInfoCustComplain)
    public void lnInfoCustComplainOnClick() {
        expanInfoCustComplain.toggle();

        if (expanInfoCustComplain.isExpanded()) {
            acImgViewInfoCustComplain.setImageResource(R.drawable.ic_keyboard_arrow_right);
        } else {
            acImgViewInfoCustComplain.setImageResource(R.drawable.ic_keyboard_arrow_down);
        }
    }

    @OnClick(R.id.lnInfoComplain)
    public void lnInfoComplainOnClick() {
        expanInfoComplain.toggle();

        if (expanInfoComplain.isExpanded()) {
            acImgViewInfoComplain.setImageResource(R.drawable.ic_keyboard_arrow_right);
        } else {
            acImgViewInfoComplain.setImageResource(R.drawable.ic_keyboard_arrow_down);
        }
    }

    @OnClick(R.id.lnInfoProcess)
    public void lnInfoProcessOnClick() {
        expanInfoProcess.toggle();

        if (expanInfoProcess.isExpanded()) {
            acImgViewInfoProcess.setImageResource(R.drawable.ic_keyboard_arrow_right);
        } else {
            acImgViewInfoProcess.setImageResource(R.drawable.ic_keyboard_arrow_down);
        }
    }

    @OnClick(R.id.lnInfoAddition)
    public void lnInfoAdditionOnClick() {
        expanInfoAddition.toggle();

        if (expanInfoAddition.isExpanded()) {
            acImgViewInfoAddition.setImageResource(R.drawable.ic_keyboard_arrow_right);
        } else {
            acImgViewInfoAddition.setImageResource(R.drawable.ic_keyboard_arrow_down);
        }
    }


    private String payType;
    private String telecomServiceId;
    private ArrayList<AreaBean> arrProvince = new ArrayList<>();
    private String province = "";
    private ArrayList<AreaBean> arrDistrict = new ArrayList<>();
    private String district = "";
    private ArrayList<AreaBean> arrPrecinct = new ArrayList<>();
    private String precinct = "";
    private final HashMap<String, ArrayList<FileObj>> hashmapFileObj = new HashMap<>();
    private ArrayList<FileObj> lstFileObjs = new ArrayList<>();
    private AttackFileAdapter attackFileAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ComplainReceiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComplainReceiveFragment newInstance() {
        ComplainReceiveFragment fragment = new ComplainReceiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        idLayout = R.layout.fragment_complain_receive;
    }

    @Override
    public void onResume() {
        setTitleActionBar(R.string.txt_create_complain);
        super.onResume();
    }

    @Override
    protected void unit(View v) {
        lnComplain.setVisibility(View.GONE);
        resetSpnGroupComplaint();
        resetSpnTheLoai();
        resetSpnType();

        edtDateAppoint.setText(getString(R.string.advisory_not_have_text));

        arrProvince = DataUtils.getProvince(getActivity());
        lvFileAttack.setExpanded(true);

        edtIsdnComplain.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!validateSearchSubscriberComplain()) {
                        return false;
                    }

                    new AsynTaskGetSubscriberFullByIsdn(getActivity(), new OnPostSearchSubscriber(), moveLogInAct)
                            .execute(edtIsdnComplain.getText().toString().trim(), "2");
                    return true;
                }
                return false;
            }
        });

        edtIsdnComplain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                lnComplain.setVisibility(View.GONE);
            }
        });

        actvGroupComplaint.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    actvGroupComplaint.showDropDown();
                }
            }
        });
    }

    private void resetSpnGroupComplaint() {
        List<ComplainDTO> lstComplainDTOs = new ArrayList<>();
        lstComplainDTOs.add(createSelectComplainDTO());

        Utils.setDataSpinner(getContext(), lstComplainDTOs, spnGroupComplaint);
    }

    private void resetSpnTheLoai() {
        List<ComplainDTO> lstComplainDTOs = new ArrayList<>();
        lstComplainDTOs.add(createSelectComplainDTO());

        Utils.setDataSpinner(getContext(), lstComplainDTOs, spnTheLoai);
    }

    private void resetSpnType() {
        List<ComplainDTO> lstComplainDTOs = new ArrayList<>();
        lstComplainDTOs.add(createSelectComplainDTO());

        Utils.setDataSpinner(getContext(), lstComplainDTOs, spnType);
    }

    @OnClick(R.id.imgViewSearch)
    public void searchSubscriberComplain() {
        if (!validateSearchSubscriberComplain()) {
            return;
        }
        new AsynTaskGetSubscriberFullByIsdn(getActivity(), new OnPostSearchSubscriber(), moveLogInAct)
                .execute(edtIsdnComplain.getText().toString().trim(), "2");
    }

    private boolean validateSearchSubscriberComplain() {
        if (CommonActivity.isNullOrEmpty(edtIsdnComplain.getText().toString())) {
            createAlertDialog(getActivity(), getActivity().getString(R.string.txt_isdn_complain_required), getActivity().getString(R.string.app_name)).show();
            return false;
        }
        return true;
    }

    @Override
    protected void setPermission() {

    }

    private class OnPostSearchSubscriber implements OnPostExecuteListener<SubscriberFullDTO> {
        @Override
        public void onPostExecute(SubscriberFullDTO result, String errorCode, String description) {
            lnComplain.setVisibility(View.GONE);
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    if(!CommonActivity.isNullOrEmpty(result.getPayType())
                            && !CommonActivity.isNullOrEmpty(result.getTelecomServiceId())) {
                        lnComplain.setVisibility(View.VISIBLE);

                        new AsynTaskGetProblemPriority(getActivity(), new OnPostGetProblemPriority(), moveLogInAct).execute();
                        Log.d("1", "AsynTaskGetProblemPriority");

//                        new AsynTaskGetProbAcceptType(getActivity(), new OnPostGetProbAccepType(), moveLogInAct).execute();
//                        Log.d("2", "AsynTaskGetProblemPriority");

                        payType = result.getPayType();
                        telecomServiceId = result.getTelecomServiceId() + "";

                        new AsynTaskGetListProblemGroupInProcess(getActivity(),
                                new OnPostGetListProblemGroupInProcess(), moveLogInAct)
                                .execute(result.getPayType(), result.getTelecomServiceId() + "");

                        Log.d("2", "AsynTaskGetListProblemGroupInProcess");
                    } else {
                        createAlertDialog(getActivity(), getActivity().getString(R.string.txt_isdn_info_invalid), getActivity().getString(R.string.app_name)).show();
                    }
                } else {
                    createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }
//
//    private class OnPostGetProbAccepType implements OnPostExecuteListener<List<ComplainDTO>> {
//        @Override
//        public void onPostExecute(List<ComplainDTO> result, String errorCode, String description) {
//            if ("0".equals(errorCode)) {
//                if (!CommonActivity.isNullOrEmptyArray(result)) {
//                    Utils.setDataSpinner(getContext(), result, spnMethodReceive);
//                } else {
//                    createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
//                }
//            } else {
//                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
//            }
//        }
//    }

    private class OnPostGetProblemPriority implements OnPostExecuteListener<List<ComplainDTO>> {
        @Override
        public void onPostExecute(List<ComplainDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    Utils.setDataSpinner(getContext(), result, spnPriorityLevel);
                } else {
                    createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    private class OnPostGetListProblemGroupInProcess implements OnPostExecuteListener<List<ComplainDTO>> {
        @Override
        public void onPostExecute(List<ComplainDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    result.add(0, createSelectComplainDTO());
                    Utils.setDataSpinner(getContext(), result, spnGroupComplaint);

//                    Utils.setDataAutoCompleteTextView(getContext(), result, actvGroupComplaint);
                } else {
                    createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    @OnItemSelected(R.id.spnGroupComplaint)
    public void spnGroupComplaintSelect() {
        ComplainDTO complainDTO = (ComplainDTO) spnGroupComplaint.getSelectedItem();
        if (!"-1".equalsIgnoreCase(complainDTO.getCode())) {
            new AsynTaskGetProblemTypeInProcess(getActivity(), new OnPostGetProblemTypeInProcess(),
                    moveLogInAct).execute(payType, telecomServiceId,
                    String.valueOf(complainDTO.getProbGroupId()));
        }

        resetSpnTheLoai();
        resetSpnType();
    }

    @OnItemSelected(R.id.spnType)
    public void spnTypeSelect() {
        ComplainDTO complainDTO = (ComplainDTO) spnType.getSelectedItem();
        if ("-1".equalsIgnoreCase(complainDTO.getCode())) {
            return;
        }
        GetTimeProcessAsyncTask getTimeProcessAsyncTask = new GetTimeProcessAsyncTask(
                getActivity(), new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    if (!CommonActivity.isNullOrEmptyArray(result)) {
                        edtDateAppoint.setText(StringUtils.getDateFromDateTime(result));
                    } else {
                        createAlertDialog(getActivity(), getActivity().getString(R.string.txt_not_find_date_deadline),
                                getActivity().getString(R.string.app_name)).show();
                    }
                } else {
                    createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
                }
            }
        }, moveLogInAct);

        getTimeProcessAsyncTask.execute(complainDTO.getProbTypeId() + "",
                complainDTO.getProbPriorityId());
    }

    private class OnPostGetProblemTypeInProcess implements OnPostExecuteListener<List<ComplainDTO>> {
        @Override
        public void onPostExecute(List<ComplainDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    result.add(0, createSelectComplainDTO());
                    Utils.setDataSpinner(getContext(), result, spnTheLoai);
                } else {
                    createAlertDialog(getActivity(), getActivity().getString(R.string.txt_theloai_not_found), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    @OnItemSelected(R.id.spnTheLoai)
    public void spnTheLoaiSelect() {
        ComplainDTO complainDTO = (ComplainDTO) spnTheLoai.getSelectedItem();
        if (!"-1".equalsIgnoreCase(complainDTO.getCode())) {
            new AsynTaskGetProblemTypeByParrent(getActivity(),
                    new OnPostGetProblemTypeByParrent(), moveLogInAct).execute(payType,
                    telecomServiceId, String.valueOf(complainDTO.getProbGroupId()));
        }

        resetSpnType();
    }

    private class OnPostGetProblemTypeByParrent implements OnPostExecuteListener<List<ComplainDTO>> {
        @Override
        public void onPostExecute(List<ComplainDTO> result, String errorCode, String description) {
            if ("0".equals(errorCode)) {
                if (!CommonActivity.isNullOrEmptyArray(result)) {
                    result.add(0, createSelectComplainDTO());
                    Utils.setDataSpinner(getContext(), result, spnType);
                } else {
                    createAlertDialog(getActivity(), getActivity().getString(R.string.no_data), getActivity().getString(R.string.app_name)).show();
                }
            } else {
                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    @OnClick(R.id.edtProvince)
    public void edtProvinceOnClick() {
        Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
        intent.putExtra("arrProvincesKey", arrProvince);
        Bundle mBundle = new Bundle();
        mBundle.putString("checkKey", "1");
        intent.putExtras(mBundle);
        startActivityForResult(intent, 106);
    }

    @OnClick(R.id.edtDistrict)
    public void edtDistrictOnClick() {
        Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
        intent.putExtra("arrDistrictKey", arrDistrict);
        Bundle mBundle = new Bundle();
        mBundle.putString("checkKey", "2");
        intent.putExtras(mBundle);
        startActivityForResult(intent, 116);
    }

    @OnClick(R.id.edtPrecint)
    public void edtPrecintOnClick() {
        Intent intent = new Intent(getActivity(), FragmentSearchLocation.class);
        intent.putExtra("arrPrecinctKey", arrPrecinct);
        Bundle mBundle = new Bundle();
        mBundle.putString("checkKey", "3");
        intent.putExtras(mBundle);
        startActivityForResult(intent, 108);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 106:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("provinceKey");

                        province = areaBean.getProvince();
                        initDistrict(province);
                        edtProvince.setText(areaBean.getNameProvince());
                        edtDistrict.setText("");
                        edtPrecint.setText("");
                    }
                    break;
                case 108:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("precinctKey");
                        precinct = areaBean.getPrecinct();
                        edtPrecint.setText(areaBean.getNamePrecint());
                    }
                    break;
                case 116:
                    if (data != null) {
                        AreaBean areaBean = (AreaBean) data.getExtras().getSerializable("districtKey");
                        district = areaBean.getDistrict();
                        initPrecinct(province, district);
                        edtDistrict.setText(areaBean.getNameDistrict());
                        edtPrecint.setText("");
                    }
                    break;
                case CHANNEL_UPDATE_IMAGE.PICK_IMAGE:
                    Log.d(Constant.TAG, "CHANNEL_UPDATE_IMAGE.PICK_IMAGE");

                    Parcelable[] parcelableUris = data
                            .getParcelableArrayExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);

                    if (parcelableUris == null) {
                        return;
                    }
                    // Java doesn't allow array casting, this is a little hack
                    Uri[] uris = new Uri[parcelableUris.length];
                    System.arraycopy(parcelableUris, 0, uris, 0,
                            parcelableUris.length);

                    int imageId = data.getExtras().getInt("imageId", -1);
                    FileObj fileObj = getFileObj(imageId);
                    fileObj.setPath(uris[0].toString());
                    File file = new File(uris[0].getPath());
                    fileObj.setName(file.getName());

                    Log.d(Constant.TAG,
                            "ComplainReceiveFragment onActivityResult() imageId: "
                                    + imageId);

                    if (uris != null && uris.length > 0 && imageId >= 0) {
                        AttackFileAdapter.ViewHolder holder = null;
                        for (int i = 0; i < lvFileAttack.getChildCount(); i++) {
                            View rowView = lvFileAttack.getChildAt(i);
                            AttackFileAdapter.ViewHolder h = (AttackFileAdapter.ViewHolder) rowView
                                    .getTag();
                            if (h != null) {
                                int id = h.imgUploadImage.getId();
                                if (imageId == id) {
                                    holder = h;
                                    break;
                                }
                            }
                        }

                        if (holder != null) {
                            Picasso.with(getActivity())
                                    .load(new File(uris[0].toString()))
                                    .centerCrop().resize(100, 100)
                                    .into(holder.imgUploadImage);


                            ArrayList<FileObj> fileObjs = new ArrayList<>();
                            for (int i = 0; i < uris.length; i++) {
                                File uriFile = new File(uris[i].getPath());
                                String fileNameServer = uriFile.getName() + "-" + (i + 1)
                                        + ".jpg";
                                FileObj obj = new FileObj(fileNameServer, uriFile,
                                        uris[i].getPath(), fileNameServer);
                                obj.setId(imageId);
                                fileObjs.add(obj);
                            }
                            hashmapFileObj.put(String.valueOf(imageId), fileObjs);

                            attackFileAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(Constant.TAG,
                                    "FragmentModifyProfile onActivityResult() holder NULL");
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    }

    int id = 0;

    @OnClick(R.id.lnAddFileAttack)
    public void lnAddFileAttackOnClick() {
        FileObj fileObj = new FileObj();
        fileObj.setId(++id);
        fileObj.setName(getString(R.string.txt_select_file_attack));

        lstFileObjs.add(fileObj);
        if (CommonActivity.isNullOrEmpty(attackFileAdapter)) {
            attackFileAdapter = new AttackFileAdapter(getContext(), lstFileObjs, imageListenner, imageDeleteListener);
            lvFileAttack.setAdapter(attackFileAdapter);
        } else {
            attackFileAdapter.notifyDataSetChanged();
        }

        //them moi khoi tao adapter
        ImagePreviewActivity.pickImage(getActivity(), hashmapFileObj,
                fileObj.getId());
    }

    private FileObj getFileObj(int imageId) {
        for (FileObj fileObj : lstFileObjs) {
            if (imageId == fileObj.getId()) {
                return fileObj;
            }
        }
        return null;
    }

    protected final View.OnClickListener moveLogInAct = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginDialog dialog = new LoginDialog(getActivity(), permission);
            dialog.show();

        }
    };

    private ComplainDTO createSelectComplainDTO() {
        ComplainDTO complainDTO = new ComplainDTO();
        complainDTO.setCode("-1");
        complainDTO.setName("Chọn");
        return complainDTO;
    }

    private void initDistrict(String province) {
        try {
            GetAreaDal dal = new GetAreaDal(getActivity());
            dal.open();
            arrDistrict = dal.getLstDistrict(province);
            dal.close();
        } catch (Exception ex) {
            Log.e("initDistrict", ex.toString());
        }
    }

    private void initPrecinct(String province, String district) {
        try {
            GetAreaDal dal = new GetAreaDal(getActivity());
            dal.open();
            arrPrecinct = dal.getLstPrecinct(province, district);
            dal.close();
        } catch (Exception ex) {
            Log.e("initPrecinct", ex.toString());
        }
    }


    private final View.OnClickListener imageListenner = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(Constant.TAG, "view.getId() : " + view.getId());
            ImagePreviewActivity.pickImage(getActivity(), hashmapFileObj,
                    view.getId());
        }
    };

    private final View.OnClickListener imageDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Log.d(Constant.TAG, "view.getId() : " + view.getId());
            if (hashmapFileObj != null) {
                if (hashmapFileObj.containsKey(String.valueOf(view.getId()))) {
                    hashmapFileObj.remove(String.valueOf(view.getId()));
                }
            }

            if (lstFileObjs != null) {
                lstFileObjs.remove(Integer.valueOf(view.getTag().toString()).intValue());
                attackFileAdapter.notifyDataSetChanged();
            }
        }
    };

    @OnClick(R.id.btnComplain)
    public void btnComplainOnClick() {
        if (!validateCreateProblemForMBCCS()) {
            return;
        }

        CommonActivity.createDialog(getActivity(),
                getString(R.string.txt_create_problem_confirm),
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                null,
                createProblemOnClick).show();

    }

    View.OnClickListener createProblemOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            new AsynTaskCreateProblemForMBCCS(getActivity(), new OnPostCreateProblemForMBCCS(), moveLogInAct)
                    .execute(createComplainDTO(), hashmapFileObj);
        }
    };

    private boolean validateCreateProblemForMBCCS() {
        if (!StringUtils.validateString(getContext(),
                edtCustComplain.getText().toString(),
                R.string.txt_cust_complain_required,
                R.string.txt_cust_complain_special)) {
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edtIsdnContact.getText().toString().trim())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.must_input_isdn),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

//        if (!CommonActivity.isNullOrEmpty(edtEmail.getText().toString())) {
//            if (!StringUtils.validateEmail(edtEmail.getText().toString())) {
//                createAlertDialog(
//                        getActivity(),
//                        getString(R.string.email_invalid_format),
//                        getString(R.string.app_name))
//                        .show();
//                return false;
//            }
//        }

        if (!CommonActivity.isNullOrEmpty(edtIsdnContact.getText().toString())) {
            String isdnComplain = edtIsdnContact.getText().toString().trim();
            if (isdnComplain.length() < 9 || isdnComplain.length() > 12) {
                createAlertDialog(
                        getActivity(),
                        getString(R.string.txt_isdn_contatct_invalid),
                        getString(R.string.app_name))
                        .show();
                return false;
            }
        }

        if (!CommonActivity.isNullOrEmpty(edtAddress.getText().toString())) {
            if (StringUtils.CheckCharSpecical(edtAddress.getText().toString(), "@*!@#$%^<>'\"")) {
                CommonActivity
                        .createAlertDialog(
                                getActivity(),
                                getString(R.string.txt_address_special),
                                getString(R.string.app_name))
                        .show();
                return false;
            }
        }

        ComplainDTO complainDTO = (ComplainDTO) spnGroupComplaint.getSelectedItem();
        if ("-1".equals(complainDTO.getCode())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.txt_group_complain_required),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        complainDTO = (ComplainDTO) spnTheLoai.getSelectedItem();
        if ("-1".equals(complainDTO.getCode())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.txt_the_loai_required),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        complainDTO = (ComplainDTO) spnType.getSelectedItem();
        if ("-1".equals(complainDTO.getCode())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.txt_complain_type_required),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edtProvince.getText().toString())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.provinceEmpty),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edtDistrict.getText().toString())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.districtEmpty),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edtPrecint.getText().toString())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.precinctEmpty),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        if (CommonActivity.isNullOrEmpty(edtProblemContent.getText().toString())) {
            createAlertDialog(
                    getActivity(),
                    getString(R.string.txt_problem_content_required),
                    getString(R.string.app_name))
                    .show();
            return false;
        }

        if (StringUtils.CheckCharSpecical(edtProblemContent.getText().toString(), "*!@#$%^<>'\"")) {
            CommonActivity
                    .createAlertDialog(
                            getActivity(),
                            getString(R.string.txt_problem_content_special),
                            getString(R.string.app_name))
                    .show();
            return false;
        }

        if (getString(R.string.advisory_not_have_text).equals(edtDateAppoint.getText().toString())) {
            CommonActivity
                    .createAlertDialog(
                            getActivity(),
                            getString(R.string.txt_not_find_date_deadline_confirm),
                            getString(R.string.app_name))
                    .show();
            return false;
        }


        return true;
    }

    private ComplainDTO createComplainDTO() {
        ComplainDTO complainDTO = new ComplainDTO();
        complainDTO.setIsdn(StringUtils.isViettelMobile(edtIsdnComplain.getText().toString().trim())
                ? StringUtils.formatIsdn(edtIsdnComplain.getText().toString().trim())
                : edtIsdnComplain.getText().toString().trim());

//        complainDTO.setProbAcceptTypeId(((ComplainDTO) spnMethodReceive.getSelectedItem()).getProbAcceptTypeId());
        complainDTO.setProbPriorityId(((ComplainDTO) spnPriorityLevel.getSelectedItem()).getProbPriorityId());
        complainDTO.setParentGroupId(Long.valueOf(((ComplainDTO) spnGroupComplaint.getSelectedItem()).getProbGroupId()));
        complainDTO.setProbGroupId(Long.valueOf(((ComplainDTO) spnTheLoai.getSelectedItem()).getProbGroupId()));
        complainDTO.setProbTypeId(Long.valueOf(((ComplainDTO) spnType.getSelectedItem()).getProbTypeId()));

        complainDTO.setProblemContent(edtProblemContent.getText().toString().trim());
        complainDTO.setComplainerAddress(edtAddress.getText().toString().trim());

        complainDTO.setComplainerPhone(StringUtils.isViettelMobile(edtIsdnContact.getText().toString().trim())
                ? StringUtils.formatIsdn(edtIsdnContact.getText().toString().trim())
                : edtIsdnContact.getText().toString().trim());
        complainDTO.setComplainerEmail(edtEmail.getText().toString().trim());
        complainDTO.setComplainerName(edtCustComplain.getText().toString().trim());
        complainDTO.setProvince(province);
        complainDTO.setDistrict(district);
        complainDTO.setPrecinct(precinct);
        complainDTO.setCustAppointDate(edtDateAppoint.getText().toString().trim());
        complainDTO.setCustomerText(edtNote.getText().toString().trim());
        return complainDTO;
    }

    private class OnPostCreateProblemForMBCCS implements OnPostExecuteListener<String> {
        @Override
        public void onPostExecute(String result, String errorCode, String description) {
            Log.d("CreateProblemForMBCCS","errorCode = " + errorCode + ", description = " + description);
            if ("0".equals(errorCode)) {
                createAlertDialog(getActivity(), getActivity().getString(R.string.txt_create_complain_success), getActivity().getString(R.string.app_name)).show();
                lnComplain.setVisibility(View.GONE);
                resetComplainForm();
            } else {
                createAlertDialog(getActivity(), description, getActivity().getString(R.string.app_name)).show();
            }
        }
    }

    private void resetComplainForm(){
        edtCustComplain.setText("");
        edtIdNo.setText("");
        edtIsdnComplain.setText("");
        edtIsdnContact.setText("");
        resetSpnType();
        resetSpnTheLoai();
        resetSpnGroupComplaint();
        edtProvince.setText("");
        edtDistrict.setText("");
        edtPrecint.setText("");
        edtProblemContent.setText("");
        edtEmail.setText("");
        edtAddress.setText("");

        lstFileObjs.clear();
        if(attackFileAdapter != null){
            attackFileAdapter.notifyDataSetChanged();
        }

        edtNote.setText("");
        edtDateAppoint.setText(getString(R.string.advisory_not_have_text));
    }
}
