package com.monitor.service;

import com.monitor.model.DeploymentStatus;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.openshift.client.OpenShiftClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@Profile("!dev")
@RequiredArgsConstructor
public class DeploymentMonitorService {

    protected final OpenShiftClient openshiftClient;
    
    public List<DeploymentStatus> getAllDeploymentStatus() {
        try {
            List<DeploymentStatus> statusList = new ArrayList<>();
            
            openshiftClient.projects().list().getItems().forEach(project -> {
                try {
                    openshiftClient.apps().deployments()
                            .inNamespace(project.getMetadata().getName())
                            .list()
                            .getItems()
                            .forEach(deployment -> {
                                DeploymentStatus status = createDeploymentStatus(project.getMetadata().getName(), deployment);
                                statusList.add(status);
                            });
                } catch (Exception e) {
                    log.error("Error fetching deployments for project {}: {}", 
                            project.getMetadata().getName(), e.getMessage());
                }
            });
            
            return statusList;
        } catch (Exception e) {
            log.error("Error fetching deployment status", e);
            return Collections.emptyList();
        }
    }

    private DeploymentStatus createDeploymentStatus(String projectName, Deployment deployment) {
        DeploymentStatus status = new DeploymentStatus();
        status.setProjectName(projectName);
        status.setDeploymentName(deployment.getMetadata().getName());
        status.setEnvironment(deployment.getMetadata().getLabels().getOrDefault("environment", "unknown"));
        status.setVersion(deployment.getMetadata().getLabels().getOrDefault("version", "unknown"));
        status.setStatus(deployment.getStatus().getConditions().isEmpty() ? "Unknown" : 
                deployment.getStatus().getConditions().get(0).getType());
        status.setLastUpdated(LocalDateTime.now());
        status.setReadyReplicas(deployment.getStatus().getReadyReplicas() != null ? 
                deployment.getStatus().getReadyReplicas() : 0);
        status.setTotalReplicas(deployment.getStatus().getReplicas() != null ? 
                deployment.getStatus().getReplicas() : 0);
        status.setNamespace(deployment.getMetadata().getNamespace());
        status.setClusterUrl(openshiftClient.getMasterUrl().toString());
        status.setHealthy(isDeploymentHealthy(deployment));
        return status;
    }

    private boolean isDeploymentHealthy(Deployment deployment) {
        Integer readyReplicas = deployment.getStatus().getReadyReplicas();
        Integer desiredReplicas = deployment.getSpec().getReplicas();
        return readyReplicas != null && desiredReplicas != null && readyReplicas.equals(desiredReplicas);
    }
} 