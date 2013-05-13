package org.astrum.services.rest.model;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DiagnosisRelatedGroupModel {

	private Long diagnosisRelatedGroupId;
	private String type;
	private BigDecimal totalDischarge;
	private BigDecimal averageCoveredCharges;
	private BigDecimal averageTotalPayments;

	@XmlElement
	public Long getDiagnosisRelatedGroupId() {
		return diagnosisRelatedGroupId;
	}
	@XmlElement
	public String getType() {
		return type;
	}
	@XmlElement
	public BigDecimal getTotalDischarge() {
		return totalDischarge;
	}
	@XmlElement
	public BigDecimal getAverageCoveredCharges() {
		return averageCoveredCharges;
	}
	@XmlElement
	public BigDecimal getAverageTotalPayments() {
		return averageTotalPayments;
	}
	public void setDiagnosisRelatedGroupId(Long diagnosisRelatedGroupId) {
		this.diagnosisRelatedGroupId = diagnosisRelatedGroupId;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setTotalDischarge(BigDecimal totalDischarge) {
		this.totalDischarge = totalDischarge;
	}
	public void setAverageCoveredCharges(BigDecimal averageCoveredCharges) {
		this.averageCoveredCharges = averageCoveredCharges;
	}
	public void setAverageTotalPayments(BigDecimal averageTotalPayments) {
		this.averageTotalPayments = averageTotalPayments;
	}

}
