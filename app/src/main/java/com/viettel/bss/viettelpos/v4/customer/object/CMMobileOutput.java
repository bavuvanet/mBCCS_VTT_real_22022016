package com.viettel.bss.viettelpos.v4.customer.object;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.customview.obj.RecordPrepaid;
import com.viettel.bss.viettelpos.v4.object.FormBean;

@Root(name = "return", strict = false)
public class CMMobileOutput {
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "subscriber", required = false)
	private SubscriberDTO subscriberPre;
	@ElementList(name = "lstFormBean", entry = "lstFormBean", required = false, inline = true)
	private List<FormBean> lstFormBean;
	@ElementList(name = "lstReasonDTO", entry = "lstReasonDTO", required = false, inline = true)
	private List<ReasonDTO> lstReasonDTO;
	@ElementList(name = "lstRecordProfile", entry = "lstRecordProfile", required = false, inline = true)
	private List<RecordPrepaid> lstProfileRecord;


	public List<ReasonDTO> getLstReasonDTO() {
		return lstReasonDTO;
	}

	public void setLstReasonDTO(List<ReasonDTO> lstReasonDTO) {
		this.lstReasonDTO = lstReasonDTO;
	}

	// private Subscriber
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public SubscriberDTO getSubscriberPre() {
		return subscriberPre;
	}

	public void setSubscriberPre(SubscriberDTO subscriberPre) {
		this.subscriberPre = subscriberPre;
	}

	public List<FormBean> getLstFormBean() {
		return lstFormBean;
	}

	public void setLstFormBean(List<FormBean> lstFormBean) {
		this.lstFormBean = lstFormBean;
	}
	public List<RecordPrepaid> getLstProfileRecord() {
		return lstProfileRecord;
	}


	public void setLstProfileRecord(List<RecordPrepaid> lstProfileRecord) {
		this.lstProfileRecord = lstProfileRecord;
	}
}
