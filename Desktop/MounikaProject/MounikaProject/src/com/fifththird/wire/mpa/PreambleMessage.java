package com.fifththird.wire.mpa;

public class PreambleMessage extends MPAMessage {

	public PreambleMessage(String message){
		super(message);
	}
	
	public boolean isPreambleMessage(){
		return true;
	}
	
	
	
	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static String createPreamble(String message){
		String preamble = intToHexString(message.length());
		return preamble;
	}
	
	private static String intToHexString(int convertThis){
		char digit2 = (char) (convertThis % 256);
		char digit1 = (char) ((convertThis -digit2) / 256);
		char result[] = {digit1, digit2};
		return new String(result);
	}

}
