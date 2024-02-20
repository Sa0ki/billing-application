import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable, OnInit} from "@angular/core";
import {TokenResponse} from "../interfaces/TokenResponse";
import {Order} from "../interfaces/Order";
import {Product} from "../interfaces/Product";
import {Bill} from "../interfaces/Bill";
import {AppStateService} from "../app.state.service";
import {Customer} from "../interfaces/Customer";

@Injectable()

export class Service implements OnInit{
  private apiUrl: string = "http://localhost:9090/auth/customers";
  private token: TokenResponse | undefined;
  private tokenKey = "auth_token";
  private email: string = "";
  private name: string = "";
  constructor(private http: HttpClient, private appStateService: AppStateService) {
  }
  ngOnInit() {
  }

  public async getCustomerInformation(email: string): Promise<void>{
    try{
      const data: any = await this.http.post<any>(`${this.apiUrl}/get-customer?email=${email}`, {}, this.getHttpOptions()).toPromise();
      if(data){
        this.appStateService.setCustomer({
          id: data.id,
          firstName: data.firstName,
          lastName: data.lastName,
          email: data.email,
          password: data.password,
          dateOfBirth: data.dateOfBirth
        })
        console.log(this.appStateService.customer)
      }
    }catch(error){
      console.log(error)
    }

  }
  public async register(newCustomer: Customer): Promise<void>{
    try{
      const data: any = await this.http.post<any>(`${this.apiUrl}/register`, newCustomer).toPromise();
      if (data) {
        console.log("Account created !")
        } else {
          this.token = undefined;
          console.log("REGISTER FAILED.");
        }
      }
     catch (error) {
      console.error('Error during register:', error);
      throw error;
    }
  }
  public async login(username: string, password: string): Promise<boolean> {
    try {
      const data: any = await this.http.post<any>(`${this.apiUrl}/login?username=${username}&password=${password}`, {}).toPromise();
      if (data) {
        this.token = {
          access_token: data.token,
          expires_in: data.token.expires_in,
          refresh_expires_in: data.token.refresh_expires_in,
          refresh_token: data.token.refresh_token,
          token_type: data.token.token_type,
          not_before_policy: data.token['not-before-policy'],
          session_state: data.token.session_state,
          scope: data.token.scope
        };
        this.email = data.email;
        this.name = data.name;
        await this.getCustomerInformation(this.email);
        if (this.token.access_token != undefined) {
          this.appStateService.setAuthState({
            name: this.name,
            email: this.email,
            roles: "",
            isAuthenticated: true,
            token: this.token.access_token
          })
          this.saveTokenInLocalStorage();
          return true;
        } else {
          this.token = undefined;
          console.log("BAD CREDENTIALS.");
          return false;
        }
      }
    } catch (error) {
      this.token = undefined;
      console.error('Error during login:', error);
      return false;
    }
    return false;
  }
  public async getOrders(): Promise<Order[]> {
    try {
      const data: any = await this.http.post<Order[]>(`${this.apiUrl}/orders/get-orders?customerId=${localStorage.getItem("id")}`, {}, this.getHttpOptions()).toPromise();

      if (Array.isArray(data)) {
        return data.map((order: any) => ({
          id: order.id,
          customerId: order.customerId,
          date: order.date,
          status: order.status,
          totalDue: order.totalDue,
          products: new Map<string, Product>(Object.entries(order.products))
        }));
      } else {
        throw new Error('Invalid data returned');
      }
    } catch (error) {
      console.error('Error fetching orders:', error);
      throw error;
    }
  }

