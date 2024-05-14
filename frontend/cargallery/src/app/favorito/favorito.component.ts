import { Component, OnInit } from '@angular/core';
import { Carro } from '../model/carros';
import { UsuarioService } from '../services/usuario.service';
import { CardCarComponent } from '../shared/card-car/card-car.component';

@Component({
  selector: 'app-favorito',
  standalone: true,
  imports: [CardCarComponent],
  templateUrl: './favorito.component.html',
  styleUrl: './favorito.component.css'
})
export class FavoritoComponent implements OnInit {
  carro = new Array<Carro>();
  user:any = undefined;
  constructor(private userService:UsuarioService) {}

  ngOnInit(): void {
    this.user = JSON.parse(sessionStorage["user_autenticated"]);

    this.userService.obter(this.user.id).subscribe(response => {
      for (const lista of response.listaDesejo) {
        this.carro.push(...(lista as any).carros);
        console.log(this.carro);
      }
    }); 


  }
  
  
  
  
}
