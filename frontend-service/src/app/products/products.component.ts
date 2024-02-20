import {Component, OnInit} from '@angular/core';
import {AppStateService} from "../app.state.service";
import {Service} from "../services/Service";
import {Product} from "../interfaces/Product";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [
    NgForOf,
    NgIf
  ],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent implements OnInit{
  products: Product[] = [];
  constructor(public appStateService: AppStateService, private service: Service) {
  }
  ngOnInit() {
    this.getProducts();
  }
  public async getProducts(){
    this.products = await this.service.getProducts();
  }
  public addToCart(productId: string, quantity: number){
    this.service.addToCart(productId, quantity);
  }
}
