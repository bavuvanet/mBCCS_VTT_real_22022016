package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;

@Root(name = "RollUpBO", strict = false)
public class RegisterRollUpBO implements Serializable {
	@Element(name = "registerTime", required = false)
	private String registerTime;
	@Element(name = "registerFromDate", required = false)
	private String registerFromDate;
	@Element(name = "registerToDate", required = false)
	private String registerToDate;
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
	@Element(name = "programCode", required = false)
	private String programCode;
	@Element(name = "id", required = false)
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getRegisterFromDate() {
		return registerFromDate;
	}

	public void setRegisterFromDate(String registerFromDate) {
		this.registerFromDate = registerFromDate;
	}

	public String getRegisterToDate() {
		return registerToDate;
	}

	public void setRegisterToDate(String registerToDate) {
		this.registerToDate = registerToDate;
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

	public String getDuration() {
		String fromDate = DateTimeUtils.convertDate(this.getRegisterFromDate());
		String toDate = DateTimeUtils.convertDate(this.getRegisterToDate());
		return fromDate + " - " + toDate;
	}

	public String getCodeNameProgram() {
		if (!CommonActivity.isNullOrEmpty(programName)) {
			return programCode + " - " + programName;
		}
		return programCode;
	}

}
