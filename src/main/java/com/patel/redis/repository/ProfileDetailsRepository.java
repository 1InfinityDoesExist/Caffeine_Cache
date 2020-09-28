package com.patel.redis.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.patel.redis.entity.ProfileDetails;

@Repository
public interface ProfileDetailsRepository extends JpaRepository<ProfileDetails, Integer> {

    public ProfileDetails getProfileDetailsByUserName(String userName);

    public ProfileDetails getProfileDetailsById(Integer id);

    public List<ProfileDetails> getProfileDetailsByParentTenant(Integer tenant);

    public ProfileDetails getProfileDetailsByEmailAndParentTenant(String email, Integer i);

}
