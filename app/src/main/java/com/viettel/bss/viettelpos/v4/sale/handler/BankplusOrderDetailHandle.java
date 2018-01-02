package com.viettel.bss.viettelpos.v4.sale.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.StockModel;

public class BankplusOrderDetailHandle extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private StockModel temp = null;
	private ArrayList<StockModel> lstStockModel = null;

	public CommonOutput getItem() {
		return item;
	}

	public ArrayList<StockModel> getLstStockModel() {
		return lstStockModel;
	}

	// Called when tag starts
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		currentElement = true;
		currentValue = "";
		if (localName.equalsIgnoreCase("return")) {
			item = new CommonOutput();
			lstStockModel = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstOrderDetailBO")) {
			temp = new StockModel();
		}

	}

	// Called when tag closing
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
		/* Get sale item */
		else if (localName.equals("quantity")) {
			temp.setQuantitySaling(Long.parseLong(currentValue));
		} else if (localName.equals("stockModelCode")) {
			temp.setStockModelCode(currentValue);
		} else if (localName.equals("stockModelId")) {
			temp.setStockModelId(Long.parseLong(currentValue));
		} else if (localName.equals("stockModelName")) {
			temp.setStockModelName(currentValue);
		} else if (localName.equals("price")) {
			temp.setPrice(Long.parseLong(currentValue));
		} else if (localName.equals("transOrderDetailId")) {
			temp.setTransOrderDetailId(Long.parseLong(currentValue));
		} else if (localName.equalsIgnoreCase("lstOrderDetailBO")) {
			lstStockModel.add(temp);
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