package com.viettel.bss.viettelpos.v4.sale.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.TotalMoneyBean;

public class TotalMoneyBillHanlder extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private TotalMoneyBean totalMoneyBill = null;

	public CommonOutput getItem() {
		return item;
	}

	public TotalMoneyBean getTotalMoneyBill() {
		return totalMoneyBill;
	}

	// Called when tag starts
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		currentElement = true;
		currentValue = "";
		if (localName.equals("return")) {
			item = new CommonOutput();
		} else if (localName.equals("result")) {
			totalMoneyBill = new TotalMoneyBean();
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
		} else if (localName.equals("countRow")) {
			totalMoneyBill.setAmount(currentValue);
		} else if (localName.equals("amountNotTax")) {
			totalMoneyBill.setMoneyPreTax(currentValue);
		} else if (localName.equals("amountTax")) {
			totalMoneyBill.setMoneyAfterTax(currentValue);
		} else if (localName.equals("discountAmount")) {
			totalMoneyBill.setDiscount(currentValue);
		} else if (localName.equals("vat")) {
			totalMoneyBill.setVat(currentValue);
		} else if (localName.equals("tax")) {
			totalMoneyBill.setTax(currentValue);
		} else if (localName.equals("token")) {
			item.setToken(currentValue);
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
