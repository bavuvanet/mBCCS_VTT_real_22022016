package com.viettel.bss.viettelpos.v4.channel.activity;



import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.channel.adapter.AdapterStaff;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.channel.dal.ChannelTypeDAL;
import com.viettel.bss.viettelpos.v4.channel.object.ChannelTypeOJ;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

public class DiaLogChooseChanel extends Activity implements OnItemClickListener{


    private ArrayList<ChannelTypeOJ> arrChannelType;

    private ChannelDAL channelDAL;
	private ChannelTypeDAL channelTypeDAL;
	

	private boolean loadmore = false, loadOk = false;
	private int pos = 0;
	private final int staff_type = 0;
	private String inputSearchName;

	private View footer;
	private EditText searchChannel;
	
	private int totalRow = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_staff_layout);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        ListView lvListStaff = (ListView) findViewById(R.id.lv_staff);
        Spinner spChannelType = (Spinner) findViewById(R.id.sp_list_channel_type);

        ArrayList<Staff> arrayStaff = new ArrayList<>();
        AdapterStaff mAdapterStaff = new AdapterStaff(arrayStaff, getApplicationContext(), AdapterStaff.TYPE_LOCATION);
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
	}
	
	
	
}
