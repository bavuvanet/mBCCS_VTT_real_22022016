package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.util.ArrayList;
import java.util.List;

public class SupplyInforBean {
	  private List<SupplyMethodBean> lstSupplyMethod = new ArrayList<>();
	    private List<SupplyProgramBean> lstSupplyProgramBean = new ArrayList<>();
	    
		public SupplyInforBean() {
			super();
		}
		public SupplyInforBean(List<SupplyMethodBean> lstSupplyMethod,
				List<SupplyProgramBean> lstSupplyProgramBean) {
			super();
			this.lstSupplyMethod = lstSupplyMethod;
			this.lstSupplyProgramBean = lstSupplyProgramBean;
		}
		public List<SupplyMethodBean> getLstSupplyMethod() {
			return lstSupplyMethod;
		}
		public void setLstSupplyMethod(List<SupplyMethodBean> lstSupplyMethod) {
			this.lstSupplyMethod = lstSupplyMethod;
		}
		public List<SupplyProgramBean> getLstSupplyProgramBean() {
			return lstSupplyProgramBean;
		}
		public void setLstSupplyProgramBean(List<SupplyProgramBean> lstSupplyProgramBean) {
			this.lstSupplyProgramBean = lstSupplyProgramBean;
		}
	    
}
