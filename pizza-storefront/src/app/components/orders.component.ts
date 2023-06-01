import { Component, OnInit } from '@angular/core';
import { OrderResponse, PizzaService } from '../pizza.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-orders',
  templateUrl: './orders.component.html',
  styleUrls: ['./orders.component.css']
})
export class OrdersComponent implements OnInit {
  email!: string;
  orders$!: Promise<OrderResponse>;
customer: any;

  constructor(
    private pizzaSvc: PizzaService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.email = this.activatedRoute.snapshot.data['email'];
    this.orders$ = this.pizzaSvc.getOrders(this.email);
  }

  back(): void {
    this.router.navigate(['/']);
  }
}