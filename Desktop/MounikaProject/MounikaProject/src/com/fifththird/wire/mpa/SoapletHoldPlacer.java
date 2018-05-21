package com.fifththird.wire.mpa;
import java.io.BufferedReader;
import java.io.FilerReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.Map;
import org.codehaus.soaplet.XPathSoaplet;
import com.fifththird.wire.*;
import com.fifththird.wire.fraud.*;


public class SoapletHoldPlacer implements HoldPlacer  {
	private static WireProperties props;
	private static final String Template_NAME = "HoldsAlertsStopsUpdateRequest.vm";
	
	
	public HoldResult placeTellerHold(
			final String afflicate,
			final String accountNumber,
			final String AccountType,
			final BigDecimal amount,
			final String forceFlag)
	throws HoldCommunicationException, InsufficientFundsException, ConfigurationException{
		if (props == null){
			props = WireProperties.getInstance();
		}
		final HoldResult holdResult = new HoldResult();
		
		XPathSoaplet soaplet = new XPathSoaplet(){
			
			public String getRequestTemplateLasteName(){
				return TEMPLATE_NAme;
			}
			String ftdEncrypt = props.hetProperty("wire.leprecchaun.password.encrypt");
			String raftPAth= props.getProperty("wire.leprecchaun.password.location");
			BufferedReader reader = null;
			String key=**, raftPasswd="";
			
			try{
				reader =new bufferedReader(new FileReader(raftPath));
				key = reader.readLine();
				reader.close();
			}
			catch(IOException e3){
				e3.printStackTrace();
				System.out.println("an error occured while attempting to read the raft password.");
			}
			Crypto crypto= new Crypto();
			try{
				raftPasswd =new String(Crypto.decryptBase64(key.toCharArray(), ftdEncrypt));
			}catch(GeneralSecurityException e4){
				e4.printStackTrace();
			System.out.println("an error occured while attempting to decrypt the RAFT password.");
			}
			context.put("credentialId",props.getProperty("wire.leprecchaun.user.id"));
			context.put("credentialPwd", raftPasswd));
			context.put("credentialCostCenter", props.getProperty("wire.costcenter"));
			context.put("credentialIdAfflicate",props.getProperty("wire.afflicate"));
			context.put("accountNumber", accountNumber.trim() );
			context.put("fileType", AccountType.getApplicationCode() );
			context.put("acronym", afflicate));
			context.put("amount",amount);
			context.put("forceFlag",forceFlag);
		}
		public void processResponse(Map response) throws Exception{
			holdresult.setMessageCode( (String) response.get(messageCode") );"
			holdresult.setReturncode( Integer.parseInt(String) response.get("returnCode"));
			
			System.out.println("DACA SoapletHoldPlacer -response");
			System.out.println(response);
			System.out.println(forceFlag);
		}
		public String getSOAPAction()
		{
			return "HoldsAlertsStopsUpdateRequest";
		}
		public void registerExpressions()
		{
			selectNode("returnCode","//HoldsAlertsStopsUpdateResponse/result/returnCode");
			selectNode("messageCode", "//HoldsAlertsStopsUpdateResponse/result/messageCode");
		}
		public void processFault(
				String faultCode,
				String FaultString,
				String actor;
				String detail;
				throws Exception{
					throw new HostCommunicationException("Exception from Host:" 
							+ faultCode +faultString);
				}
	}
	 try{
		 String ftdEncrypt = props.getProperty("wire.host.password.encrypt");
		 BufferedReader reader = null;
		 String key="" , hiPasswd= "" ;
		 try{
			 reader = new BufferedReader( New FileReader("/usr/local/intranet/areas/prod/bin/libMPABroker/lib/HIPassword.txt"));
			 key= reader.readline();
			 Reader.close();
		 }
		 catch (IOException e3){
			 e3.printStackTrace();
			 System.out.println("an error occured while attempting to read the HI password key.");
		 }
		 Crypto crypto = new Crypto();
		 try {
			 hipasswd = new String(crypto.decryptBase64(key.tocharArray(), ftdEncrypt));
		 }catch (GeneralSecurityException e4) {
			 e4.printStackTrace();
			 System.out.println("an error occured while attempting decrypt to HI password ");
		 }
		 soaplet.setUserID(props.getProperty("wire.host.userID"));
		 soaptlet.setPassword(hiPasswd);
		 soaplet.invoke(props.getProperty("wire.soapurl"));
	 }catch (HostCommunicationException e){
		 throw e;
	 } catch (Exception e){
		 throw new HostCommunicationException("Error invoking remote call", e);
	 }
	 return holdresult;
}
}
		 
					
					
			
			
			
			}
		}
	}

}
