import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';

export const apiInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const snackBar = inject(MatSnackBar);

    const token = authService.getToken();
    if (token) {
        req = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }

    return next(req).pipe(
        catchError(error => {
            if (error.status === 401) {
                authService.logout();
                router.navigate(['/auth/login']);
                snackBar.open('Session expired. Please login again.', 'Close', {
                    duration: 3000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top',
                    panelClass: ['error-snackbar']
                });
            } else {
                snackBar.open(error.error?.message || 'An error occurred', 'Close', {
                    duration: 3000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top',
                    panelClass: ['error-snackbar']
                });
            }
            return throwError(() => error);
        })
    );
}; 