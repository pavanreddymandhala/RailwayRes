package com.cg.railwayreservation.admin.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userlogin")
public class LoginModel {
	@Id
    private String username;
    private String password;
    private String role;
    private String email;
    private String gender;
    private Integer age;
    private String country;
	
}
