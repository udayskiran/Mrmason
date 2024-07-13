package com.application.mrmason.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvnWebConfig implements WebMvcConfigurer {

//	@Override
//	public void addCorsMappings(final CorsRegistry registry) {
//		registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("*").maxAge(3600);
//
//	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**").allowedOrigins("http://localhost:54288","https://localhost:4200")
		registry.addMapping("/**").allowedOrigins("/**")
				.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*").allowCredentials(true)
				.maxAge(3600);
	}
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("http://localhost:3000", "https://localhost:3000", "http://13.200.209.39", "https://13.200.209.39")
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(true).maxAge(3600);
//    }
}
