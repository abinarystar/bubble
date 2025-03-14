package com.abinarystar.core.web;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@Import(ErrorControllerAdvice.class)
@PropertySource("classpath:web.properties")
public class WebAutoConfiguration {
}
