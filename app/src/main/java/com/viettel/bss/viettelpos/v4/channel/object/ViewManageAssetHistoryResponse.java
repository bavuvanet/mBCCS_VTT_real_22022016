package com.viettel.bss.viettelpos.v4.channel.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
@Namespace(prefix = "ns2" , reference = "http://ws.smartphonev2.bss.viettel.com/")
public class ViewManageAssetHistoryResponse {
	@Element(name = "return")
	private ReturnOjectCat rObjectCat;

	public ReturnOjectCat getrObjectCat() {
		return rObjectCat;
	}

	public void setrObjectCat(ReturnOjectCat rObjectCat) {
		this.rObjectCat = rObjectCat;
	}
	
}
