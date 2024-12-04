import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap, switchMap } from 'rxjs/operators';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token: string | null = null;

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    // OpenShift OAuth token endpoint
    const tokenUrl = `${environment.openshiftUrl}/oauth/authorize/token`;
    const credentials = btoa(`${username}:${password}`);
    
    const headers = new HttpHeaders({
      'Authorization': `Basic ${credentials}`,
      'Content-Type': 'application/x-www-form-urlencoded'
    });

    const body = 'grant_type=password&scope=user:full';

    return this.http.post(tokenUrl, body, { headers }).pipe(
      tap((response: any) => {
        this.token = response.access_token;
      }),
      switchMap(() => {
        // 确保 token 不为 null
        if (!this.token) {
          throw new Error('Failed to obtain token');
        }
        return this.sendTokenToBackend(this.token);
      })
    );
  }

  private sendTokenToBackend(token: string): Observable<any> {
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