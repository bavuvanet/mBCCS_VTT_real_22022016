package com.viettel.bss.viettelpos.v3.connecttionService.handle;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;

import android.util.Log;

public class PromotionHanlder extends DefaultHandler {
	Boolean currentElement = false;
	String currentValue = "";
	CommonOutput item = null;
	PromotionTypeBeans temp = null;
	private final int NAME_DES_HTHM = 2;
	private int nameType = 0;
	private ArrayList<PromotionTypeBeans> lstData = null;

	public CommonOutput getItem() {
		return item;
	}

	public ArrayList<PromotionTypeBeans> getLstData() {
		return lstData;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if (currentElement) {
			currentValue = currentValue + new String(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
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
		else if (localName.equalsIgnoreCase("codeName")) {
			Log.d("codeName", currentValue);
			temp.setCodeName(currentValue);;
		} else if (localName.equalsIgnoreCase("name")) {
			temp.setName(currentValue);
		} else if (localName.equalsIgnoreCase("code")) {
			temp.setPromProgramCode(currentValue);
		} else if (localName.equalsIgnoreCase("lstDiscountPromotionDTO")) {
			lstData.add(temp);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		currentElement = true;
		currentValue = "";
		if (localName.equalsIgnoreCase("return")) {
			item = new CommonOutput();
			lstData = new ArrayList<PromotionTypeBeans>();
			nameType = 0;
		} else if (localName.equalsIgnoreCase("lstDiscountPromotionDTO")) {
			nameType = NAME_DES_HTHM;
			temp = new PromotionTypeBeans();
		}
	}

}
