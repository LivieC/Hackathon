import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { CryptoService } from './crypto.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = `${environment.apiUrl}/api/auth`;
  private isAuthenticated = false;

  constructor(
    private http: HttpClient,
    private cryptoService: CryptoService
  ) {}

  login(username: string, password: string): Observable<any> {
    const encryptedPassword = this.cryptoService.encryptPassword(password);
    return this.http.post(`${this.apiUrl}/login`, { 
      username, 
      password: encryptedPassword 
    }).pipe(
      tap(() => this.isAuthenticated = true)
    );
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  logout(): void {
    this.isAuthenticated = false;
  }
} 