package com.viettel.bss.viettelpos.v4.report.object;

import org.simpleframework.xml.Element;

public class AttributeObjectBO {
	@Element(name = "columnDescription", required = false)
	private String columnDescription;
	@Element(name = "columnName", required = false)
	private String columnName;
	@Element(name = "columnValue", required = false)
	private String columnValue;

	public String getColumnDescription() {
		return columnDescription;
	}

	public void setColumnDescription(String columnDescription) {
		this.columnDescription = columnDescription;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

}
