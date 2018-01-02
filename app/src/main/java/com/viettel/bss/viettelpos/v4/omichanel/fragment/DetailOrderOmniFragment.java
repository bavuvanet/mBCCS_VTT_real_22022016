package com.viettel.bss.viettelpos.v4.omichanel.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.AsynGetCustomerByCustId;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.FragmentCommon;
import com.viettel.bss.viettelpos.v4.adapter.OnItemClickListener;
import com.viettel.bss.viettelpos.v4.adapter.RecyclerItemClickListener;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.RequestCodeFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.CreateConnectMobileOmiActivity;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterInfoFragment;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.CustIdentityDTOAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.adapter.VasInfoAdapter;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.AsyncTaskGetImageOrder;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.AsyncTaskLoadImage;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.CheckInAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ClaimAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ClaimForReceptionistAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ProcessBitmapToByteArrAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.SearchCustidentityAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.TransferOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.UnclaimForReceptionistAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.UpdateOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Address;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Customer;
import com.viettel.bss.viettelpos.v4.omichanel.dao.IsdnPledgeInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Order;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderState;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PayInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProductInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VStockNumberOmniDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VasInfo;
import com.viettel.bss.viettelpos.v4.omichanel.dialog.ClaimDialog;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by thuandq on 04/09/2017.
 */

public class DetailOrderOmniFragment extends FragmentCommon {

    public static final int RESULT_OK = 0;

    // thong tin khach hang
    @BindView(R.id.imgEditCusInfo)
    ImageView imgEditCusInfo;
    @BindView(R.id.llCusOld)
    LinearLayout llCusOld;
    @BindView(R.id.tvOldInfo)
    TextView tvOldInfo;
    @BindView(R.id.tvAlertCusInvalidShop)
    TextView tvAlertCusInvalidShop;
    @BindView(R.id.tvCustName)
    TextView tvCustName;
    @BindView(R.id.tvBirthDay)
    TextView tvBirthDay;
    @BindView(R.id.tvCusIdInfo)
    TextView tvCusIdInfo;
    @BindView(R.id.tvRecipientPhone)
    TextView tvRecipientPhone;
    @BindView(R.id.tvCusAddress)
    TextView tvCusAddress;
    @BindView(R.id.imgEditCMT)
    ImageView imgEditCMT;
    @BindView(R.id.imgCMTBefore)
    ImageView imgCMTBefore;
    @BindView(R.id.imgCMTAfter)
    ImageView imgCMTAfter;

    // thong tin dich vu
    @BindView(R.id.llNumberInfoDetail)
    LinearLayout llNumberInfoDetail;
    @BindView(R.id.tvIsdn)
    TextView tvIsdn;
    @BindView(R.id.imgEditNumber)
    ImageView imgEditNumber;
    @BindView(R.id.tvOderTypeDesc)
    TextView tvOderTypeDesc;
    @BindView(R.id.llIsdnPrice)
    LinearLayout llIsdnPrice;
    @BindView(R.id.tvIsdnPrice)
    TextView tvIsdnPrice;
    @BindView(R.id.llPostpaidInfo)
    LinearLayout llPostpaidInfo;
    @BindView(R.id.tvIsdnAmount)
    TextView tvIsdnAmount;
    @BindView(R.id.tvIsdnTime)
    TextView tvIsdnTime;

    @BindView(R.id.tvBundleName)
    TextView tvBundleName;
    @BindView(R.id.llBundlePacketDetail)
    LinearLayout llBundlePacketDetail;
    @BindView(R.id.imgEditBundle)
    ImageView imgEditBundle;
    @BindView(R.id.tvBundleDesc)
    TextView tvBundleDesc;
    @BindView(R.id.llBundleConvertFee)
    LinearLayout llBundleConvertFee;
    @BindView(R.id.llBundlePrice)
    LinearLayout llBundlePrice;
    @BindView(R.id.tvBundleConvertFee)
    TextView tvBundleConvertFee;
    @BindView(R.id.tvBundlePrice)
    TextView tvBundlePrice;
    @BindView(R.id.imgEditVasPlus)
    ImageView imgEditVasPlus;
    @BindView(R.id.recVasInfo)
    RecyclerView recVasInfo;
    @BindView(R.id.llChargeCardInfo)
    LinearLayout llChargeCardInfo;
    @BindView(R.id.tvChargeCardAmound)
    TextView tvChargeCardAmound;
    @BindView(R.id.imgChargeCard)
    ImageView imgChargeCard;
    @BindView(R.id.llSignature)
    LinearLayout llSignature;
    @BindView(R.id.frlSignatureLayout)
    FrameLayout frlSignatureLayout;
    @BindView(R.id.imgSignature)
    ImageView imageSignature;
    @BindView(R.id.imgShowSignature)
    ImageView imgShowSignature;
    @BindView(R.id.cbConfirmAccept)
    CheckBox cbConfirmAccept;
    @BindView(R.id.tvSignature)
    TextView tvSignature;
    @BindView(R.id.tvTransactionPlace)
    TextView tvTransactionPlace;
    @BindView(R.id.llFeeTrans)
    LinearLayout llFeeTrans;
    @BindView(R.id.tvFeeTrans)
    TextView tvFeeTrans;
    @BindView(R.id.tvAddressTrans)
    TextView tvAddressTrans;
    @BindView(R.id.tvTotalFee)
    TextView tvTotalFee;
    @BindView(R.id.tvPayType)
    TextView tvPayType;
    @BindView(R.id.tvPayState)
    TextView tvPayState;
    @BindView(R.id.llCondition)
    LinearLayout llCondition;
    @BindView(R.id.tvConditionerInfo)
    TextView tvConditionerInfo;
    @BindView(R.id.btnAction)
    Button btnAction;
    private Activity activity;

    private Boolean isUpdate;
    private boolean isSignatured;
    private boolean isExitsCusOld;
    private ConnectionOrder connectionOrder;
    private Bitmap bmBefore;
    private Bitmap bmAfter;

    private Customer customer;
    private CustomerDTO customerDTO;
    private CustIdentityDTO custIdentityDTO;
    private ArrayList<CustIdentityDTO> lstcustIdentityDTOs;

    private String CHECK_REGISTER_INFO_OMNICHANNEL;
    private int orderActionState;
    private int orderActionStateBackup;
    private Staff staff;
    private ClaimDialog claimDialog;
    private ArrayList<Staff> arrStaff;
    private boolean forStaffOwner = true;
    private ProgressDialog progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.idLayout = R.layout.detail_order_omni_fragment;

