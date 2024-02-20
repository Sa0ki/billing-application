import {Component, OnInit} from '@angular/core';
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {HttpClient} from "@angular/common/http";
import {Order} from "../interfaces/Order";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {Service} from "../services/Service";
import {AppStateService} from "../app.state.service";

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
  private customerId: string = "";
  constructor(private http: HttpClient, private navigateRouter: Router,
              private activatedRoute: ActivatedRoute, private service: Service,
              public appStateService: AppStateService) {
  }
  ngOnInit(): void {
      this.getOrders();
  }
  public async getOrders(){
    this.orders = await this.service.getOrders();
  }
  navigateToOrderDetails(orderId: string) {
    this.navigateRouter.navigate(['/order-details', orderId]);
  }
}
