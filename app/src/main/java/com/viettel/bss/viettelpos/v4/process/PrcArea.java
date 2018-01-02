package com.viettel.bss.viettelpos.v4.process;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.viettel.bss.viettelpos.v4.R;
import com.viettel.bss.viettelpos.v4.charge.dal.AreaDal;
import com.viettel.bss.viettelpos.v4.charge.object.AreaObj;

class PrcArea implements Serializable  {
	private final Activity act;
	private final Spinner spinnerArea;
	private ArrayList<AreaObj> lstAreaObjs = new ArrayList<>();
	private String areaCode = "";
	private boolean oldArea = false;
	public PrcArea(Activity act, Spinner spinnerArea) {
		this.act = act;
		this.spinnerArea = spinnerArea;
		initSpinerArea();
	}

	private void initSpinerArea() {
		// areaCode = null;
		oldArea = false;
		try {
			AreaDal dal = new AreaDal(act);
			dal.open();
			lstAreaObjs = dal.getAllArea();
			// lstAreaObjs.get(0).getAreaCode();
			dal.close();
			if (lstAreaObjs != null && !lstAreaObjs.isEmpty()) {
				ArrayAdapter<String> adapter = new ArrayAdapter<>(act,
                        android.R.layout.simple_dropdown_item_1line,
                        android.R.id.text1);
				adapter.add(act.getString(R.string.select_area));
				for (AreaObj areaObj : lstAreaObjs) {
					adapter.add(areaObj.getName());
				}

				spinnerArea.setAdapter(adapter);
			}
		} catch (Exception e) {
			Log.e(tag, e.toString(), e);
		}
	}
	private int iArea;
	private final String tag = "prc area";
	public boolean checkOldArea() {
		// TODO Auto-generated method stub
		// kiem tra dia ban da chon truoc do
		int tmpiArea = spinnerArea.getSelectedItemPosition();
        oldArea = tmpiArea == iArea;
		// lay ma dia ban
		iArea = tmpiArea;
		areaCode = "";
		if (iArea > 0) {
			areaCode = lstAreaObjs.get(iArea - 1).getAreaCode();
		}
		return oldArea;
	}
}
