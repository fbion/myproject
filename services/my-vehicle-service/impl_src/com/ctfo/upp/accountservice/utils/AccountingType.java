package com.ctfo.upp.accountservice.utils;

import java.util.ArrayList;
import java.util.List;

public final class AccountingType {
	static public final String INCOME = "recorded";
	static public final String DEDUCTION = "deduction";
	static public final String FROZEN = "frozen";
	static public final String UNFROZEN = "unfrozen";
	static public List<String> bookaccountTypes=new ArrayList<String>();
	static{
		bookaccountTypes.add(INCOME);
		bookaccountTypes.add(DEDUCTION);
	}
}
