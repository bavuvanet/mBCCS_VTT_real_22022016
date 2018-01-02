package com.viettel.bss.viettelpos.v4.charge.activity;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.charge.object.ChargeEmployeeOJ;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

class ListStaffBeanHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = new CommonOutput();

	private ChargeEmployeeOJ mObj;

	private ArrayList<ChargeEmployeeOJ> mListObj;

	public ListStaffBeanHandler() {
		super();

		// setVsaMenu(new VSA());
	}

	public ChargeEmployeeOJ getmObj() {
		return mObj;
	}

	public void setmObj(ChargeEmployeeOJ mObj) {
		this.mObj = mObj;
	}

	public ArrayList<ChargeEmployeeOJ> getmListObj() {
		return mListObj;
	}

	public void setmListObj(ArrayList<ChargeEmployeeOJ> mListObj) {
		this.mListObj = mListObj;
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
			mListObj = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstCollectionStaffBean")) {
			mObj = new ChargeEmployeeOJ();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */
		if (localName.equalsIgnoreCase("staffCode")) {
			mObj.setStaffCode(currentValue);
		} else if (localName.equalsIgnoreCase("staffId")) {
			mObj.setEmployeeId(currentValue);
		} else if (localName.equalsIgnoreCase("staffName")) {
			mObj.setNameEmpoyee(currentValue);
		} else if (localName.equalsIgnoreCase("isdn")) {
			mObj.setIsdn(currentValue);
		} 

		else if (localName.equalsIgnoreCase("lstCollectionStaffBean")) {
			mListObj.add(mObj);
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
