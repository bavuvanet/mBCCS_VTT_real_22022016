package com.viettel.bss.viettelpos.v4.commons;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class CommonOutputHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;

	// private ArrayList<Output> itemsList = new ArrayList<Output>();

	// public ArrayList<Output> getItemsList() {
	// return itemsList;
	// }
	public CommonOutput getItem() {
		return item;
	}

	// Called when tag starts
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);

		currentElement = true;
		currentValue = "";
		if (localName.equalsIgnoreCase("Result")) {
			item = new CommonOutput();
		}

	}

	// Called when tag closing
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {

		currentElement = false;

		/** set value */
		if (localName.equalsIgnoreCase("error")) {
			item.setError(currentValue);
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
			item.setError(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("original")) {
			currentValue = currentValue.substring(
					currentValue.indexOf("<return>" ),
					currentValue.indexOf("</return>")+ 9);
			item.setOriginal(currentValue);
		} else if (localName.equalsIgnoreCase("return")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("result")) {
			// itemsList.add(item);
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
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
