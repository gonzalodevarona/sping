package com.gonza.taller.model.prod;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;

/**
 * The persistent class for the productinventory database table.
 *
 */
@Entity
@NamedQuery(name = "Productinventory.findAll", query = "SELECT p FROM Productinventory p")
public class Productinventory implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name = "PRODUCTINVENTORY_PRODUCTINVENTORYID_GENERATOR", allocationSize = 1, sequenceName = "PRODUCTINVENTORY_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTINVENTORY_PRODUCTINVENTORYID_GENERATOR")
	private Integer id;

	private Integer bin;

	private Timestamp modifieddate;

	private Integer quantity;

	private Integer rowguid;

	private String shelf;

	// bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumn(name = "locationid", insertable = false, updatable = false)
	private Location location;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productid", insertable = false, updatable = false)
	private Product product;

	public Productinventory() {
	}

	public Integer getBin() {
		return this.bin;
	}

	public Integer getId() {
		return this.id;
	}

	public Location getLocation() {
		return this.location;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public Product getProduct() {
		return this.product;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public String getShelf() {
		return this.shelf;
	}

	public void setBin(Integer bin) {
		this.bin = bin;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

	public void setShelf(String shelf) {
		this.shelf = shelf;
	}

}