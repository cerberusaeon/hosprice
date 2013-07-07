package org.astrum.common.etl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.astrum.common.domain.Address;
import org.astrum.common.domain.DiagnosisRelatedGroup;
import org.astrum.common.domain.Provider;
import org.astrum.common.domain.Region;
import org.astrum.common.repository.DiagnosisRelatedGroupRepository;
import org.astrum.common.repository.ProviderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml",
		"classpath:applicationContext-hibernate.xml" })
public class TransferJob {

	
	protected static Logger logger = LoggerFactory.getLogger(TransferJob.class);
	
	public static Integer STARTING_DATA_ROW = 1;
	public static Integer DRG_DEFINITION = 0;
	public static Integer PROVIDER_ID = 1;
	public static Integer PROVIDER_NAME = 2;
	public static Integer PROVIDER_STREET_ADDRESS = 3;
	public static Integer PROVIDER_CITY = 4;
	public static Integer PROVIDER_STATE = 5;
	public static Integer PROVIDER_ZIP = 6;
	public static Integer PROVIDER_REFERRAL_REGION = 7;
	public static Integer TOTAL_DISCHARGES = 8;
	public static Integer AVERAGE_COVERED_CHARGES = 9;
	public static Integer AVERAGE_TOTAL_PAYMENTS = 10;
	
	public static Integer MAX_ROWS = 43066 + STARTING_DATA_ROW;  //max allowed by poi library
	
	@Inject
	ProviderRepository providerRepository;
	
	@Inject
	DiagnosisRelatedGroupRepository diagnosisRelatedGroupRepository;
	
	
	@Test
	public void blankTest(){
		logger.info("junit test");
	}
	/**
	 * This was for ETL to populate data with excel sheet, do not run this test!
	 * @throws IOException
	 */
	public void runTransfer() throws IOException{
		Date start = new Date();
		logger.info("Kick the tires and light the fires...");
		File file = new File("D:\\opt\\projects\\hosprice\\opt\\Medicare_Provider_Charge_2007.xls");
		
		FileInputStream fs = new FileInputStream(file);
		POIFSFileSystem poifs = new POIFSFileSystem(fs);
		HSSFWorkbook workbook = new HSSFWorkbook(poifs);
		
		Sheet sheet  = workbook.getSheet("Medicare_Provider_Charge_03");
		
		Row firstRow = sheet.getRow(1);
		logger.info("MY ROW: "+firstRow.getCell(0));
		Provider[] list = new Provider[MAX_ROWS + 1];
		HashMap<Long, Provider> providerMap = new HashMap<Long,Provider>();
		//retrieve all providers
		List<Provider> providersFromDb = providerRepository.findAll();
		for(Provider dbp: providersFromDb){
			providerMap.put(dbp.getLegacyId(), dbp);
		}
		
		int foundNewInstance = 0;
		int mergingNewInstance = 0;
		for(int i = STARTING_DATA_ROW;i <= MAX_ROWS; i++){
			Row r = sheet.getRow(i);
			Provider p = new Provider();
			
			String drgDefinition =r.getCell(DRG_DEFINITION).getStringCellValue(); 
			String pattern = "([0-9]+)(\\s-\\s)(.+)";
			drgDefinition = drgDefinition.replaceAll(pattern, "$3");
			
			Double legacyProviderId = r.getCell(PROVIDER_ID).getNumericCellValue();
			Long tempLegacyProviderId = (long)(legacyProviderId.doubleValue());
			String providerName = r.getCell(PROVIDER_NAME).getStringCellValue();
			p.setLegacyId(tempLegacyProviderId);
			p.setName(providerName);
			//logger.info("Provider: "+p.toString());
			
			
			String street1 = r.getCell(PROVIDER_STREET_ADDRESS).getStringCellValue();
			Address a = new Address();
			a.setAddress1(street1);
			a.setCity(r.getCell(PROVIDER_CITY).getStringCellValue());
			a.setState(r.getCell(PROVIDER_STATE).getStringCellValue());
			Long tempZip = (long)(r.getCell(PROVIDER_ZIP).getNumericCellValue());
			a.setZipcode(tempZip.toString());
			//logger.info(a.toString());
			
			Region region = new Region();
			String rawRegion = r.getCell(PROVIDER_REFERRAL_REGION).getStringCellValue();
			pattern = "([A-z]+)(\\s-\\s)([A-z]+)";
			String rState = rawRegion.replaceAll(pattern, "$1");
			String rName = rawRegion.replaceAll(pattern, "$3");
			
			region.setName(rName);
			region.setState(rState);
			//logger.info(region.toString());
			
			DiagnosisRelatedGroup drg = new DiagnosisRelatedGroup();
			
			Double averageCoveredCharges = r.getCell(AVERAGE_COVERED_CHARGES).getNumericCellValue();
			Double averageTotalPayments = r.getCell(AVERAGE_TOTAL_PAYMENTS).getNumericCellValue();
			Double totalDischarges = r.getCell(TOTAL_DISCHARGES).getNumericCellValue();
			
			
			drg.setType(drgDefinition);
			drg.setAverageCoveredCharges(new BigDecimal(averageCoveredCharges));
			drg.setAverageTotalPayments(new BigDecimal(averageTotalPayments));
			drg.setTotalDischarge(new BigDecimal(totalDischarges));
			
			
			p.setAddress(a);
			p.getDiagnosisRelatedGroups().add(drg);
			p.setRegion(region);
			
			Provider matchingProvider = providerMap.get(p.getLegacyId());
			if(matchingProvider == null){
				providerMap.put(p.getLegacyId(),p);
				foundNewInstance++;
			}
			else{
				matchingProvider.getDiagnosisRelatedGroups().addAll(p.getDiagnosisRelatedGroups());
				mergingNewInstance++;
			}
			
			list[i -1] = p;
		}
		
		Collection<Provider> c = providerMap.values();
		for(Provider p: c){
			logger.info(p.toString());
			providerRepository.save(p);
			for(DiagnosisRelatedGroup ddd: p.getDiagnosisRelatedGroups()){
				ddd.setProvider(p);
				diagnosisRelatedGroupRepository.save(ddd);
			}
		}
		logger.info("size of collection: "+c.size());
		logger.info("mergedENtities: "+mergingNewInstance);
		logger.info("found new instance: "+foundNewInstance);
		logger.info("All done...."+sheet.getRow(sheet.getLastRowNum()).getCell(1));
		List<Provider> providerByZip = providerRepository.findByAddressZipcode("36116");
		if(providerByZip.size() > 0){
			logger.info("FOUND MY PROVIDER: "+providerByZip.get(0));
		}
		else{
			logger.info("TOTALLY DIDN't FIND THE PROVIDER");
		}
		
		List<Provider> unlimitedProviders = providerRepository.findAll();
		logger.info("example provider: "+unlimitedProviders.get(0));
		logger.info("total number of providers in database: "+unlimitedProviders.size());
		Date end = new Date();
		Long millis = end.getTime() - start.getTime();
		String myTime = String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
			);
		logger.info("Total time is: "+myTime);
	}


}
