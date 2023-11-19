import {Component, EventEmitter, Input, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import {ButtonComponent} from "../button/button.component";
import {ProductResponse} from "../../model/response/product-response";

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CommonModule, ButtonComponent],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.scss'
})
export class ProductCardComponent {
@Input() product : ProductResponse | undefined
@Output() buy : EventEmitter<ProductResponse>= new EventEmitter<ProductResponse>()
  onBuy() {
    this.buy.emit(this.product)
  }
}
