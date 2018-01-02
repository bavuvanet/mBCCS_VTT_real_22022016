package com.viettel.bss.viettelpos.v4.charge.activity;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.charge.object.ChargeItemGetDebit;
import com.viettel.bss.viettelpos.v4.charge.object.ChargeItemObjectDel;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;

class ListPaymentContractHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = new CommonOutput();

	private ChargeItemGetDebit mObj;
	
	private ChargeItemObjectDel chaItemObjectDel  ;

	private ArrayList<ChargeItemGetDebit> mListObj = new ArrayList<>();
	private ArrayList<ChargeItemObjectDel> mListItemObjectDels = new ArrayList<>();

	public ListPaymentContractHandler(int position) {
		super();

		// setVsaMenu(new VSA());
	}

	public ArrayList<ChargeItemObjectDel> getmListItemObjectDels() {
		return mListItemObjectDels;
	}

	public void setmListItemObjectDels(
			ArrayList<ChargeItemObjectDel> mListItemObjectDels) {
		this.mListItemObjectDels = mListItemObjectDels;
	}

	public ChargeItemGetDebit getmObj() {
		return mObj;
	}

	public void setmObj(ChargeItemGetDebit mObj) {
		this.mObj = mObj;
	}

	public ArrayList<ChargeItemGetDebit> getmListObj() {
		return mListObj;
	}

	public void setmListObj(ArrayList<ChargeItemGetDebit> mListObj) {
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
		} else if (localName.equalsIgnoreCase("paymentContractBean")) {
			mObj = new ChargeItemGetDebit();
		}else if (localName.equalsIgnoreCase("lstPaymentContractBean")) {
			
			chaItemObjectDel = new ChargeItemObjectDel();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/* Common */
		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
		}
		
		/** set value */
		if (localName.equalsIgnoreCase("adjustment")) {
			if(mObj != null){
				mObj.setAdjustment(currentValue);
			}
			if(chaItemObjectDel != null){
				chaItemObjectDel.setAdjustment(currentValue);
			}
			
		} else if (localName.equalsIgnoreCase("amountNotTax")) {
			if(mObj != null){
				mObj.setAmountNotTax(currentValue);
			}
			if(chaItemObjectDel != null){
				chaItemObjectDel.setAmountNotTax(currentValue);
			}
			
		} else if (localName.equalsIgnoreCase("amountTax")) {
			if(mObj != null){
				mObj.setAmountTax(currentValue);
			}
			if(chaItemObjectDel != null){
				chaItemObjectDel.setAmountTax(currentValue);
			}
			
		} else if (localName.equalsIgnoreCase("totCharge")) {
			if(mObj != null){
				mObj.setTotCharge(currentValue);
			}
			if(chaItemObjectDel != null){
				chaItemObjectDel.setTotCharge(currentValue);
			}
		} else if (localName.equalsIgnoreCase("billCycleFrom")) {
			if(mObj != null){
				mObj.setBillCycleFrom(currentValue);
			}
			if(chaItemObjectDel != null){
				chaItemObjectDel.setBillCycleFrom(currentValue);
			}
		} else if (localName.equalsIgnoreCase("contractId")) {
			if(mObj != null){
				mObj.setContractId(currentValue);
			}
			
			if(chaItemObjectDel != null){
				chaItemObjectDel.setContractId(currentValue);
			}
			
		}else if (localName.equalsIgnoreCase("custName")) {
			if(chaItemObjectDel != null){
				chaItemObjectDel.setNameCustomer(currentValue);
			}
			
		}else if (localName.equalsIgnoreCase("status")) {
			if(chaItemObjectDel != null){
				chaItemObjectDel.setStatus(currentValue);
			}
			
		}else if (localName.equalsIgnoreCase("address")) {
			if(chaItemObjectDel != null){
				chaItemObjectDel.setAddress(currentValue);
			}
			
		}else if (localName.equalsIgnoreCase("contractNo")) {
			if(chaItemObjectDel != null){
				chaItemObjectDel.setContractNo(currentValue);
			}
			
		}else if (localName.equalsIgnoreCase("groupId")) {
			if(chaItemObjectDel != null){
				chaItemObjectDel.setGroupId(currentValue);
			}
			
		}else if (localName.equalsIgnoreCase("isdn")) {
			if(chaItemObjectDel != null){
				chaItemObjectDel.setIsdn(currentValue);
			}
			
		}

		// add List
		else if (localName.equalsIgnoreCase("paymentContractBean")) {
			mListObj.add(mObj);
		}else if (localName.equalsIgnoreCase("lstPaymentContractBean")) {
			mListItemObjectDels.add(chaItemObjectDel);
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
