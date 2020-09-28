package com.patel.redis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.apache.tomcat.jni.Address;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "ProfileDetails")
@ApiModel(value = "ProfileDetails", description = "Profile Details Attributes")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class ProfileDetails implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modificationDate;
    private String userName;
    private String email;
    private String moible;
    private boolean isEmailVerified;
    private boolean isMobielVerified;
    private String firstName;
    private String lastName;
    private Date dob;
    private Stack<String> password = new Stack<>();
    private String profilePicture;
    private String profileDP;
    private Integer parentTenant;
    @Type(type = "jsonb")
    @Column(name = "address", columnDefinition = "jsonb")
    private Address address;
    @Type(type = "jsonb")
    @Column(name = "education", columnDefinition = "jsonb")
    private String education;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> certification = new HashMap<String, Object>();
}
