import { Component, OnInit, OnDestroy } from '@angular/core';
import { DeploymentService } from '../../services/deployment.service';
import { DeploymentStatus } from '../../models/deployment-status';
import { interval, Subscription } from 'rxjs';
import { startWith, switchMap } from 'rxjs/operators';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-deployment-dashboard',
  templateUrl: './deployment-dashboard.component.html',
  styleUrls: ['./deployment-dashboard.component.css']
})
export class DeploymentDashboardComponent implements OnInit, OnDestroy {
  deployments: DeploymentStatus[] = [];
  filteredDeployments: DeploymentStatus[] = [];
  selectedEnv: string = 'sit';
  loading = true;
  error: string | null = null;
  private refreshSubscription?: Subscription;

  constructor(private deploymentService: DeploymentService) {}

  ngOnInit(): void {
    this.refreshSubscription = interval(30000)
      .pipe(
        startWith(0),
        switchMap(() => this.deploymentService.getDeployments())
      )
      .subscribe({
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
    this.filteredDeployments = this.deployments.filter(
      d => d.environment.toLowerCase() === this.selectedEnv.toLowerCase()
    );
  }

  ngOnDestroy(): void {
    if (this.refreshSubscription) {
      this.refreshSubscription.unsubscribe();
    }
  }

  exportToExcel(): void {
    const worksheet = XLSX.utils.json_to_sheet(this.filteredDeployments);
    const workbook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(workbook, worksheet, 'Deployments');
    XLSX.writeFile(workbook, 'deployments.xlsx');
  }
} 