import {Product} from "./Product";

export interface Order{
  id: string,
  customerId: string,
  date: string,
  status: string,
  totalDue: number,
  products: Map<string, Product>

}
