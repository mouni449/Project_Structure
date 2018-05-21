package com.fifththird.wire.fraud;

import java.beans.Statement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.ConfigurationException;

public class QueryProcessorFTD {

	private static Boolean valid = true; 
	private static WireProperties props;

	public QueryProcessorFTD() {
		
	}

	public Boolean investigate(TranscationDetails trninfo){
	
		trninfo.setValid(!valid);
			if(props = null ){
				
				try { 
					props = WiredProperties.getInstances();
					} 
				
				catch (ConfigurationException e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("configuration exception when attempting to read wire.properties from QueryProcessorFTD.java");
					return (!valid);
				}
			}
	
			Boolean trusteer = false, oob = false, dualapp = false, bypasslist = false;
			Float limit = Float.valueOf(0);
			Float thresholdA = Float.valueOf(6);
			Float thresholdB = Float.valueOf(0);
			Float thresholdC = Float.valueOf(0);
			Float thresholdD = Float.valueOf(0);
			Float thresholdE = Float.valueOf(0);

			String thresholdF = "0" , thresholdH = "0", thresholdG = "0";
			String ftdDatabase = " ", ftdUser =  " ", ftdSchema = " ", ftdEncrypt =" ";
			trusteer = Boolean.parseBaoLean(props.getProperty("wire.fraud.transfer"));
			oob = Boolean.parseBoolean(props.getProperty("wire.fraud.oob"));
			dualapp = Boolean.parseBoolean(props.getProperty("wire.fraud.dualapp"));
			bypasslist = Boolean.parseBoolean(props.getProperty("wire.fraud.bypasslist"));

			limit = Float.valueOf(props.getProperty("wire.fraud.limit"));
			thresholdA = Float.valueOf(props.getProperty("wire.fraud.thresholdA"));
			thresholdB = Float.valueOf(props.getProperty("wire.fraud.thresholdB"));
			thresholdC = Float.valueOf(props.getProperty("wire.fraud.thresholdC"));
			thresholdD = Float.valueOf(props.getProperty("wire.fraud.thresholdD"));
			thresholdE = Float.valueOf(props.getProperty("wire.fraud.thresholdE"));
			thresholdF = props.getProperty("wire.fraud.thresholdF");
			thresholdG = props.getProperty("wire.fraud.thresholdG");
			thresholdH = props.getProperty("wire.fraud.thresholdH");



			ftdDatabase = props.getProperty("wire.fraud.ftd.database");
			ftdUser = props.getProperty("wire.fraud.ftd.userid");
			ftdSchema = props.getProperty("wire.fraud.ftd.schema");
			ftdEncrypt = props.getProperty("wire.fraud.ftd.encrypt");
			

			if ((!trusteer) && (!oob) && (!dualapp) && (thresholdF.compareTo("0") == 0) && (thresholdG.compareTo("0") == 0) && (thresholdH.compareTo("0") == 0)) {
				trninfo.setValid(!valid);
				System.out.println("Trn" + trninfo.getTrn() + "sent to RSK, oob and dualapp set to false, amd the RSA true");
				return(!valid);
				} 
		

			String account =trninfo.getDbt_id();
			if(!bypasslist)_
			{
				File filel = new File("/usr/local/intranet/areas/prod/bin/FraudAnalytics/lib/overrideLimits.dat"); 
				BufferedReader reader = null; 
				Matcher m;
				Pattern file = Pattern.compile("(\\d+)\\s(\\d+)"); 
			try { 
				reader = new BufferedReader(new FileReader(filel));
			}
				catch (FileNotFoundException e) {
					// TODO: handle exception
					System.out.println("There was a  problem in  opening the input file of override limits"); 
				}
			
			String text=null, date, trn_num;
			
				try { 
						while ((text = reader.readLine()) != null) { 
						text = text.trim(); 
						m = file.matcher(text); 
			
						if (m.find()) {
							if (account.length()>3 && account.equals(m.group(1))) {
								limit = Float.valueOf((m.group(2)));
								thresholdA = Float .valueOf((m.group(2)));
								thresholdB = Float.valueOf( (m.group(2) ) );
								thresholdC = Float.valueOf( (m.group(2) ) ) ;
								thresholdD = Float.valueOf( (m.group(2) ) ) ;
								thresholdE = Float.valueOf( (m.group(2) ) ) ;
								Float test = Float.valueOf( trninfo.getAmount()) ;
								System.out.println("Processing based on an overriden limit:" +limit +"amount:" +test);

							}
						} 
						
					}
				}
						
			catch (IOException e1) { 
			System.out.println("Trn " +trninfo.getTrn() +" sent to RSK. An error occured while reading the limit override list:" );

			return (!valid);

			}
		}
			
			
			
			int compare = Float.valueOf(trninfo.getAmount()).compareTo(limit);
			int compareBFX = Float.valueOf(trninfo.getAmount()).compareTo(thresholdE);

			if (trninfo.getSource().equals("BFX")) {
			
					if ((compareBFX < 0 ) && (dualapp)) {
							System.out.println("Trn"  +trninfo.getTrn() + " release is based upon: BFX under limit");
							return(valid);
					} 
					else {
							System.out.println("Trn " +trninfo.getTrn() +"sent to RSK. Above lomit or dualapp flag is not set ");
							return (!valid);
						}
					} 
					else if ((compare >= 0) && (trninfo.getSource().equals("FUN") ||
							trninfo.getSource().equals("FUS") || trninfo.getSource().equals("FF1"))){
						
							System.out.println("Trn" +trninfo.getTrn() +" sent to RSK. Over the lIMIit ");
							return (!valid);
					} 
					else if ((compare < 0) && (trninfo.getSource().equals("FUN") ||
							trninfo.getSource().equals("FUS") || trninfo.getSource().equals("FF1"))){
							Float f = new Float(trninfo.getAmount());

							String AASenderRef = trninfo.getSndr_ref().trim() ;
							if (AASenderRef.length() >= 5){
								AASenderRef = AASenderRef.substring(0,4).concat("-").concat(AASenderRef.substring(4, AASenderRef.length()));
								}
							
							String 	 AMPAPSenderRef= trninfo.getSndr_ref().trim(); 
							if ((AASenderRef.length() < 2)  || (AMPAPSenderRef.length() < 2)) {
								System.out.println("Trn " +trninfo.getTrn() + "sent to RSK. Insufficient sender ref for FTD lookup");
								return (!valid);
								}
			

							String jdbCURL = ftdDatabase;
							BufferedReader reader = null;
							String key="";

							try {
								reader = new BufferedReader(new FileReader("/usr/local/intranet/areas/prod/bin/FraudAnalytics/lib/password.txt")) ;
								key = reader.readLine( ) ;
								reader . close( ) ;
								} 
							catch (IOException e) {

								e.printStackTrace();
								System.out.println("An error occured in QueryProcessorFTD.java while attemptinf to read the FTD password key");
								return(!valid);
							}
			

						
						    Crypto crypto = new Crypto();
						    String dbPasswd = "";
						    try {
						    	dbPasswd = new String(crypto.decryptbase64 (key.toCharArray(), ftdEncrypt));  
						    }
						    	catch (GeneralSecurityException e4) {
									// TODO: handle exception
						    		e4.printStackTrace();
						    		System.out.println("An error occured in QueryProcessorFTD.java while attemptinf to read the FTD password ");
						    	}
							 

						    	Connection conn = null;
						    	Statement stmt= null;
						    	ResultSet rs = null;
						    	
						    	try {
						    		Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
						    	}
						    	catch(InstantiationException e) {
						    		e.printStackTrace() ;
						    		return(!valid);
						    	} 
						    	catch(IllegalAccessException e) {
						    		e.printStackTrace() ;
						    		return(!valid);
						    	} 
						    	catch(ClassNotFoundException e) {
						    		e.printStackTrace() ;
						    		return(!valid);
						    	} 

							 

							try {
								conn = DriverManager.getConnection(jdbCURL, ftdUser, dbPasswd);
								stmt = conn.createStatement();
								String query = "";

								if (trninfo.getSource().equals("FUN") || trninfo.getSource().equals("FUS")){ 
									query = "SELECT X_DUAL_CNTRL, X_TRUSTEER, X_OOB_CHLG " +
											"FROM" +ftdSchema +".TFTB_OOB " + "WHERE T_TRAN_ID = '" +AASenderRef +"'";

								} 
								else {
									query= "SELECT X_DUAL_CNTRL, X_TRUSTEER, X_OOB_CHLG , I_RSA_RSK_SCR" +
											"FROM" +ftdSchema +".TFTB_OOB " + ftdSchema + ".TFTB_FF_MTS_TRNLTN" +
											"WHERE T_FF_SRVRTID = T_TRAN_ID and I_MTS_SYSREFNCE = " +AMPAPSenderRef +"'";
								}
								
								
								System.out.println(query);
								rs=stmt.executeQuery(query);
								int resultCount = 0;
								Boolean accepted = false;
								String logline= "";
								int select = 0;
								
								while (rs.next()) {
								 resultCount++;
								 String d = rs.getString("X_DUAL_CNTRL");
								 String t= rs.getString("X_TRUSTEER");
								 String o = rs.getString("X_OOB_CHLG");
								 
								 String r = "1000";

								if (resultCount = 1) {
								logline = "fraud  Result: "  +trninfo.getTrn() + ", "  +trninfo.getAmount() +", " + d + ", "  +t;

								if (trninfo.getSource().equals("FFI")) {

									r = rs.getString("I_RSA_RSK_SCR");
									logline += ", " + r;

								Integer r2 = Integer.parseInt(r);

								Integer tf = Integer.parseInt(thresholdF);
								Integer tg = Integer.parseInt(thresholdG);
								Integer th = Integer.parseInt(thresholdH);

								int answer=r2.compareTo(tg);

								//DC 8 WI:

								if (dualapp && trusteer && d.equals("Y") && t.equals("Y") &&
										Float.valueOf(trninfo.getAmount()).compareTo(thresholdA) < 0 &&
										r2.compareTo(tf) < 0){
										accepted = true;
										select = 1;	
								} 
								else if (dualapp && d.equals("Y") &&
								Float.valueOf(trninfo.getAmount()).compareTo(thresholdB)) < 0 &&
								(r2.compareTo(tg) < 0)){
									accepted=true;
									select = 2;
								//Trusteeg;
								} 
								else if (trusteer && t.equals("Y") &&	
								(r2.compareTo(th) < 0)&&
								Float.valueOf(trninfo.getAmount()).compareTo(thresholdC) < 0) {
								accepted=true;
								select = 3;

								}

								//Account Mcess
								} else if ((trninfo.getSource().equals("FUN")) || (trninfo.getSource().equals("FUS"))) {

									logline += ", ?";
									
								
									if (dualapp && d.equals("Y") && Float.valueOf(trninfo.getAmount()).compareTo(thresholdD) < 0) {
										
									accepted = true;
									select = 4;

									}

								}
								else {
									//Invalid routing; there should be no other sources here
									accepted=false;

									}

									//Protect against latching null values
									if (AASenderRef.length() < 2) {
									accepted = false;

									}
									
									} 
								else { 
									//If there is lore than one Etch, then do not validate the gm
									accepted = false;

									logline = "Fraud Result:" +trninfo.getTrn() +", " +trninfo.getAmount() +", ?, ?, ? ";
									}
									}//End mile
									conn.close();
									
									logline += " , "+select;

									if (accepted) {
									logline += ", auto released ";
									System.out.println(logline);
									trninfo.setValid(valid);
									return(valid);

									} 
							
									else {
									if (resultCount == 0){
										logline = "Fraud Release : " +trninfo.getTrn() + " , " +trninfo.getAmount() + ", ?, ?, ? . ?, 0";
										
									}

									logline += " , sent to RSK ";
									System.out.println(logline) ;
									return (!valid);

								}
							}
							catch (SQLException e1) {
								// TODO: handle exception
								
								e1.printStackTrace();
								return(!valid);
							}
					}
						
							trninfo.setValid(!valid);
							return (!valid);
							



	}
			
}