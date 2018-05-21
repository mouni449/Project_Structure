package com.fifththird.wire.fraud;
import java.io.* ;

public class crypto {
	protected final log logger = LogFactory.getLog(getClass());
	protected final String method = "PBEWithMDSAndDES";
	protected byte[] salt = {
			(byte)0xc7, (byte)0x73,(byte)0x21, (byte)0x8c,
			(byte)0x7e, (byte)0xc8,(byte)0xee, (byte)0x99
	};
	protected int iteration = 20;
	
	public static void main(String[] args){
		Crypto crypto = new Crypto();
		try{
			System.out.print("enter encryption passord: ");
			java.lang.System.out.flush();
			char[] pass = crypto.readToCharArray(System.in);
			System.out.print("Enter value to encrypt: ");
			System.out.flush();
			byte[] value = crypto.readToByteArray(System.in);
			try{
				String enVal = crypto.encryptBase64(pass, value);
				System.out.println(encVal);
			}
			catch (GeneralSecurityException ex) {
				System.out.println("there was an unexcepted error while encrypting the value.");
				Array.fill(pass, ' ');
				Array.fill(value, (byte)0);
			}
			catch (IOException ex){
				System.out.println("there was an unexcepted error while retrieving the data.");
			}
		
		}
		public chat[] readToCharrray(IputStream in) throws IOException {
			char[] lineBuffer;
			char[] buf;
			int i;
			buf = lineBuffer = new char[128];
			
			int room = buf.length;
			int offset= 0;
			int c;
			
			loop: while (true) {
				switch(c = in.read()) {
				case -1:
				case '/n':
				  break loop;
				  
				case '\r':
					int c2 = in.read();
					if((c2 !='\n') && (c2 != -1)) {
						if(!(in instanceof PushbackInputStream)) {
							in = new PushBackInputStream(in);
						}
						((PushBackInputStream)in).unread(c2);
					} else
						break loop;
					default:
						if(--roon<0){
							buf = new char[offset +128];
							room = buf.length - offset -1;
							System.arraycopy(lineBuffer, 0,buf ,0, offset);
							java.util.Arrays.fill(lineBuffer, '');
							lineBuffer = buf;
						}
						buf[offset++] = (char) c;
						break;
				}
			}
			if (offset==0){
				return null;
			}
			char ret = new char [offset];
			System.arraycopy(buf, 0, ret, 0, offset);
			java.util.Arrays.fill(buf, '');
			return ret;
		}
		public byte[] readToByteArray(org.omg.CORBA_2_3.portable.InputStream in) throws IOException {
			byte[] linebuffer;
			byte[] buf;
			int i;
			
			buf = linebuffer = new byte[128];
			
			int room = buf.length;
			int offset = 0;
			int c;
			loop: while (true) {
				switch (c = in.read()){
				case -1:
				case '\n' :
					break loop;
					
				case '\r':
					int c2 = in.read();
					if ((c2 != '\n') && (c2 != -1)) {
						if(!(in instanceof PushBackInputSream)) {
							in = new PushBackInputStream(in)
							(PushBackInputStream)in.unread(c2);
						}else
							break loop;
						
						default: 
							if(--room <0){
								buf =new byte[offset + 128];
								room= buf.length - offset - 1;
								System.arraycopy(lineBuffer, 0, buf, 0, offset);
								java.util.Arrays.fill(lineBuffer, (byte)0);
								lineBuffer = buf;
							}
							buf[offset++] = (byte)c;
							break;
					}
				}
				if(offset == 0){
					return null;
				}
				byte[] ret =  new byte[offset];
				System.arraycopy(buf, 0, ret, 0, offset);
				java.util.Arrays.fill(buf, (byte)0);
				
				return ret;
			}
			public byte[] encrypt(char[] password, byte[]value)
			throws java.security.GeneralSecurityException{
				return _encrypt(password,value, javax.crypto.Cipher.DECRYPT_MODE);
			}
			public byte[] encrypt(char[] password, byte[] value)
			throws java.security.GeneralSecurityException{
				return _encrypt(password,value,javax.crypto.Cipher.ENCRYPT_MODE);
			}
			public byte[]decrypt Base64(char[] password, String value)
			throws java.security.GeneralSecurityException{
				return decrypt(password, com.sun.org.apache.xml.internal.security.utils.Base64.decode(value));
			}
			public String encryptBase64(char[] password, byte[] value)
			throws java.security.GeneralSecurityException{
				return Base64.encodeBytes(encrypt(password,value));
			}
			protected byte[] _encrypt(char[] password, byte[] value, int mode)
			throws java.security.GeneralSecurityException {
				PBEKeySpec pbeKeySpec;
				PBEParameterSpec pbePara,Spec;
				SecretKeyFactory keyFac;
				pbeParamSpec = new PBEParameterSpec(salt, iteration);
				pbeKeySpec = new PBEKeySpec(password);
				try{
					KeyFac = SecretKeyFactory.getInstance(method);
					SecretKey pbeKey = KeyFac.generateSecret(pbeKeySpec);
					
					Cipher pbeCipher = javax.crypto.Cipher.getInstance(method);
					pbeCipher.inti(mode,pbeKey,PbeParamSpec);
					
					protected byte[] _encrypt(char[] password,byte[] value, int mode)
					throws java.security.GeneralSecurityException {
						PBEKeySpec pbeKeySpec;
						PBEParameterDpec pbeParamSpec;
						javax.crypto.SecretKeyFactory KeyFac;
						
						pbeParamSpec = new PBEParameterSpec(salt, iteration);
						try{
							keyfac = javax.crypto.SecretKeyFactory.getInstance(method);
							SecretKey pbeKey = KeyFac.generateSecret(pbeKeySpec);
							
							Cipher pbeCipher = javax.crypto.Cipher.getInstance(method);
							pbeCipher.iniy(mode,pbeKey,pbeParamSpec);
							
							byte[] cipherText = pbeCipher.doFinal(clearText);
							return ciphertext;
						}
						catch(java.security.Genera;SecurityException ex){
							logger.error("There was an error during encryption/decryption.");
							logger.error("error caused by:"+ex.getMessage(),ex);
						}
					}
					public void setIteration(int i){
						ietration = i;
					}
					public void setSalt(byte[] bs) {
						salt = bs;
					}
				
				
								
							
							
							
						}
					}
				}
		}
}