package com.viettel.bss.viettelpos.v4.activity;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.activity.slidingmenu.MainActivity;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView;
import com.viettel.bss.viettelpos.v4.ui.LoadMoreListView.OnLoadMoreListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CommonLoadMoreDialog<T> {

	private Dialog dialogLoadMore;
	private LoadMoreListView loadMoreListView;
	private EditText edt_textSearch;
	private ArrayAdapter<T> adapter;
	private Activity mActivity;
	private int viewId;
	private CommonLoadMoreDialogListener<T> listener;
	private CommonLoadMoreDialogAsyncTaskFactory<T> asyncFactory;
	private int pageIndex = 0;
	private ArrayList<T> arrTractBeans;
	private String title;
	private String subTitle;
	private String searchText;
	private boolean allowSearch;
	
	public CommonLoadMoreDialog(Activity activity, int viewId, CommonLoadMoreDialogListener<T> listener, 
			CommonLoadMoreDialogAsyncTaskFactory<T> asyncFactory, String title, String subTitle, boolean allowSearch) {
		this.mActivity = activity;
		this.viewId = viewId;
		this.listener = listener;
		this.asyncFactory = asyncFactory;
		this.arrTractBeans = new ArrayList<T>();
		this.title = title;
		this.subTitle = subTitle;
		this.allowSearch = allowSearch;
		this.searchText = "";
	}
	
	public void showDiaLogLoadMoreContract() {

		dialogLoadMore = new Dialog(mActivity);
		dialogLoadMore.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialogLoadMore.setContentView(R.layout.common_load_more_list_view);
//		dialogLoadMore.setCancelable(false);
		
		loadMoreListView = (LoadMoreListView) dialogLoadMore.findViewById(R.id.loadMoreListView);
		adapter = new ArrayAdapter<T>(mActivity, R.layout.spinner_item, arrTractBeans);
		loadMoreListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		TextView txtTitle = (TextView) dialogLoadMore.findViewById(R.id.txtTitle);
		txtTitle.setText(title);
		
		TextView txtinfo = (TextView) dialogLoadMore.findViewById(R.id.txtinfo);
		txtinfo.setText(subTitle);
		
		edt_textSearch = (EditText) dialogLoadMore.findViewById(R.id.edt_textSearch);
		
		loadMoreListView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arrTractBeans != null && arrTractBeans.size() > 0) {
					T t = arrTractBeans.get(arg2);
					listener.onItemClick(viewId, t);
					dialogLoadMore.cancel();
				}

			}
		});

		loadMoreListView.setOnLoadMoreListener(new OnLoadMoreListener() {

			@Override
			public void onLoadMore() {
				// TODO load more list danh sach ban ghi
				pageIndex = pageIndex + 1;
				
				if (CommonActivity.isNetworkConnected(mActivity)) {
					CommonLoadMoreDialogAsyncTask<T> asyncTask = asyncFactory.createInstance(adapter);
					asyncTask.setCommonLoadMoreDialog(CommonLoadMoreDialog.this);
					asyncTask.execute(String.valueOf(pageIndex), searchText);
				} else {
					CommonActivity
					.createAlertDialog(mActivity, mActivity.getResources().getString(R.string.errorNetwork),
							mActivity.getResources().getString(R.string.app_name))
							.show();
				}
			}
		});
		
		

		Button btnCancel = (Button) dialogLoadMore.findViewById(R.id.btnhuy);
//		btnCancel.setVisibility(View.GONE);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dialogLoadMore.cancel();
			}
		});
		
		Button btnSearch = (Button) dialogLoadMore.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String newSearchText = edt_textSearch.getText().toString();
				if (newSearchText.equals(searchText)) {
					
				} else {
					pageIndex = 0;
					searchText = newSearchText;
					arrTractBeans.clear();
					adapter.notifyDataSetChanged();
					if (CommonActivity.isNetworkConnected(mActivity)) {
						CommonLoadMoreDialogAsyncTask<T> asyncTask = asyncFactory.createInstance(adapter);
						asyncTask.setCommonLoadMoreDialog(CommonLoadMoreDialog.this);
						asyncTask.execute(String.valueOf(pageIndex), searchText);
					} else {
						CommonActivity
						.createAlertDialog(mActivity, mActivity.getResources().getString(R.string.errorNetwork),
								mActivity.getResources().getString(R.string.app_name))
								.show();
					}
					
				}
			}
		});
		dialogLoadMore.show();

	}
	
	// move login
	OnClickListener moveLogInAct = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(mActivity, LoginActivity.class);
			mActivity.startActivity(intent);
			mActivity.finish();
			if (MainActivity.getInstance() != null) {
				MainActivity.getInstance().finish();
			}

		}
	};
	
	public OnClickListener getMoveLogInAct() {
		return moveLogInAct;
	}

	public void initData() {
		showDiaLogLoadMoreContract();
		CommonLoadMoreDialogAsyncTask<T> asyncTask = asyncFactory.createInstance(adapter);
		asyncTask.setCommonLoadMoreDialog(CommonLoadMoreDialog.this);
		asyncTask.execute(String.valueOf(pageIndex), searchText);
	}
	
	public void addToList(ArrayList<T> result) {
		arrTractBeans.addAll(result);
	}
	
	public boolean validateNotShowing() {
		return (dialogLoadMore == null || !dialogLoadMore.isShowing());
	}
	
	public void completeLoad() {
		loadMoreListView.onLoadMoreComplete();
	}
}
