package com.csv.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	
	@Id
	@NotNull
	@Size(min=2, max=30)
	@NotBlank(message = "userName is mandatory")
    private String userName;
	
	@NotNull
	@NotBlank(message = "firstName is mandatory")
    private String firstName;
	
    private String lastName;
	
	@NotNull
	@Email
	@NotBlank(message = "email is mandatory")
    private String email;
	
	@NotNull
	@NotBlank(message = "Password is mandatory")
    private String password;
	
    private String address;
}
