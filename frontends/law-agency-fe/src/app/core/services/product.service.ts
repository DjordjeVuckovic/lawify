import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {httpOptions} from "./http.options";
import {Observable} from "rxjs";
import {ProductResponse} from "../../shared/model/response/product-response";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private readonly http = inject(HttpClient)
  private readonly baseUrl = 'https://localhost:7275/api/products'

  constructor() {
  }

  getProducts(): Observable<ProductResponse[]> {
    return this.http.get<ProductResponse[]>(`${this.baseUrl}`, httpOptions)
  }
}
