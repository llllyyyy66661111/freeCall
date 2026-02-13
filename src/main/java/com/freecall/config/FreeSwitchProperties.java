package com.freecall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "freeswitch")
public record FreeSwitchProperties(String host, int port, String password, int timeoutSeconds) {
}
