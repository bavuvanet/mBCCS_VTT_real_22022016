package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@SuppressWarnings("serial")
@Root(name = "return",strict=false)
public class Serial implements Serializable {
	@Element (name = "fromSerial", required = false)
	private String fromSerial;
	@Element (name = "toSerial", required = false)
	private String toSerial;

	public Serial() {
	}

	public Serial(String fromSerial, String toSerial) {
		super();
		this.fromSerial = fromSerial;
		this.toSerial = toSerial;
	}

	public String getFromSerial() {
		return fromSerial;
	}

	public void setFromSerial(String fromSerial) {
		this.fromSerial = fromSerial;
	}

	public String getToSerial() {
		return toSerial;
	}

	public void setToSerial(String toSerial) {
		this.toSerial = toSerial;
	}

	public long getNumber() {
		Long numberSerial = 0L;
		if (toSerial == null || fromSerial == null) {
			numberSerial = 1L;
		} else if (toSerial.equals(fromSerial)) {
			numberSerial = 1L;
		} else {
			numberSerial = Long.parseLong(toSerial)
					- Long.parseLong(fromSerial) + 1;
		}

		return numberSerial;
	}

	@Override
	public String toString() {
		return fromSerial;
	}

	public boolean isContains(Serial child) {
		if (child == null) {
			return false;
		}
		long parentFrom = Long.parseLong(fromSerial);
		long parentTo = Long.parseLong(toSerial);
		long childFrom = Long.parseLong(child.getFromSerial());
		long childTo = Long.parseLong(child.getToSerial());
		if (childFrom >= parentFrom && childTo <= parentTo) {
			return true;
		}
		return false;
	}
}
