import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { Product } from '../../../core/models/product.model';
import { ProductsService } from '../services/products.service';

@Component({
    selector: 'app-product-form',
    standalone: true,
    imports: [
        CommonModule,
        ReactiveFormsModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatProgressSpinnerModule,
        MatSnackBarModule
    ],
    templateUrl: './product-form.component.html',
    styleUrls: ['./product-form.component.scss']
})
export class ProductFormComponent implements OnInit {
    productForm: FormGroup;
    isEditMode = false;
    loading = false;
    productId: number | null = null;

    constructor(
        private fb: FormBuilder,
        private productsService: ProductsService,
        private router: Router,
        private route: ActivatedRoute,
        private snackBar: MatSnackBar
    ) {
        this.productForm = this.fb.group({
            name: ['', [Validators.required, Validators.minLength(3)]],
            price: ['', [Validators.required, Validators.min(0)]]
        });
    }

    ngOnInit(): void {
        const id = this.route.snapshot.paramMap.get('id');
        if (id) {
            this.isEditMode = true;
            this.productId = +id;
            this.loadProduct(this.productId);
        }
    }

    loadProduct(id: number): void {
        this.loading = true;
        this.productsService.getById(id).subscribe({
            next: (response) => {
                this.productForm.patchValue(response.data);
                this.loading = false;
            },
            error: () => {
                this.loading = false;
                this.snackBar.open('Error loading product', 'Close', {
                    duration: 3000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top'
                });
                this.router.navigate(['/products']);
            }
        });
    }

    onSubmit(): void {
        if (this.productForm.valid) {
            this.loading = true;
            const product: Product = this.productForm.value;

            const request = this.isEditMode
                ? this.productsService.update(this.productId!, product)
                : this.productsService.create(product);

            request.subscribe({
                next: () => {
                    this.loading = false;
                    this.snackBar.open(
                        `Product ${this.isEditMode ? 'updated' : 'created'} successfully`,
                        'Close',
                        {
                            duration: 3000,
                            horizontalPosition: 'end',
                            verticalPosition: 'top'
                        }
                    );
                    this.router.navigate(['/products']);
                },
                error: () => {
                    this.loading = false;
                    this.snackBar.open(
                        `Error ${this.isEditMode ? 'updating' : 'creating'} product`,
                        'Close',
                        {
                            duration: 3000,
                            horizontalPosition: 'end',
                            verticalPosition: 'top'
                        }
                    );
                }
            });
        }
    }

    onCancel(): void {
        this.router.navigate(['/products']);
    }
} 