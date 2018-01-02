package com.viettel.bss.viettelpos.v4.connecttionMobile.beans;

import java.io.Serializable;
import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.PakageChargeBeans;

public class PakageBundeBean implements Serializable{
	private ArrayList<PakageChargeBeans> arrChargeBeans = new ArrayList<>();

	public ArrayList<PakageChargeBeans> getArrChargeBeans() {
		return arrChargeBeans;
	}

	public void setArrChargeBeans(ArrayList<PakageChargeBeans> arrChargeBeans) {
		this.arrChargeBeans = arrChargeBeans;
	}
	
}
