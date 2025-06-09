import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

export interface AuthResponse {
    access_token: string;
    token_type: string;
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private readonly TOKEN_KEY = 'access_token';
    private isAuthenticatedSubject = new BehaviorSubject<boolean>(this.hasToken());

    constructor(private http: HttpClient) { }

    login(username: string, password: string): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${environment.apiUrl}/auth/login`, { username, password })
            .pipe(
                tap(response => {
                    localStorage.setItem(this.TOKEN_KEY, response.access_token);
                    this.isAuthenticatedSubject.next(true);
                })
            );
    }

    logout(): void {
        localStorage.removeItem(this.TOKEN_KEY);
        this.isAuthenticatedSubject.next(false);
    }

    getToken(): string | null {
        return localStorage.getItem(this.TOKEN_KEY);
    }

    isAuthenticated(): Observable<boolean> {
        return this.isAuthenticatedSubject.asObservable();
    }

    private hasToken(): boolean {
        return !!this.getToken();
    }
} 