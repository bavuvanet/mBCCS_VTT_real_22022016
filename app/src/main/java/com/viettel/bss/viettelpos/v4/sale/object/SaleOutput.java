package com.viettel.bss.viettelpos.v4.sale.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bss.viettelpos.v4.charge.object.VirtualInvoice;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customview.adapter.DoiTuongObj;
import com.viettel.bss.viettelpos.v4.customview.obj.SpecObject;
import com.viettel.bss.viettelpos.v4.object.FormBean;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "return", strict = false)
public class SaleOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@Element(name = "saleTrans", required = false)
	private SaleTrans saleTrans;
	@Element(name = "productCode", required = false)
	private String productCode;
	@Element(name = "productType", required = false)
	private String productType;

	@ElementList(name = "lstCustomerDTO", entry = "lstCustomerDTO", required = false, inline = true)
	private ArrayList<CustomerDTO> lstCustomerDTO;

	@ElementList(name = "lstDepartmentDTO", entry = "lstDepartmentDTO", required = false, inline = true)
	private ArrayList<DoiTuongObj> lstDepartment;

	@ElementList(name = "lstReason", entry = "lstReason", required = false, inline = true)
	private ArrayList<ReasonDTO> lstReason;

	@Element(name = "subscriberDTO", required = false)
	private SubscriberDTO subscriberDTO;

	@Element(name = "price", required = false)
	private PriceBO price;
	@Element(name = "discount", required = false)
	private Long discount;
	@Element(name = "amountTax", required = false)
	private Long amountTax;
	@Element(name = "ocs", required = false)
	private String ocs;
	@Element(name = "hrl", required = false)
	private String hlr;
	@ElementList(name = "lstObjectSpecDTO", entry = "lstObjectSpecDTO", required = false, inline = true)
	private ArrayList<SpecObject> lstObject;
	@ElementList(name = "lstFormBean", entry = "lstFormBean", required = false, inline = true)
	private ArrayList<FormBean> lstFormBean;
	@ElementList(name = "lstAccountBookBankplusDTO", entry = "lstAccountBookBankplusDTO", required = false, inline = true)
	private List<AccountBookBankplusDTO> lstAccountBookBankplusDTO;
	@Element(name = "realBalance", required = false)
	private String realBalance;
	@ElementList(name = "lstOptionSetValueDTO", entry = "lstOptionSetValueDTO", required = false, inline = true)
	private List<OptionSetValueDTO> lstOptionSet;
	@Element(name = "virtualInvoice", required = false)
	private VirtualInvoice virtualInvoice;

	@ElementList(name = "lstSubDepositDTO", entry = "lstSubDepositDTO", required = false, inline = true)
	private List<SubDepositDTO> lstSubDeposit;

	@Element(name = "drawMethod", required = false)
	private String drawMethod;

	@ElementList(name = "lstSubSuccess", entry = "lstSubSuccess", required = false, inline = true)
	private List<SubscriberDTO> lstSubSuccess;

	public List<SubscriberDTO> getLstSubSuccess() {
		return lstSubSuccess;
	}

	public void setLstSubSuccess(List<SubscriberDTO> lstSubSuccess) {
		this.lstSubSuccess = lstSubSuccess;
	}
	@ElementList(name = "lstConfirmDebitTransDTOs", entry = "lstConfirmDebitTransDTOs", required = false, inline = true)
	private List<ConfirmDebitTransDTO> 	lstConfirmDebitTransDTOs;

	@Element(name = "taskId", required = false)
	private String taskId;


	@Element(name = "orderId", required = false)
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<ConfirmDebitTransDTO> getLstConfirmDebitTransDTOs() {
		return lstConfirmDebitTransDTOs;
	}

	public void setLstConfirmDebitTransDTOs(List<ConfirmDebitTransDTO> lstConfirmDebitTransDTOs) {
		this.lstConfirmDebitTransDTOs = lstConfirmDebitTransDTOs;
	}

	public List<SubDepositDTO> getLstSubDeposit() {
		return lstSubDeposit;
	}

	public String getDrawMethod() {
		return drawMethod;
	}

	public void setDrawMethod(String drawMethod) {
		this.drawMethod = drawMethod;
	}

	public void setLstSubDeposit(List<SubDepositDTO> lstSubDeposit) {
		this.lstSubDeposit = lstSubDeposit;
	}

	public List<OptionSetValueDTO> getLstOptionSet() {
		return lstOptionSet;
	}

	public void setLstOptionSet(List<OptionSetValueDTO> lstOptionSet) {
		this.lstOptionSet = lstOptionSet;
	}

	public VirtualInvoice getVirtualInvoice() {
		return virtualInvoice;
	}

	public void setVirtualInvoice(VirtualInvoice virtualInvoice) {
		this.virtualInvoice = virtualInvoice;
	}

	public String getRealBalance() {
		return realBalance;
	}

	public void setRealBalance(String realBalance) {
		this.realBalance = realBalance;
	}

	public ArrayList<ReasonDTO> getLstReason() {
		return lstReason;
	}

	public void setLstReason(ArrayList<ReasonDTO> lstReason) {
		this.lstReason = lstReason;
	}

	public List<AccountBookBankplusDTO> getLstAccountBookBankplusDTO() {
		return lstAccountBookBankplusDTO;
	}

	public void setLstAccountBookBankplusDTO(
			List<AccountBookBankplusDTO> lstAccountBookBankplusDTO) {
		this.lstAccountBookBankplusDTO = lstAccountBookBankplusDTO;
	}

	public SubscriberDTO getSubscriberDTO() {
		return subscriberDTO;
	}

	public void setSubscriberDTO(SubscriberDTO subscriberDTO) {
		this.subscriberDTO = subscriberDTO;
	}

	public ArrayList<FormBean> getLstFormBean() {
		return lstFormBean;
	}

	public void setLstFormBean(ArrayList<FormBean> lstFormBean) {
		this.lstFormBean = lstFormBean;
	}

	public ArrayList<SpecObject> getLstObject() {
		return lstObject;
	}

	public void setLstObject(ArrayList<SpecObject> lstObject) {
		this.lstObject = lstObject;
	}

	public ArrayList<DoiTuongObj> getLstDepartment() {
		return lstDepartment;
	}

	public void setLstDepartment(ArrayList<DoiTuongObj> lstDepartment) {
		this.lstDepartment = lstDepartment;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public ArrayList<CustomerDTO> getLstCustomerDTO() {
		return lstCustomerDTO;
	}

	public void setLstCustomerDTO(ArrayList<CustomerDTO> lstCustomerDTO) {
		this.lstCustomerDTO = lstCustomerDTO;
	}

	public String getOcs() {
		return ocs;
	}

	public void setOcs(String ocs) {
		this.ocs = ocs;
	}

	public String getHlr() {
		return hlr;
	}

	public void setHlr(String hlr) {
		this.hlr = hlr;
	}

	public Long getDiscount() {
		return discount;
	}

	public void setDiscount(Long discount) {
		this.discount = discount;
	}

	public Long getAmountTax() {
		return amountTax;
	}

	public void setAmountTax(Long amountTax) {
		this.amountTax = amountTax;
	}

	public PriceBO getPrice() {
		return price;
	}

	public void setPrice(PriceBO price) {
		this.price = price;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

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

	public SaleTrans getSaleTrans() {
		return saleTrans;
	}

	public void setSaleTrans(SaleTrans saleTrans) {
		this.saleTrans = saleTrans;
	}

    @ElementList(name = "lstProfileRecord", entry = "lstProfileRecord", required = false, inline = true)
    private ArrayList<com.viettel.bss.viettelpos.v4.customer.object.FormBean> lstProfileRecord;

    public ArrayList<com.viettel.bss.viettelpos.v4.customer.object.FormBean> getLstProfileRecord() {
        return lstProfileRecord;
    }

    public void setLstProfileRecord(ArrayList<com.viettel.bss.viettelpos.v4.customer.object.FormBean> lstProfileRecord) {
        this.lstProfileRecord = lstProfileRecord;
    }


}
