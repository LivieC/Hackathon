package com.monitor.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledMonitorService {

    private final DeploymentMonitorService deploymentMonitorService;

    @Scheduled(fixedRateString = "${monitor.refresh.rate:30000}")
    public void updateDeploymentStatus() {
        log.debug("Updating deployment status...");
        deploymentMonitorService.getAllDeploymentStatus();
    }
} 