package com.patel.redis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.patel.redis.entity.ProfileDetails;

@Repository
public interface ProfileDetailsRepository extends JpaRepository<ProfileDetails, Integer> {

}
