package com.viettel.bss.viettelpos.v4.work.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;

@Root(name = "RollUpBO", strict = false)
public class RollUpBO {
	@Element(name = "programId", required = false)
	private String programId;
	@Element(name = "programName", required = false)
	private String programName;
	@Element(name = "note", required = false)
	private String note;
	@Element(name = "address", required = false)
	private String address;
	@Element(name = "x", required = false)
	private String x;
	@Element(name = "y", required = false)
	private String y;
	@Element(name = "rollUpTime", required = false)
	private String rollUpTime;
	@Element(name = "programCode", required = false)
	private String programCode;
	
	
	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getRollUpTime() {
		return rollUpTime;
	}

	public void setRollUpTime(String rollUpTime) {
		this.rollUpTime = rollUpTime;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getNote() {
		if(note == null){
			return "";
		}
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCodeNameProgram() {
		if (!CommonActivity.isNullOrEmpty(programName)) {
			return programCode + " - " + programName;
		}
		return programCode;
	}
}
