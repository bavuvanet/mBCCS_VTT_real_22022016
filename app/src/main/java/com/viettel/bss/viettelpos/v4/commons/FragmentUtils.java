package com.viettel.bss.viettelpos.v4.commons;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.activity.FragmentSearchSubChangeSim;
import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.advisory.screen.SearchAdvisoryFragment;
import com.viettel.bss.viettelpos.v4.cc.fragment.FragmentUpdateGift;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel;
import com.viettel.bss.viettelpos.v4.channel.activity.FragmentListChannel_fromNoti;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDel;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelBankPlus;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeDelCTV;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeNew;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeNotify;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentChargeRe;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractDelay;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractNotPayment;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractPayment;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractReport;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractReportVerify;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractSearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifySearch;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentContractVerifyUpdate;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentSendSmsCtv;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentSuportUpdate;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdateComplain;
import com.viettel.bss.viettelpos.v4.charge.activity.FragmentUpdatePromotion;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.ActivityCreateNewRequestMobileNew;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentChangeOffer;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentCheckChanelSmartSim;
import com.viettel.bss.viettelpos.v4.connecttionMobile.activity.FragmentRegisterServiceVas;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentChangePromotion;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentConnectionManager;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentManagerChangeTech;
import com.viettel.bss.viettelpos.v4.connecttionService.activity.FragmentModifyProfile;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentManageBundleGroup;
import com.viettel.bss.viettelpos.v4.customer.activity.FragmentSearchComporation;
import com.viettel.bss.viettelpos.v4.customer.fragment.ComplainFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.CustomerManagerFragment;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCareLostSub;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCheckUsuallyCall;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentCollectCusInfo;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentLookupDebitInfo;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentViewOcsHlr;
import com.viettel.bss.viettelpos.v4.customer.fragment.FragmentWarranty;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentPrepairIdNo;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCV;
import com.viettel.bss.viettelpos.v4.customer.manage.FragmentSearchCustomerMustApprove;
import com.viettel.bss.viettelpos.v4.customer.manage.RegisterInfoFragment;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfoIDNumberManager;
import com.viettel.bss.viettelpos.v4.infrastrucure.activity.FragmentInfrastructureOnline;
import com.viettel.bss.viettelpos.v4.omichanel.fragment.OmniManagerFragment;
import com.viettel.bss.viettelpos.v4.profile.FragmentLookupProfile;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReport;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportBonusVAS;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportCarePopup;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportForEmployee;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportGetInveneStaff;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportImagePayment;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportInventory;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportPromotion;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportRevenueGeneral;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportStaffIncome;
import com.viettel.bss.viettelpos.v4.report.activity.FragmentReportTarget;
import com.viettel.bss.viettelpos.v4.report.fragment.FragmentLookupLogMBCCS;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentActiveAccountPayment;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentAnypayExchange;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentAnypayISDN;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChange2GTo3G;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChangePassAnypay;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCheckoutMoney;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseBHLD;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentChooseStaff;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentConfirmNote;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateChanel;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentCreateInvoice;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentInfoSearch;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentListOrder;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentManageSubscriber;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentRechargeToBank;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleByOrder;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSaling;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleSalingReturn;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSaleToCustomers;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchAnypay;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchISDN;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchKIT;
import com.viettel.bss.viettelpos.v4.sale.activity.FragmentSearchSerialCard;
import com.viettel.bss.viettelpos.v4.sale.business.GetStockOrderManager;
import com.viettel.bss.viettelpos.v4.sale.fragment.FragmentChannelOrder;
import com.viettel.bss.viettelpos.v4.sale.fragment.FragmentChannelOrderManager;
import com.viettel.bss.viettelpos.v4.sale.strategy.BlockOpenSubManagerFragment;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeEquimentFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangeProductFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ChangePromotionFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.ManageSubscriberFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.RegisterSafeNetFragmentStrategy;
import com.viettel.bss.viettelpos.v4.sale.strategy.TickAppointFragmentStrategy;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentCriteria;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentHotlineManager;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentListStaffJobAssignment;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentTaskAssignManager;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentTaskAssignStaff;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateArea;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentUpdateWork;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentWorkUpdateLocation;
import com.viettel.bss.viettelpos.v4.work.activity.FragmentWorkUpdateLocationMobileSaling;
import com.viettel.bss.viettelpos.v4.work.activity.ManagerRollUpFragment;

