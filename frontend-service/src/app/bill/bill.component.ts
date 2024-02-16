import {Component, OnInit} from '@angular/core';
import {Order} from "../interfaces/Order";
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute} from "@angular/router";
import {Product} from "../interfaces/Product";
import {Bill} from "../interfaces/Bill";

@Component({
  selector: 'app-bill',
  standalone: true,
  imports: [],
  templateUrl: './bill.component.html',
  styleUrl: './bill.component.css'
})
export class BillComponent implements OnInit{
  private apiUrl: string = "http://localhost:8080/customers";
  private orderId: string = "";
  public bill: Bill | undefined;
  constructor(private http: HttpClient, private activatedRoute: ActivatedRoute) {
  }
   ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => this.orderId = params['orderId']);
    this.http.post<Bill>(`${this.apiUrl}/bills/get-bill?orderId=${this.orderId}`, {
      id: "65c77eb06b4c6743eb5c83ec",
      firstName: "Kinan",
      lastName: "Saad"
    })
      .subscribe({
        next: (data: Bill) => {
          this.bill = {
            id: data.id,
            customerId: data.customerId,
            orderId: data.orderId,
            pdf: data.pdf
          };
          this.http.post(`${this.apiUrl}/bills/download-bill?billId=${this.bill.id}`, {}, { responseType: 'blob' })
            .subscribe((response: Blob) => {
              const blob = new Blob([response], { type: 'application/pdf' });
              const url = window.URL.createObjectURL(blob);

              const a = document.createElement('a');
              a.href = url;
              a.download = 'facture.pdf';
              document.body.appendChild(a);
              a.click();

              window.URL.revokeObjectURL(url);
              document.body.removeChild(a);
            }, error => {
              console.error('Error downloading PDF:', error);
            });
        },
        error: (err) => {
          console.log(err);
        }
      });
  }
}
