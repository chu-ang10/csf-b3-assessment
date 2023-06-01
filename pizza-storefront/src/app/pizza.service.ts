import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, inject } from "@angular/core";
import { Observable, firstValueFrom, lastValueFrom } from "rxjs";

export interface Response {
  bundleId: string;
}

export interface SingleOrderResponse {
  orderId: string;
  date: number;
  total: string;
}

export type OrderResponse = SingleOrderResponse[];

@Injectable()

export class PizzaService {

  http = inject(HttpClient)

  // TODO: Task 3
  // You may add any parameters and return any type from placeOrder() method
  // Do not change the method name
  placeOrder(formData: FormData): Promise<Response> {
    return firstValueFrom(
      this.http.post<Response>(`http://localhost:8080/order`, formData).pipe()
    );
  }

  // TODO: Task 5
  // You may add any parameters and return any type from getOrders() method
  // Do not change the method name
  getOrders(email: String): Promise<OrderResponse> {
    return firstValueFrom(
      this.http.get<OrderResponse>(`http://localhost:8080/api/orders/${email}`).pipe()
    );
  }

  // TODO: Task 7
  // You may add any parameters and return any type from delivered() method
  // Do not change the method name
  delivered(orderId: String): Promise<null> {
    return firstValueFrom(
      this.http.delete<null>(`http://localhost:8080/api/order/${orderId}`).pipe()
    );
  }

}
