package com.ctfo.account.contrast.intf.external;

import com.ctfo.upp.exception.UPPException;

/***
 * Reconciliation and business platform.
 *
 */
public interface WithBusinessPlatformContrast extends AccountContrast {
	/***
	 * Stored in the FTP server
	 * @throws UPPException
	 */
	public void buildAccountFlowFile() throws UPPException;
}
