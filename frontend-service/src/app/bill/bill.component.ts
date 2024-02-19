import {Component, OnInit} from '@angular/core';
import {Order} from "../interfaces/Order";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../interfaces/Product";
import {Bill} from "../interfaces/Bill";
import {Service} from "../services/Service";

@Component({
  selector: 'app-bill',
  standalone: true,
  imports: [],
  templateUrl: './bill.component.html',
  styleUrl: './bill.component.css'
})
export class BillComponent implements OnInit{
  private orderId: string = "";
  public bill: Bill | undefined;
  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute, private service: Service) {
  }
   ngOnInit(): void {
     this.activatedRoute.params.subscribe(params => this.orderId = params['orderId']);
     this.getBill();
   }
   public async getBill(){
    this.bill = await this.service.getBill(this.orderId);
  }
}
