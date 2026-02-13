package com.freecall.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FreeSwitchProperties.class)
public class FreeSwitchConfig {
}
