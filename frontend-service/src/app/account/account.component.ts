import {Component, OnInit} from '@angular/core';
import {AppStateService} from "../app.state.service";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [
    NgIf
  ],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit{
  public name = localStorage.getItem("name");
  public email = localStorage.getItem("email");
  public dateOfBirth = localStorage.getItem("dateOfBirth");
  public id = localStorage.getItem("id");
  constructor(public appStateService: AppStateService) {
  }
  ngOnInit() {
  }

  public tokenExists(): boolean{
    if(localStorage.getItem("auth_token") != undefined){
      this.name = localStorage.getItem("name");
      this.email = localStorage.getItem("email");
      this.dateOfBirth = localStorage.getItem("dateOfBirth");
      this.id = localStorage.getItem("id");
      this.appStateService.authState.isAuthenticated = true;
      return true;
    }
    return false;
  }
}
