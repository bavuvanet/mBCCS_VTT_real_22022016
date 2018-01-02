package com.viettel.bss.viettelpos.v4.connecttionService.beans;

public class TypePaperBeans {
	private String idTypePaper;
	private String parNamePaper;
	private String parType;
	private String parValues;
	private String description;
	private String status;
	public String getIdTypePaper() {
		return idTypePaper;
	}
	public void setIdTypePaper(String idTypePaper) {
		this.idTypePaper = idTypePaper;
	}
	public String getParNamePaper() {
		return parNamePaper;
	}
	public void setParNamePaper(String parNamePaper) {
		this.parNamePaper = parNamePaper;
	}
	public String getParType() {
		return parType;
	}
	public void setParType(String parType) {
		this.parType = parType;
	}
	public String getParValues() {
		return parValues;
	}
	public void setParValues(String parValues) {
		this.parValues = parValues;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return String.format(
				"{\"TypePaperBeans\":{\"idTypePaper\":\"%s\", \"parNamePaper\":\"%s\", \"parType\":\"%s\", \"parValues\":\"%s\", \"description\":\"%s\", \"status\":\"%s\"}}",
				idTypePaper, parNamePaper, parType, parValues, description, status);
	}
	
}
