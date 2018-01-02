package com.viettel.bss.viettelpos.v4.bo;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by toancx on 6/8/2017.
 */
@Root(name = "return", strict = false)
public class CriteriaGroup {
    @Element(name = "criteriaBO", required = false)
    private Criteria criteria;
    @ElementList(name = "lstCriteriaBOs", entry = "lstCriteriaBOs", required = false, inline = true)
    private List<Criteria> lstCriterias;

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public List<Criteria> getLstCriterias() {
        return lstCriterias;
    }

    public void setLstCriterias(List<Criteria> lstCriterias) {
        this.lstCriterias = lstCriterias;
    }

    @Override
    public String toString() {
        if(criteria != null) {
            return criteria.getName();
        } else {
            return "";
        }
    }
}
