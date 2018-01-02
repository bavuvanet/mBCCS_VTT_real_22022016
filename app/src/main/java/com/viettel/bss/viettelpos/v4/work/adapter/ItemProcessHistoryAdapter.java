package com.viettel.bss.viettelpos.v4.work.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.work.object.ProcessRequestBO;

import java.util.ArrayList;
import java.util.List;

public class ItemProcessHistoryAdapter extends BaseAdapter {

    private List<ProcessRequestBO> objects = new ArrayList<ProcessRequestBO>();

    private Context context;
    private LayoutInflater layoutInflater;

    public ItemProcessHistoryAdapter(Context context, ArrayList<ProcessRequestBO> processRequestBOs) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = processRequestBOs;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ProcessRequestBO getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_process_history, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ProcessRequestBO object, ViewHolder holder) {
        holder.vlUser.setText(object.getUserCreate());
        holder.vTimeAsignWork.setText(object.getProcessDate());
        holder.vlStatus.setText(object.getStatus());
        holder.vlContentProcess.setText(object.getContentProcess());
    }

    protected class ViewHolder {
        private TextView vlUser;
        private TextView vTimeAsignWork;
        private TextView vlStatus;
        private TextView vlContentProcess;

        public ViewHolder(View view) {
            vlUser = (TextView) view.findViewById(R.id.vl_user);
            vTimeAsignWork = (TextView) view.findViewById(R.id.vl_time_asign_work);
            vlStatus = (TextView) view.findViewById(R.id.vl_status);
            vlContentProcess = (TextView) view.findViewById(R.id.vl_content_process);
        }
    }
}
