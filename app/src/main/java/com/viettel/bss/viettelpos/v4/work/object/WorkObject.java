package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

public class WorkObject implements Serializable {
	private String workid;
	private String nameWork;
	private String startDate;
	private String endDate;
	private String pointToId;
	private String namePointTo;
	private String address;
	private String content;
	private double x = 0L;
	private double y = 0L;

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setY(long y) {
		this.y = y;
	}

	public String getWorkid() {
		return workid;
	}

	public void setWorkid(String workid) {
		this.workid = workid;
	}

	public String getNameWork() {
		return nameWork;
	}

	public void setNameWork(String nameWork) {
		this.nameWork = nameWork;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPointToId() {
		return pointToId;
	}

	public void setPointToId(String pointToId) {
		this.pointToId = pointToId;
	}

	public String getNamePointTo() {
		return namePointTo;
	}

	public void setNamePointTo(String namePointTo) {
		this.namePointTo = namePointTo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
