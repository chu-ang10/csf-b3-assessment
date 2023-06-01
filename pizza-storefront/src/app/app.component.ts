import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { PizzaService } from './pizza.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  pizzaSvc = inject(PizzaService)

  constructor(private router: Router) { }

  gotoOrders() {
    this.router.navigate([ '/orders' ])
  }
}
