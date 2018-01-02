package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;
import java.util.ArrayList;

class SalePointBackUp implements Serializable {
	private String nameSalePoint;
	private String countSalePoint;
	private String countMoneySalePoint;
	private boolean checkSalePoint;
	private ArrayList<TaskObject> arrayTaskObjects;
	private int positionTask;

	public SalePointBackUp(String nameSalePoint, String countSalePoint,
			String countMoneySalePoint,
			boolean checkSalePoint, ArrayList<TaskObject> arrayTaskObjects,int positionTask) {
		super();
		this.nameSalePoint = nameSalePoint;
		this.countSalePoint = countSalePoint;
		this.countMoneySalePoint = countMoneySalePoint;
		this.arrayTaskObjects = arrayTaskObjects;
		this.checkSalePoint = checkSalePoint;
		this.positionTask = positionTask;
	}
	
	
	
	public int getPositionTask() {
		return positionTask;
	}



	public void setPositionTask(int positionTask) {
		this.positionTask = positionTask;
	}



	public String getNameSalePoint() {
		return nameSalePoint;
	}

	public void setNameSalePoint(String nameSalePoint) {
		this.nameSalePoint = nameSalePoint;
	}

	public String getCountSalePoint() {
		return countSalePoint;
	}

	public void setCountSalePoint(String countSalePoint) {
		this.countSalePoint = countSalePoint;
	}

	public String getCountMoneySalePoint() {
		return countMoneySalePoint;
	}

	public void setCountMoneySalePoint(String countMoneySalePoint) {
		this.countMoneySalePoint = countMoneySalePoint;
	}

	public ArrayList<TaskObject> getArrayTaskObjects() {
		return arrayTaskObjects;
	}

	public void setArrayTaskObjects(ArrayList<TaskObject> arrayTaskObjects) {
		this.arrayTaskObjects = arrayTaskObjects;
	}

	public boolean isCheckSalePoint() {
		return checkSalePoint;
	}

	public void setCheckSalePoint(boolean checkSalePoint) {
		this.checkSalePoint = checkSalePoint;
	}

}
