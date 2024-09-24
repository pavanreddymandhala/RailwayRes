package com.cg.railwayreservation.booking.ExternalClasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginModel {

	private String username;
	private String password;
	private String role;
	private String email;
	private String gender;
	private Integer age;
	private String country;

}
