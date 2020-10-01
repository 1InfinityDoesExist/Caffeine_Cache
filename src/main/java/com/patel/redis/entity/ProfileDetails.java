package com.patel.redis.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.annotations.UpdateTimestamp;
import org.json.simple.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.patel.redis.model.Address;
import com.vladmihalcea.hibernate.type.array.ListArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "ProfileDetails")
@ApiModel(value = "ProfileDetails", description = "Profile Details Attributes")
@TypeDefs({@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
            @TypeDef(name = "array-list", typeClass = ListArrayType.class)})
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    private String mobile;
    private boolean isEmailVerified;
    private boolean isMobielVerified;
    private String firstName;
    private String lastName;
    @Temporal(TemporalType.DATE)
    private Date dob;
    @ElementCollection(targetClass = String.class)
    // @Type(type = "array-list")
    // @Column(name = "password", columnDefinition = "text[]")
    private List<String> password = new LinkedList<>();
    private String profilePicture;
    private String profileDP;
    private Integer parentTenant;
    @Type(type = "jsonb")
    @Column(name = "address", columnDefinition = "jsonb")
    private Address address;
    @Type(type = "jsonb")
    @Column(name = "education", columnDefinition = "jsonb")
    private JSONObject education;
    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> certification;
    // @Type(type = "array-list")
    // @Column(name = "lucky_number", columnDefinition = "integer[]")
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> luckyNumber = new LinkedList<>();
    // @Type(type = "array-list")
    // @Column(name = "hobbies", columnDefinition = "text[]")
    @ElementCollection(targetClass = String.class)
    private List<String> hobbies = new LinkedList<>();
}
