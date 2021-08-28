package com.tg.cust.pref.store.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.cust.pref.store.entity.TgCustomerPreference;
import com.tg.cust.pref.store.exception.PreferenceNotFoundException;
import com.tg.cust.pref.store.model.CreateCustomerPrefRequest;
import com.tg.cust.pref.store.model.CustomerPrefResponse;
import com.tg.cust.pref.store.model.UpdateCustomerPrefRequest;
import com.tg.cust.pref.store.repository.CustomerPreferenceRepository;
import com.tg.cust.pref.store.service.CustomerPrefStoreService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author jasar
 *
 */
@Slf4j
@Service
public class CustomerPrefStoreServiceImpl implements CustomerPrefStoreService {

	@Autowired
	private CustomerPreferenceRepository customerPreferenceRepository;

	@Override
	public List<CustomerPrefResponse> getCustomerPreferences(Long customerId) {

		List<TgCustomerPreference> preferenceEntityList = customerPreferenceRepository.findByCustomerId(customerId);
		log.info("Successfully retrieved preferences from database");

		return mapPreferenceList(preferenceEntityList);
	}

	@Override
	public List<CustomerPrefResponse> createCustomerPreferences(Long customerId,
			List<CreateCustomerPrefRequest> customerPrefList) {

		List<TgCustomerPreference> custPrefEntityList = new ArrayList<>();
		customerPrefList.forEach(customerPref -> {
			TgCustomerPreference custPrefEntity = TgCustomerPreference.builder().customerId(customerId)
					.preferenceType(customerPref.getPreferenceType().toLowerCase()).optIn(customerPref.getOptIn()).createdDate(new Date()).build();
			custPrefEntityList.add(custPrefEntity);
		});

		List<TgCustomerPreference> savedPreferenceEntityList = customerPreferenceRepository.saveAll(custPrefEntityList);
		log.info("Successfully created preferences in database");

		return mapPreferenceList(savedPreferenceEntityList);
	}

	private List<CustomerPrefResponse> mapPreferenceList(List<TgCustomerPreference> preferenceEntityList) {

		log.info("Mapping preference entities to model objects");

		List<CustomerPrefResponse> customerPrefList = new ArrayList<>();
		preferenceEntityList.forEach(preferenceEntity -> {
			CustomerPrefResponse customerPrefResponse = new CustomerPrefResponse();
			BeanUtils.copyProperties(preferenceEntity, customerPrefResponse);
			customerPrefList.add(customerPrefResponse);
		});

		return customerPrefList;
	}

	@Override
	public CustomerPrefResponse getCustomerPreferenceByType(Long customerId, String preferenceType) {

		Optional<TgCustomerPreference> preferenceEntity = customerPreferenceRepository
				.findByCustomerIdAndPreferenceType(customerId, preferenceType.toLowerCase());
		if (preferenceEntity.isPresent()) {

			log.info("Successfully retrieved preference from database");
			return mapPreference(preferenceEntity.get());
		}

		log.error("Could not find preference of type - {}, for user - {}", preferenceType, customerId);
		throw new PreferenceNotFoundException("Preference of given type not found");
	}

	@Override
	public CustomerPrefResponse updateCustomerPreference(Long customerId, String preferenceType,
			UpdateCustomerPrefRequest customerPref) {

		Optional<TgCustomerPreference> optionPreferenceEntity = customerPreferenceRepository
				.findByCustomerIdAndPreferenceType(customerId, preferenceType.toLowerCase());
		if (optionPreferenceEntity.isPresent()) {
			TgCustomerPreference preferenceEntity = optionPreferenceEntity.get();
			preferenceEntity.setOptIn(customerPref.getOptIn());
			preferenceEntity.setModifiedDate(new Date());
			TgCustomerPreference savedPreferenceEntity = customerPreferenceRepository.save(preferenceEntity);
			log.info("Successfully updated preference in database");

			return mapPreference(savedPreferenceEntity);
		}

		log.error("Could not find preference of type - {}, for user - {}", preferenceType, customerId);
		throw new PreferenceNotFoundException("Preference of given type not found");
	}

	private CustomerPrefResponse mapPreference(TgCustomerPreference preferenceEntity) {

		log.info("Mapping preference entity to model object");

		CustomerPrefResponse customerPrefResponse = new CustomerPrefResponse();
		BeanUtils.copyProperties(preferenceEntity, customerPrefResponse);
		return customerPrefResponse;
	}

	@Override
	public boolean deleteCustomerPreference(Long customerId, String preferenceType) {

		Optional<TgCustomerPreference> preferenceEntity = customerPreferenceRepository
				.findByCustomerIdAndPreferenceType(customerId, preferenceType.toLowerCase());
		if (preferenceEntity.isPresent()) {
			customerPreferenceRepository.delete(preferenceEntity.get());
			log.info("Successfully deleted preference in database");
			return true;
		}

		log.error("Could not find preference of type - {}, for user - {}", preferenceType, customerId);
		throw new PreferenceNotFoundException("Preference of given type not found");
	}
}
