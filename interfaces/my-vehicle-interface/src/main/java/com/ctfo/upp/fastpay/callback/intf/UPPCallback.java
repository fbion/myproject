package com.ctfo.upp.fastpay.callback.intf;

import org.springframework.http.ResponseEntity;

import com.ctfo.upp.exception.UPPException;

public interface UPPCallback {
	ResponseEntity<String> callback(String data/* fast payment domain json */) throws UPPException;

	public interface UPPCallbackImplemention {
		String callback(Object dataObject/* fast payment domain */) throws UPPException;
	}
}
