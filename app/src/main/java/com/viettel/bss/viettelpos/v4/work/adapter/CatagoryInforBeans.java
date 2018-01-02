package com.viettel.bss.viettelpos.v4.work.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBean;

@Root(name = "catagoryInfor", strict = false)
public class CatagoryInforBeans implements Serializable , Cloneable{
	@Element(name = "dataType", required = false)
	private String dataType;
	@Element(name = "displayOrder", required = false)
	private String displayOrder;
	@Element(name = "inforCode", required = false)
	private String inforCode;
	@Element(name = "inforName", required = false)
	private String inforName;
	@Element(name = "isLeaves", required = false)
	private String isLeaves;
	@Element(name = "isRequire", required = false)
	private String isRequire;
	@Element(name = "isTitle", required = false)
	private String isTitle;
	@Element(name = "parentCode", required = false)
	private String parentCode;
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "type", required = false)
	private String type;
	@Element(name = "value", required = false)
	private String value;
	
	@Element(name = "inforValue", required = false)
	private String inforValue;
	
	@ElementList(name = "lstCatagoryInfors", entry = "lstCatagoryInfors", required = false, inline = true)
	private List<CatagoryInforBeans> lstCatagoryInforBeans;

	@ElementList(name = "lstDataCombo", entry = "lstDataCombo", required = false, inline = true)
	private List<DataComboboxBean> lstDataCombo;

	private String nameValue;

	@Element(name = "familyInforDetailId", required = false)
	private Long familyInforDetailId;
	@Element(name = "parentId", required = false)
	private Long parentId;
	@Element(name = "isNew", required = false)
	private String isNew;
	
	private boolean isDeleted;
	
	public boolean isDeleted() {
		return isDeleted;
	}

	
	
	public String getInforValue() {
		return inforValue;
	}



	public void setInforValue(String inforValue) {
		this.inforValue = inforValue;
	}



	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public Long getFamilyInforDetailId() {
		return familyInforDetailId;
	}

	public void setFamilyInforDetailId(Long familyInforDetailId) {
		this.familyInforDetailId = familyInforDetailId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	private List<String> lstValue = new ArrayList<>();

	public List<String> getLstValue() {
		return lstValue;
	}

	public void setLstValue(List<String> lstValue) {
		this.lstValue = lstValue;
	}

	public List<DataComboboxBean> getLstDataCombo() {
		return lstDataCombo;
	}

	public void setLstDataCombo(List<DataComboboxBean> lstDataCombo) {
		this.lstDataCombo = lstDataCombo;
	}

	public List<CatagoryInforBeans> getLstCatagoryInforBeans() {
		return lstCatagoryInforBeans;
	}

	public void setLstCatagoryInforBeans(List<CatagoryInforBeans> lstCatagoryInforBeans) {
		this.lstCatagoryInforBeans = lstCatagoryInforBeans;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getInforCode() {
		return inforCode;
	}

	public void setInforCode(String inforCode) {
		this.inforCode = inforCode;
	}

	public String getInforName() {
		return inforName;
	}

	public void setInforName(String inforName) {
		this.inforName = inforName;
	}

	public String getIsLeaves() {
		return isLeaves;
	}

	public void setIsLeaves(String isLeaves) {
		this.isLeaves = isLeaves;
	}

	public String getIsRequire() {
		return isRequire;
	}

	public void setIsRequire(String isRequire) {
		this.isRequire = isRequire;
	}

	public String getIsTitle() {
		return isTitle;
	}

	public void setIsTitle(String isTitle) {
		this.isTitle = isTitle;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return String.format(
				"{\"CatagoryInforBeans\":{\"dataType\":\"%s\", \"displayOrder\":\"%s\", \"inforCode\":\"%s\", \"inforName\":\"%s\", \"isLeaves\":\"%s\", \"isRequire\":\"%s\", \"isTitle\":\"%s\", \"parentCode\":\"%s\", \"status\":\"%s\", \"type\":\"%s\", \"value\":\"%s\", \"lstCatagoryInforBeans\":\"%s\", \"parentId\":\"%s\", \"familyInforDetailId\":\"%s\"}}",
				dataType, displayOrder, inforCode, inforName, isLeaves, isRequire, isTitle, parentCode, status, type, value, lstCatagoryInforBeans,parentId, familyInforDetailId);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof CatagoryInforBeans) {
			CatagoryInforBeans obj = (CatagoryInforBeans) other;
			return inforCode.equals(obj.getInforCode());
		} else {
			return false;
		}
	}

	public String toXML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<lstCatagoryInfor>");

		
		sb.append("<familyInforDetailId>");
		sb.append(familyInforDetailId).append("");
		sb.append("</familyInforDetailId>");
		
		if("1".equals(isNew)){
			sb.append("<isNew>");
			sb.append(isNew);
			sb.append("</isNew>");
		}else{
			sb.append("<isNew>");
			sb.append("0");
			sb.append("</isNew>");
		}

		
		sb.append("<parentId>");
		sb.append(parentId).append("");
		sb.append("</parentId>");
		
		sb.append("<parentCode>");
		sb.append(parentCode);
		sb.append("</parentCode>");
		
		
		sb.append("<inforCode>");
		sb.append(inforCode);
		sb.append("</inforCode>");
		
		sb.append("<status>");
		if(isDeleted) {
			sb.append(0);
		} else if(status == null || status.isEmpty()) {
			sb.append(1);
		} else {
			sb.append(status);
		}
		sb.append("</status>");

		if (lstValue == null | lstValue.isEmpty()) {
			if(!CommonActivity.isNullOrEmpty(value)){
				sb.append("<value>");
				sb.append(value);
				sb.append("</value>");
			}else{
				sb.append("<value>");
				sb.append("");
				sb.append("</value>");
			}
		
		} else {
			for (String value : lstValue) {
				sb.append("<value>");
				sb.append(value);
				sb.append("</value>");
			}
		}

		sb.append("</lstCatagoryInfor>");
		return sb.toString();
	}
	
	
	
	
//	public CatagoryInforBeans cloneObject(){
//		CatagoryInforBeans item = new CatagoryInforBeans();
//		
//		item.setDataType(this.getDataType());
//		item.setInforCode(this.getInforCode());
//		item.setInforName(this.getInforName());
//		item.setIsLeaves(this.getIsLeaves());
//		item.setParentCode(this.getParentCode());
//		item.setParentId(this.getParentId());
//		item.setDisplayOrder(this.getDisplayOrder());
//		item.setLstDataCombo(this.getLstDataCombo());
//		item.setFamilyInforDetailId(this.getFamilyInforDetailId());
//		item.setStatus(this.getStatus());
//		item.setLstCatagoryInforBeans(this.getLstCatagoryInforBeans());
//		item.setIsRequire(this.getIsRequire());
//		item.setType(this.getType());
//		
//		return item;
//	}
	
	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		try{
			return super.clone();
		}catch(CloneNotSupportedException ex){
			return null;
		}
		
	}
}
