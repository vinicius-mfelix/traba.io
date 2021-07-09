package com.growth.startupone.api.v1.dto.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "User", description = "Describes an existing user on the database. Check the property 'UserInput' for user registering necessary payload.")
@Getter
@Setter
public class UserDTO {
	
	@ApiModelProperty(example = "1", value = "User's ID.")
	@JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
	private Long id;
	
	@ApiModelProperty(example = "John", value = "User's first name.")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String firstName;
	
	@ApiModelProperty(example = "Doe", value = "User's last name.")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String lastName;
	
	@ApiModelProperty(example = "jhon.doe@example.com", value = "User's e-mail address.")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String email;
	
	@ApiModelProperty(example = "(99) 99999-9999", value = "User's phone number.")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String phone;
	
	@ApiModelProperty(example = "1999-08-05 (August, 5th, 1999)", value = "User's birth date (year-month-day).")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate birthDate;
	
	@ApiModelProperty(example = "2021-07-09T13:00:00Z (July, 9th, 2021 at 13:00 UTC Time Zone)", value = "User's creation timestamp (UTC Time Zone).")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private OffsetDateTime createdAt;
	
	@ApiModelProperty(example = "2021-07-12T16:43:00Z (July, 12th, 2021 at 16:43 UTC Time Zone)", value = "User's last update timestamp (UTC Time Zone).")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private OffsetDateTime updatedAt;

}
