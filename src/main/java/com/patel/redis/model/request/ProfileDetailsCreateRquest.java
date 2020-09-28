package com.patel.redis.model.request;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import org.apache.tomcat.jni.Address;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileDetailsCreateRquest {
    private String userName;
    private String email;
    private String moible;
    private boolean isEmailVerified;
    private boolean isMobielVerified;
    private String firstName;
    private String lastName;
    private Date dob;
    private String password;
    private String profilePicture;
    private String profileDP;
    private Address address;
    private String education;
    private Map<String, Object> certification = new HashMap<String, Object>();
}
