package com.viettel.bss.viettelpos.v4.connecttionMobile.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.StockTypeBeans;

import android.util.Log;

public class StockTypeHanlderPre extends DefaultHandler{
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private StockTypeBeans temp = null;
	private ArrayList<StockTypeBeans> lstData = null;
	public CommonOutput getItem() {
		return item;
	}
	public ArrayList<StockTypeBeans> getLstData() {
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
		else if (localName.equalsIgnoreCase("stockId")) {
			temp.setStockId(currentValue);
		} else if (localName.equalsIgnoreCase("stockName")) {
			temp.setStockName(currentValue);
		} else if (localName.equalsIgnoreCase("lstStockTypeBean")) {
			lstData.add(temp);
		} else if (localName.equalsIgnoreCase("saleCode")) {
			Log.d("saleCode", ""+ currentValue);
			temp.setSaleServiceCode(currentValue);
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
		} else if (localName.equalsIgnoreCase("lstStockTypeBean")) {
			temp = new StockTypeBeans();
		} 

	}
}
