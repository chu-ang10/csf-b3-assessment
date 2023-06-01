import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { PizzaService } from '../pizza.service';

const SIZES: string[] = [
  "Personal - 6 inches",
  "Regular - 9 inches",
  "Large - 12 inches",
  "Extra Large - 15 inches"
]

const PIZZA_TOPPINGS: string[] = [
  'chicken', 'seafood', 'beef', 'vegetables',
  'cheese', 'arugula', 'pineapple'
]

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent {

  form!: FormGroup
  fb = inject(FormBuilder)
  uploadSvc = inject(PizzaService)

  ngOnInit(): void {
    this.form = this.fb.group({
      name: this.fb.control<string>('', [Validators.required]),
      email: this.fb.control<string>('', [Validators.required]),
      size: this.fb.control<string>('', [Validators.required]),
      base: this.fb.control<string>('', [Validators.required]),
      sauce: this.fb.control<string>('', [Validators.required]),
      toppings: this.fb.control<string[]>([] as string[], [Validators.required]),
      comments: this.fb.control<string>('')

    })
  }

  processForm() {
    if (this.form.valid) {
      console.log(this.form.value)
      this.uploadSvc.placeOrder(this.form.value)
    }
  }


  pizzaSize = SIZES[0]

  constructor() { }

  updateSize(size: string) {
    this.pizzaSize = SIZES[parseInt(size)]
  }

}
