package com.viettel.bss.viettelpos.v4.connecttionService.listener;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class DummyTabContent implements TabContentFactory{
	private final Context mContext;
	
	public DummyTabContent(Context context){
		mContext = context;
	}
			

	@Override
	public View createTabContent(String tag) {


        return new View(mContext);
	}
	

}
