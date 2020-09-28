
package com.patel.redis.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;

@Service
public interface ProfileDetailsService {

    public ProfileDetailsCreateResponse createProfileDetails(
                    ProfileDetailsCreateRquest profileDetailsCreateRequest, String tenant);

    public ProfileDetails getProfileDetailsByUserName(String userName);

    public List<ProfileDetails> getAllProfileDetails(String tenant);

    public void deleteProfileDetails(String email, String tenant);

    public void updateProfileDetails(String updateRequest, String tenant);
}
