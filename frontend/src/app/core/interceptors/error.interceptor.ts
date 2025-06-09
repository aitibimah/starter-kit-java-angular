import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
    const snackBar = inject(MatSnackBar);

    return next(req).pipe(
        catchError(error => {
            let errorMessage = 'An error occurred';

            if (error.error instanceof ErrorEvent) {
                // Client-side error
                errorMessage = error.error.message;
            } else {
                // Server-side error
                switch (error.status) {
                    case 400:
                        errorMessage = 'Bad request';
                        break;
                    case 401:
                        errorMessage = 'Unauthorized';
                        break;
                    case 403:
                        errorMessage = 'Forbidden';
                        break;
                    case 404:
                        errorMessage = 'Not found';
                        break;
                    case 500:
                        errorMessage = 'Internal server error';
                        break;
                    default:
                        errorMessage = `Error: ${error.status}`;
                }
            }

            snackBar.open(errorMessage, 'Close', {
                duration: 5000,
                horizontalPosition: 'end',
                verticalPosition: 'top',
                panelClass: ['error-snackbar']
            });

            return throwError(() => error);
        })
    );
}; 