package com.viettel.bss.viettelpos.v4.customer.object;

import com.viettel.bss.viettelpos.v4.channel.object.SalePointsRouteDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.NotifyBean;
import com.viettel.bss.viettelpos.v4.login.object.Staff;
import com.viettel.bss.viettelpos.v4.object.ProgramBO;
import com.viettel.bss.viettelpos.v4.report.object.BonusVasObject;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "return", strict = false)
public class BOCOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "lstAssignIsdnStaffBean", entry = "lstAssignIsdnStaffBean", required = false, inline = true)
	private ArrayList<AssignIsdnStaffBean> lstAssignIsdnStaffBean = new ArrayList<>();

	@Element(name = "reasonLevelBO", required = false)
	private ReasonLevelBO reasonLevelBO;

	@Element(name = "reasonCusCareResult", required = false)
	private ReasonCusCareResult reasonCusCareResult = new ReasonCusCareResult();

	@ElementList(name = "lstReportCusCareBeans", entry = "lstReportCusCareBeans", required = false, inline = true)
	private ArrayList<ReportCusCareBean> lstReportCusCareBeans;

	@ElementList(name = "lstViewCareHistoryBO", entry = "lstViewCareHistoryBO", required = false, inline = true)
	private ArrayList<ViewCareHistoryBO> lstViewCareHistoryBOs;

	@ElementList(name = "lstIsdnOtherViettelSimBO", entry = "lstIsdnOtherViettelSimBO", required = false, inline = true)
	private ArrayList<AssignIsdnStaffBean> lstIsdnOtherViettelSimBOs;

	@ElementList(name = "lstAgentInDayMapBO", entry = "lstAgentInDayMapBO", required = false, inline = true)
	// @ElementList(name = "lstReasonCare", entry = "lstReasonCare", required =
	// false, inline = true)
	// private ArrayList<ReasonDetailBO> lstReasonCare = new
	// ArrayList<ReasonDetailBO>();
	private ArrayList<Staff> lstStaff;
	@Element(name = "note", required = false)
	private String note;

	@ElementList(name = "lstReasonCare", entry = "lstReasonCare", required = false, inline = true)
	private ArrayList<ReasonDetailBO> lstReasonCare = new ArrayList<>();

	@ElementList(name = "lstCustIdentityDTOs", entry = "lstCustIdentityDTOs", required = false, inline = true)
	private ArrayList<CustIdentityBO> lstCustIdentityBOs;

	@ElementList(name = "lstAccountDTO", entry = "lstAccountDTO", required = false, inline = true)
	private ArrayList<AccountBO> lstAccountDTOs;

	@ElementList(name = "lstSubBean", entry = "lstSubBean", required = false, inline = true)
	private ArrayList<SubBeanBO> lstSubBeanBOs;

	@ElementList(name = "lstObjectPropertys", entry = "lstObjectPropertys", required = false, inline = true)
	private ArrayList<ObjectProperty> lstObjectPropertys;
	@Element(name = "mark2SellingContactHisId", required = false)
	private Long mark2SellingContactHisId;

	@ElementList(name = "lstSheduleHistory", entry = "lstSheduleHistory", required = false, inline = true)
	private ArrayList<AssignIsdnStaffBean> lstSheduleHistory;
	
	@ElementList(name = "lstBonusVasObject", entry = "lstBonusVasObject", required = false, inline = true)
	private ArrayList<BonusVasObject> lstBonusVasObject;
	
	@ElementList(name = "lstImeiReceive", entry = "lstImeiReceive", required = false, inline = true)
	private ArrayList<DeviceWarrantyBO> lstImeiReceive;
	
	@ElementList(name = "lstWarrantyReturn", entry = "lstWarrantyReturn", required = false, inline = true)
	private ArrayList<DeviceWarrantyBO> lstWarrantyReturn;
	
	@ElementList(name = "lstWarrantyAction", entry = "lstWarrantyAction", required = false, inline = true)
	private ArrayList<DeviceWarrantyBO> lstWarrantyAction;
	@ElementList(name = "lstProductSpecCharDTOs", entry = "lstProductSpecCharDTOs", required = false, inline = true)
	private ArrayList<ProductSpecCharObj> lstProductSpecCharDTOs;
	@Element(name = "custCollectId", required = false)
	private String custCollectId;
	@ElementList(name = "lstProgramBOs", entry = "lstProgramBOs", required = false, inline = true)
	private ArrayList<ProgramBO> lstProgramBOs;

	@Element(name = "isCompany", required = false)
	private String isCompany;
	
	@Element(name = "refuseCollect", required = false)
	private boolean refuseCollect = false;
	
	@Element(name = "idNo", required = false)
	private String idNo;
	
	@Element(name = "accountOrIsdn", required = false)
	private String accountOrIsdn;
	@Element(name = "sprId", required = false)
	private Long sprId;
	@Element(name = "spchId", required = false)
	private Long spchId;

	public ArrayList<BonusVasObject> getLstBonusVasObject() {
		return lstBonusVasObject;
	}

	public void setLstBonusVasObject(ArrayList<BonusVasObject> lstBonusVasObject) {
		this.lstBonusVasObject = lstBonusVasObject;
	}


	public ReasonLevelBO getReasonLevelBO() {
		return reasonLevelBO;
	}

	public void setReasonLevelBO(ReasonLevelBO reasonLevelBO) {
		this.reasonLevelBO = reasonLevelBO;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<AssignIsdnStaffBean> getLstAssignIsdnStaffBean() {
		return lstAssignIsdnStaffBean;
	}

	public void setLstAssignIsdnStaffBean(
			ArrayList<AssignIsdnStaffBean> lstAssignIsdnStaffBean) {
		this.lstAssignIsdnStaffBean = lstAssignIsdnStaffBean;
	}

	public ArrayList<ReportCusCareBean> getLstReportCusCareBeans() {
		return lstReportCusCareBeans;
	}

	public void setLstReportCusCareBeans(
			ArrayList<ReportCusCareBean> lstReportCusCareBeans) {
		this.lstReportCusCareBeans = lstReportCusCareBeans;
	}

	public ArrayList<ViewCareHistoryBO> getLstViewCareHistoryBOs() {
		return lstViewCareHistoryBOs;
	}

	public void setLstViewCareHistoryBOs(
			ArrayList<ViewCareHistoryBO> lstViewCareHistoryBOs) {
		this.lstViewCareHistoryBOs = lstViewCareHistoryBOs;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public ArrayList<AssignIsdnStaffBean> getLstIsdnOtherViettelSimBOs() {
		return lstIsdnOtherViettelSimBOs;
	}

	public ArrayList<CustIdentityBO> getLstCustIdentityBOs() {
		return lstCustIdentityBOs;
	}

	public void setLstIsdnOtherViettelSimBOs(
			ArrayList<AssignIsdnStaffBean> lstIsdnOtherViettelSimBOs) {
		this.lstIsdnOtherViettelSimBOs = lstIsdnOtherViettelSimBOs;
	}

	// public ArrayList<ReasonDetailBO> getLstReasonCare() {
	// return lstReasonCare;
	// }
	//
	// public void setLstReasonCare(ArrayList<ReasonDetailBO> lstReasonCare) {
	// this.lstReasonCare = lstReasonCare;
	// }

	public void setLstCustIdentityBOs(
			ArrayList<CustIdentityBO> lstCustIdentityBOs) {
		this.lstCustIdentityBOs = lstCustIdentityBOs;
	}

	public ArrayList<AccountBO> getLstAccountDTOs() {
		return lstAccountDTOs;
	}

	public void setLstAccountDTOs(ArrayList<AccountBO> lstAccountDTOs) {
		this.lstAccountDTOs = lstAccountDTOs;
	}

	public ArrayList<SubBeanBO> getLstSubBeanBOs() {
		return lstSubBeanBOs;
	}

	public void setLstSubBeanBOs(ArrayList<SubBeanBO> lstSubBeanBOs) {
		this.lstSubBeanBOs = lstSubBeanBOs;
	}

	public ReasonCusCareResult getReasonCusCareResult() {
		return reasonCusCareResult;
	}

	public void setReasonCusCareResult(ReasonCusCareResult reasonCusCareResult) {
		this.reasonCusCareResult = reasonCusCareResult;
	}

	public ArrayList<ObjectProperty> getLstObjectPropertys() {
		return lstObjectPropertys;
	}

	public void setLstObjectPropertys(
			ArrayList<ObjectProperty> lstObjectPropertys) {
		this.lstObjectPropertys = lstObjectPropertys;
	}

	public ArrayList<Staff> getLstStaff() {
		return lstStaff;
	}

	public void setLstStaff(ArrayList<Staff> lstStaff) {
		this.lstStaff = lstStaff;
	}

	public Long getMark2SellingContactHisId() {
		return mark2SellingContactHisId;
	}

	public void setMark2SellingContactHisId(Long mark2SellingContactHisId) {
		this.mark2SellingContactHisId = mark2SellingContactHisId;
	}

	public ArrayList<AssignIsdnStaffBean> getLstSheduleHistory() {
		return lstSheduleHistory;
	}

	public void setLstSheduleHistory(
			ArrayList<AssignIsdnStaffBean> lstSheduleHistory) {
		this.lstSheduleHistory = lstSheduleHistory;
	}

	public ArrayList<ProductSpecCharObj> getLstProductSpecCharDTOs() {
		return lstProductSpecCharDTOs;
	}

	public void setLstProductSpecCharDTOs(
			ArrayList<ProductSpecCharObj> lstProductSpecCharDTOs) {
		this.lstProductSpecCharDTOs = lstProductSpecCharDTOs;
	}

	public String getCustCollectId() {
		return custCollectId;
	}

	public void setCustCollectId(String custCollectId) {
		this.custCollectId = custCollectId;
	}

	public String getIsCompany() {
		return isCompany;
	}

	public void setIsCompany(String isCompany) {
		this.isCompany = isCompany;
	}

	public boolean isRefuseCollect() {
		return refuseCollect;
	}

	public void setRefuseCollect(boolean refuseCollect) {
		this.refuseCollect = refuseCollect;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAccountOrIsdn() {
		return accountOrIsdn;
	}

	public void setAccountOrIsdn(String accountOrIsdn) {
		this.accountOrIsdn = accountOrIsdn;
	}
	public ArrayList<DeviceWarrantyBO> getLstImeiReceive() {
		return lstImeiReceive;
	}

	public void setLstImeiReceive(ArrayList<DeviceWarrantyBO> lstImeiReceive) {
		this.lstImeiReceive = lstImeiReceive;
	}

	public ArrayList<DeviceWarrantyBO> getLstWarrantyReturn() {
		return lstWarrantyReturn;
	}

	public void setLstWarrantyReturn(ArrayList<DeviceWarrantyBO> lstWarrantyReturn) {
		this.lstWarrantyReturn = lstWarrantyReturn;
	}

	public ArrayList<DeviceWarrantyBO> getLstWarrantyAction() {
		return lstWarrantyAction;
	}

	public void setLstWarrantyAction(ArrayList<DeviceWarrantyBO> lstWarrantyAction) {
		this.lstWarrantyAction = lstWarrantyAction;
	}

	public ArrayList<ReasonDetailBO> getLstReasonCare() {
		return lstReasonCare;
	}

	public void setLstReasonCare(ArrayList<ReasonDetailBO> lstReasonCare) {
		this.lstReasonCare = lstReasonCare;
	}

	public ArrayList<ProgramBO> getLstProgramBOs() {
		return lstProgramBOs;
	}

	public void setLstProgramBOs(ArrayList<ProgramBO> lstProgramBOs) {
		this.lstProgramBOs = lstProgramBOs;
	}

    @ElementList(name = "lstNotifyBeans", entry = "lstNotifyBeans", required = false, inline = true)
    private List<NotifyBean> lstNotifyBeans;

    public List<NotifyBean> getLstNotifyBeans() {
        return lstNotifyBeans;
    }

    public void setLstNotifyBeans(List<NotifyBean> lstNotifyBeans) {
        this.lstNotifyBeans = lstNotifyBeans;
    }

	@ElementList(name = "lstSalePointsRouteDTOs", entry = "lstSalePointsRouteDTOs", required = false, inline = true)
    private List<SalePointsRouteDTO> lstSalePointsRouteDTOs;

	public List<SalePointsRouteDTO> getLstSalePointsRouteDTOs() {
		return lstSalePointsRouteDTOs;
	}

	public void setLstSalePointsRouteDTOs(List<SalePointsRouteDTO> lstSalePointsRouteDTOs) {
		this.lstSalePointsRouteDTOs = lstSalePointsRouteDTOs;
	}

	public Long getSprId() {
		return sprId;
	}

	public void setSprId(Long sprId) {
		this.sprId = sprId;
	}

	public Long getSpchId() {
		return spchId;
	}

	public void setSpchId(Long spchId) {
		this.spchId = spchId;
	}
}
