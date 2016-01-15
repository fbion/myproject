package com.ctfo.account.dto.beans;

import com.ctfo.upp.models.BaseSerializable;

public class RecordedDeductionDto extends BaseSerializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long recorded;
	
	private Long deduction;

	public Long getRecorded() {
		return recorded;
	}

	public void setRecorded(Long recorded) {
		this.recorded = recorded;
	}

	public Long getDeduction() {
		return deduction;
	}

	public void setDeduction(Long deduction) {
		this.deduction = deduction;
	}
	
	

}
