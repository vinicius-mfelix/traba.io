package com.growth.startupone.api.v1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SwaggerController {

	@RequestMapping("/v1/docs")
	public String swaggerUI() {
		return "redirect:/swagger-ui.html";
	}
}