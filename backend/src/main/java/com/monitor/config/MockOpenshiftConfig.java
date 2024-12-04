//package com.monitor.config;
//
//import io.fabric8.kubernetes.api.model.*;
//import io.fabric8.openshift.client.OpenShiftClient;
//import io.fabric8.openshift.client.server.mock.OpenShiftMockServer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//
//@Configuration
//@Profile("dev")
//public class MockOpenshiftConfig {
//
//    @Bean
//    public OpenShiftClient mockOpenShiftClient() {
//        OpenShiftMockServer mockServer = new OpenShiftMockServer();
//
//        // Create mock deployment data
//        mockServer.expect().get().withPath("/api/v1/namespaces")
//                .andReturn(200, new NamespaceListBuilder()
//                        .addNewItem()
//                            .withNewMetadata()
//                                .withName("sit")
//                            .endMetadata()
//                        .endItem()
//                        .addNewItem()
//                            .withNewMetadata()
//                                .withName("uat")
//                            .endMetadata()
//                        .endItem()
//                        .build()
//                ).always();
//
//        return mockServer.createOpenShiftClient();
//    }
//}