package com.ooba.entity;

import java.util.ArrayList;

public class AmmortisationHeader {

	public String Heading;
	public ArrayList<GridRowData> gridArrayList;
	public AmmortisationHeader(String x,ArrayList<GridRowData> list) {
	
		Heading=x;
		gridArrayList=list;
	}
}
