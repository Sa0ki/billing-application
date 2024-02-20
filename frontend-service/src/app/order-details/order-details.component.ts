import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../interfaces/Order";
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {Product} from "../interfaces/Product";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {Service} from "../services/Service";
import {AppStateService} from "../app.state.service";

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
  private orderId: string = "";
  public order!: Order;
  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute,
              private navigateRoute: Router, private service: Service, private appStateService: AppStateService) {
  }
  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => this.orderId = params['orderId']);
    this.getOrder();
  }
  public async getOrder(){
    this.order = await this.service.getOrder(this.orderId);
  }
  navigateToGetBill(orderId: string){
    this.navigateRoute.navigate(["/bill", orderId])
  }
  async confirmOrder(orderId: string){
    await this.service.confirmOrder(orderId);
    await this.getOrder();
  }
  editOrder(orderId: string){
    this.appStateService.setOrder(this.order);
    this.navigateRoute.navigate(["/edit-order", orderId]);
  }
}
