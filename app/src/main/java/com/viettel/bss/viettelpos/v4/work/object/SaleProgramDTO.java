package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;

@Root(name = "SaleProgramDTO", strict = false)
public class SaleProgramDTO implements Serializable {
	@Element(name = "effectDatetime", required = false)
	private String effectDatetime;
	@Element(name = "expireDatetime", required = false)
	private String expireDatetime;
	@Element(name = "saleProgramId", required = false)
	private String saleProgramId;
	@ElementList(name = "lstRegisterFromDate", entry = "lstRegisterFromDate", required = false, inline = true)
	private List<String> lstRegisterFromDate;
	@ElementList(name = "lstRegisterToDate", entry = "lstRegisterToDate", required = false, inline = true)
	private List<String> lstRegisterToDate;
	@Element(name = "numOfRollUp", required = false)
	private int numOfRollUp;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "code", required = false)
	private String code;
	@Element(name = "registerToday", required = false)
	private int registerToday;
	
	public int getRegisterToday() {
		return registerToday;
	}

	public void setRegisterToday(int registerToday) {
		this.registerToday = registerToday;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEffectDatetime() {
		return effectDatetime;
	}

	public void setEffectDatetime(String effectDatetime) {
		this.effectDatetime = effectDatetime;
	}

	public String getExpireDatetime() {
		return expireDatetime;
	}

	public void setExpireDatetime(String expireDatetime) {
		this.expireDatetime = expireDatetime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSaleProgramId() {
		return saleProgramId;
	}

	public void setSaleProgramId(String saleProgramId) {
		this.saleProgramId = saleProgramId;
	}

	public List<String> getLstRegisterFromDate() {
		return lstRegisterFromDate;
	}

	public void setLstRegisterFromDate(List<String> lstRegisterFromDate) {
		this.lstRegisterFromDate = lstRegisterFromDate;
	}

	public List<String> getLstRegisterToDate() {
		return lstRegisterToDate;
	}

	public void setLstRegisterToDate(List<String> lstRegisterToDate) {
		this.lstRegisterToDate = lstRegisterToDate;
	}

	public int getNumOfRollUp() {
		return numOfRollUp;
	}

	public void setNumOfRollUp(int numOfRollUp) {
		this.numOfRollUp = numOfRollUp;
	}

	public String getDuration() {
		String fromDate = DateTimeUtils.convertDate(this.getEffectDatetime());
		String toDate = DateTimeUtils.convertDate(this.getExpireDatetime());
		return fromDate + " - " + toDate;
	}

	public String getCodeNameProgram() {
		if (!CommonActivity.isNullOrEmpty(name)) {
			return code + " - " + name;
		}
		return code;
	}
}
