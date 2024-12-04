package com.monitor.service;

import com.monitor.model.DeploymentStatus;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Profile("dev")
public class MockDeploymentService extends DeploymentMonitorService {

    public MockDeploymentService() {
        super(null); // Pass null as we won't use the OpenShiftClient in mock
    }

    @Override
    public List<DeploymentStatus> getAllDeploymentStatus() {
        List<DeploymentStatus> mockDeployments = new ArrayList<>();
        
        // Add SIT deployments
        addMockDeployment(mockDeployments, "project1", "sit", "1.0.0", true);
        addMockDeployment(mockDeployments, "project2", "sit", "1.1.0", true);
        
        // Add UAT deployments
        addMockDeployment(mockDeployments, "project1", "uat", "0.9.0", true);
        addMockDeployment(mockDeployments, "project2", "uat", "1.0.0", false);
        
        // Add PROD deployments
        addMockDeployment(mockDeployments, "project1", "prod", "0.8.0", true);
        addMockDeployment(mockDeployments, "project2", "prod", "0.9.0", true);
        
        return mockDeployments;
    }

    private void addMockDeployment(List<DeploymentStatus> deployments, 
                                 String projectName, 
                                 String env, 
                                 String version, 
                                 boolean healthy) {
        DeploymentStatus status = new DeploymentStatus(projectName, env, version);
        status.setDeploymentName(projectName + "-deployment");
        status.setStatus(healthy ? "Available" : "Degraded");
        status.setReadyReplicas(healthy ? 3 : 1);
        status.setTotalReplicas(3);
        status.setNamespace(env);
        status.setClusterUrl("https://mock-cluster:8443");
        status.setHealthy(healthy);
        
        deployments.add(status);
    }
} 