import static com.viettel.bss.viettelpos.v4.commons.Constant.CHANGE_POS_TO_PRE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.CHANGE_PRE_TO_POS;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.APPROVE_MONEY;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.APPROVE_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CHANGE_PASS_ANYPAY;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CHANNEL_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CONFIRM_NOTE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.CREATE_INVOICE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.RECHARGE_TO_BANK;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.RETURN_THE_GOOD;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_2G_TO_3G;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_ACTIVE_ACCOUNT_PAYMENT;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_ANYPAY_EXCHANGE;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_ANYPAY_ISDN;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_BHLD;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_BY_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_CREATE_CHANEL;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_DEPOSIT;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_INFO_SEARCH;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_PROMOTION;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_RETAIL;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SEARCH_ISDN;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SEARCH_KIT;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_SEARCH_SERIAL_CARD;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SALE_TO_CUSTOMER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.SEARCH_ANYPAY;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.STAFF_HANDLE_ORDER;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.VIEW_INFO_ORDER_ITEM;
import static com.viettel.bss.viettelpos.v4.commons.Constant.MENU_FUNCTIONS.VIEW_STOCK;

/**
 * Created by Toancx on 2/11/2017.
 */

public class FragmentUtils {
    private final String ID_DAUNOI = "ID_DAUNOI";
    private final String ID_DAUNOIMOI = "ID_DAUNOIMOI";
    private final String ID_MOBILE = "ID_MOBILE";
    private final String ID_SMART_SIM = "ID_SMART_SIM";
    private final String ID_HOTLINE = "ID_HOTLINE";
    private final String REGISTER = "REGISTER";
    private final String ID_VAS = "ID_VAS";
    private final String SEARCH_CUSTOMER = "SEARCH_CUSTOMER";
    private final String REPAIR_ID_NO = "REPAIR_ID_NO";
    private final String BUNDLE_GROUP = "BUNDLE_GROUP";
    private final String CHECK_USUALLY_CALL = "CHECK_USUALLY_CALL";
    private final String VIEW_OCS_HLR = "VIEW_OCS_HLR";
    private final String CHANGE_SIM = "CHANGE_SIM";
    private final String MANAGER_CV = "MANAGER_CV";
    private final String CHANGER_OFFER = "CHANGER_OFFER";
    private final String CHANGE_PROMOTION = "CHANGE_PROMOTION";
    private final String CHANGE_TECH = "CHANGE_TECH";
    private final String ID_MODIFY_HOSO = "ID_MODIFY_HOSO";
    private final String CHANGE_EQUIPMENT = "CHANGE_EQUIPMENT";
    private final String ID_NEW = "ID_NEW";
    private final String ID_RE = "ID_RE";
    private final String ID_DEL = "ID_DEL";
    private final String ID_CONTRACT_PAYMENT = "ID_CONTRACT_PAYMENT";
    private final String ID_CONTRACT_NOT_PAYMENT = "ID_CONTRACT_NOT_PAYMENT";
    private final String ID_CONTRACT_REPORT = "ID_CONTRACT_REPORT";
    private final String ID_UPDATE_PROMOTION = "ID_UPDATE_PROMOTION";
    private final String ID_CHARGE_NOTIFY = "ID_CHARGE_NOTIFY";
    private final String ID_SUPPORT_UPDATE = "ID_SUPPORT_UPDATE";
    private final String ID_UPDATE_CUS = "ID_UPDATE_CUS";
    private final String ID_UPDATE_COMPLAIN = "ID_UPDATE_COMPLAIN";
    private final String ID_CONTRACT_SEARCH = "ID_CONTRACT_SEARCH";
    private final String ID_SMS_CTV = "ID_SMS_CTV";
    private final String ID_DEL_CTV = "ID_DEL_CTV";
    private final String ID_CONTRACT_BANKPLUS = "ID_CONTRACT_BANKPLUS";
    private final String ID_VERIFY_UPDATE = "ID_VERIFY_UPDATE";
    private final String ID_VERIFY_SEARCH = "ID_VERIFY_SEARCH";
    private final String ID_CONTRACT_REPORT_VERIFY = "ID_CONTRACT_REPORT_VERIFY";
    private final String ID_CONTRACT_DELAY = "ID_CONTRACT_DELAY";
    private final String ID_KS_HT_ONLINE = "ID_KS_HT_ONLINE";
    private final String ID_SL_KDT = "ID_SL_KDT";
    private final String REPORT_KIT = "REPORT_KIT";
    private final String REPORT_CHANEL_CARE = "REPORT_CHANEL_CARE";
    private final String REPORT_CHANEL_CARE_EMPLOYEE = "REPORT_CHANEL_CARE_EMPLOYEE";
    private final String REPORT_PROMOTION = "REPORT_PROMOTION";
    private final String REPORT_INVENTORY = "REPORT_INVENTORY";
    private final String REPORT_IMAGE_PAYMENT = "REPORT_IMAGE_PAYMENT";
    private final String REPORT_STAFF_REVENUE_GERENAL = "REPORT_STAFF_REVENUE_GERENAL";
    private final String REPORT_STAFF_INCOME = "REPORT_STAFF_INCOME";
    private final String REPORT_TARGET = "REPORT_TARGET";
    private final String REPORT_GETINVENE_STAFF = "REPORT_GETINVENE_STAFF";
    private final String REPORT_BONUS_VAS = "REPORT_BONUS_VAS";
    private final String REPORT_EXCEPTION_DAILY = "REPORT_EXCEPTION_DAILY";
    private final String ADV_CUSTOMERS = "ADV_CUSTOMERS";

