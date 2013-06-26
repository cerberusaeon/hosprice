package org.astrum.common.repository;

import java.util.List;

import org.astrum.common.domain.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {

	public List<Provider> findByAddressZipcode(String zipcode);
	
	public List<Provider> findByAddressState(String s);
	
	public Provider findByName(String name);
	
	public Provider findByLegacyId(Long legacyId);
	
	
	

}
