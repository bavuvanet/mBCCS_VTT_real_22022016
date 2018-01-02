package com.viettel.bss.viettelpos.v4.charge.object;

public class GroupBeans {
	// ma to thu
	 private String groupId;
	 
	 // id ban cuoc
	 private String subBranchId;
	 
	 // id chi nhanh
	 private String branchId;
	 
	 private String staffCode;
	 private String name;
	 
	 // loai nhan vien 0 la nhan vien , 1 la cong tac vien
	 private String collaborator;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSubBranchId() {
		return subBranchId;
	}

	public void setSubBranchId(String subBranchId) {
		this.subBranchId = subBranchId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCollaborator() {
		return collaborator;
	}

	public void setCollaborator(String collaborator) {
		this.collaborator = collaborator;
	}
	 
	 
	 
	 
	 
}