    private Activity mContext;

    public FragmentUtils(Activity mContext) {
        this.mContext = mContext;
    }

    public void replaceFragment(String keyMenuName) {
        Bundle bundle = new Bundle();
        Fragment fragment;
        Intent intent;

        MainActivity.getInstance().enableViews(true);

        switch (keyMenuName) {
            case Constant.MENU_FUNCTIONS.SALE_SALING: {
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALING);
                fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragmentFromMain(mContext, fragment, false);
                break;
            }
            case SALE_DEPOSIT:
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_DEPOSIT);
                fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_BHLD:
                fragment = new FragmentChooseBHLD();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_RETAIL:
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_RETAIL);
                fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_PROMOTION:
                bundle.putInt("functionType", FragmentSaleSaling.FUNCTION_SALE_PROMOTION);
                fragment = new FragmentSaleSaling();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case CONFIRM_NOTE:
                fragment = new FragmentConfirmNote();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case CREATE_INVOICE:
                fragment = new FragmentCreateInvoice();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_BY_ORDER:
                fragment = new FragmentSaleByOrder();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case VIEW_INFO_ORDER_ITEM:
                if (CommonActivity.isNetworkConnected(getActivity())) {
                    GetStockOrderManager getStockOrderManager = new GetStockOrderManager(getActivity());
                    getStockOrderManager.execute();
                } else {
                    Dialog dialog = CommonActivity.createAlertDialog(getActivity(),
                            getActivity().getString(R.string.errorNetwork), getActivity().getString(R.string.app_name));
                    dialog.show();
                }
                break;
            case APPROVE_ORDER:
                fragment = new FragmentListOrder();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_CREATE_CHANEL:
                fragment = new FragmentCreateChanel();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_ACTIVE_ACCOUNT_PAYMENT:
                fragment = new FragmentActiveAccountPayment();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case SALE_INFO_SEARCH:
                fragment = new FragmentInfoSearch();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            case RETURN_THE_GOOD:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSaleSalingReturn(), false);
                break;
            case SALE_TO_CUSTOMER:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSaleToCustomers(), false);
                break;
            case SALE_SEARCH_ISDN:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSearchISDN(), false);
                break;
            case SALE_2G_TO_3G:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChange2GTo3G(), false);
                break;
            case RECHARGE_TO_BANK:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentRechargeToBank(), false);
                break;
            case APPROVE_MONEY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentCheckoutMoney(), false);
                break;
            case SALE_SEARCH_SERIAL_CARD:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSearchSerialCard(), false);
                break;
            case SALE_SEARCH_KIT:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSearchKIT(), false);
                break;
            case SALE_ANYPAY_ISDN:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentAnypayISDN(), false);
                break;
            case SALE_ANYPAY_EXCHANGE:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentAnypayExchange(), false);
                break;
            case VIEW_STOCK:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChooseStaff(), false);
                break;
            case CHANGE_PASS_ANYPAY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChangePassAnypay(), false);
                break;
            case CHANNEL_ORDER:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChannelOrder(), false);
                break;
            case SEARCH_ANYPAY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSearchAnypay(), false);
                break;
            case STAFF_HANDLE_ORDER:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChannelOrderManager(), false);
                break;
            case Constant.MENU_FUNCTIONS.DO_WARRANTY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentWarranty(), false);
                break;
            case ID_DAUNOI:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentConnectionManager(), true);
                break;
            case ID_DAUNOIMOI:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentConnectionManager(), true);
                break;
            case ID_MOBILE:
                intent = new Intent(getActivity(),
                        ActivityCreateNewRequestMobileNew.class);
                getActivity().startActivity(intent);
                break;
            case ID_SMART_SIM:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentCheckChanelSmartSim(), true);
                break;
            case ID_HOTLINE:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentHotlineManager(), true);
                break;
            case REGISTER:
                RegisterInfoFragment registerInfoFragment = new RegisterInfoFragment();
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        registerInfoFragment, false);
                break;
            case ID_VAS:
                intent = new Intent(getActivity(),
                        FragmentRegisterServiceVas.class);
                getActivity().startActivity(intent);
                break;
            case SEARCH_CUSTOMER:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentSearchCustomerMustApprove(), true);
                break;
            case REPAIR_ID_NO:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentPrepairIdNo(), true);
                break;
            case BUNDLE_GROUP:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentManageBundleGroup(), true);
                break;
            case CHECK_USUALLY_CALL:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentCheckUsuallyCall(), true);
                break;
            case CHANGE_SIM:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentSearchSubChangeSim(), true);
                break;
            case VIEW_OCS_HLR:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentViewOcsHlr(), true);
                break;
            case MANAGER_CV:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentSearchCV(), true);
                break;
            case CHANGER_OFFER:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentChangeOffer(), true);
                break;
            case CHANGE_PROMOTION:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentChangePromotion(), true);
                break;
            case CHANGE_TECH:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentManagerChangeTech(), true);
                break;
            case ID_MODIFY_HOSO:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentModifyProfile(), true);
                break;
            case Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO:
                fragment = new FragmentLookupDebitInfo();

                bundle = new Bundle();
                bundle.putString(Constant.FUNCTION,
                        Constant.MENU_FUNCTIONS.LOOKUP_DEBIT_INFO);
                fragment.setArguments(bundle);

                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, true);
                break;
            case Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL:
                fragment = new FragmentLookupDebitInfo();

                bundle = new Bundle();
                bundle.putString(Constant.FUNCTION,
                        Constant.MENU_FUNCTIONS.UPDATE_ISDN_EMAIL);
                fragment.setArguments(bundle);

                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, true);
                break;
            case CHANGE_EQUIPMENT:
                ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy = new ChangeEquimentFragmentStrategy();
                bundle = new Bundle();
                bundle.putSerializable("fragmentStrategy",
                        manageSubscriberFragmentStrategy);

                FragmentManageSubscriber fragmentManageSubscriber = new FragmentManageSubscriber();
                fragmentManageSubscriber.setArguments(bundle);
