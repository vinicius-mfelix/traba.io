package com.growth.startupone.core.openapi;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.classmate.TypeResolver;
import com.growth.startupone.api.v1.exception.Problem;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {
	
	@Bean
	public Docket apiDocket() {
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
					.apis(RequestHandlerSelectors.basePackage("com.growth.startupone.api.v1"))
					.build()
					.apiInfo(apiInfo())
					.additionalModels(typeResolver.resolve(Problem.class))
					.tags(
							new Tag("Users", "Endpoint for users resource requests."))
					.globalResponseMessage(RequestMethod.GET, globalGetResponseMessages())
					.globalResponseMessage(RequestMethod.POST, globalPostResponseMessages())
					.globalResponseMessage(RequestMethod.PUT, globalPutResponseMessages())
					.globalResponseMessage(RequestMethod.DELETE, globalDeleteResponseMessages());
	}
	
	public List<ResponseMessage> globalGetResponseMessages() {
		return Arrays.asList(
					new ResponseMessageBuilder()
						.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
						.message("Internal server error.")
						.responseModel(new ModelRef("Problem"))
						.build(),
					new ResponseMessageBuilder()
						.code(HttpStatus.NOT_ACCEPTABLE.value())
						.message("Endpoint with no support for the ContentType requested by client.")
						.build()
				);
	}
	
	public List<ResponseMessage> globalPostResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Internal server error.")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Endpoint with no support for the ContentType requested by client.")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Error within request payload.")
					.responseModel(new ModelRef("Problem"))
					.build()
				);
	}
	
	public List<ResponseMessage> globalPutResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Internal server error.")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_ACCEPTABLE.value())
					.message("Endpoint with no support for the ContentType requested by client.")
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.BAD_REQUEST.value())
					.message("Error within request payload.")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_FOUND.value())
					.message("Requested resource not found.")
					.responseModel(new ModelRef("Problem"))
					.build()
				);
	}
	
	public List<ResponseMessage> globalDeleteResponseMessages() {
		return Arrays.asList(
				new ResponseMessageBuilder()
					.code(HttpStatus.INTERNAL_SERVER_ERROR.value())
					.message("Internal server error.")
					.responseModel(new ModelRef("Problem"))
					.build(),
				new ResponseMessageBuilder()
					.code(HttpStatus.NOT_FOUND.value())
					.message("Requested resource not found.")
					.responseModel(new ModelRef("Problem"))
					.build()
				);
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("StartupOne API")
				.description("StartupOne API for Coworking Solution Academic Development.")
				.version("1")
				.contact(new Contact("Vinicius Felix", "https://github.com/vinicius-mfelix", "vinicius.matosfelix@gmail.com"))
				.build();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
			.addResourceLocations("classpath:/META-INF/resources/");
		
		registry.addResourceHandler("*/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
