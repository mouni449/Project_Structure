package com.fifththird.wire.mpa;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import com.fifththird.wire.Account;
import com.fifththird.wire.WireProperties;
import com.fifththird.wire.exception.AccountNotFoundException;
import com.fifththird.wire.exception.ConfigurationException;


public class PostingTransactionMessage extends MPAMessage {
	private static WireProperties props;
	
	public PostingTransactionMessage(String message){
		super(message);
	}
	
	private MPAMessage defaultReturnMessage(){
		MPAMessage replyMessage = new PostingTransactionMessage(super.toString());
		replyMessage.setField(ACCT_DLOD, this.parseField(REQ_ACCT_DLOD));
		replyMessage.setField(CONC_ACCT_BANK, this.parseField(REQ_CONC_ACCT_BANK));
		replyMessage.setField(CONC_ACCT_IDTYPE, this.parseField(REQ_CONC_ACCT_IDTYPE));
		replyMessage.setField(CONC_ACCT_ID, "               ");
		replyMessage.setField(LEDGER_BALANCE, "+000000000000.00");
		replyMessage.setField(CHARGE_FLAG, "  ");
		replyMessage.setField(DEPARTMENT, "      ");
		replyMessage.setField(ACCOUNT_BALANCE, "+000000.00");
		replyMessage.setField(STATUS_REASON, "    ");
		replyMessage.setField(POST_STATUS, "32");
		return replyMessage;
	}
	
	/*public String parseField(MPAField reqAcctDlod) {
		// TODO Auto-generated method stub
		return null;
	}*/

	public String process(){
		Boolean review;
		if(props==null){
			try{
				props=WireProperties.getInstance();
			}catch (ConfigurationException ex){
				ex.printStackTrace();
				review = true;
			}	
		}
		review = Boolean.parseBoolean(props.getProperty("wire.fraud.review"));
		if(review){
			Transaction trn = new Transaction();
			String investStr = (trn.investigate("20"+parseField(INTRANET_TRN), parseField(ACCOUNT)));
			if(investStr.equals("0")){
				return processPosting(false, "");
			}else{
				return processPosting(true, investStr);
			}
		}else{
			return processPosting(false, "");
		}
	}
	
