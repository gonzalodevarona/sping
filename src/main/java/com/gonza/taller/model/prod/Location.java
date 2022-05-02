package com.gonza.taller.model.prod;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the location database table.
 *
 */
@Entity
@NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l")
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "LOCATION_LOCATIONID_GENERATOR", allocationSize = 1, sequenceName = "LOCATION_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATION_LOCATIONID_GENERATOR")
	private Integer locationid;
	
	@NotNull
	@DecimalMin(value = "1.0")
	@DecimalMax(value = "10.0")
	private BigDecimal availability;
	
	@NotNull
	@DecimalMin(value = "0.0")
	@DecimalMax(value = "1.0")
	private BigDecimal costrate;

	private LocalDate modifieddate;
	
	@NotBlank
	@Size(min = 5)
	private String name;

	// bi-directional many-to-one association to Productinventory
	@OneToMany(mappedBy = "location")
	private List<Productinventory> productinventories;

	// bi-directional many-to-one association to Workorderrouting
	@OneToMany(mappedBy = "location")
	private List<Workorderrouting> workorderroutings;

	public Location() {
	}

	public Productinventory addProductinventory(Productinventory productinventory) {
		getProductinventories().add(productinventory);
		productinventory.setLocation(this);

		return productinventory;
	}

	public Workorderrouting addWorkorderrouting(Workorderrouting workorderrouting) {
		getWorkorderroutings().add(workorderrouting);
		workorderrouting.setLocation(this);

		return workorderrouting;
	}

	public BigDecimal getAvailability() {
		return this.availability;
	}

	public BigDecimal getCostrate() {
		return this.costrate;
	}

	public Integer getLocationid() {
		return this.locationid;
	}
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public LocalDate getModifieddate() {
		return this.modifieddate;
	}

	public String getName() {
		return this.name;
	}

	public List<Productinventory> getProductinventories() {
		return this.productinventories;
	}

	public List<Workorderrouting> getWorkorderroutings() {
		return this.workorderroutings;
	}

	public Productinventory removeProductinventory(Productinventory productinventory) {
		getProductinventories().remove(productinventory);
		productinventory.setLocation(null);

		return productinventory;
	}

	public Workorderrouting removeWorkorderrouting(Workorderrouting workorderrouting) {
		getWorkorderroutings().remove(workorderrouting);
		workorderrouting.setLocation(null);

		return workorderrouting;
	}

	public void setAvailability(BigDecimal availability) {
		this.availability = availability;
	}

	public void setCostrate(BigDecimal costrate) {
		this.costrate = costrate;
	}

	public void setLocationid(Integer locationid) {
		this.locationid = locationid;
	}

	public void setModifieddate(LocalDate modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductinventories(List<Productinventory> productinventories) {
		this.productinventories = productinventories;
	}

	public void setWorkorderroutings(List<Workorderrouting> workorderroutings) {
		this.workorderroutings = workorderroutings;
	}

}