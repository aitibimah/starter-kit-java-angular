import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Product, ApiResponse } from '../../../core/models/product.model';

@Injectable({
    providedIn: 'root'
})
export class ProductsService {
    private readonly apiUrl = `${environment.apiUrl}/products`;

    constructor(private http: HttpClient) { }

    getAll(): Observable<ApiResponse<Product[]>> {
        return this.http.get<ApiResponse<Product[]>>(this.apiUrl);
    }

    getById(id: number): Observable<ApiResponse<Product>> {
        return this.http.get<ApiResponse<Product>>(`${this.apiUrl}/${id}`);
    }

    create(product: Product): Observable<ApiResponse<Product>> {
        return this.http.post<ApiResponse<Product>>(this.apiUrl, product);
    }

    update(id: number, product: Product): Observable<ApiResponse<Product>> {
        return this.http.put<ApiResponse<Product>>(`${this.apiUrl}/${id}`, product);
    }

    delete(id: number): Observable<ApiResponse<void>> {
        return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
    }
} 