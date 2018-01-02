package com.viettel.bss.viettelpos.v4.sale.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.BankplusOrderBO;

public class BankplusOrderBOHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private List<BankplusOrderBO> result;
	private BankplusOrderBO temp;
	private boolean isValue = false;

	public CommonOutput getItem() {
		return item;
	}

	public List<BankplusOrderBO> getResult() {
		return result;
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
		} else if (localName.equals("lstOrderBO")) {
			isValue = true;
			temp = new BankplusOrderBO();
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
			if (!isValue) {
				item.setDescription(currentValue);
			} else {
				temp.setDescription(currentValue);
			}
		} else if (localName.equals("orderCode")) {
			temp.setOrderCode(currentValue);
		} else if (localName.equals("staffCode")) {
			temp.setStaffCode(currentValue);
		} else if (localName.equals("staffName")) {
			temp.setStaffName(currentValue);
		} else if (localName.equals("bankPlusMobile")) {
			temp.setBankPlusMobile(currentValue);
		} else if (localName.equals("saleTransStatus")) {
			temp.setSaleTransStatus(currentValue);
		} else if (localName.equals("lstOrderBO")) {
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
