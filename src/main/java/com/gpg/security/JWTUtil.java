package com.gpg.security;

public class JWTUtil {
	public static final String SECRET="mySecret1234";
	public static final String AUTH_HEADER="Authorization";
	public static final String PREFIX="Bearer ";
	public static final long EXPIRE_ACCESS_TOCKEN=100*60*900000000;
	public static final long REFRESH_ACCESS_TOCKEN =15*60*1000;

}
