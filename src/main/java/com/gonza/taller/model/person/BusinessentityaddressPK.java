package com.gonza.taller.model.person;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the businessentityaddress database table.
 * 
 */
@Embeddable
public class BusinessentityaddressPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Integer businessentityid;

	@Column(insertable=false, updatable=false)
	private Integer addressid;

	@Column(insertable=false, updatable=false)
	private Integer addresstypeid;

	public BusinessentityaddressPK() {
	}
	public Integer getBusinessentityid() {
		return this.businessentityid;
	}
	public void setBusinessentityid(Integer businessentityid) {
		this.businessentityid = businessentityid;
	}
	public Integer getAddressid() {
		return this.addressid;
	}
	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}
	public Integer getAddresstypeid() {
		return this.addresstypeid;
	}
	public void setAddresstypeid(Integer addresstypeid) {
		this.addresstypeid = addresstypeid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BusinessentityaddressPK)) {
			return false;
		}
		BusinessentityaddressPK castOther = (BusinessentityaddressPK)other;
		return 
			this.businessentityid.equals(castOther.businessentityid)
			&& this.addressid.equals(castOther.addressid)
			&& this.addresstypeid.equals(castOther.addresstypeid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.businessentityid.hashCode();
		hash = hash * prime + this.addressid.hashCode();
		hash = hash * prime + this.addresstypeid.hashCode();
		
		return hash;
	}
}