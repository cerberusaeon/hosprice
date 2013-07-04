package org.astrum.common.repository;

import org.astrum.common.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

	public Address findAddressByAddressId(Long id);
}
