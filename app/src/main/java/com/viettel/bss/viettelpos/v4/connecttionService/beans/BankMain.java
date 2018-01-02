package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class BankMain implements Serializable{
 private ArrayList<Bank> arrBanks = new ArrayList<>();

public ArrayList<Bank> getArrBanks() {
	return arrBanks;
}

public void setArrBanks(ArrayList<Bank> arrBanks) {
	this.arrBanks = arrBanks;
}
 
}
