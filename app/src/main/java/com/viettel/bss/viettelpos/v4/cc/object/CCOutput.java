package com.viettel.bss.viettelpos.v4.cc.object;

import com.viettel.bss.viettelpos.v4.advisory.data.HistoryConsultBean;
import com.viettel.bss.viettelpos.v4.advisory.data.ProductGroupBean;
import com.viettel.bss.viettelpos.v4.advisory.data.RefillInfoData;
import com.viettel.bss.viettelpos.v4.advisory.data.SubPreChargeData;
import com.viettel.bss.viettelpos.v4.advisory.data.SubscriberInfoData;
import com.viettel.bss.viettelpos.v4.advisory.data.VtFreeActivedDTOData;
import com.viettel.bss.viettelpos.v4.advisory.data.VasDTOData;
import com.viettel.bss.viettelpos.v4.bo.CriteriaGroup;
import com.viettel.bss.viettelpos.v4.bo.ReportProfileBO;
import com.viettel.bss.viettelpos.v4.bo.StationBO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberFullDTO;
import com.viettel.bss.viettelpos.v4.object.ComplainDTO;
import com.viettel.bss.viettelpos.v4.object.ProblemHistory;
import com.viettel.bss.viettelpos.v4.object.ProblemProcessDTO;
import com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "return", strict = false)
public class CCOutput {
	@Element(name = "token", required = false)
	private String token;
	@Element(name = "errorCode", required = false)
	private String errorCode;
	@Element(name = "description", required = false)
	private String description;
	@ElementList(name = "listDetailCalled", entry = "listDetailCalled", required = false, inline = true)
	private List<DetailCalledDTO> listDetailCalled;
    @Element(name = "timeDeadline", required = false)
    private String timeDeadline;

	@Element(name = "summaryBO", required = false)
	private SummaryBo summaryBo;
	@ElementList(name = "listSubscriberCareBean", entry = "listSubscriberCareBean", required = false, inline = true)
	private ArrayList<SubscriberCareBean> listSubscriberCareBean;
	private SubscriberCareBean subscriberCareBean;
    @Element(name = "subscriberDTO", required = false)
    private SubscriberDTO subscriberDTO;
    @Element(name = "subscriberFullDTO", required = false)
    private SubscriberFullDTO subscriberFullDTO;
    @ElementList(name = "lstProblemPriorityDTOs", entry = "lstProblemPriorityDTOs", required = false, inline = true)
    private List<ComplainDTO> lstProblemPriorityDTOs;
    @ElementList(name = "lstProblemGroupDTOs", entry = "lstProblemGroupDTOs", required = false, inline = true)
    private List<ComplainDTO> lstProblemGroupDTOs;
    @ElementList(name = "lstProblemTypeDTOs", entry = "lstProblemTypeDTOs", required = false, inline = true)
    private List<ComplainDTO> lstProblemTypeDTOs;
    @ElementList(name = "lstSubscriberDTOs", entry = "lstSubscriberDTOs", required = false, inline = true)
    private List<SubscriberDTO> lstSubscriberDTOs;
    @ElementList(name = "lstProblemHistory", entry = "lstProblemHistory", required = false, inline = true)
    private List<ProblemHistory> lstProblemHistory;
    @ElementList(name = "lstProblemProcessDTOs", entry = "lstProblemProcessDTOs", required = false, inline = true)
    private List<ProblemProcessDTO> lstProblemProcessDTOs;
    @ElementList(name = "lstProbAcceptTypeDTOs", entry = "lstProbAcceptTypeDTOs", required = false, inline = true)
    private List<ComplainDTO> lstProbAcceptTypeDTOs;
    @ElementList(name = "stationBOs", entry = "stationBOs", required = false, inline = true)
    private List<StationBO> lstRevenueStation;
    @ElementList(name = "lstStationBOs", entry = "lstStationBOs", required = false, inline = true)
    private List<StationBO> lstStationBOs;
    @ElementList(name = "lstCriteriaBOs", entry = "lstCriteriaBOs", required = false, inline = true)
    private List<CriteriaGroup> lstCriteriaGroups;
    @ElementList(name = "lstReportProfile", entry = "lstReportProfile", required = false, inline = true)
    private List<ReportProfileBO> lstReportProfileBOs;