	public String processPosting(boolean quickHitsStop, String trnID){
		MPAMessage replyMessage = defaultReturnMessage();
		String forceFlag;
		if("T".equals(parseField(FORCE_FLAG))){
			forceFlag="true";
		}else 
			forceFlag="false";
		
		String nonBalForceFlag;
		if("T".equals(parseField(NON_BALANCE_FORCE_FLAG))){
			nonBalForceFlag="true";
		}else 
			nonBalForceFlag="false";
		
		
		String accountNumber = parseField(ACCOUNT); 
		
		BigDecimal amount = new BigDecimal(parseField(AMOUNT)); 
		String accountIdType = parseField(ACCOUNT_IDTYPE); 
		 
		if( !isValidAccountType(accountIdType)){
			replyMessage.setField(POST_STATUS,"31"); 
			replyMessage.setField(STATUS_REASON, "Invalid Account Type"); 
			return replyMessage.toString().substring(0,204); 
		}

		AccountRequester accountRequester; 
		try { 
			accountRequester = lookupAccountInformationRequester(); 
		} catch (ConfigurationException el) {
			System.out.println(el.toString()); 
			replyMessage.setField(POST_STATUS,"40"); 
			replyMessage.setField(STATUS_REASON, "Host Communication Errors"); 
			return replyMessage.toString().substring(0,204); 
		}

		AccountType accountType = AccountType.getAccountType(accountIdType); 
		Account account = null; 
		HoldResult holdresult = null;
		DepositAccountInformation depositaccount = null;
		try{
			try{
				account = accountRequester.requestAccountInformation(null, accountType, accountNumber);
			}catch(AccountNotFoundException e){
				if("D".equals(parseField(ACCOUNT_IDTYPE)))
					accountType = AccountType.SAVINGS;
				else
					accountType = AccountType.DOA;
				try{
					account = accountRequester.requestAccountInformation(null, accountType, accountNumber);
				}catch(AccountNotFoundException e1){
					replyMessage.setField(POST_STATUS, "31");
					replyMessage.setField(STATUS_REASON, "Account Not Found");
					return replyMessage.toString().substring(0, 204);
				}
			}
			if("C".equals(account.getAccountStatus())){
				replyMessage.setField(POST_STATUS, "32");
				replyMessage.setField(STATUS_REASON, "Hold Denied, Account Closed");
				return replyMessage.toString().substring(0, 204);
			}
			
			depositaccount = UDSGeneralRequest(account);
			
			boolean dacaFlag = "A".equals(depositaccount.getDacaFlag());
			
			
			if(!nonBalForceFlag.equals("true") && !forceFlag.equals("true")){
				String returnStr="";
				String displayTrnID = trnID;
				if(trnID.length()<2){
					displayTrnID = "";
				}else{
					displayTrnID = trnID.substring(2, 4)+
							trnID.substring(5, 7)
							+trnID.substring(8, 10)
							+trnID.substring(20);
					
				}
				
				if(dacaFlag){
					returnStr = "DACA Hold";
					if(quickHitsStop){
						if(displayTrnID.length() > 0){
							returnStr = "DF"+displayTrnID;
						}else{
							returnStr = "DACA/Fraud";
						}
					}
				}else if(quickHitsStop){
					if(displayTrnID.length() > 0){
						returnStr = "F"+displayTrnID;
					}else{
						returnStr = "Fraud";
					}
				}
				
				if(!returnStr.isEmpty()){
					replyMessage.setField(POST_STATUS, "22");
					replyMessage.setField(STATUS_REASON, returnStr);
					return replyMessage.toString().substring(0, 204);
				}
				
			}
			
		
		
		String zbaParent = depositaccount.getZeroBalanceParentAccount();
		
		if(zbaParent != ""){
			String trn = parseField(INTRANET_TRN);
			setField(ACCOUNT, zbaParent);
			zbaParent = parseField(ACCOUNT);
			
			String response;
			response = processPosting(false, "");
			
			String leftHalf = response.substring(0,66);
			String rightHalf = response.substring(80, 204);
			
			response = leftHalf.concat(accountNumber.concat(rightHalf));
			return response;
		}
		
		if(isInternalAccount(depositaccount)){
			replyMessage.setField(POST_STATUS, "00");
			return replyMessage.toString().substring(0, 204);
		}else{
			holdresult = placeHold(amount, account, forceFlag);
			if(holdresult.getReturnCode() != 1){
				String reason = (String) theMapping.get(holdresult.getMessageCode());
				if(theMapping.isEmpty()){
					replyMessage.setField(POST_STATUS, "32");
					replyMessage.setField(STATUS_REASON, "Unknown error placing hold");
				}
				if("9".equals(holdresult.getMessageCode())){
					String trn = parseField(INTRANET_TRN);
					
					if(forceFlag == "true"){
						replyMessage.setField(POST_STATUS, "10");
						replyMessage.setField(STATUS_REASON, reason);
					}else {
						BigDecimal daylightOverdraft = new BigDecimal(parseField(REQ_ACCT_DLOD));
						BigDecimal availableBalance = account.getAvailableBalance();
						BigDecimal ledgerBalance = account.getLedgerBalance();
						
						if((availableBalance.add(daylightOverdraft)).compareTo(amount) >= 0 ){
							setField(FORCE_FLAG, "T");
							String response;
							response = processPosting(false, "");
							return response;
						}else{
							replyMessage.setField(POST_STATUS, "10");
							replyMessage.setField(STATUS_REASON, reason);
						}
					}
				}else{
					if("10".equals(holdresult.getMessageCode())){
						replyMessage.setField(POST_STATUS, "22");
						replyMessage.setField(STATUS_REASON, reason);
					}else{
						if("A".equals(depositaccount.getDacaFlag())){
							replyMessage.setField(POST_STATUS, "00");
							replyMessage.setField(STATUS_REASON, "DACA override RSK");
						}else{
							replyMessage.setField(POST_STATUS, "32");
							replyMessage.setField(STATUS_REASON, reason);
						}
					}
				}
				replyMessage.setField(ACCOUNT_BALANCE, account.getAvailableBalance());
				return replyMessage.toString().substring(0, 204);
			}else{
				replyMessage.setField(POST_STATUS, "00");
				replyMessage.setField(ACCOUNT_BALANCE, account.getAvailableBalance());
				return replyMessage.toString().substring(0, 204);
			}
		}
	}catch(Exception ex){
		//list of exception captured 
	}
		return null;
	}
	private AccountRequester lookupAccountInformationRequester() throws ConfigurationException {
		return AccountRequesterFactory.getInstance().createAccountInformationRequester();
	}
	
	static Map<String, String> theMapping;
	
	static {
		theMapping = new HashMap();
		theMapping.put("1", "Hold Denied, Account Closed");
		theMapping.put("6", "Internal account status error");
		theMapping.put("7", "Holiday acct - no withdrawals");
		theMapping.put("8", "Hold Denied, Restraint on Acct");
		theMapping.put("9", "Insufficient Funds");
		theMapping.put("10", "Denied Amount > $9,999,999,99");
		theMapping.put("11", "Insufficient Security Auth");
		theMapping.put("12", "Insufficient Security Auth");
	}
	
	private HoldResult placeHold(BigDecimal amount, Account account, String forceFlag) throws ConfigurationException{
		HoldPlacer placer = HoldPlacerFactory.getInstance().createHoldsPlacer();
		return placer.placeTellerHold(account.getAffiliate(), account.getAccountNumber(),
				account.getAccountType(), amount, forceFlag);
	}
	
	private DepositAccountInformation UDSGeneralRequest(Account account) throws AccountNotFoundException, ConfigurationException{
		DepositAccountInformationRequester request =  De
.getInstance().createDepositAccountInformationRequester();
		DepositAccountInformation depositaccount = request.lookupDepositAccountInformation(account);
		return depositaccount;
	}
	
