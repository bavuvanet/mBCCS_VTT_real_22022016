package com.viettel.bss.viettelpos.v4.login.object;

import com.viettel.bss.viettelpos.v4.object.WarningStaffBO;

import java.util.ArrayList;

public class Manager {
	private int resIcon;
	private String nameManager;
	private int countManager;
	private String keyMenuName;
	private int id;
	private int backlog;
	private int term;
	private int allTask;
	private int failTask;
	private int warningTask;
	private String itemId;
	private String className;
	private String content;
	private String header;
    private ArrayList<WarningStaffBO> lstWarningStaffBOs;
	
	
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Manager() {
		super();
	}

	public Manager(int resIcon, String nameManager, int countManager,
			String keyMenuName) {
		super();
		this.resIcon = resIcon;
		this.nameManager = nameManager;
		this.countManager = countManager;
		this.keyMenuName = keyMenuName;
	}
	public Manager(int resIcon, String nameManager, int countManager,
			String keyMenuName,String content) {
		super();
		this.resIcon = resIcon;
		this.nameManager = nameManager;
		this.countManager = countManager;
		this.keyMenuName = keyMenuName;
		this.content = content;
	}

	public Manager(int resIcon, String nameManager, int countManager, int id) {
		super();
		this.resIcon = resIcon;
		this.nameManager = nameManager;
		this.countManager = countManager;
		this.id = id;
	}
	
	public Manager(int resIcon, String nameManager, int countManager, int backlog,int term,
			String keyMenuName) {
		super();
		this.resIcon = resIcon;
		this.nameManager = nameManager;
		this.countManager = countManager;
		this.backlog = backlog;
		this.term = term;
		this.keyMenuName = keyMenuName;
	}
	

	
	public int getAllTask() {
		return allTask;
	}

	public void setAllTask(int allTask) {
		this.allTask = allTask;
	}

	public int getFailTask() {
		return failTask;
	}

	public void setFailTask(int failTask) {
		this.failTask = failTask;
	}

	public int getWarningTask() {
		return warningTask;
	}

	public void setWarningTask(int warningTask) {
		this.warningTask = warningTask;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}


	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getKeyMenuName() {
		return keyMenuName;
	}

	public void setKeyMenuName(String keyMenuName) {
		this.keyMenuName = keyMenuName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public String getNameManager() {
		return nameManager;
	}

	public void setNameManager(String nameManager) {
		this.nameManager = nameManager;
	}

	public int getCountManager() {
		return countManager;
	}

	public void setCountManager(int countManager) {
		this.countManager = countManager;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

    public ArrayList<WarningStaffBO> getLstWarningStaffBOs() {
        return lstWarningStaffBOs;
    }

    public void setLstWarningStaffBOs(ArrayList<WarningStaffBO> lstWarningStaffBOs) {
        this.lstWarningStaffBOs = lstWarningStaffBOs;
    }
}
