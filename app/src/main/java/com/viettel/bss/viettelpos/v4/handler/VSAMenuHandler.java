package com.viettel.bss.viettelpos.v4.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.object.VSA;

public class VSAMenuHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = new CommonOutput();
	private VSA vsaMenu;
	private ArrayList<VSA> lstVSAMenu;
	private String upgradeDescription;
	private String forceUpgrade;
	private String version;
	private String isTracking;
	private String checkSyn;
	private String daysBetweenExpried;
	private String ttnsCode;
	private String debugMsg;
	private String publicKey;
	private String privateKey;

	public String getDebugMsg() {
		return debugMsg;
	}

	public void setDebugMsg(String debugMsg) {
		this.debugMsg = debugMsg;
	}

	public String getDaysBetweenExpried() {
		return daysBetweenExpried;
	}

	public void setDaysBetweenExpried(String daysBetweenExpried) {
		this.daysBetweenExpried = daysBetweenExpried;
	}

	public String getCheckSyn() {
		return checkSyn;
	}

	private void setCheckSyn(String checkSyn) {
		this.checkSyn = checkSyn;
	}

	public String getIsTracking() {
		return isTracking;
	}

	public void setIsTracking(String isTracking) {
		this.isTracking = isTracking;
	}

	public String getUpgradeDescription() {
		return upgradeDescription;
	}

	private void setUpgradeDescription(String upgradeDescription) {
		this.upgradeDescription = upgradeDescription;
	}

	public String getForceUpgrade() {
		return forceUpgrade;
	}

	private void setForceUpgrade(String forceUpgrade) {
		this.forceUpgrade = forceUpgrade;
	}

	public String getVersion() {
		return version;
	}

	private void setVersion(String version) {
		this.version = version;
	}

	public VSAMenuHandler() {
		super();
		setVsaMenu(new VSA());
	}

	public CommonOutput getItem() {
		return item;
	}

	public VSA getVsaMenu() {
		return vsaMenu;
	}

	private void setVsaMenu(VSA vsaMenu) {
		this.vsaMenu = vsaMenu;
	}

	public ArrayList<VSA> getLstVSAMenu() {
		return lstVSAMenu;
	}

	public void setLstVSAMenu(ArrayList<VSA> lstVSAMenu) {
		this.lstVSAMenu = lstVSAMenu;
	}

	public String getTtnsCode() {
		return ttnsCode;
	}

	public void setTtnsCode(String ttnsCode) {
		this.ttnsCode = ttnsCode;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentElement = true;
		currentValue = "";
		if (localName.equals("return")) {
			item = new CommonOutput();
			lstVSAMenu = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("lstMenu")) {
			vsaMenu = new VSA();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */
		if (localName.equalsIgnoreCase("errorCode")) {
			item.setErrorCode(currentValue);
		} else if (localName.equalsIgnoreCase("description")) {
			item.setDescription(currentValue);
		} else if (localName.equalsIgnoreCase("token")) {
			item.setToken(currentValue);
		} else if (localName.equalsIgnoreCase("objectName")) {
			vsaMenu.setObjectName(currentValue);
		} else if (localName.equalsIgnoreCase("upgradeDescription")) {
			setUpgradeDescription(currentValue);
		} else if (localName.equalsIgnoreCase("forceUpgrade")) {
			setForceUpgrade(currentValue);
		} else if (localName.equalsIgnoreCase("version")) {
			setVersion(currentValue);
		} else if (localName.equalsIgnoreCase("checkSyn")) {
			setCheckSyn(currentValue);
		} else if (localName.equalsIgnoreCase("isTracking")) {
			isTracking = currentValue;
		} else if (localName.equalsIgnoreCase("passwordDaysExpire")) {
			daysBetweenExpried = currentValue;
		} else if (localName.equalsIgnoreCase("ttnsCode")) {
			ttnsCode = currentValue;
		} else if (localName.equalsIgnoreCase("debugMsg")) {
			debugMsg = currentValue;
		} else if (localName.equalsIgnoreCase("publicKey")) {
			publicKey = currentValue;
		} else if (localName.equalsIgnoreCase("privateKey")) {
			privateKey = currentValue;
		}

		else if (localName.equalsIgnoreCase("lstMenu")) {
			lstVSAMenu.add(vsaMenu);
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
