package com.viettel.bss.viettelpos.v4.omichanel.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;
import com.viettel.bss.viettelpos.v4.commons.OnPostExecuteListener;
import com.viettel.bss.viettelpos.v4.commons.ReplaceFragment;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterInfoFragment;
import com.viettel.bss.viettelpos.v4.hsdt.asynctask.CancelOrderAsyn;
import com.viettel.bss.viettelpos.v4.hsdt.fragments.ProfileInfoOmniFragment;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.CheckInAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ClaimAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.ClaimForReceptionistAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.TransferOrderAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.asynctask.UnclaimForReceptionistAsyncTask;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ConnectionOrder;
import com.viettel.bss.viettelpos.v4.omichanel.dao.Order;
import com.viettel.bss.viettelpos.v4.omichanel.dao.OrderState;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.ChangePreChargeFragment;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.DetailOrderOmniFragment;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.SearchOrderOmniFragment;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hantt47 on 9/1/2017.
 */

public class OrderOmniAdapter extends RecyclerView.Adapter<OrderOmniAdapter.ViewHolder> {

    private Activity activity;
    private List<ConnectionOrder> orderList;
    private int orderActionStateAll;
    private Fragment fragmentTarget;
    private SearchOrderOmniFragment searchOrderOmniFragment;
    private Staff staffCurrent;
    private String idNoCheck;
    private ArrayList<Staff> arrStaff = new ArrayList<>();

    public OrderOmniAdapter(Activity activity) {
        this.activity = activity;
        this.staffCurrent = StaffBusiness.getStaffByStaffCode(activity, Session.userName);
    }

    public OrderOmniAdapter(Activity activity, int orderActionStateAll,
                            List<ConnectionOrder> orderList, Fragment fragment, SearchOrderOmniFragment searchOrderOmniFragment) {

        this.staffCurrent = StaffBusiness.getStaffByStaffCode(activity, Session.userName);
        this.activity = activity;
        this.orderList = orderList;
        this.orderActionStateAll = orderActionStateAll;
        this.fragmentTarget = fragment;
        this.searchOrderOmniFragment = searchOrderOmniFragment;

    }

    public OrderOmniAdapter(Activity activity, int orderActionStateAll,
                            List<ConnectionOrder> orderList, Fragment fragment) {

        this.staffCurrent = StaffBusiness.getStaffByStaffCode(activity, Session.userName);
        this.activity = activity;
        this.orderList = orderList;
        this.orderActionStateAll = orderActionStateAll;
        this.fragmentTarget = fragment;
    }

