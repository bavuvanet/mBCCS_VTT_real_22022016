package com.viettel.bss.viettelpos.v4.commons.auto.template;


import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.GPSTrackerFragment;
import com.viettel.bss.viettelpos.v4.commons.auto.AutoConst;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityTemplate extends GPSTrackerFragment implements OnClickListener {
    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;
    private String mScreen;
    private String mService;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_template);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle mBundle = this.getIntent().getExtras();
        if (mBundle != null) {
            mScreen = mBundle.getString(AutoConst.FROM_SCREEN, "");
            mService = mBundle.getString(AutoConst.SERVICE, "");
        }

        unitView();
    }

    // create unitView
    private void unitView() {
        mTabHost = (FragmentTabHost) findViewById(R.id.tabs);

        mTabHost.setup(this, getSupportFragmentManager());
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Thường Dùng"), Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Lịch Sử"), Fragment.class, null);

        adapter = new TabPagerAdapter(getSupportFragmentManager(), mScreen, mService);
        adapter.setTitles(new String[]{"Thường Dùng", "Lịch Sử"});

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                mTabHost.setCurrentTab(i);
                System.out.println("12345 onPageSelected : " + i);
                TemplateFragment frag1 = (TemplateFragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
                frag1.loadData(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = mTabHost.getCurrentTab();
                viewPager.setCurrentItem(i);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.header_template);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relaBackHome:
                onBackPressed();
                break;
            default:
                break;
        }
    }
}
