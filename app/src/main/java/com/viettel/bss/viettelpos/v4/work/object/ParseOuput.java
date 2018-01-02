package com.viettel.bss.viettelpos.v4.work.object;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.AccountDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustIdentityDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.MBCCSVasResultBO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferPriceDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferTypeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductPackageFeeDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.PromotionUnitVas;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ReasonDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.StockNumberDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.SubPromotionPrepaidDTO;
import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.VasResponseBO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ApStockSupplyInfo;
import com.viettel.bss.viettelpos.v3.connecttionService.model.CustomerOrderDetailDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.GroupsDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.MoreTelecomService;
import com.viettel.bss.viettelpos.v3.connecttionService.model.OptionSetValueDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ProductCatalogDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ReasonFullDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultGetVendorByConnectorForm;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SubInfrastructureDTO;
import com.viettel.bss.viettelpos.v4.bo.AgeSubscriberDTO;
import com.viettel.bss.viettelpos.v4.bo.ApParamBO;
import com.viettel.bss.viettelpos.v4.charge.object.ReasonBean;
import com.viettel.bss.viettelpos.v4.charge.object.SubGoodsDTO;
import com.viettel.bss.viettelpos.v4.commons.BillingPromotionDetailDTO;
import com.viettel.bss.viettelpos.v4.commons.CommonActivity;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ActionProfileBean;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.ProductOfferCharacterClone;
import com.viettel.bss.viettelpos.v4.connecttionMobile.beans.RecordBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailClone;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.CustomerOrderDetailMbccsDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HotLineReponseDetail;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ImageBO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.NotifyBean;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PaymentPrePaidPromotionBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.ReasonPledgeDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.TelecomServiceGroupDetailDTO;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationBO;
import com.viettel.bss.viettelpos.v4.customer.object.CorporationCategoryBO;
import com.viettel.bss.viettelpos.v4.customer.object.CustomerApproveObj;
import com.viettel.bss.viettelpos.v4.customer.object.Spin;
import com.viettel.bss.viettelpos.v4.customer.object.SubInvalidDTO;
import com.viettel.bss.viettelpos.v4.customer.object.SubscriberDTO;
import com.viettel.bss.viettelpos.v4.customer.object.UploadImageInput;
import com.viettel.bss.viettelpos.v4.object.ProductSpecificationDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ChangePrepaidDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.MapStaffArea;
import com.viettel.bss.viettelpos.v4.omichanel.dao.PoCatalogOutsideDTO;
import com.viettel.bss.viettelpos.v4.omichanel.dao.ProfileRecord;
import com.viettel.bss.viettelpos.v4.omichanel.dao.VStockNumberOmniDTO;
import com.viettel.bss.viettelpos.v4.report.object.ItemBean;
import com.viettel.bss.viettelpos.v4.report.object.LogMethodBean;
import com.viettel.bss.viettelpos.v4.sale.object.SaleOutput;
import com.viettel.bss.viettelpos.v4.sale.object.Serial;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransAction;
import com.viettel.bss.viettelpos.v4.sale.object.StockTransDetail;
import com.viettel.bss.viettelpos.v4.work.adapter.CatagoryInforBeans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Root(name = "return", strict = false)
public class ParseOuput implements Serializable {
    @Element(name = "productSpecificationDTO", required = false)
    public ProductSpecificationDTO productSpecificationDTO;
    @Element(name = "token", required = false)
    private String token;
    @Element(name = "errorCode", required = false)
    private String errorCode;
    @Element(name = "description", required = false)
    private String description;
    @Element(name = "productOfferCharacterClone", required = false)
    private ProductOfferCharacterClone productOfferCharacterClone;
    @Element(name = "hotCharge", required = false)
    private String hotCharge;
    @ElementList(name = "lstFamilyInfor", entry = "lstFamilyInfor", required = false, inline = true)
    private List<FamilyInforBean> lstFamilyInfor;
    @Element(name = "version", required = false)
    private String version;
    @ElementList(name = "lstCatagoryInfors", entry = "lstCatagoryInfors", required = false, inline = true)
    private List<CatagoryInforBeans> lstCatagoryInforBeans;
    @ElementList(name = "lstApParam", entry = "lstApParam", required = false, inline = true)
    private List<ApParamBO> lstApParam;

    @ElementList(name = "lstStockTransActionDTOs", entry = "lstStockTransActionDTOs", required = false, inline = true)
    private List<StockTransAction> lstStockTransActions;

    @ElementList(name = "lstStockTransDetailDTOs", entry = "lstStockTransDetailDTOs", required = false, inline = true)
    private List<StockTransDetail> lstStockTransDetails;

    @ElementList(name = "lstItemBeans", entry = "lstItemBeans", required = false, inline = true)
    private List<ItemBean> lstItemBeans;
    @ElementList(name = "lstActionMBCCS", entry = "lstActionMBCCS", required = false, inline = true)
    private List<Spin> lstSpin;
    @ElementList(name = "lstLogMethodBeans", entry = "lstLogMethodBeans", required = false, inline = true)
    private List<LogMethodBean> lstLogMethodBeans;
    @Element(name = "totalRecord", required = false)
    private int totalRecord;

    @ElementList(name = "lstProductOfferingDTO", entry = "lstProductOfferingDTO", required = false, inline = true)
    private ArrayList<ProductOfferingDTO> lstProductOfferingDTO;

