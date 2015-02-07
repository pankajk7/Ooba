package com.ooba.entity;

public class GridRowData {

	public String id;
	public String paymentAmount;
	public String interestAmount;
	public String centralReduction;
	public String balanceDue;

	public GridRowData(String id,String paymentAmount,String interestAmount,String centralReduction,String balanceDue)
	{
		this.id=id;
		this.paymentAmount=paymentAmount;
		this.interestAmount=interestAmount;
		this.centralReduction=centralReduction;
		this.balanceDue=balanceDue;
		
	}
}
