package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "cardInfoProvisioningDTO", strict = false)
public class CardInfoProvisioningDTO implements Serializable {
	@Element(name = "cardExpired", required = false)
	private String cardExpired;
	@Element(name = "cardSuspended", required = false)
    private String cardSuspended;
	@Element(name = "cardValue", required = false)
    private String cardValue;
	@Element(name = "dateUsed", required = false)
    private String dateUsed;
	@Element(name = "errorDescription", required = false)
    private String errorDescription;
	@Element(name = "isdn", required = false)
    private String isdn;
	@Element(name = "pinNumber", required = false)
    private String pinNumber;
	@Element(name = "responeCode", required = false)
    private String responeCode;
	@Element(name = "result", required = false)
    private boolean result;
	@Element(name = "valueCard", required = false)
    private String valueCard;
	
	
	
	public CardInfoProvisioningDTO() {
		super();
	}
	public String getCardExpired() {
		return cardExpired;
	}
	public void setCardExpired(String cardExpired) {
		this.cardExpired = cardExpired;
	}
	public String getCardSuspended() {
		return cardSuspended;
	}
	public void setCardSuspended(String cardSuspended) {
		this.cardSuspended = cardSuspended;
	}
	public String getCardValue() {
		return cardValue;
	}
	public void setCardValue(String cardValue) {
		this.cardValue = cardValue;
	}
	public String getDateUsed() {
		return dateUsed;
	}
	public void setDateUsed(String dateUsed) {
		this.dateUsed = dateUsed;
	}
	public String getErrorDescription() {
		return errorDescription;
	}
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	public String getIsdn() {
		return isdn;
	}
	public void setIsdn(String isdn) {
		this.isdn = isdn;
	}
	public String getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}
	public String getResponeCode() {
		return responeCode;
	}
	public void setResponeCode(String responeCode) {
		this.responeCode = responeCode;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getValueCard() {
		return valueCard;
	}
	public void setValueCard(String valueCard) {
		this.valueCard = valueCard;
	}
    
    
    
    
}