        this.activity = getActivity();
        this.isExitsCusOld = false;
        this.isSignatured = false;
        this.isUpdate = false;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (connectionOrder != null) {
            setTitleActionBar(activity.getString(R.string.detail_request) + " " + connectionOrder.getProcessInstanceId());
        } else {
            setTitleActionBar(activity.getString(R.string.detail_request));
        }
    }

    @Override
    protected void unit(View v) {

        this.staff = StaffBusiness.getStaffByStaffCode(getContext(), Session.userName);
        Bundle bundle = getArguments();
        this.connectionOrder = (ConnectionOrder) bundle.getSerializable("connectionOrder");
        this.arrStaff = (ArrayList<Staff>) bundle.getSerializable("arrStaff");
        this.customer = connectionOrder.getCustomer();
        this.orderActionState = bundle.getInt("orderActionState", 0);
        this.orderActionStateBackup = orderActionState;

        this.recVasInfo.setHasFixedSize(true);
        this.recVasInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.recVasInfo.setNestedScrollingEnabled(false);

        cbConfirmAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbConfirmAccept.isChecked()) {
                    llSignature.setVisibility(View.VISIBLE);
                } else {
                    llSignature.setVisibility(View.GONE);
                }
            }
        });

        imgCMTBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogImageView(bmBefore);
            }
        });
        imgCMTAfter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogImageView(bmAfter);
            }
        });

        imgEditVasPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditVasPlusFragment editVasPlusFragment =
                        new EditVasPlusFragment(connectionOrder.getProductInfo());
                editVasPlusFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_VAS_PLUS_CODE);
                ReplaceFragment.replaceFragment(activity, editVasPlusFragment, false);
            }
        });

        imgEditBundle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBundleFragment editBundleFragment = new EditBundleFragment(connectionOrder);
                editBundleFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_BUNDLE_CODE);
                ReplaceFragment.replaceFragment(activity, editBundleFragment, false);
            }
        });

        imgEditNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNumberFragment editNumberFragment;
                if (Constant.ORD_TYPE_CONNECT_PREPAID.equals(connectionOrder.getOrderType())) {
                    editNumberFragment = new EditNumberFragment(true);
                } else {
                    editNumberFragment = new EditNumberFragment(false);
                }
                editNumberFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_NUMBER_CODE);
                ReplaceFragment.replaceFragment(activity, editNumberFragment, false);
            }
        });

        tvOldInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogOldCusInfo();
            }
        });

        imgEditCusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCustInfoOmniFragment custInfoOmichanelFragment = new EditCustInfoOmniFragment();
                custInfoOmichanelFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_CUSTUMER_INFO_CODE);
                Bundle b = new Bundle();
                b.putSerializable("connectionOrder", connectionOrder);
                custInfoOmichanelFragment.setArguments(b);
                ReplaceFragment.replaceFragment(activity, custInfoOmichanelFragment, false);
            }
        });

        imgChargeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
                    connectionOrder.setProductInfo(new ProductInfo());
                }
                EditCardFragment editCardFragment = new EditCardFragment(
                        connectionOrder.getProductInfo(), connectionOrder.getChargeCardAmound());
                editCardFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_CARD_AMOUNT_CODE);
                ReplaceFragment.replaceFragment(activity, editCardFragment, false);
            }
        });
        imageSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignatureOmichanelFragment signatureOmichanelFragment = new SignatureOmichanelFragment();
                signatureOmichanelFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_SIGNATURE_CODE);
                ReplaceFragment.replaceFragment(activity, signatureOmichanelFragment, false);
            }
        });
        tvSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignatureOmichanelFragment signatureOmichanelFragment = new SignatureOmichanelFragment();
                signatureOmichanelFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_SIGNATURE_CODE);
                ReplaceFragment.replaceFragment(activity, signatureOmichanelFragment, false);
            }
        });

        imgEditCMT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditImageIdFragment editImageIdFragment = new EditImageIdFragment();
                final Bundle bundle = new Bundle();
                bundle.putSerializable("connectionOrder", connectionOrder);
                new ProcessBitmapToByteArrAsyncTask(getActivity(), new OnPostExecuteListener<byte[]>() {
                    @Override
                    public void onPostExecute(byte[] result, String errorCode, String description) {
                         final byte[] byteBefore = result;
                        new ProcessBitmapToByteArrAsyncTask(getActivity(), new OnPostExecuteListener<byte[]>() {
                            @Override
                            public void onPostExecute(byte[] result, String errorCode, String description) {
                                byte[] byteAfter = result;
                                if (!CommonActivity.isNullOrEmpty(bmBefore)) {
                                    bundle.putByteArray("bitmapBefore", byteBefore);
                                }
                                if (!CommonActivity.isNullOrEmpty(bmAfter)) {
                                    bundle.putSerializable("bitmapAfter", byteAfter);
                                }
                                editImageIdFragment.setArguments(bundle);
                                editImageIdFragment.setTargetFragment(DetailOrderOmniFragment.this,
                                        RequestCodeFragment.EDIT_CMT_IMAGE_CODE);
                                ReplaceFragment.replaceFragment(activity, editImageIdFragment, false);
                            }
                        },moveLogInAct).execute(bmAfter);
                    }
                },moveLogInAct).execute(bmBefore);
            }
        });

        frlSignatureLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignatureOmichanelFragment signatureOmichanelFragment = new SignatureOmichanelFragment();
                signatureOmichanelFragment.setTargetFragment(DetailOrderOmniFragment.this,
                        RequestCodeFragment.EDIT_SIGNATURE_CODE);
                ReplaceFragment.replaceFragment(activity, signatureOmichanelFragment, false);
            }
        });

        tvConditionerInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent target = new Intent(Intent.ACTION_VIEW);
                target.setDataAndType(Uri.parse("https://media.vietteltelecom.vn/upload/13627/fck/Dieu%20khoan%20chung_tra%20truoc(1).pdf"), "application/pdf");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent = Intent.createChooser(target, "Open File");
                startActivity(intent);
            }
        });

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getString(R.string.checkin_order).equals(btnAction.getText().toString())) {
                    CommonActivity.createDialog(getActivity(),
                            getActivity().getResources().getString(R.string.omni_confirm_checkin),
                            getActivity().getResources().getString(R.string.app_name),
                            getActivity().getResources().getString(R.string.cancel),
                            getActivity().getResources().getString(R.string.buttonOk),
                            null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doCheckInOrder();
                                }
                            })
                            .show();
                } else if (getString(R.string.accept_order).equals(btnAction.getText().toString())) {
                    CommonActivity.createDialog(getActivity(),
                            getActivity().getResources().getString(R.string.omni_confirm_claim_order),
                            getActivity().getResources().getString(R.string.app_name),
                            getActivity().getResources().getString(R.string.cancel),
                            getActivity().getResources().getString(R.string.buttonOk),
                            null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doClaimOrder();
                                }
                            })
                            .show();
                } else if (getString(R.string.order_re_claim).equals(btnAction.getText().toString())) {
                    claimDialog = new ClaimDialog(activity, arrStaff,forStaffOwner, getFragmentManager(), connectionOrder);
                    claimDialog.show();

                } else if (getString(R.string.order_continue).equals(btnAction.getText().toString())) {
                    doContinueOrderByState();
                }
            }
        });

        // check shop
        checkShopId();
        hideOrderEditUI();
        fillData();

        switch (orderActionState) {
            case OrderState.ORD_RE_CLAIM_ACT: // giao lai
                btnAction.setText(getString(R.string.order_re_claim));
                frlSignatureLayout.setEnabled(false);
                tvSignature.setEnabled(false);
                cbConfirmAccept.setEnabled(false);
                break;
            case OrderState.ORD_CHECKIN_ACT: // Le tan checkin
                btnAction.setText(getString(R.string.checkin_order));
                break;
            case OrderState.ORD_CLAIM_ACT: // CTV nhan viec
                btnAction.setText(getString(R.string.accept_order));
                break;
            case OrderState.ORD_TRANSFER_ACT: // Le tan chuyen viec
            case OrderState.ORD_CLAIM_RECEIP_ACT: // Le tan nhan viec
            case OrderState.ORD_CONNECT_ACT: // thuc hien nghiep vu
                btnAction.setText(getString(R.string.order_continue));
                showOrderEditUI();
                break;
            default:
                break;
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder)
                && Constant.ORD_TYPE_REGISTER_PREPAID.equals(connectionOrder.getOrderType())) {
            CHECK_REGISTER_INFO_OMNICHANNEL = "CHECK_REGISTER_INFO_OMNICHANNEL";
        }
    }

    // xu ly khi bam nut tiep tuc
    private void doContinueOrderByState() {
        switch (orderActionState) {
            case OrderState.ORD_SAVE_ACT:
                doUpdateOrder();
                break;
            case OrderState.ORD_TRANSFER_ACT:
                doTransferOrder();
                break;
            case OrderState.ORD_CLAIM_RECEIP_ACT:
                doConfirmClaimReciepOrder();
                break;
            case OrderState.ORD_CONNECT_ACT:
                switchConnectOmni();
                break;
            default:
                break;
        }
    }

    // thuc hien cac chuc nang tuong ung
    private void switchConnectOmni() {

        if (isExitsCusOld && CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getCustId())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.order_confirm_choose_cus_old),
                    getString(R.string.app_name)).show();
            return;
        }

        if (!validateBeforeConnect()) {
            return;
        }

        switch (connectionOrder.getOrderType()) {
            case Constant.ORD_TYPE_REGISTER_PREPAID:
                registerInfoOmni();
                break;
            case Constant.ORD_TYPE_CONNECT_PREPAID:
                connectMobilePrepaidOmni();
                break;
            case Constant.ORD_TYPE_CONNECT_POSPAID:
                connectMobilePospaidOmni();
                break;
            case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                changeToPospaidOmni();
                break;
            default:
                break;
        }
    }

    private void checkShopId() {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        this.tvAlertCusInvalidShop.startAnimation(anim);
        this.tvAlertCusInvalidShop.setVisibility(View.GONE);

        if ("SHOP".equals(connectionOrder.getTransactionPlace())) {
            if (CommonActivity.isNullOrEmpty(connectionOrder.getShopId())) {
                return;
            }
            if (!staff.getShopId().equals(connectionOrder.getShopId())) {
                tvAlertCusInvalidShop.setVisibility(View.VISIBLE);
            } else {
                tvAlertCusInvalidShop.setVisibility(View.GONE);
            }
        }
    }

    private boolean validateBeforeConnect() {

        if (CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())
            || CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.validate_cus_info_idno),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (!connectionOrder.validateProfileRecordByCode(Constant.PROFILE.CMTNDMT)) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.order_validate_cmt_mt),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (!connectionOrder.validateProfileRecordByCode(Constant.PROFILE.CMTNDMS)) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.order_validate_cmt_ms),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (!cbConfirmAccept.isChecked()) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.confirm_contract_msg),
                    getString(R.string.app_name)).show();
            return false;
        }

        if (!connectionOrder.validateProfileRecordByCode(Constant.PROFILE.CHUKY)) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.order_validate_chuky),
                    getString(R.string.app_name)).show();
            return false;
        }

        return true;
    }

    private void doClaimOrder() {
        String taskId = connectionOrder.getTaskId();
        ClaimAsyncTask claimAsyncTask = new ClaimAsyncTask(getActivity(),
                new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.toast(getActivity(), R.string.omni_claim_success);
                            orderActionState = OrderState.ORD_CONNECT_ACT;
                            orderActionStateBackup = orderActionState;
                            btnAction.setText(getString(R.string.order_continue));
                            showOrderEditUI();
                        } else {
                            CommonActivity.createAlertDialog(getActivity(),
                                    CommonActivity.isNullOrEmpty(description) ?
                                            getActivity().getString(R.string.checkdes) : description,
                                    getActivity().getString(R.string.app_name)).show();
                        }
                    }
                }, moveLogInAct);
        claimAsyncTask.execute(taskId, null, "false");
    }

    private void doTransferOrder() {

        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TransferOrderAsyncTask transferOrderAsyncTask = new TransferOrderAsyncTask(activity,
                        new OnPostExecuteListener<String>() {
                            @Override
                            public void onPostExecute(String result, String errorCode, String description) {
                                if ("0".equals(errorCode)) {
                                    CommonActivity.toast(activity, R.string.order_transfer_success);
                                    orderActionState = OrderState.ORD_CLAIM_RECEIP_ACT;
                                    orderActionStateBackup = orderActionState;
                                    doClaimForReceptionist();
                                } else {
                                    CommonActivity.createAlertDialog(activity,
                                            CommonActivity.isNullOrEmpty(description) ?
                                                    activity.getString(R.string.checkdes) : description,
                                            activity.getString(R.string.app_name)).show();
                                }
                            }
                        }, null);
                transferOrderAsyncTask.execute(connectionOrder.getProcessInstanceId(),
                        CommonActivity.getCurrentStaffId(activity) + "");
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.order_transfer_confirm, connectionOrder.getShopName()),
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                null, okListener).show();
    }

    // confirn le tan nhan viec
    private void doConfirmClaimReciepOrder() {

        if ("HOME".equals(connectionOrder.getTransactionPlace())) {
            CommonActivity.createAlertDialog(activity,
                    activity.getString(R.string.order_is_home_meg),
                    activity.getString(R.string.app_name)).show();
            return;
        }

        String actionNextString = "";
        switch (connectionOrder.getOrderType()) {
            case Constant.ORD_TYPE_CONNECT_PREPAID:
                actionNextString = getString(R.string.order_action_next_conect);
                break;
            case Constant.ORD_TYPE_REGISTER_PREPAID:
                actionNextString = getString(R.string.order_action_next_register);
                break;
            case Constant.ORD_TYPE_CONNECT_POSPAID:
                actionNextString = getString(R.string.order_action_next_conect);
                break;
            case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                actionNextString = getString(R.string.order_action_next_change);
                break;
            default:
                break;
        }

        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                doClaimForReceptionist();
            }
        };

        final View.OnClickListener cancelListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                getTargetFragment().onActivityResult(getTargetRequestCode(),
                        Activity.RESULT_OK, new Intent());
                getActivity().onBackPressed();
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.order_confirm_process_order_continue, actionNextString),
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                cancelListener, okListener).show();
    }

    private void doClaimForReceptionist() {
        ClaimForReceptionistAsyncTask claimForReceptionistAsyncTask =
                new ClaimForReceptionistAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.toast(getActivity(), R.string.order_claim_success);
                            connectionOrder.setTaskId(result);
                            orderActionState = OrderState.ORD_CONNECT_ACT;
                            orderActionStateBackup = orderActionState;
                            btnAction.setText(getString(R.string.order_continue));
                            showOrderEditUI();
                            // sau khi nhan viec xong thuc hien chuc nang tuong ung luon
                            switchConnectOmni();
                        } else {
                            if (CommonActivity.isNullOrEmpty(description)) {
                                CommonActivity.createAlertDialog(getActivity(),
                                                getActivity().getString(R.string.checkdes),
                                        getActivity().getString(R.string.app_name)).show();
                            } else {
                                String[] arrayString = description.split(" ");
                                if (arrayString.length > 0
                                        && StringUtils.isDigit(arrayString[arrayString.length - 1])) {
                                    doUnclaimAndClaimNewTask(arrayString[arrayString.length - 1]);
                                } else {
                                    CommonActivity.createAlertDialog(getActivity(),
                                            description,
                                            getActivity().getString(R.string.app_name)).show();
                                }
                            }
                        }
                    }
                }, moveLogInAct);
        claimForReceptionistAsyncTask.execute(connectionOrder.getProcessInstanceId());
    }

    private void doUnclaimAndClaimNewTask(final String processIdOld) {

        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                UnclaimForReceptionistAsyncTask unclaimForReceptionistAsyncTask =
                        new UnclaimForReceptionistAsyncTask(activity, new OnPostExecuteListener<String>() {
                            @Override
                            public void onPostExecute(String result, String errorCode, String description) {
                                if ("0".equals(errorCode)) {
                                    CommonActivity.toast(activity, getString(R.string.order_unclaim_success));
                                    doClaimForReceptionist();
                                } else {
                                    if (CommonActivity.isNullOrEmpty(description)) {
                                        CommonActivity.createAlertDialog(getActivity(),
                                                getActivity().getString(R.string.checkdes),
                                                getActivity().getString(R.string.app_name)).show();
                                    } else {
                                        CommonActivity.createAlertDialog(getActivity(),
                                                description,
                                                getActivity().getString(R.string.app_name)).show();
                                    }
                                }
                            }
                        }, moveLogInAct);
                unclaimForReceptionistAsyncTask.execute(processIdOld);
            }
        };

        CommonActivity.createDialog(getActivity(),
                getString(R.string.order_confirm_unclaim, processIdOld,
                        connectionOrder.getProcessInstanceId()),
                getString(R.string.app_name),
                getString(R.string.cancel),
                getString(R.string.ok),
                null, okListener).show();
    }

    private void doCheckInOrder() {
        String processId = connectionOrder.getProcessInstanceId();
        CheckInAsyncTask checkInAsyncTask = new CheckInAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    orderActionState = OrderState.ORD_CLAIM_RECEIP_ACT;
                    orderActionStateBackup = orderActionState;
                    btnAction.setText(getString(R.string.order_continue));
                    showOrderEditUI();
                    CommonActivity.toast(getActivity(), R.string.omni_checkin_success);
                } else {
                    CommonActivity.createAlertDialog(getActivity(), CommonActivity.isNullOrEmpty(description) ?
                                    getActivity().getString(R.string.checkdes) : description,
                            getActivity().getString(R.string.app_name)).show();
                }
            }
        }, moveLogInAct);
        checkInAsyncTask.execute(processId);
    }

    private void doUpdateOrder() {
        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                UpdateOrderAsyncTask updateOrderAsyncTask = new UpdateOrderAsyncTask(getActivity(), new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            isUpdate = false;
                            orderActionState = orderActionStateBackup;
                            CommonActivity.toast(getActivity(), R.string.omni_save_order_success);
                        } else {
                            CommonActivity.createAlertDialog(getActivity(),
                                    CommonActivity.isNullOrEmpty(description) ? getActivity().getString(R.string.checkdes) : description,
                                    getActivity().getString(R.string.app_name)).show();
                        }
                    }
                }, moveLogInAct, connectionOrder);
                updateOrderAsyncTask.execute();
            }
        };

        CommonActivity.createDialog(getActivity(),
                getActivity().getResources().getString(R.string.omni_confirm_save_order),
                getActivity().getResources().getString(R.string.app_name),
                getActivity().getResources().getString(R.string.cancel),
                getActivity().getResources().getString(R.string.buttonOk),
                null, okListener).show();
    }

    private void connectMobilePrepaidOmni() {
        if (!CommonActivity.isNullOrEmpty(connectionOrder)) {
            Intent intent = new Intent(getActivity(), CreateConnectMobileOmiActivity.class);
            Bundle bundle1 = new Bundle();
            clearContentProfileRecordsGoToConnect(connectionOrder);
            bundle1.putSerializable("connectionOrder", connectionOrder);
            intent.putExtras(bundle1);
            startActivity(intent);
        }
    }

    private void connectMobilePospaidOmni() {
        if (!CommonActivity.isNullOrEmpty(connectionOrder)) {
            Intent intent = new Intent(getActivity(), CreateConnectMobileOmiActivity.class);
            Bundle bundle1 = new Bundle();
            clearContentProfileRecordsGoToConnect(connectionOrder);
            bundle1.putSerializable("connectionOrder", connectionOrder);
            intent.putExtras(bundle1);
            startActivity(intent);
        }
    }

    private void changeToPospaidOmni() {
        if (!CommonActivity.isNullOrEmpty(connectionOrder)) {
            Bundle bundle = new Bundle();
            clearContentProfileRecordsGoToConnect(connectionOrder);
            bundle.putSerializable("connectionOrder", connectionOrder);
            FragmentSearchSubChangeSim fragmentSearchSubChangeSim = new FragmentSearchSubChangeSim();
            fragmentSearchSubChangeSim.setArguments(bundle);
            ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSubChangeSim, false);
        }
    }

    private void registerInfoOmni() {
        RegisterInfoFragment fragment = new RegisterInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHECK_REGISTER_INFO_OMNICHANNEL, CHECK_REGISTER_INFO_OMNICHANNEL);
        clearContentProfileRecordsGoToConnect(connectionOrder);
        bundle.putSerializable("connectionOrder", connectionOrder);
        fragment.setArguments(bundle);
        ReplaceFragment.replaceFragment(getActivity(), fragment, true);
    }

    private void fillData() {
        if (!CommonActivity.isNullOrEmpty(connectionOrder)) {
            checkCusOld();
            fillImagesInfo();
            fillCusInfo();
            fillIsdnInfo();
            fillFeeTotal();
            fillBundleInfo();
            fillVasPlusInfo();
            fillChargeCardInfo();
            fillAdressInfo();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RequestCodeFragment.EDIT_CUSTUMER_INFO_CODE) {
                Bundle bundle = data.getExtras();
                customer = (Customer) bundle.getSerializable("customer");
                connectionOrder.setCustomer(customer);
                fillCusInfo();
                checkCusOld();
            } else if (requestCode == RequestCodeFragment.EDIT_CMT_IMAGE_CODE) {
                Bundle bundle = data.getExtras();
                Bitmap before = bundle.getParcelable("before");
                Bitmap after = bundle.getParcelable("after");
                if (before != null) {
                    imgCMTBefore.setImageBitmap(before);
                    bmBefore = before;
                    connectionOrder.setContentRecordByCode(
                            Constant.PROFILE.CMTNDMT,
                            getBase64String(bmBefore, Constant.IMG_EXT_JPG),
                            Constant.IMG_EXT_JPG
                    );
                }
                if (after != null) {
                    imgCMTAfter.setImageBitmap(after);
                    bmAfter = after;
                    connectionOrder.setContentRecordByCode(
                            Constant.PROFILE.CMTNDMS,
                            getBase64String(bmAfter, Constant.IMG_EXT_JPG),
                            Constant.IMG_EXT_JPG
                    );
                }
            } else if (requestCode == RequestCodeFragment.EDIT_NUMBER_CODE) {
                Bundle bundle = data.getExtras();
                VStockNumberOmniDTO vStockNumberOmniDTO =
                        (VStockNumberOmniDTO) bundle.getSerializable("vStockNumberOmniDTO");

                if (CommonActivity.isNullOrEmpty(connectionOrder.getIsdnPledgeInfo())) {
                    connectionOrder.setIsdnPledgeInfo(new IsdnPledgeInfo());
                }

                if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_PREPAID)) {
                    connectionOrder.getIsdnPledgeInfo().setPrice(
                            Long.parseLong(vStockNumberOmniDTO.getPrePrice()));
                } else {
                    connectionOrder.getIsdnPledgeInfo().setPosPrice(
                            Long.parseLong(vStockNumberOmniDTO.getPosPrice()));
                }

                connectionOrder.getIsdnPledgeInfo().setIsdn(vStockNumberOmniDTO.getIsdn());
                connectionOrder.getIsdnPledgeInfo().setPledgeAmount(
                        Long.parseLong(vStockNumberOmniDTO.getPledgeAmount()));
                connectionOrder.getIsdnPledgeInfo().setPledgeTime(
                        Long.parseLong(vStockNumberOmniDTO.getPledgeTime()));
                connectionOrder.setIsdn(vStockNumberOmniDTO.getIsdn());

                fillIsdnInfo();
                reloadFeeTotal(false);
            } else if (requestCode == RequestCodeFragment.EDIT_BUNDLE_CODE) {
                Bundle bundle = data.getExtras();

                PoCatalogOutsideDTO poCatalogOutsideDTO =
                        (PoCatalogOutsideDTO) bundle.getSerializable("poCatalogOutsideDTO");

                if (CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
                    connectionOrder.setProductInfo(new ProductInfo());
                }

                connectionOrder.getProductInfo().setBundleCode(poCatalogOutsideDTO.getBundleCode());
                connectionOrder.getProductInfo().setBundleDesc(poCatalogOutsideDTO.getDescription());
                connectionOrder.getProductInfo().setBundleName(poCatalogOutsideDTO.getBundleName());
                connectionOrder.getProductInfo().setPrice(Long.parseLong(poCatalogOutsideDTO.getPrice()));
                connectionOrder.getProductInfo().setProductCode(poCatalogOutsideDTO.getProductCode());
                connectionOrder.getProductInfo().setPromotionCode(poCatalogOutsideDTO.getPromotionCode());
                connectionOrder.getProductInfo().setRegReasonId(poCatalogOutsideDTO.getRegReasonId());
                connectionOrder.getProductInfo().setRegReasonCode(poCatalogOutsideDTO.getRegReasonCode());
                connectionOrder.getProductInfo().setMiCodes(poCatalogOutsideDTO.getLstMiCode());
                connectionOrder.getProductInfo().setVtFreeCodes(poCatalogOutsideDTO.getLstVtFree());

                fillBundleInfo();
                reloadFeeTotal(false);
            } else if (requestCode == RequestCodeFragment.EDIT_SIGNATURE_CODE) {
                Bundle bundle = data.getExtras();
                Bitmap bitmapSig = bundle.getParcelable("bitmap");
                imgShowSignature.setImageBitmap(bitmapSig);
                tvSignature.setVisibility(View.GONE);
                connectionOrder.setContentRecordByCode(
                        Constant.PROFILE.CHUKY,
                        getBase64String(bitmapSig, Constant.IMG_EXT_PNG),
                        Constant.IMG_EXT_PNG);
                isSignatured = true;
            } else if (requestCode == RequestCodeFragment.EDIT_CARD_AMOUNT_CODE) {
                Bundle bundle = data.getExtras();
                Long card = (Long) bundle.getSerializable("card");
                connectionOrder.setChargeCardAmound(card);
                reloadFeeTotal(true);
            } else if (requestCode == RequestCodeFragment.EDIT_VAS_PLUS_CODE) {
                Bundle bundle = data.getExtras();
                ArrayList<VasInfo> vasInfos = (ArrayList<VasInfo>) bundle.getSerializable("vasInfos");
                if (CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
                    connectionOrder.setProductInfo(new ProductInfo());
                }
                connectionOrder.getProductInfo().setVasInfos(vasInfos);
                fillVasPlusInfo();
                reloadFeeTotal(false);
            }
            isUpdate = true;
            orderActionState = OrderState.ORD_SAVE_ACT;
            btnAction.setText(getString(R.string.order_continue));
        }
    }

    private void showDialogImageView(Bitmap image) {
        if (CommonActivity.isNullOrEmpty(image)) {
            return;
        }

        final Dialog builder = new Dialog(getContext());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView imageView = new ImageView(getContext());
        imageView.setImageBitmap(image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    private String getBase64String(Bitmap bitmap, String ext) {
        Bitmap bitmapImage = CommonActivity.getResizedBitmap(
                bitmap, Constant.SIZE_IMAGE_SCALE);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (Constant.IMG_EXT_PNG.equals(ext)) {
                bitmapImage.compress(Bitmap.CompressFormat.PNG,
                        Constant.DEFAULT_JPEG_QUALITY, baos);
            } else {
                bitmapImage.compress(Bitmap.CompressFormat.PNG,
                        Constant.DEFAULT_JPEG_QUALITY, baos);
            }
            byte[] imageBytes = baos.toByteArray();
            String base64String = Base64.encodeToString(imageBytes, Activity.TRIM_MEMORY_BACKGROUND);
            baos.close();
            return base64String;
        } catch (IOException ex) {
            Log.e("Error", "getBase64String", ex);
            return "";
        }
    }

    private void showOrderEditUI() {

        // chon thong tin KH cu
        tvOldInfo.setVisibility(View.VISIBLE);

        // chi dau noi moi, hoac yc moi moi chon so
        if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_POSPAID)
                || connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_PREPAID)) {
            imgEditNumber.setVisibility(View.VISIBLE);
        }

        imgEditCusInfo.setVisibility(View.VISIBLE);
        imgEditBundle.setVisibility(View.VISIBLE);
        imgEditVasPlus.setVisibility(View.VISIBLE);
        imgChargeCard.setVisibility(View.VISIBLE);
        imageSignature.setVisibility(View.VISIBLE);
        frlSignatureLayout.setEnabled(true);
        tvSignature.setEnabled(true);
        imgEditCMT.setVisibility(View.VISIBLE);
        llCondition.setVisibility(View.VISIBLE);

        if (!connectionOrder.getAllowUpdateCustomer()) {
            imgEditCMT.setVisibility(View.GONE);
            imgEditCusInfo.setVisibility(View.GONE);
            imageSignature.setVisibility(View.GONE);
            frlSignatureLayout.setEnabled(false);
            tvSignature.setEnabled(false);
            tvOldInfo.setVisibility(View.GONE);
        }
        if (!connectionOrder.getAllowUpdateProduct()) {
            imgEditNumber.setVisibility(View.GONE);
            imgEditBundle.setVisibility(View.GONE);
            imgEditVasPlus.setVisibility(View.GONE);
            imgChargeCard.setVisibility(View.GONE);
        }
        if (!connectionOrder.getAllowUpdateHandleShop()) {
            // do nothing
        }

        isUpdate = false;
    }

    private void hideOrderEditUI() {
        tvOldInfo.setVisibility(View.GONE);

        imgEditVasPlus.setVisibility(View.GONE);
        imgEditBundle.setVisibility(View.GONE);
        imgEditNumber.setVisibility(View.GONE);
        imgEditCusInfo.setVisibility(View.GONE);
        imgChargeCard.setVisibility(View.GONE);
        imageSignature.setVisibility(View.GONE);
        frlSignatureLayout.setEnabled(false);
        tvSignature.setEnabled(false);
        imgEditCMT.setVisibility(View.GONE);

        llCondition.setVisibility(View.GONE);
        llSignature.setVisibility(View.GONE);

        isUpdate = false;
    }

    private void checkCusOld() {

        if (isExitsCusOld) {
            return;
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())) {
            SearchCustidentityAsyncTask searchCustidentityAsyncTask = new SearchCustidentityAsyncTask(getActivity(), new OnPostExecuteListener<ArrayList<CustIdentityDTO>>() {
                @Override
                public void onPostExecute(ArrayList<CustIdentityDTO> result, String errorCode, String description) {
                    if (!CommonActivity.isNullOrEmpty(result)) {
                        lstcustIdentityDTOs = result;
                        isExitsCusOld = true;
                        llCusOld.setVisibility(View.VISIBLE);
                    } else {
                        isExitsCusOld = false;
                        llCusOld.setVisibility(View.GONE);
                    }
                }
            }, moveLogInAct);
            searchCustidentityAsyncTask.execute(connectionOrder.getCustomer().getIdNo());
        } else {
            isExitsCusOld = false;
            llCusOld.setVisibility(View.GONE);
        }
    }

    private void fillImagesInfo() {
        if (CommonActivity.isNullOrEmpty(connectionOrder.getProfileRecords())) {
            return;
        }

        AsyncTaskGetImageOrder asyncTaskGetImageOrder = new AsyncTaskGetImageOrder(
                connectionOrder.getProfileRecords(), getActivity(), new OnPostExecuteListener<List<ProfileRecord>>() {
            @Override
            public void onPostExecute(List<ProfileRecord> result, String errorCode, String description) {
                if (!CommonActivity.isNullOrEmpty(result)) {
                    for (ProfileRecord record : result) {
                        if (Constant.PROFILE.CMTNDMS.equals(record.getCode())
                                && !CommonActivity.isNullOrEmpty(record.getSymbolicLink())) {
                            AsyncTaskLoadImage asyncTaskLoadImage =
                                    new AsyncTaskLoadImage(getActivity(), new OnPostExecuteListener<Bitmap>() {
                                        @Override
                                        public void onPostExecute(Bitmap result, String errorCode, String description) {
                                            imgCMTAfter.setImageBitmap(result);
                                            bmAfter = result;
                                        }
                                    }, moveLogInAct);
                            asyncTaskLoadImage.execute(record.getSymbolicLink());
                        }

                        if (Constant.PROFILE.CMTNDMT.equals(record.getCode())
                                && !CommonActivity.isNullOrEmpty(record.getSymbolicLink())) {
                            AsyncTaskLoadImage asyncTaskLoadImage =
                                    new AsyncTaskLoadImage(getActivity(), new OnPostExecuteListener<Bitmap>() {
                                        @Override
                                        public void onPostExecute(Bitmap result, String errorCode, String description) {
                                            imgCMTBefore.setImageBitmap(result);
                                            bmBefore = result;
                                        }
                                    }, moveLogInAct);
                            asyncTaskLoadImage.execute(record.getSymbolicLink());
                        }

                        if (Constant.PROFILE.CHUKY.equals(record.getCode())
                                && !CommonActivity.isNullOrEmpty(record.getSymbolicLink())) {
                            isSignatured = true;
                            cbConfirmAccept.setChecked(true);
                            llSignature.setVisibility(View.VISIBLE);
                            llCondition.setVisibility(View.VISIBLE);
                            imgShowSignature.setVisibility(View.VISIBLE);
                            tvSignature.setVisibility(View.GONE);

                            AsyncTaskLoadImage asyncTaskLoadImage =
                                    new AsyncTaskLoadImage(getActivity(), new OnPostExecuteListener<Bitmap>() {
                                        @Override
                                        public void onPostExecute(Bitmap result, String errorCode, String description) {
                                            imgShowSignature.setImageBitmap(result);
                                        }
                                    }, moveLogInAct);
                            asyncTaskLoadImage.execute(record.getSymbolicLink());
                        }
                    }
                }
            }
        }, moveLogInAct);
        asyncTaskGetImageOrder.execute();
    }

    private void fillCusInfo() {
        customer = connectionOrder.getCustomer();
        StringBuilder idInfo = new StringBuilder("");
        if (!CommonActivity.isNullOrEmpty(customer)) {
            if (!CommonActivity.isNullOrEmpty(customer.getIdNo())) {
                idInfo.append(customer.getIdNo());
                if (idInfo.toString().length() > 0) {
                    idInfo.append("\n");
                }
                idInfo.append(getString(R.string.date_init)).append(" ");
                idInfo.append(!CommonActivity.isNullOrEmpty(customer.getIdIssueDate())
                        ? DateTimeUtils.getDateFromFullString(customer.getIdIssueDate()) : "");
                idInfo.append(" ").append(getString(R.string.order_here)).append(" ");
                idInfo.append(!CommonActivity.isNullOrEmpty(customer.getIdIssuePlace()) ? customer.getIdIssuePlace() : "");
                tvCusIdInfo.setText(idInfo.toString());
            } else {
                tvCusIdInfo.setText("");
            }

            if (!CommonActivity.isNullOrEmpty(customer.getName())) {
                tvCustName.setText(customer.getName());
            } else {
                tvCustName.setText("");
            }

            if (!CommonActivity.isNullOrEmpty(customer.getBirthDate())) {
                tvBirthDay.setText(DateTimeUtils.getDateFromFullString(customer.getBirthDate()));
            } else {
                tvBirthDay.setText("");
            }

            if (!CommonActivity.isNullOrEmpty(customer.getAddress())) {
                tvCusAddress.setText(customer.getAddress().getFullAddress());
            } else {
                tvCusAddress.setText("");
            }
        }
        tvRecipientPhone.setText(connectionOrder.getRecipientPhone());
    }

    private void fillIsdnInfo() {

        if (CommonActivity.isNullOrEmpty(connectionOrder.getIsdn())) {
            llNumberInfoDetail.setVisibility(View.GONE);
        } else {
            llNumberInfoDetail.setVisibility(View.VISIBLE);
            tvIsdn.setText(connectionOrder.getIsdn());
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getIsdnPledgeInfo())) {
            String monneyString = "0";
            if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_PREPAID)) {
                monneyString = StringUtils.formatMoney(
                        connectionOrder.getIsdnPledgeInfo().getPrice() + "");
            } else {
                monneyString = StringUtils.formatMoney(
                        connectionOrder.getIsdnPledgeInfo().getPosPrice() + "");
            }

            tvIsdnPrice.setText(getString(R.string.maintain_model_fee, monneyString));
            tvIsdnAmount.setText(activity.getString(R.string.maintain_model_fee,
                    StringUtils.formatMoney(connectionOrder.getIsdnPledgeInfo().getPledgeAmount() + "")));
            tvIsdnTime.setText(activity.getString(R.string.maintain_model_time,
                    connectionOrder.getIsdnPledgeInfo().getPledgeTime() + ""));
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getOrderType())) {
            switch (connectionOrder.getOrderType()) {
                case Constant.ORD_TYPE_REGISTER_PREPAID:
                    tvOderTypeDesc.setText(getString(R.string.order_type_register_prepaid));
                    llIsdnPrice.setVisibility(View.VISIBLE);
                    llPostpaidInfo.setVisibility(View.GONE);
                    break;
                case Constant.ORD_TYPE_CONNECT_PREPAID:
                    tvOderTypeDesc.setText(getString(R.string.order_type_connect_prepaid));
                    llIsdnPrice.setVisibility(View.VISIBLE);
                    llPostpaidInfo.setVisibility(View.GONE);
                    break;
                case Constant.ORD_TYPE_CONNECT_POSPAID:
                    tvOderTypeDesc.setText(getString(R.string.order_type_connect_pospaid));
                    llIsdnPrice.setVisibility(View.VISIBLE);
                    llPostpaidInfo.setVisibility(View.VISIBLE);
                    break;
                case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                    tvOderTypeDesc.setText(getString(R.string.order_type_change_to_pospaid));
                    llIsdnPrice.setVisibility(View.GONE);
                    llPostpaidInfo.setVisibility(View.VISIBLE);
                    break;
                default:
                    tvOderTypeDesc.setVisibility(View.GONE);
                    llIsdnPrice.setVisibility(View.GONE);
                    llPostpaidInfo.setVisibility(View.GONE);
                    break;
            }
        } else {
            tvOderTypeDesc.setVisibility(View.GONE);
        }
    }

    private void fillBundleInfo() {
        llBundleConvertFee.setVisibility(View.GONE);
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getBundleName())) {
                tvBundleName.setText(connectionOrder.getProductInfo().getBundleName());
                tvBundleName.setVisibility(View.VISIBLE);
            } else {
                tvBundleName.setVisibility(View.GONE);
            }
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getBundleDesc())) {
                tvBundleDesc.setText(connectionOrder.getProductInfo().getBundleDesc());
                tvBundleDesc.setVisibility(View.VISIBLE);
            } else {
                tvBundleDesc.setVisibility(View.GONE);
            }
            tvBundlePrice.setText(StringUtils.formatMoney(
                    connectionOrder.getProductInfo().getPrice() + "") + " VNĐ");

            llBundlePacketDetail.setVisibility(View.VISIBLE);
        } else {
            llBundlePacketDetail.setVisibility(View.GONE);
        }
    }

    private void fillVasPlusInfo() {
        ArrayList<VasInfo> vasInfos = new ArrayList<>();
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getVasInfos())) {
            vasInfos = new ArrayList<>(connectionOrder.getProductInfo().getVasInfos());
        }
        VasInfoAdapter vasInfoAdapter = new VasInfoAdapter(vasInfos);
        recVasInfo.setAdapter(vasInfoAdapter);
    }

    private void fillChargeCardInfo() {
        if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_PREPAID)
                || connectionOrder.getOrderType().equals(Constant.ORD_TYPE_REGISTER_PREPAID)) {
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getChargeCardAmound())) {
                tvChargeCardAmound.setText(StringUtils.formatMoney(
                        connectionOrder.getChargeCardAmound() + "") + " VNĐ");
            } else {
                tvChargeCardAmound.setText("0 VNĐ");
            }
        } else {
            llChargeCardInfo.setVisibility(View.GONE);
        }
    }

    private void fillAdressInfo() {

        // phi van chuyen:
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getFeeRecords())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeByCode(Order.TRANSFER_FEE))) {
            tvFeeTrans.setText(StringUtils.formatMoney(
                    connectionOrder.getFeeByCode(Order.TRANSFER_FEE) + "") + " VNĐ");
        } else {
            llFeeTrans.setVisibility(View.GONE);
        }

        StringBuilder address = new StringBuilder("");
        if ("HOME".equals(connectionOrder.getTransactionPlace())) {
            tvTransactionPlace.setText(getString(R.string.reciver_at_home));
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getRecipientName())) {
                address.append(connectionOrder.getRecipientName());
            }
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getAddress())
                    && !CommonActivity.isNullOrEmpty(connectionOrder.getAddress().getFullAddress())) {
                if (address.toString().length() > 0) {
                    address.append(", ").append(connectionOrder.getAddress().getFullAddress());
                } else {
                    address.append(connectionOrder.getAddress().getFullAddress());
                }
            }
        } else if ("SHOP".equals(connectionOrder.getTransactionPlace())) {
            tvTransactionPlace.setText(getString(R.string.reciver_at_shop));
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getShopName())) {
                address.append(connectionOrder.getShopName());
            }
            if (!CommonActivity.isNullOrEmpty(connectionOrder.getShopAddress())) {
                if (address.toString().length() > 0) {
                    address.append(", ").append(connectionOrder.getShopAddress());
                } else {
                    address.append(connectionOrder.getShopAddress());
                }
            }
        }
        if (!CommonActivity.isNullOrEmpty(address.toString())) {
            tvAddressTrans.setText(address.toString());
        } else {
            tvAddressTrans.setText(getString(R.string.not_have_address));
        }
    }

    private void fillFeeTotal() {

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getTotalFee())) {
            tvTotalFee.setText(StringUtils.formatMoney(connectionOrder.getTotalFee() + "") + " VNĐ");
        } else {
            tvTotalFee.setText("0 VNĐ");
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getPayInfo())) {
            if (connectionOrder.getPayInfo().getPayMethod() == null) {
                tvPayType.setText(getString(R.string.order_pay_type_pos));
            } else if (PayInfo.PAY_METHOD_BANKPLUS.equals(connectionOrder.getPayInfo().getPayMethod())) {
                tvPayType.setText(getString(R.string.order_pay_type_bankplus));
            } else if (PayInfo.PAY_METHOD_PREPAID_CARD.equals(connectionOrder.getPayInfo().getPayMethod())) {
                tvPayType.setText(getString(R.string.order_pay_type_pre_card));
            }
        } else {
            tvPayType.setText(getString(R.string.not_have_text));
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getPayStatus())) {
            if (connectionOrder.getPayStatus() == 1) {
                tvPayState.setText(getString(R.string.order_pay_success));
            } else if (connectionOrder.getPayStatus() == 2) {
                tvPayState.setText(getString(R.string.order_pay_error));
            } else {
                tvPayState.setText(getString(R.string.order_not_pay));
            }
        } else {
            tvPayState.setText(getString(R.string.order_not_pay));
        }
    }

    private void reloadFeeTotal(boolean isFixCardAmount) {
        Long totalFee = new Long(0);
        switch (connectionOrder.getOrderType()) {
            case Constant.ORD_TYPE_CONNECT_PREPAID:
                reloadNewCardAmount(isFixCardAmount);
                // cong gia so
                if (!CommonActivity.isNullOrEmpty(connectionOrder.getIsdnPledgeInfo())) {
                    totalFee += connectionOrder.getIsdnPledgeInfo().getPrice();
                }
                // cong the nap
                totalFee += connectionOrder.getChargeCardAmound();
                // phi van chuyen
                if ("HOME".equals(connectionOrder.getTransactionPlace())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeRecords())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeByCode(Order.TRANSFER_FEE))) {
                    totalFee += connectionOrder.getFeeByCode(Order.TRANSFER_FEE);
                }
                break;
            case Constant.ORD_TYPE_REGISTER_PREPAID:
                reloadNewCardAmount(isFixCardAmount);
                // cong the nap
                totalFee += connectionOrder.getChargeCardAmound();
                // phi van chuyen
                if ("HOME".equals(connectionOrder.getTransactionPlace())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeRecords())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeByCode(Order.TRANSFER_FEE))) {
                    totalFee += connectionOrder.getFeeByCode(Order.TRANSFER_FEE);
                }
                break;
            case Constant.ORD_TYPE_CONNECT_POSPAID:
                // cong gia so
                if (!CommonActivity.isNullOrEmpty(connectionOrder.getIsdnPledgeInfo())) {
                    totalFee += connectionOrder.getIsdnPledgeInfo().getPosPrice();
                }
                // phi van chuyen
                if ("HOME".equals(connectionOrder.getTransactionPlace())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeRecords())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeByCode(Order.TRANSFER_FEE))) {
                    totalFee += connectionOrder.getFeeByCode(Order.TRANSFER_FEE);
                }
                break;
            case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                // cong phi chuyen doi
                if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
                    totalFee += connectionOrder.getProductInfo().getPrice();
                }
                // phi van chuyen
                if ("HOME".equals(connectionOrder.getTransactionPlace())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeRecords())
                        && !CommonActivity.isNullOrEmpty(connectionOrder.getFeeByCode(Order.TRANSFER_FEE))) {
                    totalFee += connectionOrder.getFeeByCode(Order.TRANSFER_FEE);
                }
                break;
            default:
                break;
        }
        connectionOrder.setTotalFee(totalFee);
        tvTotalFee.setText(StringUtils.formatMoney(totalFee + ""));
    }

    private void reloadNewCardAmount(boolean isFixCardAmount) {
        if (connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CONNECT_POSPAID)
                || connectionOrder.getOrderType().equals(Constant.ORD_TYPE_CHANGE_TO_POSPAID)) {
            return;
        }

        long total = 0;
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getPrice())) {
            total += connectionOrder.getProductInfo().getPrice();
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo().getVasInfos())) {
            for (VasInfo vasInfo : connectionOrder.getProductInfo().getVasInfos()) {
                total += vasInfo.getPrice();
            }
        }

        if (total % 10000 != 0) {
            total = (total / 10000) * 10000 + 10000;
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getChargeCardAmound())) {
            if (isFixCardAmount) {
                if (connectionOrder.getChargeCardAmound() < total) {
                    connectionOrder.setChargeCardAmound(total);
                }
            } else {
                if (connectionOrder.getChargeCardAmound() != total) {
                    connectionOrder.setChargeCardAmound(total);
                }
            }
        }

        tvChargeCardAmound.setText(StringUtils.formatMoney(
                connectionOrder.getChargeCardAmound() + "") + " VNĐ");
    }

    private void showDialogOldCusInfo() {
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.layout_old_customer_info);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (!CommonActivity.isNullOrEmpty(lstcustIdentityDTOs)) {
            CustIdentityDTOAdapter custIdentityDTOAdapter = new CustIdentityDTOAdapter(
                    activity, lstcustIdentityDTOs, moveLogInAct, connectionOrder);
            recyclerView.setAdapter(custIdentityDTOAdapter);
        }
        dialog.setCanceledOnTouchOutside(true);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),
                recyclerView, new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                custIdentityDTO = lstcustIdentityDTOs.get(position);
                AsynGetCustomerByCustId asynGetCustomerByCustId =
                        new AsynGetCustomerByCustId(getActivity(), new OnPostExecuteListener<CustomerDTO>() {
                            @Override
                            public void onPostExecute(CustomerDTO result, String errorCode, String description) {
                                if (!CommonActivity.isNullOrEmpty(result)) {
                                    confirmSelectCusOld(result, dialog);
                                } else {
                                    if (description == null || description.isEmpty()) {
                                        description = activity.getString(R.string.checkdes);
                                    }
                                    Dialog dialog = CommonActivity.createAlertDialog(activity,
                                            description,
                                            activity.getResources().getString(R.string.app_name));
                                    dialog.show();
                                }
                            }
                        }, moveLogInAct);
                asynGetCustomerByCustId.execute(custIdentityDTO.getCustomer().getCustId() + "");
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        dialog.show();
    }

    private void confirmSelectCusOld(final CustomerDTO customerDTO, final BottomSheetDialog dialog) {
        if (CommonActivity.isNullOrEmpty(customerDTO.getCustTypeDTO())) {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.order_claim_cus_type_empty),
                    getString(R.string.app_name)).show();
            return;
        }

        if ("1".equals(customerDTO.getCustTypeDTO().getGroupType())) {
            doUpdateCusOldSelect(customerDTO, dialog);
        } else {
            CommonActivity.createAlertDialog(getActivity(),
                    getString(R.string.cus_old_info_of_company),
                    getString(R.string.app_name)).show();
        }
    }

    private void doUpdateCusOldSelect(CustomerDTO customerDTO, BottomSheetDialog dialog) {
        customer = convertCustomerDtoToCustomerOmichanel(customerDTO);
        connectionOrder.setCustomer(customer);
        isUpdate = true;
        orderActionState = OrderState.ORD_SAVE_ACT;
        btnAction.setText(getString(R.string.order_continue));
        dialog.dismiss();
        fillCusInfo();
    }

    private Customer convertCustomerDtoToCustomerOmichanel(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        // address
        Address address = new Address();
        address.setAddress(customerDTO.getHome());
        address.setDistrict(customerDTO.getDistrict());
        address.setPrecinct(customerDTO.getPrecinct());
        address.setProvince(customerDTO.getProvince());
        address.setFullAddress(customerDTO.getAddress());
        customer.setAddress(address);
        if (CommonActivity.isNullOrEmpty(customerDTO.getCustIdentityDTO())) {
            customerDTO.setCustIdentityDTO(custIdentityDTO);
        }
        customer.setIdIssuePlace(customerDTO.getCustIdentityDTO().getIdIssuePlace());
        customer.setIdNo(customerDTO.getCustIdentityDTO().getIdNo());
        customer.setIdIssueDate(DateTimeUtils.convertDateSoapToFormat(
                customerDTO.getCustIdentityDTO().getIdIssueDate(), "yyyy-MM-dd"));
        customer.setName(customerDTO.getName());
        customer.setCustId(customerDTO.getCustId());
        customer.setCustType(customerDTO.getCustType());

        // date
        customer.setBirthDate(DateTimeUtils.convertDateSoapToFormat(
                customerDTO.getBirthDate(), "yyyy-MM-dd"));
        return customer;
    }

    private byte[] getByteArrayFromImageView(ImageView imageView)
    {
        BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
        Bitmap bitmap;
        if(bitmapDrawable==null){
            imageView.buildDrawingCache();
            bitmap = imageView.getDrawingCache();
            imageView.buildDrawingCache(false);
        }else
        {
            bitmap = bitmapDrawable .getBitmap();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }

    private byte[] getByteArrayFromBitmap(Bitmap bitmap) {

        if (CommonActivity.isNullOrEmpty(bitmap)) {
            return null;
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    protected void setPermission() {

    }

    private void clearContentProfileRecordsGoToConnect(ConnectionOrder connectionOrder) {
        if (CommonActivity.isNullOrEmpty(connectionOrder.getProfileRecords())) {
            return;
        }
        for (ProfileRecord profileRecord : connectionOrder.getProfileRecords()) {
            if (!CommonActivity.isNullOrEmpty(profileRecord.getContent())) {
                profileRecord.setContent("");
            }
        }
    }
}
