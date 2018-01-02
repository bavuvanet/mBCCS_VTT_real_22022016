package com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class PakageBundeBeanBCCS implements Serializable{
	private ArrayList<ProductOfferingDTO> arrChargeBeans = new ArrayList<>();

	public ArrayList<ProductOfferingDTO> getArrChargeBeans() {
		return arrChargeBeans;
	}

	public void setArrChargeBeans(ArrayList<ProductOfferingDTO> arrChargeBeans) {
		this.arrChargeBeans = arrChargeBeans;
	}
	
}
