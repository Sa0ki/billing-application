import {Component, OnInit} from '@angular/core';
import {Service} from "../services/Service";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit{
  constructor(private service: Service) {
  }
ngOnInit() {
}
}
