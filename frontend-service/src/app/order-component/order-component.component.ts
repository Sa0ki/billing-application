import {Component, OnInit} from '@angular/core';
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {Order} from "../interfaces/Order";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Service} from "../services/Service";

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
  public orders: Order[] = [];
  private customerId: string = "65c77eb06b4c6743eb5c83ec";
  constructor(private http: HttpClient, private navigateRouter: Router,
              private activatedRoute: ActivatedRoute, private service: Service) {
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      this.customerId = params['customerId']
      this.getOrders();
    })
  }
  public async getOrders(){
    this.orders = await this.service.getOrders(this.customerId);
  }
  navigateToOrderDetails(orderId: string) {
    this.navigateRouter.navigate(['/order-details', orderId]);
  }
}
