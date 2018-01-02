package com.viettel.bss.viettelpos.v4.connecttionMobile.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;

public class HTHMPREMobileHandler extends DefaultHandler{
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private HTHMBeans temp = null;
	private final int NAME_DES = 1;
	private final int NAME_DES_HTHM = 2;
	private int nameType = 0;
	private ArrayList<HTHMBeans> lstData = null;
	public CommonOutput getItem() {
		return item;
	}
	public ArrayList<HTHMBeans> getLstData() {
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
			switch (nameType) {
			case 0:
				item.setDescription(currentValue);
				nameType = 0;
				break;
			case NAME_DES_HTHM:
				temp.setDescription(currentValue);
				nameType = 0;
				break;
			default:
				break;
			}
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
		}
		
		// GETDATA
		else if (localName.equalsIgnoreCase("code")) {
			temp.setCode(currentValue);
		} else if (localName.equalsIgnoreCase("name")) {
			temp.setName(currentValue);
		}else if (localName.equalsIgnoreCase("reasonId")) {
			temp.setReasonId(currentValue);
		} if (localName.equalsIgnoreCase("lstPrepaidReason")) {
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
			nameType = 0;
		} else if (localName.equalsIgnoreCase("lstPrepaidReason")) {
			nameType = NAME_DES_HTHM;
			temp = new HTHMBeans();
		}

	}
	
}
