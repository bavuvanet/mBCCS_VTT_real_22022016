package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import com.viettel.bss.viettelpos.v4.object.WarningStaffBO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

@Root(name = "return",strict=false)
public class NotifyBean {
	@Element(name = "status", required = false)
	private String status;
	@Element(name = "name", required = false)
	private String name;
	@Element(name = "className", required = false)
	private String className;
	@Element(name = "content", required = false)
	private String content;
	@Element(name = "header", required = false)
	private String header;
    @ElementList(name = "lstWarningStaffBOs", entry = "lstWarningStaffBOs", required = false, inline = true)
    private ArrayList<WarningStaffBO> lstWarningStaffBOs;

	public NotifyBean() {
		super();
	}

	public NotifyBean(String status, String name) {
		super();
		this.status = status;
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

    public ArrayList<WarningStaffBO> getLstWarningStaffBOs() {
        return lstWarningStaffBOs;
    }

    public void setLstWarningStaffBOs(ArrayList<WarningStaffBO> lstWarningStaffBOs) {
        this.lstWarningStaffBOs = lstWarningStaffBOs;
    }
}
