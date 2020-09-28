package com.patel.redis.service.impl;

import java.awt.color.ProfileDataException;
import java.util.List;
import java.util.Stack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.exception.ProfileDetailsNotFoundException;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;
import com.patel.redis.repository.ProfileDetailsRepository;
import com.patel.redis.service.ProfileDetailsService;

@Component
public class ProfileDetailsServiceImpl implements ProfileDetailsService {

    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;

    @Override
    public ProfileDetailsCreateResponse createProfileDetails(
                    ProfileDetailsCreateRquest pdRequest, String tenant) {
        // tenant check to be implemented
        ProfileDetails profileDetails = new ProfileDetails();
        profileDetails.setAddress(pdRequest.getAddress());
        profileDetails.setCertification(pdRequest.getCertification());
        profileDetails.setDob(pdRequest.getDob());
        profileDetails.setEducation(pdRequest.getEducation());
        profileDetails.setEmail(pdRequest.getEmail());
        profileDetails.setFirstName(pdRequest.getFirstName());
        profileDetails.setLastName(pdRequest.getLastName());
        profileDetails.setMoible(pdRequest.getMoible());
        Stack<String> password = new Stack<String>();
        password.add(pdRequest.getPassword());
        profileDetails.setPassword(password);
        profileDetails.setProfileDP(pdRequest.getProfileDP());
        profileDetails.setProfilePicture(pdRequest.getProfilePicture());
        profileDetails.setUserName(pdRequest.getUserName());
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
    public void deleteProfileDetails(String email, Integer tenant) {
        ProfileDetails profileDetailsFromDB =
                        profileDetailsRepository.getProfileDetailsByEmailAndParentTenant(email,
                                        new Integer(1));
        if (profileDetailsFromDB == null) {
            throw new ProfileDataException(
                            "ProfileDetails for the given email :" + email + " not found");
        }
        profileDetailsRepository.delete(profileDetailsFromDB);
        return;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateProfileDetails(String updateRequest, Integer id)
                    throws ParseException, Exception {
        ProfileDetails profileDetailsFromDB = profileDetailsRepository.getProfileDetailsById(id);
        if (profileDetailsFromDB == null) {
            throw new ProfileDetailsNotFoundException("ProfileDetails not found.");
        }
        JSONObject payloadRequest = (JSONObject) new JSONParser().parse(updateRequest);
        JSONObject dbJsonObject = (JSONObject) new JSONParser()
                        .parse(new ObjectMapper().writeValueAsString(profileDetailsFromDB));
        for (Object obj : payloadRequest.keySet()) {
            String param = (String) obj;
            dbJsonObject.put(param, payloadRequest.get(param));
        }
        profileDetailsRepository.save(new ObjectMapper().readValue(dbJsonObject.toJSONString(),
                        ProfileDetails.class));
        return;
    }

}
