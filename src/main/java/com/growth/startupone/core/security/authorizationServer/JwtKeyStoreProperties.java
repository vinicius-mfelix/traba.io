package com.growth.startupone.core.security.authorizationServer;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties("growth.startupone.jwt.keystore")
public class JwtKeyStoreProperties {

	@NotNull
	private Resource jksLocation;
	
	@NotNull
	private String password;
	
	@NotNull
	private String keypairAlias;
	
}
