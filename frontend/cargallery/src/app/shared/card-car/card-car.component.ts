import { Component, Input } from '@angular/core';
import { Carro } from '../../model/carros';
import { UsuarioService } from '../../services/usuario.service';

@Component({
  selector: 'app-card-car',
  standalone: true,
  imports: [],
  templateUrl: './card-car.component.html',
  styleUrl: './card-car.component.css'
})
export class CardCarComponent {
    @Input()
    public car?:Carro;

    constructor(private userService: UsuarioService) {}

    public favoritar() {
      var user = JSON.parse(sessionStorage["user_autenticated"]);

      this.userService.favoritar(user.id, this.car?.id as String)
      .subscribe(() => {
        console.log("favoritado com sucesso");
      });

    }
}
