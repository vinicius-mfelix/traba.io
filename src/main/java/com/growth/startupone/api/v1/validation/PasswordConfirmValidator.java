package com.growth.startupone.api.v1.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.growth.startupone.api.v1.dto.model.input.UserDTOInput;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, UserDTOInput> {
	
	@Override
	public boolean isValid(UserDTOInput value, ConstraintValidatorContext context) {
		boolean valido = true;
		
		if (value.getPassword() != null && value.getPasswordConfirm() != null) {
			if (!value.getPassword().equals(value.getPasswordConfirm())) {
				valido = false;
			}
		}
		
		return valido;
	}

}
