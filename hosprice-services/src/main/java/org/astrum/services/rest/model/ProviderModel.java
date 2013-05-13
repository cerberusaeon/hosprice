package org.astrum.services.rest.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.astrum.common.domain.DiagnosisRelatedGroup;
import org.astrum.common.domain.Provider;
@XmlRootElement
public class ProviderModel implements java.io.Serializable {
	

	
	private static final long serialVersionUID = -4004940063655065413L;
	private Long providerId;
	private String name;
	private Long legacyId;
	private List<DiagnosisRelatedGroupModel> diagnosisRelatedGroupModels = new ArrayList<DiagnosisRelatedGroupModel>();
	
	//address fields
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String zipcode;
	
	//region fields
	private String regionName;
	private String regionState;
	
	@XmlElement
	public Long getProviderId() {
		return providerId;
	}
	@XmlElement
	public String getName() {
		return name;
	}
	@XmlElement
	public Long getLegacyId() {
		return legacyId;
	}
	@XmlElementWrapper
	public List<DiagnosisRelatedGroupModel> getDiagnosisRelatedGroupModels() {
		return diagnosisRelatedGroupModels;
	}
	@XmlElement
	public String getAddress1() {
		return address1;
	}
	@XmlElement
	public String getAddress2() {
		return address2;
	}
	@XmlElement
	public String getCity() {
		return city;
	}
	@XmlElement
	public String getState() {
		return state;
	}
	@XmlElement
	public String getZipcode() {
		return zipcode;
	}
	@XmlElement
	public String getRegionName() {
		return regionName;
	}
	@XmlElement
	public String getRegionState() {
		return regionState;
	}
	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLegacyId(Long legacyId) {
		this.legacyId = legacyId;
	}
	public void setDiagnosisRelatedGroupModels(
			List<DiagnosisRelatedGroupModel> diagnosisRelatedGroupModels) {
		this.diagnosisRelatedGroupModels = diagnosisRelatedGroupModels;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public void setRegionState(String regionState) {
		this.regionState = regionState;
	}
	
	public static List<ProviderModel> convertToModel(List<Provider> providers){
		List<ProviderModel> pms = new ArrayList<ProviderModel>();
		for(Provider p: providers){
			ProviderModel pm = new ProviderModel();
			
			for(DiagnosisRelatedGroup dm : p.getDiagnosisRelatedGroups()){
				DiagnosisRelatedGroupModel drgm = new DiagnosisRelatedGroupModel();
				drgm.setAverageCoveredCharges(dm.getAverageCoveredCharges());
				drgm.setAverageTotalPayments(dm.getAverageTotalPayments());
				drgm.setDiagnosisRelatedGroupId(dm.getDiagnosisRelatedGroupId());
				drgm.setTotalDischarge(dm.getTotalDischarge());
				drgm.setType(dm.getType());
				pm.getDiagnosisRelatedGroupModels().add(drgm);
			}
			
			if(p.getAddress()!= null){
				pm.address1 = p.getAddress().getAddress1();
				pm.address2 = p.getAddress().getAddress2();
				pm.city = p.getAddress().getCity();
				pm.state = p.getAddress().getState();
				pm.zipcode = p.getAddress().getZipcode();
			}
			pm.legacyId = p.getLegacyId();
			pm.name = p.getName();
			pm.providerId = p.getProviderId();
			pm.regionName = p.getRegion().getName();
			pm.regionState = p.getRegion().getState();
			pms.add(pm);
		}
		
		return pms;
	}
	@Override
	public String toString() {
		return "ProviderModel [providerId=" + providerId + ", name=" + name
				+ ", legacyId=" + legacyId + ", diagnosisRelatedGroupModels="
				+ diagnosisRelatedGroupModels + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", state="
				+ state + ", zipcode=" + zipcode + ", regionName=" + regionName
				+ ", regionState=" + regionState + "]";
	}

}
