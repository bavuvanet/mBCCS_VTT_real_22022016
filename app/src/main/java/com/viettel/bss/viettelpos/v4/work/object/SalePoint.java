package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;

public class SalePoint implements Serializable {
	private String salePointName ;
	private Long visitCount;
	private Long staffId;
	private String staffCode;
	private Long moneyBuyOfMonth;
	private String address;
	private Boolean check = false;
	private ArrayList<TaskObject> arrayTaskObjects;
	private int positionTask;
	private TaskObject taskObjectSelect = null;
	private float x;
	private float y;
	public SalePoint() {
		super();
		// TODO Auto-generated constructor stub
	}
   
	
	public SalePoint(Long visitCount, String staffCode, Long moneyBuyOfMonth) {
		super();
		this.visitCount = visitCount;
		this.staffCode = staffCode;
		this.moneyBuyOfMonth = moneyBuyOfMonth;
	}


	public SalePoint(String salePointName, Long staffId, String staffCode,
			String address) {
		super();
		this.salePointName = salePointName;
		this.staffId = staffId;
		this.staffCode = staffCode;
		this.address = address;
	
	}
	

	public SalePoint(String salePointName, Long staffId, String staffCode,
			String address, float x, float y) {
		super();
		this.salePointName = salePointName;
		this.staffId = staffId;
		this.staffCode = staffCode;
		this.address = address;
		this.x = x;
		this.y = y;
	}


	public float getX() {
		return x;
	}


	public void setX(float x) {
		this.x = x;
	}


	public float getY() {
		return y;
	}


	public void setY(float y) {
		this.y = y;
	}


	public TaskObject getTaskObjectSelect() {
		return taskObjectSelect;
	}


	public void setTaskObjectSelect(TaskObject taskObjectSelect) {
		this.taskObjectSelect = taskObjectSelect;
	}


	public ArrayList<TaskObject> getArrayTaskObjects() {
		return arrayTaskObjects;
	}

	
	public int getPositionTask() {
		return positionTask;
	}


	public void setPositionTask(int positionTask) {
		this.positionTask = positionTask;
	}


	public void setArrayTaskObjects(ArrayList<TaskObject> arrayTaskObjects) {
		this.arrayTaskObjects = arrayTaskObjects;
	}


	public String getSalePointName() {
		return salePointName;
	}


	public void setSalePointName(String salePointName) {
		this.salePointName = salePointName;
	}


	public Long getVisitCount() {
		return visitCount;
	}


	public void setVisitCount(Long visitCount) {
		this.visitCount = visitCount;
	}


	public Long getStaffId() {
		return staffId;
	}


	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}


	public String getStaffCode() {
		return staffCode;
	}


	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}


	public Long getMoneyBuyOfMonth() {
		return moneyBuyOfMonth;
	}


	public void setMoneyBuyOfMonth(Long moneyBuyOfMonth) {
		this.moneyBuyOfMonth = moneyBuyOfMonth;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Boolean getCheck() {
		return check;
	}


	public void setCheck(Boolean check) {
		this.check = check;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SalePoint)) {
			return false;
		}
		SalePoint that = (SalePoint) other;
		Log.e("TAG", that.getStaffCode() + " SO SANH " + this.getStaffCode()
				+ " KET QUA " + this.getStaffCode().equals(that.getStaffCode()));
		// Custom equality check here.
		return this.getStaffCode().equals(that.getStaffCode());
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = hashCode * 37 + this.staffCode.hashCode();
		hashCode = hashCode * 37 + this.staffId.hashCode();

		return hashCode;
	}
	
}
