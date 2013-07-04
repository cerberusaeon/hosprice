package org.astrum.common.etl;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.astrum.common.domain.Address;
import org.astrum.common.domain.Provider;
import org.astrum.common.domain.Region;
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
	
	public static Integer MAX_ROWS = 65500;  //max allowed by poi library
	
	@Test
	public void runTransfer() throws IOException{
		logger.info("Kick the tires and light the fires...");
		File file = new File("D:\\opt\\projects\\hosprice\\opt\\Medicare_Provider_Charge_2007.xls");
		
		FileInputStream fs = new FileInputStream(file);
		POIFSFileSystem poifs = new POIFSFileSystem(fs);
		HSSFWorkbook workbook = new HSSFWorkbook(poifs);
		
		Sheet sheet  = workbook.getSheet("Medicare_Provider_Charge_Inpati");
		
		Row firstRow = sheet.getRow(1);
		logger.info("MY ROW: "+firstRow.getCell(0));
		Provider[] list = new Provider[MAX_ROWS + 1];
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
			logger.info("Provider: "+p.toString());
			
			
			String street1 = r.getCell(PROVIDER_STREET_ADDRESS).getStringCellValue();
			Address a = new Address();
			a.setAddress1(street1);
			a.setCity(r.getCell(PROVIDER_CITY).getStringCellValue());
			a.setState(r.getCell(PROVIDER_STATE).getStringCellValue());
			Long tempZip = (long)(r.getCell(PROVIDER_ZIP).getNumericCellValue());
			a.setZipcode(tempZip.toString());
			logger.info(a.toString());
			
			Region region = new Region();
			String rawRegion = r.getCell(PROVIDER_REFERRAL_REGION).getStringCellValue();
			pattern = "([A-z]+)(\\s-\\s)([A-z]+)";
			String rState = rawRegion.replaceAll(pattern, "$1");
			String rName = rawRegion.replaceAll(pattern, "$3");
			
			region.setName(rName);
			region.setState(rState);
			logger.info(region.toString());
			
			
			list[i -1] = p;
		}
		
//		for(int i = 0; i < 24000; i++){
//			logger.info(i+":"+list[i].getName());
//		}
		
		logger.info("All done...."+sheet.getRow(sheet.getLastRowNum()).getCell(1));
	}
}
