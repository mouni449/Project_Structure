package com.fifththird.wire.mpa;

public class DepositAccountInformationRequesterFactory {
	private static final String IMPLEMENTATION_CLASS_PROPERTY = "wire.depositinformationrequester.class";
	privatestatic DepositAccountInformationRequesterFactory instance = null;
	private DepositAccountInformationRequesterFactory(){}
	
	public static syncronized DepositAccountInformationRequesterFactory getInstance() {
		if(instance == null) {
			instance = new DepositAccountInformationrequesterFacorty();
		}
		return instance;
	}

	public DepositAccountInformationRequester createDepositAccountInformationRequester()
	throws ConfigurationException{
		String implementationClass = null;
		try{
			implementationClass = Wire.properties.getInstance().getProperty(
					IMPLEMENTATION_CLASS_PROPERTY);
			
			if (implementtionClass !=null) {
				return (DepositAccountInformationRequester) Class<T>.forName(ImplementationClass).newInstance();
			} else{
				throw new ConfiguatationException("The property"
				+IMPLEMENTATION_CLASS_PROPERTY
				+"is not set.");
				
			}
		}catch (InstantiationException e){
			throw new ConfigurationException(e);
		}catch(IllegalAccessEception e){
			throw new ConfigurationException (e);
		}catch(ClassNotFoundException e){
			throw new ConfigurationException("Theclass: " + implementationClass +"is not found", e);
		}
	}
	}
