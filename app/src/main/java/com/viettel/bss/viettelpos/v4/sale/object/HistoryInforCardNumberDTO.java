package com.viettel.bss.viettelpos.v4.sale.object;

import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "historyInforCardNumberDTO", strict = false)
public class HistoryInforCardNumberDTO implements Serializable {
	@Element(name = "cardInfoProvisioningDTO", required = false)
	private CardInfoProvisioningDTO cardInfoProvisioningDTO;
	@Element(name = "stockCardNumber", required = false)
	private StockCardFullDTO stockCardNumber;

	
	
	
	public HistoryInforCardNumberDTO() {
		super();
	}

	public CardInfoProvisioningDTO getCardInfoProvisioningDTO() {
		return cardInfoProvisioningDTO;
	}

	public void setCardInfoProvisioningDTO(
			CardInfoProvisioningDTO cardInfoProvisioningDTO) {
		this.cardInfoProvisioningDTO = cardInfoProvisioningDTO;
	}

	public StockCardFullDTO getStockCardNumber() {
		return stockCardNumber;
	}

	public void setStockCardNumber(StockCardFullDTO stockCardNumber) {
		this.stockCardNumber = stockCardNumber;
	}

}
