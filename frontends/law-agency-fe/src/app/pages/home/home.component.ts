import {Component, inject, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ProductCardComponent} from "../../shared/components/product-card/product-card.component";
import {ProductService} from "../../core/services/product.service";
import {take} from "rxjs";
import {ProductResponse} from "../../shared/model/response/product-response";
import {OrderService} from "../../core/services/order.service";
import {CreateOrderRequest} from "../../shared/model/requests/create-order-request";
import {CreateOrderResponse} from "../../shared/model/response/create-order-response";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ProductCardComponent],
  providers: [ProductService,OrderService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit {
  private readonly productsService = inject(ProductService)
  private readonly orderService = inject(OrderService)
  products: ProductResponse[] = []

  ngOnInit(): void {
    this.getProducts()
  }

  private getProducts() {
    this.productsService.getProducts()
      .pipe(take(1))
      .subscribe(x => {
        this.products = x
      })
  }

  onBuy($event: ProductResponse) {
    const productIdList:string[] = []
    const request: CreateOrderRequest = {
      buyerId: 'ea714e1f-f8f2-46a6-9b2e-2a3d9eefe3db',
      productIds: [
        ...productIdList,
        $event.id
      ]
    }
    this.orderService.createOrder(request)
      .pipe(take(1))
      .subscribe((response : CreateOrderResponse) => {
        window.location.href = response.redirectUrl;
      })

    // TODO from agency be send http to psp {amount, merchant id, api key, order id}
    // TODO receive url and do redication
  }
}
