import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { MarcaService } from '../../services/marca.service';
import { Marca } from '../../model/marca';

@Component({
  selector: 'app-filter',
  standalone: true,
  imports: [],
  templateUrl: './filter.component.html',
  styleUrl: './filter.component.css',
})
export class FilterComponent implements OnInit {
  
  @Output()
  public filterAction = new EventEmitter<String>();

  marca:Array<Marca> = [];

  constructor(private marcaService: MarcaService) {}
  
  ngOnInit(): void {
    this.marcaService.obterMarcas().subscribe(response => {
      this.marca = response;
    });
  }

  public filter(id:String) {
    this.filterAction.emit(id);
  }
}
