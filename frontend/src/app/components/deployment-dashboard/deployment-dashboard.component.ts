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
  loading = true;
  error: string | null = null;
  showHealthyOnly: boolean = false;
  searchQuery: string = '';
  selectedNamespace: string = '';
  userInfo: { name: string; email: string } = { name: 'User Name', email: 'user@example.com' };

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
      const matchesHealthy = !this.showHealthyOnly || deployment.healthy;
      const matchesQuery = deployment.projectName.toLowerCase().includes(this.searchQuery.toLowerCase());
      const matchesNamespace = this.selectedNamespace ? deployment.namespace === this.selectedNamespace : true;

      return matchesHealthy && matchesQuery && matchesNamespace;
    });
  }

  exportToExcel(): void {
    const worksheet = XLSX.utils.json_to_sheet(this.filteredDeployments);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Deployments');
    XLSX.writeFile(workbook, 'deployments.xlsx');
  }
} 