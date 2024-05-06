import { Carro } from "./carros";

export interface Marca {
    id:String,
    nome:String;
    carros:Array<Carro>
}