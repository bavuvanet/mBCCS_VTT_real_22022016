package com.viettel.bss.viettelpos.v4.connecttionMobile.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.PakageVasBean;

public class PakageVasHanlder extends DefaultHandler{

	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private PakageVasBean temp = null;
	private ArrayList<PakageVasBean> lstData = null;
	
	public CommonOutput getItem() {
		return item;
	}

	public ArrayList<PakageVasBean> getLstData() {
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
				else if (localName.equalsIgnoreCase("attributeList")) {
					temp.setAttributeList(currentValue);
				}else if (localName.equalsIgnoreCase("cmConnected")) {
					temp.setCmConnected(currentValue);
				}else if (localName.equalsIgnoreCase("constraintId")) {
					temp.setConstraintId(currentValue);
				}else if (localName.equalsIgnoreCase("isConnected")) {
					temp.setIsConnected(currentValue);
				}else if (localName.equalsIgnoreCase("relProductCode")) {
					temp.setRelProductCode(currentValue);
				}else if (localName.equalsIgnoreCase("relProductName")) {
					temp.setRelProductName(currentValue);
				}else if (localName.equalsIgnoreCase("sizeParam")) {
					temp.setSizeParam(currentValue);
				}else if (localName.equalsIgnoreCase("vasDefault")) {
					temp.setVasDefault(currentValue);
				}else if (localName.equalsIgnoreCase("status")) {
					temp.setStatus(currentValue);
					
				}
				else if (localName.equalsIgnoreCase("lstSubRelProduct")) {
					if(temp.getStatus() == null || temp.getStatus().isEmpty() ||
							temp.getStatus().equalsIgnoreCase("0")){
						temp.setCheck(false);
					}else{
						temp.setCheck(true);
					}
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
		} else if (localName.equalsIgnoreCase("lstSubRelProduct")) {
			temp = new PakageVasBean();
		}
		
	}

	
	
}
