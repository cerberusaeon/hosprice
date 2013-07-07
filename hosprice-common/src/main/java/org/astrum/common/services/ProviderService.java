package org.astrum.common.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.astrum.common.domain.Provider;
import org.astrum.common.repository.ProviderRepository;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

	
	@Inject
	ProviderRepository providerRepository;
	
	@Inject
	EntityManagerFactory entityManagerFactory;
	
	
	public List<Provider> getProviderByParams(String qZipcode, String qCity, String qState){
		  EntityManager entityManager = entityManagerFactory.createEntityManager();
		  CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		  
		  CriteriaQuery<Provider> q = cb.createQuery(Provider.class);
		  Root<Provider> c = q.from(Provider.class);
		  
		  ParameterExpression<String> zipcode = cb.parameter(String.class);
		  ParameterExpression<String> city = cb.parameter(String.class);
		  ParameterExpression<String> state = cb.parameter(String.class);
		  q.select(c);
//		  .where(cb.and(
//				 cb.equal(c.get("address").get("zipcode"), zipcode),
//				  cb.equal(c.get("address").get("city"), city),
//				  cb.equal(c.get("address").get("state"), state)	
//				  ));
		  
		  //copied code 
		  List<Predicate> criteria = new ArrayList<Predicate>();

		    if (qZipcode != null && !qZipcode.isEmpty() ) {
		        ParameterExpression<String> p = cb.parameter(String.class, "zipcode");
		        criteria.add(cb.equal(c.get("address").get("zipcode"), p));
		    }
		    if (qCity!= null && !qCity.isEmpty()) {
		        ParameterExpression<String> p = cb.parameter(String.class, "city");
		        criteria.add(cb.equal(c.get("address").get("city"), p));
		    }
		    if (qState != null && !qState.isEmpty()) {
		        ParameterExpression<String> p = cb.parameter(String.class, "state");
		        criteria.add(cb.equal(c.get("address").get("state"), p));
		    }


		    if (criteria.size() == 0) {
		        throw new RuntimeException("no criteria");
		    } else if (criteria.size() == 1) {
		        q.where(criteria.get(0));
		    } else {
		        q.where(cb.and(criteria.toArray(new Predicate[0])));
		    }

		  
		  //end copied code
		  
		  TypedQuery<Provider> query = entityManager.createQuery(q);
		  
		  if(qZipcode != null && !qZipcode.isEmpty()){
			  query.setParameter("zipcode", qZipcode);  
		  }
		  if(qCity != null && !qCity.isEmpty()){
			  query.setParameter("city", qCity );
		  }
		  if(qState != null && !qState.isEmpty()){
			  query.setParameter("state", qState);
		  }
		  
		  
		
		return query.getResultList();
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
