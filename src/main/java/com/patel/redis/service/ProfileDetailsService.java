
package com.patel.redis.service;

import java.util.List;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.patel.redis.entity.ProfileDetails;
import com.patel.redis.model.request.ProfileDetailsCreateRquest;
import com.patel.redis.model.request.ProfileDetailsUpdateRequest;
import com.patel.redis.model.response.ProfileDetailsCreateResponse;

@Service
public interface ProfileDetailsService {

    public ProfileDetailsCreateResponse createProfileDetails(
                    ProfileDetailsCreateRquest profileDetailsCreateRequest, String tenant)
                    throws Exception;

    public ProfileDetails getProfileDetailsByUserName(Integer id) throws Exception;

    public List<ProfileDetails> getAllProfileDetails(Integer parentTenant) throws Exception;

    public void deleteProfileDetails(String email, Integer parentTenant) throws Exception;

    public void updateProfileDetails(ProfileDetailsUpdateRequest updateRequest, Integer id)
                    throws Exception;
}
