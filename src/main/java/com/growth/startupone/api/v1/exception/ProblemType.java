package com.growth.startupone.api.v1.exception;

import lombok.Getter;

@Getter
public enum ProblemType {
	
	VALIDATION_ERROR("/validation-error", "Error during the validation of the payload."),
	INCOMPREHENSIBLE_MESSAGE("/incomprehensible-message", "Request payload incomprehensible."),
	INVALID_PARAMETER("/invalid-parameter", "Invalid property parameter."),
	RESOURCE_NOT_FOUND("/resource-not-found", "Resource URI not found."),
	INTERNAL_ERROR("/internal-error", "Internal server error.");
	
	private String title;
	private String uri;
	
	ProblemType(String uri, String title) {
		this.title = title;
		this.uri = "https://api.startupone.com" + uri;
	}

}
