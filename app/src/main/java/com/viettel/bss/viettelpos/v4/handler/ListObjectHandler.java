package com.viettel.bss.viettelpos.v4.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.channel.object.DetailAccountPaymentOJ;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

public class ListObjectHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private String paymentAccountTotal = "";
	private String totalDeposit = "";
	private String totalMoneySubmit = "";
	private CommonOutput item = new CommonOutput();

	private DetailAccountPaymentOJ detailAccountPaymentOJ;
	private ArrayList<DetailAccountPaymentOJ> mListObject;

	public ListObjectHandler() {
		super();

		// setVsaMenu(new VSA());
	}

	public String getPaymentAccountTotal() {
		return paymentAccountTotal;
	}

	public void setPaymentAccountTotal(String paymentAccountTotal) {
		this.paymentAccountTotal = paymentAccountTotal;
	}

	public String getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(String totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public String getTotalMoneySubmit() {
		return totalMoneySubmit;
	}

	public void setTotalMoneySubmit(String totalMoneySubmit) {
		this.totalMoneySubmit = totalMoneySubmit;
	}

	public ArrayList<DetailAccountPaymentOJ> getmListObject() {
		return mListObject;
	}

	public void setmListObject(ArrayList<DetailAccountPaymentOJ> mListObject) {
		this.mListObject = mListObject;
	}

	public CommonOutput getItem() {
		return item;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentElement = true;
		currentValue = "";
		if (localName.equals("return")) {
			item = new CommonOutput();
			mListObject = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("arrObject")) {
			detailAccountPaymentOJ = new DetailAccountPaymentOJ();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */
		if (localName.equalsIgnoreCase("name")) {
			detailAccountPaymentOJ.setName(currentValue);
		} else if (localName.equalsIgnoreCase("stockModelCode")) {
			detailAccountPaymentOJ.setStockCode(currentValue);

		} else if (localName.equalsIgnoreCase("toalDeposit")) {

			detailAccountPaymentOJ.setToalDeposit(Long.parseLong(currentValue));
		} else if (localName.equalsIgnoreCase("paymentAccountTotal")) {
			paymentAccountTotal = currentValue;
		} else if (localName.equalsIgnoreCase("totalDeposit")) {
			totalDeposit = currentValue;
		} else if (localName.equalsIgnoreCase("totalMoneySubmit")) {
			totalMoneySubmit = currentValue;
		}

		else if (localName.equalsIgnoreCase("arrObject")) {
			mListObject.add(detailAccountPaymentOJ);
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
