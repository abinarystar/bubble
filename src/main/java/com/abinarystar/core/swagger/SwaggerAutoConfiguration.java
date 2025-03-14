package com.abinarystar.core.swagger;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@PropertySource("classpath:swagger.properties")
public class SwaggerAutoConfiguration {
}
