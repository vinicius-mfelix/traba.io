package com.growth.startupone.api.v1.openapi.controller;

import com.growth.startupone.api.v1.dto.model.UserDTO;
import com.growth.startupone.api.v1.dto.model.input.UserDTOInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Users")
public interface UserControllerDocumentation {
	
	@ApiOperation("Stores a new user.")
	public UserDTO store(@ApiParam(name = "payload", value = "Model of a new user to be stored.") UserDTOInput userDTOInput);

}
