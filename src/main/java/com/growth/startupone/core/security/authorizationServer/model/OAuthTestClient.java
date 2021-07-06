package com.growth.startupone.core.security.authorizationServer.model;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("growth.startupone.oauth.client.test")
public class OAuthTestClient {
	
	@NotNull
	private String clientName;

	@NotNull
	private String clientSecret;
}
