package com.tg.cust.pref.store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tg.cust.pref.store.entity.TgCustomerPreference;

/**
 * Repository for accessing customer preference table in database
 * @author jasar
 *
 */
@Repository
public interface CustomerPreferenceRepository extends JpaRepository<TgCustomerPreference, Long> {

	/**
	 * Method to find all preferences of given customer id
	 * 
	 * @param customerId
	 * @return List<TgCustomerPreference>
	 */
	public List<TgCustomerPreference> findByCustomerId(Long customerId);

	/**
	 * Method to find all preference of given type for a given customer id
	 * 
	 * @param customerId
	 * @param preferenceType
	 * @return Optional<TgCustomerPreference>
	 */
	public Optional<TgCustomerPreference> findByCustomerIdAndPreferenceType(Long customerId, String preferenceType);

}
