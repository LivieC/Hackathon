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
} 