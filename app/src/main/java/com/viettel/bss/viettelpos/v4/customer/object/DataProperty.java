package com.viettel.bss.viettelpos.v4.customer.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "DataProperty", strict = false)
public class DataProperty {
	@Element (name = "name", required = false)
	private String name;
	@Element (name = "value", required = false)
	private String value;
	
	 public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public String getValue() {
         return value;
     }

     public void setValue(String value) {
         this.value = value;
     }
}