    public OrderOmniAdapter(Activity activity, int orderActionStateAll,
                            List<ConnectionOrder> orderList,
                            Fragment fragment, ArrayList<Staff> arrStaff) {

        this.staffCurrent = StaffBusiness.getStaffByStaffCode(activity, Session.userName);
        this.activity = activity;
        this.orderList = orderList;
        this.orderActionStateAll = orderActionStateAll;
        this.fragmentTarget = fragment;
        this.arrStaff = arrStaff;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_omni_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ConnectionOrder connectionOrder = orderList.get(position);
        connectionOrder.setOrderActionState(orderActionStateAll);

        //them button huy yeu cau
        if ("HSDT".equals(connectionOrder.getTarget())
                && connectionOrder.getAllowCancel()) {
            holder.tvDestroy.setVisibility(View.VISIBLE);
        } else {
            holder.tvDestroy.setVisibility(View.GONE);
        }

        //kiem tra co hsdt
        if ("HSDT".equals(connectionOrder.getTarget())) {
            if (Order.CHECKIN_ABLE.equals(connectionOrder.getBpState()) ||
                    Order.RECEIPTION_ABLE.equals(connectionOrder.getBpState())) {
                connectionOrder.setOrderActionState(OrderState.ORD_HSDT_ACT);
            } else {
                connectionOrder.setOrderActionState(OrderState.ORD_STATE_OTHER_ACT);
            }
        }

        // CONNECT, CHECKIN, CLAIM, RE_CLAIM
        if (connectionOrder.getOrderActionState() == OrderState.ORD_CHECKIN_ACT) {

            if (Order.CHECKIN_ABLE.equals(connectionOrder.getBpState())) {
                connectionOrder.setOrderActionState(OrderState.ORD_CHECKIN_ACT);
            } else if (Order.RECEIPTION_ABLE.equals(connectionOrder.getBpState())) {
                if (connectionOrder.getStaffId() == null) {
                    if (!checkShop(connectionOrder)) {
                        connectionOrder.setOrderActionState(OrderState.ORD_TRANSFER_ACT);
                    } else {
                        connectionOrder.setOrderActionState(OrderState.ORD_CLAIM_RECEIP_ACT);
                    }
                } else if (CommonActivity.getCurrentStaffId(activity).equals(connectionOrder.getStaffId())
                        && checkShop(connectionOrder)) {
                    connectionOrder.setOrderActionState(OrderState.ORD_CONNECT_ACT);
                } else {
                    connectionOrder.setOrderActionState(OrderState.ORD_STATE_OTHER_ACT);
                }
            } else {
                connectionOrder.setOrderActionState(OrderState.ORD_STATE_OTHER_ACT);
            }

            if (connectionOrder.getOrderActionState() == OrderState.ORD_CHECKIN_ACT) {
                holder.tvCheckIn.setText(activity.getString(R.string.checkin_order));
                holder.tvCheckIn.setVisibility(View.VISIBLE);
            } else if (connectionOrder.getOrderActionState() == OrderState.ORD_CLAIM_RECEIP_ACT
                    || connectionOrder.getOrderActionState() == OrderState.ORD_TRANSFER_ACT) {
                holder.tvCheckIn.setText(activity.getString(R.string.staff_claim_order));
                holder.tvCheckIn.setVisibility(View.VISIBLE);
            } else {
                holder.tvCheckIn.setVisibility(View.GONE);
            }
        } else if (connectionOrder.getOrderActionState() == OrderState.ORD_CLAIM_ACT) {
            holder.tvCheckIn.setText(activity.getString(R.string.staff_claim_order));
            holder.tvCheckIn.setVisibility(View.VISIBLE);
        } else {
            holder.tvCheckIn.setVisibility(View.GONE);
        }

        // yeu cau da hoan thanh
        if (connectionOrder.getStatus() == 3) {
            holder.linCurrentState.setVisibility(View.VISIBLE);
        } else {
            holder.linCurrentState.setVisibility(View.GONE);
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getOrderCode())) {
            holder.tvOrderCode.setText(connectionOrder.getOrderCode().replaceFirst("ORD", ""));
        } else {
            holder.linOrderCode.setVisibility(View.GONE);
        }

