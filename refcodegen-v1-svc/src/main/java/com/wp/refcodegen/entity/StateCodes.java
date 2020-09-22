package com.wp.refcodegen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "state_codes")
public class StateCodes {

	@Id
	private String state;
	
	@Column(name = "state_code", nullable=false)
	private String stateCode;
	
 /*
  * Optional<SiteDetails> siteDetails = Arrays.asList(values()).stream().filter(a -> a.siteId.equals(siteId)).findFirst();
  * return siteDetails.orElse(null);
  * 
  */
}
