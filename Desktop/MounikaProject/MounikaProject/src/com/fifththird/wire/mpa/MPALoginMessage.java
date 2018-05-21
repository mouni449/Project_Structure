package com.fifththird.wire.mpa; 


/** * MPALoginMessage implements the request/response paradigm required to 
 * * successfully log in to the MPA client. 

* From MPA documentation Chapter 4 page 10 (October 2000 version): 
* 
* The LAST HOST_TRN, LAST INTRANETTRN, LASTINTRANETTRN SUFFIX, and 
* LASTINTiANET REVERSE_FLAG are from the last TRN that the host successfully 
* recieved and posted. A successful post is one that causes the host to return 
* a POST STATUS of 00 in the WA/Credit Post response record. 
* 
* The fields LASTINTRANET_TRN, LASTINTRANETTRN.SUFFIX, and LAST_INTRANET 
* * REVERSE_FLAG are used to check for possible duplicate messages; they are 
* * required only if you use MPA to resolve possible duplicate messages. 
* * Possible duplicate checking is configured in the RMT function by setting the 
* * parameter LAST_TRN.INFO.SENT to Y. 
*/

public class MPALoginMessage extends MPAMessage { 
	/** * Request only. 
	 * From the Remote File */ 
	public static final MPAField LOGIN_ID = new MPAField("Login ID", 8, 8);

	/** Request only. From the Remote File */
	public static final MPAField LOGIN_PASSWORD = new MPAField("Login Password", 16, 8); 
	/** * Request only. 
* Password changed automatically by the MIS at intervals defined in the * Remote File */ 
	
	public static final MPAField NEW_PASSWORD = new MPAField("New Password", 24, 8); 
	
	public static final MPAField RESPONSE_CODE = new MPAField("Response Code", 8, 3); 
	
	public static final MPAField LAST_HOST_TRN = new MPAField("Last Host Transaction", 11, 20); 

	public static final MPAField LAST_INTRANET_TRN = new MPAField("last Intranet Transaction", 31, 12);

	public static final MPAField LAST_INTRANET_TRN_SUFFIX = new MPAField("Last Intranet Transaction Suffix", 43, 7);
	
	public static final MPAField LAST_INTRANET_REVERSE_FLAG = new MPAField("Last Intranet Reverse =lag", 50, 1); 

	private static final String RESPONSE_MESSAGE = "MT05 ACK 0000000000000000000";
	
	public MPALoginMessage(String message) 
	{ 
		super(message); 
		} 
	
	public String process(){
		return RESPONSE_MESSAGE;
	}
	
}
