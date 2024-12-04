package com.monitor.config;

import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.openshift.client.DefaultOpenShiftClient;
import io.fabric8.openshift.client.OpenShiftClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class OpenshiftConfig {
    
    @Value("${openshift.url:#{null}}")
    private String masterUrl;
    
    @Value("${openshift.username:#{null}}")
    private String username;
    
    @Value("${openshift.password:#{null}}")
    private String password;
    
    @Bean
    @Profile("!test")
    public OpenShiftClient openshiftClient() {
        if (masterUrl == null || username == null || password == null) {
            throw new IllegalStateException(
                "OpenShift configuration is incomplete. Please set openshift.url, openshift.username, and openshift.password"
            );
        }

        Config config = new ConfigBuilder()
                .withMasterUrl(masterUrl)
                .withUsername(username)
                .withPassword(password)
                .withTrustCerts(true)
                .build();
        
        return new DefaultOpenShiftClient(config);
    }
} 