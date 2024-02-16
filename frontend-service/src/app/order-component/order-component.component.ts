import {Component, OnInit} from '@angular/core';
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {Product} from "../interfaces/Product";
import {Order} from "../interfaces/Order";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-order-component',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    KeyValuePipe,
    JsonPipe,
    RouterLink
  ],
  templateUrl: './order-component.component.html',
  styleUrl: './order-component.component.css'
})
export class OrderComponentComponent implements OnInit{
  private apiUrl: string = "http://localhost:8080/customers";
  public orders: Order[] = [];
  private customerId: string = "";
  constructor(private http: HttpClient, private navigateRouter: Router,
              private activatedRoute: ActivatedRoute) {
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      console.log(params["customerId"])
      this.customerId = params['customerId']
    })
    this.http.post<Order[]>(`${this.apiUrl}/orders/get-orders?customerId=${this.customerId}`, {})
      .subscribe({
          next: (data: Order[]) => {
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
  navigateToOrderDetails(orderId: string) {
    this.navigateRouter.navigate(['/order-details', orderId]);
  }
}
