import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MarcaService } from '../services/marca.service';
import { Marca } from '../model/marca';
import { FilterComponent } from '../shared/filter/filter.component';
import { CardCarComponent } from '../shared/card-car/card-car.component';
import { Carro } from '../model/carros';
import { UserAutenticatedGuard } from '../guard/user-autenticated.guard';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FilterComponent, CardCarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
   carro = new Array<Carro>();

   constructor(private marcaService: MarcaService) { }
   
   ngOnInit(): void {
       //this.onFilter('ALL');
   }

   public onFilter($event: any) {
      let id = $event;
      this.carro = [];
      if (id !== 'ALL') {
         this.marcaService.obterCarroMarca(id).subscribe(response => {
            this.carro = response; 
         });
      } else {
         this.marcaService.obterMarcas().subscribe(marcas => {
            for (const item of marcas) {
               this.carro.push(...item.carros);
            }
         });
      }

   }
}