    @ElementList(name = "lstProductOfferTypeDTO", entry = "lstProductOfferTypeDTO", required = false, inline = true)
    private ArrayList<ProductOfferTypeDTO> lstProductOfferTypeDTO;
    @ElementList(name = "lstSerials", entry = "lstSerials", required = false, inline = true)
    private List<Serial> lstSerials;

    @ElementList(name = "lstStockTransSerialDTOs", entry = "lstStockTransSerialDTOs", required = false, inline = true)
    private List<Serial> lstStockTransSerialDTOs;

    @ElementList(name = "lstAccountDTO", entry = "lstAccountDTO", required = false, inline = true)
    private ArrayList<AccountDTO> lstAccountDTO;

    @ElementList(name = "lstReasonDTOs", entry = "lstReasonDTOs", required = false, inline = true)
    private List<ReasonBean> lstReasonBeans = new ArrayList<>();
    @ElementList(name = "lstProductCatalogDTOs", entry = "lstProductCatalogDTOs", required = false, inline = true)
    private ArrayList<ProductCatalogDTO> lstProductCatalogDTOs;


    @ElementList(name = "lstInfo", entry = "lstInfo", required = false, inline = true)
    private List<String> lstInfo;
    @Element(name = "fileAttach", required = false)
    private String fileAttach;
    @Element(name = "wsResponseAgeSubscriber", required = false)
    private AgeSubscriberDTO ageSubscriberDTO;
    @ElementList(name = "lstSubInfoFileActionDTOs", entry = "lstSubInfoFileActionDTOs", required = false, inline = true)
    private List<CustomerApproveObj> lstCustomerApproveObjs = new ArrayList<>();
    @ElementList(name = "lstInfrastructureDTOs", entry = "lstInfrastructureDTOs", required = false, inline = true)
    private ArrayList<SubInfrastructureDTO> lstInfrastructureDTOs = new ArrayList<>();
    @ElementList(name = "lstStockNumberDTO", entry = "lstStockNumberDTO", required = false, inline = true)
    private List<StockNumberDTO> lstStockNumberDTO;
    @ElementList(name = "lstProductOfferPriceDTO", entry = "lstProductOfferPriceDTO", required = false, inline = true)
    private ArrayList<ProductOfferPriceDTO> lstProductOfferPriceDTOs;
    @Element(name = "isMoreThan", required = false)
    private boolean isMoreThan;
    @Element(name = "jsonResult", required = false)
    private String jsonResult;
    @ElementList(name = "vStockNumberOmniDTOs", entry = "vStockNumberOmniDTOs", required = false, inline = true)
    private List<VStockNumberOmniDTO> vStockNumberOmniDTOs;
    @ElementList(name = "lstProfileRecords", entry = "lstProfileRecords", required = false, inline = true)
    private List<ProfileRecord> lstProfileRecords;
    @ElementList(name = "lstPoCatalogOutsideDTOs", entry = "lstPoCatalogOutsideDTOs", required = false, inline = true)
    private List<PoCatalogOutsideDTO> lstPoCatalogOutsideDTOs;
    @ElementList(name = "lstChangePrepaidDTOs", entry = "lstChangePrepaidDTOs", required = false, inline = true)
    private ArrayList<ChangePrepaidDTO> lstChangePrepaidDTOs;
    @Element(name = "taskId", required = false)
    private String taskId;
    @Element(name = "unclaimForReceptionist", required = false)
    private String unclaimForReceptionist;
    @ElementList(name = "paymentPrePaidPromotionBeans", entry = "paymentPrePaidPromotionBeans", required = false, inline = true)
    private ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans;
    @ElementList(name = "lstMapStaffArea", entry = "lstMapStaffArea", required = false, inline = true)
    private List<MapStaffArea> lstMapStaffArea;
    @Element(name = "orderId", required = false)
    private String orderId;
    @Element(name = "limitPobas", required = false)
    private Long limitPobas = 0l;
    @Element(name = "igoreOTP", required = false)
    private boolean igoreOTP;
    @ElementList(name = "lstMappingNodeStaffDTOs", entry = "lstMappingNodeStaffDTOs", required = false, inline = true)
    private ArrayList<Spin> lstMappingNodeStaffDTOs;
    @ElementList(name = "lstForms", entry = "lstForms", required = false, inline = true)
    private ArrayList<Spin> lstForms;
    @ElementList(name = "lstActionProfiles", entry = "lstActionProfiles", required = false, inline = true)
    private List<ActionProfileBean> lstActionProfileBeans;
    @ElementList(name = "listRecord", entry = "listRecord", required = false, inline = true)
    private List<RecordBean> lstRecordBeans;
    @ElementList(name = "lstNotifyBeans", entry = "lstNotifyBeans", required = false, inline = true)
    private List<NotifyBean> lstNotifyBeans;
    @ElementList(name = "lstReasonPledgeDTOs", entry = "lstReasonPledgeDTOs", required = false, inline = true)
    private List<ReasonPledgeDTO> lstReasonPledgeDTOs;
    @ElementList(name = "lstCustTypeDTO", entry = "lstCustTypeDTO", required = false, inline = true)
    private ArrayList<CustTypeDTO> lstCustTypeDTO;
    @ElementList(name = "lstCustIdentityDTOs", entry = "lstCustIdentityDTOs", required = false, inline = true)
    private ArrayList<CustIdentityDTO> lstCustIdentityDTOs;
    @Element(name = "customerDTO", required = false)
    private CustomerDTO customerDTO;
    @Element(name = "customerDTOExt", required = false)
    private CustomerDTO customerDTOExt;
    @ElementList(name = "lstSubscriberDTO", entry = "lstSubscriberDTO", required = false, inline = true)
    private ArrayList<SubscriberDTO> lstSubscriberDTO;
    @ElementList(name = "lstApStockSupplyInfos", entry = "lstApStockSupplyInfos", required = false, inline = true)
    private ArrayList<ApStockSupplyInfo> lstApStockSupplyInfos;
    @Element(name = "subscriberDTO", required = false)
    private SubscriberDTO subscriberDTO;
    @Element(name = "subscriberDTOExt", required = false)
    private SubscriberDTO subscriberDTOExt;
    @Element(name = "accountDTO", required = false)
    private AccountDTO accountDTO;
    @ElementList(name = "lstSubscriberDTO", entry = "lstSubPromotionDTO", required = false, inline = true)
    private List<SubPromotionDTO> lstSubPromotionDTO;
    @ElementList(name = "lstSubscriberDTO", entry = "lstSubPromotionPrepaidDTO", required = false, inline = true)
    private List<SubPromotionPrepaidDTO> lstSubPromotionPrepaidDTO;
    @ElementList(name = "lstReasonDTO", entry = "lstReasonDTO", required = false, inline = true)
    private List<ReasonDTO> lstReasonDTO;
    @ElementList(name = "lstProductPackageFeeDTO", entry = "lstProductPackageFeeDTO", required = false, inline = true)
    private ArrayList<ProductPackageFeeDTO> lstProductPackageFeeDTO;
    @Element(name = "value", required = false)
    private String value;
    @ElementList(name = "lstImageInputFail", entry = "lstImageInputFail", required = false, inline = true)
    private List<UploadImageInput> lstImageInputFail;
    @ElementList(name = "lstMoreTelecomService", entry = "lstMoreTelecomService", required = false, inline = true)
    private List<MoreTelecomService> lstMoreTelecomService;
    @Element(name = "stockNumberDTO", required = false)
    private StockNumberDTO stockNumberDTO;
    @ElementList(name = "lstSubGoodsDTOs", entry = "lstSubGoodsDTOs", required = false, inline = true)
    private List<SubGoodsDTO> lstSubGoodsDTOs;
    @Element(name = "isPSenTdO", required = false)
    private String isPSenTdO;
    @ElementList(name = "lstCorporationCategoryBO", entry = "lstCorporationCategoryBO", required = false, inline = true)
    private ArrayList<CorporationCategoryBO> lstCorporationCategoryBO;
    @ElementList(name = "lstCorporationBO", entry = "lstCorporationBO", required = false, inline = true)
    private ArrayList<CorporationBO> lstCorporationBO;
    @Element(name = "corporationBO", required = false)
    private CorporationBO corporationBO;
    @ElementList(name = "basicSubscriberInfos", entry = "basicSubscriberInfos", required = false, inline = true)
    private List<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> basicSubscriberInfos;
    @ElementList(name = "lstBasicSubscriberInfo", entry = "lstBasicSubscriberInfo", required = false, inline = true)
    private ArrayList<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> lstBasicSubscriberInfo;
    @Element(name = "vasResponseBO", required = false)
    private VasResponseBO vasResponseBO;
    @ElementList(name = "lstMapVasPromotionUnitDTO", entry = "lstMapVasPromotionUnitDTO", required = false, inline = true)
    private ArrayList<PromotionUnitVas> lstMapVasPromotionUnitDTO;
    @ElementList(name = "lstMbccsVasResultBO", entry = "lstMbccsVasResultBO", required = false, inline = true)
    private ArrayList<MBCCSVasResultBO> lstMbccsVasResultBO;
    @Element(name = "groupsDTO", required = false)
    private GroupsDTO groupsDTO;
    @Element(name = "resultSurveyOnlineForBccs2Form", required = false)
    private ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form;
    @ElementList(name = "arrResultSurveyOnline", entry = "arrResultSurveyOnline", required = false, inline = true)
    private ArrayList<ResultSurveyOnlineForBccs2Form> arrResultSurveyOnline;
    @ElementList(name = "lstAllGroupCustType", entry = "lstAllGroupCustType", required = false, inline = true)
    private ArrayList<Spin> lstAllGroupCustType;
    @ElementList(name = "lstReasonFullDTOs", entry = "lstReasonFullDTOs", required = false, inline = true)
    private ArrayList<ReasonFullDTO> lstReasonFullDTOs;
    @ElementList(name = "lstImageBOs", entry = "lstImageBOs", required = false, inline = true)
    private ArrayList<ImageBO> lstImageBOs;
    @ElementList(name = "lstCustomerOrderDetailDTOs", entry = "lstCustomerOrderDetailDTOs", required = false, inline = true)
    private List<CustomerOrderDetailMbccsDTO> lstCustomerOrderDetailMbccsDTOs;
    @Element(name = "isCVT", required = false)
    private Boolean isCVT;
    @ElementList(name = "lstSubscriberDTOBlock", entry = "lstSubscriberDTOBlock", required = false, inline = true)
    private List<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> subscriberDTOList;
    @ElementList(name = "lstOptionSetValueDTOs", entry = "lstOptionSetValueDTOs", required = false, inline = true)
    private ArrayList<OptionSetValueDTO> lstOptionSetValueDTOs;
    @Element(name = "resultGetVendorByConnectorForm", required = false)
    private ResultGetVendorByConnectorForm resultGetVendorByConnectorForm;
    @ElementList(name = "listValue", entry = "listValue", required = false, inline = true)
    private ArrayList<String> listValue;
    @ElementList(name = "lstchar", entry = "lstchar", required = false, inline = true)
    private ArrayList<String> lstchar;
    @ElementList(name = "lstCustomerDTOClone", entry = "lstCustomerDTOClone", required = false, inline = true)
    private ArrayList<CustomerOrderDetailClone> lstCustomerDTOClone;
    private ArrayList<CustomerOrderDetailDTO> lstCustomerOrderDetailDTOs;
    @Element(name = "account", required = false)
    private String account;
    @ElementList(name = "listProcessRequest", entry = "listProcessRequest", required = false, inline = true)
    private ArrayList<ProcessRequestBO> listProcessRequest;
    @Element(name = "receiveRequest", required = false)
    private HotLineReponseDetail hotLineReponseDetail;
    @Element(name = "ignoreTdO", required = false)
    private boolean ignoreOTP = false;
    @ElementList(name = "lstTelecomServiceGroupDetailDTO", entry = "lstTelecomServiceGroupDetailDTO", required = false, inline = true)
    private ArrayList<TelecomServiceGroupDetailDTO> lstTelecomServiceGroupDetailDTO;
    @ElementList(name = "listBillingPromotionDetailDTO", entry = "listBillingPromotionDetailDTO", required = false, inline = true)
    private ArrayList<BillingPromotionDetailDTO> listBillingPromotionDetailDTO;
    @Element(name = "fileContent", required = false)
    private String fileContent;
    @Element(name = "fileExtension", required = false)
    private String fileExtension;
    @ElementList(name = "lstSaleOutput", entry = "lstSaleOutput", required = false, inline = true)
    private ArrayList<SaleOutput> lstSaleOutPut;

