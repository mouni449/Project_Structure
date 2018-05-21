package com.fifththird.wire.mpa;

import com.fifththird.wire.Account;

public interface DepositAccountInformationRequester {
	
	public DepositAccountInformation lookupDepositAccountInformation(Account account)
		throws AccountNotFoundException, HostCommunicationException,ConfigurationException;
	
	}

