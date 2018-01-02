package com.viettel.bss.viettelpos.v4.dialog;

import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

	private final TextView txtDate;
	private StringBuilder date;
	private Date toDay;
	 public DatePickerFragment(TextView txtDate) {
		super();
		this.txtDate = txtDate;
	}

	@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current date as the default date in the picker
	        final Calendar c = Calendar.getInstance();
	        int year = c.get(Calendar.YEAR);
	        int month = c.get(Calendar.MONTH);
	        int day = c.get(Calendar.DAY_OF_MONTH);

	        // Create a new instance of DatePickerDialog and return it
	        return new FixedHoloDatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,this, year, month, day);
	    }

	    public void onDateSet(DatePicker view, int year, int month, int day) {
	    	txtDate.setText(new StringBuilder().append(day).append("-").append(month + 1)
	 			   .append("-").append(year)
	 			   .append(" "));
	    	date = new StringBuilder().append(day).append("-").append(month + 1)
		 			   .append("-").append(year)
		 			   .append(" ");
	    	
	    
	    }

		public StringBuilder getDate() {
			return date;
		}

		
	
	
	
}
