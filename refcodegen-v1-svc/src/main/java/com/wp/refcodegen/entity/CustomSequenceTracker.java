package com.wp.refcodegen.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "custom_sequence_tracker")
public class CustomSequenceTracker {

	@Id
	private String stateCode;
	
	@Column(name = "pvdr_seq", nullable = false)
	private Integer providerSeq;
	
	@Column(name = "sales_agent_seq", nullable = false)
	private Integer salesAgentSeq;
		
}
