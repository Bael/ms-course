import BasketProduct from './basket-product'
export class Basket {
constructor(
       public id: number,
       public amount : number,
       public products : BasketProduct[])
}
