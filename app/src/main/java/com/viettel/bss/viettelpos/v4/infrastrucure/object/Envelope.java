package com.viettel.bss.viettelpos.v4.infrastrucure.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Namespace;
import org.simpleframework.xml.Root;

@Root
@Namespace(prefix ="S", reference = "http://schemas.xmlsoap.org/soap/envelope/")
class Envelope {

	@Element (name = "Body")
//	@Namespace(prefix ="S")
	private Body body;

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	



}
