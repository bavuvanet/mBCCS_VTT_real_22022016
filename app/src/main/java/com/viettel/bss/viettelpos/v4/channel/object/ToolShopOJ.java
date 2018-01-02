package com.viettel.bss.viettelpos.v4.channel.object;

class ToolShopOJ {
	private String toolShopTypeName;
	private Long toolShopNumber;
	private String toolShopNote;
	public ToolShopOJ(String toolShopTypeName, Long toolShopNumber,
			String toolShopNote) {
		super();
		this.toolShopTypeName = toolShopTypeName;
		this.toolShopNumber = toolShopNumber;
		this.toolShopNote = toolShopNote;
	}
	public String getToolShopTypeName() {
		return toolShopTypeName;
	}
	public void setToolShopTypeName(String toolShopTypeName) {
		this.toolShopTypeName = toolShopTypeName;
	}
	public Long getToolShopNumber() {
		return toolShopNumber;
	}
	public void setToolShopNumber(Long toolShopNumber) {
		this.toolShopNumber = toolShopNumber;
	}
	public String getToolShopNote() {
		return toolShopNote;
	}
	public void setToolShopNote(String toolShopNote) {
		this.toolShopNote = toolShopNote;
	}
	
	
}
