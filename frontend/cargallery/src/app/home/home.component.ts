import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MarcaService } from '../services/marca.service';
import { Marca } from '../model/marca';
import { FilterComponent } from '../shared/filter/filter.component';
import { CardCarComponent } from '../shared/card-car/card-car.component';
import { Carro } from '../model/carros';
import { take } from 'rxjs';
import { CarModalComponent } from '../shared/car-modal/car-modal.component';
import { SearchComponent } from '../shared/search/search.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FilterComponent, CardCarComponent, CarModalComponent, SearchComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
   carro = new Array<Carro>();

   constructor(private marcaService: MarcaService) { }
   
   ngOnInit(): void {
       this.onFilter('ALL');
   }

   public reload() {
      this.onFilter('ALL');
   }

   public filterCar($event:any) {
     let result = this.carro.filter(x => x.id == $event);
     this.carro = [];
     this.carro = result;
   }

   public onFilter($event: any) {
      let id = $event;
      this.carro = [];
      if (id !== 'ALL') {
         this.marcaService.obterCarroMarca(id).pipe(take(1)).subscribe(response => {
            this.carro = response; 
         });
      } else {
         this.marcaService.obterMarcas().pipe(take(1)).subscribe(marcas => {
            for (const item of marcas) {
               this.carro.push(...item.carros);
            }
         });
      }

   }
}
