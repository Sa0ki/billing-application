import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../interfaces/Order";
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {Product} from "../interfaces/Product";
import {ActivatedRoute, Route, Router} from "@angular/router";

@Component({
  selector: 'app-order-details',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    KeyValuePipe,
    JsonPipe
  ],
  templateUrl: './order-details.component.html',
  styleUrl: './order-details.component.css'
})
export class OrderDetailsComponent implements OnInit{
  private apiUrl: string = "http://localhost:8080/customers";
  private orderId: string = "";
  public order: Order | undefined;
  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute,
              private navigateRoute: Router) {
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => this.orderId = params['orderId']);
    this.http.post<Order>(`${this.apiUrl}/orders/get-order?orderId=${this.orderId}`, {})
      .subscribe({
        next: (data: Order) => {
          this.order = {
            id: data.id,
            customerId: data.customerId,
            date: data.date,
            status: data.status,
            totalDue: data.totalDue,
            products: new Map<string, Product>(Object.entries(data.products))
          };
        },
        error: (err) => {
          console.log(err);
        }
      });
  }
  navigateToGetBill(orderId: string){
    this.navigateRoute.navigate(["/bill", orderId])
  }
}
