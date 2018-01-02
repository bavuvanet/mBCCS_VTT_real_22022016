package com.viettel.bss.viettelpos.v4.connecttionService.adapter;

import java.util.ArrayList;
import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class GetStreetBlockAdapter extends ArrayAdapter<AreaObj> implements Filterable {
	private final Context mContext;
	
	private final List<AreaObj> objects;
	private Filter filter;

	public GetStreetBlockAdapter(List<AreaObj> list, Context context) {
		super(context, R.layout.connectionmobile_item_pakage, list);
		mContext = context;
		objects = list;
	}

	private class ViewHolder {
		TextView txtpstn;
	}
	
	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder;
		View mView = view;

		if (mView == null) {
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			mView = inflater.inflate(R.layout.connectionmobile_item_pakage, viewGroup, false);
			holder = new ViewHolder();
			holder.txtpstn = (TextView) mView.findViewById(R.id.tvconectorcode);
			mView.setTag(holder);
		} else {
			holder = (ViewHolder) mView.getTag();
		}
		AreaObj obj = getItem(position);
		if (obj != null
				&& !CommonActivity.isNullOrEmpty(obj.getName())) {
			holder.txtpstn.setText(obj.getName());
		}

		return mView;
	}
	
	@Override
	public Filter getFilter() {
		if (filter == null)
			filter = new AppFilter<>(objects);
		return filter;
	}
	
	private class AppFilter<T> extends Filter {

		private final ArrayList<T> sourceObjects;

		public AppFilter(List<T> objects) {
			sourceObjects = new ArrayList<>();
			synchronized (this) {
				sourceObjects.addAll(objects);
			}
		}

		@Override
		protected FilterResults performFiltering(CharSequence chars) {
			String filterSeq = chars.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (filterSeq != null && filterSeq.length() > 0) {
				ArrayList<T> filter = new ArrayList<>();

				for (T object : sourceObjects) {
					// the filtering itself:
					if (object.toString().toLowerCase().contains(filterSeq))
						filter.add(object);
				}
				result.count = filter.size();
				result.values = filter;
			} else {
				// add all objects
				synchronized (this) {
					result.values = sourceObjects;
					result.count = sourceObjects.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			// NOTE: this function is *always* called from the UI thread.
			ArrayList<T> filtered = (ArrayList<T>) results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0, l = filtered.size(); i < l; i++)
				add((AreaObj) filtered.get(i));
			notifyDataSetInvalidated();
		}
	}

}
