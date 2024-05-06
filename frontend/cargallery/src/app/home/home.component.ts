import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MarcaService } from '../services/marca.service';
import { Marca } from '../model/marca';
import { FilterComponent } from '../shared/filter/filter.component';
import { CardCarComponent } from '../shared/card-car/card-car.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FilterComponent, CardCarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
   marca = new Array<Marca>();

   constructor(private marcaService: MarcaService) { }

   public adicionarProduto() {
      this.marcaService.obterMarcas().subscribe(response => {
          this.marca = response;
          console.log(response);
      });    
   }

   public onFilter($event: any) {
      console.log($event);
   }
}
