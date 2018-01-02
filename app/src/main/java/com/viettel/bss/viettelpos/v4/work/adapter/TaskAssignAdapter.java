package com.viettel.bss.viettelpos.v4.work.adapter;

import java.util.List;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.dal.ApParamDAL;
import com.viettel.bss.viettelpos.v4.work.object.TaskAssignBO;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaskAssignAdapter extends ArrayAdapter<TaskAssignBO> {
	
	private final String[] task_assign;
	
	private final List<Spin> lstSpin;


	public TaskAssignAdapter(Context context, List<TaskAssignBO> lst) {
	       super(context, R.layout.item_task_assign, lst);
	       
	       task_assign = context.getResources().getStringArray(R.array.task_assign);
	       
	       lstSpin = (new ApParamDAL(context)).getAppParam("REASON_CODE");
	}
	
	private static class ViewHolder {
		/**
		 *  private Long taskAssignId;
		    private String account;
		    private String serviceType;
		    private String serviceName;
		    private String mngtCode;
		    private String staffCode;
		    private Long status;
		    private Long reasonId;
		    private Date assignDate;
		    private Date updateDate;
		    private Date endAssignDate;
		    private String description;
		 */
		TextView taskCode;
		TextView taskName;
		TextView account;
        TextView serviceName;
        TextView mngtCode;
        TextView staffCode;
        TextView assignDate;
        TextView endAssignDate;
        TextView description;
        TextView status;
        
        TextView tvReasonName;
    }
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // Get the data item for this position
	   TaskAssignBO obj = getItem(position);    
       // Check if an existing view is being reused, otherwise inflate the view
       ViewHolder viewHolder; // view lookup cache stored in tag
       if (convertView == null) {
          viewHolder = new ViewHolder();
          LayoutInflater inflater = LayoutInflater.from(getContext());
          convertView = inflater.inflate(R.layout.item_task_assign, parent, false);
          viewHolder.taskCode = (TextView) convertView.findViewById(R.id.taskCode);
          viewHolder.taskName = (TextView) convertView.findViewById(R.id.taskName);
          viewHolder.account = (TextView) convertView.findViewById(R.id.account);
          viewHolder.serviceName = (TextView) convertView.findViewById(R.id.serviceName);
          viewHolder.mngtCode = (TextView) convertView.findViewById(R.id.mngtCode);
          viewHolder.staffCode = (TextView) convertView.findViewById(R.id.staffCode);
          viewHolder.assignDate = (TextView) convertView.findViewById(R.id.assignDate);
          viewHolder.endAssignDate = (TextView) convertView.findViewById(R.id.endAssignDate);
          viewHolder.description = (TextView) convertView.findViewById(R.id.description);
          viewHolder.status = (TextView) convertView.findViewById(R.id.status);
          
          viewHolder.tvReasonName = (TextView) convertView.findViewById(R.id.tvReasonName);

          convertView.setTag(viewHolder);
       } else {
          viewHolder = (ViewHolder) convertView.getTag();
       }
       // Populate the data into the template view using the data object
       viewHolder.taskCode.setText(obj.getTaskCode());
       viewHolder.taskName.setText(obj.getTaskName());
       viewHolder.account.setText(obj.getAccount());
       viewHolder.serviceName.setText(obj.getServiceName());
       viewHolder.mngtCode.setText(obj.getMngtCode());
       viewHolder.staffCode.setText(obj.getStaffCode());
       viewHolder.assignDate.setText(obj.getAssignDate());
       viewHolder.endAssignDate.setText(obj.getEndAssignDate());
       viewHolder.description.setText(obj.getDescription());
       
	   int iStatus = Integer.parseInt(obj.getStatus());
	   viewHolder.status.setText(task_assign[iStatus + 1]);
	   
	   for (Spin spin : lstSpin) {
		   if(spin.getId().equalsIgnoreCase(obj.getReasonId())) {
			   viewHolder.tvReasonName.setText(spin.getValue());
			   break;
		   }
	   }
	     
       // Return the completed view to render on screen
       return convertView;
   }
}
