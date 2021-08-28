package com.tg.cust.pref.store.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author jasar
 *
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TgCustomerPreference {

	@Id
	@GeneratedValue
	private Long customerPreferenceId;
	private Long customerId;
	private String preferenceType;
	private Boolean optIn;
	private Date createdDate;
	private Date modifiedDate;
}
