package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by thinhhq1 on 8/21/2017.
 */
@Root(name = "TelecomServiceGroupDetailDTO", strict = false)
public class TelecomServiceGroupDetailDTO   implements Serializable{
    @Element(name = "groupName", required = false)
    protected String groupName;

    public TelecomServiceGroupDetailDTO(String name) {
        this.name = name;
    }


    public TelecomServiceGroupDetailDTO(String name, String parentId, String telServiceAlias, String telecomServiceId) {
        this.name = name;
        this.parentId = parentId;
        this.telServiceAlias = telServiceAlias;
        this.telecomServiceId = telecomServiceId;
    }

    public TelecomServiceGroupDetailDTO() {


    }

    @Element(name = "groupProductId", required = false)
    protected String groupProductId;
    @Element(name = "listAvailableTechnologies", required = false)
        protected List<String> listAvailableTechnologies;
    @Element(name = "listIsnd", required = false)
    protected List<String> listIsnd;
    @Element(name = "maxNumberOfService", required = false)
    protected Long maxNumberOfService;
    @Element(name = "name", required = false)
    protected String name;
    @Element(name = "numberOfService", required = false)
    protected Long numberOfService;
    @Element(name = "parentId", required = false)
    protected String parentId;
    @Element(name = "selectable", required = false)
    protected boolean selectable;
    @Element(name = "selected", required = false)
    protected boolean selected;
    @Element(name = "telServiceAlias", required = false)
    protected String telServiceAlias;
    @Element(name = "telecomServiceId", required = false)
    protected String telecomServiceId;

    public String getGroupName() {
        return groupName;
    }

    public String getGroupProductId() {
        return groupProductId;
    }

    public List<String> getListAvailableTechnologies() {
        return listAvailableTechnologies;
    }

    public List<String> getListIsnd() {
        return listIsnd;
    }

    public Long getMaxNumberOfService() {
        return maxNumberOfService;
    }

    public String getName() {
        return name;
    }

    public Long getNumberOfService() {
        return numberOfService;
    }

    public String getParentId() {
        return parentId;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getTelServiceAlias() {
        return telServiceAlias;
    }

    public String getTelecomServiceId() {
        return telecomServiceId;
    }
}