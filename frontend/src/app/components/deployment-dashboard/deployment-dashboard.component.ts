import { Component, OnInit } from '@angular/core';
import { DeploymentService } from '../../services/deployment.service';
import { DeploymentStatus } from '../../models/deployment-status';

@Component({
  selector: 'app-deployment-dashboard',
  templateUrl: './deployment-dashboard.component.html',
  styleUrls: ['./deployment-dashboard.component.css']
})
export class DeploymentDashboardComponent implements OnInit {
  deployments: DeploymentStatus[] = [];
  filteredDeployments: DeploymentStatus[] = [];
  loading = true;
  error: string | null = null;
  showHealthyOnly: boolean = false;

  constructor(private deploymentService: DeploymentService) {}

  ngOnInit(): void {
    this.loadDeployments();
  }

  loadDeployments(): void {
    this.deploymentService.getDeployments().subscribe({
      next: (data) => {
        this.deployments = data;
        this.filterDeployments();
        this.loading = false;
        this.error = null;
      },
      error: (error) => {
        this.error = 'Failed to load deployment status';
        this.loading = false;
        console.error('Error fetching deployments:', error);
      }
    });
  }

  filterDeployments(): void {
    this.filteredDeployments = this.deployments.filter(deployment => {
      return !this.showHealthyOnly || deployment.healthy;
    });
  }
} 