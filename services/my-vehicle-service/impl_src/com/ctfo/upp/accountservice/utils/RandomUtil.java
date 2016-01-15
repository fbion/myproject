package com.ctfo.upp.accountservice.utils;

public final class RandomUtil {
	static public int buildThreeBitRandomNumber() {
		return (int) (Math.random() * 900) + 100;
	}
}