//                fragmentManageSubscriber.setTargetFragment(CustomerManagerFragment, 100);
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        fragmentManageSubscriber, false);
                break;
            case Constant.MENU_FUNCTIONS.LOOKUP_PROFILE_TRANSACTION:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentLookupProfile(), true);
                break;
            case CHANGE_PRE_TO_POS:
                FragmentSearchSubChangeSim fragmentSearchSubPos = new FragmentSearchSubChangeSim();
                bundle = new Bundle();
                bundle.putString("functionKey", Constant.CHANGE_PRE_TO_POS);
                fragmentSearchSubPos.setArguments(bundle);

                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentSearchSubPos, true);
                break;

            case CHANGE_POS_TO_PRE:
                FragmentSearchSubChangeSim fragmentSearchSubPre = new FragmentSearchSubChangeSim();
                bundle = new Bundle();
                bundle.putString("functionKey", Constant.CHANGE_POS_TO_PRE);
                fragmentSearchSubPre.setArguments(bundle);

                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentSearchSubPre, true);
                break;
            case ID_NEW:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChargeNew(), true);
                break;
            case ID_RE:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChargeRe(), true);
                break;
            case ID_DEL:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChargeDel(), true);
                break;
            case ID_CONTRACT_PAYMENT:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractPayment(), true);
                break;
            case ID_CONTRACT_NOT_PAYMENT:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractNotPayment(), true);
                break;
            case ID_CONTRACT_REPORT:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractReport(), true);
                break;
            case ID_UPDATE_PROMOTION:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentUpdatePromotion(), true);
                break;
            case ID_CHARGE_NOTIFY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChargeNotify(), true);
                break;
            case ID_SUPPORT_UPDATE:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSuportUpdate(), true);
                break;
            case ID_UPDATE_COMPLAIN:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentUpdateComplain(), true);
                break;
            case ID_CONTRACT_SEARCH:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractSearch(), true);
                break;
            case ID_SMS_CTV:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentSendSmsCtv(), true);
                break;
            case ID_VERIFY_UPDATE:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractVerifyUpdate(), true);
                break;
            case ID_VERIFY_SEARCH:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractVerifySearch(), true);
                break;
            case ID_CONTRACT_REPORT_VERIFY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractReportVerify(), true);
                break;
            case ID_CONTRACT_DELAY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentContractDelay(), true);
                break;
            case ID_DEL_CTV:
