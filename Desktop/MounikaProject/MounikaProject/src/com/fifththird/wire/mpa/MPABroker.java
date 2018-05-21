package com.fifththird.wire.mpa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MPABroker{ 
	
	private static final String USAGE = "\n\nUsage: java com.fifththird.wire.MPABroker port \n"; 
	private ServerSocket socket; 
	private BufferedReader reader; 
	private InputStreamReader inreader; 
	private InputStream instream; 
	private Socket mySocket; 
	private OutputStream outstream; 
	private OutputStreamWriter outwriter; 
	private BufferedWriter writer; 
	private final int portNumber;

	private Boolean trusteer; 
	private Boolean oob; 
	private Boolean dualapp; 
	private Boolean bypasslist; 
	private float limit;



	public static void main(String[] args) {
		validateArguments(args);
		int portNumber = Integer.parseInt(args[0]);
		MPABroker broker = new MPABroker(portNumber); 
		broker.listen();
	}
	

	private static void validateArguments(String[] args) {

		if( args.length != 1 ){
			System.err.println("You must provide a port number." + USAGE);
			System.exit(-1); 
		}else{ 
			try{
				Integer.parseInt(args[0]); 
			}catch(NumberFormatException e) {
				System.err.println("Port number must be numeric." + USAGE); 
				System.exit(-2);
			}
		}
	}
	
	public MPABroker(int portNumber){
		this.portNumber = portNumber;
	}

	public void listen() { 
		try {
			System.out.println( "Listening: "+portNumber );
			initialize();
			while(true){
				handle();
			}
		} catch (IOException e) {
			e.printStackTrace(); 
		}finally{
			if( writer != null ) try { writer.close(); writer = null;} catch (Exception e) {} 
			if( outwriter != null ) try { outwriter.close(); outwriter = null;} catch (Exception e) {} 
			if( outstream != null ) try { outstream.close(); outstream = null;} catch (Exception e) {}
			if( reader != null ) try { reader.close(); reader = null;} catch (Exception e) {} 
			if( inreader != null) try { inreader.close(); inreader = null;} catch (Exception e) {}
			if( instream != null ) try { instream.close();instream = null;} catch (Exception e) {} 
			if( mySocket != null ) try { mySocket.close(); mySocket = null; } catch (Exception e) {} 
			if( socket != null ) try { socket.close(); socket = null;} catch (Exception e) {}
		}
	}

	private void initialize() throws IOException {
		socket = new ServerSocket(portNumber);
		mySocket = socket.accept(); 
		instream = mySocket.getInputStream(); 
		inreader = new InputStreamReader(instream); 
		reader = new BufferedReader (inreader);
		outstream = mySocket.getOutputStream(); 
		outwriter = new OutputStreamWriter(outstream); 
		writer = new BufferedWriter(outwriter);
	}

	private final int PREAMBLE_LENGTH=2;

	private void handle() throws IOException{

		String preambleHex = readSocket(PREAMBLE_LENGTH);
		int preambleContents = preambleHex.charAt( 9 ) * 256 + preambleHex.charAt( 1 ); 
		String requestMessage = readSocket(preambleContents);
		System.out.println("MPABroker request message "+requestMessage);
		MPAMessage message = MPAMessage.newInstance(requestMessage);

		String responseMessage = message.process(); 
		String preambleMessage = PreambleMessage.createPreamble(responseMessage); 
		writer.write(preambleMessage); 
		writer.flush(); 
		writer.write(responseMessage); 
		writer.flush(); 
		System.out.println("MPABroker response message "+responseMessage);
	}

	private String readSocket(int messageLength) throws IOException{

		char[] requestChars = new char[messageLength];	
		StringBuffer results = new StringBuffer();
		int charsLeft = messageLength; 
		int charsRead = 0; 
		boolean charsExpected = true; 
		while (charsExpected){ 
				charsRead = reader.read(requestChars );
				if ((charsLeft - charsRead) > 0){ 
					charsLeft = charsLeft - charsRead; 
				}else{
					charsExpected = false;
				}
				results.append(requestChars, 0, charsRead );
		}
		return results.toString();
	}
}






