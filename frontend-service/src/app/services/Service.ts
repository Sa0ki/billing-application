import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Injectable, OnInit} from "@angular/core";
import {TokenResponse} from "../interfaces/TokenResponse";
import {Order} from "../interfaces/Order";
import {Product} from "../interfaces/Product";
import {Bill} from "../interfaces/Bill";
import {AppStateService} from "../app.state.service";

@Injectable()

export class Service implements OnInit{
  private apiUrl: string = "http://localhost:9090/auth/customers";
  private token: TokenResponse | undefined;
  private tokenKey = "auth_token";
  constructor(private http: HttpClient, private appStateService: AppStateService) {
  }
  ngOnInit() {
  }

  public async login(username: string, password: string): Promise<void> {
    try {
      const data: any = await this.http.post<any>(`${this.apiUrl}/login?username=${username}&password=${password}`, {}).toPromise();

      if (data) {
        this.token = {
          access_token: data.access_token,
          expires_in: data.expires_in,
          refresh_expires_in: data.refresh_expires_in,
          refresh_token: data.refresh_token,
          token_type: data.token_type,
          not_before_policy: data['not-before-policy'],
          session_state: data.session_state,
          scope: data.scope
        };
        if (this.token != undefined) {
          this.appStateService.setAuthState({
            username: "",
            roles: "",
            isAuthenticated: true,
            token: this.token.access_token
          })
          this.saveTokenInLocalStorage();
        } else {
          this.token = undefined;
          console.log("BAD CREDENTIALS.");
        }
      }
    } catch (error) {
      this.token = undefined;
      console.error('Error during login:', error);
      throw error;
    }
  }
  public async getOrders(customerId: string): Promise<Order[]> {
    try {
      const data: any = await this.http.post<Order[]>(`${this.apiUrl}/orders/get-orders?customerId=${customerId}`, {}, this.getHttpOptions()).toPromise();

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
    try {
      const data: any = await this.http.post<Bill>(`${this.apiUrl}/bills/get-bill?orderId=${orderId}`, {
        id: "65c77eb06b4c6743eb5c83ec",
        firstName: "Kinan",
        lastName: "Saad"
      },
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
      a.download = 'facture.pdf';
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
    if(this.token != undefined)
      localStorage.setItem(this.tokenKey, this.token.access_token)
  }
  clearToken(){
      localStorage.removeItem(this.tokenKey);
      this.appStateService.setAuthState({
        username: "",
        roles: "",
        isAuthenticated: false,
        token: undefined
      })
  }
}
