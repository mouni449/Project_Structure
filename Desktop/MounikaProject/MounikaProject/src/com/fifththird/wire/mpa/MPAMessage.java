package com.fifththird.wire.mpa;

import java.math.BigDecimal;

public abstract class MPAMessage {

private static final String LOGIN = "HTLOGIN "; 
private static final String DEBIT = "WTDEBIT "; 
private static final String CREDIT = "WTCREDIT"; 
private static final String BALANCE = "WTBALREQ"; 
private static final String REVERSEFLAG = "R";
protected static final MPAField TRANSACTION_ID = new MPAField("Transaction ID",0,8);
protected static final MPAField BANK_ID = new MPAField("Bank ID",8,10);
protected static final MPAField REVERSE_FLAG = new MPAField("Reverse Flag",57,1);

private String message;

	protected MPAMessage(String message) {
		this.message = message;
	}

	public int getLength(){
		return message.length();
	}
	
	public String parseField(MPAField field){ 
	return message.substring(field.getStartPosition(), 
			field.getStartPosition() + field.getLength());
	}
	
	protected void setField(MPAField field, BigDecimal value){
		String finalValue = field.pad( value.abs().toString() );
		// Add sign 
		boolean isNegative = value.compareTo( new BigDecimal(0) ) == -1; 
		if( isNegative )
			finalValue = "-" + finalValue.substring( 1 ); 
		else
			finalValue = "+" + finalValue.substring( 1 );
		setFieldInternal( field, finalValue );
	}
	
	protected void setField(MPAField field, String value){
		String finalValue = field.pad(value);
		setFieldInternal( field, finalValue );
	}
	
	private void setFieldInternal( MPAField field, String finalValue ){

		StringBuffer newMessage = new StringBuffer(message.length())
				.append(message.substring(0, field.getStartPosition()))
				.append( finalValue)
				.append(message.substring(field.getStartPosition() + field.getLength()));
		message = newMessage.toString();
	}
	public abstract String process();
	
	public String toString() {
		return message;
	}
	
	public static MPAMessage newInstance(String message){

		if( message.length() == 2 ){
			return new PreambleMessage(message); 
		}else{ 
			String transId = message.substring(TRANSACTION_ID.getStartPosition(),
					TRANSACTION_ID.getLength() + TRANSACTION_ID.getStartPosition());
			if ( DEBIT.equals(transId) || CREDIT.equals(transId)) { 
				String reverseFlag = message.substring(REVERSE_FLAG.getStartPosition(),
							REVERSE_FLAG.getLength() + REVERSE_FLAG.getStartPosition());
				if (REVERSEFLAG.equals(reverseFlag) && DEBIT.equals(transId)){
					transId = CREDIT; 
				}else if ( REVERSEFLAG.equals( reverseFlag) && CREDIT.equals(transId)){
					transId = DEBIT;
				}
			}
			if( LOGIN.equals( transId )) {
				return new MPALoginMessage(message);
			}else if( DEBIT.equals(transId)){
				return new PostingTransactionMessage(message); 
			}else if( CREDIT.equals(transId)){
				return new CreditPostingTransactionMessage(message); 
			}else if(BALANCE.equals(transId)){
				return new MPABalanceMessage(message);
				
			}
		}
		return null;
	}
}