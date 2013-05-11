package org.astrum.common.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.astrum.common.domain.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/applicationContext-hibernate.xml"})	
public class ProviderServiceTest {

	Logger logger = LoggerFactory.getLogger(ProviderServiceTest.class);
	
	@Inject
	ProviderService providerService;
	
	@Test
	public void testAllRepositoryFunctions(){
		
		logger.info("===========================================");
		Provider p = providerService.getProviderById(401L);
		assertNotNull(p);
		logger.info(p.toString());
		logger.info("===========================================");
		
		logger.info("===========================================");
		p = providerService.getProviderByLegacyId(10001L);
		assertNotNull(p);
		logger.info(p.toString());
		logger.info("===========================================");
		
		logger.info("===========================================");
		p = providerService.getProviderByName("SOUTHEAST ALABAMA MEDICAL CENTER");
		assertNotNull(p);
		logger.info(p.toString());
		logger.info("===========================================");
		
		logger.info("===========================================");
		List<Provider> ps = providerService.getProvidersByState("AL");
		assertTrue(ps.size() > 0);
		logger.info(ps.toString());
		logger.info("===========================================");
		
		logger.info("===========================================");
		ps = providerService.getProvidersByZipcode("36301");
		assertTrue(ps.size() > 0);
		logger.info(ps.toString());
		logger.info("===========================================");
		
		logger.info("===========================================");
		ps = providerService.findAll();
		assertTrue(ps.size() > 2);
		logger.info(ps.toString());
		logger.info("===========================================");
	}
}
