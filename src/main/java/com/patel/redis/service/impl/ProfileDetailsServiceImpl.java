package com.patel.redis.service.impl;

import java.util.List;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fb.demo.entity.Tenant;
import com.fb.demo.exception.TenantNotFoundException;
import com.fb.demo.repository.TenantRepository;
import com.google.gson.Gson;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.exception.InvalidInputException;
import com.patel.redis.exception.ProfileDetailsNotFoundException;
import com.patel.redis.exception.UserNameAlreadyInUseException;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.request.ProfileDetailsUpdateRequest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;
import com.patel.redis.repository.ProfileDetailsRepository;
import com.patel.redis.service.ProfileDetailsService;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProfileDetailsServiceImpl implements ProfileDetailsService {

	@Autowired
	private ProfileDetailsRepository profileDetailsRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired(required = true)
	private TenantRepository tenantRepository;

	@Override
	public ProfileDetailsCreateResponse createProfileDetails(ProfileDetailsCreateRquest pdRequest, String tenant)
			throws Exception {
		log.info("::::::ProfileDetailsServiceImpl Class, createProfileDetails method:::::");
		Tenant tenantFromDB = tenantRepository.getTenantByName(tenant);
		if (tenantFromDB == null) {
			throw new TenantNotFoundException("Tenant not found");
		}
		if (pdRequest.getUserName() == null || StringUtils.isEmpty(pdRequest.getUserName())) {
			throw new InvalidInputException("Username must not be null or empty");
		}
		ProfileDetails profileDetailsFromDB = profileDetailsRepository
				.getProfileDetailsByUserNameAndParentTenant(pdRequest.getUserName(), new Integer(1));
		if (profileDetailsFromDB != null) {
			throw new UserNameAlreadyInUseException("Username already user");
		}
		// tenant check to be implemented
		ProfileDetails profileDetails = new ProfileDetails();
		profileDetails.setAddress(pdRequest.getAddress());
		profileDetails.setCertification(pdRequest.getCertification());
		profileDetails.setDob(pdRequest.getDob());
		profileDetails.setEducation(pdRequest.getEducation());
		profileDetails.setEmail(pdRequest.getEmail());
		profileDetails.setFirstName(pdRequest.getFirstName());
		profileDetails.setLastName(pdRequest.getLastName());
		profileDetails.setMobile(pdRequest.getMobile());
		profileDetails.getPassword().add(encoder.encode(pdRequest.getPassword()));
		profileDetails.setProfileDP(pdRequest.getProfileDP());
		profileDetails.setProfilePicture(pdRequest.getProfilePicture());
		profileDetails.setUserName(pdRequest.getUserName());
		profileDetails.setLuckyNumber(pdRequest.getLuckyNumber());
		profileDetails.setHobbies(pdRequest.getHobbies());
		profileDetails.setParentTenant(1);
		profileDetailsRepository.save(profileDetails);
		ProfileDetailsCreateResponse response = new ProfileDetailsCreateResponse();
		response.setId(profileDetails.getId());
		response.setMsg("Successfully Crated");
		return response;
	}

	@Cacheable(value = "profileDetails", key = "#id", unless = "#result.id <100")
	@Override
	public ProfileDetails getProfileDetailsByUserName(Integer id) throws Exception {
		log.info("::::::GetProfileDetails By Id");
		ProfileDetails profileDetailsFromDB = profileDetailsRepository.getProfileDetailsById(id);
		if (profileDetailsFromDB == null) {
			throw new ProfileDetailsNotFoundException("ProfileDetails for the given userName :" + id + " not found");
		}
		return profileDetailsFromDB;
	}

	@Override
	public List<ProfileDetails> getAllProfileDetails(Integer parentTenant) throws Exception {
		Tenant tenantFromDB = tenantRepository.getTenantById(parentTenant);
		if (tenantFromDB == null) {
			throw new TenantNotFoundException("Tenant not found");
		}
		List<ProfileDetails> listOfProfileDetails = profileDetailsRepository
				.getProfileDetailsByParentTenant(parentTenant);
		return listOfProfileDetails;
	}

	@Override
	public void deleteProfileDetails(String email, Integer tenant) throws Exception {
		Tenant tenantFromDB = tenantRepository.getTenantById(tenant);
		if (tenantFromDB == null) {
			throw new TenantNotFoundException("Tenant not found");
		}
		ProfileDetails profileDetailsFromDB = profileDetailsRepository.getProfileDetailsByEmailAndParentTenant(email,
				new Integer(1));
		if (profileDetailsFromDB == null) {
			throw new ProfileDetailsNotFoundException("ProfileDetails for the given email :" + email + " not found");
		}
		profileDetailsRepository.delete(profileDetailsFromDB);
		return;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateProfileDetails(ProfileDetailsUpdateRequest updateRequest, Integer id)
			throws ParseException, Exception {
		ProfileDetails profileDetailsFromDB = profileDetailsRepository.getProfileDetailsById(id);
		if (profileDetailsFromDB == null) {
			throw new ProfileDetailsNotFoundException("ProfileDetails not found.");
		}
		JSONObject payloadRequest = (JSONObject) new JSONParser().parse(new Gson().toJson(updateRequest));
		JSONObject dbJsonObject = (JSONObject) new JSONParser().parse(new Gson().toJson(profileDetailsFromDB));
		for (Object obj : payloadRequest.keySet()) {
			String param = (String) obj;
			log.info(":::::param {}", param);
			dbJsonObject.put(param, payloadRequest.get(param));
		}
		log.info("::::::{}", dbJsonObject.toJSONString());
		ProfileDetails finalData = new Gson().fromJson(dbJsonObject.toJSONString(), ProfileDetails.class);
		log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::{}::", finalData);
		profileDetailsRepository.save(finalData);
		return;
	}
}
