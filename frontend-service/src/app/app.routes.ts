import { Routes } from '@angular/router';
import {OrderComponentComponent} from "./order-component/order-component.component";
import {CustomerComponentComponent} from "./customer-component/customer-component.component";

export const routes: Routes = [
  {path: "orders", component: OrderComponentComponent},
  {path: "customers", component: CustomerComponentComponent}
];