//                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChargeDelCTV(), true);
                Intent intent1 = new Intent(getActivity(), FragmentChargeDelCTV.class);
//			intent.putExtra("SUB_BEAN_ISDN", subBeanBO.getIsdn());
                mContext.startActivity(intent1);
                break;
            case ID_CONTRACT_BANKPLUS:
                ReplaceFragment.replaceFragmentFromMain(getActivity(), new FragmentChargeDelBankPlus(), true);
                break;
            case Constant.MENU_FUNCTIONS.CHANNEL_CARE:
                bundle = new Bundle();
                bundle.putSerializable("frommenu", true);
                fragment = new FragmentListChannel();
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, true);
                break;
            case ID_KS_HT_ONLINE:
                FragmentInfoIDNumberManager idNumberManager = new FragmentInfoIDNumberManager();
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        idNumberManager, false);
                break;
            case ID_SL_KDT:
                FragmentInfrastructureOnline mInfrastructureOnline = new FragmentInfrastructureOnline();
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        mInfrastructureOnline, false);
                break;
            case Constant.JobMenu.WORK_COLLECT_INFO:
                FragmentCriteria mFragmentCriteria = new FragmentCriteria();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), mFragmentCriteria,
                        true);
                break;
            case Constant.JobMenu.WORK_COMMUNICATION:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentListChannel_fromNoti(true), true);
                break;
            case Constant.JobMenu.WORK_UPDATE:
                FragmentUpdateWork fragmentUpdateWork = new FragmentUpdateWork();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentUpdateWork,
                        false);
                break;
            case Constant.JobMenu.ASIGN_JOB: {
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Define.VIEW_ROUTER, false);
                FragmentListStaffJobAssignment mAssignment = new FragmentListStaffJobAssignment();
                mAssignment.setArguments(mBundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), mAssignment, false);
                break;
            }
            case Constant.JobMenu.WORK_TASK_ROAD: {
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Define.VIEW_ROUTER, true);
                FragmentListStaffJobAssignment mAssignment = new FragmentListStaffJobAssignment();
                mAssignment.setArguments(mBundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), mAssignment, false);
                break;
            }
            case Constant.JobMenu.PERMISSION_WORK_UPDATE_TASK_LOCATION: {
                FragmentWorkUpdateLocation frag = new FragmentWorkUpdateLocation();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), frag, false);
                break;
            }
            case Constant.JobMenu.PERMISSION_WORK_UPDATE_BHLD_LOCATION: {
                FragmentWorkUpdateLocationMobileSaling frag = new FragmentWorkUpdateLocationMobileSaling();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), frag, false);

                break;
            }
            case Constant.JobMenu.WORK_TASK_ASSIGN_MANAGER: {
                fragment = new FragmentTaskAssignManager();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);

                break;
            }
            case Constant.JobMenu.WORK_TASK_ASSIGN_STAFF: {
                fragment = new FragmentTaskAssignStaff();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);

                break;
            }
            case Constant.JobMenu.WORK_TASK_UPDATE_AREA: {
                fragment = new FragmentUpdateArea();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            }
            case Constant.JobMenu.WORK_TASK_UPDATE_FAMILY: {
                fragment = new FragmentUpdateArea();
                bundle = new Bundle();
                bundle.putString("updatefamily", "1");
                fragment.setArguments(bundle);
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragment, false);
                break;
            }
            case Constant.JobMenu.COLLECT_CUS_INFO:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentCollectCusInfo(), true);
                break;
            case REPORT_KIT:
                FragmentReport synfragment = new FragmentReport();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), synfragment, true);
                break;
            case REPORT_CHANEL_CARE:
                FragmentReportCarePopup fragmentReportCarePopup = new FragmentReportCarePopup();
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        fragmentReportCarePopup, true);
                break;
            case REPORT_CHANEL_CARE_EMPLOYEE:
                FragmentReportForEmployee fragEmployee = new FragmentReportForEmployee();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragEmployee, true);
                break;
            case REPORT_PROMOTION:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentReportPromotion(), true);
                break;
            case REPORT_INVENTORY:
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        new FragmentReportInventory(), true);
                break;
            case REPORT_IMAGE_PAYMENT:
                FragmentReportImagePayment fragmentReportImagePayment = new FragmentReportImagePayment();
                ReplaceFragment.replaceFragmentFromMain(getActivity(),
                        fragmentReportImagePayment, true);
                break;
            case REPORT_STAFF_INCOME:
                FragmentReportStaffIncome fragmentReportStaffIncome = new FragmentReportStaffIncome();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentReportStaffIncome, true);
                break;
            case REPORT_STAFF_REVENUE_GERENAL:
                FragmentReportRevenueGeneral fragmentReportRevenueGeneral = new FragmentReportRevenueGeneral();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentReportRevenueGeneral, true);
                break;
            case REPORT_TARGET:
                FragmentReportTarget fragmentReportTargetr = new FragmentReportTarget();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentReportTargetr, true);
                break;
            case REPORT_GETINVENE_STAFF:
                FragmentReportGetInveneStaff fragmentReportGetInveneStaff = new FragmentReportGetInveneStaff();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentReportGetInveneStaff, true);
                break;
            case REPORT_BONUS_VAS:
                FragmentReportBonusVAS fragmentReportBonusVAS = new FragmentReportBonusVAS();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentReportBonusVAS, true);
                break;
            case REPORT_EXCEPTION_DAILY:
                FragmentLookupLogMBCCS fragmentLookupLogMBCCS = new FragmentLookupLogMBCCS();
                ReplaceFragment.replaceFragmentFromMain(getActivity(), fragmentLookupLogMBCCS, true);
                break;
            case Constant.JobMenu.ROLL_UP:
                Intent i = new Intent(getActivity(), ManagerRollUpFragment.class);
                getActivity().startActivity(i);
                break;
            case Constant.MENU_FUNCTIONS.UPDATE_GIFT:
                ReplaceFragment.replaceFragment(getActivity(), new FragmentUpdateGift(), false);
                break;
            case Constant.MENU_FUNCTIONS.RECEIVE_COMPLAIN_CUSTOMNER:
                ReplaceFragment.replaceFragment(getActivity(), new ComplainFragment(), false);
                break;
            case Constant.MENU_FUNCTIONS.CHANGE_PROMOTION_BCCS:
                ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy1 = new ChangePromotionFragmentStrategy();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("fragmentStrategy",
                        manageSubscriberFragmentStrategy1);
                FragmentManageSubscriber fragmentManageSubscriber1 = new FragmentManageSubscriber();
                fragmentManageSubscriber1.setArguments(bundle1);
