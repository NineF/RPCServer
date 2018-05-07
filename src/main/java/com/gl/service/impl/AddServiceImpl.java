package com.gl.service.impl;

import com.gl.service.AddService;

public class AddServiceImpl implements AddService{

	public int addInt(int a, int b) {
		System.out.println("addInt");
		return a+b;
	}

	public String addStr(String a, String b) {
		System.out.println("addStr");
		return a+b;
	}

}
