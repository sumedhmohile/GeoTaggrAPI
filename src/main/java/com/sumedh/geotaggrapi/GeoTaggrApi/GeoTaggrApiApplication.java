package com.sumedh.geotaggrapi.GeoTaggrApi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.sumedh.geotaggrapi.GeoTaggrApi.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

@SpringBootApplication
public class GeoTaggrApiApplication {

	@Autowired
	AuthFilter authFilter;

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/tags/*");
		registrationBean.addUrlPatterns("/api/users/fcm/*");

		return registrationBean;
	}



	public static void main(String[] args) {
		SpringApplication.run(GeoTaggrApiApplication.class, args);
	}

}
