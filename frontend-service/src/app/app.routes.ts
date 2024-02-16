import { Routes } from '@angular/router';
import {OrderComponentComponent} from "./order-component/order-component.component";
import {CustomerComponentComponent} from "./customer-component/customer-component.component";
import {OrderDetailsComponent} from "./order-details/order-details.component";
import {BillComponent} from "./bill/bill.component";

export const routes: Routes = [
  {path: "orders/:customerId", component: OrderComponentComponent},
  {path: "order-details/:orderId", component: OrderDetailsComponent},
  {path: "customers", component: CustomerComponentComponent},
  {path: "bill/:orderId", component: BillComponent}
];
