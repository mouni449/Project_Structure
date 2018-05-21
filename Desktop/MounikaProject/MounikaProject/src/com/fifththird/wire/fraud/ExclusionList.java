package com.fifththird.wire.fraud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ExclusionList {

	 List<String> list = new LinkedList<String>();

	  public ExclusionList() {
		  File filel = new File("/usr/local/intranet/areas/prod/bin/FraudAnalytics/lib/exclusionList.dat");
		  BufferedReader reader = null;
		  try {
			  reader = new BufferedReader(new FileReader(filel));
			  } 
		  	catch (FileNotFoundException e) {
				// TODO: handle exception	
		  		System.out.println("There was  problem opening the input file");
		  	}
		  String text = null, date, trn_num;

		  try {
			  while((text = reader.readLine()) != null) {
				  text = text.trim();
				  
				  if (text.length() >= 12) {
					  list.add(text);
				  }
				}

		  } catch (IOException e1) {

			  System.out.println(" error occured while reading the exclusion list—\n");

			  e1 . printStackTrace( );

		  }
	  }


	public String isOnExclusionlist(String trndate, String trn) {

	 String testTrn = "";

	 if (trndate.length() >= 10) { 

		 	testTrn = trndate.substring(0, 10);
	 	}

	 	testTrn = testTrn + ' ' + trn;

	 	String wrkTrn = trndate + trn;
	 	String etrn = null;
	 	String retTrn = "";

	 	ListIterator li = list.listIterator();
	 		
	 		 while(li.hasNext()) {
	 			 etrn = (String) li.next();
	 			 if (etrn.equals(testTrn)) {
	 				 retTrn = wrkTrn;

	 			 }
	 		 }
	 		 return retTrn;
	}
}