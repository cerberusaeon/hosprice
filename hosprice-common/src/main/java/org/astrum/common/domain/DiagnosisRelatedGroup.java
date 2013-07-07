package org.astrum.common.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "DIAGNOSIS_RELATED_GROUP")
public class DiagnosisRelatedGroup implements java.io.Serializable {

	
	
	private static final long serialVersionUID = -2183989461061672911L;
	private Long diagnosisRelatedGroupId;
	private String type;
	private Provider providerId;
	private BigDecimal totalDischarge;
	private BigDecimal averageCoveredCharges;
	private BigDecimal averageTotalPayments;
	//private List<Provider> providers;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getDiagnosisRelatedGroupId() {
		return diagnosisRelatedGroupId;
	}
	@Column(name="DRM_TYPE")
	public String getType() {
		return type;
	}
//	@OneToMany
//	public List<Provider> getProviders() {
//		return providers;
//	}
	public BigDecimal getTotalDischarge() {
		return totalDischarge;
	}
	public BigDecimal getAverageCoveredCharges() {
		return averageCoveredCharges;
	}
	public BigDecimal getAverageTotalPayments() {
		return averageTotalPayments;
	}
	public void setDiagnosisRelatedGroupId(Long diagnosisRelatedGroupId) {
		this.diagnosisRelatedGroupId = diagnosisRelatedGroupId;
	}
	public void setType(String type) {
		this.type = type;
	}
//	public void setProviders(List<Provider> providers) {
//		this.providers = providers;
//	}
	public void setTotalDischarge(BigDecimal totalDischarge) {
		this.totalDischarge = totalDischarge;
	}
	public void setAverageCoveredCharges(BigDecimal averageCoveredCharges) {
		this.averageCoveredCharges = averageCoveredCharges;
	}
	public void setAverageTotalPayments(BigDecimal averageTotalPayments) {
		this.averageTotalPayments = averageTotalPayments;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="providerId")
	public Provider getProvider() {
		return providerId;
	}
	public void setProvider(Provider providerId) {
		this.providerId = providerId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((averageCoveredCharges == null) ? 0 : averageCoveredCharges
						.hashCode());
		result = prime
				* result
				+ ((averageTotalPayments == null) ? 0 : averageTotalPayments
						.hashCode());
		result = prime
				* result
				+ ((diagnosisRelatedGroupId == null) ? 0
						: diagnosisRelatedGroupId.hashCode());
		result = prime * result
				+ ((totalDischarge == null) ? 0 : totalDischarge.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		DiagnosisRelatedGroup other = (DiagnosisRelatedGroup) obj;
		if (averageCoveredCharges == null) {
			if (other.averageCoveredCharges != null)
				return false;
		} else if (!averageCoveredCharges.equals(other.averageCoveredCharges))
			return false;
		if (averageTotalPayments == null) {
			if (other.averageTotalPayments != null)
				return false;
		} else if (!averageTotalPayments.equals(other.averageTotalPayments))
			return false;
		if (diagnosisRelatedGroupId == null) {
			if (other.diagnosisRelatedGroupId != null)
				return false;
		} else if (!diagnosisRelatedGroupId
				.equals(other.diagnosisRelatedGroupId))
			return false;
		if (totalDischarge == null) {
			if (other.totalDischarge != null)
				return false;
		} else if (!totalDischarge.equals(other.totalDischarge))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "DiagnosisRelatedGroup [diagnosisRelatedGroupId="
				+ diagnosisRelatedGroupId + ", type=" + type
				+ ", totalDischarge=" + totalDischarge
				+ ", averageCoveredCharges=" + averageCoveredCharges
				+ ", averageTotalPayments=" + averageTotalPayments + "]";
	}
	
	
}
