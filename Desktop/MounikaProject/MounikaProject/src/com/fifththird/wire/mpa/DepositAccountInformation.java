package com.fifththird.wire.mpa;

import java.io.Serializable;
public class DepositAccountInformation  implements Serializable{
	
	private int depositAccountType;
	private BigDecimal oneDayFloatAmount;
	private String RestraintCode;
	
	public DepositAccountInformation(){
		super();
		
	}
	
	
	public String getRestraintCode(){
		return restraintCode;
		
	}
	
	public void setRestraintCode(String restraintCode){
		this.RestraintCode = restraintCode;
				
	}
	
	public int getDepositAccountType(){
		return depositAccountType;
	
	}
	public void setDepositAccountType(int depositAccountType){
		this.depositAccountType = depositAccountType;
	}
	public BigDecimal getOneDayFloatAmount(){
		return oneDayFloatAmount;
	}
	
	public void setOneDayFloatAmount(BigDecimal oneDayFloatAmount){
		this.oneDayFloatAmount =  oneDayFloatAmount;
	}
}

