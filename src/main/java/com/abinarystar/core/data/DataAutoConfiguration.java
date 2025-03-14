package com.abinarystar.core.data;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@AutoConfiguration
@EnableConfigurationProperties(DataProperties.class)
@Import(DataAuditingConfiguration.class)
@PropertySource("classpath:data.properties")
public class DataAutoConfiguration {
}
