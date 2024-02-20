import {Injectable, OnInit} from '@angular/core';
import {Service} from "./services/Service";
import {Customer} from "./interfaces/Customer";
import {Order} from "./interfaces/Order";
import {Product} from "./interfaces/Product";

@Injectable({
  providedIn: 'root'
})
export class AppStateService{
  public authState: any = {
    name: "undefined",
    email: "undefined",
    roles: "undefined",
    isAuthenticated: false,
    token: "undefined"
  }
  public customer: any;
  public order: any;
  constructor() { }


  public setAuthState(state: any){
    this.authState = {...this.authState, ...state}
  }
  public setCustomer(c: Customer){
    this.customer = {
      id: c.id,
      firstName: c.firstName,
      lastName: c.lastName,
      email: c.email,
      password: c.password,
      dateOfBirth: c.dateOfBirth
    }
  }
  public setOrder(o: Order){
    this.order = {
      id: o.id,
      customerId: o.customerId,
      date: o.date,
      status: o.status,
      totalDue: o.totalDue,
      products: o.products
    }
  }
}
