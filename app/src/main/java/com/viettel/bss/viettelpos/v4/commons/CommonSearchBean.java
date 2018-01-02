package com.viettel.bss.viettelpos.v4.commons;

import java.io.Serializable;
import java.util.ArrayList;

import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

class CommonSearchBean implements Serializable{
	private ArrayList<HTHMBeans> arrHthmBeans = new ArrayList<>();
	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeans = new ArrayList<>();
	
	//hthm va km cua mobile
	private ArrayList<HTHMBeans> arrHthmBeansMobile = new ArrayList<>();
	private ArrayList<PromotionTypeBeans> arrPromotionTypeBeansMobile = new ArrayList<>();
	public ArrayList<HTHMBeans> getArrHthmBeans() {
		return arrHthmBeans;
	}
	public void setArrHthmBeans(ArrayList<HTHMBeans> arrHthmBeans) {
		this.arrHthmBeans = arrHthmBeans;
	}
	public ArrayList<PromotionTypeBeans> getArrPromotionTypeBeans() {
		return arrPromotionTypeBeans;
	}
	public void setArrPromotionTypeBeans(
			ArrayList<PromotionTypeBeans> arrPromotionTypeBeans) {
		this.arrPromotionTypeBeans = arrPromotionTypeBeans;
	}
	public ArrayList<HTHMBeans> getArrHthmBeansMobile() {
		return arrHthmBeansMobile;
	}
	public void setArrHthmBeansMobile(ArrayList<HTHMBeans> arrHthmBeansMobile) {
		this.arrHthmBeansMobile = arrHthmBeansMobile;
	}
	public ArrayList<PromotionTypeBeans> getArrPromotionTypeBeansMobile() {
		return arrPromotionTypeBeansMobile;
	}
	public void setArrPromotionTypeBeansMobile(
			ArrayList<PromotionTypeBeans> arrPromotionTypeBeansMobile) {
		this.arrPromotionTypeBeansMobile = arrPromotionTypeBeansMobile;
	}
	
	
}
