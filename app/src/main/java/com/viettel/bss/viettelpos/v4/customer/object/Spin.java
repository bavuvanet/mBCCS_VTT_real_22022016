package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
@Root(name = "spin", strict = false)

public class Spin implements Serializable {

    @Element(name = "id", required = false)
	private String id;
    @Element(name = "value", required = false)
	private String value;
    @Element(name = "code", required = false)
	private String code;
    @Element(name = "name", required = false)
	private String name;

	private String isdnCalled;

	@Element(name = "nodeCode", required = false)
	private String nodeCode;
	@Element(name = "splitterCode", required = false)
	private String splitterCode;

	public String getSplitterCode() {
		return splitterCode;
	}

	public void setSplitterCode(String splitterCode) {
		this.splitterCode = splitterCode;
	}

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getIsdnCalled() {
		return isdnCalled;
	}

	public void setIsdnCalled(String isdnCalled) {
		this.isdnCalled = isdnCalled;
	}

	public Spin(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Spin() {
	}

	public Spin(String id, String value) {
		super();
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
        if(code != null && name != null){
            return code + "-" + name;
        }

		if (value == null || value.isEmpty()) {
			return name;
		}else{
			return value;
		}
	}

}
