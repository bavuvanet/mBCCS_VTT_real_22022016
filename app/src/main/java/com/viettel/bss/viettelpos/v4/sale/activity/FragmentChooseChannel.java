package com.viettel.bss.viettelpos.v4.sale.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.sale.adapter.StaffAdapter;
import com.viettel.bss.viettelpos.v4.sale.business.ChannelTypeBusiness;
import com.viettel.bss.viettelpos.v4.sale.business.StaffBusiness;
import com.viettel.bss.viettelpos.v4.sale.object.ChannelTypeObject;
import com.viettel.bss.viettelpos.v4.sale.object.Staff;

import java.util.ArrayList;
import java.util.List;

public class FragmentChooseChannel extends Fragment implements OnClickListener {

    private View view;
    private View btnOK = null;
    private View btnCancel;
    private View prb;
    private ListView lvStaff;
    private Long channelTypeId;
    private Double x;
    private Double y;
    private ChannelTypeObject channelType;
    private EditText edtStaffFilter;
	private ArrayList<Staff> lstStaff = new ArrayList<>();
    private StaffAdapter adapter;
    private LoadStaffByChannel loadStaff;
    private TextView txtNameActionBar;

	private boolean firstLoad = true;
    private Button btnHome;
    private Long pointOfSale = null;
	private String[] replace;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.choose_channel_layout, container,
                    false);
            btnOK = view.findViewById(R.id.btnOk);
            btnOK.setOnClickListener(this);
            btnCancel = view.findViewById(R.id.btnViewSaleTrans);
            btnCancel.setOnClickListener(this);
            replace = getActivity().getResources().getStringArray(
                    R.array.dUnicode);
            prb = view.findViewById(R.id.prbLoadStaff);
            lvStaff = (ListView) view.findViewById(R.id.lvStaff);
            edtStaffFilter = (EditText) view.findViewById(R.id.edtSearchStaff);
            Spinner spiChannel = (Spinner) view.findViewById(R.id.spiChannel);
            final List<ChannelTypeObject> lstChannel = ChannelTypeBusiness
                    .getAllChannel(getActivity());
            if (lstChannel != null) {
                ChannelTypeObject channel = new ChannelTypeObject();
                channel.setChannelTypeId(-1L);
                channel.setName(getResources().getString(
                        R.string.selectAllChannel));
                lstChannel.add(0, channel);
            }
			ArrayAdapter<String> channelApdater = new ArrayAdapter<>(
                    getActivity(), R.layout.spinner_item);
			ArrayList<String> arrChannel = new ArrayList<>();
            for (ChannelTypeObject channelTypeObject : lstChannel) {
                channelApdater.add(channelTypeObject.getName());
                arrChannel.add(channelTypeObject.getName());
            }

            spiChannel.setAdapter(channelApdater);

            spiChannel.setOnItemSelectedListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                    ChannelTypeObject item = lstChannel.get(arg2);
                    pointOfSale = null;
                    if (item.getChannelTypeId().compareTo(-1L) == 0) {
                        channelTypeId = null;
                        channelType = null;

                    } else {
                        channelTypeId = item.getChannelTypeId();
                        channelType = item;
                        // channel_type_id =80043: kenh diem ban fix
                        // channel_type_id =10,
                        // point_of_sale = 1
                        if (Constant.CHANNEL_TYPE_POS.compareTo(channelTypeId) == 0) {
                            channelTypeId = Constant.CHANNEL_TYPE_COLLABORATOR;
                            pointOfSale = Constant.POINT_OF_SALE_TYPE;
                        } else if (Constant.CHANNEL_TYPE_COLLABORATOR
                                .compareTo(channelTypeId) == 0) {
                            // channel_type_id =10: kenh NVDB fix
                            // channel_type_id =10,
                            // point_of_sale = 2
                            pointOfSale = Constant.POINT_OF_SALE_COLLABORATOR;
                        }
                    }

                    // lstStaff.clear();
                    // loadStaff = new LoadStaffByChannel();
                    // prb.setVisibility(View.VISIBLE);
                    // lvStaff.setVisibility(View.GONE);
                    // loadStaff.execute();
                    edtStaffFilter.setText("");
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {

                }
            });
            lvStaff.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int arg2, long arg3) {
                    Intent i = new Intent();
                    Staff staff = lstStaff.get(arg2);
                    i.putExtra("channelType", channelType);
                    i.putExtra("staff", staff);
                    getTargetFragment().onActivityResult(
                            getTargetRequestCode(), Activity.RESULT_OK, i);
                    getActivity().onBackPressed();
                }
            });
            edtStaffFilter.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1,
                                              int arg2, int arg3) {

                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    if (loadStaff != null) {
                        loadStaff.cancel(true);
                    }
                    lstStaff.clear();
                    loadStaff = new LoadStaffByChannel();
                    loadStaff.execute();
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance().setTitleActionBar(R.string.choose_channel);
    }

    @Override
    public void onStart() {
        Log.e("tag", "onStart");
        super.onStart();
    }

    @Override
    public void onPause() {
        Log.e("tag", "onPause");
        // CommonActivity.hideKeyboard(context)
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("tag", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        if (loadStaff != null) {
            loadStaff.cancel(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnOK) {
            Intent i = new Intent();

            i.putExtra("channelTypeId", channelTypeId);
            i.putExtra("channelType", channelType);


            getTargetFragment().onActivityResult(getTargetRequestCode(),
                    Activity.RESULT_OK, i);
            getActivity().onBackPressed();

        } else if (v == btnCancel) {
            getTargetFragment().onActivityResult(getTargetRequestCode(),
                    Activity.RESULT_CANCELED, new Intent());
            getActivity().onBackPressed();
        } else {
            switch (v.getId()) {
                case R.id.btnHome:
                    CommonActivity.callphone(getActivity(), Constant.phoneNumber);
//				FragmentMenuHome fragmentMenuHome = new FragmentMenuHome();
//				ReplaceFragment.replaceFragment(getActivity(),
//						fragmentMenuHome, true);
//				getActivity().onBackPressed();
                    break;
                case R.id.relaBackHome:
                    getActivity().onBackPressed();
                    break;
                default:
                    break;
            }
        }
    }

    private class LoadStaffByChannel extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
            int count = 100;
            int start = 0;
            lstStaff = StaffBusiness.getStaffByChannel(getActivity(),
                    channelTypeId, pointOfSale, edtStaffFilter.getText()
                            .toString(), start, count, x, y, replace);
            return Constant.SUCCESS_CODE;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            prb.setVisibility(View.GONE);
            lvStaff.setVisibility(View.VISIBLE);
            if (firstLoad) {
                if (lstStaff != null && !lstStaff.isEmpty()) {
                    adapter = new StaffAdapter(getActivity(), lstStaff);
                    lvStaff.setAdapter(adapter);
                    firstLoad = false;
                }
            } else {
                adapter.setLstData(lstStaff);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
