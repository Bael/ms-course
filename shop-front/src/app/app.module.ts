import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ProductCatalogComponent } from './product-catalog/product-catalog.component';
import { ProductCardComponent } from './product-card/product-card.component';
import { BasketComponent } from './basket/basket.component';
import { BasketProductComponent } from './basket-product/basket-product.component';
import { OrderInfoComponent } from './order-info/order-info.component';
import { OrderLineComponent } from './order-line/order-line.component';
import { OrderLinesComponent } from './order-lines/order-lines.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductCatalogComponent,
    ProductCardComponent,
    BasketComponent,
    BasketProductComponent,
    OrderInfoComponent,
    OrderLineComponent,
    OrderLinesComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
