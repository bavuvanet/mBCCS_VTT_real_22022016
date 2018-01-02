package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

public class ProgramSaleBean {
    private String program_id;
    private String program_code;
    private String program_name;
    private String sta_date;
    private String end_date;
    private String status;
	public String getProgram_id() {
		return program_id;
	}
	public void setProgram_id(String program_id) {
		this.program_id = program_id;
	}
	public String getProgram_code() {
		return program_code;
	}
	public void setProgram_code(String program_code) {
		this.program_code = program_code;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getSta_date() {
		return sta_date;
	}
	public void setSta_date(String sta_date) {
		this.sta_date = sta_date;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ProgramSaleBean(String program_id, String program_code,
			String program_name, String sta_date, String end_date, String status) {
		super();
		this.program_id = program_id;
		this.program_code = program_code;
		this.program_name = program_name;
		this.sta_date = sta_date;
		this.end_date = end_date;
		this.status = status;
	}
	public ProgramSaleBean() {
		super();
	}
    	@Override
    	public String toString() {
    		if(!CommonActivity.isNullOrEmpty(program_name)){
				return program_code + " - " + program_name;
			}
			return program_code;


    	}
    
}
