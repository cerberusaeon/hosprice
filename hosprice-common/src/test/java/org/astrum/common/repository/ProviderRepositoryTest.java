package org.astrum.common.repository;

import static org.junit.Assert.*;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.astrum.common.domain.Address;
import org.astrum.common.domain.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:applicationContext-hibernate.xml" })
public class ProviderRepositoryTest {

	Logger logger = LoggerFactory.getLogger(Provider.class);
	
	@Inject 
	ProviderRepository providerRepository;
	
	@Inject
	AddressRepository addressRepository;
	
	@PersistenceUnit
	EntityManagerFactory entityManagerFactory;
	
	@Test
	public void getByParams(){
		String qZipcode="36301";
		String qCity = "DOTHAN";
		String qState = "AL";
		
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		  CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		  
		  CriteriaQuery<Provider> q = cb.createQuery(Provider.class);
		  Root<Provider> c = q.from(Provider.class);
		  
		  ParameterExpression<String> zipcode = cb.parameter(String.class);
		  ParameterExpression<String> city = cb.parameter(String.class);
		  ParameterExpression<String> state = cb.parameter(String.class);
		  q.select(c)
		  .where(cb.and(
				 cb.equal(c.get("address").get("zipcode"), zipcode),
				  cb.equal(c.get("address").get("city"), city),
				  cb.equal(c.get("address").get("state"), state)	
				  ));
		  
		  TypedQuery<Provider> query = entityManager.createQuery(q);
		  query.setParameter(zipcode, qZipcode);
		  query.setParameter(city, qCity );
		  query.setParameter(state, qState);
		  List<Provider> results = query.getResultList();
		  logger.info("results size: "+results.size());
		  
	}
	
	@Test
	public void testGetAddress(){
		Address a = addressRepository.findAddressByAddressId(201L);
		assertNotNull(a);
	}
	
}
