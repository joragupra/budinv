package com.joragupra.budinv.dto;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

public class LedgerTransformer {
	
	private static DozerBeanMapper mapper;
	
	static {
		List myMappingFiles = new ArrayList();
		myMappingFiles.add("bookkeepingentry-mapping.xml");
		mapper = new DozerBeanMapper();
		mapper.setMappingFiles(myMappingFiles);
	}
	
	public static com.joragupra.budinv.domain.Ledger dtoToDomain(com.joragupra.budinv.dto.Ledger ledger){
		return mapper.map(ledger, com.joragupra.budinv.domain.Ledger.class);
	}
	
	public static com.joragupra.budinv.dto.Ledger domainToDto(com.joragupra.budinv.domain.Ledger ledger){
		return mapper.map(ledger, com.joragupra.budinv.dto.Ledger.class);
	}
}
