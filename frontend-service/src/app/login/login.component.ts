import {Component, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {NgIf} from "@angular/common";
import {Service} from "../services/Service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  public formLogin!:  FormGroup;
  public errorMessage: string = "";
  constructor(private http: HttpClient, private formBuilder: FormBuilder, private service: Service,
              private navigateRouter: Router) {
  }
  ngOnInit() {
      this.formLogin = this.formBuilder.group({
        username: this.formBuilder.control(""),
        password: this.formBuilder.control("")
      });
  }
   async login(){
    if(this.formLogin != undefined){
      let username = this.formLogin.value.username;
      let password = this.formLogin.value.password;

      await this.service.login(username, password);

      if(this.service.getTokenResponse() != undefined)
        this.navigateRouter.navigate(["/"])
    }
  }
}