    public ArrayList<SaleOutput> getLstSaleOutPut() {
        return lstSaleOutPut;
    }

    public void setLstSaleOutPut(ArrayList<SaleOutput> lstSaleOutPut) {
        this.lstSaleOutPut = lstSaleOutPut;
    }

    @ElementList(name = "lstReasonDTOOuput", entry = "lstReasonDTOOutput", required = false, inline = true)
    private ArrayList<ReasonDTO> lstReasonDTOs;


    public ArrayList<ReasonDTO> getLstReasonDTOs() {
        return lstReasonDTOs;
    }

    public void setLstReasonDTOs(ArrayList<ReasonDTO> lstReasonDTOs) {
        this.lstReasonDTOs = lstReasonDTOs;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
    @ElementList (name ="lstSubInvalidDTO", entry = "lstSubInvalidDTO", required = false, inline = true)
    private  List<SubInvalidDTO> lstSubInvalidDTO;
    @Element(name = "avgCharge", required = false)
    private Double avgCharge;

    public Double getAvgCharge() {
        return avgCharge;
    }

    public void setAvgCharge(Double avgCharge) {
        this.avgCharge = avgCharge;
    }

    public List<SubInvalidDTO> getLstSubInvalidDTO() {
        return lstSubInvalidDTO;
    }

    public void setLstSubInvalidDTO(List<SubInvalidDTO> lstSubInvalidDTO) {
        this.lstSubInvalidDTO = lstSubInvalidDTO;
    }

    public String getHotCharge() {
        return hotCharge;
    }

    public void setHotCharge(String hotCharge) {
        this.hotCharge = hotCharge;
    }

    public List<String> getLstInfo() {
        return lstInfo;
    }

    public void setLstInfo(List<String> lstInfo) {
        this.lstInfo = lstInfo;
    }

    public List<MapStaffArea> getLstMapStaffArea() {
        return lstMapStaffArea;
    }

    public void setLstMapStaffArea(List<MapStaffArea> lstMapStaffArea) {
        this.lstMapStaffArea = lstMapStaffArea;
    }

    public String getUnclaimForReceptionist() {
        return unclaimForReceptionist;
    }

    public void setUnclaimForReceptionist(String unclaimForReceptionist) {
        this.unclaimForReceptionist = unclaimForReceptionist;
    }

    public ArrayList<ChangePrepaidDTO> getLstChangePrepaidDTOs() {
        return lstChangePrepaidDTOs;
    }

    public void setLstChangePrepaidDTOs(ArrayList<ChangePrepaidDTO> lstChangePrepaidDTOs) {
        this.lstChangePrepaidDTOs = lstChangePrepaidDTOs;
    }

    public ArrayList<PaymentPrePaidPromotionBeans> getLstPaymentPrePaidPromotionBeans() {
        return lstPaymentPrePaidPromotionBeans;
    }

    public void setLstPaymentPrePaidPromotionBeans(ArrayList<PaymentPrePaidPromotionBeans> lstPaymentPrePaidPromotionBeans) {
        this.lstPaymentPrePaidPromotionBeans = lstPaymentPrePaidPromotionBeans;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public ArrayList<ProductCatalogDTO> getLstProductCatalogDTOs() {
        return lstProductCatalogDTOs;
    }

    public void setLstProductCatalogDTOs(ArrayList<ProductCatalogDTO> lstProductCatalogDTOs) {
        this.lstProductCatalogDTOs = lstProductCatalogDTOs;
    }

    public List<PoCatalogOutsideDTO> getLstPoCatalogOutsideDTOs() {
        return lstPoCatalogOutsideDTOs;
    }

    public void setLstPoCatalogOutsideDTOs(List<PoCatalogOutsideDTO> lstPoCatalogOutsideDTOs) {
        this.lstPoCatalogOutsideDTOs = lstPoCatalogOutsideDTOs;
    }

    public List<VStockNumberOmniDTO> getvStockNumberOmniDTOs() {
        return vStockNumberOmniDTOs;
    }

    public void setvStockNumberOmniDTOs(List<VStockNumberOmniDTO> vStockNumberOmniDTOs) {
        this.vStockNumberOmniDTOs = vStockNumberOmniDTOs;
    }

    public List<ProfileRecord> getLstProfileRecords() {
        return lstProfileRecords;
    }

    public void setLstProfileRecords(List<ProfileRecord> lstProfileRecords) {
        this.lstProfileRecords = lstProfileRecords;
    }

    public Long getLimitPobas() {
        return limitPobas;
    }

    public void setLimitPobas(Long limitPobas) {
        this.limitPobas = limitPobas;
    }

    public ProductOfferCharacterClone getProductOfferCharacterClone() {
        return productOfferCharacterClone;
    }

    public void setProductOfferCharacterClone(ProductOfferCharacterClone productOfferCharacterClone) {
        this.productOfferCharacterClone = productOfferCharacterClone;
    }

    public ArrayList<Spin> getLstForms() {
        return lstForms;
    }

    public void setLstForms(ArrayList<Spin> lstForms) {
        this.lstForms = lstForms;
    }

    public ArrayList<Spin> getLstMappingNodeStaffDTOs() {
        return lstMappingNodeStaffDTOs;
    }

    public void setLstMappingNodeStaffDTOs(ArrayList<Spin> lstMappingNodeStaffDTOs) {
        this.lstMappingNodeStaffDTOs = lstMappingNodeStaffDTOs;
    }

    public boolean isIgoreOTP() {
        return igoreOTP;
    }

    public void setIgoreOTP(boolean igoreOTP) {
        this.igoreOTP = igoreOTP;
    }

    public boolean isMoreThan() {
        return isMoreThan;
    }

    public void setMoreThan(boolean moreThan) {
        isMoreThan = moreThan;
    }

    public void setIsMoreThan(boolean isMoreThan) {
        this.isMoreThan = isMoreThan;
    }

    public ArrayList<ResultSurveyOnlineForBccs2Form> getArrResultSurveyOnline() {
        return arrResultSurveyOnline;
    }

    public void setArrResultSurveyOnline(ArrayList<ResultSurveyOnlineForBccs2Form> arrResultSurveyOnline) {
        this.arrResultSurveyOnline = arrResultSurveyOnline;
    }

    public List<StockNumberDTO> getLstStockNumberDTO() {
        return lstStockNumberDTO;
    }

    public void setLstStockNumberDTO(List<StockNumberDTO> lstStockNumberDTO) {
        this.lstStockNumberDTO = lstStockNumberDTO;
    }

    public ArrayList<ProductOfferPriceDTO> getLstProductOfferPriceDTOs() {
        return lstProductOfferPriceDTOs;
    }

    public void setLstProductOfferPriceDTOs(ArrayList<ProductOfferPriceDTO> lstProductOfferPriceDTOs) {
        this.lstProductOfferPriceDTOs = lstProductOfferPriceDTOs;
    }

    public String getIsPSenTdO() {
        return isPSenTdO;
    }

    public void setIsPSenTdO(String isPSenTdO) {
        this.isPSenTdO = isPSenTdO;
    }

    public ArrayList<ImageBO> getLstImageBOs() {
        return lstImageBOs;
    }

    public void setLstImageBOs(ArrayList<ImageBO> lstImageBOs) {
        this.lstImageBOs = lstImageBOs;
    }

    public ArrayList<ReasonFullDTO> getLstReasonFullDTOs() {
        return lstReasonFullDTOs;
    }

    public void setLstReasonFullDTOs(ArrayList<ReasonFullDTO> lstReasonFullDTOs) {
        this.lstReasonFullDTOs = lstReasonFullDTOs;
    }

    public List<CustomerOrderDetailMbccsDTO> getLstCustomerOrderDetailMbccsDTOs() {
        return lstCustomerOrderDetailMbccsDTOs;
    }

    public void setLstCustomerOrderDetailMbccsDTOs(List<CustomerOrderDetailMbccsDTO> lstcustomerOrderDetailMbccsDTOs) {
        this.lstCustomerOrderDetailMbccsDTOs = lstcustomerOrderDetailMbccsDTOs;
    }

    public ArrayList<Spin> getLstAllGroupCustType() {
        return lstAllGroupCustType;
    }

    public void setLstAllGroupCustType(ArrayList<Spin> lstAllGroupCustType) {
        this.lstAllGroupCustType = lstAllGroupCustType;
    }

    public ProductSpecificationDTO getProductSpecificationDTO() {
        return productSpecificationDTO;
    }

    public void setProductSpecificationDTO(ProductSpecificationDTO productSpecificationDTO) {
        this.productSpecificationDTO = productSpecificationDTO;
    }

    public List<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> getSubscriberDTOList() {
        return subscriberDTOList;
    }

    public void setSubscriberDTOList(List<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> subscriberDTOList) {
        this.subscriberDTOList = subscriberDTOList;
    }

    public ResultSurveyOnlineForBccs2Form getResultSurveyOnlineForBccs2Form() {
        return resultSurveyOnlineForBccs2Form;
    }

    public void setResultSurveyOnlineForBccs2Form(ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form) {
        this.resultSurveyOnlineForBccs2Form = resultSurveyOnlineForBccs2Form;
    }

    public ArrayList<OptionSetValueDTO> getLstOptionSetValueDTOs() {
        return lstOptionSetValueDTOs;
    }

    public void setLstOptionSetValueDTOs(
            ArrayList<OptionSetValueDTO> lstOptionSetValueDTOs) {
        this.lstOptionSetValueDTOs = lstOptionSetValueDTOs;
    }

    public ResultGetVendorByConnectorForm getResultGetVendorByConnectorForm() {
        return resultGetVendorByConnectorForm;
    }

    public void setResultGetVendorByConnectorForm(ResultGetVendorByConnectorForm resultGetVendorByConnectorForm) {
        this.resultGetVendorByConnectorForm = resultGetVendorByConnectorForm;
    }

    public GroupsDTO getGroupsDTO() {
        return groupsDTO;
    }

    public void setGroupsDTO(GroupsDTO groupsDTO) {
        this.groupsDTO = groupsDTO;
    }

    public ArrayList<MBCCSVasResultBO> getLstMbccsVasResultBO() {
        return lstMbccsVasResultBO;
    }

    public void setLstMbccsVasResultBO(ArrayList<MBCCSVasResultBO> lstMbccsVasResultBO) {
        this.lstMbccsVasResultBO = lstMbccsVasResultBO;
    }

    public ArrayList<PromotionUnitVas> getLstMapVasPromotionUnitDTO() {
        return lstMapVasPromotionUnitDTO;
    }

    public void setLstMapVasPromotionUnitDTO(ArrayList<PromotionUnitVas> lstMapVasPromotionUnitDTO) {
        this.lstMapVasPromotionUnitDTO = lstMapVasPromotionUnitDTO;
    }

    public VasResponseBO getVasResponseBO() {
        return vasResponseBO;
    }

    public void setVasResponseBO(VasResponseBO vasResponseBO) {
        this.vasResponseBO = vasResponseBO;
    }

    public ArrayList<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> getLstBasicSubscriberInfo() {
        return lstBasicSubscriberInfo;
    }

    public void setLstBasicSubscriberInfo(
            ArrayList<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> lstBasicSubscriberInfo) {
        this.lstBasicSubscriberInfo = lstBasicSubscriberInfo;
    }

    public List<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> getBasicSubscriberInfos() {
        return basicSubscriberInfos;
    }

    public void setBasicSubscriberInfos(
            List<com.viettel.bss.viettelpos.v4.sale.object.SubscriberDTO> basicSubscriberInfos) {
        this.basicSubscriberInfos = basicSubscriberInfos;
    }

    public ArrayList<String> getListValue() {
        return listValue;
    }

    public void setListValue(ArrayList<String> listValue) {
        this.listValue = listValue;
    }

    public CorporationBO getCorporationBO() {
        return corporationBO;
    }

    public void setCorporationBO(CorporationBO corporationBO) {
        this.corporationBO = corporationBO;
    }

    public ArrayList<CorporationBO> getLstCorporationBO() {
        return lstCorporationBO;
    }

    public void setLstCorporationBO(ArrayList<CorporationBO> lstCorporationBO) {
        this.lstCorporationBO = lstCorporationBO;
    }

    public List<SubGoodsDTO> getLstSubGoodsDTOs() {
        return lstSubGoodsDTOs;
    }

    public void setLstSubGoodsDTOs(List<SubGoodsDTO> lstSubGoodsDTOs) {
        this.lstSubGoodsDTOs = lstSubGoodsDTOs;
    }

    public ArrayList<CorporationCategoryBO> getLstCorporationCategoryBO() {
        return lstCorporationCategoryBO;
    }

    public void setLstCorporationCategoryBO(ArrayList<CorporationCategoryBO> lstCorporationCategoryBO) {
        this.lstCorporationCategoryBO = lstCorporationCategoryBO;
    }

    public ArrayList<ProductOfferingDTO> getLstProductOfferingDTO() {
        return lstProductOfferingDTO;
    }

    public void setLstProductOfferingDTO(ArrayList<ProductOfferingDTO> lstProductOfferingDTO) {
        this.lstProductOfferingDTO = lstProductOfferingDTO;
    }

    public List<UploadImageInput> getLstImageInputFail() {
        return lstImageInputFail;
    }

    public void setLstImageInputFail(List<UploadImageInput> lstImageInputFail) {
        this.lstImageInputFail = lstImageInputFail;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ArrayList<ProductPackageFeeDTO> getLstProductPackageFeeDTO() {
        return lstProductPackageFeeDTO;
    }

    public void setLstProductPackageFeeDTO(ArrayList<ProductPackageFeeDTO> lstProductPackageFeeDTO) {
        this.lstProductPackageFeeDTO = lstProductPackageFeeDTO;
    }

    public List<ReasonDTO> getLstReasonDTO() {
        return lstReasonDTO;
    }

    public void setLstReasonDTO(List<ReasonDTO> lstReasonDTO) {
        this.lstReasonDTO = lstReasonDTO;
    }

    public ArrayList<CustomerOrderDetailClone> getLstCustomerDTOClone() {
        return lstCustomerDTOClone;
    }

    public void setLstCustomerDTOClone(ArrayList<CustomerOrderDetailClone> lstCustomerDTOClone) {
        this.lstCustomerDTOClone = lstCustomerDTOClone;
    }

    public ArrayList<CustomerOrderDetailDTO> getLstCustomerOrderDetailDTOs() {
        return lstCustomerOrderDetailDTOs;
    }

    public void setLstCustomerOrderDetailDTOs(ArrayList<CustomerOrderDetailDTO> lstCustomerOrderDetailDTOs) {
        this.lstCustomerOrderDetailDTOs = lstCustomerOrderDetailDTOs;
    }

    public ArrayList<String> getLstchar() {
        return lstchar;
    }

    public void setLstchar(ArrayList<String> lstchar) {
        this.lstchar = lstchar;
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

    public List<FamilyInforBean> getLstFamilyInfor() {
        return lstFamilyInfor;
    }

    public void setLstFamilyInfor(List<FamilyInforBean> lstFamilyInfor) {
        this.lstFamilyInfor = lstFamilyInfor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<CatagoryInforBeans> getLstCatagoryInforBeans() {
        return lstCatagoryInforBeans;
    }

    public void setLstCatagoryInforBeans(
            List<CatagoryInforBeans> lstCatagoryInforBeans) {
        this.lstCatagoryInforBeans = lstCatagoryInforBeans;
    }

    public List<StockTransAction> getLstStockTransActions() {
        return lstStockTransActions;
    }

    public void setLstStockTransActions(List<StockTransAction> lstStockTransActions) {
        this.lstStockTransActions = lstStockTransActions;
    }

    public List<StockTransDetail> getLstStockTransDetails() {
        return lstStockTransDetails;
    }

    public void setLstStockTransDetails(List<StockTransDetail> lstStockTransDetails) {
        this.lstStockTransDetails = lstStockTransDetails;
    }

    public ArrayList<ProductOfferTypeDTO> getLstProductOfferTypeDTO() {
        return lstProductOfferTypeDTO;
    }

    public void setLstProductOfferTypeDTO(
            ArrayList<ProductOfferTypeDTO> lstProductOfferTypeDTO) {
        this.lstProductOfferTypeDTO = lstProductOfferTypeDTO;
    }

    public List<Serial> getLstSerials() {
        return lstSerials;
    }

    public void setLstSerials(List<Serial> lstSerials) {
        this.lstSerials = lstSerials;
    }

    public ArrayList<AccountDTO> getLstAccountDTO() {
        return lstAccountDTO;
    }

    public void setLstAccountDTO(ArrayList<AccountDTO> lstAccountDTO) {
        this.lstAccountDTO = lstAccountDTO;
    }

    public List<ReasonBean> getLstReasonBeans() {
        return lstReasonBeans;
    }

    public void setLstReasonBeans(List<ReasonBean> lstReasonBeans) {
        this.lstReasonBeans = lstReasonBeans;
    }

    public String getFileAttach() {
        return fileAttach;
    }

    public void setFileAttach(String fileAttach) {
        this.fileAttach = fileAttach;
    }

    public List<CustomerApproveObj> getLstCustomerApproveObjs() {
        return lstCustomerApproveObjs;
    }

    public void setLstCustomerApproveObjs(
            List<CustomerApproveObj> lstCustomerApproveObjs) {
        this.lstCustomerApproveObjs = lstCustomerApproveObjs;
    }

    public List<ActionProfileBean> getLstActionProfileBeans() {
        return lstActionProfileBeans;
    }

    public void setLstActionProfileBeans(
            List<ActionProfileBean> lstActionProfileBeans) {
        this.lstActionProfileBeans = lstActionProfileBeans;
    }

    public List<RecordBean> getLstRecordBeans() {
        return lstRecordBeans;
    }

    public void setLstRecordBeans(List<RecordBean> lstRecordBeans) {
        this.lstRecordBeans = lstRecordBeans;
    }

    public List<NotifyBean> getLstNotifyBeans() {
        return lstNotifyBeans;
    }

    public void setLstNotifyBeans(List<NotifyBean> lstNotifyBeans) {
        this.lstNotifyBeans = lstNotifyBeans;
    }

    public List<ReasonPledgeDTO> getLstReasonPledgeDTOs() {
        return lstReasonPledgeDTOs;
    }

    public void setLstReasonPledgeDTOs(List<ReasonPledgeDTO> lstReasonPledgeDTOs) {
        this.lstReasonPledgeDTOs = lstReasonPledgeDTOs;
    }

    public ArrayList<CustTypeDTO> getLstCustTypeDTO() {
        return lstCustTypeDTO;
    }

    public void setLstCustTypeDTO(ArrayList<CustTypeDTO> lstCustTypeDTO) {
        this.lstCustTypeDTO = lstCustTypeDTO;
    }

    public ArrayList<CustIdentityDTO> getLstCustIdentityDTOs() {
        return lstCustIdentityDTOs;
    }

    public void setLstCustIdentityDTOs(
            ArrayList<CustIdentityDTO> lstCustIdentityDTOs) {
        this.lstCustIdentityDTOs = lstCustIdentityDTOs;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }

    public ArrayList<SubscriberDTO> getLstSubscriberDTO() {
        if (CommonActivity.isNullOrEmpty(lstSubscriberDTO)) {
            lstSubscriberDTO = new ArrayList<>();
        }
        return lstSubscriberDTO;
    }

    public void setLstSubscriberDTO(ArrayList<SubscriberDTO> lstSubscriberDTO) {
        this.lstSubscriberDTO = lstSubscriberDTO;
    }

    public SubscriberDTO getSubscriberDTO() {
        return subscriberDTO;
    }

    public void setSubscriberDTO(SubscriberDTO subscriberDTO) {
        this.subscriberDTO = subscriberDTO;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public List<SubPromotionDTO> getLstSubPromotionDTO() {
        return lstSubPromotionDTO;
    }

    public void setLstSubPromotionDTO(List<SubPromotionDTO> lstSubPromotionDTO) {
        this.lstSubPromotionDTO = lstSubPromotionDTO;
    }

    public List<SubPromotionPrepaidDTO> getLstSubPromotionPrepaidDTO() {
        return lstSubPromotionPrepaidDTO;
    }

    public void setLstSubPromotionPrepaidDTO(
            List<SubPromotionPrepaidDTO> lstSubPromotionPrepaidDTO) {
        this.lstSubPromotionPrepaidDTO = lstSubPromotionPrepaidDTO;
    }

    public CustomerDTO getCustomerDTOExt() {
        return customerDTOExt;
    }

    public void setCustomerDTOExt(CustomerDTO customerDTOExt) {
        this.customerDTOExt = customerDTOExt;
    }

    public SubscriberDTO getSubscriberDTOExt() {
        return subscriberDTOExt;
    }

    public void setSubscriberDTOExt(SubscriberDTO subscriberDTOExt) {
        this.subscriberDTOExt = subscriberDTOExt;
    }

    public StockNumberDTO getStockNumberDTO() {
        return stockNumberDTO;
    }

    public void setStockNumberDTO(StockNumberDTO stockNumberDTO) {
        this.stockNumberDTO = stockNumberDTO;
    }

    public List<Serial> getLstStockTransSerialDTOs() {
        return lstStockTransSerialDTOs;
    }

    public void setLstStockTransSerialDTOs(List<Serial> lstStockTransSerialDTOs) {
        this.lstStockTransSerialDTOs = lstStockTransSerialDTOs;
    }

    public List<ItemBean> getLstItemBeans() {
        return lstItemBeans;
    }

    public void setLstItemBeans(List<ItemBean> lstItemBeans) {
        this.lstItemBeans = lstItemBeans;
    }

    public List<Spin> getLstSpin() {
        return lstSpin;
    }

    public void setLstSpin(List<Spin> lstSpin) {
        this.lstSpin = lstSpin;
    }

    public List<LogMethodBean> getLstLogMethodBeans() {
        return lstLogMethodBeans;
    }

    public void setLstLogMethodBeans(List<LogMethodBean> lstLogMethodBeans) {
        this.lstLogMethodBeans = lstLogMethodBeans;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
    }

    public ArrayList<SubInfrastructureDTO> getLstInfrastructureDTOs() {
        return lstInfrastructureDTOs;
    }

    public void setLstInfrastructureDTOs(ArrayList<SubInfrastructureDTO> lstInfrastructureDTOs) {
        this.lstInfrastructureDTOs = lstInfrastructureDTOs;
    }

    public void setLstSubInfrastructureDTOs(ArrayList<SubInfrastructureDTO> lstSubInfrastructureDTOs) {
        this.lstInfrastructureDTOs = lstSubInfrastructureDTOs;
    }

    public ArrayList<ApStockSupplyInfo> getLstApStockSupplyInfos() {
        return lstApStockSupplyInfos;
    }

    public void setLstApStockSupplyInfos(ArrayList<ApStockSupplyInfo> lstApStockSupplyInfos) {
        this.lstApStockSupplyInfos = lstApStockSupplyInfos;
    }


    // get history

    public List<MoreTelecomService> getLstMoreTelecomService() {
        return lstMoreTelecomService;
    }

    public void setLstMoreTelecomService(List<MoreTelecomService> lstMoreTelecomService) {
        this.lstMoreTelecomService = lstMoreTelecomService;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public ArrayList<ProcessRequestBO> getListProcessRequest() {
        return listProcessRequest;
    }

    public void setListProcessRequest(ArrayList<ProcessRequestBO> listProcessRequest) {
        this.listProcessRequest = listProcessRequest;
    }

    public HotLineReponseDetail getHotLineReponseDetail() {
        return hotLineReponseDetail;
    }

    public void setHotLineReponseDetail(HotLineReponseDetail hotLineReponseDetail) {
        this.hotLineReponseDetail = hotLineReponseDetail;
    }

    public String getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(String jsonResult) {
        this.jsonResult = jsonResult;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Boolean getIsCVT() {
        return isCVT;
    }

    public void setIsCVT(Boolean isCVT) {
        this.isCVT = isCVT;
    }

    public boolean isIgnoreOTP() {
        return ignoreOTP;
    }

    public void setIgnoreOTP(boolean ignoreOTP) {
        this.ignoreOTP = ignoreOTP;
    }

    public ArrayList<TelecomServiceGroupDetailDTO> getLstTelecomServiceGroupDetailDTO() {
        return lstTelecomServiceGroupDetailDTO;
    }

    public List<ApParamBO> getLstApParam() {
        return lstApParam;
    }

    public void setLstApParam(List<ApParamBO> lstApParam) {
        this.lstApParam = lstApParam;
    }

    public ArrayList<BillingPromotionDetailDTO> getListBillingPromotionDetailDTO() {
        return listBillingPromotionDetailDTO;
    }

    public void setListBillingPromotionDetailDTO(ArrayList<BillingPromotionDetailDTO> listBillingPromotionDetailDTO) {
        this.listBillingPromotionDetailDTO = listBillingPromotionDetailDTO;
    }

    public AgeSubscriberDTO getAgeSubscriberDTO() {
        return ageSubscriberDTO;
    }

    public void setAgeSubscriberDTO(AgeSubscriberDTO ageSubscriberDTO) {
        this.ageSubscriberDTO = ageSubscriberDTO;
    }
}
