import {Component, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router, RouterLink, RouterOutlet} from '@angular/router';
import {HttpClientModule} from "@angular/common/http";
import {Service} from "./services/Service";

@Component({
  selector: 'app-root',
  standalone: true,
    imports: [CommonModule, RouterOutlet, HttpClientModule, RouterLink],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit{
  title = 'frontend-service';
  constructor(private service: Service, private router: Router) {
  }
  ngOnInit() {
  }
  logout(){
    console.log("LOGOUT")
    this.service.clearToken();
    this.router.navigate(["/"])
  }
}
