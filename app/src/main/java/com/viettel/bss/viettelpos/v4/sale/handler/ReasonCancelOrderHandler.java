package com.viettel.bss.viettelpos.v4.sale.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.ReasonCancleOrder;

public class ReasonCancelOrderHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private List<ReasonCancleOrder> result;
	private ReasonCancleOrder temp;

	public List<ReasonCancleOrder> getResult() {
		return result;
	}

	public CommonOutput getItem() {
		return item;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		currentElement = true;
		currentValue = "";
		if (localName.equals("return")) {
			item = new CommonOutput();
			result = new ArrayList<>();
		} else if (localName.equals("lstReasonLib")) {
			temp = new ReasonCancleOrder();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		/** set value */
		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equals("reasonCode")) {
			temp.setReasonCode(currentValue);
		} else if (localName.equals("reasonId")) {
			temp.setReasonId(currentValue);
		} else if (localName.equals("reasonName")) {
			temp.setReasonName(currentValue);
		} else if (localName.equals("lstReasonLib")) {
			result.add(temp);
		}
	}

	// Called to get tag characters
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}
}
