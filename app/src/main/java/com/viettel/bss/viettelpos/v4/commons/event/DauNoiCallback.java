package com.viettel.bss.viettelpos.v4.commons.event;

import com.viettel.bccs2.viettelpos.v2.connecttionMobile.beans.ProductOfferingDTO;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.HTHMBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.PromotionTypeBeans;
import com.viettel.bss.viettelpos.v4.connecttionService.beans.SubTypeBeans;

import java.util.ArrayList;

/**
 * Created by diepdc on 19/8/2017.
 */

public interface DauNoiCallback {
    void packageCallback(ProductOfferingDTO tmp, ArrayList<ProductOfferingDTO> lstGoiCuoc);

    void subTypeCallback(SubTypeBeans subTypeBeans, ArrayList<SubTypeBeans> lstData);

    void hthmCallback(HTHMBeans hthm);

    void promotionCallback(PromotionTypeBeans tmp);


}
