package com.viettel.bss.viettelpos.v4.work.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.DateTimeUtils;

@Root(name = "familyInforBean", strict = false)
public class FamilyInforBean implements Serializable {
	@Element(name = "address", required = false)
	private String address;
	@Element(name = "areaCode", required = false)
	private String areaCode;
	@Element(name = "birthDay", required = false)
	private String birthDay;
	@Element(name = "familyInforCode", required = false)
	private String familyInforCode;
	@Element(name = "familyName", required = false)
	private String familyName;
	@Element(name = "imageName", required = false)
	private String imageName;
	@Element(name = "lastUpdate", required = false)
	private String lastUpdate;
	@Element(name = "phone", required = false)
	private String phone;
	@Element(name = "staffCode", required = false)
	private String staffCode;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "x", required = false)
	private String x;
	@Element(name = "y", required = false)
	private String y;
	@Element(name = "job", required = false)
	private String job;
	@Element(name = "positionCode", required = false)
	private String positionCode;

	@Element(name = "birthDayStr", required = false)
	private String birthDayStr;
	
	
	public String getBirthDayStr() {
		return birthDayStr;
	}

	public void setBirthDayStr(String birthDayStr) {
		this.birthDayStr = birthDayStr;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBirthDay() {
		return DateTimeUtils.convertDate(birthDay);
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getFamilyInforCode() {
		return familyInforCode;
	}

	public void setFamilyInforCode(String familyInforCode) {
		this.familyInforCode = familyInforCode;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return familyName;
	}

	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<familyInforForm>");

		sb.append("<job>");
		sb.append(job);
		sb.append("</job>");
		sb.append("<positionCode>");
		sb.append(positionCode);
		sb.append("</positionCode>");

		sb.append("<address>");
		sb.append(address);
		sb.append("</address>");

		sb.append("<areaCode>");
		sb.append(areaCode);
		sb.append("</areaCode>");

		sb.append("<birthDayStr>");
		sb.append(birthDay);
		sb.append("</birthDayStr>");

		sb.append("<familyInforCode>");
		sb.append(familyInforCode);
		sb.append("</familyInforCode>");

		sb.append("<familyName>");
		sb.append(familyName);
		sb.append("</familyName>");

		sb.append("<phone>");
		sb.append(phone);
		sb.append("</phone>");
		
		sb.append("<x>");
		sb.append(x);
		sb.append("</x>");
		
		sb.append("<y>");
		sb.append(y);
		sb.append("</y>");

		sb.append("<status>");
		if (status == null || status.isEmpty()) {
			sb.append(1);
		} else {
			sb.append(status);
		}
		sb.append("</status>");

		sb.append("</familyInforForm>");
		return sb.toString();
	}

}
