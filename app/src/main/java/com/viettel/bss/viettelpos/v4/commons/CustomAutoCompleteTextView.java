package com.viettel.bss.viettelpos.v4.commons;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomAutoCompleteTextView extends AutoCompleteTextView {

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /* Overriding this method and returning String type solves the problem */
    @Override
    protected CharSequence convertSelectionToString(Object selectedItem) {
        
    	Cursor c = (Cursor) selectedItem;
        return c.getString(c.getColumnIndex("name"));
        
    }
}
