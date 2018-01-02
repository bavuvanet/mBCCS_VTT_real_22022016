package com.viettel.bss.viettelpos.v4.work.arr;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.work.object.TaskObject_;

public class ArrTaskOJ {
	private ArrayList<TaskObject_> arrTaskObjects = new ArrayList<>();
	public void setArrTaskObjects(ArrayList<TaskObject_> arrTaskObjects) {
		this.arrTaskObjects = arrTaskObjects;
	}
	public ArrayList<TaskObject_> getArrTaskObjects() {
		return arrTaskObjects;
	}
}
