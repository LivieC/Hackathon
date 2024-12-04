import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token: string | null = null;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    // 使用 OAuth password grant flow
    const tokenUrl = `${environment.openshiftUrl}/oauth/token`;
    const formData = new URLSearchParams();
    formData.append('grant_type', 'password');
    formData.append('username', username);
    formData.append('password', password);
    
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic ' + btoa('openshift-challenging-client:') // 使用内置的 client
    });

    return this.http.post(tokenUrl, formData.toString(), { headers }).pipe(
      tap((response: any) => {
        this.token = response.access_token;
      })
    );
  }

  setToken(token: string): Observable<any> {
    this.token = token;
    return this.http.post(`${environment.apiUrl}/api/auth/token`, { token });
  }

  getToken(): string | null {
    return this.token;
  }

  logout(): void {
    this.token = null;
  }

  isLoggedIn(): boolean {
    return this.token !== null;
  }
} 