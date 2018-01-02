package com.viettel.bss.viettelpos.v4.work.object;

class UpdateWorkObject {
	private String status;
	private String progress;
	public UpdateWorkObject(String status,String progress){
		this.status = status;
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
}
