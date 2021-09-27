package com.gpg.service;

public class UserUtilss {
	
	public UserUtilss() {
	
	}

	public static boolean isEmail(String email) {

		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public static boolean isMobile(String mobile) {
		String regex = "^[0-9]{8}$";
		return mobile.matches(regex);

	}

}
