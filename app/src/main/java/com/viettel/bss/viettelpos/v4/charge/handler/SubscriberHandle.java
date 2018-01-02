package com.viettel.bss.viettelpos.v4.charge.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.charge.object.ContractPromotionObj;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

public class SubscriberHandle extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private ContractPromotionObj temp = null;
	private ArrayList<ContractPromotionObj> lstData = null;

	public CommonOutput getItem() {
		return item;
	}

	public ArrayList<ContractPromotionObj> getLstData() {
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
		else if (localName.equalsIgnoreCase("subId")) {
			temp.setSubId(currentValue);
		} else if (localName.equalsIgnoreCase("payer")) {
			temp.setCustomerName(currentValue);
		} else if (localName.equalsIgnoreCase("isdn")) {
			temp.setIsdn(currentValue);
		} else if (localName.equalsIgnoreCase("payer")) {
			temp.setCustomerName(currentValue);
		} else if (localName.equalsIgnoreCase("promotionName")) {
			temp.setPromotionName(currentValue);
		}

		else if (localName.equalsIgnoreCase("contractNo")) {
			temp.setContractCode(currentValue);
		} else if (localName.equalsIgnoreCase("serviceType")) {
			temp.setServiceType(currentValue);
		} else if (localName.equalsIgnoreCase("address")) {
			temp.setAddressTBC(currentValue);
		} else if (localName.equalsIgnoreCase("promotionCode")) {
			temp.setPromotionCode(currentValue);
		} else if (localName.equalsIgnoreCase("startDate")) {
			temp.setStartTimePromotions(currentValue);
		} else if (localName.equalsIgnoreCase("endDate")) {
			temp.setEndTimePromotions(currentValue);
		} else if (localName.equalsIgnoreCase("lstSubPromotionBean")) {
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
		} else if (localName.equalsIgnoreCase("lstSubPromotionBean")) {
			temp = new ContractPromotionObj();
		}

	}

}