    // Tu van khach hang
    @Element(name = "subscriberInfo", required = false)
    private SubscriberInfoData subscriberInfoData;
    @ElementList(name = "lstSubPreCharges", entry = "lstSubPreCharges", required = false, inline = true)
    private List<SubPreChargeData> lstSubPreCharges;
    @ElementList(name = "lstVasDTOs", entry = "lstVasDTOs", required = false, inline = true)
    private List<VasDTOData> lstVasDTOs;
    @ElementList(name = "lstVtFreeActivedDTOs", entry = "lstVtFreeActivedDTOs", required = false, inline = true)
    private List<VtFreeActivedDTOData> lstVtFreeActivedDTOs;
    @ElementList(name = "lstRefillInfos", entry = "lstRefillInfos", required = false, inline = true)
    private List<RefillInfoData> lstRefillInfos;

    @ElementList(name = "lstProductGroupBeans", entry = "lstProductGroupBeans", required = false, inline = true)
    private List<ProductGroupBean> lstProductGroupBeans;
    @ElementList(name = "lstHistoryConsultBeans", entry = "lstHistoryConsultBeans", required = false, inline = true)
    private List<HistoryConsultBean> lstHistoryConsultBeans;
    @Element(name = "removeVSA", required = false)
    private String removeVSA;

    @Element(name = "fileContent", required = false)
    private String fileContent;
    @Element(name = "fileName", required = false)
    private String fileName;
    @Element(name = "recordId", required = false)
    private String recordId;
    @Element(name = "staffSignatureImage", required = false)
    private String staffSignatureImage;

    public List<ProductGroupBean> getLstProductGroupBeans() {
        return lstProductGroupBeans;
    }

    public void setLstProductGroupBeans(List<ProductGroupBean> lstProductGroupBeans) {
        this.lstProductGroupBeans = lstProductGroupBeans;
    }

    public List<HistoryConsultBean> getLstHistoryConsultBeans() {
        return lstHistoryConsultBeans;
    }

    public void setLstHistoryConsultBeans(List<HistoryConsultBean> lstHistoryConsultBeans) {
        this.lstHistoryConsultBeans = lstHistoryConsultBeans;
    }
    @Element(name = "isCVT", required = false)
    private Boolean isCVT;

    public SubscriberCareBean getSubscriberCareBean() {
		return subscriberCareBean;
	}

	public void setSubscriberCareBean(SubscriberCareBean subscriberCareBean) {
		this.subscriberCareBean = subscriberCareBean;
	}

	public ArrayList<SubscriberCareBean> getListSubscriberCareBean() {
		return listSubscriberCareBean;
	}

	public void setListSubscriberCareBean(
			ArrayList<SubscriberCareBean> listSubscriberCareBean) {
		this.listSubscriberCareBean = listSubscriberCareBean;
	}

	public SummaryBo getSummaryBo() {
		return summaryBo;
	}

