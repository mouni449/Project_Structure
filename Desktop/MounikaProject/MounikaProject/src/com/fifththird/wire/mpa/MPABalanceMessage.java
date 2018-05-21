package com.fifththird.wire.mpa;


import com.fifththird.wire.Account;
import com.fifththird.wire.exception.AccountNotFoundException;
import com.fifththird.wire.exception.ConfigurationException;
import com.fifththird.wire.exception.HostCommunicationException;

public class MPABalanceMessage extends MPAMessage { 

public MPABalanceMessage(String message){
	super(message); 
}

public String process() {
	String returnCode = "00"; 
	MPAMessage replyMessage = new MPABalanceMessage(super.toString()); 
	replyMessage.setField(BALANCE_AMOUNT,"+000000000000000.00"); 
	replyMessage.setField(STATUS_REASON,"              ");
	replyMessage.setField(POST_STATUS,"32"); 
	String accountNumber = parseField(ACCOUNT); 
	String accountIdType=this.parseField(ACCOUNT_IDTYPE); 
	Account account = null; 
	if(!isValidAccountType(accountIdType)){
		replyMessage.setField(POST_STATUS,"31"); 
		replyMessage.setField(STATUS_REASON,"Invalid Account Type"); 
		return replyMessage.toString().substring(0,80); 
	}
	AccountRequester accountRequester; 
	try { 
		try{
		accountRequester = lookupAccountInformationRequester(); 
		} catch (ConfigurationException el) {
			System.out.println(el.toString()); 
			replyMessage.setField(POST_STATUS,"40"); 
			replyMessage.setField(STATUS_REASON,"Host Communication Error    ");
			return replyMessage.toString().substring(0,80); 
		} 
		AccountType accountType = AccountType.getAccountType(accountIdType); 
		
		try{ 
			account = accountRequester.requestAccountInformation(null, accountType, accountNumber); 
		}catch(AccountNotFoundException e)
		{ 
			if( "D".equals(parseField(ACCOUNT_IDTYPE)) )
				accountType = AccountType.SAVINGS; 
			else 
				accountType = AccountType.DOA; 
			try 
			{ 
				account = accountRequester.requestAccountInformation(null, accountType, accountNumber); 
				}catch(AccountNotFoundException e1){
					replyMessage.setField(POST_STATUS,"31"); 
					replyMessage.setField(STATUS_REASON, "Account Not Found"); 
					return replyMessage.toString().substring(0,80); 
				}catch(HostCommunicationException el){ 
					System.out.println(el.toString()); 
					replyMessage.setField(POST_STATUS,"40"); 
					replyMessage.setField(STATUS_REASON, "Host Communication Error"); 
					return replyMessage.toString().substring(0,80); 
					}catch(Exception el){ 
						el.printStackTrace(); 
						replyMessage.setField(POST_STATUS,"32"); 
						replyMessage.setField(STATUS_REASON, "Unknown Error Retrieving Ba1"); 
						return replyMessage.toString().substring(0,204); 
					}
				}
				
			} catch(HostCommunicationException e)
			{ 
				System.out.println(e.toString()); 
				replyMessage.setField(POST_STATUS,"40"); 
				replyMessage.setField(STATUS_REASON, "Host Communication Error"); 
				return replyMessage.toString().substring(0, 80); 
			}catch(Exception e){ 
				e.printStackTrace(); 
				replyMessage.setField(POST_STATUS,"32"); 
				replyMessage.setField(STATUS_REASON, "Unknown Furor Placing Hold"); 
				return replyMessage.toString().substring(0,204); 
			}
			
			replyMessage.setField(POST_STATUS,"00"); 
			replyMessage.setField(BALANCE_AMOUNT,account.getAvailableBalance()); 
			return replyMessage.toString().substring(0,80); 
	} 

	private boolean isValidAccountType(String accountidType) { 
			return ("D".equals(accountidType) || "W".equals(accountidType));
	}
	
	private AccountRequester lookupAccountInformationRequester() throws ConfigurationException {
		return AccountRequesterFactory.getInstance().createAccountInformationRequester();
	}
	
	public static final MPAField ACCOUNT_IDTYPE = 
			new MPAField("Account ID Type", 18, 1);
	public static final MPAField ACCOUNT = 
			new MPAField("Account number", 19, 14);
	public static final MPAField DBT_DEPT = 
			new MPAField("Debit department code", 33, 10);
	public static final MPAField CDT_DEPT = 
			new MPAField("Credit department code", 43, 10);
	public static final MPAField ACCT_DLOD = 
			new MPAField("Account daylight overdraft limit", 53, 16);
	public static final MPAField GRP_ID = 
			new MPAField("Group identifier", 69, 10);
	public static final MPAField GRP_DLOD = 
			new MPAField("Group daylight overdraft limit", 79,16);
	public static final MPAField AFFILIATE_CODE = 
			new MPAField("Affiliate bank identifier", 96, 3);
	public static final MPAField BALANCE_AMOUNT = 
			new MPAField("Balance Amount", 33, 16, '0', MPAField.ALIGN_RIGHT);
	public static final MPAField POST_STATUS = 
			new MPAField("Status code", 49, 2);
	public static final MPAField STATUS_REASON = 
			new MPAField("Status message", 51, 30, ' ', MPAField.ALIGN_LEFT);
	
}