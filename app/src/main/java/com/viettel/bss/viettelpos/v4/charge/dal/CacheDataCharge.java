package com.viettel.bss.viettelpos.v4.charge.dal;

import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.charge.object.ChargeCustomerOJ;

public class CacheDataCharge {
	private String areaCode = "";
	private String telecomcode = "";
	private String billCycleFrom = "";
	private static CacheDataCharge instance = null;
	private ArrayList<ChargeCustomerOJ> lisArrayList = new ArrayList<>();
	private ArrayList<ChargeCustomerOJ> lisArrayListRe = new ArrayList<>();
	public ArrayList<ChargeCustomerOJ> getLisArrayListRe() {
		return lisArrayListRe;
	}
	public void setLisArrayListRe(ArrayList<ChargeCustomerOJ> lisArrayListRe) {
		this.lisArrayListRe = lisArrayListRe;
	}
	public ArrayList<ChargeCustomerOJ> getLisArrayList() {
		return lisArrayList;
	}
	public void setLisArrayList(ArrayList<ChargeCustomerOJ> lisArrayList) {
		this.lisArrayList = lisArrayList;
	}
	public static CacheDataCharge getInstance() {
		if(instance == null){
			instance = new CacheDataCharge();
		}
		return instance;
	}
	public static void setInstance(CacheDataCharge instance) {
		CacheDataCharge.instance = instance;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getTelecomcode() {
		return telecomcode;
	}
	public void setTelecomcode(String telecomcode) {
		this.telecomcode = telecomcode;
	}
	public String getBillCycleFrom() {
		return billCycleFrom;
	}
	public void setBillCycleFrom(String billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}
}
