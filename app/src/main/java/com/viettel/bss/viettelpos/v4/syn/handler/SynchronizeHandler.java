package com.viettel.bss.viettelpos.v4.syn.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.syn.object.SynchronizeDataObject;

public class SynchronizeHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private StringBuilder currentValue = new StringBuilder("");

	private CommonOutput item = null;
	private ArrayList<SynchronizeDataObject> data = new ArrayList<>();
	private SynchronizeDataObject temp;

	public SynchronizeHandler() {
	}

	public ArrayList<SynchronizeDataObject> getData() {
		return data;
	}

	public void setData(ArrayList<SynchronizeDataObject> data) {
		this.data = data;
	}

	public CommonOutput getItem() {
		return item;
	}

	public void setItem(CommonOutput item) {
		this.item = item;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentElement = true;
		currentValue = new StringBuilder("");
		if (localName.equals("return")) {
			item = new CommonOutput();
		} else if (localName.equals("lstSyncResult")) {
			temp = new SynchronizeDataObject();
		}
	}

	// Called when tag closing
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */

		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue.toString());
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue.toString());
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue.toString());
			Session.setToken(currentValue.toString());
		} else if (localName.equalsIgnoreCase("oraRowscn")) {
			temp.setOraRowSCN(currentValue.toString());
		} else if (localName.equalsIgnoreCase("table")) {
			temp.setTableName(currentValue.toString());
		} else if (localName.equalsIgnoreCase("lstData")) {
			temp.addData(currentValue.toString());
		} else if (localName.equalsIgnoreCase("sql")) {
			temp.setQuery(currentValue.toString());
		} else if (localName.equals("lstSyncResult")) {
			data.add(temp);
		}
	}

	// Called to get tag characters
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (currentElement) {
			currentValue.append(new String(ch, start, length));
		}
	}
}
