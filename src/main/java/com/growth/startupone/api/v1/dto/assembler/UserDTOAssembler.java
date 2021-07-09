package com.growth.startupone.api.v1.dto.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.growth.startupone.api.v1.dto.model.UserDTO;
import com.growth.startupone.domain.model.User;

@Component
public class UserDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public UserDTO toDTO(User user) {
		return modelMapper.map(user, UserDTO.class);
	}

}
