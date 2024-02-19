import {Injectable, OnInit} from '@angular/core';
import {Service} from "./services/Service";

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
  constructor() { }


  public setAuthState(state: any){
    this.authState = {...this.authState, ...state}
  }
}
