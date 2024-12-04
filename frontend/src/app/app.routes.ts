import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { DeploymentDashboardComponent } from './components/deployment-dashboard/deployment-dashboard.component';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DeploymentDashboardComponent,
    canActivate: [AuthGuard]
  }
]; 