package com.increff.toyassure.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.increff.toyassure")
@PropertySources({ //
		@PropertySource(value = "file:./toyassure.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {


}
