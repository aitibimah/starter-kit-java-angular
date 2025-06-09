import { Component, OnInit, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Product } from '../../../core/models/product.model';
import { ProductsService } from '../services/products.service';

@Component({
    selector: 'app-product-list',
    standalone: true,
    imports: [
        CommonModule,
        MatTableModule,
        MatButtonModule,
        MatIconModule,
        MatCardModule,
        MatProgressSpinnerModule,
        MatSnackBarModule
    ],
    templateUrl: './product-list.component.html',
    styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
    displayedColumns: string[] = ['id', 'name', 'price', 'actions'];
    dataSource = new MatTableDataSource<Product>();
    loading = false;

    @ViewChild(MatPaginator) paginator!: MatPaginator;
    @ViewChild(MatSort) sort!: MatSort;

    constructor(
        private productsService: ProductsService,
        private router: Router,
        private snackBar: MatSnackBar
    ) { }

    ngOnInit(): void {
        this.loadProducts();
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    loadProducts(): void {
        this.loading = true;
        this.productsService.getAll().subscribe({
            next: (response) => {
                this.dataSource.data = response.data;
                this.loading = false;
            },
            error: () => {
                this.loading = false;
                this.snackBar.open('Error loading products', 'Close', {
                    duration: 5000,
                    horizontalPosition: 'end',
                    verticalPosition: 'top',
                    panelClass: ['error-snackbar']
                });
            }
        });
    }

    onEdit(id: number): void {
        this.router.navigate(['/products', id]);
    }

    onDelete(id: number): void {
        if (confirm('Are you sure you want to delete this product?')) {
            this.productsService.delete(id).subscribe({
                next: () => {
                    this.snackBar.open('Product deleted successfully', 'Close', {
                        duration: 3000,
                        horizontalPosition: 'end',
                        verticalPosition: 'top'
                    });
                    this.loadProducts();
                },
                error: () => {
                    this.snackBar.open('Error deleting product', 'Close', {
                        duration: 5000,
                        horizontalPosition: 'end',
                        verticalPosition: 'top',
                        panelClass: ['error-snackbar']
                    });
                }
            });
        }
    }

    onCreate(): void {
        this.router.navigate(['/products/new']);
    }

    applyFilter(event: Event) {
        const filterValue = (event.target as HTMLInputElement).value;
        this.dataSource.filter = filterValue.trim().toLowerCase();

        if (this.dataSource.paginator) {
            this.dataSource.paginator.firstPage();
        }
    }
} 