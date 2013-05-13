package org.astrum.services.rest;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.astrum.common.domain.Person;
import org.astrum.common.domain.Provider;
import org.astrum.common.services.ProviderService;
import org.astrum.common.services.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext.xml", "/applicationContext-hibernate.xml","/applicationContext-services.xml"})	
public class UserRestServiceTest {
	
	protected static Logger logger = LoggerFactory.getLogger(UserRestServiceTest.class);	
	@Inject 
	UserService userService;
	
	@Inject
	ProviderService providerService;
	
	@Inject 
	ProviderRestService providerRestService;

	@Test
	public void tesLogin(){
		Person p = new Person();
		p.setDateOfBirth(new Date());
		p.setFirstName("Billy");
		p.setLastName("Thorton");
		
		userService.login(p);
		
	}
	
	@Test
	public void getData(){
		List<Provider> ps = providerService.findAll();
		logger.info(ps.toString());
		assertTrue(ps.size() > 0);
		
		Response r = providerRestService.getProviderByZipcode("36301");
		logger.info(r.getEntity().toString());
	}

}