  public async getOrder(orderId: string): Promise<Order> {
    try {
      const data: any = await this.http.post<Order>(`${this.apiUrl}/orders/get-order?orderId=${orderId}`, {}, this.getHttpOptions()).toPromise();
      return {
        id: data.id,
        customerId: data.customerId,
        date: data.date,
        status: data.status,
        totalDue: data.totalDue,
        products: new Map<string, Product>(Object.entries(data.products))
      };
    } catch (error) {
      console.error('Error fetching order:', error);
      throw error;
    }
  }
  public async getBill(orderId: string): Promise<Bill> {
    this.appStateService.customer = {
      id: localStorage.getItem("id"),
      firstName: localStorage.getItem("name"),
      email: localStorage.getItem("email"),
      dateOfBirth: localStorage.getItem("dateOfBirth"),
      lastName: ""
    }
    try {
      console.log(this.appStateService.customer)
      const data: any = await this.http.post<Bill>(`${this.apiUrl}/bills/get-bill?orderId=${orderId}`, this.appStateService.customer,
        this.getHttpOptions()).toPromise();

      const bill: Bill = {
        id: data.id,
        customerId: data.customerId,
        orderId: data.orderId,
        pdf: data.pdf
      };

      await this.downloadBill(bill.id);

      return bill;
    } catch (error) {
      console.error('Error fetching bill:', error);
      throw error;
    }
  }

  private async downloadBill(billId: string): Promise<void> {
    try {
      const response: any = await this.http.post(`http://localhost:9090/customers/bills/download-bill?billId=${billId}`, {}, { responseType: 'blob' }).toPromise();
      const blob = new Blob([response], { type: 'application/pdf' });
      const url = window.URL.createObjectURL(blob);

      const a = document.createElement('a');
      a.href = url;
      a.download = 'bill.pdf';
      document.body.appendChild(a);
      a.click();

      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    } catch (error) {
      console.error('Error downloading PDF:', error);
      throw error;
    }
  }
  getHttpOptions(){
    let tokenAcess = "";
    if(this.token != undefined)
        tokenAcess = this.token.access_token;
    else
      tokenAcess = this.getTokenFromLocalStorage();
    if(tokenAcess != undefined && tokenAcess != "")
      return {
        headers: new HttpHeaders({
          "Authorization": "Bearer " + tokenAcess
        })
      };
      else
    return {};
  }
  getTokenResponse(){
    return this.token;
  }
  getTokenFromLocalStorage(): any{
      return localStorage.getItem(this.tokenKey);
  }

  saveTokenInLocalStorage(){
    if(this.token != undefined){
      localStorage.setItem(this.tokenKey, this.token.access_token);
      localStorage.setItem("name", this.name);
      localStorage.setItem("email", this.email);
      localStorage.setItem("id", this.appStateService.customer.id);
      localStorage.setItem("dateOfBirth", this.appStateService.customer.dateOfBirth);
    }

  }
  clearToken(){
      localStorage.removeItem(this.tokenKey);
      localStorage.removeItem("name");
      localStorage.removeItem("email");
    localStorage.removeItem("id");
      this.token = undefined;
      this.email = "";
      this.name = "";
      this.appStateService.setAuthState({
        name: "",
        email: "",
        roles: "",
        isAuthenticated: false,
        token: undefined
      })
  }
  public async getProducts(): Promise<any>{
    let products: Product[] = [];
    try{
      const data: any = await this.http.get<any>("http://localhost:9090/products/get-all-products").toPromise();
      if(data){
        products = data;
        return products;
      }
    }catch(error){
      console.log(error);
      return [];
    }
  }
  public async addToCart(productId: string, quantity: number): Promise<any>{
    try{
      const data: any = await this.http.post<Order>(`${this.apiUrl}/orders/place-order?customerId=${localStorage.getItem("id")}&productId=${productId}&quantity=${quantity}`, {}, this.getHttpOptions()).toPromise();
    }catch(error){
      console.log(error);
      return [];
    }
  }
  async confirmOrder(orderId: string):Promise<any>{
    try{
        return await this.http.post<Order>(`${this.apiUrl}/orders/confirm-order?orderId=${orderId}`, {}, this.getHttpOptions()).toPromise();
    }catch(error){
      console.log(error);
    }
  }


  async editOrder(order: Order, productsArray: Product[]): Promise<any>{
    console.log(order)
    try{
      await this.http.post<any>(`${this.apiUrl}/orders/update-order`, {"order": order, "products": productsArray}, this.getHttpOptions()).toPromise();
    }catch(error){
      console.log(error);
    }
  }
}
