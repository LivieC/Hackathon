package com.monitor.controller;

import com.monitor.model.DeploymentStatus;
import com.monitor.service.DeploymentMonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deployments")
@RequiredArgsConstructor
public class DeploymentController {

    private final DeploymentMonitorService deploymentMonitorService;
    
    @GetMapping
    public List<DeploymentStatus> getAllDeploymentStatus() {
        return deploymentMonitorService.getAllDeploymentStatus();
    }
} 