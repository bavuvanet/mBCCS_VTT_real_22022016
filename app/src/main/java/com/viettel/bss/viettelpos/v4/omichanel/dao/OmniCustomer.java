package com.viettel.bss.viettelpos.v4.omichanel.dao;

import java.io.Serializable;

/**
 * Created by TuLA4 on 8/29/2017.
 */
public class OmniCustomer implements Serializable {
    private String name;
    private String birthDate;
    private String idNo;
    private String idIssueDate;
    private String idIssuePlace;
    private OmniAddress address;
    private Long custId;
    private String custType;
    private String email;
    private String telephone;

    public OmniCustomer() {
    }

    public OmniCustomer(Long custId, String name, String birthDate, String identityNo, String idIssueDate, String idIssuePlace, String email, String telephone, OmniAddress omniAddress) {
        this.custId = custId;
        this.name = name;
        this.birthDate = birthDate;
        this.idNo = identityNo;
        this.idIssueDate = idIssueDate;
        this.idIssuePlace = idIssuePlace;
        this.email = email;
        this.telephone = telephone;
        this.address = omniAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(String idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public OmniAddress getAddress() {
        return address;
    }

    public void setAddress(OmniAddress address) {
        this.address = address;
    }

    public Long getCustId() {
        return custId;
    }

    public void setCustId(Long custId) {
        this.custId = custId;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
