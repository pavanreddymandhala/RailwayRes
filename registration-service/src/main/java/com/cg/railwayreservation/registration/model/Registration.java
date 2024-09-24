package com.cg.railwayreservation.registration.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "userlogin")
public class Registration {
	@Id
    @Size(min = 3, max = 20, message = "Username should be between 3 and 20 characters")
    private String username;

    @Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotBlank(message = "Role should not be blank")
    private String role;

    @Email(message = "Email should be valid")
    private String email;

    @Pattern(regexp = "^(Male|Female)$", message = "Gender must be either Male or Female")
    private String gender;

    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 100, message = "Age cannot exceed 100")
    private Integer age;
    
    @NotBlank(message = "Country cannot be empty")
    private String country;
	
}