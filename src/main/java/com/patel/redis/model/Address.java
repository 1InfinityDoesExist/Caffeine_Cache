package com.patel.redis.model;

import java.io.Serializable;

@lombok.Data
public class Address implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private String city;
    private String zipCode;
    private String state;
    private Double longitude;
    private Double latitude;
    private String type;
}
