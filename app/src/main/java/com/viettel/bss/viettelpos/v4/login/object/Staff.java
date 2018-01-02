package com.viettel.bss.viettelpos.v4.login.object;

import java.io.File;
import java.io.Serializable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import com.viettel.bss.viettelpos.v4.commons.Constant;
import com.viettel.bss.viettelpos.v4.commons.Session;
import com.viettel.bss.viettelpos.v4.ui.image.SerialBitmap;

@Root(name = "return", strict = false)
public class Staff implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Element(name = "agentName", required = false)
	private String nameStaff;
	@Element(name = "agentAddress", required = false)
	private String addressStaff;
	@Element(name = "link_photo", required = false)
	private String link_photo;
	@Element(name = "idUser_no", required = false)
	private String idUser_no;
	@Element(name = "idNo_Date", required = false)
	private String idNo_Date;
	@Element(name = "birthDay", required = false)
	private String birthDay;
	@Element(name = "agentId", required = false)
	private Long staffId;
	@Element(name = "staffCode", required = false)
	private String staffCode = "";
	@Element(name = "distance", required = false)
	private Float distance;
	@Element(name = "channelTypeId", required = false)
	private Long channelTypeId;
	@Element(name = "x", required = false)
	private Double x;
	@Element(name = "y", required = false)
	private Double y;
	@Element(name = "imgUrl", required = false)
	private String imgUrl;
	@Element(name = "imgPath", required = false)
	private String imgPath;
	@Element(name = "visitNum", required = false)
	private Long visitNum;
	@Element(name = "staffType", required = false)
	private Long staffType;
	@Element(name = "totalRevenue", required = false)
	private Long totalRevenue;
	@Element(name = "tel", required = false)
	private String tel;
	@Element(name = "isdnAgent", required = false)
	private String isdnAgent;
	@Element(name = "SerialBitmap", required = false)
	private SerialBitmap bitmap;

	@Element(name = "province", required = false)
	private String province;
	@Element(name = "district", required = false)
	private String district;
	@Element(name = "precinct", required = false)
	private String precinct;
	// Thuytv3_start
	@Element(name = "contractMethod", required = false)
	private String contractMethod;
	@Element(name = "businessMethod", required = false)
	private String businessMethod;
	@Element(name = "pointOfSaleType", required = false)
	private String pointOfSaleType;
	@Element(name = "street", required = false)
	private String street;
	@Element(name = "streetBlock", required = false)
	private String streetBlock;
	@Element(name = "home", required = false)
	private String home;
	@Element(name = "idIssuePlace", required = false)
	private String idIssuePlace;
	@Element(name = "idIssueDate", required = false)
	private String idIssueDate;
	private Long careNumber;
	private Long sprId;

	// Thuytv3_end

	public Staff() {

	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getPrecinct() {
		return precinct;
	}

	public void setPrecinct(String precinct) {
		this.precinct = precinct;
	}

	public Staff(Long staffId, String nameStaff, String address,
			String link_photo, String idUser_no, String idNo_Date,
			String birthDay, String channelCode, float distance,
			Long channelTypeId, Double x, Double y) {
		super();
		this.nameStaff = nameStaff;
		this.link_photo = link_photo;
		this.idUser_no = idUser_no;
		this.idNo_Date = idNo_Date;
		this.distance = distance;
		this.birthDay = birthDay;
		this.staffId = staffId;
		this.addressStaff = address;
		this.staffCode = channelCode;
		this.channelTypeId = channelTypeId;
		this.x = x;
		this.y = y;
	}

	public Staff(Long staffId, String nameStaff, String address,
			String link_photo, String idUser_no, String idNo_Date,
			String birthDay, String channelCode, float distance,
			Long channelTypeId, Double x, Double y, String tel, String isdnAgent) {
		super();
		this.nameStaff = nameStaff;
		this.link_photo = link_photo;
		this.idUser_no = idUser_no;
		this.idNo_Date = idNo_Date;
		this.distance = distance;
		this.birthDay = birthDay;
		this.staffId = staffId;
		this.addressStaff = address;
		this.staffCode = channelCode;
		this.channelTypeId = channelTypeId;
		this.x = x;
		this.y = y;
		this.tel = tel;
		this.isdnAgent = isdnAgent;
		this.idIssueDate = idNo_Date;
	}

	public Staff(Long staffId, String nameStaff, String address,
			String link_photo, String idUser_no, String idNo_Date,
			String birthDay, String channelCode, float distance,
			Long channelTypeId, Double x, Double y, String tel,
			String isdnAgent, String bussinesMethod, String contractMethod,
			String pointOfSaleType, String province, String district,
			String precent, String street, String streetBlock, String home) {
		super();
		this.nameStaff = nameStaff;
		this.link_photo = link_photo;
		this.idUser_no = idUser_no;
		this.idNo_Date = idNo_Date;
		this.distance = distance;
		this.birthDay = birthDay;
		this.staffId = staffId;
		this.addressStaff = address;
		this.staffCode = channelCode;
		this.channelTypeId = channelTypeId;
		this.x = x;
		this.y = y;
		this.tel = tel;
		this.isdnAgent = isdnAgent;
		this.businessMethod = bussinesMethod;
		this.contractMethod = contractMethod;
		this.pointOfSaleType = pointOfSaleType;
		this.province = province;
		this.district = district;
		this.precinct = precent;
		this.street = street;
		this.streetBlock = streetBlock;
		this.home = home;
	}

	public Staff(Long staffId, String nameStaff, String address,
			String link_photo, String idUser_no, String idNo_Date,
			String birthDay, String channelCode, float distance,
			Long channelTypeId, Double x, Double y, String tel,
			String isdnAgent, String bussinesMethod, String contractMethod,
			String pointOfSaleType, String province, String district,
			String precent, String street, String streetBlock, String home, String idIssuePlace) {
		super();
		this.nameStaff = nameStaff;
		this.link_photo = link_photo;
		this.idUser_no = idUser_no;
		this.idNo_Date = idNo_Date;
		this.distance = distance;
		this.birthDay = birthDay;
		this.staffId = staffId;
		this.addressStaff = address;
		this.staffCode = channelCode;
		this.channelTypeId = channelTypeId;
		this.x = x;
		this.y = y;
		this.tel = tel;
		this.isdnAgent = isdnAgent;
		this.businessMethod = bussinesMethod;
		this.contractMethod = contractMethod;
		this.pointOfSaleType = pointOfSaleType;
		this.province = province;
		this.district = district;
		this.precinct = precent;
		this.street = street;
		this.streetBlock = streetBlock;
		this.home = home;
		this.idIssuePlace = idIssuePlace;
		this.idIssueDate = idNo_Date;
	}
	
	public Staff(Long staffId, String nameStaff, String address,
			String link_photo, String idUser_no, String idNo_Date,
			String birthDay, String channelCode, Long channelTypeId, Double x,
			Double y) {
		super();
		this.nameStaff = nameStaff;
		this.link_photo = link_photo;
		this.idUser_no = idUser_no;
		this.idNo_Date = idNo_Date;
		this.birthDay = birthDay;
		this.staffId = staffId;
		this.addressStaff = address;
		this.staffCode = channelCode;
		this.channelTypeId = channelTypeId;
		this.x = x;
		this.y = y;

	}

	public Staff(Long staffId, String nameStaff, String address,
			String link_photo, String idUser_no, String idNo_Date,
			String birthDay, String channelCode, float distance,
			Long channelTypeId, Double x, Double y, Long staffType) {
		super();
		this.nameStaff = nameStaff;
		this.link_photo = link_photo;
		this.idUser_no = idUser_no;
		this.idNo_Date = idNo_Date;
		this.distance = distance;
		this.birthDay = birthDay;
		this.staffId = staffId;
		this.addressStaff = address;
		this.staffCode = channelCode;
		this.channelTypeId = channelTypeId;
		this.x = x;
		this.y = y;
		this.staffType = staffType;
	}

	public String getIsdnAgent() {
		return isdnAgent;
	}

	public void setIsdnAgent(String isdnAgent) {
		this.isdnAgent = isdnAgent;
	}

	public Long getTotalRevenue() {
		return totalRevenue;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public void setTotalRevenue(Long totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public Long getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Long visitNum) {
		this.visitNum = visitNum;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public Long getStaffType() {
		return staffType;
	}

	public void setStaffType(Long staffType) {
		this.staffType = staffType;
	}

	public Long getStaffId() {
		return staffId;
	}

	public void setStaffId(Long staffId) {
		this.staffId = staffId;
	}

	public Long getChannelTypeId() {
		return channelTypeId;
	}

	public void setChannelTypeId(Long channelTypeId) {
		this.channelTypeId = channelTypeId;
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public String getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIdNo_Date() {
		return idNo_Date;
	}

	public void setIdNo_Date(String idNo_Date) {
		this.idNo_Date = idNo_Date;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getIdUser_no() {
		return idUser_no;
	}

	public void setIdUser_no(String idUser_no) {
		this.idUser_no = idUser_no;
	}

	public String getNameStaff() {
		return nameStaff;
	}

	public void setNameStaff(String tenDiemban) {
		this.nameStaff = tenDiemban;
	}

	public String getLink_photo() {
		return Constant.LINK_WS_SYNC + "image" + File.separator + Session.getToken() + File.separator + staffId;
	}

	public void setLink_photo(String link_photo) {
		this.link_photo = link_photo;
	}

	public String getAddressStaff() {
		return addressStaff;
	}

	public void setAddressStaff(String addressStaff) {
		this.addressStaff = addressStaff;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Staff)) {
			return false;
		}
		Staff that = (Staff) other;
		// Custom equality check here.
		return this.getStaffCode().equals(that.getStaffCode());
	}

	@Override
	public int hashCode() {
		int hashCode = 0;

		hashCode = hashCode * 37 + this.staffCode.hashCode();
		hashCode = hashCode * 37 + this.staffId.hashCode();

		return hashCode;
	}

	public void setSerialBitmap(SerialBitmap bitmap) {
		this.bitmap = bitmap;
	}

	public SerialBitmap getSerialBitmap() {
		return bitmap;
	}

	public String getContractMethod() {
		return contractMethod;
	}

	public void setContractMethod(String contractMethod) {
		this.contractMethod = contractMethod;
	}

	public String getBusinessMethod() {
		return businessMethod;
	}

	public void setBusinessMethod(String businessMethod) {
		this.businessMethod = businessMethod;
	}

	public String getPointOfSaleType() {
		return pointOfSaleType;
	}

	public void setPointOfSaleType(String pointOfSaleType) {
		this.pointOfSaleType = pointOfSaleType;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetBlock() {
		return streetBlock;
	}

	public void setStreetBlock(String streetBlock) {
		this.streetBlock = streetBlock;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public String getIdIssuePlace() {
		return idIssuePlace;
	}

	public void setIdIssuePlace(String idIssuePlace) {
		this.idIssuePlace = idIssuePlace;
	}

	public String getIdIssueDate() {
		return idIssueDate;
	}

	public void setIdIssueDate(String idIssueDate) {
		this.idIssueDate = idIssueDate;
	}

	public Long getCareNumber() {
		return careNumber;
	}

	public void setCareNumber(Long careNumber) {
		this.careNumber = careNumber;
	}

	public Long getSprId() {
		return sprId;
	}

	public void setSprId(Long sprId) {
		this.sprId = sprId;
	}
}
