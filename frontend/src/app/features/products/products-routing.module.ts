import { Routes } from '@angular/router';
import { ProductListComponent } from './list/product-list.component';
import { ProductFormComponent } from './form/product-form.component';

export const routes: Routes = [
    {
        path: '',
        component: ProductListComponent
    },
    {
        path: 'new',
        component: ProductFormComponent
    },
    {
        path: ':id',
        component: ProductFormComponent
    }
]; 