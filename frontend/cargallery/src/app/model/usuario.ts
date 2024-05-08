import { lista } from "./lista";

export interface Usuario {
    id:String;
    nome:String;
    email:String;
    senha:String;
    listaDesejo: Array<lista>;
}