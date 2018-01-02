package com.viettel.bss.viettelpos.v4.commons.auto.template;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;
import com.viettel.bss.viettelpos.v4.commons.auto.LogUtils;
import com.viettel.bss.viettelpos.v4.helper.DatabaseHelper;
import com.viettel.savelog.SaveLog;

import org.greenrobot.eventbus.EventBus;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static com.viettel.bss.viettelpos.v4.commons.Session.userName;

/**
 * Created by diepdc-pc on 7/27/2017.
 */

public class TemplateFragment extends Fragment {
    private ListView mListView;
    private int mPos = 0;
    private DatabaseHelper mDBHelper;
    private String mSceen = "";
    private String mService = "";

    public static TemplateFragment newInstance(int p, String sc, String service) {
        TemplateFragment detailedFragment = new TemplateFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("pos", p);
        bundle.putString("sc", sc);
        bundle.putString("service", service);
        detailedFragment.setArguments(bundle);

        return detailedFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutInflater localInflater = getActivity().getLayoutInflater();

        mPos = getArguments().getInt("pos", 0);
        mSceen = getArguments().getString("sc", "");
        mService = getArguments().getString("service", "");

        return localInflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListView = (ListView) view.findViewById(R.id.lv);
        mDBHelper = new DatabaseHelper(getContext());
        loadData(mPos);
    }

    private void checkList(int type, ArrayList<Map<String, String>> list) {
        if (list == null) return;

        // get template by service name
        if (!TextUtils.isEmpty(mService)) {
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);

                String service = map.get(AutoConst.SERVICE);
                if (service != null && !service.equals(mService)) {
                    list.remove(i);
                }
            }
        }

        for (int i = list.size() - 1; i >= 0; i--) {
            Map<String, String> map = list.get(i);
            String id = map.get(AutoConst.TEMPLATE_ID);

            if (type == 0 && list.size() > AutoConst.MAX_TD) { //thường dùng
                mDBHelper.deleteTemplate(id);
                list.remove(0);
            } else if (type == 1 && list.size() > AutoConst.MAX_LS) { //lịch sử
                mDBHelper.deleteTemplate(id);
                list.remove(0);
            } else {
                //nothing to do
            }
        }
    }

    private void SaveLog() {
        try {
            String requestId = userName + "_" + CommonActivity.getDeviceId(getActivity()) + "_" + System.currentTimeMillis();
            SaveLog saveLog = new SaveLog(getActivity(),
                    Constant.SYSTEM_NAME, userName, "SELECT_TEMP",
                    CommonActivity.findMyLocation(getActivity()).getX(),
                    CommonActivity.findMyLocation(getActivity()).getY());
            saveLog.saveLogRequest("SELECT_TEMP", new Date(), new Date(), requestId);
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }

    private View.OnClickListener mSelectClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                SaveLog();

                Map<String, String> m = (Map<String, String>) v.getTag();
                CallbackObj callbackObj = new CallbackObj();
                callbackObj.map = m;

                EventBus.getDefault().post(callbackObj);

                getActivity().finish();
            }
        }
    };
    private View.OnClickListener mDeleteClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getTag() != null) {
                final String tmpId = (String) v.getTag();
                if (mPos == 0) {
                    mTempId = tmpId;
                    Dialog dialog = CommonActivity.createDialog(getActivity(), getActivity().getString(R.string.delete_template), "Xác nhận", getActivity().getString(R.string.cancel), getActivity().getString(R.string.ok)
                            , null, okClick);
                    dialog.show();
                } else {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDBHelper.updateTemplate(Session.userName, mSceen, tmpId, AutoConst.EVERYDAY);
                            loadData(mPos);
                        }
                    });
                    Toast.makeText(getContext(), getActivity().getString(R.string.ls_td), Toast.LENGTH_LONG).show();
                }
            }
        }
    };
    private String mTempId;
    private View.OnClickListener okClick = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            mDBHelper.deleteTemplate(mTempId);
            loadData(mPos);
        }
    };

    public void loadData(int pos) {
        mPos = pos;
        System.out.println("12345 loadData : " + mPos);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                ArrayList<Map<String, String>> list = mDBHelper.loadAllTemplateByKey(Session.userName, mSceen, mPos);
                try {
                    checkList(mPos, list);

                    TemplateAdapter templateAdapter = new TemplateAdapter(getActivity(), mPos, list, mDeleteClick, mSelectClick);
                    mListView.setAdapter(templateAdapter);
                    templateAdapter.notifyDataSetChanged();

                    if (templateAdapter.getCount() == 0) {
                        getView().findViewById(R.id.tv_notemplate).setVisibility(View.VISIBLE);
                    } else {
                        getView().findViewById(R.id.tv_notemplate).setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    LogUtils.printStackTrace(e);
                }
            }
        });
    }
}