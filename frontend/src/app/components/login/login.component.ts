import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  token: string = '';
  error: string | null = null;
  isTokenLogin: boolean = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    if (this.isTokenLogin) {
      this.authService.setToken(this.token).subscribe({
        next: () => {
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          this.error = 'Token login failed';
          console.error('Error logging in with token:', error);
        }
      });
    } else {
      this.authService.login(this.username, this.password).subscribe({
        next: () => {
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          this.error = 'Login failed. Please check your credentials.';
          console.error('Error logging in:', error);
        }
      });
    }
  }
} 