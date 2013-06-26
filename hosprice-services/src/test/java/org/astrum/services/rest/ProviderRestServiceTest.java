package org.astrum.services.rest;

import javax.inject.Inject;

import org.astrum.common.domain.Provider;
import org.astrum.common.services.ProviderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/applicationContext-hibernate.xml","/applicationContext-services.xml"})	
public class ProviderRestServiceTest {

	
	protected static Logger logger = LoggerFactory.getLogger(UserRestServiceTest.class);	
	
	@Inject
	ProviderService providerService;
	
	@Inject 
	ProviderRestService providerRestService;

	@Test
	public void tesLogin(){
		logger.info("ok");
		
		
	}

}
