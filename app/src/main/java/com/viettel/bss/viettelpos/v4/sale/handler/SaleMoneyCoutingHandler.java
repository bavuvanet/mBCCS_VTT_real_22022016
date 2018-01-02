package com.viettel.bss.viettelpos.v4.sale.handler;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SaleMoneyCouting;

class SaleMoneyCoutingHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private String errorMsg = "";
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	private CommonOutput item = null;
	private SaleMoneyCouting data;

	public SaleMoneyCoutingHandler() {
	}

	public CommonOutput getItem() {
		return item;
	}

	public SaleMoneyCouting getData() {
		return data;
	}

	public void setData(SaleMoneyCouting data) {
		this.data = data;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentElement = true;
		currentValue = "";
		if (localName.equals("return")) {
			item = new CommonOutput();
			data = new SaleMoneyCouting();
		}
	}

	// Called when tag closing
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */
		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
		}else if(localName.equalsIgnoreCase("errMsg")){
			errorMsg = currentValue;
		}

//		List<String> lstAttribute = data.getListAttribute();
//		if (lstAttribute.contains(localName)) {
//			data.setValueOfAttribute(localName, currentValue);
//		}
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
