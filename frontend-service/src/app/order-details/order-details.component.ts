import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Order} from "../interfaces/Order";
import {JsonPipe, KeyValuePipe, NgForOf, NgIf} from "@angular/common";
import {Product} from "../interfaces/Product";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {Service} from "../services/Service";

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
  public order: Order | undefined;
  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute,
              private navigateRoute: Router, private service: Service) {
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
}
