package com.growth.startupone.core.security.authorizationServer;

import java.util.HashMap;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;

import com.growth.startupone.core.security.authorizationServer.model.AuthUser;

public class JwtCustomClaimsTokenEnhancer extends TokenEnhancerChain {
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		if(authentication.getPrincipal() instanceof AuthUser) {
			AuthUser user = (AuthUser) authentication.getPrincipal();
			
			HashMap<String, Object> payload = new HashMap<String, Object>();
			payload.put("user_id", user.getId());
			payload.put("user_first_name", user.getFirstName());
			payload.put("user_last_name", user.getLastName());
			payload.put("user_email", user.getEmail());
			payload.put("user_phone", user.getPhone());
			
			DefaultOAuth2AccessToken oauth2AccessToken = (DefaultOAuth2AccessToken) accessToken;
			oauth2AccessToken.setAdditionalInformation(payload);
		}
		
		return accessToken;
	}

}
