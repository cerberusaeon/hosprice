package org.astrum.common.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "PROVIDER")
public class Provider implements java.io.Serializable {

	
	private static final long serialVersionUID = -6566062720030448612L;
	private Long providerId;
	private String name;
	private Long legacyId;
	private List<DiagnosisRelatedGroup> diagnosisRelatedGroups = new ArrayList<DiagnosisRelatedGroup>();
	private Address address;
	private Region region;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getProviderId() {
		return providerId;
	}
	public String getName() {
		return name;
	}
	public Long getLegacyId() {
		return legacyId;
	}
	@OneToMany(mappedBy="provider",fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	public List<DiagnosisRelatedGroup> getDiagnosisRelatedGroups() {
		return diagnosisRelatedGroups;
	}
	@OneToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="addressId")
	public Address getAddress() {
		return address;
	}
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="regionId")
	public Region getRegion() {
		return region;
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
	public void setDiagnosisRelatedGroups(
			List<DiagnosisRelatedGroup> diagnosisRelatedGroups) {
		this.diagnosisRelatedGroups = diagnosisRelatedGroups;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime
				* result
				+ ((diagnosisRelatedGroups == null) ? 0
						: diagnosisRelatedGroups.hashCode());
		result = prime * result
				+ ((legacyId == null) ? 0 : legacyId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Provider other = (Provider) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Provider [providerId=" + providerId + ", name=" + name
				+ ", legacyId=" + legacyId + ", diagnosisRelatedGroups="
				+ diagnosisRelatedGroups + ", address=" + address + ", region="
				+ region + "]";
	}
	
}
