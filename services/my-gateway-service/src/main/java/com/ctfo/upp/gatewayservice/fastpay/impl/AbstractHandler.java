package com.ctfo.upp.gatewayservice.fastpay.impl;

import com.ctfo.upp.gateway.fastpay.intf.RunHandler;

public abstract class AbstractHandler implements RunHandler {
	protected RunHandler successor;

	public RunHandler getSuccessor() {
		return successor;
	}

	public void setSuccessor(RunHandler successor) {
		this.successor = successor;
	}

}
