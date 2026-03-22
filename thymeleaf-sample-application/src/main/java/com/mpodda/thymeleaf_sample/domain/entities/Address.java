package com.mpodda.thymeleaf_sample.domain.entities;

import com.mpodda.thymeleaf_sample.domain.enums.AddressType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "thysa_addresses")
public class Address extends IdentifiableEntity {
	@Column(name = "addr_street_name")
	private String streetName;
	
	@Column(name = "addr_number")
	private String number;
	
	@Column(name = "addr_zip_code")
	private String zipCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="addr_ctid")
	private City city;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "addr_type")
	private AddressType type;
	
	@Column(name = "addr_comments", length = 4000)
	private String comments;
}
