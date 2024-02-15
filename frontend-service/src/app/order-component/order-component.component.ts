import {Component, OnInit} from '@angular/core';
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {Product} from "../interfaces/Product";
import {Order} from "../interfaces/Order";

@Component({
  selector: 'app-order-component',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    KeyValuePipe,
    JsonPipe
  ],
  templateUrl: './order-component.component.html',
  styleUrl: './order-component.component.css'
})
export class OrderComponentComponent implements OnInit{
  orders: Order[] = [];
  private customerId = "65c77eb06b4c6743eb5c83ec";
  constructor(private http: HttpClient) {
  }
  ngOnInit(): void {
    this.http.post(`http://localhost:8080/customers/orders/get-orders?customerId=${this.customerId}`, {})
      .subscribe({
          next: (data) => {
            if(Array.isArray(data))
              this.orders = data.map((order: any) => ({
                id: order.id,
                customerId: order.customerId,
                date: order.date,
                status: order.status,
                totalDue: order.totalDue,
                products: new Map<string, Product>(Object.entries(order.products))
              }));
            },
          error: (err) => {console.log(err);}
        }
      )
  }
}
