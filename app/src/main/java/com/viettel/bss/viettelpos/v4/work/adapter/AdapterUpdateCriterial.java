package com.viettel.bss.viettelpos.v4.work.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.commons.Define;
import com.viettel.bss.viettelpos.v4.work.dal.JobDal;
import com.viettel.bss.viettelpos.v4.work.object.CollectedObjectInfo;
import com.viettel.bss.viettelpos.v4.work.object.ObjectDetailGroup;
import com.viettel.bss.viettelpos.v4.work.object.OjectSpinerCriterial;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterUpdateCriterial extends BaseAdapter {
	private final ArrayList<ObjectDetailGroup> arrayList;
	private final Context mContext;

    private ArrayList<OjectSpinerCriterial> mArrO;

    public AdapterUpdateCriterial(Context context,
			ArrayList<ObjectDetailGroup> arrayList) {
		this.mContext = context;
		this.arrayList = arrayList;

	}

	private interface onSelectListenner {
		void onSelectBox(int positionTask, int positonSalePoint);
	}

	public void setOnMySelectListenner(onSelectListenner listenner) {
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		TextView tvCriteriaName;
		TextView mEditText;
		Spinner mSpinner;
		TextView tvDateTime;
		TextView tvRequire;
		/* ImageView iconDate; */
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View mView = convertView;
		String valueType = null;

        ViewHolder holder;
        if (mView == null) {
			Log.e("TAG", "1.1 vi tri = " + position);
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.item_layout_update_criteria,
					parent, false);
			holder = new ViewHolder();
			/*
			 * holder.iconDate = (ImageView) mView .findViewById(R.id.iconDate);
			 */
			holder.mSpinner = (Spinner) mView
					.findViewById(R.id.spShowCriterial);
			holder.tvRequire = (TextView) mView.findViewById(R.id.tvRequire);
			holder.tvRequire.setVisibility(View.GONE);
			holder.tvCriteriaName = (TextView) mView
					.findViewById(R.id.tvCriteriaUpdateName);
			holder.mEditText = (TextView) mView
					.findViewById(R.id.edtUpdateValue);
			holder.tvDateTime = (TextView) mView.findViewById(R.id.txtViewDate);

			mView.setTag(holder);

		} else {
			holder = (ViewHolder) mView.getTag();
		}
		ObjectDetailGroup mObject = arrayList.get(position);
		if (mObject != null) {
			holder.tvCriteriaName.setText(mObject.getName());
			valueType = mObject.getValueType();
			if (mObject.getIsKey().equals("1")) {
				holder.tvRequire.setVisibility(View.VISIBLE);

			}
			Log.e("TAG", "Ten thuoc tinh = " + mObject.getName()
					+ " Kieu du lieu  = " + valueType);
            switch (valueType) {
                case "STRING":
                    holder.mEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    holder.mEditText.setVisibility(View.VISIBLE);
                    holder.tvDateTime.setVisibility(View.GONE);
				/* holder.iconDate.setVisibility(View.GONE); */
                    holder.mSpinner.setVisibility(View.GONE);
                    if (mObject.getmCollectedObjectInfo() != null
                            && mObject.getmCollectedObjectInfo().getValue() != null) {
                        holder.mEditText.setText(mObject.getmCollectedObjectInfo()
                                .getValue());
                        mObject.setValue(mObject.getmCollectedObjectInfo()
                                .getValue());

                    }
                    break;
                case "NUMBER":
                    holder.mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    holder.mEditText.setVisibility(View.VISIBLE);
                    holder.tvDateTime.setVisibility(View.GONE);
                    holder.mSpinner.setVisibility(View.GONE);
				/* holder.iconDate.setVisibility(View.GONE); */
                    if (mObject.getmCollectedObjectInfo() != null
                            && mObject.getmCollectedObjectInfo().getValue() != null) {
                        holder.mEditText.setText(mObject.getmCollectedObjectInfo()
                                .getValue());
                        mObject.setValue(mObject.getmCollectedObjectInfo()
                                .getValue());

                    }
                    break;
                case "DATE":
                    holder.mEditText.setVisibility(View.GONE);
                    holder.tvDateTime.setVisibility(View.VISIBLE);
                    holder.mSpinner.setVisibility(View.GONE);
				/* holder.iconDate.setVisibility(View.VISIBLE); */
                    if (mObject.getmCollectedObjectInfo() != null
                            && mObject.getmCollectedObjectInfo().getValue() != null
                            && !mObject.getmCollectedObjectInfo().getValue().trim()
                            .equals("")) {
                        holder.tvDateTime.setText(mObject.getmCollectedObjectInfo()
                                .getValue());
                        mObject.setValue(mObject.getmCollectedObjectInfo()
                                .getValue());
                    } else {
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int year = calendar.get(Calendar.YEAR);
                        String date = day + "/" + month + "/" + year;
                        holder.tvDateTime.setText(date);
                        CollectedObjectInfo item = new CollectedObjectInfo();
                        item.setValue(date);
                        arrayList.get(position).setmCollectedObjectInfo(item);
                        arrayList.get(position).setValue(date);
                        Log.e("tag", "Ngay thang "
                                + arrayList.get(position).getmCollectedObjectInfo()
                                .getValue());
                    }
                    break;
                case "LIST":
                    Log.e("TAG", "DU LIEU  LIST= " + mObject.getValue());
                    holder.mEditText.setVisibility(View.VISIBLE);
                    holder.tvDateTime.setVisibility(View.GONE);
				/* holder.iconDate.setVisibility(View.GONE); */
                    holder.mSpinner.setVisibility(View.GONE);
                    JobDal mDal = new JobDal(mContext);
                    try {
                        mDal.open();
                        ObjectDetailGroup mItem = mDal.getObjectChildOfList(
                                Define.object_detail_group, mObject.getId());
                        if (mItem == null) {
                            mItem = mDal.getObjectGroupDetailById(
                                    Define.object_detail_group,
                                    mObject.getParentID());
                            if (mItem != null) {
                                holder.tvCriteriaName.setText(mItem.getName());
                            }
                        }

                        mDal.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        if (mDal != null) {
                            mDal.close();
                        }

                    }
                    if (mObject.getmCollectedObjectInfo() != null
                            && mObject.getmCollectedObjectInfo().getValue() != null) {
                        holder.mEditText.setText(mObject.getmCollectedObjectInfo()
                                .getValue());
                        mObject.setValue(mObject.getmCollectedObjectInfo()
                                .getValue());
                    }

                    break;
            }
		}
		return mView;
	}

}
