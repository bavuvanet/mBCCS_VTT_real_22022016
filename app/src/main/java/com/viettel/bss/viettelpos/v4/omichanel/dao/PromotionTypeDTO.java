package com.viettel.bss.viettelpos.v4.omichanel.dao;



import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PromotionTypeDTO implements Serializable {
    private String content;
    private Long cycle;
    private String description;
    private Date endDate;
    private Short local;
    private Long monthAmount;
    private Long monthCommitment;
    private String name;
    private String pricePlan;
    private Short procedureId;
    private Short productId;
    private String promProgramCode;
    private String promType;
    private Date staDate;
    private String status;
    private String telService;
    private String type;
    private String codeName;
    //ngay bat dau huong khuyen mai, ngay dau tien thang tiep theo
    private Date startDatePromotion;
    private Date startDateSub;
    private String startDatePromotionStr;
    // ngay ket thuc khuyen mai
    private Date endDatePromotion;
    private String endDatePromotionStr;
    private Date endDateCommitment;

    public PromotionTypeDTO() {
    }

    public PromotionTypeDTO(String promProgramCode, String name) {
        this.promProgramCode = promProgramCode;
        this.name = name;
    }


    public String getCodeName() {
        return this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCycle() {
        return this.cycle;
    }

    public void setCycle(Long cycle) {
        this.cycle = cycle;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getLocal() {
        return this.local;
    }

    public void setLocal(Short local) {
        this.local = local;
    }

    public Long getMonthAmount() {
        return this.monthAmount;
    }

    public void setMonthAmount(Long monthAmount) {
        this.monthAmount = monthAmount;
    }

    public Long getMonthCommitment() {
        return this.monthCommitment;
    }

    public void setMonthCommitment(Long monthCommitment) {
        this.monthCommitment = monthCommitment;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPricePlan() {
        return this.pricePlan;
    }

    public void setPricePlan(String pricePlan) {
        this.pricePlan = pricePlan;
    }

    public Short getProcedureId() {
        return this.procedureId;
    }

    public void setProcedureId(Short procedureId) {
        this.procedureId = procedureId;
    }

    public Short getProductId() {
        return this.productId;
    }

    public void setProductId(Short productId) {
        this.productId = productId;
    }

    public String getPromProgramCode() {
        return this.promProgramCode;
    }

    public void setPromProgramCode(String promProgramCode) {
        this.promProgramCode = promProgramCode;
    }

    public String getPromType() {
        return this.promType;
    }

    public void setPromType(String promType) {
        this.promType = promType;
    }

    public Date getStaDate() {
        return this.staDate;
    }

    public void setStaDate(Date staDate) {
        this.staDate = staDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTelService() {
        return this.telService;
    }

    public void setTelService(String telService) {
        this.telService = telService;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getStartDatePromotion() {
        return startDatePromotion;
    }

    public void setStartDatePromotion(Date startDatePromotion) {
        this.startDatePromotion = startDatePromotion;
    }

    public Date getEndDatePromotion() {
        return endDatePromotion;
    }

    public void setEndDatePromotion(Date endDatePromotion) {
        this.endDatePromotion = endDatePromotion;
    }

    public String getStartDatePromotionStr() {
        return startDatePromotionStr;
    }

    public void setStartDatePromotionStr(String startDatePromotionStr) {
        this.startDatePromotionStr = startDatePromotionStr;
    }

    public String getEndDatePromotionStr() {
        return endDatePromotionStr;
    }

    public void setEndDatePromotionStr(String endDatePromotionStr) {
        this.endDatePromotionStr = endDatePromotionStr;
    }

    public Date getEndDateCommitment() {
        return endDateCommitment;
    }

    public void setEndDateCommitment(Date endDateCommitment) {
        this.endDateCommitment = endDateCommitment;
    }

    public Date getStartDateSub() {
        return startDateSub;
    }

    public void setStartDateSub(Date startDateSub) {
        this.startDateSub = startDateSub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionTypeDTO that = (PromotionTypeDTO) o;
        return Objects.equals(promProgramCode, that.promProgramCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promProgramCode);
    }
}
