import { Component } from '@angular/core';
import { Product } from './product';
import { Basket } from './basket';
import { BasketProduct } from './basket-product';
import { PRODUCTS } from './mock/mock-products';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Web shop';
  products = PRODUCTS;
//   products = [
//     new Product(1, 'Windstorm'),
//     new Product(13, 'Bombasto'),
//     new Product(15, 'Magneta'),
//     new Product(20, 'Tornado')
//   ];
  basket = new Basket(1, 20000, [new BasketProduct(1, 1, "bolid")]);
}


