import {Component, OnInit} from '@angular/core';
import {Service} from "../services/Service";
import {Order} from "../interfaces/Order";
import {KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";
import {Product} from "../interfaces/Product";

@Component({
  selector: 'app-edit-order',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    KeyValuePipe
  ],
  templateUrl: './edit-order.component.html',
  styleUrl: './edit-order.component.css'
})
export class EditOrderComponent implements OnInit{
  public order!: Order;
  private orderId: string = "";
  message: string = "";
  constructor(private service: Service, private router: Router,
              private activatedRoute: ActivatedRoute) {
  }
  ngOnInit() {
    this.activatedRoute.params.subscribe(params => this.orderId = params['orderId']);
    this.getOrder();
  }
  public async getOrder(){
    this.order = await this.service.getOrder(this.orderId);
  }
  removeProductFromMap(productId: string){
    this.order.products.delete(productId);
    this.totalOfMap();
  }
  setNewQuantityToProduct(productId: string, quantity: number){
    if(this.order.products.get(productId)!.quantity + quantity > 0){
      this.order.products.get(productId)!.quantity += quantity;
      this.totalOfMap();
    }
  }
  totalOfMap(){
    this.order.totalDue = 0;
    this.order.products.forEach( product => {
      this.order.totalDue += (product.quantity * product.price)
    })
  }
  async editOrder() {
    let productsArray: Product[] = [];
    this.order.products.forEach(product => {
      productsArray.push(product);
    })
    this.order.products = new Map<string, Product>;
    productsArray.forEach(p => this.order.products.set(p.id, p))
    this.order = await this.service.editOrder(this.order, productsArray);
    this.getOrder();
    //this.router.navigate(["/edit-order", this.order.id])
  }
}
