package com.patel.redis.service.impl;

import java.util.List;
import org.springframework.stereotype.Component;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;
import com.patel.redis.service.ProfileDetailsService;

@Component
public class ProfileDetailsServiceImpl implements ProfileDetailsService {

    @Override
    public ProfileDetailsCreateResponse createProfileDetails(
                    ProfileDetailsCreateRquest profileDetailsCreateRequest, String tenant) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProfileDetails getProfileDetailsByUserName(String userName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<ProfileDetails> getAllProfileDetails(String tenant) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteProfileDetails(String email, String tenant) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateProfileDetails(String updateRequest, String tenant) {
        // TODO Auto-generated method stub

    }

}
