import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
    {
        path: 'products',
        loadChildren: () => import('./features/products/products-routing.module').then(m => m.routes),
        canActivate: [authGuard]
    },
    {
        path: 'auth',
        loadChildren: () => import('./features/auth/auth-routing.module').then(m => m.routes)
    },
    {
        path: '',
        redirectTo: 'products',
        pathMatch: 'full'
    }
];
