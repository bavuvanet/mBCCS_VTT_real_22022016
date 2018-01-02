package com.viettel.bss.viettelpos.v4.sale.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.sale.object.SaleTrans;

public class ListSaleTransHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private List<SaleTrans> lstSaleTrans;
	private SaleTrans saleTrans;
	private String token;

	// private ArrayList<TotalMoneyBill> itemsList = new
	// ArrayList<TotalMoneyBill>();

	public CommonOutput getItem() {
		return item;
	}

	public List<SaleTrans> getListSaleTrans() {
		if (lstSaleTrans == null) {
			return new ArrayList<>();
		}
		return lstSaleTrans;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
			lstSaleTrans = new ArrayList<>();
		} else if (localName.equals("lstSaleTrans")) {
			saleTrans = new SaleTrans();
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
		} else if (localName.equals("saleTransId")) {
			saleTrans.setSaleTransId(Long.parseLong(currentValue));
		} else if (localName.equals("token")) {
			token = currentValue;
		}

		else if (localName.equals("saleTransDate")) {
			saleTrans.setSaleTransDate(currentValue);
		} else if (localName.equals("saleTransCode")) {
			saleTrans.setSaleTransCode(currentValue);
		} else if (localName.equals("custName")) {
			saleTrans.setCustName(currentValue);
		} else if (localName.equals("amountNotTax")) {
			saleTrans.setAmountNotTax(currentValue);
		} else if (localName.equals("tax")) {
			saleTrans.setTax(currentValue);
		} else if (localName.equals("amountTax")) {
			saleTrans.setAmountTax(currentValue);
		} else if (localName.equals("discount")) {
			saleTrans.setDiscount(currentValue);
		} else if (localName.equals("saleTransType")) {
			saleTrans.setSaleTransType(currentValue);
		} else if (localName.equals("lstSaleTrans")) {
			lstSaleTrans.add(saleTrans);
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