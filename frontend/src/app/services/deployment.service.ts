import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DeploymentStatus } from '../models/deployment-status';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DeploymentService {
  private apiUrl = `${environment.apiUrl}/api/deployments`;

  constructor(private http: HttpClient) { }

  getDeployments(): Observable<DeploymentStatus[]> {
    return this.http.get<DeploymentStatus[]>(this.apiUrl);
  }
} 