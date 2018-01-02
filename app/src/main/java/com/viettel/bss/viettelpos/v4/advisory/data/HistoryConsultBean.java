package com.viettel.bss.viettelpos.v4.advisory.data;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by hantt47 on 7/18/2017.
 */
@Root(name = "return", strict = false)
public class HistoryConsultBean {

    @Element(name = "customer", required = false)
    protected String customer;
    @Element(name = "time", required = false)
    protected String time;
    @Element(name = "status", required = false)
    protected String status;
    @Element(name = "statusName", required = false)
    protected String statusName;
    @Element(name = "productBean", required = false)
    protected ProductBean productBean;

    public HistoryConsultBean() {

    }

    public HistoryConsultBean(int index) {
        customer = "0988 128 191";
        time = index % 30 + "/08/2017";
        status = "1";
        statusName = "Tư vấn thành công";
        productBean = new ProductBean("HungTV " + index, "Test",
                "Hãy làm việc hết mình những điều tốt đẹp sẽ đến với bạn",
                "100000", "7000");
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ProductBean getProductBean() {
        return productBean;
    }

    public void setProductBean(ProductBean productBean) {
        this.productBean = productBean;
    }
}
