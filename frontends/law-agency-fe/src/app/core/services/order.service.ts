import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {httpOptions} from "./http.options";
import {CreateOrderRequest} from "../../shared/model/requests/create-order-request";
import {Observable} from "rxjs";
import {CreateOrderResponse} from "../../shared/model/response/create-order-response";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private readonly http = inject(HttpClient)
  private readonly baseUrl = 'https://localhost:7275/api/orders'

  constructor() {
  }

  createOrder(request: CreateOrderRequest): Observable<CreateOrderResponse> {
    return this.http.post<CreateOrderResponse>(`${this.baseUrl}`, request, httpOptions)
  }

}
