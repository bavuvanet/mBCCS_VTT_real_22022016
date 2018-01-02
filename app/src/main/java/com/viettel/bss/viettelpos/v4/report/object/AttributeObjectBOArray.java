package com.viettel.bss.viettelpos.v4.report.object;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class AttributeObjectBOArray {
	@ElementList(name = "item", entry = "item", required = false, inline = true)
	private List<AttributeObjectBO> item;
	private AttributeObjectBO key;
	
	
	public AttributeObjectBO getKey() {
		return key;
	}

	public void setKey(AttributeObjectBO key) {
		this.key = key;
	}

	public List<AttributeObjectBO> getItem() {
		return item;
	}

	public void setItem(List<AttributeObjectBO> item) {
		this.item = item;
	}

}
