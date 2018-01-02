package com.viettel.bss.viettelpos.v4.channel.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;
@Root(name = "Envelope")
@Namespace(prefix = "S" , reference = "http://schemas.xmlsoap.org/soap/envelope/")
class SEnvelopeObjectCat {
	@Element(name = "Body")
	private SBodyObjectCat  sBodyObjectCat;

	public SBodyObjectCat getsBodyObjectCat() {
		return sBodyObjectCat;
	}

	public void setsBodyObjectCat(SBodyObjectCat sBodyObjectCat) {
		this.sBodyObjectCat = sBodyObjectCat;
	}
	
}
