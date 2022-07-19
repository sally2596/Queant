package com.ssafy.queant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//http://localhost/swagger-ui.html
//http://localhost/swagger-ui/index.html

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer{

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("Queant")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ssafy.queant.controller"))
//				.paths(PathSelectors.ant("/book/**"))
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Queant API")
				.description("<h2>SSAFY Queant API Reference for Developers</h2><img src=\"./img/Queant.png\">")
				.termsOfServiceUrl("https://edu.ssafy.com")
				.license("SSAFY License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp").version("1.0").build();
	}

}
