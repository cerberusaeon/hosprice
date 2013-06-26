package org.astrum.services.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.astrum.common.domain.Provider;
import org.astrum.common.services.ProviderService;
import org.astrum.services.rest.model.ProviderModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Path("/providers")
public class ProviderRestService {

	Logger logger = LoggerFactory.getLogger(ProviderRestService.class);
	
	@Inject 
	ProviderService providerService;
	
	
	@Produces("application/json")
	@GET
	public Response getProviderByZipcode(@QueryParam("zipcode") String zipcode, @QueryParam("state") String state, @QueryParam("city") String city, @QueryParam("hospital") String hospital, @QueryParam("diagnosis") String diagnosis ){
		List<ProviderModel> pms = null;
		try{
			List <Provider> ps = providerService.getProviderByParams(zipcode, city, state);
			pms = ProviderModel.convertToModel(ps);
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("An error occurred while retreiving data.").build();
		}
		return Response.status(200).entity(pms).build();
	}
	
	
	
	
	@Path("/hospital")
	@Produces("application/json")
	@GET
	public Response getAllProviders (){
		List<ProviderModel> pms = null;
		try{
			List <Provider> ps = providerService.findAll();
			pms = ProviderModel.convertToModel(ps);
		}
		catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("An error occurred while retreiving data.").build();
		}
		return Response.status(200).entity(pms).build();
	}
	
	
	@Produces("application/json")
	@POST
	public Response postProvider(){
		
		return Response.status(200).entity("post provider").build();
	}
	
	
	
	@Path("/{providerId}")
	@Produces("application/json")
	@GET
	public Response getProviderById(@PathParam(value = "providerId") final String providerId){
		logger.info("getProviderById ");
		
		
		return Response.status(200).entity("get provider by Id").build();
	}
	
	@Path("/{providerId}")
	@Produces("application/json")
	@PUT
	public Response updateProviderId(@PathParam(value="providerId") final String providerId){
		logger.info("updateProviderById ");
		
		
		return Response.status(200).entity("updateProviderId").build();
		
	}
	@Path("/{providerId}")
	@Produces("application/json")
	@DELETE
	public Response deleteProvide(@PathParam(value="providerId") final String providerId){
		logger.info("deleteProviderById ");
		
		
		return Response.status(200).entity("deleteProvider").build();
	}
	
}
