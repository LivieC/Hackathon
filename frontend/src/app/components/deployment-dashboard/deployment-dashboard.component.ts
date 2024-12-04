import { Component, OnInit } from '@angular/core';
import { DeploymentService } from '../../services/deployment.service';
import { DeploymentStatus } from '../../models/deployment-status';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-deployment-dashboard',
  templateUrl: './deployment-dashboard.component.html',
  styleUrls: ['./deployment-dashboard.component.css']
})
export class DeploymentDashboardComponent implements OnInit {
  deployments: DeploymentStatus[] = [];
  filteredDeployments: DeploymentStatus[] = [];
  selectedEnv: string = 'sit';
  loading = true;
  error: string | null = null;

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
      },
      error: (error) => {
        this.error = 'Failed to load deployment status';
        this.loading = false;
        console.error('Error fetching deployments:', error);
      }
    });
  }

  filterDeployments(): void {
    this.filteredDeployments = this.deployments.filter(
      d => d.environment.toLowerCase() === this.selectedEnv.toLowerCase()
    );
  }

  exportToExcel(): void {
    const worksheet = XLSX.utils.json_to_sheet(this.filteredDeployments);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Deployments');
    XLSX.writeFile(workbook, 'deployments.xlsx');
  }
} 