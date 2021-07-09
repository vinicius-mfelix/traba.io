package com.growth.startupone.api.v1.dto.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.growth.startupone.api.v1.dto.model.input.UserDTOInput;
import com.growth.startupone.domain.model.User;

@Component
public class UserInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public User toDomainObject(UserDTOInput userDTOInput) {
		return modelMapper.map(userDTOInput, User.class);
	}
}
