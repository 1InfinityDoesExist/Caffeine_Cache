
package com.patel.redis.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;

@Service
public interface ProfileDetailsService {

    public ProfileDetailsCreateResponse createProfileDetails(
                    ProfileDetailsCreateRquest profileDetailsCreateRequest, String tenant)
                    throws Exception;

    public ProfileDetails getProfileDetailsByUserName(String userName) throws Exception;

    public List<ProfileDetails> getAllProfileDetails(Integer parentTenant) throws Exception;

    public void deleteProfileDetails(String email, Integer parentTenant) throws Exception;

    public void updateProfileDetails(String updateRequest, Integer id) throws Exception;
}
