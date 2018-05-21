package com.fifththird.wire.mpa;

public class MPAField {

	public static final boolean ALIGN_LEFT = true;
	public static final boolean ALIGN_RIGHT = false;
	private final int startPosition;
	private final int length;
	private final String fieldName;
	private final char padCharacter;
	public final boolean side;
	
	
	
	public MPAField(String name, int start, int length) {
		this(name, start, length, '0', ALIGN_RIGHT);
	}

	public MPAField(String name, int start, int length, char padCharacter, boolean side) {
		this.length=length;
		this.startPosition = start;
		this.fieldName = name;
		this.padCharacter = padCharacter;
		this.side=side;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public int getLength() {
		return length;
	}

	public String getFieldName() {
		return fieldName;
	}

	public char getPadCharacter() {
		return padCharacter;
	}

	public String pad(String value) {
		// TODO Auto-generated method stub
		return null;
	}

}