        if (Constant.HSDT.equals(connectionOrder.getTarget())) {
            holder.tvOrderTarget.setText(activity.getString(R.string.order_hsdt));
        } else {
            holder.tvOrderTarget.setText(activity.getString(R.string.order_omni));
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getIsdn())) {
            holder.tvIsdn.setText(connectionOrder.getIsdn());
        } else {
            holder.linIsdn.setVisibility(View.GONE);
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getRecipientPhone())) {
            holder.tvRecipientPhone.setText(connectionOrder.getRecipientPhone());
        } else {
            holder.linRecipientPhone.setVisibility(View.GONE);
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getName())) {
            holder.tvRecipientName.setText(connectionOrder.getCustomer().getName());
        } else {
            holder.linRecipientName.setVisibility(View.GONE);
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getCustomer())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress())
                && !CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getAddress().getFullAddress())) {
            holder.tvAddress.setText(connectionOrder.getCustomer().getAddress().getFullAddress());
        } else {
            holder.linAddress.setVisibility(View.GONE);
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getCreateDate())) {
            holder.tvCreateDate.setText(DateTimeUtils
                    .convertDateTimeToString(connectionOrder.getCreateDate()));
        } else {
            holder.linCreateDate.setVisibility(View.GONE);
        }
        if (!CommonActivity.isNullOrEmpty(connectionOrder.getOrderTypeDesc())) {
            holder.tvOrderTypeDesc.setText(connectionOrder.getOrderTypeDesc());
        } else {
            holder.linOrderTypeDesc.setVisibility(View.GONE);
        }

        if (!CommonActivity.isNullOrEmpty(connectionOrder.getDeadlineStatus())) {
            switch (connectionOrder.getDeadlineStatus() + "") {
                case "1":
                    holder.tvDeadlineStatus.setText("Trong hạn");
                    holder.tvDeadlineStatus.setTextColor(Color.BLUE);
                    break;
                case "2":
                    holder.tvDeadlineStatus.setText("Sắp đến hạn");
                    holder.tvDeadlineStatus.setTextColor(Color.YELLOW);
                    break;
                case "3":
                    holder.tvDeadlineStatus.setText("Quá hạn");
                    holder.tvDeadlineStatus.setTextColor(Color.RED);
                    break;
            }
        } else {
            holder.linDeadlineStatus.setVisibility(View.GONE);
        }

        holder.tvCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.tvCheckIn.getText().toString().equals(activity.getString(R.string.checkin_order))) {

                    // check shop
                    if ("SHOP".equals(connectionOrder.getTransactionPlace())
                            && !CommonActivity.isNullOrEmpty(connectionOrder.getShopId())
                            && !checkShop(connectionOrder)) {
                        CommonActivity.createDialog(activity,
                                activity.getResources().getString(R.string.order_chekin_with_shop_meg),
                                activity.getResources().getString(R.string.app_name),
                                activity.getResources().getString(R.string.cancel),
                                activity.getResources().getString(R.string.buttonOk),
                                null, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        doCheckInOrder(connectionOrder, holder.tvCheckIn);
                                    }
                                })
                                .show();
                    } else {
                        CommonActivity.createDialog(activity,
                                activity.getResources().getString(R.string.omni_confirm_checkin),
                                activity.getResources().getString(R.string.app_name),
                                activity.getResources().getString(R.string.cancel),
                                activity.getResources().getString(R.string.buttonOk),
                                null, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        doCheckInOrder(connectionOrder, holder.tvCheckIn);
                                    }
                                })
                                .show();
                    }
                } else if (connectionOrder.getOrderActionState() == OrderState.ORD_CLAIM_ACT) {
                    CommonActivity.createDialog(activity,
                            activity.getResources().getString(R.string.omni_confirm_claim_order),
                            activity.getResources().getString(R.string.app_name),
                            activity.getResources().getString(R.string.cancel),
                            activity.getResources().getString(R.string.buttonOk),
                            null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doClaimOrder(connectionOrder, holder.tvCheckIn);
                                }
                            })
                            .show();
                } else if (connectionOrder.getOrderActionState() == OrderState.ORD_TRANSFER_ACT) {

                    CommonActivity.createDialog(activity,
                            activity.getResources().getString(R.string.order_transfer_confirm,
                                    connectionOrder.getShopName()),
                            activity.getResources().getString(R.string.app_name),
                            activity.getResources().getString(R.string.cancel),
                            activity.getResources().getString(R.string.buttonOk),
                            null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doTransferOrder(connectionOrder, holder.tvCheckIn);
                                }
                            })
                            .show();
                } else if (connectionOrder.getOrderActionState() == OrderState.ORD_CLAIM_RECEIP_ACT) {

                    String actionNextString = "";
                    switch (connectionOrder.getOrderType()) {
                        case Constant.ORD_TYPE_CONNECT_PREPAID:
                            actionNextString = activity.getString(R.string.order_action_next_conect);
                            break;
                        case Constant.ORD_TYPE_REGISTER_PREPAID:
                            actionNextString = activity.getString(R.string.order_action_next_register);
                            break;
                        case Constant.ORD_TYPE_CONNECT_POSPAID:
                            actionNextString = activity.getString(R.string.order_action_next_conect);
                            break;
                        case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                            actionNextString = activity.getString(R.string.order_action_next_change);
                            break;
                        case Constant.ORD_TYPE_CHANGE_PREPAID_FEE:
                            actionNextString = activity.getString(R.string.order_action_next_change_prepaid_fee);
                            break;
                        default:
                            break;
                    }
                    CommonActivity.createDialog(activity,
                            activity.getString(R.string.order_confirm_process_order_continue, actionNextString),
                            activity.getResources().getString(R.string.app_name),
                            activity.getResources().getString(R.string.cancel),
                            activity.getResources().getString(R.string.buttonOk),
                            null, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    doClaimForReceptionist(connectionOrder, holder.tvCheckIn);
                                }
                            })
                            .show();
                }
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectionOrder.getStatus() == 3) {
                    return;
                }

                // YC tai nha
                if ("HOME".equals(connectionOrder.getTransactionPlace())
                        && connectionOrder.getOrderActionState() == OrderState.ORD_STATE_OTHER_ACT) {
                    CommonActivity.createAlertDialog(activity,
                            activity.getString(R.string.order_is_home_meg),
                            activity.getString(R.string.app_name)).show();
                    return;
                }

                // nhan vien khac
                if (connectionOrder.getOrderActionState() == OrderState.ORD_STAFF_OTHER_ACT) {
                    CommonActivity.createAlertDialog(activity,
                            activity.getString(R.string.order_meg_claim_by_other_user,
                                    connectionOrder.getStaffId()),
                            activity.getString(R.string.app_name)).show();
                    return;
                }

                if (connectionOrder.getOrderActionState() == OrderState.ORD_STATE_OTHER_ACT) {
                    CommonActivity.createAlertDialog(activity,
                            activity.getString(R.string.order_meg_state_other_user),
                            activity.getString(R.string.app_name)).show();
                    return;
                }

                if (connectionOrder.getOrderActionState() == OrderState.ORD_HSDT_ACT) {

                    if (!checkShop(connectionOrder)) {
                        CommonActivity.createAlertDialog(activity,
                                activity.getString(R.string.order_shop_invalid),
                                activity.getString(R.string.app_name)).show();
                        return;
                    }

                    if (!CommonActivity.isNullOrEmpty(connectionOrder.getUploadFiles())) {
                        Bundle mBundle = new Bundle();
                        ProfileInfoOmniFragment profileInfoOmniFragment = new ProfileInfoOmniFragment();
                        ArrayList<ConnectionOrder> arrConnectionOrder = new ArrayList<>();
                        arrConnectionOrder.add(connectionOrder);
                        mBundle.putSerializable("arrConnectionOrder", arrConnectionOrder);
                        profileInfoOmniFragment.setArguments(mBundle);
                        ReplaceFragment.replaceFragment(activity, profileInfoOmniFragment, false);

                        return;
                    } else {
                        CommonActivity.createAlertDialog(activity, activity.getResources().getString(R.string.not_ho_so), activity.getResources().getString(R.string.app_name)).show();
                        return;
                    }
                }

                processClick(connectionOrder);
            }
        });

        holder.tvDestroy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelOrder(connectionOrder.getProcessInstanceId());
            }
        });

        // set check =box check hay check dựa connectionOrder.isCheckIdNo
        if ("HSDT".equals(connectionOrder.getTarget())) {
            holder.cbccheckIdNo.setVisibility(View.VISIBLE);
        } else holder.cbccheckIdNo.setVisibility(View.GONE);
        if (CommonActivity.isNullOrEmpty(connectionOrder.getCustomer().getIdNo())) {
            holder.cbccheckIdNo.setVisibility(View.GONE);
        }

        if (getItemCount() == 1) {
            holder.cbccheckIdNo.setVisibility(View.GONE);
        }



        holder.cbccheckIdNo.setChecked(connectionOrder.isCheckIdNo());
        holder.cbccheckIdNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.cbccheckIdNo.isChecked()) {
                    if (connectionOrder.getCustomer().getIdNo().equals(idNoCheck)) {
                        connectionOrder.setCheckIdNo(true);
                    } else {

                        View.OnClickListener onClickListenerOk = new View.OnClickListener() {
                            @Override
                            public void onClick(View arg0) {
                                for (ConnectionOrder connectionOrder1 : orderList) {
                                    if (connectionOrder.getCustomer().getIdNo().equals(connectionOrder1.getCustomer().getIdNo())) {
                                        connectionOrder1.setCheckIdNo(true);
                                    } else {
                                        connectionOrder1.setCheckIdNo(false);
                                    }
                                }
                                idNoCheck = connectionOrder.getCustomer().getIdNo();
                                notifyDataSetChanged();
                                searchOrderOmniFragment.setViewLabel(orderList);
                            }
                        };

                        if (!CommonActivity.isNullOrEmpty(idNoCheck)) {
                            CommonActivity.createDialog(activity,
                                    activity.getString(R.string.confirm_select_order),
                                    activity.getString(R.string.app_name), activity.getString(R.string.cancel),
                                    activity.getString(R.string.ok), null, onClickListenerOk).show();
                        } else {
                            for (ConnectionOrder connectionOrder1 : orderList) {
                                if (connectionOrder.getCustomer().getIdNo().equals(connectionOrder1.getCustomer().getIdNo())) {
                                    connectionOrder1.setCheckIdNo(true);
                                } else {
                                    connectionOrder1.setCheckIdNo(false);
                                }
                            }
                            idNoCheck = connectionOrder.getCustomer().getIdNo();
                        }

                    }

                    notifyDataSetChanged();
                } else {
                    connectionOrder.setCheckIdNo(false);
                    Boolean check = false;
                    for (ConnectionOrder connectionOrder1 : orderList) {
                        if (connectionOrder1.isCheckIdNo()) check = true;
                    }
                    if (!check) idNoCheck = "";
                    notifyDataSetChanged();
                }
                searchOrderOmniFragment.setView(orderList);
                searchOrderOmniFragment.setViewLabel(orderList);

                // kt check bo này có check hay không
                //nếu đang check for orderList có idNo bằng idNo với connectionOrder set bằng true
                // notifichange
            }
        });

        holder.tvIdNo.setText(connectionOrder.getCustomer().getIdNo());
    }

    private void doTransferOrder(final ConnectionOrder connectionOrder, final TextView textView) {
        TransferOrderAsyncTask transferOrderAsyncTask = new TransferOrderAsyncTask(activity,
                new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.toast(activity, R.string.order_transfer_success);
                            connectionOrder.setOrderActionState(OrderState.ORD_CLAIM_RECEIP_ACT);
                            doClaimForReceptionist(connectionOrder, textView);
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

    private void doClaimOrder(final ConnectionOrder connectionOrder, final TextView textView) {
        String taskId = connectionOrder.getTaskId();
        ClaimAsyncTask claimAsyncTask = new ClaimAsyncTask(activity,
                new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.toast(activity, R.string.omni_claim_success);
                            connectionOrder.setOrderActionState(OrderState.ORD_CONNECT_ACT);
                            textView.setVisibility(View.GONE);
                        } else {
                            CommonActivity.createAlertDialog(activity,
                                    CommonActivity.isNullOrEmpty(description) ?
                                            activity.getString(R.string.checkdes) : description,
                                    activity.getString(R.string.app_name)).show();
                        }
                    }
                }, null);
        claimAsyncTask.execute(taskId, null, "false");
    }

    private void doCheckInOrder(final ConnectionOrder connectionOrder, final TextView textView) {
        String processId = connectionOrder.getProcessInstanceId();
        CheckInAsyncTask checkInAsyncTask = new CheckInAsyncTask(activity, new OnPostExecuteListener<String>() {
            @Override
            public void onPostExecute(String result, String errorCode, String description) {
                if ("0".equals(errorCode)) {
                    CommonActivity.toast(activity, R.string.omni_checkin_success);
                    connectionOrder.setOrderActionState(OrderState.ORD_CLAIM_RECEIP_ACT);
                    textView.setText(activity.getString(R.string.staff_claim_order));
                } else {
                    CommonActivity.createAlertDialog(activity, CommonActivity.isNullOrEmpty(description) ?
                                    activity.getString(R.string.checkdes) : description,
                            activity.getString(R.string.app_name)).show();
                }
            }
        }, null);
        checkInAsyncTask.execute(processId);
    }

    private void doClaimForReceptionist(final ConnectionOrder connectionOrder, final TextView textView) {
        ClaimForReceptionistAsyncTask claimForReceptionistAsyncTask =
                new ClaimForReceptionistAsyncTask(activity, new OnPostExecuteListener<String>() {
                    @Override
                    public void onPostExecute(String result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.toast(activity, R.string.order_update_sucess);
                            connectionOrder.setTaskId(result);
                            connectionOrder.setOrderActionState(OrderState.ORD_CONNECT_ACT);
                            textView.setVisibility(View.GONE);
                            processClick(connectionOrder);
                        } else {
                            if (CommonActivity.isNullOrEmpty(description)) {
                                CommonActivity.createAlertDialog(activity,
                                        activity.getString(R.string.checkdes),
                                        activity.getString(R.string.app_name)).show();
                            } else {
                                String[] arrayString = description.split(" ");
                                if (arrayString.length > 0
                                        && StringUtils.isDigit(arrayString[arrayString.length - 1])) {

                                    doUnclaimAndClaimNewTask(arrayString[arrayString.length - 1],
                                            connectionOrder, textView);
                                } else {
                                    CommonActivity.createAlertDialog(activity,
                                            description,
                                            activity.getString(R.string.app_name)).show();
                                }
                            }
                        }
                    }
                }, null);
        claimForReceptionistAsyncTask.execute(connectionOrder.getProcessInstanceId());
    }

    private void doUnclaimAndClaimNewTask(final String processIdOld,
                                          final ConnectionOrder connectionOrder,
                                          final TextView textView) {

        final View.OnClickListener okListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                UnclaimForReceptionistAsyncTask unclaimForReceptionistAsyncTask =
                        new UnclaimForReceptionistAsyncTask(activity, new OnPostExecuteListener<String>() {
                            @Override
                            public void onPostExecute(String result, String errorCode, String description) {
                                if ("0".equals(errorCode)) {
                                    CommonActivity.toast(activity, activity.getString(R.string.order_unclaim_success));
                                    doClaimForReceptionist(connectionOrder, textView);
                                } else {
                                    if (CommonActivity.isNullOrEmpty(description)) {
                                        CommonActivity.createAlertDialog(activity,
                                                activity.getString(R.string.checkdes),
                                                activity.getString(R.string.app_name)).show();
                                    } else {
                                        CommonActivity.createAlertDialog(activity,
                                                description,
                                                activity.getString(R.string.app_name)).show();
                                    }
                                }
                            }
                        }, null);
                unclaimForReceptionistAsyncTask.execute(processIdOld);
            }
        };

        CommonActivity.createDialog(activity,
                activity.getString(R.string.order_confirm_unclaim, processIdOld,
                        connectionOrder.getProcessInstanceId()),
                activity.getString(R.string.app_name),
                activity.getString(R.string.cancel),
                activity.getString(R.string.ok),
                null, okListener).show();
    }

    private void processClick(ConnectionOrder connectionOrder) {
        Bundle bundle = new Bundle();
        switch (connectionOrder.getOrderType()) {
            case Constant.ORD_TYPE_CONNECT_PREPAID:
            case Constant.ORD_TYPE_CONNECT_POSPAID:
                goToOmniDetail(connectionOrder);
                break;
            case Constant.ORD_TYPE_REGISTER_PREPAID:
                if (CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
                    if (!validateOrderInfo(connectionOrder)) {
                        return;
                    }
                    RegisterInfoFragment registerInfoFragment = new RegisterInfoFragment();
                    bundle.putString("omniProcessId", connectionOrder.getProcessInstanceId());
                    bundle.putString("omniTaskId", connectionOrder.getTaskId());
                    registerInfoFragment.setArguments(bundle);
                    ReplaceFragment.replaceFragment(activity, registerInfoFragment, false);
                } else {
                    goToOmniDetail(connectionOrder);
                }
                break;
            case Constant.ORD_TYPE_CHANGE_TO_POSPAID:
                if (CommonActivity.isNullOrEmpty(connectionOrder.getProductInfo())) {
                    if (!validateOrderInfo(connectionOrder)) {
                        return;
                    }
                    FragmentSearchSubChangeSim fragmentSearchSubPos = new FragmentSearchSubChangeSim();
                    bundle.putString("functionKey", Constant.CHANGE_PRE_TO_POS);
                    bundle.putString("omniProcessId", connectionOrder.getProcessInstanceId());
                    bundle.putString("omniTaskId", connectionOrder.getTaskId());
                    fragmentSearchSubPos.setArguments(bundle);
                    ReplaceFragment.replaceFragment(activity, fragmentSearchSubPos, true);
                } else {
                    goToOmniDetail(connectionOrder);
                }
                break;
            case Constant.ORD_TYPE_CHANGE_PREPAID_FEE:
                ChangePreChargeFragment changePreChargeFragment = new ChangePreChargeFragment();
                bundle.putSerializable("connectionOrder", connectionOrder);
                changePreChargeFragment.setArguments(bundle);
                ReplaceFragment.replaceFragment(activity, changePreChargeFragment, false);
                break;
            default:
                break;
        }
    }

    private boolean checkShop(ConnectionOrder connectionOrder) {
        if (staffCurrent.getShopId().equals(connectionOrder.getShopId())) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateOrderInfo(ConnectionOrder connectionOrder) {
        if (CommonActivity.isNullOrEmpty(connectionOrder.getProcessInstanceId())
                || CommonActivity.isNullOrEmpty(connectionOrder.getTaskId())) {

            CommonActivity.createAlertDialog(activity,
                    activity.getString(R.string.order_request_claim),
                    activity.getString(R.string.app_name)
            ).show();

            return false;
        } else {
            return true;
        }
    }

    private void goToOmniDetail(ConnectionOrder connectionOrder) {
        DetailOrderOmniFragment fragment = new DetailOrderOmniFragment();
        Bundle bundleDetail = new Bundle();
        bundleDetail.putInt("orderActionState", connectionOrder.getOrderActionState());
        bundleDetail.putSerializable("connectionOrder", connectionOrder);
        bundleDetail.putSerializable("arrStaff", arrStaff);
        fragment.setArguments(bundleDetail);
        fragment.setTargetFragment(fragmentTarget, 1011);
        ReplaceFragment.replaceFragment(activity, fragment, false);
    }

    private void cancelOrder(final String processId) {
        final View.OnClickListener cancelOrderListener = new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                CancelOrderAsyn cancelOrder = new CancelOrderAsyn(activity, new OnPostExecuteListener<Void>() {
                    @Override
                    public void onPostExecute(Void result, String errorCode, String description) {
                        if ("0".equals(errorCode)) {
                            CommonActivity.createAlertDialog(activity,
                                    activity.getString(R.string.cancel_order_success_hsdt, processId),
                                    activity.getString(R.string.app_name)).show();
                            //refresh list
                            ((SearchOrderOmniFragment) fragmentTarget).findDetailTask();
                        }
                    }
                }, null);
                cancelOrder.execute(processId);
            }
        };

        CommonActivity.createDialog(activity,
                activity.getString(R.string.confirm_cancel_order, processId),
                activity.getString(R.string.app_name),
                activity.getString(R.string.cancel),
                activity.getString(R.string.ok),
                null, cancelOrderListener).

                show();
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    //process multi order hsdt
    public void processMultiOrderHSDT(ArrayList<ConnectionOrder> arrConnectionOrder) {
        //kiem tra ho so
        for (ConnectionOrder connectionOrder : arrConnectionOrder) {
            // khong cho ky nhanh trong truong hop co 2 chu ky
            for (ProfileRecord profileRecord : connectionOrder.getProfileRecords()) {
                if (Constant.PROFILE.CHUKY2.equals(profileRecord.getCode())) {
                    CommonActivity.createAlertDialog(
                            activity,
                            activity.getResources().getString(R.string.confirm_order_2_sign, connectionOrder.getProcessInstanceId()),
                            activity.getResources().getString(R.string.app_name)).show();
                    return;
                }
            }
            //kiem tra order khong co ho so
            if (CommonActivity.isNullOrEmpty(connectionOrder.getUploadFiles())) {
                CommonActivity.createAlertDialog(activity, activity.getResources().getString(R.string.order_not_ho_so, connectionOrder.getProcessInstanceId()), activity.getResources().getString(R.string.app_name)).show();
                return;
            }

            // kiem tra order trung nhau
            ArrayList<ConnectionOrder> lisSame = getListSameOrder(connectionOrder, arrConnectionOrder);
            if (lisSame.size() > 1) {
                StringBuilder sameString = new StringBuilder("");
                for (int index = 0; index < lisSame.size(); index++) {
                    if (index == lisSame.size() - 1) {
                        sameString.append(lisSame.get(index).getProcessInstanceId());
                    } else {
                        sameString.append(lisSame.get(index).getProcessInstanceId() + ", ");
                    }
                }
                CommonActivity.createAlertDialog(activity,
                        activity.getResources().getString(
                                R.string.order_over_exits,
                                sameString.toString(),
                                lisSame.get(0).getOrderTypeDesc(),
                                lisSame.get(0).getIsdn()),
                        activity.getResources().getString(R.string.app_name)).show();
                return;
            }
        }

        ConnectionOrder connectionOrder = arrConnectionOrder.get(0);
        if (connectionOrder.getOrderActionState() == OrderState.ORD_HSDT_ACT) {
            if (!checkShop(connectionOrder)) {
                CommonActivity.createAlertDialog(activity,
                        activity.getString(R.string.order_shop_invalid),
                        activity.getString(R.string.app_name)).show();
                return;
            }

            Bundle mBundle = new Bundle();
            ProfileInfoOmniFragment profileInfoOmniFragment = new ProfileInfoOmniFragment();
            mBundle.putSerializable("arrConnectionOrder", arrConnectionOrder);
            profileInfoOmniFragment.setArguments(mBundle);
            ReplaceFragment.replaceFragment(activity, profileInfoOmniFragment, false);
        }
    }

    private ArrayList<ConnectionOrder> getListSameOrder(ConnectionOrder connectionOrder, ArrayList<ConnectionOrder> arrConnectionOrder) {
        ArrayList<ConnectionOrder> listSame = new ArrayList<>();
        for (ConnectionOrder connectionOrderTemp : arrConnectionOrder) {
            if (connectionOrder.getOrderType().equals(connectionOrderTemp.getOrderType())
                    && connectionOrder.getIsdn().equals(connectionOrderTemp.getIsdn())) {
                listSame.add(connectionOrderTemp);
            }
        }
        return listSame;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.tvCheckIn)
        TextView tvCheckIn;
        @BindView(R.id.tvOrderCode)
        TextView tvOrderCode;
        @BindView(R.id.tvOrderTarget)
        TextView tvOrderTarget;

        @BindView(R.id.tvIsdn)
        TextView tvIsdn;
        @BindView(R.id.tvRecipientPhone)
        TextView tvRecipientPhone;
        @BindView(R.id.tvRecipientName)
        TextView tvRecipientName;
        @BindView(R.id.tvAddress)
        TextView tvAddress;
        @BindView(R.id.tvCreateDate)
        TextView tvCreateDate;
        @BindView(R.id.tvOrderTypeDesc)
        TextView tvOrderTypeDesc;
        @BindView(R.id.tvDeadlineStatus)
        TextView tvDeadlineStatus;

        @BindView(R.id.linIsdn)
        LinearLayout linIsdn;
        @BindView(R.id.linOrderTarget)
        LinearLayout linOrderTarget;
        @BindView(R.id.linRecipientPhone)
        LinearLayout linRecipientPhone;
        @BindView(R.id.linRecipientName)
        LinearLayout linRecipientName;
        @BindView(R.id.linAddress)
        LinearLayout linAddress;
        @BindView(R.id.linCreateDate)
        LinearLayout linCreateDate;
        @BindView(R.id.linOrderTypeDesc)
        LinearLayout linOrderTypeDesc;
        @BindView(R.id.linDeadlineStatus)
        LinearLayout linDeadlineStatus;
        @BindView(R.id.linOrderCode)
        LinearLayout linOrderCode;
        @BindView(R.id.linCurrentState)
        LinearLayout linCurrentState;
        @BindView(R.id.tvDestroy)
        TextView tvDestroy;
        @BindView(R.id.cbccheckIdNo)
        CheckBox cbccheckIdNo;
        @BindView(R.id.tvIdNo)
        TextView tvIdNo;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
