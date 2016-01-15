package com.ctfo.account.dto.beans;

import com.ctfo.upp.models.BaseSerializable;

public class BalanceDto extends BaseSerializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//总余额
	private Long totalBalance;
	//不可提现余额
	private Long unableTakecashBalance;
	//冻结余额
	private Long frozenBalance;
	//可用余额
	private Long usableBalance;
	
	
	public Long getTotalBalance() {
		return totalBalance;
	}
	public void setTotalBalance(Long totalBalance) {
		this.totalBalance = totalBalance;
	}
	public Long getUnableTakecashBalance() {
		return unableTakecashBalance;
	}
	public void setUnableTakecashBalance(Long unableTakecashBalance) {
		this.unableTakecashBalance = unableTakecashBalance;
	}
	public Long getFrozenBalance() {
		return frozenBalance;
	}
	public void setFrozenBalance(Long frozenBalance) {
		this.frozenBalance = frozenBalance;
	}
	public Long getUsableBalance() {
		return usableBalance;
	}
	public void setUsableBalance(Long usableBalance) {
		this.usableBalance = usableBalance;
	}
	
	
	

}
