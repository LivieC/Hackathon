package com.monitor.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monitor.model.DeploymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeploymentMonitorService {
    
    private final OpenShiftApiService openShiftApiService;
    private final ObjectMapper objectMapper;
    
    public List<DeploymentStatus> getAllDeploymentStatus() {
        try {
            List<DeploymentStatus> allDeployments = new ArrayList<>();
            
            // 获取项目列表
            String projectsJson = openShiftApiService.getProjects();
            JsonNode projectsNode = objectMapper.readTree(projectsJson);
            
            // 遍历每个项目
            projectsNode.path("items").forEach(project -> {
                String namespace = project.path("metadata").path("name").asText();
                try {
                    // 获取部署信息
                    String deploymentsJson = openShiftApiService.getDeployments(namespace);
                    JsonNode deploymentsNode = objectMapper.readTree(deploymentsJson);
                    
                    // 处理每个部署
                    deploymentsNode.path("items").forEach(deployment -> {
                        DeploymentStatus status = createDeploymentStatus(namespace, deployment);
                        allDeployments.add(status);
                    });
                } catch (Exception e) {
                    log.error("Error fetching deployments for namespace {}: {}", namespace, e.getMessage());
                }
            });
            
            return allDeployments;
        } catch (Exception e) {
            log.error("Error getting deployment status", e);
            return new ArrayList<>();
        }
    }
    
    private DeploymentStatus createDeploymentStatus(String namespace, JsonNode deployment) {
        JsonNode metadata = deployment.path("metadata");
        JsonNode spec = deployment.path("spec");
        JsonNode status = deployment.path("status");
        
        DeploymentStatus deployStatus = new DeploymentStatus();
        deployStatus.setProjectName(namespace);
        deployStatus.setDeploymentName(metadata.path("name").asText());
        deployStatus.setEnvironment(metadata.path("labels").path("environment").asText("unknown"));
        deployStatus.setVersion(metadata.path("labels").path("version").asText("unknown"));
        deployStatus.setStatus(getDeploymentStatus(status));
        deployStatus.setLastUpdated(LocalDateTime.now());
        deployStatus.setReadyReplicas(status.path("readyReplicas").asInt(0));
        deployStatus.setTotalReplicas(spec.path("replicas").asInt(0));
        deployStatus.setNamespace(namespace);
        deployStatus.setClusterUrl(openShiftApiService.getBaseUrl());
        deployStatus.setHealthy(isDeploymentHealthy(status, spec));
        
        return deployStatus;
    }
    
    private String getDeploymentStatus(JsonNode status) {
        if (status.path("conditions").isEmpty()) return "Unknown";
        return status.path("conditions").get(0).path("type").asText("Unknown");
    }
    
    private boolean isDeploymentHealthy(JsonNode status, JsonNode spec) {
        int readyReplicas = status.path("readyReplicas").asInt(0);
        int desiredReplicas = spec.path("replicas").asInt(0);
        return readyReplicas == desiredReplicas && desiredReplicas > 0;
    }
} 