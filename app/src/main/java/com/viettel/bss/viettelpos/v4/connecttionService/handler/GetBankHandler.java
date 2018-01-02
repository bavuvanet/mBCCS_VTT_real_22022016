package com.viettel.bss.viettelpos.v4.connecttionService.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.Bank;

public class GetBankHandler extends DefaultHandler{
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private Bank temp = null;
	private ArrayList<Bank> lstData = null;
	public CommonOutput getItem() {
		return item;
	}
	public ArrayList<Bank> getLstData() {
		return lstData;
	}
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		currentElement = false;

		/** set value */
		/* Common */
		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
		}
		
		// GETDATA
		else if (localName.equalsIgnoreCase("address")) {
			temp.setAddress(currentValue);
		} else if (localName.equalsIgnoreCase("bankCode")) {
			temp.setBankCode(currentValue);
		}else if (localName.equalsIgnoreCase("bankType")) {
			temp.setBankType(currentValue);
		} else if (localName.equalsIgnoreCase("name")) {
			temp.setName(currentValue);
		}else if (localName.equalsIgnoreCase("lstBank")) {
			lstData.add(temp);
		}
		
	}
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		currentElement = true;
		currentValue = "";
		if (localName.equalsIgnoreCase("return")) {
			item = new CommonOutput();
			lstData = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstBank")) {
			temp = new Bank();
		}

	}
}
