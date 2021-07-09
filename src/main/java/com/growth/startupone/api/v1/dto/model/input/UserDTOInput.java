package com.growth.startupone.api.v1.dto.model.input;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.growth.startupone.api.v1.validation.PasswordConfirm;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "UserInput", description = "Describes the body of a new user to be included.")
@Getter
@Setter
@PasswordConfirm
public class UserDTOInput {
	
	@ApiModelProperty(example = "John", value = "User's first name.", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotBlank
	@Size(max = 60)
	private String firstName;
	
	@ApiModelProperty(example = "Doe", value = "User's last name.", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotBlank
	@Size(max = 60)
	private String lastName;
	
	@ApiModelProperty(example = "jhon.doe@example.com", value = "User's e-mail address.", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotBlank
	@Email
	@Size(max = 60)
	private String email;
	
	@ApiModelProperty(example = "(99) 99999-9999", value = "User's phone number.", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotBlank
	@Size(max = 60)
	private String phone;
	
	@ApiModelProperty(example = "s3cretP@ssword", value = "User's password.", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotBlank
	@Size(min = 8)
	private String password;
	
	@ApiModelProperty(example = "s3cretP@ssword", value = "User's password confirm.", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotBlank
	private String passwordConfirm;
	
	@ApiModelProperty(example = "1999-08-05 (August, 5th, 1999)", value = "User's birth date (year-month-day).", required = true)
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	@NotNull
	private LocalDate birthDate;

}
