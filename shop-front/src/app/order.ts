export class Order {
  constructor(
       public id: number,
       public name: string,
       public lines : OrderLine[])
}
