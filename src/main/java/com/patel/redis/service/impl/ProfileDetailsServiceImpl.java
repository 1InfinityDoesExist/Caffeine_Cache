package com.patel.redis.service.impl;

import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.exception.ProfileDetailsNotFoundException;
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

    @Override
    public ProfileDetailsCreateResponse createProfileDetails(
                    ProfileDetailsCreateRquest pdRequest, String tenant) {
        log.info("::::::ProfileDetailsServiceImpl Class, createProfileDetails method:::::");
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
        // profileDetails.setLuckyNumber(pdRequest.getLuckyNumber());
        // profileDetails.setHobbies(pdRequest.getHobbies());
        profileDetails.setParentTenant(1);
        profileDetailsRepository.save(profileDetails);
        ProfileDetailsCreateResponse response = new ProfileDetailsCreateResponse();
        response.setId(profileDetails.getId());
        response.setMsg("Successfully Crated");
        return response;
    }

    @Override
    public ProfileDetails getProfileDetailsByUserName(String userName) throws Exception {
        ProfileDetails profileDetailsFromDB =
                        profileDetailsRepository.getProfileDetailsByUserName(userName);
        if (profileDetailsFromDB == null) {
            throw new ProfileDetailsNotFoundException(
                            "ProfileDetails for the given userName :" + userName + " not found");
        }
        return profileDetailsFromDB;
    }

    @Override
    public List<ProfileDetails> getAllProfileDetails(Integer parentTenant) {
        List<ProfileDetails> listOfProfileDetails =
                        profileDetailsRepository.getProfileDetailsByParentTenant(parentTenant);
        return listOfProfileDetails;
    }

    @Override
    public void deleteProfileDetails(String email, Integer tenant) throws Exception {
        ProfileDetails profileDetailsFromDB =
                        profileDetailsRepository.getProfileDetailsByEmailAndParentTenant(email,
                                        new Integer(1));
        if (profileDetailsFromDB == null) {
            throw new ProfileDetailsNotFoundException(
                            "ProfileDetails for the given email :" + email + " not found");
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
        JSONObject payloadRequest = (JSONObject) new JSONParser()
                        .parse(new Gson().toJson(updateRequest));
        JSONObject dbJsonObject = (JSONObject) new JSONParser()
                        .parse(new Gson().toJson(profileDetailsFromDB));
        for (Object obj : payloadRequest.keySet()) {
            String param = (String) obj;
            log.info(":::::param {}", param);
            if (param.equals("hobbies") || param.equals("luckyNumber")
                            || param.equals("password")) {
                JSONArray jsonArray = (JSONArray) payloadRequest.get(param);
                dbJsonObject.put(param, jsonArray);
            } else {
                dbJsonObject.put(param, payloadRequest.get(param));
            }
        }
        log.info("::::::{}", dbJsonObject.toJSONString());
        ProfileDetails finalData = new Gson().fromJson(dbJsonObject.toJSONString(),
                        ProfileDetails.class);
        log.info(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::{}::", finalData);
        profileDetailsRepository.save(finalData);
        return;
    }

    @Override
    public void check() throws JsonProcessingException, ParseException {
        ProfileDetails profileDetailsFromDB = profileDetailsRepository.getProfileDetailsById(1);
        JSONObject dbJsonObject = (JSONObject) new JSONParser()
                        .parse(new ObjectMapper().writeValueAsString(profileDetailsFromDB));
        log.info("::::::dbJsonObject {}", dbJsonObject);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        profileDetailsRepository.save(objectMapper.readValue(dbJsonObject.toJSONString(),
                        ProfileDetails.class));

    }


}
