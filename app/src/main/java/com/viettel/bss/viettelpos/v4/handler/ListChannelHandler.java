package com.viettel.bss.viettelpos.v4.handler;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.location.Location;

import com.viettel.bss.viettelpos.v4.channel.dal.ChannelDAL;
import com.viettel.bss.viettelpos.v4.commons.CommonOutput;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.login.object.Staff;

public class ListChannelHandler extends DefaultHandler {
	private Boolean currentElement = false;
	private String currentValue = "";
	private CommonOutput item = new CommonOutput();

	private String numVisit;
	private String total;
	private String saleTransDate;
	private Long totalRevenue;

	private ArrayList<Staff> arrayStaff;
	private Staff staff;

	private final ChannelDAL channelDAL;
	private final Location myLocation;

	public ListChannelHandler(Activity activity, Location myLocation) {
		super();

		this.myLocation = myLocation;
		channelDAL = new ChannelDAL(activity);

		// setVsaMenu(new VSA());
	}

	public void closeDatabase() {
		try {
			channelDAL.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Long getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(Long totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public String getSaleTransDate() {
		return saleTransDate;
	}

	public void setSaleTransDate(String saleTransDate) {
		this.saleTransDate = saleTransDate;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getNumVisit() {
		return numVisit;
	}

	public void setNumVisit(String numVisit) {
		this.numVisit = numVisit;
	}

	public ArrayList<Staff> getArrayStaff() {
		return arrayStaff;
	}

	public void setArrayStaff(ArrayList<Staff> arrayStaff) {
		this.arrayStaff = arrayStaff;
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
			arrayStaff = new ArrayList<>();
		} else if (localName.equalsIgnoreCase("listStaff")
				|| localName.equalsIgnoreCase("lstStaff")) {
			staff = new Staff();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		currentElement = false;
		/** set value */
		if (localName.equalsIgnoreCase("staffCode")) {
			// staff.set(currentValue);
			if (!currentValue.equals(Session.userName)) {

				staff = channelDAL.getStaffByStaffCode("staff", currentValue,
						myLocation);

				// arrayStaff.add(tmStaff);
			}
		} else if (localName.equalsIgnoreCase("visitNum")) {
			Long visitNum = 0L;
			if (currentValue != null) {
				visitNum = Long.parseLong(currentValue);
			}
			staff.setVisitNum(visitNum);

		} else if (localName.equalsIgnoreCase("totalRevenue")) {
			// Long totalRevenueL = 0L;
			if (currentValue != null) {
				totalRevenue = Long.parseLong(currentValue);
			}
			staff.setTotalRevenue(totalRevenue);

		} else if (localName.equalsIgnoreCase("name")) {
			staff.setNameStaff(currentValue);

		} else if (localName.equalsIgnoreCase("numVisit")) {
			numVisit = currentValue;

		} else if (localName.equalsIgnoreCase("total")) {
			total = currentValue;

		} else if (localName.equalsIgnoreCase("maxSaleTransDate")) {
			saleTransDate = currentValue;

		}

		else if (localName.equalsIgnoreCase("lstStaff")
				|| localName.equalsIgnoreCase("listStaff")) {
			arrayStaff.add(staff);
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