//                fragmentManageSubscriber1.setTargetFragment(CustomerManagerFragment.this, 100);
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentManageSubscriber1, false);
                break;
            case Constant.MENU_FUNCTIONS.CHANGE_PRODUCT_BCCS:
                ManageSubscriberFragmentStrategy manageSubscriberFragmentStrategy2 = new ChangeProductFragmentStrategy();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("fragmentStrategy",
                        manageSubscriberFragmentStrategy2);
                FragmentManageSubscriber fragmentManageSubscriber2 = new FragmentManageSubscriber();
                fragmentManageSubscriber2.setArguments(bundle2);
//                fragmentManageSubscriber2.setTargetFragment(CustomerManagerFragment.this, 100);
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentManageSubscriber2, false);
                break;
            case Constant.MENU_FUNCTIONS.CARE_LOST_SUB:
                ReplaceFragment.replaceFragment(getActivity(),
                        new FragmentCareLostSub(), true);
                break;
            case Constant.MENU_FUNCTIONS.COLLECT_BUSSINESS:
                ReplaceFragment.replaceFragment(getActivity(),
                        new FragmentSearchComporation(), true);
                break;

            case Constant.MENU_FUNCTIONS.TICK_APPOINT:
                ManageSubscriberFragmentStrategy manageSubscriberFragment = new TickAppointFragmentStrategy();
                Bundle bundle3 = new Bundle();
                bundle3.putSerializable("fragmentStrategy",
                        manageSubscriberFragment);
                FragmentManageSubscriber fragmentManage = new FragmentManageSubscriber();
                fragmentManage.setArguments(bundle3);
