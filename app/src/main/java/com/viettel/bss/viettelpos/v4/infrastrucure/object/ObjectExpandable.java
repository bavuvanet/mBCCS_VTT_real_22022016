package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import java.util.List;

public class ObjectExpandable {
	private final String name;
	private final int groupId;
	private final String indexId;
	private final int indexLevel;
	private final String listCellId;
	private final String unit;
	private final String total;
	private int parentIndexId;

	private List<ObjectExpandable> objects;

	
	public ObjectExpandable(String name, int groupId, String indexId,
			int indexLevel, String listCellId, String unit, String total,
			int parentIndexId, List<ObjectExpandable> objects) {
		super();
		this.name = name;
		this.groupId = groupId;
		this.indexId = indexId;
		this.indexLevel = indexLevel;
		this.listCellId = listCellId;
		this.unit = unit;
		this.total = total;
		this.parentIndexId = parentIndexId;
		this.objects = objects;
	}

	public ObjectExpandable(String name, int groupId, String indexId,
			int indexLevel, String listCellId, String unit, String total,
			List<ObjectExpandable> objects) {
		this.name = name;
		this.objects = objects;
		this.groupId = groupId;
		this.indexId = indexId;
		this.indexLevel = indexLevel;
		this.listCellId = listCellId;
		this.unit = unit;
		this.total = total;
	}

	public int getParentIndexId() {
		return parentIndexId;
	}

	public void setParentIndexId(int parentIndexId) {
		this.parentIndexId = parentIndexId;
	}

	public String getName() {
		return this.name;
	}

	public int getGroupId() {
		return this.groupId;
	}

	public String getIndexId() {
		return this.indexId;
	}

	public int getIndexLevel() {
		return this.indexLevel;
	}

	public String getListCellId() {
		return this.listCellId;
	}

	public String getUnit() {
		return this.unit;
	}

	public String getTotal() {
		return this.total;
	}

	public List<ObjectExpandable> getObjects() {
		return this.objects;
	}

	public void setObjects(List<ObjectExpandable> objects) {
		this.objects = objects;
	}
}
