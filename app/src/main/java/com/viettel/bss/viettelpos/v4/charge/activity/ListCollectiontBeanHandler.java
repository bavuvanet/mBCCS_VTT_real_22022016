package com.viettel.bss.viettelpos.v4.charge.activity;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.charge.object.ChargeCustomerOJ;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

class ListCollectiontBeanHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = new CommonOutput();

	private ChargeCustomerOJ mObj;

	private ArrayList<ChargeCustomerOJ> mListObj;

	public ListCollectiontBeanHandler() {
		super();

		// setVsaMenu(new VSA());
	}

	public ChargeCustomerOJ getmObj() {
		return mObj;
	}

	public void setmObj(ChargeCustomerOJ mObj) {
		this.mObj = mObj;
	}

	public ArrayList<ChargeCustomerOJ> getListObj() {
		return mListObj;
	}

	public void setListObj(ArrayList<ChargeCustomerOJ> mListObj) {
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
		} else if (localName.equalsIgnoreCase("lstCollectionManagementBean")) {
			mObj = new ChargeCustomerOJ();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */
		if (localName.equalsIgnoreCase("custName")) {
			mObj.setNameCustomer(currentValue);
		} else if (localName.equalsIgnoreCase("address")) {
			mObj.setAddr(currentValue);
		} else if (localName.equalsIgnoreCase("isdn")) {
			mObj.setISDN(currentValue);
		} else if (localName.equalsIgnoreCase("serviceName")) {
			mObj.setServiceName(currentValue);
		}else if (localName.equalsIgnoreCase("appliedCycle")) {
			mObj.setAppliedCycle(currentValue);
		}else if (localName.equalsIgnoreCase("contractFormMngtGroup")) {
			mObj.setContractFormMngtGroup(currentValue);
		}else if (localName.equalsIgnoreCase("billCycleFrom")) {
			mObj.setBillCycleFrom(currentValue);
		}else if (localName.equalsIgnoreCase("serviceCode")) {
			mObj.setServiceCode(currentValue);
		}else if (localName.equalsIgnoreCase("contractId")) {
			mObj.setContractId(currentValue);
		}else if (localName.equalsIgnoreCase("groupId")) {
			mObj.setGroupId(currentValue);
		}else if (localName.equalsIgnoreCase("isCloseCycle")) {
			mObj.setIsCloseCycle(currentValue);
		}else if (localName.equalsIgnoreCase("vmId")) {
			mObj.setVmId(currentValue);
		}

		else if (localName.equalsIgnoreCase("lstCollectionManagementBean")) {
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
