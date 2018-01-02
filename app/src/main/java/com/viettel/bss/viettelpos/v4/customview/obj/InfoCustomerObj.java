package com.viettel.bss.viettelpos.v4.customview.obj;

class InfoCustomerObj {
	private String nameKH;
	private String ngaySinh;
	private boolean isCheck;

	public InfoCustomerObj(String nameKH, String ngaySinh, boolean isCheck) {
		super();
		this.nameKH = nameKH;
		this.ngaySinh = ngaySinh;
		this.isCheck = isCheck;
	}

	public String getNameKH() {
		return nameKH;
	}

	public void setNameKH(String nameKH) {
		this.nameKH = nameKH;
	}

	public String getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}

}