	private boolean isValidAccountType(String accountIdType){
		return ("D".equals(accountIdType) || "V".equals(accountIdType));
	}
	
	private boolean isInternalAccount(DepositAccountInformation depositaccount) throws AccountNotFoundException, ConfigurationException {
		int DepositAccount = depositaccount.getDepositAccountType();
		if((DepositAccount >= 66 && DepositAccount <= 99) || (DepositAccount == 62) ||
				(DepositAccount >= 155 && DepositAccount <= 299 ))
			return true;
		else 
			return false;
	}
	
	public static final MPAField HOST_TRN = 
			new MPAField("Unique ID assigned by the host", 18, 20);
	public static final MPAField INTRANET_TRN = 
			new MPAField("Transaction reference number", 38, 12);
	
	public static final MPAField INTRANET_TRN_SUFFIX = 
			new MPAField("Transaction reference number suffix", 50, 7);
	public static final MPAField REVERSE_FLAG = 
			new MPAField("Reverse flag", 57, 1);
	public static final MPAField POST_SEQUENCE = 
			new MPAField("Post sequence", 58, 6);
	public static final MPAField POST_TYPE = 
			new MPAField("Post type", 64, 1);
	public static final MPAField ACCOUNT_IDTYPE = 
			new MPAField("Account ID Type", 65, 1);
	public static final MPAField ACCOUNT = 
			new MPAField("Account number", 66, 14, ' ', MPAField.ALIGN_LEFT);
	public static final MPAField AMOUNT = 
			new MPAField("Amount", 80, 16);
	public static final MPAField ACCOUNT_BALANCE = 
			new MPAField("Balance after post", 96, 16, '0', MPAField.ALIGN_RIGHT);
	public static final MPAField POST_STATUS = 
			new MPAField("Status code", 112, 2);
	public static final MPAField STATUS_REASON = 
			new MPAField("Status message", 144, 30, ' ', MPAField.ALIGN_LEFT);
	public static final MPAField ACCT_DLOD = 
			new MPAField("Account daylight overdraft limit", 144, 16);
	public static final MPAField CONC_ACCT_BANK = 
			new MPAField("Concentration account bank", 160, 3);
	public static final MPAField CONC_ACCT_IDTYPE = 
			new MPAField("Concentration account type", 163, 1);
	
	public static final MPAField CONC_ACCT_ID = 
			new MPAField("Concentration account number", 164, 04);
	public static final MPAField LEDGER_BALANCE = 
			new MPAField("Ledger balance", 178, 16);
	public static final MPAField CHARGE_FLAG = 
			new MPAField("Debit or credit charge flag returned by host system", 194, 1);
	public static final MPAField DEPARTMENT = 
			new MPAField("Debit or credit department", 195, 10);
	public static final MPAField POSSIBLE_DUP_FLAG = 
			new MPAField("Possible duplicate flag", 96, 1);
	public static final MPAField FORCE_FLAG = 
			new MPAField("Force Flag", 97, 1);
	public static final MPAField TRAN_CODE1 = 
			new MPAField("Analysis tran code", 98, 10);
	public static final MPAField TRAN_CODE2 = 
			new MPAField("Analysis tran code", 108, 10);
	
	
	
	public static final MPAField CABLE_CHARGE = 
			new MPAField("Cable Charge", 128, 10);
	
	public static final MPAField NON_BALANCE_FORCE_FLAG = 
			new MPAField("Non balance force flag", 138, 1);
	
	public static final MPAField VALUE_DATE = 
			new MPAField("Transaction value date", 148, 6);
	
	public static final MPAField TRAN_TYPE = 
			new MPAField("Transaction type", 154, 3);
	
	public static final MPAField SRC_CODE = 
			new MPAField("Transaction Source code", 157, 3);
	
	public static final MPAField ADV_TYPE = 
			new MPAField("Advice Type", 160, 3);
	
	public static final MPAField WIRE_TYPE = 
			new MPAField("Wire Type", 163, 3);
	
	
	public static final MPAField DBT_DEPT = 
			new MPAField("Debit department code", 166, 10);
	
	public static final MPAField ACCT_ON_REL_FLG = 
			new MPAField("Not-on-file Flag", 186, 1);
	
	public static final MPAField REQ_ACCT_DLOD = 
			new MPAField("Request Msg - Account daylight overdraft limit", 187, 16);
	
	public static final MPAField GRP_ID = 
			new MPAField("Group identifier", 203, 10);
	
	public static final MPAField GRP_DLOD = 
			new MPAField("Group daylight overdraft", 213,16);
	
	public static final MPAField AFFILIATE_CODE = 
			new MPAField("Affiliate Code", 229, 3);
	
	public static final MPAField REQ_CONC_ACCT_BANK = 
			new MPAField("Request Msg - Concentration account bank", 232, 3);
	
	public static final MPAField REQ_CONC_ACCT_IDTYPE = 
			new MPAField("Request Msg - Concentration account type", 235, 1);
	
	public static final MPAField REQ_CONC_ACCT_ID = 
			new MPAField("Request Msg - Concentration account number", 236, 14);
	
}

