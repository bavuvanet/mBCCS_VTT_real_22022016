package com.viettel.bss.viettelpos.v4.channel.object;

import java.util.ArrayList;

public class AssetTypeOJ {
	private Long qty = 0L;
	private String name;
	private ArrayList<AssetDetailHistoryOJ> lstAssetDetailOJ = new ArrayList<>();
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getQty() {
		return qty;
	}
	public void setQty(Long qty) {
		this.qty = qty;
	}
	public ArrayList<AssetDetailHistoryOJ> getLstAssetDetailOJ() {
		return lstAssetDetailOJ;
	}
	public void setLstAssetDetailOJ(ArrayList<AssetDetailHistoryOJ> lstAssetDetailOJ) {
		this.lstAssetDetailOJ = lstAssetDetailOJ;
	}
	
}
