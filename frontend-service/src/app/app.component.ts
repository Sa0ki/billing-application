import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {HttpClientModule} from "@angular/common/http";
import {Service} from "./services/Service";
import {AppStateService} from "./app.state.service";

@Component({
  selector: 'app-root',
  standalone: true,
    imports: [CommonModule, RouterOutlet, HttpClientModule, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent{
  title = 'frontend-service';
  public profile = "Profile";
  constructor(private service: Service, private router: Router, public appStateService: AppStateService) {
  }
  logout(){
    console.log("LOGOUT")
    this.service.clearToken();
    this.profile = "Profile";
    this.router.navigate(["/login"])
  }
  public tokenExists(): boolean{
    if(localStorage.getItem("auth_token") != undefined){
      this.appStateService.authState.name = localStorage.getItem("name");
      this.appStateService.authState.email = localStorage.getItem("email");
      this.profile = this.appStateService.authState.name;
      return true;
    }
    return false;
  }
}
