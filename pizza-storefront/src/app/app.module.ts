import { NgModule, inject } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { MainComponent } from './components/main.component';
import { PizzaService } from './pizza.service';
import { OrdersComponent } from './components/orders.component';
import { Routes, Params, Router, RouterModule } from '@angular/router';

const appRoutes: Routes = [
  { path: '', component: MainComponent, title: 'Main' },
  { path: 'orders/:email', component: OrdersComponent, title: 'Orders' },
  { path: '**', redirectTo: '/', pathMatch: 'full' }
]

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
  ],
  imports: [
    BrowserModule, ReactiveFormsModule, HttpClientModule, RouterModule
  ],

  providers: [ PizzaService ],
  bootstrap: [AppComponent]
})
export class AppModule {
  numSvc = inject(PizzaService)

  constructor(private router: Router) { }
 }
