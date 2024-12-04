package com.monitor.service;

import com.monitor.model.DeploymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DeploymentMonitorService {
    
    public List<DeploymentStatus> getAllDeploymentStatus() {
        List<DeploymentStatus> statusList = new ArrayList<>();
        
        // 添加 SIT 环境的部署状态
        addMockDeployment(statusList, "project1", "sit", "1.0.0", true);
        addMockDeployment(statusList, "project2", "sit", "1.1.0", true);
        
        // 添加 UAT 环境的部署状态
        addMockDeployment(statusList, "project1", "uat", "0.9.0", true);
        addMockDeployment(statusList, "project2", "uat", "1.0.0", false);
        
        // 添加 PROD 环境的部署状态
        addMockDeployment(statusList, "project1", "prod", "0.8.0", true);
        addMockDeployment(statusList, "project2", "prod", "0.9.0", true);
        
        return statusList;
    }

    private void addMockDeployment(List<DeploymentStatus> deployments, 
                                 String projectName, 
                                 String env, 
                                 String version, 
                                 boolean healthy) {
        DeploymentStatus status = new DeploymentStatus();
        status.setProjectName(projectName);
        status.setEnvironment(env);
        status.setVersion(version);
        status.setDeploymentName(projectName + "-deployment");
        status.setStatus(healthy ? "Available" : "Degraded");
        status.setLastUpdated(LocalDateTime.now());
        status.setReadyReplicas(healthy ? 3 : 1);
        status.setTotalReplicas(3);
        status.setNamespace(env);
        status.setClusterUrl("https://mock-cluster:8443");
        status.setHealthy(healthy);
        
        deployments.add(status);
    }
} 