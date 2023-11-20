import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {httpOptions} from "./http.options";
import {CreateOrderRequest} from "../../shared/model/requests/create-order-request";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
private readonly http = inject(HttpClient)
  private readonly baseUrl = 'https://localhost:7275/api/orders'
  constructor() { }
  createOrder(request: CreateOrderRequest){
  return this.http.post(`${this.baseUrl}`,request,httpOptions)
  }

}
