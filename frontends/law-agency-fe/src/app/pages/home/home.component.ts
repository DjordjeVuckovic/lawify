import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ProductCardComponent} from "../../shared/components/product-card/product-card.component";
import {ProductService} from "../../core/services/product.service";
import {take} from "rxjs";
import {ProductResponse} from "../../shared/model/response/product-response";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, ProductCardComponent],
  providers: [ProductService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{
  private readonly productsService = inject(ProductService)
  products : ProductResponse[] =[]
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
  onBuy($event : ProductResponse){
    console.log($event)
  }
}
