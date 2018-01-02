package com.viettel.bss.viettelpos.v4.connecttionService.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.customer.object.AssignIsdnStaffBean;

public class AssignIsdnHandler extends DefaultHandler{
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = null;
	private AssignIsdnStaffBean temp = null;
	private ArrayList<AssignIsdnStaffBean> lstData = null;
	
	public AssignIsdnHandler(){
		item = new CommonOutput();
		lstData = new ArrayList<>();
		temp = new AssignIsdnStaffBean();
	}
	
	public CommonOutput getItem() {
		return item;
	}
	public ArrayList<AssignIsdnStaffBean> getLstData() {
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
		else if (localName.equalsIgnoreCase("id")) {
			temp.setId(Long.parseLong(currentValue));
		} else if (localName.equalsIgnoreCase("isdn")) {
			temp.setIsdn(currentValue);
		}else if (localName.equalsIgnoreCase("districtCode")) {
			temp.setDistrictCode(currentValue);
		} else if (localName.equalsIgnoreCase("districtName")) {
			temp.setDistrictName(currentValue);
		}if (localName.equalsIgnoreCase("precinctCode")) {
			temp.setPrecinctCode(currentValue);
		} else if (localName.equalsIgnoreCase("precinctName")) {
			temp.setPrecinctName(currentValue);
		}else if (localName.equalsIgnoreCase("provinceCode")) {
			temp.setProvinceCode(currentValue);
		} else if (localName.equalsIgnoreCase("provinceName")) {
			temp.setProvinceName(currentValue);
		}if (localName.equalsIgnoreCase("reStaffCode")) {
			temp.setReStaffCode(currentValue);
		} else if (localName.equalsIgnoreCase("reasonCode")) {
			temp.setReasonCode(currentValue);
		}else if (localName.equalsIgnoreCase("reasonName")) {
			temp.setReasonName(currentValue);
		} else if (localName.equalsIgnoreCase("status")) {
			temp.setStatus(Integer.parseInt(currentValue));
		}if (localName.equalsIgnoreCase("address")) {
			temp.setAddress(currentValue);
		} else if (localName.equalsIgnoreCase("isdnInfor")) {
			temp.setIsdnInfor(currentValue);
		}else if (localName.equalsIgnoreCase("isdnViettelSim")) {
			temp.setIsdnViettelSim(currentValue);
		} else if (localName.equalsIgnoreCase("meetDate")) {
			temp.setMeetDate(currentValue);
		}if (localName.equalsIgnoreCase("note")) {
			temp.setNote(currentValue);
		} else if (localName.equalsIgnoreCase("assignDate")) {
			temp.setAssignDate(currentValue);
		}else if (localName.equalsIgnoreCase("userAssign")) {
			temp.setUserAssign(currentValue);
		} else if (localName.equalsIgnoreCase("isDisable")) {
			temp.setIsDisable(Integer.parseInt(currentValue));
		} else if (localName.equalsIgnoreCase("isSim4G")) {
			temp.setIsSim4G(Integer.parseInt(currentValue));
		}else if (localName.equalsIgnoreCase("lstAssignIsdnStaffBean")) {
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
		} else if (localName.equalsIgnoreCase("lstAssignIsdnStaffBean")) {
			temp = new AssignIsdnStaffBean();
		}

	}
}
