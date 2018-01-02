package com.viettel.bss.viettelpos.v4.object;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "return", strict = false)
public class ProgramBO {
	@ElementList(name = "lstProgramStatusBO", entry = "lstProgramStatusBO", required = false, inline = true)
	private List<ProgramStatusBO> lstProgramStatusBO;
	@Element(name = "programId", required = false)
	private Integer programId;
	@Element(name = "programName", required = false)
	private String programName;
	@Element(name = "isAssign", required = false)
	private String isAssign;
    @ElementList(name = "lstProgramPropertyBO", entry = "lstProgramPropertyBO", required = false, inline = true)
    private List<ProgramPropertyBO> lstProgramPropertyBOs;
	
	public List<ProgramStatusBO> getLstProgramStatusBO() {
		return lstProgramStatusBO;
	}
	public void setLstProgramStatusBO(List<ProgramStatusBO> lstProgramStatusBO) {
		this.lstProgramStatusBO = lstProgramStatusBO;
	}
	public Integer getProgramId() {
		return programId;
	}
	public void setProgramId(Integer programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public String getIsAssign() {
		return isAssign;
	}
	public void setIsAssign(String isAssign) {
		this.isAssign = isAssign;
	}

    public List<ProgramPropertyBO> getLstProgramPropertyBOs() {
        return lstProgramPropertyBOs;
    }

    public void setLstProgramPropertyBOs(List<ProgramPropertyBO> lstProgramPropertyBOs) {
        this.lstProgramPropertyBOs = lstProgramPropertyBOs;
    }

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return programName;
	}
	
	
}
