package com.increff.ui.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@ComponentScan("com.increff.ui")
@PropertySources({ //
		@PropertySource(value = "file:./ui.properties", ignoreResourceNotFound = true) //
})
public class SpringConfig {


}
