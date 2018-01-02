package com.viettel.bss.viettelpos.v4.customer.object;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.StringUtils;
import com.viettel.bss.viettelpos.v4.work.object.DataComboboxBeanJson;

public class CorporationCategoryBO implements Serializable{
	@SerializedName("dataType")
	public String dataType;
	@SerializedName("displayOrder")
	public String displayOrder;
	@SerializedName("inforCode")
	public String inforCode;
	@SerializedName("inforName")
	public String inforName;
	@SerializedName("isLeaves")
	public String isLeaves;
	@SerializedName("isRequire")
	public String isRequire;
	@SerializedName("isTitle")
	public String isTitle;
	@SerializedName("parentCode")
	public String parentCode;
	@SerializedName("status")
	public String status;
	@SerializedName("type")
	public String type;
	
	@SerializedName("inforValue")
	public String inforValue;

	@SerializedName("lstDataCombo")
	public ArrayList<DataComboboxBeanJson> lstDataCombo;

	public String nameValue;

	@SerializedName("corporationCategoryId")
	public Long corporationCategoryId;
	
	@SerializedName("corpInforDetailId")
	public Long corpInforDetailId;
	
	@SerializedName("parentId")
	public Long parentId;
	
	@SerializedName("lstCatagoryInfors")
	public ArrayList<CorporationCategoryBO> lstCatagoryInfors;
	
	@SerializedName("path")
	public String path;
	
	@SerializedName("isView")
	public String isView;
	
	public String idInsert;

	private boolean isApprove = false;
	
	
	
	public boolean isApprove() {
		return isApprove;
	}

	public void setApprove(boolean isApprove) {
		this.isApprove = isApprove;
	}

	public String getIsView() {
		return isView;
	}

	public void setIsView(String isView) {
		this.isView = isView;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getIdInsert() {
		return idInsert;
	}

	public void setIdInsert(String idInsert) {
		this.idInsert = idInsert;
	}

	public Long getCorpInforDetailId() {
		return corpInforDetailId;
	}

	public void setCorpInforDetailId(Long corpInforDetailId) {
		this.corpInforDetailId = corpInforDetailId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ArrayList<CorporationCategoryBO> getLstCatagoryInfors() {
		return lstCatagoryInfors;
	}

	public void setLstCatagoryInfors(ArrayList<CorporationCategoryBO> lstCatagoryInfors) {
		this.lstCatagoryInfors = lstCatagoryInfors;
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

	public String getInforValue() {
		return inforValue;
	}

	public void setInforValue(String inforValue) {
		this.inforValue = inforValue;
	}

	public ArrayList<DataComboboxBeanJson> getLstDataCombo() {
		return lstDataCombo;
	}

	public void setLstDataCombo(ArrayList<DataComboboxBeanJson> lstDataCombo) {
		this.lstDataCombo = lstDataCombo;
	}

	public String getNameValue() {
		return nameValue;
	}

	public void setNameValue(String nameValue) {
		this.nameValue = nameValue;
	}

	public Long getCorporationCategoryId() {
		return corporationCategoryId;
	}

	public void setCorporationCategoryId(Long corporationCategoryId) {
		this.corporationCategoryId = corporationCategoryId;
	}
	
	
	public String toXML() {
		StringBuilder sb = new StringBuilder();
		
		
		
		
		if(!CommonActivity.isNullOrEmpty(path) && path.contains(Constant.PATH_BUSSNESS)){
			sb.append("<lstCorpServiceCategory>");
		}else{
			sb.append("<lstCategoryInfor>");
		}
		
			if(!CommonActivity.isNullOrEmpty(idInsert)){
				
			}else{
				sb.append("<corpInforDetailId>");
				sb.append(corpInforDetailId+"");
				sb.append("</corpInforDetailId>");
			}
			
			if(!CommonActivity.isNullOrEmpty(parentId)){
				sb.append("<parentId>");
				sb.append(parentId+"");
				sb.append("</parentId>");
			}
			


//        <lstCategoryInfor>
//        <dataType>COMBOBOX</dataType>
//        <inforCode>TINH</inforCode>
//        <inforValue>H004</inforValue>
//        <isLeaves>1</isLeaves>
//        <isRequire>1</isRequire>
//        <parentCode>TINH</parentCode>
//        <type>0</type>
//     </lstCategoryInfor>
		
		sb.append("<dataType>");
		sb.append(dataType);
		sb.append("</dataType>");
		
		
		sb.append("<inforCode>");
		sb.append(inforCode);
		sb.append("</inforCode>");
		
		
		
		if("DATE".equals(dataType)){
			if(!CommonActivity.isNullOrEmpty(inforValue)){
				   SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
			        try {  
			            Date date = sdf.parse(inforValue);  
			            String datetime = sdf2.format(date);
			        	sb.append("<inforValue>");
						sb.append(datetime);
						sb.append("</inforValue>");
			        } catch (ParseException e) {  
			            e.printStackTrace();  
			        }
			}
			
		}else{
			sb.append("<inforValue>");
			try {
				sb.append(StringUtils.escapeHtml(CommonActivity.getNormalText(inforValue)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			sb.append("</inforValue>");
		}
		
		
		
		sb.append("<isLeaves>");
		sb.append(isLeaves);
		sb.append("</isLeaves>");
		
		sb.append("<isRequire>");
		sb.append(isRequire);
		sb.append("</isRequire>");
		
		sb.append("<parentCode>");
		sb.append(parentCode);
		sb.append("</parentCode>");
		
		sb.append("<type>");
		sb.append(type);
		sb.append("</type>");
	

		

		if(!CommonActivity.isNullOrEmpty(path)&& path.contains(Constant.PATH_BUSSNESS)){
			sb.append("</lstCorpServiceCategory>");
		}else{
			sb.append("</lstCategoryInfor>");
		}
		
		return sb.toString();
	}
	
	
	@Override
	public String toString() {
		return String.format(
				"{\"CorporationCategoryBO\":{\"dataType\":\"%s\", \"displayOrder\":\"%s\", \"inforCode\":\"%s\", \"inforName\":\"%s\", \"isLeaves\":\"%s\", \"isRequire\":\"%s\", \"isTitle\":\"%s\", \"parentCode\":\"%s\", \"status\":\"%s\", \"type\":\"%s\", \"inforValue\":\"%s\"}}",
				dataType, displayOrder, inforCode, inforName, isLeaves, isRequire, isTitle, parentCode, status, type, inforValue);
	}
	
}
