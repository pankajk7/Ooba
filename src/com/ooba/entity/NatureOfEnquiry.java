package com.ooba.entity;

public class NatureOfEnquiry {
	public String listString;
	
	public NatureOfEnquiry(String listString){
		this.listString = listString;
	}
	
	@Override
	public String toString() {	
		return listString;
	}
}
