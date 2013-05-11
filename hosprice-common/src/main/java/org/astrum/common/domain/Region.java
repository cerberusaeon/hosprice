package org.astrum.common.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/***
 * 
 * @author cerberus
 * Hospital Referral region
 */
@Entity 
@Table( name = "REGION")
public class Region implements java.io.Serializable {

	
	private static final long serialVersionUID = -4481860452696434919L;
	
	private Long regionId;
	private String name;
	private String state;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getRegionId() {
		return regionId;
	}
	public String getName() {
		return name;
	}
	public String getState() {
		return state;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Region other = (Region) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Region [regionId=" + regionId + ", name=" + name + ", state="
				+ state + "]";
	}
	
	
}
