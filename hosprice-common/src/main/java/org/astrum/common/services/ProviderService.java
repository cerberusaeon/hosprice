package org.astrum.common.services;

import java.util.List;

import javax.inject.Inject;

import org.astrum.common.domain.Provider;
import org.astrum.common.repository.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

	
	@Inject
	ProviderRepository providerRepository;
	
	
	public List<Provider> getProvidersByZipcode(String zipcode){
		return providerRepository.findByAddressZipcode(zipcode);
	}
	
	public List<Provider> getProvidersByState(String state){
		return providerRepository.findByAddressState(state);
		
	}
	public Provider getProviderByName(String name){
		return providerRepository.findByName(name);
	}
	public Provider getProviderById(Long providerId){
		return providerRepository.findOne(providerId);
	}
	public Provider getProviderByLegacyId(Long providerId){
		return providerRepository.findByLegacyId(providerId);
	}
	
	public List<Provider> findAll(){
		return providerRepository.findAll();
	}
}
