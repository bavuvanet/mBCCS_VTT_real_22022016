package com.viettel.bss.viettelpos.v4.work.object;

public class InfoJobNew {
	private String total;
	private String backlog;
	private String term;
	public String getTotal() {
		if(total == ""){
			return "0";
		}
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getBacklog() {
		if(backlog == ""){
			return "0";
		}
		return backlog;
	}
	public void setBacklog(String backlog) {
		this.backlog = backlog;
	}
	public String getTerm() {
		if(term == ""){
			return "0";
		}
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	

	
}
