package com.viettel.bss.viettelpos.v4.connecttionService.beans;

import java.util.ArrayList;
import java.util.List;

public class SupplyProgramBean {
	  private String programName;
	    private String programCode;
	    private String supplyMethod;
	    private List<SupplyBaseBean> lstSupplyBaseBean = new ArrayList<>();
		public SupplyProgramBean() {
			super();
		}
		public SupplyProgramBean(String programName, String programCode) {
			super();
			this.programName = programName;
			this.programCode = programCode;
		}
		public SupplyProgramBean(String programName, String programCode,
				String supplyMethod, List<SupplyBaseBean> lstSupplyBaseBean) {
			super();
			this.programName = programName;
			this.programCode = programCode;
			this.supplyMethod = supplyMethod;
			this.lstSupplyBaseBean = lstSupplyBaseBean;
		}
		public String getProgramName() {
			return programName;
		}
		public void setProgramName(String programName) {
			this.programName = programName;
		}
		public String getProgramCode() {
			return programCode;
		}
		public void setProgramCode(String programCode) {
			this.programCode = programCode;
		}
		public String getSupplyMethod() {
			return supplyMethod;
		}
		public void setSupplyMethod(String supplyMethod) {
			this.supplyMethod = supplyMethod;
		}
		public List<SupplyBaseBean> getLstSupplyBaseBean() {
			return lstSupplyBaseBean;
		}
		public void setLstSupplyBaseBean(List<SupplyBaseBean> lstSupplyBaseBean) {
			this.lstSupplyBaseBean = lstSupplyBaseBean;
		}

		@Override
		public String toString() {
			return programCode + " " + programName;
		}
}
