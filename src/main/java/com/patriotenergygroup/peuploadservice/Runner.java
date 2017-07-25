package com.patriotenergygroup.peuploadservice;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Runner {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("admin = " + encoder.encode("admin"));	
		System.out.println("apisuer = " + encoder.encode("apiuser"));	
	}

}
