package com.viettel.bss.viettelpos.v4.work.object;

public class InfoNewManager {
	private String allTask = "0";
	private String failTask = "0";
	private String warningTask = "0";
	public String getAllTask() {
		return allTask;
	}
	public void setAllTask(String allTask) {
		this.allTask = allTask;
	}
	public String getFailTask() {
		return failTask;
	}
	public void setFailTask(String failTask) {
		this.failTask = failTask;
	}
	public String getWarningTask() {
		return warningTask;
	}
	public void setWarningTask(String warningTask) {
		this.warningTask = warningTask;
	}
}
