import { Routes } from '@angular/router';
import {OrderComponentComponent} from "./order-component/order-component.component";
import {CustomerComponentComponent} from "./customer-component/customer-component.component";
import {OrderDetailsComponent} from "./order-details/order-details.component";
import {BillComponent} from "./bill/bill.component";
import {LoginComponent} from "./login/login.component";
import {HomeComponent} from "./home/home.component";
import {AuthenticationGuard} from "./guards/authentication.guard";

export const routes: Routes = [
  {path: "orders/:customerId", component: OrderComponentComponent, canActivate: [AuthenticationGuard]},
  {path: "order-details/:orderId", component: OrderDetailsComponent, canActivate: [AuthenticationGuard]},
  {path: "customers", component: CustomerComponentComponent, canActivate: [AuthenticationGuard]},
  {path: "bill/:orderId", component: BillComponent, canActivate: [AuthenticationGuard]},
  {path: "login", component: LoginComponent},
  {path: "", component: HomeComponent}
]
