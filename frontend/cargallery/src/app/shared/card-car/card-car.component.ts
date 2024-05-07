import { Component, Input } from '@angular/core';
import { Carro } from '../../model/carros';

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
}
