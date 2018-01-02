package com.viettel.bss.viettelpos.v4.customer.manage;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.customer.adapter.CustomerPagerAdapter;
import com.viettel.bss.viettelpos.v4.ui.image.picker.SlidingTabText;

public class FragmentCustomersTabInfoRequirement extends Fragment implements OnClickListener {

	private Button btnHome;
	private TextView txtNameActionBar;
	private View mView;
	private Activity activity;
	private ViewPager mPager;
	private SlidingTabText slideTabtext;
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		activity = getActivity();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (mView == null) {
			mView = inflater.inflate(R.layout.layout_customers_tabinfo_requirement_success, container, false);
			mPager = (ViewPager) mView.findViewById(R.id.view_pager);
			slideTabtext = (SlidingTabText) mView.findViewById(R.id.view_sliding_tabs);
		} 
		unitView();

		return mView;
	} 

	private void unitView() { 
		mPager.setAdapter(new CustomerPagerAdapter(getFragmentManager())); 
//		slideTabtext.setSelectedIndicatorColors(getResources().getColor(mConfig.getTabSelectionIndicatorColor()));
		slideTabtext.setCustomTabView(R.layout.pp__tab_view_text, R.id.pp_tab_text);
//		slideTabtext.setTabStripColor(mConfig.getTabBackgroundColor());
//		slideTabtext.setTabTitles(getResources().getStringArray(R.array.tab_info_contract));
		slideTabtext.setViewPager(mPager); 
	}
	
	@Override
	public void onResume() { 
		super.onResume();
        MainActivity.getInstance().setTitleActionBar(getResources().getString(R.string.add_news_chanel));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnHome:
			CommonActivity.callphone(activity, Constant.phoneNumber);
			break;
		case R.id.relaBackHome:
			activity.onBackPressed();
			break;

		default:
			break;
		}
		
	}

}
