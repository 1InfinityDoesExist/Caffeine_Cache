package com.patel.redis.model.request;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import com.patel.redis.model.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileDetailsCreateRquest {
    private String userName;
    private String email;
    private String mobile;
    private boolean isEmailVerified;
    private boolean isMobielVerified;
    private String firstName;
    private String lastName;
    private Date dob;
    private String password;
    private String profilePicture;
    private String profileDP;
    private Address address;
    private JSONObject education;
    private Map<String, Object> certification;
    private List<Integer> luckyNumber;
    private List<String> hobbies;
}
