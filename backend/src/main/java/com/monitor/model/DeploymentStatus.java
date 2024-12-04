package com.monitor.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DeploymentStatus {
    private String projectName;
    private String environment;
    private String version;
    private String status;
    private LocalDateTime lastUpdated;
    private String deploymentName;
    private int readyReplicas;
    private int totalReplicas;
    private String namespace;
    private String clusterUrl;
    private boolean healthy;

    // Default constructor
    public DeploymentStatus() {
        this.lastUpdated = LocalDateTime.now();
    }

    // Constructor with essential fields
    public DeploymentStatus(String projectName, String environment, String version) {
        this();
        this.projectName = projectName;
        this.environment = environment;
        this.version = version;
    }
} 