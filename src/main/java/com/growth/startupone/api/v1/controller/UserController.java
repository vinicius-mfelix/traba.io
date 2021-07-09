package com.growth.startupone.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.growth.startupone.api.v1.dto.assembler.UserDTOAssembler;
import com.growth.startupone.api.v1.dto.disassembler.UserInputDisassembler;
import com.growth.startupone.api.v1.dto.model.UserDTO;
import com.growth.startupone.api.v1.dto.model.input.UserDTOInput;
import com.growth.startupone.api.v1.openapi.controller.UserControllerDocumentation;
import com.growth.startupone.domain.model.User;
import com.growth.startupone.domain.service.UserService;

@RestController
@RequestMapping(path = "/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController implements UserControllerDocumentation {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserDTOAssembler assembler;
	
	@Autowired
	private UserInputDisassembler disassembler;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Override
	public UserDTO store(@RequestBody @Valid UserDTOInput userDTOInput) {
		User user = disassembler.toDomainObject(userDTOInput);
		
		return assembler.toDTO(userService.store(user));
	}

}