	public void setSummaryBo(SummaryBo summaryBo) {
		this.summaryBo = summaryBo;
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

	public List<DetailCalledDTO> getListDetailCalled() {
		return listDetailCalled;
	}

	public void setListDetailCalled(List<DetailCalledDTO> listDetailCalled) {
		this.listDetailCalled = listDetailCalled;
	}

    public SubscriberFullDTO getSubscriberFullDTO() {
        return subscriberFullDTO;
    }

    public void setSubscriberFullDTO(SubscriberFullDTO subscriberFullDTO) {
        this.subscriberFullDTO = subscriberFullDTO;
    }

    public SubscriberDTO getSubscriberDTO() {
        return subscriberDTO;
    }

    public void setSubscriberDTO(SubscriberDTO subscriberDTO) {
        this.subscriberDTO = subscriberDTO;
    }

    public List<ComplainDTO> getLstProblemPriorityDTOs() {
        return lstProblemPriorityDTOs;
    }

    public void setLstProblemPriorityDTOs(List<ComplainDTO> lstProblemPriorityDTOs) {
        this.lstProblemPriorityDTOs = lstProblemPriorityDTOs;
    }

    public List<ComplainDTO> getLstProblemGroupDTOs() {
        return lstProblemGroupDTOs;
    }

    public void setLstProblemGroupDTOs(List<ComplainDTO> lstProblemGroupDTOs) {
        this.lstProblemGroupDTOs = lstProblemGroupDTOs;
    }

    public List<ComplainDTO> getLstProblemTypeDTOs() {
        return lstProblemTypeDTOs;
    }

    public void setLstProblemTypeDTOs(List<ComplainDTO> lstProblemTypeDTOs) {
        this.lstProblemTypeDTOs = lstProblemTypeDTOs;
    }

    public List<SubscriberDTO> getLstSubscriberDTOs() {
        return lstSubscriberDTOs;
    }

    public void setLstSubscriberDTOs(List<SubscriberDTO> lstSubscriberDTOs) {
        this.lstSubscriberDTOs = lstSubscriberDTOs;
    }

    public List<ProblemHistory> getLstProblemHistory() {
        return lstProblemHistory;
    }

    public void setLstProblemHistory(List<ProblemHistory> lstProblemHistory) {
        this.lstProblemHistory = lstProblemHistory;
    }

    public List<ProblemProcessDTO> getLstProblemProcessDTOs() {
        return lstProblemProcessDTOs;
    }

    public void setLstProblemProcessDTOs(List<ProblemProcessDTO> lstProblemProcessDTOs) {
        this.lstProblemProcessDTOs = lstProblemProcessDTOs;
    }

    public List<ComplainDTO> getLstProbAcceptTypeDTOs() {
        return lstProbAcceptTypeDTOs;
    }

    public void setLstProbAcceptTypeDTOs(List<ComplainDTO> lstProbAcceptTypeDTOs) {
        this.lstProbAcceptTypeDTOs = lstProbAcceptTypeDTOs;
    }

    public List<StationBO> getLstRevenueStation() {
        return lstRevenueStation;
    }

    public void setLstRevenueStation(List<StationBO> lstRevenueStation) {
        this.lstRevenueStation = lstRevenueStation;
    }

    public List<StationBO> getLstStationBOs() {
        return lstStationBOs;
    }

    public void setLstStationBOs(List<StationBO> lstStationBOs) {
        this.lstStationBOs = lstStationBOs;
    }

    public List<CriteriaGroup> getLstCriteriaGroups() {
        return lstCriteriaGroups;
    }

    public void setLstCriteriaGroups(List<CriteriaGroup> lstCriteriaGroups) {
        this.lstCriteriaGroups = lstCriteriaGroups;
    }

    public List<ReportProfileBO> getLstReportProfileBOs() {
        return lstReportProfileBOs;
    }

    public void setLstReportProfileBOs(List<ReportProfileBO> lstReportProfileBOs) {
        this.lstReportProfileBOs = lstReportProfileBOs;
    }

    public SubscriberInfoData getSubscriberInfoData() {
        if(subscriberInfoData == null) {
            return new SubscriberInfoData();
        }
        return subscriberInfoData;
    }

    public void setSubscriberInfoData(SubscriberInfoData subscriberInfoData) {
        this.subscriberInfoData = subscriberInfoData;
    }

    public List<SubPreChargeData> getLstSubPreCharges() {
        if (lstSubPreCharges == null) {
            return new ArrayList<>();
        }
        return lstSubPreCharges;
    }

    public void setLstSubPreCharges(List<SubPreChargeData> lstSubPreCharges) {
        this.lstSubPreCharges = lstSubPreCharges;
    }

    public List<VasDTOData> getLstVasDTOs() {
        if (lstVasDTOs == null) {
            return new ArrayList<>();
        }
        return lstVasDTOs;
    }

    public void setLstVasDTOs(List<VasDTOData> lstVasDTOs) {
        this.lstVasDTOs = lstVasDTOs;
    }

    public List<VtFreeActivedDTOData> getLstVtFreeActivedDTOs() {
        if (lstVtFreeActivedDTOs == null) {
            return new ArrayList<>();
        }
        return lstVtFreeActivedDTOs;
    }

    public void setLstVtFreeActivedDTOs(List<VtFreeActivedDTOData> lstVtFreeActivedDTOs) {
        this.lstVtFreeActivedDTOs = lstVtFreeActivedDTOs;
    }

    public List<RefillInfoData> getLstRefillInfos() {
        if (lstRefillInfos == null) {
            return new ArrayList<>();
        }
        return lstRefillInfos;
    }

    public void setLstRefillInfos(List<RefillInfoData> lstRefillInfos) {
        this.lstRefillInfos = lstRefillInfos;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Boolean getIsCVT() {
        return isCVT;
    }

    public void setIsCVT(Boolean isCVT) {
        this.isCVT = isCVT;
    }

    public String getStaffSignatureImage() {
        return staffSignatureImage;
    }

    public void setStaffSignatureImage(String staffSignatureImage) {
        this.staffSignatureImage = staffSignatureImage;
    }
    public String getTimeDeadline() {
        return timeDeadline;
    }

    public void setTimeDeadline(String timeDeadline) {
        this.timeDeadline = timeDeadline;
    }
	
    public String getRemoveVSA() {
        return removeVSA;
    }

    public void setRemoveVSA(String removeVSA) {
        this.removeVSA = removeVSA;
    }
}
