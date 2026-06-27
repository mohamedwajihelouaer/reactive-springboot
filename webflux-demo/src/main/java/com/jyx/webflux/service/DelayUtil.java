package com.jyx.webflux.service;

public class DelayUtil {

	public static void delay(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private DelayUtil() {
	}
}
