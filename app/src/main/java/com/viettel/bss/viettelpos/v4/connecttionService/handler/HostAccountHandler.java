package com.viettel.bss.viettelpos.v4.connecttionService.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HostAccountBeans;

public class HostAccountHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private HostAccountBeans temp = null;
	private ArrayList<HostAccountBeans> lstData = null;
	public CommonOutput getItem() {
		return item;
	}
	public ArrayList<HostAccountBeans> getLstData() {
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
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		currentElement = true;
		currentValue = "";
		if (localName.equalsIgnoreCase("return")) {
			item = new CommonOutput();
			lstData = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstSubscriber")) {
			temp = new HostAccountBeans();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
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
		
		// get lishost account
		if(localName.equalsIgnoreCase("account")){
			temp.setAccount(currentValue);
		}else if(localName.equalsIgnoreCase("subId")){
			temp.setSubId(currentValue);
		}else if(localName.equalsIgnoreCase("technology")){
			temp.setTehcology(currentValue);
		}else if (localName.equalsIgnoreCase("lstSubscriber")) {
			lstData.add(temp);
		}
	}

}
