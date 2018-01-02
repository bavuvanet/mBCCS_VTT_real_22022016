package com.viettel.bss.viettelpos.v4.charge.object;



public class ChargeCustomerOJ  {

	// String name;
    private String addr;
	private String ISDN;
	private String serviceName;
	private boolean checked = false;
	private String nameCustomer;
	private String appliedCycle;
	private String contractFormMngtGroup;
	private String groupId;
	private String isCloseCycle;
	private String billCycleFrom;
	private String contractId;
	private String serviceCode;
	private String vmId;
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getBillCycleFrom() {
		return billCycleFrom;
	}
	public void setBillCycleFrom(String billCycleFrom) {
		this.billCycleFrom = billCycleFrom;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getAppliedCycle() {
		return appliedCycle;
	}
	public void setAppliedCycle(String appliedCycle) {
		this.appliedCycle = appliedCycle;
	}
	public String getContractFormMngtGroup() {
		return contractFormMngtGroup;
	}
	public void setContractFormMngtGroup(String contractFormMngtGroup) {
		this.contractFormMngtGroup = contractFormMngtGroup;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getIsCloseCycle() {
		return isCloseCycle;
	}
	public void setIsCloseCycle(String isCloseCycle) {
		this.isCloseCycle = isCloseCycle;
	}
	public String getNameCustomer() {
		return nameCustomer;
	}
	public void setNameCustomer(String nameCustomer) {
		this.nameCustomer = nameCustomer;
	}
	public boolean getChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddr() {
		return addr;
	}
	public void setISDN(String iSDN) {
		ISDN = iSDN;
	}
	public String getISDN() {
		return ISDN;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceName() {
		return serviceName;
	}
	
	
}
