package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.CustomerDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.CustomerOrderDetailDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.GroupsDTO;
import com.viettel.bss.viettelpos.v3.connecttionService.model.ResultSurveyOnlineForBccs2Form;
import com.viettel.bss.viettelpos.v3.connecttionService.model.SubInfrastructureDTO;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by hantt47 on 8/7/2017.
 */

@Root(name = "CustomerOrderDetailMbccsDTO", strict = false)
public class CustomerOrderDetailMbccsDTO {
    @Element(name = "customerOrderDetail", required = false)
    private CustomerOrderDetailDTO customerOrderDetail;
    @Element(name = "telecomServiceName", required = false)
    private String telecomServiceName;
    @Element(name = "telecomServiceAlias", required = false)
    private String telecomServiceAlias;
    @Element(name = "resultSurveyOnlineForBccs2Form", required = false)
    private ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form;
    @ElementList(name = "lstSubInfrastructureS", entry = "lstSubInfrastructureS", required = false, inline = true)
    private List<SubInfrastructureDTO> lstSubInfrastructureS;
    @Element(name = "customer", required = false)
    private CustomerDTO customer;
    @Element(name = "groupsDTO", required = false)
    private GroupsDTO groupsDTO;

    public CustomerOrderDetailDTO getCustomerOrderDetail() {
        return customerOrderDetail;
    }

    public void setCustomerOrderDetail(CustomerOrderDetailDTO customerOrderDetail) {
        this.customerOrderDetail = customerOrderDetail;
    }

    public String getTelecomServiceName() {
        return telecomServiceName;
    }

    public void setTelecomServiceName(String telecomServiceName) {
        this.telecomServiceName = telecomServiceName;
    }

    public String getTelecomServiceAlias() {
        return telecomServiceAlias;
    }

    public void setTelecomServiceAlias(String telecomServiceAlias) {
        this.telecomServiceAlias = telecomServiceAlias;
    }

    public ResultSurveyOnlineForBccs2Form getResultSurveyOnlineForBccs2Form() {
        return resultSurveyOnlineForBccs2Form;
    }

    public void setResultSurveyOnlineForBccs2Form(ResultSurveyOnlineForBccs2Form resultSurveyOnlineForBccs2Form) {
        this.resultSurveyOnlineForBccs2Form = resultSurveyOnlineForBccs2Form;
    }

    public List<SubInfrastructureDTO> getLstSubInfrastructureS() {
        return lstSubInfrastructureS;
    }

    public void setLstSubInfrastructureS(List<SubInfrastructureDTO> lstSubInfrastructureS) {
        this.lstSubInfrastructureS = lstSubInfrastructureS;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public GroupsDTO getGroupsDTO() {
        return groupsDTO;
    }

    public void setGroupsDTO(GroupsDTO groupsDTO) {
        this.groupsDTO = groupsDTO;
    }
}
