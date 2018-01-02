package com.viettel.bss.viettelpos.v4.sale.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.StockOrderDetail;

public class StockOrderHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private StockOrderDetail temp = null;
	private ArrayList<StockOrderDetail> lstData = null;
	private String stockOrderCode = "";

	public CommonOutput getItem() {
		return item;
	}

	public String getStockOrderCode() {
		return stockOrderCode;
	}

	public void setStockOrderCode(String stockOrderCode) {
		this.stockOrderCode = stockOrderCode;
	}

	public ArrayList<StockOrderDetail> getLstData() {
		return lstData;
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
			lstData = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstStockOrderDetailDTO")) {
			temp = new StockOrderDetail();
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
		else if (localName.equals("quantityOrder")) {
			temp.setQuantityOder(Long.parseLong(currentValue));
		} else if (localName.equals("prodOfferId")) {
			temp.setStockModelId(Long.parseLong(currentValue));
		} else if (localName.equals("stockOrderCode")) {
			stockOrderCode = currentValue;
		}else if (localName.equals("lstStockOrderDetailDTO")) {
			lstData.add(temp);
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
