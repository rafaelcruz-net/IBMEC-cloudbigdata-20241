import { Component, EventEmitter, Output } from '@angular/core';
import {AutocompleteLibModule} from 'angular-ng-autocomplete';
import { MarcaService } from '../../services/marca.service';
import { take } from 'rxjs';


@Component({
  selector: 'app-search',
  standalone: true,
  imports: [AutocompleteLibModule],
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  data = [];
  keyword = 'nome_carros';
  @Output() onSelectedItem = new EventEmitter<String>();
  @Output() onClearedItem = new EventEmitter();
  constructor(private marcaService: MarcaService) {}
  
  onChangeSearch(text: string) {
    this.marcaService.autocomplete(text).pipe(take(1)).toPromise().then(response => {
      this.data = response;
    });
    
  }

  onCleared(){
    this.data = [];
    this.onClearedItem.emit();
  }

  selectEvent($event: any) {
    this.onSelectedItem.emit($event.id);
  }
}
