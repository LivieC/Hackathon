export interface DeploymentStatus {
    projectName: string;
    environment: string;
    version: string;
    status: string;
    lastUpdated: Date;
    deploymentName: string;
    readyReplicas: number;
    totalReplicas: number;
    namespace: string;
    clusterUrl: string;
    healthy: boolean;
} 