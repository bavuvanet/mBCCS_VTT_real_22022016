package com.viettel.bss.viettelpos.v4.connecttionMobile.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PakageChargeBeans;

class PakageChangeHanlder extends DefaultHandler{
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private PakageChargeBeans temp = null;
	private ArrayList<PakageChargeBeans> lstData = null;
	private  int NAME_PAKAGE = 0;
	private final int NAME_PAKAGE_CM = 1;
	private final int NAME_PAKAGEfeature_CM = 2;
	
	public CommonOutput getItem() {
		return item;
	}

	public ArrayList<PakageChargeBeans> getLstData() {
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
				else if (localName.equalsIgnoreCase("offerId")) {
					switch (NAME_PAKAGE) {
					case NAME_PAKAGE_CM:
						temp.setOfferId(currentValue);
						NAME_PAKAGE = 0;
						break;
					case NAME_PAKAGEfeature_CM:
						NAME_PAKAGE = 0;
						break;
					default:
						break;
					}
					Log.d("offerId", currentValue);
					temp.setOfferId(currentValue);
				} else if (localName.equalsIgnoreCase("offerName")) {
					switch (NAME_PAKAGE) {
					case NAME_PAKAGE_CM:
						temp.setOfferName(currentValue);
						NAME_PAKAGE = 0;
						break;
					case NAME_PAKAGEfeature_CM:
						NAME_PAKAGE = 0;
						break;
					default:
						break;
					}
				} else if (localName.equalsIgnoreCase("productCode")) {
					Log.d("productCode", currentValue);
					temp.setProductCode(currentValue);
				}else if (localName.equalsIgnoreCase("lstProductOfferCM")) {
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
		} else if (localName.equalsIgnoreCase("lstProductOfferCM")) {
			NAME_PAKAGE = NAME_PAKAGE_CM;
			temp = new PakageChargeBeans();
		}else if(localName.equalsIgnoreCase("lstProductOfferFeatureView")){
			NAME_PAKAGE = NAME_PAKAGEfeature_CM;
		}
	}
	
}
