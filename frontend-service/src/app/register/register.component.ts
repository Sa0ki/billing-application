import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Service} from "../services/Service";
import {formatDate, NgIf} from "@angular/common";
import {Customer} from "../interfaces/Customer";
import {Router, RouterLink} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    NgIf,
    RouterLink,
    ReactiveFormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  public formRegister!: FormGroup;
  public message: string = "";
  constructor(private formBuilder: FormBuilder, private service: Service, private router: Router) {
  }
  ngOnInit() {
    this.formRegister = this.formBuilder.group({
      firstName: this.formBuilder.control(""),
      lastName: this.formBuilder.control(""),
      email: this.formBuilder.control(""),
      password: this.formBuilder.control(""),
      dateOfBirth: this.formBuilder.control(new Date())
    });
  }
  public async register(){
    let newCustomer: Customer = {
      id: undefined,
      firstName: this.formRegister.value.firstName,
      lastName: this.formRegister.value.lastName,
      email: this.formRegister.value.email,
      password: this.formRegister.value.password,
      dateOfBirth: this.formRegister.value.dateOfBirth
    }
    await this.service.register(newCustomer);
    this.message = "Account created ! Please Login."
  }
}
