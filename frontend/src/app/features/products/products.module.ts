import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule } from '@angular/material/snack-bar';

import { ProductListComponent } from './list/product-list.component';
import { ProductFormComponent } from './form/product-form.component';
import { ProductsService } from './services/products.service';
import { ProductsRoutingModule } from './products-routing.module';

@NgModule({
    declarations: [
        ProductListComponent,
        ProductFormComponent
    ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        RouterModule,
        ProductsRoutingModule,
        MatTableModule,
        MatButtonModule,
        MatIconModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatProgressSpinnerModule,
        MatSnackBarModule
    ],
    providers: [
        ProductsService
    ]
})
export class ProductsModule { } 