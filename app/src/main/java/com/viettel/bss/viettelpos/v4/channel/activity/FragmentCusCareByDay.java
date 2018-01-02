package com.viettel.bss.viettelpos.v4.channel.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.LoginActivity;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.CusCareWizardModel;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.AbstractWizardModel;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.ModelCallbacks;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.model.Page;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.ui.PageFragmentCallbacks;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.ui.ReviewFragment;
import com.viettel.bss.viettelpos.v4.charge.wizardpager.wizard.ui.StepPagerStrip;
import com.viettel.bss.viettelpos.v4.commons.BCCSGateway;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.customer.object.BOCOutput;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentCusCareByDay extends AppCompatActivity
		implements PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks {

	private static final String TAG = FragmentCusCareByDay.class.getSimpleName();
	private ViewPager mPager;
	private MyPagerAdapter mPagerAdapter;

	private boolean mEditingAfterReview;

	private AbstractWizardModel mWizardModel;

	private boolean mConsumePageSelectedEvent;

	private Button mNextButton;
	private Button mPrevButton;

	private List<Page> mCurrentPageSequence;
	private StepPagerStrip mStepPagerStrip;
	public static Staff staff = null;
	public static Long mark2SellingContactHisId = null;
	private static boolean beginCareCus = true;
	public static boolean updateImage = false;
	public static boolean supportChannel = false;
	public static int careCus = Constant.BOC2.STATUS_CARE_NOT_SALE; //mac dinh la cham soc khong ban hang
	private static FragmentCusCareByDay act;
	private Long careNumber = 0L;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

	public static Activity getInstance() {
		// if(act==null)
		return act;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_cus_care_by_day);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		act = this;

		mWizardModel = new CusCareWizardModel(getApplicationContext());
		if (savedInstanceState != null) {
			mWizardModel.load(savedInstanceState.getBundle("model"));
		}
		mWizardModel.registerListener(this);

		staff = (Staff) getIntent().getExtras().get(Define.KEY_STAFF);
		careNumber = staff.getCareNumber();
		mark2SellingContactHisId = getIntent().getExtras().getLong(Define.MARK_SELLING_CONTACT_HIS_ID);
		beginCareCus = true;
		careCus = Constant.BOC2.STATUS_CARE_NOT_SALE;
		updateImage = false;
		supportChannel = false;

		

		mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mPagerAdapter);
		mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
		mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
			@Override
			public void onPageStripSelected(int position) {
				position = Math.min(mPagerAdapter.getCount() - 1, position);
				if (mPager.getCurrentItem() != position) {
					mPager.setCurrentItem(position);
				}
			}
		});

		mNextButton = (Button) findViewById(R.id.next_button);
		mPrevButton = (Button) findViewById(R.id.prev_button);

		mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				mStepPagerStrip.setCurrentPage(position);

				if (mConsumePageSelectedEvent) {
					mConsumePageSelectedEvent = false;
					return;
				}

				mEditingAfterReview = false;
				updateBottomBar();
			}
		});

		mNextButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
					Log.d(TAG, "careNumber = " + careNumber);
					if(careNumber == null || careNumber.equals(0L)){ //truong hop cham soc lan dau bat buoc cap nhat hinh anh diem ban va cham soc kenh phan phoi
						if(!updateImage){
							CommonActivity.createAlertDialog(FragmentCusCareByDay.this,
									getResources().getString(R.string.txt_update_image_channel_required),
									getResources().getString(R.string.app_name))
									.show();
							return;
						}

						if(!supportChannel){
							CommonActivity.createAlertDialog(FragmentCusCareByDay.this,
									getResources().getString(R.string.txt_support_channel_required),
									getResources().getString(R.string.app_name))
									.show();
							return;
						}
					} else {
						if(!supportChannel){
							CommonActivity.createAlertDialog(FragmentCusCareByDay.this,
									getResources().getString(R.string.txt_support_channel_required),
									getResources().getString(R.string.app_name))
									.show();
							return;
						}
					}

					Dialog dialog = CommonActivity.createDialog(FragmentCusCareByDay.this,
							getResources().getString(R.string.txt_end_care_cus),
							getResources().getString(R.string.app_name),getResources().getString(R.string.cancel) , getResources().getString(R.string.ok), null , endCareCusClick);
					dialog.show();
				} else {
					if (mEditingAfterReview) {
						mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
					} else {
						mPager.setCurrentItem(mPager.getCurrentItem() + 1);
					}
				}
			}
		});

		mPrevButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mPager.setCurrentItem(mPager.getCurrentItem() - 1);
			}
		});

		onPageTreeChanged();
		updateBottomBar();
	}

	private final OnClickListener endCareCusClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			new AsyUpdateSalePointsCare(FragmentCusCareByDay.this).execute();
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        getSupportActionBar().setTitle(R.string.txt_cham_soc_diem_ban);
	}

	private void updateBottomBar() {
		Log.d(TAG, "updateBottomBar");
		int position = mPager.getCurrentItem();
		if (position == mCurrentPageSequence.size()) {
			mNextButton.setText(R.string.button_update);
			mNextButton.setBackgroundResource(R.drawable.finish_background);
			mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
		} else {
			mNextButton.setText(mEditingAfterReview ? R.string.review : R.string.next);
			mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
			TypedValue v = new TypedValue();
			getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);
			mNextButton.setTextAppearance(this, v.resourceId);
			mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
		}

		mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onDestroy");
		super.onDestroy();
		mWizardModel.unregisterListener(this);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(outState);
		outState.putBundle("model", mWizardModel.save());
	}

	@Override
	public void onPageDataChanged(Page page) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onPageDataChanged");
		if (page.isRequired()) {
			if (recalculateCutOffPage()) {
				mPagerAdapter.notifyDataSetChanged();
				updateBottomBar();
			}
		}
	}

	@Override
	public void onPageTreeChanged() {
		Log.d(TAG, "onPageTreeChanged");
		// TODO Auto-generated method stub
		mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
		recalculateCutOffPage();
		mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 =
																		// review
																		// step
		mPagerAdapter.notifyDataSetChanged();
		updateBottomBar();
	}

	@Override
	public void onEditScreenAfterReview(String key) {
		Log.d(TAG, "onEditScreenAfterReview");
		// TODO Auto-generated method stub
		for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
			if (mCurrentPageSequence.get(i).getKey().equals(key)) {
				mConsumePageSelectedEvent = true;
				mEditingAfterReview = true;
				mPager.setCurrentItem(i);
				updateBottomBar();
				break;
			}
		}
	}

	@Override
	public AbstractWizardModel onGetModel() {
		// TODO Auto-generated method stub
		Log.d(TAG, "onGetModel");
		return mWizardModel;
	}

	@Override
	public Page onGetPage(String key) {
		// TODO Auto-generated method stub
		Log.d(TAG, "onGetPage");
		return mWizardModel.findByKey(key);
	}

	private boolean recalculateCutOffPage() {
		Log.d(TAG, "recalculateCutOffPage");
		// Cut off the pager adapter at first required page that isn't completed
		int cutOffPage = mCurrentPageSequence.size() + 1;
		for (int i = 0; i < mCurrentPageSequence.size(); i++) {
			Page page = mCurrentPageSequence.get(i);
			if (page.isRequired() && !page.isCompleted()) {
				cutOffPage = i;
				break;
			}
		}

		if (mPagerAdapter.getCutOffPage() != cutOffPage) {
			mPagerAdapter.setCutOffPage(cutOffPage);
			return true;
		}

		return false;
	}

	public class MyPagerAdapter extends android.support.v4.app.FragmentStatePagerAdapter {
		private int mCutOffPage;
		private Fragment mPrimaryItem;

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			if (i >= mCurrentPageSequence.size()) {
				return new ReviewFragment();
			}

			return mCurrentPageSequence.get(i).createFragment();
		}

		@Override
		public int getItemPosition(Object object) {
			// TODO: be smarter about this
			if (object == mPrimaryItem) {
				// Re-use the current fragment (its position never changes)
				return POSITION_UNCHANGED;
			}

			return POSITION_NONE;
		}

		@Override
		public void setPrimaryItem(ViewGroup container, int position, Object object) {
			super.setPrimaryItem(container, position, object);
			mPrimaryItem = (Fragment) object;
		}

		@Override
		public int getCount() {
			if (mCurrentPageSequence == null) {
				return 0;
			}
			return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
		}

		public void setCutOffPage(int cutOffPage) {
			if (cutOffPage < 0) {
				cutOffPage = Integer.MAX_VALUE;
			}
			mCutOffPage = cutOffPage;
		}

		public int getCutOffPage() {
			return mCutOffPage;
		}
	}

	private class AsyUpdateSalePointsCare extends AsyncTask<String, Void, BOCOutput> {

		private final Activity mActivity;
		final ProgressDialog progress;

		public AsyUpdateSalePointsCare(Activity mActivity) {

			this.mActivity = mActivity;

			this.progress = new ProgressDialog(mActivity);
			this.progress.setCancelable(false);
			this.progress.setMessage(mActivity.getResources().getString(R.string.getdataing));
			if (!this.progress.isShowing()) {
				this.progress.show();
			}

		}

		@Override
		protected BOCOutput doInBackground(String... params) {
			return updateSalePointsCare();
		}

		@Override
		protected void onPostExecute(BOCOutput result) {
			super.onPostExecute(result);
			progress.dismiss();

			if ("0".equals(result.getErrorCode())) {
				Dialog dialog = CommonActivity.createAlertDialog(mActivity,
						mActivity.getResources().getString(R.string.txt_update_end_care_cus_success),
						mActivity.getResources().getString(R.string.app_name), backPressClick);
				dialog.show();
			} else {
				if (result.getErrorCode().equals(Constant.INVALID_TOKEN2)) {
					Dialog dialog = CommonActivity.createAlertDialog(mActivity, result.getDescription(),
							mActivity.getResources().getString(R.string.app_name), moveLogInAct);
					dialog.show();
				} else {
					Dialog dialog = CommonActivity.createAlertDialog(FragmentCusCareByDay.this, result.getDescription(),
							getResources().getString(R.string.app_name));
					dialog.show();

				}
			}
		}

		private BOCOutput updateSalePointsCare() {
			BOCOutput bocOutput = new BOCOutput();
			String original = "";
			try {
				String methodName = "updateSalePointsCare";
				BCCSGateway input = new BCCSGateway();
				input.addValidateGateway("username", Constant.BCCSGW_USER);
				input.addValidateGateway("password", Constant.BCCSGW_PASS);
				input.addValidateGateway("wscode", "mbccs_" + methodName);

				StringBuilder rawData = new StringBuilder();
				rawData.append("<ws:").append(methodName).append(">");
				rawData.append("<input>");
				rawData.append("<token>").append(Session.getToken()).append("</token>");
				rawData.append("<spchId>").append(mark2SellingContactHisId).append("</spchId>");
				rawData.append("<statusCare>").append(FragmentCusCareByDay.careCus).append("</statusCare>");
				rawData.append("</input>");
				rawData.append("</ws:").append(methodName).append(">");

				Log.i("RowData", rawData.toString());
				String envelope = input.buildInputGatewayWithRawData(rawData.toString());
				Log.d("Send evelop", envelope);
				Log.i("LOG", Constant.BCCS_GW_URL);
				String response = input.sendRequest(envelope, Constant.BCCS_GW_URL, FragmentCusCareByDay.this,
						"mbccs_" + methodName);
				Log.i("Responseeeeeeeeee", response);
				CommonOutput output = input.parseGWResponse(response);
				original = output.getOriginal();
				Log.i("Responseeeeeeeeee Original", response);

				// parser
				Serializer serializer = new Persister();
				bocOutput = serializer.read(BOCOutput.class, original);
				if (bocOutput == null) {
					bocOutput = new BOCOutput();
					bocOutput.setDescription(getString(R.string.no_return_from_system));
					bocOutput.setErrorCode(Constant.ERROR_CODE);
					return bocOutput;
				} else {
					return bocOutput;
				}
			} catch (Exception e) {
				Log.e(Constant.TAG, "updateSalePointsCare", e);
				bocOutput = new BOCOutput();
				bocOutput.setDescription(CommonActivity.getDescription(getApplicationContext(), e));
				bocOutput.setErrorCode(Constant.ERROR_CODE);
			}
			return bocOutput;
		}

	}

	private final OnClickListener backPressClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			onBackPressed();
		}
	};

	private final OnClickListener moveLogInAct = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(FragmentCusCareByDay.this, LoginActivity.class);
			startActivity(intent);

			finish();
		}
	};

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