//                fragmentManage.setTargetFragment(CustomerManagerFragment.this, 100);
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentManage, false);
                break;
            case Constant.MENU_FUNCTIONS.REGISTER_VAS_SAFENET_BCCS:
                ManageSubscriberFragmentStrategy manageS= new RegisterSafeNetFragmentStrategy();
                Bundle bundle4 = new Bundle();
                bundle4.putSerializable("fragmentStrategy",
                        manageS);
                FragmentManageSubscriber fragmentManageSubscriber12 = new FragmentManageSubscriber();
                fragmentManageSubscriber12.setArguments(bundle4);
//                fragmentManageSubscriber12.setTargetFragment(CustomerManagerFragment.this, 100);
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentManageSubscriber12, false);
                break;
            case ADV_CUSTOMERS:
                ReplaceFragment.replaceFragment(getActivity(), new SearchAdvisoryFragment(), false);
                break;
            case Constant.BLOCK_OPEN_SUB_MOBILE:
                FragmentSearchSubChangeSim fragmentSearchSubMobile = new FragmentSearchSubChangeSim();
                bundle = new Bundle();
                bundle.putString("functionKey", Constant.BLOCK_OPEN_SUB_MOBILE);
                fragmentSearchSubMobile.setArguments(bundle);

                ReplaceFragment.replaceFragment(getActivity(), fragmentSearchSubMobile, true);
                break;
            case Constant.BLOCK_OPEN_SUB_HOMEPHONE:
                ManageSubscriberFragmentStrategy blockOpenSubHome = new BlockOpenSubManagerFragment();
                bundle = new Bundle();
                bundle.putSerializable("fragmentStrategy",
                        blockOpenSubHome);
                FragmentManageSubscriber fragmentManage1 = new FragmentManageSubscriber();
                fragmentManage1.setArguments(bundle);
                ReplaceFragment.replaceFragment(getActivity(),
                        fragmentManage1, false);
                break;
            case Constant.ID_SEARCH_ORDER:
            case Constant.ID_CLAIM_ORDER:
                OmniManagerFragment omniManagerFragment = new OmniManagerFragment();
                ReplaceFragment.replaceFragment(getActivity(), omniManagerFragment, true);
                break;
            default:
                break;
        }
    }

    private Activity getActivity() {
        return mContext;
    }
}
