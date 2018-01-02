package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root(name = "Envelope")
@Namespace(prefix = "S", reference = "http://schemas.xmlsoap.org/soap/envelope/")
class EnvelopeStation {

	@Element(name = "Body")
	// @Namespace(prefix ="S")
	private BodyStation body;

	public BodyStation getBody() {
		return body;
	}

	public void setBody(BodyStation body) {
		this.body = body;
	}

}
