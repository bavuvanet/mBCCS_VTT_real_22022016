package com.viettel.bss.viettelpos.v4.work.object;

import java.util.ArrayList;

public class TaskTypeOJ {
	private Long qty = 0L;
	private String name;
	private ArrayList<TaskObject_> lstTaskObjects = new ArrayList<>();
	public TaskTypeOJ(String name, long num,
			ArrayList<TaskObject_> arrTaskObjects) {
		setName(name);
		setQty(num);
		setLstTaskObjects(arrTaskObjects);
	}
	public String getName() {
		return name;
	}

	private void setName(String name) {
		this.name = name;
	}

	public Long getQty() {
		return qty;
	}
	private void setQty(Long qty) {
		this.qty = qty;
	}
	private void setLstTaskObjects(ArrayList<TaskObject_> lstTaskObjects) {
		this.lstTaskObjects = lstTaskObjects;
	}
	public ArrayList<TaskObject_> getLstTaskObjects() {
		return lstTaskObjects;
	}

}
