import {Injectable, OnInit} from '@angular/core';
import {Service} from "./services/Service";
import {Customer} from "./interfaces/Customer";

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
}
