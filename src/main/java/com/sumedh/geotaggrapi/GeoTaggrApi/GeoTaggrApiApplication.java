package com.sumedh.geotaggrapi.GeoTaggrApi;

import com.sumedh.geotaggrapi.GeoTaggrApi.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GeoTaggrApiApplication {

	@Autowired
	AuthFilter authFilter;

	@Bean
	public FilterRegistrationBean<AuthFilter> filterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();

		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/tags/*");

		return registrationBean;
	}



	public static void main(String[] args) {
		SpringApplication.run(GeoTaggrApiApplication.class, args);
	}

}
