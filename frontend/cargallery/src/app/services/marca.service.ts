import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Marca } from '../model/marca';
import { environment } from '../environments/environment';
import { Carro } from '../model/carros';

@Injectable({
  providedIn: 'root'
})
export class MarcaService {

  private url = `${environment.apiUrlBase}/marca`;

  constructor(private http: HttpClient) { }

  public obterMarcas() : Observable<Array<Marca>> {
      let options = {
        headers: environment.headers
      };
      return this.http.get<Array<Marca>>(`${this.url}`, options);
  }

  public obterCarroMarca(id:String) : Observable<Array<Carro>> {
    let options = {
      headers: environment.headers
    };
    return this.http.get<Array<Carro>>(`${this.url}/${id}/carro`, options);
  }

  public criarCarro(request:any, id:String = "89c4a77d-5d41-4c0b-9826-0941e3f68e3e") {
    let options = {
      headers: environment.headers
    };
  
    return this.http.post<Marca>(`${this.url}/${id}/carro`, request, options);
  }

  public autocomplete(searchText: String): Observable<any> {
    let options = {
      headers: environment.headers
    };
    return this.http.get(`${this.url}/autocomplete?search=${searchText}`, options);
    
  }
}
