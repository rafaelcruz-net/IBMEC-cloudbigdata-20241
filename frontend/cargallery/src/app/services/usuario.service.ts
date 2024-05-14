import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../environments/environment';
import { Usuario } from '../model/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private url = `${environment.apiUrlBase}/usuario`;
  constructor(private http: HttpClient) { }

  public login(email:String, senha: String) : Observable<Usuario> {

    let body = {
      email: email,
      senha: senha
    };
    let options = {
      headers: environment.headers
    };

    return this.http.post<Usuario>(`${this.url}/login`, body, options);
  }

  public favoritar(id:String, idCar: String) {
    let body = {  
    };
    let options = {
      headers: environment.headers
    };
    return this.http.post(`${this.url}/${id}/favoritar/${idCar}`,body, options);
  }

  public obter(id:String) :Observable<Usuario> {
    let options = {
      headers: environment.headers
    };
    return this.http.get<Usuario>(`${this.url}/${id}`, options)
  }

}
