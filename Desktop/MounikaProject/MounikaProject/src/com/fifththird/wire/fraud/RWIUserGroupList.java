package com.fifththird.wire.fraud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class RWIUsergroupList{
	List<String> list = new LinkedList<String>();

	public RWIUsergroupList(	) {
		System.out.println("In RWI group method");
		File file1 = new File("/usr/local/intranet/areas/prod/bin/FraudAnalytics/lib/RWISafeUsergroups.txt");
		
	
	
	BufferedReader reader = null;
	try {
		reader = new BufferedReader(new FileReader(file1));
	}
	catch (FileNotFoundException e) {
		System.out.println("There was a problem opoening the RWISafeUsergroups.txt file");
		// TODO: handle exception
	}
	String text = null;
	try {
		while((text = reader.readLine()) != null) {
			text = text.trim();
			if(text.length() > 0) {
				list.add(text);
			}
		}
	}
	catch (IOException e1) {
		// TODO: handle exception
		System.out.println("An error occured while reading the RWISafeUsergroups.txt file");
		e1.printStackTrace();
	}
	}
	
	
	public Boolean isOnRWISafeUsergroupList(String usergroup) {
		String etrn = null;
		Boolean onList = false;
		usergroup = usergroup.trim();
		ListIterator li = list.listIterator();
		while(li.hasNext()) {
			etrn= (String) li.next();
			if(etrn.equals(usergroup)) {
				onList = true;
			}
		}
		return onList;
	}
	
};
