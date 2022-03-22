package com.increff.channel.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.increff.channel")
@PropertySources({ //
		@PropertySource(value = "file:./channel.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
