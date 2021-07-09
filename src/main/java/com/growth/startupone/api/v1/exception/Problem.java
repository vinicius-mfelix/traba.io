package com.growth.startupone.api.v1.exception;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@ApiModel(description = "API Exception thrown model representation.")
@Getter
@Builder
@JsonInclude(Include.NON_NULL)
public class Problem {
	
	@ApiModelProperty(value = "Describes the returned HTTP status from the server.", example = "400")
	private Integer status;
	
	@ApiModelProperty(value = "Describes the timestamp of the thrown exception.", example = "2021-01-13T15:49:00Z")
	private OffsetDateTime timestamp;
	
	@ApiModelProperty(value = "Describes the title of the thrown exception.", example = "Refered entity not found.")
	private String title;
	
	@ApiModelProperty(value = "Describes the type of the thrown exception.", example = "https://startupone-api.com/entity-not-found")
	private String type;
	
	@ApiModelProperty(value = "Describes the details of the thrown exception.", example = "The entity with id X was not found.")
	private String details;
	
	@ApiModelProperty(value = "Describes all the error fields during the validation process.")
	private List<Field> fields;
	
	@ApiModel(value = "ProblemField", description = "Describes all the field with errors during the validation process.")
	@Getter
	@Builder
	public static class Field {
		
		@ApiModelProperty(value = "Describes the field name (aka object atribute).", example = "firstName")
		private String name;
		
		@ApiModelProperty(value = "Describes the error message.", example = "User's first name cannot be empty, null or blank!")
		private String message;
	}

}
